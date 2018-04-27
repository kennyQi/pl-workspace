package hsl.app.dao;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import hsl.domain.model.jp.JPOrderRefundLog;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

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
