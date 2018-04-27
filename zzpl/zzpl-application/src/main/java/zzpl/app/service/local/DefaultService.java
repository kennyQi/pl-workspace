package zzpl.app.service.local;

import org.springframework.stereotype.Component;

import hg.common.component.BaseCommand;
import hg.log.util.HgLogger;
import zzpl.app.service.local.util.CommonService;

@Component("DefaultService")
public class DefaultService implements CommonService {

	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		HgLogger.getInstance().info("cs", "【DefaultService】无绑定action");
		return CommonService.SUCCESS;
	}

}
