package hsl.payment.alipay.component;

import hsl.payment.alipay.config.HSLAlipayConfig;
import hsl.payment.alipay.pojo.PayRefundRequest;
import hsl.payment.alipay.pojo.PayRefundResponse;
import hsl.payment.alipay.pojo.PayRequest;
import org.apache.commons.httpclient.util.DateUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 支付宝工具
 *
 * @author zhurz
 * @since 1.7
 */
public class AliPayUtils {

	/**
	 * 构建支付请求表单HTML
	 *
	 * @param payRequest 支付请求
	 * @param config     支付宝配置
	 * @return 支付请求表单HTML
	 */
	public static String buildPayRequestFormHtml(PayRequest payRequest, HSLAlipayConfig config) {

		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();

		// 服务名称
		sParaTemp.put("service", "create_direct_pay_by_user");
		// 合作身份者ID，以2088开头由16位纯数字组成的字符串
		sParaTemp.put("partner", config.getPartner());
		// 编码格式
		sParaTemp.put("_input_charset", HSLAlipayConfig.INPUT_CHARSET);
		// 商品购买1
		sParaTemp.put("payment_type", "1");
		// 服务器异步通知路径
		sParaTemp.put("notify_url", payRequest.getNotifyUrl());
		// 页面跳转同步通知页面路径
		sParaTemp.put("return_url", payRequest.getReturnUrl());
		// 卖家支付宝帐户
		sParaTemp.put("seller_email", config.getSellerEmail());
		// 商户订单号
		sParaTemp.put("out_trade_no", payRequest.getOutTradeNo());
		// 订单名称
		sParaTemp.put("subject", payRequest.getSubject());
		// 付款金额
		sParaTemp.put("total_fee", String.format("%.2f", payRequest.getTotalFee()));
		// 订单描述
		sParaTemp.put("body", payRequest.getBody() == null ? "" : payRequest.getBody());
		// 商品展示地址
		sParaTemp.put("show_url", payRequest.getShowUrl() == null ? "" : payRequest.getShowUrl());
		// 防钓鱼时间戳
		// 若要使用请调用类文件submit中的query_timestamp函数
		sParaTemp.put("anti_phishing_key", "");
		// 客户端的IP地址
		// 非局域网的外网IP地址，如：221.0.0.1
		sParaTemp.put("exter_invoke_ip", "");

		// 建立请求
		return AlipaySubmit.buildRequest(sParaTemp, "get", "确认", config);
	}

	/**
	 * 支付宝通知
	 *
	 * @param request       支付宝通知请求
	 * @param config        支付宝配置
	 * @param checkNotifyId 是否检查通知ID（异步通知检查，同步不检查。）
	 * @return 支付宝请求参数MAP
	 */
	public static Map<String, String> alipayNotify(HttpServletRequest request, HSLAlipayConfig config, boolean checkNotifyId) {
		// 获取支付宝POST过来反馈信息
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}

		// 验证成功
		if (AlipayNotify.verify(params, config, checkNotifyId))
			return params;

		throw new RuntimeException("支付宝通知签名校验未通过");
	}


	/**
	 * 批量退款
	 *
	 * @param request 退款请求
	 * @param config  支付宝配置
	 * @return
	 * @throws Exception
	 */
	public static PayRefundResponse payRefund(PayRefundRequest request, HSLAlipayConfig config) throws Exception {

		//把请求参数打包成数组
		Map<String, String> sParaTemp = new HashMap<String, String>();
		// 服务名称
		sParaTemp.put("service", "refund_fastpay_by_platform_nopwd");
		// 合作身份者ID，以2088开头由16位纯数字组成的字符串
		sParaTemp.put("partner", config.getPartner());
		// 服务器异步通知路径
		sParaTemp.put("notify_url", request.getNotifyUrl());
		// 编码格式
		sParaTemp.put("_input_charset", HSLAlipayConfig.INPUT_CHARSET);
		// 退款批次号
		sParaTemp.put("batch_no", request.getBatchNo());
		// 退款请求时间
		sParaTemp.put("refund_date", DateUtil.formatDate(request.getRefundDate(), "yyyy-MM-dd HH:mm:ss"));
		// 退款总笔数
		sParaTemp.put("batch_num", request.getBatchNum().toString());
		// 单笔数据集
		sParaTemp.put("detail_data", request.getDetailData());

		// 批量退款调用
		return new PayRefundResponse(AlipaySubmit.sendPostInfo(sParaTemp, config));
	}

}
