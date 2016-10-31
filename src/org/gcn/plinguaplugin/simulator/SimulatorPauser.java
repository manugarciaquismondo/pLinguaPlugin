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

import org.gcn.plinguaplugin.controller.SimulatorController;


/**
 * This class pauses the current simulation that its {@link SimulatorDisplayer} instance simulator is currently running
 */
 
class SimulatorPauser extends SimulatorDisplayerReporter{

	/**
	 * Creates a new {@link SimulatorPauser} instance which will pause the simulator from the {@link SimulatorDisplayer} instance given as argument 
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance whose simulator will be paused by the newly created instance
	 */
	public SimulatorPauser(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void executeSpecificAction() {
		/*If there's a simulator, stop it*/
		if(getSimulatorDisplayer().getCurrentSimulator()!=null)
			SimulatorController.stopThread(getSimulatorDisplayer().getCurrentSimulator());
		/*If the simulator is running on another thread, stop it as well*/
		if(getSimulatorDisplayer().getCurrentThreadSimulator()!=null)
			getSimulatorDisplayer().getCurrentThreadSimulator()
			.interrupt();


		
	}

	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Pausing simulator";
	}

}
