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

package org.gcn.plinguaplugin.actions;


import java.util.Iterator;


import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.builder.PlinguaNature;

/**
 * This class performs the action functionality for toggling P-system nature on a project
 * It's needed as only P-Lingua nature project can be automatically parsed by P-Lingua files parser 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class ToggleNatureAction implements IObjectActionDelegate, IWorkbenchWindowActionDelegate {

	private ISelection selection;
	private IWorkbenchWindow window;
	
	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
		
		
	}
	  /**
	 * Toggles a project P-Lingua nature
	 * @param action The action which invokes the P-Lingua nature project change
	 */
	public void run(IAction action) {
			if (selection instanceof IStructuredSelection) {

				for (Iterator<?> it = ((IStructuredSelection) selection).iterator(); it
						.hasNext();) {
					Object element = it.next();
					IResource resource = ActionSupporter.obtainResource(element);
					IProject project = null;
					if(resource !=null){
						if(resource instanceof IProject)
							project = (IProject)resource;
						else
							project = resource.getProject();
					}
						
					
					if (project != null) {

						toggleNature(project);
					}
				}
			}
			/*Refresh the project to update the nature images*/
			try {
				ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				PlinguaLog.logError("The workspace couldn't be refreshed", e);
				return;
			}
		}

		/**
		 * 
		 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
		 *      org.eclipse.jface.viewers.ISelection)
		 */
		public void selectionChanged(IAction action, ISelection selection) {
			this.selection = selection;
		}

		/**
		 * 
		 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction,
		 *      org.eclipse.ui.IWorkbenchPart)
		 */
		public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		}

		/**
		 * Toggles P-Lingua nature on a project
		 * 
		 * @param project
		 *            to have P-Lingua nature added or removed
		 */
		private void toggleNature(IProject project) {

			try {
				IProjectDescription description = project.getDescription();
				String[] natures = description.getNatureIds();

				for (int i = 0; i < natures.length; ++i) {
					
					if (PlinguaNature.NATURE_ID.equals(natures[i])) {

						// Remove the nature
						String[] newNatures = new String[natures.length - 1];
						System.arraycopy(natures, 0, newNatures, 0, i);
						System.arraycopy(natures, i + 1, newNatures, i,
								natures.length - i - 1);
						description.setNatureIds(newNatures);
						project.setDescription(description, null);
						return;
					}
				}
				// Add the nature
				String[] newNatures = new String[natures.length + 1];
				System.arraycopy(natures, 0, newNatures, 0, natures.length);
				newNatures[natures.length] = PlinguaNature.NATURE_ID;
				description.setNatureIds(newNatures);
				project.setDescription(description, null);
			

			} catch (CoreException e) {
			}
		}



}
