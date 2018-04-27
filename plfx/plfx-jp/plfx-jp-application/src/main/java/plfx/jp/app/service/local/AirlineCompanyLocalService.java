package plfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.dao.AirlineCompanyDao;
import plfx.jp.domain.model.AirlineCompany;
import plfx.jp.qo.AirlineCompanyQo;

@Service
public class AirlineCompanyLocalService extends BaseServiceImpl<AirlineCompany, AirlineCompanyQo, AirlineCompanyDao> {

	@Autowired
	private AirlineCompanyDao dao;

	@Override
	protected AirlineCompanyDao getDao() {
		return dao;
	}

}
