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

import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguaplugin.controller.SimulatorController;



/**
 * This class causes the simulator from the {@link SimulatorDisplayer} instance the button belongs to to take steps forward
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class StepForwardListener extends StepListener {

	/**
	 * Creates a new {@link StepForwardListener} instance which causes the simulator of the {@link SimulatorDisplayer} given as argument to take steps forward
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which this instance's button belongs to
	 */
	public StepForwardListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected boolean specificCondition(){
		/*Return true if the simulator hasn't finished, false otherwise*/
		return !SimulatorController.isFinished(getSimulator());
		
	}



	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Taking step forward";
	}




	@Override
	protected void specificStepAction() {
		try {
			SimulatorController.step(getSimulator());
		}
		catch(PlinguaCoreException exception){
			reportError("Errors ocurred while taking a step forward", exception);
		}
		// TODO Auto-generated method stub
		
	}



}
