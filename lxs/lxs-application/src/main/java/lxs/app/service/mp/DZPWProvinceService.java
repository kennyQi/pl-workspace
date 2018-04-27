package lxs.app.service.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lxs.app.dao.mp.DZPWProvinceDAO;
import lxs.domain.model.mp.DzpwProvince;
import lxs.pojo.qo.mp.DZPWProvinceQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class DZPWProvinceService extends BaseServiceImpl<DzpwProvince, DZPWProvinceQO, DZPWProvinceDAO>{

	@Autowired
	private DZPWProvinceDAO dzpwProvinceDAO;
	
	public void deleteALL(){
		dzpwProvinceDAO.executeHql("delete DzpwProvince");
	}
	@Override
	protected DZPWProvinceDAO getDao() {
		return dzpwProvinceDAO;
	}

}
