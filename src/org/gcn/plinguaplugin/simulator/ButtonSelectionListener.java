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

import org.gcn.plinguacore.simulator.ISimulator;

/**
 * An abstract class for performing simulation actions when events which modify the simulator state (such as step forward, step back and so on) take place
 * It's supposed to be subclassed by those listeners which modify the simulator state when their actions take place.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class ButtonSelectionListener extends SimulatorDisplayerReporterListener{

	private SimulatorDisplayer simulatorDisplayer;
	
	/**
	 * Creates a new {@link ButtonSelectionListener} instance which performs actions related to the simulator of the {@link SimulatorDisplayer} given as argument
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which this instance's button belongs to
	 */
	public ButtonSelectionListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		if (simulatorDisplayer == null)
			throw new NullPointerException(
					"Simulator Displayer argument shouldn't be null");
		this.simulatorDisplayer = simulatorDisplayer;
	}

	
	protected ISimulator getSimulator(){
		return simulatorDisplayer.getCurrentSimulator();
	}

	
	
	@Override
	protected void executeSpecificAction() {
		/*If the specific condition (defined by the specific class) is not complied, do not execute the action*/
		if(!condition()) return;
		/*Otherwise, the simulator state changes*/
		simulatorDisplayer.setDirty(true);
		/*The action takes place*/
		executeSpecificButtonAction();	
		/*If the shell is still alive*/
		if(!simulatorDisplayer.getShell().isDisposed()){
			/*Update the interface*/
			simulatorDisplayer.resetTitle();
			simulatorDisplayer.updateConfigurationNumber();
			simulatorDisplayer.switchStepBack();
			simulatorDisplayer.switchReset();
			simulatorDisplayer.switchStepsForward();
		}

			

		
	}
	
	/*A method for checking if the current simulator configuration is the initial one*/


	
	protected boolean condition(){
		return getSimulator()!=null &&specificCondition();
	}
	


	protected abstract boolean specificCondition();


	protected abstract void executeSpecificButtonAction();


}
