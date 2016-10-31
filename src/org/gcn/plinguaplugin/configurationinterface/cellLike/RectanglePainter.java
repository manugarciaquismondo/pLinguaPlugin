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


import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;

/**
 * This class paints a round-cornered rectangle on a canvas to represent its membrane boundaries
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class RectanglePainter implements PaintListener {

	private Canvas canvas;
	private GC gc;
	
	/**
	 * Creates a new RectangePainter instance which draws a round-cornered rectangle on the canvas passed as argument, representing the canvas membrane boundaries
	 * @param canvas the canvas to be drawn
	 */
	public RectanglePainter(Canvas canvas) {
		super();
		if (canvas == null)
			throw new NullPointerException("canvas argument shouldn't be null");
		this.canvas = canvas;
	}
	
	/**
	 * Gets the GC of the canvas drawn
	 * @return the GC of the canvas drawn
	 */
	public GC getGc() {
		return gc;
	}



	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.PaintListener#paintControl(org.eclipse.swt.events.PaintEvent)
	 */
	@Override
	public void paintControl(PaintEvent e) {
		/*First, we get the canvas bounds*/
        Rectangle clientArea = canvas.getBounds();
        gc = e.gc;
        /*Then, on its GC we paint a round-cornered rectangle*/
        gc.drawRoundRectangle(2, 2, clientArea.width-5, clientArea.height-5, 20, 20);
	}
	




}
