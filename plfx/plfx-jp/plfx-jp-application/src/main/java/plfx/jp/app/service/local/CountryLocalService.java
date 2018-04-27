package plfx.jp.app.service.local;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import plfx.jp.app.dao.CountryDao;
import plfx.jp.domain.model.Country;
import plfx.jp.qo.CountryQo;

@Service
@Transactional
public class CountryLocalService extends BaseServiceImpl<Country, CountryQo, CountryDao> {

	@Autowired
	private CountryDao dao;

	@Override
	protected CountryDao getDao() {
		return dao;
	}

}
