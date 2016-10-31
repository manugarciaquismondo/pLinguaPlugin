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

import org.eclipse.jface.wizard.Wizard;

/**
 * This class provides the common interface for wizards to translate P-systems among formats, that is, the common wizard user interface elements to import and export P-systems
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public abstract class FormatTranslatorWizard extends Wizard {

	/**
	 * Creates a new {@link FormatTranslatorWizard} instance which will translate a P-system into an output file
	 */
	public FormatTranslatorWizard() {
		super();
		/*Initialize the fields*/
		format="";
		externalFile="";
		preserveFormat=false;
	}
	
	/**
	 * @see org.eclipse.jface.wizard.Wizard#canFinish()
	 */
	@Override
	public boolean canFinish(){
		return isCorrect(getExternalFile())&&isCorrect(getWorkspaceFile())&&(preserveOriginalFormat()||isCorrect(getFormat()));
	}

	private String format;
	private String externalFile;
	private boolean preserveFormat;
	

	/**Tests if a string is correct*/
	protected boolean isCorrect(String text) {
		// TODO Auto-generated method stub
		return text!=null&&(!text.isEmpty());
	}


	/** Gets a boolean indicating if the original format should be preserved
	 * @return a boolean indicating if the original format should be preserved
	 */
	public boolean preserveOriginalFormat(){
		return preserveFormat;
	}
	
	/**
	 * Sets if the original format should be preserved 
	 * @param preserve a boolean indicating if the original format should be preserved
	 */
	public void setPreserveOriginalFormat(boolean preserve) {
		this.preserveFormat = preserve;
	}
	
	/**
	 * Gets the format which the resulting file should be encoded on
	 * @return the format which the resulting file should be encoded on
	 */
	public String getFormat(){
		return this.format;
		
	}
	
	/**
	 * Sets the format which the destination file should be encoded on
	 * @param format the format which the destination file should be encoded on
	 */
	public void setFormat(String format){
		if (format == null)
			throw new NullPointerException("Format argument shouldn't be null");
		this.format = format;
	}
	
	/**
	 * Gets the file related to format translation which can be out of the current workspace. If it's the destination file or the source file depends on the specific child class
	 * @return the file related to format translation which can be out of the current workspace
	 */
	public String getExternalFile(){
		return externalFile;
	}
	
	/**
	 * Sets the file related to format translation which can be out of the current workspace. If it's the destination file or the source file depends on the specific child class
	 * @param file the file related to format translation which can be out of the current workspace
	 */
	public void setExternalFile(String file){
		if (file == null)
			throw new NullPointerException("the external file argument shouldn't be null");
		this.externalFile = file;
	}



	
	/**
	 * Gets the file related to format translation which needs to be into the current workspace. If it's the destination file or the source file depends on the specific child class
	 * @return the file related to format translation which needs to be into the current workspace
	 */
	public abstract String getWorkspaceFile();

	
	
}
