package slfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.api.base.ApiResponse;
import slfx.api.v1.request.command.mp.MPOrderCancelCommand;
import slfx.api.v1.request.command.mp.MPOrderCreateCommand;
import slfx.api.v1.request.command.mp.MPSyncOrderCommand;
import slfx.api.v1.request.qo.mp.MPOrderQO;
import slfx.api.v1.response.mp.MPOrderCancelResponse;
import slfx.api.v1.response.mp.MPOrderCreateResponse;
import slfx.api.v1.response.mp.MPQueryOrderResponse;
import slfx.api.v1.response.mp.MPSyncOrderResponse;
import slfx.mp.app.service.local.MPOrderLocalService;
import slfx.mp.command.SyncOrderCommand;
import slfx.mp.domain.model.order.MPOrder;
import slfx.mp.pojo.dto.order.MPOrderDTO;
import slfx.mp.pojo.dto.order.MPOrderStatusDTO;
import slfx.mp.qo.PlatformOrderQO;
import slfx.mp.spi.common.MpEnumConstants.ExceptionCode;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.spi.inter.PlatformOrderService;
import slfx.mp.spi.inter.api.ApiMPOrderService;


@Service
public class ApiMPOrderServiceImpl implements ApiMPOrderService {
	
	@Autowired
	private PlatformOrderService platformOrderService;
	@Autowired
	private MPOrderLocalService mpOrderLocalService;
	@Override
	public MPOrderCancelResponse apiCancelOrder(MPOrderCancelCommand command) {
		HgLogger.getInstance().info("wuyg", "门票取消订单");
		MPOrderCancelResponse response = new MPOrderCancelResponse();
		try {
			response.setCancelSuccess(mpOrderLocalService.apiCancelOrder(command));
		} catch (SlfxMpException e) {
			HgLogger.getInstance().error("wuyg", "门票取消订单：失败, code:"+e.getCode()+", "+e.getMessage());
			e.printStackTrace();
			response.setCancelSuccess(false);
			response.setResult(ApiResponse.RESULT_CODE_FAILE);
			response.setMessage(e.getMessage());
		}
		if(response.getCancelSuccess())
			HgLogger.getInstance().info("wuyg", "门票取消订单：成功");
		else
			HgLogger.getInstance().error("wuyg", "门票取消订单：失败");
		return response;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MPQueryOrderResponse apiQueryOrder(MPOrderQO qo) {
		HgLogger.getInstance().info("wuyg", "门票订单分页查询");
		MPQueryOrderResponse response = new MPQueryOrderResponse();
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		PlatformOrderQO orderQO = new PlatformOrderQO();
		orderQO.setUserId(qo.getUserId());
		orderQO.setId(qo.getOrderId());
		pagination.setCondition(orderQO);
		try {
			pagination = platformOrderService.queryPagination(pagination);
			response.setOrders((List<MPOrderDTO>) pagination.getList());
			response.setTotalCount(pagination.getTotalCount());
		} catch (Exception e) {
			HgLogger.getInstance().error("wuyg", "门票订单分页查询：失败, "+e.getMessage());
			response.setResult(ApiResponse.RESULT_CODE_FAILE);
		}
		return response;
	}

	@Override
	public MPOrderCreateResponse apiCreateOrder(MPOrderCreateCommand command) {
		HgLogger.getInstance().info("wuyg", "门票创建订单");
		MPOrderCreateResponse response = new MPOrderCreateResponse();
		try {
			String orderId = platformOrderService.apiOrderTicket(command);
			response.setOrderId(orderId);
		} catch (SlfxMpException e) {
			response.setMessage(e.getMessage());
			HgLogger.getInstance().error("wuyg", "门票创建订单：失败 ,code:"+e.getCode()+", "+e.getMessage());
			response.setResult(MPOrderCreateResponse.RESULT_CODE_FAILE);
		}
		return response;
	}

	@Override
	public MPSyncOrderResponse syncOrder(MPSyncOrderCommand command) {
		HgLogger.getInstance().info("wuyg", "门票同步单个订单状态");
		MPSyncOrderResponse response = new MPSyncOrderResponse();
		try {
			MPOrder mporder = mpOrderLocalService
					.syncOrder(new SyncOrderCommand(command.getOrderId()));
			if (mporder == null){
				HgLogger.getInstance().error("wuyg", "门票同步单个订单状态：订单不存在 ");
				throw new SlfxMpException(ExceptionCode.EXCEPTION_CODE_ORDER_NOT_EXITS, "订单不存在");
			}
			MPOrderStatusDTO status = BeanMapperUtils.getMapper().map(mporder.getStatus(), MPOrderStatusDTO.class);
			response.setCancelRemark(mporder.getCancelRemark());
			response.setStatus(status);
		} catch (SlfxMpException e) {
			response.setMessage(e.getMessage());
			HgLogger.getInstance().error("wuyg", "门票同步单个订单状态：失败, code:"+e.getCode()+", "+e.getMessage());
			response.setResult(MPOrderCreateResponse.RESULT_CODE_FAILE);
		}
		return response;
	}

}
