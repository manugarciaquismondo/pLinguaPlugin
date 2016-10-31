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



import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Composite;

/**
 * This class provides functionality for mouse-related events which take place on a membrane composite by updating the cell-like membrane panel content
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class MouseSubmitter implements MouseListener {

	CellLikeConfigurationPanel panel;
	protected Composite membraneComposite;
	
	/**
	 * Creates a new MouseSubmitter instance to handle mouse events on the membrane composite passed as argument. The events occurred update the panel displayed information 
	 * @param panel the panel which will be updated when the mouse events take place
	 * @param membraneComposite the membrane composite where the mouse events take place
	 */
	public MouseSubmitter(CellLikeConfigurationPanel panel, Composite membraneComposite) {
		super();
		if (panel == null)
			throw new NullPointerException("panel argument shouldn't be null");
		this.panel = panel;
		if (membraneComposite == null)
			throw new NullPointerException(
					"membraneComposite argument shouldn't be null");
		this.membraneComposite = membraneComposite;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDown(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseDown(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseUp(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseUp(MouseEvent e) {
		/*The panel displayed info is updated*/
		panel.update(membraneComposite.getData("membrane"));


		// TODO Auto-generated method stub

	}

}
