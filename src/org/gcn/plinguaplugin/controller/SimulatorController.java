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

package org.gcn.plinguaplugin.controller;

import java.io.PrintStream;

import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguacore.util.PlinguaCoreException;
import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.Psystem;

/**
 * This class is used by classes to access pLinguaCore ISimulator functionality, as stated on Controller GRASP 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class SimulatorController {
	
	/**
	 * Runs the simulator given as argument
	 * @param simulator the simulator to run
	 * @throws PlinguaCoreException if the run process produced any error
	 */
	public synchronized static void run(ISimulator simulator) throws PlinguaCoreException{
		if(simulator==null) return;
			simulator.run();	
	}
	
	/**
	 * Causes the simulator given as argument to take a step
	 * @param simulator the simulator which takes the step
	 * @return true if the step has been taken successfully, false otherwise or in case the simulator is null
	 * @throws PlinguaCoreException if the taken step produced any error
	 */
	public synchronized static boolean step(ISimulator simulator) throws PlinguaCoreException{
		if(simulator==null) return false;
		return simulator.step();
		
	}
	
	/**
	 * Cleans the previous configurations of the simulator given as argument
	 * @param simulator the simulator whose previous configurations are cleaned
	 */
	public static void cleanPreviousConfigurations(ISimulator simulator){
		if(simulator==null) return;
			simulator.cleanPreviousConfigurations();
	}
	
	/**
	 * * Stops the simulator given as argument
	 * @param simulator the simulator whose execution is stopped
	 */
	public static void stopThread(ISimulator simulator){
		if(simulator==null) return;
			simulator.stopThread();
	}
	
	/**
	 * Runs the simulator given as argument limiting a maximum number of steps
	 * @param simulator the simulator to run
	 * @param steps the maximum number of steps to take
	 * @throws PlinguaCoreException if the simulator run produced any error
	 */
	public synchronized static void runSteps(ISimulator simulator, int steps) throws PlinguaCoreException{
		if(simulator==null) return;
			simulator.runSteps(steps);
	}
	
	/**
	 * Resets the simulator given as argument
	 * @param simulator the simulator to reset
	 */
	public static void reset(ISimulator simulator){
		if(simulator==null) return;
			simulator.reset();
	}
	
	/**
	 * Causes the simulator given as argument to take a step back
	 * @param simulator the simulator which takes the step back
	 * @return true if the step back has been taken successfully, false otherwise or in case the  the simulator is null
	 */
	public synchronized static boolean stepBack(ISimulator simulator){
		if(simulator==null) return false;
		/*If it's possible to take steps back*/
		if(simulator.stepsBackAvailable())
			/*Take a step back*/
			return simulator.stepBack();
		return false;
	}
	
	/**
	 * Runs the simulator given as argument limiting a maximum time out
	 * @param simulator the simulator to run
	 * @param timeOut the maximum time out the simulation can take
	 */
	public synchronized static void runUntilTimeOut(ISimulator simulator, long timeOut){
		if(simulator==null) return;
			simulator.runUntilTimeOut(timeOut);
	}
	
	/**
	 * Runs the simulator given as argument limiting a maximum time out and a maximum number of steps
	 * @param simulator the simulator to run
	 * @param timeOut the maximum time out the simulation can take
	 * @param steps the maximum number of steps the simulation can take
	 */
	public synchronized static void runUntilTimeOutorSteps(ISimulator simulator, long timeOut, int steps){
		if(simulator==null) return;
			simulator.runUntilTimeOutorSteps(timeOut, steps);
	}
	
	/**
	 * Gets if the simulator current configuration is the first one
	 * @param simulator the simulator to get if its current configuration is the initial one
	 * @return true if the simulator current configuration is the initial one, false otherwise or in case the simulator is null
	 */
	public synchronized static boolean isInitialConfig(ISimulator simulator){
		if(simulator==null) return false;
			return simulator.getCurrentConfig().getNumber()==0;
	}
	
	/**
	 * Gets if the simulator can take steps back
	 * @param simulator the simulator to get if it can take steps back
	 * @return true if the simulator can take steps back, false otherwise
	 */
	public synchronized static boolean stepsBackAvailable(ISimulator simulator){
		if(simulator==null) return false;
			return simulator.stepsBackAvailable();
	}
	
	/**
	 * Gets the P-system from the simulator given as argument
	 * @param simulator the simulator to get the P-system from
	 * @return the simulator P-system if the simulator is not null, null otherwise
	 */
	public static Psystem getPsystem(ISimulator simulator){
		if(simulator==null) return null;
		return simulator.getPsystem();
	}
	
	/**
	 * Sets the simulator P-system with the one given as argument
	 * @param simulator the simulator whose P-system is set
	 * @param psystem the P-system to set
	 */
	public static void setPsystem(ISimulator simulator, Psystem psystem){
		if(simulator==null) return;
			simulator.setPsystem(psystem);
	}
	
	/**
	 * Sets the simulator info channel with the one given as argument
	 * @param simulator the simulator whose info channel is set
	 * @param infoChannel the info channel to set
	 */
	public static void setInfoChannel(ISimulator simulator, PrintStream infoChannel){
		if(simulator==null) return;
			simulator.setInfoChannel(infoChannel);
	}
	
	/**
	 * 
	 * Gets if the simulator supports steps back
	 * @param simulator the simulator to test if it supports steps back
	 * @return true if the simulator supports steps back, false otherwise or in case the simulator is null
	 */
	public static boolean supportsStepBack(ISimulator simulator){
		if(simulator==null) return false;
		return simulator.supportsStepBack();
	}
	/**
	 * 
	 * Gets if the simulator supports alternative steps
	 * @param simulator the simulator to test if it supports alternative steps
	 * @return true if the simulator supports alternative steps, false otherwise or in case the simulator is null
	 */
	public static boolean supportsAlternateSteps(ISimulator simulator){
		if(simulator==null) return false;
		return simulator.supportsAlternateSteps();
	}
	
	/**
	 * Gets the current configuration of the simulator given as parameter
	 * @param simulator the simulator whose current configuration is got
	 * @return the current configuration of the simulator if the simulator is not null, null otherwise
	 */
	public static Configuration getCurrentConfig(ISimulator simulator){
		if(simulator==null) return null;
		return simulator.getCurrentConfig();
	}
	
	/**
	 * Returns if the simulator given as parameter has reached a halting configuration
	 * @param simulator the simulator to test if it has reached a halting configuration
	 * @return true if the simulator has reached a halting configuration, false otherwise
	 */
	public static boolean isFinished(ISimulator simulator){
		return simulator.isFinished();
		
	}

}
