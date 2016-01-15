package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
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
		session.getTransaction().commit();

		System.err.println("\n[나이가 21에서 25사이 이거나, 국어 점수가 70점 에서 90 사이]");
		list.forEach(student -> System.out.println(student));
	}

	// Projection
	// Doc : https://docs.jboss.org/hibernate/orm/3.6/javadocs/org/hibernate/criterion/Projections.html
	//@Test
	public void projection() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria = session.createCriteria(Student.class);
		double avg = (double)criteria.setProjection(Projections.avg("kor")).uniqueResult();
		session.getTransaction().commit();

		System.err.println("\n[kor score avg] : " + avg);
	}

	// NamedQuery : 특정 쿼리가 많이 쓰일 때 Entity 클래스에 선언
	// Ref : http://www.youtube.com/watch?v=o_P-p2b_k6w&index=28&list=PL5757A5DB24A40BDC
	//@Test
	public void namedQuery() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Query query = session.getNamedQuery("FindByAge");
		query.setInteger(0, 25);
		List<Student> list = query.list();
		session.getTransaction().commit();

		System.err.println("\n[나이가 25 이상인 학생]");
		list.forEach(student -> System.out.println(student));
	}

	// Native Query : DBMS 자체의 Native 쿼리(함수)를 쓸 때
	// Ref : http://www.mkyong.com/hibernate/hibernate-native-sql-queries-examples/
	//@Test
	public void nativeQuery() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		// query for derby
		Query query = session.createSQLQuery("select * from \"USER\".\"STUDENT\"").addEntity(Student.class);
		List<Student> list = query.list();
		session.getTransaction().commit();

		System.err.println("\n[순수 쿼리로 뽑은 리스트]");
		list.forEach(student -> System.out.println(student));
	}

	// Example Query....왜 안돼는지 모름
	// Ref : http://www.youtube.com/watch?v=2DXjdl8gzOo&list=PL5757A5DB24A40BDC&index=31
	@Test
	public void exampleQuery() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();

		Student student = new Student();
		student.setKor(90);

		Example example = Example.create(student);

		Criteria criteria = session.createCriteria(Student.class);
		criteria.add(example);
		List<Student> list = criteria.list();

		session.getTransaction().commit();

		System.err.println("\n[Example로 얻어진 객체]");
		list.forEach(std -> System.out.println(std));
	}
}
