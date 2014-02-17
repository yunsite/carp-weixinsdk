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

import com.voa.weixin.model.GroupFans;
import com.voa.weixin.service.FansInfoService;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 获得组信息的回调工作
 * @author zhaiyuxin
 *
 */
public class GroupIdForFansWork extends Work {

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}

	@Override
	public void toDo() throws WorkException {
		GroupFans groupFans = (GroupFans) task.getMessage();

		String groupId = weixinResult.getString("groupid");
		groupFans.setGroupId(groupId);

		FansInfoService service = new FansInfoService();
		service.saveOrUpdateGroupFans(groupFans);
	}

}
