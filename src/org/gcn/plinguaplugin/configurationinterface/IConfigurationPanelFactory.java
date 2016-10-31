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

import org.gcn.plinguacore.util.psystem.Configuration;
import org.gcn.plinguacore.util.psystem.cellLike.CellLikeConfiguration;
import org.gcn.plinguaplugin.configurationinterface.cellLike.CellLikeConfigurationPanel;

/**
 * This class creates {@link IConfigurationPanel} instances which display {@link Configuration} instances given
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class IConfigurationPanelFactory {
	
	/**
	 * Creates an {@link IConfigurationPanel} instance which displays the {@link Configuration} instance given as argument
	 * @param configuration the configuration to be created a {@link IConfigurationPanel} for
	 * @return the {@link IConfigurationPanel} instance which displays the {@link Configuration} instance given as argument
	 */
	public static IConfigurationPanel createConfigurationPanel(Configuration configuration){
		if (configuration == null)
			throw new NullPointerException(
					"The configuration given as argument shouldn't be null");
		if(configuration instanceof CellLikeConfiguration)
			return new CellLikeConfigurationPanel(configuration);
		throw new IllegalArgumentException("The configuration given as argument can't be displayed by using any panel");
	}

}
