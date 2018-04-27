package zzpl.api.client.test.workflow;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.workflow.ChooseStepCommand;
import zzpl.api.client.response.workflow.ChooseStepResponse;

import com.alibaba.fastjson.JSON;

public class ChooseStepTest {
	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","3817fda4a81f4b718fd49472dd48fbc7");
		ChooseStepCommand chooseStepCommand = new ChooseStepCommand();
		chooseStepCommand.setCurrentStepNO(1);
		chooseStepCommand.setWorkflowID("c472a807c1bc402fad99f8f9b5c14eef");
		ApiRequest request = new ApiRequest("ChooseStep", "118d2b0769e54a948801733f551261e3", chooseStepCommand, "1.0");
		ChooseStepResponse response = client.send(request,ChooseStepResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
