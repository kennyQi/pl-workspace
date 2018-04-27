package hg.dzpw.admin.controller.ticket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hg.common.page.Pagination;
import hg.dzpw.admin.common.util.DatePriceStrUtil;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.log.domainevent.DomainEventDao;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketSingleController {
	
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	
	@Autowired
	private TicketPolicyLocalService tickSer;
	
	@Autowired
	private ScenicSpotLocalService scenSer;
	
	@Autowired
	private DomainEventDao eventDao;
	
	@Autowired
	private TicketPolicyPriceCalendarLocalService priceCalendarLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	@RequestMapping(value = "/ticket-single/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
            		   @RequestParam(value="numPerPage", required = false)Integer pageSize,
					   @RequestParam(value="createBeginDateStr", required=false)String createBeginDateStr,
					   @RequestParam(value="createEndDateStr", required=false)String createEndDateStr,
					   @RequestParam(value="ticketPolicyName", required=false)String ticketPolicyName,
					   @RequestParam(value="status", required=false)String status,
					   @RequestParam(value="scenicSpotName", required=false)String scenicSpotName,
					   @RequestParam(value="key", required=false)String key){
		
		ModelAndView mav = new ModelAndView("/ticket/single/list.html");
		
		TicketPolicyQo tpQo = new TicketPolicyQo();
		tpQo.setCreateDateSort(-1);
		tpQo.setKey(key);
		tpQo.setTicketPolicyName(ticketPolicyName);
		tpQo.setTicketPolicyNameLike(true);
		tpQo.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		//查询景区
		if(StringUtils.isNotBlank(scenicSpotName)){
			ScenicSpotQo ssqo = new ScenicSpotQo();
			ssqo.setName(scenicSpotName);
			ssqo.setNameLike(true);
			tpQo.setScenicSpotQo(ssqo);
		}
		
		// 启用
		if ("0".equals(status)) {
			tpQo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE);
		}
		// 禁用
		else if ("1".equals(status)) {
			tpQo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
		}
		
		//添加时间
		if(StringUtils.isNotBlank(createBeginDateStr)){
			try {
				tpQo.setCreateDateBegin(sdf.parse(createBeginDateStr+" 00:00:00"));
			} catch (ParseException e) {
				e.printStackTrace();
				tpQo.setCreateDateBegin(null);
			}
		}
		if(StringUtils.isNotBlank(createEndDateStr)){
			try {
				tpQo.setCreateDateEnd(sdf.parse(createEndDateStr+" 23:59:59"));
			} catch (ParseException e) {
				e.printStackTrace();
				tpQo.setCreateDateEnd(null);
			}
		}
		
		Pagination pagination = new Pagination();
		pagination.setCondition(tpQo);
		
		if(pageNum!=null){
			pagination.setPageNo(pageNum);
		}else{
			pagination.setPageNo(1);
		}
		if(pageSize!=null){
			pagination.setPageSize(pageSize);
		}else{
			pagination.setPageSize(20);
		}
		
		pagination = tickSer.queryPagination(pagination);
		
		
		mav.addObject("pagination", pagination);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("status", status);
		mav.addObject("scenicSpotName", scenicSpotName);
		mav.addObject("key", key);
		return  mav;
	}
	
	
	
	@RequestMapping(value="/ticket-single/info")
	public ModelAndView singleTicketInfo(@RequestParam(value="id", required = false)String id){
		
		ModelAndView mav = new ModelAndView("/ticket/single/info.html");
		
		TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
		ticketPolicyQo.setId(id);
		ticketPolicyQo.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		TicketPolicy ticketPolicy = tickSer.queryUnique(ticketPolicyQo);
		
		mav.addObject("ticketPolicy", ticketPolicy);
		return mav;
	}
	
	
	@RequestMapping(value="/ticket-single/datePrice/page")
	public ModelAndView datePricePage(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId){
		
		ModelAndView mav = new ModelAndView("/ticket/single/datePriceView.html");
		mav.addObject("ticketPolicyId", ticketPolicyId);
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
			ticketPolicyQo.setId(ticketPolicyId);
			
			TicketPolicyPriceCalendarQo datePriceQo = new TicketPolicyPriceCalendarQo();
			datePriceQo.setTicketPolicyQo(ticketPolicyQo);
			datePriceQo.setDealerFetch(true);
			//查询单票政策下的所有价格日历
			List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(datePriceQo);
			
			List<Dealer> dealerList = new ArrayList<Dealer>(); 
			for(TicketPolicyPriceCalendar datePrice : list){
				//基准价价格日历
				if(datePrice.getStandardPrice()){
					mav.addObject("priceStr", DatePriceStrUtil.datePriceToStr(datePrice.getPriceJson()));
					continue;
				}
				
				dealerList.add(datePrice.getDealer());
			}
			mav.addObject("dealerList", dealerList);
		}
		return mav;
	}
	
	
	@RequestMapping(value="/ticket-single/dealerDatePrice/page")
	public ModelAndView dealerDatePricePage(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
											@RequestParam(value="dealerId", required=false)String dealerId){
		
		ModelAndView mav = new ModelAndView("/ticket/single/dealerDatePriceView.html");
		
		if(StringUtils.isNotBlank(ticketPolicyId) && StringUtils.isNotBlank(dealerId)){
			
			TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
			ticketPolicyQo.setId(ticketPolicyId);
			
			TicketPolicyPriceCalendarQo datePriceQo = new TicketPolicyPriceCalendarQo();
			datePriceQo.setTicketPolicyQo(ticketPolicyQo);
			DealerQo dQo = new DealerQo();
			dQo.setId(dealerId);
			datePriceQo.setDealerQo(dQo);
			//查询单票对经销商的价格日历
			TicketPolicyPriceCalendar tppc = priceCalendarLocalService.queryUnique(datePriceQo);
			
			if(tppc!=null){
				mav.addObject("dpriceStr", DatePriceStrUtil.datePriceToStr(tppc.getPriceJson()));
			}
		}
		
		return mav;
	}
}
