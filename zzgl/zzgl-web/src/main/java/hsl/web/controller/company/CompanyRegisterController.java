package hsl.web.controller.company;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import hg.common.util.MD5HashUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hsl.pojo.command.SendValidCodeCommand;
import hsl.pojo.command.UserRegisterCommand;
import hsl.pojo.command.UserUpdatePasswordCommand;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.message.CouponMessage;
import hsl.spi.inter.user.UserService;
import hsl.web.controller.BaseController;
import hsl.web.util.NetworkUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *               
 * @类功能说明：企业注册控制器
 * @类修改者：
 * @修改日期：2014年9月18日下午4:47:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月18日下午4:47:23
 *
 */
@Controller
@RequestMapping("/comRegister")
public class CompanyRegisterController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private HgLogger hgLogger;
	@Resource
	private CityService cityService;
	@Resource
	private AreaService areaService;
	@Autowired
	private RabbitTemplate template;
	
	
	private Producer captchaProducer = null;
	 
	@Autowired
	public void setCaptchaProducer(Producer captchaProducer) {
	      this.captchaProducer = captchaProducer;
	}
	/**
	 * @方法功能说明：跳转企业注册页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:47:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/main")
	public String main(HttpServletRequest request,HttpServletResponse response,Model   model){
		hgLogger.info("chenxy", "跳转到企业注册页面");
		model.addAttribute("index", "2");
		return "/company/register.html";
	}
	/**
	 * @方法功能说明：企业用户注册
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:48:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping("/register")
	public RedirectView register(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute UserRegisterCommand command,RedirectAttributes attr){
		String authcode=request.getParameter("authcode");
		String sessionCode=(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		attr.addFlashAttribute("Password2", command.getPassword());
		command.setPassword(MD5HashUtil.toMD5(command.getPassword()));
		//设置添加的用户类别
		command.setType(2);
		if(sessionCode.equalsIgnoreCase(authcode)){
			try{
				UserDTO userDTO=userService.register(command,"PC");
				hgLogger.info("chenxy", "企业用户注册成功");
				
				//发送注册成功消息
				
				CouponMessage baseAmqpMessage=new CouponMessage();
				baseAmqpMessage.paddingUserRegisterContent(userDTO);
//				baseAmqpMessage.setContent(command);
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
				template.convertAndSend("zzpl.regist",baseAmqpMessage);
				
				attr.addAttribute("usereamil", userDTO.getContactInfo().getEmail());
				return new RedirectView("/comRegister/regsucce",true);
			}catch (UserException e){
				if(e.getCode().equals(UserException.RESULT_LOGINNAME_REPEAT)){
					hgLogger.error("chenxy", "企业用户注册失败>>登录名重复");
					attr.addFlashAttribute("nameRepeat",e.getMessage());
				}else if(e.getCode().equals(UserException.RESULT_VALICODE_INVALID)||e.getCode().equals(UserException.RESULT_VALICODE_WRONG)||
						e.getCode().equals(UserException.RESULT_VALICODE_INVALID)
						){
					hgLogger.error("chenxy", "企业用户注册失败>>手机验证错误");
					attr.addFlashAttribute("mobileRepeat",e.getMessage());
				}else{
					attr.addFlashAttribute("userNameRepeat",e.getMessage());
				}
				e.printStackTrace();
			}
		}else{
			hgLogger.error("chenxy", "企业用户注册失败>>验证错误");
			attr.addFlashAttribute("pcodeError", "error");
		}
		attr.addFlashAttribute("userInfo", command);
		return new RedirectView("/comRegister/main",true);
	}
	/**
	 * @方法功能说明：跳转企业注册成功页面
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:48:36
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/regsucce")
	public String regsucce(HttpServletRequest request,HttpServletResponse response,Model   model){
		hgLogger.info("chenxy", "跳转注册成功页面");
		model.addAttribute("usereamil", request.getParameter("usereamil"));
		return "/company/regsucce.html";
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
	   response.setDateHeader("Expires", 0);
	   response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
	   response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	   response.setHeader("Pragma", "no-cache");
	   response.setContentType("image/jpeg");
	   String capText = captchaProducer.createText();
	   request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,capText);
	   BufferedImage bi = captchaProducer.createImage(capText);
	   ServletOutputStream out = response.getOutputStream();
	   ImageIO.write(bi, "jpg", out);
	   try {
	       out.flush();
	   } catch(Exception e){
		   e.printStackTrace();
		   HgLogger.getInstance().error("chenxy", "发送验证码失败"+HgLogger.getStackTrace(e));
	   }finally {
	       out.close();
	   }
	   return null;
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
	@RequestMapping("/sendImageCodeByReg")
	public ModelAndView sendImageCodeByReg(HttpServletRequest request,
										   HttpServletResponse response) throws Exception {
		HgLogger.getInstance().info("chenxy", "发送验证码");
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control","no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String capText = captchaProducer.createText();
		request.getSession().setAttribute(SESSION_REG_IMAGE_CODE,capText);
		BufferedImage bi = captchaProducer.createImage(capText);
		ServletOutputStream out = response.getOutputStream();
		ImageIO.write(bi, "jpg", out);
		try {
			out.flush();
		} catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "发送验证码失败"+HgLogger.getStackTrace(e));
		}finally {
			out.close();
		}
		return null;
	}
	/**
	 * @方法功能说明：发送手机验证码
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:51:31
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/sendCode")
	@ResponseBody
	public String sendValidCode(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute SendValidCodeCommand command){
		Map<String,Object> map=new HashMap<String, Object>();
		String reallyIp= NetworkUtil.getIpAddr(request);
		HgLogger.getInstance().info("chenxy", "发送手机验证码 ,场景>>" + command.getScene() + "手机号：" + command.getMobile()+"来源ip>>"+reallyIp);
		try {
			String authcode=request.getParameter("imagecode");
			String sessionCode=(String) request.getSession().getAttribute(SESSION_REG_IMAGE_CODE);
			//设置添加的用户类别
			if(command.getScene().equals(1)){
				/**
				 * 当 场景是1 注册的时候必须 判断是否有 图片验证码
				 */
				if(null==sessionCode||!sessionCode.equalsIgnoreCase(authcode)){
					/**
					 * 由于恶性请求，优化注册发送手机验证码流程的请求
					 */
					throw new UserException(1,"图片验证码不正确");
				}

			}else if(command.getScene().equals(3)||command.getScene().equals(4)){
				/**
				 * 如果是场景3或者场景4的话，必须判断用户是否登录
				 */
				UserDTO userDTO=getUserBySession(request);
				if(userDTO==null){
					throw new UserException(9,"发送短信前,用户必须是登录状态");
				}
			}
			/**
			 * 如果传入的scene业务场景为其他值，则在service做相应的处理。
			 */
			String validToken = userService.sendValidCode(command);
			hgLogger.info("chenxy", "发送手机验证码成功>>验证码：" + validToken);
			map.put("validToken", validToken);
			map.put("status", "success");
			request.getSession().removeAttribute(SESSION_REG_IMAGE_CODE);
		} catch (UserException e) {
			HgLogger.getInstance().error("chenxy", "发送手机验证码失败>>"+e.getMessage()+HgLogger.getStackTrace(e));
			map.put("error", e.getMessage());
			map.put("status", "error");
			e.printStackTrace();
		}
		return JSON.toJSONString(map);
	}
	/**
	 * @方法功能说明：根据省份查询相应的城市
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:55:08
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
		hgLogger.info("chenxy", "根据省份查询城市成功");
		return JSON.toJSONString(cityList);
	}
	/**
	 * @方法功能说明：根据城市ID查询区域
	 * @修改者名字：chenxy
	 * @修改时间：2014年10月10日下午2:57:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param city
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/searchArea")
	@ResponseBody
	public String searchArea(HttpServletRequest request,Model model,
			@RequestParam(value = "city", required = false) String city) {
		// 查询区
		AreaQo areaQo = new AreaQo();
		areaQo.setCityCode(city);
		List<Area> areaList = areaService.queryList(areaQo);
		return JSON.toJSONString(areaList);
	}
	/**
	 * @throws UnsupportedEncodingException 
	 * 
	 * @方法功能说明：用户忘记密码 跳转（由于忘记密码，是共有模块，因此个人和企业公用一个模块）
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月28日下午3:08:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/forgetPwd")
	public String forgetPwd(HttpServletRequest request,HttpServletResponse response,Model model) throws UnsupportedEncodingException {
		hgLogger.info("chenxy", "跳转到忘记密码页面");
		model.addAttribute("type", request.getParameter("type"));
		String mobileCode=request.getParameter("mobileCode");
		if(StringUtils.isNotBlank(mobileCode)){
		model.addAttribute("mobileCode", new String(request.getParameter("mobileCode").getBytes("iso-8859-1"), "utf-8"));
		}
		String mobileError=request.getParameter("mobileError");
		if(StringUtils.isNotBlank(mobileError)){
		model.addAttribute("mobileError", new String(request.getParameter("mobileError").getBytes("iso-8859-1"),"utf-8"));
		}
		return "company/forgetPwd.html";
	}
	/**
	 * @方法功能说明：用户重置密码
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月28日下午3:08:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@param attr
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping("/forgetPwd/reset")
	public RedirectView resetPwd(HttpServletRequest request,
			HttpServletResponse response, Model model,
								 @ModelAttribute UserUpdatePasswordCommand command,
								 RedirectAttributes attr) {
		hgLogger.info("chenxy", "开始重置密码");
		try {
			command.setNewPwd(MD5HashUtil.toMD5(command.getNewPwd()));
			userService.updatePassword(command);
			hgLogger.info("chenxy", "重置密码成功");
		} catch (UserException e) {
			//attr.addFlashAttribute("command", command);
			hgLogger.error("chenxy","重置密码失败>>" + e.getMessage() + HgLogger.getStackTrace(e));
			attr.addFlashAttribute("index", command.getType());
			attr.addFlashAttribute("validToken",command.getValidToken());
			if (e.getCode().equals(UserException.RESULT_VALICODE_INVALID)
					|| e.getCode().equals(UserException.VALIDCODE_OVERTIME)) {
				attr.addFlashAttribute("mobileCode",e.getMessage());
			}
			if (e.getCode().equals(UserException.USER_NOT_FOUND)) {
				attr.addFlashAttribute("mobileCode", e.getMessage());
			}
			e.printStackTrace();
			return new RedirectView("/comRegister/forgetPwd", true);
		}
		return new RedirectView("/comRegister/forgetPwd/success", true);
	}
	/**
	 * @方法功能说明：用户重置密码成功
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月28日下午3:09:17
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/forgetPwd/success")
	public String resetPwdSuc(HttpServletRequest request,HttpServletResponse response,Model model){
		hgLogger.info("chenxy", "跳转重置密码成功页面");
		model.addAttribute("user", request.getParameter("user"));
		return "company/resetSuc.html";
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	public HgLogger getHgLogger() {
		return hgLogger;
	}
	public void setHgLogger(HgLogger hgLogger) {
		this.hgLogger = hgLogger;
	}
}
