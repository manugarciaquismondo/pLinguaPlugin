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

package org.gcn.plinguaplugin.simulator;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.gcn.plinguacore.simulator.ISimulator;
import org.gcn.plinguaplugin.configurationinterface.IConfigurationPanel;
import org.gcn.plinguaplugin.configurationinterface.IConfigurationPanelFactory;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.controller.SimulatorController;

/**
 * This class displays a user interface for simulating P-systems. It takes into account if the specific simulator can perform steps back and/or alternative steps.
 * It also takes care if the simulation has number of steps and/or time limit, and provides an intuitive interface for loading and saving simulators.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class SimulatorDisplayer {
		
	
	private boolean isDisplayed;

	
	private IConfigurationPanel configurationPanel;
	private Thread currentThreadSimulator;
	private PsystemController parserController;
	


	 


	
	private static final String DEFAULT_TITLE = "Simulator console";
	
	private ISimulator currentSimulator;
	
	private boolean dirtySimulator;
	
	/**
	 * Gets the current simulator of the simulator displayer
	 * @return the current simulator of the simulator displayer
	 */
	public ISimulator getCurrentSimulator() {
		return currentSimulator;
	}

	/** Gets the {@link PsystemController} instance to use pLinguaCore functionality
	 * @return  the the {@link PsystemController} instance of the simulator displayer
	 */
	public PsystemController getPsystemController(){
		return parserController;
	}
	
	/**
	 * Sets the current simulator of the simulator displayer. It's supposed to be called by org.gcn.plinguaplugin.simulator inner classes
	 * @param currentSimulator the simulator to be set
	 * @param fileRoute the file route of the simulator
	 */
	public void setCurrentSimulator(ISimulator currentSimulator, String fileRoute) {
		setCurrentSimulator(currentSimulator);
		setCurrentSimulatorRoute(fileRoute);
		/*Set the simulator as not dirty*/
		setDirty(false);
	}
	

	
	/**
	 * Sets the current simulator of the simulator displayer. It's supposed to be called by org.gcn.plinguaplugin.simulator inner classes
	 * @param currentSimulator the simulator to be set
	 * @param fileRoute the file route of the simulator
	 */
	private void setCurrentSimulator(ISimulator currentSimulator) {
		if (currentSimulator == null)
			throw new NullPointerException(
					"The current simulator shouldn't be null");
		this.currentSimulator = currentSimulator;	
		/*Enable the common features*/
		enableCommonFeatures();
		/*Enable the specific features, in case they can be used*/
		setSelectedFeatures(currentSimulator.supportsStepBack(), currentSimulator.supportsAlternateSteps());
		/*Set the current configuration*/
		currentConfigText.setText(currentSimulator.getCurrentConfig().getNumber()+"");
		/*Set step back button*/
		switchStepBack();
	}





	private String currentSimulatorRoute;
	private Shell mainShell;
	private Button limitStepsCheckBox;
	private Text limitStepsText;
	private Button timeOutCheckBox;
	private Text timeOutText;
	private Display display;
	private final Point SHELL_PREFERRED_DIMENSIONS = new Point(380, 400);
	private Text messageBoxText;
	private Text currentProcessText;
	/**
	 * Sets the message box text to report the last problem
	 * @param text the problem description
	 */
	public void setMessageBoxText(String text) {
		messageBoxText.setText(text);
	}
	/**
	 * Clears the message box text
	 */
	public void clearMesageBoxText(){
		messageBoxText.setText("");

	}
	
	/**
	 * Sets the title of the shell
	 * @param title the title of the shell
	 */
	public void setTitle(String title){
		mainShell.setText(title);
	}
	
	/**
	 * Clears the current process field text
	 */
	public void clearCurrentProcessText(){
		currentProcessText.setText("");
	}

	/**
	 * Sets the current process field text
	 * @param text the text to be set
	 */
	public void setCurrentProcessText(String text){
		currentProcessText.setText(text);
	}





	private Button alternativeStepButton;
	private Button stepBackButton;
	private Button clearPreviousStepsButton;
	private Button showCurrentConfigurationButton;
	private Button runButton;
	private Button pauseButton;
	private Button stepForwardButton;
	private Button resetButton;
	private MenuItem openFileItem;
	private MenuItem saveItem;
	private MenuItem saveAsItem;
	private Text currentConfigText;

	private SimulatorSaver simulatorSaver;

	private MenuItem fileMenuItem;
	
	/**
	 * Gets the simulator displayer shell
	 * @return the simulator displayer shell
	 */
	public Shell getShell(){
		return mainShell;
	}
	
	
	/**
	 * Enables those features (buttons, check box and text field) which are common for all simulators
	 */
	public void disableCommonFeatures(){
		switchCommonFeatures(false);
		setSelectedFeatures(false, false);		
		disableReset();
		setTitle(DEFAULT_TITLE);
	}
	/**
	 * Sets the selected features (step back and alternative steps) to enable or disable the proper buttons
	 * @param stepBack a boolean indicating if the simulator supports steps back
	 * @param alternativeStep a boolean indicating if the simulator supports alternative steps
	 */
	public void setSelectedFeatures(boolean stepBack, boolean alternativeStep){
		/*Update step back buttons*/
		updateStepBack(stepBack);
		/*Update alternative step buttons*/
		updateAlternativeStep(alternativeStep);
		
	}
	

	
	private void updateAlternativeStep(boolean alternativeStep) {
		alternativeStepButton.setEnabled(alternativeStep);
		// TODO Auto-generated method stub
		
	}


	private void updateStepBack(boolean stepBack) {
		stepBackButton.setEnabled(stepBack);
		clearPreviousStepsButton.setEnabled(stepBack);
		// TODO Auto-generated method stub
		
	}


	/**
	 * Enables those features (buttons, check box and text field) which are common for all simulators
	 */
	public void enableCommonFeatures(){
		switchCommonFeatures(true);
		setSelectedFeatures(SimulatorController.stepsBackAvailable(getCurrentSimulator()), SimulatorController.supportsAlternateSteps(getCurrentSimulator()));
		
	
		
	}
	
	private void switchCommonFeatures(boolean value){
		/*Those features are common for all simulators, it means, all simulators provide this functionality*/

		switchReset();
		switchStepBack();
		switchStepsForward();
		stepForwardButton.setEnabled(value);
		runButton.setEnabled(value);
		showCurrentConfigurationButton.setEnabled(value);
		limitStepsCheckBox.setEnabled(value);
		timeOutCheckBox.setEnabled(value);	
		limitStepsText.setEnabled(value);
		timeOutText.setEnabled(value);	
		/*These actions depend on if there's any simulator loaded or not*/
		saveItem.setEnabled(value);
		saveAsItem.setEnabled(value);
	}
	
	/**
	 * The constructor. Once instantiated, {@link SimulatorDisplayer} instances are ready to be displayed.
	 */
	public SimulatorDisplayer() {
		
		super();
		/*Create the common PlinguaController instance for every classes related to displaying simulators*/
		parserController = new PsystemController();
		display = Display.getDefault();
		mainShell = new Shell(display);
		mainShell.setMinimumSize(SHELL_PREFERRED_DIMENSIONS);
		mainShell.setSize(SHELL_PREFERRED_DIMENSIONS);
		mainShell.setLayout(new FormLayout());
		setDirty(false);
		simulatorSaver = new SimulatorSaver(this);
		intializeShell();

		
		// TODO Auto-generated constructor stub
	}


	
	
	private Composite createMainComposite(){
		/*Create composite form data*/
		FormData mainCompositeFormData = new FormData();
		mainCompositeFormData.top = new FormAttachment(0, 0);
		mainCompositeFormData.left = new FormAttachment(0, 0);
		mainCompositeFormData.bottom = new FormAttachment(100, 0);
		mainCompositeFormData.right = new FormAttachment(100, 0);
		/*Create main composite*/
		Composite mainComposite = new Composite(mainShell, 0);
		mainComposite.setLayoutData(mainCompositeFormData);
		/*Structure main composite*/
		mainComposite.setLayout(new FormLayout());
		//mainComposite.setBackground(new Color(display, 0, 0, 0));
		return mainComposite;
	}

	private void intializeShell() {
		/*Initialize the main interface structure. Delegate it on a composite so that it's not necessary to deal with shells*/
		Composite mainComposite = createMainComposite();
		
		/*Create the info box composite*/
		Composite infoBoxComposite = createInfoBoxComposite(mainComposite);
		/*Create the simulator button bars*/
		Composite bottomBarSimulatorButtons = createBottomBarSimulatorButtons(mainComposite, infoBoxComposite);
		/*Create the process info text*/
		createBottomProcessInfoText(mainComposite, bottomBarSimulatorButtons);
		/*Create the basic tool bar. So far, it will only consist of a "File" button*/
		createMenuItems();
		
		/*Create buttons and text events*/
		createListeners();
		// TODO Auto-generated method stub
		setTitle(DEFAULT_TITLE);

		setCurrentThreadSimulator(Thread.currentThread());
		setPauseButton(false);
		
		
	}



	private void createListeners() {
		/*Add all listeners to the displayer buttons*/
		/*Listeners related to file operations*/
		openFileItem.addSelectionListener(new OpenFileSelectionListener(this));
		saveItem.addSelectionListener(new SaveSelectionListener(this));
		saveAsItem.addSelectionListener(new SaveAsSelectionListener(this));
		/*Listeners related to simulation actions*/
		stepForwardButton.addSelectionListener(new StepForwardListener(this));
		runButton.addSelectionListener(new RunListener(this));
		stepBackButton.addSelectionListener(new StepBackListener(this));
		clearPreviousStepsButton.addSelectionListener(new ClearPreviousStepsListener(this));
		resetButton.addSelectionListener(new ResetListener(this));
		showCurrentConfigurationButton.addSelectionListener(new DisplayConfigurationListener(this));
		pauseButton.addSelectionListener(new PauseListener(this));
		/*Listeners related to number text fields*/
		CheckNumberTextListener checkNumberSteps = new CheckLimitStepTextListener(this, limitStepsText, limitStepsCheckBox);
		CheckNumberTextListener checkNumberTimeOut = new CheckTimeOutTextListener(this, timeOutText, timeOutCheckBox);
		limitStepsText.addModifyListener(checkNumberSteps);
		timeOutText.addModifyListener(checkNumberTimeOut);
		/*Listeners related to run limitations*/
		limitStepsCheckBox.addSelectionListener(new CheckBoxListener(limitStepsText, limitStepsCheckBox, this, checkNumberSteps));
		timeOutCheckBox.addSelectionListener(new CheckBoxListener(timeOutText, timeOutCheckBox, this, checkNumberTimeOut));
		/*Kill remaining threads*/
		mainShell.addDisposeListener(new CloseShellListener(this));
		
	}
	
	protected SimulatorSaver getSimulatorSaver(){
		return simulatorSaver;
	}




	private Composite createInfoBoxComposite(Composite mainComposite){
		/*Create the info box composite*/
		Composite infoBoxComposite = new Composite(mainComposite, 0);
		/*Attach the info box composite at the beginning of the main composite*/
		FormData infoBoxCompositeFormData = new FormData();
		infoBoxCompositeFormData.top = new FormAttachment(0, 0);
		infoBoxCompositeFormData.left = new FormAttachment(0, 0);
		infoBoxCompositeFormData.right = new FormAttachment(100, 0);
		infoBoxComposite.setLayoutData(infoBoxCompositeFormData);
		
		FillLayout infoBoxCompositeLayout = new FillLayout();
		//infoBoxCompositeLayout.marginHeight = -5;
		//infoBoxCompositeLayout.marginWidth = -5;
		infoBoxComposite.setLayout(infoBoxCompositeLayout);
		/*Create the message box within the info box composite*/
		currentProcessText = createMessageBox(infoBoxComposite);
		return infoBoxComposite;

	}




	private Text createMessageBox(Composite infoBoxComposite) {
		/*Create a message box to report messages related to this instance*/
		Text currentProcessText = new Text(infoBoxComposite, SWT.MULTI);
		currentProcessText.setEditable(false);
		return currentProcessText;
	}




	private void createBottomProcessInfoText(Composite mainComposite, Composite bottomBarButtons) {
		messageBoxText = new Text(mainComposite, SWT.SINGLE|SWT.BORDER);
		/*Create the process attachment to the main composite*/
		FormData currentProcessTextFormData = new FormData();
		currentProcessTextFormData.top = new FormAttachment(bottomBarButtons, 0);
		currentProcessTextFormData.bottom = new FormAttachment(100, 0);
		currentProcessTextFormData.left = new FormAttachment(0, 0);
		currentProcessTextFormData.right = new FormAttachment(100, 0);
		messageBoxText.setLayoutData(currentProcessTextFormData);
		((FormData)bottomBarButtons.getLayoutData()).bottom = new FormAttachment(messageBoxText);
		/*As it's only supposed to be modified by the Simulator Displayer, it shouldn't be editable*/
		messageBoxText.setEditable(false);
		
		
	}




	private Composite createBottomBarSimulatorButtons(Composite mainComposite, Composite topComposite) {
		Composite simulatorComposite = new Composite(mainComposite, SWT.BORDER);
		//simulatorComposite.setText("Simulation Actions");
		
		
		/*Create the simulator group layout data*/
		FormData simulatorCompositeFormData = new FormData();
		simulatorCompositeFormData.top = new FormAttachment(topComposite, 0);
		simulatorCompositeFormData.left = new FormAttachment(0, 0);
		simulatorCompositeFormData.right = new FormAttachment(100, 0);
		simulatorComposite.setLayoutData(simulatorCompositeFormData);		


		/*Create the simulator group layout*/
		RowLayout simulatorCompositeLayout = new RowLayout(SWT.VERTICAL);
		simulatorCompositeLayout.spacing = 0;
		simulatorCompositeLayout.pack = false;
		simulatorCompositeLayout.justify = false;
		simulatorCompositeLayout.fill = true;
		simulatorComposite.setLayout(new FormLayout());

		/*Fill in the simulator group*/
		Composite alternativeStepsActionsComposite = createAlternativeStepsActions(simulatorComposite, null);
		Composite stepBackActionsComposite = createStepBackActions(simulatorComposite, alternativeStepsActionsComposite);		
		Composite basicActionsComposite = createBasicActions(simulatorComposite, stepBackActionsComposite);
		Composite parametersComposite = createSimulationParameters(simulatorComposite, basicActionsComposite);
		createSimulatorInfo(simulatorComposite, parametersComposite);
		return simulatorComposite;
		

		

		

		
	}

	private Composite createAlternativeStepsActions(
			Composite simulatorComposite, Composite anchorComposite) {
		/*Create a new group child of simulator group, its children will be horizontally aligned*/
		Composite alternativeStepsActionComposite = createSpecificSimulatorActionsComposite(simulatorComposite, anchorComposite, "Alternative steps simulator actions");
		
		/*Add all buttons related to simulator-specific functionality*/
		alternativeStepButton = createButton(alternativeStepsActionComposite, "Alternative step", null);
	
		
		return alternativeStepsActionComposite;
	}


	private Composite createSimulatorInfo(Composite simulatorGroup, Composite anchorComposite) {
		Composite simulatorInfoComposite = createSpecificSimulatorActionsComposite(simulatorGroup, anchorComposite, null);

		/*Create the simulator info box title*/
		Label infoLabel = createSimulatorInnerLabel(simulatorInfoComposite, "Simulator info");
		/*Create the simulator info box content*/
		createSimulatorInfoLowerRow(infoLabel, simulatorInfoComposite);
		
		

		return simulatorInfoComposite;
		
	}




	private void createSimulatorInfoLowerRow(Label infoLabel,
			Composite simulatorInfoComposite) {
		// TODO Auto-generated method stub
		/*Create the simulator info lower row*/
		Composite lowerRowComposite = new Composite(simulatorInfoComposite, 0);
		lowerRowComposite.setLayout(new FormLayout());
		/*attach the lower row to the lower bound of the info label*/
		FormData lowerRowFormData = new FormData();
		lowerRowFormData.top = new FormAttachment(infoLabel, 0);
		lowerRowFormData.bottom = new FormAttachment(100, 0);
		lowerRowFormData.left = new FormAttachment(0, 0);
		lowerRowFormData.right = new FormAttachment(100, 0);
		lowerRowComposite.setLayoutData(lowerRowFormData);
		
		
		/*Create a field text to display the current configuration number*/
		Label currentConfigLabel = new Label(lowerRowComposite, 0);
		currentConfigLabel.setText("Current configuration:");
		
		/*Create the current configuration number label attachments*/
		
		attachInfoPart(currentConfigLabel, null, false);

		
		/*Create the current configuration text*/
		currentConfigText = new Text(lowerRowComposite, SWT.SINGLE|SWT.BORDER);		
		currentConfigText.setText("000");
		currentConfigText.setEditable(false);
		

		
		
		/*Create the current configuration text attachments*/
		attachInfoPart(currentConfigText, currentConfigLabel, false);
		
		/*Create a button for displaying the simulator graphic representation*/
		showCurrentConfigurationButton = new Button(lowerRowComposite, SWT.PUSH);
		showCurrentConfigurationButton.setText("Show current configuration");
		
		/*Create the show current configuration button attachments*/
		attachInfoPart(showCurrentConfigurationButton, currentConfigText, false);
	}
	
	private void attachInfoPart(Control infoControl, Control leftAttachment, boolean right){
		FormData infoFormData = new FormData();
		if(!right){
			/*If the control shouldn't be attached to its parent's right side*/
			infoFormData.top = new FormAttachment(0);
			if(leftAttachment==null)
				/*If there's no control on the control left, attach the control to its parent's left side*/
				infoFormData.left = new FormAttachment(0);
			else
				/*Otherwise, attach the control to the control on the left's left side*/
				infoFormData.left = new FormAttachment(leftAttachment);
		}
		else{

			/*Otherwise, attach the control to its parent's right side*/
			infoFormData.bottom = new FormAttachment(100);
			infoFormData.left = new FormAttachment(leftAttachment);
			infoFormData.right = new FormAttachment(100);
		}			
		infoControl.setLayoutData(infoFormData);
	}
	
	




	private Composite createSimulationParameters(Composite simulatorComposite, Composite anchorComposite) {
		Composite simulationParameters =  createSpecificSimulatorActionsComposite(simulatorComposite, SWT.VERTICAL, anchorComposite, null, 60);
		/*Create simulation parameters label*/
		Label simulationParametersLabel = createSimulatorInnerLabel(simulationParameters, "Simulator parameters");
		
		/*Create the group related to steps limitation*/

		
		
		Composite limitStepsStructure = createLimitStepsStructure(simulationParameters, simulationParametersLabel);
		/*Create the group related to time out limitation*/
		createLimitTimeOutStructure(simulationParameters, limitStepsStructure);
		return simulationParameters;
		
	}
	
	private Label createSimulatorInnerLabel(Composite simulationParameters, String labelText){
		/*Create the label on the parameters composite*/
		Label simulationParametersLabel = new Label(simulationParameters, 0);
		simulationParametersLabel.setText(labelText);
		/*Attach the label within the parameters composite*/
		FormData simulationParameterslabelFormData = new FormData();
		simulationParameterslabelFormData.top = new FormAttachment(0, 0);
		simulationParameterslabelFormData.left = new FormAttachment(0, 0);
		return simulationParametersLabel;
	}



	private Composite createParametersComposite(Composite simulationParameters,  Control anchorComposite){
		Composite parametersComposite = new Composite(simulationParameters, 0);
		/*Create the layout data for the parameters composite (composed of a check box, a label and a text field)*/
		FormData parametersCompositeLayoutData = new FormData();
		parametersCompositeLayoutData.left= new FormAttachment(0, 0);
		parametersCompositeLayoutData.right= new FormAttachment(100, 0);
		if(anchorComposite==null)
			parametersCompositeLayoutData.top= new FormAttachment(0, 0);
		else
			parametersCompositeLayoutData.top = new FormAttachment(anchorComposite, 0);
		
		/*Assign the layout data to the parameters composite*/
		parametersComposite.setLayoutData(parametersCompositeLayoutData);
		parametersComposite.setLayout(new RowLayout(SWT.HORIZONTAL));


		return parametersComposite;
	}
	

	private Composite createLimitTimeOutStructure(Composite simulationParameters, Composite anchorComposite) {
		/*Create the limit steps composite*/
		Composite limitStepsComposite = createParametersComposite(simulationParameters, anchorComposite);
		/*It will be composed of several elements (a check button, a label and a text field)*/

		/*Create the check button*/
		timeOutCheckBox = new Button(limitStepsComposite, SWT.CHECK);
		timeOutCheckBox.setEnabled(true);
		timeOutCheckBox.setSelection(false);
		/*Create the label*/

		createParameterLabel(limitStepsComposite, "Time out", " (in milliseconds)    ");
		/*Create the limit steps text field*/
		timeOutText = new Text(limitStepsComposite, SWT.SINGLE|SWT.BORDER);
		timeOutText.setEditable(false);
		/*By default, the maximum time out is 100 milliseconds*/
		timeOutText.setText("00100");
		return limitStepsComposite;

		
	}




	private void createParameterLabel(Composite simulationParameters, String text, String posfix) {
		Label parameterLabel = new Label(simulationParameters, 0);
		parameterLabel.setText(text+" limit"+posfix);
		
	}




	private Composite createLimitStepsStructure(Composite simulationParameters, Label simulationParametersLabel) {
		/*Create the limit steps composite*/
		Composite limitStepsComposite = createParametersComposite(simulationParameters, simulationParametersLabel);
		/*It will be composed of several elements (a check button, a label and a text field)*/
                           
		/*Create the check button*/
		limitStepsCheckBox = new Button(limitStepsComposite, SWT.CHECK);
		limitStepsCheckBox.setEnabled(true);
		limitStepsCheckBox.setSelection(false);
		/*Create the label*/
		createParameterLabel(limitStepsComposite, "Steps", "                             ");
		
		/*Create the limit steps text field*/
		limitStepsText = new Text(limitStepsComposite, SWT.SINGLE|SWT.BORDER);
		limitStepsText.setEditable(false);
		/*By default, the maximum number of steps is 1*/
		limitStepsText.setText("00001");
		return limitStepsComposite;

		
	}




	private Composite createStepBackActions(Composite simulatorComposite, Composite anchorComposite) {
		/*Create a new group child of simulator group, its children will be horizontally aligned*/
		Composite stepBackActionsComposite = createSpecificSimulatorActionsComposite(simulatorComposite, anchorComposite, "Advanced simulator actions");
		
		/*Add all buttons related to simulator-specific functionality*/
		
		stepBackButton = createButton(stepBackActionsComposite, "Step Back", null);
		clearPreviousStepsButton = createButton(stepBackActionsComposite, "Clear previous steps", stepBackButton);
		
		return stepBackActionsComposite;
	}


	private Composite createSpecificSimulatorActionsComposite(Composite simulatorComposite, Composite anchorComposite, String compositeName){
		
		return createSpecificSimulatorActionsComposite(simulatorComposite, SWT.HORIZONTAL, anchorComposite, compositeName);
	}

	private Composite createSpecificSimulatorActionsComposite(Composite simulatorComposite, int flag, Composite anchorComposite, String compositeName){
		return createSpecificSimulatorActionsComposite(simulatorComposite, flag, anchorComposite, compositeName, 30);
	}
	
	private Composite createSpecificSimulatorActionsComposite(Composite simulatorComposite, int flag, Composite anchorComposite, String compositeName, int labelSpacing){
		
		/*Create a new group which will be part of simulatorGroup*/
		Composite specificSimulatorActionsComposite = new Composite(simulatorComposite,	SWT.BORDER);
		/*Create the form data for specificSimulatorActionsGroup*/	
		/*If the composite has a name, create its label*/
		if(compositeName!=null){
			Label compositeNameLabel = new Label(specificSimulatorActionsComposite, SWT.SINGLE);
			/*Create the name of the composite*/
			compositeNameLabel.setText(compositeName);
			FormData compositeNameLabelFormData = new FormData();
			compositeNameLabelFormData.top = new FormAttachment(0 ,0);
			compositeNameLabelFormData.left = new FormAttachment(0 ,0);
			compositeNameLabelFormData.bottom = new FormAttachment(100 ,-labelSpacing);
			compositeNameLabel.setLayoutData(compositeNameLabelFormData);
		}
		/*Create the composite general attachments*/
		FormData simulatorActionsFormData = new FormData();
		simulatorActionsFormData.left = new FormAttachment(0, 0);
		simulatorActionsFormData.right = new FormAttachment(100, 0);
		/*In case it's the first composite, attach the composite to the bottom*/
		if(anchorComposite==null)
			simulatorActionsFormData.bottom = new FormAttachment(100, 0);
		/*Otherwise, attach it to its neighbor underneath*/
		else
			simulatorActionsFormData.bottom = new FormAttachment(anchorComposite, 0);
		
		specificSimulatorActionsComposite.setLayoutData(simulatorActionsFormData);
		/*Create the layout for the group*/
		
		specificSimulatorActionsComposite.setLayout(new FormLayout());
		return specificSimulatorActionsComposite;
	}
	

	private Composite createBasicActions(Composite simulatorComposite,Composite anchorComposite) {
		
		
		/*Create basic actions group*/
		Composite basicActionsComposite = createSpecificSimulatorActionsComposite(simulatorComposite, anchorComposite, "Basic simulator actions");				
		/*Create basic actions buttons*/
		//createButton(basicActionsGroup, "Show Configuration");
		pauseButton = createButton(basicActionsComposite, "Pause", null);
		runButton = createButton(basicActionsComposite, "Run", pauseButton);		
		stepForwardButton = createButton(basicActionsComposite, "Step forward", runButton);
		resetButton = createButton(basicActionsComposite, "Reset", stepForwardButton);
		
		
		/*Return the group*/
		return basicActionsComposite;
		
	}




	private Button createButton(Composite simulatorComposite, String text, Button rightAnchorButton) {
		/*Create a push button for simulator actions*/
		Button button = new Button(simulatorComposite, SWT.PUSH);
		button.setText(text);
		FormData buttonFormData = new FormData();
		buttonFormData.bottom = new FormAttachment(100, 0);
		/*If there is no right button, attach it to the right bound*/
		if(rightAnchorButton==null)
			buttonFormData.right = new FormAttachment(100, 0);
		else
			/*Otherwise, attach it to the right button*/
			buttonFormData.right = new FormAttachment(rightAnchorButton, -10);
		button.setLayoutData(buttonFormData);
		return button;
		
	}




	private void createMenuItems(/*ToolBar toolBar*/) {

		createFileMenuItems(/*toolBar*/);
		// TODO Auto-generated method stub
		
	}

	
	private MenuItem createMenuItem(String text, int accelerator, Menu menu){
		/*A menu item is a push button*/
		MenuItem menuItem = new MenuItem(menu, SWT.PUSH);
		menuItem.setText(text);
		/*The accelerator indicates the key shortcut to start the menu item action*/
		menuItem.setAccelerator(accelerator);
		return menuItem;
	}

	private void createFileMenuItems(/*ToolBar toolBar*/) {
		
		/*Create File button*/
		Menu mainMenu = new Menu(mainShell, SWT.BAR);
		fileMenuItem = new MenuItem(mainMenu, SWT.CASCADE);
		fileMenuItem.setText("&File");	
		
		/*Create file menu*/
		Menu fileMenu = createFileMenu();
		fileMenuItem.setMenu(fileMenu);
		
		
		mainShell.setMenuBar(mainMenu);
		// TODO Auto-generated method stub
		
	}


	private Menu createFileMenu() {
		Menu menu = new Menu(mainShell, SWT.DROP_DOWN);
		/*Create Open button*/		
		openFileItem = createMenuItem("&Open File...\tCTRL+O", SWT.CTRL+'O', menu);
		/*Create Save button*/		
		saveItem = createMenuItem("&Save\tCTRL+S", SWT.CTRL+'S', menu);
		/*Create Save As button*/		
		saveAsItem = createMenuItem("Save &As...\tCTRL+A", SWT.CTRL+'A', menu);
		// TODO Auto-generated method stub
		return menu;
	}
	
	



	private void openShell(){
	      mainShell.open();
	     mainShell.setEnabled(true);
	      /*Display shellDisplay = mainShell.getDisplay();
	      while (!mainShell.isDisposed()) {
	         if (!shellDisplay.readAndDispatch()) shellDisplay.sleep();
	      }*/
	}

	
	/**
	 * Executes the display and shows the simulator user interface
	 */
	public void display(){
		 /*At the beginning, no option is available*/
		  isDisplayed=true;
		  disableCommonFeatures();
		  setSelectedFeatures(false, false);
		  /*Display the console*/
		  openShell();
		  isDisplayed=false;

	}
	
	/**
	 * Sets if the step back button is enabled according to if there are steps back available
	 */
	public void switchStepBack(){
		/*Get the simulator*/
		ISimulator simulator = getCurrentSimulator();
		boolean available=false;
		/*If there's no simulator, the step back isn't available*/
		if(simulator!=null)
			/*Otherwise, it depends on the simulator*/
			 available = SimulatorController.stepsBackAvailable(simulator);
		this.stepBackButton.setEnabled(available);
		this.clearPreviousStepsButton.setEnabled(available);
	}


/**
 * Sets the file route where the current simulator will be saved
 * @param currentSimulatorRoute the route to be set
 */
 
	private void setCurrentSimulatorRoute(String currentSimulatorRoute) {
		if (currentSimulatorRoute == null)
			throw new NullPointerException(
					"the current simulator route argument shouldn't be null");
		this.currentSimulatorRoute = currentSimulatorRoute;
		/*Set shell text as the current simulator file route*/
		resetTitle();
	}

	/**
	 * Gets the file route where the current simulator is saved
	 * @return the file route where the current simulator is saved
	 */
	 
	public String getCurrentSimulatorRoute() {
		return currentSimulatorRoute;
	}


	 /** Sets if the simulator is dirty, it means, does not correspond with the one on the file loaded
	 * @param dirty the boolean to set if the simulator is dirty.
	 */
	public void setDirty(boolean dirty) {
		this.dirtySimulator = dirty;
	}


	/**
	 * Gets if the simulator is dirty, it means, does not correspond with the one on the file loaded
	 * @return true if the simulator is dirty, false otherwise.
	 */
	public boolean isDirty() {
		return dirtySimulator;
	}
	
	
	/**
	 * Clears the simulator contents (simulator and file route)
	 */
	public void clearSimulator(){
		/*Clear all parameters within the simulator displayer*/
		this.currentConfigText.setText("");
		this.currentSimulator = null;
		this.currentSimulatorRoute = null;
		/* Set the simulator as clear, as there is no simulator*/
		this.setDirty(false);
		setPauseButton(false);
	}
	
	


	/**
	 * Gets the maximum number of steps for simulations in case they are limited, otherwise returns -1
	 * @return the maximum number of steps for simulations in case they are limited, otherwise returns -1
	 */
	public int limitedSteps(){
		if (limitStepsCheckBox.getSelection())
			return Integer.parseInt(limitStepsText.getText());
		return -1;
	}
	/**
	 * Gets the time out for simulations in case they are limited, otherwise returns -1
	 * @return the time out for simulations in case they are limited, otherwise returns -1
	 */
	public long limitedTimeOut(){
		if (timeOutCheckBox.getSelection())
			return Long.parseLong(timeOutText.getText());
		return -1;
	}

	/**
	 * Updates the configuration text field by using the one of the current simulator
	 */
	public void updateConfigurationNumber(){
		/*If the configuration is currently displayed*/
		if((configurationPanel!=null)&&(configurationPanel.isDisplayed())){
			/*Get the configuration panel current position to set it again later*/
			Point position = configurationPanel.getPosition();
			configurationPanel.dispose();
			/*Dispose the configuration panel*/
			configurationPanel = IConfigurationPanelFactory.createConfigurationPanel(SimulatorController.getCurrentConfig(getCurrentSimulator()));
			/*Update the configuration panel*/
			configurationPanel.setPosition(position.x, position.y);
			configurationPanel.display();
		}
		
		currentConfigText.setText(Integer.toString(SimulatorController.getCurrentConfig(getCurrentSimulator()).getNumber()));
	}
	/**
	 * Sets if the file menu is enabled
	 * @param enabled a boolean representing if the file menu button is enabled
	 */
	public void switchFileMenu(boolean enabled){
		fileMenuItem.setEnabled(enabled);
		
	}

	
	/**
	 * Switch the reset button according to the current simulator configuration
	 * In case there's no current simulator or the current simulator configuration is the initial one, the reset button is disabled
	 * Otherwise, the reset button is enabled 
	 */
	public void switchReset(){
		resetButton.setEnabled(!isInitialConfig());
	}
	
	private boolean isInitialConfig(){
		return (getCurrentSimulator()==null)||(SimulatorController.isInitialConfig(getCurrentSimulator()));
	}
	
	/**
	 * Sets if the pause button is enabled
	 * @param enabled a boolean representing if the pause button is enabled
	 */
	public void setPauseButton(boolean enabled){
		pauseButton.setEnabled(enabled);
		pauseButton.setText("Pause");
	}

	/**
	 * Sets the thread in which the current simulation takes place
	 * @param currentThreadSimulator the thread in which the current simulation takes place
	 */
	public void setCurrentThreadSimulator(Thread currentThreadSimulator) {
		this.currentThreadSimulator = currentThreadSimulator;
	}

	/**
	 * Gets the thread in which the current simulation takes place
	 * @return the thread in which the current simulation takes place
	 */
	public Thread getCurrentThreadSimulator() {
		return currentThreadSimulator;
	}

	/**
	 * Sets the current configuration panel
	 * @param configurationPanel the current configuration panel to set 
	 */
	public void setConfigurationPanel(IConfigurationPanel configurationPanel) {
		this.configurationPanel = configurationPanel;
	}

	/**
	 * Disposes the current configuration panel if there is any
	 */
	public void disposeConfigurationPanel() {
		if(configurationPanel!=null)
			configurationPanel.dispose();
	}
	/**
	 * Gets if this {@link SimulatorDisplayer} instance is currently displayed
	 * @return true if this {@link SimulatorDisplayer} instance is currently displayed, false otherwise
	 */
	public boolean isDisplayed(){
		return isDisplayed;
	}
	/**
	 * Resets the console title with the simulator name
	 */
	public void resetTitle(){
		/*If there is a simulator selected, update the title with its route*/
		if(currentSimulator!=null&&currentSimulatorRoute!=null)
			mainShell.setText(DEFAULT_TITLE+" - "+currentSimulatorRoute);
	}
	
	/**
	 * Disables the reset button
	 */
	private void disableReset(){
		resetButton.setEnabled(false);
	}
	
	/**
	 * Switch the step forward and run buttons according to the current simulator configuration
	 * In case there's no current simulator or the current simulator has finished, the buttons above are disabled
	 * Otherwise, the buttons above are enabled 
	 */
	public void switchStepsForward(){
		stepForwardButton.setEnabled(stepsForwardAvailable());
		runButton.setEnabled(stepsForwardAvailable());
		
	}

	private boolean stepsForwardAvailable() {
		// TODO Auto-generated method stub
		return (getCurrentSimulator()!=null)&&(!SimulatorController.isFinished(getCurrentSimulator()));
	}
}
