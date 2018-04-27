package plfx.gjjp.app.service.api;

import hg.common.util.BeanMapperUtils;
import hg.common.util.DateUtil;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.gj.dto.GJAvailableFlightGroupDTO;
import plfx.api.client.api.v1.gj.request.FlightGJQO;
import plfx.api.client.api.v1.gj.request.FlightMoreCabinsGJQO;
import plfx.api.client.api.v1.gj.request.FlightPolicyGJQO;
import plfx.api.client.api.v1.gj.response.FlightGJResponse;
import plfx.api.client.api.v1.gj.response.FlightMoreCabinsGJResponse;
import plfx.api.client.api.v1.gj.response.FlightPolicyGJResponse;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.api.PlfxApiAction;
import plfx.api.client.common.api.PlfxApiService;
import plfx.gjjp.app.common.adapter.GJJPApiAdapter;
import plfx.gjjp.app.component.cache.AvailableJourneyCacheManager;
import plfx.gjjp.app.component.cache.RewPolicyInfoCacheManager;
import plfx.jp.app.service.local.policy.PolicySnapshotLocalService;
import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.jp.domain.model.policy.JPPlatformPolicySnapshot;
import plfx.jp.pojo.system.SupplierCode;
import plfx.yxgjclient.component.cache.YXGJServiceCacheManager;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.FareInfo;
import plfx.yxgjclient.pojo.param.Flight;
import plfx.yxgjclient.pojo.param.QueryAirRulesParams;
import plfx.yxgjclient.pojo.param.QueryFlightParams;
import plfx.yxgjclient.pojo.param.QueryFlightResult;
import plfx.yxgjclient.pojo.param.QueryMoreCabinsParams;
import plfx.yxgjclient.pojo.param.QueryPoliciesParams;
import plfx.yxgjclient.pojo.param.RewPolicyInfo;
import plfx.yxgjclient.pojo.request.QueryAirRulesRQ;
import plfx.yxgjclient.pojo.request.QueryFlightRQ;
import plfx.yxgjclient.pojo.request.QueryMoreCabinsRQ;
import plfx.yxgjclient.pojo.request.QueryPoliciesRQ;
import plfx.yxgjclient.pojo.response.QueryAirRulesRS;
import plfx.yxgjclient.pojo.response.QueryFlightRS;
import plfx.yxgjclient.pojo.response.QueryMoreCabinsRS;
import plfx.yxgjclient.pojo.response.QueryPoliciesRS;
import plfx.yxgjclient.service.YIGJService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：国际航班政策API服务
 * @类修改者：
 * @修改日期：2015-7-14上午11:23:48
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14上午11:23:48
 */
@Service
public class FlightApiService implements PlfxApiService {
	
	@Autowired
	private YIGJService yigjService;
	
	@Autowired
	private AvailableJourneyCacheManager availableJourneyCacheManager;
	
	@Autowired
	private RewPolicyInfoCacheManager rewPolicyInfoCacheManager;
	
	@Autowired
	private PolicySnapshotLocalService policySnapshotLocalService;
	
	@Autowired
	private YXGJServiceCacheManager yxgjServiceCacheManager;
	
	private final static String devName = "zhurz";
	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}

	/**
	 * @方法功能说明：查询航班
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:11:31
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:FlightGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_QueryFlight)
	public FlightGJResponse queryFlight(FlightGJQO QO) {
		
		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询航班->queryFlight(%s)", trackingToken,
						JSON.toJSONString(QO, true)), trackingToken);

		QueryFlightParams params = new QueryFlightParams();
		params.setOrgCity(QO.getOrgCity());
		params.setDstCity(QO.getDstCity());
		params.setOrgDate(DateUtil.formatDateTime(QO.getOrgDate(), "yyyy-MM-dd"));
		if (QO.getDstDate() != null)
			params.setDstDate(DateUtil.formatDateTime(QO.getDstDate(), "yyyy-MM-dd"));

		QueryFlightRQ queryFlightRQ = new QueryFlightRQ();
		queryFlightRQ.setQueryFlightParams(params);

		FlightGJResponse response = new FlightGJResponse();
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s}->查询航班->yigjService.queryFlight(%s)", trackingToken,
						JSON.toJSONString(queryFlightRQ, true)), trackingToken);

		QueryFlightRS flightRS = yigjService.queryFlight(queryFlightRQ, true);

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->查询航班->接口返回:(%s)", trackingToken,
						JSON.toJSONString(flightRS, true)), trackingToken);
		
		if (!"T".equals(flightRS.getIsSuccess())) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("未查询到航班");
			return response;
		}
		
		List<GJAvailableFlightGroupDTO> availableFlightGroups = new ArrayList<GJAvailableFlightGroupDTO>();
		QueryFlightResult flightResult = flightRS.getQueryFlightResult();
		List<AvailableJourney> availableJourneys = flightResult.getAvailableJourney();
		for (AvailableJourney availableJourney : availableJourneys) {
			availableJourneyCacheManager.cacheAvailableJourney(availableJourney);
			availableFlightGroups.add(GJJPApiAdapter.availableFlightGroup.convertDTO(availableJourney));
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->查询航班->缓存航班完毕", trackingToken), trackingToken);
		
		response.setAvailableFlightGroups(availableFlightGroups);
		
		return response;
	}
	
	/**
	 * @方法功能说明：获取出票规则
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-16上午9:58:43
	 * @修改内容：
	 * @参数：@param availableJourney
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String getAirRules(AvailableJourney availableJourney){

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->获取出票规则->getAirRules(%s)", trackingToken,
						JSON.toJSONString(availableJourney, true)), trackingToken);
		
		QueryAirRulesRQ airRulesRQ = new QueryAirRulesRQ();
		QueryAirRulesParams airRuleParams = new QueryAirRulesParams();
		List<Flight> flights = availableJourney.getTakeoffAvailJourney().getFlights();
		airRuleParams.setOrgCity(flights.get(0).getOrgCity());
		airRuleParams.setDstCity(flights.get(flights.size() - 1).getDstCity());
		airRuleParams.setStartTime(flights.get(0).getStartTime());
		FareInfo fareInfo = null;
		for (FareInfo fi : availableJourney.getTakeoffAvailJourney().getFareInfos())
			if ("ADT".equals(fi.getPassType())) {
				fareInfo = fi;
				break;
			}
		if (fareInfo == null)
			availableJourney.getTakeoffAvailJourney().getFareInfos().get(0);
		airRuleParams.setFilingAirline(fareInfo.getFilingAirline());
		airRuleParams.setFareReference(fareInfo.getFareReference());
		airRuleParams.setRef1(fareInfo.getRef1());
		airRuleParams.setRef2(fareInfo.getRef2());
		airRulesRQ.setQueryAirRulesParams(airRuleParams);

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->获取出票规则->yigjService.queryAirRules(%s)", trackingToken,
						JSON.toJSONString(airRulesRQ, true)), trackingToken);
		
		QueryAirRulesRS airRulesRS = yigjService.queryAirRules(airRulesRQ, true);

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->获取出票规则->接口返回:(%s)", trackingToken,
						JSON.toJSONString(airRulesRS, true)), trackingToken);
		
		if ("T".equals(airRulesRS.getIsSuccess()))
			return airRulesRS.getQueryAirRulesResult().getResultData();
		
		return "";
	}
	
	/**
	 * @方法功能说明：查询同一国际航班组合更多舱位
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:13:01
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:FlightMoreCabinsGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_QueryFlightMoreCabins)
	public FlightMoreCabinsGJResponse queryFlightMoreCabins(FlightMoreCabinsGJQO QO) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询同一国际航班组合更多舱位->queryFlightMoreCabins(%s)", trackingToken,
						JSON.toJSONString(QO, true)), trackingToken);
		
		AvailableJourney availableJourney = availableJourneyCacheManager.getAvailableJourneyCache(QO.getFlightCabinGroupToken());
		FlightMoreCabinsGJResponse response = new FlightMoreCabinsGJResponse();
		
		if (availableJourney == null) {
			response.setResult(FlightMoreCabinsGJResponse.RESULT_NOT_EXIST);
			response.setMessage("航班舱位组合token过期或不存在");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询同一国际航班组合更多舱位->缓存获取成功(%s)", trackingToken,
						JSON.toJSONString(availableJourney, true)), trackingToken);

		QueryMoreCabinsRQ moreCabinsRQ = new QueryMoreCabinsRQ();
		QueryMoreCabinsParams moreCabinsParams = new QueryMoreCabinsParams();
		moreCabinsParams.setAvailableJourney(availableJourney);
		moreCabinsRQ.setQueryMoreCabinsParams(moreCabinsParams);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询同一国际航班组合更多舱位->yigjService.queryMoreCabins(%s)", trackingToken,
						JSON.toJSONString(moreCabinsRQ, true)), trackingToken);
		
		QueryMoreCabinsRS moreCabinsRS = yigjService.queryMoreCabins(moreCabinsRQ, true);

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->查询同一国际航班组合更多舱位->接口返回:(%s)", trackingToken,
						JSON.toJSONString(moreCabinsRS, true)), trackingToken);

		if (!"T".equals(moreCabinsRS.getIsSuccess())) {
			response.setResult(FlightMoreCabinsGJResponse.RESULT_NO_MORE);
			response.setMessage("没有更多的航班舱位组合");
			return response;
		}

		List<GJAvailableFlightGroupDTO> availableFlightGroups = new ArrayList<GJAvailableFlightGroupDTO>();

		List<AvailableJourney> moreAvailableJourneyList = moreCabinsRS
				.getQueryMoreCabinsResult().getAvailableJourney();

		List<Flight> takeoffFlights = availableJourney.getTakeoffAvailJourney().getFlights();

		List<Flight> backFlights = null;
		if (availableJourney.getBackAvailJourney() != null)
			backFlights = availableJourney.getBackAvailJourney().getFlights();
		//将从缓存中取得的航程信息中的单向飞行中转停留时间TransferTime和总飞行时间TotalDuration合并到更多舱位查询到的航程中
		for (AvailableJourney moreAvailableJourney : moreAvailableJourneyList) {

			List<Flight> moreTakeoffFlights = moreAvailableJourney.getTakeoffAvailJourney().getFlights();

			moreAvailableJourney.getTakeoffAvailJourney().setTotalDuration(availableJourney.getTakeoffAvailJourney().getTotalDuration());
			moreAvailableJourney.getTakeoffAvailJourney().setTransferTime(availableJourney.getTakeoffAvailJourney().getTransferTime());
			
			List<Flight> moreBackFlights = null;
			if (moreAvailableJourney.getBackAvailJourney() != null){
				moreBackFlights = moreAvailableJourney.getBackAvailJourney().getFlights();
				moreAvailableJourney.getBackAvailJourney().setTotalDuration(availableJourney.getBackAvailJourney().getTotalDuration());
				moreAvailableJourney.getBackAvailJourney().setTransferTime(availableJourney.getBackAvailJourney().getTransferTime());
			}
			//合并航班信息
			for (int i = 0; i < takeoffFlights.size(); i++) {
				Flight flight = takeoffFlights.get(i);
				Flight moreFlight = moreTakeoffFlights.get(i);
				Flight fligtCopy = BeanMapperUtils.map(flight, Flight.class);
				fligtCopy.setCabinCode(moreFlight.getCabinCode());
				fligtCopy.setCabinSales(moreFlight.getCabinSales());
				fligtCopy.setQcharge(moreFlight.getQcharge());
				fligtCopy.setCabinClass(moreFlight.getCabinClass());
				moreTakeoffFlights.set(i, fligtCopy);
			}
			if (backFlights != null)
				for (int i = 0; i < backFlights.size(); i++) {
					Flight flight = backFlights.get(i);
					Flight moreFlight = moreBackFlights.get(i);
					Flight fligtCopy = BeanMapperUtils.map(flight, Flight.class);
					fligtCopy.setCabinCode(moreFlight.getCabinCode());
					fligtCopy.setCabinSales(moreFlight.getCabinSales());
					fligtCopy.setQcharge(moreFlight.getQcharge());
					fligtCopy.setCabinClass(moreFlight.getCabinClass());
					moreBackFlights.set(i, fligtCopy);
				}

			availableJourneyCacheManager.cacheAvailableJourney(moreAvailableJourney);
			//返回参数类型转换
			availableFlightGroups.add(GJJPApiAdapter.availableFlightGroup.convertDTO(moreAvailableJourney));
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s}->查询同一国际航班组合更多舱位->缓存成功:(%s)", trackingToken,
						JSON.toJSONString(moreAvailableJourneyList, true)), trackingToken);
		
		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setAvailableFlightGroups(availableFlightGroups);
		
		return response;
	}

	String parseYxPassengerType(List<Integer> passengerTypeList) {
		if (passengerTypeList == null || passengerTypeList.size() == 0)
			// 默认一般成人
			return "1";
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < passengerTypeList.size(); i++) {
			if (i != 0)
				sb.append('^');
			sb.append(passengerTypeList.get(i));
		}
		return sb.toString();
	}
	
	/**
	 * @方法功能说明：查询国际航班舱位组合政策
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:13:45
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:FlightPolicyGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_QueryFlightPolicy)
	public FlightPolicyGJResponse queryFlightPolicy(FlightPolicyGJQO QO) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际航班舱位组合政策->queryFlightPolicy(%s)", trackingToken,
						JSON.toJSONString(QO, true)), trackingToken);
		
		AvailableJourney availableJourney = availableJourneyCacheManager.getAvailableJourneyCache(QO.getFlightCabinGroupToken());
		FlightPolicyGJResponse response = new FlightPolicyGJResponse();
		
		if (availableJourney == null) {
			response.setResult(FlightPolicyGJResponse.RESULT_NOT_EXIST);
			response.setMessage("航班舱位组合token过期或不存在");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际航班舱位组合政策->缓存获取成功(%s)", trackingToken,
						JSON.toJSONString(availableJourney, true)), trackingToken);

		QueryPoliciesRQ policiesRQ = new QueryPoliciesRQ();
		QueryPoliciesParams policiesParams = new QueryPoliciesParams();
		policiesParams.setPassengerType(parseYxPassengerType(QO.getPassengerType()));
		policiesParams.setSegType(availableJourney.getBackAvailJourney() == null ? "2" : "3");
		policiesParams.setPeopleNum(QO.getPeopleNum().toString());
		policiesParams.setIsPermitOpen("0");
		policiesParams.setPolicyType("1");  // 有奖励政策
		policiesParams.setSortType("ADT");  // 成人排序
		policiesParams.setIsBestPolicy("0");// 全部政策（最优政策返回没有encryptString）
		policiesParams.setEncryptString(availableJourney.getEncryptString());
		policiesRQ.setQueryPoliciesParams(policiesParams);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际航班舱位组合政策->yigjService.queryPolicies(%s)", trackingToken,
						JSON.toJSONString(policiesRQ, true)), trackingToken);

		QueryPoliciesRS policiesRS = yigjService.queryPolicies(policiesRQ, true);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际航班舱位组合政策->接口返回(%s)", trackingToken,
						JSON.toJSONString(policiesRS, true)), trackingToken);
		
		if (!"T".equals(policiesRS.getIsSuccess())) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("没有合适的政策");
			return response;
		}

		RewPolicyInfo rewPolicyInfo = policiesRS.getQueryPoliciesResult().getRewPolicyInfos().get(0);
		rewPolicyInfo.setFlightCabinGroupToken(QO.getFlightCabinGroupToken());
		rewPolicyInfo.setAirRules(getAirRules(availableJourney));
		rewPolicyInfoCacheManager.cacheRewPolicyInfo(rewPolicyInfo);
		//获取适合平台的价格政策快照
		JPPlatformPolicySnapshot policySnapshot = policySnapshotLocalService
				.getPolicySnapshot(QO.getFromDealerId(), SupplierCode.YEEXING_GJ);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->查询国际航班舱位组合政策->获取分销平台价格政策成功(%s)", trackingToken,
						JSON.toJSONString(policySnapshot, true)), trackingToken);

		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setFlightPolicy(GJJPApiAdapter.flightPolicy.convertDTO(rewPolicyInfo, policySnapshot));
		
		return response;
	}

}
