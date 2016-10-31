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

package org.gcn.plinguaplugin.simulator;



import org.gcn.plinguaplugin.formatConstants.XMLConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.controller.SimulatorController;

/**
 * This class performs all functionality related to saving simulators on a specified route, such as in "Save" or "Save As" cases
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class SimulatorSaver extends SimulatorDisplayerReporter{

	private boolean result=false;
	
	private ISimulator simulator;
	private String route;
	
	
	/**
	 * Creates a new {@link SimulatorSaver} instance which will use simulatorDisplayer
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance used by the newly created instance
	 */
	public SimulatorSaver(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);


	}
	@Override
	protected void reportError(String error, Exception exception){
		super.reportError(error, exception);
		this.result = false;
	}

	/**
	 * Saves a simulator by encoding it on the file whose route is given as parameter
	 * @param simulator the simulator to save
	 * @param route the file route where the simulator will be encoded 
	 * @return true if the saving has been successful, false otherwise
	 */
	public boolean saveSimulator(ISimulator simulator, String route){
		if (route == null)
			throw new NullPointerException("Route argument shouldn't be null");
		this.route = route;
		if (simulator == null)
			return false;
		this.simulator = simulator;
		executeAction();
		return result;
	}
	
	@Override
	protected String getProcess(){
		return "Saving simulator";
	}
	
	@Override
	protected void executeSpecificAction(){
		
		
		/*Save the simulator*/
		getPlinguaController().saveSimulator(simulator, route);
		/*If errors occurred, report them*/
		if(getPlinguaController().errorsFound()){
			reportError(getPlinguaController().getReportMessage(), getPlinguaController().getLatestException());
		}

		/*Obtain the file route to parse the P-System to*/
		String psystemOutputFile = PsystemController.replaceExtension(route, XMLConstants.XML_EXTENSION);
		/*Parse the P-System*/
		getPlinguaController().parsePsystemToOutputFile(psystemOutputFile, SimulatorController.getPsystem(simulator), XMLConstants.XML_ID);
		/*If errors occurred, report them*/
		if(getPlinguaController().errorsFound()){
			reportError(getPlinguaController().getReportMessage(), getPlinguaController().getLatestException());
			return;
		}

		getSimulatorDisplayer().setCurrentSimulator(simulator, route);		
		this.result = true;
	}
	
	/**
	 * Ask the user if he wants to save the simulator by prompting a message box
	 * @param simulator the simulator to be saved
	 * @param route the file route where the simulator will be encoded
	 * @return if the operation which called the method should carry out its function
	 */
	public boolean querySave(ISimulator simulator, String route) {
		/*If there's no simulator to save or the simulator corresponds to the one on the file, return*/
		if (simulator==null||!getSimulatorDisplayer().isDirty()) return true;
		/*Create the query box in case the simulator could be saved*/
		Shell queryShell = new Shell(Display.getCurrent());
		MessageBox saveMessageBox = new MessageBox(queryShell, SWT.ICON_QUESTION|SWT.YES|SWT.NO|SWT.CANCEL);
		saveMessageBox.setMessage("Do you want to save the simulator "+route+"?");
		/*Open the query box*/
		int pressedButton = saveMessageBox.open();
		/*According to the user decision*/
		switch (pressedButton){
			/*If the user said "yes", save the simulator and load the file*/
			case SWT.YES:
				saveSimulator(simulator, route);
				return true;
			/*If the user said "no", load the file*/
			case SWT.NO:
				return true;
			/*If the user cancelled the operation, do not load the file*/
			case SWT.CANCEL:
				return false;
		}
		/*Otherwise, an error has occurred*/
		reportError("Unexpected saving result", new RuntimeException("Unexpected saving result"));
		return false;	
	}

	

}
