package plfx.xl.spi.inter;

import java.util.List;

import plfx.xl.pojo.command.line.UpdateLineOrderStatusCommand;
import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import plfx.xl.pojo.command.order.CancleLineOrderCommand;
import plfx.xl.pojo.command.order.ChangeLineOrderStatusCommand;
import plfx.xl.pojo.command.order.CreateLineOrderCommand;
import plfx.xl.pojo.command.order.ModifyLineOrderTravelerCommand;
import plfx.xl.pojo.command.order.ModifyPaymentLineOrderCommand;
import plfx.xl.pojo.command.pay.BatchPayLineOrderCommand;
import plfx.xl.pojo.dto.order.LineOrderDTO;
import plfx.xl.pojo.dto.order.LineOrderPaymentDTO;
import plfx.xl.pojo.exception.SlfxXlException;
import plfx.xl.pojo.qo.LineOrderPaymentQO;
import plfx.xl.pojo.qo.LineOrderQO;

/**
 * 
 * @类功能说明：线路订单service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日下午5:42:58
 * @版本：V1.0
 *
 */
public interface LineOrderService extends BaseXlSpiService<LineOrderDTO, LineOrderQO>{
		
	/**
	 * 
	 * @方法功能说明：查询线路订单详情
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月16日下午2:48:41
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO queryOrderDetail(LineOrderQO qo);
	
	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日上午10:38:00
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean cancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException;
	
	/**
	 * 
	 * @方法功能说明：更改订单状态
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:15:38
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws SlfxXlException
	 * @return:Boolean
	 * @throws
	 */
	public Boolean changeLineOrderStatus(ChangeLineOrderStatusCommand command)throws SlfxXlException;
	
	/***
	 * 
	 * @方法功能说明：修改支付游玩人全款数目
	 * @修改者名字：yaosanfeng
	 * @修改时间：2015年5月28日下午3:53:53
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws SlfxXlException
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyPaymentLineOrder(ModifyPaymentLineOrderCommand command) throws SlfxXlException;
	/**
	 * 
	 * @方法功能说明：查询订单支付信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:52:19
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<LineOrderPaymentDTO>
	 * @throws
	 */
	public List<LineOrderPaymentDTO> queryLineOrderPayInfo(LineOrderPaymentQO qo);
	
	/**
	 * 
	 * @方法功能说明：定时更新已过期的订单那状态
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月14日下午5:55:36
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public boolean updateLineOrderStatus(UpdateLineOrderStatusCommand command);

/*---------------------admin使用上面，shop使用下面----------------------*/
	

	/**
	 * 
	 * @方法功能说明：创建线路订单
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月24日下午2:48:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO shopCreateLineOrder(CreateLineOrderCommand command) throws SlfxXlException;
	
	/**
	 * 
	 * @方法功能说明：商城查询洗线路订单
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月23日下午3:09:27
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<LineOrderDTO>
	 * @throws
	 */
	public List<LineOrderDTO> shopQueryLineOrderList(LineOrderQO qo);
	
	/**
	 * 
	 * @方法功能说明：商城取消订单
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月26日下午4:04:33
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean shopCancleLineOrder(CancleLineOrderCommand command) throws SlfxXlException;
	
	/**
	 * 
	 * @方法功能说明：商城用户支付线路订单信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月23日下午4:52:19
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<LineOrderPaymentDTO>
	 * @throws
	 */
	public Boolean shopPayLineOrder(BatchPayLineOrderCommand command)throws SlfxXlException;

	/**
	 * 
	 * @方法功能说明：商城通知经销商订单状态改变
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月13日上午10:01:58
	 * @修改内容：
	 * @参数：@param xlUpdateOrderStatusCommand
	 * @return:void
	 * @throws
	 */
	public void sendLineOrderUpdateMessage(XLUpdateOrderStatusMessageApiCommand xlUpdateOrderStatusCommand);

	/**
	 * 
	 * @方法功能说明：经销商通知商城改变线路订单游玩人信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月15日上午8:33:07
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:LineOrderDTO
	 * @throws
	 */
	public LineOrderDTO modifyLineOrderTraveler(ModifyLineOrderTravelerCommand command);

	/**
	 * 
	 * @方法功能说明：通知经销商修改线路信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月14日下午1:42:42
	 * @修改内容：
	 * @参数：@param xlUpdateLineMessageApiCommand
	 * @return:void
	 * @throws
	 */
	public void sendModifyLineImageMessage(XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand);

	/**
	 * 
	 * @方法功能说明：批量修改游玩人金额
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月3日下午3:56:46
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean batchModifyPaymentLineOrder(ModifyPaymentLineOrderCommand command);

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
	public void sendLineOrderUpdateSalePriceMessage(XLUpdateOrderSalePriceMessageApiCommand apiCommand);
	
}
