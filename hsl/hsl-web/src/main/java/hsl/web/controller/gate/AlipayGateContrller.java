package hsl.web.controller.gate;

import hsl.app.service.local.yxjp.YXJPOrderLocalService;
import hsl.app.service.local.yxjp.YXJPOrderPayRecordLocalService;
import hsl.domain.model.M;
import hsl.domain.model.yxjp.YXJPOrderPayRecord;
import hsl.payment.alipay.component.AliPayUtils;
import hsl.payment.alipay.config.HSLAlipayConfig;
import hsl.payment.alipay.pojo.PayNotify;
import hsl.payment.alipay.pojo.PayRefundNotify;
import hsl.pojo.qo.yxjp.YXJPOrderPayRecordQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 支付宝请求入口（专为从PC发起的支付）
 *
 * @author zhurz
 * @since 1.7
 */
@Controller
@RequestMapping(value = "/gate/alipay")
public class AlipayGateContrller {

	@Autowired
	private HSLAlipayConfig alipayConfig;
	@Autowired
	private YXJPOrderLocalService yxjpOrderLocalService;
	@Autowired
	private YXJPOrderPayRecordLocalService yxjpOrderPayRecordLocalService;

	/**
	 * 接收支付宝异步通知（易行机票PC）
	 *
	 * @param request 支付宝通知请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/yxjp-notify")
	public String alipayYXJPNotify(HttpServletRequest request) {
		try {
			// 检查并封装支付宝请求
			Map<String, String> alipayParams = AliPayUtils.alipayNotify(request, alipayConfig, true);
			PayNotify payNotify = new PayNotify(alipayParams);
			yxjpOrderLocalService.processPaySuccess(payNotify);
			// 成功返回
			return "success";
		} catch (Exception e) {
			// 处理异常
			return "fail";
		}
	}

	/**
	 * 接收支付宝同步返回（易行机票PC）
	 *
	 * @param request 支付宝返回请求
	 * @return
	 */
	@RequestMapping(value = "/yxjp-return")
	public ModelAndView alipayYXJPReturn(HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView();

		try {
			// 检查并封装支付宝请求
			Map<String, String> alipayParams = AliPayUtils.alipayNotify(request, alipayConfig, false);
			PayNotify payNotify = new PayNotify(alipayParams);

			// 查询本地支付记录
			YXJPOrderPayRecordQO qo = new YXJPOrderPayRecordQO();
			qo.setPayOrderNo(payNotify.getOutTradeNo());
			YXJPOrderPayRecord payRecord = yxjpOrderPayRecordLocalService.queryUnique(qo);
			// 获得易行机票订单ID
			String yxjpOrderId = M.getModelObjectId(payRecord.getFromOrder());

			// 重定向到易行机票订单支付成功页
			mav.getModel().put("orderId", yxjpOrderId);
			mav.setView(new RedirectView("/yxjp/success", true));

		} catch (Exception e) {
			// 失败返回
			// 重定向到易行机票订单支付失败页
			mav.setView(new RedirectView("/page/yxjp-error.html", true));
		}

		return mav;
	}

	/**
	 * 接收支付宝通知退款结果（易行机票）
	 *
	 * @param request 支付宝通知请求
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/yxjp-refund-notify")
	public String alipayYXJPRefundNotify(HttpServletRequest request) {
		try {
			// 检查并封装支付宝请求
			Map<String, String> alipayParams = AliPayUtils.alipayNotify(request, alipayConfig, true);
			PayRefundNotify refundPayNotify = new PayRefundNotify(alipayParams);
			// 处理支付宝退款通知
			yxjpOrderLocalService.processPayRefundNotify(refundPayNotify);
			// 成功返回
			return "success";
		} catch (Exception e) {
			// 处理异常
			return "fail";
		}
	}
}
