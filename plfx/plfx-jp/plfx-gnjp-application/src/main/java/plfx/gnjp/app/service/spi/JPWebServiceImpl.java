package plfx.gnjp.app.service.spi;

import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.gnjp.app.service.local.GNJPOrderLocalService;
import plfx.gnjp.app.service.local.JPOrderLogLocalService;
import plfx.gnjp.app.service.local.JPWebLocalService;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.gnjp.domain.model.order.GNJPOrderStatus;
import plfx.gnjp.domain.model.order.GNJPPassenger;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.admin.jpOrderLog.CreateJPOrderLogCommand;
import plfx.jp.pojo.system.OrderLogConstants;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.jp.spi.inter.JPWebService;
import plfx.yeexing.pojo.command.order.JPAutoOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPBookTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPCancelTicketSpiCommand;
import plfx.yeexing.pojo.command.order.JPPayOrderSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundQueryOrderStatusSpiCommand;
import plfx.yeexing.pojo.command.order.JPRefundTicketSpiCommand;
import plfx.yeexing.pojo.dto.flight.YeeXingCancelTicketDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingFlightPolicyDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingQueryWebFlightsDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingRefundTicketDTO;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.JPPlatPriceDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingPayJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingRefundQueryOrderDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;
import plfx.yeexing.qo.api.JPFlightSpiQO;
import plfx.yeexing.qo.api.JPPolicySpiQO;

import com.alibaba.fastjson.JSON;

/****
 * 
 * @类功能说明：平台航班SERVICE实现
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午2:43:11
 * @版本：V1.0
 *
 */
@Service("jPWebService")
public class JPWebServiceImpl extends BaseJpSpiServiceImpl<JPOrderDTO, PlatformOrderQO, JPWebLocalService>implements JPWebService{

	@Autowired
	private JPWebLocalService jPWebLocalService;
	
	@Autowired
	private GNJPOrderLocalService jPOrderLocalService;
	
	@Autowired
	private  JPOrderLogLocalService jPOrderLogLocalService;
	@Autowired
	private  JPPlatformOrderService jpPlatformOrderService;
	
	/***
	 * 机票航班列表查询
	 */
	@Override
	public YeeXingQueryWebFlightsDTO queryFlightList(JPFlightSpiQO qo) {
	
		return jPWebLocalService.queryFlightList(qo);
	}

	/***
	 * 政策查询
	 */
	@Override
	public YeeXingFlightPolicyDTO queryPolicy(JPPolicySpiQO qo) {
	
		return jPWebLocalService.queryPolicy(qo);
	}

	/***
	 * 机票下单
	 */
	@Override
	public YeeXingJPOrderDTO shopCreateJPOrder(JPBookTicketSpiCommand command) {
		
		YeeXingJPOrderDTO yeeXingJPOrderDTO = jPWebLocalService.shopCreateJPOrder(command);
		HgLogger.getInstance().info("yaosanfeng","JPWebServiceImpl->shopCreateJPOrder->yeeXingJPOrderDTO:"+ JSON.toJSONString(yeeXingJPOrderDTO));
		//机票日志
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setYeeXingOrderId(yeeXingJPOrderDTO.getOrderid());
		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		if(yeeXingJPOrderDTO != null && yeeXingJPOrderDTO.getIs_success().equals("T")){
			//记录操作日志
			jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrderDTO
					.getId(), "创建订单", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "创建订单成功"));
		}else{
			jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jpOrderDTO
					.getId(), "创建订单", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "创建订单失败"));
		}

		return yeeXingJPOrderDTO;
	}
	/***
	 * 根据航班号查询
	 */
	@Override
	public YeeXingQueryWebFlightsDTO queryYXFlightsByFlightNo(JPFlightSpiQO qo) {
		
		return jPWebLocalService.queryYXFlightsByFlightNo(qo);
	}

	/***
	 * admin申请取消
	 */
	@Override
	public YeeXingCancelTicketDTO plfxCancelTicket(JPCancelTicketSpiCommand command) {
		//HgLogger.getInstance().info("yuqz","JPWebServiceImpl->plfxCancelTicket->command:"+ JSON.toJSONString(command));
		return jPWebLocalService.plfxCancelTicket(command);
	}
	
	/**
	 * api申请取消
	 */
	@Override
	public YeeXingCancelTicketDTO apiCancelTicket(JPCancelTicketSpiCommand command) {
		HgLogger.getInstance().info("yuqz","WebFlightServiceImpl->apiCancelTicket->command:"+ JSON.toJSONString(command));
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		//处理api请求
		if(command.getDealerOrderId() != null){
			platformOrderQO.setDealerOrderCode(command.getDealerOrderId());
			platformOrderQO.setRefundType("A");
		}
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		if(jPOrderDTO != null){
			//设置易行订单号
			command.setOrderid(jPOrderDTO.getYeeXingOrderId());
		}else{
			HgLogger.getInstance().error("yaosanfeng","JPWebServiceImpl->apiCancelTicket->[api申请取消订单不存在]"+ JSON.toJSONString(jPOrderDTO));
		}
		return jPWebLocalService.plfxCancelTicket(command);
	}

	/***
	 * admin申请退废
	 */
	@Override
	public YeeXingRefundTicketDTO plfxRefundTicket(JPRefundTicketSpiCommand command) {
		//HgLogger.getInstance().info("yuqz","JPWebServiceImpl->plfxRefundTicket->command:"+ JSON.toJSONString(command));
		return jPWebLocalService.refundTicket(command);
	}
	
	/**
	 * api申请退废
	 */
	@Override
	public YeeXingRefundTicketDTO apiRefundTicket(JPRefundTicketSpiCommand command) {
		HgLogger.getInstance().info("yuqz","WebFlightServiceImpl->apiRefundTicket->command:"+ JSON.toJSONString(command));
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		if(command.getDealerOrderId() != null){
			platformOrderQO.setDealerOrderCode(command.getDealerOrderId());
			platformOrderQO.setRefundType("A");
		}
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		HgLogger.getInstance().info("yuqz", "api申请退废票->jPOrderDTO:" + JSON.toJSONString(jPOrderDTO));
		if(jPOrderDTO != null){
			//设置易行订单号
			command.setOrderid(jPOrderDTO.getYeeXingOrderId());
		}else{
			HgLogger.getInstance().error("yaosanfeng","WebFlightServiceImpl->apiRefundTicket->[api申请退废订单不存在]"+ JSON.toJSONString(jPOrderDTO));
		}
		return jPWebLocalService.refundTicket(command);
	}

	@Override
	protected JPWebLocalService getService() {
		// TODO Auto-generated method stub
		return jPWebLocalService;
	}

	@Override
	protected Class<JPOrderDTO> getDTOClass() {
		// TODO Auto-generated method stub
		return JPOrderDTO.class;
	}

	@Override
	public YeeXingPayJPOrderDTO apiPayJPOrder(JPPayOrderSpiCommand command) {
		YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO = new YeeXingPayJPOrderDTO();
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setDealerOrderCode(command.getDealerOrderId());
		platformOrderQO.setRefundType("A");
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		HgLogger.getInstance().info("yuqz", "支付价格比较：jPOrderDTO：" + JSON.toJSONString(jPOrderDTO));
		//判断传的支付价格和分销保存的价格是否一致
		if(jPOrderDTO.getTotalPrice() == null){
			yeeXingPayJPOrderDTO.setIs_success("F");
			yeeXingPayJPOrderDTO.setError("分销支付价格为空");
		}
		if(command.getTotalPrice() == null){
			yeeXingPayJPOrderDTO.setIs_success("F");
			yeeXingPayJPOrderDTO.setError("经销商支付价格为空");
		}
		if(!compareDouble(jPOrderDTO.getTotalPrice(), command.getTotalPrice())){
			yeeXingPayJPOrderDTO.setIs_success("F");
			yeeXingPayJPOrderDTO.setError("支付价格不匹配");
		}
		if(jPOrderDTO != null){
			//设置易行订单号
			command.setOrderid(jPOrderDTO.getYeeXingOrderId());
		    //设置订单支付总价
			command.setTotalPrice(jPOrderDTO.getTotalPrice());
		}
		yeeXingPayJPOrderDTO = jPOrderLocalService.apiPayJPOrder(command);
		//保存机票日志
		if(yeeXingPayJPOrderDTO !=null && yeeXingPayJPOrderDTO.getIs_success().equals("T")){
			//记录操作日志
			jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jPOrderDTO
					.getId(), "为国内机票订单付款", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "国内机票订单付款成功"));
		}else{
			jPOrderLogLocalService.create(new CreateJPOrderLogCommand(jPOrderDTO
					.getId(), "为国内机票订单付款", command.getFromDealerId(),
					OrderLogConstants.LOG_WORKER_TYPE_DEALER, "国内机票订单付款失败"));
			//自动扣款失败订单状态改为出票失败
			GNJPOrder jpOrder = EntityConvertUtils.convertDtoToEntity(jPOrderDTO, GNJPOrder.class);
			GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
			//1、如果本来支付状态是未支付，改为出票失败
			//2、如果本来是已支付状态，不更改状态（多次请求支付易行返回支付失败的情况）
			if(orderStatus.getPayStatus() == Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_NO_PAY)){
				orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_FAIL));//出票失败
			}
			//乘机人关联订单
			for(GNJPPassenger gnpassenger:jpOrder.getPassengerList()){
				gnpassenger.setJpOrder(jpOrder);
			}
			jPOrderLocalService.update(jpOrder);
		}
		return yeeXingPayJPOrderDTO;
	}

	private boolean compareDouble(Double num1, Double num2) {
		HgLogger.getInstance().info("yuqz", "自动扣款价格比较：num1=" + num1 + ",num2=" + num2);
		if((num1-num2>-0.000001)&&(num1-num2)<0.000001){
			return true;
		}else{ 
			return false;
		}
	}

	@Override
	public void ticketFail(String dealerOrderId) {
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setDealerOrderCode(dealerOrderId);
		platformOrderQO.setRefundType("A");
		JPOrderDTO jPOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		GNJPOrder jpOrder = EntityConvertUtils.convertDtoToEntity(jPOrderDTO, GNJPOrder.class);
		GNJPOrderStatus orderStatus = jpOrder.getOrderStatus();
		//支付状态未支付不变
		orderStatus.setStatus(Integer.parseInt(JPOrderStatusConstant.PLFX_TICKET_FAIL));//出票失败
		jPOrderLocalService.update(jpOrder);
	}

	@Override
	public YeeXingJPAutoOrderDTO apiAutoOrder(JPAutoOrderSpiCommand command) {
		YeeXingJPAutoOrderDTO yeeXingJPAutoOrderDTO = jPWebLocalService.apiAutoJPOrder(command);
		HgLogger.getInstance().info("yaosanfeng","JPWebServiceImpl->apiAutoOrder->yeeXingJPAutoOrderDTO:"+ JSON.toJSONString(yeeXingJPAutoOrderDTO));
		return yeeXingJPAutoOrderDTO;
	}
	
	/***
	 * 同步退票状态
	 */
	public YeeXingRefundQueryOrderDTO refundQueryOrder(JPRefundQueryOrderStatusSpiCommand command){
		
		return jPWebLocalService.refundQueryOrder(command);
	}

	@Override
	public JPPlatPriceDTO dealPlatPrice(YeeXingPriceDTO yeeXingPriceDTO,
			String fromDealerCode) {
		return jPOrderLocalService.dealPlatPrice(yeeXingPriceDTO, fromDealerCode);
	}

}
