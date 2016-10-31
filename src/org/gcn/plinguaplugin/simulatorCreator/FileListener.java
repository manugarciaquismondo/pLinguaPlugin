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

package org.gcn.plinguaplugin.simulatorCreator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguaplugin.formatConstants.SimulatorConstants;

/**
 * This class implements the behavior of the button which selects the file to save the wizard simulator on
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class FileListener implements SelectionListener {

	private SimulatorFeaturesWizardPage wizardPage;
	
	/**
	 * Creates a new {@link FileListener} instance to implement the behavior of the file selection button to select the simulator file
	 * @param wizardPage the wizard page where the file selection button is
	 */
	public FileListener(
			SimulatorFeaturesWizardPage wizardPage) {
		super();
		this.wizardPage = wizardPage;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * Opens a file dialog button to select the file to save the simulator from the wizard to
	 * @param e the event which triggers the file selection 
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		/*Create a file dialog to select the file to create the simulator to*/
		Shell fileDialogShell = new Shell(Display.getCurrent());
		FileDialog fileDialog = new FileDialog(fileDialogShell,SWT.SAVE);
		fileDialog.setFilterExtensions(new String[]{"*."+SimulatorConstants.SIM_EXTENSION});
		/*Open the file dialog and get the file route*/
		String fileRoute = fileDialog.open();
		wizardPage.setFileRoute(fileRoute);

		

	}

}
