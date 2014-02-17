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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.voa.weixin.message.MessageType;
import com.voa.weixin.utils.LogUtil;
import com.voa.weixin.utils.WeixinUtils;

/**
 * 操作管理器
 * @author zhaiyuxin
 *
 */
public class HandlerManager {

	private static final Log logger = LogFactory.getLog(HandlerManager.class);

	/**
	 * String：事件名称 List<String>:handler类名
	 */
	private Map<String, List<String>> chains;

	private static HandlerManager manager;

	private HandlerManager() {
		init();
	}

	private void init() {
		chains = new HashMap<String, List<String>>();

		chains.put(MessageType.HANDLER_TYPE_TEXT, new ArrayList<String>());
		chains.put(MessageType.HANDLER_TYPE_IMAGE, new ArrayList<String>());
		chains.put(MessageType.HANDLER_TYPE_VOICE, new ArrayList<String>());
		chains.put(MessageType.HANDLER_TYPE_VIDEO, new ArrayList<String>());
		chains.put(MessageType.HANDLER_TYPE_LOCATION, new ArrayList<String>());
		chains.put(MessageType.HANDLER_TYPE_LINK, new ArrayList<String>());

		chains
				.put(MessageType.HANDLER_EVENT_SUBSCRIBE,
						new ArrayList<String>());
		chains.put(MessageType.HANDLER_EVENT_SUBSCRIBE_QRSCENE,
				new ArrayList<String>());
		chains.put(MessageType.HANDLER_EVENT_UNSUBSCRIBE,
				new ArrayList<String>());
		chains.put(MessageType.HANDLER_EVENT_SCAN, new ArrayList<String>());
		chains.put(MessageType.HANDLER_EVENT_LOCATION, new ArrayList<String>());
		chains.put(MessageType.HANDLER_EVENT_CLICK, new ArrayList<String>());

		Document doc = null;
		try {
			doc = WeixinUtils.getDocumentResource("weixin.handler.xml");
		} catch (Exception e) {
			logger.error("weixin.handler.xml parser error");
			e.printStackTrace();
			return;
		}

		Element root = doc.getRootElement();
		logger.debug(root.asXML());
		List<Element> handlerEs = root.elements();
		for (Element handlerE : handlerEs) {
			String handlerName = handlerE.elementText("name");
			logger.debug("handlerName : " + handlerName);
			List<Element> handlerClzEs = handlerE.elements("handlerclz");
			if (handlerClzEs == null || handlerClzEs.size() == 0)
				continue;

			List<String> handlerClzNames = chains.get(handlerName);
			logger.debug("handlerClzNames:" + handlerClzNames);
			if (handlerClzNames == null)
				continue;
			for (Element handlerClzE : handlerClzEs) {
				String clzName = handlerClzE.getText();
				try {
					Class.forName(clzName);
					handlerClzNames.add(clzName);
				} catch (ClassNotFoundException e) {
					logger.error(clzName + " class not fount exception");
					e.printStackTrace();
				}
			}
		}

	}

	public static HandlerManager getInstance() {
		if (manager == null)
			manager = new HandlerManager();

		return manager;
	}

	public HandlerChain getChain(String handlerType) {
		List<String> clzNames = chains.get(handlerType);
		logger.debug("handlerType = " + handlerType);
		if (clzNames == null)
			return null;
		logger.debug("clzNames = " + clzNames);
		HandlerChain chain = new HandlerChain(handlerType);

		try {
			for (String clzName : clzNames) {
				Class clz = Class.forName(clzName);
				Handler handler = (Handler) clz.newInstance();
				chain.append(handler);
			}

			return chain;
		} catch (Exception e) {
			e.printStackTrace(LogUtil.getErrorStream(logger));
			e.printStackTrace();
		}

		return null;
	}
}
