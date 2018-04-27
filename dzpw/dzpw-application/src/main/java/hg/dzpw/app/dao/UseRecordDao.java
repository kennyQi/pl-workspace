package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.domain.model.ticket.UseRecord;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UseRecordDao extends BaseDao<UseRecord, UseRecordQo> {
	@Autowired
	private SingleTicketDao singleTicketDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, UseRecordQo qo) {
		// 所属单票
		if (qo.getSingleTicketId() != null) {
			if (null != qo.getSingleTicketId())
				criteria.add(Restrictions.eq("singleTicketId",
						qo.getSingleTicketId()));
		}

		if (StringUtils.isNotBlank(qo.getGroupTicketId())) {
			criteria.add(Restrictions.eq("groupTicketId", qo.getGroupTicketId()));
		}
		if (qo.getUseDateStart() != null) {
			criteria.add(Restrictions.ge("useDate", qo.getUseDateStart()));
		}
		if (qo.getUseDateEnd() != null) {
			criteria.add(Restrictions.le("useDate", qo.getUseDateEnd()));
		}

		return criteria;
	}

	@Override
	protected Class<UseRecord> getEntityClass() {
		return UseRecord.class;
	}

}
