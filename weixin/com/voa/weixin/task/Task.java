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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.Carp;
import com.voa.weixin.model.WeixinTaskMessage;
import com.voa.weixin.utils.HttpUtils;

/**
 * 发送客服消息的任务基类，
 * @author zhaiyuxin
 *
 */
public abstract class Task implements Runnable, TaskUrl {

	private static final Log logger = LogFactory.getLog(Task.class);

	public static final int SUCCESS_CODE = 0;

	public static final String METHOD_POST = "post";
	public static final String MEHTOD_GET = "get";

	protected String url;
	protected WeixinTaskMessage message;
	protected Work work;
	protected String name;
	protected String method;
	protected String instance;
	protected int repeatCount;

	public Task() {
		repeatCount = 0;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getInstance() {
		return instance;
	}

	public void setInstance(String instance) {
		this.instance = instance;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public WeixinTaskMessage getMessage() {
		return message;
	}

	public void setMessage(WeixinTaskMessage message) {
		this.message = message;
	}

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public String getUrl() {
		return url;
	}

	public void send() throws WorkException {
		repeatCount++;
		TaskManager.getInstance().addTask(this, true);
	}

	public void send(boolean isSynchronized) throws WorkException {
		repeatCount++;
		TaskManager.getInstance().addTask(this, isSynchronized);
	}

	/**
	 * 替换所需的access_token
	 */
	public void changeToken() {
		if (StringUtils.contains(url, "ACCESS_TOKEN")) {
			url = StringUtils.replace(url, "ACCESS_TOKEN", Carp.token);
		} else {
			String oldToken = StringUtils.substringAfter(url, "access_token=");
			if (StringUtils.contains(oldToken, "&")) {
				oldToken = StringUtils.substringBetween(url, "access_token=",
						"&");
			}

			url = StringUtils.replace(url, oldToken, Carp.token);

		}
	}

	/**
	 * 同步或异步的调用
	 */
	@Override
	public void run() {

		String responseStr = null;
		String sendMessage = "";
		if (message != null)
			sendMessage = message.toJson();
		try {
			url = StringUtils.replace(url, "ACCESS_TOKEN", Carp.token);
			generateUrl();
			logger.debug("url : " + url);
			if (method.equals(METHOD_POST))
				responseStr = HttpUtils.doHttpsPost(url, sendMessage);
			else
				responseStr = HttpUtils.doHttpsGet(url, sendMessage);
			
			logger.debug("json result : " + responseStr);

			WeixinResult result = new WeixinResult();
			result.setJson(responseStr);

			callbackWork(result);
		} catch (Exception e) {
			logger.error("url error : " + url);
			e.printStackTrace();
			throw new WorkException("url error : " + url, e);
		}

		
	}

	/**
	 * 任务完成后的回调工作
	 * @param result
	 */
	protected void callbackWork(WeixinResult result) {
		if (work != null) {
			work.setWeixinResult(result);
			work.setTask(this);
			if (result.getErrorCode() != SUCCESS_CODE) {
				work.failedToDo();
			} else
				work.toDo();
		}
	}

	public int getRepeatCount() {
		return this.repeatCount;
	}

	/**
	 * 任务的克隆方法，用来复制一个新任务
	 */
	@Override
	public Task clone() {
		Task newTask = null;
		try {
			Class clz = Class.forName(this.getClass().getName());
			newTask = (Task) clz.newInstance();
			newTask.setName(this.name);
			newTask.setUrl(this.url);
			newTask.setWork(this.work);
			newTask.setMethod(this.method);
			newTask.setInstance(this.instance);
			newTask.setMessage(this.message);
		} catch (Exception e) {
			throw new TaskException("clone task error.", e);
		}
		return newTask;
	}

	/**
	 * 处理url，如果需要的话
	 */
	@Override
	public abstract void generateUrl();

	
	@Override
	public void generateUrl(String[] keys, String[] values) {
		if (keys != null) {
			for (int i = 0; i < keys.length; i++) {
				this.url = StringUtils.replace(this.url, keys[i], values[i]);
			}
		}
	}
}
