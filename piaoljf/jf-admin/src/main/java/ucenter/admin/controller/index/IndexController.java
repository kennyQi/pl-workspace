package ucenter.admin.controller.index;

import ucenter.admin.common.SideUtil;
import ucenter.admin.controller.BaseController;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 
 * 
 * @author yuxx
 */
@Controller
public class IndexController extends BaseController {

	@RequestMapping(value = "/home")
	public String index(HttpServletRequest request,Model model){
		AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			return "/home.html";
		}else{
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
		case SideUtil.SIDE_HOTEL:
			return "/public/side/hotel.html";
		case SideUtil.SIDE_AIRLINE:
			return "/public/side/airline.html";
		case SideUtil.SIDE_VIEWSPOT:
			return "/public/side/viewspot.html";
		case SideUtil.SIDE_LINE:
			return "/public/side/line.html";
		default:
			return "/error/error.html";
		}
		
	}
}
