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

package org.gcn.plinguaplugin.psystemWizard;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguaplugin.wizardCommonComponents.AbstractPackageListener;

/**
 * This class checks if the referenced package by the package text field exists and updates its {@link PsystemWizardParametersPage} instance wizard package field
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class PsystemPackageListener extends AbstractPackageListener {

	
	/**
	 * Creates a new {@link PsystemPackageListener} instance which contains the package text field given as parameter
	 * @param paramPage the page which the package text field belongs to 
	 * @param textField the package text field to listen for changes
	 */
	public PsystemPackageListener(WizardPage paramPage,
			Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}




	

	@Override
	protected String getProject(){
		/*Get the project where the package belongs to*/
		PsystemWizard wizard = ((PsystemWizard)getParamPage().getWizard());
		return wizard.getProject();
	}
	
	@Override
	protected void setPackage(String filePackage, boolean change){
		/*Set the package in its proper field*/
		((PsystemWizard)getParamPage().getWizard()).setPackage(filePackage, change);
		
	}

}
