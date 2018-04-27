package plfx.mp.app.service.spi;

import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.api.client.api.v1.mp.request.command.MPOrderCancelCommand;
import plfx.api.client.api.v1.mp.request.command.MPOrderCreateCommand;
import plfx.api.client.api.v1.mp.request.command.MPSyncOrderCommand;
import plfx.api.client.api.v1.mp.request.qo.MPOrderQO;
import plfx.api.client.api.v1.mp.response.MPOrderCancelResponse;
import plfx.api.client.api.v1.mp.response.MPOrderCreateResponse;
import plfx.api.client.api.v1.mp.response.MPQueryOrderResponse;
import plfx.api.client.api.v1.mp.response.MPSyncOrderResponse;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.mp.app.service.local.MPOrderLocalService;
import plfx.mp.command.SyncOrderCommand;
import plfx.mp.domain.model.order.MPOrder;
import plfx.mp.pojo.dto.order.MPOrderDTO;
import plfx.mp.pojo.dto.order.MPOrderStatusDTO;
import plfx.mp.qo.PlatformOrderQO;
import plfx.mp.spi.common.MpEnumConstants.ExceptionCode;
import plfx.mp.spi.exception.SlfxMpException;
import plfx.mp.spi.inter.PlatformOrderService;
import plfx.mp.spi.inter.api.ApiMPOrderService;

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
