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

/**
 * 
 * @author zhaiyuxin
 *
 */
public class MultiMenu extends Menu {

	private final List<SingleMenu> menus = new ArrayList<SingleMenu>();

	public List<SingleMenu> getMenus() {
		return menus;
	}

	public void addMenu(SingleMenu menu) {
		this.menus.add(menu);
	}

	@Override
	public String toJson() {
		JSONArray menusJson = new JSONArray();
		for (SingleMenu menu : menus) {
			menusJson.add(menu.toJson());
		}

		JSONObject multiMenuJson = new JSONObject();
		multiMenuJson.element("name", this.name);
		multiMenuJson.element("sub_button", menusJson);

		return multiMenuJson.toString();
	}
}
