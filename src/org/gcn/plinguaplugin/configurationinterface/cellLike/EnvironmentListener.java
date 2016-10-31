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

import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

/**
 * This class performs the functionality to be carried out when the configuration environment composite is selected by updating the cell-like membrane panel content
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class EnvironmentListener implements Listener {

	
	private CellLikeConfigurationPanel panel;

	
	/**
	 * @param panel the cell-like membrane panel where the configuration environment is displayed
	 */
	public EnvironmentListener(CellLikeConfigurationPanel panel) {
		super();
		if (panel == null)
			throw new NullPointerException("panel argument shouldn't be null");
		this.panel = panel;
	}
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		/*the cell-like membrane panel is updated by passing the environment as parameter*/
		panel.update(panel.getConfiguration().getEnvironment());
	}

}
