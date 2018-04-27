package hg.demo.web.controller;

import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.spi.DepartmentSPI;
import hg.demo.member.common.spi.command.department.CreateDepartmentCommand;
import hg.demo.member.common.spi.qo.DepartmentSQO;
import hg.demo.web.component.cache.CacheManager;
import hg.framework.common.model.Pagination;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author zhurz
 */
@Controller
public class MemberController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private DepartmentSPI departmentService;
	@Autowired
	private CacheManager cacheManager;

	@SuppressWarnings("unchecked")
	@RequestMapping({"/member", ""})
	public String index(@ModelAttribute("sqo") DepartmentSQO sqo,
						@RequestParam(value = "useCache", required = false) String useCache,
						Model model) {
		logger.info("测试记录日志->sqo->" + sqo.toJSONString());
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
		model.addAttribute("useCache", useCache);
		return "/member/index.ftl";
	}

	@RequestMapping("/member/create")
	public String create(@ModelAttribute CreateDepartmentCommand command) throws UnsupportedEncodingException {

		Department department = departmentService.create(command);
		System.out.println(department.getId());
		return "redirect:/?name=" + URLEncoder.encode(department.getName(), "utf-8")
				+ "&fromPlatform=" + URLEncoder.encode(command.getFromPlatform(), "utf-8");
	}
	



}
