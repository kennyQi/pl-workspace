package plfx.yl.ylclient.yl.inter;

import plfx.yl.ylclient.yl.command.CheckCreditCardNoCommand;
import plfx.yl.ylclient.yl.command.OrderCancelCommand;
import plfx.yl.ylclient.yl.command.OrderCreateCommand;
import plfx.yl.ylclient.yl.command.OrderUpdateCommand;
import plfx.yl.ylclient.yl.dto.CheckCreditCardNoDTO;
import plfx.yl.ylclient.yl.dto.CheckGuestNameDTO;
import plfx.yl.ylclient.yl.dto.HotelDataInventoryDTO;
import plfx.yl.ylclient.yl.dto.HotelDataValidateDTO;
import plfx.yl.ylclient.yl.dto.HotelListDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCancelDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderCreateDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderDetailDTO;
import plfx.yl.ylclient.yl.dto.HotelOrderUpdateDTO;
import plfx.yl.ylclient.yl.dto.HotelRatePlanDTO;
import plfx.yl.ylclient.yl.dto.IncrHotelDTO;
import plfx.yl.ylclient.yl.dto.IncrInventoryDTO;
import plfx.yl.ylclient.yl.dto.IncrOrderDTO;
import plfx.yl.ylclient.yl.dto.IncrRateDTO;
import plfx.yl.ylclient.yl.dto.IncrStateDTO;
import plfx.yl.ylclient.yl.dto.LastIdDTO;
import plfx.yl.ylclient.yl.dto.OrderListDTO;
import plfx.yl.ylclient.yl.dto.RateDTO;
import plfx.yl.ylclient.yl.dto.ValidateInventoryDTO;
import plfx.yl.ylclient.yl.qo.CheckGuestNameQO;
import plfx.yl.ylclient.yl.qo.HotelDetailQO;
import plfx.yl.ylclient.yl.qo.HotelListQO;
import plfx.yl.ylclient.yl.qo.IncrLastIdQO;
import plfx.yl.ylclient.yl.qo.IncrQO;
import plfx.yl.ylclient.yl.qo.InventoryQO;
import plfx.yl.ylclient.yl.qo.OrderListQO;
import plfx.yl.ylclient.yl.qo.OrderQO;
import plfx.yl.ylclient.yl.qo.RatePlanQO;
import plfx.yl.ylclient.yl.qo.RateQO;
import plfx.yl.ylclient.yl.qo.ValidateInventoryQO;
import plfx.yl.ylclient.yl.qo.ValidateQO;


/**
 * 
 * @类功能说明：艺龙酒店SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2015年01月27日下午5:17:29
 * @版本：V1.0
 *
 */
public interface YLHotelService {
	/**
	 * 
	 * @方法功能说明：酒店列表查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午1:48:18
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelListDTO
	 * @throws
	 */
	public HotelListDTO queryHotelList(HotelListQO qo);
	/**
	 * 
	 * @方法功能说明：酒店详情查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午1:48:06
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelListDTO
	 * @throws
	 */
	public HotelListDTO queryHotelDetail(HotelDetailQO qo);
	/**
	 * 
	 * @方法功能说明：房态库存查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午1:47:40
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelDataInventoryDTO
	 * @throws
	 */
	public HotelDataInventoryDTO queryHotelInventory(InventoryQO qo);
	/**
	 * 
	 * @方法功能说明：验证订单数据是否正常
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午2:05:38
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelDataValidateDTO
	 * @throws
	 */
	public HotelDataValidateDTO queryOrderValidate(ValidateQO qo);
	/**
	 * 
	 * @方法功能说明：订单创建
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午2:18:22
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:
	 * @throws
	 */
	public HotelOrderCreateDTO createOrder(OrderCreateCommand command);
	/**
	 * 
	 * @方法功能说明：订单查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午2:22:43
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:HotelOrderDetailDTO
	 * @throws
	 */
	public HotelOrderDetailDTO queryOrderDetail(OrderQO qo);
	/**
	 * 
	 * @方法功能说明：订单取消
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月5日下午3:18:30
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:HotelOrderCancelDTO
	 * @throws
	 */
	public HotelOrderCancelDTO cancelOrder(OrderCancelCommand command);
	/**
	 * 
	 * @方法功能说明：产品价格查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午9:24:26
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:RateDTO
	 * @throws
	 */
	public RateDTO queryRate(RateQO qo);
	/**
	 * 
	 * @方法功能说明：产品详情查询
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午9:51:40
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:HotelRatePlanDTO
	 * @throws
	 */
	public HotelRatePlanDTO queryRatePlan(RatePlanQO qo);
	/**
	 * 
	 * @方法功能说明：数据变化增量
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午10:34:08
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:IncrHotelDTO
	 * @throws
	 */
	public IncrHotelDTO incrHotel(IncrQO qo);
	/**
	 * 
	 * @方法功能说明：增量编号
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午10:47:32
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:LastIdDTO
	 * @throws
	 */
	public LastIdDTO incrLastId(IncrLastIdQO qo);
	/**
	 * 
	 * @方法功能说明：库存增量
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午11:15:03
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:IncrInventoryDTO
	 * @throws
	 */
	public IncrInventoryDTO incrInventory(IncrQO qo);
	/**
	 * 
	 * @方法功能说明：订单增量
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午11:19:45
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:IncrOrderDTO
	 * @throws
	 */
	public IncrOrderDTO incrOrder(IncrQO qo);
	/**
	 * 
	 * @方法功能说明：价格增量
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日上午11:25:54
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:IncrRateDTO
	 * @throws
	 */
	public IncrRateDTO incrRate(IncrQO qo);
	/**
	 * 
	 * @方法功能说明：状态增量
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日下午1:39:07
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:IncrStateDTO
	 * @throws
	 */
	public IncrStateDTO incrState(IncrQO qo);
	/**
	 * 
	 * @方法功能说明：库存验证
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日下午1:44:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:ValidateInventoryDTO
	 * @throws
	 */
	public ValidateInventoryDTO queryInventoryValidate(ValidateInventoryQO qo);
	/**
	 * 
	 * @方法功能说明：客人姓名验证
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日下午1:56:44
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:CheckGuestNameDTO
	 * @throws
	 */
	public CheckGuestNameDTO queryCheckGuestName(CheckGuestNameQO qo);
	/**
	 * 
	 * @方法功能说明：订单列表
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日下午2:10:23
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:OrderListDTO
	 * @throws
	 */
	public OrderListDTO queryOrderList(OrderListQO qo);
	/**
	 * 
	 * @方法功能说明：修改订单
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月9日下午2:19:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:HotelOrderUpdateDTO
	 * @throws
	 */
	public HotelOrderUpdateDTO updateOrder(OrderUpdateCommand command);
	
	/**
	 * @方法功能说明：验证信用卡号是否有效
	 * @修改者名字：renfeng
	 * @修改时间：2015年7月14日下午5:00:19
	 * @修改内容：
	 * @参数：@return
	 * @return:CheckCreditCardNoDTO
	 * @throws
	 */
	public CheckCreditCardNoDTO validateCreditCard(CheckCreditCardNoCommand command);
}
