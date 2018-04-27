package hgria.admin.controller.hgCommon.auth;

import hg.log.util.HgLogger;
import hgria.admin.app.pojo.command.login.LoginCommand;
import hgria.admin.app.pojo.command.login.ValidCodeCheckCommand;
import hgria.admin.app.pojo.exception.LoginException;
import hgria.admin.app.service.LoginService;
import hgria.admin.controller.BaseController;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;

@Controller
public class LoginController extends BaseController {

	/**
	 * 登陆service
	 */
	@Autowired
	private LoginService 	loginService;
	

	/**
	 * 登陆_方法
	 */
	public final static String FUN_LOGIN 		= "/login";
	
	/**
	 * 主页面_方法
	 */
	public final static String FUN_HOME 		= "/home";
	
	/**
	 * 锁定页面_方法
	 */
	public final static String FUN_LOCKED 		= "/locked";
	
	/**
	 * 登陆_页面
	 */
	public final static String PAGE_LOGIN 		= "/admin/login.html";
	
	/**
	 * 锁屏_页面
	 */
	public final static String PAGE_LOCKED 		= "/admin/locked.html";
	

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
	 * @方法功能说明：后台登陆页面
	 * @修改者名字：zzb
	 * @修改时间：2014年12月1日下午1:36:34
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = FUN_LOGIN)
	public String login(HttpServletRequest request, Model model, String message) {

		model.addAttribute("message", message);
		return PAGE_LOGIN;
	}

	
	/**
	 * @方法功能说明：登陆提交
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日上午11:16:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param loginName
	 * @参数：@param password
	 * @参数：@param validcode
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping(value = "/login/check")
	public RedirectView loginCheck(
			HttpServletRequest request, HttpServletResponse response,
			Model model, LoginCommand command)
			throws IOException {
		
		try {
			loginService.login(command, request);
		} catch (LoginException e) {
			HgLogger.getInstance().error("zzb", "登陆失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			
			model.addAttribute("message", e.getMessage());
			return new RedirectView(FUN_LOGIN, true);
		}
		return new RedirectView(FUN_HOME, true);
	}

	
	/**
	 * @方法功能说明：登出
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日上午10:34:24
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
		response.sendRedirect(request.getContextPath() + FUN_LOGIN);
	}
	
	
	/**
	 * @方法功能说明：锁屏页面
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日上午10:39:35
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = FUN_LOCKED)
	public String locked(HttpServletRequest request, Model model, String message) {
		
		model.addAttribute("message", message);
		return PAGE_LOCKED;
	}
	
	
	/**
	 * @方法功能说明：解除锁定
	 * @修改者名字：zzb
	 * @修改时间：2014年11月24日下午12:22:28
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param loginName
	 * @参数：@param password
	 * @参数：@return
	 * @参数：@throws IOException
	 * @return:RedirectView
	 * @throws
	 */
	@RequestMapping(value = "/lockedLogin")
	public RedirectView lockedLogin(
			HttpServletRequest request,
			HttpServletResponse response,
			Model model, LoginCommand command)
			throws IOException {

		try {
			command.setCheckValidcode(false);
			loginService.login(command, request);
		} catch (LoginException e) {
			HgLogger.getInstance().error("zzb", "登陆失败:command【" + JSON.toJSONString(command, true) + "】");
			e.printStackTrace();
			
			model.addAttribute("message", e.getMessage());
			return new RedirectView(PAGE_LOCKED, true);
		}
		return new RedirectView(FUN_HOME, true);
	}
	
}
