package zzpl.api.client.test.workflow;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.workflow.ChooseExecutorCommand;
import zzpl.api.client.response.workflow.ChooseExecutorResponse;

import com.alibaba.fastjson.JSON;

public class ChooseExecutorTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","0ca80e796fb94d46bfd500adddd0f7de");
		ChooseExecutorCommand chooseExecutorCommand = new ChooseExecutorCommand();
		chooseExecutorCommand.setWorkflowID("c472a807c1bc402fad99f8f9b5c14eef");
		chooseExecutorCommand.setUserID("4bf6a8a7a3394e4e9ce59f603e3956b8");
		chooseExecutorCommand.setNextStepNO(2);
		chooseExecutorCommand.setWorkflowInstanceID("");
		ApiRequest request = new ApiRequest("ChooseExecutor", "981bd6cf2c764345b9a360d5f4c8cad3", chooseExecutorCommand, "1.0");
		ChooseExecutorResponse response = client.send(request,ChooseExecutorResponse.class);
		System.out.println(JSON.toJSON(response));
	}
}
