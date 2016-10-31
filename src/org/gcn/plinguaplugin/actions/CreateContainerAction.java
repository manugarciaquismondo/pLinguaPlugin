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
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowActionDelegate;

/**
 * This class provides common functionality for actions which create wizards based on a container selected by the user
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class CreateContainerAction implements IObjectActionDelegate, IWorkbenchWindowActionDelegate {

	
	private IStructuredSelection selection;
	private IWorkbenchPart targetPart;
	private IWorkbenchWindow window;
	
	/**
	 * @see org.eclipse.ui.IObjectActionDelegate#setActivePart(org.eclipse.jface.action.IAction, org.eclipse.ui.IWorkbenchPart)
	 */
	@Override
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		// TODO Auto-generated method stub
		this.targetPart = targetPart;

	}

	/**
	 * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
	 */
	@Override
	public void run(IAction action) {
		// TODO Auto-generated method stub
		/*If the selection is not suitable, return null*/
		if(selection==null) return;
		
		IResource resource = ActionSupporter.obtainResource(selection.getFirstElement());
		if(resource == null){
			ActionSupporter.reportError("There is no selected project or folder to perform the operation", new NullPointerException("There is no selected project or folder to perform the operation"));
			return;
		}
		/*If the resource is a file, is not valid, so report an error and return*/
		if(resource instanceof IFile){
			resource = resource.getParent();
			
			
		}
		
		IWizard wizard = getWizard();
		
		/*Get the package route*/
		String packageRoute = resource
		.getProjectRelativePath()
		.toString();		

		/*If the resource is a project, the package route is "src"*/
		if(resource instanceof IProject){
			packageRoute = "src";
			
		}
		/*Set the project route*/
		setProject(wizard, resource.getProject().getFullPath().toString(), false);
		/*Set the package route*/
		setPackage(wizard, packageRoute, false);
		/*Display the wizard*/
		WizardDialog wizardDialog = new WizardDialog(targetPart.getSite().getWorkbenchWindow().getShell(), wizard);
		wizardDialog.setPageSize(150, 300);
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
	
	/**This method reports errors related to wrong selected resources*/
	private void reportWrongResource() {
		/*Create the message box*/
		MessageBox errorBox = new MessageBox(new Shell(Display.getCurrent()), SWT.ERROR);
		/*Set the messages on the message box*/
		errorBox.setText("P-System simulator creation error");		
		errorBox.setMessage("P-System can't be created on files");
		/*Open the message box*/
		errorBox.open();
		
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

	
	protected abstract void setProject(IWizard wizard, String project, boolean change);
	
	protected abstract void setPackage(IWizard wizard, String filePackage, boolean change);

	protected abstract IWizard getWizard();
}
