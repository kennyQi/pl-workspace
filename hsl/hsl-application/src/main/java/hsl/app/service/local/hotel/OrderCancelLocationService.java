package hsl.app.service.local.hotel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hg.common.component.BaseServiceImpl;
import hsl.app.dao.hotel.OrderCancelDAO;
import hsl.domain.model.hotel.order.OrderCancel;
import hsl.pojo.qo.hotel.OrderCancelQO;
@Service
@Transactional
public class OrderCancelLocationService extends BaseServiceImpl<OrderCancel, OrderCancelQO, OrderCancelDAO>{
	@Autowired
	private OrderCancelDAO orderCancelDAO;
	@Override
	protected OrderCancelDAO getDao() {
		return orderCancelDAO;
	}

}
