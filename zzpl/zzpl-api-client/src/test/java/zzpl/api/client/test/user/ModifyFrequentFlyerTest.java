package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.request.user.ModifyFrequentFlyerCommand;
import zzpl.api.client.response.user.ModifyFrequentFlyerResponse;

import com.alibaba.fastjson.JSON;





public class ModifyFrequentFlyerTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "04a57166920a4b28a87c54d8c858c0e5");
		ModifyFrequentFlyerCommand modifyFrequentFlyerCommand = new ModifyFrequentFlyerCommand();
		FrequentFlyerDTO frequentFlyerDTO = new FrequentFlyerDTO();
		frequentFlyerDTO.setId("cc87d0d1921f4c799cf11107942fab32");
		frequentFlyerDTO.setIdNO("asdasdsadsad");
		frequentFlyerDTO.setPassengerName("testtttt");
		modifyFrequentFlyerCommand.setFrequentFlyer(frequentFlyerDTO);
		ApiRequest request = new ApiRequest("ModifyFrequentFlyer", "1ec70190069d47f78963f0ebbcb15103",modifyFrequentFlyerCommand , "1.0");
		ModifyFrequentFlyerResponse response = client.send(request,ModifyFrequentFlyerResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
