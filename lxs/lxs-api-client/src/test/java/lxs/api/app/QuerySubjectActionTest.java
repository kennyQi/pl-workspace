package lxs.api.app;

import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.app.QuerySubjectQO;
import lxs.api.v1.response.app.QuerySubjectResponse;

public class QuerySubjectActionTest {

	public static void main(String[] args) {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		QuerySubjectQO qo = new QuerySubjectQO();
		qo.setSubjectType(1);
		System.out.println(JSON.toJSON(qo));
		ApiRequest request = new ApiRequest("QuerySubject", UUID.randomUUID()
				.toString(), qo, "1.0");
		QuerySubjectResponse recommendResponse = client.send(request,QuerySubjectResponse.class);
		
		System.out.println(JSON.toJSON(recommendResponse));
	}

}
