package zzpl.app.service.local.user;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zzpl.app.dao.user.FrequentFlyerDAO;
import zzpl.domain.model.user.FrequentFlyer;
import zzpl.pojo.qo.user.FrequentFlyerQO;

@Service
public class FrequentFlyerService extends BaseServiceImpl<FrequentFlyer, FrequentFlyerQO, FrequentFlyerDAO>{

	@Autowired
	private FrequentFlyerDAO FrequentFlyer;
	
	@Override
	protected FrequentFlyerDAO getDao() {
		return FrequentFlyer;
	}

}
