package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;

import chap07.vo.Student;
import util.CommonDao;
import util.HibernateTestUtil;

public class StudentTest {
	SessionFactory factory = HibernateTestUtil.getSessionFactory(Student.class);
	CommonDao<Student> studentDao = new CommonDao<>(Student.class);

	@Before
	public void setUp() {
		studentDao.deleteAll();
		studentDao.insert(new Student("Student01", 100, 100, 100,  50));
		studentDao.insert(new Student("Student02",  25,  90,  10,  30));
		studentDao.insert(new Student("Student03",  22,  80,  20,  20));
		studentDao.insert(new Student("Student04",  20,  70,  30,  40));
		studentDao.insert(new Student("Student05",  23,  60,  40,  90));
		studentDao.insert(new Student("Student06",  21,  40,  50, 100));
		studentDao.insert(new Student("Student07",  19,  30,  60,  90));
		studentDao.insert(new Student("Student08",  24,  10,  70,  80));
		studentDao.insert(new Student("Student09",  26,   0,  80,  70));
		studentDao.insert(new Student("Student10",  27,  90,  90,  60));
	}

	//@Test
	public void test() {
		// confirm setUp()
		assertEquals(10, studentDao.count());

		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.gt("age", 22));
		//criteria.add(Restrictions.lt("math", 100));
		criteria.addOrder(Order.desc("id"));

		// Paging
		//criteria.setFirstResult(1);
		//criteria.setMaxResults(2);

		List<Student> students = criteria.list();
		session.getTransaction().commit();

		System.out.println();
		for(Student student : students) {
			System.out.println(student);
		}
		System.out.println();
		// lambda
		students.forEach(student -> System.out.println(student));
	}

	// Restriction additional
	//@Test
	public void restriction() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(Restrictions.or(Restrictions.between("age", 21, 25), Restrictions.between("kor", 70, 90)));
		List<Student> list = criteria.list();
		System.err.println("\n[나이가 21에서 25사이 이거나, 국어 점수가 70점 에서 90 사이]");
		list.forEach(student -> System.out.println(student));
		session.getTransaction().commit();
	}

	// Projection
	// Doc : https://docs.jboss.org/hibernate/orm/3.6/javadocs/org/hibernate/criterion/Projections.html
	@Test
	public void projection() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class);
		double avg = (double)criteria.setProjection(Projections.avg("kor")).uniqueResult();
		System.err.println("\n[kor score avg] : " + avg);
		session.getTransaction().commit();
	}
}
