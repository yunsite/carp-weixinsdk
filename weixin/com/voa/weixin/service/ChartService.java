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
import com.voa.weixin.model.ChartTranscripts;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class ChartService {

	/**
	 * 保存通话记录
	 * @param chart
	 */
	public void saveChatTrancripts(ChartTranscripts chart) {
		WeixinCommonDao dao = new WeixinCommonDao();
		dao.saveObject(chart);
	}

}
