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
package com.voa.weixin.utils;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;

import com.voa.weixin.Carp;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class WeixinUtils {

	/**
	 * 检查消息验证
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @param echostr
	 * @param token
	 * @return
	 */
	public static boolean checkAuthentication(String signature,
			String timestamp, String nonce, String echostr, String token) {
		//String result = "";
		boolean result = false;
		// 将获取到的参数放入数组
		String[] ArrTmp = { token, timestamp, nonce };
		// 按微信提供的方法，对数据内容进行排序
		Arrays.sort(ArrTmp);
		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < ArrTmp.length; i++) {
			sb.append(ArrTmp[i]);
		}
		// 对排序后的字符串进行SHA-1加密
		String pwd = "";
		try {
			pwd = DigestUtils.sha1Hex(sb.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
		result = pwd.equals(signature);
		return result;
	}

	public static Document getDocumentResource(String sourceName)
			throws Exception {
		String repertoryPath = Carp.ROOTPATH + sourceName;

		SAXReader reader = new SAXReader();
		Document doc = null;

		File repertoryFile = new File(repertoryPath);
		if (repertoryFile.exists() && repertoryFile.isFile()) {
			doc = reader.read(repertoryFile);
		} else {
			InputStream in = Carp.class.getResourceAsStream("/" + sourceName);
			doc = reader.read(in);
			in.close();
		}

		return doc;
	}

}
