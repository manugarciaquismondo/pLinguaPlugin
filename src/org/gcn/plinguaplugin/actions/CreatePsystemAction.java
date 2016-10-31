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


import org.eclipse.jface.wizard.IWizard;

import org.gcn.plinguaplugin.psystemWizard.PsystemWizard;

/**
 * This class displays a {@link PsystemWizard} instance when activated, providing support for the graphic interface button in charge to do so
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class CreatePsystemAction extends CreateContainerAction {








	
	@Override
	protected void setProject(IWizard wizard, String project, boolean change){
		((PsystemWizard)wizard).setProject(project, change);
		
	}
	
	@Override
	protected void setPackage(IWizard wizard, String filePackage, boolean change){
		((PsystemWizard)wizard).setPackage(filePackage, change);
		
	}
	
	@Override
	protected IWizard getWizard(){
		return new PsystemWizard();
	}


}
