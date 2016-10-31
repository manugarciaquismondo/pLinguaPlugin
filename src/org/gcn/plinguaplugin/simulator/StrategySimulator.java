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
import org.gcn.plinguacore.util.PlinguaCoreException;
/**
 * This class provides a common interface for running simulations without depending on the {@link SimulatorDisplayer} parameters
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class StrategySimulator {
	
	private SimulatorDisplayer displayer;
	/**
	 * Creates a new {@link StrategySimulator} instance whose parameters (if any) are taken from the {@link SimulatorDisplayer} instance given as argument
	 * @param displayer the {@link SimulatorDisplayer} instance which provides the simulation parameters
	 */
	public StrategySimulator(SimulatorDisplayer displayer){
		if (displayer == null)
			throw new NullPointerException(
					"Displayer argument shouldn't be null");
		this.displayer = displayer;
	}
	
	protected ISimulator getSimulator(){ return displayer.getCurrentSimulator();}
	/**
	 * Executes a simulation according to the specific parameters from the {@link SimulatorDisplayer} instance
	 * @throws PlinguaCoreException if the simulation execution couldn't be performed
	 */
	public abstract void run() throws PlinguaCoreException ;

}
