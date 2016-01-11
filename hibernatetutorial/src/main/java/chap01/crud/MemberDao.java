package chap01.crud;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import chap01.vo.MemberVo;
import util.HibernateUtil;

public class MemberDao {
	private SessionFactory factory;

	public MemberDao() {
		factory = HibernateUtil.getSessionFactory();
	}

	public List<MemberVo> selectList() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from MemberVo");
		List<MemberVo> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public void delete(MemberVo vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(vo);
		session.getTransaction().commit();
	}

	public void update(MemberVo vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(vo);
		session.getTransaction().commit();
	}

	public MemberVo selectById(int i) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		MemberVo selectedMember = (MemberVo) session.get(MemberVo.class, i);
		session.getTransaction().commit();
		return selectedMember;
	}

	public void insert(MemberVo vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(vo);
		session.getTransaction().commit();
	}
}
