package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseServiceImpl;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.util.payback.AlipayService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.user.CostCenterOrder;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.jp.AlipayCommand;
import zzpl.pojo.dto.jp.status.COSAOFStatus;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class AlipayNotifyService extends
BaseServiceImpl<FlightOrder, FlightOrderQO, FlightOrderDAO> {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private PushService pushService;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	/**
	 * 
	 * @方法功能说明：收到支付宝支付成功通知
	 * @修改者名字：cangs
	 * @修改时间：2015年8月17日下午7:15:14
	 * @修改内容：
	 * @参数：@param alipayCommand
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean payGNFlight(AlipayCommand alipayCommand){
		try{
			HgLogger.getInstance().info("cs", "【payGNFlight】"+"alipayCommand:"+JSON.toJSONString(alipayCommand));
			List<FlightOrder> flightOrders = new ArrayList<FlightOrder>();
			if(alipayCommand.getOrderNO().startsWith("UN_")){
				//如果是多订单合并付款
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setUnionOrderNO(alipayCommand.getOrderNO());
				flightOrders = flightOrderDAO.queryList(flightOrderQO);
			}else{
				//只是单独提交
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setOrderNO(alipayCommand.getOrderNO());
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				flightOrders.add(flightOrder);
			}
			for(FlightOrder flightOrder:flightOrders){
				/*
				 * 明天NBA开干啦！火箭是冠军！
				 * 因为发生过支付一次支付宝异步通知十次的逆天反人类行为，这里新加一个判断，如果结算中心里已经有该订单相关的数据就直接跳出循环
				 */
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				if(cosaofdao.queryCount(cosaofqo)!=0){
					HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+"该订单结算中心相关数据已经添加过，直接跳出循环");
					break;
				}
				HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+"进行结算中心等表的操作");
				//这里用flightOrder.getPlatTotalPrice()是因为如果多订单合并的话支付宝传入的金额是总订单金额
				flightOrder.setUserPayPrice(flightOrder.getPlatTotalPrice());
				flightOrder.setTrade_no(alipayCommand.getTrade_no());
				flightOrder.setBuyer_email(alipayCommand.getBuyer_email());
				flightOrder.setPayStatus(FlightPayStatus.USER_PAY_SUCCESS);
				HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
				/*
				 * 先更新进去为了让部门经理审批不通过的时候退款有支付宝订单信息
				 */
				flightOrderDAO.update(flightOrder);
				if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_ORDER_FAILED){
					HgLogger.getInstance().info("gk", "【payGNFlight】"+"【退款】");
					//当订单状态是审批失败 说明部门经理审批未通过 所以要把钱推给用户
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
//					sb.append(df.format(Math.abs(tureNum)));
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
					HgLogger.getInstance().info("cs", "【payGNFlight】【国内机票部门经理审批未通过退款】"+"定时刷出待退款的订单开始退款方法"+ "退款单信息"+sParaTemp);
					try {
						String sHtmlText = AlipayService.refund_fastpay_by_platform_nopwd(sParaTemp);
						HgLogger.getInstance().info("cs", "【payGNFlight】【国内机票部门经理审批未通过退款】"+"定时刷出待退款的订单开始退款方法"+"发起退款后的回传信息"+sHtmlText);
					} catch (Exception e) {
						//退款异常了 但是要把付款的信息记录进去
						HgLogger.getInstance().info("cs", "【payGNFlight】【国内机票部门经理审批未通过退款】"+"发起退款出现异常，"+HgLogger.getStackTrace(e));
						flightOrder.setUserPayPrice(flightOrder.getPlatTotalPrice());
						flightOrder.setTrade_no(alipayCommand.getTrade_no());
						flightOrder.setBuyer_email(alipayCommand.getBuyer_email());
						flightOrder.setPayStatus(FlightPayStatus.REFUND_USER_FAILED);
						HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
						flightOrderDAO.update(flightOrder);
						/*
						 * 创建结算中心数据
						 */
						User user = userDAO.get(flightOrder.getUserID());
						COSAOF cosaof = new COSAOF();
						cosaof.setId(UUIDGenerator.getUUID());
						PassengerQO passengerQO = new PassengerQO();
						passengerQO.setFlightOrderID(flightOrder.getId());
						List<Passenger> passengers = passengerDAO.queryList(passengerQO);
						String passengerNames = "";
						for (Passenger passenger : passengers) {
							passengerNames=passengerNames+passenger.getPassengerName()+";";
						}
						cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
						cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
						cosaof.setOrderID(flightOrder.getId());
						cosaof.setCompanyID(user.getCompanyID());
						cosaof.setCompanyName(user.getCompanyName());
						cosaof.setDepartmentID(user.getDepartmentID());
						cosaof.setDepartmentName(user.getDepartmentName());
						cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
						if(flightOrder.getTotalPrice()!=-1){
							cosaof.setTotalPrice(flightOrder.getTotalPrice());
						}else{
							cosaof.setTotalPrice(flightOrder.getPlatTotalPrice());
						}
						cosaof.setCreateTime(flightOrder.getCreateTime());
						cosaof.setOrderStatus(flightOrder.getStatus());
						cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
						cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
						cosaof.setRefundPrice(flightOrder.getPlatTotalPrice());
						cosaof.setUserPayPrice(flightOrder.getPlatTotalPrice());
						cosaof.setUserRefundPrice(0.0);
						Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
						cosaof.setCasaofPrice(sum);
						HgLogger.getInstance().info("cs", "【部门经理审批未通过】【申请人付款】【提交原路退款申请失败】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
						cosaofdao.save(cosaof);
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
						costCenterOrder.setCostfPrice(0.0);
						costCenterOrder.setCreateTime(new Date());
						HgLogger.getInstance().info("cs", "【部门经理审批未通过】【申请人付款】【提交原路退款申请失败】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
						costCenterOrderDAO.save(costCenterOrder);*/
						break;
					}
					//这里用flightOrder.getPlatTotalPrice()是因为如果多订单合并的话支付宝传入的金额是总订单金额
					flightOrder.setUserPayPrice(flightOrder.getPlatTotalPrice());
					flightOrder.setTrade_no(alipayCommand.getTrade_no());
					flightOrder.setBuyer_email(alipayCommand.getBuyer_email());
					flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
					HgLogger.getInstance().info("cs", "【payGNFlight】【execute】【flightOrder】:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					/*
					 * 创建结算中心数据
					 */
					User user = userDAO.get(flightOrder.getUserID());
					COSAOF cosaof = new COSAOF();
					cosaof.setId(UUIDGenerator.getUUID());
					PassengerQO passengerQO = new PassengerQO();
					passengerQO.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers = passengerDAO.queryList(passengerQO);
					String passengerNames = "";
					for (Passenger passenger : passengers) {
						passengerNames=passengerNames+passenger.getPassengerName()+";";
					}
					cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
					cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					cosaof.setOrderID(flightOrder.getId());
					cosaof.setCompanyID(user.getCompanyID());
					cosaof.setCompanyName(user.getCompanyName());
					cosaof.setDepartmentID(user.getDepartmentID());
					cosaof.setDepartmentName(user.getDepartmentName());
					cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
					
					if(flightOrder.getTotalPrice()!=-1){
						cosaof.setTotalPrice(flightOrder.getTotalPrice());
					}else{
						cosaof.setTotalPrice(flightOrder.getPlatTotalPrice());
					}
					cosaof.setCreateTime(new Date());
					cosaof.setOrderStatus(flightOrder.getStatus());
					cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
					cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
					cosaof.setRefundPrice(flightOrder.getPlatTotalPrice());
					cosaof.setUserPayPrice(flightOrder.getPlatTotalPrice());
					cosaof.setUserRefundPrice(0.0);
					Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
					cosaof.setCasaofPrice(sum);
					HgLogger.getInstance().info("cs", "【部门经理审批未通过】【申请人付款】【提交原路退款申请成功】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.save(cosaof);
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
					costCenterOrder.setCostfPrice(0.0);
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【部门经理审批未通过】【申请人付款】【提交原路退款申请成功】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}else{
					//这里用flightOrder.getPlatTotalPrice()是因为如果多订单合并的话支付宝传入的金额是总订单金额
					flightOrder.setUserPayPrice(flightOrder.getPlatTotalPrice());
					flightOrder.setTrade_no(alipayCommand.getTrade_no());
					flightOrder.setBuyer_email(alipayCommand.getBuyer_email());
					flightOrder.setPayStatus(FlightPayStatus.USER_PAY_SUCCESS);
					HgLogger.getInstance().info("cs", "【payGNFlight】【flightOrder】:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					/*
					 * 创建结算中心数据
					 */
					User user = userDAO.get(flightOrder.getUserID());
					COSAOF cosaof = new COSAOF();
					cosaof.setId(UUIDGenerator.getUUID());PassengerQO passengerQO = new PassengerQO();
					passengerQO.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers = passengerDAO.queryList(passengerQO);
					String passengerNames = "";
					for (Passenger passenger : passengers) {
						passengerNames=passengerNames+passenger.getPassengerName()+";";
					}
					cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
					cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					cosaof.setOrderID(flightOrder.getId());
					cosaof.setCompanyID(user.getCompanyID());
					cosaof.setCompanyName(user.getCompanyName());
					cosaof.setDepartmentID(user.getDepartmentID());
					cosaof.setDepartmentName(user.getDepartmentName());
					cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
					if(flightOrder.getTotalPrice()!=-1){
						cosaof.setTotalPrice(flightOrder.getTotalPrice());
					}else{
						cosaof.setTotalPrice(flightOrder.getPlatTotalPrice());
					}
					cosaof.setCreateTime(new Date());
					cosaof.setOrderStatus(flightOrder.getStatus());
					cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
					cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
					cosaof.setRefundPrice(0.0);
					cosaof.setUserPayPrice(flightOrder.getPlatTotalPrice());
					cosaof.setUserRefundPrice(0.0);
//					cosaof.setCasaofPrice(flightOrder.getPlatTotalPrice()-cosaof.getRefundPrice());
					cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getUserPayPrice());
					HgLogger.getInstance().info("cs", "【支付成功】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.save(cosaof);
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
					costCenterOrder.setCostfPrice(flightOrder.getPlatTotalPrice());
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【支付成功】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}
			}
			return true;
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【payGNFlight】"+HgLogger.getStackTrace(e));
			return false;
		}
	}
	
	
	/**
	 * 
	 * @方法功能说明：接收到支付宝通知的退款成功通知 将订单的支付状态改为退款成功
	 * @修改者名字：cangs
	 * @修改时间：2015年8月18日下午1:46:00
	 * @修改内容：
	 * @参数：@param result_details
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean refundSuccess(String result_details){
		try{
			if(StringUtils.isBlank(result_details))
				return false;
			String[] trades=result_details.split("#");
			for(String trade:trades){
				String str=trade.split("\\$")[0];//获得交易退款数据集
				String[] data=str.split("\\^");//按照 "原付款支付宝交易号^退款总金额^退款状态" 格式分割为数组
				String tradeno=data[0];
				String sum=data[1];
				String status=data[2];
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】付款支付宝交易号:"+tradeno);
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】退款总金额:"+sum);
				HgLogger.getInstance().info("wuyg", "【接收到退款通知】退款状态"+status);
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setTradeno(tradeno);
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				HgLogger.getInstance().info("cs", "【refundSuccess】【即将要更新的订单】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				if(flightOrder!=null){
					flightOrder.setPayStatus(FlightPayStatus.REFUND_USER_SUCCESS);
//					flightOrder.setRefundPrice(Double.parseDouble(sum));
					HgLogger.getInstance().info("cs", "【refundSuccess】【更新的订单】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
				}
				/**
				 * 更改 退费订单所关联的 结算中心价格 新增订单流转记录信息
				 */
//				User user = userDAO.get(flightOrder.getUserID());
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
				cosaof.setUserRefundPrice(Double.parseDouble(sum));
				Double sum1 = cosaof.getTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
				cosaof.setCasaofPrice(sum1);
				HgLogger.getInstance().info("cs", "【无论哪种出票方式-支付方式为申请人支付】【原路退回支付宝账户（通知）成功】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
				costCenterOrder.setUserID(user.getId());
				costCenterOrder.setName(user.getName());
				costCenterOrder.setIdCardNO(user.getIdCardNO());
				costCenterOrder.setCompanyID(user.getCompanyID());
				costCenterOrder.setCompanyName(user.getCompanyName());
				costCenterOrder.setDepartmentID(user.getDepartmentID());
				costCenterOrder.setDepartmentName(user.getDepartmentName());
				costCenterOrder.setCostfPrice(Double.parseDouble(sum)*-1);
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【记录costCenterOrder】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
				
				Map<String, String> map = new HashMap<String, String>();
				map.put("actionName", "GetFlightOrderInfo");
				FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
				flightOrderInfoQO.setOrderID(flightOrder.getId());
				map.put("payload", JSON.toJSONString(flightOrderInfoQO));
				if(flightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_FAILED){
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票出票失败", map);
				}else if(flightOrder.getStatus()==FlightOrderStatus.CANCEL_TICKET_SUCCESS){
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票退票成功", map);
				}else{
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票退款成功", map);
				}
				final String text = "【"+SysProperties.getInstance().get("smsSign")+"】"+"您的订单"+flightOrder.getOrderNO()+"退款成功，票款会在7-15个工作日内返回到您的支付宝账户。客服电话0571-28280813";
				final String linkMobile = flightOrder.getLinkTelephone();
				HgLogger.getInstance().info("cs", "【FlightOrderService】"+"发送短信内容，电话"+flightOrder.getLinkTelephone());
				HgLogger.getInstance().info("cs", "【FlightOrderService】"+"发送短信内容，内容"+text);
				new Thread(){
					public void run(){
						try {
							smsUtils.sendSms(linkMobile, text);
						} catch (Exception e) {
							HgLogger.getInstance().info("cs", "【FlightOrderService】"+"发送短信异常，"+HgLogger.getStackTrace(e));
						}
					}
				}.start();
			}
			return true;
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【refundSuccess】"+HgLogger.getStackTrace(e));
			return false;
		}
	}

	@Override
	protected FlightOrderDAO getDao() {
		return flightOrderDAO;
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
