package hsl.web.controller.mp;

import hg.log.util.HgLogger;
import hg.system.model.meta.Province;
import hg.system.qo.ProvinceQo;
import hg.system.service.ProvinceService;
import hsl.api.base.ApiResponse;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.command.UserClickRecordCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.ImageSpecDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.mp.ScenicSpotsBaseInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.mp.HslMPDatePriceQO;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslRankListQO;
import hsl.pojo.qo.mp.HslUserClickRecordQO;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.mp.MPOrderService;
import hsl.spi.inter.mp.MPScenicSpotService;
import hsl.spi.inter.user.UserService;
import hsl.web.controller.BaseController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.dubbo.common.URL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @类功能： 门票控制器
 * @作者： zhuxy
 *
 */
@Controller
@RequestMapping("/mp")
@SuppressWarnings({"unchecked","rawtypes"})
public class MpController extends BaseController{
	@Resource
	private MPScenicSpotService scenicSpotService;
	
	@Resource
	private UserService memberService;
	
	@Resource
	private MPOrderService orderService;
	
	@Resource
	private CompanyService companyService;
	
	@Resource
	private ProvinceService provinceService;
	/**
	 * 景点门票主页
	 * @return
	 * @throws MPException 
	 */
	@RequestMapping("/list")
	public String main(HttpServletRequest request,HslMPScenicSpotQO mpScenicSpotsQO,Model model) {
		/**
		 * 获取用户区分是企业用户还是个人用户
		 */
		UserDTO user=getUserBySession(request);
		String userId = "";
		if(user!=null){
			userId = user.getId();
			HgLogger.getInstance().debug("zhuxy", "景点列表有用户登录："+user.getAuthInfo().getLoginName());
		}else{
			HgLogger.getInstance().debug("zhuxy", "景点列表没有用户登录");
		}
		
		getRecord(userId, model, request);
		
		//景点列表的数据
		Map scenicMap;
		mpScenicSpotsQO.setByArea(true);
		mpScenicSpotsQO.setByName(true);
		if(mpScenicSpotsQO.getPageNo()==null){
			mpScenicSpotsQO.setPageNo(1);
		}
		mpScenicSpotsQO.setPageSize(10);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);
		mpScenicSpotsQO.setImagesFetchAble(true);
		model.addAttribute("mpScenicSpotsQO", mpScenicSpotsQO);
		
		HgLogger.getInstance().info("zhuxy", "景点列表，查询QO回传："+JSON.toJSONString(mpScenicSpotsQO));
		
		try {
			//景点列表查询
			scenicMap = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
			List<PCScenicSpotDTO> spots = (List<PCScenicSpotDTO>)scenicMap.get("dto");
			HslMPPolicyQO mpPolicyQO = new HslMPPolicyQO();
			if(spots!=null&&spots.size()>0){
				for(PCScenicSpotDTO spot : spots){
					mpPolicyQO.setScenicSpotId(spot.getId());
					Map map=scenicSpotService.queryScenicPolicy(mpPolicyQO);
					List<PolicyDTO> plist = (List<PolicyDTO>)map.get("dto");
					spot.setPlist(plist);
				}
			}
			HgLogger.getInstance().info("zhuxy", "景点列表，查询列表数据："+JSON.toJSONString(scenicMap));
			model.addAttribute("scenicMap", scenicMap);
			
		} catch (MPException e) {
			//放置空数据s
			scenicMap = new HashMap();
			scenicMap.put("dto",new ArrayList<ScenicSpotDTO>());
			scenicMap.put("count", 0);
			model.addAttribute("scenicMap", scenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", "MpController->main->exception:" + HgLogger.getStackTrace(e));
		}
		
		//热门景点数据
			
		Map hotScenicMap ;
		 try {
		 
			HslMPScenicSpotQO hotMpScenicSpotsQO = new HslMPScenicSpotQO();
			hotMpScenicSpotsQO.setHot(true);
			hotMpScenicSpotsQO.setPageNo(1);
			hotMpScenicSpotsQO.setPageSize(6);
			hotMpScenicSpotsQO.setIsOpen(true);
			hotMpScenicSpotsQO.setContent(null);
			hotScenicMap = scenicSpotService.queryScenicSpot(hotMpScenicSpotsQO);
			HgLogger.getInstance().info("zhuxy", "景点列表，查询热门景点："+JSON.toJSONString(hotScenicMap));
			model.addAttribute("hotScenicMap", hotScenicMap);
		} catch (MPException e) {
			hotScenicMap = new HashMap();
			hotScenicMap.put("dto",new ArrayList<HotScenicSpotDTO>());
			hotScenicMap.put("count", 0);
			model.addAttribute("hotScenicMap", hotScenicMap);
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", "MpController->main->exception:" + HgLogger.getStackTrace(e));
		}
		
		//热卖门票排行版
		HgLogger.getInstance().debug("zhuxy", "查询热门门票排行");
		HslRankListQO rankListQO = new HslRankListQO();
		rankListQO.setPageSize(10);
		List<MPSimpleDTO> rank = scenicSpotService.queryScenicSpotClickRate(rankListQO);
		model.addAttribute("rank", rank);
		
		
		ProvinceQo provinceQo = new ProvinceQo();
		List<Province> provinceList = provinceService.queryList(provinceQo);
		model.addAttribute("provinces", provinceList);
		return "mp/list.html";
	}

	/**
	 * 获取浏览记录
	 * @param model
	 * @param ucrq
	 */
	private void getRecord(String userId, final Model model, HttpServletRequest request) {
		HgLogger.getInstance().debug("zhuxy", "景点列表,浏览记录查询");
		if(StringUtils.isBlank(userId)){
			/**
			 * 没有用户默认将浏览记录添加到session中，查询的浏览记录也从session中取
			 */
			List<MPSimpleDTO> record=new ArrayList<MPSimpleDTO>();
			HashMap<String, MPSimpleDTO> recordMap = (HashMap<String, MPSimpleDTO>) request
					.getSession().getAttribute("recordMap");
			if(recordMap!=null){
				Set<String> keySet = recordMap.keySet();
				for(String key : keySet){
					record.add(recordMap.get(key));
				}
			}
			model.addAttribute("record", record);
		}else{
			/**
			 * 有用户按用户id查询浏览记录
			 */
			HslUserClickRecordQO ucrq = new HslUserClickRecordQO();
			ucrq.setPageSize(10);
			ucrq.setUserId(userId);
			List<MPSimpleDTO> record=new ArrayList<MPSimpleDTO>();
			record = memberService.queryUserClickRecord(ucrq);
			model.addAttribute("record", record);
		}
	}
	
	
	/**
	 * 景点门票详情页
	 * @return
	 * @throws Exception 
	 */

	@RequestMapping("/view")
	public String details(@RequestParam(value="id",required=true)String id ,Model model,HttpServletRequest request){
		if(StringUtils.isBlank(id)){
			return null;
		}
		
		/**
		 * 获取用户区分是企业用户还是个人用户
		 */
		UserDTO user=getUserBySession(request);
		String userId = "";
		if(user!=null){
			userId = user.getId();
			HgLogger.getInstance().debug("zhuxy", "景点详情，有用户登录："+user.getAuthInfo().getLoginName());
		}else{
			HgLogger.getInstance().debug("zhuxy", "景点详情，没有用户登录");
		}
		
		//查询景点的详细信息
		PCScenicSpotDTO pssd = null;
		try{
		Map map;
		HslMPScenicSpotQO mpScenicSpotsQO = new HslMPScenicSpotQO();
		mpScenicSpotsQO.setScenicSpotId(id);
		mpScenicSpotsQO.setByArea(true);
		mpScenicSpotsQO.setByName(true);
		mpScenicSpotsQO.setImagesFetchAble(true);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);
		map = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
		pssd = ((List<PCScenicSpotDTO>)map.get("dto")).get(0);
		model.addAttribute("pssd", pssd);
		model.addAttribute("images_json", JSONObject.toJSONString(pssd.getImages()));
		HgLogger.getInstance().info("zhuxy", "景点详情，景点详细信息数据："+JSON.toJSONString(pssd));
		
		
		//热门景点查询
		Map hotScenicMap ;
		mpScenicSpotsQO.setHot(true);
		mpScenicSpotsQO.setPageNo(1);
		mpScenicSpotsQO.setPageSize(6);
		mpScenicSpotsQO.setIsOpen(true);
		mpScenicSpotsQO.setScenicSpotId(null);
		hotScenicMap = scenicSpotService.queryScenicSpot(mpScenicSpotsQO);
		model.addAttribute("hotScenicMap", hotScenicMap);
		HgLogger.getInstance().info("zhuxy", "景点详情，热门景点数据："+JSON.toJSONString(hotScenicMap));
		
		
		//查询所有政策
		HslMPPolicyQO mpPolicyQO = new HslMPPolicyQO();
		mpPolicyQO.setScenicSpotId(id);
		Map hashMap=scenicSpotService.queryScenicPolicy(mpPolicyQO);
		List<PolicyDTO> plist = (List<PolicyDTO>)hashMap.get("dto");
		model.addAttribute("plist", plist);
		HgLogger.getInstance().info("zhuxy", "景点详情，景点政策数据："+JSON.toJSONString(plist));
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy","MpController->main->exception:"+HgLogger.getStackTrace(e));
		}
		/**
		 * 添加浏览记录
		 */
		HgLogger.getInstance().debug("zhuxy", "景点详情，添加浏览记录");
		if(!StringUtils.isBlank(userId)){
			/**
			 * 有用户登录，保存到数据库
			 */
			UserClickRecordCommand mcrc = new UserClickRecordCommand();
			mcrc.setUserId(userId);//这里需要取出用户的id
			mcrc.setUrl(request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/"+request.getRequestURI());
			mcrc.setScenicSpotId(id);
			mcrc.setFromIP("pc");
			memberService.createUserClickRecord(mcrc);
		}else{
			/**
			 * 没有用户登录，保存到session
			 */
			if (pssd!=null) {
				HashMap<String, MPSimpleDTO> recordMap = (HashMap<String, MPSimpleDTO>) request
						.getSession().getAttribute("recordMap");
				if (recordMap == null) {
					recordMap = new HashMap<String, MPSimpleDTO>();
				}
				MPSimpleDTO recordItem = new MPSimpleDTO();
				recordItem.setScenicSpotId(id);
				recordItem.setScenicSpotName(pssd.getScenicSpotBaseInfo().getName());
				recordItem.setPrice(pssd.getBookInfoDTO().getAmountAdvice());
				recordMap.put(id, recordItem);
				request.getSession().setAttribute("recordMap", recordMap);
				System.out.println("添加一行");
			}
		}
		
		/**
		 * 获取浏览记录
		 */
		getRecord(userId, model, request);
		
		//热卖门票排行版
		HgLogger.getInstance().debug("zhuxy", "查询热门门票排行");
		HslRankListQO rankListQO = new HslRankListQO();
		rankListQO.setPageSize(10);
		List<MPSimpleDTO> rank = scenicSpotService.queryScenicSpotClickRate(rankListQO);
		model.addAttribute("rank", rank);
		return "mp/view.html";
	}
	
	/**
	 * 预定门票页
	 * @return
	 */
	
	@RequestMapping(value="/scart",method=RequestMethod.GET)
	public Object reserveTicket(@RequestParam(value="id",required=true)String id,
								@RequestParam(value="price",required=false)String price,
								HttpServletRequest request,
								Model model){
		model.addAttribute("price", price);
		
		//获取用户区分是企业用户还是个人用户
		UserDTO user=getUserBySession(request);
		Integer type = 0;
		if(user!=null){
			type = user.getBaseInfo().getType();
		}else{
			HgLogger.getInstance().error("zhuxy", "门票下单，用户未登录，拦截器也没有使其跳转");
			return null;
		}
		//没有用户默认个人用户 ：1是个人，2是企业
		if(type==0){
			type=1;
		}
		
		//获取门票政策详情
		try{
			model.addAttribute("id", id);
			HslMPPolicyQO hslMpPolicyQO = new HslMPPolicyQO();
			hslMpPolicyQO.setPolicyId(id);
			Map policysMap = scenicSpotService.queryScenicPolicy(hslMpPolicyQO);
			List<PolicyDTO> dtos = (List<PolicyDTO>)policysMap.get("dto");
			if(dtos!=null&&dtos.size()>0){
				model.addAttribute("policy", dtos.get(0));
				HgLogger.getInstance().info("zhuxy", "门票预订，门票政策："+JSON.toJSONString(dtos.get(0)));
			}
		}catch(Exception e){
			HgLogger.getInstance().error("zhuxy", "预订门票页==》异常："+HgLogger.getStackTrace(e));
		}
		
		//如果是企业用户，查询企业下的公司
		if (type==2) {
			model.addAttribute("type", 2);
			model.addAttribute("userName", user.getBaseInfo().getName());
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setUserId(user.getId());//这里替换成用户的id，公司查询条件
			List<CompanyDTO> companyList = companyService
					.getCompanys(hslCompanyQO);
			model.addAttribute("companyList", companyList);
			HgLogger.getInstance().info("zhuxy", "门票预订，是企业用户，公司列表："+JSON.toJSONString(companyList));
		}
		//日期价格的查询
		HslMPDatePriceQO mpDatePriceQO = new HslMPDatePriceQO();
		mpDatePriceQO.setPolicyId(id);
		try {
			Map map = scenicSpotService.queryDatePrice(mpDatePriceQO);
			model.addAttribute("datePrice", map);
			HgLogger.getInstance().info("zhuxy", "门票预订，价格日历："+JSON.toJSONString(map));
		} catch (MPException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", "MpController->reserveTicket->exception:" + HgLogger.getStackTrace(e));
			model.addAttribute("msg", e.getMessage());
		}
		
		
		return "mp/scart.html";
	}
	
	
	/**
	 * 下订单并返回下单结果页
	 * @return
	 */
	@RequestMapping(value="/handle",method=RequestMethod.POST)
	public RedirectView handleOrders(MPOrderCreateCommand mpOrderCreateCommand,RedirectAttributes attr,HttpServletRequest request){
		/**
		 * 处理订单数据
		 * 注：下单的人信息要详细，包括id。可以通过session中获取用户id
		 * map内容{dto:MPOrderDTO,code:订单结果码,msg:订单结果中的消息}
		 */
		UserDTO user=getUserBySession(request);
		if(user==null){
			HgLogger.getInstance().error("zhuxy", "门票预订下单，用户没有登录，下单失败");
			return null;
		}else{
			HgLogger.getInstance().info("zhuxy", "门票预订，下单用户："+user.getAuthInfo().getLoginName());
		}
		
		String dealerOrderCode ="";
		String policyName = "";
		attr.addAttribute("dealerOrderCode", dealerOrderCode);
		attr.addAttribute("policyName",policyName);
		
		try {
			mpOrderCreateCommand.setSource("pc");
			mpOrderCreateCommand.setBookManIP("127.0.0.1");
			mpOrderCreateCommand.getOrderUserInfo().setId(user.getId());
			mpOrderCreateCommand.getOrderUserInfo().setMobile(mpOrderCreateCommand.getOrderUserInfo().getContactInfo().getMobile());
			//mpOrderCreateCommand.setTravelDate("2015-08-15");
			Map orderMap = orderService.createMPOrder(mpOrderCreateCommand);
			
			String code = orderMap.get("code").toString();
			if(ApiResponse.RESULT_CODE_OK.equals(code)){
				//下单成功
				//重定向的时候将订单信息发送过去（如果为了保护用户隐私的话可以不发送,只发送订单id，在成功界面从业务层抽取订单信息，根据订单id）
				MPOrderDTO mpOrderDTO = (MPOrderDTO)(orderMap.get("dto"));
			    dealerOrderCode = mpOrderDTO.getDealerOrderCode();
				attr.addAttribute("dealerOrderCode", dealerOrderCode);
			    policyName = URL.encode(request.getParameter("policyName"));
				attr.addAttribute("policyName",policyName);
				//为了防止订单重交，使用重定向
				//失败的话要转到哪个页面需要问一下
				HgLogger.getInstance().info("zhuxy", "门票预订，下单成功");
				return new RedirectView("/mp/success", true);
			}else{
				//下单失败
				attr.addAttribute("message", orderMap.get("msg").toString());
				HgLogger.getInstance().info("zhuxy", "门票预订，下单失败");
				attr.addAttribute("id", mpOrderCreateCommand.getPolicyId());
				
				System.out.println("景点订单下单失败："+ orderMap.get("msg").toString());
				return new RedirectView("/mp/failed",true);
			}
		} catch (RuntimeException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", newLog("门票订单处理方法", HgLogger.getStackTrace(e)));
			
			return new RedirectView("/mp/failed",true);
		}
		
	}


	/**
	 * 下单成功的页面
	 * @return
	 * @throws MPException 
	 */
	@RequestMapping("/success")
	public String success(@RequestParam(value = "dealerOrderCode",required=true)String dealerOrderCode,
			@RequestParam(value = "policyName",required=true)String policyName,
			HttpServletRequest request,
			Model model) throws MPException{
		//检查用户是否持有此订单
		/*UserDTO user = getUserBySession(request);
		HslMPOrderQO mpOrderQO = new HslMPOrderQO();
		mpOrderQO.setUserId(user.getId());
		mpOrderQO.setDealerOrderCode(dealerOrderCode);
		try {
			orderService.queryMPOrderList(mpOrderQO);
		} catch (MPException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", newLog("查询下单结果", "用户与订单不匹配"));
			throw e;
		}*/
		//直接使用
		model.addAttribute("dealerOrderCode", dealerOrderCode);
		model.addAttribute("policyName", URL.decode(policyName));
		HgLogger.getInstance().info("zhuxy", "下单成功，订单号："+dealerOrderCode+" 政策名称："+URL.decode(policyName));
		return "mp/success.html";
	}
	
	/**
	 * 下单失败的页面(目前没有，可能使用同一个页面)
	 * @return
	 */
	@RequestMapping("/failed")
	public String failed(@RequestParam(value = "dealerOrderCode",required=true)String dealerOrderCode,
			@RequestParam(value = "policyName",required=true)String policyName,
			HttpServletRequest request,
			Model model){
		
		model.addAttribute("dealerOrderCode", dealerOrderCode);
		model.addAttribute("policyName", URL.decode(policyName));
		HgLogger.getInstance().info("zhuxy", "下单失败，订单号："+dealerOrderCode+" 政策名称："+URL.decode(policyName));
		
		return "mp/reseveFail.html";
	}

}
