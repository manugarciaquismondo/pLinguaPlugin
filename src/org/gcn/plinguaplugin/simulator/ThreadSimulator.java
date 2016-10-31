/*
 * This file is part of the pLinguaCore API 
 * pLinguaPlugin is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 *  pLinguaPlugin is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with P-Lingua compiler.  If not, see <http://www.gnu.org/licenses/>.
 *
 *  pLinguaCore 1.0 beta
 *  Copyright (c) 2008 Ignacio Perez-Hurtado (perezh@us.es), Manuel Garcia-Quismondo-Fernandez
 *  
 *  Research Group On Natural Computing
 *  http://www.gcn.us.es
 */

package org.gcn.plinguaplugin.simulator;




import org.gcn.plinguacore.util.PlinguaCoreException;
/**
 * This class gives support for simulations which are be interrupted when defined a timeout has elapsed
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */

class ThreadSimulator extends Thread{
	
	private StrategySimulator strategy;
	
/**
 * Creates a new {@link ThreadSimulator} instance which executes the run method in the {@link StrategySimulator} instance given as argument
 * @param strategy the {@link StrategySimulator} instance whose run method is executed by the newly created instance
 */
	public ThreadSimulator(StrategySimulator strategy) {
		super();

		if (strategy == null)
			throw new NullPointerException(
					"Simulator Displayer argument shouldn't be null");
		this.strategy = strategy;
	}


	
	/**
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		/*
		 * The simulation runs and when it has finished the ThreadSimulator
		 * instance reports the monitor
		 */
		try {
			strategy.run();
		} catch (PlinguaCoreException e) {
			end();
			return;

		}
		end();
	}

	protected synchronized void end(){
		notify();
		/*Give in execution time, as we can't destroy the thread*/
		//Thread.yield();
		try {
			finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
		}

		

	}



}
