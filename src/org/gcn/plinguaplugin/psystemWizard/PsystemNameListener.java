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
import org.gcn.plinguaplugin.wizardCommonComponents.AbstractFileListener;

/**
 * This class checks if the text in the name text field matches the expected pattern and updates its {@link PsystemWizardParametersPage} instance wizard file name
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class PsystemNameListener extends AbstractFileListener {

	
	
	/**
	 * Creates a new {@link PsystemNameListener} instance which contains the text field given as parameter
	 * @param paramPage the page which the text field belongs to 
	 * @param textField the name text field to listen for changes
	 */
	public PsystemNameListener(WizardPage paramPage,
			Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}


	
	@Override
	protected void setFile(String file){
		((PsystemWizardParametersPage)getParamPage()).setPsystemName(file);
	}

	@Override
	protected String getProject(){
		return ((PsystemWizard)getParamPage().getWizard()).getProject();
	}
	
	@Override
	protected String getPackage(){
		return ((PsystemWizard)getParamPage().getWizard()).getPackage();
	}


}
