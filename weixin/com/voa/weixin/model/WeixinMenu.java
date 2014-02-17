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

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class WeixinMenu extends WeixinTaskMessage {
	private final List<Menu> menus = new ArrayList<Menu>();

	public List<Menu> getMenu() {
		return this.menus;
	}

	public void addMenu(Menu menu) {
		menus.add(menu);
	}

	@Override
	public String toJson() {
		JSONArray menusJson = new JSONArray();

		for (Menu menu : menus) {
			menusJson.add(menu.toJson());
		}

		JSONObject buttonJson = new JSONObject();
		buttonJson.element("button", menusJson);

		return menusJson.toString();
	}

	public Menu getMenuByName(String name) {
		Menu result = null;
		for (Menu menu : menus) {
			String menuName = menu.getName();
			if (StringUtils.equals(menuName, name)) {
				result = menu;
			}
		}

		return result;
	}

}
