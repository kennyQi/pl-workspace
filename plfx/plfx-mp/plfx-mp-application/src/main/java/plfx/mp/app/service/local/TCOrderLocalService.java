package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.dao.TCOrderDAO;
import plfx.mp.app.pojo.qo.TCOrderQO;
import plfx.mp.domain.model.order.TCOrder;

@Service
@Transactional
public class TCOrderLocalService extends BaseServiceImpl<TCOrder, TCOrderQO, TCOrderDAO> {
	
	@Autowired
	private TCOrderDAO dao;

	@Override
	protected TCOrderDAO getDao() {
		return dao;
	}

}
