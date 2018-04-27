package slfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.mp.app.component.manager.ScenicSpotDetailCacheManager;
import slfx.mp.app.dao.ScenicSpotDAO;
import slfx.mp.app.dao.TCScenicSpotsDAO;
import slfx.mp.app.pojo.qo.ScenicSpotQO;
import slfx.mp.domain.model.scenicspot.ScenicSpot;
import slfx.mp.tcclient.tc.dto.jd.SceneryDetailDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.ResultSceneryDetail;
import slfx.mp.tcclient.tc.service.TcClientService;

@Service
@Transactional
public class ScenicSpotLocalService extends BaseServiceImpl<ScenicSpot, ScenicSpotQO, ScenicSpotDAO> {
	
	@Autowired
	private ScenicSpotDAO dao;
	@Autowired
	private TCScenicSpotsDAO tcScenicSpotsDAO;
	@Autowired
	private ScenicSpotDetailCacheManager cacheManager;
	@Autowired
	private TcClientService tcClientService;

	@Override
	protected ScenicSpotDAO getDao() {
		return dao;
	}
	
	/**
	 * 根据同程景区ID获取平台景区
	 * 
	 * @param TcScenicId
	 * @return
	 */
	public ScenicSpot getByTcScenicId(String tcScenicId) {
		if (tcScenicId == null)
			return null;
		ScenicSpotQO qo = new ScenicSpotQO();
		qo.setTcScenicSpotId(tcScenicId);
		return getDao().queryUnique(qo);
	}
	
	/**
	 * @方法功能说明：获取景点详情
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-16上午11:24:16
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	public String getScenicDetail(String id) {
		String detail = cacheManager.getDetail(id);
		if (StringUtils.isBlank(detail)) {
			try {
				String tcScenicId = getDao().get(id).getTcScenicSpotInfo().getId();
				SceneryDetailDto dto = new SceneryDetailDto();
				dto.setSceneryId(tcScenicId);
				Response<ResultSceneryDetail> response = tcClientService.getSceneryDetail(dto);
				if (!"0".equals(response.getHead().getRspType()))
					return null;
				detail = response.getResult().getIntro();
				cacheManager.putDetail(id, detail);
			} catch (Exception e) { }
		}
		return detail;
	}

}
