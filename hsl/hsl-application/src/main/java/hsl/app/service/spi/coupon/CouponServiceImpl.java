package hsl.app.service.spi.coupon;
import hg.log.util.HgLogger;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.coupon.CouponLocalService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.Coupon;
import hsl.domain.service.CouponConsumeRuleService;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.coupon.*;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CouponServiceImpl extends BaseSpiServiceImpl<CouponDTO, HslCouponQO, CouponLocalService> implements CouponService {
	@Autowired
	private CouponLocalService couponLocalService;
	@Autowired
	private CouponConsumeRuleService consumeRuleService;
	@Autowired
	private UserService userService;
	@Override
	protected CouponLocalService getService() {
		return couponLocalService;
	}

	@Override
	protected Class<CouponDTO> getDTOClass() {
		return CouponDTO.class;
	}
	@Override
	public CouponDTO cancelCoupon(CancelCouponCommand command) throws CouponException {
		HgLogger.getInstance().info("chenxy","废弃卡券开始");
		CouponDTO couponDTO=couponLocalService.cancelCoupon(command);
		
		return couponDTO;
	}
	@Override
	public List<CouponDTO> createCoupon(CreateCouponCommand comand) throws CouponException{
		HgLogger.getInstance().info("chenxy",comand.getLoginName()+"创建卡券service开始");
		List<CouponDTO> dtos=new ArrayList<CouponDTO>();
		try {
			dtos=couponLocalService.createCoupon(comand);
		} catch (CouponActivityException e) {
			HgLogger.getInstance().error("chenxy","创建卡券出错："+e.getLocalizedMessage());
			throw new CouponException(CouponException.ACTIVITY_STATUS_ERROR,"活动状态异常："+e.getLocalizedMessage());
		}
		return dtos;
	}

	@Override
	public List<CouponDTO> consumeCoupon(List<ConsumeCouponCommand> commandlist) throws CouponException {
		HgLogger.getInstance().info("chenxy","消费卡券开始");
		//先检查卡券是否可用
		HslCouponQO qo=new HslCouponQO();
		ArrayList<Coupon> couponlist=new ArrayList<Coupon>();
		for(ConsumeCouponCommand command :commandlist){
	           qo.setId(command.getCouponId());
	           Coupon coupon=couponLocalService.queryUnique(qo);
	           ConsumeOrderSnapshot order=new ConsumeOrderSnapshot();
	           order.setOrderId(command.getOrderId());
	           coupon.setConsumeOrder(order);
	           couponlist.add(coupon);
	       }
		consumeRuleService.checkConsumeRule(couponlist);
		List<CouponDTO> dtos=new ArrayList<CouponDTO>();
		for(ConsumeCouponCommand command :commandlist){
			dtos.add(couponLocalService.consumeCoupon(command));
		}
		return dtos;
	}

	@Override
	public CouponDTO overDueCoupon(CancelCouponCommand command) throws CouponException {
		HgLogger.getInstance().info("chenxy", "过期卡券开始");
		CouponDTO couponDTO=couponLocalService.overDueCoupon(command);
		return couponDTO;
	}

	@Override
	public CouponDTO getCoupon(HslCouponQO qo) {
		CouponDTO couponDTO=couponLocalService.getCouponByMaxTime(qo);
		return couponDTO;
	}

	@Override
	public List<CouponDTO> orderRefund(OrderRefundCommand command) throws CouponException {
		if(StringUtils.isBlank(command.getOrderId())){
			throw new CouponException(CouponException.COUPON_UNAVAILABLE,"订单id不能为空");
		}
		//删除订单快照，修改卡券状态
		List<CouponDTO> list=couponLocalService.orderRefund(command);
		return list;
	}

	@Override
	public boolean checkCoupon(BatchConsumeCouponCommand command)throws CouponException {
		List<Coupon> list=new ArrayList<Coupon>();
		String[] couponids=command.getCouponIds();
		for(String couponid:couponids){
			if(StringUtils.isBlank(couponid)){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE,"卡券id为空");
			}
			Coupon coupon=couponLocalService.get(couponid);
			if(coupon.getConsumeOrder()!=null){
				throw new CouponException(CouponException.COUPON_UNAVAILABLE,"卡券已使用");
			}
			ConsumeOrderSnapshot consumeOrderSnapshot=new ConsumeOrderSnapshot();
			consumeOrderSnapshot.setOrderId(command.getOrderId());
			consumeOrderSnapshot.setPayPrice(command.getPayPrice());
			coupon.setConsumeOrder(consumeOrderSnapshot);
			list.add(coupon);
		}
		return consumeRuleService.checkConsumeRule(list);
	}

	@Override
	public List<CouponDTO> occupyCoupon(BatchConsumeCouponCommand command)
			throws CouponException {
		List<CouponDTO> couponDTOs=couponLocalService.occupyCoupon(command);
		return couponDTOs;
	}
	
	@Override
	public List<CouponDTO> confirmConsumeCoupon(BatchConsumeCouponCommand command)throws CouponException {
		return couponLocalService.confirmConsumeCoupon(command);
	}

	@Override
	public List<CouponDTO> cancelConsumeCoupon(ConsumeCouponCommand command)
			throws CouponException {
		OrderRefundCommand refund=new OrderRefundCommand();
		refund.setOrderId(command.getOrderId());
		return orderRefund(refund);
	}

	@Override
	public int queryCouponCount(HslCouponQO qo) {
		return couponLocalService.queryCount(qo);
	}

	@Override
	public CouponDTO sendCouponToUser(SendCouponToUserCommand command) throws CouponException, CouponActivityException {
		return couponLocalService.sendCouponToUser(command);
	}
    @Override
    public Double queryTotalPrice(String ids,Integer orderCountMoney) {
	// TODO Auto-generated method stub
	Double balance=0.00;
	Integer condition=0;
	String [] idsp=ids.split(",");
	for(String id:idsp){
	    Coupon coupon=couponLocalService.get(id);
	    condition=coupon.getBaseInfo().getCouponActivity().getUseConditionInfo().getCondition();//满足最小使用金额
	    if(orderCountMoney>=condition){
		balance+=coupon.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
	    }
	}
	return balance;
    }

	@Override
	public void checkCouponUseCondition(List<String> couponIds, String fromUserId, Integer orderType, String orderNo, Double orderPrice) throws ShowMessageException {
		couponLocalService.checkCouponUseCondition(couponIds, fromUserId, orderType, orderNo, orderPrice);
	}
}
