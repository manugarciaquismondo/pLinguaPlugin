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

package org.gcn.plinguaplugin.configurationinterface;

import org.eclipse.swt.graphics.Point;

/**
 * This interface defines the functionality for all configuration panels, without depending on its group
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public interface IConfigurationPanel {
	/**
	 * Displays the configuration the CellLikeMembranePanel object is constructed with
	 */
	public void display();
	/**
	 * Disposes the current panel
	 */
	public void dispose();
	/**
	 * Gets if the panel is currently displayed
	 * @return true if the panel is currently displayed, false otherwise
	 */
	public boolean isDisplayed();
	/**
	 * Gets the panel current position
	 * @return the panel current position
	 */
	public Point getPosition();
	
	/**
	 * Sets the panel current position
	 * @param x the panel horizontal position component
	 * @param y the panel vertical position component
	 */
	public void setPosition(int x, int y);

}
