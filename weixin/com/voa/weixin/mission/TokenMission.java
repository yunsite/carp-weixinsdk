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
package com.voa.weixin.mission;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.task.Task;
import com.voa.weixin.task.TaskRepertory;
import com.voa.weixin.utils.LogUtil;

/**
 * token的周期任务，用来在token过期前，重新获得有效token
 * @author zhaiyuxin
 *
 */
public class TokenMission extends Mission {

	private final Log logger = LogFactory.getLog(TokenMission.class);

	private long getTokenTime; //获得token时间

	public TokenMission(String name) {
		super(name);
		this.getTokenTime = new Date().getTime();
	}

	@Override
	public void run() {
		long now = new Date().getTime();

		if ((now - getTokenTime) > 5800 * 1000 || (this.getTokenTime == 0)) {
			getToken();
		}

	}

	public void getToken() {
		try {
			Task task = TaskRepertory.getInstance().getTaskByName("token");
			task.send(true);

			getTokenTime = new Date().getTime();
		} catch (Exception e) {
			e.printStackTrace(LogUtil.getErrorStream(logger));
		}
	}

	@Override
	public String getName() {
		return this.name;
	}
}
