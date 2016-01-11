package chap01.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

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
		MemberDao selectedMember = selectById(1);
		assertEquals(VWAN, selectedMember.getName());

		// Select all
		System.out.println("\n[select all]");
		List<MemberDao> list = selectAll();
		System.out.println(list);

		// Update
		System.out.println("\n[update]");
		selectedMember.setMessage(HELLO_HIBERNATE);
		update(selectedMember);

		// Confirm Update
		System.out.println("\n[confirm update]");
		MemberDao updatedMember = selectById(1);
		assertEquals(HELLO_HIBERNATE, updatedMember.getMessage());

		// Delete
		System.out.println("\n[delete]");
		delete(updatedMember);

		// Confirm Delete
		System.out.println("\n[confirm delete]");
		MemberDao deleteMember = selectById(1);
		assertNull(deleteMember);
	}

	public void insert() {
		MemberDao member = new MemberDao(VWAN, HELLO_WORLD);
		insert(member);

		member = new MemberDao("Dopingcat", HELLO_HIBERNATE);
		insert(member);

		member = new MemberDao("Test1", "Test1");
		insert(member);

		member = new MemberDao("Test2", "Test2");
		insert(member);
	}

	public List<MemberDao> selectAll() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Member");
		List<MemberDao> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public void delete(MemberDao updatedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(updatedMember);
		session.getTransaction().commit();
	}

	public void update(MemberDao selectedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(selectedMember);
		session.getTransaction().commit();
	}

	public MemberDao selectById(int i) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		MemberDao selectedMember = (MemberDao) session.get(MemberDao.class, i);
		session.getTransaction().commit();

		return selectedMember;
	}

	public void insert(MemberDao member) {
		Session session = factory.getCurrentSession();
		// Transaction begin
		session.beginTransaction();
		// Bussiness
		session.save(member);
		// Transaction commit
		session.getTransaction().commit();
	}
}