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

package org.gcn.plinguaplugin.wizardCommonComponents;

import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * This class provides common functionality for wizards and dialogs used within the plugin
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class WizardComponents {
	/**
	 * Creates a composite based on a list and a top label describing the list
	 * @param composite the parent composite of the composite to create
	 * @param description the top label text to describe the list content
	 * @param contentProvider the content provider for the list within the composite
	 * @param labelProvider the label provider for the list within the composite
	 * @param listener the listener which defines the action triggered when the selection has changed within the list
	 * @param input the list input
	 */
	public static void createListIDsContent(Composite composite, String description, IContentProvider contentProvider, ILabelProvider labelProvider, ISelectionChangedListener listener, Object input) {
		/*Create the IDs Label*/
		Label simulatorIDsLabel = new Label(composite, SWT.NULL);

		simulatorIDsLabel.setText(description);
		/*Create the IDs Label Attachments*/
		FormData labelFormData = new FormData();
		labelFormData.top = new FormAttachment(0);
		labelFormData.left = new FormAttachment(0);
		labelFormData.right = new FormAttachment(100);		
		simulatorIDsLabel.setLayoutData(labelFormData);

		/*Create the list viewers composite*/
		Composite listViewerComposite = new Composite(composite, SWT.NULL);
		listViewerComposite.setLayout(new FillLayout());
		/*Create the list viewers attachments*/
		FormData viewerFormData = new FormData();
		viewerFormData.top = new FormAttachment(simulatorIDsLabel, 10);
		viewerFormData.left = new FormAttachment(0);
		viewerFormData.right = new FormAttachment(100);	
		viewerFormData.bottom = new FormAttachment(100);		
		listViewerComposite.setLayoutData(viewerFormData);
		/*Create the list viewers*/
		ListViewer idsList = new ListViewer(listViewerComposite, SWT.BORDER|SWT.V_SCROLL|SWT.H_SCROLL);
		
		/*Set its providers*/
		idsList.setContentProvider(contentProvider);
		idsList.setLabelProvider(labelProvider);
		/*Set its behavior*/
		idsList.addSelectionChangedListener(listener);
		/*Set the list viewer input*/
		idsList.setInput(input);
		
	}

	
	/**
	 * Creates a row to contain a check box to represent an option
	 * @param optionComposite the row parent
	 * @param optionDescription a description for the option
	 * @return the check box created within the row
	 */
	public static  Button createOptionRow(
			Composite optionComposite, String optionDescription) {
		/*Create the action row*/
		Composite actionComposite = new Composite(optionComposite, SWT.NULL);
		actionComposite.setLayout(new RowLayout(SWT.HORIZONTAL));
		/*Create the check box*/
		Button checkBox = new Button(actionComposite, SWT.CHECK);
		/*Create the action label*/
		Label actionLabel = new Label(actionComposite, SWT.NULL);
		actionLabel.setText(optionDescription);
		/*Return the check box*/
		return checkBox;
		
	}
	

	
	/**
	 * Creates a file chooser composite which contains a file chooser
	 * @param label A text describing the selected file
	 * @param parent the parent composite to the file chooser composite
	 * @param fileListener the selection listener to be added to the text field
	 * @return the text field contained in the composite
	 */
	public static Text createFileChooserText(String label, Composite parent, SelectionListener fileListener){
		Text fileChooserText=null;
		/*Create the file chooser label*/
		Label fileChooserLabel = new Label(parent, SWT.NULL);
		fileChooserLabel.setText(label+"  ");
		createFileChooserAttachments(fileChooserLabel, null);
		
		/*Create the file chooser text field*/
		fileChooserText = new Text(parent, SWT.BORDER);
		fileChooserText.setEditable(false);
		FormData fileChooserTextFormData = createFileChooserAttachments(fileChooserText, fileChooserLabel);
		
		/*Create the file chooser select file*/
		Button fileChooserButton = new Button(parent, SWT.NULL);
		fileChooserButton.setText("Browse...");
		FormData fileChooserButtonFormData = createFileChooserAttachments(fileChooserButton, fileChooserText);
		fileChooserButton.addSelectionListener(fileListener/*new FileListener(this)*/);
		fileChooserButtonFormData.left = null;
		fileChooserButtonFormData.right = new FormAttachment(100);
		fileChooserTextFormData.right = new FormAttachment(fileChooserButton);
		return fileChooserText;
	}
	
	private static FormData createFileChooserAttachments(Control fileChooserWidget, Control leftControl) {
		/*Create the attachments*/
		FormData widgetFormData = new FormData();
		//widgetFormData.top = new FormAttachment(0, 3);
		widgetFormData.bottom = new FormAttachment(100, -3);
		/*If it's the first widget, attach it to the left bound*/
		if(leftControl==null)
			widgetFormData.left = new FormAttachment(0, 0);
		/*Otherwise, attach it to its left control*/
		else
			widgetFormData.left = new FormAttachment(leftControl, 0);
		fileChooserWidget.setLayoutData(widgetFormData);
		return widgetFormData;
		
	}
	
	
	/**
	 * Creates a composite which can be stacked one upon the other
	 * @param parent the parent composite to the composite to create
	 * @param upperComposite the composite located upon the composite to create
	 * @return the composite created to be stacked
	 */
	public static Composite createStackedComposite(Composite parent,
			Composite upperComposite){
		Composite textFieldComposite = new Composite(parent, SWT.NULL);
		textFieldComposite.setLayout(new FormLayout());
		/*Create the attachments*/
		FormData textFieldCompositeFormData = new FormData();
		if(upperComposite!=null)
			textFieldCompositeFormData.top = new FormAttachment(upperComposite, 10);
		else
			textFieldCompositeFormData.top = new FormAttachment(0, 10);
		textFieldCompositeFormData.left = new FormAttachment(0, 0);
		textFieldCompositeFormData.right = new FormAttachment(100, 0);
		textFieldComposite.setLayoutData(textFieldCompositeFormData);
		/*Return the text field composite*/
		return textFieldComposite;
		
	}
	
	/**
	 * Creates a text field inside a composite
	 * @param composite the composite to contain the text field
	 * @param labelText the label which refers to the text field to create
	 * @return the text field within the composite
	 */
	public static Text createTextContents(
			Composite composite, String labelText){
		return createTextContents(composite, labelText, 0);
	}

	/**
	 * Creates a text field inside a composite
	 * @param textComposite the composite to contain the text field
	 * @param labelText the label which refers to the text field to create
	 * @param offSet the offset to be left on the left side
	 * @return the text field within the composite
	 */
	public static Text createTextContents(
			Composite textComposite, String labelText, int offSet) {
		/*Create the label*/
		Label contentsLabel = new Label(textComposite, SWT.NULL);
		/*Set the label text*/
		contentsLabel.setText(labelText);
		/*Create the label attachment*/
		FormData contentsLabelFormData = new FormData();		
		contentsLabelFormData.top = new FormAttachment(0, 0);
		contentsLabelFormData.bottom = new FormAttachment(100, 0);
		contentsLabelFormData.left = new FormAttachment(0, 0);
		contentsLabel.setLayoutData(contentsLabelFormData);
		/*Create the text field*/
		Text textField = new Text(textComposite, SWT.SINGLE|SWT.BORDER);
		textField.setEnabled(true);
		/*Create the text field attachment*/
		FormData textFieldFormData = new FormData();		
		textFieldFormData.top = new FormAttachment(0, 0);
		textFieldFormData.bottom = new FormAttachment(100, 0);
		textFieldFormData.left = new FormAttachment(contentsLabel, offSet+40);
		textFieldFormData.right = new FormAttachment(100, -40);
		textField.setLayoutData(textFieldFormData);

		/*Return the text field*/
		return textField;
	}
	


}
