package slfx.mp.app.component.job;

import static slfx.mp.spi.common.MpEnumConstants.ExceptionCode.EXCEPTION_CODE_INTER_FAIL;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_CANCEL;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_OUTOFDATE;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_PREPARED;
import static slfx.mp.spi.common.MpEnumConstants.OrderStatusEnum.ORDER_STATUS_USED;
import hg.common.page.Pagination;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import slfx.mp.app.pojo.qo.MPOrderQO;
import slfx.mp.app.service.local.MPOrderLocalService;
import slfx.mp.command.ModifyMPOrderStatusCommand;
import slfx.mp.domain.model.order.MPOrder;
import slfx.mp.spi.exception.SlfxMpException;
import slfx.mp.tcclient.tc.domain.order.Order;
import slfx.mp.tcclient.tc.dto.order.SceneryOrderDetailDto;
import slfx.mp.tcclient.tc.pojo.Response;
import slfx.mp.tcclient.tc.pojo.order.response.ResultSceneryOrderDetail;
import slfx.mp.tcclient.tc.service.TcClientService;
import slfx.mp.tcclient.tc.service.impl.TcClientServiceImpl;

import com.alibaba.fastjson.JSON;

/**
 * 同步同程订单状态任务
 * 
 * @author zhurz
 */
@Component
public class MPOrderSyncJob {
	
	private final static Logger logger = LoggerFactory.getLogger(MPOrderSyncJob.class);
	
	@Autowired
	private TcClientService tcClientService;
	
	@Autowired
	private MPOrderLocalService mpOrderLocalService;
	
	/** 一次查询的订单数量 */
	private Integer handlerNum = 1000;
	
	/**
	 * 同步新建待游玩的订单状态
	 */
	public void syncOrder() {
		
		int currentPageNo = 1;
		int totalPage = 1;
		Pagination pagination = new Pagination();
		pagination.setPageNo(currentPageNo);
		pagination.setPageSize(handlerNum);
		MPOrderQO qo = new MPOrderQO();
		qo.setOrderStatus(ORDER_STATUS_PREPARED);
		qo.setCreateDateAsc(true);
		qo.setSupplierOrderCodeNotNull(true);
		pagination.setCondition(qo);

		for (; currentPageNo <= totalPage; currentPageNo++) {
			pagination = mpOrderLocalService.queryPagination(pagination);
			totalPage = pagination.getTotalPage();
			@SuppressWarnings("unchecked")
			List<MPOrder> orders = (List<MPOrder>) pagination.getList();
			if (orders.size() == 0)
				break;
			for (MPOrder order : orders) {
				try {
					SceneryOrderDetailDto dto = new SceneryOrderDetailDto();
					dto.setSerialIds(order.getSupplierOrderCode());
					Response<ResultSceneryOrderDetail> response = tcClientService
							.getSceneryOrderDetail(dto);
					// 不成功则抛出异常
					if (!"0".equals(response.getHead().getRspType())) {
						throw new SlfxMpException(EXCEPTION_CODE_INTER_FAIL,
								response.getHead().getRspDesc());
					}
					ResultSceneryOrderDetail result = response.getResult();
					Order remoteOrder = result.getOrder().get(0);

					ModifyMPOrderStatusCommand command = new ModifyMPOrderStatusCommand();
					command.setCancelAble(remoteOrder.getEnableCancel() == 0 ? false : true);
					// 1:新建（待游玩）订单
					// 2:取消订单
					// 3:游玩过订单
					// 4:预订未游玩订单
					switch (remoteOrder.getOrderStatus()) {
					case 1:
						command.setOrderStatus(ORDER_STATUS_PREPARED);
						break;
					case 2:
						command.setOrderStatus(ORDER_STATUS_CANCEL);
						break;
					case 3:
						command.setOrderStatus(ORDER_STATUS_USED);
						break;
					default:
						command.setOrderStatus(ORDER_STATUS_OUTOFDATE);
						break;
					}
					
					order.modifyMPOrderStatus(command);
					mpOrderLocalService.update(order);
				} catch (Exception e) {
					logger.error(e.getMessage());
				}
			}
		}

	}
	
	public static void main(String[] args) {
		TcClientService service = new TcClientServiceImpl();
		SceneryOrderDetailDto dto = new SceneryOrderDetailDto();
		dto.setSerialIds("st53d856bd210034d128");
		Response<ResultSceneryOrderDetail> response=service.getSceneryOrderDetail(dto);
//		ResultSceneryOrderDetail result=response.getResult();
		System.out.println(JSON.toJSONString(response, true));
	}
}
