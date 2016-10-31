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
 * This class creates {@link StrategySimulator} instances whose specific instances depend on the given parameters, as stated in SimpleFactory idiom
 *
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class StrategySimulatorFactory {
	/**
	 * Creates a {@link StrategySimulator} instance according to the arguments given, as stated in SimpleFactory idiom
	 * @param steps the maximum number of steps the simulator can take. If it's less or equal to 0, there's no maximum steps limit
	 * @param timeOut the maximum time out the simulator can take. If it's less or equal to 0, there's no maximum time out limit
	 * @param displayer the {@link SimulatorDisplayer} instance which provides the parameters
	 * @return the {@link StrategySimulator} instance created 
	 */
	public static StrategySimulator createStrategySimulator(int steps, long timeOut, SimulatorDisplayer displayer){
		if(steps>0 && timeOut>0){

			return new StepsAndTimeOutStrategySimulator(steps, timeOut, displayer);
		}
		if(steps>0){
			return new StepsStrategySimulator(steps, displayer);
		}
		if(timeOut>0)
			return new TimeOutStrategySimulator(timeOut, displayer);
		return new RunStrategySimulator(displayer);
		
	}

}
