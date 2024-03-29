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
package com.voa.weixin.db;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.voa.weixin.Carp;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class WeixinSessionFactory {
	private static final Log log = LogFactory
			.getLog(WeixinSessionFactory.class);

	private static SessionFactory sessionFactory;
	private static WeixinSessionFactory weixinSessionFactory;

	private WeixinSessionFactory() {

	}

	public static WeixinSessionFactory getInstanc() {
		if (weixinSessionFactory == null) {
			weixinSessionFactory = new WeixinSessionFactory();
		}

		return weixinSessionFactory;
	}

	public void init() throws Exception {

		Configuration config = new Configuration();
		File configFile = new File(Carp.ROOTPATH+"db.config.xml");
		
		config.configure(configFile);
		sessionFactory = config.buildSessionFactory();
		log.info("init end");
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void release() {

		sessionFactory.close();

	}
}
