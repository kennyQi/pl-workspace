package plfx.jd.app.service.spi;

import hg.common.util.BeanMapperUtils;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import plfx.jd.app.component.base.BaseJDSpiServiceImpl;
import plfx.jd.app.service.local.HotelOrderLocalService;
import plfx.jd.domain.model.order.HotelOrder;
import plfx.jd.pojo.command.plfx.order.OrderCancelAdminCommand;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCancelCommand;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCreateCommand;
import plfx.jd.pojo.command.plfx.ylclient.ValidateCreditCardNoCommand;
import plfx.jd.pojo.dto.plfx.order.HotelOrderDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCancelResultDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderDetailResultDTO;
import plfx.jd.pojo.dto.ylclient.order.ValidateCreditCardNoResultDTO;
import plfx.jd.pojo.qo.HotelOrderQO;
import plfx.jd.pojo.qo.ylclient.JDOrderQO;
import plfx.jd.spi.inter.HotelOrderService;
import plfx.yl.ylclient.yl.command.CheckCreditCardNoCommand;
import plfx.yl.ylclient.yl.command.OrderCancelCommand;
import plfx.yl.ylclient.yl.dto.CheckCreditCardNoDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCancelDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderDetailDTO;
import plfx.yl.ylclient.yl.qo.OrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：线路订单service实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午2:55:10
 * @版本：V1.0
 *
 */
@Service("hotelOrderService")
public class HotelOrderServiceImpl extends BaseJDSpiServiceImpl<HotelOrderDTO, HotelOrderQO, HotelOrderLocalService> implements HotelOrderService {

	@Resource
	private HotelOrderLocalService hotelOrderLocalService;

	@Override
	protected HotelOrderLocalService getService() {
		return hotelOrderLocalService;
	}

	@Override
	protected Class<HotelOrderDTO> getDTOClass() {
		return HotelOrderDTO.class;
	}

	@Override
	public OrderCreateResultDTO orderCreate(JDOrderCreateCommand command) {
		OrderCreateResultDTO dto =  hotelOrderLocalService.createHotelOrder(command);
		return dto;
	}

	@Override
	public OrderCancelResultDTO orderCancel(JDOrderCancelCommand command) {
		OrderCancelCommand orderCancelCommand = new OrderCancelCommand();
		BeanUtils.copyProperties(command.getOrdercancel(),orderCancelCommand);
		OrderCancelResultDTO dto = null;
		HotelOrderCancelDTO result = hotelOrderLocalService.cancelHotelOrder(orderCancelCommand);
		if(result != null){
			dto = JSON.parseObject(JSON.toJSONString(result),OrderCancelResultDTO.class);
		}
		return dto;
	}

	@Override
	public OrderDetailResultDTO queryOrder(JDOrderQO qo) {
		OrderDetailResultDTO dto = null;
		OrderQO orderQo = new OrderQO();
		BeanUtils.copyProperties(qo,orderQo);
		HotelOrderDetailDTO result = hotelOrderLocalService.queryHotelOrder(orderQo);
		if(result != null){
			dto = JSON.parseObject(JSON.toJSONString(result),OrderDetailResultDTO.class);
		}
		return dto;
	}

	
	public List<HotelOrderDTO> queryHotelOrder(HotelOrderQO qo) {
		List<HotelOrder> orders = hotelOrderLocalService.queryList(qo);
		List<HotelOrderDTO> ordersDTO  = null;
		try{
			ordersDTO = hg.common.util.EntityConvertUtils.convertEntityToDtoList(orders, getDTOClass());		
		}catch(Exception e){
			e.printStackTrace();
		}
		return ordersDTO;
	}
	@Override
	public HotelOrderDTO queryUnique(HotelOrderQO qo) {
		HotelOrder order = getService().queryUnique(qo);
		HotelOrderDTO dto = null;
		try{
			dto = JSON.parseObject(JSON.toJSONString(order),HotelOrderDTO.class);
		}catch(Exception e){
			e.printStackTrace();
		}
		return dto;
	}

	@Override
	public Boolean OrderCancel(
			OrderCancelAdminCommand command) {
		return getService().cancelOrder(command);
	}

	@Override
	public ValidateCreditCardNoResultDTO validateCreditCardNo(
			ValidateCreditCardNoCommand command) {
		ValidateCreditCardNoResultDTO dto=null;
		
		CheckCreditCardNoCommand ylCommand=BeanMapperUtils.map(command, CheckCreditCardNoCommand.class);
		CheckCreditCardNoDTO result=this.hotelOrderLocalService.validateCreditCard(ylCommand);
		
		if(result != null){
			dto = BeanMapperUtils.map(result,ValidateCreditCardNoResultDTO.class);
		}
		return dto;
	}
}
