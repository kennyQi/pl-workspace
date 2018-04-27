package zzpl.app.dao.jp;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.jp.JPOrderRefundLog;

@Repository
public class JPOrderRefundLogDao extends BaseDao<JPOrderRefundLog, BaseQo> {

	@Override
	protected Class<JPOrderRefundLog> getEntityClass() {

		return JPOrderRefundLog.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return null;
	}

}
