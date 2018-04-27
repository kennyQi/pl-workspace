package zzpl.app.service.local.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class WorkflowInstanceOrderService extends BaseServiceImpl<WorkflowInstanceOrder, WorkflowInstanceOrderQO, WorkflowInstanceOrderDAO> {

	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	
	@Override
	protected WorkflowInstanceOrderDAO getDao() {
		return workflowInstanceOrderDAO;
	}

}
