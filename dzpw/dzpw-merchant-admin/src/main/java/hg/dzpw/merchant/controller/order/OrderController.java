package hg.dzpw.merchant.controller.order;

import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.SingleTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TicketPolicySnapshotQo;
import hg.dzpw.app.pojo.qo.TouristQo;
import hg.dzpw.app.pojo.qo.UseRecordQo;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.SingleTicketLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.UseRecordLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.order.TicketOrder;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicySnapshot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicket;
import hg.dzpw.domain.model.ticket.UseRecord;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.pojo.qo.TicketOrderListQo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * @类功能说明：景区订单管理
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
public class OrderController extends BaseController{
	
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
	private UseRecordLocalService useRecordLocalService;
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/order/list")
	public ModelAndView orderList(HttpServletRequest request,
								  @RequestParam(value="pageNum", required = false)Integer pageNum,
            					  @RequestParam(value="numPerPage", required = false)Integer pageSize,
            					  @RequestParam(value="ticketPolicyName", required=false)String ticketPolicyName,
								  @RequestParam(value="ticketPolicyType", required=false)Integer ticketPolicyType,
								  @RequestParam(value="dealerId", required=false)String dealerId,
								  @RequestParam(value="createBeginDateStr", required=false)String createBeginDateStr,
								  @RequestParam(value="createEndDateStr", required=false)String createEndDateStr,
								  @RequestParam(value="linkMan", required=false)String linkMan,
								  @RequestParam(value="orderId", required=false)String orderId,
								  @RequestParam(value="dealerOrderId", required=false)String dealerOrderId,
								  @RequestParam(value="orderStatus", required=false)Integer orderStatus){
		
		//获取经销商列表
		DealerQo dealerQo1 = new DealerQo();
		dealerQo1.setOrderDesc(true); //按创建日期倒序
		List<Dealer> dealerList = dealerLocalService.queryList(dealerQo1);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TicketOrderQo qo = new TicketOrderQo();
		TicketPolicySnapshotQo tpsQo = new TicketPolicySnapshotQo();
		GroupTicketQo gqo = new GroupTicketQo();
		DealerQo dealerQo = new DealerQo();
		gqo.setTicketOrdeQo(qo);
		//		HgLogger.getInstance().info("zzx", "进入订单分页查询方法:ticketOrderQo【" + ticketOrderQo + "】");

		
		if(StringUtils.isNotBlank(ticketPolicyName)){//票务名称
			tpsQo.setName(ticketPolicyName);
		}
		
		if(ticketPolicyType!=null){
			tpsQo.setType(ticketPolicyType);
		}
		qo.setTicketPolicySnapshotQo(tpsQo);
		
		if(StringUtils.isNotBlank(dealerId)){//经销商
			dealerQo.setId(dealerId);
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
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
		}
		
		pagination.setCondition(gqo);
		//返回实体TicketOrderListVo
		SingleTicketQo singleTicketQo = new SingleTicketQo();
		ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
		scenicSpotQo.setId(MerchantSessionUserManager.getSessionUserId(request));
//		scenicSpotQo.setId(id);
		singleTicketQo.setScenicSpotQo(scenicSpotQo);
		gqo.setSingleTicketQo(singleTicketQo);
		
		pagination = groupTicketLocalService.queryPagination(pagination);
		
		ModelAndView mav = new ModelAndView("/order/order_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("dealerList", dealerList);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("ticketPolicyType", ticketPolicyType);
		mav.addObject("dealerId", dealerId);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("linkMan", linkMan);
		mav.addObject("orderId", orderId);
		mav.addObject("dealerOrderId", dealerOrderId);
		mav.addObject("orderStatus", orderStatus);
		return mav;
	}
	
	/**
	 * 订单详情
	 * @author CaiHuan
	 * @param groupTicketId
	 * @return
	 */
	@RequestMapping("/order/info")
	public ModelAndView orderInfo(@RequestParam(value="groupTicketId", required=true)String groupTicketId){
		
		ModelAndView mav = new ModelAndView("/order/order_info.html");
		
		if(StringUtils.isNotBlank(groupTicketId)){
			GroupTicketQo groupTicketQo = new GroupTicketQo();
			TicketOrderQo ticketOrderQo = new TicketOrderQo();
			groupTicketQo.setId(groupTicketId);
			ticketOrderQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
			ticketOrderQo.setDealerQo(new DealerQo());
			groupTicketQo.setTicketOrdeQo(ticketOrderQo);
			SingleTicketQo singleTicketQo = new SingleTicketQo();
			ScenicSpotQo scenicSpotQo = new ScenicSpotQo();
			TicketPolicySnapshotQo tspQo = new TicketPolicySnapshotQo();
			tspQo.setScenicSpotQo(scenicSpotQo);
			singleTicketQo.setTourQo(new TouristQo());
			groupTicketQo.setSingleTicketQo(singleTicketQo);
			List<GroupTicket> groupTicketList = groupTicketLocalService.queryList(groupTicketQo);
			GroupTicket groupTicket = groupTicketList.get(0);
			
			//游玩时间
			for(SingleTicket singleTicket:groupTicket.getSingleTickets())
			{
				UseRecordQo uqo = new UseRecordQo();
				uqo.setSingleTicketId(singleTicket.getId());
				uqo.setGroupTicketId(groupTicketList.get(0).getId());
				List<UseRecord> useRecordList = useRecordLocalService.queryList(uqo);
				singleTicket.setUseRecordList(useRecordList);
			}
			mav.addObject("groupTicket", groupTicket);
		}
		return mav;
	}
	
	/**
	 * 订单详情，根据订单编号，singleTicketId
	 * @author CaiHuan
	 * @param orderId
	 * @param singleTicketId
	 * @param model
	 * @return
	 */
	@RequestMapping("/order/detail")
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
//	    singleTicketQo.setTicketPolicySnapshotQo(new TicketPolicySnapshotQo());
//	    singleTicketQo.getTicketPolicySnapshotQo().setScenicSpotQo(new ScenicSpotQo());
		singleTicketQo.setScenicSpotQo(new ScenicSpotQo());
	    singleTicketQo.setTourQo(new TouristQo());
	    List<GroupTicket > groupTicketList = groupTicketLocalService.queryList(groupTicketQo);
	    
	  //游玩时间
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
		return "/order/order_info.html";
	}
}
