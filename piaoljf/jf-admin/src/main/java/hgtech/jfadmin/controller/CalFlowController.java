/**
 * @文件名称：TemplateController.java
 * @类路径：hgtech.jfaddmin.controller
 * @描述：规则模版管理
 * @作者：xinglj
 * @时间：2014年10月13日下午1:25:08
 */
package hgtech.jfadmin.controller;



import java.text.SimpleDateFormat;
import java.util.Date;

import hg.common.page.Pagination;
import hgtech.jf.piaol.trade.PiaolTrade;
import hgtech.jfadmin.dto.CalLogDto;
import hgtech.jfadmin.hibernate.CalFlowHiberEntity;
import hgtech.jfadmin.service.CalFlowService;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：计算日至管理
 * @类修改者：
 * @修改日期：2014年10月31日下午1:25:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xiaoying
 * @创建时间：2014年10月13日下午1:25:08
 * 
 */
@Controller
@RequestMapping(value = "/cal")
public class CalFlowController extends BaseController {

	public static final String navTabId = "calList";
	public static final String rel = "jbsxBoxRule";

	@Autowired
	CalFlowService calFlowService;

	/**
	 * 积分日志流水列表
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 */
  @RequestMapping(value = "/list")
  public String list(HttpServletRequest request, Model model,
			@ModelAttribute CalLogDto dto)
  {
		Pagination paging = dto.getPagination();
		if (null == dto.getCalTime()  ||  null == dto.getNowTime()){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			dto.setCalTime(sdf.format(new Date()));
			dto.setNowTime(sdf.format(new Date()));
		}
		
		if (null != dto.getCalTime()  && null != dto.getIn_userCode()  &&  null != dto.getNowTime())
		{
		paging.setCondition(dto);
		paging = calFlowService.findPagination(paging);
		CalFlowHiberEntity  cfe = null;
		//获得交易流水对象 的待积流水
		for (int i =0; i<paging.getList().size(); i++)
		{
		  cfe = (CalFlowHiberEntity) paging.getList().get(i);
		  String flowJson =  cfe.getIn_tradeFlowJson();
		  if(!("".equals(flowJson) || null == flowJson))
		  {  
			  PiaolTrade pt  = JSONObject.parseObject(cfe.getIn_tradeFlowJson(), new  PiaolTrade().getClass());
			  //设置积分流水
			  cfe.setFlowText(pt.toString());
			  cfe.setDetail_resultText(pt.showSelf());
		  }
		 }
	}
	    model.addAttribute("pagination", paging);
	    model.addAttribute("dto",dto);
	
		return "/calflow/calFlowList.html";
  }
	/**
	 * 积分交易日志流水列表
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 */
@RequestMapping(value = "/listTrade")
public String listTrade(HttpServletRequest request, Model model,
			@ModelAttribute CalLogDto dto)
{
		Pagination paging = dto.getPagination();
		if (null == dto.getCalTime()  ||  null == dto.getNowTime()){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			dto.setCalTime(sdf.format(new Date()));
			dto.setNowTime(sdf.format(new Date()));
		}
		
		if (null != dto.getCalTime()  && null != dto.getIn_userCode()  &&  null != dto.getNowTime())
		{
		paging.setCondition(dto);
		paging = calFlowService.findTradeFlowPagination( paging);
		CalFlowHiberEntity  cfe = null;
		//获得交易流水对象 的待积流水
		for (int i =0; i<paging.getList().size(); i++)
		{
		  cfe = (CalFlowHiberEntity) paging.getList().get(i);
		  String flowJson =  cfe.getIn_tradeFlowJson();
		  if(!("".equals(flowJson) || null == flowJson))
		  {  
			  //设置积分流水
			  cfe.setFlowText(flowJson);
		  }
		 }
	}
	    model.addAttribute("pagination", paging);
	    model.addAttribute("dto",dto);
	
		return "/calflow/tradeFlowList.html";
}
  
  
}
