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
package com.voa.weixin.task;

/**
 * 回调工作的基类
 * @author zhaiyuxin
 *
 */
public abstract class Work {

	protected WeixinResult weixinResult;
	protected Task task;

	public void setWeixinResult(WeixinResult result) {
		this.weixinResult = result;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Task getTask() {
		return this.task;
	}

	public abstract void toDo() throws WorkException;

	public abstract void failedToDo() throws WorkException;

}
