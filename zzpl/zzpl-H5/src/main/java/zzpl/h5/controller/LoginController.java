package zzpl.h5.controller;

import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.request.user.CheckSMSValidCommand;
import zzpl.api.client.request.user.GetResetPasswordSMSValidCommand;
import zzpl.api.client.request.user.ResetPasswordCommand;
import zzpl.api.client.response.user.CheckSMSValidResponse;
import zzpl.api.client.response.user.GetResetPasswordSMSValidResponse;
import zzpl.api.client.response.user.ResetPasswordResponse;
import zzpl.h5.service.LoginService;
import zzpl.h5.service.UserService;
import zzpl.h5.service.command.LoginCommand;
import zzpl.h5.service.command.ValidCodeCheckCommand;
import zzpl.h5.service.exception.LoginException;
import zzpl.h5.util.base.BaseController;

import com.alibaba.fastjson.JSON;

@Controller
public class LoginController extends BaseController {
	
	/**
	 * 登陆service
	 */
	@Autowired
	private LoginService loginService;

	@Autowired
	private UserService userService;

	/**
	 * 
	 * @方法功能说明：跳转登陆页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:09:22
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param message
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model, String message) {
		model.addAttribute("message", message);
		return "fly_login.html";
	}

	/**
	 * 
	 * @方法功能说明：登录校验
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:09:10
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping(value = "/login/check")
	public RedirectView loginCheck(HttpServletRequest request,
			HttpServletResponse response, Model model, LoginCommand command)
			throws IOException {
		try {
			loginService.login(command, request);
		} catch (LoginException e) {
			HgLogger.getInstance().error("cs",
					"登陆失败:command【" + JSON.toJSONString(command, true) + "】");
			model.addAttribute("message", e.getMessage());
			return new RedirectView("/login", true);
		}
		return new RedirectView("/home", true);
	}

	/**
	 * @方法功能说明：注销登录
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:08:46
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@throws IOException
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		loginService.logout();
		response.sendRedirect(request.getContextPath() + "/login");
	}

	/**
	 * 
	 * @方法功能说明：登录后跳转
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:07:56
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/home")
	public String index(HttpServletRequest request,Model model){
		AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		
		if(user!=null){
			//1.校验用户是否合法
			return "/fly_index.html";
		}else{
			return "/fly_login.html";
		}
	}
	
	/**
	 * 
	 * @方法功能说明：主页面默认页
	 * @修改者名字：cangs
	 * @修改时间：2015年6月19日上午9:08:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		return "/admin/welcome.html";
	}
	
	/**
	 * 跳转到忘记密码页面1
	 */
	@RequestMapping("/login/fPwd_1")
	public String person_setting(HttpServletRequest request,
			HttpServletResponse response,Model model,String message) {
		model.addAttribute("message", message);
		return "fPwd_1.html";
	}
	/**
	 * @方法功能说明：验证码输出
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23下午4:57:06
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	@RequestMapping(value = "/login/valid-code-image.jpg")
	public void getKaptchaImage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		BufferedImage bi = loginService.getKaptchaImage(request, response);
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(bi, "jpg", out);
			out.flush();
		} finally {
			if (out != null)
				out.close();
		}
	}

	/**
	 * @方法功能说明：验证码检查
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23下午4:56:56
	 * @修改内容：
	 * @参数：@param validCode
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	protected boolean validCodeCheck(HttpServletRequest request,
			ValidCodeCheckCommand command) {

		return loginService.validCodeCheck(command, request);
	}
	
	/**
	 * 第二页，验证身份
	 */
	@RequestMapping("/login/getResetPasswordSMSValid")
	public  String getResetPasswordSMSValid(ValidCodeCheckCommand command,HttpServletRequest request,
			HttpServletResponse response,Model model) {
	
		Map<Object, Object> maplist = new HashMap<Object, Object>();
		if (!validCodeCheck(request, command)) {
			return person_setting(request,response,model,"验证码不正确");
		}
		
		GetResetPasswordSMSValidCommand getResetPasswordSMSValidCommand = new GetResetPasswordSMSValidCommand();
		getResetPasswordSMSValidCommand.setCompanyID(command.getBh());
		getResetPasswordSMSValidCommand.setLoginName(command.getZh());
		GetResetPasswordSMSValidResponse getResetPasswordSMSValidResponse = userService.getResetPasswordSMSValid(getResetPasswordSMSValidCommand,request);
		if(getResetPasswordSMSValidResponse!=null&&getResetPasswordSMSValidResponse.getResult()!=null&&getResetPasswordSMSValidResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			maplist.put("success", true);
			model.addAttribute("sagaID", getResetPasswordSMSValidResponse.getSagaID());
			model.addAttribute("mobile", getResetPasswordSMSValidResponse.getLinkMobile());
			return "fPwd_2.html";
		}else{
			return person_setting(request,response,model,getResetPasswordSMSValidResponse.getMessage());
		}

	}
	
	
	/**
	 * 跳转到忘记密码页面3
	 */
	@RequestMapping("/login/checkSMSValid")
	public  String checkSMSValid(CheckSMSValidCommand command,HttpServletRequest request,
			HttpServletResponse response,Model model,String mobile) {
		CheckSMSValidResponse checkSMSValidResponse = userService.checkSMSValid(command);
		if(checkSMSValidResponse!=null&&checkSMSValidResponse.getResult()!=null&&checkSMSValidResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			model.addAttribute("sagaID", command.getSagaID());
			return "fPwd_3.html";
		}else{
			model.addAttribute("sagaID", command.getSagaID());
			model.addAttribute("mobile", mobile);
			model.addAttribute("message", checkSMSValidResponse.getMessage());
			return "fPwd_2.html";
		}
	}
	
	@RequestMapping("login/resetpassword")
	public RedirectView resetPassword(ResetPasswordCommand resetPasswordCommand,HttpServletRequest request,
			HttpServletResponse response,Model model) {
		ResetPasswordResponse resetPasswordResponse = userService.resetPassword(resetPasswordCommand);
		if(resetPasswordResponse!=null&&resetPasswordResponse.getResult()!=null&&resetPasswordResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			model.addAttribute("message", "重置密码成功");
			return new RedirectView("/login", true);
		}else{
			model.addAttribute("sagaID", resetPasswordCommand.getSagaID());
			return new RedirectView("/login/resetpassword", true);
		}
	}
	
	
}
