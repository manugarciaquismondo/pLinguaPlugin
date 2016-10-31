/* 
 * pLinguaPlugin: An Eclipse plug-in for Membrane Computing
 *              http://www.p-lingua.org
 *
 * Copyright (C) 2009  Manuel Garcia-Quismondo Fernandez
 *                      
 * This file is part of pLinguaPlugin.
 *
 * pLinguaPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * pLinguaPlugin is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with pLinguaCore.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.gcn.plinguaplugin.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguacore.parser.AbstractParserFactory;
import org.gcn.plinguacore.parser.input.InputParser;
import org.gcn.plinguacore.parser.input.InputParserFactory;
import org.gcn.plinguacore.parser.input.VerbosityConstants;
import org.gcn.plinguacore.parser.input.messages.InputParserMsg;
import org.gcn.plinguacore.parser.output.OutputParser;
import org.gcn.plinguacore.parser.output.OutputParserFactory;
import org.gcn.plinguacore.simulator.ElementFileStream;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguacore.util.psystem.factory.AbstractPsystemFactory;

/**
 * This class is used by classes to access pLinguaCore parsers functionality, specifically functionality related to writing and reading {@link Psystem} instances to or from files or streams, as stated on Controller GRASP 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class PsystemController {
	

	
	private Exception latestException;
	
	private String msgReport;
	
	private Map<String, Set<InputParserMsg>> latestReport;

	private String latestExtension;
	
	private boolean errorsFound;
	
	private InputParser latestParser;
	
	/**
	 * Parses a file encoding a P-system and returns the P-system parsed
	 * @param fileRoute the file route of the P-system to parse
	 * @return the {@link Psystem} instance parsed
	 */
	public Psystem parsePsystemFromInputFile(String fileRoute){
		try {
			Psystem psystem =  parsePsystemFromInputFile(fileRoute, new FileInputStream(fileRoute));
			/*If the P-system is not valid, report so*/
			if(psystem==null) return null;
			/*Report the file parse has succeeded*/
			processSucceeded();
			return psystem;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			registerError("The P-system file "+fileRoute+" couldn't be accessed", e);
			return null;
		}

		
	}
	
	
	

	
	/**
	 * Encodes a {@link Psystem} instance on a file specified by its route
	 * @param fileRoute the file route to parse the P-system on
	 * @param psystem the {@link Psystem} instance to parse
	 * @param formatID the ID format which defines the codification of the output file
	 */
	public void parsePsystemToOutputFile(String fileRoute, Psystem psystem, String formatID){
		try {
			/*Parse the P-system on a new output stream*/
			parsePsystemToOutputFile(new FileOutputStream(fileRoute), psystem, formatID);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			registerError("The P-system file "+fileRoute+" couldn't be accessed", e);
			return;
		}
		/*Report the file parse has succeeded*/
		processSucceeded();
	}
	
	/**
	 * Encodes a {@link Psystem} instance on an output stream
	 * @param outputStream the output stream to encode the P-system
	 * @param psystem the {@link Psystem} instance to parse
	 * @param formatID the ID format which defines the codification of the output file
	 */
	public void parsePsystemToOutputFile(OutputStream outputStream, Psystem psystem, String formatID){
		/*Check the arguments*/
		if (outputStream == null)
			throw new NullPointerException(
					"The output stream given as argument shouldn't be null");

		if (psystem == null)
			throw new NullPointerException(
					"the P-system given as argument shouldn't be null");

		if (formatID == null)
			throw new NullPointerException(
					"the ID format given as argument shouldn't be null");		
		/*Create the output parser factory*/
		OutputParserFactory outputParserFactory = new OutputParserFactory();
		OutputParser xmlOutputParser = null;
		try {
			/* Create the output parser*/
			xmlOutputParser = (OutputParser)outputParserFactory.createParser(formatID);
		}
		catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			registerError("There's no recognized output parser", e);
			return;
		}
		try{
			/*Parse the simulator P-system on the specified file*/
			xmlOutputParser.parse(psystem, outputStream);
			outputStream.close();
		}
		catch(Exception e){
			registerError("The simulator P-system couldn't be parsed to a file", e);
			return;
		}
		
		/*Report the file parse has succeeded*/
		processSucceeded();
	}
	
	/**
	 * Reads a {@link Psystem} instance from an {@link InputStream} instance
	 * @param fileRoute the file route  of the input stream
	 * @param inputStream the {@link InputStream} instance file route to read the P-system from
	 * @return the {@link Psystem} instance read
	 */
	public Psystem parsePsystemFromInputFile(String fileRoute, InputStream inputStream){
		/*Check the arguments*/
		if (fileRoute == null)
			throw new NullPointerException(
					"The File Route given as argument shouldn't be null");
		if (inputStream == null)
			throw new NullPointerException(
					"The Input Stream given as argument shouldn't be null");

		/*Obtain the file extension*/
		String extension = obtainExtension(fileRoute);
		/*In case it's not the last extension used, create a new input parser*/
		if(latestExtension==null || !latestExtension.equals(extension)){
			InputParserFactory inputParserFactory = new InputParserFactory();
			try {
				/*Create the parser by using its extension*/
				latestParser = (InputParser)inputParserFactory.createInputParserThroughExtension(extension);
			} catch (PlinguaCoreException e) {
				// TODO Auto-generated catch block
				registerError("There's no recognized input format whose extension is "+extension, e);
				return null;
			}
		}
		/*Set the verbosity level to the maximum*/
		latestParser.setVerbosityLevel(VerbosityConstants.MAXIMUM_VERBOSITY);
		Psystem psystem = null;
		try {
			/*Parse the P-system*/
			psystem =  latestParser.parse(inputStream);

					
		} 
		catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			registerError("The P-system encoded on the file "+fileRoute+" contains errors", e);
			latestReport = latestParser.getReport();	
			return null;
		}

		latestReport = latestParser.getReport();	
		assert latestReport!=null : "the latest report should not be null";
		/*Report the file parse has succeeded*/
		processSucceeded();
		return psystem;	
		

	}

	
	private static String obtainExtension(String fileRoute){
		return fileRoute.substring(fileRoute.lastIndexOf(".")+1);
	}

	/**
	 * Gets a boolean representing if the latest public method called reported errors
	 * @return true if the latest called public method reported errors, false otherwise
	 */
	public boolean errorsFound(){
		return errorsFound;
	}
	
	/**
	 * Gets the message reported by the latest public method called
	 * @return the message reported by the latest public method called
	 */
	public String getReportMessage(){
		return msgReport;
	}

	/**
	 * Gets the latest report produced by an input parsed called by a public method
	 * @return the latest report produced by an input parsed called by a public method
	 */
	public Map<String, Set<InputParserMsg>> getLatestReport() {
		return latestReport;
	}
	/*A common method for reporting errors*/
	private void registerError(String error, Exception e){
		errorsFound=true;
		latestException = e;
		this.msgReport = error;
	}
	
	/**
	 * Gets the latest exception thrown by a method
	 * @return the latest exception thrown by a method
	 */
	public Exception getLatestException(){
		return latestException;
	}
	
	/**
	 * Loads a {@link ISimulator} instance from a file whose route is given as argument. It doesn't load its {@link Psystem} instance, which should be loaded separately
	 * @param fileRoute the route of the file where the simulator is encoded
	 * @return the {@link ISimulator} instance  from the file indicated by fileRoute
	 */
	public ISimulator loadSimulator(String fileRoute){
		if (fileRoute == null)
			throw new NullPointerException(
					"The File Route argument shouldn't be null");

		/*Create the simulator reader*/
		ElementFileStream<ISimulator> elementFileStream= new ElementFileStream<ISimulator>(fileRoute);
		/*Load the simulator*/
		ISimulator simulatorRead = null;
		try{
			simulatorRead = elementFileStream.loadElement();
		}
		catch(IOException exception){
			registerError("The file "+fileRoute+" does not encode a valid simulator", exception);
			return null;
		}
		/*Report the simulator loading has succeeded*/
		processSucceeded();
		return simulatorRead;
	}
	
	
	private void processSucceeded(){
		/*Report the process has succeeded*/
		msgReport ="";
		errorsFound=false;
	}
	/**
	 * Saves a {@link ISimulator} instance on a file whose route is given as argument. It doesn't save its {@link Psystem} instance, which should be saved separately
	 * @param simulator the {@link ISimulator} instance to save
	 * @param fileRoute the route of the file where the simulator will be saved
	 */
	public void saveSimulator(ISimulator simulator, String fileRoute){
		if (fileRoute == null)
			throw new NullPointerException(
					"The File Route argument shouldn't be null");
		if (simulator == null)
			throw new NullPointerException(
					"The Simulator argument shouldn't be null");

		/*Create the file stream instance*/
		ElementFileStream<ISimulator> elementFileStream = new ElementFileStream<ISimulator>(simulator, fileRoute);
		try {
			/*Save the simulator*/
			elementFileStream.saveElement();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			registerError("The simulator couldn't be saved on the route "+fileRoute, e);
			return;
		}
		/*Report the simulator saving has succeeded*/
		processSucceeded();
	}
	
	/**
	 * Gets the available simulators IDs for a {@link Psystem} instance given as argument
	 * @param psystem the {@link Psystem} instance to get the available simulators IDs from
	 * @return an array containing the simulators IDs for psystem
	 */
	public String[] getAvailableSimulators(Psystem psystem){
		/*Obtain an iterator over the simulators IDs*/
		Iterator<String> simulatorIDs=null;
		try {
			simulatorIDs = psystem.getSimulatorsIDs();
		} catch (PlinguaCoreException e) {
			// TODO Auto-generated catch block
			registerError("The create simulator instance of the P-system couldn't be accessed", e);
			return null;
		}
		/*Pass the simulators to an array*/
		return toStringArray(simulatorIDs);
		
	}
	
	/**
	 * Returns if the simulator represented by simulatorID can take alternative steps when simulating the {@link Psystem} instance given as argument 
	 * @param psystem the {@link Psystem} instance to simulate
	 * @param simulatorID the ID of the simulator of the Psystem
	 * @return if the simulator related to id could be created capable of
	 *         performing alternative steps
	 */
	public boolean supportsAlternativeStep(Psystem psystem, String simulatorID){
		try{
			return psystem.supportsAlternativeStep(simulatorID);
		} catch (PlinguaCoreException e) {
		// TODO Auto-generated catch block
			registerError("The create simulator instance of the P-system couldn't be accessed", e);
			return false;
		}
	}
	
	
	/**
	 * Creates a simulator for the {@link Psystem} instance given as parameter
	 * @param psystem the {@link Psystem} instance to create a simulator for
	 * @param back
	 *            sets if the simulator created supports steps back
	 * @param alternative
	 *            sets if the simulator created supports alternate steps
	 * @param ID
	 *            the Simulator id which references the specific Simulator class
	 *            to be instantiated
	 * @return a simulator which complies with the parameter conditions
	 * 
	 */
	
	public ISimulator createSimulator(Psystem psystem, boolean back, boolean alternative, String ID){
		try{
			return psystem.createSimulator(back, alternative, ID);
		}
		catch(PlinguaCoreException exception){
			registerError("The simulator which ID is "+ID+" couldn't be created", exception);
			return null;
		}
		
	}
	
	/**
	 * Replaces a file route extension by switching it with the given extension
	 * @param fileRoute the file route whose extension will be switched
	 * @param extension the new extension of the file
	 * @return a string which is the original file route once switched its extension
	 */
	public static final String replaceExtension(String fileRoute, String extension){
		/*If the file has no extension, concat it*/
		if(!fileRoute.contains("."))
			return fileRoute+"."+extension;
		return fileRoute.substring(0, fileRoute.lastIndexOf("."))+"."+extension;
	}
	
	/**
	 * Gets the available models for P-systems
	 * @return an array containing all available models for P-systems
	 */
	public static String[] getAvailableModels(){
		/*Obtain the models*/
		Iterator<String> iterator = AbstractPsystemFactory.getModelsInfo().getModelsIterator();
		/*Pass the models to an array*/
		return toStringArray(iterator);
		
	}
	
	/**
	 * 
	 * Gets the available input formats
	 * @return a {@link String} array containing the available input formats
	 */
	public static String[] getAvailableInputFormats(){
		/*Obtain the input formats*/
		Iterator<String> iterator = AbstractParserFactory.getParserInfo().getInputFormatsIterator();
		/*Pass the input formats to an array*/
		return toStringArray(iterator);
		
	}
	
	/**
	 * 
	 * Gets the available output formats
	 * @return a {@link String} array containing the available output formats
	 */
	public static String[] getAvailableOutputFormats(){
		/*Obtain the output formats*/
		Iterator<String> iterator = AbstractParserFactory.getParserInfo().getOutputFormatsIterator();
		/*Pass the output formats to an array*/
		return toStringArray(iterator);
		
	}
	
	private static String[] toStringArray(Iterator<String> iterator){
		/*Create the list to pass to array*/
		List<String> elements = new LinkedList<String>();
		/*Add the elements*/
		while(iterator.hasNext())
			elements.add(iterator.next());
		/*Pass the elements to an array*/
		return elements.toArray(new String[0]);
	}
	
	
	/**
	 * Gets the file extension for the format given
	 * @param format the ID of the format
	 * @return the extension for the format given
	 */
	public static String getExtension(String format){

		return AbstractParserFactory.getParserInfo().getFileExtension(format);
	}

}
