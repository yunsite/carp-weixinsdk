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

import java.util.LinkedList;

import com.voa.weixin.message.recieve.RecieveMessage;
import com.voa.weixin.message.send.SendMessage;

/**
 * 操作链，对一个接收消息，将调用操作链的所有handler进行处理
 * @author zhaiyuxin
 *
 */
public class HandlerChain {

	private final String msgKey;

	private final LinkedList<Handler> handlerQuene = new LinkedList<Handler>();

	public HandlerChain(String msgKey) {
		this.msgKey = msgKey;
	}

	public void insertFirst(Handler handler) {
		handlerQuene.add(0, handler);
	}

	public void append(Handler handler) {
		handlerQuene.addLast(handler);
	}

	public SendMessage doHandler(RecieveMessage recieveMsg) throws Exception {
		SendMessage sendMessage = null;
		for (Handler handler : handlerQuene) {
			Handler nextHandler = handler.getNextHandler();

			handler.setRecieveMsg(recieveMsg);

			handler.process();

			if (nextHandler != null) {
				nextHandler.setSendMsg(handler.getSendMsg());
			}

			sendMessage = handler.getSendMsg();

		}
		return sendMessage;
	}

	public int getSize() {
		return handlerQuene.size();
	}

}
