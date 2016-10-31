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
 * This class causes the simulator of the {@link SimulatorDisplayer} instance the button belongs to to reset to its initial configuration
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class ResetListener extends ButtonSelectionListener{
	
	/**
	 * Creates a new {@link ResetListener} instance which causes the simulator of the {@link SimulatorDisplayer} given as argument to reset to its initial configuration
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which this instance's button belongs to
	 */
	public ResetListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void executeSpecificButtonAction() {
		SimulatorController.reset(getSimulator());
		
	}

	@Override
	protected boolean specificCondition() {
		// TODO Auto-generated method stub
		return !SimulatorController.isInitialConfig(getSimulator());
	}

	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Resetting simulator";
	}
	

}
