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

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;

/**
 * This class updates the option of preserving the original file format of the source file in a {@link FormatTranslatorWizard} instance file  
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class PreserveFormatListener implements SelectionListener {

	private FormatTranslatorWizardPage page;
	private Button checkBox;
	private Label label;
	/*the "preserve original label" message which reports if the file format will be preserved from the source file*/
	private final String PRESERVE_FORMAT = "The format of the file \nresulting of the \ntranslation will be set \nas the source file one.";
	/**
	 * Creates a new {@link PreserveFormatListener} instance which updates the original format preservation option based on a check box selection
	 * @param page the {@link FormatTranslatorWizardPage} instance where the check box is
	 * @param checkBox the check box which defines the original format preservation enablement
	 * @param label the label which indicates the effect of preserving or not the format.
	 */
	public PreserveFormatListener(FormatTranslatorWizardPage page, Button checkBox, Label label) {
		super();
		this.checkBox = checkBox;
		this.page = page;
		this.label = label;
	}

	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetDefaultSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * @see org.eclipse.swt.events.SelectionListener#widgetSelected(org.eclipse.swt.events.SelectionEvent)
	 */
	@Override
	public void widgetSelected(SelectionEvent e) {
		/*Set the "preserve original format" option according to the check box value*/
		page.setPreserveOriginalFormat(checkBox.getSelection());
		/*Update the "preserve original format" current message*/
		if(checkBox.getSelection()){
			label.setText(PRESERVE_FORMAT);
		}
		else
			label.setText("");
		
		
	}

}
