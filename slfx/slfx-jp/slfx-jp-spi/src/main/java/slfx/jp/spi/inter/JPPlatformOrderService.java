package slfx.jp.spi.inter;

import hg.common.page.Pagination;

import java.util.List;

import slfx.jp.command.admin.AdminCancelOrderCommand;
import slfx.jp.command.admin.ApplyRefundCommand;
import slfx.jp.command.admin.JPOrderCommand;
import slfx.jp.command.admin.jp.JPAskOrderTicketSpiCommand;
import slfx.jp.command.admin.jp.JPCancelTicketSpiCommand;
import slfx.jp.command.admin.jp.JPOrderCreateSpiCommand;
import slfx.jp.pojo.dto.order.JPOrderDTO;
import slfx.jp.pojo.dto.order.PlatformOrderDetailDTO;
import slfx.jp.pojo.dto.supplier.yg.YGRefundActionTypesDTO;
import slfx.jp.pojo.exception.JPOrderException;
import slfx.jp.qo.admin.PlatformOrderQO;
import slfx.jp.qo.api.JPOrderSpiQO;

/**
 * 
 * @类功能说明：平台订单SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:10:45
 * @版本：V1.0
 *
 */
public interface JPPlatformOrderService extends BaseJpSpiService<JPOrderDTO, PlatformOrderQO>{
	
	/**
	 * 下单--供商城使用
	 * @param jPOrderCreateCommand
	 * @return
	 */
	public JPOrderDTO shopCreateOrder(JPOrderCreateSpiCommand jPOrderCreateSpiCommand ) throws JPOrderException;
	

	
	/**
	 * 请求出票--供商城使用
	 * @param 
	 * @return
	 */
	public boolean shopAskOrderTicket(JPAskOrderTicketSpiCommand jpAskOrderTicketSpiCommand) throws JPOrderException;
	
	/**
	 * 分页查询--供商城使用
	 * @param pagination
	 * @return
	 */
	public Pagination shopQueryOrderList(JPOrderSpiQO jpOrderSpiQO)throws JPOrderException;
	
	/**
	 * 取消订单--供商城使用
	 * @param jpCancelTicketCommand
	 * @return boolean
	 */
	public boolean shopCancelOrder(JPCancelTicketSpiCommand jpCancelTicketSpiCommand)throws JPOrderException;
	/**
	 * 
	 */
	public boolean shopRefundOrder(JPCancelTicketSpiCommand jpCancelTicketSpiCommand) throws JPOrderException;
	
	/**
	 * 分页查询--供admin使用
	 * @param pagination
	 * @return
	 */
	public Pagination queryOrderList(Pagination pagination);

	/**
	 * 订单详情查询--供admin使用
	 * @param platformOrderQO
	 * @return
	 */
	public PlatformOrderDetailDTO queryOrderDetail(PlatformOrderQO platformOrderQO);

	/**
	 * 取消订单--供admin使用
	 * @param adminCancelOrderCommand
	 */
	public boolean adminCancelOrder(AdminCancelOrderCommand adminCancelOrderCommand);
	
	/**
	 * 取消订单--供admin使用
	 * @param adminCancelOrderCommand
	 */
	public void adminRefundOrder(ApplyRefundCommand applyRefundCommand);
	
	/**
	 * 
	 * @方法功能说明：机票管理-财务管理-查看订单明细功能：机票订单条件查询列表（供Admin端使用）
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月6日下午6:26:52
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryFJPOrderList(Pagination pagination);
	
	/**
	 * 
	 * @方法功能说明：机票异常订单查询
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月12日下午3:27:31
	 * @修改内容：
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:List<JPOrderDTO>
	 * @throws
	 */
	public List<JPOrderDTO> queryErrorJPOrderList(PlatformOrderQO platformOrderQO);

	/**
	 * 
	 * @方法功能说明：查询订单列表
	 * @修改者名字：tandeng
	 * @修改时间：2014年9月16日下午7:30:29
	 * @修改内容：
	 * @参数：@param platformOrderQO
	 * @参数：@return
	 * @return:List<JPOrderDTO>
	 * @throws
	 */
	public List<JPOrderDTO> queryJPOrderList(PlatformOrderQO platformOrderQO);
	
	/**
	 * 
	 * @方法功能说明：保存异常订单
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月14日上午10:54:17
	 * @修改内容：
	 * @参数：@param errorJPOrder
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand);
	
	/**
	 * 更新订单状态
	 * @param command
	 * @return
	 */
	public boolean updateOrderStatus(JPOrderCommand command);
	
	/**
	 * 易购通知出票失败
	 * @param command
	 * @return
	 */
	public boolean getTicketNoFailure(JPOrderCommand command);
	
	/**
	 * 处理支付宝退款业务
	 * @param resultDetails
	 * @return
	 */
	public boolean refund(String resultDetails);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月21日下午1:25:31
	 * @修改内容：
	 * @参数：@param platCode
	 * @参数：@return
	 * @return:YGRefundActionTypesDTO
	 * @throws
	 */
	public YGRefundActionTypesDTO getRefundActionType(final String platCode);
	
	/**
	 * 退废票通知处理
	 * @param command
	 * @return
	public boolean cancelOrderNotify(JPOrderCommand command);
	 */
	
	/**
	 * 
	 * @方法功能说明：处理易购退废票通知
	 * @修改者名字：tandeng
	 * @修改时间：2014年11月25日下午3:03:25
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean backOrDiscardTicketNotify(JPOrderCommand command);

}
