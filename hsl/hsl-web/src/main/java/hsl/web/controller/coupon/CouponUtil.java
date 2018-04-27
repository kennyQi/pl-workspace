package hsl.web.controller.coupon;

import hg.log.util.HgLogger;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.coupon.CouponStatus;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import hsl.web.controller.BaseController;
/**
 * @方法功能说明：卡券工具
 * @修改者名字：zhaows
 * @修改时间：2015年4月16日上午9.21
 */
@Controller
@RequestMapping("/coupon")
public class CouponUtil  extends BaseController{
	@Resource
	private CouponService couponService;
	/**
	 * @方法功能说明：查询卡券
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月16日上午9.21
	 */
	@RequestMapping(value="/selectCoupon")
	@ResponseBody
	public String selectCoupon(HttpServletRequest request,Model model){
		 /*得到当前用户的卡券*/
		// 根据登录用户的id查询该用户拥有的卡券列表
		UserDTO user = getUserBySession(request); // 获取登录用户的信息
		HslCouponQO hslCouponQO = new HslCouponQO();
		if(user!=null){
			hslCouponQO.setUserId(user.getId());
			// 只查询未使用的卡券并且满足使用条件的卡券
			hslCouponQO.setCurrentValue(CouponStatus.TYPE_NOUSED);
			hslCouponQO.setOrderPrice(Double.parseDouble(request.getParameter("orderPrice")));
			hslCouponQO.setUsedKind(request.getParameter("usedKind"));
			//查询卡券的使用类别  暂时没有类别
			hslCouponQO.setOverdue(true);//是否过期
		}
		List<CouponDTO> couponList =null;
		try {
			if (hslCouponQO.getUserId() != null) {
				HgLogger.getInstance().info("zhaows", "selectCoupon-->查询卡券列表条件:"+hslCouponQO);
				couponList = couponService.queryList(hslCouponQO);
				HgLogger.getInstance().info("zhaows", "selectCoupon-->查询卡券列表成功:"+couponList);
			}
			return JSON.toJSONString(couponList);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "selectCoupon-->查询卡券列表失败");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @方法功能说明：修改卡券状态
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月16日上午10.19
	 * @参数 couponids卡券Id, 用','隔开
	 * OrderId 订单号
	 * Price   价格 Double类型
	 */
	public void updateCouponState(String  couponids,String OrderId,Double Price){
		try {
			String[] ids = couponids.split(",");
			if(couponids!=""){
				BatchConsumeCouponCommand consumeCouponCommand = null;
				consumeCouponCommand = new BatchConsumeCouponCommand();
				consumeCouponCommand.setCouponIds(ids);
				consumeCouponCommand.setOrderId(OrderId);
				consumeCouponCommand.setPayPrice(Price);
				consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_XL);
				couponService.occupyCoupon(consumeCouponCommand);
				HgLogger.getInstance().info("zhaows", "commandUser-->卡券支付时修改卡券状态成功-->参数"+consumeCouponCommand);
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "selectCouponCondition-->查询卡券状态失败");
			e.printStackTrace();
		}
	}
	/**
	 * @方法功能说明：查询代金券使用条件
	 * @修改者名字：zhaows
	 * @修改时间：2015年4月16日上午10.12
	 */
	@RequestMapping(value="/selectCouponCondition")
	@ResponseBody
	public String selectCouponCondition(HttpServletRequest request,Model model){
		try {
			String couponId = request.getParameter("couponId");
			HslCouponQO hslCouponQO = new HslCouponQO();
			CouponDTO couponDto = null;
			hslCouponQO.setId(couponId);
			if (hslCouponQO != null) {
				hslCouponQO.setIdLike(true);
			}
			couponDto = couponService.queryUnique(hslCouponQO);
			HgLogger.getInstance().info("zhaows", "selectCouponCondition-->查询使用条件成功:"+couponDto);
			return JSON.toJSONString(couponDto);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhaows", "selectCouponCondition-->查询卡券使用条件失败");
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * @创建者名字：zhaows
	 * @修改时间：2015年4月16日上午10.30
	 * 查询现金券同类型和不同类型是否可用
	 * return Json
	 * */
	@RequestMapping("/estimateCouponFold")
	@ResponseBody
	public String estimateCouponFold(HttpServletRequest request,HttpServletResponse response){
		Map<String ,Object> map=new HashMap<String ,Object>();
		try {
			HgLogger.getInstance().info("zhaows", "estimateCouponFold-->卡券点击后计算");
			//根据需要查询出当前现金券是否可以叠加
			String couponId=request.getParameter("couponId");//卡券id
			String identification=request.getParameter("identification");//1为选中卡券 2为不选中
			double youhui=Double.parseDouble(request.getParameter("youhui"));//优惠金额
			double pay=Double.parseDouble(request.getParameter("pay"));//应付金额
			//根据Id查询
			HslCouponQO hslCouponQO = new HslCouponQO();
			hslCouponQO.setId(couponId);
			List<CouponDTO> couponList = couponService.queryList(hslCouponQO);
			HgLogger.getInstance().info("zhaows","estimateCouponFold-->根据id查询券的基本信息：" + JSON.toJSONString(couponList));
			//得到现金券金额
			double faceValue=couponList.get(0).getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
			double youhuis=0.00;
			double pays=0.00;
			if(identification.equals("1")){
				//计算优惠金额
				youhuis=faceValue+youhui;
				//计算应付金额
				pays=pay-faceValue;
			}else if(identification.equals("2")){
				youhuis=youhui-faceValue;
				pays=pay+faceValue;
			}
			map.put("youhui",youhuis);
			map.put("pay",pays);
			map.put("faceValue", faceValue);
			//不同类型
			//map.put("IsShareNotSameType", couponList.get(0).getBaseInfo().getCouponActivity().getUseConditionInfo().getIsShareNotSameType());
			//同一类型
			//map.put("IsShareSameKind", couponList.get(0).getBaseInfo().getCouponActivity().getUseConditionInfo().getIsShareSameKind());
		} catch (Exception ex) {
			ex.printStackTrace();
			HgLogger.getInstance().error("zhaows",HgLogger.getStackTrace(ex));
			return ex.getMessage();
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 获取卡券的使用条件以及获得获取卡券所属的活动id
	 * @param request
	 * */
	@RequestMapping("/getCouponCondition")
	@ResponseBody
	private String getCouponCondition(HttpServletRequest request) {
		String couponId = request.getParameter("couponId");
		HslCouponQO hslCouponQO = new HslCouponQO();
		CouponDTO couponDto = null;
		hslCouponQO.setId(couponId);
		if (hslCouponQO != null) {
			hslCouponQO.setIdLike(true);
		}
		try {
			couponDto = couponService.queryUnique(hslCouponQO);
			HgLogger.getInstance().info("zhaows", "getCouponCondition-->查询卡券成功:"+couponDto);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("liusong","getCouponCondition-->获取卡券失败:" + HgLogger.getStackTrace(e));
		}
		return JSON.toJSONString(couponDto);
	}
}
