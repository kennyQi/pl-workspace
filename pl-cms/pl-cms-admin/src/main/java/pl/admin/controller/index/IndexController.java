package pl.admin.controller.index;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.admin.common.SideUtil;
import pl.admin.controller.BaseController;
/**
 * 
 * 
 * @author yuxx
 */
@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = "/home")
	public String index(HttpServletRequest request, Model model) {
		System.out.println(System.currentTimeMillis());
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		System.out.println("dddddddd"+currentUser.getSession().getTimeout());
		System.out.println("sssssddd"+request.getSession().getMaxInactiveInterval());
		if (user != null) {
			return "/home.html";
		} else {
			return "/admin/login.html";
		}
	}

	/**
	 * 获取菜单
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/side/{side}")
	public String side(HttpServletRequest request, Model model,
			@PathVariable int side) {
		switch (side) {
		case SideUtil.SIDE_ALL:
			return "/public/side/default.html";
		case SideUtil.SIDE_SYSTEM:
			return "/public/side/system.html";
		default:
			return "/error/error.html";
		}

	}

}
