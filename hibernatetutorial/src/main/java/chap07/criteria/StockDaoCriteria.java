package chap07.criteria;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import chap06.vo.StockDailyRecord;

// 소스출처
// http://www.mkyong.com/hibernate/hibernate-criteria-examples/
public class StockDaoCriteria {
	public static List<StockDailyRecord> getStockDailyRecordCriteria(Date startDate, Date endDate, Integer volume, Session session) {
		Criteria criteria = session.createCriteria(StockDailyRecord.class);
		if(startDate != null) {
			criteria.add(Restrictions.ge("date", startDate));
		}
		if(endDate != null) {
			criteria.add(Restrictions.le("date", endDate));
		}
		if(volume != null) {
			criteria.add(Restrictions.ge("volume", volume));
		}
		criteria.addOrder(Order.asc("date"));

		return criteria.list();
	}
}
