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

import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;

import com.voa.weixin.Carp;

/**
 * 下载文件任务
 * @author zhaiyuxin
 *
 */
public class DownloadFileTask extends Task {

	private String mediaId;
	private InputStream inputStream;
	private String fileName;
	private int fileLength;

	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public void generateUrl() {
		this.url = StringUtils.replace(url, "ACCESS_TOKEN", Carp.token);
		this.url = StringUtils.replace(url, "MEDIA_ID", this.mediaId);
	}

	@Override
	public void run() {
		generateUrl();
		HttpClient client = new HttpClient();
		GetMethod httpGet = new GetMethod(this.url);

		WeixinResult result = new WeixinResult();
		try {
			client.executeMethod(httpGet);
			String contentType = httpGet.getResponseHeader("Content-Type")
					.getValue();
			if (contentType.equals("text/plain")) {
				String json = httpGet.getResponseBodyAsString();
				result.setJson(json);
			} else {
				fileName = StringUtils.substringBetween(httpGet
						.getResponseHeader("Content-Type").getValue(),
						"filename=", "\"");
				fileLength = Integer.parseInt(httpGet.getResponseHeader(
						"Content-Length").getValue());
				result.setErrorCode(0);
				this.inputStream = httpGet.getResponseBodyAsStream();
			}
			callbackWork(result);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WorkException("download file error.", e);
		} finally {
			if (inputStream != null)
				try {
					inputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			httpGet.releaseConnection();

		}
	}

	public InputStream getInputStream() {
		return this.inputStream;
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getFileLength() {
		return this.fileLength;
	}
}
