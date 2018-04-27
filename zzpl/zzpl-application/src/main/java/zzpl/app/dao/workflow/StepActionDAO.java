package zzpl.app.dao.workflow;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.StepAction;
import zzpl.pojo.qo.workflow.StepActionQO;
import hg.common.component.BaseDao;

@Repository
public class StepActionDAO extends BaseDao<StepAction, StepActionQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, StepActionQO qo) {
		return criteria;
	}

	@Override
	protected Class<StepAction> getEntityClass() {
		return StepAction.class;
	}

}
