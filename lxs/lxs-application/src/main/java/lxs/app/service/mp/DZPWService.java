package lxs.app.service.mp;

import hg.dzpw.dealer.client.api.v1.request.ApplyRefundCommand;
import hg.dzpw.dealer.client.api.v1.request.CloseTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.CreateTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.GroupTicketQO;
import hg.dzpw.dealer.client.api.v1.request.PayToTicketOrderCommand;
import hg.dzpw.dealer.client.api.v1.request.RegionQO;
import hg.dzpw.dealer.client.api.v1.request.ScenicSpotQO;
import hg.dzpw.dealer.client.api.v1.request.TicketOrderQO;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.request.UseRecordQO;
import hg.dzpw.dealer.client.api.v1.response.CloseTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.CreateTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.GroupTicketResponse;
import hg.dzpw.dealer.client.api.v1.response.PayToTicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.RefundResponse;
import hg.dzpw.dealer.client.api.v1.response.RegionResponse;
import hg.dzpw.dealer.client.api.v1.response.ScenicSpotResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketOrderResponse;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.api.v1.response.UseRecordResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.log.util.HgLogger;
import lxs.pojo.exception.mp.DZPWException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class DZPWService {

	@Autowired
	private DealerApiClient dealerApiClient;
	
	/**电子票务远程接口失败	 */
	public final static String DZPW_INTERFACE_SUCCESS = "1";
	
	/**
	 * 
	 * @方法功能说明：查询行政区域
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:03:13
	 * @修改内容：
	 * @参数：@param regionQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:RegionResponse
	 * @throws
	 */
	public RegionResponse queryRegion(RegionQO regionQO) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryRegion】"+JSON.toJSONString(regionQO));
		RegionResponse response = dealerApiClient.send(regionQO, RegionResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryRegion】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryRegion】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_REGION_FAIL);
		}
	}

	/**
	 * 
	 * @方法功能说明：查询景区
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:08:51
	 * @修改内容：
	 * @参数：@param scenicSpotQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:ScenicSpotResponse
	 * @throws
	 */
	public ScenicSpotResponse queryScenicSpot(ScenicSpotQO scenicSpotQO) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryScenicSpot】"+JSON.toJSONString(scenicSpotQO));
		ScenicSpotResponse response = dealerApiClient.send(scenicSpotQO, ScenicSpotResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryScenicSpot】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryScenicSpot】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_SCENICSPOT_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查询门票政策
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:09:21
	 * @修改内容：
	 * @参数：@param ticketPolicyQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:TicketPolicyResponse
	 * @throws
	 */
	public TicketPolicyResponse queryTicketPolicy(TicketPolicyQO ticketPolicyQO) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketPolicy】"+JSON.toJSONString(ticketPolicyQO));
		TicketPolicyResponse response = dealerApiClient.send(ticketPolicyQO, TicketPolicyResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketPolicy】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketPolicy】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_POLICY_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：创建订单
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午9:58:05
	 * @修改内容：
	 * @参数：@param createTicketOrderCommand
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:CreateTicketOrderResponse
	 * @throws
	 */
	public CreateTicketOrderResponse createTicketOrder(CreateTicketOrderCommand createTicketOrderCommand) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【createTicketOrderCommand】"+JSON.toJSONString(createTicketOrderCommand));
		CreateTicketOrderResponse response = dealerApiClient.send(createTicketOrderCommand, CreateTicketOrderResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【createTicketOrderCommand】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【createTicketOrder】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.CREATE_DZPW_ORDER_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查询门票订单
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:10:14
	 * @修改内容：
	 * @参数：@param ticketOrderQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:TicketOrderResponse
	 * @throws
	 */
	public TicketOrderResponse queryTicketOrder(TicketOrderQO ticketOrderQO) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketOrder】"+JSON.toJSONString(ticketOrderQO));
		TicketOrderResponse response = dealerApiClient.send(ticketOrderQO, TicketOrderResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketOrder】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryTicketOrder】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_ORDER_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查询订单中的门票
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:10:43
	 * @修改内容：
	 * @参数：@param groupTicketQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:GroupTicketResponse
	 * @throws
	 */
	public GroupTicketResponse queryGroupTicket(GroupTicketQO groupTicketQO) throws DZPWException {
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryGroupTicket】"+JSON.toJSONString(groupTicketQO));
		GroupTicketResponse response = dealerApiClient.send(groupTicketQO, GroupTicketResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryGroupTicket】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【queryGroupTicket】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_TICKET_IN_ORDER_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：支付订单
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:11:33
	 * @修改内容：
	 * @参数：@param payToTicketOrderCommand
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:PayToTicketOrderResponse
	 * @throws
	 */
	public PayToTicketOrderResponse payToOrder(PayToTicketOrderCommand payToTicketOrderCommand) throws DZPWException{
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【payToOrder】"+JSON.toJSONString(payToTicketOrderCommand));
		PayToTicketOrderResponse response = dealerApiClient.send(payToTicketOrderCommand, PayToTicketOrderResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【payToOrder】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【payToOrder】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.PAY_DZPW_ORDER_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：退票
	 * @修改者名字：cangs
	 * @修改时间：2016年4月14日下午3:28:41
	 * @修改内容：
	 * @参数：@param applyRefundCommand
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:RefundResponse
	 * @throws
	 */
	public RefundResponse refundTicketOrder(ApplyRefundCommand applyRefundCommand) throws DZPWException{
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【refundTicketOrder】"+JSON.toJSONString(applyRefundCommand));
		RefundResponse response = dealerApiClient.send(applyRefundCommand, RefundResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【refundTicketOrder】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【refundTicketOrder】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.REFUND_DZPW_ORDER_FAIL);
		}
	}
	/**
	 * 
	 * @方法功能说明：关闭订单
	 * @修改者名字：cangs
	 * @修改时间：2016年3月5日上午10:11:21
	 * @修改内容：
	 * @参数：@param closeTicketOrderCommand
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:CloseTicketOrderResponse
	 * @throws
	 */
	public CloseTicketOrderResponse closeTicketOrder(CloseTicketOrderCommand closeTicketOrderCommand) throws DZPWException{
		
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【closeTicketOrder】"+JSON.toJSONString(closeTicketOrderCommand));
		CloseTicketOrderResponse response = dealerApiClient.send(closeTicketOrderCommand, CloseTicketOrderResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【closeTicketOrder】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【closeTicketOrder】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.CLOSE_DZPW_ORDER_FAIL);
		}
	}
	
	/**
	 * 
	 * @方法功能说明：门票使用记录查询
	 * @修改者名字：cangs
	 * @修改时间：2016年4月20日下午3:42:28
	 * @修改内容：
	 * @参数：@param useRecordQO
	 * @参数：@return
	 * @参数：@throws DZPWException
	 * @return:UseRecordResponse
	 * @throws
	 */
	public UseRecordResponse queryUseRecord(UseRecordQO useRecordQO) throws DZPWException{
		HgLogger.getInstance().info("lxs_dev", "【DzpwService】【useRecordQO】"+JSON.toJSONString(useRecordQO));
		UseRecordResponse response = dealerApiClient.send(useRecordQO, UseRecordResponse.class);
		if(StringUtils.equals(response.getResult(),DZPW_INTERFACE_SUCCESS)){
			HgLogger.getInstance().info("lxs_dev", "【DzpwService】【useRecordQO】"+JSON.toJSONString(response));
			return response;
		}else{
			if(response!=null){
				HgLogger.getInstance().info("lxs_dev", "【DzpwService】【useRecordQO】"+JSON.toJSONString(response));
			}
			throw new DZPWException(DZPWException.QUERY_DZPW_USE_RECORD_FAIL);
		}
	}
}
