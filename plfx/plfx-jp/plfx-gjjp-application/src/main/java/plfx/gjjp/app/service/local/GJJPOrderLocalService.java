package plfx.gjjp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand;
import plfx.api.client.api.v1.gj.request.ApplyRefundTicketGJCommand.RefundPassengerInfo;
import plfx.gjjp.app.dao.GJJPOrderDao;
import plfx.gjjp.app.dao.GJPassengerDao;
import plfx.gjjp.app.pojo.qo.GJJPOrderQo;
import plfx.gjjp.app.pojo.qo.GJPassengerQo;
import plfx.gjjp.domain.common.GJJPConstants;
import plfx.gjjp.domain.model.GJJPOrder;
import plfx.gjjp.domain.model.GJJPTicket;
import plfx.gjjp.domain.model.GJPassenger;
import plfx.jp.app.common.PropertiesDict;
import plfx.jp.app.common.util.RemoteConfigUtils;
import plfx.jp.command.admin.gj.AddExceptionOrderCommand;
import plfx.jp.command.admin.gj.ApplyCancelGJJPOrderCommand;
import plfx.jp.command.admin.gj.ApplyCancelNoPayGJJPOrderCommand;
import plfx.jp.command.admin.gj.ApplyRefundGJJPTicketCommand;
import plfx.jp.command.pub.gj.CreateGJJPOrderLogCommand;
import plfx.jp.common.ThreadTrackingTokenGenerator;
import plfx.jp.pojo.exception.PLFXJPException;
import plfx.yxgjclient.pojo.param.ApplyCancelNoPayParams;
import plfx.yxgjclient.pojo.param.ApplyCancelParams;
import plfx.yxgjclient.pojo.param.ApplyRefundParams;
import plfx.yxgjclient.pojo.param.ApplyRefundResult;
import plfx.yxgjclient.pojo.param.PassengerInfo;
import plfx.yxgjclient.pojo.param.PerOperationIsSuc;
import plfx.yxgjclient.pojo.param.QueryOrderParams;
import plfx.yxgjclient.pojo.request.ApplyCancelNoPayRQ;
import plfx.yxgjclient.pojo.request.ApplyCancelRQ;
import plfx.yxgjclient.pojo.request.ApplyRefundRQ;
import plfx.yxgjclient.pojo.request.QueryOrderDetailRQ;
import plfx.yxgjclient.pojo.response.ApplyCancelNoPayRS;
import plfx.yxgjclient.pojo.response.ApplyCancelRS;
import plfx.yxgjclient.pojo.response.ApplyRefundRS;
import plfx.yxgjclient.pojo.response.QueryOrderDetailRS;
import plfx.yxgjclient.service.YIGJService;

import com.alibaba.fastjson.JSON;

/**
 * @类功能说明：国际机票订单服务
 * @类修改者：
 * @修改日期：2015-7-20下午1:57:40
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-7-20下午1:57:40
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GJJPOrderLocalService extends BaseServiceImpl<GJJPOrder, GJJPOrderQo, GJJPOrderDao> {

	@Autowired
	private GJJPOrderDao gjjpOrderDao;
	
	@Autowired
	private GJPassengerDao passengerDao;
	
	@Autowired
	private YIGJService yigjService;
	
	@Autowired
	private GJJPOrderLogLocalService orderLogLocalService;

	private final static String devName = "zhurz";
	private HgLogger getHgLogger() {
		return HgLogger.getInstance();
	}

	@Override
	protected GJJPOrderDao getDao() {
		return gjjpOrderDao;
	}

	/**
	 * @方法功能说明：新增异常订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午3:21:30
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void addExceptionOrder(AddExceptionOrderCommand command) throws PLFXJPException {

		if (command.getAdjustAmount() == null || command.getAdjustAmount() == 0)
			return;

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->新增异常订单->addExceptionOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);
		
		GJJPOrder jpOrder = getDao().get(command.getPlatformOrderId());
		if (jpOrder == null)
			throw new PLFXJPException("国际机票订单不存在");
		
		jpOrder.addExceptionOrder(command);
		getDao().update(jpOrder);
		
		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder.getId(), 
				"新增异常订单", command.getFromAdminId(),
				GJJPConstants.LOG_WORKER_TYPE_PLATFORM, String.format(
				"%s %.2f 元", command.getAdjustAmount() > 0 ? "增加" : "减少", 
				Math.abs(command.getAdjustAmount()))));
	}

	/**
	 * @方法功能说明：管理员申请取消已付款未出票的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午3:21:43
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void applyCancelOrder(ApplyCancelGJJPOrderCommand command) throws PLFXJPException {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消已付款未出票的订单->applyCancelOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);
		
		GJJPOrder jpOrder = getDao().get(command.getPlatformOrderId());
		
		if (jpOrder == null)
			throw new PLFXJPException("国际机票订单不存在");

		// 状态检查
		// 平台订单状态
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		// 非待出票状态下的订单不能取消
		if (!GJJPConstants.ORDER_STATUS_OUT_TICKET_WAIT.equals(orderStatus)) {
			throw new PLFXJPException("非待出票的订单不能取消");
		}

		ApplyCancelRQ cancelRQ = new ApplyCancelRQ();
		ApplyCancelParams cancelParams = new ApplyCancelParams();
		cancelRQ.setApplyCancelParams(cancelParams);
		cancelParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		cancelParams.setExtOrderId(jpOrder.getId());
		cancelParams.setCancelNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消已付款未出票的订单->yigjService.applyCancelOrder(%s)", trackingToken,
						JSON.toJSONString(cancelRQ, true)), trackingToken);
		
		ApplyCancelRS cancelRS = yigjService.applyCancelOrder(cancelRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消已付款未出票的订单->接口返回(%s)", trackingToken,
						JSON.toJSONString(cancelRS, true)), trackingToken);

		// 取消失败
		if (!"T".equals(cancelRS.getIsSuccess())) {
			
			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "管理员申请取消已付款未出票的订单", command.getFromAdminId(),
					GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消已付款未出票的订单失败"));
			
			throw new PLFXJPException("申请取消失败");
		}
		
		// 更新订单状态
		jpOrder.cancelToSupplierAccept();
		getDao().update(jpOrder);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "管理员申请取消已付款未出票的订单", command.getFromAdminId(),
				GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消已付款未出票的订单成功"));
		
	}

	/**
	 * @throws PLFXJPException 
	 * @方法功能说明：管理员申请取消未付款的订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午3:22:10
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void applyCancelNoPayOrder(ApplyCancelNoPayGJJPOrderCommand command) throws PLFXJPException {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消未付款的订单->applyCancelNoPayOrder(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);
		
		GJJPOrder jpOrder = getDao().get(command.getPlatformOrderId());
		
		if (jpOrder == null)
			throw new PLFXJPException("国际机票订单不存在");

		// 状态检查
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		if (!(GJJPConstants.ORDER_STATUS_ORDER_TO_SUPPLIER.equals(orderStatus)
			|| GJJPConstants.ORDER_STATUS_PAY_WAIT.equals(orderStatus) 
			|| GJJPConstants.ORDER_STATUS_AUDIT_FAIL.equals(orderStatus))) {
			throw new PLFXJPException("订单已被支付过或正在审核");
		}

		// 参数检查
		if (Integer.valueOf(6).equals(command.getCancelReasonType())
			&& StringUtils.isBlank(command.getCancelOtherReason())) {
			throw new PLFXJPException("缺少其他原因具体内容");
		}

		ApplyCancelNoPayRQ cancelNoPayRQ = new ApplyCancelNoPayRQ();
		ApplyCancelNoPayParams cancelNoPayParams = new ApplyCancelNoPayParams();
		cancelNoPayRQ.setApplyCancelNoPayParams(cancelNoPayParams);
		cancelNoPayParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		cancelNoPayParams.setExtOrderId(jpOrder.getId());
		cancelNoPayParams.setCancelReasonType(command.getCancelReasonType().toString());
		cancelNoPayParams.setCancelOtherReason(command.getCancelOtherReason());

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消未付款的订单->yigjService.applyCancelOrderNoPay(%s)", trackingToken,
						JSON.toJSONString(cancelNoPayRQ, true)), trackingToken);
		
		ApplyCancelNoPayRS cancelNoPayRS = yigjService.applyCancelOrderNoPay(cancelNoPayRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请取消未付款的订单->接口返回(%s)", trackingToken,
						JSON.toJSONString(cancelNoPayRS, true)), trackingToken);

		// 取消失败
		if (!"T".equals(cancelNoPayRS.getIsSuccess())) {

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "管理员申请取消未付款的订单", command.getFromAdminId(),
					GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消未付款的订单失败"));
			
			throw new PLFXJPException("申请取消失败");
		}

		// 更新订单和乘客机票状态
		jpOrder.adminCancelNoPayToSupplierAccept(command);
		getDao().update(jpOrder);
		

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "管理员申请取消未付款的订单", command.getFromAdminId(),
				GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请取消未付款的订单成功"));
		
	}

	/**
	 * @throws PLFXJPException 
	 * @方法功能说明：管理员申请退废票
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-20下午3:22:25
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void applyRefundTicket(ApplyRefundGJJPTicketCommand command) throws PLFXJPException {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请退废票->applyRefundTicket(%s)", trackingToken,
						JSON.toJSONString(command, true)), trackingToken);

		GJJPOrder jpOrder = getDao().get(command.getPlatformOrderId());
		
		if (jpOrder == null)
			throw new PLFXJPException("国际机票订单不存在");

		if (command.getPassengerIds() == null || command.getPassengerIds().size() == 0)
			throw new PLFXJPException("未选择任何乘客");
		
		GJPassengerQo passengerQo = new GJPassengerQo();
		passengerQo.setIds(command.getPassengerIds());
		GJJPOrderQo orderQo = new GJJPOrderQo();
		orderQo.setId(command.getPlatformOrderId());
		passengerQo.setJpOrderQo(orderQo);
		List<GJPassenger> passengers = passengerDao.queryList(passengerQo);
		if (passengers == null || passengers.size() == 0)
			return;
		
		// 状态检查
		// 平台订单状态
		Integer orderStatus = jpOrder.getStatus().getCurrentValue();
		if (!(GJJPConstants.ORDER_STATUS_OUT_TICKET_ALREADY.equals(orderStatus)
			|| GJJPConstants.ORDER_STATUS_REFUND_WAIT.equals(orderStatus) 
			|| GJJPConstants.ORDER_STATUS_REFUND_SOME.equals(orderStatus))) {
			throw new PLFXJPException("订单不能退废票");
		}

		// 检查是否可以当日退票
		if (Integer.valueOf(1).equals(command.getRefundType())) {
			Date now = new Date();
			Date bookTime = jpOrder.getBaseInfo().getBookTime();
			Date startTime = jpOrder.getSegmentInfo().getTakeoffFlights().get(0).getStartTime();
			// 废票条件：当天定的票且不是当天的航班
			if (!(DateUtils.isSameDay(bookTime, now) && DateUtils.truncatedCompareTo(startTime, bookTime, Calendar.DATE) > 0)) {
				throw new PLFXJPException("无法当日废票");
			}
		}

		ApplyRefundRQ refundRQ = new ApplyRefundRQ();
		ApplyRefundParams refundParams = new ApplyRefundParams();
		refundRQ.setApplyRefundParams(refundParams);

		refundParams.setOrderId(jpOrder.getBaseInfo().getSupplierOrderId());
		refundParams.setExtOrderId(jpOrder.getId());
		refundParams.setRefundType(command.getRefundType().toString());
		refundParams.setRefundMemo(command.getRefundMemo());
		List<PassengerInfo> refundPassengerInfos = new ArrayList<PassengerInfo>();
		//参数中添加乘客信息
		for (GJPassenger passenger : passengers) {
			PassengerInfo passengerInfo=new PassengerInfo();
			passengerInfo.setPassengerName(passenger.getBaseInfo().getPassengerName());
			String ticketString=null;
			//遍历该乘客机票，拼接票号
			for(GJJPTicket gjjpTicket : passenger.getTickets()){
				ticketString=gjjpTicket.getTicketNo()+"/";
			}
			ticketString=ticketString.substring(0, ticketString.length()-1);
			passengerInfo.setOrdDetEticketNo(ticketString);
			refundPassengerInfos.add(passengerInfo);
		}
		
		refundParams.setPassengerInfos(refundPassengerInfos);
		refundParams.setRefundNotifyUrl(RemoteConfigUtils.getValue(PropertiesDict.YXGJ_NOTIFY_URL));

		// 申请成功的乘客
		List<RefundPassengerInfo> succPassengerInfos = new ArrayList<ApplyRefundTicketGJCommand.RefundPassengerInfo>();
		// 申请失败的乘客
		List<RefundPassengerInfo> failPassengerInfos = new ArrayList<ApplyRefundTicketGJCommand.RefundPassengerInfo>();
		// 发起退票的乘机人
		Map<String, GJPassenger> passengerNameMap = new HashMap<String, GJPassenger>();
		
		for (GJPassenger passenger : passengers) {
			passengerNameMap.put(passenger.getBaseInfo().getPassengerName(), passenger);
		}

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请退废票->yigjService.applyRefundTicket(%s)", trackingToken,
						JSON.toJSONString(refundRQ, true)), trackingToken);
		
		// 调用供应商退废票接口
		ApplyRefundRS refundRS = yigjService.applyRefundTicket(refundRQ);
		
		getHgLogger().info(getClass(), devName,
				String.format("(%s)->管理员申请退废票->接口返回(%s)", trackingToken,
						JSON.toJSONString(refundRS, true)), trackingToken);

		if (!"T".equals(refundRS.getIsSuccess())) {

			// 记录操作日志
			orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
					.getId(), "管理员申请退废票", command.getFromAdminId(),
					GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请退废票失败"));
			
			throw new PLFXJPException("退废票申请失败");
		}

		ApplyRefundResult refundResult = refundRS.getApplyRefundResult();
		for (PerOperationIsSuc perOperationIsSuc : refundResult.getPerPassengerIsSucs()) {
			GJPassenger passenger = passengerNameMap.get(perOperationIsSuc.getPassengerName());
			if (passenger == null)
				continue;
			RefundPassengerInfo passengerInfo = new RefundPassengerInfo();
			passengerInfo.setIdNo(passenger.getBaseInfo().getIdNo());
			passengerInfo.setIdType(passenger.getBaseInfo().getIdType());
			passengerInfo.setPassengerName(passenger.getBaseInfo().getPassengerName());
			if ("T".equals(perOperationIsSuc.getPerIsSuccess())) {
				succPassengerInfos.add(passengerInfo);
				passenger.adminRefundSupplierAccept(command);
			} else
				failPassengerInfos.add(passengerInfo);
		}

		// 更新订单和乘客机票状态
		getDao().update(jpOrder);

		// 记录操作日志
		orderLogLocalService.recordLog(new CreateGJJPOrderLogCommand(jpOrder
				.getId(), "管理员申请退废票", command.getFromAdminId(),
				GJJPConstants.LOG_WORKER_TYPE_PLATFORM, "管理员申请退废票成功"));
		
	}
	
	/**
	 * @方法功能说明：同步供应商订单
	 * @修改者名字：zhurz
	 * @修改时间：2015-7-23下午3:21:21
	 * @修改内容：
	 * @参数：@param platformOrderId
	 * @return:void
	 * @throws
	 */
	public void syncSupplierOrder(String platformOrderId) {

		// 跟踪代码作为日志搜索关键字
		String trackingToken = ThreadTrackingTokenGenerator.get();

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->同步供应商订单->syncSupplierOrder(%s)", trackingToken,
						platformOrderId), trackingToken);
		
		GJJPOrder jpOrder = getDao().get(platformOrderId);
		if (jpOrder == null){
			getHgLogger().info(getClass(), devName,
					String.format("(%s)->同步供应商订单->订单不存在(%s)", trackingToken,
							platformOrderId), trackingToken);
			return;
		}
		

		QueryOrderDetailRQ orderDetailRQ = new QueryOrderDetailRQ();
		QueryOrderParams queryOrderParams = new QueryOrderParams();
		orderDetailRQ.setQueryOrderParams(queryOrderParams);
		queryOrderParams.setExtOrderId(jpOrder.getId());

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->同步供应商订单->yigjService.queryOrderDetail(%s)", trackingToken,
						JSON.toJSONString(orderDetailRQ, true)), trackingToken);
		
		QueryOrderDetailRS orderDetailRS = yigjService.queryOrderDetail(orderDetailRQ);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->同步供应商订单->接口返回(%s)", trackingToken,
						JSON.toJSONString(orderDetailRS, true)), trackingToken);

		if (!"T".equals(orderDetailRS.getIsSuccess()))
			return;

		jpOrder.syncSupplierOrder(orderDetailRS.getQueryOrderDetailResult());
		getDao().update(jpOrder);

		getHgLogger().info(getClass(), devName,
				String.format("(%s)->同步供应商订单->同步成功(%s)", trackingToken,
						JSON.toJSONString(orderDetailRS, true)), trackingToken);
		
	}

}
