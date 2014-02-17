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

import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mchange.v2.c3p0.DataSources;

/**
 * 
 * @author zhaiyuxin
 *
 */
public class WeixinHibernateUtil {
	private static final Log log = LogFactory.getLog(WeixinHibernateUtil.class);

	private static SessionFactory sessionFactory = WeixinSessionFactory
			.getSessionFactory();

	public static final ThreadLocal<Session> tLocalsess = new ThreadLocal<Session>();
	public static final ThreadLocal<Transaction> tLocaltx = new ThreadLocal<Transaction>();

	/**
	 * 获得一个Hibernate Session
	 * 
	 * @return
	 */
	public static Session getSession() throws HibernateException {
		Session session = tLocalsess.get();

		if (session == null) { //判断是否为空，如果为空，则产生新的
			session = sessionFactory.openSession();
			tLocalsess.set(session);

		} else {
			if (!session.isOpen()) { //不为空，但session已关闭，产生新的
				session = sessionFactory.openSession();
				tLocalsess.set(session);
			} else if (!session.isConnected()) { //不为空，session未关闭，但连接中断，则关闭原session，并产生新的
				session.close();
				session = sessionFactory.openSession();
				tLocalsess.set(session);
			}
		}

		return session;
	}

	public static Session getTransactSession() throws HibernateException {
		beginTransaction();
		return getSession();
	}

	public static void beginTransaction() throws HibernateException {
		Transaction tx = tLocaltx.get();

		if (tx == null) {
			tx = getSession().beginTransaction();
			tLocaltx.set(tx);
		}

	}

	public static void commitTransaction() throws HibernateException {
		Transaction tx = tLocaltx.get();

		if (tx == null) {
			tx = getSession().beginTransaction();
			tx.commit();
		} else if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack())
			tx.commit();
		tLocaltx.set(null);

	}

	public static void closeSession() throws HibernateException {

		Session session = tLocalsess.get();
		tLocalsess.set(null);

		if (session != null && session.isOpen()) {
			session.close();
		}

	}

	public static void rollbackTransaction() throws HibernateException {
		Transaction tx = tLocaltx.get();

		tLocaltx.set(null);

		if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
			tx.rollback();
		}

	}

	public static void shutDown() {
		closeSession();
		tLocalsess.set(null);
		tLocalsess.remove();
		tLocaltx.set(null);
		tLocaltx.remove();

		Session currSession = sessionFactory.getCurrentSession();
		if (currSession != null)
			currSession.close();
		sessionFactory.close();

		try {
			DataSources.unpooledDataSource();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		log.info("WeixinHibernateUtil shutDown!");
	}
}
