package hsl.admin.controller.timer;
import com.alibaba.fastjson.JSON;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hsl.domain.model.coupon.CouponActivity;
import hsl.domain.model.coupon.CouponStatus;
import hsl.pojo.command.coupon.ActivateCouponActivityCommand;
import hsl.pojo.command.coupon.CancelCouponCommand;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import hsl.pojo.command.coupon.FinishCouponActivityCommand;
import hsl.pojo.command.line.UpdateLineOrderStatusCommand;
import hsl.pojo.dto.coupon.CouponActivityDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.coupon.CouponEventDTO;
import hsl.pojo.dto.line.order.LineOrderDTO;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.spi.inter.Coupon.CouponActivityService;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.line.HslLineOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
@Controller
@RequestMapping("/timer")
public class TimerController {
	@Autowired
	private CouponActivityService couponActivityService;
	
	@Autowired
	private  CouponService  couponService;
	
	@Autowired 
	private HslLineOrderService lineOrderService;
	/**
	 * 定时刷新卡券状态
	 * @return
	 */
	@RequestMapping("/refreshActivity")
	@ResponseBody
	public String refreshActivity(){
		HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务开始");
		//卡券活动状态刷新
		HslCouponActivityQO qo=new HslCouponActivityQO();
		Integer[] values={CouponActivity.COUPONACTIVITY_STATUS_CHECK_OK,CouponActivity.COUPONACTIVITY_STATUS_ACTIVE,CouponActivity.COUPONACTIVITY_STATUS_QUOTA_FULL};
		qo.setStatusTypes(values);
		List<CouponActivityDTO> list = couponActivityService.getCouponActivityList(qo);
		HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务,查询卡券活动数量："+list.size());
		if(list!=null&&list.size()>0){
			for(CouponActivityDTO item : list){
				//检测活动状态
				int status = item.getStatus().getCurrentValue();
				HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务,审核状态："+JSON.toJSONString(item));
				//审核通过状态
				if(status==CouponActivity.COUPONACTIVITY_STATUS_CHECK_OK){
					Date begin = item.getIssueConditionInfo().getIssueBeginDate();
					if(begin.getTime()<=System.currentTimeMillis()){
						ActivateCouponActivityCommand cmd = new ActivateCouponActivityCommand();
						cmd.setCouponActivityId(item.getId());
						try {
							couponActivityService.activeCouponActivity(cmd);
						} catch (CouponActivityException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("zhuxy", "卡券活动定时任务，发放开始失败："+e.getMessage());
						}
					}
				}
				//发放中状态或者名额已满状态
				if(status==CouponActivity.COUPONACTIVITY_STATUS_ACTIVE||status==CouponActivity.COUPONACTIVITY_STATUS_QUOTA_FULL){
					Date end = item.getIssueConditionInfo().getIssueEndDate();
					if(end.getTime()<System.currentTimeMillis()){
						FinishCouponActivityCommand cmd = new FinishCouponActivityCommand();
						cmd.setCouponActivityId(item.getId());
						try {
							couponActivityService.finshCouponActivity(cmd);
						} catch (CouponActivityException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("chenxy", "卡券活动定时任务，活动结束失败："+e.getMessage()+HgLogger.getStackTrace(e));
						}
					}
				}
			}
		}
		HslCouponQO couponQo=new HslCouponQO();
		Integer[] stauts={CouponStatus.TYPE_NOUSED};
		couponQo.setStatusTypes(stauts);
		//卡券状态刷新
		List<CouponDTO> couponList =  couponService.queryList(couponQo);
		HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务,查询卡券数量："+couponList.size());
		CancelCouponCommand command;
		if(couponList!=null&&couponList.size()>0){
			for(CouponDTO item : couponList){
//				HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务,修改过期卡券："+JSON.toJSONString(item));
				if(item.getStatus()!=null&&item.getStatus().getCurrentValue()==CouponStatus.TYPE_NOUSED&&item.getBaseInfo()!=null&&item.getBaseInfo().getCouponActivity()!=null&&item.getBaseInfo().getCouponActivity().getUseConditionInfo()!=null){
					//使用开始有效期暂时不管
					//Date begin;
					Date end = item.getBaseInfo().getCouponActivity().getUseConditionInfo().getEndDate();
					if(end.getTime()<System.currentTimeMillis()){
						HgLogger.getInstance().info("chenxy", "TimerController>>定时刷新卡券状态任务,修改过期卡券："+JSON.toJSONString(item));
						//让票已过期
						command = new CancelCouponCommand();
						command.setCouponId(item.getId());
						try {
							couponService.overDueCoupon(command);
						} catch (CouponException e) {
							e.printStackTrace();
							HgLogger.getInstance().error("chenxy", "卡券定时任务，id为"+item.getId()+"的卡券废票失败"+HgLogger.getStackTrace(e));
						}
					}
				}
			}
		}
		return "success";
	}
	/**
	 * @方法功能说明：线路订单是否过期
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月10日下午2:33:01
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkLineOrder")
	@ResponseBody
	public String judgeLineOrderStauts(HttpServletRequest request){
		HgLogger.getInstance().info("chenxy","检查线路订单定时器>>>开始运行");
		HslLineOrderQO qo=new HslLineOrderQO();
		qo.setOrderStatus(XLOrderStatusConstant.SHOP_CREATE_SUCCESS_NOT_RESERVE);
		List<LineOrderDTO> lineOrderDTOs=lineOrderService.queryList(qo);
		HgLogger.getInstance().info("chenxy","检查线路订单定时器>>>查询符合>下单成功未订位>线路订单数量："+lineOrderDTOs.size());
		for (LineOrderDTO lineOrderDTO : lineOrderDTOs) {
			Date travelDate=lineOrderDTO.getBaseInfo().getTravelDate();
			Date today=new Date();
			boolean flag=false;
			if(today.before(travelDate)){
				long trTime=today.getTime();
				long todayTime=today.getTime();
				long time=trTime-todayTime ;
				long second=time/1000;
				long minute=second/60;
				long hour=minute/60;
				long day=hour/24;
				int day1=(int) day;
				if(day1<lineOrderDTO.getLineSnapshot().getLine().getPayInfo().getPayTotalDaysBeforeStart()){
					flag=true;
				}else{
					continue;
				}
			}else{
				flag=true;
			}
			if(flag){
				HgLogger.getInstance().info("chenxy","检查线路订单定时器>>>修改订单状态："+lineOrderDTOs.size());
				UpdateLineOrderStatusCommand command=new UpdateLineOrderStatusCommand();
				command.setOrderStatus(Integer.parseInt(XLOrderStatusConstant.SHOP_ORDER_CANCEL_VAL));
				command.setPayStatus(lineOrderDTO.getStatus().getPayStatus());
				command.setOrderId(lineOrderDTO.getId());
				command.setDealerOrderCode(lineOrderDTO.getBaseInfo().getDealerOrderNo());
				try {
					lineOrderService.updateLineOrderStatus(command);
				} catch (Exception e) {
					HgLogger.getInstance().error("chenxy","检查线路订单定时器>>>修改订单状态出错："+HgLogger.getStackTrace(e));
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
	/**
	 * 定时清空广告那边的脏数据
	 * @return
	 */
	@SuppressWarnings("static-access")
	public String cleanDeartyData(){
		return new DwzJsonResultUtil().createJsonString("200", "执行成功", null, null);
	}
	/**
	 *
	 * @方法功能说明：定时解绑占用状态的卡券
	 * @创建者名字：zhaows
	 * @创建时间：2015-10-19下午5:08:11
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("checkCoupon")
	@ResponseBody
	public String checkCoupon(){
		Date date=new Date();
		try {
			HgLogger.getInstance().info("zhaows","定时解绑占用状态的卡券-----checkCoupon");
			HslCouponQO hslCouponQO=new HslCouponQO();
			hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
			hslCouponQO.setOccupy(true);
			List<CouponDTO> listCouponDTO=couponService.queryList(hslCouponQO);
			HgLogger.getInstance().info("zhaows","定时解绑占用状态的卡券-----checkCoupon---hslCouponQO"+JSON.toJSONString(listCouponDTO));
			for(CouponDTO couponDTO:listCouponDTO){
				List<CouponEventDTO> listCouponEventDTO = couponDTO.getEventList();
				for (CouponEventDTO couponEventDTO : listCouponEventDTO) {
					long time = date.getTime() - couponEventDTO.getOccurrenceTime().getTime();
					if (couponDTO.getConsumeOrder()!=null&&time > 300000) {
						ConsumeCouponCommand command = new ConsumeCouponCommand();
						command.setOrderId(couponDTO.getConsumeOrder().getOrderId());
						couponService.cancelConsumeCoupon(command);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","Exception--checkCoupon"+HgLogger.getStackTrace(e));
			return "fail";
		}
		return "success";
	}
}
