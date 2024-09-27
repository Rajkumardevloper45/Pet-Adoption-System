package com.demo.project.dao.impl;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.demo.project.dao.AdminDao;
import com.demo.project.helper.*;
import com.demo.project.entity.*;

public class AdminDaoImpl implements AdminDao {

	@Override
	public boolean login(String username, String password) {
		Session session = null;
		try {
			session = HibernateHelper.getSessionFactory().openSession();
			String hql = "FROM Admin WHERE username = :username AND password = :password";
			Query query = session.createQuery(hql);
			query.setParameter("username", username);
			query.setParameter("password", password);

			Admin admin = (Admin) query.uniqueResult();

			return admin != null;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			if (session != null)
				session.close();
		}
	}
}