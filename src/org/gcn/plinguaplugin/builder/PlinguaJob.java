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
import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.builder.PlinguaFileAuditor;
/**
 * This class adds {@link PlinguaFileAuditor} builder to projects so its files can be built by {@link PlinguaFileAuditor}   
 * @author Manuel Garcia-Quismondo-Fernandez
 */
class PlinguaJob extends Job {
	

	private IProject project;
	/**
	 * The constructor
	 * @param name the name of the job
	 * @param project the project to be built by 
	 */
	public PlinguaJob(String name, IProject project) {
		super(name);
		if (project == null)
			throw new NullPointerException("project argument shouldn't be null");
		this.project = project;
		// TODO Auto-generated constructor stub
	}


	/*Build the project by using the P-Lingua builder*/
    @Override
	protected IStatus run(IProgressMonitor monitor) {
        try {
            project.build(
               IncrementalProjectBuilder.FULL_BUILD,
               PlinguaFileAuditor.BUILDER_ID,
               null,
               monitor);
        }
        catch (CoreException e) {
            PlinguaLog.logError(e);
        }
        return Status.OK_STATUS;
     }

}
