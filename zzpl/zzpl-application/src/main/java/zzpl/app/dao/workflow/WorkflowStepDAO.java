package zzpl.app.dao.workflow;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

@Repository
public class WorkflowStepDAO extends BaseDao<WorkflowStep, WorkflowStepQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, WorkflowStepQO qo) {
		if (qo != null) {
			if (qo.getWorkflowStepID() != null&& StringUtils.isNotBlank(qo.getWorkflowStepID())) {
				criteria.add(Restrictions.eq("id", qo.getWorkflowStepID()));
			}
			if (qo.getWorkflowID() != null&& StringUtils.isNotBlank(qo.getWorkflowID())) {
				criteria.add(Restrictions.eq("workflowID", qo.getWorkflowID()));
			}
			if (qo.getStepNO() != null) {
				criteria.add(Restrictions.eq("stepNO", qo.getStepNO()));
			}
			if(StringUtils.isNotBlank(qo.getOrderByStepNOasc())){
				criteria.addOrder(Order.asc("stepNO"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<WorkflowStep> getEntityClass() {
		return WorkflowStep.class;
	}
	
	@Override
	public WorkflowStep queryUnique(WorkflowStepQO qo){
		WorkflowStep workflowStep = super.queryUnique(qo);
		if (workflowStep!=null) {
			Hibernate.initialize(workflowStep.getWorkflowStepActions());
		}
		return workflowStep;
	}

	public int maxProperty(String propertyName, WorkflowStepQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
	
}
