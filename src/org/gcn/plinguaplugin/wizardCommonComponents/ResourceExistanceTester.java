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

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;

/**
 * This class tests if the resources given as arguments to its methods exist, and reports its type
 * @author Manuel Garcia-Quismondo-Fernandez
 *
 */
public class ResourceExistanceTester {
	/**
	 * Test if the resource indicated by the route passed as argument exists and is a container
	 * @param route the route to test its existance
	 * @return true if the container already exists, false otherwise
	 */
	
	public static boolean testContainer(String route){
		
		IResource resource = getResource(route);
		/*Otherwise, report an error*/
		return (resource!=null)&&(resource.exists()) && (resource instanceof IContainer);
	}
	
	/**
	 * Test if the resource indicated by the route passed as argument exists
	 * @param route the route to test its existance
	 * @return true if the resource already exists, false otherwise
	 */
	
	public static boolean testExistance(String route){
		IResource resource = getResource(route);
		return (resource!=null)&&(resource.exists());
	}
	
	
	/**
	 * A method for obtaining a resource from a route
	 */
	private static IResource getResource(String resource){
		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		return root.findMember(new Path(resource));
		
	}
	
	/**
	 * Tests if a project and a package exist and are containers, but a file inside the package does not exist
	 * @param page the wizard page to report errors to
	 * @param project the project route to test its existance
	 * @param filePackage the package route to test its existance
	 * @param file the file to test its existance
	 * @return true if the project and the package exist and are containers, but the file does not exist
	 * 
	 */
	public static boolean testResources(WizardPage page, String project, String filePackage, String file){
		/*If the project does not exist, report it*/
		if(!ResourceExistanceTester.testContainer(project)){
			page.setErrorMessage("The project "+project+" does not exist or is not a container");
			return false;
		}
		/*If the package does not exist, report it*/
		if(!ResourceExistanceTester.testContainer(project+"/"+filePackage)){
			page.setErrorMessage("The package "+project+"/"+filePackage+" does not exist or is not a container");
			return false;
		}
		/*If the file already exists, report it*/		
		if(ResourceExistanceTester.testExistance(project+"/"+filePackage+"/"+file)){
			page.setErrorMessage("The file "+project+"/"+filePackage+"/"+file+" already exists");
			return false;
		}
		return true;
	}

}
