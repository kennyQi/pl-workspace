package hg.demo.web.controller.operationLog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import hg.demo.member.common.domain.model.Staff;
import hg.demo.member.common.spi.StaffSPI;
import hg.demo.member.common.spi.qo.StaffSQO;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.abnormalRule.ModifyAbnormalRuleCommand;
import hg.fx.domain.AbnormalRule;
import hg.fx.domain.OperationLog;
import hg.fx.spi.AbnormalRuleSPI;
import hg.fx.spi.OperationLogSPI;
import hg.fx.spi.qo.AbnormalRuleSQO;
import hg.fx.spi.qo.OperationLogSQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class OperationLogController {
	
	@Autowired
	private OperationLogSPI operationLogService;
	
	@Autowired
	private StaffSPI staffService;
	
	@Autowired
	private AbnormalRuleSPI abnormalService;
	
	private SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * 查询系统操作日志
	 * @auth   yangkang
	 * @date   2016-6-2上午10:49:46
	 * @param  pageSize
	 * @param  pageNo
	 * @param  sqo
	 * @return
	 * @return:ModelAndView
	 */
	@RequestMapping("/operationlog/list")
	public ModelAndView opLogList(@RequestParam(value="numPerPage", required = false) Integer pageSize,
								  @RequestParam(value="pageNum", required = false) Integer pageNo,
								  @ModelAttribute OperationLogSQO sqo){
		
		ModelAndView mav = new ModelAndView("/operationLog/oper_log_list.html");
		
		if (pageSize == null)
			pageSize = 20;
		
		if (pageNo == null)
			pageNo = 1;
		
		// 分页设置
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNo);
		sqo.getLimit().setPageSize(pageSize);
		
		Pagination<OperationLog> pagination = this.operationLogService.queryPagination(sqo);
		mav.addObject("pagination", pagination);
		
		// 查询系统操作员列表
		List<Staff> staffList = staffService.queryList(new StaffSQO());
		mav.addObject("staffList", staffList);
		mav.addObject("sqo", sqo);
		mav.addObject("startDate", sqo.getStartDate()!=null?sd.format(sqo.getStartDate()).substring(0,10) : "");
		mav.addObject("endDate",   sqo.getEndDate()!=null?sd.format(sqo.getEndDate()).substring(0,10) : "");
		return mav;
	}
	
	
	
	
}
