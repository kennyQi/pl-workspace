package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.client.request.order.FlightOrderInfoQO;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.CostCenterOrderDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.CommonService;
import zzpl.app.service.local.util.payback.AlipayService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：拒绝出票
 * @类修改者：
 * @修改日期：2015年12月24日下午2:59:46
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午2:59:46
 */
@Component("ApprovalFailedGNFlightService")
public class ApprovalFailedGNFlightService implements CommonService {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private PushService pushService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private CostCenterOrderDAO costCenterOrderDAO;
	@Autowired
	private CostCenterDAO costCenterDAO;
	@Autowired
	private SMSUtils smsUtils;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】【execute】"+JSON.toJSONString(command));
		//查询订单
		WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
		workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
		List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
		for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setId(workflowInstanceOrder.getOrderID());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			/**
			 * 如果要是选择公司代扣 更新订单状态
			 * 
			 * 如果是 申请人自己支付 则提出退款 更新结算中心 和 新增订单流转记录了 结算价格 计算规则 （支付价-退款金额）-（用户支付价-用户退款金额） 现在按照用户100%退款
			 * 
			 * 第一判断是 当用户支付成功 没做任何操作 就点击出票失败
			 * 第二判断是 用户支付成功 点击 自动出票 然后 出票失败了 这时再点击出票失败 所以要退款
			 */
			HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			if((Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF&&flightOrder.getPayStatus()==FlightPayStatus.USER_PAY_SUCCESS)||(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF&&flightOrder.getPayStatus()==FlightPayStatus.PAY_FAILED)){
				HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】"+"【退款】");
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
		        HgLogger.getInstance().info("cs", "【ApprovalFailedGNFlightService】【国内机票拒绝出票】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
				try {
					flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
					flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
					HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】国内机票点击右下角拒绝出票"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					PassengerQO passengerQO = new PassengerQO();
					passengerQO.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers = passengerDAO.queryList(passengerQO);
					for (Passenger passenger : passengers) {
						passenger.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED.toString());
						passengerDAO.update(passenger);
					}
					String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
					HgLogger.getInstance().info("cs", "【ApprovalFailedGNFlightService】【国内机票拒绝出票】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
				} catch (Exception e) {
					HgLogger.getInstance().info("cs", "【ApprovalFailedGNFlightService】【国内机票拒绝出票】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
//					User user = userDAO.get(flightOrder.getUserID());
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
					//出票失败 证明没去易行付款也没有去易行退款 这是设置成本中心退款的时候应该是totalPrcie
					//因为收到退款通知的时候 要去计算
					cosaof.setRefundPrice(cosaof.getTotalPrice());
					cosaof.setUserRefundPrice(0.0);
					cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice());
					HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）失败】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.update(cosaof);
					flightOrder.setPayStatus(FlightPayStatus.REFUND_USER_FAILED);
					flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
					HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】国内机票点击右下角拒绝出票"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					PassengerQO passengerQO = new PassengerQO();
					passengerQO.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers = passengerDAO.queryList(passengerQO);
					for (Passenger passenger : passengers) {
						passenger.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED.toString());
						passengerDAO.update(passenger);
					}
					/*
					 * 创建成本中心订单记录
					 */
					/*CostCenterOrder costCenterOrder = new CostCenterOrder();
					costCenterOrder.setId(UUIDGenerator.getUUID());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					if (StringUtils.isNotBlank(flightOrder.getCostCenterID())) {
						CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
						costCenterOrder.setCostCenter(costCenter);
					}
					costCenterOrder.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					costCenterOrder.setOrderID(flightOrder.getId());
					costCenterOrder.setOrderNO(flightOrder.getOrderNO());
					costCenterOrder.setUserID(user.getId());
					costCenterOrder.setName(user.getName());
					costCenterOrder.setIdCardNO(user.getIdCardNO());
					costCenterOrder.setCompanyID(user.getCompanyID());
					costCenterOrder.setCompanyName(user.getCompanyName());
					costCenterOrder.setDepartmentID(user.getDepartmentID());
					costCenterOrder.setDepartmentName(user.getDepartmentName());
					costCenterOrder.setCostfPrice(flightOrder.getRefundPrice()*-1);
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）失败】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}
//				User user = userDAO.get(flightOrder.getUserID());
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				//参照出票失败注释
				cosaof.setRefundPrice(cosaof.getTotalPrice());
				cosaof.setUserRefundPrice(0.0);
				cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice());
				HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）成功】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
				cosaofdao.update(cosaof);
				/*
				 * 创建成本中心订单记录
				 */
				/*CostCenterOrder costCenterOrder = new CostCenterOrder();
				costCenterOrder.setId(UUIDGenerator.getUUID());
				costCenterOrder.setOrderNO(flightOrder.getOrderNO());
				if (StringUtils.isNotBlank(flightOrder.getCostCenterID())) {
					CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
					costCenterOrder.setCostCenter(costCenter);
				}
				costCenterOrder.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
				costCenterOrder.setOrderID(flightOrder.getId());
				costCenterOrder.setOrderNO(flightOrder.getOrderNO());
				costCenterOrder.setUserID(user.getId());
				costCenterOrder.setName(user.getName());
				costCenterOrder.setIdCardNO(user.getIdCardNO());
				costCenterOrder.setCompanyID(user.getCompanyID());
				costCenterOrder.setCompanyName(user.getCompanyName());
				costCenterOrder.setDepartmentID(user.getDepartmentID());
				costCenterOrder.setDepartmentName(user.getDepartmentName());
				costCenterOrder.setCostfPrice(cosaof.getRefundPrice()*-1);
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）成功】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
			}else{
				HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】"+"【不退款】");
				flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				flightOrder.setPayStatus(FlightPayStatus.REFUND_SUCCESS);
				HgLogger.getInstance().info("gk", "【ApprovalFailedGNFlightService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				PassengerQO passengerQO = new PassengerQO();
				passengerQO.setFlightOrderID(flightOrder.getId());
				List<Passenger> passengers = passengerDAO.queryList(passengerQO);
				for (Passenger passenger : passengers) {
					passenger.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED.toString());
					passengerDAO.update(passenger);
				}
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("actionName", "GetFlightOrderInfo");
				FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
				flightOrderInfoQO.setOrderID(flightOrder.getId());
				map.put("payload", JSON.toJSONString(flightOrderInfoQO));
				pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票出票失败", map);
				final String text = "【"+SysProperties.getInstance().get("smsSign")+"】"+"您的订单"+flightOrder.getOrderNO()+"退款成功，票款会在7-15个工作日内返回到您的公司账户。客服电话0571-28280813";
				final String linkMobile = flightOrder.getLinkTelephone();
				HgLogger.getInstance().info("cs", "【CancelManaulGNFlightService】"+"发送短信内容，电话"+flightOrder.getLinkTelephone());
				HgLogger.getInstance().info("cs", "【CancelManaulGNFlightService】"+"发送短信内容，内容"+text);
				new Thread(){
					public void run(){
						try {
							smsUtils.sendSms(linkMobile, text);
						} catch (Exception e) {
							HgLogger.getInstance().info("cs", "【CancelManaulGNFlightService】"+"发送短信异常，"+HgLogger.getStackTrace(e));
						}
					}
				}.start();
			}
		}
		return CommonService.SUCCESS;
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
}
