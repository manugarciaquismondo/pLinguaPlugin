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
 * This class provides functionality for selecting an existing package inside a wizard page and updating its proper text field
 * @author Manuel Garcia-Quismonso-Fernandez
 *
 */
public abstract class AbstractPackageListener extends ExistingResourceListener {

	/**
	 * Creates a new {@link AbstractPackageListener} instance to listen from a text field and set a wizard package string as the text from the field
	 * @param paramPage the page from the wizard to set its package string
	 * @param textField the text field which provides the package string
	 */
	public AbstractPackageListener(WizardPage paramPage, Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.ExistingResourceListener#getResourceRoute(java.lang.String)
	 */
	@Override
	public String getResourceRoute(String field) {
		// TODO Auto-generated method stub
		/*Get the wizard*/
		
		String resourceRoute = getProject()+"/"+field;
		/*Obtain the package route*/
		return resourceRoute.replace(".", "/");
		// TODO Auto-generated method stub
	}

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.ExistingResourceListener#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean change) {
		String valueSet = value;
		/*If the value is null, do not replace the syntax*/
		if(valueSet!=null)
			valueSet = valueSet.replace(".", "/");
		

		/*Set the package*/
		setPackage(valueSet, change);
		// TODO Auto-generated method stub

	}
	
	protected abstract String getProject();

	protected abstract void setPackage(String filePackage, boolean change);
	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.AbstractTextListener#getFieldName()
	 */
	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return "Package";
	}

}
