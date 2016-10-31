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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * This class updates a {@link PsystemWizard} instance model according to the model list selection
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class PsystemModelListener implements ISelectionChangedListener {

	private PsystemWizardParametersPage paramPage;

	/**
	 * Creates a new {@link PsystemModelListener} instance which updates the model on a {@link PsystemWizard} instance 
	 * @param paramPage the parameters page where the listener is
	 */
	public PsystemModelListener(PsystemWizardParametersPage paramPage){
		super();
		this.paramPage = paramPage;
	}
	
	/**
	 * Sets the wizard model according to the selection
	 * @param event the event which triggered the model change
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		/*Get the model name */
		String selection = event.getSelection().toString();
		/*Set the wizard model*/
		paramPage.setPsystemModel(selection.substring(1, selection.length()-1));

	}

}
