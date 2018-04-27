package lxs.app.service.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.mp.TouristDAO;
import lxs.domain.model.mp.Tourist;
import lxs.pojo.qo.mp.TouristQO;

@Service
@Transactional
public class TouristService extends BaseServiceImpl<Tourist, TouristQO, TouristDAO> {
	
	@Autowired
	private TouristDAO touristDAO; 

	@Override
	protected TouristDAO getDao() {
		return touristDAO;
	}

}
