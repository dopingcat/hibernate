package chap01.crud;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import chap01.vo.Member;
import util.HibernateUtil;

public class MemberDao {
	private SessionFactory factory;

	public MemberDao() {
		factory = HibernateUtil.getSessionFactory();
	}

	public List<Member> selectList() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from MemberVo");
		List<Member> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public void delete(Member vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(vo);
		session.getTransaction().commit();
	}

	public void update(Member vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(vo);
		session.getTransaction().commit();
	}

	public Member selectById(int i) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Member selectedMember = (Member) session.get(Member.class, i);
		session.getTransaction().commit();
		return selectedMember;
	}

	public void insert(Member vo) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.save(vo);
		session.getTransaction().commit();
	}
}
