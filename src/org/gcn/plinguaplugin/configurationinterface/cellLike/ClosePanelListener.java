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

import java.util.Stack;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Shell;

class ClosePanelListener implements DisposeListener {
	
	private Stack<Shell> closerShells;

	public ClosePanelListener(Stack<Shell> closerShells) {
		super();
		if (closerShells == null)
			throw new NullPointerException(
					"closerShells argument shouldn't be null");
		this.closerShells = closerShells;
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		// TODO Auto-generated method stub
		while(!closerShells.isEmpty()){
			(closerShells.pop()).dispose();
		}
	}

}
