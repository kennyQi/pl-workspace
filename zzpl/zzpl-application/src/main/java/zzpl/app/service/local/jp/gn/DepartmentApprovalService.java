package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseCommand;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
import zzpl.app.dao.workflow.CommentDAO;
import zzpl.app.dao.workflow.TasklistDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.CommonService;
import zzpl.app.service.local.util.payback.AlipayService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.workflow.Comment;
import zzpl.domain.model.workflow.Tasklist;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.workflow.AddCommentCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：部门经理购票审批
 * @类修改者：
 * @修改日期：2015年12月24日下午3:03:05
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午3:03:05
 */
@Component("DepartmentApprovalService")
public class DepartmentApprovalService implements CommonService {

	@Autowired
	private CommentDAO commentDAO;
	@Autowired
	private TasklistDAO tasklistDAO;
	@Autowired
	private PushService pushService;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private CostCenterDAO costCenterDAO;
	@Autowired
	private CostCenterOrderDAO costCenterOrderDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		
		AddCommentCommand addCommentCommand = JSON.parseObject(JSON.toJSONString(command), AddCommentCommand.class);
		HgLogger.getInstance().info("gk", "【DepartmentApprovalService】【execute】"+JSON.toJSONString(addCommentCommand));
		try {
			Comment comment = new Comment();
			comment.setId(UUIDGenerator.getUUID());
			comment.setCommentJSON(addCommentCommand.getCommentJSON());
			comment.setCommentType(addCommentCommand.getCommentType());
			comment.setCurrentUserID(addCommentCommand.getCurrentUserID());
			comment.setCurrentUserName(addCommentCommand.getCurrentUserName());
			comment.setStepNO(addCommentCommand.getStepNO());
			comment.setStepName(addCommentCommand.getStepName());
			comment.setCommentTime(new Date());
			
			Tasklist tasklist = tasklistDAO.get(addCommentCommand.getTasklistID());
			comment.setTasklist(tasklist);
			commentDAO.save(comment);
			tasklist.setOpinion(addCommentCommand.getOpinion());
			tasklistDAO.update(tasklist);
			HgLogger.getInstance().info("gk", "【DepartmentApprovalService】【execute】【tasklist】"+JSON.toJSONString(tasklist));
			
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
			List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setId(workflowInstanceOrder.getOrderID());
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				HgLogger.getInstance().info("gk", "【DepartmentApprovalService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("actionName", "GetFlightOrderInfo");
				FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
				flightOrderInfoQO.setOrderID(flightOrder.getId());
				map.put("payload", JSON.toJSONString(flightOrderInfoQO));
				if (StringUtils.equals(addCommentCommand.getOpinion(), "Y")) {
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您的购票审批已通过。", map);
				}else {
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您的购票审批未通过。", map);
					if(flightOrder.getPayStatus()==FlightPayStatus.USER_PAY_SUCCESS){
						HgLogger.getInstance().info("gk", "【DepartmentApprovalService】"+"【退款】");
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
//						sb.append(df.format(Math.abs(tureNum)));
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
				        HgLogger.getInstance().info("cs", "【DepartmentApprovalService】【国内机票部门经理审批未通过退款】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
						try {
							
							flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
							flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
							flightOrder.setRefundPrice(flightOrder.getUserPayPrice());
							HgLogger.getInstance().info("gk", "【DepartmentApprovalService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
							flightOrderDAO.update(flightOrder);
							
							PassengerQO passengerQO = new PassengerQO();
							passengerQO.setFlightOrderID(flightOrder.getId());
							List<Passenger> passengers = passengerDAO.queryList(passengerQO);
							for (Passenger passenger : passengers) {
								passenger.setStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED.toString());
								passengerDAO.update(passenger);
							}
							String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
							HgLogger.getInstance().info("cs", "【DepartmentApprovalService】【国内机票部门经理审批未通过退款】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
						} catch (Exception e) {
							HgLogger.getInstance().info("cs", "【DepartmentApprovalService】【国内机票部门经理审批未通过退款】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
							flightOrder.setPayStatus(FlightPayStatus.REFUND_USER_FAILED);
							flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
							HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
							flightOrderDAO.update(flightOrder);
							
//							User user = userDAO.get(flightOrder.getUserID());
							COSAOFQO cosaofqo = new COSAOFQO();
							cosaofqo.setOrderID(flightOrder.getId());
							COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
							cosaof.setOrderStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
							//参照出票失败注释
							cosaof.setRefundPrice(cosaof.getTotalPrice());
							cosaof.setUserRefundPrice(0.0);
							cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice());
							HgLogger.getInstance().info("cs", "【国内机票部门经理审批未通过退款】【提交原路退款成功】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
							costCenterOrder.setCostfPrice(flightOrder.getUserPayPrice()*-1);
							costCenterOrder.setCreateTime(new Date());
							HgLogger.getInstance().info("cs", "【国内机票部门经理审批未通过退款】【提交原路退款失败】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
							costCenterOrderDAO.save(costCenterOrder);*/
							return CommonService.SUCCESS;
						}
						flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
						flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
						HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
						flightOrderDAO.update(flightOrder);
						
//						User user = userDAO.get(flightOrder.getUserID());
						COSAOFQO cosaofqo = new COSAOFQO();
						cosaofqo.setOrderID(flightOrder.getId());
						COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
						cosaof.setOrderStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
						//参照出票失败注释
						cosaof.setRefundPrice(cosaof.getTotalPrice());
						cosaof.setUserRefundPrice(0.0);
						cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice());
						HgLogger.getInstance().info("cs", "【国内机票部门经理审批未通过退款】【提交原路退款成功】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
						costCenterOrder.setCostfPrice(flightOrder.getUserPayPrice()*-1);
						costCenterOrder.setCreateTime(new Date());
						HgLogger.getInstance().info("cs", "【国内机票部门经理审批未通过退款】【提交原路退款成功】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
						costCenterOrderDAO.save(costCenterOrder);*/
					}else{
						flightOrder.setStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED);
						flightOrder.setRefundPrice(flightOrder.getUserPayPrice());
						HgLogger.getInstance().info("gk", "【DepartmentApprovalService】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
						flightOrderDAO.update(flightOrder);
					}
				}
			}
			return CommonService.SUCCESS;
		} catch (Exception e) {
			HgLogger.getInstance().error("cs",	"【DepartmentApprovalService】【execute】："+e.getMessage());
			return CommonService.FAIL;
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
}
