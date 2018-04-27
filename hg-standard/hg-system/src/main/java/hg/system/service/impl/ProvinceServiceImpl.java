package hg.system.service.impl;

import hg.common.component.BaseServiceImpl;
import hg.system.dao.ProvinceDao;
import hg.system.model.meta.Province;
import hg.system.qo.ProvinceQo;
import hg.system.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProvinceServiceImpl extends BaseServiceImpl<Province, ProvinceQo, ProvinceDao> implements ProvinceService {

	@Autowired
	private ProvinceDao dao;
	
	@Override
	protected ProvinceDao getDao() {
		return dao;
	}

}
