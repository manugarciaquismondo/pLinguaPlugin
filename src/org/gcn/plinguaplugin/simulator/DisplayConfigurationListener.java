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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.gcn.plinguaplugin.configurationinterface.IConfigurationPanel;
import org.gcn.plinguaplugin.configurationinterface.IConfigurationPanelFactory;
import org.gcn.plinguaplugin.controller.SimulatorController;

/**
 * This class displays the current configuration of its {@link SimulatorDisplayer} when the button whose behavior the instance controls is pressed
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class DisplayConfigurationListener implements SelectionListener {

	private boolean displayed;
	
	private IConfigurationPanel configurationPanel;
	private SimulatorDisplayer simulatorDisplayer;
	
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Displays the {@link SimulatorDisplayer} instance current configuration
	 * @param e the event which triggers the display
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		/*If there's no simulator displayer, return*/
		if(simulatorDisplayer.getCurrentSimulator()==null)return;
		/*Toggle the simulator displayer mark*/
		displayed=!displayed;
		/*If it wasn't displayer, display it*/
		if(displayed){
			/*Create the configuration panel*/
			configurationPanel = IConfigurationPanelFactory.createConfigurationPanel(SimulatorController.getCurrentConfig(simulatorDisplayer.getCurrentSimulator()));
			simulatorDisplayer.setConfigurationPanel(configurationPanel);
			configurationPanel.display();
			/*Report the panel is no longer displayed*/
			displayed=false;
		}
		/*Otherwise, dispose it*/
		else{
			simulatorDisplayer.disposeConfigurationPanel();
		}
		
	}

	/**
	 * Creates a new {@link DisplayConfigurationListener} instance to display a {@link SimulatorDisplayer} instance simulator's current configuration
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance whose simulator's current configuration is displayed
	 */
	public DisplayConfigurationListener(SimulatorDisplayer simulatorDisplayer) {
		super();
		if (simulatorDisplayer == null)
			throw new NullPointerException(
					"simulatorDisplayer argument shouldn't be null");
		this.simulatorDisplayer = simulatorDisplayer;
		/*By default, there's no displayed configuration*/
		displayed=false;
	}

}
