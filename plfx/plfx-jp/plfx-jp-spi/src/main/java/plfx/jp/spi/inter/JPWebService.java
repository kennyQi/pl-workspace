package plfx.jp.spi.inter;

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
import plfx.yeexing.pojo.dto.order.JPPlatPriceDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPAutoOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingPayJPOrderDTO;
import plfx.yeexing.pojo.dto.order.YeeXingRefundQueryOrderDTO;
import plfx.yeexing.qo.api.JPFlightSpiQO;
import plfx.yeexing.qo.api.JPPolicySpiQO;


/****
 * 
 * @类功能说明：平台航班查询SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日上午11:33:46
 * @版本：V1.0
 *
 */
public interface JPWebService {

	//易行天下开始了---------------------------------------------------------------
	/***
	 * 
	 * @方法功能说明：查询航班列表
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月19日上午10:23:49
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:PlatformQueryWebFlightsDTO
	 * @throws
	 */
	public  YeeXingQueryWebFlightsDTO  queryFlightList(JPFlightSpiQO qo);
    
	/***
	 * 
	 * @方法功能说明：政策查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月29日下午5:29:54
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:PlfxFlightPolicyDTO
	 * @throws
	 */
	public YeeXingFlightPolicyDTO queryPolicy(JPPolicySpiQO qo);
	
	
	/***
	 * 
	 * @方法功能说明：生成订单
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月19日下午3:44:57
	 * @修改内容：
	 * @参数：@param jPOrderCreateSpiCommand
	 * @参数：@return
	 * @参数：@throws JPOrderException
	 * @return:JPOrderDTO
	 * @throws
	 */
	public YeeXingJPOrderDTO shopCreateJPOrder(JPBookTicketSpiCommand command );
	
	/****
	 * 
	 * @方法功能说明：根据航班号查询
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年6月30日下午4:19:47
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:PlfxQueryWebFlightsDTO
	 * @throws
	 */
	public YeeXingQueryWebFlightsDTO queryYXFlightsByFlightNo(JPFlightSpiQO qo);
	
	/****
	 * 
	 * @方法功能说明：admin申请取消
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月3日下午7:02:55
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:PlfxCancelTicketDTO
	 * @throws
	 */
	public YeeXingCancelTicketDTO plfxCancelTicket(JPCancelTicketSpiCommand command);
	
	/**
	 * 
	 * @方法功能说明：api接口申请取消
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月20日上午11:00:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingCancelTicketDTO
	 * @throws
	 */
	public YeeXingCancelTicketDTO apiCancelTicket(JPCancelTicketSpiCommand command);
	
	/****
	 * 
	 * @方法功能说明：admin申请退废票
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年7月3日下午7:04:17
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:PlfxRefundTicketDTO
	 * @throws
	 */
	public  YeeXingRefundTicketDTO plfxRefundTicket(JPRefundTicketSpiCommand command);
	
	/**
	 * 
	 * @方法功能说明：api申请退废票
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月20日上午11:04:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingRefundTicketDTO
	 * @throws
	 */
	public  YeeXingRefundTicketDTO apiRefundTicket(JPRefundTicketSpiCommand command);

	/**
	 * 
	 * @方法功能说明：api调用自动扣款
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月21日下午5:33:40
	 * @修改内容：
	 * @参数：@param spiCommand
	 * @参数：@return
	 * @return:YeeXingPayJPOrderDTO
	 * @throws
	 */
	public YeeXingPayJPOrderDTO apiPayJPOrder(JPPayOrderSpiCommand spiCommand);

	/**
	 * 
	 * @方法功能说明：经销商id存在的情况下，自动扣款参数有问题，更改订单状态为出票失败
	 * @修改者名字：yuqz
	 * @修改时间：2015年8月24日下午1:53:07
	 * @修改内容：
	 * @参数：@param dealerOrderId
	 * @return:void
	 * @throws
	 */
	public void ticketFail(String dealerOrderId);
	
	/****
	 * 
	 * @方法功能说明：生成订单并自动扣款完成支付
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月8日上午9:57:32
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:YeeXingJPAutoOrderDTO
	 * @throws
	 */
	public YeeXingJPAutoOrderDTO apiAutoOrder(JPAutoOrderSpiCommand command);
	
	/****
	 * 
	 * @方法功能说明：查询退票状态接口
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年9月16日上午10:48:06
	 * @修改内容：
	 * @参数：@return
	 * @return:YeeXingRefundQueryOrderDTO
	 * @throws
	 */
	public YeeXingRefundQueryOrderDTO  refundQueryOrder(JPRefundQueryOrderStatusSpiCommand command);

	/**
	 * 
	 * @方法功能说明：计算平台价格
	 * @修改者名字：yuqz
	 * @修改时间：2015年10月9日上午11:03:17
	 * @修改内容：
	 * @参数：@param yeeXingPriceDTO
	 * @参数：@param fromDealerCode
	 * @参数：@return
	 * @return:JPPlatPriceDTO
	 * @throws
	 */
	public JPPlatPriceDTO dealPlatPrice(YeeXingPriceDTO yeeXingPriceDTO,String fromDealerCode);
}
