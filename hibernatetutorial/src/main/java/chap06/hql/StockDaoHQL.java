package chap06.hql;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import chap06.vo.StockDailyRecord;

/**
 * Source by MKyong.com
 * http://www.mkyong.com/hibernate/hibernate-criteria-examples/
 *  Moved by Arahansa
 *  HQL을 썼을 때, 소스도 길어지고 문자열에서 실수할 확률이 일어난다.
 *  그래서 Criteria를 쓴다는 얘기도 있다. 단 크리테리아는 성능을 유념해야 한다.
 */
public class StockDaoHQL {
	public static List<StockDailyRecord> getStockDailyRecord(Date startDate, Date endDate, Integer volume, Session session) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-DD");
		boolean isFirst = true;
		StringBuffer query = new StringBuffer("from StockDailyRecord ");

		if(startDate != null) {
			if(isFirst) {
				query.append(" where date >= '" + format.format(startDate) + "'");
			} else {
				query.append(" and date >= '" + format.format(startDate) + "'");
			}
			isFirst = false;
		}

		if(endDate != null) {
			if(isFirst) {
				query.append(" where date <= '" + format.format(endDate) + "'");
			} else {
				query.append(" and date <= '" + format.format(endDate) + "'");
			}
			isFirst = false;
		}

		if(volume != null) {
			if(isFirst) {
				query.append(" where volume >= " + volume);
			} else {
				query.append(" and volume >= " + volume);
			}
			isFirst = false;
		}
		query.append(" order by date");
		System.out.println(query);
		Query result = session.createQuery(query.toString());

		return result.list();
	}
}
