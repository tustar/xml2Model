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
 * The Android component description
 * 
 * @author tustar (tustar1030@gmail.com)
 * @version 1.3.0
 */
public class Component {

	/**
	 * If android:id in layout is <b>android:id="@+id/home_splash"</b>, then the
	 * id is <b>home_splash</b>.</br>If android:id in layout is
	 * <b>"@id/action_settings"</b>, then the id is
	 * <b>action_setting</b>.</br>If android:id in layout is
	 * <b>android:id="@android:id/tabcontent"</b>, then the id is
	 * <b>tabcontent</b>.
	 */
	private String id;
	/**
	 * Change the id to var by using camel nomenclature</br> <b>id =
	 * home_splash, name = "mHomeSplash"</b></br> <b>id = action_setting, name =
	 * "mActionSetting"</b></br> <b>id = tabcontent, name =
	 * "mTabContent"</b></br>
	 */
	private String name;
	/**
	 * The type is the component class Name.</br> <b>eg.</b> TextView
	 */
	private String type;
	/**
	 * The component is ViewStub, so mark it.
	 */
	private boolean stubView;
	/**
	 * The component is include, so mark it.
	 */
	private boolean include;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public boolean isStubView() {
		return stubView;
	}

	public void setStubView(boolean stubView) {
		this.stubView = stubView;
	}

	protected boolean isInclude() {
		return include;
	}

	protected void setInclude(boolean include) {
		this.include = include;
	}

	@Override
	public String toString() {
		return "Component [id=" + id + ", name=" + name + ", type=" + type
				+ ", stubView=" + stubView + ", include=" + include + "]";
	}

}