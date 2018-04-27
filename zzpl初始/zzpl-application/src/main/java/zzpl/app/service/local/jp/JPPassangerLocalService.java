package zzpl.app.service.local.jp;

import hg.common.component.BaseServiceImpl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.jp.JPPassangerDao;
import zzpl.domain.model.jp.JPPassanger;
import zzpl.pojo.qo.jp.JPPassangerQO;

@Service
@Transactional
public class JPPassangerLocalService extends BaseServiceImpl<JPPassanger, JPPassangerQO, JPPassangerDao> {

	@Resource
	private JPPassangerDao passangerDao;
	
	@Override
	protected JPPassangerDao getDao() {
		return passangerDao;
	}
	
}
