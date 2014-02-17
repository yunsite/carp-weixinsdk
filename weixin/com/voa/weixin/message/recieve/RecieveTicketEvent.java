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
 * 对应扫描带参二维码事件
 * @author zhaiyuxin
 *
 */
public class RecieveTicketEvent extends RecieveEvent {

	public RecieveTicketEvent(String msgXml, Element root) {
		super(msgXml, root);
		if (this.event.equals(MessageType.EVENT_SCAN))
			this.handlerType = MessageType.HANDLER_EVENT_SCAN;
		else
			this.handlerType = MessageType.HANDLER_EVENT_SUBSCRIBE_QRSCENE;
	}

	private String eventKey;
	private String ticket;

	public String getEventKey() {
		return eventKey;
	}

	public void setEventKey(String eventKey) {
		this.eventKey = eventKey;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	@Override
	public void parseXml(Element root) {
		super.parseXml(root);
		this.eventKey = root.elementText("EventKey");
		this.ticket = root.elementText("Ticket");
	}
}
