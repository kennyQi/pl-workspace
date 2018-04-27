package hg.demo.web.controller.fixedPrice;

import javax.servlet.http.HttpServletRequest;

import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.fx.command.rebate.AduitRebateSetCommand;
import hg.fx.domain.OperationLog;
import hg.fx.domain.fixedprice.FixedPriceSet;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.spi.FixedPriceSetSPI;
import hg.fx.spi.qo.FixedPriceSetSQO;
import hg.fx.spi.qo.RebateSetSQO;

import org.jboss.logging.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

@Controller
public class FixedPriceController extends BaseController {
	
	@Autowired
	private FixedPriceSetSPI fixedPriceSetSPI;
	
	
	@RequestMapping("/fixedprice/checklist")
	public ModelAndView checkList(@RequestParam(value="numPerPage", required = false) Integer pageSize,
			 					  @RequestParam(value="pageNum", required = false) Integer pageNo){
		
		ModelAndView mav = new ModelAndView("/fixedPrice/checkList.html");
		FixedPriceSetSQO sqo = new FixedPriceSetSQO();
		
		if (pageSize == null)
			pageSize = 20;
		
		if (pageNo == null)
			pageNo = 1;
		
		// 分页设置
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNo);
		sqo.getLimit().setPageSize(pageSize);
		Pagination<FixedPriceSet> pagination = fixedPriceSetSPI.queryPagination(sqo);
		
		mav.addObject("pagination", pagination);
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping("/fixedprice/checkpass")
	public String checkPass(@RequestParam(value="id", required =false)String id,
							HttpServletRequest request){
		
		JSONObject o = new JSONObject();
		FixedPriceSet set = fixedPriceSetSPI.queryByID(id);
		
		if (set==null){
			o.put("statusCode", 300);
			o.put("message", "请选择要修改的记录");
			return o.toString();
		}
		
		try {
			fixedPriceSetSPI.shenhe(id, getAuthUser(request.getSession()).getId(), true);
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
	@RequestMapping("/fixedprice/checkrefuse")
	public String checkRefuse(@RequestParam(value="id", required =false)String id,
							  HttpServletRequest request){
		
		JSONObject o = new JSONObject();
		FixedPriceSet set = fixedPriceSetSPI.queryByID(id);
		
		if (set==null){
			o.put("statusCode", 300);
			o.put("message", "请选择要修改的记录");
			return o.toString();
		}
		
		try {
			fixedPriceSetSPI.shenhe(id, getAuthUser(request.getSession()).getId(), false);
		} catch (Exception e) {
			
			o.put("statusCode", 300);
			o.put("message", "审核失败,稍后请尝试");
			return o.toString();
		}
		
		o.put("statusCode", 200);
		o.put("message", "操作成功");
		return o.toString();
	}
	
}
