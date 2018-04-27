package zzpl.app.service.local.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zzpl.app.dao.workflow.WorkflowStepActionDAO;
import zzpl.domain.model.workflow.WorkflowStepAction;
import zzpl.pojo.qo.workflow.WorkflowStepActionQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class WorkflowStepActionService extends BaseServiceImpl<WorkflowStepAction, WorkflowStepActionQO, WorkflowStepActionDAO> {

	@Autowired
	private WorkflowStepActionDAO workflowStepActionDAO;
	
	@Override
	protected WorkflowStepActionDAO getDao() {
		return workflowStepActionDAO;
	}

}
