package zzpl.app.dao.workflow;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.workflow.Tasklist;
import zzpl.pojo.qo.workflow.TasklistQO;
import hg.common.component.BaseDao;

@Repository
public class TasklistDAO extends BaseDao<Tasklist, TasklistQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, TasklistQO qo) {
		if (qo != null) {
			if (qo.getWorkflowInstanceID() != null&& StringUtils.isNotBlank(qo.getWorkflowInstanceID())) {
				criteria.add(Restrictions.eq("workflowInstanceID", qo.getWorkflowInstanceID()));
			}
			if (qo.getStepNO() != null) {
				criteria.add(Restrictions.eq("stepNO", qo.getStepNO()));
			}
			if (qo.getCurrentUserID() != null&& StringUtils.isNotBlank(qo.getCurrentUserID())) {
				criteria.add(Restrictions.eq("currentUserID", qo.getCurrentUserID()));
			}
			if(qo.isOrderByReceiveTimeDesc()){
				criteria.addOrder(Order.desc("receiveTime"));
			}
			
			if (qo.getLeSendTime()!=null) {
				criteria.add(Restrictions.le("sendTime", qo.getLeSendTime()));
			}
			
			if (qo.isSendTimeAsc()) {
				criteria.addOrder(Order.asc("sendTime"));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Tasklist> getEntityClass() {
		return Tasklist.class;
	}

}
