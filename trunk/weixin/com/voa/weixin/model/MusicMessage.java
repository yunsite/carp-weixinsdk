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
public class MusicMessage extends Message {
	private String title;
	private String description;
	private String musicUrl;
	private String hqmusicUrl;
	private String thumbMediaId;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMusicUrl() {
		return musicUrl;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public String getHqmusicUrl() {
		return hqmusicUrl;
	}

	public void setHqmusicUrl(String hqmusicUrl) {
		this.hqmusicUrl = hqmusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toJson() {
		JSONObject music = new JSONObject();
		music.element("title", title);
		music.element("description", description);
		music.element("musicurl", musicUrl);
		music.element("hqmusicurl", hqmusicUrl);
		music.element("thumb_media_id", thumbMediaId);

		JSONObject jsonObj = new JSONObject();
		jsonObj.element("touser", touser);
		jsonObj.element("msgtype", "music");
		jsonObj.element("music", music);

		return jsonObj.toString();
	}
}
