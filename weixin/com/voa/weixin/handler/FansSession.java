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

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.message.recieve.RecieveMessage;
import com.voa.weixin.message.send.SendMessage;
import com.voa.weixin.message.send.SendNullMessage;
import com.voa.weixin.utils.HttpUtils;

/**
 * 用户会话状态
 * @author zhaiyuxin
 *
 */
public class FansSession {

	private static final Log logger = LogFactory.getLog(FansSession.class);

	private String id;			//会话唯一身份	
	private long createTime;  	//会话创建时间
	private String openId;		//用户的openID
	private RecieveMessage recieveMessage;	//接收的消息
	private HttpServletResponse response;	
	private HandlerChain chain;				//操作链
	private int repeatCount;				//消息重复接收次数

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public RecieveMessage getRecieveMessage() {
		return recieveMessage;
	}

	public void setRecieveMessage(RecieveMessage recieveMessage) {
		this.recieveMessage = recieveMessage;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public HandlerChain getChain() {
		return chain;
	}

	public void setChain(HandlerChain chain) {
		this.chain = chain;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	/**
	 * 执行操作链
	 */
	public void toChain() {
		try {
			SendMessage sendMessage = chain.doHandler(recieveMessage);
			if (sendMessage == null) {
				sendMessage = new SendNullMessage(recieveMessage);
			}
			String responseXml = sendMessage.toXml();
			logger.debug("send message xml : " + responseXml);
			HttpUtils.write(response, responseXml);
			SessionManager.getInstance().removeSession(this.id);
		} catch (Exception e) {
			SessionManager.getInstance().removeSession(this.id);
			throw new HandlerException("user message process error.", e);

		}
	}

}
