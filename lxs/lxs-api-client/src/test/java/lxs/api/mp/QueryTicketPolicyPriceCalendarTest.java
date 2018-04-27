package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.TicketPolicyPriceCalendarQO;
import lxs.api.v1.response.mp.TicketPolicyPriceCalendarResponse;

import com.alibaba.fastjson.JSON;

public class QueryTicketPolicyPriceCalendarTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		TicketPolicyPriceCalendarQO ticketPolicyPriceCalendarQO = new TicketPolicyPriceCalendarQO();
		ticketPolicyPriceCalendarQO.setTicketPolicyID("d9e4a704ca2c478abf73f99eb4a84ab4");
		ApiRequest request = new ApiRequest("QueryTicketPolicyPriceCalendar", UUID.randomUUID().toString(), ticketPolicyPriceCalendarQO, "1.0");
		TicketPolicyPriceCalendarResponse ticketPolicyPriceCalendarResponse = client.send(request,
				TicketPolicyPriceCalendarResponse.class);
		System.out.println(JSON.toJSONString(ticketPolicyPriceCalendarQO));
		System.out.println(JSON.toJSONString(ticketPolicyPriceCalendarResponse));
	}

}
