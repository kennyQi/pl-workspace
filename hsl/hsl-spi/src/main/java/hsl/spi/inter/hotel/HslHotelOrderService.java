package hsl.spi.inter.hotel;

import hsl.pojo.command.hotel.CheckCreditCardNoCommand;
import hsl.pojo.command.hotel.JDOrderCancelCommand;
import hsl.pojo.command.hotel.JDOrderCreateCommand;
import hsl.pojo.dto.hotel.order.CheckCreditCardNoDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDTO;
import hsl.pojo.dto.hotel.order.HotelOrderDetailDTO;
import hsl.pojo.dto.hotel.order.OrderCancelResultDTO;
import hsl.pojo.dto.hotel.order.OrderCreateResultDTO;
import hsl.pojo.exception.HotelException;
import hsl.pojo.qo.hotel.HotelOrderDetailQO;
import hsl.pojo.qo.hotel.HotelOrderQO;
import hsl.spi.inter.BaseSpiService;

/**
 * @类功能说明：酒店订单接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量云数据科技股份有限公司
 * @部门：技术部
 * @作者：renfeng
 * @创建时间：2015年7月1日下午3:26:44
 * @版本：V1.5
 *
 */
public interface HslHotelOrderService extends BaseSpiService<HotelOrderDTO,HotelOrderQO >{

	/**
	 * @方法功能说明：创建酒店订单
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月2日下午1:50:38
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:HotelOrderDTO
	 * @throws
	 */
	public OrderCreateResultDTO createHotelOrder(JDOrderCreateCommand command)throws HotelException;
	
	/**
	 * @方法功能说明：取消订单
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月6日上午9:15:07
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public OrderCancelResultDTO cancelHotelOrder(JDOrderCancelCommand command)throws HotelException;
	
	/**
	 * 
	 * @方法功能说明：验证信用卡是否有效
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月6日下午5:41:02
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws HotelException
	 * @return:CheckCreditCardNoDTO
	 * @throws
	 */
	public CheckCreditCardNoDTO checkCreditCardNo(CheckCreditCardNoCommand command)throws HotelException;
	
	/**
	 * @方法功能说明：从分销查询定的那明细
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月14日下午4:06:08
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelOrderDetailDTO
	 * @throws
	 */
	public HotelOrderDetailDTO queryHotelOrderDetail(HotelOrderDetailQO qo) throws HotelException; 
}
