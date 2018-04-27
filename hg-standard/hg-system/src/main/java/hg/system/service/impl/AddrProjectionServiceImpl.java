package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.AddrProjectionDao;
import hg.system.model.meta.AddrProjection;
import hg.system.qo.AddrProjectionQo;
import hg.system.service.AddrProjectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddrProjectionServiceImpl extends BaseServiceImpl<AddrProjection, AddrProjectionQo, AddrProjectionDao> implements AddrProjectionService {
	
	@Autowired
	private AddrProjectionDao dao;

	@Override
	protected AddrProjectionDao getDao() {
		return dao;
	}

}
