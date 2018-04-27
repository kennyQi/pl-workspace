package plfx.gnjp.app.service.api;

import hg.common.util.EntityConvertUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import pay.record.api.client.api.v1.pay.record.request.CreateAirPayReocrdCommand;
import pay.record.api.client.api.v1.pay.record.response.CreateAirPayRecordResponse;
import pay.record.api.client.common.util.PayRecordApiClient;
import plfx.api.client.api.v1.gn.dto.SavePaymentInfoDTO;
import plfx.api.client.api.v1.gn.request.JPCreatePayRecordCommand;
import plfx.api.client.api.v1.gn.request.JPPayOrderGNCommand;
import plfx.gnjp.domain.model.order.GNJPOrder;
import plfx.jp.spi.inter.JPPlatformOrderService;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.pojo.dto.order.JPOrderStatusConstant;
import plfx.yeexing.pojo.dto.order.YeeXingPayJPOrderDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

import com.alibaba.fastjson.JSON;
/****
 * 
 * @类功能说明：调用支付记录日志系统的api
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年11月26日上午10:16:55
 * @版本：V1.0
 *
 */
@Component
public class LogPaymentApi {
	
	@Autowired
	private JPPlatformOrderService jpPlatformOrderService;
	/***
	 * 
	 * @方法功能说明：调用支付记录日志系统保存支付信息(和钱相关的信息)
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月25日上午9:20:16
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean savePaymentInfo(JPCreatePayRecordCommand command){
		HgLogger.getInstance().info("yaosanfeng","GnJpAPIService->savePaymentInfo->command:"+ JSON.toJSONString(command));
		boolean flag = false;
		if(command != null){
			try {
				//从配置文件获取支付记录系统的url,modulus和public_exponent
				PayRecordApiClient client = new PayRecordApiClient(SysProperties.getInstance().get("save_payment_httpUrl"), SysProperties.getInstance().get("modulus"), SysProperties.getInstance().get("public_exponent"));
				//本系统的command转化成支付系统的command
				CreateAirPayReocrdCommand createAirPayReocrdcommand  = JSON.parseObject(JSON.toJSONString(command),CreateAirPayReocrdCommand.class);
				//发送请求
				CreateAirPayRecordResponse response = client.send(createAirPayReocrdcommand, CreateAirPayRecordResponse.class);
				
				SavePaymentInfoDTO savePaymentInfoDTO = JSON.parseObject(JSON.toJSONString(response),SavePaymentInfoDTO.class);
				HgLogger.getInstance().info("yaosanfeng","LogPaymentApi->savePaymentInfo->[支付记录系统返回]:"+ JSON.toJSONString(savePaymentInfoDTO));
				if(savePaymentInfoDTO != null && StringUtils.isNotBlank(savePaymentInfoDTO.getResult()) && "1".equals(savePaymentInfoDTO.getResult())){
					flag = true;
				}	
			} catch (Exception e) {
				HgLogger.getInstance().error("yaosanfeng","LogPaymentApi->savePaymentInfo->Exception:"+ HgLogger.getStackTrace(e));
			}
			
		}
		return flag;
	}

	/***
	 * 
	 * @方法功能说明：从用户支付信息中得到调用支付记录系统的command
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月26日上午9:56:41
	 * @修改内容：
	 * @参数：@param jPCreatePayRecordCommand
	 * @参数：@param yeeXingJPOrderDTO
	 * @参数：@return
	 * @return:JPCreatePayRecordCommand
	 * @throws
	 */
//	public JPCreatePayRecordCommand getJPCreatePayRecordCommand(JPBookTicketGNCommand jPBookTicketGNCommand ,YeeXingJPOrderDTO yeeXingJPOrderDTO) {
//		//项目来源标志,付款账号  支付流水号  经销商订单号  支付方式   收入金额  支出金额  订单状态  支付状态  支付时间  备注 -----------> 这些字段经销商必传过来,下面操作补上没有的信息
//		//保存IP地址,舱位代码,舱位名称在支付记录command
//		JPCreatePayRecordCommand command = jPBookTicketGNCommand.getCreatePayRecordCommand();
//		command.setFromDealerIp(jPBookTicketGNCommand.getFromDealerIp());
//		command.setCabinName(jPBookTicketGNCommand.getCabinName());
//		command.setClassCode(jPBookTicketGNCommand.getCabinCode());
//		command.setFromClientType(jPBookTicketGNCommand.getFromClientType());
//		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
//		platformOrderQO.setYeeXingOrderId(yeeXingJPOrderDTO.getOrderid());
//		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
//		if(null != jpOrderDTO){
//			//航空公司
//			command.setAirCompName(jpOrderDTO.getAirCompName());
//			//订购人
//			command.setBooker(jpOrderDTO.getLoginName());
//			//目的地
//			command.setDestCity(jpOrderDTO.getEndCityName());
//			//出发地
//			command.setStartCity(jpOrderDTO.getStartCityName());
//			//航班号
//			command.setFlightNo(jpOrderDTO.getFlightNo());
//			//乘机人
//			command.setPassengers(this.getPassengers(jpOrderDTO));
//			//支付平台
//			command.setPayPlatform(Integer.parseInt(JPOrderStatusConstant.PAY_PLATFORM_ZFB));
//			//平台订单号
//			command.setPlatOrderNo(yeeXingJPOrderDTO.getOut_orderid());
//			//佣金
//		    command.setBrokerage(jpOrderDTO.getCommAmount());
//			//折扣返点
//			command.setRebate(jpOrderDTO.getDisc());
//			//记录类型(用户->直销)
//			command.setRecordType(Integer.parseInt(JPOrderStatusConstant.RECORD_TYEP_UZ));
//			//易行订单号
//			command.setSupplierOrderNo(yeeXingJPOrderDTO.getOrderid());
//		}
//		return command;
//	}
	
	/***
	 * 
	 * @方法功能说明：从自动扣款中得到调用支付记录系统的command
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月26日上午9:56:41
	 * @修改内容：
	 * @参数：@param jPCreatePayRecordCommand
	 * @参数：@param yeeXingJPOrderDTO
	 * @参数：@return
	 * @return:JPCreatePayRecordCommand
	 * @throws
	 */
	public JPCreatePayRecordCommand getJPCreatePayRecordCommand(YeeXingPayJPOrderDTO yeeXingPayJPOrderDTO,JPPayOrderGNCommand jPPayOrderGNCommand) {
		PlatformOrderQO platformOrderQO = new PlatformOrderQO();
		platformOrderQO.setYeeXingOrderId(yeeXingPayJPOrderDTO.getOrderid());
		JPOrderDTO jpOrderDTO = jpPlatformOrderService.queryUnique(platformOrderQO);
		JPCreatePayRecordCommand command = new JPCreatePayRecordCommand();
        if(null != jpOrderDTO){
        	//来源Ip
        	command.setFromDealerIp(jPPayOrderGNCommand.getFromDealerIp());
        	//pc  or 移动端
        	command.setFromClientType(jPPayOrderGNCommand.getFromClientType());
			//舱位名称
	    	command.setCabinName(jpOrderDTO.getCabinName());
	    	//舱位代码
	    	command.setClassCode(jpOrderDTO.getClassCode());
	    	//经销商订单号
	    	command.setDealerOrderNo(jpOrderDTO.getDealerOrderCode());
	    	//备注
	    	command.setRemarks("机票自动扣款成功");
	    	//项目来源
	    	command.setFromProjectCode(Integer.parseInt(JPOrderStatusConstant.FROM_PROJECT_PLFX));
	    	
	    	//航空公司
			command.setAirCompName(jpOrderDTO.getAirCompName());
			//订购人
			command.setBooker(jpOrderDTO.getLoginName());
			//目的地
			command.setDestCity(jpOrderDTO.getEndCityName());
			//出发地
			command.setStartCity(jpOrderDTO.getStartCityName());
			//航班号
			command.setFlightNo(jpOrderDTO.getFlightNo());
			//乘机人
			command.setPassengers(this.getPassengers(jpOrderDTO));
			//支付平台
			command.setPayPlatform(Integer.parseInt(JPOrderStatusConstant.PAY_PLATFORM_ZFB));
			//平台订单号
			command.setPlatOrderNo(jpOrderDTO.getOrderNo());
			//佣金
		    command.setBrokerage(jpOrderDTO.getCommAmount());
			//折扣返点
			command.setRebate(jpOrderDTO.getDisc());
			//记录类型(供应商->分销)
			command.setRecordType(Integer.parseInt(JPOrderStatusConstant.RECORD_TYEP_GF));
			//易行订单号
			command.setSupplierNo(jpOrderDTO.getYeeXingOrderId());
			//---------------------------------------
			//收入金额
			command.setIncomeMoney(0.0);
			//订单状态
			command.setOrderStatus(jpOrderDTO.getOrderStatus().getStatus().toString());
			//付款帐号
			command.setPayAccountNo("易行付款账户");
			//支出(支付给供应商的金额)
			command.setPayMoney(jpOrderDTO.getTotalPrice());
			//支付方式
			command.setPayPlatform(Integer.parseInt(JPOrderStatusConstant.PAY_PLATFORM_ZFB));
			//支付流水号
			command.setPaySerialNumber(jpOrderDTO.getPlatPaySerialNumber());
			//订单支付状态
			command.setPayStatus(jpOrderDTO.getOrderStatus().getPayStatus().toString());
			//支付时间
			command.setPayTime(new Date());
			//订单金额
			command.setOrderPrice(jpOrderDTO.getUserPayAmount());

        }
    	
		return command;
	}
	
	/***
	 * 
	 * @方法功能说明：从供应商通知退款成功或取消成功中得到调用支付记录系统的command
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年11月26日上午10:06:54
	 * @修改内容：
	 * @参数：@param jpOrder
	 * @参数：@return
	 * @return:JPCreatePayRecordCommand
	 * @throws
	 */
    public JPCreatePayRecordCommand getJPCreatePayRecordCommand(GNJPOrder jpOrder) {
    	JPCreatePayRecordCommand command = new JPCreatePayRecordCommand();
       
    	if(null != jpOrder){
    		 //舱位名称
        	command.setCabinName(jpOrder.getCabinName());
        	//舱位代码
        	command.setClassCode(jpOrder.getClassCode());
        	//经销商订单号
        	command.setDealerOrderNo(jpOrder.getDealerOrderCode());
        	//备注
        	command.setRemarks("机票退款成功");
        	//项目来源
        	command.setFromProjectCode(Integer.parseInt(JPOrderStatusConstant.FROM_PROJECT_PLFX));
			//航空公司
			command.setAirCompName(jpOrder.getAirCompName());
			//订购人
			command.setBooker(jpOrder.getLoginName());
			//目的地
			command.setDestCity(jpOrder.getEndCityName());
			//出发地
			command.setStartCity(jpOrder.getStartCityName());
			//航班号
			command.setFlightNo(jpOrder.getFlightNo());
			//乘机人
			JPOrderDTO jpOrderDTO = EntityConvertUtils.convertEntityToDto(jpOrder, JPOrderDTO.class);
			command.setPassengers(this.getPassengers(jpOrderDTO));
			//支付平台
			command.setPayPlatform(Integer.parseInt(JPOrderStatusConstant.PAY_PLATFORM_ZFB));
			//平台订单号
			command.setPlatOrderNo(jpOrder.getOrderNo());
			//佣金
		    command.setBrokerage(jpOrder.getCommAmount());
			//折扣返点
			command.setRebate(jpOrder.getDisc());
			//记录类型(供应商->分销)
			command.setRecordType(Integer.parseInt(JPOrderStatusConstant.RECORD_TYEP_GF));
			//易行订单号
			command.setSupplierNo(jpOrder.getYeeXingOrderId());
			//---------------------------------------
			//退款金额(收入)
			command.setIncomeMoney(jpOrder.getRefundPrice());
			//订单状态
			command.setOrderStatus(jpOrder.getOrderStatus().getStatus().toString());
			//付款帐号(默认易行付款账户)
			command.setPayAccountNo("易行付款账户");
			//支出
			command.setPayMoney(0.0);
			//支付方式
			command.setPayPlatform(Integer.parseInt(JPOrderStatusConstant.PAY_PLATFORM_ZFB));
			//退款流水号
			command.setPaySerialNumber(jpOrder.getPlatPaySerialNumber());
			//订单支付状态
			command.setPayStatus(jpOrder.getOrderStatus().getPayStatus().toString());
			//退款时间
			command.setPayTime(new Date());
			//订单金额
			command.setOrderPrice(jpOrder.getUserPayAmount());

		}
		return command;
	}
    
    /****"
     * 
     * @方法功能说明：组装乘机人  多乘机人用|
     * @修改者名字：yaosanfeng
     * @修改时间：2015年12月8日下午3:54:27
     * @修改内容：
     * @参数：@param yeeXingJPOrderDTO
     * @参数：@return
     * @return:String
     * @throws
     */
    public String getPassengers(JPOrderDTO jpOrderDTO){
    	//乘机人  | 乘机人
		StringBuilder passengers = new  StringBuilder();
		for(int i = 0; i < jpOrderDTO.getPassengerList().size(); i++){
			if(i!= 0){
				passengers.append("|");
			}
			passengers.append(jpOrderDTO.getPassengerList().get(i).getName());
		}
    	return passengers.toString();	
    }

}
