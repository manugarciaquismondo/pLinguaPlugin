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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;

/**
 * This class updates the option of enabling and disabling the inclusion of a main method in a {@link PsystemWizard} instance P-system file  
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class HasMainListener implements SelectionListener {

	private PsystemWizardParametersPage paramPage;
	
	private Button checkBox;
	
	/**
	 * Creates a new {@link PsystemWizardParametersPage} instance which updates the main method inclusion based on a check box selection
	 * @param paramPage the {@link PsystemWizardParametersPage} instance where the check box is
	 * @param checkBox the check box which defines the main method enablement
	 */
	public HasMainListener(PsystemWizardParametersPage paramPage, Button checkBox) {
		super();
		this.paramPage = paramPage;
		this.checkBox = checkBox;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		/*Set the "has main" option according to the check box value*/
		paramPage.setHasMain(checkBox.getSelection());

	}

}
