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
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolTip;
import org.eclipse.swt.widgets.Shell;

/**
 * This class shows the tool tip about detaching a membrane composite when the mouse scrolls over the composite
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
class MembraneMenuListener implements MouseTrackListener {

	private ToolTip toolTip;
	private Shell shell;
	private Composite membraneComposite;
	
	/**
	 * Creates a new MembraneMenuListener instance of the membrane composite passed as parameter
	 * @param shell the shell where the membrane composite is shown
	 * @param membraneComposite the membrane composite which should be notified it's capable of being detached, when possible
	 */
	public MembraneMenuListener(Shell shell, Composite membraneComposite) {
		super();
		if (shell == null)
			throw new NullPointerException("shell argument shouldn't be null");
		this.shell = shell;
		if (membraneComposite == null)
			throw new NullPointerException(
					"membraneComposite argument shouldn't be null");
		this.membraneComposite = membraneComposite;
		/*The tool tip to be shown when the mouse hovers on the composite is created*/
		toolTip = new ToolTip(this.shell, SWT.ICON_INFORMATION);
		Rectangle shellBounds = this.shell.getBounds();
		/*The tool tip should be located on the bottom-left corner*/
		toolTip.setLocation(shellBounds.x+2, shellBounds.y+shellBounds.height-40);
		/*It should report about detaching the membrane composite*/
		toolTip.setText("For a detached view of this membrane, double-click on it");
	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseEnter(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseEnter(MouseEvent e) {
		/*First, we need to know if the membrane composite is already displayed in a detached view*/
		String displayed = (String) membraneComposite.getData("displayed");
		/*In such case, we don't show the tool tip*/
		if (displayed!=null&&displayed.equals("yes"))return;
		/*Otherwise, the tool tip is shown*/
		toolTip.setVisible(true);
		toolTip.setAutoHide(true);
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseExit(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseExit(MouseEvent e) {
		/*If the tool tip was shown now it's hidden*/
		if(toolTip!=null&&(!toolTip.isDisposed()))
			toolTip.setVisible(false);
		
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.eclipse.swt.events.MouseTrackListener#mouseHover(org.eclipse.swt.events.MouseEvent)
	 */
	@Override
	public void mouseHover(MouseEvent e) {
	}

}
