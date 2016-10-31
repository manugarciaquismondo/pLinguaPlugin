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

import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;

/**
 * An abstract class which is intended to be subclassed by those classes which need to report errors to a {@link SimulatorDisplayer} class
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class SimulatorDisplayerReporter {
	private SimulatorDisplayer simulatorDisplayer;
	private SimulatorSaver simulatorSaver;
	
	protected SimulatorSaver getSimulatorSaver(){ return simulatorSaver;}
	
	/**
	 * Creates a new {@link SimulatorDisplayerReporter} instance
	 * @param simulatorDisplayer
	 */
	public SimulatorDisplayerReporter(SimulatorDisplayer simulatorDisplayer) {
		super();
		if (simulatorDisplayer == null)
			throw new NullPointerException(
					"SimulatorDisplayer argument shouldn't be null");
		this.simulatorDisplayer = simulatorDisplayer;
		this.simulatorSaver = simulatorDisplayer.getSimulatorSaver();
	}
	
	/**
	 * Returns the {@link SimulatorDisplayer} instance where all messages are reported to
	 * @return the {@link SimulatorDisplayer} instance where all messages are reported to
	 */
	protected SimulatorDisplayer getSimulatorDisplayer(){
		return simulatorDisplayer;
	}
	
	protected void reportError(String error, Exception exception){
		simulatorDisplayer.setMessageBoxText(error);
		PlinguaLog.logError(error, exception);
	}
	
	protected abstract void executeSpecificAction();
	
	protected PsystemController getPlinguaController(){
		return simulatorDisplayer.getPsystemController();
	}
	
	/**
	 * Executes the specific action of the class which uses {@link SimulatorDisplayer}
	 */
	public void executeAction(){
		/*Report the current process*/
		reportProcess(getProcess());
		/*Clear the report box*/
		simulatorDisplayer.clearMesageBoxText();
		/*Execute the action*/
		executeSpecificAction();
		/*Stop reporting the current process*/
		clearCurrentProcess();
	}

	protected abstract String getProcess();
	
	protected void reportProcess(String process){
		if(simulatorDisplayer!=null)
			simulatorDisplayer.setCurrentProcessText(process);
	}
	
	protected void clearCurrentProcess(){
		if(simulatorDisplayer!=null)
			simulatorDisplayer.clearCurrentProcessText();
	}

}
