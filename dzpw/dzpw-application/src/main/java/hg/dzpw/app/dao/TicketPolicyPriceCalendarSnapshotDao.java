package hg.dzpw.app.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendarSnapshot;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarSnapshotQo;

/**
 * @类功能说明：门票政策价格日历快照
 * @类修改者：
 * @修改日期：2015-3-6上午10:55:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-3-6上午10:55:53
 */
@Repository
public class TicketPolicyPriceCalendarSnapshotDao extends BaseDao<TicketPolicyPriceCalendarSnapshot, TicketPolicyPriceCalendarSnapshotQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TicketPolicyPriceCalendarSnapshotQo qo) {
		if (qo == null)
			return criteria;
		
		if(StringUtils.isNotBlank(qo.getTicketPolicySnapshotId())){
			criteria.add(Restrictions.eq("ticketPolicySnapshot.id", qo.getTicketPolicySnapshotId()));
		}
		
		return criteria;
	}

	@Override
	protected Class<TicketPolicyPriceCalendarSnapshot> getEntityClass() {
		return TicketPolicyPriceCalendarSnapshot.class;
	}

}
