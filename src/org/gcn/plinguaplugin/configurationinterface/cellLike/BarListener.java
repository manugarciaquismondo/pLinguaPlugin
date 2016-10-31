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

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;

/**
 * This class performs all functionality which should be carried out when a scroll bar pointer is moved
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class BarListener implements Listener {

	Composite scrolled;
	ScrollBar bar;
	boolean vertical;
	
	/**
	 * Creates a new BarListener
	 * @param bar the scroll bar which should implement the functionality according to its relative position 
	 * @param scrolled the composite which is scrolled by shifting the scroll bar pointer0
	 * @param vertical a boolean indicating if the scroll bar is horizontal (false) or vertical (true)
	 */
	public BarListener(ScrollBar bar, Composite scrolled, boolean vertical) {
		super();
		this.bar = bar;
		this.scrolled = scrolled;
		this.vertical = vertical;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.swt.widgets.Listener#handleEvent(org.eclipse.swt.widgets.Event)
	 */
	@Override
	public void handleEvent(Event event) {
		// TODO Auto-generated method stub
		Point location = scrolled.getLocation();
		/*The dimension of the scrolled composite should be updated so that it takes care of the scroll bar size*/
		setDimension(location);
		scrolled.setLocation(location);
			
	}
	
	private void setDimension(Point location){
		if(vertical)
			location.y = -bar.getSelection();
		else
			location.x = -bar.getSelection();
	}
		// TODO Auto-generated method stub


}
