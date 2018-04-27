//package slfx.api;
//
//import java.util.UUID;
//
//import slfx.api.base.ApiRequest;
//import slfx.api.base.SlfxApiClient;
//import slfx.api.v1.request.qo.mp.MPScenicSpotsQO;
//import slfx.api.v1.response.mp.MPQueryScenicSpotsResponse;
//
//import com.alibaba.fastjson.JSON;
//
//public class SlfxMPApiTest {
//
//	public static void test1() {
//
//		// [INFO][HSL][HSL-RELEASE][2014-09-29
//		// 09:43:07][liujz][对slfx-api发送景区查询请求
//		// {"amountAdviceSort":0,"gradeSort":0,"imagesFetchAble":true,"pageNo":1,"pageSize":1,
//		// "scenicSpotId":"803ba2f84e584b5da4fbceca8495727f","tcPolicyNoticeFetchAble":true}][]
//		// SlfxApiClient client = new SlfxApiClient(
//		// "http://183.129.207.6:8280/slfx/api", "hsl", "abc");
//		SlfxApiClient client = new SlfxApiClient(
//				"http://192.168.2.211:9080/slfx-api/slfx/api", "hsl", "abc");
//
//		MPScenicSpotsQO qo = new MPScenicSpotsQO();
//		qo.setImagesFetchAble(true);
//		qo.setScenicSpotId("803ba2f84e584b5da4fbceca8495727f");
//		qo.setTcPolicyNoticeFetchAble(true);
//		qo.setPageNo(1);
//		qo.setPageSize(1);
//
//		ApiRequest apiRequest = new ApiRequest("MPQueryScenicSpots", "F1001",
//				"127.0.0.1", UUID.randomUUID().toString(), qo);
//		MPQueryScenicSpotsResponse response = client.send(apiRequest,
//				MPQueryScenicSpotsResponse.class);
//		System.out.println(JSON.toJSONString(response, true));
//	}
//
//	public static void test2() {
//
//		 SlfxApiClient client = new SlfxApiClient(
//				 "http://183.129.207.6:8280/slfx/api", "hsl", "abc");
////		SlfxApiClient client = new SlfxApiClient(
////				"http://192.168.2.211:9080/slfx-api/slfx/api", "hsl", "abc");
//
//		// [INFO][HSL][HSL-RELEASE][2014-09-29
//		// 10:42:03][liujz][对slfx-api发送景区查询请求
//		// {"amountAdviceSort":0,"gradeSort":0,"imagesFetchAble":false,"name":"","pageNo":2,"pageSize":15,
//		// "tcPolicyNoticeFetchAble":false}][]
//
//		MPScenicSpotsQO qo = new MPScenicSpotsQO();
//		qo.setPageNo(2);
//		qo.setPageSize(15);
//		qo.setName("");
//
//		ApiRequest apiRequest = new ApiRequest("MPQueryScenicSpots", "F1001",
//				"127.0.0.1", UUID.randomUUID().toString(), qo);
//		MPQueryScenicSpotsResponse response = client.send(apiRequest,
//				MPQueryScenicSpotsResponse.class);
//		System.out.println(JSON.toJSONString(response, true));
//	}
//	
//	public static void main(String[] args) {
//		test2();
//	}
//	
//	
//}
