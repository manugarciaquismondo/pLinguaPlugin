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

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * This class checks if the text within a text field defines a number greater than 0. The format number is defined by the specific {@link CheckNumberTextListener} child 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
abstract class CheckNumberTextListener implements ModifyListener {

	private SimulatorDisplayer simulatorDisplayer;
	private Text checkedText;
	private Button checkBox;
	
	/**
	 * Creates a new {@link SimulatorDisplayer} instance which test a text field
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance the field text belongs to
	 * @param checkedText the text field to test
	 * @param checkBox the check box which enables or disables the text field
	 */
	public CheckNumberTextListener(SimulatorDisplayer simulatorDisplayer, 
			Text checkedText, Button checkBox) {
		super();
		if (checkedText == null)
			throw new NullPointerException(
					"The checked text argument shouldn't be null");
		this.checkedText = checkedText;
		if (simulatorDisplayer == null)
			throw new NullPointerException(
					"the Simulator Displayer argument shouldn't be null");
		this.simulatorDisplayer = simulatorDisplayer;
		if (checkBox == null)
			throw new NullPointerException(
					"checkBox argument shouldn't be null");
		this.checkBox = checkBox;
	}

	
	/**
	 * Checks if the text complies defines a number greater than and complies with {@link CheckNumberTextListener} child format
	 */
	@Override
	public void modifyText(ModifyEvent e) {
		/*If the text doesn't match the expected numeric format*/
		if(!checkSpecificType(checkedText.getText())){
			/*Report the error and disable the simulator displayer controls*/
			simulatorDisplayer.setMessageBoxText("The "+getSpecificText()+" field should be an integer greater than 0");
			simulatorDisplayer.disableCommonFeatures();
			/*Ban file actions*/
			simulatorDisplayer.switchFileMenu(false);
			/*Only the checked text should be editable*/
			checkedText.setEditable(true);
			checkedText.setEnabled(true);
			checkBox.setEnabled(true);
		}
		else{
			/*Otherwise, enable the previous available actions*/
			simulatorDisplayer.enableCommonFeatures();
			/*Allow file actions*/
			simulatorDisplayer.switchFileMenu(true);
			/*Clear the previous message*/
			simulatorDisplayer.clearMesageBoxText();
			/*Reset the displayer title*/
			simulatorDisplayer.resetTitle();
			
		}
		
			
		

	}


	
	protected abstract boolean checkSpecificType(String text);


	protected abstract String getSpecificText();

}
