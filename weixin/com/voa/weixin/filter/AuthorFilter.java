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
package com.voa.weixin.filter;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.voa.weixin.Carp;
import com.voa.weixin.db.WeixinSessionFactory;
import com.voa.weixin.handler.HandlerManager;
import com.voa.weixin.handler.SessionManager;
import com.voa.weixin.utils.LogUtil;
import com.voa.weixin.utils.WeixinUtils;

/**
 * 部署在web服务，当用户向公众号发送消息和事件时，接受微信服务器的信息
 * @author zhaiyuxin
 *
 */
public class AuthorFilter extends HttpServlet {

	private static final Log logger = LogFactory.getLog(AuthorFilter.class);
	private static final long serialVersionUID = -2440724656567941748L;
	private static String token;

	@Override
	public void init() throws ServletException {
		token = getInitParameter("TOKEN");
		logger.debug("token : "+token);
		Carp.ROOTPATH = getServletContext().getRealPath("/") + File.separator
				+ "WEB-INF" + File.separator;

		HandlerManager.getInstance();
		SessionManager.getInstance();
		try {
			System.out.println("1");
			WeixinSessionFactory.getInstanc().init();
			System.out.println("2");
			Carp.getInstance().init();
			System.out.println("3");
		} catch (Exception e) {
			throw new ServletException(e);
		}
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String signature = request.getParameter("signature");// 微信加密签名
		String timestamp = request.getParameter("timestamp");// 时间戳
		String nonce = request.getParameter("nonce");// 随机数
		String echostr = request.getParameter("echostr");// 随机字符串
		try {
			logger.debug("signatrue : "+signature);
			logger.debug("timestamp : "+timestamp);
			
			boolean checked = WeixinUtils.checkAuthentication(signature,
					timestamp, nonce, echostr, token);
			if (checked) {
				String requestStr = IOUtils.toString(request.getInputStream(),
						"UTF-8");
				logger.debug("requestStr : " + requestStr);
				SessionManager.getInstance().buildSession(requestStr, response);

			} else {
				logger.warn("host:" + request.getRemoteHost()
						+ " request content cant't checked.");

			}
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace(LogUtil.getErrorStream(logger));
		}

	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
