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
package com.voa.weixin.service;

import com.voa.weixin.db.WeixinCommonDao;
import com.voa.weixin.db.WeixinDao;
import com.voa.weixin.db.WeixinHibernateUtil;
import com.voa.weixin.model.FansInfo;
import com.voa.weixin.model.GroupFans;
import com.voa.weixin.model.WeixinGroup;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class FansInfoService extends WeixinDao {

	/**
	 * 根据openId获得用户信息
	 * @param openId
	 * @return
	 */
	public FansInfo findFansInfoByOpenId(String openId) {
		FansInfo fans = null;
		try {
			FansInfoDao dao = new FansInfoDao();
			fans = dao.findFansInfoByOpenId(openId);
		} finally {
			WeixinHibernateUtil.closeSession();
		}
		return fans;
	}

	/**
	 * 保存用户信息
	 * @param fans
	 */
	public void saveOrUpdateFansInfo(FansInfo fans) {
		WeixinCommonDao dao = new WeixinCommonDao();
		dao.saveOrUpdateObject(fans);
	}

	/**
	 * 保存分组信息
	 * @param group
	 */
	public void saveOrUpdateGroup(WeixinGroup group) {
		WeixinCommonDao dao = new WeixinCommonDao();
		dao.saveOrUpdateObject(group);
	}

	/**
	 * 保存用户的分组信息
	 * @param groupFans
	 */
	public void saveOrUpdateGroupFans(GroupFans groupFans) {
		WeixinCommonDao dao = new WeixinCommonDao();
		dao.saveOrUpdateObject(groupFans);
	}
}
