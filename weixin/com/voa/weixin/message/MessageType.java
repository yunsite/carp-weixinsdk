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
package com.voa.weixin.message;

/**
 * 消息类型和操作类型
 * @author zhaiyuxin
 *
 */
public class MessageType {

	public static final String TYPE_TEXT = "text";
	public static final String TYPE_IMAGE = "image";
	public static final String TYPE_VOICE = "voice";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_MUSIC = "music";
	public static final String TYPE_NEWS = "news";
	public static final String TYPE_LOCATION = "location";
	public static final String TYPE_LINK = "link";
	public static final String TYPE_EVENT = "event";

	public static final String EVENT_SUBSCRIBE = "subscibe";
	public static final String EVENT_UNSUBSCRIBE = "unsubscribe";
	public static final String EVENT_SUBSCRIBE_QRSCENE = "subscribe_qrscene";
	public static final String EVENT_SCAN = "scan";
	public static final String EVENT_LOCATION = "location";
	public static final String EVENT_CLICK = "click";

	public static final String HANDLER_TYPE_TEXT = "type_text";
	public static final String HANDLER_TYPE_IMAGE = "type_image";
	public static final String HANDLER_TYPE_VOICE = "type_voice";
	public static final String HANDLER_TYPE_VIDEO = "type_video";
	public static final String HANDLER_TYPE_NEWS = "type_news";
	public static final String HANDLER_TYPE_LOCATION = "type_location";
	public static final String HANDLER_TYPE_LINK = "type_link";
	//	public static final String HANDLER_TYPE_EVENT = "type_event";
	public static final String HANDLER_EVENT_SUBSCRIBE = "event_subscribe";
	public static final String HANDLER_EVENT_UNSUBSCRIBE = "event_unsubscribe";
	public static final String HANDLER_EVENT_SUBSCRIBE_QRSCENE = "event_subscribe_qrscene";
	public static final String HANDLER_EVENT_SCAN = "event_scan";
	public static final String HANDLER_EVENT_LOCATION = "event_location";
	public static final String HANDLER_EVENT_CLICK = "event_click";
}
