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
package com.voa.weixin.message.recieve;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;

/**
 * 接收消息的基类
 * @author zhaiyuxin
 *
 */
public abstract class RecieveMessage {

	private static final Log logger = LogFactory.getLog(RecieveMessage.class);

	protected String msgXml;

	protected String toUserName;
	protected String fromUserName;
	protected Date createTime;
	protected String msgType;
	protected String msgId;
	protected String handlerType;

	public void setHandlerType(String handlerType) {
		this.handlerType = handlerType;
	}

	public RecieveMessage(String msgXml, Element root) {
		this.setMsgXml(msgXml);
		parsePubXml(root);
		parseXml(root);
	}

	public String getToUserName() {
		return toUserName;
	}

	public void setToUserName(String toUserName) {
		this.toUserName = toUserName;
	}

	public String getFromUserName() {
		return fromUserName;
	}

	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMsgXml() {
		return msgXml;
	}

	public void setMsgXml(String msgXml) {
		this.msgXml = msgXml;
	}

	public String getHandlerType() {
		return this.handlerType;
	}

	private void parsePubXml(Element root) {
		this.toUserName = root.elementText("ToUserName");
		this.fromUserName = root.elementText("FromUserName");
		String getTime = root.elementText("CreateTime");
		logger.debug("getTime : " + getTime);
		this.createTime = new Date((Long.parseLong(getTime) * 1000));
		this.msgType = root.elementText("MsgType");
		this.msgId = root.elementText("MsgId");

		logger.debug("createtime : " + this.createTime);
	}

	public abstract void parseXml(Element root);

}
