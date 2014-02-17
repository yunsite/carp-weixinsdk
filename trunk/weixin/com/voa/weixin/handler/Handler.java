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

import com.voa.weixin.message.recieve.RecieveMessage;
import com.voa.weixin.message.send.SendMessage;

/**
 * 接收消息的操作类基类
 * @author zhaiyuxin
 *
 */
public abstract class Handler {

	/** 需处理的消息 **/
	protected RecieveMessage recieveMsg;

	/** 返回的信息 **/
	protected SendMessage sendMsg;

	/** 链上前一个操作类 **/
	protected Handler preHandler;

	/** 链上下一个操作类 **/
	protected Handler nextHandler;

	/** 操作类所在的处理链 **/
	protected HandlerChain chain;

	/**
	 * 获得处理链
	 * @return
	 */
	public HandlerChain getChain() {
		return chain;
	}

	/**
	 * 设置处理链
	 * @param chain
	 */
	public void setChain(HandlerChain chain) {
		this.chain = chain;
	}

	/**
	 * 获得接收消息
	 * @return
	 */
	public RecieveMessage getRecieveMsg() {
		return recieveMsg;
	}

	/**
	 * 设置接收消息
	 * @param recieveMsg
	 */
	public void setRecieveMsg(RecieveMessage recieveMsg) {
		this.recieveMsg = recieveMsg;
	}

	/**
	 * 获得返回消息
	 * @return
	 */
	public SendMessage getSendMsg() {
		return sendMsg;
	}

	public void setSendMsg(SendMessage sendMsg) {
		this.sendMsg = sendMsg;
	}

	public Handler getPreHandler() {
		return preHandler;
	}

	public void setPreHandler(Handler preHandler) {
		this.preHandler = preHandler;
	}

	public Handler getNextHandler() {
		return nextHandler;
	}

	public void setNextHandler(Handler nextHandler) {
		this.nextHandler = nextHandler;
	}

	/**
	 * 子类实现，处理接收到的信息
	 * @throws Exception
	 */
	public abstract void process() throws Exception;
}
