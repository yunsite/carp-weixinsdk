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

import java.io.File;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.Carp;
import com.voa.weixin.utils.LogUtil;

/**
 * 上传文件任务
 * @author zhaiyuxin
 *
 */
public class UpdateFileTask extends Task {

	public static final String TYPE_IMG = "image";
	public static final String TYPE_VOICE = "voice";
	public static final String TYPE_VIDEO = "video";
	public static final String TYPE_THUMB = "thumb";

	private static final Log logger = LogFactory.getLog(UpdateFileTask.class);

	private File updateFile;

	private String type;

	public String getType() {
		return type;
	}

	public File getUpdateFile() {
		return updateFile;
	}

	private void setFile(File file) {
		this.updateFile = file;
		if (file != null && file.exists() && file.isFile()) {
			long fileLength = file.length();
			long limitLength = 0;
			if (this.type.equals(TYPE_IMG)) {
				limitLength = 128 * 1024;
			} else if (this.type.equals(TYPE_VOICE))
				limitLength = 256 * 1024;
			else if (this.type.equals(TYPE_VIDEO))
				limitLength = 1024 * 1024;
			else
				limitLength = 64 * 1024;

			if (fileLength > limitLength) {
				logger.error("update file : " + file.getName()
						+ ",size out of limit " + (limitLength / 1024) + "k");
				throw new TaskException("update file out of limit");
			}
		}
	}

	public void setImgFile(File imgFile) {
		this.type = TYPE_IMG;
		this.setFile(imgFile);
	}

	public void setVoiceFile(File voiceFile) {
		this.type = TYPE_VOICE;
		this.setFile(voiceFile);

	}

	public void setVideoFile(File videoFile) {
		this.type = TYPE_VIDEO;
		this.setFile(videoFile);
	}

	public void setThumbFile(File thumbFile) {
		this.type = TYPE_THUMB;
		this.setFile(thumbFile);
	}

	@Override
	public void generateUrl() {
		this.url = StringUtils.replace(url, "ACCESS_TOKEN", Carp.token);
		this.url = StringUtils.replace(url, "TYPE", this.type);
	}

	@Override
	public void run() {
		logger.debug("updatefile url :" + this.url);
		generateUrl();
		PostMethod filePost = new PostMethod(url);
		try {
			Part[] parts = { new FilePart(updateFile.getName(), updateFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,
					filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(
					5000);
			int status = client.executeMethod(filePost);
			WeixinResult result = new WeixinResult();
			if (status == 200) {
				String responseStr = filePost.getResponseBodyAsString();
				logger.debug(responseStr);
				result.setJson(responseStr);
			} else {
				result
						.setErrMsg("uplaod file weixin request error , http status : "
								+ status);
			}

			callbackWork(result);

		} catch (Exception e) {
			e.printStackTrace(LogUtil.getErrorStream(logger));
			throw new WorkException("update file error.", e);
		} finally {
			filePost.releaseConnection();
		}
	}
}
