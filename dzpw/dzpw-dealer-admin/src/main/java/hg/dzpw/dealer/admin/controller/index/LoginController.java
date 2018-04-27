package hg.dzpw.dealer.admin.controller.index;

import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.app.pojo.vo.DealerSessionUserVo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;
import hg.dzpw.dealer.admin.component.manager.MerchantSessionUserManager;
import hg.dzpw.dealer.admin.controller.BaseController;
import hg.dzpw.pojo.command.merchant.dealer.DealerLoginCommand;
import hg.dzpw.pojo.command.merchant.dealer.ModifyLoginDealerPasswordCommand;
import hg.system.common.system.SecurityConstants;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.google.code.kaptcha.Producer;

/**
 * @类功能说明：登录控制
 * @类修改者：
 * @修改日期：2015-2-11上午10:28:35
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:28:35
 */
@Controller("MerchantLoginController")
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	/** 登录页 */
	private static final String LOGIN_PAGE = "/admin/login.html";

	private static final String MERCHANT_SESSION_VALID_CODE_KEY = "_MERCHANT_SESSION_VALID_CODE_";
	
	@Autowired
	private DealerLocalService dealerLocalService;
	
	@Autowired
	private Producer captchaProducer;

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
		if (request.getSession().getAttribute(MERCHANT_SESSION_VALID_CODE_KEY) != null)
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
		
		String sessionValidCode = (String) request.getSession().getAttribute(MERCHANT_SESSION_VALID_CODE_KEY);
		request.getSession().removeAttribute(MERCHANT_SESSION_VALID_CODE_KEY);
		
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
	@RequestMapping(value = "/valid-code-image.jpg")
	public void getKaptchaImage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		response.setDateHeader("Expires", 0);
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		response.addHeader("Cache-Control", "post-check=0, pre-check=0");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		// 生成验证码
		String capText = captchaProducer.createText();
		session.setAttribute(MERCHANT_SESSION_VALID_CODE_KEY, capText);
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
	 * @方法功能说明：登录页，当用户已经登录时直接重定向到HOME页
	 * @修改者名字：zhurz
	 * @修改时间：2014-9-23上午10:30:19
	 * @修改内容：
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value = "")
	public Object index(Model model, 
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(required = false, value = "error") String error) {

		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		
		// 已经登录
		if (MerchantSessionUserManager.isLogin(request))
			return new RedirectView(request.getContextPath() + "/home");
		
		// 检查错误代码
		String message = "";
		if ("-1".equals(error)) {
			message = "验证码错误";
		} else if ("-2".equals(error)) {
			message = "用户名或密码错误";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_UNKNOWN_LOGIN_NAME.equals(error)) {
			message = "用户名不存在";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_PASSWORD.equals(error)) {
			message = "用户名或密码错误";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_LOCK.equals(error)) {
			message = "用户被禁用或删除";
		} else if (SecurityConstants.LOGIN_CHECK_ERROR_NULL.equals(error)) {
			message = "用户异常";
		} else if (StringUtils.isNotBlank(error)) {
			message = "未知异常";
		}
		
		String msg = (String)request.getSession().getAttribute("msg");
		if(StringUtils.isNotBlank(msg)){
			message = msg;
		}
		
		model.addAttribute("message", message);
		
		return LOGIN_PAGE;
	}

	/**
	 * @方法功能说明：登录检查
	 * @修改者名字：guotx
	 * @修改时间：2015-12-4下午5：23：33
	 * @修改内容：
	 * @参数：@param model
	 * @参数：@param request
	 * @参数：@param command
	 * @参数：@return
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping(value = "/check")
	public RedirectView submit(Model model, 
			HttpServletRequest request, 
			@ModelAttribute DealerLoginCommand command) {
		
		if (StringUtils.isBlank(command.getLoginName()) || StringUtils.isBlank(command.getPassword())) {
			model.addAttribute("error", "-2");
			return new RedirectView("/login", true);
		}

		// 验证码检查
		if(!validCodeCheck(request, command.getValidcode())){
			model.addAttribute("error", "-1");
			return new RedirectView("");
		}

		try {
			DealerSessionUserVo vo = dealerLocalService.loginCheck(command);
			DealerSessionUserManager.login(request, vo);
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
		
		return new RedirectView("/home", true);
	}

	/**
	 * @方法功能说明：登出
	 * @修改者名字：zhurz
	 * @修改时间：2015-2-11上午10:26:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:Object
	 * @throws
	 */
	@RequestMapping(value = "/out")
	public Object out(HttpServletRequest request) {
		DealerSessionUserManager.logout(request);
		return new RedirectView(request.getContextPath() + "/login");
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
	 * @修改者名字：guotx
	 * @修改时间：2015-12-7下午3:35:16
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/change-password")
	public String changePassword(
			HttpServletRequest request,
			@ModelAttribute ModifyLoginDealerPasswordCommand command) {
		try {
			command.setDealerId(DealerSessionUserManager.getSessionUserId(request));
			dealerLocalService.modifyLoginDealerPassword(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, null);
		} catch (Exception e) {
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败："+e.getMessage());
		}
	}
}
