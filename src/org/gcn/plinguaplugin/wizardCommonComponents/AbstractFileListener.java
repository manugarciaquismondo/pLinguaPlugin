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
import org.eclipse.swt.widgets.Text;


/**
 * This class checks if the text in the name text field matches the expected pattern and updates its {@link WizardPage} instance wizard file name
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class AbstractFileListener extends AbstractTextListener {
	
	private static final String FIRST_CHARACTER = "([A-Z]|_|\\-)";
	/**
	 * The regular expression for P-system file names
	 */
	public static final String REGEX = "([0-9]|[A-Z]|[a-z]|_|(\\-))";
	




	

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.AbstractTextListener#getFieldName()
	 */
	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return "Name";
	}

	/**
	 * Gets the regular expression the name field content should comply with
	 * @return the regular expression the name field content should comply with
	 */
	public String getRegex() {
		// TODO Auto-generated method stub
		return FIRST_CHARACTER+REGEX+"*";
	}

	/**
	 * Creates a new {@link PsystemNameListener} instance which contains the text field given as parameter
	 * @param paramPage the page which the text field belongs to 
	 * @param textField the name text field to listen for changes
	 */
	public AbstractFileListener(WizardPage paramPage, Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}


	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.AbstractTextListener#setField(java.lang.String)
	 */
	@Override
	public void setField(String field) {
		/*If the name field doesn't comply with the expression, update the field as null and report an error*/
		if(!field.matches(getRegex())){
			getParamPage().setErrorMessage("The "+getFieldName()+" field should be a non-empty string starting with a capital letter.");
			setFile(null);
		}

		/*Otherwise, update the name field as the text field content*/
		else{
			getParamPage().setErrorMessage(null);
			setFile(field);
		}

		// TODO Auto-generated method stub

	}
	
	protected abstract void setFile(String file);
	
	protected abstract String getProject();
	
	protected abstract String getPackage();


}
