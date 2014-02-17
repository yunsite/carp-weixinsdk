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
 * 对应地理位置事件
 * @author zhaiyuxin
 *
 */
public class RecieveLocationEvent extends RecieveEvent {

	public RecieveLocationEvent(String msgXml, Element root) {
		super(msgXml, root);
		this.handlerType = MessageType.HANDLER_EVENT_LOCATION;
	}

	private Float latitude;
	private Float longitude;
	private Float precision;

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getPrecision() {
		return precision;
	}

	public void setPrecision(Float precision) {
		this.precision = precision;
	}

	@Override
	public void parseXml(Element root) {
		super.parseXml(root);
		this.latitude = Float.valueOf(root.elementText("Latitude"));
		this.longitude = Float.valueOf(root.elementText("Longitude"));
		this.precision = Float.valueOf(root.elementText("Precision"));

	}
}
