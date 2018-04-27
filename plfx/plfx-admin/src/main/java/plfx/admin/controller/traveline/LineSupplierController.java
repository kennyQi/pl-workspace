package plfx.admin.controller.traveline;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.admin.controller.BaseController;
import plfx.xl.pojo.command.supplier.AuditLineSupplierCommand;
import plfx.xl.pojo.command.supplier.CreateLineSupplierCommand;
import plfx.xl.pojo.command.supplier.ModifyLineSupplierCommand;
import plfx.xl.pojo.dto.LineSupplierDTO;
import plfx.xl.pojo.qo.LineSupplierQO;
import plfx.xl.pojo.system.SupplierConstants;
import plfx.xl.spi.inter.LineSupplierService;

import com.alibaba.fastjson.JSON;

/**
 *@类功能说明：线路供应商Controller
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日下午1:31:55
 */
@Controller
@RequestMapping(value = "/traveline/linesupplier")
public class LineSupplierController extends BaseController{
	
	@Autowired
	private LineSupplierService lineSupplierService;
	
	@RequestMapping("/list")
	public String querySupplierList(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute LineSupplierQO qo,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize){
		
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo == null?new Integer(1):pageNo);
		pagination.setPageSize(pageSize == null?new Integer(20):pageSize);
		
		if(qo == null){
			qo = new LineSupplierQO();
		}
		pagination.setCondition(qo);
		pagination = lineSupplierService.queryPagination(pagination);
		model.addAttribute("STATUS_MAP", SupplierConstants.STATUS_MAP);
		
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);
		return "/traveline/supplier/supplier_list.html";
	}
	
	/**
	 * 跳转到新增线路供应商页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toadd")
	public String toAdd(HttpServletRequest request,HttpServletResponse response,Model model){
		return "/traveline/supplier/supplier_add.html";
	}
	
	/**
	 * 新增线路供应商
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public String add(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute CreateLineSupplierCommand command){
		
		try{
			
			Boolean result = lineSupplierService.createLineSupplier(command);
			String message = "";
			String statusCode = "";
			if(result){
				message = "新增成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			}else{
				message = "新增失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-新增供应商失败" + JSON.toJSONString(command));
			}
			
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-新增供应商失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "新增失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
		}
		
	}
	
	/**
	 * 跳转到修改线路供应商信息页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/toupdate")
	public String toUpdate(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "id", required = false) String id){
		LineSupplierDTO lineSupplierDTO = new LineSupplierDTO();
		LineSupplierQO lineSupplierQO = new LineSupplierQO();
		lineSupplierQO.setId(id);
		lineSupplierDTO = lineSupplierService.queryUnique(lineSupplierQO);
		model.addAttribute("lineSupplier", lineSupplierDTO);
		return "/traveline/supplier/supplier_update.html";
	}
	
	/**
	 * 修改线路供应商信息
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String update(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute ModifyLineSupplierCommand command){
		
		try{
			
			boolean result = lineSupplierService.modifyLineSupplier(command);
			String message = "";
			String statusCode = "";
			if(result){
				message = "修改成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			}else{
				message = "修改失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-修改供应商信息失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-修改供应商信息失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "修改失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
		}
		
	}
	
	/**
	 * 跳转到审核页面
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping("/toaudit")
	public String toAudit(HttpServletRequest request,HttpServletResponse response,Model model,
			@RequestParam(value = "id", required = false) String id){
		LineSupplierDTO lineSupplierDTO = new LineSupplierDTO();
		LineSupplierQO lineSupplierQO = new LineSupplierQO();
		lineSupplierQO.setId(id);
		lineSupplierDTO = lineSupplierService.queryUnique(lineSupplierQO);
		model.addAttribute("lineSupplier", lineSupplierDTO);
		model.addAttribute("STATUS_MAP", SupplierConstants.AUDIT_STATUS_MAP); 
		return "/traveline/supplier/supplier_audit.html";
	}
	
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @param model
	 * @param command
	 * @return
	 */
	@RequestMapping("/audit")
	@ResponseBody
	public String audit(HttpServletRequest request,HttpServletResponse response,Model model,
			@ModelAttribute AuditLineSupplierCommand command){
		
		try{
			
			boolean result = lineSupplierService.auditLineSupplier(command);
			String message = "";
			String statusCode = "";
			if(result){
				message = "审核操作成功";
				statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			}else{
				message = "审核操作失败";
				statusCode = DwzJsonResultUtil.STATUS_CODE_300;
				HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-审核供应商操作失败" + JSON.toJSONString(command));
			}
			return DwzJsonResultUtil.createJsonString(statusCode, message,DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
			
		}catch(Exception e){
			HgLogger.getInstance().error("luoyun", "旅游线路管理-供应商管理-审核供应商操作失败" + HgLogger.getStackTrace(e));
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_300, "审核操作失败",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "supplier");
		}
	}
}