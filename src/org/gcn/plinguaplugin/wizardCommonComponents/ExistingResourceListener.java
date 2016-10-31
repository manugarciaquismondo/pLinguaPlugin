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
import org.gcn.plinguaplugin.psystemWizard.PsystemWizard;
/**
 * This class checks if the text in the specific resource text field exists and updates its {@link PsystemWizard} instance wizard speficic field
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class ExistingResourceListener extends AbstractTextListener {

	/**
	 * Creates a new {@link ExistingResourceListener} instance which contains the text field given as parameter
	 * @param paramPage the page which the text field belongs to 
	 * @param textField the resource text field to listen for changes
	 */
	public ExistingResourceListener(WizardPage paramPage,
			Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}

	
	
	



	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.AbstractTextListener#setField(java.lang.String)
	 */
	@Override
	public void setField(String field) {
		/*If the field is null, set it on the wizard and stop*/
		if(field==null){
			setValue(null, false);
			return;
		}

		String resourceRoute = getResourceRoute(field);
		/*Test if the package already existed. Otherwise, report an error*/
		if (!ResourceExistanceTester.testContainer(resourceRoute)) {
			getParamPage().setErrorMessage("Package \"" + resourceRoute + "\" does not exist.");
			/*Set the wizard package as null*/
			setValue(null, false);
			return;
		}

		// TODO Auto-generated method stub

		setValue(field, false);
		getParamPage().setErrorMessage(null);

	}
	
	/**
	 * Gets the resource route for the specific class
	 * @param field the base resource text
	 * @return the resource route for the specific class
	 */
	public abstract String getResourceRoute(String field);
	
	
	/**
	 * Sets the resource field value for the specific class
	 * @param value the resource field value for the specific class
	 * @param change a boolean representing if the resource field should be updated on the parameters page
	 */
	public abstract void setValue(String value, boolean change);

}
