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

package org.gcn.plinguaplugin.formatWizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.wizardCommonComponents.OpenFileEditor;

/**
 * This class translates a P-system among formats and reports its translation progress. The P-system file should be within the current workspace, but the source one can be out of the workspace 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class RunnableInputTranslator extends RunnableOutputTranslator {

	
	
	private Shell shell;
	private String localRoute;
	/**
	 * Creates a new {@link RunnableOutputTranslator} instance, which will translate a P-system to a file indicated by its route
	 * @param psystem the file to translate to an output file
	 * @param workspaceRoute the workspace route of the source file
	 * @param destination the file route to parse the P-system to
	 * @param format the format of the external file
	 * @param shell the shell to report the file opening 
	 */
	public RunnableInputTranslator(Psystem psystem, String workspaceRoute, String destination,
			String format, Shell shell) {
		super(psystem, workspaceRoute+"/"+destination, format);
		this.units = 2;
		this.shell = shell;
		this.localRoute = destination;
		
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @see org.gcn.plinguaplugin.formatWizards.RunnableOutputTranslator#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
	InterruptedException {
		super.run(monitor);
		/*Create the resource to open an editor for*/
		try {
			ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			PlinguaLog.logError("The workspace couldn't be refreshed", e);
			return;
		}
		/*If the file does not exist, report an error*/
		IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(new Path(localRoute));
		if(file==null||!file.exists()){
			PlinguaLog.logError("The file "+destination+" couldn't be created", new RuntimeException("The file "+destination+" couldn't be created"));
			monitor.done();
			return;
		}
		/*Set the file as derived*/
		try {
			file.setDerived(true);
		/*If the file cannot be set as derived, report an error*/
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			PlinguaLog.logError("The file "+destination+" couldn't be set as derived", new RuntimeException("The file "+destination+" couldn't be as derived"));
			monitor.done();
			return;
		}
		/*Open the file on an editor*/
		 shell.getDisplay().asyncExec(new OpenFileEditor(file));
		monitor.worked(1);
	}


}
