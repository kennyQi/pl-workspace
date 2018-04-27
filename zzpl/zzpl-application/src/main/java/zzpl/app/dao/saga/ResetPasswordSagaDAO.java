package zzpl.app.dao.saga;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.saga.ResetPasswordSaga;
import zzpl.pojo.qo.saga.ResetPasswordSagaQO;
import hg.common.component.BaseDao;

@Repository
public class ResetPasswordSagaDAO extends
		BaseDao<ResetPasswordSaga, ResetPasswordSagaQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, ResetPasswordSagaQO qo) {
		return criteria;
	}

	@Override
	protected Class<ResetPasswordSaga> getEntityClass() {
		return ResetPasswordSaga.class;
	}

}
