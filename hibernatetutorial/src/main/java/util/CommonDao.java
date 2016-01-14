package util;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;

public class CommonDao<T> {
	private SessionFactory factory;
	private Class<?> clazz;
	private String boardName;

	public CommonDao(Class<?> clazz) {
		factory = HibernateTestUtil.getSessionFactory(clazz);
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

	public int deleteAll() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		int result = session.createQuery("delete from " + boardName).executeUpdate();
		session.getTransaction().commit();
		return result;
	}

	public long count() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		long cnt = (Long) session.createCriteria(clazz).setProjection(Projections.rowCount()).uniqueResult();
		session.getTransaction().commit();
		return cnt;
	}

	public List<?> getPagedList(int page, int viewSize) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from " + boardName + " order by id asc");
		query.setFirstResult((page-1)*viewSize);	// (page-1) * viewSize	[e.g. 0, 10, 20...]
		query.setMaxResults(viewSize);				// viewSize				[e.g. 9, 19, 29...]
		List<?> list = query.list();
		session.getTransaction().commit();
		return list;
	}
}