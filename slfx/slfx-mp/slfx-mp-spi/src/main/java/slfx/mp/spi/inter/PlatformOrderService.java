package slfx.mp.spi.inter;

import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.mp.command.admin.AdminCancelOrderCommand;
import slfx.mp.pojo.dto.order.MPOrderDTO;
import slfx.mp.qo.PlatformOrderQO;
import slfx.mp.spi.exception.SlfxMpException;

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
