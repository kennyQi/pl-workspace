package zzpl.app.service.local.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.user.RoleWorkflowDAO;
import zzpl.domain.model.user.RoleWorkflow;
import zzpl.pojo.qo.user.RoleWorkflowQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class RoleWorkflowService extends BaseServiceImpl<RoleWorkflow, RoleWorkflowQO, RoleWorkflowDAO> {

	@Autowired
	private RoleWorkflowDAO roleWorkflowDAO;
	
	public List<RoleWorkflow> getRoleWorkflowByQo(RoleWorkflowQO qo) {
		List<RoleWorkflow> roleWorkflow = roleWorkflowDAO.queryList(qo);
		return roleWorkflow;
	}

	@Override
	protected RoleWorkflowDAO getDao() {
		return roleWorkflowDAO;
	}

}
