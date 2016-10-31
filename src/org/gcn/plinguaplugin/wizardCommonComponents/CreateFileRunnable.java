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

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;


import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.widgets.Shell;

/**
 * This class creates a P-system file and reports its creation progress
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class CreateFileRunnable implements IRunnableWithProgress {

	private Shell shell;

	private String initialContent;

	/**
	 * Creates a new {@link CreateFileRunnable} instance, which will create a P-System file on the file route composed of its package and name
	 * @param initialContent the initial content of the P-System file to create
	 * @param container the package where the P-System file will be created
	 * @param name the name of the P-system file to create
	 * @param shell the shell where the monitor reporting the creation is shown
	 */
	public CreateFileRunnable(String initialContent, String container, String name, Shell shell) {
		super();
		this.container = container;
		this.name = name;
		this.shell = shell;
		this.initialContent = initialContent;
	
		
	}

	private String container;
	
	private String name;
	
	/**
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException {
		try {
			doFinish(monitor);
		} catch (CoreException e) {
			throw new InvocationTargetException(e);
		} finally {
			monitor.done();
		}
	}
	
	private void doFinish(IProgressMonitor monitor)
			throws CoreException {
		/*Delegate on CreateFile the file creation*/
		FileCreator.createFile(openContentStream(), name, container, shell, monitor);
		}
	
	/**
	 * We will initialize file contents with the given text.
	 */

	private InputStream openContentStream() {
		return new ByteArrayInputStream(initialContent.getBytes());
	}

}
