package hg.demo.web.controller.rebate;

import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.rebate.AduitRebateSetCommand;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.domain.rebate.RebateSetDto;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.qo.RebateSetSQO;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class RebateController extends BaseController{
	
	@Autowired
	private RebateSetSPI rebateSetSPIService;
	
	
	@RequestMapping("/rebate/checklist")
	public ModelAndView checkList(@RequestParam(value="numPerPage", required = false) Integer pageSize,
			  					  @RequestParam(value="pageNum", required = false) Integer pageNo){
		
		ModelAndView mav = new ModelAndView("/rebate/checkList.html");
		RebateSetSQO sqo = new RebateSetSQO();
		
		if (pageSize == null)
			pageSize = 20;
		
		if (pageNo == null)
			pageNo = 1;
		
		// 分页设置
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNo);
		sqo.getLimit().setPageSize(pageSize);
		sqo.setRunningSetId("-");// 查询列表时排除默认初始记录
				
		
		Pagination<RebateSet> pagination = rebateSetSPIService.queryAduitPagination(sqo);
		
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	
	/**
	 * 审核通过
	 * */
	@ResponseBody
	@RequestMapping("/rebate/checkpass")
	public String checkPass(@RequestParam(value="id", required =false)String id,
			                HttpServletRequest request){
		
		JSONObject o = new JSONObject();
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rs = rebateSetSPIService.queryUnique(sqo);
		
		if (rs==null){
			o.put("statusCode", 300);
			o.put("message", "请选择要修改的记录");
			return o.toString();
		}
		
		try {
			AduitRebateSetCommand command = new AduitRebateSetCommand();
			command.setId(id);
			command.setProductId(rs.getProduct().getId());
			command.setDistributorId(rs.getDistributor().getId());
			command.setCheckUser(getAuthUser(request.getSession()));
			command.setCheckUserName(getAuthUser(request.getSession()).getDisplayName());
			
			rebateSetSPIService.aduitRebateSet(command, true);
		} catch (Exception e) {
			
			o.put("statusCode", 300);
			o.put("message", "审核失败,稍后请尝试");
			return o.toString();
		}
		
		o.put("statusCode", 200);
		o.put("message", "操作成功");
		return o.toString();
	}
	
	
	@ResponseBody
	@RequestMapping("/rebate/checkrefuse")
	public String checkRefuse(@RequestParam(value="id", required =false)String id,
							  HttpServletRequest request){
		
		JSONObject o = new JSONObject();
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rs = rebateSetSPIService.queryUnique(sqo);
		
		if (rs==null){
			o.put("statusCode", 300);
			o.put("message", "请选择要修改的记录");
			return o.toString();
		}
		
		try {
			AduitRebateSetCommand command = new AduitRebateSetCommand();
			command.setId(id);
			command.setProductId(rs.getProduct().getId());
			command.setDistributorId(rs.getDistributor().getId());
			command.setCheckUser(getAuthUser(request.getSession()));
			command.setCheckUserName(getAuthUser(request.getSession()).getDisplayName());
			
			rebateSetSPIService.aduitRebateSet(command, false);
		} catch (Exception e) {
			
			o.put("statusCode", 300);
			o.put("message", "审核失败,稍后请尝试");
			return o.toString();
		}
		
		o.put("statusCode", 200);
		o.put("message", "操作成功");
		return o.toString();
	}
	/**
	 * 查看修改详细
	 * @author admin
	 * @since hgfx-admin-web
	 * @date 2016-7-26 下午8:47:18 
	 * @param id
	 * @param request
	 * @return
	 */
	@RequestMapping("/rebate/info")
	public String info(@RequestParam(value="id", required =false)String id,
							  HttpServletRequest request,
							  Model model){
		
		RebateSetSQO sqo = new RebateSetSQO();
		sqo.setId(id);
		RebateSet rs = rebateSetSPIService.queryUnique(sqo);
		sqo.setId(rs.getRunningSetId());
		RebateSet rsBefore = rebateSetSPIService.queryUnique(sqo);
		model.addAttribute("running", new RebateSetDto(rsBefore));
		model.addAttribute("modify", new RebateSetDto(rs));
		return "/rebate/modifySetInfo.html";
	}
	
}
