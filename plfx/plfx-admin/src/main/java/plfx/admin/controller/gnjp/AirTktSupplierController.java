package plfx.admin.controller.gnjp;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.jp.command.admin.supplier.SupplierCommand;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.pojo.system.SupplierConstants;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.supplier.SupplierService;

/**
 * 
 * @类功能说明：供应商管理CONTROLLER
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午5:28:53
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/airtkt/supplier")
public class AirTktSupplierController extends BaseController {
	
	@Autowired
	SupplierService jpSupplierService;
	
	@RequestMapping(value = "/list")
	public String querySupplierList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute SupplierQO supplierQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
		) {
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			supplierQO.setBeginTime(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			supplierQO.setEndTime(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		supplierQO.setCreateDateAsc(false);//按创建时间倒序排序
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination.setCondition(supplierQO);// 添加查询条件

		pagination = jpSupplierService.querySupplierList(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("supplierQO", supplierQO);
		model.addAttribute("STATUS_MAP", SupplierConstants.STATUS_MAP);
		model.addAttribute("pre_use", SupplierConstants.PRE_USE);
		model.addAttribute("use", SupplierConstants.USE);
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-查询供应商列表成功");
		return "/airticket/supplier/supplier_list.html";
	}
	
	@RequestMapping(value = "/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {
		return "/airticket/supplier/supplier_add.html";
	}

	@RequestMapping(value = "/toUpdate")
	public String toUpdate(HttpServletRequest request, Model model,@RequestParam(value = "id", required = false) String id) {
		SupplierQO qo=new SupplierQO();
		qo.setId(id);
		SupplierDTO dto=jpSupplierService.queryUnique(qo);
		model.addAttribute("dto", dto);
		return "/airticket/supplier/supplier_update.html";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute SupplierCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "编辑失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = jpSupplierService.updateSupplier(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-编辑供应商成功"+command.getName());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "编辑成功";
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-供应商管理-编辑供应商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "supplier");
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(HttpServletRequest request, Model model,
			@ModelAttribute SupplierCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "保存失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = jpSupplierService.saveSupplier(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-保存供应商成功"+command.getName());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "保存成功";
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-供应商管理-保存供应商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "supplier");
	}
	
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, Model model,
			@ModelAttribute SupplierCommand command) {
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = jpSupplierService.deleteSupplier(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-删除供应商成功"+command.getId());
		
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "supplier");
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-供应商管理-删除供应商失败"+command.getId());
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null, "");
		}
		  
	}
	
	@RequestMapping(value = "/use")
	@ResponseBody
	public String use(HttpServletRequest request, 
			Model model,
			@ModelAttribute SupplierCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = jpSupplierService.useSupplier(command);
		if(command.getStatus().equals(SupplierConstants.USE)){
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-启用供应商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "启用成功!", null, "supplier");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-供应商管理-启用供应商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-供应商管理-禁用供应商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "禁用成功!", null, "supplier");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-供应商管理-禁用供应商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "禁用失败!", null, "");
			}
		}
	}
	
	@RequestMapping(value = "/multiUse")
	@ResponseBody
	public String multiUse(HttpServletRequest request, 
			Model model,
			@ModelAttribute SupplierCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = jpSupplierService.multiUse(command);
		if("use".equals(command.getFlag())){
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-批量启用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量启用成功!", null, "supplier");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-批量启用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-批量禁用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量禁用成功!", null, "supplier");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-批量禁用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量禁用失败!", null, "");
			}
		}
	}
	
	
	
	
}
