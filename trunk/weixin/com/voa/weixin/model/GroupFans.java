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
public class GroupFans extends WeixinTaskMessage {

	private String groupId;
	private String fansOpenId;

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getFansOpenId() {
		return fansOpenId;
	}

	public void setFansOpenId(String fansOpenId) {
		this.fansOpenId = fansOpenId;
	}

	@Override
	public String toJson() {
		JSONObject jo = new JSONObject();
		jo.element("openid", this.fansOpenId);
		jo.element("groupid", this.groupId);
		return jo.toString();
	}

}
