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

import java.util.List;

import org.hibernate.Query;

import com.voa.weixin.db.WeixinDao;
import com.voa.weixin.model.FansInfo;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class FansInfoDao extends WeixinDao {

	/**
	 * 根据openId获得用户信息
	 * @param openId
	 * @return
	 */
	public FansInfo findFansInfoByOpenId(String openId) {
		FansInfo fans = null;

		String hql = "from FansInfo where openId = ?";
		Query query = session.createQuery(hql.toString());
		query.setString(0, openId);

		List<FansInfo> fanses = query.list();

		if (null != fanses && fanses.size() > 0)
			fans = fanses.get(0);

		return fans;
	}
}
