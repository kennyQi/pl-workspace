package hsl.h5.alipay.ctl;

import com.alibaba.fastjson.JSON;

import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.alipay.util.AlipayNotify;
import hsl.alipay.util.AlipaySubmit;
import hsl.domain.model.coupon.ConsumeOrderSnapshot;
import hsl.domain.model.jp.FlightOrder;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.h5.alipay.bean.AliProduct;
import hsl.h5.alipay.entity.AlipayWapTradeCreateDirect;
import hsl.h5.alipay.entity.AlipayWapTradeCreateDirectRes;
import hsl.h5.control.HslCtrl;
import hsl.h5.exception.HslapiException;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.AccountConsumeSnapshotCommand;
import hsl.pojo.command.coupon.BatchConsumeCouponCommand;
import hsl.pojo.command.coupon.CreateCouponCommand;
import hsl.pojo.command.jp.BookGNFlightCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.jp.plfx.JPPayOrderGNCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.plfx.JPBookOrderGNDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.exception.AccountException;
import hsl.pojo.exception.CouponException;
import hsl.pojo.message.CouponMessage;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.commonContact.CommonContactService;
import hsl.spi.inter.jp.JPService;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.*;
/***
 * 支付宝支付网关
 * @author 张有良
 */
@Controller
@RequestMapping("alipay")
public class AlipayCtl extends HslCtrl{

	@Resource
	private RabbitTemplate template;

	@Resource
	private SMSUtils smsUtils;

	@Autowired
	private  CouponService   couponService;

	@Autowired
	private JPService jpService;
	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	@Autowired
	private CommonContactService commonContactService;
	/**
	 * 去支付
	 * @param request
	 * @return
	 * @throws HslapiException
	 * @throws Exception
	 * @throws DocumentException
	 */
	@RequestMapping("pay")
	public Object pay(Model model,String out_trade_no, String total_fee,String subject, PrintWriter out, HttpServletRequest request,String id,String payAmount,String balance) throws HslapiException {
		//String ctx = getWebAppPath(request);
		HgLogger.getInstance().info("renfeng", "pay--使用余额--balance"+balance+"==id"+id);
		ModelAndView mv = new ModelAndView("alipay/autoForm");
		model.addAttribute("orderNo", out_trade_no);
		model.addAttribute("subject", subject);
		String message="";
		String ctx = getWebAppPath(request);
		//登录用户
		String userId = this.getUserId(request);
		//判断机票价格是否有变化
		FlightOrderQO flightOrderQO=new FlightOrderQO();
		flightOrderQO.setOrderNO(out_trade_no);
		flightOrderQO.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
		FlightOrderDTO flightOrderDTO=jpService.queryOrderDetail(flightOrderQO);
		
		if(flightOrderDTO==null){
			message="订单不存在";
			HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>订单不存在，订单号："+out_trade_no);
			model.addAttribute("message", message);
			mv = new ModelAndView("jporder/payerror");
			return mv;
			
		}else if(!flightOrderDTO.getJpOrderUser().getUserId().equals(userId)){
			flightOrderDTO=null;
			message="操作有误，订单不属于当前用户";
			HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>操作有误，订单不属于当前用户，订单信息："+JSON.toJSONString(flightOrderDTO));
			model.addAttribute("message", message);
			mv = new ModelAndView("jporder/payerror");
			return mv;
			
		}else if(flightOrderDTO.getStatus().equals(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_CANCEL))){
			message="订单已取消";
			HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>订单已取消，订单信息："+JSON.toJSONString(flightOrderDTO));
			model.addAttribute("message", message);
			model.addAttribute("order", flightOrderDTO);
			mv = new ModelAndView("jporder/payerror");
			return mv;
		}
		
		double price=(Double.parseDouble(payAmount)*100-flightOrderDTO.getFlightPriceInfo().getPayAmount()*100)/100;
		HgLogger.getInstance().info("zhaows", "支付平台生成订单,价格对比："+price);
		if(price!=0||flightOrderDTO.getPayStatus()!=Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY)){
			HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>支付宝页面跳转失败");
			//支付失败页面跳转
			return new ModelAndView("jporder/payerror");
		}
		//获取联系人手机号,支付成功后，发短信用
		String mobile = flightOrderDTO.getJpLinkInfo().getLinkTelephone();
		//删除占用状态没有使用的卡券
		OrderRefundCommand command = new OrderRefundCommand();
		command.setOrderId(out_trade_no);
		double balanceM=0.0;
		try {
			//如果使用余额支付
			if(StringUtils.isNotBlank(balance)){
				balanceM=Double.parseDouble(balance);
				if(balanceM>0.00){
					/**********更新当前订单 已经占用的余额快照为“作废”状态*************/
					updateAccount(userId,out_trade_no);
					//占用账户余额快照
					consumptionAccount(userId,balanceM,out_trade_no,AccountConsumeSnapshot.STATUS_ZY,false);
				}
				couponService.orderRefund(command);
				HgLogger.getInstance().info("zhaows","删除卡券中的与之相关联的订单号成功："+ JSON.toJSONString(command));
			}
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","删除卡券中的与之相关联的订单号失败！！"+ HgLogger.getStackTrace(e));
			//支付失败页面跳转
			return new ModelAndView("jporder/payerror");
		}
		//如果使用卡券支付
		double couponPrice=0.0;
		if(StringUtils.isNotBlank(id)){
			try{
				//如果使用卡券，卡券面值
				couponPrice=Double.parseDouble(request.getParameter("couponPrice"));
				//如果用户选择了卡券， 用户点击确认支付后保存一个订单快照

				HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>用户选择卡券ID"+id);
				if (StringUtils.isNotBlank(id)) {
					String[] ids=id.split(",");
					BatchConsumeCouponCommand consumeCouponCommand = new BatchConsumeCouponCommand();
					consumeCouponCommand.setCouponIds(ids);
					consumeCouponCommand.setOrderId(out_trade_no);
					consumeCouponCommand.setOrderType(ConsumeOrderSnapshot.ORDERTYPE_JP);
					consumeCouponCommand.setPayPrice(couponPrice);
					consumeCouponCommand.setUserID(userId);
					//占用卡券，保存订单快照
					couponService.occupyCoupon(consumeCouponCommand);
					HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>占用卡券完成");
				}
			}catch(Exception e){
				e.printStackTrace();
				HgLogger.getInstance().info("renfeng", "AlipayCtl>>pay>>用户选择的卡券不能使用");
				return new ModelAndView("jporder/payerror");
			}
		}
		UpdateJPOrderStatusCommand updateJPOrderStatusCommands=new UpdateJPOrderStatusCommand();
		updateJPOrderStatusCommands.setDealerOrderCode(out_trade_no);
		updateJPOrderStatusCommands.setId(flightOrderDTO.getId());
		//订单支付价格
		double payPrice=Double.parseDouble(total_fee);
		payPrice=payPrice-balanceM;

		if(payPrice<=0.00){
			payPrice=0.00;
			updateJPOrderStatusCommands.setPayCash(0.00);//设置现金支付价格
		}else{
			updateJPOrderStatusCommands.setPayCash((double)(int)(payPrice/flightOrderDTO.getPassengers().size()));//设置现金支付价格
		}
		if(balanceM<=0.00){
			updateJPOrderStatusCommands.setPayBalance(0.00);//设置余额支付价格
		}else{
			updateJPOrderStatusCommands.setPayBalance((double)(int)(balanceM/flightOrderDTO.getPassengers().size()));//设置余额支付价格
		}
		HgLogger.getInstance().info("zhaows", "支付平台生成订单,修改实际支付金额参数"+JSON.toJSONString(updateJPOrderStatusCommands));
		jpService.updateOrderStatus(updateJPOrderStatusCommands);// 修改入实际支付金额
		//如果订单实际金额减去优惠券面值、余额后大于0，则仍然去调用支付宝支付减去优惠之后的价格；相反则直接跳转支付成功页面
		if(payPrice>0){
			//跳转支付宝页面
			try{
				AliProduct product = new AliProduct();
				product.setOut_trade_no(out_trade_no);
				product.setSubject(URLDecoder.decode(subject.replaceAll("\\$", "%"), "utf-8"));
				product.setTotal_fee(payPrice+"");
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
				HgLogger.getInstance().info("liusong", "AlipayCtl>>pay>>支付宝页面跳转失败");
				return new ModelAndView("jporder/payerror");
			}
			return mv;
		}else{
			try {
				//如果使用账户余额：从当前用户的帐号余额中扣除使用的余额
				if(balanceM>0){
					consumptionAccount(userId,balanceM,out_trade_no,AccountConsumeSnapshot.STATUS_SY,true);
				}
				//如果使用卡券，：修改卡券的状态
				if(StringUtils.isNotBlank(id)){
					BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
					couponCommand.setCouponIds(id.split(","));
					couponCommand.setOrderId(out_trade_no);
					couponCommand.setPayPrice(couponPrice);
					couponService.confirmConsumeCoupon(couponCommand);
				}
				//修改订单状态:支付成功，出票处理中
				updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_DEALING,JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC,out_trade_no,"","");
				//创建分销订单
				BookGNFlightCommand bookGNFlightCommand=new BookGNFlightCommand();
				bookGNFlightCommand.setOrderNO(out_trade_no);
				JPBookOrderGNDTO jPBookOrderGNDTO=jpService.orderCreate(bookGNFlightCommand);
				// 支付成功后发送出票请求
				JPPayOrderGNCommand jPPayOrderGNCommand = new JPPayOrderGNCommand();
				jPPayOrderGNCommand.setDealerOrderId(out_trade_no);
				//jPPayOrderGNCommand.setTotalPrice(payPrice);
				jPPayOrderGNCommand.setPayPlatform(jPBookOrderGNDTO.getPriceGNDTO().getPayType());
				jPPayOrderGNCommand.setPayPlatform(1);
				jPPayOrderGNCommand.setTotalPrice(jPBookOrderGNDTO.getPriceGNDTO().getTotalPrice());//给分销传分销支付价格
				HgLogger.getInstance().info("renfeng","JPController>>请求出票：" + JSON.toJSONString(jPPayOrderGNCommand));
				// 请求出票
				jpService.askOrderTicket(jPPayOrderGNCommand);
				// 发放卡券：订单满
				HgLogger.getInstance().info("renfeng","JPController>>createClientNotify--发放卡券：订单满");
				CreateCouponCommand cmd = new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				cmd.setPayPrice(flightOrderDTO.getFlightPriceInfo().getPayAmount());
				cmd.setMobile(flightOrderDTO.getJpOrderUser().getMobile());
				cmd.setUserId(flightOrderDTO.getJpOrderUser().getUserId());
				cmd.setLoginName(flightOrderDTO.getJpOrderUser().getLoginName());
				CouponMessage baseAmqpMessage = new CouponMessage();
				baseAmqpMessage.setMessageContent(cmd);
				String issue = SysProperties.getInstance().get("issue_on_full");
				int type = 0;
				if (StringUtils.isBlank(issue))
					type = 2;// 默认为2
				else {
					type = Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				HgLogger.getInstance().info("renfeng","JPController>>createClientNotify--发放卡券：订单满"+JSON.toJSONString(baseAmqpMessage));
				template.convertAndSend("zzpl.order", baseAmqpMessage);

			}catch(Exception e){
				e.printStackTrace();
				HgLogger.getInstance().info("renfeng","JPController>>createClientNotify--出票失败"+ HgLogger.getStackTrace(e));
				//修改订单状态
				updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_FAIL,JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT,out_trade_no,"","");
				return new ModelAndView("jporder/payerror");
			}

			// 发送短信通知下单人,商旅分销已经有调用过发送短信的方法接口了		
			String smsContent = ("【"+SysProperties.getInstance().get("sms_sign")+"】您的订单"+ out_trade_no+ "，已经购买成功，出票完成后将短信通知，请您放心安排行程。客服电话：010—65912283。");
			HgLogger.getInstance().info("renfeng", "机票支付--支付平台--卡券、余额支付成功，给用户发送短信-->:" + mobile + " smsContent:" + smsContent);

			model.addAttribute("orderId", flightOrderDTO.getId());
			return 	new ModelAndView("jporder/success");

		}
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

	/**
	 * @throws AccountException
	 * @方法功能说明：消费账户余额
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-7下午1:43:27
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void consumptionAccount(String userID,double accountBalance,String orderNo,int stutas,boolean consumption) throws AccountException{
		HgLogger.getInstance().info("zhaows","jP--->consumptionAccount=" + userID+"="+accountBalance+"="+orderNo+"="+stutas);
		AccountCommand accountCommand=new AccountCommand();
		accountCommand.setCommandId(userID);//余额账户ID
		accountCommand.setCurrentMoney(accountBalance);//本次消费金额
		accountCommand.setConsumption(consumption);
		AccountConsumeSnapshotCommand snapshotCommand=new AccountConsumeSnapshotCommand();
		snapshotCommand.setOrderType(AccountConsumeSnapshot.ORDERTYPE_JP);
		snapshotCommand.setOrderId(orderNo);
		snapshotCommand.setStatus(stutas);
		snapshotCommand.setPayPrice(accountBalance);
		//snapshotCommand.setDetail("暂无详细");
		accountCommand.setAccountConsumeSnapshotCommand(snapshotCommand);
		accountService.consumptionAccount(accountCommand);
	}

	/**
	 *
	 * @方法功能说明：修改订单状态
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-24下午2:57:13
	 * @参数：@param status
	 * @参数：@param payStatus
	 * @参数：@param dealerOrderId
	 * @return:void
	 * @throws
	 */

	public void updateOrderStatus(String status,String payStatus,String dealerOrderId,String buyer_email,String trade_no){
		HgLogger.getInstance().info("zhaows","updateOrderStatus--修改订单状态=" + status+"="+payStatus+"="+dealerOrderId+"="+buyer_email+"="+trade_no);
		try {
			UpdateJPOrderStatusCommand updateJPOrderStatusCommand=new UpdateJPOrderStatusCommand();
			updateJPOrderStatusCommand.setBuyerEmail(buyer_email);//支付帐号
			updateJPOrderStatusCommand.setPayTradeNo(trade_no);//支付订单号
			updateJPOrderStatusCommand.setDealerOrderCode(dealerOrderId);
			updateJPOrderStatusCommand.setStatus(Integer.parseInt(status));
			updateJPOrderStatusCommand.setPayStatus(Integer.parseInt(payStatus));
			//updateJPOrderStatusCommand.setPayCash(0.00);//设置现金支付价格
			HgLogger.getInstance().info("zhaows","JPController>>createClientNotify>修改订单状态"+ JSON.toJSONString(updateJPOrderStatusCommand));
			jpService.updateOrderStatus(updateJPOrderStatusCommand);// 修改入库订单状态和订单的支付状态
			HgLogger.getInstance().info("zhaows","JPController>>createClientNotify>入库订单的订单号：" + dealerOrderId);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhaows","updateOrderStatus---修改订单状态失败");
		}
	}

	/**
	 * 通知
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
	@RequestMapping(value="callback")
	public ModelAndView callback(HttpServletRequest request,PrintWriter out,Model model) throws Exception{
		Map<String, String> params = getAllReqParam(request);
		AlipayWapTradeCreateDirectRes obj = new AlipayWapTradeCreateDirectRes();
		obj.setSign(params.get("sign"));
		obj.setTradeStatus(params.get("result"));
		obj.setTradeNo(params.get("trade_no"));
		obj.setSecId(params.get("sign_type"));
		obj.setOutTradeNo(params.get("out_trade_no"));
		obj.setRequestToken(params.get("request_token"));
		//查询订单
		FlightOrderQO flightOrderQO=new FlightOrderQO();
		flightOrderQO.setOrderNO(params.get("out_trade_no"));
		flightOrderQO.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
		FlightOrderDTO flightOrderDTO=jpService.queryOrderDetail(flightOrderQO);
		if (flightOrderDTO != null) {

			//项目名称
			String start = flightOrderDTO.getFlightBaseInfo().getOrgCity();// 获取机票的起飞城市
			String end = flightOrderDTO.getFlightBaseInfo().getDstCity();// 获取机票的到达城市
			Integer num = flightOrderDTO.getPassengers().size();// 获取机票乘客的数量
			StringBuilder subject = new StringBuilder("飞机票 [");
			subject.append(start).append("-").append(end).append("]*").append(num);

			model.addAttribute("subject", subject.toString());
			model.addAttribute("orderId",  flightOrderDTO.getId());
		} else {
			HgLogger.getInstance().info("liusong","JPOrderCtrl->pay->queryOrder[根据订单号查询订单失败！查询的QO]:"+ JSON.toJSONString(flightOrderQO));

		}

		model.addAttribute("orderNo", params.get("out_trade_no"));

		if(AlipayNotify.verifyReturn(params)){//验证成功
			return new ModelAndView("jporder/success");
		}else{//危险
			return new ModelAndView("jporder/payerror");
		}
	}

	@RequestMapping("payerror")
	public ModelAndView error(String message) {
		ModelAndView mav = new ModelAndView("jporder/payerror");
		mav.addObject("message",message);
		return mav;
	}
	@RequestMapping("/error")
	public ModelAndView errorjsp() {
		ModelAndView mav = new ModelAndView("jporder/error");
		return mav;
	}
	@RequestMapping("success")
	public ModelAndView success() {
		ModelAndView mav = new ModelAndView("jporder/success");
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
	@SuppressWarnings("rawtypes")
	private Map<String, String> getAllReqParam(HttpServletRequest request) {
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
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
	@RequestMapping("/notify")
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

		FlightOrderQO qo = new FlightOrderQO();
		qo.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_PAY_WAIT));
		qo.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_NO_PAY));
		qo.setOrderNO(out_trade_no);
		qo.setOrderType(FlightOrder.ORDERTYPE_NORMAL);
		HgLogger.getInstance().info("zhaows", "jpOrder---qo"+JSON.toJSONString(qo));
		FlightOrderDTO jpOrder = jpService.queryUnique(qo);
		HgLogger.getInstance().info("zhaows", "jpOrder---查询机票订单"+JSON.toJSONString(jpOrder));
		if (jpOrder == null) {
			HgLogger.getInstance().info("zhangka", "待支付订单查询失败，dealerOrderCode="+out_trade_no);
			return;
		} else {
			jpOrder.setBuyerEmail(buyer_email);//设置付款支付宝账号
			HgLogger.getInstance().info("zhangka", "ApiController->notify->jpOrder->ID="+jpOrder.getId());
		}
		HgLogger.getInstance().info("zhangka", "查看是否继续往下执行");
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
				/*CreateCouponCommand cmd=new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				cmd.setPayPrice(jpOrder.getFlightPriceInfo().getPayAmount());
				cmd.setMobile(jpOrder.getJpOrderUser().getMobile());
				cmd.setUserId(jpOrder.getJpOrderUser().getUserId());
				cmd.setLoginName(jpOrder.getJpOrderUser().getLoginName());
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
				template.convertAndSend("zzpl.order",baseAmqpMessage);
				 */
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
			UpdateJPOrderStatusCommand command=new UpdateJPOrderStatusCommand();
			command.setId(jpOrder.getId());
			command.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
			command.setPayTradeNo(trade_no);//支付订单号
			jpService.updateOrderStatus(command);
			HgLogger.getInstance().info("zhangka", "ApiController->notify->支付宝异步通知验证失败AlipayNotify.verifyNotify="+verifyNotify);
			return;
		}

		//如果 支付成功
		if ((JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC).equals(String.valueOf(jpOrder.getPayStatus())) && jpOrder.getJpOrderUser() != null) {

			// 如果支付成功，则同时修改与该订单相关的卡券的状态为已消费状态
			HslCouponQO hslCouponQO = new HslCouponQO();
			hslCouponQO.setOrderId(jpOrder.getOrderNO());
			// 根据订单号查询一个与订单绑定的卡券快照
			HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->根据订单号查询一个与订单绑定使用的卡券hslCouponQO"+ JSON.toJSONString(hslCouponQO));

			List<CouponDTO> coupondtos = couponService.queryList(hslCouponQO);

			HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->根据订单号查询一个与订单绑定使用的卡券"+ JSON.toJSONString(coupondtos));
			String couponId="";
			if(coupondtos!=null&&coupondtos.size()>0){
				//List<String> ids=new ArrayList<String>();
				for (CouponDTO coupondto : coupondtos) {
					if(coupondto!=null){
						//ids.add(coupondto.getId());
						couponId+=coupondto.getId()+",";
					}
				}
				// 修改卡券的状态
				BatchConsumeCouponCommand couponCommand = new BatchConsumeCouponCommand();
				couponCommand.setCouponIds(couponId.split(","));
				couponCommand.setOrderId(jpOrder.getOrderNO());
				couponCommand.setPayPrice(jpOrder.getFlightPriceInfo().getPayAmount());
				HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->机票出票后确认使用卡券command："+JSON.toJSONString(couponCommand));
				try {
					couponService.confirmConsumeCoupon(couponCommand);
				} catch (Exception e) {
					e.printStackTrace();
					HgLogger.getInstance().error("zhaows","机票支付--支付平台--异步通知-->机票出票后确认使用卡券出现异常："+ e.getMessage()+ HgLogger.getStackTrace(e));
				}
			}
			//请求出票
			try {
				/*-------通知支付记录平台，创建该订单的支付记录--Start--***/
			/*	String httpUrl = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_httpUrl"))?SysProperties.getInstance().get("payRecord_httpUrl"):"http://127.0.0.1:8080/pay-record-api/api";
				String modulus = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_modulus"))?SysProperties.getInstance().get("payRecord_modulus"):
						"124772110688646963986080649555964103539422188358187828168937575305950354245761868431506632387775213795975959903801685687213725513485979867335122668713621985468566123166884763277431607358336804344573792493151032125487645367798247528849276077786351681288665334101843452187400145922866688656369859774560299619129";
				String public_exponent = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_public_exponent"))?SysProperties.getInstance().get("payRecord_public_exponent"):
						"65537";
				PayRecordApiClient client;
				try {
					client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
					CreateAirPayReocrdCommand command = new CreateAirPayReocrdCommand();
					command.setFromProjectCode(1000);
					command.setPayPlatform(1);
					command.setRecordType(1);
					StringBuffer names=new StringBuffer();
					for(PassengerGNDTO passengerGNDTO:jpOrder.getPassengers()){
						if(StringUtils.isNotBlank(names.toString())){
							names.append("|"+passengerGNDTO.getPassengerName());
						}else{
							names.append(passengerGNDTO.getPassengerName());
						}
					}
					command.setBooker(names.toString());
					command.setDealerOrderNo(jpOrder.getOrderNO());
					command.setPaySerialNumber(jpOrder.getPayTradeNo());
					command.setStartCity(jpOrder.getFlightBaseInfo().getOrgCity());
					command.setDestCity(jpOrder.getFlightBaseInfo().getDstCity());
					command.setPayAccountNo(jpOrder.getBuyerEmail());
					command.setIncomeMoney(jpOrder.getFlightPriceInfo().getPayCash()*jpOrder.getPassengers().size());
					command.setFromClientType(1);
					CreateAirPayRecordResponse recordResponse = client.send(command, CreateAirPayRecordResponse.class);
					HgLogger.getInstance().info("zhaows", "机票--支付平台--异步通知-->:recordResponse" + JSON.toJSONString(recordResponse));
				}catch (Exception e){
					e.printStackTrace();
					HgLogger.getInstance().info("chenxy","记录支付记录出现问题-->"+HgLogger.getStackTrace(e));
				}*/
				/**-------通知支付记录平台，创建该订单的支付记录--End--*/
				//如果使用账户余额
				AccountConsumeSnapshotQO accountConsumeSnapshotQO=new AccountConsumeSnapshotQO();
				accountConsumeSnapshotQO.setOrderId(jpOrder.getOrderNO());//根据订单id查询余额订单快照
				HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知--账户余额>参数"+JSON.toJSONString(qo));
				AccountConsumeSnapshotDTO accDTO=accountConsumeSnapshotService.queryUnique(accountConsumeSnapshotQO);
				HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知--账户余额>返回DTO"+JSON.toJSONString(accDTO));
				//使用账户余额快照
				if(accDTO!=null&&accDTO.getPayPrice()>0){
					consumptionAccount(accDTO.getAccount().getUser().getId(),accDTO.getPayPrice(),jpOrder.getOrderNO(),AccountConsumeSnapshot.STATUS_SY,true);
				}
				// 修改入库订单状态和订单的支付状态
				Integer status = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING);//此时订单状态为出票处理中
				Integer payStatus = Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC);//此时订单的支付状态已支付状态
				UpdateJPOrderStatusCommand updateJPOrderStatusCommand=new UpdateJPOrderStatusCommand();
				updateJPOrderStatusCommand.setDealerOrderCode(jpOrder.getOrderNO());
				updateJPOrderStatusCommand.setStatus(status);
				updateJPOrderStatusCommand.setPayStatus(payStatus);
				updateJPOrderStatusCommand.setBuyerEmail(buyer_email);//支付帐号
				updateJPOrderStatusCommand.setPayTradeNo(trade_no);//支付订单号
				//updateJPOrderStatusCommand.setPayCash(0.00);//设置现金支付价格
				jpService.updateOrderStatus(updateJPOrderStatusCommand);
				//通知分销下单
				BookGNFlightCommand bookGNFlightCommand=new BookGNFlightCommand();
				bookGNFlightCommand.setOrderNO(jpOrder.getOrderNO());
				JPBookOrderGNDTO jpBookOrderGNDTO=jpService.orderCreate( bookGNFlightCommand);//创建分销订单
				//更新到数据库（支付状态和出票状态的修改可以不在同一个事务里面）
				JPPayOrderGNCommand jPPayOrderGNCommand = new JPPayOrderGNCommand();
				jPPayOrderGNCommand.setDealerOrderId(jpOrder.getOrderNO());
				jPPayOrderGNCommand.setPayPrice(jpOrder.getFlightPriceInfo().getPayAmount());//支付总价格
				jPPayOrderGNCommand.setBuyerEmail(buyer_email);//支付帐号
				jPPayOrderGNCommand.setPayTradeNo(trade_no);//支付订单号
				// 易行支持的支付方式
				jPPayOrderGNCommand.setPayPlatform(jpBookOrderGNDTO.getPriceGNDTO().getPayType());
				jPPayOrderGNCommand.setTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getTotalPrice());//给分销传分销支付价格
				HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->发送出票请求："+ JSON.toJSONString(jPPayOrderGNCommand));
				// 请求出票
				jpService.askOrderTicket(jPPayOrderGNCommand);


				HgLogger.getInstance().info("zhaows","机票支付--支付平台--异步通知-->发放卡券：订单满");
				// 发放卡券：订单满
				CreateCouponCommand cmd = new CreateCouponCommand();
				cmd.setSourceDetail("订单满送");
				cmd.setPayPrice(jpOrder.getFlightPriceInfo().getPayAmount());
				cmd.setMobile(jpOrder.getJpOrderUser().getMobile());
				cmd.setUserId(jpOrder.getJpOrderUser().getUserId());
				cmd.setLoginName(jpOrder.getJpOrderUser().getLoginName());
				CouponMessage baseAmqpMessage = new CouponMessage();
				baseAmqpMessage.setMessageContent(cmd);
				String issue = SysProperties.getInstance().get("issue_on_full");
				int type = 0;
				if (StringUtils.isBlank(issue))
					type = 2;// 默认为2
				else {
					type = Integer.parseInt(issue);
				}
				baseAmqpMessage.setType(type);
				baseAmqpMessage.setSendDate(new Date());
				baseAmqpMessage.setArgs(null);
				HgLogger.getInstance().info("zhaows","删除卡券中的与之相关联的订单号成功--->："+ JSON.toJSONString(baseAmqpMessage));
				template.convertAndSend("zzpl.order", baseAmqpMessage);
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().info("renfeng","JPController>>createClientNotify--请求出票失败"+ HgLogger.getStackTrace(e));

				//支付异常时，删除占用状态的卡券
				OrderRefundCommand command = new OrderRefundCommand();
				command.setOrderId(out_trade_no);
				try {
					couponService.orderRefund(command);
					HgLogger.getInstance().info("zhaows","删除卡券中的与之相关联的订单号成功："+ JSON.toJSONString(command));
				} catch (CouponException e1) {
					e.printStackTrace();
					HgLogger.getInstance().error("zhaows","删除卡券中的与之相关联的订单号失败！！"+ HgLogger.getStackTrace(e));
				}
				//修改订单状态：出票失败，待退款
				updateOrderStatus(JPOrderStatusConstant.SHOP_TICKET_FAIL,JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT,out_trade_no,buyer_email,trade_no);

			}

			//发送短信通知用户付款成功
			try {
				String mobile = jpOrder.getJpOrderUser().getMobile();
				String smsContent = "【"+SysProperties.getInstance().get("sms_sign")+"】您的订单" + jpOrder.getOrderNO() + "，已经购买成功，出票完成后将短信通知，请您放心安排行程。查询订单请点击http://mzzpl.ply365.com/user/jpos，客服电话：010—65912283。";
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
	 * @param request
	 * */
	@RequestMapping(value = "/getCouponUsed")
	@ResponseBody
	private String getCouponUsed(HttpServletRequest request) {
		String message = "";// 标识卡券是否可用的字段变量
		try {
			// 查询用户选择的卡券是否可使用
			String userId = this.getUserId(request);
			String useCouponIDs = request.getParameter("useCouponIDs");
			String dealerOrderId = request.getParameter("dealerOrderId");// 获取订单号
			Double payPrice = Double.parseDouble(request.getParameter("payPrice"));// 获取实际价格
			String[] ids= useCouponIDs.split(",");
			BatchConsumeCouponCommand consumeCouponCommand = null;
			consumeCouponCommand = new BatchConsumeCouponCommand();
			consumeCouponCommand.setCouponIds(ids);
			consumeCouponCommand.setOrderId(dealerOrderId);
			consumeCouponCommand.setPayPrice(payPrice);
			consumeCouponCommand.setUserID(userId);
			// 如果用户选择了多个卡券，那么每循环出一个卡券id就要去做一次判断，判断该卡券是否可用
			boolean flag = couponService.checkCoupon(consumeCouponCommand);
			if (flag) {
				HgLogger.getInstance().info("liusong", "卡券可用");
				message = "success";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			HgLogger.getInstance().error("liusong","卡券不可用->JpController->payOrder->exception:"+ HgLogger.getStackTrace(ex));
			//return ex.getMessage();
		}
		return message;
	}
	/**
	 * 修改占用状态的账户余额
	 * @param userId
	 * @param OrderNo
	 */
	public  void updateAccount(String userId,String OrderNo){
		AccountConsumeSnapshotQO qo = new AccountConsumeSnapshotQO();
		AccountQO accountQO = new AccountQO();
		accountQO.setUserID(userId);
		AccountDTO dto = accountService.queryUnique(accountQO);
		qo.setAccountId(dto.getId());
		qo.setAccountstatus(AccountConsumeSnapshot.STATUS_ZY);
		qo.setOrderId(OrderNo);
		HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照QO--pay" + JSON.toJSONString(qo));
		List<AccountConsumeSnapshotDTO> consumeSnapshotList = accountConsumeSnapshotService.queryList(qo);
		HgLogger.getInstance().info("zhaows", "支付平台生成订单查询是否有消费快照实体--pay" + JSON.toJSONString(consumeSnapshotList));
		String ids="";
		for (AccountConsumeSnapshotDTO consumeSnapshot : consumeSnapshotList) {
			ids+=consumeSnapshot.getId()+",";
		}
		accountConsumeSnapshotService.update(ids);
	}
}
