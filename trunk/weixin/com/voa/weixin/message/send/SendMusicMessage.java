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
package com.voa.weixin.message.send;

import com.voa.weixin.message.MessageType;
import com.voa.weixin.message.recieve.RecieveMessage;

/**
 * 对应音乐消息
 * @author zhaiyuxin
 *
 */
public class SendMusicMessage extends SendMessage {

	private String title;
	private String description;
	private String musicUrl;
	private String hqMusicUrl;
	private String thumbMediaId;

	public SendMusicMessage(RecieveMessage recieveMessage) {
		super(recieveMessage);
		this.msgType = MessageType.TYPE_MUSIC;
	}

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

	public String getHqMusicUrl() {
		return hqMusicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toXml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml><ToUserName><![CDATA[");
		sb.append(this.toUserName);
		sb.append("]]></ToUserName>");
		sb.append("<FromUserName><![CDATA[");
		sb.append(this.fromUserName);
		sb.append("]]></FromUserName>");
		sb.append("<CreateTime>");
		sb.append(this.createTime.getTime() / 1000);
		sb.append("</CreateTime>");
		sb.append("<MsgType><![CDATA[music]]></MsgType>");
		sb.append("<Music><Title><![CDATA[");
		sb.append(this.title);
		sb.append("]]></Title>");
		sb.append("<Description><![CDATA[");
		sb.append(this.description);
		sb.append("]]></Description>");
		sb.append("<MusicUrl><![CDATA[");
		sb.append(this.musicUrl);
		sb.append("]]></MusicUrl>");
		sb.append("<HQMusicUrl><![");
		sb.append(this.hqMusicUrl);
		sb.append("<ThumbMediaId><![CDATA[");
		sb.append(this.thumbMediaId);
		sb.append("]]></ThumbMediaId></Music></xml>");

		return sb.toString();
	}

}
