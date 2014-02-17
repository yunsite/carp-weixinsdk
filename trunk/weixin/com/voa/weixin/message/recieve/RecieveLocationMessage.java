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
 * 对应地理位置消息
 * @author zhaiyuxin
 *
 */
public class RecieveLocationMessage extends RecieveMessage {

	public RecieveLocationMessage(String msgXml, Element root) {
		super(msgXml, root);
		this.handlerType = MessageType.HANDLER_TYPE_LOCATION;
	}

	private Float locationX;
	private Float locationY;
	private Integer scale;
	private String label;

	public Float getLocationX() {
		return locationX;
	}

	public void setLocationX(Float locationX) {
		this.locationX = locationX;
	}

	public Float getLocationY() {
		return locationY;
	}

	public void setLocationY(Float locationY) {
		this.locationY = locationY;
	}

	public Integer getScale() {
		return scale;
	}

	public void setScale(Integer scale) {
		this.scale = scale;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	@Override
	public void parseXml(Element root) {
		this.locationX = Float.valueOf(root.elementText("Location_X"));
		this.locationY = Float.valueOf(root.elementText("Location_Y"));
		this.scale = Integer.valueOf(root.elementText("Scale"));
		this.label = root.elementText("Label");
	}

}
