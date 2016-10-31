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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;
/**
 * This class copies a source file to a destination one. The source file should be within the current workspace
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class RunnableOutputCopycat implements IRunnableWithProgress {

	private IFile source;
	private String destination;
	
	
	
	/**
	 * Creates a new {@link RunnableOutputCopycat} which will copy the source file to the file indicated by the destination route
	 * @param source the source file to copy
	 * @param destination the copy file route
	 */
	public RunnableOutputCopycat( IFile source, String destination) {
		super();
		this.destination = destination;
		this.source = source;
	}



	/**
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		/*Begin the monitor task*/
		monitor.beginTask("Coying P-system", 1);
		/*If the source file isn't correct, report an error and return*/
		if(source==null||!source.exists()){
			PlinguaLog.logError("The source file does not exist", new RuntimeException("The source file does not exist"));
			monitor.done();
			return;
		}
		OutputStream destinationFile = null;
		InputStream inputStream = null;
		/*Add the format file extension*/
		
		try {
			/*Obtain the destination file stream*/
			destinationFile = new FileOutputStream(destination);
		}		
		/*If the destination file can't be accessed, report an error and return*/
		catch (FileNotFoundException e) {
			PlinguaLog.logError("Errors ocurred while accessing "+destination+" file", e);
			monitor.done();
			return;
			
		}
		try {
			/*Obtain the source file stream*/
			inputStream = source.getContents();
		}
		/*If the source file can't be read, report an error and return*/
		catch (CoreException e) {
			PlinguaLog.logError("Errors ocurred while reading "+source.getFullPath().toString()+" file", e);
			monitor.done();
			return;

		}
		try{
			FileCreator.copyStreams(inputStream, destinationFile);
		}
		/*If errors occurred while copying the source file to the destination file, report an error and return*/
	    catch(IOException e){
	    	PlinguaLog.logError("Errors occurred while copying "+source.getFullPath().toString()+" file to "+destination+" file", e);
	    	monitor.done();
	    	return;
	    }
	    monitor.worked(1);
			


		// TODO Auto-generated method stub

	}

}
