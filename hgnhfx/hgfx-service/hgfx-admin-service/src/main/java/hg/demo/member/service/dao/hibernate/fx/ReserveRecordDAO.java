package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ReserveRecordQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.ReserveRecord;

/**
 * @author cangs
 */
@Repository("reserveRecordDAO")
public class ReserveRecordDAO extends BaseHibernateDAO<ReserveRecord, ReserveRecordQO>{

	@Override
	protected Class<ReserveRecord> getEntityClass() {
		return ReserveRecord.class;
	}

	@Override
	protected void queryEntityComplete(ReserveRecordQO qo,
			List<ReserveRecord> list) {
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ReserveRecordQO qo) {
		return criteria;
	}

}
