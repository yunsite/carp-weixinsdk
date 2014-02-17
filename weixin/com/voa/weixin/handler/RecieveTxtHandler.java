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

import java.util.UUID;

import com.voa.weixin.message.MessageType;
import com.voa.weixin.message.recieve.RecieveTxtMessage;
import com.voa.weixin.model.ChartTranscripts;
import com.voa.weixin.service.ChartService;

/**
 * text类型信息的操作类，保存text信息到数据库
 * @author zhaiyuxin
 *
 */
public class RecieveTxtHandler extends Handler {

	@Override
	public void process() throws Exception {
		RecieveTxtMessage txtMsg = (RecieveTxtMessage) this.recieveMsg;

		ChartTranscripts chart = new ChartTranscripts();
		chart.setFid(UUID.randomUUID().toString());
		chart.setCreateTime(txtMsg.getCreateTime());
		chart.setFromUser(txtMsg.getFromUserName());
		chart.setToUser(txtMsg.getToUserName());
		chart.setFtype(MessageType.HANDLER_TYPE_TEXT);
		chart.setMessage(txtMsg.getContent());

		ChartService service = new ChartService();
		service.saveChatTrancripts(chart);
	}

}
