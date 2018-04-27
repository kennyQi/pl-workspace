package hg.demo.web.controller.log;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import logUtil.LogConstants;
import logUtil.logUtil;
import hg.demo.member.common.spi.qo.log.LogSQO;
import hg.framework.common.model.Pagination;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mongodb.DBObject;

@Controller
public class LogController {



	@RequestMapping({ "/getLogList" })
	public String index(HttpServletRequest request,@ModelAttribute LogSQO sqo, Model model,@RequestParam(value="numPerPage", required = false) Integer pageSize,
			@RequestParam(value="pageNum", required = false) Integer pageNo) {
		if (pageSize == null) {
			pageSize = 20;
		}
		if (pageNo == null) {
			pageNo = 1;
		}
		List<DBObject> list = logUtil.getLog(null,sqo.getUserName(),
				sqo.getUpdate(), pageNo,pageSize);
		
		Pagination<DBObject> pagination = new Pagination<DBObject>();
		pagination.setList(list);
		pagination.setTotalCount(logUtil.getLogCount(null, sqo.getUserName(), sqo.getUpdate()));
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		model.addAttribute("sqo", sqo);
		model.addAttribute("pagination", pagination);
		model.addAttribute("updateList", LogConstants.LOG_LIST);

		return "/log/logList.ftl";
	}
}
