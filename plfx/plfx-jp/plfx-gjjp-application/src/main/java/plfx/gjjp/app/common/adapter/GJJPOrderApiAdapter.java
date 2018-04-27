package plfx.gjjp.app.common.adapter;

import hg.common.util.BeanMapperUtils;

import java.util.List;

import org.hibernate.Hibernate;

import plfx.api.client.api.v1.gj.dto.GJJPOrderDTO;
import plfx.api.client.api.v1.gj.dto.GJPassengerDTO;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJPassenger;

/**
 * @类功能说明：国际机票订单适配器
 * @类修改者：
 * @修改日期：2015-7-24下午5:53:27
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-24下午5:53:27
 */
public class GJJPOrderApiAdapter {

	/**
	 * @方法功能说明：转为API用的DTO
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-24下午5:53:10
	 * @修改内容：
	 * @参数：@param order
	 * @参数：@return
	 * @return:GJJPOrderDTO
	 * @throws
	 */
	public GJJPOrderDTO convertDTO(GJJPOrder jpOrder) {
		initAll(jpOrder);
		GJJPOrderDTO dto = BeanMapperUtils.map(jpOrder, GJJPOrderDTO.class);
		List<GJPassenger> passengers = jpOrder.getPassengers();
		List<GJPassengerDTO> passengerDTOs = dto.getPassengers();
		for (GJPassenger passenger : passengers) {
			for (GJPassengerDTO passengerDTO : passengerDTOs) {
				if (passenger.getBaseInfo().getIdNo().equals(passengerDTO.getBaseInfo().getIdNo())
						&& passenger.getBaseInfo().getIdType().equals(passengerDTO.getBaseInfo().getIdType())) {
					passengerDTO.getTicketPriceDetail().setOutTickMoney(passenger.getTicketPriceDetail().getSupplierOutTickMoney());
					passengerDTO.getTicketPriceDetail().setTax(passenger.getTicketPriceDetail().getSupplierTax());
					break;
				}
			}
		}
		return dto;
	}

	/**
	 * @方法功能说明：初始化所有订单实体中的延迟加载对象(session开启未关闭时可用)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-29上午10:01:04
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @return:void
	 * @throws
	 */
	public void initAll(GJJPOrder jpOrder) {
		List<GJPassenger> passengers = jpOrder.getPassengers();
		Hibernate.initialize(passengers);
		if (passengers != null)
			for (GJPassenger passenger : passengers)
				Hibernate.initialize(passenger.getTickets());
		Hibernate.initialize(jpOrder.getSegmentInfo().getTakeoffFlights());
		Hibernate.initialize(jpOrder.getSegmentInfo().getBackFlights());
	}
}
