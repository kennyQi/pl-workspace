package hg.dzpw.dealer.admin.controller.order;

import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.MoneyUtil;
import hg.common.util.StringUtil;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.common.SystemConfig;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.app.service.api.alipay.AliPayCaeChargeService;
import hg.dzpw.app.service.local.ApplyRefundRecordLocalService;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.UseRecordLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.order.OrderStatus;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.pay.ApplyRefundRecord;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.GroupTicketStatus;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.ticket.UseRecord;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;
import hg.dzpw.dealer.admin.controller.BaseController;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.pojo.api.alipay.CaeChargeParameter;
import hg.dzpw.pojo.api.alipay.CaeChargeResponse;
import hg.dzpw.pojo.command.platform.dealer.PlatformModifyDealerCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformModifyGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.ticket.singleTicket.PlatformModifySingleTicketCommand;
import hg.dzpw.pojo.exception.DZPWException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：经销商订单管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2015-3-2上午11:02:21
 * @版本：V1.2
 */
@Controller
public class OrderController extends BaseController {

	@Autowired
	private TicketOrderLocalService ticketOrderLocalService;

	@Autowired
	private DealerLocalService dealerLocalService;

	@Autowired
	private TicketPolicyLocalService ticketPolicyLocalService;

	@Autowired
	private GroupTicketLocalService groupTicketLocalService;

	@Autowired
	private SingleTicketLocalService singleTicketLocalService;

	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;

	@Autowired
	private UseRecordLocalService useRecordLocalService;

	@Autowired
	private AliPayCaeChargeService aliPayCaeChargeService;
	
	@Autowired
	private ApplyRefundRecordLocalService refundRecordLocalService;

	@RequestMapping("/order/list")
	public ModelAndView orderList(
			HttpServletRequest request,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "ticketPolicyName", required = false) String ticketPolicyName,
			@RequestParam(value = "ticketPolicyType", required = false) Integer ticketPolicyType,
			@RequestParam(value = "scenicSpotId", required = false) String scenicSpotId,
			@RequestParam(value = "createBeginDateStr", required = false) String createBeginDateStr,
			@RequestParam(value = "createEndDateStr", required = false) String createEndDateStr,
			@RequestParam(value = "linkMan", required = false) String linkMan,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "orderStatus", required = false) Integer orderStatus) {

		// 景区列表
		List<ScenicSpot> scenicSpotList = scenicSpotLocalService
				.queryList(new ScenicSpotQo());

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		TicketOrderQo qo = new TicketOrderQo();
		TicketPolicySnapshotQo tpsQo = new TicketPolicySnapshotQo();
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();

		GroupTicketQo gqo = new GroupTicketQo();
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(DealerSessionUserManager.getSessionUserId(request));
		qo.setDealerQo(dealerQo);

		// 关联景区
		gqo.setTicketOrdeQo(qo);
		// HgLogger.getInstance().info("zzx", "进入订单分页查询方法:ticketOrderQo【" +
		// ticketOrderQo + "】");

		if (StringUtils.isNotBlank(ticketPolicyName)) {// 票务名称
			tpsQo.setName(ticketPolicyName);
		}

		if (ticketPolicyType != null) {
			tpsQo.setType(ticketPolicyType);
		}
		gqo.setTicketPolicySnapshotQo(tpsQo);
		if (StringUtils.isNotBlank(linkMan)) {// 游客姓名
			qo.setLinkMan(linkMan);
		}
		// 景区id查询条件不为空时才进行singleTicket左连接查询，否则会造成重复的groupTicket，导致分页数字不好弄
		if (StringUtils.isNotBlank(scenicSpotId)) {
			SingleTicketQo singleTicketQo = new SingleTicketQo();
			singleTicketQo.setScenicSpotQo(scenicSpotQo);
			scenicSpotQo.setId(scenicSpotId);
			gqo.setSingleTicketQo(singleTicketQo);
		}
		if (StringUtils.isNotBlank(orderId)) {// 订单号
			qo.setOrderId(orderId);
		}
		if (orderStatus != null) {// 订单状态
		// qo.setStatus(orderStatus);
			gqo.setStatus(orderStatus);
		}

		if (StringUtils.isNotBlank(createBeginDateStr)) {// 下单开始时间
			try {
				qo.setCreateBeginDate(sdf.parse(createBeginDateStr
						+ " 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setCreateBeginDate(null);
			}
		}

		if (StringUtils.isNotBlank(createEndDateStr)) {// 下单结束时间
			try {
				qo.setCreateEndDate(sdf.parse(createEndDateStr + " 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setCreateEndDate(null);
			}
		}

		Pagination pagination = new Pagination();

		if (pageNum != null) {
			qo.setPageNo(pageNum);
			gqo.setPageNo(pageNum);
			pagination.setPageNo(pageNum);

		} else {
			pagination.setPageNo(1);
			qo.setPageNo(1);
			gqo.setPageNo(1);
		}

		if (pageSize != null) {
			pagination.setPageSize(pageSize);
		} else {
			pagination.setPageSize(20);
		}

		pagination.setCondition(gqo);

		pagination = groupTicketLocalService.queryPagination(pagination);
		ModelAndView mav = new ModelAndView("/order/order_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("scenicSpotList", scenicSpotList);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("ticketPolicyType", ticketPolicyType);
		mav.addObject("scenicSpotId", scenicSpotId);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("linkMan", linkMan);
		mav.addObject("orderId", orderId);
		mav.addObject("orderStatus", orderStatus);
		return mav;
	}

	@RequestMapping("/order/info")
	public ModelAndView orderInfo(
			@RequestParam(value = "orderId", required = true) String orderId) {

		ModelAndView mav = new ModelAndView("/order/order_info.html");

		if (StringUtils.isNotBlank(orderId)) {

			GroupTicketQo groupTicketQo = new GroupTicketQo();
			TicketOrderQo ticketOrderQo = new TicketOrderQo();
			groupTicketQo.setId(orderId);
			ticketOrderQo
					.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
			ticketOrderQo.setDealerQo(new DealerQo());
			groupTicketQo.setTicketOrdeQo(ticketOrderQo);
			SingleTicketQo singleTicketQo = new SingleTicketQo();
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			TicketPolicySnapshotQo tspQo = new TicketPolicySnapshotQo();
			tspQo.setScenicSpotQo(scenicSpotQo);
			singleTicketQo.setTourQo(new TouristQo());
			singleTicketQo.setTicketPolicySnapshotQo(tspQo);
			groupTicketQo.setSingleTicketQo(singleTicketQo);
			List<GroupTicket> groupTicketList = groupTicketLocalService
					.queryList(groupTicketQo);
			GroupTicket groupTicket = groupTicketList.get(0);

			// 游玩时间
			for (SingleTicket singleTicket : groupTicket.getSingleTickets()) {
				UseRecordQo uqo = new UseRecordQo();
				uqo.setSingleTicketId(singleTicket.getId());
				uqo.setGroupTicketId(groupTicketList.get(0).getId());
				List<UseRecord> useRecordList = useRecordLocalService
						.queryList(uqo);
				singleTicket.setUseRecordList(useRecordList);
			}
			mav.addObject("groupTicket", groupTicket);
		}
		return mav;
	}

	/**
	 * 待激活订单列表
	 * 
	 * @author CaiHuan
	 * @return
	 */
	@RequestMapping("/order/activeList")
	public String activeList(
			HttpServletRequest request,
			Model model,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "ticketId", required = false) String ticketId,
			@RequestParam(value = "ticketPolicyType", required = false) Integer ticketPolicyType,
			@RequestParam(value = "ticketPolicyName", required = false) String ticketPolicyName,
			@RequestParam(value = "scenicSpotId", required = false) String scenicSpotId,
			@RequestParam(value = "linkMan", required = false) String linkMan,
			@RequestParam(value = "createBeginDateStr", required = false) String createBeginDateStr,
			@RequestParam(value = "createEndDateStr", required = false) String createEndDateStr) {
		// 景区列表
		List<ScenicSpot> scenicSpotList = scenicSpotLocalService
				.queryList(new ScenicSpotQo());
		Pagination pagination = new Pagination();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if (pageNum != null) {
			pagination.setPageNo(pageNum);

		} else {
			pagination.setPageNo(1);
		}

		if (pageSize != null) {
			pagination.setPageSize(pageSize);
		} else {
			pagination.setPageSize(20);
		}
		// SingleTicket
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		TicketOrderQo ticketOrderQo = new TicketOrderQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		TicketPolicySnapshotQo ticketPolicySnapshotQo = new TicketPolicySnapshotQo();
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(DealerSessionUserManager.getSessionUserId(request));
		ticketOrderQo.setDealerQo(dealerQo);
		groupTicketQo.setTicketOrdeQo(ticketOrderQo);
		groupTicketQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		singleTicketQo.setTicketPolicySnapshotQo(ticketPolicySnapshotQo);
		ticketPolicySnapshotQo.setScenicSpotQo(new ScenicSpotQo());
		// 单票为待激活状态
		singleTicketQo.setStatus(0);

		if (StringUtils.isNotBlank(orderId)) {
			ticketOrderQo.setOrderId(orderId);
		}
		if (StringUtils.isNotBlank(ticketId)) {
			groupTicketQo.setTicketNo(ticketId);
		}
		if (StringUtils.isNotBlank(ticketPolicyName)) {// 票务名称
			groupTicketQo.getTicketPolicySnapshotQo().setName(ticketPolicyName);
		}

		if (ticketPolicyType != null) {
			groupTicketQo.getTicketPolicySnapshotQo().setType(ticketPolicyType);
		}
		if (StringUtils.isNotBlank(scenicSpotId)) {
			ticketPolicySnapshotQo.getScenicSpotQo().setId(scenicSpotId);
		}
		if (StringUtils.isNotBlank(linkMan)) {
			ticketOrderQo.setLinkMan(linkMan);
		}
		if (StringUtils.isNotBlank(createBeginDateStr)) {// 下单开始时间
			try {
				ticketOrderQo.setCreateBeginDate(sdf.parse(createBeginDateStr
						+ " 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				ticketOrderQo.setCreateBeginDate(null);
			}
		}

		if (StringUtils.isNotBlank(createEndDateStr)) {// 下单结束时间
			try {
				ticketOrderQo.setCreateEndDate(sdf.parse(createEndDateStr
						+ " 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				ticketOrderQo.setCreateEndDate(null);
			}
		}

		pagination.setCondition(singleTicketQo);
		pagination = singleTicketLocalService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		model.addAttribute("orderId", orderId);
		model.addAttribute("ticketId", ticketId);
		model.addAttribute("ticketPolicyName", ticketPolicyName);
		model.addAttribute("ticketPolicyType", ticketPolicyType);
		model.addAttribute("scenicSpotId", scenicSpotId);
		model.addAttribute("linkMan", linkMan);
		model.addAttribute("createBeginDateStr", createBeginDateStr);
		model.addAttribute("createEndDateStr", createEndDateStr);
		model.addAttribute("scenicSpotList", scenicSpotList);
		return "/order/order_active.html";
	}

	/**
	 * 票务激活弹出框页面
	 * 
	 * @author CaiHuan
	 * @param singTicketId
	 * @return
	 */
	@RequestMapping("/order/toActive")
	public String toActive(String singleTicketId, Model model) {

		model.addAttribute("singleTicketId", singleTicketId);
		return "/order/toActive.html";
	}

	/**
	 * 票务激活
	 * 
	 * @author CaiHuan
	 * @param request
	 * @param singleTicketId
	 * @param touristName
	 * @param idType
	 * @param idNo
	 * @return
	 */
	@RequestMapping("/order/active")
	public @ResponseBody
	String active(HttpServletRequest request, String singleTicketId,
			String touristName, Integer idType, String idNo) {
		JSONObject o = new JSONObject();
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(DealerSessionUserManager.getSessionUserId(request));
		TicketOrderQo ticketOrdeQo = new TicketOrderQo();
		ticketOrdeQo.setDealerQo(dealerQo);
		groupTicketQo.setTicketOrdeQo(ticketOrdeQo);
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		singleTicketQo.setId(singleTicketId);
		singleTicketQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
		SingleTicket singleTicket = singleTicketLocalService
				.queryUnique(singleTicketQo);
		if (singleTicket == null) {
			o.put("message", "不存在票务！！");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		if (singleTicket.getStatus().getCurrent() != 0) {
			o.put("message", "该票务已激活或交易关闭，激活失败！！");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		PlatformModifySingleTicketCommand command = new PlatformModifySingleTicketCommand();
		command.setSingleTicketId(singleTicketId);
		command.setTouristName(touristName);
		command.setIdType(idType);
		command.setIdNo(idNo);
		command.setStatus(SingleTicketStatus.SINGLE_TICKET_CURRENT_UNUSE); // 状态待激活改为1
																			// 待游玩
		try {
			singleTicketLocalService.platformModifySingleTicket(command,
					singleTicket.getSettlementInfo().getDealerPrice());

			o.put("message", "激活成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", "activeList");
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}
		return o.toJSONString();
	}

	/**
	 * 待激活订单导出
	 * 
	 * @author CaiHuan
	 */
	@ResponseBody
	@RequestMapping("/order/export")
	public void export(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "pageNum", required = false) Integer pageNum,
			@RequestParam(value = "numPerPage", required = false) Integer pageSize,
			@RequestParam(value = "orderId", required = false) String orderId,
			@RequestParam(value = "ticketId", required = false) String ticketId,
			@RequestParam(value = "ticketPolicyType", required = false) Integer ticketPolicyType,
			@RequestParam(value = "ticketPolicyName", required = false) String ticketPolicyName,
			@RequestParam(value = "scenicSpotId", required = false) String scenicSpotId,
			@RequestParam(value = "linkMan", required = false) String linkMan,
			@RequestParam(value = "createBeginDateStr", required = false) String createBeginDateStr,
			@RequestParam(value = "createEndDateStr", required = false) String createEndDateStr) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		TicketOrderQo ticketOrderQo = new TicketOrderQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		TicketPolicySnapshotQo ticketPolicySnapshotQo = new TicketPolicySnapshotQo();
		DealerQo dealerQo = new DealerQo();
		dealerQo.setId(DealerSessionUserManager.getSessionUserId(request));
		ticketOrderQo.setDealerQo(dealerQo);
		// ticketOrderQo.setTicketPolicySnapshotQo(ticketPolicySnapshotQo);
		groupTicketQo.setTicketOrdeQo(ticketOrderQo);
		groupTicketQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		singleTicketQo.setTicketPolicySnapshotQo(ticketPolicySnapshotQo);
		ticketPolicySnapshotQo.setScenicSpotQo(new ScenicSpotQo());
		// 单票为待激活状态
		singleTicketQo.setStatus(0);

		if (StringUtils.isNotBlank(orderId)) {
			ticketOrderQo.setOrderId(orderId);
		}
		if (StringUtils.isNotBlank(ticketId)) {
			groupTicketQo.setTicketNo(ticketId);
		}
		if (StringUtils.isNotBlank(ticketPolicyName)) {// 票务名称
			groupTicketQo.getTicketPolicySnapshotQo().setName(ticketPolicyName);
		}

		if (ticketPolicyType != null) {
			groupTicketQo.getTicketPolicySnapshotQo().setType(ticketPolicyType);
		}
		if (StringUtils.isNotBlank(scenicSpotId)) {
			ticketPolicySnapshotQo.getScenicSpotQo().setId(scenicSpotId);
		}
		if (StringUtils.isNotBlank(linkMan)) {
			ticketOrderQo.setLinkMan(linkMan);
		}
		if (StringUtils.isNotBlank(createBeginDateStr)) {// 下单开始时间
			try {
				ticketOrderQo.setCreateBeginDate(sdf.parse(createBeginDateStr
						+ " 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				ticketOrderQo.setCreateBeginDate(null);
			}
		}

		if (StringUtils.isNotBlank(createEndDateStr)) {// 下单结束时间
			try {
				ticketOrderQo.setCreateEndDate(sdf.parse(createEndDateStr
						+ " 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				ticketOrderQo.setCreateEndDate(null);
			}
		}

		try {
			File file = singleTicketLocalService
					.exportActiveOrderToExcel(singleTicketQo);
			FileInputStream fis = new FileInputStream(file);
			try {
				// 设置输出的格式
				response.reset();
				response.setContentType("application/vnd.ms-excel MSEXCEL");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + StringUtil.getRandomName()
								+ ".xls" + "\"");
				// 循环取出流中的数据
				byte[] b = new byte[100];
				int len;
				while ((len = fis.read(b)) > 0) {
					response.getOutputStream().write(b, 0, len);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fis.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导入激活弹出框页面
	 * 
	 * @author CaiHuan
	 * @return
	 */
	@RequestMapping("/order/toImportActive")
	public String toImportActive() {
		return "/order/to_import.html";
	}

	/**
	 * 导入激活
	 * 
	 * @author CaiHuan
	 * @return
	 */
	@RequestMapping("/order/importActive")
	public @ResponseBody
	String importActive(@RequestParam MultipartFile importFile,
			HttpServletRequest request) {
		if (importFile == null) {
			return DwzJsonResultUtil.createJsonString("300", "请上传excel文件",
					null, null);
		}
		String type = importFile.getContentType();
		if (!(type.equals("application/vnd.ms-excel") || type
				.equals("application/x-xls"))) {
			return DwzJsonResultUtil.createJsonString("300", "请上传excel文件",
					null, null);
		}
		ClassPathTool.getInstance();
		String tempFilePath = ClassPathTool.getWebRootPath() + File.separator
				+ "excel" + File.separator + "upload" + UUIDGenerator.getUUID()
				+ importFile.getOriginalFilename();
		tempFilePath = tempFilePath.replace("file:", "");
		File tempFile = new File(tempFilePath);
		List<List<String>> dataListList = null;
		try {
			importFile.transferTo(tempFile);
			dataListList = ExcelUtils.getExecelStringValues(tempFile);
			tempFile.delete();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return DwzJsonResultUtil
					.createJsonString("300", "导入失败", null, null);
		} catch (IOException e) {
			e.printStackTrace();
			return DwzJsonResultUtil
					.createJsonString("300", "导入失败", null, null);
		}

		List<String> title = dataListList.get(1);
		if (!(title.get(0).equals("编号") && title.get(1).equals("票务编号")
				&& title.get(2).equals("票务名称") && title.get(3).equals("票务类型") && title
				.get(4).equals("景区"))) {
			return DwzJsonResultUtil.createJsonString("300", "文件内容不正确", null,
					null);
		}
		List<SingleTicket> singleTicketList = new ArrayList<SingleTicket>();
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		TicketOrderQo ticketOrdeQo = new TicketOrderQo();
		TicketPolicySnapshotQo tspQo = new TicketPolicySnapshotQo();
		tspQo.setScenicSpotQo(new ScenicSpotQo());
		groupTicketQo.setTicketOrdeQo(ticketOrdeQo);
		singleTicketQo.setGroupTicketQo(groupTicketQo);
		singleTicketQo.setTicketPolicySnapshotQo(tspQo);
		for (int i = 2; i < dataListList.size(); i++) {
			List<String> t = dataListList.get(i);
			if (StringUtils.isBlank(t.get(0)) || StringUtils.isBlank(t.get(4))
					|| StringUtils.isBlank(t.get(8))
					|| StringUtils.isBlank(t.get(9))
					|| StringUtils.isBlank(t.get(10))) {
				return DwzJsonResultUtil.createJsonString("300", "导入失败，第"
						+ (i + 1) + "行有订单编号，景区,游玩人，证件类型，证件号码信息未填写", null, null);
			}
			String orderId = t.get(0);
			String scenicSpotName = t.get(4);
			tspQo.getScenicSpotQo().setName(scenicSpotName);
			ticketOrdeQo.setOrderId(orderId);

			SingleTicket singleTicket = singleTicketLocalService
					.queryUnique(singleTicketQo);
			if (singleTicket == null) {
				return DwzJsonResultUtil.createJsonString("300", "导入失败，第"
						+ (i + 1) + "行订单编号和景区信息不符合", null, null);
			}
			if (singleTicket.getStatus().getCurrent() != 0) {
				return DwzJsonResultUtil.createJsonString("300", "导入失败，第"
						+ (i + 1) + "行票务已激活或交易关闭，激活失败！", null, null);
			}

			Tourist tourist = new Tourist();
			tourist.setName(t.get(8));
			tourist.setIdType(1);
			tourist.setIdNo(t.get(10));
			singleTicket.setTourist(tourist);
			singleTicketList.add(singleTicket);
		}
		JSONObject o = new JSONObject();
		try {
			singleTicketLocalService.batchActive(singleTicketList);
			o.put("message", "激活成功");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
			o.put("callbackType",
					DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
			o.put("navTabId", "activeList");
		} catch (DZPWException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			o.put("callbackType", null);
			o.put("navTabId", null);
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_500);
		}

		return o.toJSONString();
	}

	/**
	 * @方法功能说明：经销商端支付
	 * @修改者名字：yangkang
	 * @修改时间：2016-3-21下午3:02:28
	 * @参数：@param orderId
	 * @参数：@return
	 * @return:String
	 */
	@ResponseBody
	@RequestMapping("/order/topay")
	public String toPay(
			@RequestParam(value = "orderId", required = false) String orderId) {

		JSONObject o = new JSONObject();

		if (StringUtils.isBlank(orderId)) {
			o.put("message", "请选择要支付的订单");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}

		TicketOrderQo qo = new TicketOrderQo();
		qo.setId(orderId);
		TicketOrder order = ticketOrderLocalService.queryUnique(qo);

		CaeChargeParameter param = new CaeChargeParameter();
		// 手续费 = 门票手续费*门票数量
		Double f = order.getBaseInfo().getFromDealer().getAccountInfo()
				.getSettlementFee()
				* order.getBaseInfo().getTicketNo();

		Double sum = MoneyUtil.add(order.getPayInfo().getPrice(), f);
		// 代扣金额
		param.setAmount(Double.toString(sum));
		// 订单创建时间
		param.setGmt_out_order_create(DateUtil.formatDateTime(order
				.getBaseInfo().getCreateDate()));
		// 商户订单号
		param.setOut_order_no(order.getId());
		// 备注
		param.setSubject(SystemConfig.alipaySubjectPrefix);
		// 支付账户
		param.setTrans_account_out(order.getBaseInfo().getFromDealer()
				.getAccountInfo().getAccountNumber());
		// 调用支付宝代扣接口
		CaeChargeResponse response = ticketOrderLocalService.payByAli(param);

		if (response == null) {
			o.put("message", "请选择要支付的订单");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}

		// 支付 失败 抛异常
		if (!response.isSuccess()) {
			if ("AVAILABLE_AMOUNT_NOT_ENOUGH".equals(response.getError())
					|| "USER_PAY_TYPE_MISMATCH".equals(response.getError())) {

				o.put("message", "余额不足");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			} else

				o.put("message", "支付失败");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		} else {
			if (!"TRADE_SUCCESS".equals(response.getStatus())
					&& !"TRADE_FINISHED".equals(response.getStatus())) {

				o.put("message", "支付失败");
				o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
				return o.toJSONString();
			}
		}

		// 支付成功
		order.getPayInfo().setPayDate(new Date());
		order.getStatus().setCurrentValue(OrderStatus.ORDER_STATUS_OUT_SUCC);
		ticketOrderLocalService.update(order);

		// 查询订单下的门票
		GroupTicketQo gtqo = new GroupTicketQo();
		gtqo.setTicketOrdeQo(new TicketOrderQo());
		gtqo.getTicketOrdeQo().setId(order.getId());
		List<GroupTicket> lg = groupTicketLocalService.queryList(gtqo);

		// 修改门票状态
		for (GroupTicket gt : lg) {
			gt.getStatus().setCurrent(
					GroupTicketStatus.GROUP_TICKET_CURRENT_OUT_SUCC);
			// 设支付流水
			gt.setPayTradeNo(response.getTrade_no());
		}
		groupTicketLocalService.saveList(lg);

		o.put("message", "支付成功");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		o.put("callbackType", null);
		o.put("navTabId", "orderList");

		return o.toJSONString();
	}

	@ResponseBody
	@RequestMapping("/order/applyrefund")
	public String applyRefund(
			@RequestParam(value = "groupTicketId", required = false) String groupTicketId) {

		JSONObject o = new JSONObject();
		if (StringUtils.isBlank(groupTicketId)) {
			o.put("message", "请选择要申请退款的门票");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}

		GroupTicketQo qo = new GroupTicketQo();
		qo.setId(groupTicketId);
		qo.setTicketOrdeQo(new TicketOrderQo());
		GroupTicket gt = groupTicketLocalService.queryUnique(qo);

		if (gt == null) {
			o.put("message", "门票不存在");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}

		if (gt.getStatus().getRefundCurrent() == null
				|| gt.getStatus().getRefundCurrent() != GroupTicketStatus.GROUP_TICKET_REFUND_CURRENT_CAN) {
			o.put("message", "门票当前状态不能申请退款");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		
		Calendar useDateCalendar = Calendar.getInstance();
		useDateCalendar.setTime(gt.getUseDateEnd());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, -1);
		if (calendar.after(useDateCalendar)) {
			o.put("message", "门票过期超一个月，不能申请退款");
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		
		
		// 添加到退款记录中，等待轮询处理
		ApplyRefundRecord record = new ApplyRefundRecord();
		record.setId(UUIDGenerator.getUUID());
		record.setGroupTicket(gt);
		record.setRecordDate(new Date());
		record.setTicketOrder(gt.getTicketOrder());
		refundRecordLocalService.save(record);

		//修改可退款门票景区状态为退款待处理
		singleTicketLocalService.updateCanRefundSingleStatus(new String[]{gt.getTicketNo()});
		
		o.put("message", "申请成功");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		return o.toJSONString();
	}

}
