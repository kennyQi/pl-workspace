package hg.jxc.admin.controller.index;

import hg.jxc.admin.common.PermUtil;
import hg.jxc.admin.common.SideUtil;
import hg.jxc.admin.controller.BaseController;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import hg.system.service.SecurityService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @类功能说明：联盟后台
 * @类修改者：
 * @修改日期：2014-9-28上午10:50:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-9-28上午10:50:53
 */
@Controller
public class IndexController extends BaseController {
	@Autowired
	SecurityService securityService;

	@RequestMapping(value = "")
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/login").forward(request, response);
	}

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request,HttpServletResponse response, Model model){
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
		AuthUser user = (AuthUser)request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		PermUtil.addPermAttr4Side(securityService, model, user);
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
			
			
		default:
			return "/error/error.html";
		}
	}
}
