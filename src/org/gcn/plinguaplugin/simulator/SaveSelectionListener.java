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

/**
 * This class performs Save actions to encode simulators on their files
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class SaveSelectionListener extends SimulatorDisplayerReporterListener{

	/**
	 * Creates a new SaveSelectionListener instance
	 * @param simulatorDisplayer the simulator displayer which holds the instance
	 */
	public SaveSelectionListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}


	/**
	 * Saves the {@link SimulatorDisplayer} simulator on its specified file
	 */
	@Override
	protected void executeSpecificAction() {
		/*If the simulator is not dirty, there's no point in saving it*/
		if(getSimulatorDisplayer().getCurrentSimulator()==null||!getSimulatorDisplayer().isDirty()) return;
		/*If there's a previous simulator, save it on the current file*/
		getSimulatorSaver().saveSimulator(getSimulatorDisplayer().getCurrentSimulator(), getSimulatorDisplayer().getCurrentSimulatorRoute());
		
		
	}

	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Saving file";
	}

}
