package hsl.app.service.local.dzp;

import com.alibaba.fastjson.JSON;
import hg.dzpw.dealer.client.api.v1.request.*;
import hg.dzpw.dealer.client.api.v1.response.*;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 电子票务服务
 *
 * @author zhurz
 * @since 1.8
 */
@Service
public class DZPWService {

	@Autowired
	private DealerApiClient            apiClient;

	// 查询行政区
	public RegionResponse queryRegion(RegionQO qo) {
		RegionResponse  regionResponse = apiClient.send(qo, RegionResponse.class);

		return regionResponse;
	}

	// 查询景区
	public ScenicSpotResponse queryScenicSpot(ScenicSpotQO qo) {

		ScenicSpotResponse  scenicSpotResponse = apiClient.send(qo, ScenicSpotResponse.class);

		return scenicSpotResponse;
	}

	// 查询门票政策
	public TicketPolicyResponse queryTicketPolicy(TicketPolicyQO qo) {
		TicketPolicyResponse  ticketPolicyResponse = apiClient.send(qo, TicketPolicyResponse.class);

		return ticketPolicyResponse;
	}

	// 向电子票务下单
	public CreateTicketOrderResponse createTicketOrder(CreateTicketOrderCommand command) {
		System.out.println("向电子票务下单");
		System.out.println(JSON.toJSONString(command, true));
		CreateTicketOrderResponse  createTicketOrderResponse = apiClient.send(command, CreateTicketOrderResponse.class);

		return createTicketOrderResponse;
	}

	// 付款给电子票务
	public PayToTicketOrderResponse payToTicketOrder(PayToTicketOrderCommand command) {
		PayToTicketOrderResponse  payToTicketOrderResponse = apiClient.send(command, PayToTicketOrderResponse.class);

		return payToTicketOrderResponse;
	}

	// 关闭电子票务订单
	public CloseTicketOrderResponse closeTicketOrder(CloseTicketOrderCommand command) {
		CloseTicketOrderResponse  closeTicketOrderResponse = apiClient.send(command, CloseTicketOrderResponse.class);

		return closeTicketOrderResponse;
	}

	// 查询订单
	public TicketOrderResponse queryTicketOrder(TicketOrderQO qo) {
		TicketOrderResponse  ticketOrderResponse = apiClient.send(qo, TicketOrderResponse.class);

		return ticketOrderResponse;
	}

	// 查询入园记录
	public UseRecordResponse queryUseRecord(UseRecordQO qo) {
		UseRecordResponse  useRecordResponse = apiClient.send(qo, UseRecordResponse.class);

		return useRecordResponse;
	}

	// 查询门票
	public GroupTicketResponse queryGroupTicket(GroupTicketQO qo) {
		GroupTicketResponse  groupTicketResponse = apiClient.send(qo, GroupTicketResponse.class);

		return groupTicketResponse;
	}

}
