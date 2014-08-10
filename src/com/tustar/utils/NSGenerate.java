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
 * Translate xml layout to component.
 * 
 * @author tustar (tustar1030@gmail.com)
 * @version 1.3.0
 */

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class NSGenerate {
	/**
	 * TAG
	 */
	public static final String TAG = NSGenerate.class.getSimpleName();
	/**
	 * The class name prefix
	 */
	public static final String CLASS_NAME_PREFIX = "NS";
	/**
	 * The file name suffix
	 */
	public static final String FILE_NAME = CLASS_NAME_PREFIX + ".java";
	/**
	 * The project package name
	 */
	public static final String PACKAGE_NAME = "com.tustar.xmltomodel";
	/**
	 * The author
	 */
	public static final String AUTHOR = "tustar";
	/**
	 * The xml layout, normal is "res/layout",
	 */
	public static final String RES_LAYOUT = "res/layout";

	public static void main(String[] args) {

		StringBuffer content = new StringBuffer();
		// Add file comment.
		content.append(getHeaderJavadoc());
		// Add file package and import.
		content.append(getPackageAndImport());
		// Add public class
		content.append(getPublicInterface());
		// Get all generate xml files
		List<String> tranXmlList = filterLayouts(RES_LAYOUT);
		// Generate inner class
		content.append(xml2Class(tranXmlList));
		// Finish generate
		content.append("}\r\n ");
		// Generate CLASS_NAME_PREFIX.java
		writeFile(content, PACKAGE_NAME, FILE_NAME);
	}

	/**
	 * Get header and java document.
	 * 
	 * @return
	 */
	public static String getHeaderJavadoc() {
		StringBuffer comment = new StringBuffer();
		comment.append("/**\r\n");
		comment.append(" * @author " + AUTHOR + "\r\n");
		comment.append(" * @date " + new Date() + "\r\n");
		comment.append(" */\r\n\r\n");
		return comment.toString();
	}

	/**
	 * Get package name and imports
	 * 
	 * @return
	 */
	public static String getPackageAndImport() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("package " + PACKAGE_NAME + ";\r\n\r\n");
		buffer.append("import " + PACKAGE_NAME + ".R;\r\n");
		buffer.append("import android.os.*;\r\n");
		buffer.append("import android.view.*;\r\n");
		buffer.append("import android.widget.*;\r\n");
		// buffer.append("import android.webkit.*;\r\n");
		// buffer.append("import android.content.Context;\r\n");
		buffer.append("import android.app.*;\r\n");
		buffer.append("import android.support.v4.app.Fragment;\r\n");
		buffer.append("import android.support.v4.app.*;\r\n");
		// buffer.append("import android.widget.RelativeLayout.LayoutParams;\r\n");
		buffer.append("\r\n");

		return buffer.toString();
	}

	public static String getPublicInterface() {
		return "public interface " + CLASS_NAME_PREFIX + " {\r\n" + "\r\n";
	}

	/**
	 * Write content to fileName
	 * 
	 * @param content
	 * @param packageName
	 * @param fileName
	 */
	public static void writeFile(StringBuffer content, String packageName,
			String fileName) {

		if (packageName == null) {
			Log.e(TAG, "writeFile", "Package name is null, somethings wrong!!!");
			return;
		}

		if (fileName == null) {
			Log.e(TAG, "writeFile", "File name is null, something wrong!!!");
			return;
		}

		StringBuffer filePath = new StringBuffer("src/");
		packageName = packageName.replace(".", "/");
		filePath = filePath.append(packageName);
		filePath = filePath.append("/" + fileName);
		File oldFile = new File(filePath.toString());
		// Delete old file.
		if (oldFile.exists()) {
			boolean result = oldFile.delete();
			if (result) {
				Log.o(TAG, "writeFile", "Delete old " + fileName
						+ " success!!!");
			} else {
				Log.e(TAG, "writeFile", "Delete old " + fileName + "failed!!!");
				return;
			}
		}
		// Create new file.
		File newFile = new File(filePath.toString());
		if (!newFile.exists()) {
			try {
				newFile.createNewFile();
				OutputStream os = new BufferedOutputStream(
						new FileOutputStream(newFile));
				os.write(content.toString().getBytes());
				os.flush();
				os.close();
				Log.o(TAG, "writeFile", "Auto generate " + FILE_NAME + " ok!!!");
			} catch (IOException e) {
				Log.t(e);
				Log.e(TAG, "writeFile", "Auto generate " + FILE_NAME
						+ " failed!!!");
			}
		}
	}

	/**
	 * Filter all need to translate layout.
	 * 
	 * @param resLayout
	 * @return
	 */
	public static List<String> filterLayouts(String resLayout) {

		Log.o(TAG, "filterLayouts :: ", "resLayout = " + resLayout);
		File layoutDir = new File(resLayout);
		String[] xmlFiles = layoutDir.list();
		List<String> tranXmlList = new LinkedList<String>();
		// Filter xml files
		for (String layout : xmlFiles) {
			if (!LayoutUtils.isNoTransLayout(layout)) {
				tranXmlList.add(layout);
			}
		}

		return tranXmlList;
	}

	/**
	 * Translate xml to class.
	 * 
	 * @param tranXmlList
	 * @return
	 */
	public static String xml2Class(List<String> tranXmlList) {

		if (tranXmlList == null || tranXmlList.size() == 0) {
			Log.e(TAG, "xml2Class :: ", "tranXmlList is empty");
			return "";
		}

		List<XmlLayout> xmlLayouts = new ArrayList<XmlLayout>();
		Log.e(TAG, "xml2Class :: ", "Parsering... ");
		for (String xmlName : tranXmlList) {
			Log.o(TAG, "xml2Class :: ", "Parser " + xmlName);
			XmlLayout xmlLayout = new XmlLayout();
			xmlLayout.setXmlName(xmlName);
			xmlLayout.setComponents(layout2Components(xmlName));
			xmlLayouts.add(xmlLayout);
		}
		Log.e(TAG, "xml2Class :: ", "Parser Done ");
		// for (XmlLayout xmlLayout : xmlLayouts) {
		// Log.e(TAG, "xml2Class :: ", "" + xmlLayout);
		// }

		StringBuilder content = new StringBuilder();
		Log.e(TAG, "xml2Class :: ", "Translating... ");
		for (XmlLayout xmlLayout : xmlLayouts) {

			String xmlName = xmlLayout.getXmlName();
			Log.o(TAG, "xml2Class :: ", "Translate " + xmlName);
			String clzName = LayoutUtils.xmlName2ClassName(CLASS_NAME_PREFIX,
					xmlName);
			content.append("\n\n\t" + "public class " + clzName + " {  \n ");
			List<Component> components = xmlLayout.getComponents();
			for (Component component : components) {
				// Log.e(TAG, "xml2Class :: ", "" + component);
				content.append("\n\t\tpublic " + component.getType() + " "
						+ component.getName() + ";");
			}

			String paramType = "View";
			if (clzName.startsWith(CLASS_NAME_PREFIX + "Activity")) {
				paramType = "Activity";
			}

			content.append("\n\n\t\tpublic " + clzName + " (" + paramType
					+ " convertView) {");
			for (Component component : components) {
				// Include
				if (component.isInclude()) {
					String convertView = component.getName() + "View";
					content.append("\n\t\t\tView " + convertView
							+ " = (View)convertView.findViewById("
							+ component.getId() + ");");
					content.append("\n\t\t\t" + component.getName() + " = "
							+ "new " + component.getType() + "(" + convertView
							+ ");");
				}
				// ViewStub
				else if (component.isStubView()) {
					
				}
				// Normal
				else {
					content.append("\n\t\t\t" + component.getName() + " = ("
							+ component.getType()
							+ ")convertView.findViewById(" + component.getId()
							+ ");");
				}
			}
			content.append("\n\t\t}");
			content.append("\n\t}\n ");
		}
		Log.e(TAG, "xml2Class :: ", "Translate done ");

		return content.toString();
	}

	/**
	 * Translate element to component.
	 * 
	 * @param root
	 * @return
	 */
	public static List<Component> element2Components(Element root) {

		List<Component> components = new LinkedList<Component>();
		// Root component
		List<Attribute> attributes = root.attributes();
		for (Attribute attribute : attributes) {

			String name = attribute.getName();
			String value = attribute.getValue();

			if (name.equals("id")) {
				Component component = new Component();
				String id = "";
				if (LayoutUtils.isAndroidId(value)) {
					id = "android.R.id." + LayoutUtils.getIdInR(value);
				} else {
					id = "R.id." + LayoutUtils.getIdInR(value);
				}
				component.setId(id);
				component.setName("m" + LayoutUtils.cameId(value));
				component.setType(root.getName());
				components.add(component);
			}
		}

		// Elements
		List<Element> elements = root.elements();
		for (Element element : elements) {

			// Container component
			String type = element.getName();
			if (LayoutUtils.isContainerLayout(type)) {
				components.addAll(element2Components(element));
				continue;
			}

			// Include view component
			if (LayoutUtils.isIncludeLayout(type)) {
				List<Attribute> stubAttributes = element.attributes();
				String id = "";
				String cName = "";
				String stubLayout = "";
				for (Attribute attribute : stubAttributes) {

					String name = attribute.getName();
					String value = attribute.getValue();

					if (name.equals("id")) {
						if (LayoutUtils.isAndroidId(value)) {
							id = "android.R.id." + LayoutUtils.getIdInR(value);
						} else {
							id = "R.id." + LayoutUtils.getIdInR(value);
						}
						cName = value;
						continue;
					}

					if (name.equals("layout")) {
						stubLayout = LayoutUtils.getStubLayout(value);
					}
				}

				if (id == null || id.length() == 0) {
					Log.o(TAG, "element2Components :: ", type + " "
							+ stubLayout + " id is empty");
					components.addAll(layout2Components(stubLayout));
				} else {
					Log.o(TAG, "element2Components :: ", type + " "
							+ stubLayout + " id is normal");
					Component component = new Component();
					component.setId(id);
					component.setName("m" + LayoutUtils.cameId(cName));
					component.setType(LayoutUtils.xmlName2ClassName(
							CLASS_NAME_PREFIX, stubLayout));
					component.setInclude(true);
					components.add(component);
				}
				continue;
			}

			// ViewStub view component
			if (LayoutUtils.isViewStubLayout(type)) {
				List<Attribute> stubAttributes = element.attributes();
				String id = "";
				String cName = "";
				String stubLayout = "";
				for (Attribute attribute : stubAttributes) {

					String name = attribute.getName();
					String value = attribute.getValue();

					if (name.equals("id")) {
						if (LayoutUtils.isAndroidId(value)) {
							id = "android.R.id." + LayoutUtils.getIdInR(value);
						} else {
							id = "R.id." + LayoutUtils.getIdInR(value);
						}
						cName = value;
						continue;
					}

					if (name.equals("layout")) {
						stubLayout = LayoutUtils.getStubLayout(value);
					}
				}

				if (id == null || id.length() == 0) {
					Log.o(TAG, "element2Components :: ", type + " "
							+ stubLayout + " id is empty");
					components.addAll(layout2Components(stubLayout));
				} else {
					Log.o(TAG, "element2Components :: ", type + " "
							+ stubLayout + " id is normal");
					// ViewStub
					Component viewStub = new Component();
					viewStub.setId(id);
					viewStub.setName("m" + LayoutUtils.cameId(cName));
					viewStub.setType(type);
					components.add(viewStub);

					// Layout
					Component component = new Component();
					component.setId("m" + LayoutUtils.cameId(cName));
					component.setName("m" + LayoutUtils.cameId(cName)
							+ "Layout");
					component.setType(LayoutUtils.xmlName2ClassName(
							CLASS_NAME_PREFIX, stubLayout));
					component.setStubView(true);
					components.add(component);
				}

				continue;
			}

			// Normal
			List<Attribute> stubAttributes = element.attributes();
			for (Attribute attribute : stubAttributes) {

				String name = attribute.getName();
				String value = attribute.getValue();
				if (name.equals("id")) {
					Component component = new Component();
					String id = "";
					if (LayoutUtils.isAndroidId(value)) {
						id = "android.R.id." + LayoutUtils.getIdInR(value);
					} else {
						id = "R.id." + LayoutUtils.getIdInR(value);
					}
					component.setId(id);
					component.setName("m" + LayoutUtils.cameId(value));
					component.setType(element.getName());
					components.add(component);
				}
			}
		}

		return components;
	}

	/**
	 * Translate layout to component.
	 * 
	 * @param xmlName
	 * @return
	 */
	public static List<Component> layout2Components(String xmlName) {

		if (xmlName == null || xmlName.length() == 0) {
			Log.e(TAG, "layout2Component :: ", "xmlName is empty!");
			return null;
		}

		List<Component> components = new LinkedList<Component>();
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File(RES_LAYOUT + "/" + xmlName));
			Element root = doc.getRootElement();
			components = element2Components(root);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		return components;
	}
}
