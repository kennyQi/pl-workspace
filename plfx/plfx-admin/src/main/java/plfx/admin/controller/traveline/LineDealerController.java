package plfx.admin.controller.traveline;

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
import plfx.xl.pojo.command.dealer.AuditLineDealerCommand;
import plfx.xl.pojo.command.dealer.CreateLineDealerCommand;
import plfx.xl.pojo.command.dealer.ModifyLineDealerCommand;
import plfx.xl.pojo.dto.LineDealerDTO;
import plfx.xl.pojo.qo.LineDealerQO;
import plfx.xl.pojo.system.DealerConstants;
import plfx.xl.spi.inter.LineDealerService;

/**
 * @类功能说明：线路经销商Controller
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月5日上午9:29:34
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/traveline/dealer")
public class LineDealerController extends BaseController{
	@Autowired
	LineDealerService lineDealerService;
	/**
	 * 列表查询
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:32:54
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param dealerQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@param beginTimeStr
	 * @参数：@param endTimeStr
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryDealerList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute LineDealerQO lineDealerQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
		) {
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			lineDealerQO.setBeginTime(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			lineDealerQO.setEndTime(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		lineDealerQO.setCreateDateAsc(false);//按创建时间倒序排序
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination.setCondition(lineDealerQO);// 添加查询条件

		pagination = lineDealerService.queryDealerList(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("dealerQO", lineDealerQO);
		model.addAttribute("STATUS_MAP", DealerConstants.STATUS_MAP);
		model.addAttribute("pre_use", DealerConstants.PRE_USE);
		model.addAttribute("use", DealerConstants.USE);
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-查询经销商列表成功");
		return "/traveline/dealer/dealer_list.html";
	}

	/**
	 * 跳转添加页面
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:29:48
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {
		return "/traveline/dealer/dealer_add.html";
	}
	
	
	/**
	 * 跳转更新页面
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:29:52
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toUpdate")
	public String toUpdate(HttpServletRequest request, Model model,@RequestParam(value = "id", required = false) String id) {
		LineDealerQO qo=new LineDealerQO();
		qo.setId(id);
		LineDealerDTO dto=lineDealerService.queryUnique(qo);
		model.addAttribute("dto", dto);
		return "/traveline/dealer/dealer_update.html";
	}
	
	
	/**
	 * 更新
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:29:58
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute ModifyLineDealerCommand command) {
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
		boolean result = lineDealerService.updateDealer(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-编辑经销商成功"+command.getName());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "编辑成功";
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-编辑经销商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "xldealer");
	}
	
	
	/**
	 * 添加
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:30:04
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(HttpServletRequest request, Model model,
			@ModelAttribute CreateLineDealerCommand command) {
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
		boolean result = lineDealerService.saveDealer(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-保存经销商成功"+command.getName());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "保存成功";
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-保存经销商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "xldealer");
	}
	
	
	/**
	 * 删除
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:30:09
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	/*@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, Model model,
			@ModelAttribute DealerCommand command) {
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = dealerService.deleteDealer(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-删除经销商成功"+command.getId());
		
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "xldealer");
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-删除经销商失败"+command.getId());
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null, "");
		}
		  
	}*/
	
	
	/**
	 * 启用
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:30:13
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/use")
	@ResponseBody
	public String use(HttpServletRequest request, 
			Model model,
			@ModelAttribute AuditLineDealerCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = lineDealerService.useDealer(command);
		if(command.getStatus().equals(DealerConstants.USE)){
			if(result) {
				HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-启用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "启用成功!", null, "xldealer");
			}else {
				HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-启用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-禁用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "禁用成功!", null, "xldealer");
			}else {
				HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-禁用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "禁用失败!", null, "xldealer");
			}
		}
	}
	
	/**
	 * 批量启用禁用
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:30:20
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/multiUse")
	@ResponseBody
	public String multiUse(HttpServletRequest request, 
			Model model,
			@ModelAttribute AuditLineDealerCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = lineDealerService.multiUse(command);
		if("use".equals(command.getFlag())){
			if(result) {
				HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-批量启用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量启用成功!", null, "xldealer");
			}else {
				HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-批量启用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-批量禁用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量禁用成功!", null, "xldealer");
			}else {
				HgLogger.getInstance().error("tuhualiang", "机票分销平台-旅游线路管理-经销商管理-批量禁用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量禁用失败!", null, "xldealer");
			}
		}
	}
}