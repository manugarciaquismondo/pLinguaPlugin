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

package org.gcn.plinguaplugin.wizardCommonComponents;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Text;

/**
 * This class checks if the text in a text field matches the expected pattern and updates its specific {@link WizardPage} instance wizard field
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class AbstractTextListener implements ModifyListener {
	

	
	private WizardPage paramPage;

	private Text textField;

	/**
	 * Creates a new {@link AbstractTextListener} instance which contains the text field given as parameter
	 * @param paramPage the page which the text field belongs to 
	 * @param textField the text field to listen for changes
	 */
	public AbstractTextListener(WizardPage paramPage, Text textField){
		if (paramPage == null)
			throw new NullPointerException(
					"The Wizard Page argument shouldn't be null");
		this.paramPage = paramPage;
		if (textField == null)
			throw new NullPointerException(
					"The Text Field argument shouldn't be null");
		this.textField = textField;
		
	}
	
	protected WizardPage getParamPage(){
		return paramPage;
	}

	/**
	 * Reads the text field, checks it and updates the instance page 
	 * @param e the event which triggered the listener
	 */
	@Override
	public void modifyText(ModifyEvent e) {

			setField(textField.getText());


	}

	/**
	 * Gets the text field name
	 * @return the text field name
	 */
	public abstract String getFieldName();
	

	
	/**
	 * Sets the text field content
	 * @param field the text field content to set
	 */
	public abstract void setField(String field);

}
