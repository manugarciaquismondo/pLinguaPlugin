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

import org.eclipse.swt.widgets.Button;
/**
 * This abstract class describes a check box selection behavior which consists of enabling and disabling if the simulator will support alternative steps
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class AlternativeStepsCheckBoxSelectionListener extends
		CheckBoxSelectionListener {
	/**
	 * Creates a new {@link AlternativeStepsCheckBoxSelectionListener} which describes a check box selection behavior which consists of enabling and disabling if the simulator will support alternative steps 
	 * @param checkBox the check box this instances describes a behavior for
	 * @param displayer the {@link SimulatorCreatorDisplayer} the check box belongs to
	 */
	public AlternativeStepsCheckBoxSelectionListener(Button checkBox,
			SimulatorCreatorDisplayer displayer) {
		super(checkBox, displayer);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void setValue(boolean arg0) {
		getSimulatorCreatorDisplayer().setAlternativeSteps(arg0);
		// TODO Auto-generated method stub

	}

}
