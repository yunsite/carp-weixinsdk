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

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 * 一个通用dao，完成常用的数据库操作
 * @author zhaiyuxin
 *
 */
public class WeixinCommonDao {

	public Object getObjectById(Class clazz, Serializable id)
			throws HibernateException {
		Object object = null;
		Session session = null;
		try {
			session = WeixinHibernateUtil.getSession();
			object = session.get(clazz, id);
		} finally {
			WeixinHibernateUtil.closeSession();
		}

		return object;
	}

	public void saveObject(Object o) throws HibernateException {
		Session s = null;
		try {
			s = WeixinHibernateUtil.getTransactSession();
			WeixinHibernateUtil.beginTransaction();
			s.save(o);
			WeixinHibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			WeixinHibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			WeixinHibernateUtil.closeSession();
		}

	}

	public void deleteObject(Object obj) throws HibernateException {
		Session s = null;
		try {
			s = WeixinHibernateUtil.getTransactSession();
			WeixinHibernateUtil.beginTransaction();
			s.delete(obj);
			WeixinHibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			WeixinHibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			WeixinHibernateUtil.closeSession();
		}

	}

	public void deleteObject(Class clazz, Serializable id)
			throws HibernateException {
		Session s = null;
		try {
			s = WeixinHibernateUtil.getSession();
			Object obj = s.get(clazz, id);
			WeixinHibernateUtil.beginTransaction();
			s.delete(obj);
			WeixinHibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			WeixinHibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			WeixinHibernateUtil.closeSession();
		}
	}

	public void updateObject(Object obj) throws HibernateException {
		Session s = null;
		try {
			s = WeixinHibernateUtil.getTransactSession();
			WeixinHibernateUtil.beginTransaction();
			s.update(obj);
			WeixinHibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			WeixinHibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			WeixinHibernateUtil.closeSession();
		}
	}

	public void saveOrUpdateObject(Object obj) throws HibernateException {
		Session s = null;
		try {
			s = WeixinHibernateUtil.getTransactSession();
			WeixinHibernateUtil.beginTransaction();
			s.saveOrUpdate(obj);
			WeixinHibernateUtil.commitTransaction();
		} catch (HibernateException e) {
			WeixinHibernateUtil.rollbackTransaction();
			throw e;
		} finally {
			WeixinHibernateUtil.closeSession();
		}
	}

}
