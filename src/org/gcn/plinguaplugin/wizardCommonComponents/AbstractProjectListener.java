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
 * This class checks if the project referenced by the project text field exists and updates its {@link WizardPage} instance wizard project field
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class AbstractProjectListener extends ExistingResourceListener {

	/**
	 * Creates a new {@link AbstractProjectListener} instance which contains the project text field given as parameter
	 * @param paramPage the page which the project text field belongs to 
	 * @param textField the project text field to listen for changes
	 */
	public AbstractProjectListener(WizardPage paramPage, Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.ExistingResourceListener#getResourceRoute(java.lang.String)
	 */
	@Override
	public String getResourceRoute(String field) {
		// TODO Auto-generated method stub
		return field;
	}

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.ExistingResourceListener#setValue(java.lang.String, boolean)
	 */
	@Override
	public void setValue(String value, boolean change) {
		String valueSet = value;
		/*Set the project*/
		setProject(valueSet, change);		
		// TODO Auto-generated method stub

	}
	
	protected abstract void setProject(String project, boolean change);

	/**
	 * @see org.gcn.plinguaplugin.wizardCommonComponents.AbstractTextListener#getFieldName()
	 */
	@Override
	public String getFieldName() {
		// TODO Auto-generated method stub
		return "Project";
	}

}
