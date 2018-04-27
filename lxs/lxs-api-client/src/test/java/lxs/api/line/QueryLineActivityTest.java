package lxs.api.line;

import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.LineActivityQO;
import lxs.api.v1.response.line.QueryLineActivityResponse;

public class QueryLineActivityTest {
	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(
				"http://115.238.43.242:60000/lxs-api/api", "ios", "ios");
		LineActivityQO lineActivityQO =  new LineActivityQO();
//		lineActivityQO.setStartingCity("383");
		lineActivityQO.setStartingProvince("31");
		ApiRequest request = new ApiRequest("QueryLineActivity", UUID.randomUUID().toString(), lineActivityQO, "1.0");
		QueryLineActivityResponse response = client.send(request,QueryLineActivityResponse.class);

		System.out.println(response.getMessage());
	}
}
