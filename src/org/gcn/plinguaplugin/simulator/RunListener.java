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

import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguaplugin.controller.SimulatorController;


/**
 * This class executes simulation actions according to the specific instance
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class RunListener extends ButtonSelectionListener {

	

	/** Creates a new {@link StepForwardListener} instance which causes the simulator of the {@link SimulatorDisplayer} given as argument to execute simulation actions
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which this instance's button belongs to
	 */
	public RunListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}

	
	private void setUpSimulation(){
		/*Obtain the simulator displayer*/
		SimulatorDisplayer simulatorDisplayer = getSimulatorDisplayer();		
		/*Set up the simulator displayer*/
		simulatorDisplayer.disableCommonFeatures();
		simulatorDisplayer.switchFileMenu(false);
		simulatorDisplayer.setPauseButton(true);


		
	}
	@Override
	protected void executeSpecificButtonAction() {
		setUpSimulation();
		StrategySimulator strategySimulator= obtainStrategySimulator();
		/*Create a new thread to run the simulator*/
		ThreadSimulator threadSimulator = new ThreadSimulator(strategySimulator);
		/*Set the current simulator thread*/
		getSimulatorDisplayer().setCurrentThreadSimulator(threadSimulator);
		/*We want the thread no to interrupt others when its execution time has elapsed, and to be disposed by the JVM*/
		threadSimulator.setDaemon(true);
		/*Start the simulator*/
		threadSimulator.start();

		
		Shell shell = getSimulatorDisplayer().getShell();
		/*Wait until the time execution has finished, the displayer has been closed or the pause button has been clicked*/
		while(threadSimulator.isAlive()&&(!(threadSimulator.isInterrupted())&&!(shell.isDisposed()))){
		    if (!shell.getDisplay().readAndDispatch())
						shell.getDisplay().sleep();
	
		}
		/*Finish the thread*/
		if(threadSimulator.isAlive()){
			threadSimulator.end();
			threadSimulator.interrupt();
		}
			
		/*Stop allowing to pause the simulation*/

		reportSimulation();


	}
	
	private StrategySimulator obtainStrategySimulator() {
		// TODO Auto-generated method stub
		/*Obtain simulation parameters*/
		int steps = getSimulatorDisplayer().limitedSteps();
		long timeOut = getSimulatorDisplayer().limitedTimeOut();
		return StrategySimulatorFactory.createStrategySimulator(steps, timeOut, getSimulatorDisplayer());		
	}
	
	@Override
	protected boolean specificCondition() {
		// TODO Auto-generated method stub
		/*Return true if the simulator hasn't finished, false otherwise*/
		return !SimulatorController.isFinished(getSimulator());
		
	}

	@Override
	protected String getProcess() {
		// TODO Auto-generated method stub
		return "Running simulation";
	}


	private void reportSimulation() {
		/*If there's no shell, return*/
		if(getSimulatorDisplayer().getShell().isDisposed()) return;
		/*Stop allowing to stop the simulator*/
		getSimulatorDisplayer().enableCommonFeatures();
		/*Restart displayer features*/
		getSimulatorDisplayer().setPauseButton(false);
		/*Allow to open files*/
		getSimulatorDisplayer().switchFileMenu(true);
		/*Update the configuration number*/
		getSimulatorDisplayer().updateConfigurationNumber();

	}



}
