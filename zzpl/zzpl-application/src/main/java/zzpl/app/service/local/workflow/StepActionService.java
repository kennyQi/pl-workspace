package zzpl.app.service.local.workflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.workflow.StepActionDAO;
import zzpl.domain.model.workflow.StepAction;
import zzpl.pojo.qo.workflow.StepActionQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class StepActionService extends BaseServiceImpl<StepAction, StepActionQO, StepActionDAO> {

	@Autowired
	private StepActionDAO stepActionDAO;
	
	@Override
	protected StepActionDAO getDao() {
		return stepActionDAO;
	}

}
