package hg.demo.web.controller;


import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.common.spi.AuthPermSPI;
import hg.demo.member.common.spi.DepartmentSPI;
import hg.demo.member.common.spi.ParameterSPI;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.qo.AuthPermSQO;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import hg.demo.member.common.spi.qo.ParameterSQO;
import hg.demo.web.common.UserInfo;
import hg.demo.web.component.cache.CacheManager;
import hg.framework.common.model.Pagination;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhurz
 */
@Controller
public class MemberController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private DepartmentSPI departmentService;
	@Autowired
	private CacheManager cacheManager;
	@Autowired
	private ParameterSPI parameterService;
	@Resource
	private AuthPermSPI authPermSPIService;

	@SuppressWarnings("unchecked")
	@RequestMapping({"/member", ""})
	public RedirectView index(@ModelAttribute("sqo") DepartmentSQO sqo,
						@RequestParam(value = "useCache", required = false) String useCache,
						Model model) {
		/*logger.info("测试记录日志->sqo->" + sqo.toJSONString());
		long a = System.currentTimeMillis();
		Pagination<Department> pagination = null;
		if (StringUtils.isNotBlank(useCache)) {
			pagination = cacheManager.getCache(sqo, Pagination.class);
		}
		if (pagination == null) {
			pagination = departmentService.queryDepartmentPagination(sqo);
			if (StringUtils.isNotBlank(useCache)) {
				cacheManager.cache(sqo, pagination);
			}
		}
		model.addAttribute("pagination", pagination);
		long time = System.currentTimeMillis() - a;
		model.addAttribute("time", time);
		model.addAttribute("useCache", useCache);*/
		return new RedirectView("/index",true);
	}

	@RequestMapping("/member/create")
	public String create(@ModelAttribute CreateDepartmentCommand command) throws UnsupportedEncodingException {

		Department department = departmentService.create(command);
//		System.out.println(department.getId());
		return "redirect:/?name=" + URLEncoder.encode(department.getName(), "utf-8")
				+ "&fromPlatform=" + URLEncoder.encode(command.getFromPlatform(), "utf-8");
	}
	

	@RequestMapping("/index")
	public String index(Model model,HttpSession httpSession) throws UnsupportedEncodingException {
		ParameterSQO qo = new ParameterSQO();
		//查询商城logo
		qo.setName("logoUrl");
		Parameter parameter = parameterService.queryParameter(qo);
		if (parameter != null) {
			model.addAttribute("logoUrl", parameter.getValue());
		}
		//查询用户皮肤
		UserInfo user = getUserInfo(httpSession);
		String skin = user.getLoginName()+"skinUrl";
		qo = new ParameterSQO();
		qo.setName(skin);
		parameter = parameterService.queryParameter(qo);
		if (parameter != null) {
			model.addAttribute("skin", parameter.getName());
			model.addAttribute("skinUrl", parameter.getValue());
		}
		model.addAttribute("userinfo",getUserInfo(httpSession));
		AuthPermSQO authPermSQO = new AuthPermSQO();
		authPermSQO.setPermType((short) 0);
		List<AuthPerm> perms = authPermSPIService.queryAuthPerms(authPermSQO);
		List<AuthPerm> sidePerms = new ArrayList<AuthPerm>();
		for (AuthPerm authPerm : perms) {
			if (StringUtils.equals(authPerm.getUrl(), "/side/1")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> logPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("logPerms", logPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/2")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> sysPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("sysPerms", sysPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/3")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("authPerms", authPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/4")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("orderList", authPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/5")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("distributorList", authPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/6")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("sysMangerList", authPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/7")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("config", authPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/8")){
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("reportList", authPerms);
			}
			
		}
		model.addAttribute("sidePerms", sidePerms);
		return "/member/index.ftl";
	}
	/**
	 * @Title: side 
	 * @author guok
	 * @Description: 获取菜单
	 * @Time 2016年6月1日上午11:08:45
	 * @param request
	 * @param model
	 * @param side
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/side/{side}")
	public String side(HttpServletRequest request, Model model, @PathVariable int side) {
		AuthPermSQO authPermSQO = new AuthPermSQO();
		authPermSQO.setPermType((short) 0);
		List<AuthPerm> perms = authPermSPIService.queryAuthPerms(authPermSQO);
		List<AuthPerm> sidePerms = new ArrayList<AuthPerm>();
		for (AuthPerm authPerm : perms) {
			if (StringUtils.equals(authPerm.getUrl(), "/side/1")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> logPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("logPerms", logPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/2")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> sysPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("sysPerms", sysPerms);
			}else if (StringUtils.equals(authPerm.getUrl(), "/side/3")) {
				sidePerms.add(authPerm);
				AuthPermSQO sqo = new AuthPermSQO();
				sqo.setParentId(authPerm.getId());
				List<AuthPerm> authPerms = authPermSPIService.queryAuthPerms(sqo);
				model.addAttribute("authPerms", authPerms);
			}
		}
		switch (side) {
		case 0:
			return "/public/side/default.html";
		case 1:
			return "/public/side/logs.ftl";
		case 2:
			return "/public/side/system.ftl";
		case 3:
			return "/public/side/auth.ftl";
		default:
			return "/error/error.jsp";
		}
		
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
