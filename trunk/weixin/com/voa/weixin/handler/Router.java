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
package com.voa.weixin.handler;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.voa.weixin.message.MessageType;
import com.voa.weixin.message.recieve.RecieveEvent;
import com.voa.weixin.message.recieve.RecieveImgMessage;
import com.voa.weixin.message.recieve.RecieveLinkMessage;
import com.voa.weixin.message.recieve.RecieveLocationEvent;
import com.voa.weixin.message.recieve.RecieveLocationMessage;
import com.voa.weixin.message.recieve.RecieveMenuEvent;
import com.voa.weixin.message.recieve.RecieveMessage;
import com.voa.weixin.message.recieve.RecieveTicketEvent;
import com.voa.weixin.message.recieve.RecieveTxtMessage;
import com.voa.weixin.message.recieve.RecieveVedioMessage;
import com.voa.weixin.message.recieve.RecieveVoiceMessage;

/**
 * 路由类，负责将接收到的信息转换为RecieveMessage，并分析对应的操作链
 * @author zhaiyuxin
 *
 */
public class Router {

	/**
	 * 获得消息的操作链
	 * @param message
	 * @return
	 */
	public static HandlerChain parseHandler(RecieveMessage message) {
		String handlerType = message.getHandlerType();

		HandlerChain chain = HandlerManager.getInstance().getChain(handlerType);

		return chain;
	}

	/**
	 * 转换消息为ReciveMessage
	 * @param recieveMsg
	 * @return
	 */
	public static RecieveMessage generateMsg(String recieveMsg) {
		RecieveMessage message = null;
		try {
			Document doc = DocumentHelper.parseText(recieveMsg);
			Element root = doc.getRootElement();

			String type = root.elementText("MsgType");

			if (StringUtils.equals(type, MessageType.TYPE_TEXT)) {
				message = new RecieveTxtMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_IMAGE)) {
				message = new RecieveImgMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_VOICE)) {
				message = new RecieveVoiceMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_VIDEO)) {
				message = new RecieveVedioMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_LOCATION)) {
				message = new RecieveLocationMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_LINK)) {
				message = new RecieveLinkMessage(recieveMsg, root);
			} else if (StringUtils.equals(type, MessageType.TYPE_EVENT)) {
				String event = root.elementText("Event");

				if (StringUtils.equals(event, MessageType.EVENT_SUBSCRIBE)) {
					if (root.elementText("EventKey") == null)
						message = new RecieveEvent(recieveMsg, root);
					else
						message = new RecieveTicketEvent(recieveMsg, root);
				} else if (StringUtils.equals(event, MessageType.EVENT_SCAN)) {
					message = new RecieveTicketEvent(recieveMsg, root);
				} else if (StringUtils
						.equals(event, MessageType.EVENT_LOCATION)) {
					message = new RecieveLocationEvent(recieveMsg, root);
				} else if (StringUtils.equals(event, MessageType.EVENT_CLICK)) {
					message = new RecieveMenuEvent(recieveMsg, root);
				}
			}

		} catch (Exception e) {
			throw new HandlerException("RecieveMessage parse error.", e);
		}

		return message;
	}
}
