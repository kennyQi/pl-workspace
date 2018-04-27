package hsl.app.service.spi.jp;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hsl.app.common.util.EntityConvertUtils;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.app.service.local.jp.JPFlightPolicyLocalService;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.domain.model.jp.JPOrder;
import hsl.pojo.command.JPOrderCreateCommand;
import hsl.pojo.command.UpdateJPOrderStatusCommand;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.jp.FlightPolicyDTO;
import hsl.pojo.dto.jp.JPOrderCreateDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.RefundActionTypeDTO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.command.JPOrderCommand;
import hsl.spi.inter.api.jp.JPService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
import slfx.api.v1.request.command.jp.JPCancelTicketCommand;
import slfx.api.v1.request.qo.jp.JPAirCodeQO;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.request.qo.jp.JPPolicyQO;

@Service("jpService")
public class JPServiceImpl implements JPService {

	@Autowired
	@Qualifier("jpFlightLocalService")
	private JPFlightLocalService jpFlightLocalService;

	@Autowired
	@Qualifier("jpFlightPolicyLocalService")
	private JPFlightPolicyLocalService jpFlightPolicyLocalService;

	@Autowired
	@Qualifier("jpOrderLocalService")
	private JPOrderLocalService jpOrderLocalService;

	@Override
	public FlightDTO queryUnique(BaseQo qo) {
		return null;
	}

	@Override
	public List<FlightDTO> queryList(BaseQo qo) {
		return null;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		return jpOrderLocalService.queryPaginationInit(pagination);
	}

	@Override
	public List<FlightDTO> queryFlight(JPFlightQO qo) {
		return jpFlightLocalService.queryFlight(qo);
	}

	@Override
	public FlightPolicyDTO queryFlightPolicy(JPPolicyQO qo) {
		return jpFlightPolicyLocalService.queryFlightPolicy(qo);
	}

	@Override
	public void askOrderTicket(JPAskOrderTicketCommand command) {
		jpFlightLocalService.askOrderTicket(command);
	}

	@Override
	public List<JPOrderDTO> queryOrder(HslJPOrderQO qo) {

		// QO转化
		/*
		 * HslJPOrderQO hslqo = new HslJPOrderQO();
		 * 
		 * hslqo.setDealerOrderCode(qo.getOrderId());
		 * hslqo.setUserId(qo.getUserId()); hslqo.setPageNo(qo.getPageNo());
		 * hslqo.setPageSize(qo.getPageSize());
		 */
		// 查询机票订单
		List<JPOrder> jpOrders = jpOrderLocalService.queryOrder(qo);
		
		List<JPOrderDTO> jpOrderDTOList = EntityConvertUtils.convertEntityToDtoList(jpOrders, JPOrderDTO.class);

		return jpOrderDTOList;
		/*
		返回值转化
		return BeanMapperUtils.getMapper()
				.mapAsList(jpOrders, JPOrderDTO.class);
		*/
	}

	@Override
	public JPOrderCreateDTO orderCreate(JPOrderCreateCommand command) {
		return jpOrderLocalService.orderCreate(command);
	}

	@Override
	public JPOrderDTO cancelTicket(JPCancelTicketCommand command) {
		return jpOrderLocalService.cancelTicket(command);
	}

	@Override
	public List<CityAirCodeDTO> queryCityAirCode(JPAirCodeQO jpAirCodeQO) {
		return jpFlightLocalService.queryCityAirCode(jpAirCodeQO);
	}

	@Override
	public List<RefundActionTypeDTO> queryCancelOrderTicketReason(String platCode) {
		return jpFlightLocalService.queryCancelOrderTicketReason(platCode);
	}

	@Override
	public boolean updateOrderStatus(String dealerOrderCode,int orderStatus, int payStatus) {
		return jpOrderLocalService.updateOrderStatus(dealerOrderCode, orderStatus,payStatus);
	}

	@Override
	public boolean updateOrderStatus(UpdateJPOrderStatusCommand command) {
		return jpOrderLocalService.updateOrderStatus(command);
	}

	@Override
	public boolean OrderRefund(String resultDetails) {
		return jpOrderLocalService.orderRefund(resultDetails);
	}

	@Override
	public boolean orderTicketNotify(JPOrderCommand command) {
		return jpOrderLocalService.orderTicketNotify(command);
	}

	@Override
	public boolean orderRefundNotify(JPOrderCommand command) {
		return jpOrderLocalService.orderRefundNotify(command);
	}

	@Override
	public JPOrderDTO queryOrderDetail(HslJPOrderQO qo) {
		return jpOrderLocalService.queryOrderOne(qo);
	}

}
