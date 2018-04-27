package hg.dzpw.merchant.controller.ticket;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.ScenicSpotQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.merchant.common.utils.DatePriceStrUtil;
import hg.dzpw.merchant.component.manager.MerchantSessionUserManager;
import hg.dzpw.merchant.controller.BaseController;
import hg.dzpw.pojo.command.merchant.policy.DatePrice;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotChangeSingleTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotCreateSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotModifySingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotRemoveSingleTicketPolicyCommand;
import hg.dzpw.pojo.command.merchant.policy.ScenicspotSetSingleTicketPolicyPriceCommand;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;




/**
 * @类功能说明: 单票政策
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：liusong
 * @创建时间：2014-11-11 上午9:57:47
 */
@Controller
public class TicketSingleController extends BaseController {
	
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	@Autowired
	private TicketPolicyLocalService  ticketPolicyLocalService;
	@Autowired
	private TicketPolicyPriceCalendarLocalService priceCalendarLocalService;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**金额正则*/
	private static final String patten = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	/**连续游玩天数*/
	private static final String patten2 = "[1-9]{1,}\\d*";
	
	
	@ResponseBody
	@RequestMapping("/singleTicket/showDateSalePrice")
	public ModelAndView showDateSalePrice(@RequestParam(value="id", required = false)String id,HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/singleTicket/datePrice.html");
		mav.addObject("ticketPolicyId", id);
		
		if(StringUtils.isNotBlank(id)){
			TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
			ticketPolicyQo.setId(id);
			
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
	
	
	/**
	 * @方法功能说明: 独立单票列表检索
	 * @param name   单票名称
	 * @param beginTimeStr  发布时间起始
	 * @param endTimeStr    发布时间结束
	 * @param status    启用状态
	 * @return
	 */
	@RequestMapping(value = "/singleTicket/list")
	public ModelAndView list(HttpServletRequest request,
							@RequestParam(value="pageNum", required = false)Integer pageNum,
				            @RequestParam(value="numPerPage", required = false)Integer pageSize,
				            @RequestParam(value="ticketPolicyName", required = false)String ticketPolicyName,
				            @RequestParam(value="key", required = false)String key,
				            @RequestParam(value="createBeginDateStr", required = false)String createBeginDateStr,
				            @RequestParam(value="createEndDateStr", required = false)String createEndDateStr,
				            @RequestParam(value="status", required = false)Integer status) {
		
		TicketPolicyQo policyQo = new TicketPolicyQo();
		policyQo.setCreateDateSort(-1);
		policyQo.setType(GroupTicket.GROUP_TICKET_TYPE_SINGLE_SCENIC_SPOT);

		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		ScenicSpotQo sqo = new ScenicSpotQo();
		sqo.setId(scenicSpotId);
		policyQo.setScenicSpotQo(sqo);
		
		//单票名称
		if(StringUtils.isNotBlank(ticketPolicyName)){
			policyQo.setTicketPolicyName(ticketPolicyName);
			policyQo.setTicketPolicyNameLike(true);
		}
		
		if(StringUtils.isNotBlank(key)){
			policyQo.setKey(key);
		}
		
		//发布时间开始
		if(StringUtils.isNotBlank(createBeginDateStr)){
			try {
				policyQo.setCreateDateBegin(sdf.parse(createBeginDateStr+" 00:00:00"));
			} catch (Exception e) {
				e.printStackTrace();
				policyQo.setCreateDateBegin(null);
			}
		}
		
		//发布时间范围结束
		if(StringUtils.isNotBlank(createEndDateStr)){
			try {
				policyQo.setCreateDateEnd(sdf.parse(createEndDateStr+" 23:59:59"));
			} catch (Exception e) {
				e.printStackTrace();
				policyQo.setCreateDateEnd(null);
			}
		}
		
		if(status!=null){
			// 启用
			if (3==status) {
				policyQo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE);
			}
			// 禁用
			else if (4==status) {
				policyQo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_FINISH);
			}
		}
		
		Pagination pagination = new Pagination();
        pagination.setCondition(policyQo);
		
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
		
		pagination = this.ticketPolicyLocalService.queryPagination(pagination);
		//返回对象视图
		ModelAndView mav = new ModelAndView("/singleTicket/singleTicket_list.html");
		mav.addObject("pagination", pagination);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("key", key);
		mav.addObject("createBeginDateStr", createBeginDateStr);
		mav.addObject("createEndDateStr", createEndDateStr);
		mav.addObject("status", status);
		return mav;
	}
	
	
	/**
	 * @方法功能说明: 跳转发布单票视图
	 * @param model
	 */
	@RequestMapping(value = "/singleTicket/toAdd")
	public String toAddSingleTicket(Model  model){
		return "/singleTicket/singleTicket_add_step1.html";
	}
	
	
	/**
	 * @方法功能说明: 发布单票业务处理(创建独立单票政策)
	 * @param ScenicspotCreateSingleTicketPolicyCommand
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/addSubmit")
	public String createSingleTicket(HttpServletRequest request,
									 @RequestParam(value="name", required=false)String name,
									 @RequestParam(value="hiddenNotice", required=false)String notice,
									 @RequestParam(value="hiddenIntro", required=false)String intro,
									 @RequestParam(value="hiddenSaleAgreement", required=false)String saleAgreement,
									 @RequestParam(value="rackRate", required=false)String rackRate,
									 @RequestParam(value="days", required=false)Integer days,//单票连续游玩天数
									 @RequestParam(value="validUseDateType", required=false)Integer validUseDateType,
									 @RequestParam(value="autoMaticRefund", required=false)Integer autoMaticRefund/**过期自动退*/,
									 @RequestParam(value="remainingQty", required=false)String remainingQty,
									 @RequestParam(value="returnRule", required=false)Integer returnRule,
									 @RequestParam(value="returnCost", required=false)String returnCost){
		

		JSONObject o = new JSONObject();
		
		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		
		if(StringUtils.isBlank(scenicSpotId)){
			o.put("status", "-1");
			o.put("msg", "当前景区不存在");
			return o.toJSONString();
		}
		
		try {
			this.saveSingleTicketValidate(rackRate, remainingQty, returnRule, returnCost);
		} catch (DZPWException e1) {
			e1.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e1.getMessage());
			return o.toJSONString();
		}
		
		ScenicSpotQo sq = new ScenicSpotQo();
		sq.setId(scenicSpotId);
		ScenicSpot scenicSpot = scenicSpotLocalService.queryUnique(sq);
		
		TicketPolicyQo tq = new TicketPolicyQo();
		tq.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
		
		
		tq.setScenicSpotQo(sq);
		
		Integer m = ticketPolicyLocalService.queryCount(tq)+1;
		
		ScenicspotCreateSingleTicketPolicyCommand command = new ScenicspotCreateSingleTicketPolicyCommand();
		command.setValidDays(days);//连续游玩天数
		command.setValidUseDateType(validUseDateType);
		command.setScenicSpotId(scenicSpotId);
		command.setCreateDate(new Date());
		command.setKey(String.format("DP"+scenicSpot.getBaseInfo().getCode().toUpperCase()+"%1$04d", m));
		command.setName(name);
		command.setIntro(intro);
		command.setNotice(notice);
		command.setSaleAgreement(saleAgreement);
		command.setRackRate(Double.valueOf(rackRate));
		command.setRemainingQty(Integer.valueOf(remainingQty));
		command.setReturnRule(returnRule);
		if(returnRule==3||returnRule==4)
			command.setReturnCost(Double.valueOf(returnCost));	
		
		if(autoMaticRefund!=null && autoMaticRefund==1)
			command.setAutoMaticRefund(true);
		else
			command.setAutoMaticRefund(false);
		
		try {
			String ticketPolicyId = ticketPolicyLocalService.scenicspotCreateSingleTicketPolicy(command);
			
			o.put("status", "1");
			o.put("ticketPolicyId", ticketPolicyId);
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e.getMessage());
		}catch(Exception e2){
			o.put("status", "-1");
			o.put("msg", e2.getMessage());
		}
		
		return o.toJSONString();
	}
	
	
	/**
	 * @方法功能说明: 跳转修改单票
	 * @param model
	 */
	@RequestMapping(value = "/singleTicket/toUpdate")
	public ModelAndView toUpdateSingleTicket(HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/singleTicket/singleTicket_edit.html");
		
		String ticketPolicyId = request.getParameter("id");
		TicketPolicyQo qo = new TicketPolicyQo();
		qo.setId(ticketPolicyId);
		TicketPolicy t = ticketPolicyLocalService.queryUnique(qo);
		
		//查询基本价格价格日历
		TicketPolicyPriceCalendarQo priceQo = new TicketPolicyPriceCalendarQo();
		priceQo.setTicketPolicyQo(qo);
		priceQo.setStandardPrice(true);
		TicketPolicyPriceCalendar tppc = priceCalendarLocalService.queryUnique(priceQo);
		
		if(tppc!=null){
			String priceStr = DatePriceStrUtil.datePriceToStr(tppc.getPriceJson());
			mav.addObject("priceStr", priceStr);
		}
		
		//关联过的经销商
		priceQo.setDealerQo(new DealerQo());
		priceQo.setStandardPrice(false);
		List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(priceQo);
		
		if(list!=null && list.size()>0){
			mav.addObject("dealerDatePriceList", list);
			mav.addObject("length", list.size());
		}
		
		mav.addObject("ticketPolicy", t);
		return mav;
	}
	
	
	/**
	 * @方法功能说明: 修改单票业务处理(修改独立单票政策)
	 * @param model
	 * @param ScenicspotCreateSingleTicketPolicyCommand
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/editSubmit")
	public String editSingleTicket(HttpServletRequest request,
			 					   @RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
								   @RequestParam(value="name", required=false)String name,
								   @RequestParam(value="hiddenNotice", required=false)String notice,
								   @RequestParam(value="hiddenIntro", required=false)String intro,
								   @RequestParam(value="hiddenSaleAgreement", required=false)String saleAgreement,
								   @RequestParam(value="rackRate", required=false)String rackRate,
								   @RequestParam(value="validUseDateType", required=false)Integer validUseDateType,
								   @RequestParam(value="remainingQty", required=false)String remainingQty,
								   @RequestParam(value="days", required=false)Integer days,//单票连续游玩天数
								   @RequestParam(value="autoMaticRefund", required=false)Integer autoMaticRefund/**过期自动退*/,
								   @RequestParam(value="returnRule", required=false)Integer returnRule,
								   @RequestParam(value="returnCost", required=false)String returnCost){
		
		JSONObject o = new JSONObject();
		String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);
		
		
		try {
			this.saveSingleTicketValidate(rackRate, remainingQty, returnRule, returnCost);
		} catch (DZPWException e2) {
			e2.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e2.getMessage());
			return o.toJSONString();
		}
		
		ScenicspotModifySingleTicketPolicyCommand command = new ScenicspotModifySingleTicketPolicyCommand(); 
		
		command.setScenicSpotId(scenicSpotId);
		command.setTicketPolicyId(ticketPolicyId);
		command.setName(name);
		command.setNotice(notice);
		command.setIntro(intro);
		command.setSaleAgreement(saleAgreement);
		command.setValidDays(days);//连续游玩天数
		command.setValidUseDateType(validUseDateType);
		command.setRackRate(Double.valueOf(rackRate));
		command.setRemainingQty(Integer.valueOf(remainingQty));
		command.setReturnRule(returnRule);
		if(returnRule==3 || returnRule==4)
			command.setReturnCost(Double.valueOf(returnCost));	
		
		
		if(autoMaticRefund!=null && autoMaticRefund==1)
			command.setAutoMaticRefund(true);
		else
			command.setAutoMaticRefund(false);
		
		try{
			ticketPolicyLocalService.scenicspotModifySingleTicketPolicy(command);
			o.put("status", "1");
		}catch(DZPWException e){
			e.printStackTrace();
			//修改失败
			o.put("status", "-1");
			o.put("msg", e.getMessage());
		}catch(Exception e1){
			o.put("status", "-1");
			o.put("msg", e1.getMessage());
		}
		return o.toJSONString();
	}
	
	
	private void saveSingleTicketValidate(String rackRate, String remainingQty, Integer returnRule, String returnCost)throws DZPWException{
		
		Pattern pattern = null;
		
		if(rackRate!=null){
			pattern = Pattern.compile(patten);
			if(!pattern.matcher(rackRate.toString()).matches())
				throw new DZPWException(DZPWException.NEED_SINGLE_TICKET_WITHOUTPARAM,"市场价格格式错误");
		}
		
		if(returnRule!=null && returnRule!=1){
			pattern = Pattern.compile(patten);
			if(!pattern.matcher(returnCost.toString()).matches())
				throw new DZPWException(DZPWException.NEED_SINGLE_TICKET_WITHOUTPARAM,"退票收取手续费格式错误");
		}
		
		//-1 时不校验 remainingQty格式
		if(remainingQty!=null && !remainingQty.equals("-1")){
			pattern = Pattern.compile(patten2);
			if(!pattern.matcher(remainingQty.toString()).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"库存数量格式错误");
		}
	}
	
	
	/**
	 * @方法功能说明：启用/批量启用
	 * @修改者名字：liusong
	 * @修改时间：2014-11-12下午5:50:37
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/usableBatch")
	public String usableBatch(@RequestParam(value="ids", required =false)String[] ids,HttpServletRequest request){
		
		String msg = "启用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "singleTicket";
		String callbackType = null;
		
		if(ids!=null && ids.length>0){
			try {
				String scenicSpotId = MerchantSessionUserManager.getSessionUserId(request);//获取登录用户id，即景区id
				for(int i=0;i<ids.length;i++){
					ScenicspotChangeSingleTicketPolicyStatusCommand changeSinglePolicyCommand = new ScenicspotChangeSingleTicketPolicyStatusCommand();
					changeSinglePolicyCommand.setTicketPolicyId(ids[i]);
					changeSinglePolicyCommand.setActive(true);
					changeSinglePolicyCommand.setScenicSpotId(scenicSpotId);
					//修改独立单票政策为启用状态
					ticketPolicyLocalService.scenicspotChangeSingleTicketPolicyStatus(changeSinglePolicyCommand);
				}
			
			} catch (DZPWException e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
				msg = e.getMessage();
				navTabId = null;
			}
		}
		
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}
	
	
	/**
	 * @方法功能说明：禁用/批量禁用
	 * @修改者名字：liusong
	 * @修改时间：2014-11-13上午11:15:11
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/disableBatch")
	public String disableBatch(@RequestParam(value="ids", required =false)String[] ids){
		String msg = "禁用成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "singleTicket";
		String callbackType = null;
		
		if(ids!=null && ids.length>0){
			try {
				for(int i=0;i<ids.length;i++){
					ScenicspotChangeSingleTicketPolicyStatusCommand   changeSinglePolicyCommand = new  ScenicspotChangeSingleTicketPolicyStatusCommand();
					changeSinglePolicyCommand.setTicketPolicyId(ids[i]);
					changeSinglePolicyCommand.setActive(false);
					//修改独立单票政策为禁用状态
					ticketPolicyLocalService.scenicspotChangeSingleTicketPolicyStatus(changeSinglePolicyCommand);
				}
			} catch (Exception e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
				msg = "禁用失败";
				navTabId = null;
			}
		}
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}
	
	
	/**
	 * @方法功能说明：删除/批量删除
	 * @修改者名字：liusong
	 * @修改时间：2014-11-12下午5:50:59
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/delBatch")
	public String delBatch(@RequestParam(value="ids", required =false)String[] ids){
		String msg = "删除成功";
		String code = DwzJsonResultUtil.STATUS_CODE_200;
		String navTabId = "singleTicket";
		String callbackType = null;
		
		if(ids!=null&&ids.length>0){
			try {
				List<String>  ticketPolicyId = new ArrayList<String>();
				ScenicspotRemoveSingleTicketPolicyCommand removeSingleTicketCommand = new ScenicspotRemoveSingleTicketPolicyCommand();
				for(int i=0;i<ids.length;i++){
					ticketPolicyId.add(ids[i]);
				}
				removeSingleTicketCommand.setTicketPolicyId(ticketPolicyId);
				//删除独立单票政策
				ticketPolicyLocalService.scenicspotRemoveSingleTicketPolicy(removeSingleTicketCommand);
			} catch (Exception e) {
				e.printStackTrace();
				code = DwzJsonResultUtil.STATUS_CODE_500;
			    msg = "删除失败";
				navTabId = null;
			}
		}
		return DwzJsonResultUtil.createJsonString(code, msg, callbackType, navTabId);
	}

	
	/**
	 * @方法功能说明：独立单票政策查看详情
	 * @修改者名字：zhurz
	 * @修改时间：2015-4-24下午1:59:45
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@param request
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping(value="/singleTicket/info")
	public ModelAndView singleTicketInfo(@RequestParam(value="id", required = false)String id,HttpServletRequest request){
		
		//返回的视图对象
		ModelAndView mav = new ModelAndView("/singleTicket/singleTicket_info.html");
		
		if(StringUtils.isNotBlank(id)){
			TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
			ticketPolicyQo.setId(id);
			ticketPolicyQo.setType(TicketPolicy.TICKET_POLICY_TYPE_SINGLE);
			TicketPolicy ticketPolicy = ticketPolicyLocalService.queryUnique(ticketPolicyQo);
			
			// 查询价格日历
			TicketPolicyPriceCalendarQo calendarQo = new TicketPolicyPriceCalendarQo();
			calendarQo.setDealerFetch(true);
			calendarQo.setTicketPolicyQo(ticketPolicyQo);
			
			List<TicketPolicyPriceCalendar> calendars = priceCalendarLocalService.queryList(calendarQo);
			TicketPolicyPriceCalendar standardPriceCalendar = null;
			for(TicketPolicyPriceCalendar calendar:calendars)
				if(calendar.getStandardPrice()){
					standardPriceCalendar = calendar;
					break;
				}
			
			if (standardPriceCalendar != null) {
				Map<String, TicketPolicyDatePrice> standardPriceMap = new HashMap<String, TicketPolicyDatePrice>();
				List<TicketPolicyDatePrice> standardPrices = standardPriceCalendar.getPrices();

				for (TicketPolicyDatePrice standardPrice : standardPrices)
					standardPriceMap.put(standardPrice.getDate(), standardPrice);

				for (TicketPolicyPriceCalendar calendar : calendars) {
					if (calendar.getStandardPrice())
						continue;
					Map<String, TicketPolicyDatePrice> priceMap = new HashMap<String, TicketPolicyDatePrice>();
					priceMap.putAll(standardPriceMap);
					
					List<TicketPolicyDatePrice> prices = calendar.getPrices();
					for (TicketPolicyDatePrice price : prices)
						priceMap.put(price.getDate(), price);
					
					List<String> dateList = new ArrayList<String>(priceMap.keySet());
					Collections.sort(dateList);
					prices.clear();
					for (String date : dateList)
						prices.add(priceMap.get(date));
				}
			}
			
			List<Map<String, Object>> calendarList = new ArrayList<Map<String,Object>>();
			for (TicketPolicyPriceCalendar calendar : calendars) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("prices", calendar.getPrices());
				if (calendar.getStandardPrice()) {
					map.put("dealerId", "standard");
					map.put("dealerName", "基准价");
				} else {
					map.put("dealerId", calendar.getDealer().getId());
					map.put("dealerName", calendar.getDealer().getBaseInfo().getName());
				}
				calendarList.add(map);
			}

			mav.addObject("calendarList", JSON.toJSONString(calendarList, SerializerFeature.DisableCircularReferenceDetect));
			mav.addObject("ticketPolicy", ticketPolicy);
		}
		return mav;
	}
	
	
	@RequestMapping(value="/singleTicket/showDealerDatePrice")
	public ModelAndView showDealerDatePrice(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
											@RequestParam(value="dealerId", required=false)String dealerId,
											@RequestParam(value="dealerName", required=false)String dealerName,
											@RequestParam(value="type", required=false)Integer type){
		
		ModelAndView mav = new ModelAndView("/singleTicket/dealerDatePrice.html");
		
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
		
		mav.addObject("ticketPolicyId", ticketPolicyId);
		mav.addObject("dealerId", dealerId);
		mav.addObject("dealerName", dealerName);
		if(type!=null){
			mav.addObject("type", type);
		}
		return mav;
	}
	
	
	/**
	 * @方法功能说明：保存基本价格日历
	 * @修改者名字：yangkang
	 * @修改时间：2015-3-11上午10:32:27
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param ticketPolicyId
	 * @参数：@param dealerId
	 * @参数：@param priceStr
	 * @参数：@param dealerName
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@ResponseBody
	@RequestMapping("/singleTicket/datePrice/save")
	public String saveDatePrice(HttpServletRequest request,
								@RequestParam(value="ticketPolicyId", required=true)String ticketPolicyId,
								@RequestParam(value="priceStr", required=true)String priceStr){
		
		JSONObject o = new JSONObject();
		ScenicspotSetSingleTicketPolicyPriceCommand command = new ScenicspotSetSingleTicketPolicyPriceCommand();
		
		if(StringUtils.isBlank(priceStr)){
			//缺少基本价格价格日历
			o.put("status", "-2");
			return o.toJSONString();
		}
		
		command.setTicketPolicyId(ticketPolicyId);
		command.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		command.setCreateDate(new Date());
		command.setStandardPrice(true);//基本价价格日历
		
		Pattern pattern = Pattern.compile(patten);
		Boolean f = false;
		//解析价格日历
		String[] arr = priceStr.split(";");
		for(String s: arr){
			//单日价格  20150212:22
			String[] arr2 = s.split(":");
			DatePrice dp = new DatePrice(); 
			dp.setDate(arr2[0]);
			dp.setOpen(true);
			//价格日历金额格式错误
			if(!pattern.matcher(arr2[1]).matches()){
				f = true;
				break;
			}
			dp.setPrice(Double.parseDouble(arr2[1]));
			command.getDatePrices().add(dp);
		}
		
		if(f){
			o.put("status", "-1");
			return o.toJSONString();
		}
		try {
			priceCalendarLocalService.scenicspotSetSingleTicketPolicyPrice(command);
			
			/**
			 * 单票发布后 暂时为禁用状态
			//保存完价格日历后修改单票状态为启用
			ScenicspotChangeSingleTicketPolicyStatusCommand changeSinglePolicyCommand = new ScenicspotChangeSingleTicketPolicyStatusCommand();
			changeSinglePolicyCommand.setTicketPolicyId(ticketPolicyId);
			changeSinglePolicyCommand.setActive(true);
			changeSinglePolicyCommand.setScenicSpotId(command.getScenicSpotId());
			ticketPolicyLocalService.scenicspotChangeSingleTicketPolicyStatus(changeSinglePolicyCommand);
			*/
			
			o.put("status", "1");
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("status", "0");
			o.put("msg", e.getMessage());
		}
		return o.toJSONString();
	}
	
	
	/**
	 * @方法功能说明：保存经销商价格日历
	 * @修改者名字：yangkang
	 * @修改时间：2015-3-11上午10:33:50
	 * @参数：@param request
	 * @参数：@param ticketPolicyId
	 * @参数：@param dealerId
	 * @参数：@param priceStr
	 * @参数：@param dealerName
	 * @参数：@return
	 * @return:String
	 */
	@ResponseBody
	@RequestMapping(value="/singleTicket/dealerDatePrice/save")
	public String saveDealerDatePrice(HttpServletRequest request,
									  @RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
									  @RequestParam(value="dealerId", required=false)String dealerId,
									  @RequestParam(value="priceStr", required=false)String priceStr,
									  @RequestParam(value="dealerName", required=false)String dealerName,
									  @RequestParam(value="type", required=false)Integer type){
		
		JSONObject o = new JSONObject();
		
		ScenicspotSetSingleTicketPolicyPriceCommand command = new ScenicspotSetSingleTicketPolicyPriceCommand();
		command.setCreateDate(new Date());
		command.setStandardPrice(false);//经销商价格日历
		command.setDealerId(dealerId);
		command.setTicketPolicyId(ticketPolicyId);
		command.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		
		if(type==null)
			type = 1;
		
		Pattern pattern = Pattern.compile(patten);
		Boolean f = false;
		//解析价格日历
		String[] arr = priceStr.split(";");
		for(String s: arr){
			//单日价格  20150212:22
			String[] arr2 = s.split(":");
			DatePrice dp = new DatePrice(); 
			dp.setDate(arr2[0]);
			dp.setOpen(true);
			//价格日历金额格式错误
			if(!pattern.matcher(arr2[1]).matches()){
				f = true;
				break;
			}
			dp.setPrice(Double.parseDouble(arr2[1]));
			command.getDatePrices().add(dp);
		}
		if(f){
			o.put("status", "-1");
			return o.toJSONString();
		}
		try {
			String datePriceId = priceCalendarLocalService.scenicspotSetSingleTicketPolicyPrice(command);
			//返回价格日历ID
			o.put("status", "1");
			o.put("dealerDatePriceId", datePriceId);
			o.put("dealerName", dealerName);
			o.put("dealerId", dealerId);
			o.put("ticketPolicyId", ticketPolicyId);
//			o.put("type", type);
		} catch (DZPWException e) {
			e.printStackTrace();
			//经销商价格日历保存失败
			o.put("status", "0");
			o.put("msg", e.getMessage());
		}
		
		return o.toJSONString();
	}
	
	
	@ResponseBody
	@RequestMapping("/singleTicket/dealerDatePrice/del")
	public String delDatePrice(@RequestParam(value="datePriceId", required=true)String datePriceId){
		
		JSONObject o = new JSONObject();
		
		try {
			priceCalendarLocalService.deleteById(datePriceId);
			o.put("status", "1");
		} catch (Exception e) {
			e.printStackTrace();
			o.put("status", "-1");
		}
		
		return o.toJSONString();
	}
	
	
	@ResponseBody
	@RequestMapping(value="/singleTicket/datePrice/update")
	public String updateDatePrice(HttpServletRequest request,
								  @RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
								  @RequestParam(value="dealerId", required=false)String dealerId,
								  @RequestParam(value="priceStr", required=false)String priceStr){
		
		ScenicspotSetSingleTicketPolicyPriceCommand command = new ScenicspotSetSingleTicketPolicyPriceCommand();
		JSONObject o = new JSONObject();
		
		if(StringUtils.isBlank(priceStr)){
			//缺少基本价格价格日历
			o.put("status", "-2");
			return o.toJSONString();
		}
		
		command.setScenicSpotId(MerchantSessionUserManager.getSessionUserId(request));
		command.setTicketPolicyId(ticketPolicyId);
		if(StringUtils.isNotBlank(dealerId)){
			command.setStandardPrice(false);//经销商价格日历
			command.setDealerId(dealerId);
		}else{
			command.setStandardPrice(true);
		}
		
		Pattern pattern = Pattern.compile(patten);
		Boolean f = false;
		//解析价格日历
		String[] arr = priceStr.split(";");
		for(String s: arr){
			//单日价格  20150212:22
			String[] arr2 = s.split(":");
			DatePrice dp = new DatePrice(); 
			dp.setDate(arr2[0]);
			dp.setOpen(true);
			//价格日历金额格式错误
			if(!pattern.matcher(arr2[1]).matches()){
				f = true;
				break;
			}
			dp.setPrice(Double.parseDouble(arr2[1]));
			command.getDatePrices().add(dp);
		}
		if(f){
			o.put("status", "-1");
			return o.toJSONString();
		}
		try {
			priceCalendarLocalService.scenicspotSetSingleTicketPolicyPrice(command);
			o.put("status", "1");
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("status", "0");
			o.put("msg", e.getMessage());
		}
		
		return o.toJSONString();
	}
	
	
	@ResponseBody
	@RequestMapping("/singleTicket/editDealerDatePrice")
	public ModelAndView toEditDealerDatePrice(HttpServletRequest request,
											  @RequestParam(value="priceId", required=false)String priceId,
											  @RequestParam(value="dealerId", required=false)String dealerId,
											  @RequestParam(value="dealerName", required=false)String dealerName,
											  @RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId){
		
		ModelAndView mav = new ModelAndView("/singleTicket/dealerDatePrice.html");
		
		TicketPolicyPriceCalendarQo qo = new TicketPolicyPriceCalendarQo();
		
		qo.setDealerFetch(true);
		qo.setId(priceId);
		
		TicketPolicyPriceCalendar tppc = priceCalendarLocalService.queryUnique(qo);
		
		if(tppc!=null){
			String priceStr = DatePriceStrUtil.datePriceToStr(tppc.getPriceJson());
			mav.addObject("dpriceStr", priceStr);
		}
		
		mav.addObject("dealerId", dealerId);
		mav.addObject("dealerName", dealerName);
		mav.addObject("ticketPolicyId", ticketPolicyId);
		mav.addObject("type", 0);//type==0是页面可以编辑
		return mav;
	}
}