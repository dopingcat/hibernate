package chap06.hql;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;

import chap06.vo.UserDetail;
import util.CommonDao;
import util.HibernateTestUtil;

public class HqlLearningTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		CommonDao<UserDetail> userDetailDao = new CommonDao<>(UserDetail.class);

		for(int i = 0; i < 10; i++) {
			userDetailDao.insert(new UserDetail("User" + i));
		}
		SessionFactory factory = HibernateTestUtil.getSessionFactory(UserDetail.class);
		Session session = factory.getCurrentSession();
		session.beginTransaction();

		// * HQL Part *
		String minUserid = "5";

		// 1. most simple
		//Query query = session.createQuery("from UserDetail");

		// 2. extend query
		//Query query = session.createQuery("from UserDetail where id > 5");

		// 3. variable injection
		// but this way has weakness about sql injection [a.k.a. Statement]
		//Query query = session.createQuery("from UserDetail where id > " + minUserid);

		// 4. like PreparedStatement
		/*
		 *  The difference with PreparedStatement is that
		 *  Index start with 0. [O.T.O.H. PreparedStatement start with 1.]
		 */
		//Query query = session.createQuery("from UserDetail where id > ? and userName = ?");
		//query.setInteger(0, Integer.parseInt(minUserid));
		//query.setString(1, "User9");

		// 5. using :[name]
		Query query = session.createQuery("from UserDetail where id > :userId and userName = :userName");
		query.setInteger("userId", Integer.parseInt(minUserid));
		query.setString("userName", "User9");

		List<UserDetail> list = query.list();
		session.getTransaction().commit();

		for(UserDetail userDetail : list) {
			System.out.println(userDetail);
		}
	}
}
