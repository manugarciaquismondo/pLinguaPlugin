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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;

/**
 * A class for supporting actions by providing auxiliary functionality
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */

public abstract class ActionSupporter {
	/*A method for reporting errors*/
	/**
	 * Reports an error in an error box
	 * @param errorMsg the error description
	 * @param exception the exception which caused the error
	 */
	public static void reportError(String errorMsg, Exception exception){
		MessageBox errorBox = new MessageBox(new Shell(Display.getCurrent()), SWT.ERROR);
		errorBox.setText("P-system simulator creation error");
		errorBox.setMessage(errorMsg);
		PlinguaLog.logError(errorMsg, exception);
		errorBox.open();
	}
	/**
	 * Tests if a file contains a P-system without errors and returns the P-system
	 * @param file the file of the P-system to read
	 * @return the P-system within the file
	 */
	public static Psystem createPsystem(IFile file){
		PsystemController controller = new PsystemController();
		Psystem psystem=null;
		
		try{
			/*Parse the file P-system*/
			psystem = controller.parsePsystemFromInputFile(file.getFullPath().toString(), file.getContents());
		}
		/*If there were errors when accessing file content, report them*/
		catch(CoreException e){			
			reportError("Errors ocurred when accessing "+file.getFileExtension()+" content", e);			
			return null;
		}

		/*If there were errors when parsing file content, report them*/
		if(controller.errorsFound()){			
			//reportError(controller.getReportMessage(), controller.getLatestException());
			return null;
		}
		return psystem;
	}
	
	/**
	 * Obtains the resource represented by an object
	 * @param element the object which represents the resource 
	 * @return the resource obtained from the object
	 */
	public static IResource obtainResource(Object element){

		/*If the element is a resource, return it casted to resource*/
		if (element instanceof IResource) {
			return (IResource) element;
		/*Else, try to adapt it to a resource*/
		} else if (element instanceof IAdaptable) {
			return (IResource) ((IAdaptable) element)
					.getAdapter(IResource.class);
		}
		/*If the element is not a resource, return null*/
		return null;
	}
}
