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

/**
 * 
 */
package org.gcn.plinguaplugin.configurationinterface.cellLike;


import java.util.Stack;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeMembrane;
import org.gcn.plinguacore.util.psystem.cellLike.membrane.CellLikeSkinMembrane;



/**
 * This class performs the membrane composite detachment when this composite is double-clicked.
 * It only works in case the membrane composite hasn't already a detached view on a shell where the composite is framed on
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class MembraneMouseSubmitter extends MouseSubmitter {
	
	private Rectangle formerDimension;
	private Composite formerParent;
	private Display display;
	private Object formerLayoutData;
	private Composite displayerComposite;
	private Stack<Shell> closerShell;



	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseListener#mouseDoubleClick(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseDoubleClick(MouseEvent e) {
		/*If the composite membrane is already displayed in a detached view, we don't detach it*/
		String displayed = (String) membraneComposite.getData("displayed");
		if (displayed!=null&&displayed.equals("yes"))return;
		/*Otherwise, a new shell is create to display the membrane*/
		Shell shell = new Shell(display);
		/*This shell has a form layout*/
		shell.setLayout(new FormLayout());
		
		/*Now it's time to create the form data of the scrolling composite for the composite membrane, which fills all area available*/
		FormData scrollingData = new FormData();
		scrollingData.bottom = new FormAttachment(100, 0);
		scrollingData.top = new FormAttachment(0, 0);
		scrollingData.left = new FormAttachment(0, 0);
		scrollingData.right = new FormAttachment(100, 0);
		
		/*The scrolling composite is created to display the different views of the membrane composite*/
		Composite scrollingComposite = new Composite(shell, SWT.H_SCROLL|SWT.V_SCROLL);
		scrollingComposite.setLayout(new FormLayout());
		scrollingComposite.setLayoutData(scrollingData);
		/*The shell text claims the shell is showing membrane details, not a whole configuration*/
		shell.setText("Membrane Details");		
		shell.setBackground(new Color(display, 255, 255, 255));
		shell.setBounds(50, 50, 300, 300);
		/*The shell is tagged to report it's not the main one, but one only created to display a membrane composite*/
		shell.setData("secondaryShell");
		/*Before setting the new membrane composite parent we should store the former parent information so that when the shell for the composite membrane is closed the membrane composite can go back to its former state*/
		formerDimension = membraneComposite.getBounds();
		formerParent = membraneComposite.getParent();
		formerLayoutData = membraneComposite.getLayoutData();
		/*Then, the membrane composite layout data is created to fit the new shell*/
		FormData currentLayout = new FormData();
		currentLayout.top = new FormAttachment(0, 0);
		currentLayout.bottom = new FormAttachment(100, 0);
		currentLayout.left = new FormAttachment(0, 0);
		currentLayout.right = new FormAttachment(100, 0);
		membraneComposite.setLayoutData(currentLayout);
		/*Now we should disable the former membrane composite shell. For this purpose, we need to know if this shell is the main shell of the configuration or a shell created for showing a detached view of another membrane*/
		final String shellId = (String)membraneComposite.getShell().getData();
		/*In case the former shell is not the main shell, the entire shell should be disabled*/
		if(!shellId.equals("mainShell"))
			membraneComposite.getShell().setEnabled(false);
		/*Otherwise, only the membrane structure displayer should be disabled, not other parts of the cell-like membrane panel*/
		else		
			displayerComposite.setEnabled(false);
		/*Now the membrane composite parent is the scrolling composite, framed within the newly-created shell*/
		membraneComposite.setParent(scrollingComposite);
		/*We need to report the membrane composite is already displayed*/
		membraneComposite.setData("displayed", "yes");
		/*To be scrolled, a bar initializer for the membrane composite needs to be created*/
		BarInitializer barInitializer = new BarInitializer(membraneComposite);
		/*The former shell should be stacked onto the shell stack so that when the cell-like membrane the newly-created shell could be disposed*/
		closerShell.push(shell);
		/*The membrane of the membrane composite info is set as the shell header*/
		shell.setText(getMembraneInfo());
		/*When the newly-created shell is disposed the membrane composite former info should be set as it were before being detached*/
		shell.addDisposeListener(new DisposeListener(){

			@Override
			public void widgetDisposed(DisposeEvent e) {
				// TODO Auto-generated method stub
				if(formerParent.isDisposed()) return;
				membraneComposite.setParent(formerParent);
				membraneComposite.setBounds(formerDimension);
				/*The former shell should be enabled*/
				if(!shellId.equals("mainShell"))
					membraneComposite.getShell().setEnabled(true);
				else		
					displayerComposite.setEnabled(true);
				membraneComposite.setLayoutData(formerLayoutData);
				/*We also need to report the membrane composite is no longer shown in a detached view*/
				membraneComposite.setData("displayed", "no");
			}
			
		});
		shell.open();
		/*The bars need to be initialized*/
		barInitializer.setupBars(membraneComposite);
		/*Now the newly-created shell is opened*/
		shell.setEnabled(true);
		while(!shell.isDisposed()){
			if(!display.readAndDispatch()) display.sleep();
		}
		/*As the newly-created shell was the last to be stacked on the shell stack, it should be popped*/
		if(!closerShell.isEmpty())
			closerShell.pop();
		shell.dispose();

	}

	/*A method for obtaining the membrane of the composite info*/
	private String getMembraneInfo(){
		/*First, we get the membrane whose info will be displayed*/
		CellLikeMembrane membrane = (CellLikeMembrane) membraneComposite.getData("membrane");
		/*Then, we create a string to show the membrane id*/
		String result = "embrane Id: "+membrane.getId();
		/*If the membrane is the skin membrane, it should be reported*/
		if(membrane instanceof CellLikeSkinMembrane)
			result= "Skin m"+result;
		else
			result="M"+result;
		return result;
	}
	



	/**
	 * Creates a new MembraneMouseSubmitter to show a membrane composite in a detached view if possible
	 * @param membraneComposite the membrane composite to show in a detached view when double-clicked
	 * @param display the display of all shells
	 * @param panel the cell-like membrane panel which displays the configuration the membrane belongs to
	 * @param displayerComposite the scrolling composite to scroll through the membrane composite
	 * @param closerShell the shell where the membrane composite is currently shown
	 */
	public MembraneMouseSubmitter(Composite membraneComposite, Display display, CellLikeConfigurationPanel panel, Composite displayerComposite, Stack<Shell> closerShell) {
		super(panel, membraneComposite);
		if (membraneComposite == null)
			throw new NullPointerException(
					"membraneComposite argument shouldn't be null");
		this.membraneComposite = membraneComposite;
		if (display == null)
			throw new NullPointerException("display argument shouldn't be null");
		this.display = display;
		if (panel == null)
			throw new NullPointerException("panel argument shouldn't be null");
		this.panel = panel;
		if (displayerComposite == null)
			throw new NullPointerException(
					"displayerComposite argument shouldn't be null");
		this.displayerComposite = displayerComposite;
		if (closerShell == null)
			throw new NullPointerException(
					"closerShell argument shouldn't be null");
		this.closerShell = closerShell;
	}
	
	

}
