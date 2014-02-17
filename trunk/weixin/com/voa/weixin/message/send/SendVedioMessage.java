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
 * 对应视频消息
 * @author zhaiyuxin
 *
 */
public class SendVedioMessage extends SendMessage {

	private String vedioMediaId;
	private String title;
	private String description;

	public SendVedioMessage(RecieveMessage recieveMessage) {
		super(recieveMessage);
		this.msgType = MessageType.TYPE_VIDEO;
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
		sb.append("<MsgType><![CDATA[video]]></MsgType>");
		sb.append("<Video><MediaId><![CDATA[");
		sb.append(this.vedioMediaId);
		sb.append("]]></MediaId>");
		sb.append("<Title><![CDATA[");
		sb.append(this.title);
		sb.append("]]></Title>");
		sb.append("<Description><![CDATA[");
		sb.append(this.description);
		sb.append("]]></Description></Video></xml>");

		return sb.toString();
	}
}
