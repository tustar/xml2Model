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

import java.util.List;

public class XmlLayout {

	private String xmlName;
	private List<Component> components;

	public String getXmlName() {
		return xmlName;
	}

	public void setXmlName(String xmlName) {
		this.xmlName = xmlName;
	}

	public List<Component> getComponents() {
		return components;
	}

	public void setComponents(List<Component> components) {
		this.components = components;
	}

	@Override
	public String toString() {
		return "XmlLayout [xmlName=" + xmlName + ", components=" + components
				+ "]";
	}

}
