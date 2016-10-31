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

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;


/**
 * This class implements the behavior of the button which selects the file to be the external wizard file
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class ExternalFileListener implements SelectionListener {

	private FormatTranslatorWizardPage page;
	

	/**
	 * Creates a new {@link ExternalFileListener} instance to implement the behavior of the file selection button to select the external file
	 * @param page the wizard page where the file selection button is
	 */
	public ExternalFileListener(FormatTranslatorWizardPage page) {
		super();
		this.page = page;
	}
	
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	/**
	 * Opens a file dialog button to select the file to select the wizard external file the page belongs to
	 * @param e the event which triggers the file selection 
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		/*Create a file dialog to select the external file*/
		Shell fileDialogShell = new Shell(Display.getCurrent());
		FileDialog fileDialog = new FileDialog(fileDialogShell,SWT.SAVE);
		/*Open the file dialog and get the file route*/
		String fileRoute = fileDialog.open();
		if(fileRoute==null)
			fileRoute="";
		page.setExternalFile(fileRoute);

	}

}
