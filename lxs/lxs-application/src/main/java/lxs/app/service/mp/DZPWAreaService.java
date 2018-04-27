package lxs.app.service.mp;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.mp.DZPWAreaDAO;
import lxs.domain.model.mp.DzpwArea;
import lxs.pojo.qo.mp.DZPWAreaQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DZPWAreaService extends BaseServiceImpl<DzpwArea, DZPWAreaQO, DZPWAreaDAO>{

	@Autowired
	private DZPWAreaDAO dzpwAreaDAO;
	
	public void deleteALL(){
		dzpwAreaDAO.executeHql("delete DzpwArea");
	}
	
	@Override
	protected DZPWAreaDAO getDao() {
		return dzpwAreaDAO;
	}

}
