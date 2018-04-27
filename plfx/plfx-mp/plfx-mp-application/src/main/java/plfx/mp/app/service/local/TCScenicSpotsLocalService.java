package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.dao.TCScenicSpotsDAO;
import plfx.mp.app.pojo.qo.TCScenicSpotsQO;
import plfx.mp.domain.model.scenicspot.TCScenicSpots;

@Service
@Transactional
public class TCScenicSpotsLocalService extends BaseServiceImpl<TCScenicSpots, TCScenicSpotsQO, TCScenicSpotsDAO> {
	
	@Autowired
	private TCScenicSpotsDAO dao;

	@Override
	protected TCScenicSpotsDAO getDao() {
		return dao;
	}
	
}
