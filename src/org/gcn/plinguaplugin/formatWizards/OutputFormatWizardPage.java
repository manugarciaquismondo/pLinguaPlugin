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

/**
 * This class implements the page for {@link OutputFormatWizard} instances related to translation parameters when translating from files within the current workspace. It provides no additional controls, as the inherited ones are enough.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class OutputFormatWizardPage extends FormatTranslatorWizardPage {
	private static final String PAGE_NAME = "outputTranslationFeatures";
	private  static final String PAGE_TITLE = "Select destination file features";
	private  static final String PAGE_DESCRIPTION = "Select the destination file format and the P-system file route";
	
	protected OutputFormatWizardPage() {
		super(PAGE_NAME);
		setTitle(PAGE_TITLE);
		setDescription(PAGE_DESCRIPTION);
		
		// TODO Auto-generated constructor stub
	}
	@Override
	protected String getDirection(){ return "destination";}
}
