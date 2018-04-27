package zzpl.app.service.local.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.pojo.qo.workflow.TasklistQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class TasklistService extends BaseServiceImpl<Tasklist, TasklistQO, TasklistDAO> {

	@Autowired
	private TasklistDAO tasklistDAO;
	
	@Override
	protected TasklistDAO getDao() {
		return tasklistDAO;
	}

}
