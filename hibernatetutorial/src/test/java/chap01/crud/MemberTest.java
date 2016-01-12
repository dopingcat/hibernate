package chap01.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import chap01.vo.Member;
import util.HibernateUtil;

public class MemberTest {
	private static final String HELLO_HIBERNATE = "hello hibernate";
	private static final String HELLO_WORLD = "hello world";
	private static final String VWAN = "vwan";

	SessionFactory factory = HibernateUtil.getSessionFactory();

	@Test
	public void test() {
		// Insert
		System.out.println("\n[insert]");
		insert();

		// Select One
		System.out.println("\n[select by ID]");
		Member selectedMember = selectById(1);
		assertEquals(VWAN, selectedMember.getName());

		// Select all
		System.out.println("\n[select all]");
		List<Member> list = selectAll();
		System.out.println(list);

		// Update
		System.out.println("\n[update]");
		selectedMember.setMessage(HELLO_HIBERNATE);
		update(selectedMember);

		// Confirm Update
		System.out.println("\n[confirm update]");
		Member updatedMember = selectById(1);
		assertEquals(HELLO_HIBERNATE, updatedMember.getMessage());

		// Delete
		System.out.println("\n[delete]");
		delete(updatedMember);

		// Confirm Delete
		System.out.println("\n[confirm delete]");
		Member deleteMember = selectById(1);
		assertNull(deleteMember);
	}

	public void insert() {
		Member member = new Member(VWAN, HELLO_WORLD);
		insert(member);

		member = new Member("Dopingcat", HELLO_HIBERNATE);
		insert(member);

		member = new Member("Test1", "Test1");
		insert(member);

		member = new Member("Test2", "Test2");
		insert(member);
	}

	public List<Member> selectAll() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Member");
		List<Member> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public void delete(Member updatedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(updatedMember);
		session.getTransaction().commit();
	}

	public void update(Member selectedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(selectedMember);
		session.getTransaction().commit();
	}

	public Member selectById(int i) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Member selectedMember = (Member) session.get(Member.class, i);
		session.getTransaction().commit();

		return selectedMember;
	}

	public void insert(Member member) {
		Session session = factory.getCurrentSession();
		// Transaction begin
		session.beginTransaction();
		// Bussiness
		session.save(member);
		// Transaction commit
		session.getTransaction().commit();
	}
}