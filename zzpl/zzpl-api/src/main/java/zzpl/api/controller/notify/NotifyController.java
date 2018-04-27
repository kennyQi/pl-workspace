package zzpl.api.controller.notify;

import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.controller.BaseController;
import zzpl.api.util.payback.AlipayNotify;
import zzpl.api.util.payback.AlipayService;
import zzpl.app.service.local.jp.gn.AlipayNotifyService;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.log.SystemCommunicationLogService;
import zzpl.app.service.local.push.PushService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.pojo.command.jp.plfx.JPNotifyMessageApiCommand;
import zzpl.pojo.command.log.SystemCommunicationLogNotifyCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：国内机票通知
 * @类修改者：
 * @修改日期：2015年7月16日下午3:31:26
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年7月16日下午3:31:26
 */
@Controller
public class NotifyController extends BaseController{
	@Autowired
	private AlipayNotifyService alipayNotifyService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private PushService pushService;
	@Autowired
	private FlightOrderService flightOrderService;
	@Autowired
	private SystemCommunicationLogService logService;

	@ResponseBody
	@RequestMapping(value="/notify/gn")
	public String gnFlightNotify(HttpServletRequest request){
		HgLogger.getInstance().info("cs", "【NotifyController】"+"【收到通知】");
		String value = request.getParameter("value");
		HgLogger.getInstance().info("cs", "【NotifyController】"+"value:"+JSON.toJSONString(value));
		JPNotifyMessageApiCommand jpNotifyMessageApiCommand = JSON.parseObject(value, JPNotifyMessageApiCommand.class);
		HgLogger.getInstance().info("cs", "【NotifyController】"+"jpNotifyMessageApiCommand:"+JSON.toJSONString(jpNotifyMessageApiCommand));
		SystemCommunicationLogNotifyCommand command = new SystemCommunicationLogNotifyCommand();
		command.setNotifyHost("票量分销");
		command.setNotifyContent(JSON.toJSONString(jpNotifyMessageApiCommand));
		logService.saveLocalLog(command);
		switch (Integer.valueOf(jpNotifyMessageApiCommand.getType())) {
		case 1:
			HgLogger.getInstance().info("cs", "【NotifyController】"+"【收到国内机票出票成功通知】");
			//出票成功通知
			flightOrderService.printTicket(jpNotifyMessageApiCommand);
			return "success";
		case 3:
			HgLogger.getInstance().info("cs", "【NotifyController】"+"【收到国内机票取消订单通知】");
			//取消成功通知
			String orderID = flightOrderService.cancelTicket(jpNotifyMessageApiCommand);
			HgLogger.getInstance().info("cs", "【NotifyController】"+"orderID:"+JSON.toJSONString(orderID));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder flightOrder = flightOrderService.queryUnique(flightOrderQO);
			flightOrder.setPassengers(null);
			HgLogger.getInstance().info("gk", "【NotifyController】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			HgLogger.getInstance().info("gk", "【NotifyController】【flightOrder.getStatus()】"+flightOrder.getStatus());
			HgLogger.getInstance().info("gk", "【NotifyController】【FlightOrderStatus.CANCEL_TICKET_SUCCESS)】"+FlightOrderStatus.CANCEL_TICKET_SUCCESS);
			HgLogger.getInstance().info("gk", "【NotifyController】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【NotifyController】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			//当前为用户支付
			if(flightOrder!=null&&flightOrder.getStatus()==FlightOrderStatus.CANCEL_TICKET_SUCCESS&&Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【NotifyController】【开始退款操作】");
				// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
				String batch_no = this.getBatchNo();
				// 退款请求时间
				String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
				// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
				String batch_num = "1";
				// 单笔数据集
				String detail_data = "";
				//计算出实际的退款金额
				HgLogger.getInstance().info("gk", "【请求】【退款订单信息】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				Double tureNum = flightOrder.getUserPayPrice();
				HgLogger.getInstance().info("cs", "【请求】【退款金额】"+"--------------"+tureNum+"--------------");
				//单次批量退款最多1000笔
				StringBuilder sb = new StringBuilder();
				sb.append(flightOrder.getTrade_no());
				sb.append("^");
//				sb.append(df.format(Math.abs(tureNum)));
				sb.append(tureNum);
				sb.append("^");
				sb.append("原路退款");
				detail_data = sb.toString();
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
		        sParaTemp.put("batch_no", batch_no);
		        sParaTemp.put("refund_date", refund_date);
		        sParaTemp.put("batch_num", batch_num);
		        sParaTemp.put("detail_data", detail_data);
		        HgLogger.getInstance().info("cs", "【NotifyController】【国内机票取消订单】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
				try {
					String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票取消订单】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
				} catch (Exception e) {
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票取消订单】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
				}
			}
			return "success";
		case 4:
			HgLogger.getInstance().info("cs", "【NotifyController】"+"【收到国内机票退废票通知】");
			//退费票通知
			String refundorderID = flightOrderService.refundTicket(jpNotifyMessageApiCommand);
			HgLogger.getInstance().info("cs", "【NotifyController】"+"refundorderID:"+JSON.toJSONString(refundorderID));
			FlightOrderQO flightOrderQO1 = new FlightOrderQO();
			flightOrderQO1.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder refundflightOrder = flightOrderService.queryUnique(flightOrderQO1);
			refundflightOrder.setPassengers(null);
			HgLogger.getInstance().info("gk", "【NotifyController】"+"refundflightOrder:"+JSON.toJSONString(refundflightOrder));
			HgLogger.getInstance().info("gk", "【NotifyController】【flightOrder.getPayType()】"+refundflightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【NotifyController】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			//当前为用户支付
			if(refundflightOrder!=null&&Integer.parseInt(refundflightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【NotifyController】【开始退款操作】");
				// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
				String batch_no = this.getBatchNo();
				// 退款请求时间
				String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
				// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
				String batch_num = "1";
				// 单笔数据集
				String detail_data = "";
				//计算出实际的退款金额
				HgLogger.getInstance().info("gk", "【请求】【退款订单信息】"+"flightOrder:"+JSON.toJSONString(refundflightOrder));
				Double tureNum = refundflightOrder.getUserPayPrice()-jpNotifyMessageApiCommand.getProcedures();
				HgLogger.getInstance().info("cs", "【请求】【退款金额】"+"--------------"+tureNum+"--------------");
				//单次批量退款最多1000笔
				StringBuilder sb = new StringBuilder();
				sb.append(refundflightOrder.getTrade_no());
				sb.append("^");
//				sb.append(df.format(Math.abs(tureNum)));
				sb.append(tureNum);
				sb.append("^");
				sb.append("原路退款");
				detail_data = sb.toString();
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
		        sParaTemp.put("batch_no", batch_no);
		        sParaTemp.put("refund_date", refund_date);
		        sParaTemp.put("batch_num", batch_num);
		        sParaTemp.put("detail_data", detail_data);
		        HgLogger.getInstance().info("cs", "【NotifyController】【国内机票退废票】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
				try {
					String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票退废票】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
				} catch (Exception e) {
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票退废票】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
				}
			}
			return "success";
		case 6:
			HgLogger.getInstance().info("cs", "【NotifyController】"+"【收到国内机票拒绝出票通知】");
			//拒绝出票通知
			String refuseOrderID = flightOrderService.refuseTicket(jpNotifyMessageApiCommand);
			HgLogger.getInstance().info("cs", "【NotifyController】"+"refuseOrderID:"+JSON.toJSONString(refuseOrderID));
			FlightOrderQO flightOrderQO2 = new FlightOrderQO();
			flightOrderQO2.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder refuseflightOrder = flightOrderService.queryUnique(flightOrderQO2);
			refuseflightOrder.setPassengers(null);
			HgLogger.getInstance().info("gk", "【NotifyController】"+"refuseflightOrder:"+JSON.toJSONString(refuseflightOrder));
			HgLogger.getInstance().info("gk", "【NotifyController】【flightOrder.getPayType()】"+refuseflightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【NotifyController】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			//当前为用户支付
			if(refuseflightOrder!=null&&Integer.parseInt(refuseflightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【NotifyController】【开始退款操作】");
				// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
				String batch_no = this.getBatchNo();
				// 退款请求时间
				String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
				// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
				String batch_num = "1";
				// 单笔数据集
				String detail_data = "";
				//计算出实际的退款金额
				HgLogger.getInstance().info("gk", "【请求】【退款订单信息】"+"flightOrder:"+JSON.toJSONString(refuseflightOrder));
				Double tureNum = refuseflightOrder.getUserPayPrice();
				HgLogger.getInstance().info("cs", "【请求】【退款金额】"+"--------------"+tureNum+"--------------");
				//单次批量退款最多1000笔
				StringBuilder sb = new StringBuilder();
				sb.append(refuseflightOrder.getTrade_no());
				sb.append("^");
//				sb.append(df.format(Math.abs(tureNum)));
				sb.append(tureNum);
				sb.append("^");
				sb.append("原路退款");
				detail_data = sb.toString();
				//把请求参数打包成数组
				Map<String, String> sParaTemp = new HashMap<String, String>();
		        sParaTemp.put("batch_no", batch_no);
		        sParaTemp.put("refund_date", refund_date);
		        sParaTemp.put("batch_num", batch_num);
		        sParaTemp.put("detail_data", detail_data);
		        HgLogger.getInstance().info("cs", "【NotifyController】【国内机票拒绝出票】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
				try {
					String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票拒绝出票】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
				} catch (Exception e) {
					HgLogger.getInstance().info("cs", "【NotifyController】【国内机票拒绝出票】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
				}
			}
			return "success";
		}
		return "success";
	}
	
	
	/**
	 * 接受支付宝订单退款结果
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/alipay/paybackResult")
	public String paybackResult(HttpServletRequest request){
		HgLogger.getInstance().info("cs", "【接收到退款通知】");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		if(requestParams==null||requestParams.isEmpty()){
			HgLogger.getInstance().warn("zhuxy", "【接收到退款通知】ApiController->paybackResult->params is null or empty");
			return "fail";
		}
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
			sb.append(name).append("=").append(valueStr).append("&");
		}
		
		HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->params:" + sb.toString());
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->result_details:" + result_details);

		if (AlipayNotify.verify(params)) {//验证成功
			//修改订单状态并且激活已使用的卡券
			alipayNotifyService.refundSuccess(result_details);
			return "success";
		} else {//验证失败
			HgLogger.getInstance().info("zhuxy", "【接收到退款通知】ApiController->paybackResult->验证失败");
			return "fail";
		}
	}
	
	
	
	
	// 获取批次号
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
	private String getBatchNo() {
		String refundDate = df.format(Calendar.getInstance().getTime());
		String sequence = String.format("%012d", this.getSequence());
		return refundDate + SysProperties.getInstance().get("alipay_prefix") + sequence;
	}
	
	// 获取给定格式的日期字符串
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
	
	// 获取流水号
		public long getSequence() {
			Jedis jedis = null;
			String value = "1";
			try {
				jedis = jedisPool.getResource();
				value = jedis.get("zzpl_jp_tk_sequence");
				String date = jedis.get("zzpl_jp_tk_sequence_date");

				if (StringUtils.isBlank(value)
						|| StringUtils.isBlank(date)
						|| date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
					value = "1";
					HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法流水数字重置");
				}
				
				long v = Long.parseLong(value);
				v++;
				
				jedis.set("zzpl_jp_tk_sequence", String.valueOf(v));
				jedis.set("zzpl_jp_tk_sequence_date", String.valueOf(Calendar.getInstance().getTime().getTime()));
			} catch(RuntimeException e){
				HgLogger.getInstance().info("cs", "【NotifyController】"+"流水号方法"+ "获取流水号异常"+HgLogger.getStackTrace(e));
			} finally {
				jedisPool.returnResource(jedis);
			}

			return Long.parseLong(value);
		}
		class FlightOrderInfoQO{
			private String orderID;

			public String getOrderID() {
				return orderID;
			}

			public void setOrderID(String orderID) {
				this.orderID = orderID;
			}
		}
}
