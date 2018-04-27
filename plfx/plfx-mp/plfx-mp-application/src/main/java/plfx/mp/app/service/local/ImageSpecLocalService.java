package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.dao.ImageSpecDAO;
import plfx.mp.app.pojo.qo.ImageSpecQO;
import plfx.mp.domain.model.scenicspot.ImageSpecTemp;

@Service
@Transactional
public class ImageSpecLocalService extends BaseServiceImpl<ImageSpecTemp, ImageSpecQO, ImageSpecDAO> {
	
	@Autowired
	private ImageSpecDAO dao;
	@Override
	protected ImageSpecDAO getDao() {
		return dao;
	}

	/**
	 * 根据景区ID删除对应图片
	 * 
	 * @param scenicSpotId
	 */
	public void deleteByScenicSpotId(String scenicSpotId) {
		HgLogger.getInstance().info("wuyg", "根据景区ID删除对应图片"+scenicSpotId);
		getDao().deleteByScenicSpotId(scenicSpotId);
	}
}
