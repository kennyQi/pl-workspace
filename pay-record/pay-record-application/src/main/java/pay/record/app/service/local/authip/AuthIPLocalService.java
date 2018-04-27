package pay.record.app.service.local.authip;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pay.record.app.dao.authip.AuthIPDAO;
import pay.record.domain.model.authip.AuthIP;
import pay.record.pojo.qo.authip.AuthIPQO;
@Service
@Transactional
public class AuthIPLocalService extends BaseServiceImpl<AuthIP, AuthIPQO, AuthIPDAO>{

	@Autowired
	AuthIPDAO authIPDAO;
	
	@Override
	protected AuthIPDAO getDao() {
		return authIPDAO;
	}

}
