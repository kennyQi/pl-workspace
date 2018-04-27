package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.pojo.qo.workflow.WorkflowInstanceQO;
import hg.common.component.BaseDao;

@Repository
public class WorkflowInstanceDAO extends
		BaseDao<WorkflowInstance, WorkflowInstanceQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WorkflowInstanceQO qo) {
		if (qo!=null) {
			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
				return criteria;
			}
		}
		return criteria;
	}

	@Override
	protected Class<WorkflowInstance> getEntityClass() {
		return WorkflowInstance.class;
	}

}
