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
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * This class toggles the related text field when a check box is toggled. It only works in case the simulator from its {@link SimulatorDisplayer} instance is not null
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class CheckBoxListener implements SelectionListener {

	private Text toggledText;
	private Button toggledButton;
	private SimulatorDisplayer simulatorDisplayer;
	private CheckNumberTextListener checkNumberListener;
	
	/**
	 * Creates a new {@link CheckBoxListener} instance
	 * @param toggledText the text this instance will toggle (from editable to not editable or vice versa)
	 * @param toggledButton the button this instance considers in order to toggle the text
	 * @param simulatorDisplayer the simulator displayer which toggledText and toggledButton belong to
	 * @param checkNumberListener The {@link CheckNumberTextListener} instance which controls toggledButton behavior
	 */
	public CheckBoxListener(Text toggledText, Button toggledButton, SimulatorDisplayer simulatorDisplayer, CheckNumberTextListener checkNumberListener) {
		super();
		if (toggledText == null)
			throw new NullPointerException(
					"Toggled Text argument shouldn't be null");
		this.toggledText = toggledText;
		if (toggledButton == null)
			throw new NullPointerException(
					"Toggled Button argument shouldn't be null");
		this.toggledButton = toggledButton;
		if (simulatorDisplayer == null)
			throw new NullPointerException(
					"Simulator Displayer argument shouldn't be null");
		this.simulatorDisplayer = simulatorDisplayer;
		if (checkNumberListener == null)
			throw new NullPointerException(
					"CheckNumberListener argument shouldn't be null");
		this.checkNumberListener = checkNumberListener;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Switches the toggled text according to the button state
	 * @param e the event which triggers the toggling process
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		toggledText.setEditable(simulatorDisplayer.getCurrentSimulator()!=null&&toggledButton.getSelection());
		/*If the check button is now off, simulation actions should be allowed again even if the text box content condition is not complied*/
		if(!(toggledButton.getSelection())){
			/*Enable simulator actions*/
			simulatorDisplayer.enableCommonFeatures();
			/*Enable file actions*/
			simulatorDisplayer.switchFileMenu(true);
			/*Reset the displayer title*/
			simulatorDisplayer.resetTitle();
		}
		else{
			/*If the text box string is does not comply with the check number condition*/
			if(!checkNumberListener.checkSpecificType(toggledText.getText()))	{			
				/*Disable simulator actions*/
				simulatorDisplayer.disableCommonFeatures();
				/*Disable file actions*/
				simulatorDisplayer.switchFileMenu(false);
				/*Enable specific check box data, both check box and text field*/
				toggledButton.setEnabled(true);
				toggledText.setEnabled(true);
			}
		}

	}

}
