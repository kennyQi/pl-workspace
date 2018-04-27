package zzpl.h5.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import zzpl.api.client.base.ApiClient;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.request.jp.GNCancelTicketCommand;
import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.api.client.request.order.FlightOrderListQO;
import zzpl.api.client.request.order.GetGNFlightOrderTotalPriceCommand;
import zzpl.api.client.response.jp.GNCancelTicketResponse;
import zzpl.api.client.response.order.FlightOrderInfoResponse;
import zzpl.api.client.response.order.FlightOrderListResponse;
import zzpl.api.client.response.order.GetGNFlightOrderTotalPriceResponse;

@Service
public class OrderService {

	@Autowired
	private ApiClient apiClient;
	/**
	 * 
	 * @方法功能说明：订单列表
	 * @修改者名字：cangs
	 * @修改时间：2015年11月26日下午1:17:18
	 * @修改内容：
	 * @参数：@param flightOrderListQO
	 * @参数：@param httpServletRequest
	 * @参数：@return
	 * @return:FlightOrderListResponse
	 * @throws
	 */
	public FlightOrderListResponse getFlightOrderList(FlightOrderListQO flightOrderListQO,HttpServletRequest httpServletRequest){
		ApiRequest request = new ApiRequest("GetFlightOrderList",httpServletRequest.getSession().getAttribute("USER_SESSION_ID").toString(), flightOrderListQO, "1.0");
		return apiClient.send(request, FlightOrderListResponse.class);
	}
	/**
	 * 
	 * @方法功能说明：订单详情
	 * @修改者名字：cangs
	 * @修改时间：2015年11月26日下午1:21:00
	 * @修改内容：
	 * @参数：@param flightOrderInfoQO
	 * @参数：@param httpServletRequest
	 * @参数：@return
	 * @return:FlightOrderInfoResponse
	 * @throws
	 */
	public FlightOrderInfoResponse getFlightOrderInfo(FlightOrderInfoQO flightOrderInfoQO,HttpServletRequest httpServletRequest){
		ApiRequest request = new ApiRequest("GetFlightOrderInfo",httpServletRequest.getSession().getAttribute("USER_SESSION_ID").toString(), flightOrderInfoQO, "1.0");
		return apiClient.send(request, FlightOrderInfoResponse.class);
	}
	
	/**
	 * 
	 * @方法功能说明：获取支付价格
	 * @修改者名字：cangs
	 * @修改时间：2015年12月1日下午3:21:24
	 * @修改内容：
	 * @参数：@param getGNFlightOrderTotalPriceCommand
	 * @参数：@param httpServletRequest
	 * @参数：@return
	 * @return:GetGNFlightOrderTotalPriceResponse
	 * @throws
	 */
	public GetGNFlightOrderTotalPriceResponse getGNFlightOrderTotalPrice(GetGNFlightOrderTotalPriceCommand getGNFlightOrderTotalPriceCommand,HttpServletRequest httpServletRequest){
		ApiRequest request = new ApiRequest("GetGNFlightOrderTotalPrice",httpServletRequest.getSession().getAttribute("USER_SESSION_ID").toString(), getGNFlightOrderTotalPriceCommand, "1.0");
		return apiClient.send(request, GetGNFlightOrderTotalPriceResponse.class);
	}
	
	/**
	 * @Title: cancelGNTicket 
	 * @author guok
	 * @Description: 申请退票
	 * @Time 2015年12月7日上午9:37:46
	 * @param gnCancelTicketCommand
	 * @param httpServletRequest
	 * @return GNCancelTicketResponse 设定文件
	 * @throws
	 */
	public GNCancelTicketResponse cancelGNTicket(GNCancelTicketCommand gnCancelTicketCommand,HttpServletRequest httpServletRequest) {
		ApiRequest request = new ApiRequest("CancelGNTicket",httpServletRequest.getSession().getAttribute("USER_SESSION_ID").toString(), gnCancelTicketCommand, "1.0");
		GNCancelTicketResponse response = apiClient.send(request, GNCancelTicketResponse.class);
		return response;
	}
}
