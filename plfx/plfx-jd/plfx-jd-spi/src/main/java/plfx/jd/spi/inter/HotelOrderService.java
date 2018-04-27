package plfx.jd.spi.inter;

import plfx.jd.pojo.command.plfx.order.OrderCancelAdminCommand;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCancelCommand;
import plfx.jd.pojo.command.plfx.ylclient.JDOrderCreateCommand;
import plfx.jd.pojo.command.plfx.ylclient.ValidateCreditCardNoCommand;
import plfx.jd.pojo.dto.plfx.order.HotelOrderDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCancelResultDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderCreateResultDTO;
import plfx.jd.pojo.dto.ylclient.order.OrderDetailResultDTO;
import plfx.jd.pojo.dto.ylclient.order.ValidateCreditCardNoResultDTO;
import plfx.jd.pojo.qo.HotelOrderQO;
import plfx.jd.pojo.qo.ylclient.JDOrderQO;
import plfx.jd.spi.BaseJDSpiService;


/**
 * 
 * @类功能说明：酒店订单service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午5:42:58
 * @版本：V1.0
 *
 */
public interface HotelOrderService extends BaseJDSpiService<HotelOrderDTO, HotelOrderQO>{

	public Boolean OrderCancel(OrderCancelAdminCommand command);
/*---------------------admin使用上面，shop使用下面----------------------*/
	public OrderCreateResultDTO orderCreate(JDOrderCreateCommand command);

	public OrderCancelResultDTO orderCancel(JDOrderCancelCommand command);

	public OrderDetailResultDTO queryOrder(JDOrderQO qo);

	public ValidateCreditCardNoResultDTO validateCreditCardNo(ValidateCreditCardNoCommand command);
}
