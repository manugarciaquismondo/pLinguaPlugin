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

package org.gcn.plinguaplugin.builder;

import java.util.Iterator;

import org.gcn.plinguaplugin.formatConstants.PlinguaConstants;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.gcn.plinguacore.parser.input.VerbosityConstants;
import org.gcn.plinguacore.parser.input.messages.InputParserMsg;
import org.gcn.plinguacore.parser.input.messages.MsgInterval;


import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.builder.PlinguaFileAuditor;
import org.gcn.plinguaplugin.controller.PsystemController;


/**
 * This class visits all files within the given project and parses them, in case they are P-Lingua files, it is, they are encoded in P-Lingua format
 * @author Manuel Garcia-Quismondo-Fernandez
*/
public class PlinguaResourceVisitor implements IResourceVisitor {

	
	private PsystemController controller;
	
	/**
	 * Creates a new {@link PlinguaResourceVisitor} instance which uses the {@link PsystemController} given as parameter to use pLinguaCore functionality
	 * @param controller the {@link PsystemController} instance that the newly created instance will use
	 */
	public PlinguaResourceVisitor(PsystemController controller) {
		super();
		if (controller == null)
			throw new NullPointerException(
					"Controller argument shouldn't be null");
		this.controller = controller;
	}

	/**
	 * Visits a file within the project and parses it, in case it's a P-Lingua file.
	 * @return true if the file has been visited
	 */
	@Override
	public boolean visit(IResource resource) throws CoreException {
		// TODO Auto-generated method stub
	
		if(resource instanceof IFile){
			parseFile((IFile)resource);
		}
		return true;
	}
	
	private void parseFile(IFile file){
		/*Get the file extension*/
		String extension = file.getProjectRelativePath().getFileExtension();
		if(extension==null||!extension.equals(PlinguaConstants.PLINGUA_EXTENSION)) return;
		

		try {
			/*Obtain the P-Lingua parser*/
			
			
			/*Parse the file, whether it has errors or not*/
			controller.parsePsystemFromInputFile(file.getProjectRelativePath().toString(), file.getContents());		
			/*Add the obtained markers*/
			addMarkers(controller.getLatestReport().get("").iterator(), file);

		} catch (CoreException e) {
			// TODO Auto-generated catch block
			PlinguaLog.logError("The parsing markers couldn't be added", e);
		}
	}
	
	private void addMarkers(Iterator<InputParserMsg> iterator, IFile file) {
  	  int max=0;
		while(iterator.hasNext()){

			InputParserMsg msg = iterator.next();	
			/*If there's no marker interval, do not add the marker*/
			assert msg.getVerbosityLevel()>0||msg.hasInterval() : "The msg "+msg+" should have an interval";
			if(!msg.hasInterval()) continue;
			
			max++;
			/*Get the message interval*/
			MsgInterval msgInterval = msg.getInterval();
		      try {
		    	  /*Create the marker on the parsed file*/

		    	  IMarker marker = file.createMarker(PlinguaFileAuditor.PLINGUA_MARKER);

		    	  /*Set its attributes*/
		    	  marker.setAttribute(IMarker.MESSAGE, msg.toString());
			      //marker.setAttribute(IMarker.LINE_NUMBER, msgInterval.getBeginLine());
			      marker.setAttribute(IMarker.CHAR_START, msgInterval.getBeginByte());
			      marker.setAttribute(IMarker.CHAR_END, msgInterval.getEndByte());
			      marker.setAttribute(
			         IMarker.SEVERITY,
			         /*Translate its severity from P-Lingua Core notation to Eclipse notation*/
			         obtainIMarkerSeverity(msg.getVerbosityLevel()));
			      /*Report the parsing message*/
			      reportConsoleMessage(msg);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				PlinguaLog.logError(e);
			}

		}
		
		
	}



	/**Reports a message to be shown on the console*/
	private void reportConsoleMessage(InputParserMsg msg) {
		/*If it's necessary to report the message, report it*/
		if(isIncidence(msg.getVerbosityLevel()))
			PlinguaLog.log(obtainIStatusSeverity(msg.getVerbosityLevel()), IStatus.OK, msg.toString(), null);
		
	}

	/**Reports if a message is an incidence*/
	private boolean isIncidence(int verbosityLevel) {
		// TODO Auto-generated method stub
		
		switch(verbosityLevel){
			case(VerbosityConstants.ERROR):
				return true;
			case(VerbosityConstants.WARNINGS):
				return true;
			default:
				return false;
		}
	}

	/**Translates from P-Lingua severity option to IStatus severity option*/
	private int obtainIStatusSeverity(int statusType){
		switch(statusType){
			case(VerbosityConstants.ERROR):
				return IStatus.ERROR;
			case(VerbosityConstants.WARNINGS):
				return IStatus.WARNING;
			case(VerbosityConstants.GENERAL_INFO):
				return IStatus.INFO;
			case(VerbosityConstants.DETAILED_INFO):
				return IStatus.INFO;
			case(VerbosityConstants.MAXIMUM_VERBOSITY):
				return IStatus.INFO;
			default:
				throw new IllegalArgumentException("Message type not recognized");
		}
	}
	
	
	/**Translates from P-Lingua severity option to IMarker severity option*/
	private int obtainIMarkerSeverity(int msgType){
		switch(msgType){
			case(VerbosityConstants.ERROR):
				return IMarker.SEVERITY_ERROR;
			case(VerbosityConstants.WARNINGS):
				return IMarker.SEVERITY_WARNING;
			case(VerbosityConstants.GENERAL_INFO):
				return IMarker.SEVERITY_INFO;
			case(VerbosityConstants.DETAILED_INFO):
				return IMarker.SEVERITY_INFO;
			case(VerbosityConstants.MAXIMUM_VERBOSITY):
				return IMarker.SEVERITY_INFO;
			default:
				throw new IllegalArgumentException("Message type not recognized");
		}
	}



}
