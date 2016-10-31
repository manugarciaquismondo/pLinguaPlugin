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

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;

/**
 * This class updates a {@link FormatTranslatorWizard} instance output format according to the format list selection
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class FormatIDListener implements ISelectionChangedListener {

	private FormatTranslatorWizardPage page;

	/**
	 * Creates a new {@link FormatIDListener} instance which updates the output format on a {@link FormatTranslatorWizard} instance 
	 * @param page the parameters page where the listener is
	 */
	public FormatIDListener(FormatTranslatorWizardPage page) {
		super();
		this.page = page;
	}

	/**
	 * Sets the output format according to the selection
	 * @param event the event which triggered the format change
	 */
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		/*Get the format name */
		String selection = event.getSelection().toString();
		/*Set the wizard format*/
		page.setFormat(selection.substring(1, selection.length()-1));
		// TODO Auto-generated method stub
		
	}



}
