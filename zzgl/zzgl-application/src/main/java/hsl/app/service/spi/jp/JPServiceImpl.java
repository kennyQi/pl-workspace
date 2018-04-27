package hsl.app.service.spi.jp;
import hg.common.util.BeanMapperUtils;
import hg.common.util.EntityConvertUtils;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.jp.FlightOrderService;
import hsl.app.service.local.jp.GNFlightService;
import hsl.domain.model.jp.FlightOrder;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.command.jp.JPOrderCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.jp.plfx.CancelTicketGNCommand;
import hsl.pojo.command.jp.plfx.JPBookTicketGNCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.command.jp.plfx.RefundTicketGNCommand;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.PassengerGNDTO;
import hsl.pojo.dto.jp.PassengerInfoGNDTO;
import hsl.pojo.dto.jp.RefundTicketGNDTO;
import hsl.pojo.dto.jp.plfx.JPBookOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPPayOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryFlightListGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryHighPolicyGNDTO;
import hsl.pojo.exception.GNFlightException;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.pojo.qo.jp.plfx.JPPolicyGNQO;
import hsl.spi.inter.jp.JPService;
import hsl.spi.qo.sys.CityAirCodeQO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("jpService")
public class JPServiceImpl extends BaseSpiServiceImpl<FlightOrderDTO,FlightOrderQO,FlightOrderService> implements JPService {

	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private FlightOrderService flightOrderService;


	@Override
	public JPQueryFlightListGNDTO queryFlight(JPFlightGNQO qo) throws GNFlightException {
		return gnFlightService.queryGNFlight(qo);
	}

	@Override
	public JPQueryHighPolicyGNDTO queryFlightPolicy(JPPolicyGNQO qo) throws GNFlightException {
		return gnFlightService.queryHighPolicyGN(qo);
	}

	@Override
	public JPPayOrderGNDTO askOrderTicket(JPPayOrderGNCommand command) throws GNFlightException{
		return  gnFlightService.askOrderTicket(command);
	}
	@Override
	public boolean updateOrderStatus(UpdateJPOrderStatusCommand command) {
		return flightOrderService.updateOrderStatus(command);
	}
	@Override
	public boolean updateOrderStatusById(UpdateJPOrderStatusCommand command) {
		return flightOrderService.updateOrderStatusByID(command);
	}
	@Override
	public List<FlightOrderDTO> queryOrder(FlightOrderQO qo) {
		// 查询机票订单
		return flightOrderService.queryOrder(qo);
	}

	@Override
	public JPBookOrderGNDTO orderCreate(BookGNFlightCommand command) throws GNFlightException {
		JPBookTicketGNCommand jpBookTicketGNCommand=new JPBookTicketGNCommand(command);
		//根据订单号查询订单信息
		FlightOrderQO flightOrderQO=new FlightOrderQO();
		flightOrderQO.setOrderNO(command.getOrderNO());
		FlightOrder flightOrder=flightOrderService.queryUnique(flightOrderQO);
		/***************设置航班信息*************/
		jpBookTicketGNCommand.setEncryptString(flightOrder.getFlightBaseInfo().getEncryptString());
		jpBookTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
		jpBookTicketGNCommand.setFlightNo(flightOrder.getFlightBaseInfo().getFlightNO());
		jpBookTicketGNCommand.setStartDate(flightOrder.getFlightBaseInfo().getStartTime());
		jpBookTicketGNCommand.setCabinCode(flightOrder.getFlightBaseInfo().getCabinDiscount());//仓位代码
		jpBookTicketGNCommand.setCabinName(flightOrder.getFlightBaseInfo().getCabinName());//仓位名称
		/*********设置联系人和乘客信息*************/
		PassengerInfoGNDTO passengerInfoGNDTO=new PassengerInfoGNDTO();
		List<PassengerGNDTO> listpassengerGNDTO=EntityConvertUtils.convertEntityToDtoList(flightOrder.getPassengers(), PassengerGNDTO.class);
		passengerInfoGNDTO.setName(flightOrder.getJpLinkInfo().getLinkName());
		passengerInfoGNDTO.setTelephone(flightOrder.getJpLinkInfo().getLinkTelephone());
		passengerInfoGNDTO.setPassengerList(listpassengerGNDTO);
		jpBookTicketGNCommand.setPassengerInfoGNDTO(passengerInfoGNDTO);
		JPBookOrderGNDTO jpBookOrderGNDTO=gnFlightService.createGNFightOrder(jpBookTicketGNCommand);//正式请求分销下单到易行
		//command.setOrderNO(jpBookOrderGNDTO.getDealerOrderId());
		flightOrder.getDealerReturnInfo().setMemo(jpBookOrderGNDTO.getPriceGNDTO().getMemo());
		flightOrder.getDealerReturnInfo().setTickType(jpBookOrderGNDTO.getPriceGNDTO().getTickType());
		flightOrder.getDealerReturnInfo().setTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getTotalPrice());
		flightOrder.getDealerReturnInfo().setPlcid(jpBookOrderGNDTO.getPriceGNDTO().getPlcid());
		flightOrder.getDealerReturnInfo().setTickPrice(jpBookOrderGNDTO.getPriceGNDTO().getTickPrice());
		flightOrder.getDealerReturnInfo().setPayPlatform(jpBookOrderGNDTO.getPriceGNDTO().getPayType());
		//flightOrder.getDealerReturnInfo().setTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getTickPrice());
		flightOrderService.updates(flightOrder);
		return jpBookOrderGNDTO;
	}
	@Override
	public FlightOrderDTO createLocalityOrder(BookGNFlightCommand command)
			throws GNFlightException {
		FlightOrderDTO flightOrderDTO=flightOrderService.createFlightOrder(command);
		return flightOrderDTO;
	}
	@Override
	public FlightOrderDTO cancelTicket(CancelTicketGNCommand command) throws Exception {
		return gnFlightService.cancelTicketGN(command);
	}
	
	@Override
	public boolean OrderRefund(String resultDetails) {
		return flightOrderService.orderRefund(resultDetails);
	}
	@Override
	public FlightOrderDTO queryOrderDetail(FlightOrderQO qo) {
		FlightOrder flightOrder=flightOrderService.queryUnique(qo);
		FlightOrderDTO flightOrderDTO=BeanMapperUtils.map(flightOrder,FlightOrderDTO.class);
		return flightOrderDTO;
	}

	@Override
	public FlightOrderDTO queryUnique(FlightOrderQO qo) {
		FlightOrder flightOrder=flightOrderService.queryUnique(qo);
		FlightOrderDTO flightOrderDTO=BeanMapperUtils.map(flightOrder,FlightOrderDTO.class);
		return flightOrderDTO;
	}

	@Override
	public List<FlightOrderDTO> queryList(FlightOrderQO qo) {
		List<FlightOrder> flightOrders=flightOrderService.queryList(qo);
		List<FlightOrderDTO> flightOrderDTOs=new ArrayList<FlightOrderDTO>();
		for (int i = 0; i < flightOrders.size(); i++) {
			FlightOrderDTO flightOrderDTO=BeanMapperUtils.map(flightOrders.get(i),FlightOrderDTO.class);
			flightOrderDTOs.add(flightOrderDTO);
		}
		return flightOrderDTOs;
	}

	@Override
	protected FlightOrderService getService() {
		return flightOrderService;
	}

	@Override
	protected Class<FlightOrderDTO> getDTOClass() {
		return FlightOrderDTO.class;
	}


	@Override
	public boolean notifyUpdateFightOrder(JPOrderCommand command) {
		return flightOrderService.updateJpOrder(command);
	}

	@Override
	public RefundTicketGNDTO refundTicket(
			RefundTicketGNCommand refundTicketGNCommand)
			throws GNFlightException {
		
		return this.gnFlightService.refundTicketGN(refundTicketGNCommand);
	}

	public CityAirCodeDTO queryLocalCityAirCode(CityAirCodeQO cityAirCodeQO) {
		CityAirCodeDTO cityAirCodeDTO=BeanMapperUtils.map(flightOrderService.queryLocalCityAirCode(cityAirCodeQO), CityAirCodeDTO.class);
		return cityAirCodeDTO;
	}
	public List<CityAirCodeDTO> queryCityAirCode(CityAirCodeQO cityAirCodeQO) {
		return gnFlightService.queryCityAirCode(cityAirCodeQO);
	}
}
