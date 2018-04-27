package lxs.api.line;


import java.text.ParseException;
import java.util.UUID;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.GetInsuranceQO;
import lxs.api.v1.response.line.GetInsuranceResponse;

import com.alibaba.fastjson.JSON;

//package hsl.api;

public class GetInsuranceTest {

	public static void main(String[] args) throws ParseException {

		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");

		GetInsuranceQO getInsuranceQO = new GetInsuranceQO();
		ApiRequest request = new ApiRequest("GetInsurance", UUID.randomUUID()
				.toString(), getInsuranceQO, "1.0");
		GetInsuranceResponse response = client.send(request,
				GetInsuranceResponse.class);

		System.out.println(JSON.toJSONString(response));
		System.out.println(JSON.toJSONString(getInsuranceQO));
		System.out.println(JSON.toJSONString(response));
	}

}
