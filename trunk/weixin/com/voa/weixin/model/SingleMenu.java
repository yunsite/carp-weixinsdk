/**
  	This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.voa.weixin.model;

import net.sf.json.JSONObject;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class SingleMenu extends Menu {

	public static final String TYPE_CLICK = "click";
	public static final String TYPE_VIEW = "view";

	private String ftype;
	private String key;
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
		this.ftype = TYPE_VIEW;
	}

	public String getFtype() {
		return ftype;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		this.ftype = TYPE_CLICK;
	}

	@Override
	public String toJson() {
		JSONObject menuJson = new JSONObject();
		menuJson.element("type", this.ftype);
		menuJson.element("name", this.name);
		if (TYPE_CLICK.equals(this.ftype)) {
			menuJson.element("key", this.key);
		} else {
			menuJson.element("url", this.url);
		}

		return menuJson.toString();
	}
}
