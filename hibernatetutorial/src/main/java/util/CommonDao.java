package util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class CommonDao<T> {
	private SessionFactory factory;
	private Class<?> clazz;
	private String boardName;

	public CommonDao(Class<?> clazz) {
		factory = HibernateUtil.getSessionFactory();
		this.clazz = clazz;
		this.boardName = clazz.getSimpleName();
	}

	public List<?> selectList() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from " + boardName);
		List<?> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public T selectById(int id) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		T vo = (T) session.get(clazz, id);
		session.getTransaction().commit();
		return vo;
	}

	public void delete(T vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(vo);
		session.getTransaction().commit();
	}

	public void update(T vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(vo);
		session.getTransaction().commit();
	}

	public void insert(T vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(vo);
		session.getTransaction().commit();
	}
}