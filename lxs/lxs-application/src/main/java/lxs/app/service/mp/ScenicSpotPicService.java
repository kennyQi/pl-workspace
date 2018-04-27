package lxs.app.service.mp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import lxs.app.dao.mp.ScenicSpotPicDAO;
import lxs.domain.model.mp.ScenicSpotPic;
import lxs.pojo.qo.mp.ScenicSpotPicQO;

@Service
@Transactional
public class ScenicSpotPicService extends
		BaseServiceImpl<ScenicSpotPic, ScenicSpotPicQO, ScenicSpotPicDAO> {

	@Autowired
	private ScenicSpotPicDAO scenicSpotPicDAO;

	public void deleteOldScenicSpotPic(int versionNO){
		scenicSpotPicDAO.executeHql("delete ScenicSpotPic ssp where ssp. versionNO < "+versionNO);
	}
	
	@Override
	protected ScenicSpotPicDAO getDao() {
		return scenicSpotPicDAO;
	}

}
