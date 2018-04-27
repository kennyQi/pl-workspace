package zzpl.app.service.local.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zzpl.app.dao.app.APPDAO;
import zzpl.domain.model.app.APP;
import zzpl.pojo.qo.app.APPQO;
import hg.common.component.BaseServiceImpl;

@Service
public class APPService extends BaseServiceImpl<APP, APPQO, APPDAO>{
	
	@Autowired
	private APPDAO appdao;

	@Override
	protected APPDAO getDao() {
		return appdao;
	}

}
