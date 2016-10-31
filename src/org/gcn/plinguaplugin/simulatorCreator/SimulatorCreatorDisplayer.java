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

import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.IWizardPage;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.gcn.plinguacore.util.psystem.Psystem;
import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.controller.PsystemController;
import org.gcn.plinguaplugin.wizardCommonComponents.FileCreator;

/**
 * This class provides an user interface for creating simulators for P-systems
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class SimulatorCreatorDisplayer extends Wizard  implements INewWizard{
	

	private static final String TITLE = "New Simulation";
	private String psystemName;
	private Psystem psystem;
	private boolean allowStepsBack;
	private boolean allowAlternativeSteps;
	private boolean openSimulationConsole;
	private String simulatorID;
	private PsystemController psystemController;
	private IStructuredSelection selection;
	private String simulatorRoute;

	/**
	 * Gets the {@link PsystemController} instance used by this instance
	 * @return the {@link PsystemController} instance used by this instance
	 */
	public PsystemController getPsystemController(){
		return psystemController;
	}
	
	/**
	 * Creates a new {@link SimulatorCreatorDisplayer} instance which creates a simulator for the {@link Psystem} instance given as argument
	 * @param psystem the {@link Psystem} instance to provide a simulator for
	 * @param name the name of the P-System to create a simulator for
	 */
	public SimulatorCreatorDisplayer(Psystem psystem, String name) {
		super();
		if (psystem == null)
			throw new NullPointerException("psystem argument shouldn't be null");
		this.setPsystem(psystem);
		this.setPsystemName(name);
		psystemController = new PsystemController();
		simulatorID=null;
		this.allowStepsBack = false;
		this.allowAlternativeSteps = false;
	}
	
	

	/**
	 * @see org.eclipse.jface.wizard.Wizard#addPages()
	 */
	@Override
	public void addPages() {
		/*Set the title*/
		setWindowTitle(TITLE +" - "+getPsystemName());

		/*Add the pages*/
		IWizardPage simWizardPage = new SimulatorFeaturesWizardPage(); 
		addPage(simWizardPage);

	}

	/**
	 * Sets if the simulator to create can execute steps back
	 * @param stepsBack the boolean representing if the simulator to create can execute steps back
	 */
	public void setStepsBack(boolean stepsBack){
		this.allowStepsBack = stepsBack;
	}
	/**
	 * Sets if the simulator to create can execute alternative steps
	 * @param alternativeSteps the boolean representing if the simulator to create can execute alternative steps
	 */
	public void setAlternativeSteps(boolean alternativeSteps){
		this.allowAlternativeSteps = alternativeSteps;
	}

	/**
	 * Creates the simulator for the {@link Psystem} instance according to the user interface parameters
	 */
	@Override
	public boolean performFinish() {
		/*Create the file creation operation*/
		IRunnableWithProgress op = new CreateSimulatorRunnable(this, allowStepsBack, allowAlternativeSteps, openSimulationConsole);
		/*Execute the file creation operation, by delegating it on wizardComponents class*/
		FileCreator.executeContainerAction(this, op, getShell());
		if(getPsystemController().errorsFound())
			PlinguaLog.logError(psystemController.getReportMessage(), psystemController.getLatestException());
		return true;
		

	}
	
	/**
	 * Sets if a simulation console will be shown when the simulator is created
	 * @param openSimulationConsole a boolean representing if a simulation console will be shown when the simulator is created
	 */
	public void setOpenSimulationConsole(boolean openSimulationConsole){
		this.openSimulationConsole= openSimulationConsole;
	}
	
	/**
	 * Gets a boolean representing if a simulation console will be shown when the simulator is created
	 * @return true if a simulation console will be shown when the simulator is created, false otherwise
	 */
	public boolean getOpenSimulationConsole(){
		return openSimulationConsole;
	}



	/**
	 * @see org.eclipse.ui.IWorkbenchWizard#init(org.eclipse.ui.IWorkbench, org.eclipse.jface.viewers.IStructuredSelection)
	 */
	@Override
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		this.selection = selection;
		addPages();
		
	}



	/**
	 * Sets the {@link Psystem} instance for this instance
	 * @param psystem the {@link Psystem} instance to be set
	 */
	public void setPsystem(Psystem psystem) {
		this.psystem = psystem;
	}



	/**
	 * Gets the {@link Psystem} instance
	 * @return the{@link Psystem} instance
	 */
	public Psystem getPsystem() {
		return psystem;
	}



	/**
	 * Sets the ID of the simulator to create
	 * @param simulatorID the ID of the simulator to create
	 */
	public void setSelectedSimulator(String simulatorID) {
		this.simulatorID = simulatorID;
		/*If the simulator doesn't support alternative steps, don't allow it*/
		if(!psystemController.supportsAlternativeStep(psystem, simulatorID))
			allowAlternativeSteps = false;

	}
	
	/**
	 * Gets the ID of the simulator to create
	 * @return the ID of the simulator to create
	 */
	public String getSelectedSimulator(){
		return simulatorID;
	}

	
	/**
	 * Gets the route of the file where the simulator file will be created
	 * @return the route of the file where the simulator file will be created
	 */
	public String getSelectedFile(){
		return simulatorRoute;
	}

	
	/**
	 * Sets the route of the file where the simulator file will be created
	 * @param simulatorRoute the route of the file where the simulator will be created
	 */
	public void setSelectedFile(String simulatorRoute){
		this.simulatorRoute = simulatorRoute;
		
	}

	/**
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish() {
		// TODO Auto-generated method stub
		return specificCondition();
	}
	
	private boolean specificCondition(){
		return (simulatorRoute!=null)&&(simulatorID!=null);
	}
	/**
	 * Reports if there were an error while creating the simulator file
	 * @param route the file route where the simulator were expected to be created
	 * @param exception the exception launched as a result of the file creation
	 */
	protected void reportError(String route, Exception exception){
		/*Create message*/
		String message = "The file "+route+" couldn't be created";
		/*Report problem in the log*/
		PlinguaLog.logError(message, exception);
		/*Report errors in a message box*/
		MessageBox messageBox = new MessageBox(getShell(), SWT.ERROR|SWT.OK);
		messageBox.setText("Error creating file");
		messageBox.setMessage(message);
		messageBox.open();
		
		
	}

	/**
	 * Sets the file name where the original P-system is encoded
	 * @param psystemName the file name where the original P-system is encoded
	 */
	public void setPsystemName(String psystemName) {
		this.psystemName = psystemName;
	}

	/**
	 * Gets the file name where the original P-system is encoded
	 * @return the file name where the original P-system is encoded
	 */
	public String getPsystemName() {
		return psystemName;
	}
	



	

}
