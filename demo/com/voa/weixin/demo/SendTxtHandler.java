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
package com.voa.weixin.demo;

import java.util.UUID;

import com.voa.weixin.handler.Handler;
import com.voa.weixin.message.MessageType;
import com.voa.weixin.message.send.SendTxtMessage;
import com.voa.weixin.model.ChartTranscripts;
import com.voa.weixin.service.ChartService;

/**
 * 对公众号进行回复的测试类，并保存信息到数据库
 * @author zhaiyuxin
 *
 */
public class SendTxtHandler extends Handler {

	@Override
	public void process() throws Exception {
		SendTxtMessage msg = new SendTxtMessage(this.recieveMsg);
		msg.setContent("测试回复");
		this.sendMsg = msg;

		ChartTranscripts chart = new ChartTranscripts();
		chart.setFid(UUID.randomUUID().toString());
		chart.setCreateTime(msg.getCreateTime());
		chart.setFromUser(msg.getFromUserName());
		chart.setToUser(msg.getToUserName());
		chart.setFtype(MessageType.HANDLER_TYPE_TEXT);
		chart.setMessage(msg.getContent());

		ChartService service = new ChartService();
		service.saveChatTrancripts(chart);

	}

}
