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

/**
 * An abstract class which is intended to be subclassed by those classes which need to report errors to a {@link SimulatorDisplayer} class and react to control events, such as clicking buttons and switching check boxes 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class SimulatorDisplayerReporterListener extends
		SimulatorDisplayerReporter implements SelectionListener {
	/**
	 * Creates a new {@link SimulatorDisplayerReporterListener} instance which performs actions related to the simulator of the {@link SimulatorDisplayer} given as argument
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance which this instance's button belongs to
	 */
	public SimulatorDisplayerReporterListener(
			SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}


	/**
	 * Executes the action specified by the specific class when the selection event takes place on the listener's control
	 * @param e the event which triggers the specific action
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		executeAction();

	}

}
