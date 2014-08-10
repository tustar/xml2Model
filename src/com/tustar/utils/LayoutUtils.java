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
 * 
 * @author tustar (tustar1030@gmail.com)
 * @version 1.3.0
 */

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class LayoutUtils {

	/**
	 * Android id prefix in layout xml
	 */
	private static final String ANDROID_ID = "@android:id/";
	/**
	 * All id prefix
	 */
	private static final String[] ID_PREFS = new String[] { "@+id/", "@id/",
			"@android:id/" };
	/**
	 * include component.
	 */
	public static final String INCLUDE = "include";
	/**
	 * 
	 */
	public static final String VIEWSTUB = "ViewStub";
	/**
	 * Container component.
	 */
	public static final String[] CONTAINER_LAYOUTS = new String[] {
			"LinearLayout", "RelativeLayout", "GridLayout", "FrameLayout",
			"Fragment", "TableLayout", "TableRow", "Space", "ScrollView",
			"android.support.v4.app.FragmentTabHost", "TabWidget", "RadioGroup" };
	public static final String[] NO_TRANS_LAYOUTS = new String[] {};

	/**
	 * Get id in R$id.java by android:id="@+id|@id|@android:id/yyy"
	 * 
	 * @param id
	 *            The id in layout "@+id|@id|@android:id/yyy"
	 * @return R.id.yyy
	 */
	public static String getIdInR(String id) {

		for (String pref : ID_PREFS) {
			if (id.startsWith(pref)) {
				return id.substring(pref.length());
			}
		}

		return "";
	}

	/**
	 * home_splash => HomeSplash
	 * 
	 * @param id
	 *            home_splash
	 * @return HomeSplash
	 */
	public static String cameId(String id) {

		if (id == null || id.length() == 0) {
			return null;
		}

		String newStr = null;
		newStr = getIdInR(id);
		String[] names = newStr.split("_");
		StringBuffer componentName = new StringBuffer();
		for (String name : names) {
			componentName.append(camelcase(name));
		}
		return componentName.toString();
	}

	/**
	 * Upper word first letter. <b>eg.</b> hello => Hello
	 * 
	 * @param word
	 *            hello
	 * @return Hello
	 */
	public static String camelcase(String word) {
		return word.substring(0, 1).toUpperCase() + word.substring(1);
	}

	public static boolean isAndroidId(String id) {
		return id.startsWith(ANDROID_ID);
	}

	/**
	 * Translate xml name to class name. <b>eg.</b> activity_main.xml =>
	 * NSActivityMain
	 * 
	 * @param prefix
	 *            NS
	 * @param xmlName
	 *            activity_main.xml
	 * @return NSActivityMain
	 */
	public static String xmlName2ClassName(String prefix, String xmlName) {

		String layoutId = rmXmlSuffix(xmlName);
		String[] words = layoutId.split("_");
		StringBuilder sb = new StringBuilder(prefix);
		for (String word : words) {
			sb.append(camelcase(word));
		}
		return sb.toString();
	}

	/**
	 * Remove name xml suffix. <b>eg.</b> activity_main.xml => activity_main
	 * 
	 * @param xmlName
	 *            activity_main.xml
	 * @return activity_main
	 */
	public static String rmXmlSuffix(String xmlName) {
		String layoutId = xmlName.replace(".xml", "");
		return layoutId;
	}

	/**
	 * Get stubview layout name. <b>eg.</b> layout="@layout/include_layout" =>
	 * include_layout.xml
	 * 
	 * @param value
	 *            layout="@layout/include_layout"
	 * @return include_layout.xml
	 */
	public static String getStubLayout(String value) {

		String xmlName = "";
		xmlName = value.substring("@layout/".length()) + ".xml";
		return xmlName;
	}

	/**
	 * Judge the layout is need to translate to model or not.
	 * 
	 * @param xmlName
	 *            The layout xml name.
	 * @return True - Don't translate. False - Translate
	 */
	public static boolean isNoTransLayout(String xmlName) {

		if (NO_TRANS_LAYOUTS == null || NO_TRANS_LAYOUTS.length == 0) {
			return false;
		}

		for (String condition : NO_TRANS_LAYOUTS) {
			if (xmlName.startsWith(condition)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Judge the component is include component or not.
	 * 
	 * @param type
	 *            The component type.
	 * @return True - Include component, False - Not include component.
	 */
	public static boolean isIncludeLayout(String type) {

		return type.equals(INCLUDE);
	}

	/**
	 * Judge the component is viewstub component or not.
	 * 
	 * @param type
	 *            The component type.
	 * @return True - stub component, False - Not stub component.
	 */
	public static boolean isViewStubLayout(String type) {

		return type.equals(VIEWSTUB);
	}

	/**
	 * Judge the component is container component or not.
	 * 
	 * @param type
	 *            The component type.
	 * @return True - container component, False - Not container component.
	 */
	public static boolean isContainerLayout(String type) {

		List<String> containers = Arrays.asList(CONTAINER_LAYOUTS);
		return containers.contains(type);
	}
}
