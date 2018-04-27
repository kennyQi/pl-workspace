package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.CityDao;
import hg.system.model.meta.City;
import hg.system.qo.CityQo;
import hg.system.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CityServiceImpl extends BaseServiceImpl<City, CityQo, CityDao> implements CityService {
	
	@Autowired
	private CityDao dao;

	@Override
	protected CityDao getDao() {
		return dao;
	}

}
