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
package com.voa.weixin.work;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.Carp;
import com.voa.weixin.task.TaskManager;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 获得access_token的回调工作
 * @author zhaiyuxin
 *
 */
public class TokenWork extends Work {

	private static final Log logger = LogFactory.getLog(TokenWork.class);

	@Override
	public void toDo() throws WorkException {
		String token = weixinResult.getString("access_token");

		Carp.token = token;

		TaskManager.getInstance().setTokenStatus(false);

	}

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}
}
