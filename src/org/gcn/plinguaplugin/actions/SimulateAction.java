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

package org.gcn.plinguaplugin.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.gcn.plinguaplugin.simulator.SimulatorDisplayer;

/**
 * This class performs the creation and displaying of an {@link SimulatorDisplayer} instance when the extension referring this class is activated
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class SimulateAction implements IWorkbenchWindowActionDelegate {

	private IWorkbenchWindow window;

	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		//Cache the workbench window
		this.window = window;
		// TODO Auto-generated method stub

	}

	/**  Creates and displays a new {@link SimulatorDisplayer} instance for simulations
	 * @param action the action which triggers the displayer opening
	 */
	@Override
	public void run(IAction action) {
		
		//Get the SimulatorDisplayer instance
		SimulatorDisplayer displayer = new SimulatorDisplayer();
		//Open the displayer
		displayer.display();
		// TODO Auto-generated method stub

	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		// TODO Auto-generated method stub

	}

}
