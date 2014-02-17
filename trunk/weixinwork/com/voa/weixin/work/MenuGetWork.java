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
package com.voa.weixin.work;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.voa.weixin.Carp;
import com.voa.weixin.model.MultiMenu;
import com.voa.weixin.model.SingleMenu;
import com.voa.weixin.model.WeixinMenu;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 获得菜单的回调工作
 * @author zhaiyuxin
 *
 */
public class MenuGetWork extends Work {

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toDo() throws WorkException {
		JSONObject menuJson = JSONObject.fromObject(weixinResult.getJson());
		JSONArray buttonArrayJson = menuJson.getJSONObject("menu")
				.getJSONArray("button");

		WeixinMenu weixinMenu = new WeixinMenu();
		for (int i = 0; i < buttonArrayJson.size(); i++) {
			JSONObject buttonJson = buttonArrayJson.getJSONObject(i);

			if (buttonJson.has("type")) {
				SingleMenu sm = parseSingleMenu(buttonJson);

				weixinMenu.addMenu(sm);
			} else {
				MultiMenu mm = new MultiMenu();
				mm.setName(buttonJson.getString("name"));
				JSONArray subbuttonArrayJson = buttonJson
						.getJSONArray("sub_button");

				for (int j = 0; j < subbuttonArrayJson.size(); i++) {
					SingleMenu subSm = parseSingleMenu(buttonJson);
					mm.addMenu(subSm);
				}

				weixinMenu.addMenu(mm);
			}
		}

		Carp.getInstance().getWeixinCache().setWeixinMenu(weixinMenu);
	}

	private SingleMenu parseSingleMenu(JSONObject buttonJson) {
		SingleMenu sm = new SingleMenu();
		sm.setName(buttonJson.getString("name"));
		String type = buttonJson.getString("type");
		if (type.equals(SingleMenu.TYPE_CLICK)) {
			sm.setKey(buttonJson.getString("key"));
		} else {
			sm.setUrl(buttonJson.getString("url"));
		}

		return sm;
	}
}
