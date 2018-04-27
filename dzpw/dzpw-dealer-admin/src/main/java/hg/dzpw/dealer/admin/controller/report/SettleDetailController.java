package hg.dzpw.dealer.admin.controller.report;

import hg.common.page.Pagination;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 此类描述的是： 结算明细控制器，景区端结算管理
 * @author: guotx 
 * @version: 2015-11-27 下午2:20:47
 */
@Controller
@RequestMapping(value="/report/settleDetail")
public class SettleDetailController {
	
	@RequestMapping(value="/list")
	public String list(HttpServletRequest request,
			HttpServletResponse response,Model model,
			@RequestParam(value="pageNum", required = false)Integer pageNum,
            @RequestParam(value="numPerPage", required = false)Integer pageSize){
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNum);
		pagination.setPageSize(pageSize);
		model.addAttribute("pagination", pagination);
		return "report/settleDetail/settleDetail_list.html";
				
	}
}
