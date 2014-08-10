/*******************************************************************************
 * Copyright 2011-2014 Tuxingping
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.tustar.utils;

/**
 * Log input.
 * 
 * @author tustar (tustar1030@gmail.com)
 * @version 1.3.0
 */
public class Log {

	public static boolean DEBUG = true;

	/**
	 * <b>System.out.println()</b>
	 * 
	 * @param tag
	 *            The class name
	 * @param msg
	 *            The input message
	 */
	public static void o(String tag, String msg) {
		o(tag, "", msg);
	}

	/**
	 * <b>System.out.println()</b>
	 * 
	 * @param tag
	 *            The class name
	 * @param method
	 *            The class method
	 * @param msg
	 *            The input message
	 */
	public static void o(String tag, String method, String msg) {
		if (DEBUG) {
			try {
				System.out.println(" " + tag + " ## " + method + "  " + msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <b>System.err.println()</b>
	 * 
	 * @param tag
	 *            The class name
	 * @param msg
	 *            The input message
	 */
	public static void e(String tag, String msg) {
		e(tag, "", msg);
	}

	/**
	 * <b>System.err.println()</b>
	 * 
	 * @param tag
	 *            The class name
	 * @param method
	 *            The class method
	 * @param msg
	 *            The input message
	 */
	public static void e(String tag, String method, String msg) {
		if (DEBUG) {
			try {
				System.err.println(" " + tag + " ## " + method + "  " + msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * <b>e.printStackTrace()</b>
	 * 
	 * @param e
	 *            The exception
	 */
	public static void t(Exception e) {
		if (DEBUG) {
			try {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
