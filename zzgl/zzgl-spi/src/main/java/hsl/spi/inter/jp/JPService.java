package hsl.spi.inter.jp;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.command.jp.JPOrderCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.jp.plfx.CancelTicketGNCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.command.jp.plfx.RefundTicketGNCommand;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.RefundTicketGNDTO;
import hsl.pojo.dto.jp.plfx.JPBookOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPPayOrderGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryFlightListGNDTO;
import hsl.pojo.dto.jp.plfx.JPQueryHighPolicyGNDTO;
import hsl.pojo.exception.GNFlightException;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.jp.plfx.JPFlightGNQO;
import hsl.pojo.qo.jp.plfx.JPPolicyGNQO;
import hsl.spi.inter.BaseSpiService;
import hsl.spi.qo.sys.CityAirCodeQO;
import java.util.List;
public interface JPService extends BaseSpiService<FlightOrderDTO, FlightOrderQO> {
	
	/**
	 * 航班查询
	 * @param qo
	 * @return
	 */
	public JPQueryFlightListGNDTO queryFlight(JPFlightGNQO qo) throws GNFlightException;
	
	/**
	 * 航班政策查询
	 * @param qo
	 * @return
	 */
	public JPQueryHighPolicyGNDTO queryFlightPolicy(JPPolicyGNQO qo)throws GNFlightException;
	
	/**
	 * 出票
	 * @param command
	 * @return
	 */
	public JPPayOrderGNDTO askOrderTicket(JPPayOrderGNCommand command)throws GNFlightException;
	/**
	 * 订单查询
	 * @param qo
	 * @return
	 */
	public List<FlightOrderDTO> queryOrder(FlightOrderQO qo);
	
	/**
	 * 分销订单创建
	 * @param command
	 * @return
	 */
	public JPBookOrderGNDTO orderCreate(BookGNFlightCommand command)throws GNFlightException ;
	
	/**
	 * 订单取消
	 * @param command
	 * @return
	 */
	public FlightOrderDTO cancelTicket(CancelTicketGNCommand command)throws Exception ;
	/**
	 * 退款时更新机票订单状态
	 * @param command
	 * @return
	 */
	public boolean updateOrderStatus(UpdateJPOrderStatusCommand command);
	/**
	 * 退款时更新机票订单状态
	 * @param command
	 * @return
	 */
	public boolean updateOrderStatusById(UpdateJPOrderStatusCommand command);
	
	/**
	 * 退款后根据支付宝返回的result_details更新订单
	 * @param resultDetails
	 * @return
	 */
	public boolean OrderRefund(String resultDetails);

	/**
	 * 查询详细订单
	 * @param qo
	 * @return
	 */
	public FlightOrderDTO queryOrderDetail(FlightOrderQO qo);
	/******************通知类 修改方法***************************/
//	/**
//	 * @方法功能说明：同步订单(机票订单出票通知)
//	 * @修改者名字：chenxy
//	 * @修改时间：2015年7月24日上午8:28:03
//	 * @修改内容：
//	 * @参数：@param command
//	 * @参数：@return
//	 * @return:boolean
//	 * @throws
//	 */
//	public boolean orderTicketNotify(JPOrderCommand command);
//	/**
//	 * @方法功能说明：取消机票订单通知
//	 * @修改者名字：chenxy
//	 * @修改时间：2015年8月3日上午9:55:06
//	 * @修改内容：
//	 * @参数：@param command
//	 * @参数：@return
//	 * @return:boolean
//	 * @throws
//	 */
//	public boolean cancelFightOrderNotify(JPOrderCommand command);
	
	public boolean notifyUpdateFightOrder(JPOrderCommand command);
	/**
	 * @方法功能说明：申请退费票
	 * @修改者名字：renfeng
	 * @修改时间：2015年8月5日上午9:55:29
	 * @修改内容：
	 * @参数：@param refundTicketGNCommand
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:RefundTicketGNDTO
	 * @throws
	 */
	public RefundTicketGNDTO refundTicket(RefundTicketGNCommand refundTicketGNCommand)throws GNFlightException;
	/**
	 * 查询机场编码信息
	 * @param cityAirCodeQO
	 * @return
	 */
	public CityAirCodeDTO queryLocalCityAirCode(CityAirCodeQO cityAirCodeQO);
	/**
	 * 
	 * @方法功能说明：创建本地订单
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-8下午3:23:06
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws GNFlightException
	 * @return:FlightOrderDTO
	 * @throws
	 */
	public FlightOrderDTO createLocalityOrder(BookGNFlightCommand command)throws GNFlightException ;

	public List<CityAirCodeDTO> queryCityAirCode(CityAirCodeQO cityAirCodeQO) ;
}
