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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.gcn.plinguaplugin.formatConstants.PlinguaConstants;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceDelta;
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;

import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.PlinguaPlugin;
import org.gcn.plinguaplugin.builder.PlinguaFileAuditor;
import org.gcn.plinguaplugin.builder.PlinguaWorkspaceAuditor;


/**
 * 
 * This class builds P-Lingua files and, for each one which has no errors, generates a binary P-system files which will be read by simulators to simulate the P-system
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class PlinguaFileAuditor extends IncrementalProjectBuilder {
	/**
	 * The specific marker for P-Lingua files
	 */
	public static final String MARKER_ID =
		   PlinguaPlugin.PLUGIN_ID + ".plinguamarker";
	private Set<IResource> filesToCompile;
	/**
	 * The Plingua Plugin builder ID
	 */
	public static final String BUILDER_ID = PlinguaPlugin.PLUGIN_ID + ".plinguaFileAuditor";
	
	/**
	 * The marker ID for P-Lingua files built by PlinguaResourceVisitor builder
	 */
	
	public static final String PLINGUA_MARKER = PlinguaPlugin.PLUGIN_ID+".plinguamarker";
	
	
	@Override
	protected IProject[] build(int kind, Map args, IProgressMonitor monitor)
			throws CoreException {
		// TODO Auto-generated method stub
		/*In case the selected project needs to be audited*/

		 if (shouldAudit(kind)) {

			 /*The workspace will execute the builder on its P-Lingua files*/

		  		
		      ResourcesPlugin.getWorkspace().run(
		         new PlinguaWorkspaceAuditor(this),
		         monitor
		      );
		   }
		   return null;
	}
	
	private boolean shouldAudit(int kind) {
		filesToCompile = new HashSet<IResource>();
		/*If the flag states that the file should be completely built, we return true*/
		   if (kind == FULL_BUILD||kind == CLEAN_BUILD||kind == AUTO_BUILD){
		      return true;
		   }
		   /*Otherwise, we look for changes (deltas) in our project*/
		   IResourceDelta delta = getDelta(getProject());
		   /*If there are no changes, return false*/
		   if (delta == null)
		      return false;
		   /*Otherwise, we browse each affected resource*/
		   IResourceDelta[] children = delta.getAffectedChildren();
		  // boolean toCompile = false;
		   for (int i = 0; i < children.length; i++) {
		      IResourceDelta child = children[i];
		      /*In case any P-Lingua file has been modified, rebuild this file and report that the project should be rebuilt*/

		      String fileExtension = child.getProjectRelativePath().getFileExtension();
		      if (/*fileName.equals("plugin.xml")
		         ||*/ fileExtension.equals(PlinguaConstants.PLINGUA_EXTENSION)){
		    	/* filesToCompile.add(child.getResource());
		    	 *
		         toCompile = true;*/

		    	  return true;
		      }
		   }
		   /*If none of the changed resources are configuration file or P-Lingua file, return false*/
		  // return toCompile;
		   return false;
		}

	/**
	 * Gets a set contains all files which need to be compiled
	 * @return a set which contains all files which need to be compiled
	 */
	public Iterator<IResource> getFilesToCompile() {
		return filesToCompile.iterator();
	}


	/**
	 * Adds {@link PlinguaFileAuditor} builder to the project given as parameter.
	 * This will allow {@link PlinguaFileAuditor} builder to build P-Lingua files belonging to that project.
	 * This action takes place when the project nature is switched to P-system nature. 
	 * @param project the project to be added to the builder
	 */
	public static void addBuilderToProject(IProject project) {
		   // Cannot modify closed projects.
		   if (!project.isOpen())
		      return;

		   // Get the description.
		   IProjectDescription description;
		   try {
		      description = project.getDescription();
		   }
		   catch (CoreException e) {
		      PlinguaLog.logError(e);
		      return;
		   }

		   // Look for builder already associated.
		   ICommand[] cmds = description.getBuildSpec();
		   for (int j = 0; j < cmds.length; j++)
		      if (cmds[j].getBuilderName().equals(BUILDER_ID))
		         return;

		   // Associate builder with project.
		   ICommand newCmd = description.newCommand();
		   newCmd.setBuilderName(BUILDER_ID);
		   List<ICommand> newCmds = new ArrayList<ICommand>();
		   newCmds.addAll(Arrays.asList(cmds));
		   newCmds.add(newCmd);
		   description.setBuildSpec(
		      newCmds.toArray(
		         new ICommand[newCmds.size()]));
		   try {
		      project.setDescription(description, null);
		   }
		   catch (CoreException e) {
			   PlinguaLog.logError(e);
		   }
		
	}

	/**
	 * Deletes all {@link PlinguaFileAuditor} markers on the given project.
	 * This action usually takes place when the project nature is no longer P-system nature, and so markers are confusing as well as not necessary. 
	 * @param project the project whose {@link PlinguaFileAuditor} markers will be deleted
	 * @return true if {@link PlinguaFileAuditor} markers have been successfully removed, false otherwise
	 */
	public static boolean deleteAuditMarkers(IProject project) {
		try {
			/* Deleting markers is delegated on the project.
			 * Specification for P-Lingua builder markers is called for.*/
			project.deleteMarkers(PLINGUA_MARKER, false, IResource.DEPTH_INFINITE);

			return true;
		/*If errors ocurred, report them*/
		} catch (CoreException e) {
			PlinguaLog.logError(e);
			return false;
		}
	}

	/**
	 * Removes {@link PlinguaFileAuditor} builder from the given project.
	 * This action usually takes place when the project nature is no longer P-system nature, and so it doesn't need to be built by {@link PlinguaFileAuditor}. 
	 * @param project the project which won't be built by {@link PlinguaFileAuditor} anymore, until its nature becomes P-Lingua nature back.
	 */
	public static void removeBuilderFromProject(IProject project) {

		// Cannot modify closed projects.
		if (!project.isOpen())
			return;

		// Get the description.
		IProjectDescription description;
		try {
			description = project.getDescription();
		} catch (CoreException e) {
			//PsystemLog.logError(e);
			return;
		}

		// Look for builder.
		int index = -1;
		ICommand[] cmds = description.getBuildSpec();
		for (int j = 0; j < cmds.length; j++) {
			if (cmds[j].getBuilderName().equals(BUILDER_ID)) {
				index = j;
				break;
			}
		}
		if (index == -1)
			return;

		// Remove builder from project.
		List newCmds = new ArrayList();
		newCmds.addAll(Arrays.asList(cmds));
		newCmds.remove(index);
		description.setBuildSpec((ICommand[]) newCmds
				.toArray(new ICommand[newCmds.size()]));
		try {
			project.setDescription(description, null);
		} catch (CoreException e) {
			//PsystemLog.logError(e);
		}
	}


	

}
