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

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;
import org.gcn.plinguaplugin.wizardCommonComponents.OpenFileEditor;

/**
 * This class copies a source file to a destination one. The destination file should be within the current workspace
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class RunnableInputCopycat implements IRunnableWithProgress {

	private String source;
	private String destination;
	private Shell shell;
	private String localRoute;
	
	
	/**
	 * Creates a new {@link RunnableOutputCopycat} which will copy the source file to the file indicated by the destination route
	 * @param source the source file to copy
	 * @param localRoute the destination file route excluding the workspace route
	 * @param destination the complete file route to parse the file to
	 * @param shell the shell to report errors to
	 */
	public RunnableInputCopycat(String source, String localRoute, String destination, Shell shell){
		if (destination == null)
			
			throw new NullPointerException(
					"Destination argument shouldn't be null");
		this.localRoute = localRoute;
		this.destination = destination;
		if (source == null)
			throw new NullPointerException(
					"Source argument shouldn't be null");
		this.source = source;
		if (shell == null)
			throw new NullPointerException(
					"shell argument shouldn't be null");
		this.shell = shell;
	}
	
	/**
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) {
		monitor.beginTask("Coying P-system", 1);
		/*If the source file isn't correct, report an error and return*/
		OutputStream destinationFile = null;
		InputStream inputStream = null;
		/*Add the format file extension*/
		
		try {
			/*Obtain the destination file stream*/
			destinationFile = new FileOutputStream(destination);
		}		
		/*If the destination file can't be accessed, report an error and return*/
		catch (FileNotFoundException e) {
			PlinguaLog.logError("Errors ocurred while accessing "+destination+" file for writing", e);
			monitor.done();
			return;
			
		}
		try {
			/*Obtain the source file stream*/
			inputStream = new FileInputStream(source);
		}
		/*If the source file can't be read, report an error and return*/
		catch (IOException e) {
			PlinguaLog.logError("Errors ocurred while reading "+source+" file", e);
			monitor.done();
			return;

		}
		try{
			FileCreator.copyStreams(inputStream, destinationFile);
		}
		/*If errors occurred while copying the source file to the destination file, report an error and return*/
	    catch(IOException e){
	    	PlinguaLog.logError("Errors occurred while copying "+source+" file to "+destination+" file", e);
	    	monitor.done();
	    	return;
	    }
	    monitor.worked(1);
	    /*Open the file on an editor*/
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		try {
			root.refreshLocal(IResource.DEPTH_INFINITE, null);
		} catch (CoreException e) {
			// TODO Auto-generated catch block
			PlinguaLog.logError("The workspace couldn't be refreshed", e);
			return;
		}
		IResource file = root.findMember(new Path(localRoute));
		if(file==null||!file.exists()){
			PlinguaLog.logError("The file "+localRoute+" couldn't be created", new RuntimeException("The file "+destination+" couldn't be created"));
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
	    shell.getDisplay().asyncExec(new OpenFileEditor((IFile)file));
	    /*End the work*/
	    monitor.worked(1);

	}

}

