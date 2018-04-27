package hsl.h5.control;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;
import hsl.api.base.ApiResponse;
import hsl.h5.base.result.mp.MPOrderCreateResult;
import hsl.h5.base.result.mp.MPQueryDatePriceResult;
import hsl.h5.base.result.mp.MPQueryOrderResult;
import hsl.h5.base.result.mp.MPQueryPolicyResult;
import hsl.h5.base.result.mp.MPQueryScenicSpotsResult;
import hsl.h5.base.utils.OpenidTracker;
import hsl.h5.control.constant.Constants;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.dto.company.CompanyDTO;
import hsl.pojo.dto.mp.DateSalePriceDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.user.UserBaseInfoDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.company.HslCompanyQO;
import hsl.pojo.qo.mp.HslMPDatePriceQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.spi.inter.company.CompanyService;
import hsl.spi.inter.mp.MPOrderService;
import hsl.spi.inter.mp.MPScenicSpotService;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @类功能说明：门票订单Action
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：zhuxy
 * @创建时间：2014年11月6日上午11:03:29
 * @版本：V1.0
 *
 */
@Controller
@RequestMapping("mpo")
public class MPOrderCtrl extends HslCtrl {
	@Resource
	private CompanyService companyService;
	
	@Autowired
	private MPOrderService mpOrderService;
	
	@Autowired
	private MPScenicSpotService mpScenicSpotService;

	/**
	 * 门票下单
	 */
	@RequestMapping("settle")
	public ModelAndView settle(String scenicSpotId, String policyId, HttpServletRequest request) {
		HgLogger.getInstance().debug("zhuxy", newHeader("门票预订页面方法开始"));
		ModelAndView mav = new ModelAndView("mporder/settle");
		mav.addObject("openid", OpenidTracker.get());
		try {
			mav.addObject("scenicSpot",getScenicSpotDetail(scenicSpotId)); 
			String userid = getUserId(request);
			HslCompanyQO hslCompanyQO = new HslCompanyQO();
			hslCompanyQO.setUserId(userid);//这里替换成用户的id，公司查询条件
			List<CompanyDTO> companyList = companyService.getCompanys(hslCompanyQO);
			mav.addObject("companyList", companyList);
			mav.addObject("userType",getUserByUserId(userid).getBaseInfo().getType());
			/**
			 * 查询门票政策
			 */
			List<PolicyDTO> policies = null;
			policies = getPolicyList(scenicSpotId);
			
			PolicyDTO policy = null;
			for (PolicyDTO object : policies) {
				if (policyId.equals(object.getPolicyId())) {
					policy = object;
				}
			}
			if (policy == null) {
				throw new Exception("未找到正确的景点政策");
			} else {
				mav.addObject("policy", policy);
			}
			
			/**
			 * 查询价格日历
			 */
			List<DateSalePriceDTO> dateSalePriceDTOs = getDateSalePrice(policyId);
			HgLogger.getInstance().info("zhuxy", newLog("门票预订页面方法", "价格日历", JSON.toJSONString(dateSalePriceDTOs)));
			mav.addObject("datePrices",JSON.toJSONString(dateSalePriceDTOs));
		} catch(RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("门票预订页面方法出错")+HgLogger.getStackTrace(e));
		} catch (Exception e) {
			log.error("hsl.err", e);
			mav.setViewName("error");
			HgLogger.getInstance().error("zhuxy", newHeader("门票预订页面方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("门票预订页面方法开始"));
		return mav;
	}
	
	@RequestMapping("confirm")
	public void confirm(HttpServletRequest request,
			String command, PrintWriter out) {
		HgLogger.getInstance().debug("zhuxy", newHeader("门票订单处理页面方法开始"));
		ModelAndView mav = new ModelAndView("mporder/success");
		try {
			mav.addObject("openid", OpenidTracker.get());
			command = URLDecoder.decode(command, "UTF-8");
			MPOrderCreateCommand createCommand = 
					JSONObject.parseObject(command, MPOrderCreateCommand.class);
			createCommand.setPrice(createCommand.getPrice() * createCommand.getNumber());
			createCommand.setOrderUserInfo(
					getUserByUserId(getUserId(request)));
			if(StringUtils.isBlank(createCommand.getOrderUserInfo().getContactInfo().getMobile())){
				createCommand.getOrderUserInfo().getContactInfo().setMobile(createCommand.getTakeTicketUserInfo().getContactInfo().getMobile());
			}
			UserBaseInfoDTO baseInfo = new UserBaseInfoDTO();
			baseInfo.setName(createCommand.getTakeTicketUserInfo().getBaseInfo().getName());
			createCommand.getOrderUserInfo().setBaseInfo(baseInfo);
			createCommand.getOrderUserInfo().getAuthInfo().setLoginName(null);
			createCommand.getOrderUserInfo().getAuthInfo().setPassword(null);
//			String ip = request.getRemoteHost();
			createCommand.setBookManIP(request.getRemoteHost());
			createCommand.setSource("weixin");
			Map map = mpOrderService.createMPOrder(createCommand);
			MPOrderCreateResult orderCreateResponse = new MPOrderCreateResult();
			orderCreateResponse.setOrderId(((MPOrderDTO)map.get("dto")).getDealerOrderCode());
			orderCreateResponse.setResult(map.get("code").toString());
			
			if (map.get("msg") != null) {
				orderCreateResponse.setMessage(map.get("msg").toString());
			}
			out.print(JSON.toJSONString(orderCreateResponse));
		} catch(RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("门票订单处理页面方法出错")+HgLogger.getStackTrace(e));
		} catch (Exception e) {
			out.print(getHslErrResponseJsonStr(e));
			HgLogger.getInstance().error("zhuxy", newHeader("门票订单处理页面方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("门票订单处理页面方法结束"));
	}
	
	@RequestMapping("success")
	public ModelAndView success(
			String orderInfo, String orderId) {
		HgLogger.getInstance().debug("zhuxy", newHeader("门票下单成功方法开始"));
		ModelAndView mav = new ModelAndView("mporder/success");
		try {
			mav.addObject("openid", OpenidTracker.get());
			mav.addObject("orderId", orderId);
			orderInfo =  orderInfo.replaceAll("-", "%");
			mav.addObject("orderInfo", URLDecoder.decode(orderInfo, "UTF-8"));
		} catch(RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("门票下单成功方法出错")+HgLogger.getStackTrace(e));
		} catch (Exception e) {
			log.error("hsl.err", e);
			mav.setViewName("error");
			HgLogger.getInstance().error("zhuxy", newHeader("门票下单成功方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("门票下单成功方法开始"));
		return mav;
	}
	
	@RequestMapping("ajaxList")
	public void ajaxList(HttpServletRequest request, PrintWriter out,
			Integer pageNo, Integer pageSize) {
		HgLogger.getInstance().debug("zhuxy", newHeader("查询门票订单列表方法开始"));
		try {
			out.print(JSON.toJSONString(
					list(getUserId(request), pageNo, pageSize)));
		} catch(RuntimeException e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", newHeader("查询门票订单列表方法出错")+HgLogger.getStackTrace(e));
		} catch (HslapiException e) {
			out.print(getHslErrResponseJsonStr(e));
			HgLogger.getInstance().error("zhuxy", newHeader("查询门票订单列表方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("查询门票订单列表方法结束"));
	}
	
	/**
	 * 查询订单列表
	 * @param userId
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private MPQueryOrderResult list(String userId, Integer pageNo, Integer pageSize) {
		HslMPOrderQO hslMPOrderQO = new HslMPOrderQO();
		hslMPOrderQO.setUserId(userId);
		hslMPOrderQO.setDetail(true);
		hslMPOrderQO.setPageNo(pageNo);
		hslMPOrderQO.setPageSize(pageSize);
		hslMPOrderQO.setWithPolicy(false);
		hslMPOrderQO.setWithScenicSpot(true);
		return getOrder(hslMPOrderQO);
	}

	/**
	 * 查询订单
	 * @param hslMPOrderQO
	 * @return
	 */
	private MPQueryOrderResult getOrder(HslMPOrderQO hslMPOrderQO) {
		MPQueryOrderResult mpQueryOrderResponse = new MPQueryOrderResult();
		Pagination pagination = new Pagination();
		pagination.setCondition(hslMPOrderQO);
		pagination.setPageNo(hslMPOrderQO.getPageNo());
		pagination.setPageSize(hslMPOrderQO.getPageSize());
		try {
			pagination = mpOrderService.queryPagination(pagination);
			List<MPOrderDTO> mpOrderDTOList = (List<MPOrderDTO>)pagination.getList();
			if(mpOrderDTOList != null && mpOrderDTOList.size() > 0){
				mpQueryOrderResponse.setOrders(mpOrderDTOList);
				mpQueryOrderResponse.setTotalCount(mpOrderDTOList.size());
				mpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_OK);
				mpQueryOrderResponse.setMessage("订单查询成功");				
			}else{
				mpQueryOrderResponse.setOrders(null);
				mpQueryOrderResponse.setTotalCount(0);
				mpQueryOrderResponse.setResult(ApiResponse.RESULT_CODE_FAIL);
				mpQueryOrderResponse.setMessage("没有数据");
			}
		} catch (Exception e) {
			mpQueryOrderResponse.setResult(Constants.exceptionMap.get(17));
			mpQueryOrderResponse.setMessage(e.getMessage());
		}
		return mpQueryOrderResponse;
	}

	/**
	 * 查询门票订单详情
	 * @param orderId
	 * @return
	 */
	@RequestMapping("detail")
	public ModelAndView detail(String orderId) {
		HgLogger.getInstance().debug("zhuxy", newHeader("查询门票订单详情方法结束"));
		ModelAndView mav = new ModelAndView("mporder/detail");
		try {
			MPOrderDTO order = find(orderId);
			mav.addObject("order", order);
			mav.addObject("orderDate", new SimpleDateFormat(
					"yyyy-MM-dd").format(order.getCreateDate()));
		} catch(RuntimeException e){
			HgLogger.getInstance().error("zhuxy", newHeader("查询门票订单详情方法出错")+HgLogger.getStackTrace(e));
		} catch (HslapiException e) {
			log.error("hsl.err", e);
			mav.setViewName("error");
			HgLogger.getInstance().error("zhuxy", newHeader("查询门票订单详情方法出错")+HgLogger.getStackTrace(e));
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("查询门票订单详情方法结束"));
		return mav;
	}
	
	/**
	 * 查询门票订单详情
	 * @param orderId
	 * @return
	 */
	private MPOrderDTO find(String orderId) throws HslapiException{
		HslMPOrderQO mpOrderQO = new HslMPOrderQO();
		mpOrderQO.setDealerOrderCode(orderId);
		mpOrderQO.setDetail(true);
		mpOrderQO.setPageNo(1);
		mpOrderQO.setPageSize(1);
		mpOrderQO.setWithPolicy(true);
		mpOrderQO.setWithScenicSpot(true);
		MPQueryOrderResult mpQueryOrderResult = getOrder(mpOrderQO);
		if (success(mpQueryOrderResult)) {
			return mpQueryOrderResult.getOrders().get(0);
		} else {
			throw new HslapiException(mpQueryOrderResult.getMessage());
		}
	}

	/**
	 * 取消订单（这个功能h5暂时没有实现）
	 */
	@RequestMapping("cancel")
	public void cancel(PrintWriter out, String orderId){
		out.print(cancel(orderId));
	}
	
	/**
	 * 取消订单
	 * @param orderId
	 * @return
	 */
	private String cancel(String orderId) {
		// TODO Auto-generated method stub
		return null;
	}

	
	/**
	 * 查询景点详情
	 * @param scenicSpotId
	 * @return
	 * @throws HslapiException 
	 */
	private ScenicSpotDTO getScenicSpotDetail(String scenicSpotId) throws HslapiException {
		HslMPScenicSpotQO mpScenicSpotsQO = new HslMPScenicSpotQO();
		mpScenicSpotsQO.setScenicSpotId(scenicSpotId);
		mpScenicSpotsQO.setImagesFetchAble(true);
		mpScenicSpotsQO.setTcPolicyNoticeFetchAble(true);
		MPQueryScenicSpotsResult scenicSpotsResponse = getScenicSpot(mpScenicSpotsQO);
		if (success(scenicSpotsResponse)) {
			return scenicSpotsResponse.getScenicSpots().get(0);
		} else {
			HgLogger.getInstance().error("zhuxy", "移动端景点详情查询失败："+scenicSpotsResponse.getMessage());
			throw new HslapiException(scenicSpotsResponse.getMessage());
		}
	}


	/**
	 * 查询景点的方法
	 * @param mpScenicSpotsQO
	 * @param scenicSpotsResponse
	 */
	private MPQueryScenicSpotsResult getScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO) {
		HslMPScenicSpotQO hslMPScenicSpotQO=BeanMapperUtils.map(mpScenicSpotsQO, HslMPScenicSpotQO.class);
		MPQueryScenicSpotsResult scenicSpotsResponse = new MPQueryScenicSpotsResult();

		Map scenicSpotMap=null;
		try {
			scenicSpotMap = mpScenicSpotService.queryScenicSpot(hslMPScenicSpotQO);
			List<ScenicSpotDTO> scenicSpotDTOList;
			//是热门景点的话需要修改id
			if(mpScenicSpotsQO.getHot()){
				List<HotScenicSpotDTO> hotlist = (List<HotScenicSpotDTO>)scenicSpotMap.get("dto");
				scenicSpotDTOList = new ArrayList<ScenicSpotDTO>();
				if(hotlist!=null&&hotlist.size()>0){
					ScenicSpotDTO scenicSpotDTO;
					for(HotScenicSpotDTO hot: hotlist){
						scenicSpotDTO = new ScenicSpotDTO();
						scenicSpotDTO.setId(hot.getScenicSpotId());
						scenicSpotDTO.setScenicSpotBaseInfo(hot.getScenicSpotBaseInfo());
						scenicSpotDTOList.add(scenicSpotDTO);
					}
				}
			}else{
				List<PCScenicSpotDTO> pcScenicSpotDTOList = (List<PCScenicSpotDTO>) scenicSpotMap.get("dto");
				scenicSpotDTOList =BeanMapperUtils.getMapper().mapAsList(pcScenicSpotDTOList, ScenicSpotDTO.class);
			}
			if(scenicSpotDTOList != null && scenicSpotDTOList.size() > 0){
				scenicSpotsResponse.setScenicSpots(scenicSpotDTOList);
				scenicSpotsResponse.setTotalCount(Integer.parseInt(scenicSpotMap.get("count").toString()));				
			}else{
				scenicSpotsResponse.setScenicSpots(null);
				scenicSpotsResponse.setTotalCount(0);
				scenicSpotsResponse.setMessage("没有数据");
			}
		} catch (MPException e) {
			scenicSpotsResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			scenicSpotsResponse.setMessage(e.getMessage());
		}
		return scenicSpotsResponse;
	}
	

	/**
	 * 查询价格日历
	 * @param policyId
	 * @return
	 * @throws HslapiException
	 */
	private List<DateSalePriceDTO> getDateSalePrice(String policyId) throws HslapiException {
		HslMPDatePriceQO mpDatePriceQO = new HslMPDatePriceQO();
		mpDatePriceQO.setPolicyId(policyId);
		HslMPDatePriceQO hslMPDatePriceQO=BeanMapperUtils.map(mpDatePriceQO, HslMPDatePriceQO.class);
		
		MPQueryDatePriceResult datePriceResponse = new MPQueryDatePriceResult();
		Map datePriceMap=null;
		try {
			datePriceMap = mpScenicSpotService.queryDatePrice(hslMPDatePriceQO);
			datePriceResponse.setDateSalePrices((List<DateSalePriceDTO>)datePriceMap.get("dto"));
			datePriceResponse.setTotalCount(Integer.parseInt(datePriceMap.get("count").toString()));
		} catch (MPException e) {
			datePriceResponse.setResult(Constants.exceptionMap.get(e.getCode()));
			datePriceResponse.setMessage(e.getMessage());
		}
		if (success(datePriceResponse)) {
			return datePriceResponse.getDateSalePrices();
		} else {
			throw new HslapiException(datePriceResponse.getMessage());
		}
	}

	/**
	 * 根据景点id查询政策
	 * @param scenicSpotId
	 * @param policies
	 * @return
	 * @throws HslapiException 
	 */
	private List<PolicyDTO> getPolicyList(String scenicSpotId) throws HslapiException {
		HslMPPolicyQO mpPolicyQO = new HslMPPolicyQO();
		mpPolicyQO.setScenicSpotId(scenicSpotId);
		HslMPPolicyQO hslMPPolicyQO=BeanMapperUtils.map(mpPolicyQO, HslMPPolicyQO.class);
		MPQueryPolicyResult policyResponse = new MPQueryPolicyResult();
		Map policyMap = mpScenicSpotService.queryScenicPolicy(hslMPPolicyQO);
		policyResponse.setPolicies((List<PolicyDTO>)policyMap.get("dto"));
		policyResponse.setTotalCount(Integer.parseInt(policyMap.get("count").toString()));
		if (success(policyResponse)) {
			return policyResponse.getPolicies();
		} else {
			HgLogger.getInstance().error("zhuxy", "汇商旅移动端————》门票下单————》：景点政策查询失败");
			throw new HslapiException(policyResponse.getMessage());
		}
	}
}
