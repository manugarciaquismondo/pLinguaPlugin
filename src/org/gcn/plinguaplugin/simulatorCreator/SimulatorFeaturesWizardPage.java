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

package org.gcn.plinguaplugin.simulatorCreator;

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
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaPlugin;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.WizardComponents;

/**
 * This class implements the page for {@link SimulatorCreatorDisplayer} instances related to simulator parameters, such as allowing steps back or alternative steps 
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class SimulatorFeaturesWizardPage extends WizardPage {

	private final String SIMULATOR_ICON =  "icons/Simulator.JPG";
	
	/**
	 * @see org.eclipse.jface.wizard.WizardPage#isPageComplete()
	 */
	@Override
	public boolean isPageComplete() {
		// TODO Auto-generated method stub
		/*Get the wizard*/
		SimulatorCreatorDisplayer displayer = (SimulatorCreatorDisplayer)getWizard();
		/*Test if the file route is not null*/
		boolean complete = displayer.getSelectedFile()!=null;
		/*Test if there is a selected simulator ID*/
		complete = complete && displayer.getSelectedSimulator()!=null;
		/*Test if the P-system is not null*/
		complete = complete && displayer.getPsystem()!=null;
		/*Return the result*/
		return complete;
	}

	private static final String PAGE_NAME = "simulationFeatures";
	private  static final String PAGE_TITLE = "Select simulation features";
	private  static final String PAGE_DESCRIPTION = "Select the simulation type, its allowed operations and the file where it will be encoded in";
	
	private Button alternativeStepsCheckBox;
	private Text fileChooserText;

	/**
	 * Sets if the alternative steps check box is enabled
	 * @param enabled a boolean to set if the alternative steps check box is enabled
	 */
	public void setAlternativeStepsCheckBoxEnabled(boolean enabled) {
		this.alternativeStepsCheckBox.setEnabled(enabled);
		/*If the value can't be chosen, switch it to false*/
		if(enabled==false)
			alternativeStepsCheckBox.setSelection(false);
	}

	
	/**
	 * Creates a new {@link SimulatorFeaturesWizardPage} instance
	 */
	protected SimulatorFeaturesWizardPage(){
		super(PAGE_NAME);
		setTitle(PAGE_TITLE);
		setDescription(PAGE_DESCRIPTION);
		/*Set the simulator icon*/
		setImageDescriptor(PlinguaPlugin.getImageDescriptor(SIMULATOR_ICON));
	}










	/** Sets up the simulator features page by adding simulator parameters
	 * @param parent the composite to add the feature controls to
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
		createSimulatorParametersDisplayer(mainComposite);

	}
	
	private void createSimulatorParametersDisplayer(Composite mainComposite) {
		/*Create the simulator parameters composite*/
		Composite simulatorsIDsComposite = createSimulatorsIDsComposite(mainComposite);
		/*Create the simulator supported actions composite*/
		createSimulatorSupportedActionsComposite(mainComposite, simulatorsIDsComposite);
		/*Create the file chooser*/
		createSimulatorFileChooser(mainComposite);

		
	}

	private Composite createSimulatorFileChooser(
			Composite parentComposite) {
		
		/*Create the file chooser composite*/
		Composite simulatorFileChooserComposite = new Composite(parentComposite, SWT.NULL);
		simulatorFileChooserComposite.setLayout(new FormLayout());
		/*Create the file chooser composite attachments*/
		FormData simulatorFileChooserFormData = new FormData();
		simulatorFileChooserFormData.left = new FormAttachment(0, 0);
		simulatorFileChooserFormData.right = new FormAttachment(100, 0);
		simulatorFileChooserFormData.bottom = new FormAttachment(100, 0);
		simulatorFileChooserComposite.setLayoutData(simulatorFileChooserFormData);
				
		/*Create file chooser contents and get the resulting field*/
		fileChooserText = WizardComponents.createFileChooserText("Select file", simulatorFileChooserComposite, new FileListener(this));
		return simulatorFileChooserComposite;
		
		// TODO Auto-generated method stub
		
	}





	private Composite createSimulatorSupportedActionsComposite(
			Composite parentComposite, Composite leftComposite) {
		/*Create the simulator supported actions composite*/
		Composite simulatorsSupportedActionsComposite = new Composite(parentComposite, SWT.NULL);
		/*Set the simulator supported actions  layout*/
		simulatorsSupportedActionsComposite.setLayout(new RowLayout(SWT.VERTICAL));
		/*Set the simulator supported actions  attachments*/
		FormData simulatorsSupportedActionsFormData = new FormData();
		simulatorsSupportedActionsFormData.top = new FormAttachment(0, 0);
		//simulatorsSupportedActionsFormData.left = new FormAttachment(leftComposite, 0);
		simulatorsSupportedActionsFormData.right = new FormAttachment(100, 0);
		simulatorsSupportedActionsComposite.setLayoutData(simulatorsSupportedActionsFormData);
		/*Create the steps back check box*/
		Button stepsBackCheckBox = WizardComponents.createOptionRow(simulatorsSupportedActionsComposite, "Allow steps back                  ");
		stepsBackCheckBox.addSelectionListener(new StepsBackCheckBoxSelectionListener(stepsBackCheckBox, (SimulatorCreatorDisplayer)getWizard()));
		/*Create the alternative steps check box*/
		
		alternativeStepsCheckBox = WizardComponents.createOptionRow(simulatorsSupportedActionsComposite, "Allow alternative steps        ");
		alternativeStepsCheckBox.addSelectionListener(new AlternativeStepsCheckBoxSelectionListener(alternativeStepsCheckBox, (SimulatorCreatorDisplayer)getWizard()));
		alternativeStepsCheckBox.setEnabled(false);
		/*Create the open simulation console check box*/
		Button openSimulatorCheckBox = WizardComponents.createOptionRow(simulatorsSupportedActionsComposite, "Open simulation console    ");
		openSimulatorCheckBox.addSelectionListener(new OpenConsoleCheckBoxSelectionListener(openSimulatorCheckBox, (SimulatorCreatorDisplayer)getWizard()));		
		
		// TODO Auto-generated method stub
		return simulatorsSupportedActionsComposite;
	}
	
	



	private Composite createSimulatorsIDsComposite(
			Composite simulatorParametersComposite) {
		/*Create the simulator IDs composite*/
		Composite simulatorsIDsComposite = new Composite(simulatorParametersComposite, SWT.NULL);
		/*Create its attachments*/
		FormData simulatorsIDsFormData = new FormData();
		simulatorsIDsFormData.top = new FormAttachment(0, 0);
		simulatorsIDsFormData.left = new FormAttachment(0, 0);
		simulatorsIDsComposite.setLayoutData(simulatorsIDsFormData);
		/*Set its layout, so inner objects can attach to it*/
		simulatorsIDsComposite.setLayout(new FormLayout());
		createSimulatorsIDsList(simulatorsIDsComposite);
		// TODO Auto-generated method stub
		return simulatorsIDsComposite;
	}

	private void createSimulatorsIDsList(Composite simulatorsIDsComposite) {
		/*Get the P-system*/
		Psystem psystem = ((SimulatorCreatorDisplayer)getWizard()).getPsystem();
		/*Get the parser controller*/
		PsystemController parserController = ((SimulatorCreatorDisplayer)getWizard()).getPsystemController();
		/*Create the simulators IDs List*/
		WizardComponents.createListIDsContent(simulatorsIDsComposite, "Available simulators", new ArrayContentProvider(), new SimulatorIDsLabelProvider(), new SimulatorListListener((SimulatorCreatorDisplayer)getWizard(), this), parserController.getAvailableSimulators(psystem));
	}
	
	/**
	 * Sets the file route given as argument within its field
	 * @param fileRoute the file route to set. In case it's null, it will be replaced by an empty string
	 */
	public void setFileRoute(String fileRoute){
		String fileToSet= fileRoute;
		/*Set the wizard simulator file route*/
		SimulatorCreatorDisplayer displayer = (SimulatorCreatorDisplayer)getWizard();
		displayer.setSelectedFile(fileToSet);
		/*Set the simulator file route text field*/
		if(fileRoute==null)
			fileToSet = "";		
		fileChooserText.setText(fileToSet);
		/*Update page completition*/
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
		SimulatorCreatorDisplayer displayer = (SimulatorCreatorDisplayer)getWizard();
		return (displayer.getSelectedSimulator()!=null)&&(displayer.getSelectedFile()!=null);
	}

}
