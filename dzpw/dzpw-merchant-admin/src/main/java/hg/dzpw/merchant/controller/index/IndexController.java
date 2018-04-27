package hg.dzpw.merchant.controller.index;

import hg.dzpw.merchant.common.utils.SideUtil;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @类功能说明：商户后台
 * @类修改者：
 * @修改日期：2015-2-11上午10:56:07
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-2-11上午10:56:07
 */
@Controller
public class IndexController extends BaseController {
	
	@RequestMapping(value = "")
	public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/login").forward(request, response);
	}

	@RequestMapping(value = "/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) {
		response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

		if (MerchantSessionUserManager.isLogin(request))
			return "/home.html";
		else
			return "/admin/login.html";
		
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
		default:
			return "/error/error.html";
		}
	}
}
