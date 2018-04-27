package jxc.app.service.system;

import hg.common.component.BaseQo;
import hg.common.component.BaseServiceImpl;
import jxc.app.dao.system.ZoneDao;
import jxc.domain.model.system.Zone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service("zoneService")
public class ZoneService extends BaseServiceImpl<Zone, BaseQo, ZoneDao>{
	@Autowired
	private ZoneDao zoneDao;
	@Override
	protected ZoneDao getDao() {
		return zoneDao;
	}

}
