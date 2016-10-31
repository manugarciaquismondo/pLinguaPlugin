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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.gcn.plinguaplugin.controller.PsystemController;

/**
 * This class implements the listener for the simulator IDs list, so the selected simulator ID is updated 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class SimulatorListListener implements ISelectionChangedListener {

	private SimulatorCreatorDisplayer displayer;
	private SimulatorFeaturesWizardPage wizardPage;
	
	/**
	 * Sets up the selected ID as the ID of the simulator to be created 
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		/*Obtain the simulator ID*/
		String simulatorID = event.getSelection().toString();
		/*Remove the selection brackets*/
		simulatorID = simulatorID.substring(1, simulatorID.length()-1);
		displayer.setSelectedSimulator(simulatorID);
		/*Set the simulator ID*/
		PsystemController parserController = displayer.getPsystemController();
		wizardPage.setAlternativeStepsCheckBoxEnabled(parserController.supportsAlternativeStep(displayer.getPsystem(), simulatorID));
		/*Update page completition*/
		wizardPage.testCompleteness();

	}

	/**
	 * Creates a new {@link SimulatorListListener} instance to update the simulator ID
	 * @param displayer the {@link SimulatorCreatorDisplayer} which contains this instance
	 * @param wizardPage the {@link SimulatorFeaturesWizardPage} where the simulator IDs list is
	 */
	public SimulatorListListener(SimulatorCreatorDisplayer displayer, SimulatorFeaturesWizardPage wizardPage) {
		super();
		this.displayer = displayer;
		this.wizardPage = wizardPage;
	}

}
