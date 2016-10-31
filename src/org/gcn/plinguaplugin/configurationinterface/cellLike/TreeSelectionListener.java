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

package org.gcn.plinguaplugin.configurationinterface.cellLike;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TreeItem;

/**
 * This class updates the cell-like membrane panel when an item of its configuration tree is selected
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class TreeSelectionListener implements SelectionListener {

	private CellLikeConfigurationPanel panel;
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		/*The item selected (a tree item) is retrieved*/
		TreeItem itemSelected = (TreeItem)e.item;
		/*If it's not null, the cell-like membrane panel is updated*/
		if(itemSelected!=null){
			Object data = itemSelected.getData();
			panel.update(data);
				
		}
		
	}

	/**
	 * Creates a new TreeSelectionListener to update the panel content when an item of its configuration tree is selected
	 * @param panel the panel to update
	 */
	public TreeSelectionListener(CellLikeConfigurationPanel panel) {
		super();
		if (panel == null)
			throw new NullPointerException("panel argument shouldn't be null");
		this.panel = panel;
	}

}
