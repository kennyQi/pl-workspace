package zzpl.api.client.test.jp;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.jp.GNCancelTicketCommand;
import zzpl.api.client.response.jp.GNCancelTicketResponse;

import com.alibaba.fastjson.JSON;

public class CancelGNTicketTest {
	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.105:8080/zzpl-api/api", "ios","4d9a2252ac6c4c2db2f61e1c522ef091");
		GNCancelTicketCommand gnCancelTicketCommand = new GNCancelTicketCommand();
		gnCancelTicketCommand.setOrderNO("B817092643010000");
		gnCancelTicketCommand.setOrderID("50b920ad4d6a43ca93303c968d51bb47");
		gnCancelTicketCommand.setUserID("4bf6a8a7a3394e4e9ce59f603e3956b8");
		gnCancelTicketCommand.setWorkflowID("ed5b82ef03e64ef58943664e5fba84e3");
		gnCancelTicketCommand.setCurrentStepNO(1);
		gnCancelTicketCommand.setNextStepNO(2);
		List<String> strings = new ArrayList<String>();
		strings.add("d7c3a96d07e648ba8614c37153928066");
		gnCancelTicketCommand.setNextUserIDs(strings);
		//废票
		gnCancelTicketCommand.setRefundType("5");
		gnCancelTicketCommand.setRefundMemo("其它");
		ApiRequest request = new ApiRequest("CancelGNTicket",	"83af8abdb9e84f449d3ba1b6913166cf", gnCancelTicketCommand, "1.0");
		GNCancelTicketResponse response = new GNCancelTicketResponse();
		response = client.send(request, GNCancelTicketResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
