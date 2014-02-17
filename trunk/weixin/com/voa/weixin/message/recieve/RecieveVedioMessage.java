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
 * 对应视频消息
 * @author zhaiyuxin
 *
 */
public class RecieveVedioMessage extends RecieveMessage {

	public RecieveVedioMessage(String msgXml, Element root) {
		super(msgXml, root);
		this.handlerType = MessageType.HANDLER_TYPE_VIDEO;
	}

	private String mediaId;
	private String thumbMediaId;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getThumbMediaId() {
		return thumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public void parseXml(Element root) {
		this.mediaId = root.elementText("MediaId");
		this.thumbMediaId = root.elementText("ThumbMediaId");
	}

}
