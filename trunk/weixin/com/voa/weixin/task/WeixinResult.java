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
package com.voa.weixin.task;

import java.util.Collection;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

/**
 * 客服消息提交后返回的结果
 * 
 * @author zhaiyuxin
 * 
 */
public class WeixinResult {

	private Date recieveTime;
	private String json;
	private int errorCode;
	private String errMsg;
	private JSONObject result;

	public Date getRecieveTime() {
		return recieveTime;
	}

	public void setRecieveTime(Date recieveTime) {
		this.recieveTime = recieveTime;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
		this.recieveTime = new Date();
		try {
			this.result = JSONObject.fromObject(json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result.has("errcode"))
			this.errorCode = result.getInt("errcode");
		else
			this.errorCode = 0;

		if (result.has("errmsg"))
			this.errMsg = result.getString("errmsg");
	}

	public int getErrorCode() {
		return errorCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public int getInt(String xpath) {
		int value = 0;
		JSONObject temp = result;
		String[] names = StringUtils.split(xpath, "/");
		for (int i = 0; i < names.length; i++) {
			if (i == (names.length - 1)) {
				value = temp.getInt(names[i]);
				break;
			}

			temp = temp.getJSONObject(names[i]);
		}

		return value;
	}

	public long getLong(String xpath) {
		long value = 0;
		JSONObject temp = result;
		String[] names = StringUtils.split(xpath, "/");
		for (int i = 0; i < names.length; i++) {
			if (i == (names.length - 1)) {
				value = temp.getLong(names[i]);
				break;
			}

			temp = temp.getJSONObject(names[i]);
		}

		return value;
	}

	public String getString(String xpath) {
		String value = "";
		JSONObject temp = result;
		String[] names = StringUtils.split(xpath, "/");
		for (int i = 0; i < names.length; i++) {
			if (i == (names.length - 1)) {
				value = temp.getString(names[i]);
				break;
			}

			temp = temp.getJSONObject(names[i]);
		}

		return value;
	}

	public boolean has(String name) {
		return result.has(name);
	}

	public Collection getCollection(String xpath) {
		Collection value = null;
		JSONObject temp = result;
		String[] names = StringUtils.split(xpath, "/");
		for (int i = 0; i < names.length; i++) {
			if (i == (names.length - 1)) {
				value = JSONArray.toCollection(temp.getJSONArray(names[i]));
				break;
			}

			temp = temp.getJSONObject(names[i]);
		}

		return value;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
}
