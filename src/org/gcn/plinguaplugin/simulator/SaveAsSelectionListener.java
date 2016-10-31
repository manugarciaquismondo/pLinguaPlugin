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

package org.gcn.plinguaplugin.simulator;

import org.gcn.plinguaplugin.formatConstants.SimulatorConstants;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

/**
 * This class performs Save As actions to encode simulators on their files
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class SaveAsSelectionListener extends SaveSelectionListener{

	private Shell saveAsShell;
	
	/**
	 * Creates a new SaveAsSelectionListener instance
	 * @param simulatorDisplayer the simulator displayer which holds the instance
	 */
	public SaveAsSelectionListener(SimulatorDisplayer simulatorDisplayer) {
		super(simulatorDisplayer);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void executeSpecificAction(){
		/*Create a file dialog*/
		saveAsShell = new Shell(Display.getCurrent());
		FileDialog fileDialog = new FileDialog(saveAsShell, SWT.SAVE);
		fileDialog.setFilterExtensions(new String[]{"*."+SimulatorConstants.SIM_EXTENSION, "*.*"});
		/*Obtain a file route*/
		String fileRoute = fileDialog.open();
		/*If the route is null, the save as process is cancelled*/
		if(fileRoute==null) return;
		/*Save the simulator*/
		getSimulatorSaver().saveSimulator(getSimulatorDisplayer().getCurrentSimulator(), fileRoute);
	}




}
