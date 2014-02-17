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

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.model.FansInfo;
import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 下载文件的回调工作
 * @author zhaiyuxin
 *
 */
public class FansInfoWork extends Work {

	private static final Log logger = LogFactory.getLog(FansInfoWork.class);

	@Override
	public void toDo() throws WorkException {
		FansInfo fansInfo = new FansInfo();
		fansInfo.setOpenId(weixinResult.getString("openid"));
		fansInfo.setSubscribe(weixinResult.getInt("subscribe"));
		fansInfo.setNickName(weixinResult.getString("nickname"));
		fansInfo.setSex(weixinResult.getInt("sex"));
		fansInfo.setLanguage(weixinResult.getString("language"));
		fansInfo.setCity(weixinResult.getString("city"));
		fansInfo.setProvince(weixinResult.getString("province"));
		fansInfo.setCountry(weixinResult.getString("country"));
		fansInfo.setHeadimgUrl(weixinResult.getString("headimgurl"));
		long createTime = weixinResult.getLong("subscribe_time") * 1000;
		fansInfo.setSubscribeTime(new Date(createTime));
		logger.debug(fansInfo.getNickName() + ": openId = "
				+ fansInfo.getOpenId());
	}

	@Override
	public void failedToDo() throws WorkException {

	}
}
