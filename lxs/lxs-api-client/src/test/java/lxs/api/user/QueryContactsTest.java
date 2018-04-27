package lxs.api.user;

import java.text.ParseException;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.user.ContactsQO;
import lxs.api.v1.response.user.QueryContactsResponse;

public class QueryContactsTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		ContactsQO qo = new ContactsQO();
		qo.setUserId("dbffa7b66e574c68b5398165ef32c3a1");

		ApiRequest request = new ApiRequest("QueryContacts", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryContactsResponse response = client.send(request,QueryContactsResponse.class);

		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSONString(response));

	}
}
