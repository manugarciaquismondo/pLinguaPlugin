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

package org.gcn.plinguaplugin.builder;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectNature;
import org.eclipse.core.runtime.CoreException;

import org.gcn.plinguaplugin.PlinguaPlugin;
import org.gcn.plinguaplugin.builder.PlinguaFileAuditor;
import org.gcn.plinguaplugin.builder.PlinguaJob;


/**
 * This class represents P-system project nature.
 *  A project nature is compulsory to parse files by using developer-defined builders
 *  P-Lingua files are parsed into binary, simulated P-system files by using an specific P-Lingua parser
 *  (which, eventually, delegates most of its work on P-Lingua core)
 *  That's the reason why a P-system project nature (and a class to implement its specific functionality) is called for.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class PlinguaNature implements IProjectNature {

	/**
	 * ID of this project nature
	 */
	public static final String NATURE_ID = PlinguaPlugin.PLUGIN_ID + ".pLinguaNature";



	private IProject project;

	 /**
	 * Gets the project whose P-Lingua nature can be toggled
	 * @return the project whose P-Lingua nature can be toggled
	 */
	@Override
	public IProject getProject() {
	      return project;
	   }

	   /** 
	 * Sets the project whose P-Lingua nature will be toggled, it means, it will become a P-Lingua project or it will stop being so
	 */
	@Override
	public void setProject(IProject project) {
	       this.project = project;
	   }
	
	/**
	 * Adds P-Lingua nature to the project so that it becomes a P-Lingua nature project
	 */
	@Override
	   public void configure() throws CoreException {
	      PlinguaFileAuditor.addBuilderToProject(project);
	      (new PlinguaJob("P-Lingua File Audit", project)).schedule();
	   }
	
	/**
	 * Removes P-Lingua nature from the project
	 */
	@Override
	   public void deconfigure() throws CoreException {
	      PlinguaFileAuditor.removeBuilderFromProject(project);
	      PlinguaFileAuditor.deleteAuditMarkers(project);
	   }

}
