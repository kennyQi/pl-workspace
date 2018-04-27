package hsl.app.service.spi.hotel;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.hotel.OrderCancelLocationService;
import hsl.pojo.dto.hotel.order.OrderCancelDTO;
import hsl.pojo.qo.hotel.OrderCancelQO;
import hsl.spi.inter.hotel.HslHotelOrderCancelService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HslHotelOrderCancelServiceImpl extends BaseSpiServiceImpl<OrderCancelDTO,OrderCancelQO, OrderCancelLocationService> 
	implements HslHotelOrderCancelService{
	
	@Autowired
    private OrderCancelLocationService orderCancelLocationService;

	@Override
	protected OrderCancelLocationService getService() {
		return orderCancelLocationService;
	}

	@Override
	protected Class<OrderCancelDTO> getDTOClass() {
		return OrderCancelDTO.class;
	}
	
	
}
