package zzpl.api.client.test.workflow;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.workflow.WorkflowQO;
import zzpl.api.client.response.workflow.WorkflowResponse;



public class WorkflowTest {

	public static void main(String[] args) throws ParseException {

		/*ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","0ca80e796fb94d46bfd500adddd0f7de");		
		List<String> roleList = new ArrayList<String>();
		roleList.add("e52c5ca1eec8485fb49dead5d3454fc6");		
		roleList.add("2d538176b72542bf9761d6446a31644c");
		WorkflowQO workflowQO=new WorkflowQO();
		workflowQO.setCompanyID("a93313a57050422ca4f2dd7ad9732d68");
		workflowQO.setRoleList(roleList);
		ApiRequest request = new ApiRequest("QueryWorkflow", "981bd6cf2c764345b9a360d5f4c8cad3", workflowQO, "1.0");		
		WorkflowResponse response = client.send(request,WorkflowResponse.class);
//		System.out.println(response.getMessage());
//		System.out.println(response.getWorkflowDTOs().get(0).getWorkflowName());
		System.out.println(JSON.toJSON(response));*/
		
		String cabinDiscount = "100";
		Double count = (new Double(cabinDiscount))/10;
		System.out.println(count);
	}

}
