package plfx.jp.spi.inter;

import hg.common.page.Pagination;
import plfx.yeexing.pojo.command.order.JPOrderCancelCommand;
import plfx.yeexing.pojo.command.order.JPOrderCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefundCommand;
import plfx.yeexing.pojo.command.order.JPOrderRefuseCommand;
import plfx.yeexing.pojo.command.order.JPPayNotifyCommand;
import plfx.yeexing.pojo.dto.order.JPOrderDTO;
import plfx.yeexing.qo.admin.PlatformOrderQO;

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
	 * 
	 * @方法功能说明：更新订单信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月29日下午5:12:44
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public Boolean updateJPOrder(JPOrderCommand command);

	/**
	 * 
	 * @方法功能说明：更新订单支付信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午1:53:55
	 * @修改内容：
	 * @参数：@param jpPayNotifyCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updatePayStatus(JPPayNotifyCommand jpPayNotifyCommand);

	/**
	 * 
	 * @方法功能说明：取消订单
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午2:37:55
	 * @修改内容：
	 * @参数：@param jpOrderCancelCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean jpOrderCancel(JPOrderCancelCommand jpOrderCancelCommand);

	/**
	 * 
	 * @方法功能说明：处理退废票通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午4:17:34
	 * @修改内容：
	 * @参数：@param jpOrderRefundCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean jpOrderRefund(JPOrderRefundCommand jpOrderRefundCommand);

	/**
	 * 
	 * @方法功能说明：处理拒绝出票通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月30日下午5:18:43
	 * @修改内容：
	 * @参数：@param jpOrderRefuseCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean jpOrderRefuse(JPOrderRefuseCommand jpOrderRefuseCommand);

	/**
	 * 
	 * @方法功能说明：机票管理-财务管理-查看订单明细功能：机票订单条件查询列表（供Admin端使用）
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日上午10:58:27
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryFJPOrderList(Pagination pagination);

	/**
	 * 
	 * @方法功能说明：保存异常订单
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午1:51:20
	 * @修改内容：
	 * @参数：@param jpOrderCommand
	 * @参数：@returnT
	 * @return:boolean
	 * @throws
	 */
	public boolean saveErrorJPOrder(JPOrderCommand jpOrderCommand);

}
