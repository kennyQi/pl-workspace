package hsl.app.service.local.jp;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.JPPassangerDao;
import hsl.domain.model.jp.JPPassanger;
import hsl.pojo.qo.jp.HslJPPassangerQO;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("jpPassangerLocalService")
@Transactional
public class JPPassangerLocalService extends BaseServiceImpl<JPPassanger, HslJPPassangerQO, JPPassangerDao> {

	@Resource
	private JPPassangerDao passangerDao;
	
	@Override
	protected JPPassangerDao getDao() {
		return passangerDao;
	}
	
}
