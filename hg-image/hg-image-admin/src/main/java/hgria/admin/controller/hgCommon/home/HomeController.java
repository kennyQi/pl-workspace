package hgria.admin.controller.hgCommon.home;

import hgria.admin.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @类功能说明：主页控制器
 * @类修改者：zzb
 * @修改日期：2014年10月15日下午2:54:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月15日下午2:54:53
 *
 */
@Controller("hgHomeController")
@RequestMapping(value="hg/home")
public class HomeController extends BaseController {
	
	
	/**
	 * 主页面
	 */
	public final static String PAGE_VIEW = "/content/home.html";
	
	@RequestMapping(value="/view")
	public String view(HttpServletRequest request, HttpServletResponse response, Model model) {
		return PAGE_VIEW;
	}
	
	
}
