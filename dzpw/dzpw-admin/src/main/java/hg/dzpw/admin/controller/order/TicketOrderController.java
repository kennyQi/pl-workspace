package hg.dzpw.admin.controller.order;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DateUtil;
import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.dao.TicketPolicySnapshotDao;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarSnapshotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.app.pojo.qo.TicketQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.app.service.local.TicketPolicySnapshotLocalService;
import hg.dzpw.app.service.local.UseRecordLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendarSnapshot;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.UseRecord;
import hg.dzpw.pojo.qo.TicketOrderListQo;
import hg.dzpw.pojo.vo.TicketPolicyCheckVo;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明：订单管理
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zzx
 * @创建时间：2014-11-10下午4:47:48
 * @版本：V1.0
 */
@Controller
@RequestMapping(value="/order/ticketOrder")
public class TicketOrderController extends BaseController {
	
	@Autowired
	private TicketOrderLocalService ticketOrderLocalService;
	@Autowired
	private DealerLocalService dealerService;
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	@Autowired
	private UseRecordLocalService useRecordService;
	@Autowired
	private TicketPolicyPriceCalendarLocalService ticketPolicyDatePriceService;
	@Autowired
	private SingleTicketLocalService singleTicketService;
	@Autowired
	private TicketPolicySnapshotLocalService ticketPolicySnapshotLocalService;
	@Autowired
	private UseRecordLocalService useRecordLocalService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	public final static String PAGE_PATH_LIST = "/order/ticket_order_list.html";
	
	public final static String PAGE_PATH_VIEW = "/order/ticket_order_view.html";
	
	public final static String PAGE_SETTLE_DETAIL = "/order/settle_detail.html";
	
	/**
	 * 订单列表
	 * @param request
	 * @param response
	 * @param model
	 * @param articleQo
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping(value="/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
					   @RequestParam(value="numPerPage", required = false)Integer pageSize,
					   @RequestParam(value="ticketPolicyName", required=false)String ticketPolicyName,
					   @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType,
					   @RequestParam(value="dealerId", required=false)String dealerId,
					   @RequestParam(value="createBeginDateStr", required=false)String createBeginDateStr,
					   @RequestParam(value="createEndDateStr", required=false)String createEndDateStr,
					   @RequestParam(value="linkMan", required=false)String linkMan,
					   @RequestParam(value="orderId", required=false)String orderId,
					   @RequestParam(value="scenicSpotId", required=false)String scenicSpotId,
					   @RequestParam(value="orderStatus", required=false)Integer orderStatus) {
		
		//景区列表
		List<ScenicSpot> scenicSpotList = scenicSpotLocalService.queryList(new ScenicSpotQo());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TicketOrderQo qo = new TicketOrderQo();
		TicketPolicySnapshotQo tpsQo = new TicketPolicySnapshotQo();
		GroupTicketQo gqo = new GroupTicketQo();
		DealerQo dealerQo = new DealerQo();
		
		gqo.setTicketOrdeQo(qo);
		//		HgLogger.getInstance().info("zzx", "进入订单分页查询方法:ticketOrderQo【" + ticketOrderQo + "】");

		//获取经销商列表
		DealerQo dealerQo1 = new DealerQo();
		dealerQo1.setOrderDesc(true);
		List<Dealer> dealerList = dealerService.queryList(dealerQo1);
		
		if(StringUtils.isNotBlank(ticketPolicyName)){//票务名称
			tpsQo.setName(ticketPolicyName);
		}
		
		if(ticketPolicyType!=null){
			tpsQo.setType(ticketPolicyType);
		}
		gqo.setTicketPolicySnapshotQo(tpsQo);
		if(StringUtils.isNotBlank(dealerId)){//经销商
			dealerQo.setId(dealerId);
		}
		
		//景区
		if(StringUtils.isNotBlank(scenicSpotId))
		{
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			scenicSpotQo.setId(scenicSpotId);
			gqo.setSingleTicketQo(new SingleTicketQo());
			gqo.getSingleTicketQo().setScenicSpotQo(scenicSpotQo);
			
		}
		
		qo.setDealerQo(dealerQo);
		if(StringUtils.isNotBlank(linkMan)){//游客姓名
			qo.setLinkMan(linkMan);
		}
		if(StringUtils.isNotBlank(orderId)){//订单号
			qo.setOrderId(orderId);
		}
		if(orderStatus!=null){//订单状态
			gqo.setStatus(orderStatus);
		}
		
		if(StringUtils.isNotBlank(createBeginDateStr)){//下单开始时间
			try {
				qo.setCreateBeginDate(sdf.parse(createBeginDateStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setCreateBeginDate(null);
			}
		}
		
		if(StringUtils.isNotBlank(createEndDateStr)){//下单结束时间
			try {
				qo.setCreateEndDate(sdf.parse(createEndDateStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				qo.setCreateEndDate(null);
			}
		}
		
		Pagination pagination = new Pagination();
		
		if(pageNum!=null){
			qo.setPageNo(pageNum);
			gqo.setPageNo(pageNum);
			pagination.setPageNo(pageNum);
			
		}else{
			pagination.setPageNo(1);
			qo.setPageNo(1);
			gqo.setPageNo(1);
		}
		
		if(pageSize!=null){
			qo.setPageSize(pageSize);
			gqo.setPageSize(pageSize);
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
			gqo.setPageSize(20);
			qo.setPageSize(20);
		}
		
		pagination.setCondition(gqo);
//		pagination = ticketOrderLocalService.queryListVoPagination(qo);老方法
		pagination = groupTicketLocalService.queryPagination(pagination);
		ModelAndView mav = new ModelAndView(PAGE_PATH_LIST);
		mav.addObject("pagination", pagination);
		mav.addObject("dealerList", dealerList);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("ticketPolicyType", ticketPolicyType);
		mav.addObject("dealerId", dealerId);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("linkMan", linkMan);
		mav.addObject("orderId", orderId);
		mav.addObject("scenicSpotList", scenicSpotList);
		mav.addObject("scenicSpotId", scenicSpotId);
		mav.addObject("orderStatus", orderStatus);
		return mav;
	}
	
	/**
	 * 订单详情页面 根据groupTicketId
	 * @param request
	 * @param response
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/view/{groupTicketId}")
	public String preview(HttpServletRequest request, 
						  HttpServletResponse response,
						  Model model, @PathVariable String groupTicketId){
		
		HgLogger.getInstance().info("zzx", "进入订单详情查询方法:groupTicketId【" + groupTicketId + "】");
		
		if(StringUtils.isNotBlank(groupTicketId)){
			GroupTicketQo groupTicketQo = new GroupTicketQo();
			TicketOrderQo ticketOrdeQo = new TicketOrderQo();
			ticketOrdeQo.setDealerQo(new DealerQo());
			TicketPolicySnapshotQo ticketPolicySnapshotQo = new TicketPolicySnapshotQo();
			ticketPolicySnapshotQo.setScenicSpotQo(new ScenicSpotQo());
			ticketOrdeQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
			SingleTicketQo singleTicketQo = new SingleTicketQo();
			groupTicketQo.setTicketOrdeQo(ticketOrdeQo);
			//设置政策类型
			groupTicketQo.setId(groupTicketId);
			singleTicketQo.setTourQo(new TouristQo());
			singleTicketQo.setTicketPolicySnapshotQo(ticketPolicySnapshotQo);
			groupTicketQo.setSingleTicketQo(singleTicketQo);
			List<GroupTicket> groupTicketList = groupTicketLocalService.queryList(groupTicketQo);
			
			//游玩记录
			for(SingleTicket singleTicket:groupTicketList.get(0).getSingleTickets())
			{
				UseRecordQo uqo = new UseRecordQo();
				uqo.setSingleTicketId(singleTicket.getId());
				uqo.setGroupTicketId(groupTicketList.get(0).getId());
				List<UseRecord> useRecordList = useRecordLocalService.queryList(uqo);
				singleTicket.setUseRecordList(useRecordList);
			}
			model.addAttribute("groupTicket", groupTicketList.get(0));
			model.addAttribute("singleTicketList", groupTicketList.get(0).getSingleTickets());
			model.addAttribute("nowDate", new Date());
		}

		return PAGE_PATH_VIEW;
	}
	
	@RequestMapping("/settleDetail")
	public String settleDetail(String id,
			Model model)
	{
		
		return PAGE_SETTLE_DETAIL;
	}
	
	/**
	 * 订单详情，根据订单编号，singleTicketId
	 * @author CaiHuan
	 * @param orderId
	 * @param singleTicketId
	 * @param model
	 * @return
	 */
	@RequestMapping("/detail")
	public String detail(String orderId,String singleTicketId,Model model)
	{
		TicketOrderQo ticketOrderQo = new TicketOrderQo();
		ticketOrderQo.setId(orderId);
		ticketOrderQo.setDealerQo(new DealerQo());
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		GroupTicketQo groupTicketQo = new GroupTicketQo();
		groupTicketQo.setSingleTicketQo(singleTicketQo);
		groupTicketQo.setTicketOrdeQo(ticketOrderQo);
		singleTicketQo.setId(singleTicketId);
		groupTicketQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
		singleTicketQo.setScenicSpotQo(new ScenicSpotQo());
	    singleTicketQo.setTourQo(new TouristQo());
	    List<GroupTicket > groupTicketList = groupTicketLocalService.queryList(groupTicketQo);
	    model.addAttribute("groupTicket", groupTicketList.get(0));
		model.addAttribute("singleTicketList", groupTicketList.get(0).getSingleTickets());
		return PAGE_PATH_VIEW;
	}
	
	/**
	 * 结算
	 * @author CaiHuan
	 * @param singleTicketId
	 * @return
	 */
	@RequestMapping("/settle")
	public @ResponseBody String settle(String singleTicketId,String navTabId)
	{
		JSONObject o = new JSONObject();
		try {
			singleTicketService.settle(singleTicketId);
		} catch (Exception e) {
			o.put("message", e.getMessage());
			o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_300);
			return o.toJSONString();
		}
		o.put("message", "结算成功");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		if(StringUtils.isNotBlank(navTabId))
		{
			o.put("navTabId", navTabId);
		}
		else
		o.put("navTabId", "ticketOrder");
		return o.toJSONString();
	}
}
