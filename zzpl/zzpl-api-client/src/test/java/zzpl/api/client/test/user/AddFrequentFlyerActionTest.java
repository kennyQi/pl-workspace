package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.dto.user.FrequentFlyerDTO;
import zzpl.api.client.request.user.AddFrequentFlyerCommand;
import zzpl.api.client.response.user.AddFrequentFlyerResponse;

import com.alibaba.fastjson.JSON;





public class AddFrequentFlyerActionTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "3a30c7b9b3d84b98b4fa25a8ad63ee2c");
		AddFrequentFlyerCommand addFrequentFlyerCommand = new AddFrequentFlyerCommand();
		FrequentFlyerDTO frequentFlyerDTO = new FrequentFlyerDTO();
		frequentFlyerDTO.setUserID("222222");
		frequentFlyerDTO.setPassengerName(":test:tasdest");
		frequentFlyerDTO.setIdNO("110119120");
		addFrequentFlyerCommand.setFrequentFlyer(frequentFlyerDTO);
		ApiRequest request = new ApiRequest("AddFrequentFlyer", "8ccd35fe21f64352bbfc5fbd77ac469d",addFrequentFlyerCommand , "1.0");
		AddFrequentFlyerResponse response = client.send(request,AddFrequentFlyerResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
