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

import java.io.File;

import com.voa.weixin.Carp;
import com.voa.weixin.model.TextMessage;
import com.voa.weixin.task.Task;

/**
 * 发送文本客服消息
 * 
 * @author zhaiyuxin
 * 
 */
public class SendCustomerMessage {

	public static void main(String[] args) throws Exception {
		Carp.ROOTPATH = System.getProperty("user.dir") + File.separator;
		Carp carp = Carp.getInstance();
		carp.init();

		Task task = carp.getTaskRepertory().getTaskByName("message");
		TextMessage message = new TextMessage();
		//设置用户的openId
		message.setTouser("oTMC0txGNUisZyU9lMpGu8K8TlsU");
		message.setTextContent("测试");
		task.setMessage(message);
		
		task.send();

		carp.destroy();
	}

}
