package lxs.api.user;

import java.text.ParseException;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.command.user.SMSCommand;
import lxs.api.v1.response.user.SMSResponse;

import com.alibaba.fastjson.JSON;





public class SendSMSTest {

	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(	"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		SMSCommand smsCommand= new SMSCommand();
		smsCommand.setContent("测试");
		smsCommand.setMobile("18646292336");

		ApiRequest request = new ApiRequest("SendSMS", "24643539434c4241b51223d8566f8f17", smsCommand, "1.0");
		SMSResponse response = client.send(request,SMSResponse.class);
		System.out.println(JSON.toJSON(response));
		

	}

}
