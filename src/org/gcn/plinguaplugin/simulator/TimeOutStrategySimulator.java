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
 * This class runs the simulator limiting the time out
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class TimeOutStrategySimulator extends StrategySimulator {

	private long timeOut;

	/**
	 * Creates a new {@link StepsAndTimeOutStrategySimulator} instance which runs the {@link SimulatorDisplayer} instance simulator limiting the time out
	 * @param timeOut the time out for the simulation
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which the newly created instance belongs to
	 */
	public TimeOutStrategySimulator(long timeOut, SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		if(timeOut<=0)
			throw new IllegalArgumentException("Time out parameter shouldn't be 0 nor less");		
		this.timeOut = timeOut;
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see org.gcn.plinguaplugin.simulator.StrategySimulator#run()
	 */
	@Override
	public void run() throws PlinguaCoreException {
		SimulatorController.runUntilTimeOut(getSimulator(),timeOut);
		
	}
	
	


}
