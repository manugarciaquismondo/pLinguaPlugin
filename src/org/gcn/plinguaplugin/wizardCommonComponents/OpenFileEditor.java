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

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.gcn.plinguaplugin.PlinguaLog;

/**
 * This class opens a P-System editor for a file
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class OpenFileEditor implements Runnable{
	
	
	private IFile file;

	/**
	 * Creates a new {@link OpenFileEditor} instance which opens a file on a page
	 * @param file the file to open
	 */
	public OpenFileEditor(IFile file) {
		super();
		this.file = file;
	}

	/**
	 * Opens the instance file on a new page
	 */
	@Override
	public void run() {
		/*Obtain the page where the file will be opened*/
		IWorkbenchPage page =
			PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
		/*Open the page*/
		try {
			IDE.openEditor(page, file, true);
		} catch (PartInitException e) {
			PlinguaLog.logError("Errors ocurred while opening an editor for the file "+file.getName(), e);
		}
	}

}
