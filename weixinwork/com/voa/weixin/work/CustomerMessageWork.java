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

import com.voa.weixin.task.Work;
import com.voa.weixin.task.WorkException;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class CustomerMessageWork extends Work {

	@Override
	public void toDo() throws WorkException {
		System.out.println(this.weixinResult.getJson());
	}

	@Override
	public void failedToDo() throws WorkException {
		// TODO Auto-generated method stub

	}

}
