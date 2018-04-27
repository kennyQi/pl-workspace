package hsl.app.dao.line;


import hg.common.component.BaseDao;
import hsl.domain.model.xl.DateSalePrice;
import hsl.pojo.qo.line.DateSalePriceQO;
import org.apache.commons.lang.time.DateUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.Date;

@Repository
public class DateSalePriceDAO extends BaseDao<DateSalePrice, DateSalePriceQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DateSalePriceQO qo) {

		// 当前时间
		Date nowTime = new Date();

		// 获取月数，包括当月
		if (qo.getFetchMonthCount() != null && qo.getFetchMonthCount() > 0) {
			Date now = DateUtils.truncate(nowTime, Calendar.DATE);
			Date end = DateUtils.addMonths(now, 12);
			end = DateUtils.round(end, Calendar.MONTH);
			end = DateUtils.addDays(end, -1);
			criteria.add(Restrictions.ge("saleDate", now));
			criteria.add(Restrictions.le("saleDate", end));
		}

		if (qo.getBeforeDay() != null) {
			Date now = DateUtils.truncate(nowTime, Calendar.DATE);
			criteria.add(Restrictions.ge("saleDate", DateUtils.addDays(now, qo.getBeforeDay())));
		}

		/*if (qo.getSaleDate() == null) {
			// 今天之前的价格日历不显示
			criteria.add(Restrictions.gt("saleDate", DateUtils.truncate(nowTime, Calendar.DATE)));
		}*/
		// 日期正序排列
		criteria.addOrder(Order.asc("saleDate"));
		return criteria;
	}
	
	@Override
	protected Class<DateSalePrice> getEntityClass() {
		return DateSalePrice.class;
	}

}
