package zzpl.app.dao.workflow;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

@Repository
public class WorkflowStepActionDAO extends
		BaseDao<WorkflowStepAction, WorkflowStepActionQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WorkflowStepActionQO qo) {
		if (qo != null) {
			if (qo.getWorkflowStepID() != null&&StringUtils.isNotBlank(qo.getWorkflowStepID())) {
				criteria.add(Restrictions.eq("workflowStep.id", qo.getWorkflowStepID()));
			}
			if (qo.getStatus() != null&&qo.getStatus()!=0) {
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if (StringUtils.isNotBlank(qo.getOrderType())) {
				criteria.add(Restrictions.eq("orderType", qo.getOrderType()));
			}
			if(StringUtils.isNotBlank(qo.getViewActionValue())){
				Criteria stepActionCriteria = criteria.createCriteria("stepAction");
				stepActionCriteria.add(Restrictions.eq("viewActionValue", qo.getViewActionValue()));
			}
		}
		criteria.addOrder(Order.asc("sort"));
		return criteria;
	}

	@Override
	protected Class<WorkflowStepAction> getEntityClass() {
		return WorkflowStepAction.class;
	}
	
	public int maxProperty(String propertyName, WorkflowStepActionQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}

}
