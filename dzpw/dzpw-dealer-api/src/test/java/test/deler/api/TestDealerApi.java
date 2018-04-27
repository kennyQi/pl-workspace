package test.deler.api;

import hg.common.model.HttpResponse;
import hg.common.util.HttpUtil;
import hg.common.util.MD5HashUtil;
import hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand;
import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.UseRecordQO;
import hg.dzpw.dealer.client.common.ApiRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import com.alibaba.fastjson.JSON;

public class TestDealerApi {
	private static String url = "http://192.168.2.227:17017/api";
	private String dealerKey = "JX0006";
	private String secretKey = "1a570669cc9541af8c3a118f682421ca";

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 
	 * @描述：经销商订单查询接口
	 * @author: guotx
	 * @version: 2016-1-27 下午2:44:58
	 */
	@Test
	public void queryTicketOrder() throws UnsupportedEncodingException {
		ApiRequest<TicketOrderQO> request = new ApiRequest<TicketOrderQO>();
		request.getHeader().setActionName("QueryTicketOrder");
		request.getHeader().setDealerKey(dealerKey);
		if (request.getBody() == null) {
			request.setBody(new TicketOrderQO());
		}
		// 查询未支付订单
//		request.getBody().setStatus(0);
//		request.getBody().setCreateDateBegin(
//				DateUtil.dateStr2BeginDate("2016-01-01"));
//		request.getBody().setCreateDateEnd(
//				DateUtil.dateStr2EndDate("2016-01-31"));
		request.getBody().setOrderId("JX00060000000146");
		request.getBody().setGroupTicketsFetch(true);
		request.getBody().setSingleTicketsFetch(true);
		request.getBody().setTouristFetch(true);
		String json = JSON.toJSONString(request);
		StringBuilder builder = new StringBuilder();
		String msg = URLEncoder.encode(json, "UTF-8");
		System.out.println("msg>>>>>>>" + json);
		builder.append("msg=").append(msg);
		builder.append("&sign=").append(MD5HashUtil.toMD5(secretKey + json));
		HttpResponse response = HttpUtil.reqForPost(url, builder.toString(),
				5000);
		System.out.println(response.getResult());
	}

	/**
	 * 
	 * @描述：经销商门票查询接口
	 * @author: guotx
	 * @version: 2016-1-27 下午2:45:34
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void queryGroupTicket() throws UnsupportedEncodingException {
		ApiRequest<GroupTicketQO> request=new ApiRequest<GroupTicketQO>();
		request.getHeader().setActionName("QueryGroupTicket");
		request.getHeader().setDealerKey(dealerKey);
		request.setBody(new GroupTicketQO());
		request.getBody().setOrderId("JX00060000000084");
		request.getBody().setSingleTicketFetch(true);
		request.getBody().setTouristFetch(true);
		String ids[]=new String[2];
//		ids[0]="100523008551";
//		ids[1]="100523008514";
		request.getBody().setTicketNos(ids);
//		request.getBody().setTouristFetch(true);
		
		String json = JSON.toJSONString(request);
		StringBuilder builder = new StringBuilder();
		String msg = URLEncoder.encode(json, "UTF-8");
		System.out.println("msg>>>>>>>" + json);
		builder.append("msg=").append(msg);
		builder.append("&sign=").append(MD5HashUtil.toMD5(secretKey + json));
		HttpResponse response = HttpUtil.reqForPost(url, builder.toString(),
				5000);
		System.out.println(response.getResult());
	}
	/**
	 * 
	 * @描述：入园记录查询接口 
	 * @author: guotx 
	 * @version: 2016-1-27 下午4:12:44
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void queryUseRecoed() throws UnsupportedEncodingException{
		ApiRequest<UseRecordQO> request=new ApiRequest<UseRecordQO>();
		request.getHeader().setActionName("QueryUseRecord");
		request.getHeader().setDealerKey(dealerKey);
		request.setBody(new UseRecordQO());
		request.getBody().setTicketNo("100523008502");
		
		String json = JSON.toJSONString(request);
		StringBuilder builder = new StringBuilder();
		String msg = URLEncoder.encode(json, "UTF-8");
		System.out.println("msg>>>>>>>" + json);
		builder.append("msg=").append(msg);
		builder.append("&sign=").append(MD5HashUtil.toMD5(secretKey + json));
		HttpResponse response = HttpUtil.reqForPost(url, builder.toString(),
				5000);
		System.out.println(response.getResult());
	}
	
	/**
	 * 
	 * @描述：退款申请 
	 * @author: guotx 
	 * @version: 2016-3-18 下午2:55:17
	 * @throws UnsupportedEncodingException 
	 */
	@Test
	public void applyRefund() throws UnsupportedEncodingException{
		ApiRequest<ApplyRefundCommand> apiRequest=new ApiRequest<ApplyRefundCommand>();
		apiRequest.getHeader().setActionName("ApplyRefund");
		apiRequest.getHeader().setDealerKey(dealerKey);
		apiRequest.setBody(new ApplyRefundCommand());
		apiRequest.getBody().setOrderId("JX00060000000321");
		String[] ticketNos=new String[1];
		ticketNos[0]="100523013363";
		apiRequest.getBody().setTicketNos(ticketNos);
		
		String json = JSON.toJSONString(apiRequest);
		StringBuilder builder = new StringBuilder();
		String msg = URLEncoder.encode(json, "UTF-8");
		System.out.println("msg>>>>>>>" + json);
		builder.append("msg=").append(msg);
		builder.append("&sign=").append(MD5HashUtil.toMD5(secretKey + json));
		HttpResponse response = HttpUtil.reqForPost(url, builder.toString(),
				5000);
		System.out.println(response.getResponseState());
		System.out.println(response.getRespoinsCode());
		System.out.println(response.getResult());
	}
}
