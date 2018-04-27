package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.Workflow;
import zzpl.pojo.qo.workflow.WorkflowQO;
import hg.common.component.BaseDao;

@Repository
public class WorkflowDAO extends BaseDao<Workflow, WorkflowQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WorkflowQO qo) {
		if (qo != null) {
			
			if (StringUtils.isNotBlank(qo.getWorkflowID())) {
				criteria.add(Restrictions.eq("id", qo.getWorkflowID()));
				return criteria;
			}
			
			if (qo.getCompanyID() != null && StringUtils.isNotBlank(qo.getCompanyID())) {
				criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
			}
			
			if (qo.getWorkflowName() != null && StringUtils.isNotBlank(qo.getWorkflowName())) {
				criteria.add(Restrictions.like("workflowName", qo.getWorkflowName(),MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Workflow> getEntityClass() {
		return Workflow.class;
	}

}
