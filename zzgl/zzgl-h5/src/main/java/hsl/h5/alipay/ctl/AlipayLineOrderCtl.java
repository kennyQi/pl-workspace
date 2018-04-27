package hsl.h5.alipay.ctl;

import com.alibaba.fastjson.JSON;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.alipay.util.AlipayNotify;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.service.local.line.LineOrderLocalService;
import hsl.domain.model.xl.order.LineOrder;
import hsl.pojo.command.coupon.ConsumeCouponCommand;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.line.HslLineOrderQO;
import hsl.spi.inter.Coupon.CouponService;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import plfx.api.client.api.v1.xl.request.command.XLPayLineOrderApiCommand;
import plfx.api.client.api.v1.xl.response.XLPayLineOrderResponse;
import plfx.api.client.base.slfx.ApiRequest;
import plfx.api.client.base.slfx.ApiResponse;
import plfx.api.client.base.slfx.SlfxApiClient;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * @类功能说明：支付宝支付网关(线路订单专用)
 * @类修改者：
 * @修改日期：2015-10-13下午2:59:52
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2015-10-13下午2:59:52
 */
@Controller
@RequestMapping("alipay")
public class AlipayLineOrderCtl {

	/**
	 * 地址后缀
	 */
	public final static String SUFFIX = "-line-order";

	@Resource
	private RabbitTemplate template;

	@Autowired
	private CouponService couponService;
	
	@Autowired
	private LineOrderLocalService lineOrderLocalService;

	@Autowired
	private SlfxApiClient slfxApiClient;

	/**
	 * @方法功能说明：接收线路订单支付宝回调
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-13下午2:39:37
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param out
	 * @参数：@return
	 * @参数：@throws Exception
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "callback" + SUFFIX)
	public String callback(HttpServletRequest request, PrintWriter out) throws Exception {
		Map<String, String> params = getAllReqParam(request);
		String ctx = getWebAppPath(request);
		String dealerOrderNo = params.get("out_trade_no");
		if (AlipayNotify.verifyReturn(params)) {
			// 验证成功
			dealerOrderNo = dealerOrderNo.split("-")[0];
			return "redirect:" + ctx + "/hslH5/line/success?dealerOrderNo=" + dealerOrderNo;
		} else {
			return "redirect:" + ctx + "/alipay/payerror" + SUFFIX;
		}
	}
	
	/**
	 * @方法功能说明：支付宝中断页面
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-16下午3:07:02
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param dealerOrderNo		订单号
	 * @参数：@return
	 * @return:ModelAndView
	 * @throws
	 */
	@RequestMapping("payerror" + SUFFIX)
	public ModelAndView error(
			HttpServletRequest request,
			@RequestParam(value = "dealerOrderNo", required = false) String dealerOrderNo) {
		if (StringUtils.isNotBlank(dealerOrderNo)) {
			try {
				dealerOrderNo = dealerOrderNo.split("-")[0];
				ConsumeCouponCommand consumeCouponCommand = new ConsumeCouponCommand();
				consumeCouponCommand.setOrderId(dealerOrderNo);
				couponService.cancelConsumeCoupon(consumeCouponCommand);
				return new ModelAndView(new RedirectView("/hslH5/line/lineOrderDetail?dealerOrderNo=" + dealerOrderNo, true));
			} catch (CouponException e1) {
				e1.printStackTrace();
			}
		}
		return new ModelAndView("error");
	}

	/**
	 * 获取webapp路径
	 * 
	 * @return
	 */
	private String getWebAppPath(HttpServletRequest request) {
		Boolean isRoot = Boolean.parseBoolean(SysProperties.getInstance().get("root"));
		String proj = request.getContextPath();
		String port = SysProperties.getInstance().get("port");
		String system = "http://" + SysProperties.getInstance().get("host") + ("80".equals(port) ? "" : ":" + port);
		if (!isRoot) {
			system += proj;
		}
		return system;
	}

	/**
	 * @方法功能说明：获取请求的数据组合成map
	 * @修改者名字：zhurz
	 * @修改时间：2015-10-13下午2:41:17
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	private Map<String, String> getAllReqParam(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map<?, ?> requestParams = request.getParameterMap();
		for (Iterator<?> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		return params;
	}

	/**
	 * 支付宝异步通知地址 web处理方式(线路订单)
	 * 
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/notify" + SUFFIX)
	public String notify(HttpServletRequest request) {
		HgLogger.getInstance().info("zhangka", "支付宝线路订单->notify->处理支付宝异步通知开始");
		//获取支付宝POST过来反馈信息
		Map<String, String> params = getAllReqParam(request);
		HgLogger.getInstance().info("zhangka", "支付宝线路订单->notify->params[post参数]"+JSON.toJSONString(params));
		
		
		//商户订单号
		String out_trade_no = "";
		//支付宝交易号
		String trade_no = "";
		//交易状态
		String trade_status = "";
		//付款支付宝账号
		String buyer_email = "";
		
		try {

//			doc_notify_data
//			<notify>
//				<payment_type>8</payment_type>
//				<subject>“鸟岛一日游”预订金</subject>
//				<trade_no>2015102700001000410027886814</trade_no>
//				<buyer_email>1058118328@qq.com</buyer_email>
//				<gmt_create>2015-10-27 13:35:54</gmt_create>
//				<notify_type>trade_status_sync</notify_type>
//				<quantity>1</quantity>
//				<out_trade_no>BA27133539010000</out_trade_no>
//				<notify_time>2015-10-28 13:59:15</notify_time>
//				<seller_id>2088611359544680</seller_id>
//				<trade_status>TRADE_SUCCESS</trade_status>
//				<is_total_fee_adjust>N</is_total_fee_adjust>
//				<total_fee>0.09</total_fee>
//				<gmt_payment>2015-10-27 13:35:54</gmt_payment>
//				<seller_email>ply365@ply365.com</seller_email>
//				<price>0.09</price>
//				<buyer_id>2088202607562417</buyer_id>
//				<notify_id>b6bb6266506f8b61da2eb12add4ebbba4a</notify_id>
//				<use_coupon>N</use_coupon>
//			</notify>

			boolean verifyNotify = AlipayNotify.verifyNotify(params,false);
			Document doc_notify_data = DocumentHelper.parseText(params.get("notify_data"));
			out_trade_no = doc_notify_data.selectSingleNode("//notify/out_trade_no").getText();
			trade_no = doc_notify_data.selectSingleNode("//notify/trade_no").getText();
			trade_status = doc_notify_data.selectSingleNode("//notify/trade_status").getText();
			buyer_email = doc_notify_data.selectSingleNode("//notify/buyer_email").getText();

			if (verifyNotify) {
				// 是否是尾款
				boolean isBalance = out_trade_no.split("-").length > 1;
				// 经销商订单号
				String dealerOrderNo = out_trade_no.split("-")[0];
				boolean process = lineOrderLocalService.lineOrderPaySuccess(dealerOrderNo, isBalance);
				if (process) try {
					HslLineOrderQO qo = new HslLineOrderQO();
					qo.setDealerOrderNo(dealerOrderNo);
					LineOrder lineOrder = lineOrderLocalService.queryUnique(qo);

					// 交易总金额
					String total_fee = doc_notify_data.selectSingleNode("//notify/total_fee").getText();
					// 支付成功时间(yyyy-MM-dd HH:mm:ss)
					//String gmt_payment = doc_notify_data.selectSingleNode("//notify/gmt_payment").getText();

					XLPayLineOrderApiCommand command = new XLPayLineOrderApiCommand();
					//暂时注释,分销没有这两个字段
					/*	command.setPaymentTime(DateUtil.parseDateTime(gmt_payment, "yyyy-MM-dd HH:mm:ss"));
						command.setDealerOrderNo(dealerOrderNo);*/
					command.setLineOrderID(dealerOrderNo);
					command.setPaymentType(1);
					command.setPaymentAccount(buyer_email);
					command.setPaymentName(lineOrder.getLinkInfo().getLinkName());
					command.setSerialNumber(trade_no);
					command.setPaymentAmount(Double.valueOf(total_fee));
					//command.setPaymentTime(DateUtil.parseDateTime(gmt_payment, "yyyy-MM-dd HH:mm:ss"));
					// 付款类型：1:定金；2：尾款；3全款
					if (isBalance) {
						command.setShopPayType(2);
					} else {
						if (lineOrder.needPayAll()) {
							command.setShopPayType(3);
						} else {
							command.setShopPayType(1);
						}
					}
					ApiRequest apiRequest = new ApiRequest("XLPayLineOrder", ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), command);
					// 通知分销付款增加付款信息
					ApiResponse response = slfxApiClient.send(apiRequest, XLPayLineOrderResponse.class);
					HgLogger.getInstance().info("zhurz", "通知分销付款增加付款信息返回:" + JSON.toJSONString(response));
					/********通知支付记录平台，创建该订单的支付记录--Start**********/
					/*String httpUrl = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_httpUrl")) ? SysProperties.getInstance().get("payRecord_httpUrl") : "http://127.0.0.1:8080/pay-record-api/api";
					String modulus = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_modulus")) ? SysProperties.getInstance().get("payRecord_modulus") :
							"124772110688646963986080649555964103539422188358187828168937575305950354245761868431506632387775213795975959903801685687213725513485979867335122668713621985468566123166884763277431607358336804344573792493151032125487645367798247528849276077786351681288665334101843452187400145922866688656369859774560299619129";
					String public_exponent = StringUtils.isNotBlank(SysProperties.getInstance().get("payRecord_public_exponent")) ? SysProperties.getInstance().get("payRecord_public_exponent") :
							"65537";
					PayRecordApiClient client;
					try {
						client = new PayRecordApiClient(httpUrl, modulus, public_exponent);
						CreateLinePayReocrdCommand linePayCommand = new CreateLinePayReocrdCommand();
						linePayCommand.setFromProjectCode(1000);
						linePayCommand.setPayPlatform(1);
						linePayCommand.setRecordType(1);
						StringBuffer travelerName = new StringBuffer();
						for (LineOrderTraveler traveler : lineOrder.getTravelerList()) {
							if (StringUtils.isNotBlank(travelerName.toString())) {
								travelerName.append("|" + traveler.getName());
							} else {
								travelerName.append(traveler.getName());
							}
						}
						linePayCommand.setBooker(travelerName.toString());
						//linePayCommand.setPlatOrderNo("F1001123456");
						linePayCommand.setDealerOrderNo(out_trade_no);
						//linePayCommand.setSupplierNo("T20151203");
						linePayCommand.setPaySerialNumber(trade_no);//支付宝订单号
						//linePayCommand.setStartCity(lineOrder.getLineSnapshot().getLine().getBaseInfo().getStarting());
						//linePayCommand.setDestCity(lineOrder.getLineSnapshot().getLine().getBaseInfo().getDestinationCity());
						linePayCommand.setIncomeMoney(lineOrder.getLineOrderPayInfo().getCashPrice());
						linePayCommand.setFromClientType(0);
						CreateAirPayRecordResponse recordResponse = client.send(linePayCommand, CreateAirPayRecordResponse.class);
						HgLogger.getInstance().info("zhaows", "线路--支付平台--异步通知-->:recordResponse" + JSON.toJSONString(recordResponse));
					} catch (Exception e) {
						e.printStackTrace();
						HgLogger.getInstance().info("zhaows", "线路记录支付记录出现问题-->" + HgLogger.getStackTrace(e));
					}*/
					/********通知支付记录平台，创建该订单的支付记录--end**********/

				} catch (Exception e) {
					HgLogger.getInstance().error(AlipayLineOrderCtl.class, "zhurz", "新增付款信息异常", e);
				}
			}
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "支付宝线路订单->notify->获取参数为空:" 
					+"|out_trade_no="+out_trade_no
					+"|trade_no"+trade_no
					+"|trade_status"+trade_status
					+"|buyer_email"+buyer_email
					+ HgLogger.getStackTrace(e));
			return "fail";
		}
		
		HgLogger.getInstance().info("zhangka", "out_trade_no="+out_trade_no+",trade_no="+trade_no+",trade_status="+trade_status);
		HgLogger.getInstance().info("zhangka", "支付宝线路订单->处理支付宝异步通知结束");
		return "success";
	}

}
