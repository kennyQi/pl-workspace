package zzpl.app.service.local.jp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.jp.CityAirCodeDAO;
import zzpl.domain.model.order.CityAirCode;
import zzpl.pojo.qo.sys.CityAirCodeQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class CityAirCodeService extends
		BaseServiceImpl<CityAirCode, CityAirCodeQO, CityAirCodeDAO> {

	@Autowired
	private CityAirCodeDAO cityAirCodeDAO;

	@Override
	protected CityAirCodeDAO getDao() {
		return cityAirCodeDAO;
	}

}
