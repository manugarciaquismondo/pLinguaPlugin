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

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;
/**
 * This class translates a P-system among formats and reports its translation progress.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class RunnableOutputTranslator implements IRunnableWithProgress {

	private Psystem psystem;
	protected String destination;
	private String format;
	protected int units;
	/**
	 * Creates a new {@link RunnableOutputTranslator} instance, which will translate a P-system to a file indicated by its route
	 * @param psystem the file to translate to an output file
	 * @param destination the file route to parse the P-system to
	 * @param format the format of the external file 
	 */
	public RunnableOutputTranslator(Psystem psystem, String destination, String format) {
		super();
		if (destination == null)
			throw new NullPointerException("destination argument shouldn't be null");
		this.destination = destination;
		if (psystem == null)
			throw new NullPointerException("psystem argument shouldn't be null");
		this.psystem = psystem;
		if (format == null)
			throw new NullPointerException("format argument shouldn't be null");
		this.format = format;
		this.units = 1;
	}
	
	/**
	 * @see org.eclipse.jface.operation.IRunnableWithProgress#run(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		/*Begin the task*/
		monitor.beginTask("Creating " + destination, units);
		/*Parse the file*/
		PsystemController controller = new PsystemController();
		controller.parsePsystemToOutputFile(destination, psystem, format);	
		/*If errors ocurred, report them and return*/
		if(controller.errorsFound()){
			PlinguaLog.logError("Errors found while creating "+destination+"."+PsystemController.getExtension(format), controller.getLatestException());
			monitor.done();
			return;
		}
		/*Report the last working unit and finish*/
		monitor.worked(1);
		
		// TODO Auto-generated method stub

	}

}
