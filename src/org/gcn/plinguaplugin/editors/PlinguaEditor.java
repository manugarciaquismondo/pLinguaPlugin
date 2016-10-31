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

package org.gcn.plinguaplugin.editors;


import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.ide.IGotoMarker;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.MultiPageEditorPart;
import org.eclipse.ui.texteditor.ITextEditor;

import org.gcn.plinguaplugin.PlinguaLog;
import org.gcn.plinguaplugin.editors.PlinguaEditorContributor;



/**
 * An example showing how to create a multi-page editor.
 * This example has 1 page, but it's expected to be added another page for showing a structured view of the P-system
 * <ul>
 * <li>page 0 contains a nested text editor.
 * </ul>
 */
public class PlinguaEditor extends MultiPageEditorPart implements IResourceChangeListener{

	

	private TextEditor textEditor;
	/**
	 * Creates a multi-page editor example.
	 */
	
	public PlinguaEditor() {
		super();
		/*We need to notice the editor will change with plugins changes*/
		ResourcesPlugin.getWorkspace().addResourceChangeListener(this);
	}

	/**
	 * Creates the pages of the multi-page editor.
	 */
	@Override
	protected void createPages() {
		/*createPages is a main method which calls several hook methods*/
		createSourcePage();
		updateTitle();
		//createActions();
	}
	
	/**
	 * Gets the source code editor
	 * @return the source code editor
	 */
	public ITextEditor getSourceEditor() {
		return textEditor;
	}

	
	void updateTitle() {
		IEditorInput input = getEditorInput();
		setPartName(input.getName());
		setTitleToolTip(input.getToolTipText());
	}
	
	void createSourcePage() {
		try {
			/*The editor is created*/
			textEditor = new TextEditor();
			/*Then, it's assigned an index (by default, it's 0)*/
			int index = addPage(textEditor, getEditorInput());
			/*Finally, we need to report that page 0 corresponds to the source code*/
			setPageText(index, "Source");
		} catch (PartInitException e) {
			PlinguaLog.logError("Error creating nested text editor", e);
		}
	}
	
	@Override
	protected void handlePropertyChange(int propertyId) {
		if (propertyId == IEditorPart.PROP_DIRTY)
		super.handlePropertyChange(propertyId);
	}
	/**
	 * The <code>MultiPageEditorPart</code> implementation of this 
	 * <code>IWorkbenchPart</code> method disposes all nested editors.
	 * Subclasses may extend.
	 */
	@Override
	public void dispose() {
		ResourcesPlugin.getWorkspace().removeResourceChangeListener(this);
		super.dispose();
	}
	/**
	 * Saves the multi-page editor's document.
	 */
	@Override
	public void doSave(IProgressMonitor monitor) {
		/*The action is delegated to the text editor (the delegated object), as stated in Delegator pattern*/
		textEditor.doSave(monitor);
	}
	/**
	 * Saves the multi-page editor's document as another file.
	 * Also updates the text for page 0's tab, and updates this multi-page editor's input
	 * to correspond to the nested editor's.
	 */
	@Override
	public void doSaveAs() {
		/*First, the action is delegated to the editor (the delegated object) as stated in Delegator pattern*/
		textEditor.doSaveAs();
		setInput(textEditor.getEditorInput());
		/*At last, we need to update the title to report the editor content is currently saved*/
		updateTitle();
	}
	
	/**
	 * Goes to a marker previously defined on the document
	 * @param marker the marker to go to within the editor
	 */
	public void gotoMarker(IMarker marker) {
		setActivePage(0);
		((IGotoMarker) textEditor.getAdapter(IGotoMarker.class))
				.gotoMarker(marker);
	}
	/**
	 * The <code>MultiPageEditorExample</code> implementation of this method
	 * checks that the input is an instance of <code>IFileEditorInput</code>.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput editorInput)
		throws PartInitException {
		/*The editor input should be a file editor input*/
		if (!(editorInput instanceof IFileEditorInput))
			throw new PartInitException("Invalid Input: Must be IFileEditorInput");
		super.init(site, editorInput);
	}
	
	/*
	 * Method declared on IEditorPart.
	 */
	/**
	 * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
	 */
	@Override
	public boolean isSaveAsAllowed() {
		/*Saving is always allowed in Psystem editor*/
		return true;
	}
	
	/**
	 * @see org.eclipse.ui.part.MultiPageEditorPart#setFocus()
	 */
	@Override
	public void setFocus() {
		switch (getActivePage()) {
		/*If page 0 is selected, we need to focus on the source code editor*/
		case 0:
			textEditor.setFocus();
			break;
	
		}
	}
	
	/**
	 * @see org.eclipse.ui.part.MultiPageEditorPart#isDirty()
	 */
	@Override
	public boolean isDirty() {
		return textEditor.isDirty();
	}
	/**
	 * Calculates the contents of page 2 (not currently implemented) when the it is activated.
	 */
	@Override
	protected void pageChange(int newPageIndex) {
		IEditorActionBarContributor contributor = getEditorSite()
				.getActionBarContributor();
		if (contributor instanceof PlinguaEditorContributor)
			((PlinguaEditorContributor) contributor).setActivePage(getActiveEditor());
	}
	/**
	 * Closes all project files on project close.
	 * @param event The event whose type might indicate to close the resource
	 */
	@Override
	public void resourceChanged(final IResourceChangeEvent event){
		if(event.getType() == IResourceChangeEvent.PRE_CLOSE){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
					IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
					/*For each page displayed*/
					for (int i = 0; i<pages.length; i++){
						/*If this page belongs to the project to close*/
						if(((FileEditorInput)textEditor.getEditorInput()).getFile().getProject().equals(event.getResource())){
							/*that page should be closed*/
							IEditorPart editorPart = pages[i].findEditor(textEditor.getEditorInput());
							pages[i].closeEditor(editorPart,true);
						}
					}
				}            
			});
		}
		if(!textEditor.getEditorInput().exists()){
			Display.getDefault().asyncExec(new Runnable(){
				public void run(){
				IWorkbenchPage[] pages = getSite().getWorkbenchWindow().getPages();
				/*For each page displayed*/
				for (int i = 0; i<pages.length; i++){
					IEditorPart editorPart = pages[i].findEditor(textEditor.getEditorInput());
					pages[i].closeEditor(editorPart,true);
					}
				}
			});
		}

	}
}
	


	

