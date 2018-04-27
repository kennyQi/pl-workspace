package hg.dzpw.app.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;

/**
 * @类功能说明：门票政策价格日历
 * @类修改者：
 * @修改日期：2015-3-5下午2:24:26
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-5下午2:24:26
 */
@Repository
public class TicketPolicyPriceCalendarDao extends BaseDao<TicketPolicyPriceCalendar, TicketPolicyPriceCalendarQo> {

	@Autowired
	private TicketPolicyDao ticketPolicyDao;

	@Autowired
	private DealerDao dealerDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketPolicyPriceCalendarQo qo) {
		
		if (qo == null)
			return criteria;
		
		if (qo.getTicketPolicyQo() != null) {
			Criteria ticketPolicyCriteria = criteria.createCriteria(
					"ticketPolicy", qo.getTicketPolicyQo().getAlias(), JoinType.INNER_JOIN);
			ticketPolicyDao.buildCriteriaOut(ticketPolicyCriteria, qo.getTicketPolicyQo());
		}
		
		if (qo.getStandardPrice() != null)
			criteria.add(Restrictions.eq("standardPrice", qo.getStandardPrice()));

		if (qo.getDealerQo() != null) {
			if (qo.getStandardPrice() == null
					|| (qo.getStandardPrice() != null && !qo.getStandardPrice())) {
				Criteria dealerCriteria = criteria.createCriteria("dealer", qo
						.getDealerQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				dealerDao.buildCriteriaOut(dealerCriteria, qo.getDealerQo());
			}
		} else if (qo.getDealerFetch() != null && qo.getDealerFetch()) {
			criteria.createCriteria("dealer", JoinType.LEFT_OUTER_JOIN);
		}
		
		return criteria;
	}

	@Override
	protected Class<TicketPolicyPriceCalendar> getEntityClass() {
		return TicketPolicyPriceCalendar.class;
	}

}
