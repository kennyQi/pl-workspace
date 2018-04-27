package hsl.h5.alipay.ctl;
import hg.common.util.SMSUtils;
import hsl.app.component.config.SysProperties;
import hg.log.util.HgLogger;
import hsl.alipay.util.AlipayNotify;
import hsl.alipay.util.AlipaySubmit;
import hsl.app.service.local.jp.JPFlightLocalService;
import hsl.app.service.local.jp.JPOrderLocalService;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.jp.JPOrder;
import hsl.h5.alipay.bean.AliProduct;
import hsl.h5.alipay.entity.AlipayWapTradeCreateDirect;
import hsl.h5.alipay.entity.AlipayWapTradeCreateDirectRes;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.exception.CouponException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.inter.Coupon.CouponService;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;

import com.alibaba.fastjson.JSON;
/***
 * 支付宝支付网关
 * @author 张有良
 */
@Controller
@RequestMapping("alipay")
public class AlipayJpOrderCtl {
	
	@Resource
	private JPOrderLocalService jpOrderLocalService;
	
	@Resource
	private JPFlightLocalService jpFlightLocalService;
	
	@Resource
	private RabbitTemplate template;
	
	@Resource
	private SMSUtils smsUtils;
	
	@Autowired
	private  CouponService   couponService;

	/**
	 * 地址后缀
	 */
	public final static String SUFFIX = "-jp-order";
	
	/**
	 * 去支付(机票订单)
	 * @param request
	 * @param pw
	 * @return
	 * @throws Exception 
	 * @throws DocumentException
	 */
	@RequestMapping("pay")
	public Object pay(String out_trade_no, String total_fee,String subject, PrintWriter out, HttpServletRequest request,String id,String payAmount) {
		String ctx = getWebAppPath(request);
		ModelAndView mv = new ModelAndView("alipay/autoForm");		
			//如果订单实际金额减去优惠券面值后大于0，则仍然去调用支付宝支付减去优惠之后的价格；相反则直接跳转支付成功页面
			if(Double.parseDouble(total_fee)>0){
				try{
					//如果用户选择了卡券， 用户点击确认支付后保存一个订单快照
					if(id!=null){
						HgLogger.getInstance().info("liusong", "AlipayCtl>>pay>>用户选择卡券ID"+id);
						if (StringUtils.isNotBlank(id)) {
							String[] ids={id};
							BatchConsumeCouponCommand consumeCouponCommand = new BatchConsumeCouponCommand();
							consumeCouponCommand.setCouponIds(ids);
							consumeCouponCommand.setOrderId(out_trade_no);
							consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_JP);
							consumeCouponCommand.setPayPrice(Double.parseDouble(payAmount));
							//占用卡券，保存订单快照
							couponService.occupyCoupon(consumeCouponCommand);
							HgLogger.getInstance().info("liusong", "AlipayCtl>>pay>>占用卡券完成");
						}
					}
				}catch(CouponException e){
					HgLogger.getInstance().info("liusong", "AlipayCtl>>pay>>用户选择的卡券不能使用");
					 return 	"redirect:" + ctx + "/jpo/pay?orderId=" + out_trade_no+"&couponError="+1;
				}
			try{
				AliProduct product = new AliProduct();
				product.setOut_trade_no(out_trade_no);
				product.setSubject(URLDecoder.decode(subject.replaceAll("\\$", "%"), "utf-8"));
				product.setTotal_fee(total_fee);
				////////////////////////////////////调用授权接口alipay.wap.trade.create.direct获取授权码token//////////////////////////////////////
				AlipayWapTradeCreateDirect obj = getReqBean(product);
	
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
				mv.addObject("htmlText", sHtmlText);
			}catch(Exception e){
				e.printStackTrace();
			}
			// alipayCommunicationService.saveAlipayWapTradeCreateDirect(obj);
			return mv;
		}else{
			try{
				//如果用户选择了卡券， 用户点击确认支付后保存一个订单快照
				if(id!=null){
					if (StringUtils.isNotBlank(id)) {
						String[] ids={id};
						BatchConsumeCouponCommand consumeCouponCommand = new BatchConsumeCouponCommand();
						consumeCouponCommand.setCouponIds(ids);
						consumeCouponCommand.setOrderId(out_trade_no);
						consumeCouponCommand.setPayPrice(Double.parseDouble(payAmount));
						//占用卡券，保存订单快照
						couponService.occupyCoupon(consumeCouponCommand);
					}
				}
			}catch(CouponException e){
				HgLogger.getInstance().info("liusong", "AlipayCtl>>pay>>卡券已被使用"+HgLogger.getStackTrace(e));
				return 	"redirect:" + ctx + "/jpo/pay?orderId=" + out_trade_no+"&couponError="+e.getMessage();
			}
		        return 	"redirect:" + ctx + "/jpo/success?orderId=" + out_trade_no;
		}
	}

	/**
	 * 获得请求授权的token(机票订单)
	 * @param obj
	 * @return
	 */
	private String getReqAuthonToken(AlipayWapTradeCreateDirect obj, HttpServletRequest request) {
		String ctx = getWebAppPath(request);
		String req_dataToken = "<direct_trade_create_req><notify_url>" + obj.getNotifyUrl()+SUFFIX + "</notify_url>" +
				"<call_back_url>" + (ctx + obj.getCallBackUrl()+SUFFIX) + "</call_back_url>" +
				"<seller_account_name>" + obj.getSellerAccountName() + "</seller_account_name>" +
				"<out_trade_no>" + obj.getOutTradeNo() + "</out_trade_no>" +
				"<subject>" + obj.getSubject() + "</subject>" +
				"<total_fee>" + obj.getTotalFee() + "</total_fee>" +
				"<merchant_url>" + (ctx + obj.getMerchantUrl()+SUFFIX) + "</merchant_url>" +
				"</direct_trade_create_req>";
		return req_dataToken;
	}
	
	/**
	 * 通知(机票订单)
	 * @param request
	 * @param id
	 * @param type
	 * @param pw
	 * @return
	 * {sign=QPhOG5Q+XweORBK5wM+ITe9O3YEqzIrjpNsztb8njIaNA3RKn5u3XOxU/5u4mFas+7zYAYjfDnLqNo/NOQUL0juGsGpvZeTj7V0KlTSNsR/h1hvtVZK/lF+262yatwGvT7o4+9h7alYQnQE6aApPKW9pT0QoXtLRbWrc5xR//5I=, 
	 * result=success, 
	 * trade_no=2014011352228584, 
	 * sign_type=0001, 
	 * out_trade_no=1, 
	 * request_token=requestToken}
	 * http://mobiren.gicp.net:7000/ylz/pay/alipayApi/notify?out_trade_no=123456813&request_token=requestToken&result=success&trade_no=2014011456024384&sign=JuUYPH4SSp36JCAwpXt5ECQbsXVkdFUaoYgYLzhVl%2BY0hb7yhKJl3TozI4QBUud3CqfOLdXV9u14lsPVoWl%2BXdVpdylR%2B3fDQ%2FHGOmpNrrllAoQO9g9TCiK8bRYKe4lJ0aw0JYqmt5NcZGmZ5XXiYdzyqKzWyi%2FOEKioWICXH6k%3D&sign_type=0001
	 * @throws Exception
	 */
	@RequestMapping(value="callback"+SUFFIX)
	public String callback(HttpServletRequest request,PrintWriter out) throws Exception{
		Map<String, String> params = getAllReqParam(request);
		AlipayWapTradeCreateDirectRes obj = new AlipayWapTradeCreateDirectRes();
		obj.setSign(params.get("sign"));
		obj.setTradeStatus(params.get("result"));
		obj.setTradeNo(params.get("trade_no"));
		obj.setSecId(params.get("sign_type"));
		obj.setOutTradeNo(params.get("out_trade_no"));
		obj.setRequestToken(params.get("request_token"));
		String ctx = getWebAppPath(request);
		String orderId = params.get("out_trade_no");
		if(AlipayNotify.verifyReturn(params)){//验证成功
			return "redirect:" + ctx + "/jpo/success?orderId=" + orderId;
		}else{//危险
			return "redirect:" + ctx + "/alipay/payerror"+SUFFIX;
		}
	}

	@RequestMapping("payerror"+SUFFIX)
	public ModelAndView error() {
		ModelAndView mav = new ModelAndView("error");
		return mav;
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
	 * 获取webapp路径
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
        String proj = request.getContextPath();
        String port = SysProperties.getInstance().get("port");
        String system = "http://" + SysProperties.getInstance().get("host") + 
        		("80".equals(port) ? "" : ":" + port);
        if (!isRoot) {
        	system += proj;
        }
        return system;
	}
	
	//获取请求的数据组合成map
	private Map<String, String> getAllReqParam(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map<?,?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		return params;
	}
	
	public static String getNodeText(Node node){
		if(node!=null){
			return node.getText();
		}
		return null;
	}
	
	
	/**
	 * 支付宝异步通知地址      web处理方式
	 * @param request
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping("/notify"+SUFFIX)
	public void notify(HttpServletRequest request) {
		HgLogger.getInstance().info("zhangka", "ApiController->notify->处理支付宝异步通知开始");
		//获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		HgLogger.getInstance().info("zhangka", "ApiController->notify->requestParams[post参数]"+JSON.toJSONString(requestParams));
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		HgLogger.getInstance().info("zhangka", "ApiController->notify->params[post参数]"+JSON.toJSONString(params));
		
		
		//商户订单号
		String out_trade_no = "";
		//支付宝交易号
		String trade_no = "";
		//交易状态
		String trade_status = "";
		//付款支付宝账号
		String buyer_email = "";
		
		boolean verifyNotify = false;
		try {
			verifyNotify = AlipayNotify.verifyNotify(params,false);
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			
			out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
			trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
			buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "ApiController->notify->获取参数为空:" 
					+"|out_trade_no="+out_trade_no
					+"|trade_no"+trade_no
					+"|trade_status"+trade_status
					+ HgLogger.getStackTrace(e));
			return;
		}
		
		HgLogger.getInstance().info("zhangka", "out_trade_no="+out_trade_no+",trade_no="+trade_no+",trade_status="+trade_status);
		
		HslJPOrderQO qo = new HslJPOrderQO();
		qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));
		qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
		qo.setDealerOrderCode(out_trade_no);
		JPOrder jpOrder = jpOrderLocalService.queryUnique(qo);
		
		if (jpOrder == null) {
			HgLogger.getInstance().info("zhangka", "待支付订单查询失败，dealerOrderCode="+out_trade_no);
			return;
		} else {
			jpOrder.setBuyerEmail(buyer_email);//设置付款支付宝账号
			HgLogger.getInstance().info("zhangka", "ApiController->notify->jpOrder->ID="+jpOrder.getId());
		}

		if (verifyNotify) {//验证成功
			
			if (trade_status.equals("WAIT_BUYER_PAY")) {
				//交易创建，等待买家付款。
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));//待确认
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));//待支付
			} else if (trade_status.equals("TRADE_CLOSED")) {
				//1、在指定时间段内未支付时关闭的交易；
				//2、在交易完成全额退款成功时关闭的交易。
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_CANCEL));//已取消
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_CANCEL));//已取消
			} else if (trade_status.equals("TRADE_SUCCESS")) {
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//该种交易状态只在一种情况下出现——开通了高级即时到账，买家付款成功后。
				//更改订单状态
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));//出票处理中
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));//已支付
				
				//卡券
				
				CreateCouponCommand cmd=new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				cmd.setPayPrice(jpOrder.getPayAmount());
				cmd.setMobile(jpOrder.getOrderUser().getMobile());
				cmd.setUserId(jpOrder.getOrderUser().getUserId());
				cmd.setLoginName(jpOrder.getOrderUser().getLoginName());
				CouponMessage baseAmqpMessage=new CouponMessage();
				baseAmqpMessage.setMessageContent(cmd);
				String issue=SysProperties.getInstance().get("issue_on_full");
				int type=0;
				if(StringUtils.isBlank(issue))
					type=2;//默认为2
				else{
					type=Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				template.convertAndSend("hsl.order",baseAmqpMessage);
				
			} else if (trade_status.equals("TRADE_PENDING")) {
				//等待卖家收款（买家付款后，如果卖家账号被冻结）。
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));//已支付
			} else if (trade_status.equals("TRADE_FINISHED")) {
				//判断该笔订单是否在商户网站中已经做过处理
					//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
					//如果有做过处理，不执行商户的业务程序
					
				//注意：
				//该种交易状态只在两种情况下出现
				//1、开通了普通即时到账，买家付款成功后。
				//2、开通了高级即时到账，从该笔交易成功时间算起，过了签约时的可退款时限（如：三个月以内可退款、一年以内可退款等）后。
				
				//更改订单状态
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));//出票处理中
				jpOrder.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));//已支付
			}

		} else {//验证失败
			jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
			jpOrderLocalService.update(jpOrder);
			HgLogger.getInstance().info("zhangka", "ApiController->notify->支付宝异步通知验证失败AlipayNotify.verifyNotify="+verifyNotify);
			return;
		}
		
		//发送短信通知用户付款成功
		if ((JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC).equals(String.valueOf(jpOrder.getPayStatus())) && jpOrder.getOrderUser() != null) {
			//如果支付成功，则修改与该订单相关的卡券的状态为已消费状态
			HslCouponQO hslCouponQO = new HslCouponQO();
			hslCouponQO.setOrderId(jpOrder.getDealerOrderCode());
			
			//根据订单号查询一个与订单绑定的卡券快照
			CouponDTO  coupondto = couponService.queryUnique(hslCouponQO);
			HgLogger.getInstance().info("liusong","JPController>>createClientNotify>根据订单号查询一个与订单绑定的卡券快照"+JSON.toJSONString(coupondto));
			
			//修改卡券的状态
			String[] ids={coupondto.getId()};
			BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
			if(coupondto!=null){
				couponCommand.setCouponIds(ids);
				couponCommand.setOrderId(coupondto.getConsumeOrder().getOrderId());
				couponCommand.setPayPrice(coupondto.getConsumeOrder().getPayPrice());
				try{
					couponService.confirmConsumeCoupon(couponCommand);
				}catch(CouponException e){
					e.printStackTrace();
					HgLogger.getInstance().error("liusong", "AlipayCtl>>notify>支付成功后调用，修改卡券状态为已使用失败："+e.getMessage()+HgLogger.getStackTrace(e));
				}
			}
			
			//请求出票
			try {
				//更新到数据库（支付状态和出票状态的修改可以不在同一个事务里面）
				jpOrderLocalService.update(jpOrder);
				
				JPAskOrderTicketCommand command = new JPAskOrderTicketCommand();
				command.setOrderId(out_trade_no);
				command.setPayOrderId(trade_no);
				command.setBuyerEmail(buyer_email);
				command.setPayWay("ZH");
				jpFlightLocalService.askOrderTicket(command);//请求出票
			} catch (Exception e) {
				HgLogger.getInstance().error("zhangka", "ApiController->notify->请求出票异常" + HgLogger.getStackTrace(e));
			}
			
			//发短信
			try {
				String mobile = jpOrder.getOrderUser().getMobile();
				String smsContent = "【"+SysProperties.getInstance().get("sms_sign", "票量旅游")+"】您的订单" + jpOrder.getDealerOrderCode() + "，已经购买成功，出票完成后将短信通知，请您放心安排行程。查询订单请点击http://m.ply365.com/user/jpos，客服电话：0571-28280813。";
				HgLogger.getInstance().info("chenxy", "订单支付成功发送短信  :" + mobile + " smsContent:" + smsContent);
				smsUtils.sendSms(mobile, smsContent);
				HgLogger.getInstance().info("zhangka", "ApiController->notify->jpOrder->mobile:" + mobile + " smsContent:" + smsContent);
			} catch (Exception e) {
				HgLogger.getInstance().error("zhangka", "ApiController->notify->用户付款成功短信发送异常" + HgLogger.getStackTrace(e));
			}
		}
		
		HgLogger.getInstance().info("zhangka", "ApiController->/api/alipay/notify->处理支付宝异步通知结束");
	}
	
	
	/**
	 * 支付页面提交订单之前，验证卡券是否可用
	 * 
	 * @param command
	 * @param model
	 * @param request
	 * */
	@RequestMapping(value = "/getCouponUsed")
	@ResponseBody
	private String getCouponUsed(HttpServletRequest request) {
		// 查询用户选择的卡券是否可使用
		String useCouponIDs = request.getParameter("useCouponIDs");
		String dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
		Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
		String[] ids= useCouponIDs.split(",");
		BatchConsumeCouponCommand consumeCouponCommand = null;
		String message = "";// 标识卡券是否可用的字段变量
				consumeCouponCommand = new BatchConsumeCouponCommand();
				consumeCouponCommand.setCouponIds(ids);
				consumeCouponCommand.setOrderId(dealerOrderId);
				consumeCouponCommand.setPayPrice(payPrice);
			// 如果用户选择了多个卡券，那么每循环出一个卡券id就要去做一次判断，判断该卡券是否可用
			try {
				boolean flag = couponService.checkCoupon(consumeCouponCommand);
				if (flag) {
					HgLogger.getInstance().info("liusong", "卡券可用");
					message = "success";
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				HgLogger.getInstance().error("liusong","卡券不可用->JpController->payOrder->exception:"+ HgLogger.getStackTrace(ex));
				return ex.getMessage();
			}
		return message;
	}

}
