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

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.ResourceExistanceTester;
import org.gcn.plinguaplugin.wizardCommonComponents.WizardComponents;

/**
 * This class implements the page for {@link InputFormatWizard} instances related to translation parameters when translating to files in the current workspace, such as selecting the project, package and file where the translated file will be created in
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class InputFormatWizardPage extends FormatTranslatorWizardPage {

	private static final String PAGE_NAME = "translationFeatures";
	private  static final String PAGE_TITLE = "Select destination file features";
	private  static final String PAGE_DESCRIPTION = "Select the destination file format and the P-system file route, as well as the file route where the destination file will be created in";
	protected InputFormatWizardPage() {
		super(PAGE_NAME);
		setTitle(PAGE_TITLE);
		setDescription(PAGE_DESCRIPTION);
		// TODO Auto-generated constructor stub
	}

	private Text projectTextField;
	private Text packageTextField;
	private Text workspaceFileTextField;
	
	/**
	 * This class adds the project, package and workspace text fields to the wizard, as they're necessary for including P-system encoded in files
	 * @param parent the parent composite for the wizard
	 */
	@Override
	public void createControl(Composite parent){
		/*Create the main composite*/
		createMainComposite(parent);
		/*Create the text fields*/
		Composite textFields = createTextFields(mainComposite);
		/*Create the rest of the interface*/
		createParametersDisplayer(mainComposite, textFields);
		
	}

	private Composite createTextFields(Composite mainComposite) {
		// TODO Auto-generated method stub
		/*Create the project composite*/
		Composite projectComposite = createProjectComposite(mainComposite);
		/*Create the package composite*/
		Composite packageComposite = createPackageComposite(mainComposite, projectComposite);
		/*Create the workspace file composite*/
		return createWorkspaceFileComposite(mainComposite, packageComposite);
	}

	private Composite createWorkspaceFileComposite(Composite mainComposite,
			Composite upperPackageComposite) {
		// TODO Auto-generated method stub
		Composite fileNameComposite = WizardComponents.createStackedComposite(mainComposite, upperPackageComposite);
		/*Create the file name composite contents*/
		InputFormatWizard wizard = (InputFormatWizard)getWizard();
		workspaceFileTextField = WizardComponents.createTextContents(fileNameComposite, "Name:", 10);
		workspaceFileTextField.addModifyListener(new InternalFileListener(this, workspaceFileTextField));
		/*If the package is empty, the format wizard is not ready*/
		if(wizard.getWorkspaceFile()==null||wizard.getWorkspaceFile().isEmpty())
			wizard.setWorkspaceFile("", false);
		// TODO Auto-generated method stub
		return fileNameComposite;
	}

	/**
	 * Sets the wizard page workspace project to create the translated file in. The project is actually stored in the wizard where the wizard page belongs to
	 * @param project the wizard page workspace project to create the translated file in	 
	 */
	public void setProject(String project){
		String projectToSet = project;
		/*If the package is null, represent it as an empty string*/
		if(project==null)
			projectToSet="";
		else{
			/*Update the wizard page indicating not to update this page only in case it's not null, indicating the project is valid*/
			InputFormatWizard wizard = (InputFormatWizard)getWizard();
			
			wizard.setProject(project, false);
		}
		/*Set the project representation (an empty string in case it's null)*/
		if(projectTextField!=null){
			/*If the project representation starts with "/", erase it*/
			if(projectToSet.startsWith("/"))
				projectToSet = projectToSet.substring(1);
			/*Set the project representation*/
			projectTextField.setText(projectToSet);
		}
		/*Test if the page is complete*/
		testCompleteness();
	}
	
	private Composite createPackageComposite(Composite mainComposite,
			Composite upperComposite) {
		// TODO Auto-generated method stub
		/*Create the composite*/
		Composite formatPackageComposite = WizardComponents.createStackedComposite(mainComposite, upperComposite);
		/*Create the package composite contents*/
		packageTextField = WizardComponents.createTextContents(formatPackageComposite, "Package: ", -5);
		/*Set the selected package*/
		InputFormatWizard wizard = (InputFormatWizard)getWizard();
		packageTextField.setText(wizard.getPackage());
		packageTextField.addModifyListener(new FormatPackageListener(this, packageTextField));
		/*If the package is empty, the format wizard is not ready*/
		if(wizard.getPackage()==null||wizard.getPackage().isEmpty())
			wizard.setPackage("", false);
		return formatPackageComposite;
	}

	private Composite createProjectComposite(Composite mainComposite) {
		// TODO Auto-generated method stub
		/*Create the composite*/
		Composite formatProjectComposite = WizardComponents.createStackedComposite(mainComposite, null);
		/*Create the package composite contents*/
		projectTextField = WizardComponents.createTextContents(formatProjectComposite, "Project:   ", -5);
		/*Set the selected package*/
		InputFormatWizard wizard = (InputFormatWizard)getWizard();
		projectTextField.setText(wizard.getProject());
		projectTextField.addModifyListener(new FormatProjectListener(this, projectTextField));
		/*If the package is empty, the format wizard is not ready*/
		if(wizard.getProject()==null||wizard.getProject().isEmpty())
			wizard.setProject("", false);
		return formatProjectComposite;
	}
	
	/**
	 * Sets the wizard parameters page package where the P-System file will be translated to
	 * @param filePackage the package where the P-System file will be translated to
	 */
	public void setPackage(String filePackage){
		String packageToSet = filePackage;
		/*If the package is null, represent it as an empty string*/
		if(filePackage==null)
			packageToSet="";
		else{
			/*Update the wizard page indicating not to update this page only in case it's not null, indicating the package is valid*/
			InputFormatWizard wizard = (InputFormatWizard)getWizard();
			
			wizard.setPackage(filePackage.replace("/", "."), false);
		}
		/*Set the package representation (an empty string in case it's null)*/
		if(packageTextField!=null)
			packageTextField.setText(packageToSet);
		testCompleteness();
	}
	
	/**
	 * Sets the wizard file name where the P-system translation will be created
	 * @param file the wizard file name where the P-system translation will be created
	 */
	public void setWorkspaceFile(String file){
		String fileToSet = file;
		/*If the file is null, represent it as an empty string*/
		if(file==null)
			fileToSet="";
		else{
			/*Update the wizard page indicating not to update this page only in case it's not null, indicating the package is valid*/
			InputFormatWizard wizard = (InputFormatWizard)getWizard();
			
			wizard.setWorkspaceFile(fileToSet, false);
		}
		/*Set the package representation (an empty string in case it's null)*/
		if(workspaceFileTextField!=null)
			workspaceFileTextField.setText(fileToSet);
		testCompleteness();
		
	}
	
	@Override
	protected boolean specificCondition(){
		/*Test if the package and project are correct, as well*/
		InputFormatWizard wizard = (InputFormatWizard)getWizard();
		if(! (super.specificCondition()&&isCorrect(wizard.getProject())&&isCorrect(wizard.getPackage())))
			return false;	
		/*Test if the package and project exist, but the file does not*/
		return ResourceExistanceTester.testResources(this, wizard.getProject(), wizard.getPackage(), wizard.getWorkspaceFile()+obtainExtension());
		
	}
	

	private String obtainExtension() {
		// TODO Auto-generated method stub
		InputFormatWizard wizard = (InputFormatWizard)getWizard();
		String format = wizard.getFormat();
		if(format==null||format.isEmpty())
			return "";
		return "."+PsystemController.getExtension(format);
	}
	
	@Override
	protected String getDirection(){ return "source";}

}
