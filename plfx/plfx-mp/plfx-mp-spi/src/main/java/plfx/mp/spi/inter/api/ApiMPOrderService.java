package plfx.mp.spi.inter.api;

import plfx.api.client.api.v1.mp.request.command.MPOrderCancelCommand;
import plfx.api.client.api.v1.mp.request.command.MPOrderCreateCommand;
import plfx.api.client.api.v1.mp.request.command.MPSyncOrderCommand;
import plfx.api.client.api.v1.mp.request.qo.MPOrderQO;
import plfx.api.client.api.v1.mp.response.MPOrderCancelResponse;
import plfx.api.client.api.v1.mp.response.MPOrderCreateResponse;
import plfx.api.client.api.v1.mp.response.MPQueryOrderResponse;
import plfx.api.client.api.v1.mp.response.MPSyncOrderResponse;
import plfx.mp.spi.exception.SlfxMpException;

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
