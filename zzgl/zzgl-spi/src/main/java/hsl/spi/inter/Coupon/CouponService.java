package hsl.spi.inter.Coupon;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CancelCouponCommand;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.coupon.SendCouponToUserCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;
/**
 * @类功能说明：卡券spi service
 * @类修改者：
 * @修改日期：2014年10月15日下午1:49:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午1:49:03
 *
 */
public interface CouponService extends BaseSpiService<CouponDTO, HslCouponQO>{
	/**
	 * @方法功能说明：作废卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月15日下午3:25:51
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponDTO
	 * @throws
	 */
	public CouponDTO cancelCoupon(CancelCouponCommand command)throws CouponException;
	/**
	 * @方法功能说明：发放卡券,因为可能发放多张所以返回list
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月16日上午9:52:48
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponDTO
	 * @throws
	 */
	public List<CouponDTO> createCoupon(CreateCouponCommand command)throws CouponException;
	/**
	 * @方法功能说明：消费卡券
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月16日上午9:53:05
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponDTO
	 * @throws
	 */
	public List<CouponDTO> consumeCoupon(List<ConsumeCouponCommand> commandlist)throws CouponException;
	/**
	 * @方法功能说明：卡券过期
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月15日下午3:25:51
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponDTO
	 * @throws
	 */
	public CouponDTO overDueCoupon(CancelCouponCommand command)throws CouponException;
	public CouponDTO getCoupon(HslCouponQO qo);
	/**
	 * 订单退款，修改订单里使用的卡券的状态，并删除订单快照
	 * @return
	 */
	public List<CouponDTO> orderRefund(OrderRefundCommand command)throws CouponException;
	/**
	 * 检查卡券是否可用，需要卡券id，订单id，订单价格，不可用即抛出CouponException
	 * @param command
	 * @return
	 * @throws CouponException
	 */
	public boolean checkCoupon(BatchConsumeCouponCommand command)throws CouponException;
	/**
	 * 支付前调用，占用卡券，保存订单快照
	 * @param command
	 * @return
	 * @throws CouponException
	 */
	public List<CouponDTO>  occupyCoupon(BatchConsumeCouponCommand command)throws CouponException;
	/**
	 * 支付成功后调用，修改卡券状态为已使用
	 * @param command
	 * @return
	 * @throws CouponException
	 */
	public List<CouponDTO> confirmConsumeCoupon(BatchConsumeCouponCommand command)throws CouponException;
	/**
	 * 支付失败后调用，还原卡券信息
	 * @param command
	 * @return
	 * @throws CouponException
	 */
	public List<CouponDTO> cancelConsumeCoupon(ConsumeCouponCommand command)throws CouponException;
	
	public int queryCouponCount(HslCouponQO qo);  
	/**
	 * @方法功能说明：卡券转赠用户
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月5日上午10:10:50
	 * @修改内容：
	 * @参数：@return
	 * @return:CouponDTO
	 * @throws
	 */
	public CouponDTO sendCouponToUser(SendCouponToUserCommand command)throws CouponException, CouponActivityException ;
	/**
	 * 
	 * @方法功能说明：根据卡券ids查询总金额
	 * @创建者名字：zhaows
	 * @创建时间：2015-7-30上午11:15:20
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws CouponException
	 * @参数：@throws CouponActivityException
	 * @return:CouponDTO
	 * @throws
	 */
	public Double queryTotalPrice(String ids,Integer orderCountMoney);
}
