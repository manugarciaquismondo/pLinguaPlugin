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

import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;

/**
 * This class provides the wizard interface for importing P-systems among formats, that is, for including P-systems in the current workspace
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class InputFormatWizard extends FormatTranslatorWizard {

	/**
	 * The standard extension for backup files
	 */
	private static final String BACKUP_EXTENSION ="bak";
	
	/** Creates a new {@link InputFormatWizard} instance to display a graphical interface for translating P-system encoded in files from files located within the current workspace*/
	public InputFormatWizard() {
		super();

		// TODO Auto-generated constructor stub
	}

	private static final String TITLE = "Import P-system";
	private InputFormatWizardPage page;
	private String project;
	private String filePackage;
	private String workspaceFile;
	
	/** Translates the P-system encoded in the external file into the format given as parameter, and encodes the resulting P-system in the internal file. If the parameter "preserve format" is true, the format is kept among files 
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {

		/*Create the operation to create the file*/ 
		IRunnableWithProgress op = createInputTranslator();
		/*If the P-system contained errors, return false*/
		if(op==null) {			
			return true;
		}
		/*Execute the operation to create the file, by delegating it on WizardComponents class*/
		FileCreator.executeContainerAction(this, op, getShell());
		return true;
	}
	
	private IRunnableWithProgress createInputTranslator(){
		
		/*Obtain the workspace route*/
		String workspaceRoute = ResourcesPlugin.getWorkspace().getRoot().getLocation().toString();
		/*Obtain the local route*/
		String localRoute = getProject()+"/"+getPackage()+"/"+getWorkspaceFile()+obtainExtension();
		/*Concat the destination file route*/
		String totalRoute = workspaceRoute+localRoute;
		
		/*If the original format should be preserved, copy the file*/
		if(preserveOriginalFormat()){
			
			
			return new RunnableInputCopycat(getExternalFile(), localRoute, totalRoute, getShell());
		}
		/*Create a controller to parse the P-system*/
		PsystemController controller = new PsystemController();
		/*Parse the P-system*/
		Psystem psystem = controller.parsePsystemFromInputFile(getExternalFile());
		/*If there were errors, report them*/
		if(controller.errorsFound()){
			reportErrors(controller.getReportMessage(), controller.getLatestException());
			return null;
		}
		/*If no P-system could be parsed, return null*/
		if(psystem==null) return null;
		/*If the format should not be preserved, parse the file*/
		return new RunnableInputTranslator(psystem, workspaceRoute,localRoute,  getFormat(), getShell());
		
			
	}

	private void reportErrors(String reportMessage, Exception latestException) {
		/*Report the error on the log*/
		PlinguaLog.logError(reportMessage, latestException);
		/*Report errors in a message box*/
		MessageBox messageBox = new MessageBox(getShell(), SWT.ERROR|SWT.OK);
		messageBox.setText("Error creating file");
		messageBox.setMessage(reportMessage);
		
		/*Open the box*/
		messageBox.open();
		
	}

	
	/** This method obtains the extension for a format*/
	private String obtainExtension(){
		/*If the format shouldn't be preserved, check the defined format*/
		if(!preserveOriginalFormat()){
			if(getFormat()!=null)
				/*If there is a format, return its extension*/
				return "."+PsystemController.getExtension(getFormat());
			return "";
		}
		/*If the format should be preserved, obtain the external file extension*/
		String externalFile = getExternalFile();
		if(externalFile!=null&&!externalFile.isEmpty())
			return externalFile.substring(externalFile.lastIndexOf("."));
		return "";


		
		
	}
	/**
	 * Sets the wizard workspace project to create the translated file in
	 * @param project the wizard workspace project to create the translated file in
	 * @param change a boolean indicating if it's necessary to update the parameters wizard page
	 */
	public void setProject(String project, boolean change){
		this.project = project;
		/*If the project is not null, display it*/
		if(change&&project!=null)
			page.setProject(project);
	}
	
	/**
	 * Gets the wizard workspace file project to create the translated file in
	 * @return the wizard workspace file project to create the translated file in
	 */
	public String getProject(){
		return project;
		
	}
	
	/**
	 * Gets the package where the P-system will be translated to
	 * @return the package where the P-system will be translated to
	 */
	public String getPackage() {
		return filePackage;
	}
	
	/**
	 * Sets the package where the P-system will be translated to
	 * @param filePackage the package where the  P-system will be translated to
	 * @param change a boolean which indicates if the wizard parameters page should be update
	 */
	public void setPackage(String filePackage, boolean change) {
		this.filePackage = filePackage;
		/*If the file package is not null, display it*/
		if(change&&filePackage!=null)
			page.setPackage(filePackage);
	}
	
	/**
	 * Sets the name of the P-system file to create
	 * @param file the name of the P-system file to create	 
	 * @param change a boolean which indicates if the wizard parameters page should be updated
	 */
	public void setWorkspaceFile(String file, boolean change) {
		this.workspaceFile = file;
		/*If the file package is not null, display it*/
		if(change&&file!=null)
			page.setWorkspaceFile(file);
	}
	/**
	 * @see org.gcn.plinguaplugin.formatWizards.FormatTranslatorWizard#getWorkspaceFile()
	 */
	@Override
	public String getWorkspaceFile() {
		// TODO Auto-generated method stub
		return workspaceFile;
	}
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages(){
		/*Set the title*/
		setWindowTitle(TITLE);

		/*Add the pages*/
		page = new InputFormatWizardPage();
		
		addPage(page);
	}
	/**
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish(){
		return super.canFinish()&&isCorrect(getProject())&&isCorrect(getPackage());
	}


}
