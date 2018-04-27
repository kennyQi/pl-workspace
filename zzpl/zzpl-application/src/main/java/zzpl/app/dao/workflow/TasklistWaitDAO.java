package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.TasklistWait;
import zzpl.pojo.qo.workflow.TasklistWaitQO;
import hg.common.component.BaseDao;

@Repository
public class TasklistWaitDAO extends BaseDao<TasklistWait, TasklistWaitQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TasklistWaitQO qo) {
		if (qo != null) {
			if (qo.getWorkflowInstanceID() != null&& StringUtils.isNotBlank(qo.getWorkflowInstanceID())) {
				criteria.add(Restrictions.eq("workflowInstanceID", qo.getWorkflowInstanceID()));
			}
			if (qo.getCurrentUserID() != null&& StringUtils.isNotBlank(qo.getCurrentUserID())) {
				criteria.add(Restrictions.eq("currentUserID", qo.getCurrentUserID()));
			}
			if (qo.getStepNO() != null) {
				criteria.add(Restrictions.eq("stepNO", qo.getStepNO()));
			}
			if (StringUtils.isNotBlank(qo.getStepName())) {
				criteria.add(Restrictions.like("stepName", qo.getStepName(), MatchMode.ANYWHERE));
			}
			if (qo.getAction() != null&& StringUtils.isNotBlank(qo.getAction())) {
				criteria.add(Restrictions.eq("action", qo.getAction()));
			}
			
			criteria.addOrder(Order.desc("receiveTime"));
		}
		return criteria;
	}

	@Override
	protected Class<TasklistWait> getEntityClass() {
		return TasklistWait.class;
	}

}
