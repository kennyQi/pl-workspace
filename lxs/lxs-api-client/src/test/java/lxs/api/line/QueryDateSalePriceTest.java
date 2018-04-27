package lxs.api.line;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiRequest;
import lxs.api.base.LxsApiClient;
import lxs.api.v1.request.qo.line.DateSalePriceQO;
import lxs.api.v1.response.line.QueryDateSalePriceResponse;

public class QueryDateSalePriceTest {
	public static void main(String[] args) throws ParseException {
		LxsApiClient client = new LxsApiClient(
				"http://192.168.10.65:60000/lxs-api/api", "ios", "ios");
		DateSalePriceQO qo = new DateSalePriceQO();
		qo.setLineID("cfa3886faade4e808a1e776a401a55f6");
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			 date = sdf.parse("2015-06-01");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qo.setStartDate(date);
		try {
			 date = sdf.parse("2016-06-20");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		qo.setEndDate(date);
		ApiRequest request = new ApiRequest("QueryDateSalePrice", UUID.randomUUID()
				.toString(), qo, "1.0");
		QueryDateSalePriceResponse response = client.send(request,
				QueryDateSalePriceResponse.class);

		System.out.println(JSON.toJSONString(qo));
		System.out.println(JSON.toJSONString(response));
	}
}