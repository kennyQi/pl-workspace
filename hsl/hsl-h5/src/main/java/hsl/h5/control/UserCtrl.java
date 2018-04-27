package hsl.h5.control;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;
import hsl.app.component.config.SysProperties;
import hsl.h5.base.result.api.ApiResult;
import hsl.h5.base.result.user.*;
import hsl.h5.base.utils.CachePool;
import hsl.h5.base.utils.Crypto;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.base.utils.WxUtil;
import hsl.h5.base.weixin.TokenAndExpiresOfWx;
import hsl.h5.base.weixin.WxUserInfo;
import hsl.h5.control.constant.Constants;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.*;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.dto.user.UserBindAccountDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.user.HslUserBindAccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.user.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * 用户管理Action
 * @author 胡永伟
 */
@Controller
@RequestMapping("user")
public class UserCtrl extends HslCtrl {

	
	@Autowired
	private UserService userService;
	
	@Resource
	private ProvinceService provinceService;
	
	@Resource
	private CityService cityService;
	
	@Resource
	private RabbitTemplate template;
	@Autowired
	private Producer captchaProducer;
	/**
	 * 进入注册页面
	 */
	@RequestMapping("reg")
	public ModelAndView reg(String redirectUrl, HttpServletRequest request) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->reg进入注册页面");
		Date now = new Date();
		Date topicDateEnd = DateUtil.parseDateTime("2015-12-01", "yyyy-MM-dd");
		ModelAndView mav;
		if (topicDateEnd.compareTo(now) <= 0) {
			// 双十一活动后
			mav = new ModelAndView("user/reg");
		} else {
			// 双十一活动内
			mav = new ModelAndView("user/reg2");
		}
		mav.addObject("openid", OpenidTracker.get());
		mav.addObject("redirectUrl", redirectUrl);
		mav.addObject("isWCBrowser", isWCBrowser(request));
		return mav;
	}
	
	/**
	 * 手机验证
	 * @param loginName
	 * @param password
	 * @param openid
	 * @return
	 */
	@RequestMapping("valid/{scene}")
	public ModelAndView valid(
			@PathVariable("scene") Integer scene, 
			String loginName, String password, String redirectUrl) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->valid进入手机验证loginName:" + loginName);
		ModelAndView mav = new ModelAndView("user/valid");
		mav.addObject("scene", scene);
		mav.addObject("loginName", loginName);
		mav.addObject("password", password);
		mav.addObject("openid", OpenidTracker.get());
		mav.addObject("redirectUrl", redirectUrl);
		return mav;
	}

	/**
	 * 获取验证码
	 */
	@RequestMapping("sendValidCode")
	public void sendValidCode(SendValidCodeCommand sendValidCodeCommand, PrintWriter out,HttpServletRequest request) {
		HgLogger.getInstance().info("wuyg", "UserCtrl->sendValidCode获取验证码开始");
		SendValidCodeResult sendValidCodeResult = new SendValidCodeResult();
		HgLogger.getInstance().info("chenxy", "发送手机验证码 ,场景>>" + sendValidCodeCommand.getScene() + "手机号：" + sendValidCodeCommand.getMobile());
		try {
			String authcode=request.getParameter("imgcode");
			String sessionCode=(String) request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			//设置添加的用户类别
			if(sendValidCodeCommand.getScene().equals(1)){
				/**
				 * 当 场景是1 注册的时候必须 判断是否有 图片验证码
				 */
				if(null==sessionCode||!sessionCode.equalsIgnoreCase(authcode)){
					/**
					 * 由于恶性请求，优化注册发送手机验证码流程的请求
					 */
					throw new UserException(1,"图片验证码不正确");
				}
				request.getSession().removeAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);

			}
			String token = userService.sendValidCode(sendValidCodeCommand);
			sendValidCodeResult.setToken(token);
			sendValidCodeResult.setResult(ApiResult.RESULT_CODE_OK);
			HgLogger.getInstance().info("wuyg", "UserCtrl->sendValidCode短信验证码发送成功");
			sendValidCodeResult.setMessage("验证码已发送");
		} catch (UserException e) {
			HgLogger.getInstance().info("wuyg", "UserCtrl->sendValidCode短信验证码发送失败" + e.getMessage()+HgLogger.getStackTrace(e));
			sendValidCodeResult.setResult(Constants.exceptionMap.get(e.getCode()));
			sendValidCodeResult.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("wuyg", "UserCtrl->sendValidCode短信验证码发送完成->结果" + JSON.toJSONString(sendValidCodeResult));
		String s= JSON.toJSONString(sendValidCodeResult);
		out.print(s);
	}

	/**
	 * 注册
	 * @param out
	 * @param command
	 */
	@RequestMapping("register")
	public void register(HttpServletRequest request,
			UserRegisterCommand command, PrintWriter out) {
		command.setLoginName(command.getMobile());
		HgLogger.getInstance().info("yuqz", "UserCtrl->register账号注册开始" + JSON.toJSONString(command));
		String openid = OpenidTracker.get();
		command.setType(1);
		command.setPassword(Crypto.md5(command.getPassword()));
		
		HgLogger.getInstance().info("wuyg", "UserCtrl->register用户注册请求开始" + JSON.toJSONString(command));
		
		String client = null;
		if (isWCBrowser(request)) {
			client ="weixin";
		} else {
			client ="wap";
		}
		String clientKey = client;
		UserRegisterResult userRegisterResult = new UserRegisterResult();
		if (command.getType() == 1) {
			try {
				UserDTO userDTO = userService.register(command,clientKey);
				userRegisterResult.setUserDTO(userDTO);
				userRegisterResult.setResult(ApiResult.RESULT_CODE_OK);
				userRegisterResult.setMessage("注册成功");
				HgLogger.getInstance().info("wuyg","UserCtrl->register用户注册->"+userDTO.getAuthInfo().getLoginName() + "用户注册成功");
				//注册送卡券
				CouponMessage baseAmqpMessage=new CouponMessage();
				baseAmqpMessage.paddingUserRegisterContent(userDTO);
				String issue=SysProperties.getInstance().get("issue_on_register");
				int type=0;
				if(StringUtils.isBlank(issue))
					type=1;
				else{
					type=Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);//注册发放
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				template.convertAndSend("hsl.regist",baseAmqpMessage);
			} catch (UserException e) {
				userRegisterResult.setResult(Constants.exceptionMap.get(e.getCode()));
				userRegisterResult.setMessage(e.getMessage());
				HgLogger.getInstance().error("wuyg","UserCtrl->register用户注册->"+command.getLoginName() + "用户注册失败:"+ e.getMessage()+HgLogger.getStackTrace(e));
			}
			HgLogger.getInstance().info("wuyg","UserCtrl->register用户注册完成->结果:" + JSON.toJSONString(userRegisterResult));
		} else {
			userRegisterResult.setResult(ApiResult.RESULT_CODE_FAIL);
			userRegisterResult.setMessage("不允许注册企业用户");
		}
		
		UserRegisterResult regResp = userRegisterResult;
		if (success(regResp)) {
			String userId = regResp.userDTO.getId();
			if(openid != null && isWCBrowser(request)) {
				CachePool.setUser(openid, userId);
				BindWXCommand bwxCommand = new BindWXCommand();
				bwxCommand.setWxAccountId(openid);
				bwxCommand.setUserId(userId);
				bwxCommand.setPassword(command.getPassword());
				log.info(bindWX(bwxCommand));
			} else {
				request.getSession().setAttribute("user", userId);
			}
		}

		out.print(JSON.toJSONString(regResp));
	}
	
	/**
	 * wap进入登录页面
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("login")
	public ModelAndView login(String redirectUrl, PrintWriter out) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->login进入登录页面");
		ModelAndView mav = new ModelAndView("user/login");
		mav.addObject("redirectUrl", redirectUrl);
		return mav;
	}
	
	/**
	 * wap用户登入验证
	 */
	@RequestMapping("check")
	public void check(HttpServletRequest request,HslUserQO command, PrintWriter out) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->check用户登录请求开始" + JSON.toJSONString(command));
		command.setPassword(Crypto.md5(command.getPassword()));
		UserCheckResult userCheckResult = check(command);
		if((userCheckResult.getResult()).equals(ApiResult.RESULT_CODE_OK)) {
			request.getSession().setAttribute("user", userCheckResult.userDTO.getId());
		}
		HgLogger.getInstance().info("yuqz", "UserCtrl->check用户登录请求完成->结果：" + JSON.toJSONString(userCheckResult));
		out.print(JSON.toJSONString(userCheckResult));
	}
	
	/**
	 * 验证手机验证码
	 */
	@RequestMapping("checkValidCode")
	public void checkValidCode(HttpServletRequest request, CheckValidCodeCommand command, PrintWriter out) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->checkValidCode验证手机验证码command：" + JSON.toJSONString(command));
		ApiResult apiResult = new ApiResult();
		try {
			userService.checkValidCode(command);
			apiResult.setResult(ApiResult.RESULT_CODE_OK);
		} catch (UserException e) {
			apiResult.setResult(ApiResult.RESULT_CODE_FAIL);
			apiResult.setMessage("验证失败");
		}
		out.print(JSON.toJSONString(apiResult));
	}
	
	/**
	 * 验证用户成功，跳转到修改密码页面
	 */
	@RequestMapping("successProving")
	public ModelAndView successProving(HttpServletRequest request, PrintWriter out, 
			String validToken, String redirectUrl) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->successProving进入到修改密码页面");
		ModelAndView mav = new ModelAndView("user/repwd_phone");
		mav.addObject("openid", OpenidTracker.get());
		mav.addObject("validToken", validToken);
		mav.addObject("redirectUrl", redirectUrl);
		mav.addObject("isWCBrowser", isWCBrowser(request));
		return mav;
	}
	
	/**
	 * 修改密码
	 */
	@RequestMapping("updatePwd")
	public void updatePwd(HttpServletRequest request,PrintWriter out, UserUpdatePasswordCommand cmd) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->updatePwd修改密码请求开始" + JSON.toJSONString(cmd));
		UserUpdatePasswordResult userUpdatePasswordResult = new UserUpdatePasswordResult();
		//个人中心用旧密码新密码来修改密码
		if(StringUtils.isNotBlank(cmd.getOldPwd())){
			cmd.setOldPwd(Crypto.md5(cmd.getOldPwd()));
			cmd.setNewPwd(Crypto.md5(cmd.getNewPwd()));
			try {
				String userId = "";
				if(isWCBrowser(request)){
					userId = CachePool.getUser(OpenidTracker.get());
				}else{
					userId= getUserId(request);
				}
				cmd.setUserId(userId);
				UserDTO user = getUserByUserId(userId);
				cmd.setType(user.getBaseInfo().getType());
			} catch (HslapiException e) {
				HgLogger.getInstance().error("yuqz", "UserCtrl->updatePwd获取用户信息失败" + e.getMessage()+HgLogger.getStackTrace(e));
			}
			
			HgLogger.getInstance().info("yuqz", "UserCtrl->updatePwd修改密码开始->用户更新密码请求" + JSON.toJSONString(cmd));

			try {
				UserDTO userDTO = userService.updatePassword(cmd);
				userUpdatePasswordResult.setUserDTO(userDTO);
				userUpdatePasswordResult.setResult(ApiResult.RESULT_CODE_OK);
				userUpdatePasswordResult.setMessage("密码修改成功");
				HgLogger.getInstance().info("yuqz", "UserCtrl->updatePwd修改密码->密码修改成功");
			} catch (UserException e) {
				HgLogger.getInstance().error("yuqz", "UserCtrl->updatePwd修改密码->密码修改失败" + e.getMessage()+HgLogger.getStackTrace(e));
				userUpdatePasswordResult.setResult(Constants.exceptionMap.get(e.getCode()));
				userUpdatePasswordResult.setMessage(e.getMessage());
			}
			HgLogger.getInstance().info("yuqz","UserCtrl->updatePwd修改密码完成->用户更新密码结果"+ JSON.toJSONString(userUpdatePasswordResult));
			out.print(JSON.toJSONString(userUpdatePasswordResult));
		}//用手机验证码来修改
		else{
			
			cmd.setType(1);
			cmd.setNewPwd(Crypto.md5(cmd.getNewPwd()));
			cmd.setValidToken(cmd.getValidToken());
//			cmd.setIdentify(cmd.getIdentify());
			HgLogger.getInstance().info("yuqz", "UserCtrl->updatePwd手机验证码修改密码：" + cmd);
			try {
				UserDTO userDTO = userService.updatePassword(cmd);
			} catch (UserException e) {
				HgLogger.getInstance().error("yuqz", "UserCtrl->updatePwd修改密码->密码修改失败" + e.getMessage()+HgLogger.getStackTrace(e));
				userUpdatePasswordResult.setResult(Constants.exceptionMap.get(e.getCode()));
				userUpdatePasswordResult.setMessage(e.getMessage());
			}
			
			HgLogger.getInstance().info("yuqz","UserCtrl->updatePwd修改密码完成->用户更新密码结果"+ JSON.toJSONString(userUpdatePasswordResult));
			out.print(JSON.toJSONString(userUpdatePasswordResult));
		}
		
		
	}
	
	/**
	 * 进入微信绑定
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	@RequestMapping("bind")
	public ModelAndView bind(PrintWriter out) throws ClientProtocolException, IOException {
		HgLogger.getInstance().info("yuqz", "UserCtrl->bind进入微信绑定页面");
		ModelAndView mav = new ModelAndView("user/bind");
		mav.addObject("openid", OpenidTracker.get());
		String appid  = SysProperties.getInstance().get("wx_app_id");
		String secret = SysProperties.getInstance().get("wx_app_secret");
		TokenAndExpiresOfWx token = WxUtil.getWxAccessTokenFromWx(appid, secret);
		WxUserInfo userInfo = WxUtil.getWxUserInfo(token.getAccess_token(), OpenidTracker.get());
		mav.addObject("nickname", userInfo.getNickname());
		return mav;
	}
	
	/**
	 * 绑定微信号
	 */
	@RequestMapping("bindwx")
	public void bindwx(HslUserQO command, PrintWriter out) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->bindwx绑定微信号请求开始" + JSON.toJSONString(command));
		command.setPassword(Crypto.md5(command.getPassword()));
		UserCheckResult checkResp = check(command);
		if (success(checkResp)) {
			String openid = OpenidTracker.get();
			String userId = checkResp.userDTO.getId();
			CachePool.setUser(openid, userId);
			BindWXCommand bwxCommand = new BindWXCommand();
			bwxCommand.setUserId(userId);
			bwxCommand.setWxAccountId(openid);
			bwxCommand.setPassword(command.getPassword());
			out.print(bindWX(bwxCommand));
		} else {
			out.print(JSON.toJSONString(checkResp));
		}
		HgLogger.getInstance().info("yuqz", "UserCtrl->bindwx绑定微信号请求完成->结果：" + JSON.toJSONString(checkResp));
	}
	
	/**
	 * 验证码校验
	 * @param request
	 * @param out
	 * @param ccode
	 */
	@RequestMapping("check/{ccode}")
	public void check(HttpServletRequest request, PrintWriter out, @PathVariable("ccode")String ccode){
		HgLogger.getInstance().info("yuqz", "UserCtrl->check/{ccode}验证码校验请求开始ccode:" + ccode);
		String scode = (String) request.getSession().getAttribute("rand_code_for_bind");
		if(ccode.equalsIgnoreCase(scode)){
			out.print(1);
			HgLogger.getInstance().info("yuqz", "UserCtrl->check/{ccode}验证码校验成功");
		}
	}
	
	@RequestMapping("nologin")
	public Object center_nologin(HttpServletRequest request) {
		ModelAndView mav;
		if((request.getSession().getAttribute("user") == null)&&StringUtils.isBlank(OpenidTracker.get())) {
			mav = new ModelAndView("user/center_nolog");
			return mav;
		}else{
			RedirectView rv= new RedirectView("center",true);
			return rv;
		}
	}
	
	@RequestMapping("center")
	public ModelAndView center(HttpServletRequest request) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->center进入个人中心请求开始");
		ModelAndView mav;
		if((request.getSession().getAttribute("user") == null)&&StringUtils.isBlank(OpenidTracker.get())) {
			mav = new ModelAndView("user/center_nolog");
			return mav;
		}else{
			mav = new ModelAndView("user/center_new");
		}
		mav.addObject("openid", OpenidTracker.get());
		try {
			if (isWCBrowser(request)) {
				HgLogger.getInstance().info("yuqz", "UserCtrl->center微信用户登录...");
				String appid  = SysProperties.getInstance().get("wx_app_id");
				String secret = SysProperties.getInstance().get("wx_app_secret");
				HgLogger.getInstance().info("yuqz", "UserCtrl->center:appid="+appid + ",secret=" + secret );
				TokenAndExpiresOfWx token = WxUtil.getWxAccessTokenFromWx(appid, secret);
				HgLogger.getInstance().info("yuqz", "UserCtrl->center->token" + token);
				HgLogger.getInstance().info("yuqz", "UserCtrl->center：openid:"+OpenidTracker.get());
				WxUserInfo userInfo = WxUtil.getWxUserInfo(token.getAccess_token(), OpenidTracker.get());
				HgLogger.getInstance().info("yuqz", "UserCtrl->center微信登录userInfo：" + JSON.toJSONString(userInfo));
				HslUserBindAccountQO userQO = new HslUserBindAccountQO();
				userQO.setAccountType(1);
				userQO.setBindAccountId(userInfo.getOpenid());
				HgLogger.getInstance().info("yuqz", "UserCtrl->center根据openid查用户信息：" + JSON.toJSONString(userQO));
				UserDTO userDTO = userService.queryUser(userQO);
				HgLogger.getInstance().info("yuqz", "UserCtrl->center微信登录userDTO：" + JSON.toJSONString(userDTO));
				if(null==userDTO){
					return new ModelAndView("user/bind");
				}
				mav.addObject("nickname", userDTO.getBaseInfo().getNickName());
				mav.addObject("headimgurl", userDTO.getBaseInfo().getImage());
				if(null == userDTO.getBaseInfo().getType()){
					request.setAttribute("userType", "0");
				}else{
					request.setAttribute("userType", userDTO.getBaseInfo().getType().toString());
				}
				HgLogger.getInstance().info("yuqz", "UserCtrl->center账号类型：" + request.getAttribute("userType"));
				CachePool.setUser(userInfo.getOpenid(), userDTO.getId());
				mav.addObject("loginName", userDTO.getAuthInfo().getLoginName());
				mav.addObject("name", userDTO.getBaseInfo().getName());
			} else {
				String userId = getUserId(request);
				HgLogger.getInstance().info("yuqz", "UserCtrl->center用户id：" + userId);
				UserDTO userDTO = getUserByUserId(userId);
				HgLogger.getInstance().info("yuqz", "UserCtrl->center  userDTO：" + JSON.toJSONString(userDTO));
				mav.addObject("loginName", userDTO.getAuthInfo().getLoginName());
				mav.addObject("name", userDTO.getBaseInfo().getName());
				mav.addObject("headimgurl", userDTO.getBaseInfo().getImage());
//				mav.addObject("userDTO",userDTO);
				if(null == userDTO.getBaseInfo().getType()){
					request.setAttribute("userType", "0");
				}else{
					request.setAttribute("userType", userDTO.getBaseInfo().getType().toString());
				}
				HgLogger.getInstance().info("yuqz", "UserCtrl->center账号类型：" + request.getAttribute("userType"));
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz","获取用户信息失败:" + HgLogger.getStackTrace(e));
			return new ModelAndView("error");
		}
			
		return mav;
	}
	
	@RequestMapping("mpos")
	public ModelAndView mpos(HttpServletRequest request) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->mpos门票查询请求开始");
		ModelAndView mav = new ModelAndView("user/mporders");
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	/**
	 * 用户中心的机票订单管理页面:机票订单与门票订单的入口合并
	 * @param request
	 * @return
	 */
	@RequestMapping("jpos")
	public ModelAndView jpos(HttpServletRequest request) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->jpos机票查询请求开始");
		ModelAndView mav = new ModelAndView("user/mpdingdan");
		mav.addObject("statusMap",JSON.toJSONString(JPOrderStatusConstant.SHOP_JPORDER_USER_WAP_STATUS_MAP));
		mav.addObject("NOPAY", JPOrderStatusConstant.SHOP_TICKET_NO_PAY);
		mav.addObject("PAYWAIT", JPOrderStatusConstant.SHOP_PAY_WAIT);
		mav.addObject("TICKET_SUCC", JPOrderStatusConstant.SHOP_TICKET_SUCC);
		mav.addObject("BACK_SUCC_REFUND", JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC);
		mav.addObject("DISCARD_SUCC_REFUND", "");//废票成功已退款,先保留
		mav.addObject("nowDate", new Date());
		mav.addObject("openid", OpenidTracker.get());
		return mav;
	}
	
	/**
	 * 绑定微信
	 * 
	 * @param command
	 * @return
	 */
	private String bindWX(BindWXCommand command) {
		HgLogger.getInstance().info("yuqz", "UserCtrl->bindWX绑定微信开始->绑定微信请求" + JSON.toJSONString(command));
		BindWXResult bindWXResult = new BindWXResult();
		try {
			UserBindAccountDTO userBindAccountDTO = userService.bindWXCommand(command);
			bindWXResult.setUserBindAccountDTO(userBindAccountDTO);
			bindWXResult.setResult(ApiResult.RESULT_CODE_OK);
			bindWXResult.setMessage("绑定成功");
		} catch (UserException e) {
			bindWXResult.setResult(Constants.exceptionMap.get(e.getCode()));
			bindWXResult.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("yuqz", "UserCtrl->bindWX绑定微信完成->绑定微信" + JSON.toJSONString(bindWXResult));
		return JSON.toJSONString(bindWXResult);
	}
	
	/**
	 * 用户验证
	 * 
	 * @param loginName
	 * @param password
	 * @return
	 */
	private UserCheckResult check(HslUserQO userCheckQO) {
		HgLogger.getInstance().info("wuyg", "UserCtrl->check用户验证开始->用户登录请求" + JSON.toJSONString(userCheckQO));
		UserCheckResult userCheckResult = new UserCheckResult();
		try {
			UserDTO userDTO = userService.userCheck(userCheckQO);
			userCheckResult.setUserDTO(userDTO);
			userCheckResult.setResult(ApiResult.RESULT_CODE_OK);
			userCheckResult.setMessage("校验成功");
			HgLogger.getInstance().info("wuyg", "UserCtrl->check用户验证->" + userDTO.getAuthInfo().getLoginName() + "用户登录成功");
		} catch (UserException e) {
			userCheckResult.setResult(Constants.exceptionMap.get(e.getCode()));
			userCheckResult.setMessage(e.getMessage());
			HgLogger.getInstance().error("wuyg", "UserCtrl->check用户验证->" + userCheckQO.getLoginName() + "用户登录失败:" + e.getMessage() + HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().info("wuyg", "UserCtrl->check用户验证完成->用户登录结果:" + JSON.toJSONString(userCheckResult));
		return userCheckResult;
	}
	
	/**
	 * 
	 * @方法功能说明：修改用户资料
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月6日上午10:14:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("updateCompanyUser")
	public String updateCompanyUser(UpdateCompanyUserCommand command,PrintWriter out){
		HgLogger.getInstance().info("yuqz", "UserCtrl->updateCompanyUser修改用户资料请求" + JSON.toJSONString(command));
		UpdateCompanyResult updateCompanyResult = new UpdateCompanyResult();
		if(command.getUserId() == null){
			updateCompanyResult.setResult(UpdateCompanyResult.RESULT_USERID_NULL);
			updateCompanyResult.setMessage("用户id不存在");
			return JSON.toJSONString(updateCompanyResult);
		}
		HslUserQO qo = new HslUserQO();
		qo.setId(command.getUserId());
		UserDTO user = new UserDTO();
		user = userService.queryUnique(qo);
		if(user == null){
			updateCompanyResult.setResult(UpdateCompanyResult.RESULT_USER_NOTEXIST);
			updateCompanyResult.setMessage("用户不存在");
			return JSON.toJSONString(updateCompanyResult);
		}
		UserDTO userDTO = userService.updateCompanyUser(command);
		updateCompanyResult.setUserDTO(userDTO);
		updateCompanyResult.setResult(ApiResult.RESULT_CODE_OK);
		updateCompanyResult.setMessage("修改资料成功");
		HgLogger.getInstance().info("yuqz", "UserCtrl->updateCompanyUser修改用户资料请求完成->结果:" + JSON.toJSONString(updateCompanyResult));
		return JSON.toJSONString(updateCompanyResult);
	}
	
	/**
	 * 
	 * @方法功能说明：修改绑定手机
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月10日下午1:40:40
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("updateBindMobile")
	public String updateBindMobile(UpdateBindMobileCommand command){
		HgLogger.getInstance().info("yuqz", "UserCtrl->updateBindMobile修改绑定手机请求" + JSON.toJSONString(command));
		UpdateBindMobileResult updateBindMobileResult = new UpdateBindMobileResult();
		if(command.getUserId() == null){
			updateBindMobileResult.setResult(UpdateBindMobileResult.RESULT_USERID_NULL);
			updateBindMobileResult.setMessage("用户id不存在");
			return JSON.toJSONString(updateBindMobileResult);
		}
		if(command.getMobile() == null){
			updateBindMobileResult.setResult(UpdateBindMobileResult.RESULT_MOBILE_NULL);
			updateBindMobileResult.setMessage("手机号码不存在");
			return JSON.toJSONString(updateBindMobileResult);
		}
		HslUserQO qo = new HslUserQO();
		qo.setId(command.getUserId());
		UserDTO user = userService.queryUnique(qo);
		if(user == null){
			updateBindMobileResult.setResult(UpdateBindMobileResult.RESULT_USER_NOTEXIST);
			updateBindMobileResult.setMessage("用户不存在");
			return JSON.toJSONString(updateBindMobileResult);
		}
		
		hsl.pojo.command.UpdateBindMobileCommand updateBindMobileCommand = BeanMapperUtils.map(command, hsl.pojo.command.UpdateBindMobileCommand.class);
		UserDTO userDTO = new UserDTO();
		userDTO = userService.updateBindMobile(updateBindMobileCommand);
		updateBindMobileResult.setUserDTO(userDTO);
		updateBindMobileResult.setResult(ApiResult.RESULT_CODE_OK);
		updateBindMobileResult.setMessage("修改手机成功");
		HgLogger.getInstance().info("yuqz", "UserCtrl->updateBindMobile修改绑定手机请求完成->结果:" + JSON.toJSONString(updateBindMobileResult));
		return JSON.toJSONString(updateBindMobileResult);
	}
	
	/**
	 * 
	 * @方法功能说明：查询个人资料
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月17日下午3:14:56
	 * @修改内容：
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("userDetailInfo")
	public ModelAndView userDetailInfo(HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "user->userDetailInfo进入查询个人资料请求");
		ModelAndView mav = new ModelAndView("user/myInfo");
		mav.addObject("openid", OpenidTracker.get());
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
			}else{
				userId= getUserId(request);
			}
			HgLogger.getInstance().info("yuqz", "user->userDetailInfo进入查询个人资料请求userId:"+userId);
			UserDTO userDTO = getUserByUserId(userId);
			HgLogger.getInstance().info("yuqz", "user->userDetailInfo查询个人资料成功:"+JSON.toJSONString(userDTO));
			mav.addObject("headimgurl", userDTO.getBaseInfo().getImage());
			mav.addObject("name", userDTO.getBaseInfo().getName());
			mav.addObject("mobile", userDTO.getContactInfo().getMobile());
			mav.addObject("nickName", userDTO.getBaseInfo().getNickName());
			mav.addObject("email", userDTO.getContactInfo().getEmail());
			mav.addObject("loginName", userDTO.getAuthInfo().getLoginName());
			Province province = provinceService.get(userDTO.getContactInfo().getProvinceId());
			mav.addObject("province", province.getName());
			City city = cityService.get(userDTO.getContactInfo().getCityId());
			mav.addObject("city", city.getName());
			HgLogger.getInstance().info("yuqz", "user->userDetailInfo省份信息:"+province.getName());
			HgLogger.getInstance().info("yuqz", "user->userDetailInfo城市信息:"+city.getName());
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz","查询个人资料失败:" + HgLogger.getStackTrace(e));
		}
		return mav;
	}
	
	/**
	 * 
	 * @方法功能说明：修改头像
	 * @修改者名字：yuqz
	 * @修改时间：2014年11月25日下午3:35:01
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param command
	 * @return:String
	 * @throws
	 */
	@RequestMapping("updateHeadImage")
	@ResponseBody
	public String updateHeadImage(HttpServletRequest request, UpdateHeadImageCommand command){
		HgLogger.getInstance().info("yuqz", "user->updateHeadImage进入修改头像请求:" + command);
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
			}else{
				userId= getUserId(request);
			}
			command.setUserId(userId);
		} catch (HslapiException e) {
			HgLogger.getInstance().error("yuqz","查询个人资料失败:" + HgLogger.getStackTrace(e));
		}
		UserDTO userDTO = userService.updateHeadImage(command);
		HgLogger.getInstance().info("yuqz", "user->updateHeadImage修改头像成功,结果:" + userDTO);
		return JSON.toJSONString(userDTO);
	}
	
	
	/**
	 * 
	 * @方法功能说明：查询个人资料
	 * @修改者名字：zhuxy
	 * @修改时间：2015年1月20日下午3:14:56
	 * @修改内容：
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("repwdd")
	public ModelAndView repwd(HttpServletRequest request){
		ModelAndView mav = new ModelAndView("user/repwd");
		return mav;
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到个人资料修改页面
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月21日下午5:47:26
	 * @修改内容：
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("editInfoView")
	public ModelAndView editInfoView(HttpServletRequest request){
		HgLogger.getInstance().info("yuqz", "user->editInfoView跳转到个人资料修改页面请求");
		ModelAndView mav = new ModelAndView("user/editInfo");
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		mav.addObject("provinces", provinceList);
		mav.addObject("openid", OpenidTracker.get());
		try {
			String userId = "";
			if(isWCBrowser(request)){
				userId = CachePool.getUser(OpenidTracker.get());
			}else{
				userId= getUserId(request);
			}
			HgLogger.getInstance().info("yuqz", "user->editInfoView进入查询个人资料请求userId:"+userId);
			UserDTO userDTO = getUserByUserId(userId);
			HgLogger.getInstance().info("yuqz", "user->editInfoView查询个人资料成功:"+JSON.toJSONString(userDTO));
			mav.addObject("userDTO", userDTO);
			Province province = provinceService.get(userDTO.getContactInfo().getProvinceId());
			// 查询市
			CityQo cityQo = new CityQo();
			cityQo.setProvinceCode(province.getCode());
			List<City> cityList = cityService.queryList(cityQo);
			mav.addObject("cities", cityList);
			mav.addObject("province", province);
			City city = cityService.get(userDTO.getContactInfo().getCityId());
			mav.addObject("city", city);
			HgLogger.getInstance().info("yuqz", "user->editInfoView省份信息:"+province.getName());
			HgLogger.getInstance().info("yuqz", "user->editInfoView城市信息:"+city.getName());
		} catch (Exception e) {
			HgLogger.getInstance().error("yuqz","查询个人资料失败:" + HgLogger.getStackTrace(e));
		}
		return mav;
	}
	
	/**
	 * 
	 * @方法功能说明：根据省份查询相应的城市
	 * @修改者名字：yuqz
	 * @修改时间：2015年1月22日下午5:19:18
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param province
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/searchCity")
	@ResponseBody
	public String searchCity(HttpServletRequest request,Model model,
			@RequestParam(value = "province", required = false) String province){
		// 查询市
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(province);
		List<City> cityList = cityService.queryList(cityQo);
		HgLogger.getInstance().info("yuqz", "根据省份查询城市成功");
		return JSON.toJSONString(cityList);
	}
	/**
	 * @方法功能说明：发送验证码
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:48:59
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("/captcha-image")
	public ModelAndView handleRequest(HttpServletRequest request,
									  HttpServletResponse response) throws Exception {
		HgLogger.getInstance().info("chenxy", "发送验证码");
		ServletOutputStream out=null;
		try {
			response.setDateHeader("Expires", 0);
			response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
			response.addHeader("Cache-Control", "post-check=0, pre-check=0");
			response.setHeader("Pragma", "no-cache");
			response.setContentType("image/jpeg");
			String capText = captchaProducer.createText();
			request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
			BufferedImage bi = captchaProducer.createImage(capText);
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "发送验证码失败"+HgLogger.getStackTrace(e));
		}finally {
			if(null!=out){
				out.close();
			}
		}
		return null;
	}
	
}

