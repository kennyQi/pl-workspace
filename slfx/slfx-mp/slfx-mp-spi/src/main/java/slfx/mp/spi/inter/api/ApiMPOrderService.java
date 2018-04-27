package slfx.mp.spi.inter.api;

import slfx.api.v1.request.command.mp.MPSyncOrderCommand;
import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.api.v1.request.qo.mp.MPOrderQO;
import slfx.api.v1.response.mp.MPOrderCancelResponse;
import slfx.api.v1.response.mp.MPOrderCreateResponse;
import slfx.api.v1.response.mp.MPQueryOrderResponse;
import slfx.api.v1.response.mp.MPSyncOrderResponse;
import slfx.mp.spi.exception.SlfxMpException;

public interface ApiMPOrderService {
	
	/**
	 * @方法功能说明：同步订单状态
	 * @修改者名字：zhurz
	 * @修改时间：2014-8-26下午4:17:42
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:MPSyncOrderResponse
	 * @throws
	 */
	public MPSyncOrderResponse syncOrder(MPSyncOrderCommand command);

	/**
	 * 创建订单
	 * 
	 * @param command
	 * @throws SlfxMpException
	 */
	public MPOrderCreateResponse apiCreateOrder(MPOrderCreateCommand command);
	
	/**
	 * 取消订单
	 * 
	 * @param command
	 * @throws SlfxMpException
	 */
	public MPOrderCancelResponse apiCancelOrder(MPOrderCancelCommand command);
	
	/**
	 * 分页查询
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryOrderResponse apiQueryOrder(MPOrderQO qo);
	
}
