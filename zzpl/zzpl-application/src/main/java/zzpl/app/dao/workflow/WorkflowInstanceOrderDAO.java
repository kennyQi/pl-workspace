package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;
import hg.common.component.BaseDao;

@Repository
public class WorkflowInstanceOrderDAO extends
		BaseDao<WorkflowInstanceOrder, WorkflowInstanceOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria,
			WorkflowInstanceOrderQO qo) {
		if (qo!=null) {
			if (StringUtils.isNotBlank(qo.getWorkflowInstanceID())) {
				criteria.add(Restrictions.eq("workflowInstance.id", qo.getWorkflowInstanceID()));
			}
			if (StringUtils.isNotBlank(qo.getOrderID())) {
				criteria.add(Restrictions.eq("orderID", qo.getOrderID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<WorkflowInstanceOrder> getEntityClass() {
		return WorkflowInstanceOrder.class;
	}

}
