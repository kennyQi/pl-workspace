package zzpl.app.service.local.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.domain.model.workflow.WorkflowInstance;
import zzpl.pojo.qo.workflow.WorkflowInstanceQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class WorkflowInstanceService extends BaseServiceImpl<WorkflowInstance, WorkflowInstanceQO, WorkflowInstanceDAO> {

	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	
	@Override
	protected WorkflowInstanceDAO getDao() {
		return workflowInstanceDAO;
	}

}
