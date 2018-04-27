package hg.dzpw.admin.controller.auth;

import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.system.common.system.SecurityConstants;
import hg.system.common.util.MD5HashUtil;
import hg.system.model.auth.AuthUser;
import hg.system.model.staff.Staff;
import hg.system.qo.StaffQo;
import hg.system.service.SecurityService;
import hg.system.service.StaffService;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.google.code.kaptcha.Producer;

@Controller
public class LoginController extends BaseController {
	
	@Autowired
	private SecurityService securityService;
	@Resource
	private StaffService staffService;
	
	@Autowired
	private Producer captchaProducer;

	private static final String DZPW_SESSION_VALID_CODE_KEY = "_DZPW_SESSION_VALID_CODE_";
	
	/**
	 * @方法功能说明：判断是否登录
	 * @修改者名字：zhurz
	 * @修改时间：2014-10-23下午3:25:09
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/is-login")
	public String isLogin(HttpServletRequest request) {
		if (request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY) != null)
			return "true";
		return "false";
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
	protected boolean validCodeCheck(HttpServletRequest request, String validCode) {
		if (validCode == null)
			return false;
		
		String sessionValidCode = (String) request.getSession().getAttribute(DZPW_SESSION_VALID_CODE_KEY);
		request.getSession().removeAttribute(DZPW_SESSION_VALID_CODE_KEY);
		
		if (sessionValidCode == null)
			return false;
		
		// 匹配验证码
		if (StringUtils.equalsIgnoreCase(validCode, sessionValidCode))
			return true;
		
		return false;
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
	@RequestMapping(value="/login/valid-code-image.jpg")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		// 生成验证码
		String capText = captchaProducer.createText();
		session.setAttribute(DZPW_SESSION_VALID_CODE_KEY, capText);
		BufferedImage bi = captchaProducer.createImage(capText);
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
	 * 后台登录
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/login")
	public String login(HttpServletRequest request, Model model,
			@RequestParam(required = false) String error,
			@RequestParam(required = false) String loginName) {

		String message = "";
		if ("-1".equals(error))
			message="验证码错误";
		else if (SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME.equals(error)) {
			message = "用户名不存在";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)) {
			message = "用户名或密码错误";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)) {
			message = "用户被锁定";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)) {
			message = "用户异常";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_FREEZE_TIME.equals(error)) {
			message = "多次登录错误,用户处于禁止登录中,稍后再试";
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
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "validcode", required = false) String validcode)
			throws IOException {
		

		// 验证码检查
		if (!validCodeCheck(request, validcode)) {
			model.addAttribute("error", "-1");
			return new RedirectView("/login", true);
		}

		if(StringUtils.isBlank(loginName) || StringUtils.isBlank(password)){
			model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
			return new RedirectView("/login", true);
		} else {

			StaffQo sqo = new StaffQo();
			sqo.setLoginName(loginName);
			Staff staff = staffService.queryUnique(sqo);

			if (staff == null) {
				model.addAttribute("error", SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME);
				return new RedirectView("/login", true);
			}

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
	
	@RequestMapping(value="/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException{
		Subject currentUser = SecurityUtils.getSubject();
		currentUser.logout();
		currentUser.getSession().removeAttribute(SecurityConstants.SESSION_USER_KEY);
		response.sendRedirect(request.getContextPath() + "/login");
	}
	
	/**
	 * @方法功能说明：修改密码
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-6下午4:50:13
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/password-edit")
	public String passwordEdit(){
		return "/admin/passwordEdit.html";
	}
	
	/**
	 * @方法功能说明：修改密码
	 * @修改者名字：zhurz
	 * @修改时间：2014-11-6下午4:50:13
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/change-password")
	public String changePassword(
			HttpServletRequest request,
			@RequestParam(value="oldpwd", required=true)String oldpwd,
			@RequestParam(value="newPwd", required=true)String newPwd,
			@RequestParam(value="reNewPwd", required=true)String reNewPwd) {
		Object obj = request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if (obj != null) {
			AuthUser usr = (AuthUser) obj;
			if (oldpwd.length() == 0 || newPwd.length() < 6)
				return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败：密码长度不够");
			if (!StringUtils.equals(newPwd, reNewPwd))
				return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败：两次输入的密码不同");
			try {
				securityService.updateUserPassword(usr.getId(), oldpwd, newPwd);
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
			} catch (Exception e) {
				e.printStackTrace();
				return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败："+e.getMessage());
			}
		}
		return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "修改失败");
	}
}
