package plfx.gjjp.app.service.api;

import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.gj.dto.GJJPOrderDTO;
import plfx.api.client.api.v1.gj.request.ApplyCancelNoPayOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyCancelOrderGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand;
import plfx.api.client.api.v1.gj.request.CreateJPOrderGJCommand;
import plfx.api.client.api.v1.gj.request.JPOrderGJQO;
import plfx.api.client.api.v1.gj.request.PayToJPOrderGJCommand;
import plfx.api.client.api.v1.gj.response.ApplyCancelNoPayOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyCancelOrderGJResponse;
import plfx.api.client.api.v1.gj.response.ApplyRefundTicketGJResponse;
import plfx.api.client.api.v1.gj.response.CreateJPOrderGJResponse;
import plfx.api.client.api.v1.gj.response.JPOrderGJResponse;
import plfx.api.client.api.v1.gj.response.PayToJPOrderGJResponse;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.api.PlfxApiAction;
import plfx.api.client.common.api.PlfxApiService;
import plfx.gjjp.app.common.adapter.GJJPApiAdapter;
import plfx.gjjp.app.component.cache.AvailableJourneyCacheManager;
import plfx.gjjp.app.component.cache.RewPolicyInfoCacheManager;
import plfx.gjjp.app.service.local.GJJPOrderApiLocalService;
import plfx.gjjp.app.service.local.GJJPOrderLogLocalService;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.jp.app.common.PropertiesDict;
import plfx.jp.app.common.util.RemoteConfigUtils;
import plfx.jp.app.component.cache.DealerCacheManager;
import plfx.jp.app.service.local.policy.PolicySnapshotLocalService;
import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.yxgjclient.pojo.param.AvailableJourney;
import plfx.yxgjclient.pojo.param.ContacterInfo;
import plfx.yxgjclient.pojo.param.CreateOrderParams;
import plfx.yxgjclient.pojo.param.PassengerInfo;
import plfx.yxgjclient.pojo.param.RewPolicyInfo;
import plfx.yxgjclient.pojo.request.CreateOrderRQ;
import plfx.yxgjclient.pojo.response.CreateOrderRS;
import plfx.yxgjclient.service.YIGJService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：国际机票订单API服务
 * @类修改者：
 * @修改日期：2015-7-14上午11:23:18
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-14上午11:23:18
 */
@Service
public class GJJPOrderApiService implements PlfxApiService {

	@Autowired
	private YIGJService yigjService;
	
	@Autowired
	private AvailableJourneyCacheManager availableJourneyCacheManager;
	
	@Autowired
	private RewPolicyInfoCacheManager rewPolicyInfoCacheManager;
	
	@Autowired
	private DealerCacheManager dealerCacheManager;
	
	@Autowired
	private PolicySnapshotLocalService policySnapshotLocalService;
	
	@Autowired
	private GJJPOrderLogLocalService orderLogLocalService;
	
	@Autowired
	private GJJPOrderApiLocalService jpOrderApiLocalService;

	private final static String devName = "zhurz";
	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}
	
	/**
	 * @方法功能说明：创建国际机票订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:14:30
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateJPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_CreateJPOrder)
	public CreateJPOrderGJResponse createJPOrder(CreateJPOrderGJCommand command) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->createJPOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);
		
		CreateJPOrderGJResponse response = new CreateJPOrderGJResponse();
		
		// 有奖励政策
		RewPolicyInfo rewPolicyInfo = rewPolicyInfoCacheManager.getAvailableJourneyCache(command.getFlightAndPolicyToken());

		if (rewPolicyInfo == null) {
			response.setResult(CreateJPOrderGJResponse.RESULT_POLICY_NOT_EXIST);
			response.setMessage("航班舱位组合和对应政策token过期或不存在");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->获取有奖励的政策缓存成功(%s)", trackingToken,
						JSON.toJSONString(rewPolicyInfo, true)), trackingToken);

		// 可用航班组合
		AvailableJourney availableJourney = availableJourneyCacheManager.getAvailableJourneyCache(rewPolicyInfo.getFlightCabinGroupToken());
		
		if (availableJourney == null) {
			response.setResult(CreateJPOrderGJResponse.RESULT_FLIGHT_NOT_EXIST);
			response.setMessage("航班舱位组合token过期或不存在");
			return response;
		}
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->获取可用航班组合缓存成功(%s)", trackingToken,
						JSON.toJSONString(availableJourney, true)), trackingToken);
		
		// 创建平台订单
		GJJPOrder order = jpOrderApiLocalService.createPlatformOrder(command, availableJourney, rewPolicyInfo);

		if (order == null) {
			response.setResult(CreateJPOrderGJResponse.RESULT_DEALER_ORDER_ID_EXIST);
			response.setMessage("经销商的订单ID已存在");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->创建平台订单成功(%s)", trackingToken,
						order.getId()), trackingToken);
		
		CreateOrderRQ createOrderRQ = new CreateOrderRQ();
		CreateOrderParams createOrderParams = new CreateOrderParams();
		createOrderRQ.setCreateOrderParams(createOrderParams);
		
		createOrderParams.setExtOrderId(order.getId());
		createOrderParams.setAuditNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));
		createOrderParams.setEncryptString(rewPolicyInfo.getEncryptString());
		createOrderParams.setOrdMemo(order.getBaseInfo().getRemark());
		
		// 联系人
		ContacterInfo contacterInfo = new ContacterInfo();
		createOrderParams.setContacterInfo(contacterInfo);
		contacterInfo.setContactName(order.getContacterInfo().getContactName());
		contacterInfo.setContactPhone(order.getContacterInfo().getContactMobile());
		contacterInfo.setContactAddress(order.getContacterInfo().getContactAddress());
		contacterInfo.setContactMailAdd(order.getContacterInfo().getContactMail());

		// 乘客
		List<PassengerInfo> passengerInfos = new ArrayList<PassengerInfo>();
		createOrderParams.setPassengerInfos(passengerInfos);
		for (GJPassenger gjPassenger : order.getPassengers())
			passengerInfos.add(GJJPApiAdapter.passenger.convertYXGJDTO(gjPassenger));

		// 创建供应商订单
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->yigjService.createOrder(%s)", trackingToken,
						JSON.toJSONString(createOrderRQ, true)), trackingToken);
		
		CreateOrderRS createOrderRS = yigjService.createOrder(createOrderRQ);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->接口调用返回(%s)", trackingToken,
						JSON.toJSONString(createOrderRS, true)), trackingToken);

		// 更新平台订单
		GJJPOrderDTO orderDTO = jpOrderApiLocalService.createSupplierOrderOver(
				order.getId(), createOrderRS);
		
		if (orderDTO == null) {
			response.setResult(ApiResponse.RESULT_FAILURE);
			response.setMessage("订单创建失败");
			return response;
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->创建国际机票订单->更新平台订单成功(%s)", trackingToken,
						JSON.toJSONString(orderDTO, true)), trackingToken);
		
		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setOrder(orderDTO);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(order
				.getId(), "创建订单", command.getFromDealerCode(),
				GJJPConstants.LOG_WORKER_TYPE_DEALER, "创建订单成功"));

		return response;
	}
	
	/**
	 * @方法功能说明：为国际机票订单付款
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:15:09
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:CreateJPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_PayToJPOrder)
	public PayToJPOrderGJResponse payToJPOrder(PayToJPOrderGJCommand command) {
		return jpOrderApiLocalService.payToJPOrder(command);
	}
	
	/**
	 * @方法功能说明：查询国际机票订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:15:50
	 * @修改内容：
	 * @参数：@param QO
	 * @参数：@return
	 * @return:JPOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_QueryJPOrder)
	public JPOrderGJResponse queryJPOrder(JPOrderGJQO QO) {
		JPOrderGJResponse response = new JPOrderGJResponse();
		response.setResult(ApiResponse.RESULT_SUCCESS);
		response.setOrders(jpOrderApiLocalService.queryJPOrder(QO));
		return response;
	}
	
	/**
	 * @方法功能说明：申请取消未付款的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:16:47
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyCancelNoPayOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_ApplyCancelNoPayOrder)
	public ApplyCancelNoPayOrderGJResponse applyCancelNoPayOrder(ApplyCancelNoPayOrderGJCommand command) {
		return jpOrderApiLocalService.applyCancelNoPayOrder(command);
	}

	/**
	 * @方法功能说明：申请取消已付款未出票的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:17:59
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyCancelOrderGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_ApplyCancelOrder)
	public ApplyCancelOrderGJResponse applyCancelOrder(ApplyCancelOrderGJCommand command) {
		return jpOrderApiLocalService.applyCancelOrder(command);
	}
	
	/**
	 * @方法功能说明：申请退废票(成功出票时可调用)
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-14上午11:18:07
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:ApplyRefundTicketGJResponse
	 * @throws
	 */
	@PlfxApiAction(PlfxApiAction.GJ_ApplyRefundTicket)
	public ApplyRefundTicketGJResponse applyRefundTicket(ApplyRefundTicketGJCommand command) {
		return jpOrderApiLocalService.applyRefundTicket(command);
	}
	
}
