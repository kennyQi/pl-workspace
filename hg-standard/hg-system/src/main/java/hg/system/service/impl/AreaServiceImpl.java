package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.AreaDao;
import hg.system.model.meta.Area;
import hg.system.qo.AreaQo;
import hg.system.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AreaServiceImpl extends BaseServiceImpl<Area, AreaQo, AreaDao> implements AreaService {
	
	@Autowired
	private AreaDao dao;

	@Override
	protected AreaDao getDao() {
		return dao;
	}

}
