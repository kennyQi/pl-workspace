package slfx.jp.app.service.local;

import hg.common.component.BaseServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.jp.app.dao.FlightTicketDAO;
import slfx.jp.domain.model.order.FlightTicket;
import slfx.jp.qo.admin.PlatformOrderQO;

@Service
@Transactional
public class JPFlightTicketLocalService extends BaseServiceImpl<FlightTicket, PlatformOrderQO, FlightTicketDAO>{
	
	
	@Autowired
	private FlightTicketDAO flightTicketDAO;
	
	@Override
	protected FlightTicketDAO getDao() {
		return flightTicketDAO;
	}
	
}


