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
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.builder.PlinguaFileAuditor;
import org.gcn.plinguaplugin.builder.PlinguaResourceVisitor;
import org.gcn.plinguaplugin.controller.PsystemController;



class PlinguaWorkspaceAuditor implements IWorkspaceRunnable{

	private PlinguaFileAuditor fileAuditor;
	private IProject project;


	
	
	public PlinguaWorkspaceAuditor(PlinguaFileAuditor fileAuditor) {
		if (fileAuditor == null)
			throw new NullPointerException(
					"fileAuditor argument shouldn't be null");
		this.fileAuditor = fileAuditor;
	}



	public void run(IProgressMonitor monitor) throws CoreException {
		// TODO Auto-generated method stub
		auditPlinguaFile();
	}


	private void auditPlinguaFile() {

		   
		   /*First, it's needed to remove the previous markers.
		    * In case it's not possible, it's not possible to set new markers, as well, and the method ends*/
			project = fileAuditor.getProject();
		    if (!PlinguaFileAuditor.deleteAuditMarkers(project)) {
		        return;
		    }

			try {
				/*Add the builder visitor*/
				project.accept(new PlinguaResourceVisitor(new PsystemController()));
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				PlinguaLog.logError(e);
			}
		    
		
	}
	
	
	


	








	


}
