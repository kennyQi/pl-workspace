package zzpl.api.client.test.user;

import java.text.ParseException;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.user.CostCenterQO;
import zzpl.api.client.response.user.QueryCostCenterResponse;

import com.alibaba.fastjson.JSON;





public class QueryCostCenterTest {

	public static void main(String[] args) throws ParseException {

		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios", "3817fda4a81f4b718fd49472dd48fbc7");
		CostCenterQO costCenterQO = new CostCenterQO();
		costCenterQO.setCompanyID("a93313a57050422ca4f2dd7ad9732d68");
		ApiRequest request = new ApiRequest("QueryCostCenter", "118d2b0769e54a948801733f551261e3", costCenterQO, "1.0");
		QueryCostCenterResponse response = client.send(request,QueryCostCenterResponse.class);
		System.out.println(JSON.toJSON(response));

	}

}
