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


package org.gcn.plinguaplugin;

import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import org.gcn.plinguaplugin.PlinguaPlugin;


/**
 * This class provides a plug-in log to register all possible errors and messages during P-Lingua Plugin execution
 * @author Manuel Garcia-Quismondo-Fernandez 
 */
 
public class PlinguaLog {

	/**
	 * Creates a new info log report
	 * @param message the message of the report
	 */
	public static void logInfo(String message) {
		log(IStatus.INFO, IStatus.OK, message, null);

	}

	/**
	 * Creates a new error log report, which reports an exception
	 * @param exception the exception which caused the error
	 */
	public static void logError(Throwable exception) {
		logError("Unexpected Exception", exception);

	}

	/**
	 *
	 * Creates a new error log report, which reports an exception
	 * @param message The message of the report
	 * @param exception the exception which caused the error
	 */
	public static void logError(String message, Throwable exception) {
		log(IStatus.ERROR, IStatus.OK, message, exception);
	}

	/**
	 * Creates a new log report, whose severity and category depend on the arguments given
	 * @param severity the severity of the report, for example, error severity
	 * @param code the code of the report specifying its category
	 * @param message the message of the report
	 * @param exception the exception which caused the error
	 */
	public static void log(int severity, int code, String message,
			Throwable exception) {
		log(createStatus(severity, code, message, exception));
	}

	/**
	 * Creates a status report according to the parameters given
	 * @param severity the severity of the report, for example, error severity
	 * @param code the code of the report specifying its category
	 * @param message the message of the report
	 * @param exception the exception which caused the error
	 * @return an {@link IStatus} instance representing the report
	 */
	public static IStatus createStatus(int severity, int code, String message,
			Throwable exception) {
		return new Status(severity, PlinguaPlugin.PLUGIN_ID, code, message,
				exception);
	}

	/**
	 * Reports a log report based on a status instance
	 * @param status the status to create the report
	 */
	public static void log(IStatus status) {
		PlinguaPlugin plugin = PlinguaPlugin.getDefault();
		ILog log = plugin.getLog();
		log.log(status);
	}

}