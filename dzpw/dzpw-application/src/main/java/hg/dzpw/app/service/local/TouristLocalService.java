package hg.dzpw.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.dzpw.app.dao.TouristDao;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.command.platform.tourist.PlatformModifyTouristCommand;
import hg.dzpw.pojo.exception.DZPWException;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TouristLocalService extends BaseServiceImpl<Tourist, TouristQo, TouristDao>{

	@Autowired
	private TouristDao touristDao;
	
	@Override
	protected TouristDao getDao() {
		return touristDao;
	}

	public Tourist load(String id){
		Tourist tourist = touristDao.load(id);
		Hibernate.initialize(tourist);
		return tourist;
	}
	
	/**
	 * @方法功能说明: 修改游客信息
	 * @param command
	 * @throws DZPWException
	 */
	public void modifyTourist(PlatformModifyTouristCommand command) {
		Tourist tourist = touristDao.get(command.getTouristId());
		if(null == tourist)
			throw new RuntimeException("游客信息不存在或已被删除");
		
		tourist.modify(command);
		touristDao.update(tourist);
	}
	
}