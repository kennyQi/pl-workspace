package hsl.admin.controller.auth;

import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.service.SecurityService;
import hsl.admin.controller.BaseController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;


/**
 * 后台用户管理模块
 * 
 * @author lixuanxuan
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private SecurityService securityService;
	
	/**
	 * 后台登录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model, 
			@RequestParam(required=false) String error) {

		String message = "";
		if (SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME.equals(error)) {
			message = "用户名不存在";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)) {
			message = "密码错误";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)) {
			message = "用户被锁定";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)) {
			message = "用户异常";
		}

		model.addAttribute("message", message);
		
		return "/admin/login.html";
	}
	
	/**
	 * 登录表单提交入口
	 * @param request
	 * @param model
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/login/check")
	public RedirectView loginCheck(HttpServletRequest request, HttpServletResponse response, Model model, 
			@RequestParam(value="loginName", required=false) String loginName,
			@RequestParam(value = "password", required = false) String password)
			throws IOException {

		UsernamePasswordToken upt = new UsernamePasswordToken(loginName, MD5HashUtil.toMD5(password));
		Subject currentUser = SecurityUtils.getSubject();

		try {
			currentUser.login(upt);
		} catch (UnknownAccountException uae) {
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
			return new RedirectView("/login", true);
		} catch (IncorrectCredentialsException ice) {
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD);
			return new RedirectView("/login", true);
		} catch (LockedAccountException lae) {
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_LOCK);
			return new RedirectView("/login", true);
		} catch (AuthenticationException ae) {
			ae.printStackTrace();
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_NULL);
			return new RedirectView("/login", true);
		}
		AuthUser usr = securityService.findUserByLoginName(loginName);
		currentUser.getSession().setAttribute(SecurityConstants.SESSION_USER_KEY, usr);
		
		return new RedirectView("/home", true);

	}
}
