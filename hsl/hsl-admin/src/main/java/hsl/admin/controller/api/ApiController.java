package hsl.admin.controller.api;
import hsl.app.common.util.AppInfoUtils;
import hsl.app.component.config.SysProperties;
import hg.log.util.HgLogger;
import hsl.admin.alipay.services.AlipayService;
import hsl.admin.alipay.util.AlipayNotify;
import hsl.admin.controller.BaseController;
import hsl.pojo.command.OrderRefundCommand;
import hsl.pojo.command.UpdateJPOrderStatusCommand;
import hsl.pojo.dto.coupon.CouponDTO;
import hsl.pojo.dto.jp.JPOrderDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.exception.CouponException;
import hsl.pojo.qo.coupon.HslCouponQO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.pojo.util.LogFormat;
import hsl.spi.command.JPOrderCommand;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.api.jp.JPService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;
import hsl.spi.inter.user.UserService;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import slfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;

import com.alibaba.fastjson.JSON;
/**
 * @类功能说明：直销api接口
 * @类修改者：
 * @修改日期：2015年1月30日上午10:20:46
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2015年1月30日上午10:20:46
 *
 */
@Controller
@RequestMapping(value="/api")
public class ApiController extends BaseController{
	
	@Resource
	private CouponService couponService;
	
	@Resource
	private JPService jpService;
	
	@Autowired
	private JedisPool jedisPool;
	
	@Autowired
	private HslLineService hslLineService;
	
	@Autowired
	private HslLineOrderService hslLineOrderService;
	
	
	@Autowired
	private UserService userService;
	/**
	 * 出票完成通知
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ticket/notify")
	public String ticketNotify(HttpServletRequest request) {
		String msg = request.getParameter("msg");
		HgLogger.getInstance().info("zhangka", "ApiController->ticketNotify->出票完成通知：msg="+msg);
		if (StringUtils.isNotBlank(msg)) {
			JPOrderCommand command = JSON.parseObject(msg, JPOrderCommand.class);
			return jpService.orderTicketNotify(command) ? "SUCCESS" : "FAIL";
		}
		return "FAIL";
	}
	/**
	 * 分销退款成功的通知，将订单的状态改为待退款的状态。同时保存下退款金额。
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/returned/notify")
	@ResponseBody
	public String returned(HttpServletRequest request){
		HgLogger.getInstance().debug("zhuxy", newHeader("分销退款通知方法开始"));
		String dealerOrderCode = request.getParameter("dealerOrderCode");
		String status = request.getParameter("status");
		String payStatus = request.getParameter("payStatus");
		String amount = request.getParameter("amount");
		StringBuilder builder = new StringBuilder();
		builder.append("dealerOrderCode == ").append(dealerOrderCode).append(" ;");
		builder.append("status == ").append(status).append(" ;");
		builder.append("payStatus == ").append(payStatus).append(" ;");
		builder.append("amount == ").append(amount).append(" ;");
		HgLogger.getInstance().info("zhuxy", newLog("分销退款通知方法", "修改订单状态", builder.toString()));
		//判断状态
		if(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC.equals(payStatus)){
			//保存订单并修改状态
			UpdateJPOrderStatusCommand command = new UpdateJPOrderStatusCommand();
			command.setDealerOrderCode(dealerOrderCode);
			command.setBackPrice(Double.valueOf(amount));
			command.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
			command.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REFUND_SUCC));
			if(jpService.updateOrderStatus(command)){
				HgLogger.getInstance().info("zhuxy", newLog("分销退款通知方法", "修改订单状态", "修改订单号为："+dealerOrderCode+"待退款状态成功"));
			}else{
				HgLogger.getInstance().error("zhuxy", newLog("分销退款通知方法", "修改订单状态", "修改订单号为："+dealerOrderCode+"待退款状态失败"));
			}
		}else{
			HgLogger.getInstance().error("zhuxy", "ApiController->returned分销退款通知->支付状态异常:"+payStatus);
		}
		
		HgLogger.getInstance().debug("zhuxy", newHeader("分销退款通知方法结束"));
		return null;
	}
	
	/**
	 * 定时处理退款的订单
	 * @return
	 */
	@RequestMapping(value="/payback/task")
	@ResponseBody
	public String payback(){
		HgLogger.getInstance().debug("zhuxy", newHeader("定时刷出待退款的订单开始退款方法开始"));
		DecimalFormat df = new DecimalFormat("0.00");
		HslJPOrderQO hslJPOrderQO = new HslJPOrderQO();
		hslJPOrderQO.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
		List<JPOrderDTO> jpOrderList = jpService.queryOrder(hslJPOrderQO);
		HgLogger.getInstance().info("chenxy",newLog("定时刷出待退款的订单开始退款方法","查询出待退款的机票订单：",JSON.toJSONString(jpOrderList)));
		if(jpOrderList!=null&&jpOrderList.size()>0){
			// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
			String batch_no = this.getBatchNo();

			// 退款请求时间
			String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");

			// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
			String batch_num = "";

			// 单笔数据集
			String detail_data = "";
			
			//退款记录数
			int count = 0;

			StringBuilder sb = new StringBuilder();
			
				for (JPOrderDTO jpOrder : jpOrderList) {
					if (jpOrder == null ||StringUtils.isBlank(jpOrder.getPayTradeNo())|| jpOrder.getBackPrice() == null||(jpOrder.getBackPrice()!= null&&jpOrder.getBackPrice()<=0)) {
						continue;
					}
					//计算出实际的退款金额
					Double tureNum = getTrueNum(jpOrder.getDealerOrderCode(),jpOrder.getBackPrice());
					//单次批量退款最多1000笔
					if (count >= 1000) break;
					sb.append(jpOrder.getPayTradeNo());
					sb.append("^");
					sb.append(df.format(Math.abs(tureNum)));
					sb.append("^");
					sb.append(AppInfoUtils.getAlipayRemarkPrefix());
					sb.append("原路退款");
					sb.append("#");
					
					count++;
				}
				HgLogger.getInstance().info("zhuxy", newLog("定时刷出待退款的订单开始退款方法", "待退款订单数量", "count:" + count));
			
			if (count == 0) return null;
			
			//去掉最有一个‘#’
			detail_data = sb.toString();
			if (detail_data.endsWith("#")) {
				detail_data = detail_data.substring(0, detail_data.length() - 1);
			}
			
			batch_num = String.valueOf(count);
			
			//把请求参数打包成数组
			Map<String, String> sParaTemp = new HashMap<String, String>();
	        sParaTemp.put("batch_no", batch_no);
	        sParaTemp.put("refund_date", refund_date);
	        sParaTemp.put("batch_num", batch_num);
	        sParaTemp.put("detail_data", detail_data);
	        HgLogger.getInstance().info("zhuxy", newLog("定时刷出待退款的订单开始退款方法", "退款单信息", JSON.toJSONString(sParaTemp)));
			 
	        //批量退款操作
			try {
				String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
				HgLogger.getInstance().info("zhuxy", newLog("定时刷出待退款的订单开始退款方法", "发起退款后的回传信息", sHtmlText));
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("zhuxy", newLog("定时刷出待退款的订单开始退款方法","发起退款出现异常",HgLogger.getStackTrace(e)));
			}
		
		}
		HgLogger.getInstance().debug("zhuxy", newHeader("定时刷出待退款的订单开始退款方法结束"));
		return "success";
	}
	
	/**
	 * 计算出实际的退款金额
	 * @param dealerOrderCode
	 * @param backPrice
	 * @return
	 */
	private Double getTrueNum(String dealerOrderCode, Double backPrice) {
		//查询是否有卡券
		HslCouponQO qo = new HslCouponQO();
		qo.setOrderId(dealerOrderCode);
		List<CouponDTO> couponDTOs = couponService.queryList(qo);
		HgLogger.getInstance().info("chenxy", "计算实际退款时，查询根据订单号 查询所属卡券："+JSON.toJSONString(couponDTOs));
		if(couponDTOs!=null&&couponDTOs.size()>0){
			for(CouponDTO couponDTO : couponDTOs){
				try{
					if(couponDTO.getBaseInfo().getCouponActivity().getBaseInfo()!=null){
						backPrice = backPrice - couponDTO.getBaseInfo().getCouponActivity().getBaseInfo().getFaceValue();
					}
				}catch(NullPointerException e){
					e.printStackTrace();
					HgLogger.getInstance().error("chenxy", "计算实际退款金额出错"+HgLogger.getStackTrace(e));
				}
			}
			
		}
		if(backPrice<0){
			backPrice = 0d;
		}
		return backPrice;
	}

	/**
	 * 接受支付宝订单退款结果
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@ResponseBody
	@RequestMapping(value="/alipay/paybackResult")
	public String paybackResult(HttpServletRequest request){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		if(requestParams==null||requestParams.isEmpty()){
			HgLogger.getInstance().warn("zhuxy", "ApiController->paybackResult->params is null or empty");
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
		
		HgLogger.getInstance().info("zhuxy", "ApiController->paybackResult->params:" + sb.toString());
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("zhuxy", "ApiController->paybackResult->result_details:" + result_details);

		if (AlipayNotify.verify(params)) {//验证成功
			//修改订单状态并且激活已使用的卡券
			jpService.OrderRefund(result_details);
			return "success";
		} else {//验证失败
			HgLogger.getInstance().info("zhuxy", "ApiController->paybackResult->验证失败");
			return "fail";
		}
	}
	
	/**
	 * 分销平台订单状态同步通知商城
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/syn/notify")
	@ResponseBody
	public String syn(HttpServletRequest request) {
		String dealerOrderCode = request.getParameter("dealerOrderCode");
		String status = request.getParameter("status");
		String payStatus = request.getParameter("payStatus");
		if(dealerOrderCode==null||status==null||payStatus==null)
			return "FAIL";
		HgLogger.getInstance().info("chenxy","分销平台订单状态同步通知商城 >>同步状态>>经销商订单号："+dealerOrderCode+"status"+status+",payStatus:"+payStatus);
		System.out.println(payStatus.hashCode());
		System.out.println(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC.hashCode());
		//订单取消或已退款
		if (JPOrderStatusConstant.SHOP_TICKET_CANCEL.equals(status)
				|| JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC.equals(payStatus)) {
			HgLogger.getInstance().info("liusong","该订单号对应订单的订单状态已经为已取消状态，该订单号："+dealerOrderCode);
			
			//如果订单状态为已取消，则需要删除卡券中的与之相关联的订单号
			OrderRefundCommand command = new OrderRefundCommand();
			command.setOrderId(dealerOrderCode);
			try {
				couponService.orderRefund(command);
			} catch(CouponException e) {
				HgLogger.getInstance().error("liusong", "删除卡券中的与之相关联的订单号失败"+e.getMessage()+HgLogger.getStackTrace(e));
			}
		}else if(JPOrderStatusConstant.SHOP_TICKET_FAIL.equals(status)){
			//出票失败,设为待回款
			HgLogger.getInstance().info("liusong","ApiController->syn->订单出票失败->设为待回款,dealerOrderCode:"+dealerOrderCode+"status"+status+",payStatus:"+payStatus);
			//判断状态 当分销同步状态传值过来为已回款，默认 认为 向易购支付失败，钱在公司账户上面可以直接变为待退款. @chenxy 2015-0709
			if(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC.equals(payStatus)){
				payStatus=JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT;
				HgLogger.getInstance().info("liusong","ApiController->syn->订单出票失败->设为待退款,设置payStatus为"+payStatus);
			}else{
				payStatus=JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_WAIT;
				HgLogger.getInstance().info("liusong","ApiController->syn->订单出票失败->设为待回款,设置payStatus为"+payStatus);
			}
		}
		
		HgLogger.getInstance().info("zhangka", "ApiController->syn->[分销平台订单状态同步通知商城]->dealerOrderCode:" + dealerOrderCode + " status:" + status+",payStatus:"+payStatus);
		
		return jpService.updateOrderStatus(dealerOrderCode, Integer.parseInt(status),Integer.parseInt(payStatus)) ? "SUCCESS" : "FAIL";
	}
	/**
	 * @方法功能说明：同步线路接口
	 * @修改者名字：chenxy
	 * @修改时间：2015年1月30日上午10:39:05
	 * @修改内容：
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync/line")
	@ResponseBody
	public String syncLine(HttpServletRequest request){
		//获取分销改变的的线路
		String variableLine = request.getParameter("variableLine").trim();
		HgLogger.getInstance().info("chenxy",  LogFormat.log("同步线路接口", ">改变的值>>:"+variableLine));
		XLUpdateLineMessageApiCommand xlUpdateLineMessageApiCommand=JSON.parseObject(variableLine,XLUpdateLineMessageApiCommand.class);
		try {
			hslLineService.updateLineData(xlUpdateLineMessageApiCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", LogFormat.log("同步线路接口","接口出现异常"+HgLogger.getStackTrace(e)));
			return "Fail";
		}
		return "SUCCESS";
	}
	/**
	 * @方法功能说明：同步线路订单状态
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月10日下午2:16:26
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync/lineOrderStatus")
	@ResponseBody
	public String syncLineOrderStatus(HttpServletRequest request) {
		HgLogger.getInstance().info("chenxy",LogFormat.log("同步线路订单状态", "开始>>>>>" ));
		// 获取分销改变的的线路
		String lineOrder = request.getParameter("variableLine").trim();
		HgLogger.getInstance().info("chenxy",LogFormat.log("同步线路订单状态", ">改变的值>>:" + lineOrder));
		XLUpdateOrderStatusMessageApiCommand updateLineOrderStatusCommand = JSON.parseObject(lineOrder, XLUpdateOrderStatusMessageApiCommand.class);
		try {
			hslLineOrderService.updateLineOrderStatus(updateLineOrderStatusCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy",LogFormat.log("同步线路订单状态","接口出现异常" + HgLogger.getStackTrace(e)));
			return "Fail";
		}
		return "SUCCESS";
	}
	
	/**
	 * 
	 * @方法功能说明：同步线路订单金额
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月4日上午10:13:55
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/sync/lineOrderSalePrice")
	@ResponseBody
	public String syncLineOrderSalePrice(HttpServletRequest request) {
		HgLogger.getInstance().info("yuqz",LogFormat.log("同步线路订单金额", "开始>>>>>" ));
		// 获取分销改变的的线路
		String lineOrder = request.getParameter("variableLine").trim();
		HgLogger.getInstance().info("yuqz",LogFormat.log("同步线路订单金额", ">改变的值>>:" + lineOrder));
		XLUpdateOrderSalePriceMessageApiCommand updateLineOrderSalePriceCommand = JSON.parseObject(lineOrder, XLUpdateOrderSalePriceMessageApiCommand.class);
		try {
			hslLineOrderService.updateLineOrderSalePrice(updateLineOrderSalePriceCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("yuqz",LogFormat.log("同步线路订单状态","接口出现异常" + HgLogger.getStackTrace(e)));
			return "Fail";
		}
		return "SUCCESS";
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
			value = jedis.get("jp_tk_sequence");
			String date = jedis.get("jp_tk_sequence_date");

			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
				value = "1";
				HgLogger.getInstance().debug("zhuxy", newLog("流水号方法", "流水数字重置", ""));
			}
			
			long v = Long.parseLong(value);
			v++;
			
			jedis.set("jp_tk_sequence", String.valueOf(v));
			jedis.set("jp_tk_sequence_date", String.valueOf(Calendar.getInstance().getTime().getTime()));
		} catch(RuntimeException e){
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", newLog("流水号方法", "获取流水号异常", HgLogger.getStackTrace(e)));
		} finally {
			jedisPool.returnResource(jedis);
		}

		return Long.parseLong(value);
	}
	public static void main(String[] args) {
		String payStatus="25";
		if(JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_SUCC.equals(payStatus)){
			payStatus=JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT;
		}else{
			payStatus=JPOrderStatusConstant.SHOP_TICKET_TO_BE_BACK_WAIT;
		}
		System.out.println(payStatus);
	}
}
