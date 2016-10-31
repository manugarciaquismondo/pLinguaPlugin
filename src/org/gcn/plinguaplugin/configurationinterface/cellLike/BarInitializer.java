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


package org.gcn.plinguaplugin.configurationinterface.cellLike;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ScrollBar;


/**
 * This class performs the set-up actions for composite widgets which should be scrolled to have partial views of them
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class BarInitializer {
	private ScrollBar horizontalBar;
	private ScrollBar verticalBar;
	private Composite scrolled;
	
	/**
	 * Creates a new BarInitializer capable of starting up all features of the object to scroll
	 * @param scrolled the object the BarInitializer should set up
	 */
	public BarInitializer(Composite scrolled) {
		super();
		this.scrolled = scrolled;
		/*First, we get the scroll bars*/
		horizontalBar  = scrolled.getParent().getHorizontalBar();
		verticalBar = scrolled.getParent().getVerticalBar();
		/*Then, each bar is added a new Listener, so that when they're moved the displayed view is updated*/
		horizontalBar.addListener(SWT.Selection, new BarListener(horizontalBar, scrolled, false));	
		verticalBar.addListener(SWT.Selection, new BarListener(verticalBar, scrolled, true));
	}
	
	/**
	 * Sets up the horizontal and vertical bars so they are eventually capable of displaying the view part required
	 * @param skinMembrane the skinMembrane which bars are set up
	 */
	public void setupBars(Composite skinMembrane){
		/*First, the row of contained membranes are obtained*/
		Composite rowComposite = (Composite)skinMembrane.getData("row");
		int verticalMaximum=0;
		/*In case the membrane contains any row*/
		if(rowComposite!=null){
			/*The horizontal bar maximum is set according to the row bounds*/
			Rectangle compositeBounds = rowComposite.getBounds();
			/*The vertical maximum still needs to check the multiset because its the summing of both heights (the contained membrane set and the multiset)*/
			verticalMaximum = compositeBounds.height*3/4;
			horizontalBar.setMaximum(compositeBounds.width*3/4);
		}
		/*Aftermath, the next step to take is to obtain the skin multiset*/
		Composite multiSetSpan = (Composite)skinMembrane.getData("multiset");
		/*In case there are any objects within the multiset*/
		if(multiSetSpan!=null){			
			
			Rectangle multiSetBounds = multiSetSpan.getBounds();
			/*The vertical maximum is increased with the multiset vertical bounds*/
			verticalMaximum+=multiSetBounds.height;
			/*If the multiset horizontal bound overcomes the membranes horizontal bound, the scroll bar maximum should be updated*/
			if(multiSetBounds.width*3/4>horizontalBar.getMaximum())
				horizontalBar.setMaximum(multiSetBounds.width*3/4);
		}
		/*In case there's a multiset or a contained membrane set to display, the vertical maximum is eventually set*/
		if(verticalMaximum!=0)
			verticalBar.setMaximum(verticalMaximum);
		int horizontalMaximum = horizontalBar.getMaximum();
		/*Now it's time to attach the scroll bars to the composite where skinMembrane is contained*/
		FormData scrolledData = (FormData)scrolled.getLayoutData();
		if(scrolledData!=null){
			/*We leave a small horizontal and vertical margin*/
			scrolledData.bottom = new FormAttachment(100, verticalMaximum-10);
			scrolledData.right = new FormAttachment(100, horizontalMaximum-10);			
		}

	}
	
	

}
