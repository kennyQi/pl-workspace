

package hg.jxc.admin.controller.log;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.PermUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.qo.JxcLogQo;
import hg.system.model.auth.AuthRole;
import hg.system.service.SecurityService;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxc.app.service.system.JxcLogService;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/log")
public class LogController extends BaseController {
	@Autowired
	JxcLogService logService;
	
	
	@Autowired
	SecurityService securityService;
	@RequestMapping("/list")
	public String queryLogList(Model model, @ModelAttribute JxcLogQo qo,@ModelAttribute DwzPaginQo dwzPaginQo,HttpServletRequest request) {
		qo.setCreateDateDesc(true);
		String createDateBegin = request.getParameter("createDateBegin");
		String createDateEnd = request.getParameter("createDateEnd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if(StringUtils.isNotBlank(createDateBegin)){
				qo.setCreateDateBegin(sdf.parse(createDateBegin));
			}
			if(StringUtils.isNotBlank(createDateEnd)){
				qo.setCreateDateEnd(sdf.parse(createDateEnd));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Pagination pagination = createPagination(dwzPaginQo);
		pagination.setCondition(qo);
		pagination = logService.queryPagination(pagination);
		model.addAttribute("pagination",pagination);
		List<AuthRole> roles = securityService.findAllRoles();
		model.addAttribute("roles",roles);
		PermUtil.addPermAttr4List(securityService, model, getAuthUser());
		return "log/log_list.html";
	}


	@RequestMapping("/export")
	public void exportLog(JxcLogQo qo,HttpServletResponse response,HttpServletRequest request) {
		qo.setCreateDateDesc(true);
		String createDateBegin = request.getParameter("createDateBegin");
		String createDateEnd = request.getParameter("createDateEnd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			if(StringUtils.isNotBlank(createDateBegin)){
				qo.setCreateDateBegin(sdf.parse(createDateBegin));
			}
			if(StringUtils.isNotBlank(createDateEnd)){
				qo.setCreateDateEnd(sdf.parse(createDateEnd));
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		try {
			outputExcel(logService.exportJxcLog(qo), response);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}

