package lxs.api.line;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.LineQO;
import lxs.api.v1.response.line.QueryLineResponse;

public class QueryLineActionTest {

	public static void main(String[] args) throws ParseException {

//		LxsApiClient client = new LxsApiClient("http://101.71.39.36:15004/api", "ios", "ios");
		LxsApiClient client = new LxsApiClient("http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
//				"http://101.71.39.36:15004/api", "ios", "ios");
	LineQO qo = new LineQO();
				qo.setQuerySelectiveLine(false);
		qo.setPageNO(1);
		qo.setPageSize(20);
		qo.setSubjectId("27f63fb2d4e34a4ea0a219986cb5015c");
//		qo.setSaleDate("11");
		qo.setDestinationCity("383");
		qo.setRouteDays(99);
		qo.setSaleDate("201602");
	/*	
		
	 */
		qo.setName("杭州出发");
//		qo.setOrder(2);
//		qo.setOrderType("asc");
		qo.setStartingCity("383");
		qo.setType("1");
		qo.setLowPrice(0);
		qo.setHighPrice(0);
//		qo.setLineID("cfa3886faade4e808a1e776a401a55f6");
//		qo.setLineSelectiveID("4");
//		qo.setLineID("1000");
		System.out.println(JSON.toJSONString(qo));
		ApiRequest request = new ApiRequest("QueryLine", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryLineResponse response = client.send(request,QueryLineResponse.class);

		System.out.println(JSON.toJSONString(response));

	}
}
