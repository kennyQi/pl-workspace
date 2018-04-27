package plfx.admin.controller.gnjp;

import java.util.List;

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
import plfx.gnjp.app.common.util.EntityConvertUtils;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.command.DealerCommand;
import plfx.jp.domain.model.dealer.Dealer;
import plfx.jp.pojo.dto.dealer.DealerDTO;
import plfx.jp.pojo.system.DealerConstants;
import plfx.jp.qo.admin.dealer.DealerQO;
import plfx.jp.spi.inter.dealer.DealerService;

/**
 * 
 * @类功能说明：经销商CONTROLLER
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:28:44
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping(value = "/airtkt/dealer")
public class AirTktDealerController extends BaseController{
	
	@Autowired
	private DealerService jpDealerService;
	
	@Autowired
	private DealerCacheManager dealerCacheManager;
	
	@RequestMapping(value = "/list")
	public String queryDealerList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute DealerQO dealerQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr
		) {
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			dealerQO.setBeginTime(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			dealerQO.setEndTime(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		dealerQO.setCreateDateAsc(false);//按创建时间倒序排序
		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		
		pagination.setCondition(dealerQO);// 添加查询条件

		pagination = jpDealerService.queryDealerList(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("dealerQO", dealerQO);
		model.addAttribute("STATUS_MAP", DealerConstants.STATUS_MAP);
		model.addAttribute("pre_use", DealerConstants.PRE_USE);
		model.addAttribute("use", DealerConstants.USE);
		HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-查询经销商列表成功");
		return "/airticket/dealer/dealer_list.html";
	}
	
	/**
	 * 
	 * @方法功能说明：跳转到增加经销商页面
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:29:19
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {
		return "/airticket/dealer/dealer_add.html";
	}
	
	
	/**
	 * 
	 * @方法功能说明：跳转到修改经销商页面
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:30:55
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
		DealerQO qo=new DealerQO();
		qo.setId(id);
		DealerDTO dto=jpDealerService.queryUnique(qo);
		model.addAttribute("dto", dto);
		return "/airticket/dealer/dealer_update.html";
	}
	
	/**
	 * 
	 * @方法功能说明：修改经销商信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:31:26
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
			@ModelAttribute DealerCommand command) {
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
		boolean result = jpDealerService.updateDealer(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-编辑经销商成功"+command.getName());
			//刷新经销商缓存
			reflushDealerMap();
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "编辑成功";
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-编辑经销商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "dealer");
	}

	/**
	 * 
	 * @方法功能说明：新增经销商
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:31:52
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
			@ModelAttribute DealerCommand command) {
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
		DealerQO qo = new DealerQO();
		qo.setCode(command.getCode());
		DealerDTO dealerDTO = jpDealerService.queryUnique(qo);
		if(dealerDTO != null){
			message = "经销商代码已存在";
			return DwzJsonResultUtil.createJsonString(statusCode, message,
					callbackType, "dealer");
		}
		boolean result = jpDealerService.saveDealer(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-保存经销商成功"+command.getName());
			//刷新经销商缓存
			reflushDealerMap();
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "保存成功";
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-保存经销商失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "dealer");
	}
	
	
	/**
	 * 
	 * @方法功能说明：删除经销商
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:32:08
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param command
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/delete")
	@ResponseBody
	public String delete(HttpServletRequest request, Model model,
			@ModelAttribute DealerCommand command) {
		boolean result = jpDealerService.deleteDealer(command);
		if (result) {
			HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-价格政策管理-删除价格政策成功"+command.getId());
			//刷新经销商缓存
			reflushDealerMap();
			return DwzJsonResultUtil.createJsonString("200", "删除成功!", null, "dealer");
		} else {
			HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-价格政策管理-删除价格政策失败"+command.getId());
			return DwzJsonResultUtil.createJsonString("300", "删除失败!", null, "");
		}
		  
	}
	
	/**
	 * 
	 * @方法功能说明：更新经销商缓存
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月27日上午11:32:32
	 * @修改内容：
	 * @return:void
	 * @throws
	 */
	private void reflushDealerMap() {
		//刷新经销商缓存
		DealerQO dealerQO = new DealerQO();
		List<DealerDTO> dealers = jpDealerService.queryList(dealerQO);
		List<Dealer> dealerList = EntityConvertUtils.convertDtoToEntityList(dealers, Dealer.class);
		dealerCacheManager.reflushDealerMap(dealerList);
	}

	/**
	 * 
	 * @方法功能说明：修改经销状态--是否启用
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:32:33
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
			@ModelAttribute DealerCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = jpDealerService.useDealer(command);
		if(command.getStatus().equals(DealerConstants.USE)){
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-启用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "启用成功!", null, "dealer");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-启用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-禁用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "禁用成功!", null, "dealer");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-禁用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "禁用失败!", null, "");
			}
		}
	}
	
	/**
	 * 
	 * @方法功能说明：批量修改经销商是否启用状态
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:33:00
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
			@ModelAttribute DealerCommand command) {
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = jpDealerService.multiUse(command);
		if("use".equals(command.getFlag())){
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-批量启用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量启用成功!", null, "dealer");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-批量启用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量启用失败!", null, "");
			}
		}else{
			if(result) {
				HgLogger.getInstance().info("yuqz", "机票分销平台-机票管理-经销商管理-批量禁用经销商成功"+command.getId());
				return DwzJsonResultUtil.createJsonString("200", "批量禁用成功!", null, "dealer");
			}else {
				HgLogger.getInstance().error("yuqz", "机票分销平台-机票管理-经销商管理-批量禁用经销商失败"+command.getId());
				return DwzJsonResultUtil.createJsonString("300", "批量禁用失败!", null, "");
			}
		}
	}
	

}
