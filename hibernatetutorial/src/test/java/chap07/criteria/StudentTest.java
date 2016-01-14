package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
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
		studentDao.insert(new Student("Student01", 100, 100, 100, 100));
		studentDao.insert(new Student("Student02",  25, 100, 100, 100));
		studentDao.insert(new Student("Student03",  22,  90, 100, 100));
		studentDao.insert(new Student("Student04",  20, 100,  90, 100));
		studentDao.insert(new Student("Student05",  23, 100, 100,  90));
		studentDao.insert(new Student("Student06",  21,  90, 100, 100));
		studentDao.insert(new Student("Student07",  19,  90,  90, 100));
		studentDao.insert(new Student("Student08",  24, 100,  90,  90));
		studentDao.insert(new Student("Student09",  26, 100, 100,  90));
		studentDao.insert(new Student("Student10",  27,  90,  90,  90));
	}

	@Test
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
}
