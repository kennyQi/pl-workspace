package zzpl.api.client.test.jp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.jp.GNFlightQO;
import zzpl.api.client.response.jp.GNFlightResponse;

import com.alibaba.fastjson.JSON;

public class QueryGNFlightTest {
		
	
	public static void main(String[] args) throws ParseException {
		ApiClient client = new ApiClient(	"http://192.168.10.65:60000/zzpl-api/api", "ios","fbdffd6ae2294bcb9d4a7e42efb5eeda");
		GNFlightQO gnFlightQO = new GNFlightQO();
		gnFlightQO.setOrgCity("SVG");
		gnFlightQO.setDstCity("PAY");
		//gnFlightQO.setAirCompany("");
		Date date = new Date();
		Date date2 = new Date(date.getTime());
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		date2=simpleDateFormat.parse("18:00");
		Long time = new Long(36000000);
		String d =simpleDateFormat.format(time);
		date2= simpleDateFormat.parse(d);
		gnFlightQO.setStartTime(date2);
		gnFlightQO.setStartDate("2016-05-12");
		gnFlightQO.setOrderBy(GNFlightQO.START_TIME.toString());
		gnFlightQO.setOrderType(GNFlightQO.DESC.toString());
		ApiRequest request = new ApiRequest("QueryGNFlight",	"c42a541e120b4e1696f96990c7675a82", gnFlightQO, "1.0");
		//ApiRequest request = new ApiRequest("QueryGNFlight",	"995be0fa30b2426a885b7f82f99529ea", gnFlightQO, "1.0");
		GNFlightResponse response = client.send(request, GNFlightResponse.class);
		System.out.println(JSON.toJSON(gnFlightQO));
		System.out.println(JSON.toJSON(response));
	}
	
		
	
}

