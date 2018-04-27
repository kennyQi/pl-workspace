package hsl.web.controller.coupon;
import hg.log.util.HgLogger;
import hsl.app.component.manager.LoginUserMobileValidateCodeManager;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.SendCouponToUserCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.CouponActivityException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.web.controller.BaseController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
@Controller
@RequestMapping("/couponcontroller")
public class CouponController extends BaseController{
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private LoginUserMobileValidateCodeManager  mobileValidateCodeManager;
	
	/**
	 * 券转赠
	 * @throws CouponException 
	 */
	@RequestMapping("/updateholdcoupon")
	@ResponseBody
	public String updateholdcoupon(HttpServletRequest request,HttpServletResponse response) throws CouponException{
		    Map<String,Object> map=new HashMap<String,Object>();
		try {
			String session=request.getSession().getId();
			String initiativeuserid=request.getParameter("initiativeuserid");
			String passivityuserid=request.getParameter("passivityuserid");
			SendCouponToUserCommand sendCouponToUserCommand=new SendCouponToUserCommand();
			sendCouponToUserCommand.setUserId(passivityuserid);
			sendCouponToUserCommand.setCouponId(initiativeuserid);
			couponService.sendCouponToUser(sendCouponToUserCommand);
			map.put("result", "转赠成功!");
			map.put("session", session);
		} catch (CouponActivityException e) {
			map.put("result", e.getMessage());
			e.printStackTrace();
		}catch (CouponException e) {
			map.put("result", e.getMessage());
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}
	/**
	 * 判断session是否过期
	 * @throws CouponException 
	 */
	@RequestMapping("/checkingsession")
	@ResponseBody
	public String checkingsession(HttpServletRequest request,HttpServletResponse response) throws CouponException{
		UserDTO user=getUserBySession(request);
		 Map<String,Object> map=new HashMap<String,Object>(); 
		if(user==null){
			map.put("session", "no");
		}else{
			HttpSession session = request.getSession();
			String userPhotoVoild = (String) session.getAttribute(user.getId()+"session");
			if(userPhotoVoild == null){
				map.put("session", "needCheckMobiel");
			}else{
				map.put("session", "yes");
			}
		}
		return JSON.toJSONString(map);
			
	}
	/**
	 * 
	 * @方法功能说明：查询卡券使用规则
	 * @创建者名字：zhaows
	 * @创建时间：2015-6-17上午9:03:37
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws CouponException
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkRule")
	@ResponseBody
	public String checkRule(HttpServletRequest request,HttpServletResponse response) throws CouponException{
		try {
			String couponid=request.getParameter("couponid");
			HslCouponQO hslcouponqo=new HslCouponQO();
			hslcouponqo.setCouponId(couponid);
			CouponDTO coupondto=couponService.queryUnique(hslcouponqo);
			String ruleintro=coupondto.getBaseInfo().getCouponActivity().getBaseInfo().getRuleIntro();
			return JSON.toJSONString(ruleintro);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
			
	}
	/**
	 * @方法功能说明：支付页面提交订单之前，验证卡券是否可用
	 * @修改者名字：zhaows
	 * @修改时间：2015年1月13日下午3:32:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/getCouponUsed")
	@ResponseBody
	private String getCouponUsed(HttpServletRequest request) {
		// 查询用户选择的卡券是否可使用
		String useCouponIDs = request.getParameter("useCouponIDs");
		String dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
		Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
		String[] id = useCouponIDs.split(",");
		BatchConsumeCouponCommand consumeCouponCommand = null;
		consumeCouponCommand = new BatchConsumeCouponCommand();
		consumeCouponCommand.setCouponIds(id);
		consumeCouponCommand.setOrderId(dealerOrderId);
		consumeCouponCommand.setPayPrice(payPrice);
		String message = "";// 标识卡券是否可用的字段变量
		// 如果用户选择了多个卡券，那么每循环出一个卡券id就要去做一次判断，判断该卡券是否可用
		try {
			boolean flag = couponService.checkCoupon(consumeCouponCommand);
			if (flag) {
				HgLogger.getInstance().info("zhaows", "卡券可用");
				message = "success";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			HgLogger.getInstance().error("zhaows","卡券不可用->getCouponUsed:"+ HgLogger.getStackTrace(ex));
			System.out.println(HgLogger.getStackTrace(ex)+"==========================");

			return ex.getMessage();
		}
		return message;
	}
	
	/**
	 * 
	 * @方法功能说明：发送验证码
	 * @修改者名字：hgg
	 * @修改时间：2015年11月12日下午3:29:18
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/sendValidateCode")
	@ResponseBody
	public String sendValidateCode(Model model,HttpServletRequest request){
		
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		Map<String,Object> map =new HashMap<String,Object>();
		map.put("message", "验证码已发送");
		UserDTO user=getUserBySession(request);
		
		try {
			mobileValidateCodeManager.sendValidateCode(sessionId, user.getId());
		} catch (ShowMessageException e) {
			map.put("message", e.getMessage());
		}
		return JSON.toJSONString(map);
	}
	
	/**
	 * 
	 * @方法功能说明：验证验证码
	 * @修改者名字：hgg
	 * @修改时间：2015年11月12日下午3:32:43
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/validateCode")
	@ResponseBody
	public String validateCode(Model model,HttpServletRequest request,String code){
		
		Map<String,Object> map=new HashMap<String,Object>(); 
		map.put("message", "false");
		HttpSession session = request.getSession();
		String sessionId = session.getId();
		UserDTO user=getUserBySession(request);
		
		boolean result = mobileValidateCodeManager.checkValidateCode(sessionId, user.getId(), code);
		if(result){
			map.put("message", "true");
			session.setAttribute(user.getId()+"session", user.getId());
		}
		
		return JSON.toJSONString(map);
	}
}