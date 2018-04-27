package plfx.yxgjclient.service;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import plfx.yxgjclient.common.util.ServicesUtil;
import plfx.yxgjclient.common.util.YIGJConfig;
import plfx.yxgjclient.common.util.YIGJConstant;
import plfx.yxgjclient.pojo.param.ApplyCancelNoPayParams;
import plfx.yxgjclient.pojo.param.ApplyRefundParams;
import plfx.yxgjclient.pojo.param.CreateOrderParams;
import plfx.yxgjclient.pojo.param.MatchPoliciesBySegInfoParams;
import plfx.yxgjclient.pojo.param.QueryMoreCabinsParams;
import plfx.yxgjclient.pojo.request.ApplyCancelNoPayRQ;
import plfx.yxgjclient.pojo.request.ApplyCancelRQ;
import plfx.yxgjclient.pojo.request.ApplyRefundRQ;
import plfx.yxgjclient.pojo.request.CreateOrderRQ;
import plfx.yxgjclient.pojo.request.MatchPoliciesBySegInfoRQ;
import plfx.yxgjclient.pojo.request.PayAutoRQ;
import plfx.yxgjclient.pojo.request.QueryAirRulesRQ;
import plfx.yxgjclient.pojo.request.QueryFlightRQ;
import plfx.yxgjclient.pojo.request.QueryMoreCabinsRQ;
import plfx.yxgjclient.pojo.request.QueryOrderDetailRQ;
import plfx.yxgjclient.pojo.request.QueryOrderStatusRQ;
import plfx.yxgjclient.pojo.request.QueryPoliciesRQ;
import plfx.yxgjclient.pojo.request.QueryPolicyStateRQ;
import plfx.yxgjclient.pojo.request.QueryRefundStatesRQ;
import plfx.yxgjclient.pojo.request.QueryTicketNoRQ;
import plfx.yxgjclient.pojo.response.ApplyCancelNoPayRS;
import plfx.yxgjclient.pojo.response.ApplyCancelRS;
import plfx.yxgjclient.pojo.response.ApplyRefundRS;
import plfx.yxgjclient.pojo.response.CreateOrderRS;
import plfx.yxgjclient.pojo.response.MatchPoliciesBySegInfoRS;
import plfx.yxgjclient.pojo.response.PayAutoRS;
import plfx.yxgjclient.pojo.response.QueryAirRulesRS;
import plfx.yxgjclient.pojo.response.QueryFlightRS;
import plfx.yxgjclient.pojo.response.QueryMoreCabinsRS;
import plfx.yxgjclient.pojo.response.QueryOrderDetailRS;
import plfx.yxgjclient.pojo.response.QueryOrderStatusRS;
import plfx.yxgjclient.pojo.response.QueryPoliciesRS;
import plfx.yxgjclient.pojo.response.QueryPolicyStateRS;
import plfx.yxgjclient.pojo.response.QueryRefundStatusRS;
import plfx.yxgjclient.pojo.response.QueryTicketNoRS;

import com.yeexing.iat.services.basic.utils.SignUtils;

/**
 * 易行国际机票接口
 * @author guotx
 * 2015-07-02
 */
public class YIGJBaseService {
	
	@Autowired
	private YIGJConfig yigjConfig;
	
	private String GET_SIGN_ERROR="LOCALREEOR:GET_SIGN_ERROR";
	
	private Logger logger=LoggerFactory.getLogger(YIGJBaseService.class);
	public void setYigjConfig(YIGJConfig yigjConfig) {
		this.yigjConfig = yigjConfig;
	}
	/**
	 * 初始化，配置易行国际接口地址
	 */
	@PostConstruct
	public void init(){
		logger.info("初始化易行国际机票接口地址");
		if (yigjConfig.getInterUrl()!=null) {
			ServicesUtil.YIGJ_INTER_URL=yigjConfig.getInterUrl();
		}
		logger.info("易行国际接口地址配置成功:"+yigjConfig.getInterUrl());
	}
	/**
	 * 取消未支付订单
	 * @param rq
	 * @return
	 */
	public ApplyCancelNoPayRS applyCancelOrderNoPay(ApplyCancelNoPayRQ rq){
		rq.getApplyCancelNoPayParams().setUserName(yigjConfig.getUserName());
		rq.getApplyCancelNoPayParams().setServiceName(YIGJConstant.APPLY_CANCEL_NOPAY);
		rq.getApplyCancelNoPayParams().setSignUserName(yigjConfig.getSignUserName());
		
		ApplyCancelNoPayParams newParams=new ApplyCancelNoPayParams();
		newParams.setServiceName(rq.getApplyCancelNoPayParams().getServiceName());
		newParams.setUserName(rq.getApplyCancelNoPayParams().getUserName());
		newParams.setOrderId(rq.getApplyCancelNoPayParams().getOrderId());
		newParams.setSignUserName(rq.getApplyCancelNoPayParams().getSignUserName());
		newParams.setExtOrderId(rq.getApplyCancelNoPayParams().getExtOrderId());
		//rq.getApplyCancelNoPayParams().setUserName(userName)
		try {
			rq.setSign(SignUtils.getSign(newParams, yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			ApplyCancelNoPayRS rs=new ApplyCancelNoPayRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(ApplyCancelNoPayRS.class, rq);
	}
	/**
	 * 取消已支付订单
	 * @return
	 */
	public ApplyCancelRS applyCancelOrder(ApplyCancelRQ rq){
		rq.getApplyCancelParams().setUserName(yigjConfig.getUserName());
		rq.getApplyCancelParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getApplyCancelParams().setServiceName(YIGJConstant.APPLY_CANCEL_ORDER);
		try {
			rq.setSign(SignUtils.getSign(rq.getApplyCancelParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			ApplyCancelRS rs=new ApplyCancelRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(ApplyCancelRS.class, rq);
	}
	/**
	 * 申请退废票
	 * @param rq
	 * @return
	 */
	public ApplyRefundRS applyRefundTicket(ApplyRefundRQ rq){
		rq.getApplyRefundParams().setServiceName(YIGJConstant.APPLY_REFUND_TICKET);
		rq.getApplyRefundParams().setUserName(yigjConfig.getUserName());
		rq.getApplyRefundParams().setSignUserName(yigjConfig.getSignUserName());
		
		ApplyRefundParams newParams=new ApplyRefundParams();
		newParams.setUserName(rq.getApplyRefundParams().getUserName());
		newParams.setSignUserName(rq.getApplyRefundParams().getSignUserName());
		newParams.setServiceName(rq.getApplyRefundParams().getServiceName());
		newParams.setOrderId(rq.getApplyRefundParams().getOrderId());
		try {
			rq.setSign(SignUtils.getSign(newParams, yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			ApplyRefundRS rs=new ApplyRefundRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(ApplyRefundRS.class, rq);
	}
	/**
	 * 生成订单
	 * @param rq
	 * @return
	 */
	public CreateOrderRS createOrder(CreateOrderRQ rq){
		rq.getCreateOrderParams().setUserName(yigjConfig.getUserName());
		rq.getCreateOrderParams().setServiceName(YIGJConstant.CREATE_ORDER);
		rq.getCreateOrderParams().setSignUserName(yigjConfig.getSignUserName());
		
		CreateOrderParams params=new CreateOrderParams();
		params.setUserName(rq.getCreateOrderParams().getUserName());
		params.setSignUserName(rq.getCreateOrderParams().getSignUserName());
		params.setServiceName(rq.getCreateOrderParams().getServiceName());
		params.setAuditNotifyUrl(rq.getCreateOrderParams().getAuditNotifyUrl());
		params.setExtOrderId(rq.getCreateOrderParams().getExtOrderId());
		try {
			rq.setSign(SignUtils.getSign(params, yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			CreateOrderRS rs=new CreateOrderRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(CreateOrderRS.class, rq);
	}
	/**
	 * 航段获取政策
	 * @param rq
	 * @return
	 */
	public MatchPoliciesBySegInfoRS matchPoliciesBySegInfo(MatchPoliciesBySegInfoRQ rq){
		rq.getMatchPoliciesBySegInfoParams().setUserName(yigjConfig.getUserName());
		rq.getMatchPoliciesBySegInfoParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getMatchPoliciesBySegInfoParams().setServiceName(YIGJConstant.MATCH_POLICIES_BY_SEGINFO);
		MatchPoliciesBySegInfoParams params=new MatchPoliciesBySegInfoParams();
		params.setUserName(rq.getMatchPoliciesBySegInfoParams().getUserName());
		params.setSignUserName(rq.getMatchPoliciesBySegInfoParams().getSignUserName());
		params.setServiceName(rq.getMatchPoliciesBySegInfoParams().getServiceName());
		try {
			rq.setSign(SignUtils.getSign(params, yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			MatchPoliciesBySegInfoRS rs=new MatchPoliciesBySegInfoRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(MatchPoliciesBySegInfoRS.class, rq);
	}
	/**
	 * 自动扣款
	 * @param rq
	 * @return
	 */
	public PayAutoRS payAuto(PayAutoRQ rq){
		rq.getPayAutoParams().setUserName(yigjConfig.getUserName());
		rq.getPayAutoParams().setServiceName(YIGJConstant.PAY_AUTO);
		rq.getPayAutoParams().setSignUserName(yigjConfig.getSignUserName());
		try {
			rq.setSign(SignUtils.getSign(rq.getPayAutoParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			PayAutoRS rs=new PayAutoRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(PayAutoRS.class, rq);
	}
	
	/**
	 * 出票规则
	 * @param rq
	 * @return
	 */
	public QueryAirRulesRS queryAirRules(QueryAirRulesRQ rq){
		rq.getQueryAirRulesParams().setUserName(yigjConfig.getUserName());
		rq.getQueryAirRulesParams().setServiceName(YIGJConstant.QUERY_AIR_RULES);
		rq.getQueryAirRulesParams().setSignUserName(yigjConfig.getSignUserName());
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryAirRulesParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryAirRulesRS rs=new QueryAirRulesRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryAirRulesRS.class, rq);
	}

	/**
	 * 航班查询
	 * @param rq
	 * @return
	 */
	public QueryFlightRS queryFlight(QueryFlightRQ rq){
		rq.getQueryFlightParams().setUserName(yigjConfig.getUserName());
		rq.getQueryFlightParams().setServiceName(YIGJConstant.QUERY_FLIGHT);
		rq.getQueryFlightParams().setSignUserName(yigjConfig.getSignUserName());
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryFlightParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryFlightRS rs=new QueryFlightRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryFlightRS.class, rq);
	}

	/**
	 * 更多舱位
	 * @param rq
	 * @return
	 */
	public QueryMoreCabinsRS queryMoreCabins(QueryMoreCabinsRQ rq){
		rq.getQueryMoreCabinsParams().setServiceName(YIGJConstant.QUERY_MORE_CABINS);
		rq.getQueryMoreCabinsParams().setUserName(yigjConfig.getUserName());
		rq.getQueryMoreCabinsParams().setSignUserName(yigjConfig.getSignUserName());
		QueryMoreCabinsParams params=new QueryMoreCabinsParams();
		params.setServiceName(rq.getQueryMoreCabinsParams().getServiceName());
		params.setUserName(rq.getQueryMoreCabinsParams().getUserName());
		params.setSignUserName(rq.getQueryMoreCabinsParams().getSignUserName());
		try {
			rq.setSign(SignUtils.getSign(params, yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryMoreCabinsRS rs=new QueryMoreCabinsRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryMoreCabinsRS.class, rq);
	}
	/**
	 * 查询订单信息
	 * @return
	 */
	public QueryOrderDetailRS queryOrderDetail(QueryOrderDetailRQ rq){
		rq.getQueryOrderParams().setUserName(yigjConfig.getUserName());
		rq.getQueryOrderParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryOrderParams().setServiceName(YIGJConstant.QUERY_ORDER_DETAIL);
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryOrderParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryOrderDetailRS rs=new QueryOrderDetailRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryOrderDetailRS.class, rq);
	}
	/**
	 * 查询订单状态
	 * @param rq
	 * @return
	 */
	public QueryOrderStatusRS queryOrderState(QueryOrderStatusRQ rq){
		rq.getQueryOrderParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryOrderParams().setServiceName(YIGJConstant.QUERY_ORDER_STATE);
		rq.getQueryOrderParams().setUserName(yigjConfig.getUserName());
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryOrderParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryOrderStatusRS rs=new QueryOrderStatusRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryOrderStatusRS.class, rq);
	}
	
	/**
	 * 获取政策
	 * @param rq
	 * @return
	 */
	public QueryPoliciesRS queryPolicies(QueryPoliciesRQ rq){
		rq.getQueryPoliciesParams().setUserName(yigjConfig.getUserName());
		rq.getQueryPoliciesParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryPoliciesParams().setServiceName(YIGJConstant.QUERY_POLICIES);
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryPoliciesParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryPoliciesRS rs=new QueryPoliciesRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryPoliciesRS.class, rq);
	}
	/**
	 * 查询政策信息
	 * @param rq
	 * @return
	 */
	public QueryPolicyStateRS queryPoliciesState(QueryPolicyStateRQ rq){
		rq.getQueryPolicyStateParams().setUserName(yigjConfig.getUserName());
		rq.getQueryPolicyStateParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryPolicyStateParams().setServiceName(YIGJConstant.QUERY_POLICY_STATE);
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryPolicyStateParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryPolicyStateRS rs=new QueryPolicyStateRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryPolicyStateRS.class, rq);
	}
	/**
	 * 查询退废票信息
	 * @param rq
	 * @return
	 */
	public QueryRefundStatusRS queryRefundState(QueryRefundStatesRQ rq){
		rq.getQueryRefundTktParams().setUserName(yigjConfig.getUserName());
		rq.getQueryRefundTktParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryRefundTktParams().setServiceName(YIGJConstant.QUERY_REFUND_STATE);
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryRefundTktParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryRefundStatusRS rs=new QueryRefundStatusRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryRefundStatusRS.class, rq);
	}
	/**
	 * 查询订单票号
	 * @param rq
	 * @return
	 */
	public QueryTicketNoRS queryTicketNo(QueryTicketNoRQ rq){
		rq.getQueryOrderParams().setUserName(yigjConfig.getUserName());
		rq.getQueryOrderParams().setSignUserName(yigjConfig.getSignUserName());
		rq.getQueryOrderParams().setServiceName(YIGJConstant.QUERY_TICKET_NO);
		try {
			rq.setSign(SignUtils.getSign(rq.getQueryOrderParams(),yigjConfig.getKey()));
		} catch (Exception e) {
			e.printStackTrace();
			QueryTicketNoRS rs=new QueryTicketNoRS();
			rs.setErrorMessage(GET_SIGN_ERROR);
			return rs;
		}
		return ServicesUtil.callService(QueryTicketNoRS.class, rq);
	}
}
