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
package com.voa.weixin;

import com.voa.weixin.db.WeixinSessionFactory;
import com.voa.weixin.mission.MissionManager;
import com.voa.weixin.task.Task;
import com.voa.weixin.task.TaskManager;
import com.voa.weixin.task.TaskRepertory;

/**
 * 应用的服务入口，用来初始化和注销各种微信所需的服务与监听
 * 
 * @author zhaiyuxin
 * 
 */
public class Carp {

	public static String token;

	private WeixinCache weixinCache;

	public static String ROOTPATH;

	private static Carp carp;

	private TaskRepertory taskRepertory;

	private MissionManager missionManager;

	private Carp() {
	}

	public static Carp getInstance() {
		if (carp == null) {
			carp = new Carp();
		}

		return carp;
	}

	public void init() throws Exception {

		weixinCache = new WeixinCache();

		WeixinSessionFactory.getInstanc().init();

		taskRepertory = TaskRepertory.getInstance();
		taskRepertory.init();

		initTocken();

		missionManager = MissionManager.getInstance();
	}

	private void initTocken() throws Exception {
		Task task = taskRepertory.getTaskByName("token");
		task.send(true);
	}

	public TaskRepertory getTaskRepertory() {
		return this.taskRepertory;
	}

	public MissionManager getMissionManager() {
		return this.missionManager;
	}

	public WeixinCache getWeixinCache() {
		return this.weixinCache;
	}
	
	public void destroy(){
		TaskManager.getInstance().destroy();
		MissionManager.getInstance().destroy();
	}

}
