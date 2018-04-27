package hsl.spi.inter.mp;

import java.util.Map;

import hg.common.component.BaseQo;
import hsl.pojo.command.MPOrderCancelCommand;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.command.MPSyncOrderCommand;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.inter.BaseSpiService;

@SuppressWarnings("rawtypes")
public interface MPOrderService extends BaseSpiService<MPOrderDTO,BaseQo>{

	
	/**
	 * 查询取票人
	 * @param id
	 * @return
	 */
	public UserDTO queryTakeTicketUser(String id);
	
	/**
	 * 创建订单
	 * @param command
	 * @return
	 */
	public Map createMPOrder(MPOrderCreateCommand command);
	
	/**
	 * 查询订单列表
	 * @param mpOrderQO
	 * @return
	 * @throws MPException 
	 */

	public Map queryMPOrderList(HslMPOrderQO mpOrderQO) throws MPException;

	/**
	 * 取消订单
	 * @param command
	 * @return
	 */
	public Map cancelMPOrder(MPOrderCancelCommand command);
	
	/**
	 * 同步订单状态
	 * @param command
	 * @return
	 */
	public void syncOrder(MPSyncOrderCommand command);
}
