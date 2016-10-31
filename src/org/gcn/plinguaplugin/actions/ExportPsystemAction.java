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
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.formatWizards.OutputFormatWizard;
/**
 * This class performs the creation and displaying of a {@link OutputFormatWizard} wizard when the extension referring this class is activated
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class ExportPsystemAction implements IObjectActionDelegate, IWorkbenchWindowActionDelegate {

	private IStructuredSelection selection;
	private IWorkbenchPart targetPart;
	private IWorkbenchWindow window;
	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;

	}
	/**
	 * Creates and displays a new {@link OutputFormatWizard} instance for exporting P-systems from a file within the workspace to another
	 * @param action the action which triggers the wizard opening
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		/*If the selection is not suitable, return null*/
		if(selection==null) return;
		/*Get the selection*/
		IResource selectedObject = ActionSupporter.obtainResource(selection.getFirstElement());
		/*If the selected object is not a file, report an error and quit*/
		if(! (selectedObject instanceof IFile)){
			ActionSupporter.reportError("No file to export selected", new RuntimeException("No file to simulate selected"));
			return;
		}
		IFile file = (IFile)selection.getFirstElement();
		
		Psystem psystem = ActionSupporter.createPsystem(file);
		
		/*Create the wizard*/
		OutputFormatWizard outputWizard = new OutputFormatWizard(psystem, file.getFullPath().toString());
		/*Display the wizard*/
		WizardDialog wizardDialog = new WizardDialog(targetPart.getSite().getWorkbenchWindow().getShell(), outputWizard);
		wizardDialog.setBlockOnOpen(true);
		wizardDialog.open();

	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction, org.eclipse.jface.viewers.ISelection)
	 */
	@Override
	public void selectionChanged(IAction action, ISelection selection) {
		if(selection instanceof IStructuredSelection){
			this.selection = ((IStructuredSelection)selection);
		}
		else
			this.selection = null;

	}
	
	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
	 */
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
	 */
	@Override
	public void init(IWorkbenchWindow window) {
		this.window = window;
		targetPart = window.getPartService().getActivePart();
		
	}



}
