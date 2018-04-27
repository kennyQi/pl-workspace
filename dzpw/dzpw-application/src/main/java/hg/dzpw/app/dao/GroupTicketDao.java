package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GroupTicketDao extends BaseDao<GroupTicket, GroupTicketQo> {

	@Autowired
	private TicketDao ticketDao;
	@Autowired
	private TicketOrderDao orderDao;
	@Autowired
	private SingleTicketDao singleTicketDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, GroupTicketQo qo) {
		ticketDao.buildCriteriaOut(criteria, qo);
		if (qo != null) {
			// 套票状态
			if (qo.getStatus() != null && qo.getStatus().length > 0) {
				criteria.add(Restrictions.or(eachEqProperty(qo.getStatus(), "status.current")));
			}
			
			// 退款状态
			if (qo.getRefundCurrent()!=null){
				criteria.add(Restrictions.eq("status.refundCurrent", qo.getRefundCurrent()));
			}
			
			// 订单
			if (qo.getTicketOrdeQo() != null) {
				Criteria orderCriteria = criteria.createCriteria("ticketOrder", JoinType.LEFT_OUTER_JOIN);
				orderDao.buildCriteriaOut(orderCriteria, qo.getTicketOrdeQo());
			}
			//游客
			if(qo.getSingleTicketQo()!=null)
			{
				Criteria singleTicketCriteria = criteria.createCriteria("singleTickets",qo.getSingleTicketQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
				singleTicketDao.buildCriteriaOut(singleTicketCriteria, qo.getSingleTicketQo());
			}
		}
		
		return criteria;
	}

	@Override
	protected Class<GroupTicket> getEntityClass() {
		return GroupTicket.class;
	}
	@Override
	public List<GroupTicket> queryList(GroupTicketQo qo, Integer offset,
			Integer maxSize) {
		List<GroupTicket> list = super.queryList(qo, offset, maxSize);
			for (GroupTicket o : list) {
				Hibernate.initialize(o.getSingleTickets());
				for(SingleTicket singleTicket : o.getSingleTickets()){
					Hibernate.initialize(singleTicket.getScenicSpot());
//					Hibernate.initialize(singleTicket.getUseRecordList());
				}
			}
		return list;
	}
}