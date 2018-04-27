package plfx.jp.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import plfx.jp.app.dao.AirportDao;
import plfx.jp.domain.model.Airport;
import plfx.jp.qo.AirportQo;

@Service
@Transactional(rollbackFor = Exception.class)
public class AirportLocalService extends BaseServiceImpl<Airport, AirportQo, AirportDao> {

	@Autowired
	private AirportDao dao;

	@Override
	protected AirportDao getDao() {
		return dao;
	}

}
