package hsl.h5.alipay.bean;

import hsl.app.component.config.SysProperties;
import hsl.alipay.util.AlipaySubmit;
import hsl.h5.alipay.entity.AlipayWapTradeCreateDirect;
import hsl.pojo.command.line.order.ApplyToPayLineOrderCommand.PaymentFormHtmlBuilder;
import hsl.pojo.exception.ShowMessageException;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import static  hsl.h5.alipay.ctl.AlipayLineOrderCtl.*;

public class AlipayLineOrderFormBuilder implements PaymentFormHtmlBuilder {

	/**
	 * 支付宝网关地址
	 */
	public static String ALIPAY_GATEWAY_NEW = "http://wappaygw.alipay.com/service/rest.htm?";

	/**
	 * 站点地址
	 */
	private String siteAddr;

	public AlipayLineOrderFormBuilder(String siteAddr) {
		this.siteAddr = siteAddr;
	}

	/**
	 * 获得请求授权的token
	 * 
	 * @param obj
	 * @return
	 */
	private String getReqAuthonToken(AlipayWapTradeCreateDirect obj) {
		String req_dataToken = "<direct_trade_create_req><notify_url>"
				+ (obj.getNotifyUrl() + SUFFIX)
				+ "</notify_url>"
				+ "<call_back_url>"
				+ (siteAddr + obj.getCallBackUrl() + SUFFIX)
				+ "</call_back_url>"
				+ "<seller_account_name>"
				+ obj.getSellerAccountName()
				+ "</seller_account_name>"
				+ "<out_trade_no>"
				+ obj.getOutTradeNo()
				+ "</out_trade_no>"
				+ "<subject>"
				+ obj.getSubject()
				+ "</subject>"
				+ "<total_fee>"
				+ obj.getTotalFee()
				+ "</total_fee>"
				+ "<merchant_url>"
				+ (siteAddr + obj.getMerchantUrl() + SUFFIX + "?dealerOrderNo=" + obj
						.getOutTradeNo()) + "</merchant_url>"
				+ "</direct_trade_create_req>";
		return req_dataToken;
	}

	private AlipayWapTradeCreateDirect getReqBean(AliProduct product) {
		AlipayWapTradeCreateDirect obj = new AlipayWapTradeCreateDirect();
		// 返回格式
		obj.setFormat("xml");
		obj.setV("2.0");
		// 请求号 必填，须保证每次请求都是唯一
		obj.setReqId(System.currentTimeMillis() + RandomStringUtils.randomNumeric(10));
		// req_data详细信息
		// 服务器异步通知页面路径
		obj.setNotifyUrl(SysProperties.getInstance().get("alipay_notify_url"));
		// 页面跳转同步通知页面路径
		obj.setCallBackUrl(SysProperties.getInstance().get("alipay_callback_url"));
		// 操作中断返回地址
		obj.setMerchantUrl(SysProperties.getInstance().get("alipay_merchant_url"));
		// 卖家支付宝帐户
		obj.setSellerAccountName(SysProperties.getInstance().get("alipay_seller_mail"));
		// 商户订单号
		obj.setOutTradeNo(product.getOut_trade_no());
		// 订单名称
		obj.setSubject(product.getSubject());
		// 付款金额
		obj.setTotalFee(product.getTotal_fee());
		obj.setReqDate(new Date());
		return obj;
	}

	public static String getNodeText(Node node) {
		if (node != null)
			return node.getText();
		return null;
	}

	@Override
	public String buildAlipayFormHtml(String out_trade_no, String subject, double total_fee) throws Exception {
		
		// 实际支付金额必须大于0
		if (total_fee <= 0)
			throw new ShowMessageException("无需支付");

		AliProduct product = new AliProduct();
		product.setOut_trade_no(out_trade_no);
		product.setSubject(URLDecoder.decode(subject.replaceAll("\\$", "%"), "utf-8"));
		product.setTotal_fee(String.valueOf(total_fee));
		// 调用授权接口alipay.wap.trade.create.direct获取授权码token
		AlipayWapTradeCreateDirect obj = getReqBean(product);

		// 请求业务参数详细
		String req_dataToken = getReqAuthonToken(obj);

		Map<String, String> sParaTempToken = new HashMap<String, String>();
		sParaTempToken.put("service", obj.getService());
		sParaTempToken.put("partner", obj.getPartner());
		sParaTempToken.put("_input_charset", SysProperties.getInstance().get("alipay_charset"));
		sParaTempToken.put("sec_id", obj.getSecId());
		sParaTempToken.put("format", obj.getFormat());
		sParaTempToken.put("v", obj.getV());
		sParaTempToken.put("req_id", obj.getReqId());
		sParaTempToken.put("req_data", req_dataToken);

		System.out.println("req_dataToken------------------>>");
		System.out.println(req_dataToken);

		// 授权接口
		Long startTime = System.currentTimeMillis();
		String sHtmlTextToken = AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, "", "", sParaTempToken);
		obj.setTimes(System.currentTimeMillis() - startTime);
		sHtmlTextToken = URLDecoder.decode(sHtmlTextToken, SysProperties.getInstance().get("alipay_charset"));
		String xml = AlipaySubmit.getWapTradeCreateDirectResStr(sHtmlTextToken);
		Document document = DocumentHelper.parseText(xml);
		String code = getNodeText(document.selectSingleNode("//err/code"));
		if (StringUtils.isNotBlank(code)) {
			// 报错 <?xml version="1.0" encoding="utf-8"?>
			// <err><code>0005</code><sub_code>0005</sub_code><msg>partnerillegal</msg><detail>合作伙伴没有开通接口访问权限</detail></err>
			obj.setResErrCode(code);
			obj.setResErrSubCode(getNodeText(document.selectSingleNode("//err/sub_code")));
			obj.setResErrMsg(getNodeText(document.selectSingleNode("//err/msg")));
			obj.setResErrDetail(getNodeText(document.selectSingleNode("//err/detail")));
		} else {
			// 请求成功 <?xml version="1.0" encoding="utf-8"?>
			// <direct_trade_create_res><request_token>20100830e8085e3e0868a466b822350ede5886e8</request_token></direct_trade_create_res>
			obj.setRequestToken(getNodeText(document.selectSingleNode("//direct_trade_create_res/request_token")));
		}
		
		// 根据授权码token调用交易接口alipay.wap.auth.authAndExecute
		Map<String, String> sParaTemp = new HashMap<String, String>();
		sParaTemp.put("service", "alipay.wap.auth.authAndExecute");
		sParaTemp.put("partner", obj.getPartner());
		sParaTemp.put("_input_charset", SysProperties.getInstance().get("alipay_charset"));
		sParaTemp.put("sec_id", obj.getSecId());
		sParaTemp.put("format", obj.getFormat());
		sParaTemp.put("v", obj.getV());
		sParaTemp.put("req_data", "<auth_and_execute_req><request_token>" + obj.getRequestToken() + "</request_token></auth_and_execute_req>");

		// 建立请求
		return AlipaySubmit.buildRequest(ALIPAY_GATEWAY_NEW, sParaTemp, "get", "确认");

	}

}
