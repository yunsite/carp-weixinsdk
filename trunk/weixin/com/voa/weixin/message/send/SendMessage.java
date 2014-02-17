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

import java.util.Date;

import com.voa.weixin.message.recieve.RecieveMessage;

/**
 * 被动发送消息的基类 
 * @author zhaiyuxin
 *
 */
public abstract class SendMessage {

	protected String toUserName;
	protected String fromUserName;
	protected Date createTime;
	protected String msgType;

	public SendMessage(RecieveMessage recieveMessage) {
		this.toUserName = recieveMessage.getFromUserName();
		this.fromUserName = recieveMessage.getToUserName();
		this.createTime = new Date();
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

	public abstract String toXml();

}
