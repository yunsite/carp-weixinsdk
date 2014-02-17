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

import org.dom4j.Element;

import com.voa.weixin.message.MessageType;

/**
 * 对应菜单事件
 * @author zhaiyuxin
 *
 */
public class RecieveMenuEvent extends RecieveEvent {

	public RecieveMenuEvent(String msgXml, Element root) {
		super(msgXml, root);
		this.handlerType = MessageType.HANDLER_EVENT_CLICK;
	}

	private String eventKey;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	@Override
	public void parseXml(Element root) {
		this.eventKey = root.elementText("EventKey");
	}
}
