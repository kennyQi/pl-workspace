/**
 * @文件名称：TemplateController.java
 * @类路径：hgtech.jfaddmin.controller
 * @描述：规则模版管理
 * @作者：xinglj
 * @时间：2014年10月13日下午1:25:08
 */
package hgtech.jfadmin.controller;


import java.io.Serializable;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.hjf.app.service.grade.GradeService;
import hg.hjf.domain.model.grade.GradeQo;
import hgtech.jf.entity.StringUK;
import hgtech.jfaccount.Grade;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.JfName;
import hgtech.jfaccount.SetupAccountContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import ucenter.admin.controller.BaseController;

/**
 * @类功能说明：等级管理
 * @类修改者：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：xinglj
 * @创建时间： 2015、8.20
 * 
 */
@Controller
@RequestMapping(value = "/grade")
public class GradeController extends BaseController {

	public static final String navTabId = "gradeList";
	public static final String rel = "jbsxBoxGrade";

	@Autowired
	GradeService gradeService;

		/**
	 * 规则列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute GradeQo qo) {
		Pagination paging = new Pagination();
		paging.setCondition(qo);
		paging = this.gradeService.findPagination(paging);
		
		model.addAttribute("pagination", paging);
		model.addAttribute("dto", qo);
		 
		return "/grade/gradeList.html";
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
			HttpServletResponse response, Model model) {
		
	 	/**
		 * 积分类型
		 */
		List acctT = SetupAccountContext.topAcctType.getSubList();
		model.addAttribute("accountTypeList", acctT);
		
		return "/grade/gradeAdd.html";
	}
	/**
	 * 
	 * @方法功能说明： 添加 页面
	 * @修改者名字： 
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
	public String save(HttpServletRequest request, Model model,@ModelAttribute Grade dto) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			
			String code = dto.getCode();
			Grade   gradeEntity = gradeService.queryByCode(code);
			if(null != gradeEntity)
			{
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "输入的编码已存在";
				return DwzJsonResultUtil.createJsonString(statusCode, message,
						callbackType, navTabId, null, null, rel);
			}else
			{
				dto.setId(UUIDGenerator.getUUID());
				gradeService.save(dto);
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
	 *  跳转到编辑页面
	 * @param request
	 * @param response
	 * @param model
	 * @param code 规则编码
	 * @param gradeEntity
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/toEdit")
	public String toEdite(HttpServletRequest request, HttpServletResponse response, Model model
			  ) {
		Grade gradeEntity; 
		gradeEntity = gradeService.get(request.getParameter("id"));
		JfAccountType accountTypeObj = SetupAccountContext. findType(gradeEntity.getAccountType() );
		gradeEntity.setAccountTypeObj( accountTypeObj);
		
	 	/**
			 * 积分类型
			 */
		model.addAttribute("jfTypeList", SetupAccountContext.jfNames);
		
		if (null != gradeEntity)
		{
			model.addAttribute("entity", gradeEntity);
		}
		return "/grade/gradeEdit.html";
	}	

	
	/**
	 *  编辑
	 * @param request
	 * @param response
	 * @param model
	 * @param code
	 * @param grade
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/edit") 
	public String edit(HttpServletRequest request, HttpServletResponse response, Model model , @ModelAttribute Grade grade){
		
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "更新成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		try {
			
						
			gradeService.update(grade);
		 
		} catch (Exception e) {
			e.printStackTrace();
			statusCode = DwzJsonResultUtil.STATUS_CODE_500;
			message = "更新失败";
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId, null, null, rel);
	}
	
	/**
	 * 
	 * @方法功能说明： 删除
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
					  @RequestParam(value="code", required=false)String code   ){
		
		try {
			gradeService.deleteById(request.getParameter("id"));
			 
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, navTabId,null,null,rel);
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null,navTabId, null, null, rel);
		}
	}	 
	
}
