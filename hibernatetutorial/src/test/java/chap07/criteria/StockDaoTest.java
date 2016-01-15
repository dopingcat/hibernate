package chap07.criteria;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.junit.BeforeClass;
import org.junit.Test;

import chap06.hql.StockDaoHQL;
import chap06.vo.StockDailyRecord;
import util.CommonDao;
import util.HibernateTestUtil;

/**
 * MKyong에 의하면 성능이슈와 유지보수이슈가 발생한다고 한다.
 * 생성된 쿼리가 느릴 수도 있고 그럴때는 튜닝하기 힘들다고 한다.
 * 그리고 쿼리가 잘못나왔을 때 유지보수가 어려울 수 있다.
 * 하이버네이트 매핑 파일안의 NamedQuery 가 더 유지보수가 하기 쉬울 수 있다고 한다.
 *
 *  이번 테스트는 두가지 방식으로 돌리는데,
 *  1. 그냥 테스트에서는
 *  DaoStock_HQL 에서는 hql 방식으로 돌린다.
 *  DaoStock_Criteria 에서는 criteria 방식으로 돌린다.
 *
 *  2. 두번째 테스트에서는
 *  Criteria API를 좀 더 살펴보는 시간을 가진다.
 *
 *  @author arahansa
 */
public class StockDaoTest {
	SessionFactory factory = HibernateTestUtil.getSessionFactory(StockDailyRecord.class);
	static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
	static CommonDao<StockDailyRecord> stockDailyRecordDao;

	private List<StockDailyRecord> list1;
	private List<StockDailyRecord> list2;
	private List<StockDailyRecord> list3;

	@BeforeClass
	public static void before() throws ParseException {
		//stockDailyRecordDao = new CommonDao<>(StockDailyRecord.class);
		stockDailyRecordDao = CommonDao.newInstance(StockDailyRecord.class);
		stockDailyRecordDao.insert(new StockDailyRecord(new Date(), 1));
		stockDailyRecordDao.insert(new StockDailyRecord(format.parse("2012-01-01"), 2));
		stockDailyRecordDao.insert(new StockDailyRecord(format.parse("2013-01-01"), 10001));
		stockDailyRecordDao.insert(new StockDailyRecord(format.parse("2014-01-01"), 9999));
		stockDailyRecordDao.insert(new StockDailyRecord(format.parse("2014-01-06"), 10000));
	}

	@Test
	public void justTest() throws Exception {
		// confirm setUp()
		assertEquals(5, stockDailyRecordDao.count());

		Session session = factory.getCurrentSession();
		session.beginTransaction();
		list1 = StockDaoHQL.getStockDailyRecord(format.parse("2014-01-01"), new Date(), 0, session);
		list2 = StockDaoCriteria.getStockDailyRecordCriteria(format.parse("2014-01-01"), new Date(), 0, session);
		session.getTransaction().commit();

		System.out.println("\n* * * * * * * * * * * list1 * * * * * * * * * *");
		list1.forEach(record -> System.out.println(record));
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");
		System.out.println("\n* * * * * * * * * * * list2 * * * * * * * * * *");
		list2.forEach(record -> System.out.println(record));
		System.out.println("* * * * * * * * * * * * * * * * * * * * * * * *");
	}

	@Test
	public void criteriaAPI() throws Exception {
		Session session = factory.getCurrentSession();
		session.beginTransaction();
		Criteria criteria;

		// Equals = Resrtrictions.eq
		criteria = session.createCriteria(StockDailyRecord.class);
		list3 = criteria.add(Restrictions.eq("volume", 10000)).list();
		System.out.println("[Criteria's toString() test] : " + criteria.toString());
		assertEquals(list3.size(), 1);
		assertEquals(list3.get(0).getVolume(), 10000);

		// Less than or equals = Restrictions.le
		criteria = session.createCriteria(StockDailyRecord.class);
		list3 = criteria.add(Restrictions.le("volume", 10000)).list();
		assertEquals(list3.size(), 4);

		// Greater than or equals = Restriction.ge
		criteria = session.createCriteria(StockDailyRecord.class);
		list3 = criteria.add(Restrictions.ge("volume", 10000)).list();
		assertEquals(list3.size(), 2);
		assertEquals(list3.get(0).getVolume(), 10001);

		// Between = Restriction.between
		criteria = session.createCriteria(StockDailyRecord.class);
		list3 = criteria.add(Restrictions.between("date", format.parse("2014-01-02"), format.parse("2014-01-07"))).list();
		assertEquals(list3.size(), 1);

		// And plus + like, isNull, isNotNull 등 다양하다.
		// Criteria도 Paging가능. (setMaxResults, setFirstResults)
		session.getTransaction().commit();
	}
}
