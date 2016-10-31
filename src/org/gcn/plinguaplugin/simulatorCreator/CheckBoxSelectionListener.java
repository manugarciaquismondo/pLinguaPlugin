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

package org.gcn.plinguaplugin.simulatorCreator;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * This abstract class describes a check box selection behavior which consists of enabling and disabling the simulator supported actions
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class CheckBoxSelectionListener implements SelectionListener {

	private Button checkBox;
	private SimulatorCreatorDisplayer displayer;
	

	
	/**
	 * Creates a new {@link CheckBoxSelectionListener} which describes a check box selection behavior which consists of enabling and disabling the simulator supported actions 
	 * @param checkBox the check box this instances describes a behavior for
	 * @param displayer the {@link SimulatorCreatorDisplayer} the check box belongs to
	 */
	public CheckBoxSelectionListener(Button checkBox, SimulatorCreatorDisplayer displayer) {
		super();
		
		if (displayer == null)
			throw new NullPointerException(
					"The Simulator Creator Displayer argument shouldn't be null");
		/*Check if the simulatorCreatorDisplayer argument is an instance of SimulatorCreatorDisplayer*/		
		this.displayer = displayer;
		if (checkBox == null)
			throw new NullPointerException(
					"Check Box argument shouldn't be null");
		this.checkBox = checkBox;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}
	
	protected SimulatorCreatorDisplayer getSimulatorCreatorDisplayer(){
		return displayer;
	}

	/**
	 * Sets the check box value as the one depending on the specific class
	 * @param e the event which triggers the value check box setting
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		setValue(checkBox.getSelection());
	}
	
	protected abstract void setValue(boolean value);

}
