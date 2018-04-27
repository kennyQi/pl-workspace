package plfx.yl.ylclient.yl.inter.impl;

import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import plfx.yl.ylclient.yl.command.CheckCreditCardNoCommand;
import plfx.yl.ylclient.yl.command.OrderCancelCommand;
import plfx.yl.ylclient.yl.command.OrderCreateCommand;
import plfx.yl.ylclient.yl.command.OrderUpdateCommand;
import plfx.yl.ylclient.yl.dto.CheckCreditCardNoDTO;
import plfx.yl.ylclient.yl.dto.CheckGuestNameDTO;
import plfx.yl.ylclient.yl.dto.HotelDataInventoryDTO;
import plfx.yl.ylclient.yl.dto.HotelDataInventoryResultDTO;
import plfx.yl.ylclient.yl.dto.HotelDataValidateDTO;
import plfx.yl.ylclient.yl.dto.HotelDataValidateResultDTO;
import plfx.yl.ylclient.yl.dto.HotelListDTO;
import plfx.yl.ylclient.yl.dto.HotelListResultDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCancelDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCancelResultDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCreateDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCreateResultDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderDetailDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderDetailResultDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderUpdateDTO;
import plfx.yl.ylclient.yl.dto.HotelRatePlanDTO;
import plfx.yl.ylclient.yl.dto.IncrHotelDTO;
import plfx.yl.ylclient.yl.dto.IncrInventoryDTO;
import plfx.yl.ylclient.yl.dto.IncrOrderDTO;
import plfx.yl.ylclient.yl.dto.IncrRateDTO;
import plfx.yl.ylclient.yl.dto.IncrStateDTO;
import plfx.yl.ylclient.yl.dto.LastIdDTO;
import plfx.yl.ylclient.yl.dto.OrderListDTO;
import plfx.yl.ylclient.yl.dto.RateDTO;
import plfx.yl.ylclient.yl.dto.ValidateCreditCardNoResultDTO;
import plfx.yl.ylclient.yl.dto.ValidateInventoryDTO;
import plfx.yl.ylclient.yl.inter.YLHotelService;
import plfx.yl.ylclient.yl.qo.CheckGuestNameQO;
import plfx.yl.ylclient.yl.qo.HotelDetailQO;
import plfx.yl.ylclient.yl.qo.HotelListQO;
import plfx.yl.ylclient.yl.qo.IncrLastIdQO;
import plfx.yl.ylclient.yl.qo.IncrQO;
import plfx.yl.ylclient.yl.qo.InventoryQO;
import plfx.yl.ylclient.yl.qo.OrderListQO;
import plfx.yl.ylclient.yl.qo.OrderQO;
import plfx.yl.ylclient.yl.qo.RatePlanQO;
import plfx.yl.ylclient.yl.qo.RateQO;
import plfx.yl.ylclient.yl.qo.ValidateInventoryQO;
import plfx.yl.ylclient.yl.qo.ValidateQO;
import plfx.yl.ylclient.yl.util.HttpsUtil;

import com.alibaba.fastjson.JSON;
@Service("YLHotelService")
public class YLHotelServiceImpl implements YLHotelService {

	@Override
	public HotelListDTO queryHotelList(HotelListQO qo) {
		HotelListResultDTO dto = null;
		String result = HttpsUtil.send("hotel.list",qo);
		HgLogger.getInstance().info("yaosanfeng", "YLHotelServiceImpl->queryHotelList->result" + result);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelListResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			return null;
		}
		return dto.getResult();
	}

	@Override
	public HotelListDTO queryHotelDetail(HotelDetailQO qo) {
		HotelListResultDTO dto = null;
		String result = HttpsUtil.send("hotel.detail",qo);
		HgLogger.getInstance().info("yaosanfeng", "YLHotelServiceImpl->queryHotelDetail->result" + result);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelListResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}

	@Override
	public HotelDataInventoryDTO queryHotelInventory(InventoryQO qo) {
		HotelDataInventoryResultDTO dto = null;
		String result = HttpsUtil.send("hotel.data.inventory",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelDataInventoryResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}
	

	@Override
	public HotelDataValidateDTO queryOrderValidate(ValidateQO qo) {
		HotelDataValidateResultDTO dto = null;
		String result = HttpsUtil.send("hotel.data.validate",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelDataValidateResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}

	@Override
	public HotelOrderCreateDTO createOrder(OrderCreateCommand command) {
		HotelOrderCreateResultDTO dto = null;
		String result = HttpsUtil.send("hotel.order.create",command);
		HgLogger.getInstance().info("yaosanfeng", "YLHotelServiceImpl->createOrder->result" + result);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelOrderCreateResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}

	@Override
	public HotelOrderDetailDTO queryOrderDetail(OrderQO command) {
		HotelOrderDetailResultDTO dto = null;
		String result = HttpsUtil.send("hotel.order.detail",command);
		HgLogger.getInstance().info("yaosanfeng", "YLHotelServiceImpl->queryOrderDetail->result" + result);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelOrderDetailResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}

	@Override
	public HotelOrderCancelDTO cancelOrder(OrderCancelCommand command) {
		HotelOrderCancelResultDTO dto = null;
		String result = HttpsUtil.send("hotel.order.cancel",command);
		HgLogger.getInstance().info("yaosanfeng", "YLHotelServiceImpl->cancelOrder->result" + result);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelOrderCancelResultDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto.getResult();
	}

	@Override
	public RateDTO queryRate(RateQO qo) {
		RateDTO dto = null;
		String result = HttpsUtil.send("hotel.data.rate",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,RateDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public HotelRatePlanDTO queryRatePlan(RatePlanQO qo) {
		HotelRatePlanDTO dto = null;
		String result = HttpsUtil.send("hotel.data.rp",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelRatePlanDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public IncrHotelDTO incrHotel(IncrQO qo) {
		IncrHotelDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.data",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,IncrHotelDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public LastIdDTO incrLastId(IncrLastIdQO qo) {
		LastIdDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.id",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,LastIdDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public IncrInventoryDTO incrInventory(IncrQO qo) {
		IncrInventoryDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.inv",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,IncrInventoryDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public IncrOrderDTO incrOrder(IncrQO qo) {
		IncrOrderDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.order",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,IncrOrderDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public IncrRateDTO incrRate(IncrQO qo) {
		IncrRateDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.rate",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,IncrRateDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public IncrStateDTO incrState(IncrQO qo) {
		IncrStateDTO dto = null;
		String result = HttpsUtil.send("hotel.incr.state",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,IncrStateDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public ValidateInventoryDTO queryInventoryValidate(ValidateInventoryQO qo) {
		ValidateInventoryDTO dto = null;
		String result = HttpsUtil.send("hotel.inv.validate",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,ValidateInventoryDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public CheckGuestNameDTO queryCheckGuestName(CheckGuestNameQO qo) {
		CheckGuestNameDTO dto = null;
		String result = HttpsUtil.send("hotel.order.checkguest",qo);
		if(StringUtils.isNotBlank(result)){
			try {		
				dto=JSON.parseObject(result,CheckGuestNameDTO.class);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public OrderListDTO queryOrderList(OrderListQO qo) {
		OrderListDTO dto = null;
		String result = HttpsUtil.send("hotel.order.list",qo);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,OrderListDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public HotelOrderUpdateDTO updateOrder(OrderUpdateCommand command) {
		HotelOrderUpdateDTO dto = null;
		String result = HttpsUtil.send("hotel.order.update",command);
		if(StringUtils.isNotBlank(result)){
			try {
				dto = JSON.parseObject(result,HotelOrderUpdateDTO.class);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dto;
	}

	@Override
	public CheckCreditCardNoDTO validateCreditCard(
			CheckCreditCardNoCommand command) {
		CheckCreditCardNoDTO dto=null;
		String result = HttpsUtil.send("common.creditcard.validate",command);
		if(StringUtils.isNotBlank(result)){
			try {
				ValidateCreditCardNoResultDTO resultDto = JSON.parseObject(result,ValidateCreditCardNoResultDTO.class);
				dto=resultDto.getResult();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		
		return dto;
	}
	
	
	
	
	
}
