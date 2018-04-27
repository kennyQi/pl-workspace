package pl.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.AdDao;
import pl.app.dao.AdPositionDao;
import hg.common.component.BaseServiceImpl;
import hg.service.ad.command.ad.ChangeAdImageCommand;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.command.ad.DownAdPriorityCommand;
import hg.service.ad.command.ad.HideAdCommand;
import hg.service.ad.command.ad.ModifyAdCommand;
import hg.service.ad.command.ad.ShowAdCommand;
import hg.service.ad.command.ad.UpAdPriorityCommand;
import hg.service.ad.domain.model.ad.Ad;
import hg.service.ad.domain.model.position.AdPosition;
import hg.service.ad.domain.service.AdService;
import hg.service.ad.pojo.qo.ad.AdQO;

@Service
@Transactional
public class AdServiceImpl extends BaseServiceImpl<Ad, AdQO, AdDao> {

	@Autowired
	private AdService adService;

	@Autowired
	private AdDao adDao;
	@Autowired
	private AdPositionDao adPositionDao;

	public Ad createAd(CreateAdCommand command) {

		AdPosition position = adPositionDao.load(command.getPositionId());

		Ad ad = new Ad();
		ad.create(command, position);

		adDao.save(ad);
		return ad;
	}

	public void modifyAd(ModifyAdCommand command) {

		Ad ad = adDao.load(command.getAdId());
		ad.modify(command);

		adDao.update(ad);
	}

	public void showAd(ShowAdCommand command) {
		
		Ad ad = adDao.load(command.getAdId());
		ad.show();

		adDao.update(ad);
	}

	public void hideAd(HideAdCommand command) {

		Ad ad = adDao.load(command.getAdId());
		ad.hide();

		adDao.update(ad);
	}

	public void upAdPriority(UpAdPriorityCommand command) {

		Ad targetAd = adDao.get(command.getAdId());

		// 查询优先级排在前一位的广告
		AdQO qo = new AdQO();
		qo.setId(command.getAdId());
		qo.setLastOrNextPriority(true);
		qo.setTargetPriority(targetAd.getBaseInfo().getPriority());
		Ad lastAd = adDao.queryUnique(qo);

		// 提升优先级，两条广告优先级互换
		adService.exchangePriority(targetAd, lastAd);
	}

	public void downAdPriority(DownAdPriorityCommand command) {

		Ad targetAd = adDao.get(command.getAdId());

		// 查询优先级排在后一位的广告
		AdQO qo = new AdQO();
		qo.setId(command.getAdId());
		qo.setLastOrNextPriority(false);
		Ad nextAd = adDao.queryUnique(qo);

		// 降低优先级，两条广告优先级互换   
		adService.exchangePriority(targetAd, nextAd);
	}

	public void deleteAd(DeleteAdCommand command) {
		adDao.deleteById(command.getAdId());
	}
	
	public void changeAdImage(ChangeAdImageCommand command) {
		Ad ad = adDao.load(command.getAdId());
		ad.changeImage(command);
		adDao.update(ad);
	}
	
	@Override
	protected AdDao getDao() {
		return adDao;
	}

}
