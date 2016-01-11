package chap01.crud;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;

import chap01.vo.MemberVo;
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
		MemberVo selectedMember = selectById(1);
		assertEquals(VWAN, selectedMember.getName());

		// Select all
		System.out.println("\n[select all]");
		List<MemberVo> list = selectAll();
		System.out.println(list);

		// Update
		System.out.println("\n[update]");
		selectedMember.setMessage(HELLO_HIBERNATE);
		update(selectedMember);

		// Confirm Update
		System.out.println("\n[confirm update]");
		MemberVo updatedMember = selectById(1);
		assertEquals(HELLO_HIBERNATE, updatedMember.getMessage());

		// Delete
		System.out.println("\n[delete]");
		delete(updatedMember);

		// Confirm Delete
		System.out.println("\n[confirm delete]");
		MemberVo deleteMember = selectById(1);
		assertNull(deleteMember);
	}

	public void insert() {
		MemberVo member = new MemberVo(VWAN, HELLO_WORLD);
		insert(member);

		member = new MemberVo("Dopingcat", HELLO_HIBERNATE);
		insert(member);

		member = new MemberVo("Test1", "Test1");
		insert(member);

		member = new MemberVo("Test2", "Test2");
		insert(member);
	}

	public List<MemberVo> selectAll() {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.createQuery("from Member");
		List<MemberVo> list = query.list();
		session.getTransaction().commit();
		return list;
	}

	public void delete(MemberVo updatedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.delete(updatedMember);
		session.getTransaction().commit();
	}

	public void update(MemberVo selectedMember) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		session.update(selectedMember);
		session.getTransaction().commit();
	}

	public MemberVo selectById(int i) {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		MemberVo selectedMember = (MemberVo) session.get(MemberVo.class, i);
		session.getTransaction().commit();

		return selectedMember;
	}

	public void insert(MemberVo member) {
		Session session = factory.getCurrentSession();
		// Transaction begin
		session.beginTransaction();
		// Bussiness
		session.save(member);
		// Transaction commit
		session.getTransaction().commit();
	}
}