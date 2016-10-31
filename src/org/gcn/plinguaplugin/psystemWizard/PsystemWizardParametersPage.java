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

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguaplugin.PlinguaPlugin;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.formatConstants.PlinguaConstants;
import org.gcn.plinguaplugin.wizardCommonComponents.ResourceExistanceTester;
import org.gcn.plinguaplugin.wizardCommonComponents.WizardComponents;




/**
 * This class implements the page for {@link PsystemWizard} instances related to P-system parameters, such as file name, package and P-system model 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class PsystemWizardParametersPage extends WizardPage {

	private final String PSYSTEM_ICON = "icons/Psystem.JPG";
	private static final String PAGE_NAME = "psystemFeatures";
	private  static final String PAGE_TITLE = "Select P-System features";
	private  static final String PAGE_DESCRIPTION = "Select the P-System model, the P-System file name and package, and if it will contain a main method";
	
	private Text packageTextField;
	private Text projectTextField;
	private Text nameTextField;
	private Button mainMethodOption;
	
	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		/*Create the main composite*/
		Composite mainComposite = new Composite(parent, SWT.NULL);
		mainComposite.setLayout(new FormLayout());
		/*Create the main composite attachments*/
		FormData mainCompositeFormData = new FormData();
		mainCompositeFormData.top = new FormAttachment(0);
		mainCompositeFormData.bottom = new FormAttachment(100);
		mainCompositeFormData.left = new FormAttachment(0);
		mainCompositeFormData.right = new FormAttachment(100);
		mainComposite.setLayoutData(mainCompositeFormData);
		/*Set the main composite as the wizard composite*/
		setControl(mainComposite);
		/*Create the simulator parameters part*/
		createPsystemParametersDisplayer(mainComposite);
	}
	
	private void createPsystemParametersDisplayer(Composite mainComposite) {
		/*Create the p-system file package composite*/
		Composite psystemProjectComposite = createPsystemProject(mainComposite);
		/*Create the p-system file package composite*/
		Composite psystemPackageComposite = createPsystemPackage(mainComposite, psystemProjectComposite);
		/*Create the p-system file name composite*/
		Composite psystemFileNameComposite = createPsystemFileName(mainComposite, psystemPackageComposite);
		/*Create the model selector chooser*/
		createPsystemModelSelector(mainComposite, psystemFileNameComposite);
		/*Create the main method option*/
		createPsystemMainMethodOption(mainComposite, psystemFileNameComposite);

		
	}
	
	private void createPsystemMainMethodOption(Composite mainComposite,
			Composite upperComposite) {
		/*Create the main method option composite*/
		Composite mainMethodComposite = new Composite(mainComposite, SWT.NULL);
		/*The main method content belongs not to the main method composite, but to one of its rows. It's so because this way it's simpler to add parameters*/
		mainMethodComposite.setLayout(new RowLayout(SWT.VERTICAL));
		/*Set the main method option attachments*/
		FormData mainMethodLayoutData = new FormData();
		mainMethodLayoutData.top = new FormAttachment(upperComposite, 10);
		mainMethodLayoutData.right = new FormAttachment(100, -35);
		mainMethodLayoutData.bottom = new FormAttachment(100);
		mainMethodComposite.setLayoutData(mainMethodLayoutData);
		/*Set the main method option contents*/
		mainMethodOption = WizardComponents.createOptionRow(mainMethodComposite, "Create main method");
		/*Set the main method option behavior*/
		mainMethodOption.addSelectionListener(new HasMainListener(this, mainMethodOption));
		/*Enable the has main option. When the modular P-system specification will has been developed, this option will be disabled. So far, this option remains enabled*/
		disableHasMain();
		

		
		
		// TODO Auto-generated method stub
		
	}

	private void disableHasMain() {
		mainMethodOption.setEnabled(false);
		mainMethodOption.setSelection(true);
		setHasMain(true);
		// TODO Auto-generated method stub
		
	}

	private Composite createPsystemModelSelector(Composite mainComposite,
			Composite psystemUpperComposite) {
		
		/*Create the simulator IDs composite*/
		Composite psystemModelsComposite = new Composite(mainComposite, SWT.NULL);
		/*Create its attachments*/
		FormData psystemModelsFormData = new FormData();
		psystemModelsFormData.top = new FormAttachment(psystemUpperComposite, 10);
		psystemModelsFormData.left = new FormAttachment(0, 0);
		psystemModelsComposite.setLayoutData(psystemModelsFormData);
		/*Set its layout, so inner objects can attach to it*/
		psystemModelsComposite.setLayout(new FormLayout());
		createPsystemModelList(psystemModelsComposite);
		// TODO Auto-generated method stub
		return psystemModelsComposite;
	}

	private void createPsystemModelList(Composite psystemModelsComposite) {
		WizardComponents.createListIDsContent(psystemModelsComposite, "Available Models", new ArrayContentProvider(), new PsystemModelLabelProvider(), new PsystemModelListener(this), PsystemController.getAvailableModels());
		
	}

	

	private Composite createPsystemFileName(Composite mainComposite,
			Composite upperPackageComposite) {
		Composite psystemFileNameComposite = WizardComponents.createStackedComposite(mainComposite, upperPackageComposite);
		/*Create the file name composite contents*/
		nameTextField = WizardComponents.createTextContents(psystemFileNameComposite, "Name:", 10);
		nameTextField.addModifyListener(new PsystemNameListener(this, nameTextField));
		// TODO Auto-generated method stub
		return psystemFileNameComposite;
	}

	private Composite createPsystemPackage(Composite mainComposite, Composite upperComposite) {
		// TODO Auto-generated method stub
		/*Create the composite*/
		Composite psystemPackageComposite = WizardComponents.createStackedComposite(mainComposite, upperComposite);
		/*Create the package composite contents*/
		packageTextField = WizardComponents.createTextContents(psystemPackageComposite, "Package:", -5);
		/*Set the selected package*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		packageTextField.setText(wizard.getPackage());
		packageTextField.addModifyListener(new PsystemPackageListener(this, packageTextField));
		/*If the package is empty, the P-system wizard is not ready*/
		if(wizard.getPackage().isEmpty())
			wizard.setPackage(null, false);
		return psystemPackageComposite;
	}
	
	private Composite createPsystemProject(Composite mainComposite) {
		// TODO Auto-generated method stub
		/*Create the composite*/
		Composite psystemProjectComposite = WizardComponents.createStackedComposite(mainComposite, null);
		/*Create the package composite contents*/
		projectTextField = WizardComponents.createTextContents(psystemProjectComposite, "Project:");
		/*Set the selected package*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		
		projectTextField.setText(wizard.getProject());
		projectTextField.addModifyListener(new PsystemProjectListener(this, projectTextField));
		/*If the project is empty, the P-system wizard is not ready*/
		if(wizard.getProject().isEmpty())
			wizard.setProject(null, false);
		
		return psystemProjectComposite;
	}
	


	/**
	 * Creates a new {@link PsystemWizardParametersPage} instance
	 */
	protected PsystemWizardParametersPage() {
		super(PAGE_NAME);
		setTitle(PAGE_TITLE);
		setDescription(PAGE_DESCRIPTION);
		/*Set the page image*/
		setImageDescriptor(PlinguaPlugin.getImageDescriptor(PSYSTEM_ICON));
	}
	
	/**
	 * Sets the wizard parameters page package where the P-System file will be created
	 * @param filePackage the package where the P-System file will be created
	 */
	public void setPackage(String filePackage){
		String packageToSet = filePackage;
		/*If the package is null, represent it as an empty string*/
		if(filePackage==null)
			packageToSet="";
		else{
			/*Update the wizard page indicating not to update this page only in case it's not null, indicating the package is valid*/
			PsystemWizard wizard = (PsystemWizard)getWizard();
			
			wizard.setPackage(filePackage.replace("/", "."), false);
		}
		/*Set the package representation (an empty string in case it's null)*/
		if(packageTextField!=null)
			packageTextField.setText(packageToSet);
		testCompleteness();

		
		
		
	}
	/**
	 * Sets the model of the P-system file to create
	 * @param model the model of the P-system file to create
	 */
	 
	public void setPsystemModel(String model){
		/*Get the wizard*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		/*Set the model*/
		wizard.setPsystemModel(model);
		/*Test page completeness*/
		testCompleteness();
		
		
	}
	
	/**
	 * Test if the page is complete and reports so
	 */
	public void testCompleteness(){
		this.setPageComplete(specificCondition());
		
		
	}

	private boolean specificCondition() {
		// TODO Auto-generated method stub
		/*Get the wizard*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		/*Test if the P-system file parameters are correct*/
		if (!(wizard.getProject()!=null&&wizard.getPsystemModel()!=null&&wizard.getPackage()!=null&&wizard.getPsystemName()!=null&&!wizard.getPsystemName().isEmpty()))
			return false;
		return ResourceExistanceTester.testResources(this, wizard.getProject(), wizard.getPackage(), wizard.getPsystemName()+"."+PlinguaConstants.PLINGUA_EXTENSION);
	}
	
	/**
	 * Sets the wizard P-System file name
	 * @param name the P-System file name to set
	 */
	public void setPsystemName(String name){
		/*Get the wizard*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		/*Set its P-System file name*/
		wizard.setPsystemName(name);
		/*Test page completeness*/
		testCompleteness();
		
	}
	
	/**
	 * Sets the wizard page workspace project to create the file where the P-system is encoded in. The project is actually stored in the wizard where the wizard page belongs to
	 * @param project the wizard page workspace project to create the file where the P-system is encoded in	 
	 */
	public void setProject(String project){
		String projectToSet = project;
		/*If the package is null, represent it as an empty string*/
		if(project==null)
			projectToSet="";
		else{
			/*Update the wizard page indicating not to update this page only in case it's not null, indicating the project is valid*/
			PsystemWizard wizard = (PsystemWizard)getWizard();
			
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
	
	/**
	 * Sets if the P-System file name in the wizard has a main method
	 * @param hasMain a boolean indicating if the P-System file name in the wizard has a main method
	 */
	public void setHasMain(boolean hasMain){
		/*Get the wizard*/
		PsystemWizard wizard = (PsystemWizard)getWizard();
		/*Set if the P-System file name has a main method*/
		wizard.setHasMain(hasMain);
	}
	


}
