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

import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;

/**
 * This class checks if the text within a text field defines a number greater than 0. The format number should be Integer.
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class CheckLimitStepTextListener extends CheckNumberTextListener {
	/**
	 * Creates a new {@link CheckLimitStepTextListener} instance which test a text field
	 * @param simulatorDisplayer the {@link SimulatorDisplayer} instance the field text belongs to
	 * @param checkedText the text field to test
	 * @param checkBox the check box which enables or disables the text field
	 */
	public CheckLimitStepTextListener(SimulatorDisplayer simulatorDisplayer,
			Text checkedText, Button checkBox) {
		super(simulatorDisplayer, checkedText, checkBox);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected boolean checkSpecificType(String arg0) {
		// TODO Auto-generated method stub
		try{
			return Integer.parseInt(arg0)>0;
		}
		catch(NumberFormatException num){return false;}
		
	}

	@Override
	protected String getSpecificText() {
		// TODO Auto-generated method stub
		return "limit of steps";
	}

}
