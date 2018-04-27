package hsl.spi.inter.api.jp;

import hg.common.component.BaseQo;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.command.UpdateJPOrderStatusCommand;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.jp.FlightPolicyDTO;
import hsl.pojo.dto.jp.JPOrderCreateDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.RefundActionTypeDTO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.command.JPOrderCommand;
import hsl.spi.inter.BaseSpiService;

import java.util.List;

import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.request.qo.jp.JPAirCodeQO;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.request.qo.jp.JPPolicyQO;

public interface JPService extends BaseSpiService<FlightDTO, BaseQo> {
	
	/**
	 * 航班查询
	 * @param qo
	 * @return
	 */
	public List<FlightDTO> queryFlight(JPFlightQO qo);
	
	/**
	 * 航班政策查询
	 * @param qo
	 * @return
	 */
	public FlightPolicyDTO queryFlightPolicy(JPPolicyQO qo);
	
	/**
	 * 出票
	 * @param command
	 * @return
	 */
	public void askOrderTicket(JPAskOrderTicketCommand command);
	
	/**
	 * 订单查询
	 * @param qo
	 * @return
	 */
	public List<JPOrderDTO> queryOrder(HslJPOrderQO qo);
	
	/**
	 * 订单创建
	 * @param command
	 * @return
	 */
	public JPOrderCreateDTO orderCreate(JPOrderCreateCommand command);
	
	/**
	 * 订单取消
	 * @param command
	 * @return
	 */
	public JPOrderDTO cancelTicket(JPCancelTicketCommand command);
	
	/**
	 * 城市机场三字码查询（返回所有数据）
	 * @return
	 */
	public List<CityAirCodeDTO> queryCityAirCode(JPAirCodeQO jpAirCodeQO);
	
	/**
	 * 通过出票平台编码查询退废票原因
	 * @param platCode
	 * @return
	 */
	public List<RefundActionTypeDTO> queryCancelOrderTicketReason(String platCode);
	
	/**
	 * 通过商城订单号 更新订单状态
	 * @param clientTradeNo
	 * @param status
	 * @return
	 */
	public boolean updateOrderStatus(String dealerOrderCode, int orderStatus, int payStatus);
	
	/**
	 * 退款时更新机票订单状态
	 * @param command
	 * @return
	 */
	boolean updateOrderStatus(UpdateJPOrderStatusCommand command);
	
	/**
	 * 退款后根据支付宝返回的result_details更新订单
	 * @param resultDetails
	 * @return
	 */
	boolean OrderRefund(String resultDetails);
	
	/**
	 * 出票完成通知
	 * @param command
	 * @return
	 */
	boolean orderTicketNotify(JPOrderCommand command);
	
	/**
	 * 退废票完成通知
	 * @param command
	 * @return
	 */
	boolean orderRefundNotify(JPOrderCommand command);

	/**
	 * 查询详细订单
	 * @param qo
	 * @return
	 */
	public JPOrderDTO queryOrderDetail(HslJPOrderQO qo);
	
	
}
