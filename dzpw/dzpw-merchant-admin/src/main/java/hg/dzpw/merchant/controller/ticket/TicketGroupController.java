package hg.dzpw.merchant.controller.ticket;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.log.util.HgLogger;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TicketGroupController extends BaseController {
	
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	
	@Autowired
	private TicketPolicyLocalService  ticketPolicyLocalService;
	
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	@Autowired
	private TicketPolicyPriceCalendarLocalService priceCalendarLocalService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * @方法功能说明：联票列表
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-7上午11:02:28
	 */
	@RequestMapping("/groupTicket/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
 		   					 @RequestParam(value="numPerPage", required = false)Integer pageSize,
 		   					 @RequestParam(value="createBeginDateStr", required=false)String createBeginDateStr,
 		   					 @RequestParam(value="createEndDateStr", required=false)String createEndDateStr,
 		   					 @RequestParam(value="ticketPolicyName", required=false)String ticketPolicyName,
 		   					 @RequestParam(value="status", required=false)Integer status,
 		   					 @RequestParam(value="scenicSpotStr", required=false)String scenicSpotStr,
 		   					 HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/groupTicket/groupTicket_list.html");
		TicketPolicyQo tpQo = new TicketPolicyQo();
		
		
		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		if(StringUtils.isBlank(scenicSpotId))
			return mav;
		
		ScenicSpotQo sqo = new ScenicSpotQo();
		sqo.setId(scenicSpotId);
		
		ScenicSpot scenicSpot = scenicSpotLocalService.queryUnique(sqo);
		if(scenicSpot==null)
			return mav;
		
		tpQo.setScenicSpotStr(scenicSpot.getBaseInfo().getName());
		tpQo.setType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		//创建时间(排序：>0 asc <0 desc)
		tpQo.setCreateDateSort(-1);
		
		
		//单票名称
		if(StringUtils.isNotBlank(ticketPolicyName)){
			tpQo.setTicketPolicyName(ticketPolicyName);
			tpQo.setTicketPolicyNameLike(true);
		}
		
		//发布时间开始
		if(StringUtils.isNotBlank(createBeginDateStr)){
			try {
				tpQo.setCreateDateBegin(sdf.parse(createBeginDateStr+" 00:00:00"));
			} catch (Exception e) {
				e.printStackTrace();
				tpQo.setCreateDateBegin(null);
			}
		}
				
		//发布时间范围结束
		if(StringUtils.isNotBlank(createEndDateStr)){
			try {
				tpQo.setCreateDateEnd(sdf.parse(createEndDateStr+" 23:59:59"));
			} catch (Exception e) {
				e.printStackTrace();
				tpQo.setCreateDateEnd(null);
			}
		}
		
		if(StringUtils.isNotBlank(scenicSpotStr)){
			tpQo.setScenicSpotStr(scenicSpotStr);
		}
		
		
		if(status!=null){
			tpQo.setStatus(status);
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
		
		pagination = ticketPolicyLocalService.queryPagination(pagination);
		
		
		mav.addObject("pagination", pagination);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("status", status);
		mav.addObject("scenicSpotStr", scenicSpotStr);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：查看详情
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-7上午11:02:13
	 * @参数：@param ticketPolicyId
	 * @参数：@return
	 * @return:ModelAndView
	 */
	@RequestMapping("/groupTicket/info")
	public ModelAndView info(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId){
		
		ModelAndView mav = new ModelAndView("/groupTicket/groupTicket_info.html");
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			
			TicketPolicyQo qo = new TicketPolicyQo();
			qo.setId(ticketPolicyId);
			
			TicketPolicy policy = ticketPolicyLocalService.queryUnique(qo);
			mav.addObject("policy", policy);
			
			//查询对应的经销商价格日历
			TicketPolicyPriceCalendarQo priceQo = new TicketPolicyPriceCalendarQo();
			priceQo.setDealerQo(new DealerQo());
			priceQo.setTicketPolicyQo(qo);
			
			List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(priceQo);
			if(list!=null && list.size()>0)
				mav.addObject("priceList",list);
		}
		
		return mav;
	}
	
	
	
	
}
