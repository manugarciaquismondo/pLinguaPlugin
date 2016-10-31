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


import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.WizardComponents;
/**
 * This class displays a wizard page containing all export and import parameters needed to perform such operations
 * @author Manuel Garcia-Quismondo-Fernandez
 */
public abstract class FormatTranslatorWizardPage extends WizardPage {

	protected Composite mainComposite;
	/*This constant sets the label dimensions of the "preserve original label" message, reporting if the file format will be preserved from the source file*/
	private final String PRESERVE_LABEL_DIMENSIONS = "                                           \n                                           \n                                           \n                                           ";
	private static final String NAME = "formatTranslatorWizard";
	
	private boolean disablePreserveFormat;
	
	private boolean preserveFormatValue;
	/**
	 * Creates a new  {@link FormatTranslatorWizardPage} instance
	 */
	protected FormatTranslatorWizardPage(String name){
		super(NAME);
		disablePreserveFormat = false;
	}
	private Text fileChooserText;
	private Button preserveFormatOption;

	/**
	 * Sets if the original file format and name should be preserved
	 * @param preserve a boolean indicating if the original file format and name should be preserved
	 */
	public void setPreserveOriginalFormat(boolean preserve){
		((FormatTranslatorWizard)getWizard()).setPreserveOriginalFormat(preserve);
		testCompleteness();
	}
	
	protected void createMainComposite(Composite parent){
		/*Create the main composite*/
		mainComposite = new Composite(parent, SWT.NULL);
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
	}
	/**
	 * @see org.eclipse.jface.dialogs.IDialogPage#createControl(org.eclipse.swt.widgets.Composite)
	 */
	@Override
	public void createControl(Composite parent) {
		/*Create the main composite*/
		createMainComposite(parent);


		/*Create the simulator parameters part*/
		createParametersDisplayer(mainComposite, null);

	}
	protected void createParametersDisplayer(Composite mainComposite, Composite upperComposite) {
		// TODO Auto-generated method stub
		/*Create the model selector chooser*/
		createOutputFormatSelector(mainComposite, upperComposite);
		/*Create the main method option*/
		createPreserveFormatOption(mainComposite, upperComposite);
		/*Create the external file chooser*/
		createExternalFileChooser(mainComposite);
		
	}
	
	private Composite createExternalFileChooser(Composite parentComposite){
		/*Create the file chooser composite*/
		Composite fileChooserComposite = new Composite(parentComposite, SWT.NULL);
		fileChooserComposite.setLayout(new FormLayout());
		/*Create the file chooser composite attachments*/
		FormData fileChooserFormData = new FormData();
		fileChooserFormData.left = new FormAttachment(0, 0);
		fileChooserFormData.right = new FormAttachment(100, 0);
		fileChooserFormData.bottom = new FormAttachment(100, 0);
		fileChooserComposite.setLayoutData(fileChooserFormData);
				
		/*Create file chooser contents and get the resulting field*/
		fileChooserText = WizardComponents.createFileChooserText("Select "+getDirection()+" file:", fileChooserComposite, new ExternalFileListener(this));
		return fileChooserComposite;
	}
	private void createPreserveFormatOption(Composite mainComposite,
			Composite upperComposite) {
		/*Create the main method option composite*/
		Composite mainMethodComposite = new Composite(mainComposite, SWT.NULL);
		/*The main method content belongs not to the main method composite, but to one of its rows. It's so because this way it's simpler to add parameters*/
		mainMethodComposite.setLayout(new RowLayout(SWT.VERTICAL));
		/*Set the main method option attachments*/
		FormData mainMethodLayoutData = new FormData();
		if(upperComposite!=null)
			mainMethodLayoutData.top = new FormAttachment(upperComposite, 10);
		else
			mainMethodLayoutData.top = new FormAttachment(0, 10);
		mainMethodLayoutData.right = new FormAttachment(100, -35);
		mainMethodComposite.setLayoutData(mainMethodLayoutData);
		/*Set the main method option contents*/
		preserveFormatOption = WizardComponents.createOptionRow(mainMethodComposite, "Preserve format");
		/*Report the preserve format signal*/
		Label preserveLabel = new Label(mainMethodComposite, SWT.NULL);
		/*Set the original label dimensions*/
		preserveLabel.setText(PRESERVE_LABEL_DIMENSIONS);
		/*Set the main method option behavior*/
		preserveFormatOption.addSelectionListener(new PreserveFormatListener(this, preserveFormatOption, preserveLabel));
		if(disablePreserveFormat){
			/*Disable the option*/
			this.preserveFormatOption.setEnabled(false);
			/*Force the option*/
			this.preserveFormatOption.setSelection(preserveFormatValue);
		}
		
		
		// TODO Auto-generated method stub
		
	}
	/**
	 * Sets the file related to format translation which can be out of the current workspace. If it's the destination file or the source file depends on the specific child class
	 * @param file the file related to format translation which can be out of the current workspace
	 */
	public void setExternalFile(String file){
		String fileToSet = file;
		/*Set the wizard simulator file route*/
		((FormatTranslatorWizard)getWizard()).setExternalFile(fileToSet);
		/*Set the simulator file route text field*/
		if(file==null)
			fileToSet = "";		
		fileChooserText.setText(fileToSet);
		
		
		
		/*Update page completition*/
		testCompleteness();
	}
	
	/**
	 * Sets the format which the destination file should be encoded on
	 * @param format the format which the destination file should be encoded on
	 */
	public void setFormat(String format){
		((FormatTranslatorWizard)getWizard()).setFormat(format);
		testCompleteness();
	}
	
	private Composite createOutputFormatSelector(Composite mainComposite,
			Composite upperComposite) {
		
		/*Create the simulator IDs composite*/
		Composite psystemModelsComposite = new Composite(mainComposite, SWT.NULL);
		/*Create its attachments*/
		FormData psystemModelsFormData = new FormData();
		if(upperComposite!=null)
			psystemModelsFormData.top = new FormAttachment(upperComposite, 10);
		else
			psystemModelsFormData.top = new FormAttachment(0, 10);
		psystemModelsFormData.left = new FormAttachment(0, 0);
		psystemModelsComposite.setLayoutData(psystemModelsFormData);
		/*Set its layout, so inner objects can attach to it*/
		psystemModelsComposite.setLayout(new FormLayout());
		WizardComponents.createListIDsContent(psystemModelsComposite, "Available Output Formats", new ArrayContentProvider(), new OutputFormatLabelProvider(), new FormatIDListener(this), PsystemController.getAvailableOutputFormats());
		// TODO Auto-generated method stub
		return psystemModelsComposite;
	}
	/**
	 * Test if the page is complete
	 */
	public void testCompleteness(){
		setPageComplete(specificCondition());
		
	}
	
	/**
	 * Tells if the wizard status is correct to perform finish
	 */
	protected boolean specificCondition() {
		FormatTranslatorWizard wizard = (FormatTranslatorWizard)getWizard();
		// TODO Auto-generated method stub
		return isCorrect(wizard.getExternalFile())&&(wizard.preserveOriginalFormat()||isCorrect(wizard.getFormat()))&&isCorrect(wizard.getWorkspaceFile());
	}

	/**Tests if a string is correct*/
	protected boolean isCorrect(String text) {
		// TODO Auto-generated method stub
		return text!=null&&(!text.isEmpty());
	}
	
	/**
	 * Gets the direction of the external file
	 *@return a string reporting the direction of the external file
	 */
	protected abstract String getDirection();
	
	/**
	 * Disables the preserve format option and sets a value
	 * @param selected the value to set
	 */
	
	protected void disablePreserveFormat(boolean selected){
		disablePreserveFormat= true;
		preserveFormatValue = selected;

	}
	

}
