package hsl.spi.inter.Coupon;
import hsl.pojo.command.coupon.ActivateCouponActivityCommand;
import hsl.pojo.command.coupon.BatchIssueCouponCommand;
import hsl.pojo.command.coupon.CancelCouponActivityCommand;
import hsl.pojo.command.coupon.CheckCouponActivityCommand;
import hsl.pojo.command.coupon.CouponActivityQuotaMaxCommand;
import hsl.pojo.command.coupon.FinishCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivityCommand;
import hsl.pojo.command.coupon.ModifyCouponActivitySendInfoCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.coupon.CouponActivityEventDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.qo.coupon.HslCouponActivityEventQO;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.spi.inter.BaseSpiService;

import java.util.List;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日下午2:12:12
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午2:12:12
 */
public interface CouponActivityService extends BaseSpiService<CouponActivityDTO, HslCouponActivityQO>{
	/**
	 * 
	 * @方法功能说明：查询活动列表
	 * @修改者名字：wuyg
	 * @修改时间：2014年10月15日下午3:31:45
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<CouponActivityDTO>
	 * @throws
	 */
	public List<CouponActivityDTO> getCouponActivityList(HslCouponActivityQO qo);
	
	/**
	 * 
	 * @方法功能说明：创建新活动
	 * @修改者名字：wuyg
	 * @修改时间：2014年10月15日下午3:32:09
	 * @修改内容：
	 * @参数：@param cmd
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO saveCouponActivity(ModifyCouponActivityCommand cmd);
	/**
	 * 活动审核
	 * @param command
	 * @return
	 * @throws CouponActivityException
	 */
	public CouponActivityDTO checkCouponActivity(CheckCouponActivityCommand command)throws CouponActivityException;
	/**
	 * 活动上线
	 * @param command
	 * @return
	 * @throws CouponActivityException
	 */
	public CouponActivityDTO activeCouponActivity(ActivateCouponActivityCommand command)throws CouponActivityException;
	/**
	 * 活动名额已满
	 * @param command
	 * @return
	 * @throws CouponActivityException
	 */
	public CouponActivityDTO couponActivityQuotaMax(CouponActivityQuotaMaxCommand command)throws CouponActivityException;
	/**
	 * 活动结束
	 * @param command
	 * @return
	 * @throws CouponActivityException
	 */
	public CouponActivityDTO finshCouponActivity(FinishCouponActivityCommand command)throws CouponActivityException;
	/**
	 * 
	 * @方法功能说明：修改活动
	 * @修改者名字：wuyg
	 * @修改时间：2014年10月15日下午3:33:21
	 * @修改内容：
	 * @参数：@param cmd
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO updateCouponActivity(ModifyCouponActivityCommand cmd) throws CouponActivityException;
	/**
	 * 
	 * @方法功能说明：取消活动
	 * @修改者名字：wuyg
	 * @修改时间：2014年10月15日下午3:35:33
	 * @修改内容：
	 * @参数：@param cmd
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO cancelCouponActivity(CancelCouponActivityCommand cmd) throws CouponActivityException;
	
	/**
	 * 查询活动取消原因/备注
	 * @param qo
	 * @return
	 */
	public CouponActivityEventDTO getCancelRemark(HslCouponActivityEventQO qo);
	/*----------1.4.2现金券添加方法------------*/
	
	/**
	 * @方法功能说明：修改卡券赠送活动
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月4日下午2:28:21
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public CouponActivityDTO modifyCouponSendInfo(ModifyCouponActivitySendInfoCommand command) throws CouponActivityException;
	/**
	 * @方法功能说明：批量导入卡券
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月4日下午4:29:11
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CouponActivityDTO
	 * @throws
	 */
	public String batchIssueCoupons(BatchIssueCouponCommand command) throws CouponActivityException ;
}
