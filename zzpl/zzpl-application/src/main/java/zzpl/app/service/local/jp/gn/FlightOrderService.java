package zzpl.app.service.local.jp.gn;

import hg.common.component.BaseServiceImpl;
import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import redis.clients.jedis.JedisPool;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.CostCenterOrderDAO;
import zzpl.app.dao.user.DepartmentDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.TasklistWaitDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.push.PushService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.CostCenterOrder;
import zzpl.domain.model.user.Department;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.jp.plfx.JPNotifyMessageApiCommand;
import zzpl.pojo.dto.jp.status.COSAOFStatus;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.COSAOFQO;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class FlightOrderService extends
		BaseServiceImpl<FlightOrder, FlightOrderQO, FlightOrderDAO> {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private CostCenterDAO costCenterDAO;
	@Autowired
	private CostCenterOrderDAO costCenterOrderDAO;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private TasklistWaitDAO tasklistWaitDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private GNFlightService gnFlightService;
	@Autowired
	private PushService pushService;
	@Autowired
	private SMSUtils smsUtils;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private DepartmentDAO departmentDAO;
	
	//-------------------------------------------------------------以下为接收到分销通知的方法-------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------以下为接收到分销通知的方法-------------------------------------------------------------------------------------------------------------------
	//-------------------------------------------------------------以下为接收到分销通知的方法-------------------------------------------------------------------------------------------------------------------
	
	
	/**
	 * 
	 * @方法功能说明：出票成功通知
	 * @修改者名字：cangs
	 * @修改时间：2015年7月16日下午2:11:10
	 * @修改内容：
	 * @参数：@param jpNotifyMessageApiCommand
	 * @return:void
	 * @throws
	 */
	public void printTicket(JPNotifyMessageApiCommand jpNotifyMessageApiCommand){
		try{
			HgLogger.getInstance().info("cs", "【FlightOrderService】【printTicket】"+"jpNotifyMessageApiCommand:"+JSON.toJSONString(jpNotifyMessageApiCommand));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			String[] passengerName = jpNotifyMessageApiCommand.getPassengerName().split("\\^");
			String[] airID = jpNotifyMessageApiCommand.getAirId().split("\\^");
			for(int i=0;i<passengerName.length;i++){
				PassengerQO passengerQO = new PassengerQO();
				passengerQO.setPassengerName(passengerName[i]);
				passengerQO.setFlightOrderID(flightOrder.getId());
				Passenger passenger = passengerDAO.queryUnique(passengerQO);
				passenger.setAirID(airID[i]);
				passenger.setStatus(String.valueOf(FlightOrderStatus.PRINT_TICKET_SUCCESS));
				passengerDAO.update(passenger);
			}
			flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
			HgLogger.getInstance().info("cs", "【printTicket】【flightOrder】:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			
			/*
			 * 如果要是选择公司代扣 需要新增结算中心 和 订单流转记录
			 * 
			 * 如果是 申请人自己支付 则已经有 结算中心 和 订单流转记录了 需要 将结算价格更更新进去
			 */
			HgLogger.getInstance().info("gk", "【FlightOrderService】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【FlightOrderService】【FlightOrder.PAY_BY_TRAVEL】"+FlightOrder.PAY_BY_TRAVEL);
			if(flightOrder.getStatus()==FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS){
				if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_TRAVEL){
					//只在差旅出票的时候会有这种情况因为个人支付的需要等支付成功才能有任何操作，支付成功时就会生成结算中心数据
					HgLogger.getInstance().info("gk", "【cancelTicket】【正在出票时】【提交取消订单申请】【还没有真正去分销申请取消的时候出票了】");
					/*
					 * 创建结算中心数据
					 */
					COSAOF cosaof = new COSAOF();
					cosaof.setId(UUIDGenerator.getUUID());
					PassengerQO passengerQO1 = new PassengerQO();
					passengerQO1.setFlightOrderID(flightOrder.getId());
					List<Passenger> passengers1 = passengerDAO.queryList(passengerQO1);
					String passengerNames = "";
					for (Passenger passenger : passengers1) {
						passengerNames=passengerNames+passenger.getPassengerName()+";";
					}
					cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
					cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
					cosaof.setOrderID(flightOrder.getId());
					if(flightOrder.getCompanyID()!=null){
						cosaof.setCompanyID(flightOrder.getCompanyID());
						Company company = companyDAO.get(flightOrder.getCompanyID());
						if(company!=null){
							cosaof.setCompanyName(company.getCompanyName());
						}
					}
					if(flightOrder.getDepartmentID()!=null){
						cosaof.setDepartmentID(flightOrder.getDepartmentID());
						Department department = departmentDAO.get(flightOrder.getDepartmentID());
						if(department!=null){
							cosaof.setDepartmentName(department.getDepartmentName());
						}
					}
					cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
					
					cosaof.setTotalPrice(flightOrder.getTotalPrice());
					cosaof.setCreateTime(new Date());
					cosaof.setOrderStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS);
					cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
					cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
					cosaof.setUserPayPrice(0.0);
					cosaof.setRefundPrice(0.0);
					cosaof.setCasaofPrice(flightOrder.getPlatTotalPrice());
					HgLogger.getInstance().info("cs", "【cancelTicket】【正在出票时】【提交取消订单申请】【还没有真正去分销申请取消的时候出票了】"+"cosaof:"+JSON.toJSONString(cosaof));
					cosaofdao.save(cosaof);
				}
			}else if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_TRAVEL){
				HgLogger.getInstance().info("gk", "【cancelTicket】【申请人支付】");
				/*
				 * 创建结算中心数据
				 */
				COSAOF cosaof = new COSAOF();
				cosaof.setId(UUIDGenerator.getUUID());
				PassengerQO passengerQO1 = new PassengerQO();
				passengerQO1.setFlightOrderID(flightOrder.getId());
				List<Passenger> passengers1 = passengerDAO.queryList(passengerQO1);
				String passengerNames = "";
				for (Passenger passenger : passengers1) {
					passengerNames=passengerNames+passenger.getPassengerName()+";";
				}
				cosaof.setPassengerName(passengerNames.substring(0, passengerNames.length()-1));
				cosaof.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
				cosaof.setOrderID(flightOrder.getId());
				if(flightOrder.getCompanyID()!=null){
					cosaof.setCompanyID(flightOrder.getCompanyID());
					Company company = companyDAO.get(flightOrder.getCompanyID());
					if(company!=null){
						cosaof.setCompanyName(company.getCompanyName());
					}
				}
				if(flightOrder.getDepartmentID()!=null){
					cosaof.setDepartmentID(flightOrder.getDepartmentID());
					Department department = departmentDAO.get(flightOrder.getDepartmentID());
					if(department!=null){
						cosaof.setDepartmentName(department.getDepartmentName());
					}
				}
				cosaof.setVoyage(flightOrder.getOrgCityName()+"--"+flightOrder.getDstCityName());
				
				cosaof.setTotalPrice(flightOrder.getTotalPrice());
				cosaof.setCreateTime(new Date());
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
				cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
				cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
				cosaof.setUserPayPrice(0.0);
				cosaof.setRefundPrice(0.0);
				cosaof.setCasaofPrice(flightOrder.getPlatTotalPrice());
				HgLogger.getInstance().info("cs", "【自动出票成功】【公司代扣】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
				if(user!=null){
					costCenterOrder.setUserID(user.getId());
					costCenterOrder.setName(user.getName());
					costCenterOrder.setIdCardNO(user.getIdCardNO());
				}
				if(flightOrder.getCompanyID()!=null){
					costCenterOrder.setCompanyID(flightOrder.getCompanyID());
					Company company = companyDAO.get(flightOrder.getCompanyID());
					if(company!=null){
						costCenterOrder.setCompanyName(company.getCompanyName());
					}
				}
				if(flightOrder.getDepartmentID()!=null){
					costCenterOrder.setDepartmentID(flightOrder.getDepartmentID());
					Department department = departmentDAO.get(flightOrder.getDepartmentID());
					if(department!=null){
						costCenterOrder.setDepartmentName(department.getDepartmentName());
					}
				}
				costCenterOrder.setCostfPrice(flightOrder.getPlatTotalPrice());
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【自动出票成功】【公司代扣】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
			}else if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【cancelTicket】【申请人支付】");
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setAirID(airID[0]);
				if(jpNotifyMessageApiCommand.getNewPnr()!=null&&StringUtils.isNotBlank(jpNotifyMessageApiCommand.getNewPnr())){
					cosaof.setPnr(jpNotifyMessageApiCommand.getNewPnr());
				}
				cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getUserPayPrice());
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS);
				HgLogger.getInstance().info("cs", "【自动出票成功】【申请人支付】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
				cosaofdao.update(cosaof);
				/*
				 * 创建成本中心订单记录
				 */
				/*User user = userDAO.get(flightOrder.getUserID());
				CostCenterOrder costCenterOrder = new CostCenterOrder();
				costCenterOrder.setId(UUIDGenerator.getUUID());
				costCenterOrder.setOrderNO(flightOrder.getOrderNO());
				if (StringUtils.isNotBlank(flightOrder.getCostCenterID())) {
					CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
					costCenterOrder.setCostCenter(costCenter);
				}
				costCenterOrder.setOrderType(CostCenterOrder.GN_FLIGHT_ORDER);
				costCenterOrder.setOrderID(flightOrder.getId());
				costCenterOrder.setOrderNO(flightOrder.getOrderNO());
				if(user!=null){
					costCenterOrder.setUserID(user.getId());
					costCenterOrder.setName(user.getName());
					costCenterOrder.setIdCardNO(user.getIdCardNO());
				}
				if(flightOrder.getCompanyID()!=null){
					costCenterOrder.setCompanyID(flightOrder.getCompanyID());
					Company company = companyDAO.get(flightOrder.getCompanyID());
					if(company!=null){
						costCenterOrder.setCompanyName(company.getCompanyName());
					}
				}
				if(flightOrder.getDepartmentID()!=null){
					costCenterOrder.setDepartmentID(flightOrder.getDepartmentID());
					Department department = departmentDAO.get(flightOrder.getDepartmentID());
					if(department!=null){
						costCenterOrder.setDepartmentName(department.getDepartmentName());
					}
				}
				costCenterOrder.setCostfPrice(flightOrder.getPlatTotalPrice());
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【自动出票成功】【申请人支付】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
			}
			Map<String, String> map = new HashMap<String, String>();
			map.put("actionName", "GetFlightOrderInfo");
			FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
			flightOrderInfoQO.setOrderID(flightOrder.getId());
			map.put("payload", JSON.toJSONString(flightOrderInfoQO));
			pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票已出票", map);
			final String text = "【"+SysProperties.getInstance().get("smsSign")+"】起飞时间"+flightOrder.getStartTime().toString().substring(0, flightOrder.getStartTime().toString().length()-5)+"降落时间"+flightOrder.getEndTime().toString().substring(0, flightOrder.getEndTime().toString().length()-5)+"的"+flightOrder.getAirCompanyName()+flightOrder.getFlightNO()+flightOrder.getOrgCityName()+"-"+flightOrder.getDstCityName()+"已出票，"+passengerName[0]+"票号"+airID[0]+"。请提前2个小时到机场办理乘机手续。客服电话0571-28280813。";
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
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【printTicket】"+HgLogger.getStackTrace(e));
		}
	}

	/**
	 * 
	 * @方法功能说明：取消订单通知
	 * @修改者名字：cangs
	 * @修改时间：2015年7月16日下午2:12:52
	 * @修改内容：
	 * @参数：@param jpNotifyMessageApiCommand
	 * @return:void
	 * @throws
	 */
	public String cancelTicket(JPNotifyMessageApiCommand jpNotifyMessageApiCommand){
		try{
			HgLogger.getInstance().info("cs", "【FlightOrderService】【cancelTicket】"+"jpNotifyMessageApiCommand:"+JSON.toJSONString(jpNotifyMessageApiCommand));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			HgLogger.getInstance().info("gk", "【cancelTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			//如果是手动出票 取消订单的通知不更改本地订单的状态
			HgLogger.getInstance().info("cs", "【FlightOrderService】【cancelTicket】"+"自动出票修改订单等状态");
			String[] passengerName = jpNotifyMessageApiCommand.getPassengerName().split("\\^");
			for(int i=0;i<passengerName.length;i++){
				PassengerQO passengerQO = new PassengerQO();
				passengerQO.setPassengerName(passengerName[i]);
				passengerQO.setFlightOrderID(flightOrder.getId());
				Passenger passenger = passengerDAO.queryUnique(passengerQO);
				passenger.setStatus(String.valueOf(FlightOrderStatus.CANCEL_TICKET_SUCCESS));
				passengerDAO.update(passenger);
			}
			
			
			/**
			 * 如果要是选择公司代扣 更新结算中心 和 新增订单流转记录
			 * 
			 * 如果是 申请人自己支付 则提出退款 更新结算中心 和 新增订单流转记录了 结算价格 计算规则 （支付价-退款金额）-（用户支付价-用户退款金额） 现在按照用户100%退款
			 */
			HgLogger.getInstance().info("gk", "【cancelTicket】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【cancelTicket】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				//如果是订单状态是 提交取消订单成功 或者是提交取消订单失败 这里做订单状态 支付状态的更改 因为不是这种状态有可能是为了看机票成本价去分销下单 然后超时 分销取消订单 如果是后者这种情况 维持原来订单状态 和 支付状态 
				//具体结合DepartmentCancelService 这个方法注释分析
				HgLogger.getInstance().info("gk", "【cancelTicket】【申请人支付】");
				if(flightOrder.getStatus()==FlightOrderStatus.CANCEL_APPROVAL_SUCCESS||flightOrder.getStatus()==FlightOrderStatus.CANCEL_TICKET_FAILED){
					HgLogger.getInstance().info("gk", "【cancelTicket】【去分销申请成功】");
					flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
					//这个价格因为在订单列表中要展示给用户 所以要用分销加价 实际上易行推给分销 分销推给本平台的是totalprice
					flightOrder.setRefundPrice(flightOrder.getPlatTotalPrice());
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					HgLogger.getInstance().info("gk", "【cancelTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					cosaof.setRefundPrice(cosaof.getPlatTotalPrice());
					cosaof.setUserRefundPrice(0.0);
					Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
					cosaof.setCasaofPrice(sum);
					HgLogger.getInstance().info("cs", "【退票---取消订单---自动出票】【原路退回支付宝账户】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
					costCenterOrder.setCostfPrice(flightOrder.getRefundPrice()*-1);
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【退票---取消订单---自动出票】【原路退回支付宝账户】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}else if(flightOrder.getStatus()==FlightOrderStatus.APPROVAL_SUCCESS){
					HgLogger.getInstance().info("gk", "【cancelTicket】【超时未支付，自动取消订单】");
//					flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
					flightOrder.setTotalPrice(-1.0);
					flightOrderDAO.update(flightOrder);
				}
			}else{	
				HgLogger.getInstance().info("gk", "【cancelTicket】【公司代扣】");
				//为什么有这个判断 参照上面的注释
				if(flightOrder.getStatus()==FlightOrderStatus.CANCEL_APPROVAL_SUCCESS||flightOrder.getStatus()==FlightOrderStatus.CANCEL_TICKET_FAILED){
					HgLogger.getInstance().info("gk", "【cancelTicket】【去分销申请成功】");
					flightOrder.setPayStatus(FlightPayStatus.REFUND_SUCCESS);
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					//这个价格因为在订单列表中要展示给用户 所以要用分销加价 实际上易行推给分销 分销推给本平台的是totalprice
					flightOrder.setRefundPrice(flightOrder.getPlatTotalPrice());
					HgLogger.getInstance().info("gk", "【cancelTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					/**
					 * 取消订单有一种情况就是 用户选择代扣然后 自动出票 差旅管理员同意出票后 机票一直没有出 然后就是等待出票 计算中心 成订单记录表中没有数据 所以在这里就不更新 结算中心 和 也不再订单记录表中新建记录了
					 * 这块 和 ConfirmFlightOrderService 这个类一块看
					 */
					if(cosaof!=null){
						cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
						cosaof.setRefundPrice(cosaof.getPlatTotalPrice());
						cosaof.setUserRefundPrice(0.0);
						cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice());
						HgLogger.getInstance().info("cs", "【退票---取消订单---自动出票】【公司代扣】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
						costCenterOrder.setCostfPrice(flightOrder.getRefundPrice()*-1);
						costCenterOrder.setCreateTime(new Date());
						HgLogger.getInstance().info("cs", "【记录costCenterOrder】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
						costCenterOrderDAO.save(costCenterOrder);*/
					}else{
						//直到订单取消也没生成结算中心数据 给他补上
						/*
						 * 创建结算中心数据
						 */
						User user = userDAO.get(flightOrder.getUserID());
						cosaof = new COSAOF();
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
						
						cosaof.setTotalPrice(flightOrder.getTotalPrice());
						cosaof.setCreateTime(new Date());
						cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
						cosaof.setCosaofStatus(COSAOFStatus.NOT_SETTLED);
						cosaof.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
						cosaof.setRefundPrice(flightOrder.getPlatTotalPrice());
						cosaof.setUserPayPrice(0.0);
						cosaof.setUserRefundPrice(0.0);
						Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
						cosaof.setCasaofPrice(sum);
						HgLogger.getInstance().info("cs", "【退票---取消订单---自动出票】【公司代扣】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
						cosaofdao.save(cosaof);
					}
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("actionName", "GetFlightOrderInfo");
					FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
					flightOrderInfoQO.setOrderID(flightOrder.getId());
					map.put("payload", JSON.toJSONString(flightOrderInfoQO));
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票退票成功", map);
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
					return flightOrder.getId();
				}else if(flightOrder.getStatus()==FlightOrderStatus.APPROVAL_SUCCESS){
					HgLogger.getInstance().info("gk", "【cancelTicket】【超时未支付，自动取消订单】");
					flightOrder.setTotalPrice(-1.0);
					flightOrderDAO.update(flightOrder);
				}
			}
			return flightOrder.getId();
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【cancelTicket】"+HgLogger.getStackTrace(e));
			return "";
		}
		
	}
	
	/**
	 * 
	 * @方法功能说明：退费票通知
	 * @修改者名字：cangs
	 * @修改时间：2015年7月16日下午2:13:38
	 * @修改内容：
	 * @参数：@param jpNotifyMessageApiCommand
	 * @return:void
	 * @throws
	 */
	public String refundTicket(JPNotifyMessageApiCommand jpNotifyMessageApiCommand){
		try{
			HgLogger.getInstance().info("cs", "【FlightOrderService】【refundTicket】"+"jpNotifyMessageApiCommand:"+JSON.toJSONString(jpNotifyMessageApiCommand));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			HgLogger.getInstance().info("gk", "【FlightOrderService】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			PassengerQO passengerQO = new PassengerQO();
			passengerQO.setFlightOrderID(flightOrder.getId());
			List<Passenger> passengers = passengerDAO.queryList(passengerQO);
			String[] airID = jpNotifyMessageApiCommand.getAirId().split("\\^");
			Integer refundMSG = null;
			for (Passenger passenger : passengers) {
				for(int i=0;i<airID.length;i++){
					if(StringUtils.equals(passenger.getAirID(), airID[i])){
						if(StringUtils.equals(String.valueOf(jpNotifyMessageApiCommand.getRefundStatus()),"1")){
							passenger.setStatus(String.valueOf(FlightOrderStatus.CANCEL_TICKET_SUCCESS));
							refundMSG = FlightOrderStatus.CANCEL_TICKET_SUCCESS;
						}else{
							passenger.setStatus(String.valueOf(FlightOrderStatus.CANCEL_TICKET_FAILED));
							refundMSG = FlightOrderStatus.CANCEL_TICKET_SUCCESS;
						}
						passengerDAO.update(passenger);
					}
				}
			}
			/**
			 * 如果要是选择公司代扣 更新结算中心 和 新增订单流转记录
			 * 
			 * 如果是 申请人自己支付 则提出退款 更新结算中心 和 新增订单流转记录了 结算价格 计算规则 （支付价-退款金额）-（用户支付价-用户退款金额） 现在按照用户100%退款
			 */
			HgLogger.getInstance().info("gk", "【FlightOrderService】【refundMSG】"+refundMSG);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【FlightOrderService】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			//如果退款成功 执行操作
			if(refundMSG==FlightOrderStatus.CANCEL_TICKET_SUCCESS){
				HgLogger.getInstance().info("gk", "【FlightOrderService】【退款成功】");
				if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
					HgLogger.getInstance().info("gk", "【FlightOrderService】【退款申请人支付】");
					flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
					//这个价格因为在订单列表中要展示给用户 所以要用分销加价 实际上易行推给分销 分销推给本平台的是totalprice
					flightOrder.setRefundPrice(jpNotifyMessageApiCommand.getRefundPrice());
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					flightOrder.setRefuseMemo("退款金额:"+jpNotifyMessageApiCommand.getRefundPrice()+",退款手续费:"+jpNotifyMessageApiCommand.getProcedures()+","+jpNotifyMessageApiCommand.getRefuseMemo());
					HgLogger.getInstance().info("gk", "【refundTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					cosaof.setRefundPrice(jpNotifyMessageApiCommand.getRefundPrice());
					cosaof.setUserRefundPrice(0.0);
					Double sum = cosaof.getTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
					cosaof.setCasaofPrice(sum);
					HgLogger.getInstance().info("cs", "【退票---退废票---自动出票】【原路退回支付宝账户】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
					costCenterOrder.setCostfPrice(jpNotifyMessageApiCommand.getRefundPrice()*-1);
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【退票--退废票---自动出票】【原路退回支付宝账户】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
				}else{
					HgLogger.getInstance().info("gk", "【FlightOrderService】【公司代扣支付】");
					flightOrder.setPayStatus(FlightPayStatus.REFUND_SUCCESS);
					//这个价格因为在订单列表中要展示给用户 所以要用分销加价 实际上易行推给分销 分销推给本平台的是totalprice
					flightOrder.setRefundPrice(jpNotifyMessageApiCommand.getRefundPrice());
					flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					flightOrder.setRefuseMemo("退款金额:"+jpNotifyMessageApiCommand.getRefundPrice()+",退款手续费:"+jpNotifyMessageApiCommand.getProcedures()+","+jpNotifyMessageApiCommand.getRefuseMemo());
					HgLogger.getInstance().info("gk", "【refundTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
					flightOrderDAO.update(flightOrder);
					
					COSAOFQO cosaofqo = new COSAOFQO();
					cosaofqo.setOrderID(flightOrder.getId());
					COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
					cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS);
					cosaof.setRefundPrice(jpNotifyMessageApiCommand.getRefundPrice());
					cosaof.setUserRefundPrice(0.0);
					cosaof.setCasaofPrice(cosaof.getPlatTotalPrice()-cosaof.getRefundPrice());
					HgLogger.getInstance().info("cs", "【退票---退废票---自动出票】【原路退回支付宝账户】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
					costCenterOrder.setCostfPrice(jpNotifyMessageApiCommand.getRefundPrice()*-1);
					costCenterOrder.setCreateTime(new Date());
					HgLogger.getInstance().info("cs", "【记录costCenterOrder】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
					costCenterOrderDAO.save(costCenterOrder);*/
					
					Map<String, String> map = new HashMap<String, String>();
					map.put("actionName", "GetFlightOrderInfo");
					FlightOrderInfoQO flightOrderInfoQO = new FlightOrderInfoQO();
					flightOrderInfoQO.setOrderID(flightOrder.getId());
					map.put("payload", JSON.toJSONString(flightOrderInfoQO));
					pushService.pushAndroidByDeviceID(flightOrder.getUserID(),"您有一张机票退票成功", map);
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
					return flightOrder.getId();
				}
				return flightOrder.getId();
			}else{
				HgLogger.getInstance().info("gk", "【FlightOrderService】【退款失败】");
				//分销退款失败
				flightOrder.setStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
				HgLogger.getInstance().info("gk", "【refundTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.CANCEL_TICKET_FAILED);
				HgLogger.getInstance().info("cs", "【退票---退废票---自动出票】【原路退回支付宝账户】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
				cosaofdao.update(cosaof);
				return "";
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【refundTicket】"+HgLogger.getStackTrace(e));
			return "";
		}
	}
	
	/**
	 * 
	 * @方法功能说明：拒绝出票
	 * @修改者名字：cangs
	 * @修改时间：2015年7月16日下午2:59:51
	 * @修改内容：
	 * @参数：@param jpNotifyMessageApiCommand
	 * @return:void
	 * @throws
	 */
	public String refuseTicket(JPNotifyMessageApiCommand jpNotifyMessageApiCommand){
		try{
			HgLogger.getInstance().info("cs", "【FlightOrderService】【refuseTicket】"+"jpNotifyMessageApiCommand:"+JSON.toJSONString(jpNotifyMessageApiCommand));
			FlightOrderQO flightOrderQO = new FlightOrderQO();
			flightOrderQO.setOrderNO(jpNotifyMessageApiCommand.getDealerOrderCode());
			FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
			HgLogger.getInstance().info("gk", "【refuseTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
			String[] passengerName = jpNotifyMessageApiCommand.getPassengerName().split("\\^");
			for(int i=0;i<passengerName.length;i++){
				PassengerQO passengerQO = new PassengerQO();
				passengerQO.setPassengerName(passengerName[i]);
				passengerQO.setFlightOrderID(flightOrder.getId());
				Passenger passenger = passengerDAO.queryUnique(passengerQO);
				passenger.setStatus(String.valueOf(FlightOrderStatus.PRINT_TICKET_FAILED));
				passengerDAO.update(passenger);
			}
			/*
			 * 如果要是选择公司代扣 更新订单状态
			 * 
			 * 如果是 申请人自己支付 则提出退款 更新结算中心 和 新增订单流转记录了 结算价格 计算规则 （支付价-退款金额）-（用户支付价-用户退款金额） 现在按照用户100%退款
			 */
			HgLogger.getInstance().info("gk", "【FlightOrderService】【flightOrder.getPayType()】"+flightOrder.getPayType());
			HgLogger.getInstance().info("gk", "【FlightOrderService】【FlightOrder.PAY_BY_SELF.toString()】"+FlightOrder.PAY_BY_SELF.toString());
			if(Integer.parseInt(flightOrder.getPayType())==FlightOrder.PAY_BY_SELF){
				HgLogger.getInstance().info("gk", "【FlightOrderService】【申请人支付支付】");
				//这个价格因为在订单列表中要展示给用户 所以要用分销加价 实际上易行推给分销 分销推给本平台的是totalprice
				flightOrder.setRefundPrice(flightOrder.getPlatTotalPrice());
				flightOrder.setPayStatus(FlightPayStatus.USER_REFUND);
				flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				HgLogger.getInstance().info("gk", "【refuseTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				cosaof.setRefundPrice(cosaof.getPlatTotalPrice());
				cosaof.setUserRefundPrice(0.0);
				Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
				cosaof.setCasaofPrice(sum);
				HgLogger.getInstance().info("cs", "【退票---拒绝出票---自动出票】【原路退回支付宝账户】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
				costCenterOrder.setCostfPrice(flightOrder.getRefundPrice()*-1);
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）失败】【记录订单流转信息】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
			}else{
				HgLogger.getInstance().info("gk", "【FlightOrderService】【公司代扣支付】");
				flightOrder.setRefundPrice(flightOrder.getPlatTotalPrice());
				flightOrder.setPayStatus(FlightPayStatus.REFUND_SUCCESS);
				flightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				HgLogger.getInstance().info("gk", "【refuseTicket】"+"flightOrder:"+JSON.toJSONString(flightOrder));
				flightOrderDAO.update(flightOrder);
				
				COSAOFQO cosaofqo = new COSAOFQO();
				cosaofqo.setOrderID(flightOrder.getId());
				COSAOF cosaof = cosaofdao.queryUnique(cosaofqo);
				cosaof.setOrderStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				cosaof.setRefundPrice(cosaof.getPlatTotalPrice());
				cosaof.setUserRefundPrice(cosaof.getPlatTotalPrice());
				Double sum = cosaof.getPlatTotalPrice()-cosaof.getRefundPrice()-cosaof.getUserPayPrice()+cosaof.getUserRefundPrice();
				cosaof.setCasaofPrice(sum);
				HgLogger.getInstance().info("cs", "【拒绝出票】【原路退回支付宝账户（提交）失败】【更新】【记录结算中心】"+"cosaof:"+JSON.toJSONString(cosaof));
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
				costCenterOrder.setCostfPrice(flightOrder.getRefundPrice()*-1);
				costCenterOrder.setCreateTime(new Date());
				HgLogger.getInstance().info("cs", "【记录costCenterOrder】"+"costCenterOrder:"+JSON.toJSONString(costCenterOrder));
				costCenterOrderDAO.save(costCenterOrder);*/
				
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
				return flightOrder.getId();
			}
			return flightOrder.getId();
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【refuseTicket】"+HgLogger.getStackTrace(e));
			return "";
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
}
