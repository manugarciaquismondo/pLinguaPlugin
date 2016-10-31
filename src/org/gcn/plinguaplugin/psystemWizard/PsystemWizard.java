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

package org.gcn.plinguaplugin.psystemWizard;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.gcn.plinguaplugin.formatConstants.PlinguaConstants;
import org.gcn.plinguaplugin.wizardCommonComponents.CreateFileRunnable;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;

/**
 * This class provides a graphic interface for creating P-System file, setting all its preferences such as name and model
 * @author Manuel Garcia-Quismondo-Dernandez
 *
 */
public class PsystemWizard extends Wizard implements INewWizard {

	private PsystemWizardParametersPage paramPage;
	private static final String TITLE = "New P-System";
	private final String PRE_MODEL_CODE="@model<";
	private final String POST_MODEL_CODE = ">";
	private final String PRE_FUNCTION_CODE="\n\ndef ";
	private final String PRE_CALL_CODE = "\n\tcall ";
	private final String INITIAL_MEMBRANE = "\n\t@mu = []'0;\n";
	private final String POST_CALL_CODE = "();\n";
	private final String POST_FUNCTION_CODE="() {\n";
	private final String FUNCTION_CLOSURE_CODE ="\n}";
	private final String MAIN_CODE = "main";

	private String model;
	private String name;
	private String filePackage;
	private String project;
	private boolean hasMain;
	
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish(){
		return (filePackage!=null&&project!=null&&model!=null&&name!=null);
	}
	/**
	 * Creates a new {@link PsystemWizard} instance
	 */
	public PsystemWizard() {
		
		/*Set everything to null, so the wizard can't finish*/
		model = null;
		name = null;
		setPackage(null, true);		
		project = null;
		hasMain = false;
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a P-System file according to the wizard parameters
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		String initialContent = createInitialContent();
		/*Create the file creation operation*/ 
		IRunnableWithProgress op = new CreateFileRunnable(initialContent, project+"/"+filePackage, name+"."+PlinguaConstants.PLINGUA_EXTENSION, getShell());
		/*Execute the file creation operation, by delegating it on WizardComponents class*/
		FileCreator.executeContainerAction(this, op, getShell());
		return true;
	}
	
	/**
	 * A method for creating the initial file content
	 */
	private String createInitialContent() {
		/*Build the common P-System file content*/
		String initialContent = PRE_MODEL_CODE;
		initialContent+= model;
		initialContent+= POST_MODEL_CODE;
		initialContent+=PRE_FUNCTION_CODE;
		initialContent+= name;
		initialContent+= POST_FUNCTION_CODE;
		initialContent += INITIAL_MEMBRANE;
		initialContent+=FUNCTION_CLOSURE_CODE;
		/*If there's a main method, add it*/
		if(hasMain){
			initialContent+= PRE_FUNCTION_CODE;
			initialContent+=MAIN_CODE;
			initialContent+= POST_FUNCTION_CODE;
			initialContent+= PRE_CALL_CODE;
			initialContent+= name;
			initialContent+= POST_CALL_CODE;
			initialContent+=FUNCTION_CLOSURE_CODE;
			
		}
		/*Return the string created*/
		return initialContent;
		
		// TODO Auto-generated method stub

	}

	


	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		/*If the selection is not suitable, return null*/
		if(selection==null) return;
		Object elementSelected = selection.getFirstElement();
		/*If the element is not a resource, do not set project nor package*/
		if(!(elementSelected instanceof IResource)){
			setProject("", false);
			setPackage("", false);
			return;
		}
		IResource resource = (IResource)elementSelected;
		/*If the resource is a file, is not valid, so get its container*/
		if(resource instanceof IFile){
			resource = resource.getParent();
			
		}

		
		/*Get the package route*/
		String packageRoute = resource.getProjectRelativePath().toString();		

		/*If the resource is a project, the package route is "src"*/
		if(resource instanceof IProject){
			packageRoute = "src";
			
		}
		/*Set the project route*/
		setProject(resource.getProject().getFullPath().toString(), false);
		/*Set the package route*/
		setPackage(packageRoute, false);

	}
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		/*Set the title*/
		setWindowTitle(TITLE);

		/*Add the pages*/
		paramPage = new PsystemWizardParametersPage();
		
		addPage(paramPage);

	}

	/**
	 * Sets the package where the  P-system file will be created
	 * @param filePackage the package where the  P-system file will be created
	 * @param change a boolean which indicates if the wizard parameters page should be update
	 */
	public void setPackage(String filePackage, boolean change) {
		this.filePackage = filePackage;
		/*If the file package is not null, display it*/
		if(change&&filePackage!=null)
			paramPage.setPackage(filePackage);
	}
	
	/**
	 * Sets the name of the P-system file to create
	 * @param name the name of the P-system file to create	 
	 */
	public void setPsystemName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name of the P-system file to create
	 * @return the name of the P-system file to create	 
	 */
	public String getPsystemName() {
		return name;
	}

	/**
	 * Gets the package where the P-system file will be created
	 * @return the package where the P-system file will be created
	 */
	public String getPackage() {
		return filePackage;
	}
	

	/**
	 * Sets the model of the P-system to create
	 * @param model the model of the P-system to create
	 */
	public void setPsystemModel(String model){
		this.model = model;
	}
	
	/**
	 * Gets the model of the P-system to create
	 * @return the model of the P-system to create
	 */
	public String getPsystemModel(){
		return model;
	}
	
	/**
	 * Gets if the P-System file to create will have a main method
	 * @return true if the P-System file to create will have a main method, false otherwise
	 */
	public boolean getHasMain(){
		return hasMain;
	}
	
	/**
	 * Sets if the P-System file to create will have a main method
	 * @param hasMain true if the P-System file to create will have a main method, false otherwise
	 * 
	 */
	public void setHasMain(boolean hasMain){
		this.hasMain = hasMain;
	}
	
	/**
	 * Sets the project of the P-System file to create
	 * @param project the project of the P-System file to create
	 * @param change a boolean indicating if it's necessary to update the parameters page
	 */
	public void setProject(String project, boolean change){
		this.project = project;
		/*If the project is not null, display it*/
		if(change&&project!=null)
			paramPage.setProject(project);
		
	}
	
	/**
	 * Gets the project of the P-System file to create
	 * @return the project of the P-System file to create
	 */
	public String getProject(){
		return project;
		
	}


}
