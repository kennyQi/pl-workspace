package hg.dzpw.dealer.admin.controller.ticket;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import hg.common.page.Pagination;
import hg.common.util.ClassPathTool;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.ExcelUtils;
import hg.common.util.UUIDGenerator;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.app.pojo.qo.GroupTicketQo;
import hg.dzpw.app.pojo.qo.TicketOrderQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.AnnualMettingSmsService;
import hg.dzpw.app.service.local.DealerLocalService;
import hg.dzpw.app.service.local.DealerScenicspotSettingLocalService;
import hg.dzpw.app.service.local.GroupTicketLocalService;
import hg.dzpw.app.service.local.TicketOrderLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.dealer.admin.common.utils.DatePriceStrUtil;
import hg.dzpw.dealer.admin.component.manager.DealerSessionUserManager;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.common.exception.DZPWDealerApiException;
import hg.dzpw.dealer.client.dto.tourist.TouristDTO;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerScenicspotSetting;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyDatePrice;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.domain.model.scenicspot.ScenicSpot;
import hg.dzpw.domain.model.ticket.GroupTicket;
import hg.dzpw.domain.model.ticket.SingleTicketStatus;
import hg.dzpw.domain.model.ticket.Ticket;
import hg.dzpw.domain.model.tourist.Tourist;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.util.HgLogger;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.common.json.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
public class TicektController {
	
	@Autowired
	private HgLogger hgLogger;//MongoDB日志
	
	@Autowired
	private TicketPolicyLocalService tickSer;
	
	@Autowired
	private TicketPolicyPriceCalendarLocalService priceCalendarLocalService;
	
	@Autowired
	private TicketOrderLocalService ticketOrderLocalService;
	
	@Autowired
	private DealerLocalService dealerLocalService;
	
	@Autowired
	private GroupTicketLocalService groupTicketLocalService;
	
	@Autowired
	private AnnualMettingSmsService annualMettingSmsService;
	
	@Autowired
	private DealerScenicspotSettingLocalService settingLocalService;
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
	/**
	 * @方法功能说明：票务查询列表
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-11上午9:21:32
	 */
	@RequestMapping("/ticket/list")
	public ModelAndView list(@RequestParam(value="pageNum", required = false)Integer pageNum,
 		   					 @RequestParam(value="numPerPage", required = false)Integer pageSize,
 		   					 @RequestParam(value="ticketPolicyName", required = false)String ticketPolicyName,
							 @RequestParam(value="scenicSpotStr", required = false)String scenicSpotStr,
							 HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/ticket/ticket_list.html");
		
		TicketPolicyQo tpQo = new TicketPolicyQo();
		tpQo.setCreateDateSort(-1);
		tpQo.setTicketPolicyName(ticketPolicyName);
		tpQo.setTicketPolicyNameLike(true);
		tpQo.setScenicSpotStr(scenicSpotStr);
		tpQo.setStatus(TicketPolicyStatus.TICKET_POLICY_STATUS_ISSUE);
		
		
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
		
		//查询禁用该经销商的景区
		DealerScenicspotSettingQo settingQo = new DealerScenicspotSettingQo();
		settingQo.setUseable(false);
		settingQo.setDealerQo(new DealerQo());
		settingQo.getDealerQo().setId(DealerSessionUserManager.getSessionUserId(request));
		settingQo.setScenicSpotAble(true);
		List<DealerScenicspotSetting> list = settingLocalService.queryList(settingQo);
		
		mav.addObject("pagination", pagination);
		mav.addObject("ticketPolicyName", ticketPolicyName);
		mav.addObject("scenicSpotStr", scenicSpotStr);
		mav.addObject("settingList", list);
		return mav;
	}
	
	
	/**
	 * @方法功能说明：单票、联票详情页
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-11上午9:22:58
	 */
	@RequestMapping("/ticket/info")
	public ModelAndView info(@RequestParam(value="ticketPolicyId", required = false)String ticketPolicyId){
		
		ModelAndView mav = new ModelAndView("/ticket/ticket_group_info.html");
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			TicketPolicyQo qo = new TicketPolicyQo();
			qo.setId(ticketPolicyId);
			
			TicketPolicy policy = tickSer.queryUnique(qo);
			
			//单票
			if(policy.getType()==TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
				mav = new ModelAndView("/ticket/ticket_single_info.html");
			}
			
			mav.addObject("policy", policy);
		}
		
		return mav;
	}
	
	
	@RequestMapping(value="/ticket/single/datePrice/page")
	public ModelAndView datePricePage(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
									  HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/ticket/datePriceView.html");
		mav.addObject("ticketPolicyId", ticketPolicyId);
		
		if(StringUtils.isNotBlank(ticketPolicyId) && 
				StringUtils.isNotBlank(DealerSessionUserManager.getSessionUserId(request))){
			
			// 查询基准价格日历
			TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
			ticketPolicyQo.setId(ticketPolicyId);
			
			TicketPolicyPriceCalendarQo datePriceQo = new TicketPolicyPriceCalendarQo();
			datePriceQo.setTicketPolicyQo(ticketPolicyQo);
			datePriceQo.setStandardPrice(true);
			
			TicketPolicyPriceCalendar baseTPPC = priceCalendarLocalService.queryUnique(datePriceQo);
			
			// 查询门票对应当前经销商的价格日历
			DealerQo dQo = new DealerQo();
			dQo.setId(DealerSessionUserManager.getSessionUserId(request));
			datePriceQo.setStandardPrice(false);
			datePriceQo.setDealerQo(dQo);
			
			TicketPolicyPriceCalendar dealerTPPC = priceCalendarLocalService.queryUnique(datePriceQo);
			
			if (dealerTPPC == null) {
				mav.addObject("priceStr", DatePriceStrUtil.datePriceToStr(baseTPPC.getPriceJson()));
			}else {
				List<TicketPolicyDatePrice> list = baseTPPC.getPrices();
				
				// 合并经销商价格日历 和基准价格日历内容
				for (TicketPolicyDatePrice o : dealerTPPC.getPrices()){
					boolean nullFalg = true;
					for (TicketPolicyDatePrice bo : list){
						// 同一天都有价格时   取经销商价格日历价格
						if (o.getDate().equals(bo.getDate())) {
							bo.setPrice(o.getPrice());
							nullFalg = false;
							break;
						}
					}
					
					// 经销商价格日历某天没有的  添加到价格日历
					if (nullFalg)
						list.add(o);
				}
				
				try {
					mav.addObject("priceStr", DatePriceStrUtil.datePriceToStr(JSON.json(list)));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			/**
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
			*/
		}
		
		return mav;
	}
	
	
	/**
	 * @方法功能说明：票预订页
	 * @修改者名字：yangkang
	 * @修改时间：2015-12-11上午9:23:46
	 */
	@RequestMapping(value="/ticket/buy")
	public ModelAndView buyTicket(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
								  HttpServletRequest request){
		
		ModelAndView mav = new ModelAndView("/ticket/ticket_buy.html");
		
		if(StringUtils.isNotBlank(ticketPolicyId)){
			TicketPolicyQo qo = new TicketPolicyQo();
			qo.setId(ticketPolicyId);
			
			TicketPolicy policy = tickSer.queryUnique(qo);
			
			//拼接游玩景点和天数
			if(policy.getType()==TicketPolicy.TICKET_POLICY_TYPE_GROUP){
				StringBuffer sb = new StringBuffer();
				for(TicketPolicy sp : policy.getSingleTicketPolicies()){
					sb.append(sp.getBaseInfo().getName()).append("(").append(sp.getUseInfo().getValidDays()).append("天)、");
				}
				mav.addObject("scenicSpotNameStr", sb.substring(0, sb.length()-1));
			}
			
			
			//单票时查询价格日历
			if(policy.getType()==TicketPolicy.TICKET_POLICY_TYPE_SINGLE){
				
				TicketPolicyQo ticketPolicyQo = new TicketPolicyQo();
				TicketPolicyPriceCalendarQo datePriceQo = new TicketPolicyPriceCalendarQo();
				DealerQo dQo = new DealerQo();
				
				ticketPolicyQo.setId(policy.getId());
				dQo.setId(DealerSessionUserManager.getSessionUserId(request));
				datePriceQo.setTicketPolicyQo(ticketPolicyQo);
				datePriceQo.setDealerQo(dQo);
				datePriceQo.setStandardPrice(false);
				
				//查询单票对当前经销商设置的价格日历
				List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(datePriceQo);
				if(list!=null && list.size()>0){
					mav.addObject("dealerPriceStr", DatePriceStrUtil.datePriceToStr(list.get(0).getPriceJson()));
				}
				
				datePriceQo.setStandardPrice(true);
				datePriceQo.setDealerQo(null);
				//查询单票的基本价格日历
				TicketPolicyPriceCalendar datePrice = priceCalendarLocalService.queryUnique(datePriceQo);
				if(datePrice!=null){
					mav.addObject("priceStr", DatePriceStrUtil.datePriceToStr(datePrice.getPriceJson()));
				}
			}
			
			mav.addObject("policy", policy);
			Calendar c = Calendar.getInstance();
			// 今天日期
			mav.addObject("minDate", new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
		}
		
		return mav;
	}
	
	
	@ResponseBody
	@RequestMapping(value="/ticket/submit/order")
	public String submitOrder(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
							  @RequestParam(value="travelDate", required=false)String travelDate,
							  @RequestParam(value="buyNum", required=false)Integer buyNum,
							  @RequestParam(value="bookMan", required=false)String bookMan,
							  @RequestParam(value="bookManMobile", required=false)String bookManMobile,
							  @RequestParam(value="totalNum", required=false)String totalNum,//订单金额
							  @RequestParam(value="pepoleName", required=false)String[] pepoleNames,
							  @RequestParam(value="idNo", required=false)String[] idNos,
							  @RequestParam(value="version", required=false)Integer version,
							  HttpServletRequest request){
		
		JSONObject o = new JSONObject();
		CreateTicketOrderCommand command = new CreateTicketOrderCommand();
		List<TouristDTO> tourists = new ArrayList<TouristDTO>();
		
		if(buyNum==null || buyNum<=0){
			o.put("status", "500");
			o.put("msg", "缺少购买数量");
			return o.toJSONString();
		}
		
		if(pepoleNames!=null && idNos!=null && pepoleNames.length!=idNos.length
				|| pepoleNames==null && idNos!=null || pepoleNames!=null && idNos==null){
			
			o.put("status", "500");
			o.put("msg", "缺少游玩人信息");
			return o.toJSONString();
		}
		
		if(pepoleNames!=null && idNos!=null && 
		   pepoleNames.length==idNos.length &&
		   buyNum!=pepoleNames.length){
			
			o.put("status", "500");
			o.put("msg", "购票数量与游玩人个数不一致");
			return o.toJSONString();
		}

		// 添加游玩人信息
		if(pepoleNames!=null && idNos!=null){
			for(int i=0; i<idNos.length; i++){
				
				if(StringUtils.isBlank(pepoleNames[i]) || StringUtils.isBlank(idNos[i])){
					o.put("status", "500");
					o.put("msg", "游玩人姓名、证件号不匹配");
					return o.toJSONString();
				}
				TouristDTO t = new TouristDTO();
				t.setName(pepoleNames[i]);
				t.setIdNo(idNos[i]);
				t.setIdType(Tourist.TOURIST_ID_TYPE_SFZ);
				tourists.add(t);
			}
		}
		
		if(StringUtils.isBlank(bookMan) || StringUtils.isBlank(bookManMobile)){
			o.put("status", "500");
			o.put("msg", "缺少预订人姓名或手机号码");
			return o.toJSONString();
		}
		
		if(StringUtils.isNotBlank(travelDate)){
			try {
				command.setTravelDate(new SimpleDateFormat("yyyy-MM-dd").parse(travelDate));
			} catch (ParseException e) {
				e.printStackTrace();
				o.put("status", "500");
				o.put("msg", "出游日期格式错误");
				return o.toJSONString();
			}
		}
		
		try {
			command.setPrice(Double.valueOf(totalNum));
		} catch (NumberFormatException e1) {
			e1.printStackTrace();
			o.put("status", "500");
			o.put("msg", "订单金额格式错误");
			return o.toJSONString();
		}
		command.setTicketPolicyId(ticketPolicyId);
		command.setBuyNum(buyNum);
		command.setTourists(tourists);
		command.setTicketPolicyVersion(version);
		command.setBookMan(bookMan);
		command.setBookManMobile(bookManMobile);
		
		String dealerId = DealerSessionUserManager.getSessionUserId(request);
		
		try {
			String ticketOrderNo = ticketOrderLocalService.dealerAdminCreateTicketOrder(command, dealerId);
			o.put("status", "100");
			o.put("orderNo", ticketOrderNo);//票号
		} catch (DZPWDealerApiException e1) {
			e1.printStackTrace();
			o.put("status", "500");
			o.put("msg", e1.getMessage());

		} catch (Exception e){
			e.printStackTrace();
			o.put("status", "500");
			o.put("msg", e.getMessage());

		}
		
		if (o.get("msg")!=null) {
			HgLogger.getInstance().info("yangk", String.format("经销商端下单失败: Exception Message[%s] | 请求参数[%s]", o.get("msg"), command));
		}
		
		return o.toJSONString();
	}
	
	
	@RequestMapping(value="/ticket/dealerDatePrice/page")
	public ModelAndView dealerDatePricePage(@RequestParam(value="ticketPolicyId", required=false)String ticketPolicyId,
											@RequestParam(value="dealerId", required=false)String dealerId){
		
		ModelAndView mav = new ModelAndView("/ticket/dealerDatePrice.html");
		
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


	/**
	 * @方法功能说明：导入弹出框页面
	 * @修改者名字：yangkang
	 * @修改时间：2016-1-12上午10:43:56
	 * @参数：@return
	 * @return:String
	 */
	@RequestMapping("/ticket/toImport")
	public String toImport(){
		return "/ticket/to_import.html";
	}
	
	
	@ResponseBody
	@RequestMapping("/ticket/batchBuy")
	public String batchBuy(@RequestParam MultipartFile importFile,HttpServletRequest request){
		
		if(importFile==null){
			return DwzJsonResultUtil.createJsonString("300", "请上传excel文件", null, null);
		}
		
		String type =  importFile.getContentType();
		
		if(!(type.equals("application/vnd.ms-excel")||type.equals("application/x-xls"))) {
			return DwzJsonResultUtil.createJsonString("300", "请上传excel文件", null, null);
		}
		
		ClassPathTool.getInstance();
		String tempFilePath = ClassPathTool.getWebRootPath() + File.separator + "excel" + File.separator + "upload" + UUIDGenerator.getUUID() + importFile.getOriginalFilename();
		tempFilePath = tempFilePath.replace("file:", "");
		File tempFile = new File(tempFilePath);
		List<List<String>> dataListList = null;
		try {
			importFile.transferTo(tempFile);
			dataListList = ExcelUtils.getExecelStringValues(tempFile);
			tempFile.delete();
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "导入失败", null,
					null);
		} catch (IOException e) {
			e.printStackTrace();
			return DwzJsonResultUtil.createJsonString("300", "导入失败", null,
					null);
		}
		
		//校验表头
		List<String> title = dataListList.get(0);
		
		if(!(title.get(0).equals("姓名") && title.get(1).equals("身份证号") && title.get(2).equals("手机号")&& title.get(3).equals("抽奖码"))){
			return DwzJsonResultUtil.createJsonString("300", "文件内容不正确", null, null);
		}
		
		TicketPolicyQo qo = new TicketPolicyQo();
		qo.setTicketPolicyName("汇购科技2016年年会入场券");
		qo.setTicketPolicyNameLike(false);
		TicketPolicy tp = tickSer.queryUnique(qo);
		
		String dealerId = DealerSessionUserManager.getSessionUserId(request);
		
		JSONObject o = new JSONObject(); 
		
		List<TouristDTO> list = new ArrayList<TouristDTO>();
		for (int i = 1; i < dataListList.size(); i++) {
			List<String> t = dataListList.get(i);
			TouristDTO td = new TouristDTO();
			td.setName(t.get(0));
			td.setIdType(Tourist.TOURIST_ID_TYPE_SFZ);//身份证
			td.setIdNo(t.get(1));
			td.setTelephone(t.get(2));
			td.setNation(t.get(3));// 设置抽奖码
			
			list.add(td);
			
			CreateTicketOrderCommand command = new CreateTicketOrderCommand();
			command.setTicketPolicyId(tp.getId());
			command.setBuyNum(1);
			command.setTourists(list);
			command.setTicketPolicyVersion(tp.getVersion());
			command.setBookMan(td.getName());
			command.setBookManMobile(td.getTelephone());
			command.setPrice(Double.valueOf(1));
			try {
				command.setTravelDate(new SimpleDateFormat("yyyy-MM-dd").parse("2016-01-23"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			try {
				Object obj = ticketOrderLocalService.dealerAdminCreateTicketOrder(command, dealerId);
				
				if(obj instanceof DZPWDealerApiException){
					DZPWDealerApiException ep = (DZPWDealerApiException)obj;
					o.put("status", "500");
					o.put("msg", ep.getMessage());
				}else{
					o.put("status", "100");
					o.put("orderNo", (String)obj);//票号
				}
				
				GroupTicketQo gto = new GroupTicketQo();
				gto.setTicketOrdeQo(new TicketOrderQo());
				gto.getTicketOrdeQo().setOrderId((String)obj);
				GroupTicket gt = groupTicketLocalService.queryUnique(gto);
				// 发送二维码链接短信
				annualMettingSmsService.sendSmsNotice(td.getTelephone(), 
						"http://dzpw.ply365.com/annualmeeting/view/"+gt.getTicketNo());
				
			} catch (DZPWDealerApiException e) {
				e.printStackTrace();
				HgLogger.getInstance().info("yangk", String.format("经销商端批量下单失败: 身份证号[%s] | Exception Message[%s]", td.getIdNo(), e.getMessage()));
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().info("yangk", String.format("经销商端批量下单失败: 身份证号[%s] | Exception Message[%s]", td.getIdNo(), e.getMessage()));
			}
			
			list.clear();
		}
		
		o.put("message", "导入完成");
		o.put("statusCode", DwzJsonResultUtil.STATUS_CODE_200);
		o.put("callbackType", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT);
		o.put("navTabId", "orderList");
		return o.toString();
	}
	
}
