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



import org.gcn.plinguaplugin.formatConstants.SimulatorConstants;
import org.gcn.plinguaplugin.formatConstants.XMLConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.controller.SimulatorController;

/**
 * This class loads simulators and their associated P-systems.
 *  It's necessary to have both files (the simulator file and the P-system file) in the same directory, otherwise the load won't be able to be performed.
 *   All problems will be reported on the {@link SimulatorDisplayer} instance message box. The P-system must be codified on XML format to accomplish the loading.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class OpenFileSelectionListener extends SimulatorDisplayerReporterListener{

	
	/**
	 * Creates a new {@link OpenFileSelectionListener} instance
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which creates this instance and delegates loading functionality on it
	 */
	public OpenFileSelectionListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);		
	}


	
	
	
	@Override
	protected void reportError(String error, Exception exception){
		super.reportError(error, exception);
		/*Clear the existent simulator due to errors occurred*/
		getSimulatorDisplayer().disableCommonFeatures();
		getSimulatorDisplayer().clearSimulator();
	}
	
	/** 
	 * Loads the simulator when the load action takes place. If there are any problems, they will be reported on the message box.
	 * @param e the event which unleash the simulator loading
	 */
	@Override
	protected void executeSpecificAction() {
		/*If there's a previous simulator*/
		if(getSimulatorDisplayer().getCurrentSimulator()!=null)
			/*If the user decides not to save, abort the loading*/
			if(!getSimulatorSaver().querySave(getSimulatorDisplayer().getCurrentSimulator(), getSimulatorDisplayer().getCurrentSimulatorRoute())) return;
		/*Create the open file dialog*/
		FileDialog openFileDialog = new FileDialog(getSimulatorDisplayer().getShell(), SWT.OPEN);
		/*The file dialog will only allow .sim files*/
		openFileDialog.setFilterExtensions(new String[]{"*."+SimulatorConstants.SIM_EXTENSION, "*.*"});
		String fileRoute = openFileDialog.open();
		if (fileRoute==null) return;	
		/*Load the simulator*/
		ISimulator simulatorRead = getPlinguaController().loadSimulator(fileRoute);
		/*If errors occurred, report them*/
		if(getPlinguaController().errorsFound()){
			reportError(getPlinguaController().getReportMessage(), getPlinguaController().getLatestException());
			return;
		}
		/*Obtain the XML associated file codifying a P-system*/
		String psystemRoute = PsystemController.replaceExtension(fileRoute, XMLConstants.XML_EXTENSION);
		/*Parse the psystem associated to the simulator*/
		Psystem psystem = getPlinguaController().parsePsystemFromInputFile(psystemRoute);
		/*If the parsing process had errors, stop the loading*/
		if(getPlinguaController().errorsFound()){
			reportError(getPlinguaController().getReportMessage(), getPlinguaController().getLatestException());
			return;
		}
		/*Set the simulator fields*/
		SimulatorController.setPsystem(simulatorRead, psystem);
		SimulatorController.setInfoChannel(simulatorRead, System.out);
		/*Dispose the previous configuration panel*/
		getSimulatorDisplayer().disposeConfigurationPanel();
		/*Set simulator displayer features*/				
		getSimulatorDisplayer().setCurrentSimulator(simulatorRead, fileRoute);
		getSimulatorDisplayer().setDirty(false);		
	}

	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Loading file";
	}

}
