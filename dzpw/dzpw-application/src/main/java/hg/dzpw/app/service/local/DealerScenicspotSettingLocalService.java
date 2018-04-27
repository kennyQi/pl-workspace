package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.app.dao.DealerDao;
import hg.dzpw.app.dao.DealerScenicspotSettingDao;
import hg.dzpw.app.dao.ScenicSpotDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerScenicspotSetting;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.pojo.command.merchant.dealer.ScenicspotModifyDealerSettingCommand;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @类功能说明：景区对经销商的设置
 * @类修改者：
 * @修改日期：2015-2-11下午5:00:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11下午5:00:21
 */
@Service
@Transactional
public class DealerScenicspotSettingLocalService extends BaseServiceImpl<DealerScenicspotSetting, DealerScenicspotSettingQo, DealerScenicspotSettingDao> {
	
	@Autowired
	private DealerScenicspotSettingDao dao;
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	@Autowired
	private DealerDao dealerDao;

	@Override
	protected DealerScenicspotSettingDao getDao() {
		return dao;
	}

	/**
	 * @方法功能说明：景区对经销商设置（景区后台）
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11下午5:48:35
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void modifyDealerSetting(ScenicspotModifyDealerSettingCommand command) {
		if (command == null || StringUtils.isBlank(command.getDealerId())
				|| StringUtils.isBlank(command.getScenicSpotId()))
			return;
		
		DealerScenicspotSettingQo qo = new DealerScenicspotSettingQo();
		DealerQo dealerQo = new DealerQo();
		qo.setScenicSpotId(command.getScenicSpotId());
		qo.setDealerQo(dealerQo);
		dealerQo.setId(command.getDealerId());
		DealerScenicspotSetting setting = getDao().queryUnique(qo);
		
		if (setting == null){
			setting = new DealerScenicspotSetting();
			Dealer dealer = dealerDao.get(command.getDealerId());
			ScenicSpot scenicSpot = scenicSpotDao.get(command.getScenicSpotId());
			setting.createDealerSetting(command, scenicSpot, dealer);
			getDao().save(setting);
		} else {
			setting.modifyDealerSetting(command);
			getDao().update(setting);
		}
		
	}
	
}
