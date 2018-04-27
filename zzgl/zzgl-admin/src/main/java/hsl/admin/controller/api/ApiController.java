package hsl.admin.controller.api;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import hsl.admin.alipay.services.AlipayService;
import hsl.admin.alipay.util.AlipayNotify;
import hsl.admin.controller.BaseController;
import hsl.domain.model.lineSalesPlan.order.LSPOrder;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.command.jp.JPOrderCommand;
import hsl.pojo.command.jp.UpdateJPOrderStatusCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.jp.FlightOrderDTO;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.jp.FlightOrderQO;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
import hsl.pojo.util.LogFormat;
import hsl.spi.inter.Coupon.CouponService;
import hsl.spi.inter.account.AccountConsumeSnapshotService;
import hsl.spi.inter.account.AccountService;
import hsl.spi.inter.jp.JPService;
import hsl.spi.inter.line.HslLineOrderService;
import hsl.spi.inter.line.HslLineService;
import hsl.spi.inter.lineSalesPlan.order.LSPOrderService;
import hsl.spi.inter.user.UserService;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
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
import org.apache.http.HttpRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import plfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderSalePriceMessageApiCommand;
import plfx.xl.pojo.command.line.XLUpdateOrderStatusMessageApiCommand;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

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
	private AccountConsumeSnapshotService accountConsumeSnapshotService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private LSPOrderService lspOrderService;
	//固定值,为了解决精度问题
	private Integer constant=100;
	/**
	 * 定时处理退款的订单
	 * @return
	 */
	@RequestMapping(value="/payback/task")
	@ResponseBody
	public String payback(){
		HgLogger.getInstance().debug("chenxy", newHeader("定时刷出待退款的订单开始退款方法开始"));
		DecimalFormat df = new DecimalFormat("0.00");
		FlightOrderQO flightOrderQO = new FlightOrderQO();
		flightOrderQO.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_REBACK_WAIT));
		List<FlightOrderDTO> jpOrderList = jpService.queryList(flightOrderQO);
		HgLogger.getInstance().info("chenxy",newLog("定时刷出待退款的订单开始退款方法1","查询出待退款的机票订单：",JSON.toJSONString(jpOrderList)));
		if(jpOrderList!=null&&jpOrderList.size()>0){
			//批量退款操作
			try {
				// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
				String batch_no = this.getBatchNo("zzgljp");
				// 退款请求时间
				String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
				// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
				String batch_num = "";
				// 单笔数据集
				String detail_data = "";
				//退款记录数
				int count = 0;
				StringBuilder sb = new StringBuilder();
				Map<String,Double> refundJp=new HashMap<String, Double>();
				for (FlightOrderDTO jpOrder : jpOrderList) {
					if (jpOrder == null) {
						continue;
					}
					int passengersSize=jpOrder.getPassengers().size();
					FlightOrderQO qo1 = new FlightOrderQO();
					qo1.setOrderNO(jpOrder.getOrderNO());
					qo1.setPayStatus(Integer.valueOf(JPOrderStatusConstant.SHOP_TICKET_PAY_SUCC));
					FlightOrderDTO flightOrderDTO= jpService.queryUnique(qo1);
					Double payCash=jpOrder.getFlightPriceInfo().getPayCash();//每人使用现金乘于人数等到退款总金额
					Double payBalance=0.0;
					if(flightOrderDTO==null) {
						AccountConsumeSnapshotQO accountConsumeSnapshotQO=new AccountConsumeSnapshotQO();
						accountConsumeSnapshotQO.setOrderId(jpOrder.getOrderNO());
						accountConsumeSnapshotQO.setAccountstatus(AccountConsumeSnapshot.STATUS_SY);
						AccountConsumeSnapshotDTO accountConsumeSnapshotDTO=accountConsumeSnapshotService.queryUnique(accountConsumeSnapshotQO);
						HgLogger.getInstance().info("chenxy","accountConsumeSnapshotDTO" + JSON.toJSONString(accountConsumeSnapshotDTO));
						if(accountConsumeSnapshotDTO==null){
							payBalance =0.0;
						}else{
							payBalance = accountConsumeSnapshotDTO.getPayPrice();//每人使用余额乘人数等到退款总金额
						}
					}else{
						payBalance = jpOrder.getFlightPriceInfo().getPayBalance() * passengersSize;//每人使用余额乘于人数等到退款总金额
					}
					Double procedureFe=jpOrder.getDealerReturnInfo().getProcedureFee();//退款手续费
					Double refundBalance=0.00;
					if(procedureFe==null||procedureFe<0.00){
						procedureFe=0.00;
					}
					if(payBalance==null||payBalance<0.00){
						payBalance=0.00;
					}
					//计算出实际的应退金额
					Double tureNum = getTrueNum(passengersSize,procedureFe,payCash);
					/*当退费金额为0的时候，无须调用支付宝退款,直接更新状态以及 退费金额*/
					HgLogger.getInstance().info("chenxy","payback--退账户余额"+tureNum);
					/*********帐号余额退款***************/
					AccountConsumeSnapshotQO account=new AccountConsumeSnapshotQO();
					account.setOrderId(jpOrder.getOrderNO());
					AccountConsumeSnapshotDTO accountConsumeSnapshot=accountConsumeSnapshotService.queryUnique(account);
					HgLogger.getInstance().info("chenxy","payback--账户余额查询：" + JSON.toJSONString(accountConsumeSnapshot));
					if(accountConsumeSnapshot!=null&&accountConsumeSnapshot.getPayPrice()>0){
						if(tureNum<0){
							refundBalance=(payBalance*constant+tureNum*constant)/constant;
							tureNum=0.00;
						}else{
							refundBalance=payBalance;
						}
						HgLogger.getInstance().info("chenxy","payback--账户余额退款金额" + refundBalance);
						if(refundBalance>0){
							refundAccount(accountConsumeSnapshot.getAccount().getId(),accountConsumeSnapshot, refundBalance,payBalance,jpOrder);
						}else{
							refundAccount(accountConsumeSnapshot.getAccount().getId(),accountConsumeSnapshot, 0.00,payBalance,jpOrder);
						}
					}
					HgLogger.getInstance().info("chenxy", "payback--支付宝退款金额" + tureNum);
					HgLogger.getInstance().info("chenxy", "payback--支付宝订单号:" + jpOrder.getPayTradeNo());
					if(StringUtils.isBlank(jpOrder.getPayTradeNo())||tureNum==0.00){
						continue;
					}
					//单次批量退款最多1000笔
					if (count >= 1000) break;
					refundJp.put(jpOrder.getId(),tureNum);
					sb.append(jpOrder.getPayTradeNo());
					sb.append("^");
					sb.append(df.format(Math.abs(tureNum)));
					sb.append("^");
					sb.append("原路退款");
					sb.append("#");
					count++;
				}
				HgLogger.getInstance().info("chenxy", newLog("定时刷出待退款的订单开始退款方法2", "待退款订单数量", "count:" + count));
				if (count == 0) return "fail";
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
				HgLogger.getInstance().info("chenxy", newLog("定时刷出待退款的订单开始退款方法3", "退款单信息", JSON.toJSONString(sParaTemp)));
				/*提交支付宝退款请求*/
				String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp, "");
				HgLogger.getInstance().info("chenxy", newLog("定时刷出待退款的订单开始退款方法4", "发起退款后的回传信息", sHtmlText));
				boolean isSuc=analyzeAlipayRefundResult(sHtmlText);
				//判断是否退款成功，修改订单状态
				if(isSuc){
					for (String id:refundJp.keySet()){
						refund(id,refundJp.get(id));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("chenxy", newLog("定时刷出待退款的订单开始退款方法5","发起退款出现异常",HgLogger.getStackTrace(e)));
				return "fail";
			}
		}
		HgLogger.getInstance().debug("chenxy", newHeader("定时刷出待退款的订单开始退款方法结束"));
		return "success";
	}
	/**
	 * 计算出实际的退款金额
	 * @return
	 */
	private Double getTrueNum(int passengersSize, double procedureFeet, double payCash) {
		//现在退款不考虑卡券
		double backPrice=0.00;
		try {														
			HgLogger.getInstance().info("zhaows","传入参数getTrueNum=" +passengersSize+"="+procedureFeet+"="+payCash );
			double countPeople=passengersSize*payCash;
			backPrice=countPeople-procedureFeet;//平均每人支付现金-退款手续费=应退金额
			HgLogger.getInstance().info("zhaows","实际退款金额"+backPrice );
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().info("zhaows","getTrueNum--->"+HgLogger.getStackTrace(e));
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
			HgLogger.getInstance().warn("chenxy", "ApiController->paybackResult->params is null or empty");
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

		HgLogger.getInstance().info("chenxy", "ApiController->paybackResult->params:" + sb.toString());

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("chenxy", "ApiController->paybackResult->result_details:" + result_details);

		if (AlipayNotify.verify(params)) {//验证成功
			//修改订单状态并且激活已使用的卡券
			jpService.OrderRefund(result_details);
			return "success";
		} else {//验证失败
			HgLogger.getInstance().info("chenxy", "ApiController->paybackResult->验证失败");
			return "fail";
		}
	}
	/**
	 * @方法功能说明：分销通知机票接口
	 * @修改者名字：chenxy
	 * @修改时间：2015年8月3日上午10:45:07
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param value
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value="/sync/jp")
	@ResponseBody
	public String syncJpOrder(HttpServletRequest request,@RequestParam(value = "value", required = false) String value){
		HgLogger.getInstance().info("chenxy", "分销同步订单接口>>>参数>>>"+value);
		JPOrderCommand command=JSON.parseObject(value, JPOrderCommand.class);
		boolean flag=jpService.notifyUpdateFightOrder(command);
		HgLogger.getInstance().info("chenxy", "分销同步订单接口>>>处理完成返回状态>>>"+flag);
		if(flag){  
			return "success";
		}else{
			return "fail";
		}

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
		HgLogger.getInstance().info("chenxy",  LogFormat.log("同步线路接口", ">改变的值>>:" + variableLine));
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
			HgLogger.getInstance().error("chenxy", LogFormat.log("同步线路订单状态", "接口出现异常" + HgLogger.getStackTrace(e)));
			return "Fail";
		}
		return "SUCCESS";
	}

	/**
	 * 
	 * @方法功能说明：同步线路订单金额
	 * @修改者名字：yuqz
	 * @修改时间：2015年6月4日上午10:13:55
	 * @修改内容：2015年11月18日，经和产品商定，去掉分销修改订单价格的这个功能
	 * @参数：@param request
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	/*@RequestMapping(value = "/sync/lineOrderSalePrice")
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
	}*/

	// 获取批次号
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	/**
	 * 生成退款批次号，prefix为类型的前缀
	 * @param prefix
	 * @return
	 */
	public  String getBatchNo(String prefix) {
		String refundDate = df.format(Calendar.getInstance().getTime());
		String sequence = String.format("%04d", this.getSequence(prefix));
		String flag=SysProperties.getInstance().get("alipay_prefix")+prefix;
		/**格式为：退款日期（ 8 位当天日期） +流水号（ 3～24 位，不能接受“ 000”，但是可以接受英文字符）*/
		return refundDate +flag+ sequence;
	}

	// 获取给定格式的日期字符串
	private String getDateString(String format) {
		SimpleDateFormat df = new SimpleDateFormat(format);
		return df.format(Calendar.getInstance().getTime());
	}
	// 获取流水号
	public long getSequence(String sequencePrefix) {
		Jedis jedis = null;
		String value;
		try {
			jedis = jedisPool.getResource();
			value = jedis.get(sequencePrefix+"_tk_sequence");
			String date = jedis.get(sequencePrefix+"_tk_sequence_date");
			/**----确保从Redis中获得的value与date为空时，直接修改将value值修改为1.当期日期不是同一天也修改为1--------*/
			if (StringUtils.isBlank(value)|| StringUtils.isBlank(date)
					|| !date.equals(this.getDateString("yyyyMMdd"))) {
				value = "1";
				HgLogger.getInstance().debug("chenxy", newLog("流水号方法", "流水数字重置", ""));
			}

			long v = Long.parseLong(value);
			if (v >= 9999) {
				v = 0;
			}
			v++;
			jedis.set(sequencePrefix+"_tk_sequence", String.valueOf(v));
			jedis.set(sequencePrefix+"_tk_sequence_date", this.getDateString("yyyyMMdd"));
			return v;
		} catch(RuntimeException e){
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", newLog("流水号方法", "获取流水号异常", HgLogger.getStackTrace(e)));
			return 0;
		} finally {
			jedisPool.returnResource(jedis);
		}

	}
	public void refund(String id,double backPrice){
		try {
			UpdateJPOrderStatusCommand command=new UpdateJPOrderStatusCommand();
			command.setId(id);
			command.setPayStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_DEALING));
			command.setBackPrice(backPrice);
			HgLogger.getInstance().info("chenxy", "退款时退款金额为0>>>直接修改数据库>>>command" + JSON.toJSONString(command));
			jpService.updateOrderStatusById(command);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "refund--修改状态失败");
		}

	}
	/**
	 * @方法功能说明：退余额
	 * @创建者名字：zhaows
	 * @创建时间：2015-9-25上午8:53:28
	 * @参数：
	 * @return:void
	 * @throws
	 */
	public void refundAccount(String accountId,AccountConsumeSnapshotDTO accountConsumeSnapshot,double refundPrice,double payPrice,FlightOrderDTO jpOrderDTO){
		try {
			HgLogger.getInstance().info("chenxy", "refundAccount>>>command=" + accountId + "=" + payPrice);
			RefundCommand command=new RefundCommand();
			command.setAccountId(accountConsumeSnapshot.getAccount().getId());
			command.setAccountConsumeSnapshotId(accountConsumeSnapshot.getId());
			command.setConsumeOrderSnapshot(accountConsumeSnapshot);
			command.setOrderID(jpOrderDTO.getOrderNO());
			command.setPayPrice(payPrice);
			command.setRefundMoney(refundPrice);
			command.setRefundOrderId(jpOrderDTO.getId());
			accountService.refundAccount(command);
		} catch (AccountException e) {
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "refundAccount-->退余额失败"+HgLogger.getStackTrace(e));
		}
	}

	/**
	 * 定时退还未成团的线路活动订单
	 * @return
	 */
	@RequestMapping("/refundLSPOrder/task")
	@ResponseBody
	public String refundLSPOrder(){
		HgLogger.getInstance().debug("chenxy", "ApiController-->refundLSPOrder-->退款开始");
		DecimalFormat df = new DecimalFormat("0.00");
		LSPOrderQO lspOrderQO=new LSPOrderQO();
		lspOrderQO.setOrderStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_SUCCESS_GROUP_ERR);
		lspOrderQO.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_PAY_SUCCESS);
		List<LSPOrderDTO> lspOrders=lspOrderService.queryList(lspOrderQO);
		if(lspOrders!=null&&lspOrders.size()>0){
			//批量退款操作
			try {
				HgLogger.getInstance().info("chenxy","ApiController-->refundLSPOrder-->退款查询退款订单数量"+lspOrders.size());
				// 退款批次号。格式为：退款日期（8位当天日期）+流水号（3～24位，不能接受“000”，但是可以接受英文字符）
				String batch_no = this.getBatchNo("zzpllsp");
				// 退款请求时间
				String refund_date = this.getDateString("yyyy-MM-dd HH:mm:ss");
				// 退款总笔数，即参数detail_data的值中，“#”字符出现的数量加1，最大支持1000笔（即“#”字符出现的数量999个）
				String batch_num;
				// 单笔数据集
				String detail_data;
				//退款记录数
				int count = 0;
				StringBuilder sb = new StringBuilder();
				Map<String,Double> refundLsp=new HashMap<String, Double>();
				for (LSPOrderDTO lspOrderDTO : lspOrders) {
					if (lspOrderDTO == null) {
						continue;
					}
					//购买者支付宝帐号
					String buyerEmail=lspOrderDTO.getOrderPayInfo().getBuyerEmail();
					//订单的支付宝交易号
					String tradeNo=lspOrderDTO.getOrderPayInfo().getPayTradeNo();
					//真实支付金额
					Double payPrice=lspOrderDTO.getOrderPayInfo().getPayPrice();
					HgLogger.getInstance().info("chenxy", "ApiController-->refundLSPOrder-->退款查询订单的经销商订单号-->" + lspOrderDTO.getOrderBaseInfo().getDealerOrderNo()
							+ "支付宝帐号-->" + buyerEmail + "交易号-->" + tradeNo + "支付价格-->" + payPrice + "相应的订单状态-->" + JSON.toJSONString(lspOrderDTO.getOrderStatus()));
					if(StringUtils.isBlank(buyerEmail)||StringUtils.isBlank(tradeNo) ||payPrice==null||payPrice<=0)
						continue;
					//单次批量退款最多1000笔
					if (count >= 1000) break;
					refundLsp.put(lspOrderDTO.getId(),payPrice);
					sb.append(tradeNo);
					sb.append("^");
					sb.append(df.format(Math.abs(payPrice)));
					sb.append("^");
					sb.append("原路退款");
					sb.append("#");
					count++;
				}
				HgLogger.getInstance().info("chenxy", "ApiController-->refundLSPOrder-->退款数量-->count:" + count);
				if (count == 0) return "fail";
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
				HgLogger.getInstance().info("chenxy","ApiController-->refundLSPOrder-->退款-->退款单信息"+JSON.toJSONString(sParaTemp));
				/*提交支付宝退款请求*/
				String notifyUrl=SysProperties.getInstance().get("http_domain") + "/api/lspOrder/Alipay/paybackResult";
				String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp,notifyUrl);
				HgLogger.getInstance().info("chenxy", "ApiController-->refundLSPOrder-->退款-->发起退款后的回传信息"+sHtmlText);
				boolean isSuc=analyzeAlipayRefundResult(sHtmlText);
				/**-----------------修改活动订单状态,为退款处理中状态----------*/
				//判断是否退款成功，修改订单状态
				if(isSuc){
					for (String id:refundLsp.keySet()){
						UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand=new UpdateLSPOrderStatusCommand();
						updateLSPOrderStatusCommand.setOrderId(id);
						updateLSPOrderStatusCommand.setStatus(LineSalesPlanConstant.LSP_ORDER_STATUS_REFUND_DEALING);
						updateLSPOrderStatusCommand.setPayStatus(LineSalesPlanConstant.LSP_PAY_STATUS_REFUND_WAIT);
						lspOrderService.updateLSPOrderStatus(updateLSPOrderStatusCommand);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("chenxy", "ApiController-->refundLSPOrder-->退款-->ERROR" + HgLogger.getStackTrace(e));
				return "fail";
			}
		}
		HgLogger.getInstance().debug("chenxy","ApiController-->refundLSPOrder-->退款-->结束");
		return "success";
	}
	@RequestMapping("/lspOrder/Alipay/paybackResult")
	@ResponseBody
	public String refundNotifyForLSPOrder(HttpServletRequest request){
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		StringBuilder sb = new StringBuilder();
		if(requestParams==null||requestParams.isEmpty()){
			HgLogger.getInstance().warn("chenxy", "ApiController->refundNotifyForLSPOrder->params is null or empty");
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

		HgLogger.getInstance().info("chenxy", "ApiController->refundNotifyForLSPOrder->params:" + sb.toString());

		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		String result_details = request.getParameter("result_details");	//处理结果详情
		HgLogger.getInstance().info("chenxy", "ApiController->refundNotifyForLSPOrder->result_details:" + result_details);

		if (AlipayNotify.verify(params)) {//验证成功
			//退款成功，修改相应订单的状态
			try {
				lspOrderService.refundLSPOrder(result_details);
			} catch (LSPException e) {
				e.printStackTrace();
				HgLogger.getInstance().error("chenxy","ApiController->refundNotifyForLSPOrder->Error-->"+HgLogger.getStackTrace(e));
			}
			return "success";
		} else {//验证失败
			HgLogger.getInstance().info("chenxy", "ApiController->refundNotifyForLSPOrder->验证失败");
			return "fail";
		}
	}

	/**
	 * 解析支付宝退款返回结果是否退款成功
	 * @param sHtmlText
	 * @return
	 */
	private boolean analyzeAlipayRefundResult(String sHtmlText){
		SAXReader saxReader = new SAXReader();
		try {
			Document document =saxReader.read(new ByteArrayInputStream(sHtmlText.getBytes("UTF-8")));
			// 获取根元素
			Element root = document.getRootElement();
			// 获取所有子元素
//			List<Element> childList = root.elements();
			// 获取特定名称的子元素
			Element is_success = root.element("is_success");
			System.out.println(is_success.getData());
			if(is_success.getData().equals("T")){
				return true;
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return  false;
	}
}
