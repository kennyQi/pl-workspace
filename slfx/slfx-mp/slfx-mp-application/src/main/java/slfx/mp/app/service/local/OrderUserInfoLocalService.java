package slfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slfx.mp.app.dao.OrderUserInfoDAO;
import slfx.mp.app.pojo.qo.OrderUserInfoQO;
import slfx.mp.domain.model.order.OrderUserInfo;

@Service
@Transactional
public class OrderUserInfoLocalService extends BaseServiceImpl<OrderUserInfo, OrderUserInfoQO, OrderUserInfoDAO> {
	
	@Autowired
	private OrderUserInfoDAO dao;

	@Override
	protected OrderUserInfoDAO getDao() {
		return dao;
	}

}
