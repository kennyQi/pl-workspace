package pl.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.AdDao;
import pl.app.dao.AdPositionDao;
import hg.common.component.BaseServiceImpl;
import hg.service.ad.command.position.CreateAdPositionCommand;
import hg.service.ad.command.position.DeleteAdPositionCommand;
import hg.service.ad.command.position.ModifyAdPositionCommand;
import hg.service.ad.domain.model.position.AdPosition;
import hg.service.ad.pojo.qo.ad.AdPositionQO;

@Service
@Transactional
public class AdPositionServiceImpl extends
		BaseServiceImpl<AdPosition, AdPositionQO, AdPositionDao> {

	@Autowired
	private AdDao adDao;
	@Autowired
	private AdPositionDao adPositionDao;
	
	public AdPosition createAdPosition(CreateAdPositionCommand command) {
		
		AdPosition adPosition = new AdPosition();
		adPosition.create(command);
		
		adPositionDao.save(adPosition);
		
		return adPosition;
	}
	
	public void modifyAdPosition(ModifyAdPositionCommand command) {
		
		AdPosition adPosition = adPositionDao.load(command.getAdPositionId());
		adPosition.modify(command);
		
		adPositionDao.update(adPosition);
	}
	
	public void deleteAdPosition(DeleteAdPositionCommand command) {
		
		adPositionDao.deleteById(command.getAdPositionId());
	}
	
	@Override
	protected AdPositionDao getDao() {
		return adPositionDao;
	}

}
