package hsl.domain.service;

import hsl.domain.model.coupon.Coupon;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.coupon.CouponUseConditionInfo;
import hsl.pojo.exception.CouponException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

/**
 * 
 * @类功能说明：用于预判及下单验证若干卡券能否用在一个订单里
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年10月22日上午9:55:44
 *
 */
@Component
public class CouponConsumeRuleService {
	
	/**
	 * @throws CouponException 
	 * 
	 * @方法功能说明：组合判断方法，通过调用本service其它单一验证，组合判断这些卡券能否在同一订单中使用，组合判断方法可能有多个，由运营规则决定，目前只有这一种
	 * 		
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日上午9:54:07
	 * @修改内容：
	 * @参数：@param coupons 在一张订单中要使用的若干卡券
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean checkConsumeRule(List<Coupon> coupons) throws CouponException {
		//检查卡券对应的订单id
		if(!checkCouponUseNum(coupons)){
			return false;
		}
		//检查有效期、是否已用、是否到达最低价格
		for(Coupon coupon:coupons){
			if(!checkUseValidDate(coupon)||!checkCouponStatusUnUse(coupon)||!checkMinOrderPrice(coupon)){
				return false;
			}else
				return true;
		}
		return false;
	}
	
	/**
	 * @throws CouponException 
	 * 
	 * @方法功能说明：检查卡券使用有效期
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日上午9:39:00
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public boolean checkUseValidDate(Coupon coupon) throws CouponException {
		CouponUseConditionInfo useinfo=coupon.getBaseInfo().getCouponActivity().getUseConditionInfo();
		long now =new Date().getTime();
		long start=useinfo.getBeginDate().getTime();
		long end=useinfo.getEndDate().getTime();
		if(now<start||now > end){
			throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券不在有效期内");
		}else
			return true;
	}
	
	/**
	 * @throws CouponException 
	 * 
	 * @方法功能说明：检查卡券状态是否未使用过
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日上午9:39:00
	 * @修改内容：
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public boolean checkCouponStatusUnUse(Coupon coupon) throws CouponException {
		if(coupon.getStatus().getCurrentValue()==CouponStatus.TYPE_NOUSED)
			return true;
		throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券已使用");
	}
	
	/**
	 * @throws CouponException 
	 * 
	 * @方法功能说明：检查一个订单中使用的多张券，有没有互斥，以及卡券订单快照是否属于同一订单
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日上午9:44:13
	 * @修改内容：
	 * @参数：@param coupon
	 * @参数：@return
	 * @return:boolean 在一个订单中用到的多张卡券
	 * @throws
	 */
	public boolean checkCouponUseNum(List<Coupon> coupons) throws CouponException {
		//条件：卡券必须对应同一订单，同时卡券对应的活动不能重复
		ArrayList<String> activityids=new ArrayList<String>();
		String orderid=null;
		for(Coupon coupon:coupons){
			String oid=coupon.getConsumeOrder().getOrderId();
			if(orderid==null){
				orderid=oid;
			}
			if(!orderid.equals(oid)){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券不是对应同一订单");
			}
			String activityid=coupon.getBaseInfo().getCouponActivity().getId();
			// 如果活动设置可以同时使用则可以同时使用
			Boolean judge=coupon.getBaseInfo().getCouponActivity().getUseConditionInfo().getIsShareSameKind();
			if(activityids.contains(activityid)&&judge!=true){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE, "不能使用同一活动的多张卡券");
				
			}
			activityids.add(activityid);
		}
		
		return true;
	}
	
	/**
	 * @throws CouponException 
	 * 
	 * @方法功能说明：检查对应活动的最小使用金额要求
	 * @修改者名字：yuxx
	 * @修改时间：2014年10月22日上午9:50:45
	 * @修改内容：
	 * @参数：@param coupon
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean checkMinOrderPrice(Coupon coupon) throws CouponException {
		CouponUseConditionInfo useinfo=coupon.getBaseInfo().getCouponActivity().getUseConditionInfo();
		double limit=useinfo.getMinOrderPrice();
		double orderprice=coupon.getConsumeOrder().getPayPrice();
		if(orderprice>=limit)
			return true;
		throw new CouponException(CouponException.COUPON_UNAVAILABLE, "卡券没有到达最小使用金额");
	}
}
