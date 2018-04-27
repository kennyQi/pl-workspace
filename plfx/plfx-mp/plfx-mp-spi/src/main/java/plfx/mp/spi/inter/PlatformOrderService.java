package plfx.mp.spi.inter;

import plfx.api.client.api.v1.mp.request.command.MPOrderCancelCommand;
import plfx.api.client.api.v1.mp.request.command.MPOrderCreateCommand;
import plfx.mp.command.admin.AdminCancelOrderCommand;
import plfx.mp.pojo.dto.order.MPOrderDTO;
import plfx.mp.qo.PlatformOrderQO;
import plfx.mp.spi.exception.SlfxMpException;

public interface PlatformOrderService extends BaseMpSpiService<MPOrderDTO, PlatformOrderQO> {

	/**
	 * 管理员取消订单
	 * 
	 * @param command
	 */
	public Boolean adminCancelOrder(AdminCancelOrderCommand command) throws SlfxMpException;
	
	/**
	 * 创建订单
	 * 
	 * @param command
	 * @return String 订单号
	 */
	public String apiOrderTicket(MPOrderCreateCommand command) throws SlfxMpException;
	
	/**
	 * 取消订单
	 * 
	 * @param command
	 * @return 
	 */
	public Boolean apiCancelOrder(MPOrderCancelCommand command) throws SlfxMpException;
	
	/**
	 * 同步订单
	 * @param id
	 */
	public void syncOrder(String id) throws SlfxMpException;
	
}
