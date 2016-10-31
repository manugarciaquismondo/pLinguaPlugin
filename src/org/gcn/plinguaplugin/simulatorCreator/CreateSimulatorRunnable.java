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

package org.gcn.plinguaplugin.simulatorCreator;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.controller.SimulatorController;
import org.gcn.plinguaplugin.formatConstants.XMLConstants;
import org.gcn.plinguaplugin.simulator.SimulatorDisplayer;

/**
 * This class creates simulators reporting the operation process on a progress monitor
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class CreateSimulatorRunnable implements IRunnableWithProgress {

	private SimulatorCreatorDisplayer displayer;
	private boolean stepsBack;
	private boolean alternativeSteps;
	private boolean openConsole;
	/**
	 * Creates a new {@link CreateSimulatorRunnable} to create files which encode {@link ISimulator} instances
	 * @param displayer the {@link SimulatorCreatorDisplayer} instance where all the required parameters for creating a simulator file are 
	 * @param stepsBack a boolean indicating if the simulator to create will allow steps back
	 * @param alternativeSteps a boolean indicating if the simulator to create will allow alternative steps
	 * @param openConsole a boolean indicating if the simulation console will be opened when the file simulator has been created
	 */
	public CreateSimulatorRunnable(SimulatorCreatorDisplayer displayer, boolean stepsBack, boolean alternativeSteps, boolean openConsole) {
		super();
		if (displayer == null)
			throw new NullPointerException(
					"The Simulator Displayer argument shouldn't be null");
		this.displayer = displayer;
		this.stepsBack = stepsBack;
		this.alternativeSteps = alternativeSteps;
		this.openConsole = openConsole;
	}

	
	/**
	 * Creates the simulator file and reports the process
	 * @param monitor the monitor where the process is reported
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		/*Get the operation parameters*/
		PsystemController psystemController = displayer.getPsystemController();
		final String simulatorRoute = displayer.getSelectedFile();
		/*Set up the monitor*/
		int monitorLimit = 3;
		if(openConsole)
			monitorLimit++;
			monitor.beginTask("Creating " + displayer.getSelectedFile(), monitorLimit);
			/*Create the simulator to save*/
			final ISimulator simulator = psystemController.createSimulator(displayer.getPsystem(), stepsBack, alternativeSteps, displayer.getSelectedSimulator());
			if(psystemController.errorsFound()){
				displayer.reportError(simulatorRoute, psystemController.getLatestException());
				return;
		}
		monitor.worked(1);
		/*Save the simulator*/
		psystemController.saveSimulator(simulator, simulatorRoute);
		/*If errors ocurred, report them*/
		if(psystemController.errorsFound()){
			displayer.reportError(simulatorRoute, psystemController.getLatestException());
			return;
		}
		monitor.worked(1);
		/*Parse the P-system*/
		String psystemFileRoute = PsystemController.replaceExtension(simulatorRoute, XMLConstants.XML_EXTENSION);
		Psystem psystem = SimulatorController.getPsystem(simulator);
		psystemController.parsePsystemToOutputFile(psystemFileRoute, psystem, XMLConstants.XML_ID);
		/*If errors ocurred, report them*/
		if(psystemController.errorsFound()){
			displayer.reportError(psystemFileRoute, psystemController.getLatestException());
			return;
		}
		monitor.worked(1);
		/* If the checkbox for opening the simulator displayer is selected, display a SimulatorDisplayer instance*/
		if(openConsole){
			final SimulatorDisplayer simulatorDisplayer = new SimulatorDisplayer();
			
			/*Display the simulator console*/
			simulatorDisplayer.display();			
			/*Set the displayer simulator as the one to create*/
			simulatorDisplayer.setCurrentSimulator(simulator, simulatorRoute);
			monitor.worked(1);
		}
		// TODO Auto-generated method stub

	}

}
