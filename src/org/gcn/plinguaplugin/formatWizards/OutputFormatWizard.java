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

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;

/**
 * This class provides the wizard interface for exporting P-systems among formats, that is, for generating files which encode P-systems whose original files are within the current workspace
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class OutputFormatWizard extends FormatTranslatorWizard {

	private static final String TITLE = "Export P-system";
	
	private Psystem psystem;
	/** Creates a new {@link OutputFormatWizard} instance to display a graphical interface for translating P-system whose files are located within the current workspace
	 * @param psystem The P-system to translate
	 * @param file the source file where the P-system is encoded in
	 * */
	public OutputFormatWizard(Psystem psystem, String file){

		this.psystem = psystem;
		/*If the P-system is null, preserve the format*/
		if(psystem==null)
			super.setPreserveOriginalFormat(true);
		if (file == null)
			throw new NullPointerException("The internal file argument shouldn't be null");
		
		this.workspaceFile = file;
	}
	
	private String workspaceFile;
	/**
	 * @see org.gcn.plinguaplugin.formatWizards.FormatTranslatorWizard#getWorkspaceFile()
	 */
	@Override
	public String getWorkspaceFile() {
		// TODO Auto-generated method stub
		return workspaceFile;
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#performFinish()
	 */
	@Override
	public boolean performFinish() {
		// TODO Auto-generated method stub
		IRunnableWithProgress op = createRunnable();
		/*Execute the operation to create the file, by delegating it on WizardComponents class*/
		return FileCreator.executeContainerAction(this, op, getShell());
	}
	
	private IRunnableWithProgress createRunnable() {
		// TODO Auto-generated method stub
		/*Obtain the file with the right extension*/
		String externalFile = trimExtension();
		if(this.preserveOriginalFormat()){
			
			/*Get the container*/
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			IResource resource = root.findMember(new Path(getWorkspaceFile()));
			/*If there's no container, report an error*/
			if (resource==null||!resource.exists() || !(resource instanceof IFile)) {
				PlinguaLog.logError(new RuntimeException("File \"" + getWorkspaceFile() + "\" does not exist."));
				return null;
			}
			/*Copy the file with the right extension*/
			return new RunnableOutputCopycat((IFile)resource, externalFile);			
		}
		/*Translate the P-system*/
		return new RunnableOutputTranslator(psystem, externalFile, getFormat());
	}

	


	private String trimExtension(){
		String baseString = getExternalFile();
		if(getExternalFile().contains("."))
			baseString =  baseString.substring(0, getExternalFile().lastIndexOf("."));
		if(preserveOriginalFormat())
			return baseString+getWorkspaceFile().substring(getWorkspaceFile().lastIndexOf("."));
		return baseString+"."+PsystemController.getExtension(getFormat());
		
	}
	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages(){
		/*Set the title*/
		setWindowTitle(getWorkspaceFile()+" - "+TITLE);

		/*Add the pages*/
		OutputFormatWizardPage page = new OutputFormatWizardPage();
		if(psystem==null){
			page.disablePreserveFormat(true);
		}
		addPage(page);
		
	}

}
