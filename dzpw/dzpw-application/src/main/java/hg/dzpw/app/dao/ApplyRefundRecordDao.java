package hg.dzpw.app.dao;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.ApplyRefundRecordQo;
import hg.dzpw.domain.model.pay.ApplyRefundRecord;

@Repository
public class ApplyRefundRecordDao extends BaseDao<ApplyRefundRecord, ApplyRefundRecordQo>{

	@Autowired
	private GroupTicketDao groupTicketDao;
	@Autowired
	private TicketOrderDao ticketOrderDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ApplyRefundRecordQo qo) {
		if (qo!=null) {
			if (qo.getGroupTicketQo()!=null) {
				Criteria groupTicketCriteria=criteria.createCriteria("groupTicket");
				groupTicketDao.buildCriteriaOut(groupTicketCriteria, qo.getGroupTicketQo());
			}
			if (qo.getTicketOrderQo()!=null) {
				Criteria orderCriteria = criteria.createCriteria("ticketOrder");
				ticketOrderDao.buildCriteriaOut(orderCriteria, qo.getTicketOrderQo());
			}
		}
		return criteria;
	}

	@Override
	protected Class<ApplyRefundRecord> getEntityClass() {
		return ApplyRefundRecord.class;
	}


}
