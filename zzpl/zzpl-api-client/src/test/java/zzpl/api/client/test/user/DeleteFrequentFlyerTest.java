package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.DeleteFrequentFlyerCommand;
import zzpl.api.client.response.user.DeleteFrequentFlyerResponse;

import com.alibaba.fastjson.JSON;





public class DeleteFrequentFlyerTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "04a57166920a4b28a87c54d8c858c0e5");
		DeleteFrequentFlyerCommand deleteFrequentFlyerCommand = new DeleteFrequentFlyerCommand();
		deleteFrequentFlyerCommand.setId("cc87d0d1921f4c799cf11107942fab32");
		ApiRequest request = new ApiRequest("DeleteFrequentFlyer", "1ec70190069d47f78963f0ebbcb15103",deleteFrequentFlyerCommand , "1.0");
		DeleteFrequentFlyerResponse response = client.send(request,DeleteFrequentFlyerResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
