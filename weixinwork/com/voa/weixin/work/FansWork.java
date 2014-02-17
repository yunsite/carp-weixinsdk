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

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.service.FansInfoService;
import com.voa.weixin.task.FansInfoTask;
import com.voa.weixin.task.TaskRepertory;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;
import com.voa.weixin.utils.LogUtil;

/**
 * 获得所有用户的回调工作
 * @author zhaiyuxin
 *
 */
public class FansWork extends Work {

	private static final Log logger = LogFactory.getLog(FansWork.class);

	@Override
	public void toDo() throws WorkException {
		int total = weixinResult.getInt("total");
		int count = weixinResult.getInt("count");

		if (count == 0)
			return;

		Collection<String> openIds = weixinResult.getCollection("data/openid");

		FansInfoService service = new FansInfoService();
		for (String openId : openIds) {
			try {
				//FansInfo fansInfo = service.findFansInfoByOpenId(openId);
				logger.debug("openId : " + openId);
				//if (fansInfo == null) {
				logger.debug("begin get new fans");
				FansInfoTask fansinfoTask = (FansInfoTask) TaskRepertory
						.getInstance().getTaskByName("newfansinfo");
				fansinfoTask.setOpenId(openId);
				fansinfoTask.send();
			} catch (Exception e) {
				e.printStackTrace(LogUtil.getErrorStream(logger));
			}
		}

		String nextOpenId = weixinResult.getString("next_openid");

		task
				.setUrl("https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid="
						+ nextOpenId);
		task.send(true);

	}

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}
}
