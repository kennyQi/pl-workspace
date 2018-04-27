package hsl.spi.inter.line;
import hsl.pojo.command.line.UpdateLineOrderPayInfoCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import hsl.pojo.command.line.CancelLineOrderCommand;
import hsl.pojo.command.line.HslCreateLineOrderCommand;
import hsl.pojo.command.line.UpdateLineOrderStatusCommand;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.exception.LineException;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.spi.inter.BaseSpiService;
/**
 * @类功能说明：线路订单service
 * @类修改者：
 * @修改日期：2015年2月3日上午10:41:21
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年2月3日上午10:41:21
 *
 */
public interface HslLineOrderService extends BaseSpiService<LineOrderDTO,HslLineOrderQO >{
	
	/**
	 * @throws LineException 
	 * @功能说明：创建线路订单
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月26日
	 */
	public LineOrderDTO createLineOrder(HslCreateLineOrderCommand command) throws LineException;
	
	/**
	 * @方法功能说明：（直销Admin端）取消线路订单
	 * @修改者名字：renfeng
	 * @修改时间：2015年5月14日下午2:48:38
	 * @修改内容：修改游客订单状态为取消状态，支付状态不变
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean cancelLineOrder(CancelLineOrderCommand command);
	
	/**
	 * @方法功能说明：更新线路订单状态
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月3日下午4:19:51
	 * @修改内容：本地修改并通知分销
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public boolean updateLineOrderStatus(UpdateLineOrderStatusCommand command) throws Exception;
	
	/**
	 * @param <XLUpdateOrderStatusCommand>
	 * @方法功能说明：接收分销通知，修改本地线路订单状态
	 * @修改者名字：renfeng
	 * @修改时间：2015年4月13日上午9:58:46
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws LineException
	 * @return:boolean
	 * @throws
	 */                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	public boolean updateLineOrderStatus(XLUpdateOrderStatusMessageApiCommand command) throws LineException;

	/**
	 * 
	 * @方法功能说明：接收分销通知，修改本地线路订单金额
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月4日上午10:15:35
	 * @修改内容：
	 * @参数：@param updateLineOrderSalePriceCommand
	 * @return:Boolean
	 * @throws
	 */
	public boolean updateLineOrderSalePrice(XLUpdateOrderSalePriceMessageApiCommand updateLineOrderSalePriceCommand);
	/**
	 * @方法功能说明: 更新线路订单支付信息
	 * @修改者名字：chenxy
	 * @修改时间 2015-11-18 14:53:53
	 * @修改内容：
	 * @param command
	 * @return 
	 * @throws 
	 */
	public void updateLineOrderPayInfo(UpdateLineOrderPayInfoCommand command);
	/**
	 * @方法功能说明: 修改线路现金支付金额(平均每个人)
	 * @修改者名字：zhaows
	 * @修改时间 2015-12-11 14:53:53
	 * @修改内容：
	 * @param command
	 * @return
	 * @throws
	 */
	public void updateLineOrderPayCash(UpdateLineOrderPayInfoCommand command);
		
}
