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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.message.recieve.RecieveMessage;

/**
 * 会话管理，在消息未被处理完成之前，会话保存在此处
 * @author zhaiyuxin
 *
 */
public class SessionManager {

	private static final Log logger = LogFactory.getLog(SessionManager.class);

	private static SessionManager manager;

	private final Map<String, FansSession> fansSessions;

	private SessionManager() {
		fansSessions = new HashMap<String, FansSession>();
	}

	/**
	 * 获得会话实例
	 * @return
	 */
	public static SessionManager getInstance() {
		if (manager == null)
			manager = new SessionManager();

		return manager;
	}

	/**
	 * 创建一个用户会话，并调用操作链进行处理
	 * @param recieveMessage
	 * @param response
	 */
	public void buildSession(String recieveMessage, HttpServletResponse response) {
		RecieveMessage message = Router.generateMsg(recieveMessage);
		String sessionId = "";
		if (!message.getMsgType().equals("event")) {
			sessionId = message.getMsgId();
		} else {
			sessionId = message.getToUserName()
					+ message.getCreateTime().getTime();
		}

		FansSession session = fansSessions.get(sessionId);
		if (session == null) {
			HandlerChain chain = Router.parseHandler(message);

			session = new FansSession();
			session.setId(sessionId);
			session.setChain(chain);
			session.setCreateTime(new Date().getTime());
			session.setRecieveMessage(message);
			session.setOpenId(message.getFromUserName());
			session.setRepeatCount(1);
			session.setResponse(response);

			fansSessions.put(sessionId, session);

			session.toChain();
		} else
			session.setRepeatCount(session.getRepeatCount() + 1);

	}

	/**
	 * 移除一个会话
	 * @param sessionId
	 */
	public void removeSession(String sessionId) {
		fansSessions.remove(sessionId);
	}
}
