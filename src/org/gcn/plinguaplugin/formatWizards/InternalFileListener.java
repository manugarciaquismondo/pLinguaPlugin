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

package org.gcn.plinguaplugin.formatWizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguaplugin.wizardCommonComponents.AbstractFileListener;

/**
 * Sets a {@link InputFormatWizard} instance internal file based on a text field
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class InternalFileListener extends AbstractFileListener {


	/**
	 * Creates a new {@link InternalFileListener} instance
	 * @param paramPage the page to set its external file name
	 * @param textField the text field which provides the file name
	 */
	public InternalFileListener(WizardPage paramPage, Text textField) {
		super(paramPage, textField);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setFile(String file) {
		((InputFormatWizard)(getParamPage().getWizard())).setWorkspaceFile(file, false);
		
	}
	
	@Override
	protected String getProject(){
		return ((InputFormatWizard)getParamPage().getWizard()).getProject();
	}
	
	@Override
	protected String getPackage(){
		return ((InputFormatWizard)getParamPage().getWizard()).getPackage();
	}



}
