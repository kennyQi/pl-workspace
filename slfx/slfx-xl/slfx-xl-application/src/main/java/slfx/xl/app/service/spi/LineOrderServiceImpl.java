package slfx.xl.app.service.spi;

import hg.common.page.Pagination;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.xl.app.common.util.EntityConvertUtils;
import slfx.xl.app.component.base.BaseXlSpiServiceImpl;
import slfx.xl.app.service.local.LineOrderLocalService;
import slfx.xl.app.service.local.LineOrderPaymentLocalService;
import slfx.xl.app.service.local.LineOrderTravelerLocalService;
import slfx.xl.domain.model.order.LineOrder;
import slfx.xl.domain.model.order.LineOrderPayment;
import slfx.xl.domain.model.order.LineOrderTraveler;
import slfx.xl.pojo.command.line.UpdateLineOrderStatusCommand;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import slfx.xl.pojo.command.order.CancleLineOrderCommand;
import slfx.xl.pojo.command.order.ChangeLineOrderStatusCommand;
import slfx.xl.pojo.command.order.CreateLineOrderCommand;
import slfx.xl.pojo.command.order.ModifyLineOrderTravelerCommand;
import slfx.xl.pojo.command.order.ModifyPaymentLineOrderCommand;
import slfx.xl.pojo.command.pay.BatchPayLineOrderCommand;
import slfx.xl.pojo.dto.order.LineOrderDTO;
import slfx.xl.pojo.dto.order.LineOrderPaymentDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.message.NoticeUpdateLineOrderSalePriceMessage;
import slfx.xl.pojo.message.NoticeUpdateLineOrderStatusMessage;
import slfx.xl.pojo.message.SynchronizationLineMessage;
import slfx.xl.pojo.qo.LineOrderPaymentQO;
import slfx.xl.pojo.qo.LineOrderQO;
import slfx.xl.pojo.qo.LineOrderTravelerQO;
import slfx.xl.pojo.system.LineMessageConstants;
import slfx.xl.spi.inter.LineOrderService;
import slfx.xl.spi.inter.LineService;

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
 * @创建时间：2014年12月16日下午2:55:10
 * @版本：V1.0
 *
 */
@Service("lineOrderService")
public class LineOrderServiceImpl extends BaseXlSpiServiceImpl<LineOrderDTO, LineOrderQO, LineOrderLocalService> implements LineOrderService {

	@Resource
	private LineOrderLocalService lineOrderLocalService;
	
	@Resource
	private LineOrderTravelerLocalService lineOrderTravelerLocalService;
	
	@Resource
	private LineOrderPaymentLocalService lineOrderPaymentLocalService;
	
	@Autowired
	private RabbitTemplate template;
	
	@Autowired
	private LineService lineService;

	@Override
	protected LineOrderLocalService getService() {
		return lineOrderLocalService;
	}

	@Override
	protected Class<LineOrderDTO> getDTOClass() {
		return LineOrderDTO.class;
	}

	
	@Override
	public LineOrderDTO queryOrderDetail(LineOrderQO qo){
		LineOrder lineOrder = lineOrderLocalService.queryUnique(qo);
		LineOrderDTO lineOrderDTO = null;
		
		if(lineOrder != null){
			//查询游玩人
			LineOrderTravelerQO lineOrderTravelerQO = new LineOrderTravelerQO();
			lineOrderTravelerQO.setLineOrderId(lineOrder.getId());
			List<LineOrderTraveler> LineOrderTravelerList = lineOrderTravelerLocalService.queryList(lineOrderTravelerQO);
			Set<LineOrderTraveler> LineOrderTravelers = new HashSet<LineOrderTraveler>(LineOrderTravelerList);
			lineOrder.setTravelers(LineOrderTravelers);
			
			//model转换dto
			try{
				lineOrderDTO = EntityConvertUtils.convertEntityToDto(lineOrder, LineOrderDTO.class);
			}catch(Exception e){
				HgLogger.getInstance().error("yuqz", "LineOrderServiceImpl->queryOrderDetail->error:" + HgLogger.getStackTrace(e));
				e.printStackTrace();
			}
		}
		
		return lineOrderDTO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
	
		Pagination pagination2 = getService().queryPagination(pagination);
		List<LineOrder> lineOrderList = (List<LineOrder>) pagination2.getList();

		List<LineOrderDTO> list = null;
		try{
			list = EntityConvertUtils.convertEntityToDtoList(lineOrderList, getDTOClass());			
		}catch(Exception e){
			list = new ArrayList<LineOrderDTO>();
			e.printStackTrace();
		}
		pagination2.setList(list);
		return pagination2;
	}

	@Override
	public Boolean cancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException{
		return lineOrderLocalService.cancleLineOrder(command);
	}
	
	
	
	@Override
	public Boolean modifyPaymentLineOrder(ModifyPaymentLineOrderCommand command) throws SlfxXlException{
		return lineOrderLocalService.modifyPaymentLineOrder(command);
	}
	
	
	
	@Override
	public Boolean changeLineOrderStatus(ChangeLineOrderStatusCommand command)
			throws SlfxXlException {
		return lineOrderLocalService.changeLineOrderStatus(command);
	}

	@Override
	public List<LineOrderPaymentDTO> queryLineOrderPayInfo(LineOrderPaymentQO qo) {
		
		List<LineOrderPayment> list = lineOrderPaymentLocalService.queryList(qo);
		List<LineOrderPaymentDTO>  dtoList = new ArrayList<LineOrderPaymentDTO>();
		//将多个游客的同一流水号的支付记录合并到一条记录里
		HashMap<String,LineOrderPaymentDTO> map = new HashMap<String,LineOrderPaymentDTO>();
		if(list != null && list.size() != 0){
			for(LineOrderPayment lineOrderPayment:list){
				LineOrderPaymentDTO dto = new LineOrderPaymentDTO();
				if(map.containsKey(lineOrderPayment.getSerialNumber())){
					dto = map.get(lineOrderPayment.getSerialNumber());
					dto.setLineOrderTravelerNames(dto.getLineOrderTravelerNames() + "，" +lineOrderPayment.getLineOrderTraveler().getName());
				}else{
					dto = EntityConvertUtils.convertEntityToDto(lineOrderPayment, LineOrderPaymentDTO.class);
					dto.setLineOrderTravelerNames(lineOrderPayment.getLineOrderTraveler().getName());
					map.put(lineOrderPayment.getSerialNumber(), dto);
				}
			}
		}
		dtoList.addAll(map.values());
		return dtoList;
	}
	
/*---------------------admin使用上面，shop使用下面----------------------*/
	
	@Override
	public LineOrderDTO shopCreateLineOrder(CreateLineOrderCommand command) throws SlfxXlException{
		LineOrderDTO orderDto = lineOrderLocalService.shopCreateLineOrder(command);
		//发送消息
//		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
//		apiCommand.setLineId(command.getLineID());
//		apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
//		lineService.sendLineUpdateMessage(apiCommand);
		return orderDto;
	}
	
	public List<LineOrderDTO> shopQueryLineOrderList(LineOrderQO qo){
		
		List<LineOrder> lineOrders = lineOrderLocalService.queryList(qo);
		
		List<LineOrderDTO> lineOrderDTOList = null;
		try{
			lineOrderDTOList = EntityConvertUtils.convertEntityToDtoList(lineOrders, getDTOClass());			
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","LineOrderServiceImpl->[线路订单entity转换dto异常]:"+HgLogger.getStackTrace(e));
		}
		
		return lineOrderDTOList;
	}

	
	public Boolean shopCancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException{
		return lineOrderLocalService.ShopCancleLineOrder(command);
	}

	@Override
	public Boolean shopPayLineOrder(BatchPayLineOrderCommand command) throws SlfxXlException {
		return lineOrderLocalService.shopPayLineOrder(command);
	}

	@Override
	public void sendLineOrderUpdateMessage(XLUpdateOrderStatusMessageApiCommand apiCommand) {
//		Set<LineOrderTravelerDTO> travelers = apiCommand.getTravelers();
//		XLOrderStatusDTO xlOrderStatus = new XLOrderStatusDTO();
//		//下单成功已锁定位置
//		xlOrderStatus.setStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_LOCK_SEAT));
//		//等待支付尾款
//		xlOrderStatus.setPayStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_WAIT_PAY_BALANCE_MONEY));
//		for(LineOrderTravelerDTO dto : travelers){
//			dto.setXlOrderStatus(xlOrderStatus);
//		}
//		apiCommand.setTravelers(travelers);
		HgLogger.getInstance().info("yuqz","LineOrderServiceImpl->sendLineOrderUpdateMessage-开始:"+ JSON.toJSONString(apiCommand));
		NoticeUpdateLineOrderStatusMessage baseAmqpMessage=new NoticeUpdateLineOrderStatusMessage();
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(1);
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			HgLogger.getInstance().info("yuqz","LineOrderServiceImpl->sendLineOrderUpdateMessage-baseAmqpMessage:"+ JSON.toJSONString(baseAmqpMessage));
			template.convertAndSend("slfx.sendLineOrderUpdateMessage",baseAmqpMessage);			
			
			HgLogger.getInstance().info("yuqz","LineOrderServiceImpl->sendLineOrderUpdateMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz","LineOrderServiceImpl->sendLineOrderUpdateMessage-失败:"+ HgLogger.getStackTrace(e));
		}
	}
	
	
	//更改线路订单游玩人
	@Override
	public LineOrderDTO modifyLineOrderTraveler(ModifyLineOrderTravelerCommand command) {
		return lineOrderLocalService.modifyLineOrderTraveler(command);
	}

	@Override
	public void sendModifyLineImageMessage(XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand) {
		SynchronizationLineMessage baseAmqpMessage=new SynchronizationLineMessage();
		XLUpdateLineMessageApiCommand apiCommand = new XLUpdateLineMessageApiCommand();
		apiCommand.setLineId(xlUpdateLineMessageApiCommand.getLineId());
		apiCommand.setStatus(LineMessageConstants.UPDATE_DATE_SALE_PRICE);
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(Integer.parseInt(LineMessageConstants.UPDATE_DATE_SALE_PRICE));
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			template.convertAndSend("slfx.modifyLineMinPriceMessage",baseAmqpMessage);			
			
			HgLogger.getInstance().info("tandeng","LineServiceImpl->sendLineUpdateMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("tandeng","LineServiceImpl->sendLineUpdateMessage-失败:"+ JSON.toJSONString(baseAmqpMessage));
		}
	}

	@Override
	public boolean updateLineOrderStatus(UpdateLineOrderStatusCommand command) {
		return lineOrderLocalService.updateLineOrderStatus(command);
	}

	@Override
	public Boolean batchModifyPaymentLineOrder(ModifyPaymentLineOrderCommand command) {
		return lineOrderLocalService.batchModifyPaymentLineOrder(command);
	}

	/**
	 * 
	 * @方法功能说明：通知经销商修改游玩人金额和订单金额
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月4日上午9:24:02
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @return:void
	 * @throws
	 */
	@Override
	public void sendLineOrderUpdateSalePriceMessage(XLUpdateOrderSalePriceMessageApiCommand apiCommand) {
		NoticeUpdateLineOrderSalePriceMessage baseAmqpMessage=new NoticeUpdateLineOrderSalePriceMessage();
		baseAmqpMessage.setContent(apiCommand);
		baseAmqpMessage.setType(Integer.parseInt(LineMessageConstants.UPDATE_LINE_ORDER_SALEPRICE));
		baseAmqpMessage.setSendDate(new Date());
		baseAmqpMessage.setArgs(null);
		try{
			template.convertAndSend("slfx.lineOrderUpdateSalePriceMessage",baseAmqpMessage);			
			
			HgLogger.getInstance().info("yuqz","LineServiceImpl->sendLineOrderUpdateSalePriceMessage-成功:"+ JSON.toJSONString(baseAmqpMessage));
		}catch(Exception e){
			HgLogger.getInstance().error("yuqz","LineServiceImpl->sendLineOrderUpdateSalePriceMessage-失败:"+ JSON.toJSONString(baseAmqpMessage));
		}
	}
}
