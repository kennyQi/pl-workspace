/**
 * @文件名称：TemplateController.java
 * @类路径：hgtech.jfaddmin.controller
 * @描述：规则模版管理
 * @作者：xinglj
 * @时间：2014年10月13日下午1:25:08
 */
package hgtech.jfadmin.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hgtech.jf.piaol.SetupSpiApplicationContext;
import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.SetupAccountContext;
import hgtech.jfadmin.dto.RuleDto;
import hgtech.jfadmin.hibernate.RuleHiberEntity;
import hgtech.jfadmin.service.JfCalService;
import hgtech.jfadmin.service.RuleService;
import hgtech.jfadmin.service.TemplateService;
import hgtech.jfcal.model.RuleTemplate;
import hgtech.jfcal.rulelogic.FindParameters;
import hgtech.jfcal.rulelogic.FindParameters.Parameters;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：rule管理
 * @类修改者：
 * @修改日期：2014年10月13日下午1:25:08
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间：2014年10月13日下午1:25:08
 * 
 */
@Controller
@RequestMapping(value = "/rule")
public class RuleController extends BaseController {

	public static final String navTabId = "ruleList";
	public static final String rel = "jbsxBoxRule";

	@Autowired
	RuleService ruleService;

	@Autowired
	TemplateService templateService;
	
	@Autowired
	JfCalService calService;

	/**
	 * 规则列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute RuleDto dto) {
		Pagination paging = dto.getPagination();
			paging.setCondition(dto);
		paging = this.ruleService.findPagination(paging);
		List<RuleHiberEntity> rules=(List<RuleHiberEntity>)paging.getList();
		SetupSpiApplicationContext.init();
		for(RuleHiberEntity rule:rules){
			rule.setJfAccountType(SetupSpiApplicationContext.findType(rule.accountType));
		}
		model.addAttribute("pagination", paging);
		model.addAttribute("dto", dto);
		RuleHiberEntity r = null;
		long now=new Date().getTime();
	   //判断规则是否过期
		for (int i =0; i<paging.getList().size(); i++)
		{
			 r = (RuleHiberEntity) paging.getList().get(i);
			 if(null != r )
			 {
				 //获取模板名称
				 gettemplateNameByCode(r);
				 
			     boolean startFlage =  r.getStartDate().getTime() >= now ;
			     boolean endFlage  =  r.getEndDate().getTime()+1*24*3600*1000 <= now;
			     
			     //开始时间小于当前时间  为 未到期   标识：0
			     if (true == startFlage)
			     {
			    	 r.setIsLimit("0");
			     }
			     
			     //开始时间小于当前时间，结束时间大于当前时间  为  在期内   标识：2
			     else if (false == startFlage && false == endFlage)
			     {
			    	 r.setIsLimit("2");
			     }
			     //结束时间小于当前时间 为过期  标识：1
			     else if (true == endFlage)
			     {
			    	 r.setIsLimit("1");
			     }

			 }

		}
		return "/rule/ruleList.html";
	}


	/**
	 * 跳转到添加 的页面
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/creat")
	public String creat(HttpServletRequest request,
			HttpServletResponse response, Model model,
			@ModelAttribute RuleDto dto) {
		
		
		LinkedList<WithChildren<JfAccountType>> ruleTypeList =  SetupAccountContext.topAcctType.getSubList();
		Pagination paging=dto.getPagination();
		paging=templateService.findPagination(paging);
		
		/**
		 * 模板列表集合
		 */
		model.addAttribute("templateList", paging);
		/**
		 * 积分账户类型
		 */
		model.addAttribute("ruleTypeList", ruleTypeList);
		
		return "/rule/ruleAdd.html";
	}
	

	/**
	 * 
	 * @方法功能说明： 添加规则页面
	 * @修改者名字：xy
	 * @修改时间：2014年10月22日下午4:52:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param dto
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public String save(HttpServletRequest request, Model model,@ModelAttribute RuleHiberEntity dto) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			
			String code = dto.getCode();
			RuleHiberEntity   ruleEntity = ruleService.queryByCode(code);
			if(null != ruleEntity)
			{
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "输入的规则编码已存在";
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						callbackType, navTabId, null, null, rel);
			}else
			{
				dto.setStatus(dto.STATUS_Y);
				ruleService.save(dto);
				calService.refreshCalModel();
			}

		} catch (Throwable e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "保存失败"+e.getMessage();
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}
	
	
	/**
	 * 
	 * @方法功能说明： 删除规则
	 * @修改者名字：xy
	 * @修改时间：2014年10月23日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param code
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/delete")
	public String delete(HttpServletRequest request, HttpServletResponse response, Model model,
					  @RequestParam(value="code", required=false)String code ,@ModelAttribute RuleHiberEntity dto  ){
		
		try {
			dto.setCode(code);
			ruleService.delete(dto);
			calService.refreshCalModel();
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, navTabId,null,null,rel);
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null,navTabId, null, null, rel);
		}
	}
	
	/**
	 * 
	 * @方法功能说明： 动态获取模板参数，根据模板路径
	 * @修改者名字：xy
	 * @修改时间：2014年10月24日上午10:07:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param code  规则模板code
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/getParm")
	public String getParm(HttpServletRequest request, HttpServletResponse response, Model model,
					  @RequestParam(value="code", required=false)String code ){
		try {
			//获取模板路径
			RuleTemplate rt =  templateService.get(code);
			if (null != rt)
			{
				//模板路径
				String file = rt.getSrcFile();
				InputStream stream = new FileInputStream(new File(file));
				LinkedList<Parameters> findParameters = FindParameters.findParameters(stream);
				
				return	JSONObject.toJSONString(findParameters);
			}
			return DwzJsonResultUtil.createJsonString("300", "获取参数失败!", null,navTabId, null, null, rel);

		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "获取参数失败!", null,navTabId, null, null, rel);
		}
	}
	
	/**
	 * 
	 * @方法功能说明： 作废规则
	 * @修改者名字：xy
	 * @修改时间：2014年10月23日上午10:13:33
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param code  规则编码
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/cancel") 
	public String cancel(HttpServletRequest request, HttpServletResponse response, Model model,
					  @RequestParam(value="code", required=false)String code ,@ModelAttribute RuleHiberEntity rule){
		
		try {
			rule = ruleService.queryByCode(code);
			rule.setStatus("N");
			ruleService.update(rule);
			calService.refreshCalModel();
			return DwzJsonResultUtil.createJsonString("200", "作废成功!", null, navTabId,null,null,rel);
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "作废失败!", null,navTabId, null, null, rel);
		}
	}

 
	/**
	 * 到升级规则页面
	 * @param request
	 * @param response
	 * @param model
	 * @param code 规则编码
	 * @param ruleEntity  
	 * @param dto
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/toUpgrade")
	public String toUpgrade(HttpServletRequest request, HttpServletResponse response, Model model,
			  @RequestParam(value="code", required=false)String code ,@ModelAttribute RuleHiberEntity ruleEntity, @ModelAttribute RuleDto dto) throws ParseException {
		 
		ruleEntity = ruleService.queryByCode(code);
		gettemplateNameByCode(ruleEntity);
		LinkedList<WithChildren<JfAccountType>> ruleTypeList =  SetupAccountContext.topAcctType.getSubList();
		Pagination paging=dto.getPagination();
		paging=templateService.findPagination(paging);
		
		/**
		 * 模板列表集合
		 */
		model.addAttribute("templateList", paging);
        dto.setStartDate(new Date());
		model.addAttribute("dto", dto);
		/**
		 * 积分账户类型
		 */
		model.addAttribute("ruleTypeList", ruleTypeList);
		if (null != ruleEntity)
		{
			model.addAttribute("ruleEntity", ruleEntity);
		}
		return "/rule/ruleUpgrade.html";
	}
	
	/**
	 * 升级规则
	 * @param request
	 * @param response
	 * @param model
	 * @param ex_code
	 * @param newRule
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/upgrade") 
	public String upgrade(HttpServletRequest request, HttpServletResponse response, Model model , 
			 @RequestParam(value="ex_code", required=false)String ex_code ,
			 @ModelAttribute RuleHiberEntity newRule){
		
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "升级成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			RuleHiberEntity exRule = new RuleHiberEntity();
			exRule = ruleService.queryByCode(ex_code);
			exRule.setStatus("N");
			//作废升级前规则,添加升级后的子新规则
			newRule.setStatus("Y");
			
			String code = newRule.getCode();
			RuleHiberEntity   ruleEntity = ruleService.queryByCode(code);
			if(null != ruleEntity)
			{
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "输入的规则编码已存在";
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						callbackType, navTabId, null, null, rel);
			}else
			{
				ruleService.upgrade(exRule, newRule);
				calService.refreshCalModel();
			}

		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "升级失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}
	
	/**
	 *  跳转到编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @param code 规则编码
	 * @param ruleEntity
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/toEdite")
	public String toEdite(HttpServletRequest request, HttpServletResponse response, Model model,
			  @RequestParam(value="code", required=false)String code ,@ModelAttribute RuleHiberEntity ruleEntity, @ModelAttribute RuleDto dto) {
		 
		ruleEntity = ruleService.queryByCode(code);

		
		LinkedList<WithChildren<JfAccountType>> ruleTypeList =  SetupAccountContext.topAcctType.getSubList();
		Pagination paging=dto.getPagination();
		paging=templateService.findPagination(paging);
		
		/**
		 * 模板列表集合
		 */
		model.addAttribute("templateList", paging);
		/**
		 * 积分账户类型
		 */
		model.addAttribute("ruleTypeList", ruleTypeList);
		if (null != ruleEntity)
		{
			model.addAttribute("ruleEntity", ruleEntity);
		}
		return "/rule/ruleEdite.html";
	}
	/**
	 *  规则编辑
	 * @param request
	 * @param response
	 * @param model
	 * @param code
	 * @param rule
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edite") 
	public String edite(HttpServletRequest request, HttpServletResponse response, Model model , @ModelAttribute RuleHiberEntity rule){
		
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "更新成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
 		 
 			rule.setStatus(ruleService.queryByCode(rule.getCode()).getStatus());
			ruleService.update(rule);
			calService.refreshCalModel();
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "更新失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}
	
	
	/**
	 * 查看规则
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/check")
	public String check(HttpServletRequest request,Model model,
			@RequestParam(value="code", required=false)String code ,@ModelAttribute RuleHiberEntity ruleEntity ) {
		ruleEntity = ruleService.queryByCode(code);
		SetupSpiApplicationContext.init();
		ruleEntity.setJfAccountType(SetupSpiApplicationContext.findType(ruleEntity.accountType));
		
		gettemplateNameByCode(ruleEntity);
		model.addAttribute("ruleEntity", ruleEntity);
		return "/rule/ruleCheck.html";
	}

	
	/**
	 * //根据模板code 获得模板的显示名
	 * @param ruleEntity
	 */
	private RuleTemplate gettemplateNameByCode(RuleHiberEntity r) {
		//根据模板code 获得模板的显示名
		if(null != r)
		{
			RuleTemplate rt =  templateService.get(r.getLogicClass());
			if (null != rt)
			{
				r.setTemplateName(rt.getName());
				return rt;
			
			}
		}
	
		return null;
	}
	
}
