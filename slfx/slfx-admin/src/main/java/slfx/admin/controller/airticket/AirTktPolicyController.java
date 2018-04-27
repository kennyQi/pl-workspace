package slfx.admin.controller.airticket;

import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.log.domainevent.DomainEventDao;
import hg.log.domainevent.DomainEventQo;
import hg.log.po.domainevent.DomainEvent;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import slfx.admin.controller.BaseController;
import slfx.jp.command.admin.policy.PolicyCommand;
import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.pojo.system.PolicyConstants;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.inter.AutoCheckPolicyStatusService;
import slfx.jp.spi.inter.dealer.DealerService;
import slfx.jp.spi.inter.policy.PolicyService;
import slfx.jp.spi.inter.supplier.SupplierService;

/**
 * 
 * @类功能说明：价格政策
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年8月4日下午1:59:07
 * @版本：V1.0
 * 
 */
@Controller
@RequestMapping(value = "/airtkt/policy")
public class AirTktPolicyController extends BaseController {

	@Autowired
	PolicyService jpPolicyService;
	@Autowired
	DealerService jpDealerService;
	@Autowired
	SupplierService jpSupplierService;
	@Autowired
	private AutoCheckPolicyStatusService autoCheckPolicyStatusService;
	
	@Autowired
	private DomainEventDao domainEventDao;
	/**
	 * 
	 * @方法功能说明： 价格政策列表
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月4日下午2:00:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param policyQO
	 * @参数：@param pageNo
	 * @参数：@param pageSize
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String queryPolicyList(
			HttpServletRequest request,
			Model model,
			@ModelAttribute PolicyQO policyQO,
			@RequestParam(value = "pageNum", required = false) Integer pageNo,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "beginTimeStr", required = false) String beginTimeStr,
			@RequestParam(value = "endTimeStr", required = false) String endTimeStr) {
		
		if (null != beginTimeStr && !"".equals(beginTimeStr)) {
			policyQO.setBeginTime(DateUtil.dateStr2BeginDate(beginTimeStr));
		}
		if (null != endTimeStr && !"".equals(endTimeStr)) {
			policyQO.setEndTime(DateUtil.dateStr2EndDate(endTimeStr));
		}
		
		List<DealerDTO> dealerDtoList = jpDealerService
				.queryList(new DealerQO());
		List<SupplierDTO> supplierDtoList = jpSupplierService
				.queryList(new SupplierQO());
		model.addAttribute("dealerDtoList", dealerDtoList);
		model.addAttribute("supplierDtoList", supplierDtoList);

		// 添加分页参数
		Pagination pagination = new Pagination();
		pageNo = pageNo == null ? new Integer(1) : pageNo;
		pageSize = pageSize == null ? new Integer(20) : pageSize;
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		policyQO.setSortCreateTime(true);
		pagination.setCondition(policyQO);// 添加查询条件

		pagination = jpPolicyService.queryPolicyList(pagination);

		model.addAttribute("pagination", pagination);
		model.addAttribute("policyQO", policyQO);
		model.addAttribute("STATUS_MAP", PolicyConstants.STATUS_MAP);
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-价格管理-查询价格政策列表成功");
		return "/airticket/policy/policy_list.html";
	}

	/**
	 * 
	 * @方法功能说明：价格政策详情
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月4日下午3:07:40
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/detail")
	public String queryPolicyDetail(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = true) String id) {

		PolicyQO qo = new PolicyQO();
		qo.setId(id);
		PolicyDTO dto = jpPolicyService.queryUnique(qo);
		HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-价格管理-查看价格政策详情成功");
		model.addAttribute("policyDTO", dto);

		DomainEventQo domainEventQo = new DomainEventQo();
		domainEventQo.setModelName("slfx.jp.app.service.spi.policy.PolicyServiceImpl");
		domainEventQo.setTags(new String[]{id});
		List<DomainEvent> events = domainEventDao.queryList(domainEventQo);
		model.addAttribute("LOG_EVENTS", events);
		//System.out.println(JSON.toJSONString(events.get(0)));
		
		model.addAttribute("policyDTO", dto);
		return "/airticket/policy/policy_detail.html";
	}

	@RequestMapping(value = "/toAdd")
	public String toAdd(HttpServletRequest request, Model model) {

		List<DealerDTO> dealerDtoList = jpDealerService
				.queryList(new DealerQO());
		List<SupplierDTO> supplierDtoList = jpSupplierService
				.queryList(new SupplierQO());
		model.addAttribute("dealerDtoList", dealerDtoList);
		model.addAttribute("supplierDtoList", supplierDtoList);

		return "/airticket/policy/policy_add.html";
	}

	@RequestMapping(value = "/add")
	@ResponseBody
	public String add(HttpServletRequest request, Model model,
			@ModelAttribute PolicyCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "保存失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setCreatePersion(au.getLoginName());
			command.setFromAdminId(au.getLoginName());
		}
		
		boolean result = jpPolicyService.savePolicy(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-价格管理-保存价格政策成功"+command.getName());
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "保存成功";
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-价格管理-保存价格政策失败"+command.getName());
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "policy");
	}

	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月7日上午9:38:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toPublish")
	public String toPublishPlicy(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = true) String id) {
		PolicyQO qo = new PolicyQO();
		qo.setId(id);
		PolicyDTO dto = jpPolicyService.queryUnique(qo);
		model.addAttribute("policyDTO", dto);
		model.addAttribute("policyId", id);
		model.addAttribute("PUBLISH", true);
		return "/airticket/policy/policy_detail.html";
	}

	/**
	 * 
	 * @方法功能说明：发布价格政策
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月4日下午4:10:05
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws 
	 * 
	 */
	@RequestMapping(value = "/publish")
	@ResponseBody
	public String publishPlicy(HttpServletRequest request, Model model,
			@RequestParam(value = "policyId", required = true) String policyId) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "发布失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;

		PolicyCommand command = new PolicyCommand();
		command.setId(policyId);
		
		
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = jpPolicyService.publicPolicy(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-价格管理-发布价格政策成功"+policyId);
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "发布成功";
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-价格管理-发布价格政策失败"+policyId);
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "policy");
	}

	/**
	 * 
	 * @方法功能说明：跳转到取消页面，输入取消原因
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月4日下午4:12:03
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/toCancel")
	public String toCancel(HttpServletRequest request, Model model,
			@RequestParam(value = "id", required = true) String id) {
		model.addAttribute("policyId", id);
		return "/airticket/policy/policy_cancel.html";
	}

	/**
	 * 
	 * @方法功能说明：取消该订单
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年8月7日上午10:07:39
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param id
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/cancel")
	@ResponseBody
	public String cancel(HttpServletRequest request, Model model,
			@RequestParam(value = "policyId", required = true) String policyId) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_300;
		String message = "取消失败";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		PolicyCommand command = new PolicyCommand();
		command.setId(policyId);
		// 设置姓名
		Subject currentUser = SecurityUtils.getSubject();
		AuthUser au = (AuthUser) currentUser.getSession().getAttribute(
				SecurityConstants.SESSION_USER_KEY);
		//String loginName = "";
		if(au!=null){
			command.setFromAdminId(au.getLoginName());
		}
		boolean result = jpPolicyService.cancelPolicy(command);
		if (result) {
			HgLogger.getInstance().info("tuhualiang", "机票分销平台-机票管理-价格管理-取消价格政策成功"+policyId);
			statusCode = DwzJsonResultUtil.STATUS_CODE_200;
			message = "取消成功";
		} else {
			HgLogger.getInstance().error("tuhualiang", "机票分销平台-机票管理-价格管理-发布价格政策失败"+policyId);
		}
		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, "policy");
	}
	
	/**
	 * 自动检查价格政策状态定时任务入口
	 * 
	 * @return
	 */
	@RequestMapping("/autoCheckPolicyStatus")
	public void autoCheckPolicyStatus(HttpServletRequest request,HttpServletResponse response){
		
		//自动检查价格政策状态
		autoCheckPolicyStatusService.autoCheckPolicyStatus();
	}
}