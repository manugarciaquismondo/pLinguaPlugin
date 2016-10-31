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

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.gcn.plinguaplugin.controller.SimulatorController;

/**
 * This class closes the currently active configuration panel (if any) and pauses the threads which are still alive when the {@link SimulatorDisplayer} instance given as parameter is closed 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class CloseShellListener implements DisposeListener {

	private SimulatorDisplayer simulatorDisplayer; 
	
	/**
	 * Creates a new {@link CloseShellListener} instance which closes the currently active configuration panel (if any) and pauses the threads which are still alive when the {@link SimulatorDisplayer} instance given as parameter is closed
	 * @param simulatorDisplayer
	 */
	public CloseShellListener(SimulatorDisplayer simulatorDisplayer) {
		super();
		this.simulatorDisplayer = simulatorDisplayer;
	}

	/**
	 * Closes the currently active configuration panel (if any) and pauses all alive threads
	 * @param e the event which triggers the thread pause and the configuration panel closing
	 */
	@Override
	public void widgetDisposed(DisposeEvent e) {
		if (!(simulatorDisplayer.getSimulatorSaver().querySave(simulatorDisplayer.getCurrentSimulator(), simulatorDisplayer.getCurrentSimulatorRoute()))) return;
		synchronized(simulatorDisplayer){
			/*Kill simulations threads*/
			(new SimulatorPauser(simulatorDisplayer)).executeAction();
			/*Kill main thread*/
			SimulatorController.stopThread(simulatorDisplayer.getCurrentSimulator());
			/*Dispose the configuration panel*/
			simulatorDisplayer.disposeConfigurationPanel();
			
		}
		

	}

}
