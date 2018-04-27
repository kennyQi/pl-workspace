package hg.demo.web.controller.distributor;

import hg.demo.member.common.domain.model.system.DwzJsonResultUtil;
import hg.demo.web.common.UserInfo;
import hg.demo.web.controller.BaseController;
import hg.framework.common.model.LimitQuery;
import hg.framework.common.model.Pagination;
import hg.framework.common.util.UUIDGenerator;
import hg.fx.command.arrearsRecord.AuditArrearsRecordCommand;
import hg.fx.command.arrearsRecord.CreateArrearsRecordCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.fixedPrice.CreateFixedPriceSetCommand;
import hg.fx.command.productInUse.CreateProductInUseCommand;
import hg.fx.command.productInUse.ModifyProductInUseCommand;
import hg.fx.command.rebate.CreateRebateSetCommand;
import hg.fx.command.reserveRecord.AuditReserveRecordCommand;
import hg.fx.command.reserveRecord.ChargeUpdateReserveRecordCommand;
import hg.fx.command.reserveRecord.CreateReserveRecordCommand;
import hg.fx.domain.ArrearsRecord;
import hg.fx.domain.Distributor;
import hg.fx.domain.DistributorUser;
import hg.fx.domain.OperationLog;
import hg.fx.domain.Product;
import hg.fx.domain.ProductInUse;
import hg.fx.domain.ReserveInfo;
import hg.fx.domain.ReserveRecord;
import hg.fx.domain.rebate.RebateInterval;
import hg.fx.domain.rebate.RebateSet;
import hg.fx.spi.ArrearsRecordSPI;
import hg.fx.spi.DistributorRegisterSPI;
import hg.fx.spi.DistributorSPI;
import hg.fx.spi.DistributorUserSPI;
import hg.fx.spi.ProductInUseSPI;
import hg.fx.spi.ProductSPI;
import hg.fx.spi.RebateIntervalSPI;
import hg.fx.spi.RebateSetSPI;
import hg.fx.spi.ReserveInfoSPI;
import hg.fx.spi.ReserveRecordSPI;
import hg.fx.spi.qo.ArrearsRecordSQO;
import hg.fx.spi.qo.DistributorSQO;
import hg.fx.spi.qo.DistributorUserSQO;
import hg.fx.spi.qo.ProductInUseSQO;
import hg.fx.spi.qo.ProductSQO;
import hg.fx.spi.qo.RebateIntervalSQO;
import hg.fx.spi.qo.ReserveRecordSQO;
import hg.fx.util.CharacterUtil;
import hg.fx.util.CodeUtil;
import hg.fx.util.DateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 
 * @author zqq
 * @date 2016-6-1上午9:56:50
 * @since
 */
@Controller
@RequestMapping(value = "/distributorManagement")
public class DistributorManagementController extends BaseController {

	@Autowired
	private RebateIntervalSPI rebateIntervalSPIService;
	@Autowired
	private DistributorSPI distributorService;

	@Autowired
	private DistributorUserSPI distributorUserService;

	@Autowired
	private ReserveRecordSPI reserveRecordSPI;
	@Autowired
	private ProductInUseSPI productInUseService;
	@Autowired
	private ReserveInfoSPI reserveInfoService;
	@Autowired
	private DistributorRegisterSPI distributorRegisterService;

	@Autowired
	private ArrearsRecordSPI arrearsRecordSPIService;
	@Autowired
	private ReserveRecordSPI reserveRecordSPIService;
	@Autowired
	private ProductSPI productService;
	@Autowired
	private RebateSetSPI rebateSetSPIService;

	@RequestMapping(value = "/distributorList")
	public String toList(
			@ModelAttribute DistributorUserSQO sqo,
			Model model,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize) {

		LimitQuery limitQuery = new LimitQuery();
		limitQuery.setPageNo(pageNum);
		limitQuery.setPageSize(pageSize);
		sqo.setLimit(limitQuery);
		sqo.setCheckStatus(Distributor.CHECK_STATUS_PASS);
		sqo.setType(1); // 主账号
		sqo.setUserRemoved(false);
		sqo.setQueryReserveInfo(true);
		Pagination<DistributorUser> pagination = distributorUserService
				.queryPagination(sqo);
		List<DistributorUser> list = pagination.getList();
		List<Long> sumList = new ArrayList<Long>();
		for (DistributorUser user : list) {
			Long sum = distributorUserService.getMonthReserveInfo(user
					.getDistributor().getId());
			sumList.add(sum);
		}
		model.addAttribute("sqo", sqo);
		model.addAttribute("sumList", sumList);
		model.addAttribute("pagination", pagination);

		return "/distributor/distributorList.ftl";
	}

	/**
	 * 禁用/启用商户
	 * 
	 * @author Caihuan
	 * @date 2016年6月3日
	 */
	@ResponseBody
	@RequestMapping("/modifyEnable")
	public Map<String, Object> modifyEnable(String id, Integer status,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String content = "";
		if (status.equals(Distributor.STATUS_OF_DISABLE)) {
			content = "禁用操作";
		} else if (status.equals(Distributor.STATUS_OF_IN_USE)) {
			content = "启用操作";
		} else {
			map.put("statusCode", 300);
			map.put("message", "非法操作");
			return map;
		}
		ModifyDistributorCommand command = new ModifyDistributorCommand();
		command.setId(id);
		command.setStatus(status);
		Distributor distributor = distributorService.modifyDistributor(command);
		String content1 = "对商户 '" + distributor.getName() + " '" + content
				+ " ,商户编号：" + distributor.getCode();
		saveLog(content1, request, OperationLog.OPERATION_TYPE_ENABLE_DISABLE);
		map.put("statusCode", 200);
		map.put("message", "操作成功");
		return map;
	}

	/**
	 * 添加新增商户页面
	 * 
	 * @author Caihuan
	 * @date 2016年6月3日
	 */
	@RequestMapping("/toAddDistributor")
	public String toAddDistributor() {

		return "/distributor/addDistributor.ftl";
	}

	/**
	 * 
	 * @author Caihuan
	 * @date 2016年6月3日
	 */
	@ResponseBody
	@RequestMapping("/saveDistributor")
	public Map<String, Object> saveDistributor(HttpServletRequest request,
			CreateDistributorUserCommand command) {
		Map<String, Object> map = new HashMap<String, Object>();
		String checkResult = checkSave(command);
		if (checkResult != null) {
			map.put("statusCode", 400);
			map.put("message", checkResult);
			return map;
		}
		command.setType(1); // 主帐号
		// 后台添加 直接启用，审核通过
		command.setStatus(Distributor.STATUS_OF_IN_USE);
		command.setCheckStatus(Distributor.CHECK_STATUS_PASS);
		command.setPassword(CharacterUtil.getDistributorPassword(command
				.getCompanyName()));
		DistributorUser distributorUser = distributorUserService
				.create(command);
		saveLog("添加了新用户  " + command.getAccount() + " ，商户编号:"
				+ distributorUser.getDistributor().getCode(), request,
				OperationLog.OPERATION_TYPE_ADD_DISTRIBUTOR);
		map.put("statusCode", 200);
		map.put("callbackType", "closeCurrent");
		map.put("message", "添加商户成功！！");
		return map;
	}

	/**
	 * 检查参数是否符合业务
	 * 
	 * @author Caihuan
	 * @date 2016年6月3日
	 */
	private String checkSave(CreateDistributorUserCommand command) {
		String error = null;
		if (!CharacterUtil.checkDistributorAccount(command.getAccount())) {
			error = "0|账号请以字母开头,数字/字母组合";
		} else {
			DistributorUserSQO sqo = new DistributorUserSQO();
			sqo.setAccount(command.getAccount());
			sqo.setEqAccount(true);
			//DistributorUser user = distributorUserService.queryUnique(sqo);
			boolean isExist = distributorRegisterService.checkExistAccount(sqo.getAccount());
			if(isExist)
				error = "0|该账号已注册，请用其他用户名注册";
		}
		boolean isExist = distributorRegisterService.checkExistPhone(command.getPhone());
		if(isExist)
		{
			if (error == null) {
				error = "1|该手机号已绑定，请用其他用手机号";
			} else
				error += "#split#1|该手机号已绑定，请用其他用手机号";
		}
		if (!CharacterUtil.checkCharacter(command.getName())) {
			if (error == null) {
				error = "2|请输入正确的姓名,汉字/字母组合";
			} else
				error += "#split#2|请输入正确的姓名,汉字/字母组合";
		}
		if (!CharacterUtil.checkCharacterOrHan(command.getCompanyName())) {
			if (error == null) {
				error = "3|请输入正确的公司名称,汉字/字母/数字组合";
			} else
				error += "#split#3|请输入正确的公司名称,汉字/字母/数字组合";
		}
		if (command.getDiscountType()!=1 && command.getDiscountType()!=2){
			if (error == null){
				error = "5| * 请选择正确折扣模式";
			}else{
				error += "#split#5| * 请选择正确折扣模式";
			}
		}
		
		return error;
	}

	/**
	 * 查看签名页面
	 * 
	 * @author Caihuan
	 * @date 2016年6月3日
	 */
	@RequestMapping("/signView")
	public String signView(String id, Model model) {
		DistributorUserSQO sqo = new DistributorUserSQO();
		sqo.setId(id);
		DistributorUser distributorUser = distributorUserService
				.queryUnique(sqo);
		model.addAttribute("user", distributorUser);
		return "/distributor/signView.ftl";

	}

	/**
	 * 添加里程申请列表
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-4 下午4:08:08
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/addLCAudit")
	public String addLC(
			Model model,
			@ModelAttribute ReserveRecordSQO sqo,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize) {
		Pagination<ReserveRecord> pagination = new Pagination<ReserveRecord>();
		// 设置分页条件
		sqo.setLimit(new LimitQuery());
		sqo.getLimit().setPageNo(pageNum);
		sqo.getLimit().setPageSize(pageSize);
		// 设置排序 待审核优先然后按时间排序 降序
		sqo.setOrderByStatus(-2);
		sqo.setOrderByApplyDate(-1);
		// 设置交易类型 后台备付里程添加申请
		sqo.setType(ReserveRecord.RECORD_TYPE_RECHARGE);
		pagination = reserveRecordSPI.queryReserveRecordPagination(sqo);
		model.addAttribute("pagination", pagination);
		return "/distributor/addLCAudit.ftl";
	}

	/**
	 * 可欠费里程修改申请列表
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-4 下午4:07:18
	 * @param model
	 * @param arSQO
	 * @param pageNum
	 * @param pageSize
	 * @param curNav
	 * @return
	 */
	@RequestMapping(value = "/owingLCAudit")
	public String owingLC(
			Model model,
			@ModelAttribute ArrearsRecordSQO arSQO,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
			@RequestParam(value = "numPerPage", defaultValue = "20") Integer pageSize) {
		// 设置排序方式
		arSQO.setOrderWay(ArrearsRecordSQO.ORDERWAY_1);
		arSQO.setLimit(new LimitQuery());
		arSQO.getLimit().setPageNo(pageNum);
		arSQO.getLimit().setPageSize(pageSize);
		Pagination<ArrearsRecord> pagination = arrearsRecordSPIService
				.queryArrearsRecordPagination(arSQO);
		model.addAttribute("pagination", pagination);
		return "/distributor/owingLCAudit.ftl";
	}

	/**
	 * 跳转到修改可欠费里程页面
	 * 
	 * @author admin
	 * @since hgfx-admin-web
	 * @date 2016-6-6 上午9:38:55
	 * @param model
	 * @param distributorUserSQO
	 * @return
	 */
	@RequestMapping(value = "/toOwingLC")
	public String toOwingLC(Model model,
			@ModelAttribute DistributorUserSQO distributorUserSQO) {
		DistributorUser distributorUser = distributorUserService
				.queryUnique(distributorUserSQO);
		// 商户帐号
		model.addAttribute("distributorUser", distributorUser);
		// 备付里程信息
		model.addAttribute("reserveInfo", distributorUser.getDistributor()
				.getReserveInfo());
		return "/distributor/toOwingLC.ftl";
	}

	/**
	 * 申请修改可欠费里程
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-4 下午8:21:39
	 * @param model
	 * @param cmd
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/createArrearsRecord")
	public Map<String, Object> createArrearsRecord(Model model,
			@ModelAttribute CreateArrearsRecordCommand cmd,
			HttpServletRequest request) {
		// 获得当前用户信息
		UserInfo userInfo = getUserInfo(request.getSession());
		// 添加审核人信息
		cmd.setAuthUserID(userInfo.getUserId());
		// 添加审核记录
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			ArrearsRecord ar = arrearsRecordSPIService.create(cmd);
			// 添加操作日志
			String content = "修改可欠费积分申请操作,记录id,(" + ar.getId() + ")";
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_APPLY_EDIT_ARREARS);
			map.put("statusCode", 200);
			map.put("callbackType", "closeCurrent");
			map.put("message", "申请成功");
		} catch (Exception e) {
			map.put("statusCode", 300);
			map.put("callbackType", "closeCurrent");
			map.put("message", "申请失败");
		}
		return map;
	}

	/**
	 * 审核可欠费里程结果提交(通过)
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-3 下午6:59:07
	 * @param model
	 * @param cmd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/owingLCAuditPass")
	public Map<String, Object> owingLCAuditPass(Model model,
			@ModelAttribute AuditArrearsRecordCommand cmd,
			HttpServletRequest request) {
		// 处理结果map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cmd.setCheckStatus(AuditArrearsRecordCommand.CHECK_STATUS_PASS);
			arrearsRecordSPIService.audit(cmd);
			// 添加操作日志
			String content = "可欠费积分审核通过操作,记录id,(" + cmd.getArrearsRecordID()
					+ ")";
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_EDIT_ARREARS);
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");
		} catch (Exception e) {
			// 添加操作日志
			String content = "可欠费积分审核通过操作,记录id,(" + cmd.getArrearsRecordID()
					+ ")" + "操作失败，异常:" + e.getMessage();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_EDIT_ARREARS);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");
		}
		return map;
	}

	/**
	 * 审核可欠费里程结果提交(拒绝)
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-3 下午6:59:07
	 * @param model
	 * @param cmd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/owingLCAuditRefuse")
	public Map<String, Object> owingLCAuditRefuse(Model model,
			@ModelAttribute AuditArrearsRecordCommand cmd,
			HttpServletRequest request) {
		// 处理结果map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cmd.setCheckStatus(AuditArrearsRecordCommand.CHECK_STATUS_REFUSE);
			arrearsRecordSPIService.audit(cmd);
			// 添加操作日志
			String content = "可欠费积分审核拒绝操作,记录id,(" + cmd.getArrearsRecordID()
					+ ")";
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_EDIT_ARREARS);
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");
		} catch (Exception e) {
			// 添加操作日志
			String content = "可欠费积分审核拒绝操作,记录id,(" + cmd.getArrearsRecordID()
					+ ")操作失败，异常:" + e.getMessage();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_EDIT_ARREARS);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");
		}
		return map;
	}

	/**
	 * 审核添加里程结果提交(通过)
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-3 下午6:59:07
	 * @param model
	 * @param cmd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addLCAuditPass")
	public Map<String, Object> addLCAuditPass(Model model,
			@ModelAttribute AuditReserveRecordCommand cmd,
			@ModelAttribute ChargeUpdateReserveRecordCommand cr,
			HttpServletRequest request) {
		// 处理结果map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cmd.setCheckStatus(AuditReserveRecordCommand.CHECK_STATUS_PASS);
			// 审核充值一步完成
			cr.setAuditCmd(cmd);
			reserveInfoService.auditAndUpdateReserve(cr);
			// 添加操作日志
			String content = "添加积分审核通过操作,记录id,(" + cmd.getReserveRecordID()
					+ "),商户id,(" + cmd.getDistributorID() + ")";
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_ADD_MILEAGE);
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");
		} catch (Exception e) {
			// 添加操作日志
			String content = "添加积分审核通过操作,记录id,(" + cmd.getReserveRecordID()
					+ "),商户id,(" + cmd.getDistributorID() + ")操作失败，异常:"
					+ e.getMessage();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_ADD_MILEAGE);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");
		}
		return map;
	}

	@RequestMapping(value = "/showImg")
	public String showImg(Model model, @ModelAttribute ReserveRecordSQO sqo) {
		ReserveRecord reser = reserveRecordSPI.queryReserveRecordByID(sqo);
		model.addAttribute("reser", reser);
		return "/distributor/showImg.ftl";
	}

	/**
	 * 审核添加里程结果提交(拒绝)
	 * 
	 * @author zqq
	 * @since hgfx-admin-web
	 * @date 2016-6-3 下午6:59:07
	 * @param model
	 * @param cmd
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addLCAuditRefuse")
	public Map<String, Object> addLCAuditRefuse(Model model,
			@ModelAttribute AuditReserveRecordCommand cmd,
			HttpServletRequest request) {
		// 处理结果map
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			cmd.setCheckStatus(AuditReserveRecordCommand.CHECK_STATUS_REFUSE);
			reserveRecordSPIService.audit(cmd);
			// 添加操作日志
			String content = "添加积分审核拒绝操作,记录id(" + cmd.getReserveRecordID()
					+ "),商户id(" + cmd.getDistributorID() + ")";
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_ADD_MILEAGE);
			map.put("statusCode", 200);
			map.put("callbackType", null);
			map.put("message", "操作成功");
		} catch (Exception e) {
			// 添加操作日志
			String content = "添加积分审核拒绝操作,记录id(" + cmd.getReserveRecordID()
					+ "),商户id(" + cmd.getDistributorID() + ")操作失败，异常:"
					+ e.getMessage();
			saveLog(content, request,
					OperationLog.OPERATION_TYPE_CHECK_ADD_MILEAGE);
			map.put("statusCode", 300);
			map.put("callbackType", null);
			map.put("message", "操作失败");
		}
		return map;
	}

	/**
	 * 跳转至添加里程页面
	 * 
	 * @author cangs
	 */
	@RequestMapping(value = "/toAddLC")
	public String toAddLC(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name = "distributorID", required = true) String distributorID,
			@RequestParam(name = "loginName", required = true) String loginName,
			Model model) {
		DistributorSQO distributorSQO = new DistributorSQO();
		distributorSQO.setId(distributorID);
		distributorSQO.setQueryReserveInfo(true);
		Distributor distributor = distributorService
				.queryUnique(distributorSQO);
		if (distributor.getReserveInfo() != null
				&& distributor.getReserveInfo().getUsableBalance() != null) {
			model.addAttribute("usableBalance", distributor.getReserveInfo()
					.getUsableBalance());
		}
		model.addAttribute("distributorID", distributorID);
		model.addAttribute("loginName", loginName);

		return "/distributor/toAddLC.html";
	}

	/**
	 * 添加里程页面
	 * 
	 * @author cangs
	 */
	@ResponseBody
	@RequestMapping(value = "/addLC")
	public String addLC(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(name = "distributorID", required = true) String distributorID,
			@RequestParam(name = "increment", required = true) Long increment,
			@RequestParam(name = "provePath", required = true) String provePath,
			Model model) {
		try {
			DistributorSQO distributorSQO = new DistributorSQO();
			distributorSQO.setId(distributorID);
			distributorSQO.setQueryReserveInfo(true);
			Distributor distributor = distributorService.queryUnique(distributorSQO);
			// 获取当前登录人
			UserInfo userInfo = (UserInfo) request.getSession().getAttribute("_SESSION_USER_");
			CreateReserveRecordCommand command = new CreateReserveRecordCommand();
			command.setAuthUserID(userInfo.getUserId());
			command.setDistributorID(distributorID);
			command.setType(CreateReserveRecordCommand.RECORD_TYPE_RECHARGE);
			command.setIncrement(increment);
			command.setProvePath(provePath);
			if (distributor != null&& StringUtils.isNotBlank(distributor.getCode())) {
				command.setTradeNo(CodeUtil.getMileOrderFlowCode(distributor.getCode()));
			}
			/**
			 * 审核通过后才修status=充值成功
			// 与产品去人后添加里程默认状态 为‘交易成功’
			//楼上作废 讨论结果作废 
			//经过再次讨论 在 实体 以及command中添加 静态变量
			//变成'充值成功'
			command.setStatus(CreateReserveRecordCommand.RECORD_STATUS_CHONGZHI_SUCC);
			*/
			ReserveRecord rr = reserveRecordSPI.create(command);
			if (rr != null) {
				// 添加操作日志
				String content = "添加积分申请操作,记录id,(" + rr.getId() + ")";
				saveLog(content, request,OperationLog.OPERATION_TYPE_APPLY_ADD_MILEAGE);
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "添加成功",	"closeCurrent", "distributorList");
			} else {
				return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败",	"closeCurrent", "distributorList");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_500, "添加失败", "closeCurrent",	"distributorList");
		}
	}

	/**
	 * 
	 * @方法功能说明：里程预警修改跳转页
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月3日 下午6:52:00
	 * @修改内容：
	 * @param model
	 * @param id
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/toEditWarning")
	public String toEditWarning(Model model,
			@RequestParam(name = "id", required = true) String id,
			@RequestParam(name = "loginName", required = true) String loginName) {
		// 根据商户id，查询商户的里程预警信息
		DistributorSQO sqo = new DistributorSQO();
		sqo.setId(id);
		sqo.setQueryReserveInfo(true);
		Distributor distributor = distributorService.queryUnique(sqo);
		model.addAttribute("distributor", distributor);
		model.addAttribute("loginName", loginName);
		return "/distributor/toEditWarning.ftl";
	}

	/**
	 * 
	 * @方法功能说明：里程预警修改
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月3日 下午7:00:08
	 * @修改内容：
	 * @param model
	 * @param reserveInfoId
	 * @param warnValue
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modifyWarningValue")
	public String modifyWarningValue(
			Model model,
			HttpServletRequest request,
			@RequestParam(name = "reserveInfoId", required = true) String reserveInfoId,
			@RequestParam(name = "warnValue", required = true) Integer warnValue) {

		try {
			ReserveInfo reserveInfo = reserveInfoService.modifyWarnValue(
					reserveInfoId, warnValue);
			if (reserveInfo != null) {
				// 记录操作日志
				String content = "添加积分预警修改操作成功,记录id,(" + reserveInfoId + ")";
				saveLog(content, request, OperationLog.OPERATION_TYPE_SET_WARN);
				return DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_200, "修改成功",
						DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "distributorList");
			} else {
				return DwzJsonResultUtil.createJsonString(
						DwzJsonResultUtil.STATUS_CODE_500, "修改失败", "","");
			}
		} catch (Exception e) {
			return DwzJsonResultUtil.createJsonString(
					DwzJsonResultUtil.STATUS_CODE_500, "修改失败", "","");
		}
	}

	/**
	 * 
	 * @方法功能说明：商品使用页面跳转controller
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月3日 下午2:03:48
	 * @修改内容：
	 * @param model
	 * @param psqo
	 * @param loginName
	 * @return
	 */
	@RequestMapping(value = "/toProduct")
	public String toProduct(Model model, @ModelAttribute ProductInUseSQO psqo,
			@RequestParam(name = "loginName", required = true) String loginName) {
		// 通过商户id，查询使用中,停用中的商品
		List<ProductInUse> productInUseList = productInUseService
				.queryList(psqo);
		// 通过商户id，查询未使用商品列表
		List<Product> productNotUseList = productInUseService
				.productNotUseList(psqo.getDistributorId());

		model.addAttribute("productInUseList", productInUseList);
		model.addAttribute("productNotUseList", productNotUseList);
		model.addAttribute("distributorId", psqo.getDistributorId());
		model.addAttribute("loginName", loginName);
		return "/distributor/toProduct.ftl";
	}

	/**
	 * 
	 * @方法功能说明：商品使用页的操作启用，停用操作
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月3日 下午3:26:34
	 * @修改内容：
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/productAct")
	public String productAct(@ModelAttribute ModifyProductInUseCommand command,
			HttpServletRequest request) {
		try {
			if (productInUseService.changeStatus(command) != null) {
				// 成功
				// 记录操作日志
				String content = "添加商户-商品的启用禁用操作成功,记录id,"
						+ command.getProductInUseId();
				saveLog(content, request,
						OperationLog.OPERATION_TYPE_MER_PRODUCT_ENABLE_DISABLE);
				return DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				// 失败
				return DwzJsonResultUtil.STATUS_CODE_500;
			}
		} catch (Exception e) {
			// 失败
			return DwzJsonResultUtil.STATUS_CODE_500;
		}
	}

	/**
	 * 
	 * @方法功能说明：商户使用商品controller(商品使用页)
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月5日 上午10:11:01
	 * @修改内容：
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/addProductInUse")
	public String addProductInUse(CreateProductInUseCommand command) {
		Product product;
		if (command != null && StringUtils.isNotBlank(command.getProdId())
				&& StringUtils.isNotBlank(command.getDistributorId())) {
			ProductSQO sqo = new ProductSQO();
			sqo.setProductID(command.getProdId());
			product = productService.queryProductByID(sqo);

			command.setId(UUIDGenerator.getUUID());
			command.setAgreementPath(product.getAgreementPath());
			command.setStatus(2);// 后台添加使用中状态
			command.setUseDate(new Date());// 启用时间即当前时间
		} else {
			// 请选择要添加的商品
			return "-1";
		}
		try {
			if (productInUseService.addProductInUse(command) != null) {
				DistributorSQO distributorSQO = new DistributorSQO();
				distributorSQO.setId(command.getDistributorId());
				Distributor distributor = distributorService.queryUnique(distributorSQO);
				if(distributor.getDiscountType()==Distributor.DISCOUNT_TYPE_REBATE){
					CreateRebateSetCommand cmd = new CreateRebateSetCommand();
					//创建一个默认设置
					cmd.setProductId(product.getId());
					cmd.setDistributorId(command.getDistributorId());
					DistributorUserSQO disUserSqo = new DistributorUserSQO();
					disUserSqo.setDistributorId(command.getDistributorId());
					disUserSqo.setType(1);
					//查询商户主帐号
					DistributorUser disUser = distributorUserService.queryUnique(disUserSqo);
					cmd.setDistributorUserId(disUser.getId());
					cmd.setApplyDate(new Date());
					cmd.setCheckStatus(RebateSet.CHECK_STATUS_PASS);
					cmd.setImplementDate(DateUtil.parseDateTime1(DateUtil.forDateFirst()+"00:00:00"));
					//查询默认设置
					RebateIntervalSQO sqo = new RebateIntervalSQO();
					sqo.setIsImplement(true);
					sqo.setProductId(product.getId());
					RebateInterval rebateInterval = rebateIntervalSPIService.queryUnique(sqo);
					cmd.setIntervalStr(rebateInterval.getIntervalStr());
					cmd.setIsImplement(true);
					rebateSetSPIService.createDefaultRebateSet(cmd);
				}
				return DwzJsonResultUtil.STATUS_CODE_200;
			} else {
				return DwzJsonResultUtil.STATUS_CODE_500;
			}
		} catch (Exception e) {
			return DwzJsonResultUtil.STATUS_CODE_500;
		}
	}

}
