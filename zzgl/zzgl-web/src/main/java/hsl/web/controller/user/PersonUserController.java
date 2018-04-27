package hsl.web.controller.user;
import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Constants;
import hg.common.util.MD5HashUtil;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.pojo.command.CheckRegisterMailCommand;
import hsl.pojo.command.SendMailCodeCommand;
import hsl.pojo.command.UserRegisterCommand;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.UserException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.user.HslUserQO;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.user.UserService;
import hsl.web.controller.BaseController;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @类功能说明：个人用户控制器
 * @类修改者：
 * @修改日期：2014年9月18日下午4:47:23
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年9月18日下午4:47:23
 *
 */
@Controller
@RequestMapping("/user")
public class PersonUserController extends BaseController{
	
	@Autowired
	private UserService userService;
	@Autowired
	private RabbitTemplate template;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private AccountService accountService;
	/**
	 * @方法功能说明：跳转个人注册页面
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
	@RequestMapping("/register")
	public String main(HttpServletRequest request,HttpServletResponse response,Model   model){
		hgLogger.info("chenxy", "跳转到个人注册页面");
		model.addAttribute("index", "1");
		return "/company/register.html";
	}
	/**
	 * @方法功能说明：个人用户注册
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
	@RequestMapping("/register/regUser")
	public RedirectView register(HttpServletRequest request,HttpServletResponse response,
			@ModelAttribute UserRegisterCommand command,RedirectAttributes attr){
		String authcode=request.getParameter("authcode");
		String sessionCode=(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		if(sessionCode==null||command.getPassword()==null||authcode==null){
			return new RedirectView("/user/register",true);
		}
		attr.addFlashAttribute("Password1", command.getPassword());
		//设置添加的用户类别
		command.setType(1);
		if(sessionCode.equalsIgnoreCase(authcode)){
			try{
				command.setPassword(MD5HashUtil.toMD5(command.getPassword()));
				UserDTO userDTO=userService.register(command,"PC");
				hgLogger.info("chenxy", "个人用户注册成功");
				attr.addAttribute("usereamil", userDTO.getContactInfo().getEmail());
				//发送注册成功消息
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
				template.convertAndSend("zzpl.regist",baseAmqpMessage);
				return new RedirectView("/user/regsucce",true);
			}catch (UserException e){
				attr.addFlashAttribute("index","2");
				hgLogger.error("chenxy", "个人用户注册失败>>登录名重复");
				if(e.getCode().equals(UserException.RESULT_VALICODE_INVALID)||
						e.getCode().equals(UserException.RESULT_VALICODE_INVALID)
						||e.getCode().equals(UserException.VALIDCODE_OVERTIME)
						||e.getCode().equals(UserException.RESULT_VALICODE_WRONG)){
					attr.addFlashAttribute("valicodeInvalid",e.getMessage());
				}else if(e.getCode().equals(UserException.RESULT_EMAIL_BIND)){
					attr.addFlashAttribute("emailBind",e.getMessage());
				}else if(e.getCode().equals(UserException.RESULT_LOGINNAME_REPEAT)){
					attr.addFlashAttribute("userNameRepeat",e.getMessage());
				}else if(e.getCode().equals(UserException.RESULT_MOBILE_BIND)||
						e.getCode().equals(UserException.RESULT_MOBILE_WRONG)){
					attr.addFlashAttribute("mobileRepeat",e.getMessage());
				}
				e.printStackTrace();
			}
			
		}else{
			hgLogger.error("chenxy", "个人用户注册失败>>验证错误");
			attr.addFlashAttribute("pcodeError", "error");
		}
		attr.addFlashAttribute("puserInfo", command);
		return new RedirectView("/user/register",true);
	}
	/**
	 * @方法功能说明：跳转个人注册成功页面
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
		model.addAttribute("usereamil",request.getParameter("usereamil"));
		return "/company/regsucce.html";
	}
	/**
	 * @方法功能说明：发送邮件
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月5日下午4:13:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/sendMail")
	@ResponseBody
	public String sendMail(HttpServletRequest request,HttpServletResponse response,Model model){
		String mail=request.getParameter("email");
		SendMailCodeCommand sendMailCodeCommand=new SendMailCodeCommand();
		sendMailCodeCommand.setMail(mail);
		sendMailCodeCommand.setScene(SendMailCodeCommand.SENDMAIL_SCENE_REGISTER);
		HgLogger.getInstance().info("chenxy", "重新发送注册激活邮件邮件");
		String token="";
		try {
			token=userService.sendRegisterMail(sendMailCodeCommand);
		} catch (UserException e) {
			HgLogger.getInstance().error("yuqz", "重新发送注册激活邮件邮件失败:" + e.getMessage() + HgLogger.getStackTrace(e));
			e.printStackTrace();
		}
		return token;
	}
	/**
	 * @方法功能说明：发送邮箱验证码
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月19日上午10:20:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/sendMailCode")
	@ResponseBody
	public String sendValidCode(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute SendMailCodeCommand command){
		Map<String,Object> map=new HashMap<String, Object>();
		HgLogger.getInstance().info("chenxy", "发送手机验证码 ,场景>>" + command.getScene() + "邮箱：" + command.getMail());
		try {
			String validToken = userService.sendMailValidCode(command);
			hgLogger.info("chenxy", "发送邮箱验证码成功>>验证码："+validToken);
			map.put("validToken", validToken);
			map.put("status", "success");
		} catch (Exception e) {
			HgLogger.getInstance().error("chenxy", "发送邮箱验证码失败>>"+e.getMessage()+HgLogger.getStackTrace(e));
			map.put("error", e.getMessage());
			map.put("status", "error");
		}
		return JSON.toJSONString(map);
	}
	@RequestMapping("/login")
	public String toLogin(HttpServletRequest request,HttpServletResponse response,Model model){
		hgLogger.info("chenxy", "跳转个人用户登录页面");
		model.addAttribute("index", "0");
		return "company/login.html";
	}
	/**
	 * @方法功能说明：个人用户登录
	 * @修改者名字：chenxy
	 * @修改时间：2014年9月25日上午11:19:42
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param hslUserQO
	 * @参数：@param attr
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping("/login/check")
	public Object userLogin(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute HslUserQO hslUserQO,RedirectAttributes attr){
		String authcode=request.getParameter("authcode");
		String sessionCode=(String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
		hgLogger.info("chenxy", hslUserQO.getLoginName() + "个人用户登录");
		attr.addFlashAttribute("password1", request.getParameter("password"));
		attr.addFlashAttribute("hslUserQO", hslUserQO);
		if(null!=sessionCode&&sessionCode.equalsIgnoreCase(authcode)){
			try {
				//设置个人用户类型
				hslUserQO.setType(1);
				hslUserQO.setPassword(MD5HashUtil.toMD5(hslUserQO.getPassword()));
				UserDTO userDTO=userService.userCheck(hslUserQO);
				//查询余额账户
				AccountQO account=new AccountQO();
				account.setUserID(userDTO.getId());
				account.setConsumeOrderSnapshots(false);
				AccountDTO accountDTO=accountService.queryUnique(account);
				//将用户放入SESSION中
				hgLogger.info("chenxy",hslUserQO.getLoginName()+"个人用户登录成功");
				if(accountDTO!=null){
					userDTO.setAccountId(accountDTO.getId());
				}
				putUserToSession(request, userDTO);
			} catch (Exception e) {
				HgLogger.getInstance().error("chenxy", "IndexController->companyLogin->exception:"+"个人用户登录失败"+e.getMessage() + HgLogger.getStackTrace(e));
				attr.addFlashAttribute("perror", e.getMessage());
				attr.addFlashAttribute("index", "0");
				return new RedirectView("/user/login",true);
			}
		}else{
			hgLogger.error("chenxy",hslUserQO.getLoginName()+"个人用户登录验证码错误");
			attr.addFlashAttribute("pcodeError", "验证码错误");
			attr.addFlashAttribute("index", "0");
			return new RedirectView("/user/login",true);
		}
		//获得用户未登录时重定向地址
		String redirectUrl=(String) request.getSession().getAttribute("redirectUrl");
		if(StringUtils.isNotBlank(redirectUrl)){
			String[]  urls=redirectUrl.split("\\?");
			if(urls!=null&&urls.length>1){
				ModelAndView mAndView=new ModelAndView(new RedirectView(urls[0],true));
				String[] params=urls[1].split("\\&");
			
					if(params!=null&&params.length>0) {
						for (int i = 0; i < params.length; i++) {
							String[] m = params[i].split("\\=");
							mAndView.addObject(m[0], m[1]);
						}

						//跳转后将重定向地址session删除
						request.getSession().removeAttribute("redirectUrl");
						return mAndView;
					}
				
			}
		}
		return  new RedirectView("/home",true);
	}
	/**
	 * @方法功能说明：检查注册激活邮件
	 * @修改者名字：chenxy
	 * @修改时间：2014年11月20日下午4:37:05
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/checkRegisterMail")
	public RedirectView checkRegisterMail(HttpServletRequest request,HttpServletResponse response,Model model,@ModelAttribute CheckRegisterMailCommand command){
		try {
			HgLogger.getInstance().info("chenxy", "PersonUserController>>checkRegisterMail>激活注册邮件>>>" + command.getUserName());
			UserDTO userDTO= userService.checkRegisterMail(command);
			putUserToSession(request, userDTO);
		} catch (Exception e) {
			HgLogger.getInstance().error("chenxy", "PersonUserController>>checkRegisterMail>激活注册邮件失败>>>" + command.getUserName() + HgLogger.getStackTrace(e));
			e.printStackTrace();
			return new RedirectView("/user/login",true);
		}
		return new RedirectView("/home",true);
	}
	
	@RequestMapping("/registerDeal")
	public String userRegisterDeal(HttpServletRequest request,HttpServletResponse response,Model model){
		return "deal/registerDeal.html";
	}
	@RequestMapping("/jpOrderDeal")
	public String jpOrderDeal(HttpServletRequest request,HttpServletResponse response,Model model){
		return "deal/jpOrderDeal.html";
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
