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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.voa.weixin.utils.WeixinUtils;

/**
 * 客服任务仓库
 * @author zhaiyuxin
 *
 */
public class TaskRepertory {

	private final Log logger = LogFactory.getLog(TaskRepertory.class);

	/** 任务集合 **/
	private final Map<String, Task> tasks;
	/** 回调工作集合 **/
	private final Map<String, String> taskWorkNames;

	private static TaskRepertory repertory;

	private TaskRepertory() {
		tasks = new HashMap<String, Task>();
		taskWorkNames = new HashMap<String, String>();
	}

	public static TaskRepertory getInstance() {
		if (repertory == null)
			repertory = new TaskRepertory();

		return repertory;
	}

	/**
	 * 初始化，读取weixin.task.xml文件
	 * @throws Exception
	 */
	public void init() throws Exception {
		Document doc = WeixinUtils.getDocumentResource("weixin.task.xml");

		if (doc == null)
			return;

		List<Element> taskEs = doc.getRootElement().elements("task");

		for (Element taskE : taskEs) {
			String name = taskE.elementText("name");
			String url = taskE.elementText("url");
			String method = taskE.elementText("method");
			String workClzName = taskE.elementText("work");
			String instance = taskE.elementText("instance");
			String clzName = taskE.elementText("class");

			Task task = null;
			try {
				if (clzName == null || clzName.equals("com.voa.weixin.task")) {
					task = new CommonTask();
				} else {
					Class taskClz = Class.forName(clzName);
					task = (Task) taskClz.newInstance();
				}
			} catch (Exception e) {
				logger.warn("task class not found : " + clzName);
			}

			task.setName(name);
			task.setUrl(url);
			task.setMethod(method == null ? "post" : method);
			task.setInstance(instance == null ? "multi" : instance);

			if (StringUtils.isNotEmpty(workClzName)) {
				try {
					Class workClz = Class.forName(workClzName);
					Work work = (Work) workClz.newInstance();
					tasks.put(name, task);
					taskWorkNames.put(name, workClzName);
				} catch (ClassNotFoundException e) {
					logger.warn("work class not found : " + workClzName);
				} catch (Exception e) {
					logger.warn("work class not be instance : " + workClzName);
				}

			} else {
				tasks.put(name, task);
			}
		}
	}

	/**
	 * 根据名称获得任务
	 * @param name
	 * @return
	 * @throws TaskException
	 */
	public Task getTaskByName(String name) throws TaskException {
		Task task = tasks.get(name);

		if (task != null) {
			if (!task.getInstance().equals("single"))
				task = task.clone();
			String workClzName = taskWorkNames.get(name);

			if (workClzName != null) {
				try {
					Class workClz = Class.forName(workClzName);
					Work work = (Work) workClz.newInstance();
					task.setWork(work);
				} catch (Exception e) {
					throw new TaskException("task's work class is error.", e);
				}
			}
		} else {
			throw new TaskException("task not found : " + name);
		}

		return task;
	}

	/**
	 * 根据url获得任务
	 * @param url
	 * @return
	 * @throws Exception
	 */
	public Task getTaskByUrl(String url) throws Exception {
		Collection<Task> taskEntities = tasks.values();
		Task target = null;
		for (Task task : taskEntities) {
			if (task.getUrl().equalsIgnoreCase(url)) {
				target = task;
				break;
			}
		}

		if (target != null)
			return getTaskByName(target.getName());
		else
			throw new TaskException("url not found : " + url);
	}
}
