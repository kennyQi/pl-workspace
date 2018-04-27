package zzpl.h5.controller;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.user.UserDTO;
import zzpl.api.client.dto.workflow.WorkflowDTO;
import zzpl.api.client.request.jp.GNCancelTicketCommand;
import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.api.client.request.order.FlightOrderListQO;
import zzpl.api.client.request.workflow.WorkflowQO;
import zzpl.api.client.response.jp.GNCancelTicketResponse;
import zzpl.api.client.response.order.FlightOrderInfoResponse;
import zzpl.api.client.response.order.FlightOrderListResponse;
import zzpl.api.client.response.user.QueryUserResponse;
import zzpl.h5.service.FlightService;
import zzpl.h5.service.OrderService;
import zzpl.h5.service.UserService;
import zzpl.h5.util.alipay.AlipaySubmit;
import zzpl.h5.util.alipay.bean.AliProduct;
import zzpl.h5.util.alipay.entity.AlipayWapTradeCreateDirect;
import zzpl.h5.util.base.BaseController;

import com.alibaba.fastjson.JSON;

@Controller
@RequestMapping("order")
public class OrderController extends BaseController{

	@Autowired
	private OrderService orderService;
	@Autowired
	private FlightService flightService;
	@Autowired
	private UserService userService;
	
	/**
	 * 
	 * @方法功能说明：订单列表
	 * @修改者名字：cangs
	 * @修改时间：2015年12月2日下午4:07:24
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param type
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/view")
	public String orderView(HttpServletRequest request,HttpServletResponse response,Model model,int type){
		FlightOrderListQO flightOrderListQO = new FlightOrderListQO();
		flightOrderListQO.setPageNO(1);
		flightOrderListQO.setPageSize(9999);
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		flightOrderListQO.setUserID(authUser.getId());
		if(type==1){
			flightOrderListQO.setStatus(100);
		}else if(type==2){
			flightOrderListQO.setStatus(102);
		}else if(type==3){
			flightOrderListQO.setStatus(104);
		}
		FlightOrderListResponse flightOrderListResponse = orderService.getFlightOrderList(flightOrderListQO, request);
		if(flightOrderListResponse!=null&&flightOrderListResponse.getResult()!=null&&flightOrderListResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			model.addAttribute("flightOrders", flightOrderListResponse.getFlightOrderLists());
			model.addAttribute("isTheLastPage", flightOrderListResponse.getIsTheLastPage());
			model.addAttribute("status", "success");
		}else{
			//状态位留个页面处理，当失败的时候让前台重新请求本页
			model.addAttribute("status", "fail");
		}
		model.addAttribute("type", type);
		return "fly_order.html";
	}
	
	
	
	/**
	 * 
	 * @方法功能说明：订单详情
	 * @修改者名字：cangs
	 * @修改时间：2015年12月2日下午4:06:57
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param orderType
	 * @参数：@param orderID
	 * @参数：@param orderNO
	 * @参数：@param type
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/topay")
	public String toPay(HttpServletRequest request,HttpServletResponse response,Model model,String orderType,String orderID,String orderNO,int type){
		if(StringUtils.isNotBlank(orderType)&&StringUtils.equals(orderType, "singleOrder")){
			FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
			flightOrderInfoQO.setOrderID(orderID);
			FlightOrderInfoResponse flightOrderInfoResponse = orderService.getFlightOrderInfo(flightOrderInfoQO, request);
			if(flightOrderInfoResponse.getFlightOrderInfo().getEndTime()!=null&&flightOrderInfoResponse.getFlightOrderInfo().getStartTime()!=null){
				long endTime= flightOrderInfoResponse.getFlightOrderInfo().getEndTime().getTime();
				long startTime = flightOrderInfoResponse.getFlightOrderInfo().getStartTime().getTime();
				long difference = endTime - startTime;
				long day=difference/(24*60*60*1000);   
				long hour=(difference/(60*60*1000)-day*24);   
				long min=((difference/(60*1000))-day*24*60-hour*60);
				String  flightDuration = "";
				if(day!=0){
					flightDuration+=day+"天";
				}
				if(hour!=0){
					flightDuration+=hour+"小时";
				}
				if(min!=0){
					flightDuration+=min+"分";
				}
				model.addAttribute("flightDuration", flightDuration);
			}else{
				model.addAttribute("flightDuration", "");
			}
			model.addAttribute("flightOrderInfo", flightOrderInfoResponse.getFlightOrderInfo());
			model.addAttribute("passengers", flightOrderInfoResponse.getFlightOrderInfo().getPassengerList());
			model.addAttribute("orderNO", orderNO);
			model.addAttribute("orderType", "singleOrder");
		}
		model.addAttribute("type", type);
		return "fly_topay.html";
	}
	
	/**
	 * 
	 * @方法功能说明：支付
	 * @修改者名字：cangs
	 * @修改时间：2015年12月2日下午4:06:49
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param response
	 * @参数：@param model
	 * @参数：@param orderID
	 * @参数：@param orderNO
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping("/pay")
	public String pay(HttpServletRequest request,HttpServletResponse response,Model model,String orderID,String orderNO){
		try{
			/*GetGNFlightOrderTotalPriceCommand getGNFlightOrderTotalPriceCommand = new GetGNFlightOrderTotalPriceCommand();
			getGNFlightOrderTotalPriceCommand.setOrderNO(orderNO);
			GetGNFlightOrderTotalPriceResponse getGNFlightOrderTotalPriceResponse = orderService.getGNFlightOrderTotalPrice(getGNFlightOrderTotalPriceCommand, request);*/
			FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
			flightOrderInfoQO.setOrderNO(orderNO);
			FlightOrderInfoResponse flightOrderInfoResponse = orderService.getFlightOrderInfo(flightOrderInfoQO, request);
			if(flightOrderInfoResponse.getResult()==ApiResponse.RESULT_CODE_FAIL){
				return "alipay/pay_fail_redirect.html";
			}
			AliProduct product = new AliProduct();
			product.setOut_trade_no(flightOrderInfoResponse.getFlightOrderInfo().getOrderNO());
			product.setSubject("中智差旅机票订单");
			product.setTotal_fee(flightOrderInfoResponse.getFlightOrderInfo().getTotalPrice().toString());
			AlipayWapTradeCreateDirect obj = getReqBean(product);
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平请求【订单编号】："+JSON.toJSONString(product.getOut_trade_no()));
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平请求【商品名称】："+JSON.toJSONString(product.getTotal_fee()));
			HgLogger.getInstance().error("cs", "【alipayNotify】"+"【支付宝】【H5】支付平请求【付款金额】："+JSON.toJSONString(product.getSubject()));
			//请求业务参数详细
			String req_dataToken = getReqAuthonToken(obj, request);
			
			Map<String, String> sParaTempToken = new HashMap<String, String>();
			sParaTempToken.put("service", obj.getService());
			sParaTempToken.put("partner",obj.getPartner());
			sParaTempToken.put("_input_charset", SysProperties.getInstance().get("alipay_charset"));
			sParaTempToken.put("sec_id", obj.getSecId());
			sParaTempToken.put("format",obj.getFormat());
			sParaTempToken.put("v",obj.getV());
			sParaTempToken.put("req_id",obj.getReqId());
			sParaTempToken.put("req_data", req_dataToken);
			//支付宝网关地址
			String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";
			
			//授权接口
			Long startTime = System.currentTimeMillis();
			String sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW,"", "",sParaTempToken);
			
			obj.setTimes(System.currentTimeMillis() - startTime);
			
			sHtmlTextToken = URLDecoder.decode(sHtmlTextToken,SysProperties.getInstance().get("alipay_charset"));
			
			String xml = AlipaySubmit.getWapTradeCreateDirectResStr(sHtmlTextToken);
			Document document = DocumentHelper.parseText(xml);
			String code  = getNodeText(document.selectSingleNode("//err/code"));
			if(StringUtils.isNotBlank(code)){//报错 <?xml version="1.0" encoding="utf-8"?><err><code>0005</code><sub_code>0005</sub_code><msg>partnerillegal</msg><detail>合作伙伴没有开通接口访问权限</detail></err>
				obj.setResErrCode(code);
				obj.setResErrSubCode(getNodeText(document.selectSingleNode("//err/sub_code")));
				obj.setResErrMsg(getNodeText(document.selectSingleNode("//err/msg")));
				obj.setResErrDetail(getNodeText(document.selectSingleNode("//err/detail")));
			}else{//请求成功 <?xml version="1.0" encoding="utf-8"?><direct_trade_create_res><request_token>20100830e8085e3e0868a466b822350ede5886e8</request_token></direct_trade_create_res>
				obj.setRequestToken(getNodeText(document.selectSingleNode("//direct_trade_create_res/request_token")));
			}
			////////////////////////////////////根据授权码token调用交易接口alipay.wap.auth.authAndExecute//////////////////////////////////////
			//把请求参数打包成数组
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
			sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
			sParaTemp.put("partner", obj.getPartner());
			sParaTemp.put("_input_charset", SysProperties.getInstance().get("alipay_charset"));
			sParaTemp.put("sec_id", obj.getSecId());
			sParaTemp.put("format",obj.getFormat());
			sParaTemp.put("v",obj.getV());
			sParaTemp.put("req_data","<auth_and_execute_req><request_token>"+obj.getRequestToken()+"</request_token></auth_and_execute_req>");
			
			//建立请求
			String sHtmlText = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");
			model.addAttribute("htmlText", sHtmlText);
			HgLogger.getInstance().info("cs", "【pay】"+"【支付宝】【H5】跳转成功");
			return "alipay/autoForm.html";
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【pay】"+"【支付宝】【H5】异常，"+HgLogger.getStackTrace(e));
			return "alipay/pay_fail_redirect.html";
		}
	}
	
	
	@RequestMapping("/orderinfo")
	public String orderInfo(HttpServletRequest request,HttpServletResponse response,Model model,String orderID){
		FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
		flightOrderInfoQO.setOrderID(orderID);
		FlightOrderInfoResponse flightOrderInfoResponse = orderService.getFlightOrderInfo(flightOrderInfoQO, request);
		if(flightOrderInfoResponse!=null&&flightOrderInfoResponse.getResult()!=null&&flightOrderInfoResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			model.addAttribute("flightOrderInfo", flightOrderInfoResponse.getFlightOrderInfo());
			//计算飞行时长
			if(flightOrderInfoResponse.getFlightOrderInfo().getEndTime()!=null&&flightOrderInfoResponse.getFlightOrderInfo().getStartTime()!=null){
				long endTime= flightOrderInfoResponse.getFlightOrderInfo().getEndTime().getTime();
				long startTime = flightOrderInfoResponse.getFlightOrderInfo().getStartTime().getTime();
				long difference = endTime - startTime;
				long day=difference/(24*60*60*1000);   
				long hour=(difference/(60*60*1000)-day*24);   
				long min=((difference/(60*1000))-day*24*60-hour*60);
				String  flightDuration = "";
				if(day!=0){
					flightDuration+=day+"天";
				}
				if(hour!=0){
					flightDuration+=hour+"小时";
				}
				if(min!=0){
					flightDuration+=min+"分";
				}
				model.addAttribute("flightDuration", flightDuration);
				SimpleDateFormat simpleDateFormat=  new SimpleDateFormat("MMdd");
				int startDay = Integer.parseInt(simpleDateFormat.format(flightOrderInfoResponse.getFlightOrderInfo().getStartTime()));
				int endDay = Integer.parseInt(simpleDateFormat.format(flightOrderInfoResponse.getFlightOrderInfo().getEndTime()));
				int now = Integer.parseInt(simpleDateFormat.format(new Date()));
				if(startDay-now==0){
					model.addAttribute("startCHN", "今天");
				}else if(startDay-now==1){
					model.addAttribute("startCHN", "明天");
				}else if(startDay-now==2){
					model.addAttribute("startCHN", "后天");
				}
				if(endDay-now==0){
					model.addAttribute("endCHN", "今天");
				}else if(endDay-now==1){
					model.addAttribute("endCHN", "明天");
				}else if(endDay-now==2){
					model.addAttribute("endCHN", "后天");
				}
			}else{
				model.addAttribute("flightDuration", "");
			}
		}else{
			return orderView(request, response, model,0);
		}
		return "fly_orderinfo.html";
	}
	
	/**
	 * @Title: toCancel 
	 * @author guok
	 * @Description: 跳转退票页面
	 * @Time 2015年12月7日上午9:44:22
	 * @param request
	 * @param response
	 * @param model
	 * @param orderID
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/toCancel")
	public String toCancel(HttpServletRequest request,HttpServletResponse response,Model model,String orderID) {
		HgLogger.getInstance().info("gk", "【OrderController】【toCancel】"+"orderID:"+JSON.toJSONString(orderID));
		FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
		flightOrderInfoQO.setOrderID(orderID);
		FlightOrderInfoResponse flightOrderInfoResponse = orderService.getFlightOrderInfo(flightOrderInfoQO, request);
		if(flightOrderInfoResponse!=null&&flightOrderInfoResponse.getResult()!=null&&flightOrderInfoResponse.getResult()==ApiResponse.RESULT_CODE_OK){
			model.addAttribute("flightOrderInfo", flightOrderInfoResponse.getFlightOrderInfo());
		}else{
			return orderView(request, response, model,0);
		}
		//sessionID
		String sessionID = request.getSession().getAttribute("USER_SESSION_ID").toString();
		//当前登陆人
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		QueryUserResponse userResponse = userService.getUserInfo(authUser.getId());
		UserDTO userDTO = userResponse.getUserDTO();
		model.addAttribute("userDTO", userDTO);
		//查询流程
		WorkflowQO workflowQO = new WorkflowQO();
		workflowQO.setCompanyID(userDTO.getCompanyID());
		workflowQO.setRoleList(userDTO.getRoleList());
		List<WorkflowDTO> workflowDTOs = flightService.queryWorkflow(workflowQO, sessionID).getWorkflowDTOs();
		model.addAttribute("workflowDTOs", workflowDTOs);
		return "apply_refund.html";
	}
	
	/**
	 * @Title: cancelGNTicket 
	 * @author guok
	 * @Description: 申请退票
	 * @Time 2015年12月7日上午9:13:54
	 * @param request
	 * @param response
	 * @param model
	 * @return String 设定文件
	 * @throws
	 */
	@RequestMapping("/cancelGNTicket")
	public String cancelGNTicket(HttpServletRequest request,HttpServletResponse response,Model model,
			GNCancelTicketCommand gnCancelTicketCommand,String nextUserID) {
		AuthUser authUser = (AuthUser) request.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
		gnCancelTicketCommand.setUserID(authUser.getId());
		//下一步执行人
		List<String> nextUserIDs = new ArrayList<String>();
		nextUserIDs.add(nextUserID);
		gnCancelTicketCommand.setNextUserIDs(nextUserIDs);
		HgLogger.getInstance().info("gk", "【OrderController】【cancelGNTicket】"+"gnCancelTicketCommand:"+JSON.toJSONString(gnCancelTicketCommand));
		GNCancelTicketResponse cancelTicketResponse = orderService.cancelGNTicket(gnCancelTicketCommand, request);
		if (cancelTicketResponse.getResult() == ApiResponse.RESULT_CODE_OK) {
			return orderView(request, response, model,0);
		}else {
			return orderView(request, response, model,0);
		}
	}
	
	/**
	 * -------------------------------------------------------------------------------支付宝-------------------------------------------------------------------
	 */
	
	/**
	 * 获取webapp路径
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
        String proj = request.getContextPath();
        String port = SysProperties.getInstance().get("port");
        String system = "https://" + SysProperties.getInstance().get("host") + 
        		("80".equals(port) ? "" : ":" + port);
        if (!isRoot) {
        	system += proj;
        }
        return system;
	}
	
	private AlipayWapTradeCreateDirect getReqBean(AliProduct product) {
		AlipayWapTradeCreateDirect obj = new AlipayWapTradeCreateDirect();		
		//返回格式
		obj.setFormat("xml");
		obj.setV("2.0");
		obj.setReqId(System.currentTimeMillis()+RandomStringUtils.randomNumeric(10));//请求号  必填，须保证每次请求都是唯一
		//req_data详细信息
		//服务器异步通知页面路径
		obj.setNotifyUrl(SysProperties.getInstance().get("alipay_notify_url"));
		//页面跳转同步通知页面路径
		obj.setCallBackUrl(SysProperties.getInstance().get("alipay_callback_url"));
		//操作中断返回地址
		obj.setMerchantUrl(SysProperties.getInstance().get("alipay_merchant_url"));

		//卖家支付宝帐户
		obj.setSellerAccountName(SysProperties.getInstance().get("alipay_seller_mail"));
		//商户订单号 
		obj.setOutTradeNo(product.getOut_trade_no());
		//订单名称
		obj.setSubject(product.getSubject());
		//付款金额
		obj.setTotalFee(product.getTotal_fee());
		obj.setReqDate(new Date());
		return obj;
	}
	
	/**
	 * 获得请求授权的token
	 * @param obj
	 * @return
	 */
	private String getReqAuthonToken(AlipayWapTradeCreateDirect obj, HttpServletRequest request) {
		String ctx = getWebAppPath(request);
		String req_dataToken = "<direct_trade_create_req><notify_url>" + obj.getNotifyUrl() + "</notify_url>" +
				"<call_back_url>" + (ctx + obj.getCallBackUrl()) + "</call_back_url>" +
				"<seller_account_name>" + obj.getSellerAccountName() + "</seller_account_name>" +
				"<out_trade_no>" + obj.getOutTradeNo() + "</out_trade_no>" +
				"<subject>" + obj.getSubject() + "</subject>" +
				"<total_fee>" + obj.getTotalFee() + "</total_fee>" +
				"<merchant_url>" + (ctx + obj.getMerchantUrl()) + "</merchant_url>" +
				"</direct_trade_create_req>";
		return req_dataToken;
	}
	public static String getNodeText(Node node){
		if(node!=null){
			return node.getText();
		}
		return null;
	}
}
