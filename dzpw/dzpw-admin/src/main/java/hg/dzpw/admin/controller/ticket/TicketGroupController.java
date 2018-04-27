package hg.dzpw.admin.controller.ticket;

import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.dzpw.admin.controller.BaseController;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.TicketPolicyPriceCalendarQo;
import hg.dzpw.app.pojo.qo.TicketPolicyQo;
import hg.dzpw.app.service.local.ScenicSpotLocalService;
import hg.dzpw.app.service.local.TicketPolicyLocalService;
import hg.dzpw.app.service.local.TicketPolicyPriceCalendarLocalService;
import hg.dzpw.domain.model.policy.TicketPolicy;
import hg.dzpw.domain.model.policy.TicketPolicyPriceCalendar;
import hg.dzpw.domain.model.policy.TicketPolicyStatus;
import hg.dzpw.pojo.command.platform.policy.PlatformChangeGroupTicketPolicyStatusCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformCreateGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.PlatformModifyGroupTicketPolicyCommand;
import hg.dzpw.pojo.command.platform.policy.SingleTicketPolicy;
import hg.dzpw.pojo.exception.DZPWException;
import hg.log.domainevent.DomainEventDao;
import hg.log.util.HgLogger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

/**
 * @类功能说明: 联票管理的 Controller
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-11 上午9:57:47
 */
@Controller
@RequestMapping("/ticket-group")
public class TicketGroupController extends BaseController {
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
	/**金额正则*/
	private static final String patten = "^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){1,2})?$";
	/**连续游玩天数*/
	private static final String patten2 = "[1-9]{1,}\\d*";
	
	/**
	 * @方法功能说明: 联票(套票)列表 
	 * @param policyQo
	 * @param dwzQo
	 * @param model
	 */
	@RequestMapping(value = "/list")
	public String list(@RequestParam(value="pageNum", required = false)Integer pageNum,
            		   @RequestParam(value="numPerPage", required = false)Integer pageSize,
					   @RequestParam(value="createBeginDateStr", required=false)String createBeginDateStr,
					   @RequestParam(value="createEndDateStr", required=false)String createEndDateStr,
					   @RequestParam(value="ticketPolicyName", required=false)String ticketPolicyName,
					   @RequestParam(value="status", required=false)String status,
					   @RequestParam(value="scenicSpotStr", required=false)String scenicSpotStr,
					   @RequestParam(value="soldQtyMin", required=false)String soldQtyMin,
					   @RequestParam(value="soldQtyMax", required=false)String soldQtyMax,
					   @RequestParam(value="priceMin", required=false)String priceMin,
					   @RequestParam(value="priceMax", required=false)String priceMax,
					   Model model){
		
		TicketPolicyQo tpQo = new TicketPolicyQo();
		tpQo.setCreateDateSort(-1);
		tpQo.setTicketPolicyName(ticketPolicyName);
		tpQo.setTicketPolicyNameLike(true);
		tpQo.setScenicSpotStr(scenicSpotStr);
		tpQo.setType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		
		try {
			tpQo.setSoldQtyMax(Integer.parseInt(soldQtyMax));
		} catch (Exception e1) {
//			e1.printStackTrace();
			tpQo.setSoldQtyMax(null);
		}
		try {
			tpQo.setSoldQtyMin(Integer.parseInt(soldQtyMin));
		} catch (Exception e1) {
//			e1.printStackTrace();
			tpQo.setSoldQtyMin(null);
		}
		try {
			tpQo.setPriceMax(Double.valueOf(priceMax));
		} catch (Exception e1) {
//			e1.printStackTrace();
			tpQo.setPriceMax(null);
		}
		try {
			tpQo.setPriceMin(Double.valueOf(priceMin));
		} catch (Exception e1) {
//			e1.printStackTrace();
			tpQo.setPriceMin(null);
		}

		// status
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
		
		model.addAttribute("pagination",pagination);
		model.addAttribute("createBeginDateStr",createBeginDateStr);
		model.addAttribute("createEndDateStr",createEndDateStr);
		model.addAttribute("ticketPolicyName",ticketPolicyName);
		model.addAttribute("status",status);
		model.addAttribute("scenicSpotStr",scenicSpotStr);
		model.addAttribute("soldQtyMin",soldQtyMin);
		model.addAttribute("soldQtyMax",soldQtyMax);
		model.addAttribute("priceMin",priceMin);
		model.addAttribute("priceMax",priceMax);
		
		return "/ticket/group/list.html";
	}
	
	
	/**
	 * @方法功能说明: 查看详细
	 * @param policyQo
	 * @param model
	 */
	@RequestMapping(value = "/info")
	public String info(@ModelAttribute("policyQo") TicketPolicyQo policyQo,Model model) {
		try {
			
			if(StringUtils.isBlank(policyQo.getId()))
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票ID不能为空！");
			
			policyQo.setScenicFetchAble(true);
			policyQo.setSingleTicketPoliciesFetchAble(true);
			TicketPolicy policy = tickSer.queryUnique(policyQo);
			if(null == policy)
				throw new DZPWException(DZPWException.TICKETGROUP_NOT_EXISTS,"联票不存在或已删除！");
			
			model.addAttribute("policy",policy);
			
			//查询对应的经销商价格日历
			TicketPolicyPriceCalendarQo priceQo = new TicketPolicyPriceCalendarQo();
			priceQo.setDealerQo(new DealerQo());
			priceQo.setTicketPolicyQo(policyQo);
			
			List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(priceQo);
			if(list!=null && list.size()>0)
				model.addAttribute("priceList",list);
			
		} catch (Exception e) {
			e.printStackTrace();
			hgLogger.error(TicketGroupController.class,"dzpw-application","[info] 查看详细："+e.getMessage(),e);
		}
		return "/ticket/group/newInfo.html";
	}
	
	
	/**
	 * @方法功能说明: 跳转到联票新增页面 
	 */
	@RequestMapping(value = "/add")
	public String add(Model model,@RequestParam(value="navTabid",required=false) String navTabid){
		return "/ticket/group/newAdd.html";
	}
	
	
	@RequestMapping("/toUpdate")
	public String toUpdate(Model model, @RequestParam(value="policyId", required=true)String policyId){
		
		TicketPolicyQo policyQo = new TicketPolicyQo();
		policyQo.setId(policyId);
		policyQo.setScenicFetchAble(true);
		policyQo.setSingleTicketPoliciesFetchAble(true);
		TicketPolicy policy = tickSer.queryUnique(policyQo);
		model.addAttribute("policy",policy);
		
		//查询对应的经销商价格日历
		TicketPolicyPriceCalendarQo priceQo = new TicketPolicyPriceCalendarQo();
		priceQo.setDealerQo(new DealerQo());
		priceQo.setTicketPolicyQo(policyQo);
		List<TicketPolicyPriceCalendar> list = priceCalendarLocalService.queryList(priceQo);
		if(list!=null && list.size()>0)
			model.addAttribute("priceList",list);
		
		return "/ticket/group/update.html";
	}
	
	
	/**
	 * @方法功能说明：联票保存参数校验
	 * @修改者名字：yangkang
	 * @修改时间：2015-4-1下午5:52:09
	 * @参数：@param days
	 */
	public void saveValidate(String[] days, String[] settlementPrice, String[] scenicSpotIds, String[] rackRate, 
							 String remainingQty, String[] playPrice) throws DZPWException{
		
		Pattern pattern = null;
		
//		if(rackRate!=null){
//			pattern = Pattern.compile(patten);
//			if(!pattern.matcher(rackRate.toString()).matches())
//				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"市场价格格式错误");
//		}
		
//		if(scenicSpotIds==null || scenicSpotIds.length==0)
//			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"请选择联票景区！");
//		
//		if(settlementPrice==null || settlementPrice.length==0)
//			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"缺少联票景区结算价！");
//		
//		if(playPrice==null || playPrice.length==0)
//			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"缺少联票景区游玩理财价！");
//		
//		if(rackRate==null || rackRate.length==0)
//			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"缺少联票景区市场票面价！");
//		
//		if(days==null || days.length==0)
//			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"缺少联票景区可连续游玩天数！");
		
		
		if(scenicSpotIds.length != settlementPrice.length)
			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区结算价不完整！");
		
		if(scenicSpotIds.length != playPrice.length)
			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区游玩理财价不完整！");
		
		if(scenicSpotIds.length != rackRate.length)
			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区市场票面价不完整！");
		
		if(scenicSpotIds.length != days.length)
			throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区可连续游玩天数不完整！");
		
		for(int x=0; x<scenicSpotIds.length; x++){
//			if(settlementPrice[x] == null)
//				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区结算价不完整！");
			
//			if(days[x] == null)
//				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区可连续游玩天数不完整！");
			
			pattern = Pattern.compile(patten);
			if(!pattern.matcher(settlementPrice[x].toString()).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区结算价格式不正确！");
			
			if(!pattern.matcher(playPrice[x].toString()).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区游玩理财价格式不正确！");
			
			if(!pattern.matcher(rackRate[x].toString()).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区市场票面价格式不正确！");
			
			pattern = Pattern.compile(patten2);
			if(!pattern.matcher(days[x]).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"联票景区可连续游玩天数格式不正确！");
		}
		
		//-1 时不校验 remainingQty格式
		if(remainingQty!=null && !remainingQty.equals("-1")){
			pattern = Pattern.compile(patten2);
			if(!pattern.matcher(remainingQty.toString()).matches())
				throw new DZPWException(DZPWException.NEED_TICKETGROUP_WITHOUTPARAM,"库存数量格式错误");
		}
		
	}
	
	
	/**
	 * @方法功能说明：保存联票STEP ONE
	 * @修改者名字：yangkang
	 * @修改时间：2015-4-1下午4:13:11
	 */
	@ResponseBody
	@RequestMapping("/saveAdd")
	public String add(
					@RequestParam(value="name", required=false)String name,
					@RequestParam(value="hiddenIntro", required=false)String intro,
					@RequestParam(value="hiddenNotice", required=false)String notice,
					@RequestParam(value="hiddenSaleAgreement", required=false)String saleAgreement,
					@RequestParam(value="remainingQty", required=false)String remainingQty ,
					@RequestParam(value="validUseDateType", required=false)Integer validUseDateType,
					@RequestParam(value="validDays", required=false)Integer validDays,
					@RequestParam(value="returnRule", required=false)Integer returnRule,/**退票规则*/
					@RequestParam(value="scenicSpotIds", required=false)String[] scenicSpotIds,
					@RequestParam(value="days", required=false)String[] days,/**可连续游玩天数*/
					@RequestParam(value="settlementPrice", required=false)String[] settlementPrice,/**结算价*/
					@RequestParam(value="playPrice", required=false)String[] playPrice,/**游玩理财价*/
					@RequestParam(value="rackRate", required=false)String[] rackRate,/**市场票面价*/
					@RequestParam(value="autoMaticRefund", required=false)Integer autoMaticRefund/**过期自动退*/){
		
		JSONObject o = new JSONObject();
		PlatformCreateGroupTicketPolicyCommand command = new PlatformCreateGroupTicketPolicyCommand();
		
		/**校验联票景区*/
		try {
			this.saveValidate(days, settlementPrice, scenicSpotIds, rackRate, remainingQty, playPrice);
		} catch (DZPWException e1) {
			e1.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e1.getMessage());
			return o.toJSONString();
		}
		
		//查询联票记录数
		TicketPolicyQo qo = new TicketPolicyQo();
		qo.setType(TicketPolicy.TICKET_POLICY_TYPE_GROUP);
		Integer m = tickSer.queryCount(qo)+1;
		
		command.setKey(String.format("LPYY%1$08d", m));//联票产品格式LPYY0000002
		command.setName(name);
		command.setIntro(intro);
		command.setNotice(notice);
		command.setSaleAgreement(saleAgreement);
		command.setRemainingQty(Integer.valueOf(remainingQty));//-1表示库存无限
		command.setValidUseDateType(validUseDateType);
		command.setValidDays(validDays);
		command.setReturnRule(returnRule);
		command.setAutoMaticRefund(true);
		
		//计算联票的市场票面价总和
		Double r = 0d;
		for(String s : rackRate){
			r = r + Double.valueOf(s);
		}
		command.setRackRate(r);
		
		//计算联票的结算价总和
		r = 0d;
		for(String s : settlementPrice){
			r = r + Double.valueOf(s);
		}
		command.setSettlementPrice(r);
		
		//计算联票的游玩理财价总和
		r = 0d;
		for(String s : playPrice){
			r = r + Double.valueOf(s);
		}
		command.setPlayPrice(r);
		
		//解析景区参数
		List<SingleTicketPolicy> list = new ArrayList<SingleTicketPolicy>();
		for(int i=0; i<days.length; i++){
			SingleTicketPolicy stp = new SingleTicketPolicy();
			
			stp.setScenicSpotId(scenicSpotIds[i]);
			//景区连续游玩天数
			stp.setValidDays(Integer.valueOf(days[i]));
			//景区游玩理财价
			stp.setPlayPrice(Double.valueOf(playPrice[i]));
			//景区市场票面价
			stp.setRackRate(Double.valueOf(rackRate[i]));
			//景区结算价
			stp.setSettlementPrice(Double.valueOf(settlementPrice[i]));
			list.add(stp);
		}
		command.setSingleTicketPolicies(list);
		
		
		try {
			tickSer.platformCreateGroupTicketPolicy(command);
			o.put("status", "1");
			
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e.getMessage());
		}
		return o.toJSONString();
				
	}
	
	
	/**
	 * @方法功能说明：启用/禁用
	 * @修改者名字：yangkang
	 * @参数：@param policyId
	 * @参数：@param status
	 */
	@ResponseBody
	@RequestMapping("/useOrUnuse")
	public String useOrUnuse(@RequestParam(value="policyId", required=true)String policyId,
							 @RequestParam(value="status", required=true)Integer status){
		
		if(StringUtils.isNotBlank(policyId)){
			PlatformChangeGroupTicketPolicyStatusCommand command = new PlatformChangeGroupTicketPolicyStatusCommand();
			command.setTicketPolicyId(policyId);
			if(status==3){
				command.setActive(true);
			}else if(status==4){
				command.setActive(false);
			}else{
				return DwzJsonResultUtil.createJsonString("300", "系统异常", null, "ticket-group");
			}
			
			try {
				tickSer.platformChangeGroupTicketPolicyStatus(command);
			} catch (DZPWException e) {
				e.printStackTrace();
			}
			
			return DwzJsonResultUtil.createJsonString("200", "", null, "ticket-group");
		}else{
			return DwzJsonResultUtil.createJsonString("300", "请选择一条记录", null, "ticket-groups");
		}
	}
	
	
	@ResponseBody
	@RequestMapping("/update")
	public String update(@RequestParam(value="ticketPolicyId", required=true)String ticketPolicyId,
						 @RequestParam(value="key", required=false)String key,
						 @RequestParam(value="name", required=false)String name,
						 @RequestParam(value="hiddenIntro", required=false)String intro,
						 @RequestParam(value="hiddenNotice", required=false)String notice,
						 @RequestParam(value="hiddenSaleAgreement", required=false)String saleAgreement,
						 @RequestParam(value="remainingQty", required=false)String remainingQty,
						 @RequestParam(value="validUseDateType", required=false)Integer validUseDateType,
						 @RequestParam(value="validDays", required=false)Integer validDays,
						 @RequestParam(value="returnRule", required=false)Integer returnRule,/**退票规则*/
						 @RequestParam(value="scenicSpotIds", required=false)String[] scenicSpotIds,
						 @RequestParam(value="days", required=false)String[] days,/**可连续游玩天数*/
						 @RequestParam(value="settlementPrice", required=false)String[] settlementPrice,/**结算价*/
						 @RequestParam(value="playPrice", required=false)String[] playPrice,/**游玩理财价*/
						 @RequestParam(value="rackRate", required=false)String[] rackRate,/**市场票面价*/
						 @RequestParam(value="autoMaticRefund", required=false)Integer autoMaticRefund/**过期自动退*/){
		
		JSONObject o = new JSONObject();
		PlatformModifyGroupTicketPolicyCommand command = new PlatformModifyGroupTicketPolicyCommand();
		
		/**校验联票景区*/
		try {
			this.saveValidate(days, settlementPrice, scenicSpotIds, rackRate, remainingQty, playPrice);
		} catch (DZPWException e1) {
			e1.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e1.getMessage());
			return o.toJSONString();
		}
		
		command.setTicketPolicyId(ticketPolicyId);
		command.setKey(key);
		command.setName(name);
		command.setIntro(intro);
		command.setNotice(notice);
		command.setSaleAgreement(saleAgreement);
		command.setRemainingQty(Integer.valueOf(remainingQty));//-1表示库存无限
		command.setValidUseDateType(validUseDateType);
		command.setValidDays(validDays);
		command.setReturnRule(returnRule);
		
		/**
		if(autoMaticRefund!=null && autoMaticRefund==1)
			command.setAutoMaticRefund(true);
		else
			command.setAutoMaticRefund(false);
		*/
		//计算联票的市场票面价总和
		Double r = 0d;
		for(String s : rackRate){
			r = r + Double.valueOf(s);
		}
		command.setRackRate(r);
				
		//计算联票的结算价总和
		r = 0d;
		for(String s : settlementPrice){
			r = r + Double.valueOf(s);
		}
		command.setSettlementPrice(r);
		
		//计算联票的游玩理财价总和
		r = 0d;
		for(String s : playPrice){
			r = r + Double.valueOf(s);
		}
		command.setPlayPrice(r);

		//解析景区参数
		List<SingleTicketPolicy> list = new ArrayList<SingleTicketPolicy>();
		for(int i=0; i<days.length; i++){
			SingleTicketPolicy stp = new SingleTicketPolicy();
			
			stp.setScenicSpotId(scenicSpotIds[i]);
			//景区连续游玩天数
			stp.setValidDays(Integer.valueOf(days[i]));
			//景区游玩理财价
			stp.setPlayPrice(Double.valueOf(playPrice[i]));
			//景区市场票面价
			stp.setRackRate(Double.valueOf(rackRate[i]));
			//景区结算价
			stp.setSettlementPrice(Double.valueOf(settlementPrice[i]));
			list.add(stp);
		}
		command.setSingleTicketPolicies(list);
		
		try {
			tickSer.platformModifyGroupTicketPolicy(command);
			o.put("status", "1");
		} catch (DZPWException e) {
			e.printStackTrace();
			o.put("status", "-1");
			o.put("msg", e.getMessage());
		}
		
		return o.toJSONString();
	}
	
}