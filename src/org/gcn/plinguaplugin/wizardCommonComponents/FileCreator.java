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

package org.gcn.plinguaplugin.wizardCommonComponents;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguaplugin.PlinguaLog;

/**
 * This abstract class creates files and reports errors on shells in case they occurred. It's intended to provide support for wizard actions, as many of them create new files when they finish
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class FileCreator {
	
	/** Creates a new file on the container and file specified with the workspace
	 * @param content the content of the file to create
	 * @param fileName the name of the file to create
	 * @param containerName the name of the container where create the file in
	 * @param shell the shell to show if errors occur
	 * @param monitor the monitor which show the file creatin progress
	 * @throws CoreException if the file couldn't be created
	 */
	public static void createFile(InputStream content, String fileName, String containerName, Shell shell, IProgressMonitor monitor) throws CoreException{
		/*Begin the file creation*/
		monitor.beginTask("Creating " + fileName, 2);
		/*Get the container*/
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IResource resource = root.findMember(new Path(containerName));
		/*If there's no container, report an error*/
		if (resource==null||!resource.exists() || !(resource instanceof IContainer)) {
			PlinguaLog.logError(new RuntimeException("Container \"" + containerName + "\" does not exist."));
			monitor.done();
			return;
		}
		/*Get the P-system file*/
		IContainer container = (IContainer) resource;
		final IFile file = container.getFile(new Path(fileName));
		
		try {
			/*If the P-system file already existed, set it contents*/
			if (file.exists()) {
				file.setContents(content, true, true, monitor);
				/*Otherwise, create the P-system file*/
			} else {
				file.create(content, true, monitor);
			}
			content.close();
		/*If there were errors, report them*/
		} catch (IOException e) {
			PlinguaLog.logError("Errors ocurred while creating the file "+containerName+"/"+fileName, e);
			monitor.done();
			return;
		}
		
		monitor.worked(1);
		/*Open the file on an editor*/

		monitor.setTaskName("Opening file for editing...");
		
		shell.getDisplay().asyncExec(new OpenFileEditor(file));
		monitor.worked(1);
		
		
	}
	
	/**
	 * Executes an action on the container provided by a wizard
	 * @param wizard the wizard which executed the action
	 * @param operation the operation which executes the action
	 * @param shell the shell where the error messages are displayed
	 * @return true if the file has been successfully created, false otherwise
	 */
	public static boolean executeContainerAction(IWizard wizard, IRunnableWithProgress operation, Shell shell){
		/*Execute the specific operation*/
		try {
			wizard.getContainer().run(false, false, operation);
			
		} 
		/*If the operation was interrupted, stop*/
		catch (InterruptedException e) {			
			return false;
			
		}
		/*If there were errors, report them*/
		catch (InvocationTargetException e) {
			//MessageDialog.openError(shell, "Error", e.getMessage());
			return true;
		}
		return true;
	}
	
	/**
	 * Copies an input stream to an output stream and closes them
	 * @param inputStream the input stream to copy
	 * @param outputStream the destination output stream to write the input stream content
	 * @throws IOException if errors ocurred during the stream copy
	 */
	public static void copyStreams(InputStream inputStream, OutputStream outputStream) throws IOException{
		/*Use an auxiliary buffer to copy the files*/
	    byte[] buf = new byte[1024];
	    int len;
		/*Copy the files*/
		while ((len = inputStream.read(buf)) > 0){
			outputStream.write(buf, 0, len);
      }
		/*Close the streams*/
		inputStream.close();
		outputStream.close();

		
	}
}
