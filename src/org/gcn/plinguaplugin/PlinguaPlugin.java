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

package org.gcn.plinguaplugin;

import java.io.File;
import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.osgi.service.datalocation.Location;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.osgi.service.prefs.Preferences;

import org.gcn.plinguaplugin.PlinguaPlugin;


/**
 * The activator class controls the plug-in life cycle
 */
public class PlinguaPlugin extends AbstractUIPlugin {

	private IEclipsePreferences configPrefs;

	/** The plug-in ID*/
	public static final String PLUGIN_ID = "org.gcn.plinguaplugin";

	// The shared instance
	private static PlinguaPlugin plugin;

	/**
	 * The constructor
	 */
	public PlinguaPlugin() {
		if (plugin != null)
			throw new IllegalStateException("Plugin already exists");
		plugin = this;
	}

	/**
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);

	}


	/**
	 *
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */

	@Override
	public void stop(BundleContext context) throws Exception {
		if (configPrefs != null) {
			configPrefs.flush();
			configPrefs = null;
		}
		/*FavoritesManager.getManager().saveFavorites();
		FavoritesManager.getManager().shutdown();
		BasicFavoriteItem.disposeColors();*/

		super.stop(context);
		plugin = null;
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static PlinguaPlugin getDefault() {
		return plugin;
	}

	/**
	 * Returns an image descriptor for the image file at the given plug-in
	 * relative path
	 * 
	 * @param path
	 *            the path
	 * @return the image descriptor
	 */
	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	/**
	 * Gets the route of the configuration file which contains P-Lingua plugin settings 
	 * @return the route of the configuration file which contains P-Lingua plugin settings
	 */
	public File getConfigDir() {
		Location location = Platform.getConfigurationLocation();
		if (location != null) {
			URL configURL = location.getURL();
			if (configURL != null && configURL.getProtocol().startsWith("file")) {
				return new File(configURL.getFile(), PLUGIN_ID);
			}
		}
		// If the configuration directory is read-only,
		// then return an alternate location
		// rather than null or throwing an Exception.
		return getStateLocation().toFile();
	}

	/**
	 * Gets the configuration preferences of P-Lingua plugin
	 * @return the configuration preferences of P-Lingua plugin
	 */
	public Preferences getConfigPrefs() {
		if (configPrefs == null)
			configPrefs = new ConfigurationScope().getNode(PLUGIN_ID);
		return configPrefs;
	}

}
