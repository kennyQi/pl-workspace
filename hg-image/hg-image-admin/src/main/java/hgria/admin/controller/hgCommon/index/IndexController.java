package hgria.admin.controller.hgCommon.index;

import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import hgria.admin.controller.BaseController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		AuthUser user = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		if(user!=null){
			return "/home.html";
		}else{
			return "/admin/login.html";
		}
	}
	
}
