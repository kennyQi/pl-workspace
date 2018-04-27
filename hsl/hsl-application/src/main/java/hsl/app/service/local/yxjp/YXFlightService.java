package hsl.app.service.local.yxjp;

import com.alibaba.fastjson.JSON;
import hg.common.util.BeanMapperUtils;
import hg.common.util.MoneyUtil;
import hg.log.util.HgLogger;
import hsl.domain.model.yxjp.*;
import hsl.pojo.dto.yxjp.YXFlightDTO;
import hsl.pojo.dto.yxjp.YXResultCabinDTO;
import hsl.pojo.dto.yxjp.YXResultFlightDTO;
import hsl.pojo.dto.yxjp.YXResultQueryFlightDTO;
import hsl.pojo.exception.ShowMessageException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import plfx.api.client.api.v1.gn.dto.PassengerGNDTO;
import plfx.api.client.api.v1.gn.dto.PassengerInfoGNDTO;
import plfx.api.client.api.v1.gn.request.JPAutoOrderGNCommand;
import plfx.api.client.api.v1.gn.request.JPFlightGNQO;
import plfx.api.client.api.v1.gn.request.JPPolicyGNQO;
import plfx.api.client.api.v1.gn.request.RefundTicketGNCommand;
import plfx.api.client.api.v1.gn.response.JPAutoOrderGNResponse;
import plfx.api.client.api.v1.gn.response.JPQueryHighPolicyGNResponse;
import plfx.api.client.api.v1.gn.response.RefundTicketGNResponse;
import plfx.api.client.api.v1.yxjp.request.UpdatePayBalancesCommand;
import plfx.api.client.api.v1.yxjp.response.UpdatePayBalancesResponse;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.util.PlfxApiClient;

import java.util.ArrayList;

/**
 * 易行机票接口服务（封装接口调用步骤）
 *
 * @author zhurz
 * @since 1.7
 */
@Service
public class YXFlightService {

	@Autowired
	private PlfxApiClient apiClient;
	@Autowired
	private YXJPOrderOperateLogLocalService logService;

	/**
	 * 查询航班
	 *
	 * @param qo 航班查询QO
	 * @return
	 */
	public YXResultQueryFlightDTO queryFlight(JPFlightGNQO qo) {

		// 调用分销接口查询航班
		YXResultQueryFlightDTO result = apiClient.send(qo, YXResultQueryFlightDTO.class);

		// 处理返回成功且航班列表不为空的
		if ("T".equals(result.getIs_success()) && result.getFlightList() != null) {
			for (YXResultFlightDTO resultFlightDTO : result.getFlightList()) {
				YXFlightDTO.BaseInfo baseInfo = BeanMapperUtils.map(resultFlightDTO, YXFlightDTO.BaseInfo.class);
				for (YXResultCabinDTO resultCabinDTO : resultFlightDTO.getCabinList()) {
					YXFlightDTO.CabinInfo cabinInfo = BeanMapperUtils.map(resultCabinDTO, YXFlightDTO.CabinInfo.class);
					YXFlightDTO flightDTO = new YXFlightDTO();
					flightDTO.setBaseInfo(baseInfo);
					flightDTO.setCabinInfo(cabinInfo);
					resultCabinDTO.setOrderEncryptString(flightDTO.toEncryptString().replaceAll("[\r\n]+", ""));
				}
			}
		}

		return result;
	}

	/**
	 * 查询航班政策
	 *
	 * @param orderEncryptString 下单用加密串
	 * @return
	 */
	public YXFlightDTO queryAirPolicy(String orderEncryptString) {

		// 将加密字符串解密为航班信息
		YXFlightDTO flightDTO = YXFlightDTO.valueOfEncryptString(orderEncryptString);

		// 检查航班信息
		if (flightDTO == null || flightDTO.getBaseInfo() == null || flightDTO.getCabinInfo() == null)
			throw new ShowMessageException("航班和舱位信息错误");

		// 组装查询政策参数
		JPPolicyGNQO qo = new JPPolicyGNQO();
		qo.setEncryptString(flightDTO.getCabinInfo().getEncryptString());
		qo.setTickType(3);

		// 调用分销接口查询政策
		JPQueryHighPolicyGNResponse response = apiClient.send(qo, JPQueryHighPolicyGNResponse.class);

		// 处理返回成功且政策不会空的
		if ("T".equals(response.getIs_success()) && response.getPricesGNDTO() != null) {
			YXFlightDTO.PolicyInfo policyInfo = BeanMapperUtils.map(response, YXFlightDTO.PolicyInfo.class);
			BeanMapperUtils.getMapper().map(response.getPricesGNDTO(), policyInfo);
			flightDTO.setPolicyInfo(policyInfo);
		} else
			throw new ShowMessageException("航班政策查询失败");

		return flightDTO;
	}

	/**
	 * 向分销下单并自动扣款（扣款是分销给供应商）
	 *
	 * @param order
	 * @return
	 */
	JPAutoOrderGNResponse autoOrder(YXJPOrder order) {

		// -- 组装调用分销接口command --
		JPAutoOrderGNCommand command = new JPAutoOrderGNCommand();
		command.setEncryptString(order.getFlight().getPolicyInfo().getEncryptString());
		command.setDealerOrderId(order.getBaseInfo().getOrderNo());
		command.setCabinCode(order.getFlight().getBaseInfo().getCabinCode());
		command.setCabinName(order.getFlight().getBaseInfo().getCabinName());
		command.setFlightNo(order.getFlight().getBaseInfo().getFlightNo());
		command.setStartDate(order.getFlight().getBaseInfo().getStartTime());
		command.setPassengerInfoGNDTO(new PassengerInfoGNDTO());

		// -- 余额预警用 --
		command.setUserPayAmount(MoneyUtil.round(order.getPayInfo().getTotalPrice() - order.getPayInfo().getTotalCancelPrice(), 2));
		command.setUserPayCash(order.getPayInfo().getTotalPayAmount());

		// == 组装乘客信息 ==
		PassengerInfoGNDTO passengerInfoGNDTO = command.getPassengerInfoGNDTO();
		passengerInfoGNDTO.setTelephone(order.getLinkman().getLinkMobile());
		// 检查联系人姓名是否存在，不存在取第一个乘机人姓名
		passengerInfoGNDTO.setName(StringUtils.isNotBlank(order.getLinkman().getLinkName())
				? order.getLinkman().getLinkName()
				: order.getPassengers().get(0).getBaseInfo().getName());
		passengerInfoGNDTO.setPassengerList(new ArrayList<PassengerGNDTO>());
		// 转换乘客信息
		for (YXJPOrderPassenger passenger : order.getPassengers()) {
			// 非出票处理中的乘客将跳过
			if (!YXJPOrderPassengerTicket.STATUS_TICKET_DEALING.equals(passenger.getTicket().getStatus()))
				continue;
			PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
			passengerGNDTO.setPassengerName(passenger.getBaseInfo().getName());
			// 客人类型(1-成人，2-儿童)
			if (!YXJPOrderPassengerBaseInfo.TYPE_ADULT.equals(passenger.getBaseInfo().getIdType()))
				throw new ShowMessageException("乘客类型只支持成人");
			passengerGNDTO.setPassengerType("1");
			// 证件类型(0-身份证，1-护照，2-军人证，3-台胞证，4-回乡证，5-文职证)
			if (!YXJPOrderPassengerBaseInfo.ID_TYPE_SFZ.equals(passenger.getBaseInfo().getIdType()))
				throw new ShowMessageException("证件类型只支持身份证");
			passengerGNDTO.setIdType("0");
			passengerGNDTO.setIdNo(passenger.getBaseInfo().getIdNo());
			passengerInfoGNDTO.getPassengerList().add(passengerGNDTO);
		}

		// -- 调用分销下单自动扣款接口 --
		JPAutoOrderGNResponse response = apiClient.send(command, JPAutoOrderGNResponse.class);
//		// wtdo 测试成功
//		JPAutoOrderGNResponse response = new JPAutoOrderGNResponse();
//		response.setIs_success("T");
////		response.setError("测试失败");

		// 记录接口调用信息
		logService.record(order.getId(), "系统后台",
				String.format("向分销下单%s。", "T".equals(response.getIs_success()) ?
						"成功" : "失败，原因：" + response.getError()),
				logRequestJson(command, response)
		);

		return response;
	}

	/**
	 * 申请退废票
	 *
	 * @param apply 退废票申请
	 * @return
	 */
	RefundTicketGNResponse applyRefundOrder(YXJPOrderRefundApply apply) {

		// -- 组装退废票command --
		RefundTicketGNCommand command = new RefundTicketGNCommand();
		command.setDealerOrderId(apply.getFromOrder().getBaseInfo().getOrderNo());
		// 乘客姓名 ^ 分隔
		StringBuilder passengerNames = new StringBuilder();
		// 机票票号 ^ 分隔
		StringBuilder airIds = new StringBuilder();
		for (YXJPOrderPassenger passenger : apply.getPassengers()) {
			if (passengerNames.length() > 0)
				passengerNames.append('^');
			if (airIds.length() > 0)
				airIds.append('^');
			passengerNames.append(passenger.getBaseInfo().getName());
			airIds.append(passenger.getTicket().getTicketNo());
		}
		command.setPassengerName(passengerNames.toString());
		command.setAirId(airIds.toString());
		command.setRefundType(apply.getRefundType().toString());
		command.setRefundMemo(apply.getRefundMemo());

		// 调用分销接口申请退废票
		RefundTicketGNResponse response = apiClient.send(command, RefundTicketGNResponse.class);
//		// wtdo 测试申请退废票成功
//		RefundTicketGNResponse response = new RefundTicketGNResponse();
//		response.setIs_success("T");
////		response.setError("测试退废票申请失败");

		// 记录订单操作日志
		logService.record(apply.getFromOrder().getId(), apply.getOperator(),
				String.format("申请退废票：%s|%s|%s，申请结果：%s。", command.getPassengerName(),
						YXJPOrderRefundApply.REFUND_TYPE_MAP.get(apply.getRefundType()),
						command.getRefundMemo(),
						"T".equals(response.getIs_success()) ? "成功" : "失败，原因：" + response.getError()),
				logRequestJson(command, response));

		return response;
	}

	/**
	 * 更新支付宝账户余额（余额预警用）
	 *
	 * @param amount 实际退款的金额
	 */
	public void updatePayBalances(Double amount) {
		UpdatePayBalancesCommand command = new UpdatePayBalancesCommand();
		command.setType(0);
		command.setChangeCash(-Math.abs(amount));
		UpdatePayBalancesResponse response = apiClient.send(command, UpdatePayBalancesResponse.class);
		HgLogger.getInstance().info("zhurz", String.format("更新支付宝余额%.2f，%s。", command.getChangeCash(),
				ApiResponse.RESULT_SUCCESS.equals(response.getResult()) ? "成功" : "失败"
		));
	}

	/**
	 * 请求分销日志
	 *
	 * @param request  调用分销的请求
	 * @param response 分销的响应结果
	 * @return 日志内容
	 */
	private String logRequestJson(Object request, Object response) {
		return String.format("请求分销：%s，响应结果：%s。", JSON.toJSONString(request), JSON.toJSONString(response));
	}

}
