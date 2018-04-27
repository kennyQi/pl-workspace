package slfx.mp.app.dao;

import hg.common.component.BaseDao;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
import slfx.mp.app.pojo.qo.TCPolicyNoticeQO;
import slfx.mp.domain.model.supplierpolicy.TCPolicyNotice;

@Repository
public class TCPolicyNoticeDAO extends BaseDao<TCPolicyNotice, TCPolicyNoticeQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TCPolicyNoticeQO qo) {
		return criteria;
	}

	@Override
	protected Class<TCPolicyNotice> getEntityClass() {
		return TCPolicyNotice.class;
	}

}
