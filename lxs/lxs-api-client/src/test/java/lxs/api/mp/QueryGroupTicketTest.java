package lxs.api.mp;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.mp.GroupTicketQO;
import lxs.api.v1.response.mp.GroupTicketResponse;

import com.alibaba.fastjson.JSON;

public class QueryGroupTicketTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");


		GroupTicketQO groupTicketQO = new GroupTicketQO();
		groupTicketQO.setPageNO("1");
		groupTicketQO.setPageSize("5");
		groupTicketQO.setName("测");
		ApiRequest request = new ApiRequest("QueryGroupTicket", UUID.randomUUID().toString(), groupTicketQO, "1.0");
		GroupTicketResponse groupTicketResponse = client.send(request,
				GroupTicketResponse.class);
		System.out.println(JSON.toJSONString(groupTicketQO));
		System.out.println(JSON.toJSONString(groupTicketResponse));
	}

}
