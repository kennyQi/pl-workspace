package hgfx.web.controller.sys;


import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.spi.DepartmentSPI;
import hg.demo.member.common.spi.ParameterSPI;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import hg.framework.common.model.Pagination;
import hg.fx.domain.DistributorUser;
import hgfx.web.component.cache.CacheManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @author zhurz
 */
@Controller
public class MemberController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private DepartmentSPI departmentService;
/*	@Autowired
	private CacheManager cacheManager;*/
	@Autowired
	private ParameterSPI parameterService;

	@SuppressWarnings("unchecked")
	@RequestMapping({"/member", ""})
	public String index(@ModelAttribute("sqo") DepartmentSQO sqo,
						@RequestParam(value = "useCache", required = false) String useCache,
						Model model) {
//		logger.info("测试记录日志->sqo->" + sqo.toJSONString());
//		long a = System.currentTimeMillis();
//		Pagination<Department> pagination = null;
//		if (StringUtils.isNotBlank(useCache)) {
////			pagination = cacheManager.getCache(sqo, Pagination.class);
//		}
//		if (pagination == null) {
//			pagination = departmentService.queryDepartmentPagination(sqo);
//			if (StringUtils.isNotBlank(useCache)) {
////				cacheManager.cache(sqo, pagination);
//			}
//		}
//		model.addAttribute("pagination", pagination);
//		long time = System.currentTimeMillis() - a;
//		model.addAttribute("time", time);
//		model.addAttribute("useCache", useCache);
//		return "/member/member.ftl";
//		return "/campaign/campaign.html";
		return "/campaign/campaign2.html";
	}

	@RequestMapping("/member/create")
	public String create(@ModelAttribute CreateDepartmentCommand command) throws UnsupportedEncodingException {

		Department department = departmentService.create(command);
//		System.out.println(department.getId());
		return "redirect:/?name=" + URLEncoder.encode(department.getName(), "utf-8")
				+ "&fromPlatform=" + URLEncoder.encode(command.getFromPlatform(), "utf-8");
	}
	
	/*@RequestMapping("/index")
	public String index(Model model,HttpSession httpSession) throws UnsupportedEncodingException {
		return "order-manage-history.html";
	}*/
	/**
	 * 登录后的商户端主页面
	 * @author Caihuan
	 * @date   2016年6月6日
	 */
	@RequestMapping("/index")
	public String index(Model model,HttpSession httpSession) throws UnsupportedEncodingException {
		return "/home/index.html";
//		return new RedirectView("//importHistoryList", true);
	}

	/**
	 * 无权限普通请求
	 * @return
     */
	@RequestMapping(value = "/noauthorized")
	public String noauthorized(){
		return "/auth/403.ftl";
	}

	/**
	 * 无权限ajax请求
	 * @return
     */
	@ResponseBody
	@RequestMapping(value = "/noauthorized/ajax")
	public Map<String,Object> noauthorizedajax(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("statusCode", 300);
		map.put("message", "对不起，您没有该功能的操作权限!");
		return map;
	}

}
