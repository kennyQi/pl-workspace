package lxs.app.service.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lxs.app.dao.mp.DZPWCityDAO;
import lxs.domain.model.mp.DzpwCity;
import lxs.pojo.qo.mp.DZPWCityQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class DZPWCityService extends BaseServiceImpl<DzpwCity, DZPWCityQO, DZPWCityDAO>{

	@Autowired
	private DZPWCityDAO dzpwCityDAO;
	
	public void deleteALL(){
		dzpwCityDAO.executeHql("delete DzpwCity");
	}
	
	@Override
	protected DZPWCityDAO getDao() {
		return dzpwCityDAO;
	}

}
