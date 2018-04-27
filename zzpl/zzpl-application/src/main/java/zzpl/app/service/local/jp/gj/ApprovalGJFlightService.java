package zzpl.app.service.local.jp.gj;

import hg.common.component.BaseCommand;
import hg.log.util.HgLogger;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.app.dao.jp.GJFlightCabinDAO;
import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.COSAOFDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.CostCenterOrderDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.GJFlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.plfx.gj.CreateJPOrderGJCommand;
import zzpl.pojo.command.jp.plfx.gj.PayToJPOrderGJCommand;
import zzpl.pojo.dto.jp.plfx.gj.CreateJPOrderGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.FlightPolicyGJDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJJPOrderContacterInfoDTO;
import zzpl.pojo.dto.jp.plfx.gj.GJPassengerBaseInfoDTO;
import zzpl.pojo.dto.jp.plfx.gj.PayToJPOrderGJDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.exception.jp.GJFlightException;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.jp.plfx.gj.JPPolicyGJQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

@Component("ApprovalGJFlightService")
public class ApprovalGJFlightService implements CommonService {

	@Autowired
	private GJFlightOrderDAO gjFlightOrderDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	@Autowired
	private GJFlightService gjFlightService;
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private COSAOFDAO cosaofdao;
	@Autowired
	private CostCenterDAO costCenterDAO;
	@Autowired
	private CostCenterOrderDAO costCenterOrderDAO;
	@Autowired
	private GJFlightCabinDAO gjFlightCabinDAO;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		HgLogger.getInstance().info("gk", "【ApprovalGJFlightService】【execute】"+JSON.toJSONString(command));
		//查询订单
		WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
		workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
		List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
		for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
			
			GJFlightOrder gjFlightOrder = gjFlightOrderDAO.get(workflowInstanceOrder.getOrderID());
			Hibernate.initialize(gjFlightOrder.getGjFlightCabins());
			PassengerQO passengerQO = new PassengerQO();
			passengerQO.setGjFlightOrderID(gjFlightOrder.getId());
			List<Passenger> passengers = passengerDAO.queryList(passengerQO);
			//根据订单信息组装查询订单所有的政策信息，进行验证
			JPPolicyGJQO jpPolicyGJQO = new JPPolicyGJQO();
			jpPolicyGJQO.setFlightCabinGroupToken(gjFlightOrder.getEncryptString());
			jpPolicyGJQO.setPeopleNum(passengers.size());
			FlightPolicyGJDTO flightPolicyGJDTO = new FlightPolicyGJDTO();
			try {
				flightPolicyGJDTO = gjFlightService.queryGJPolicy(jpPolicyGJQO);
			} catch (GJFlightException e) {
				HgLogger.getInstance().error("gk", "【ApprovalGJFlightService】【execute】政策查询失败:" + HgLogger.getStackTrace(e));
				return e.getCode().toString()+":"+e.getMessage();
			}
			
			gjFlightOrder.setEncryptString(flightPolicyGJDTO.getFlightPolicy().getFlightAndPolicyToken());
			//组装下单Command
			CreateJPOrderGJCommand createJPOrderGJCommand = new CreateJPOrderGJCommand();
			//1.订单编号
			createJPOrderGJCommand.setDealerOrderId(gjFlightOrder.getOrderNO());
			//2.联系人信息设置
			GJJPOrderContacterInfoDTO dto = new GJJPOrderContacterInfoDTO();
			dto.setContactName(gjFlightOrder.getLinkName());
			dto.setContactMobile(gjFlightOrder.getLinkTelephone());
			dto.setContactMail(gjFlightOrder.getLinkEmail());
			dto.setContactAddress(gjFlightOrder.getLinkAddress());
			createJPOrderGJCommand.setContacterInfo(dto);
			//3.航班舱位组合和对应政策token
			createJPOrderGJCommand.setFlightAndPolicyToken(gjFlightOrder.getEncryptString());
			//4.备注
			createJPOrderGJCommand.setRemark(gjFlightOrder.getNote());
			//5.乘机人信息
			List<GJPassengerBaseInfoDTO> passengerInfos = new ArrayList<GJPassengerBaseInfoDTO>();
			for (Passenger passenger : passengers) {
				GJPassengerBaseInfoDTO dto2 = new GJPassengerBaseInfoDTO();
				dto2.setPassengerName(passenger.getPassengerName());
				dto2.setIdNo(passenger.getIdNO());
				dto2.setIdType(new Integer(passenger.getIdType()));
				dto2.setMobile(passenger.getTelephone());
				dto2.setPassengerType(new Integer(passenger.getPassengerType()));
				dto2.setSex(passenger.getSex());
				passengerInfos.add(dto2);
			}
			createJPOrderGJCommand.setPassengerInfos(passengerInfos);
			
			CreateJPOrderGJDTO createJPOrderGJDTO = new CreateJPOrderGJDTO();
			try {
				//去分销下订单
				createJPOrderGJDTO = gjFlightService.createOrderGJ(createJPOrderGJCommand);
			} catch (GJFlightException e) {
				HgLogger.getInstance().error("gk", "【ApprovalGJFlightService】【execute】下单失败:" + HgLogger.getStackTrace(e));
				gjFlightOrder.setStatus(FlightOrderStatus.PRINT_TICKET_FAILED);
				gjFlightOrderDAO.update(gjFlightOrder);
				return  e.getCode().toString()+";"+e.getMessage();
			}
			
			HgLogger.getInstance().info("gk", "【ApprovalGJFlightService】【execute】下单成功:"+JSON.toJSONString(createJPOrderGJDTO));
			//下单成功后根据返回参数修改订单表中数据
			gjFlightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
			gjFlightOrder.setTotalPrice(createJPOrderGJDTO.getOrder().getDealerPayInfo().getTotalPrice());
			gjFlightOrder.setMemo(createJPOrderGJDTO.getOrder().getBaseInfo().getRemark());
			gjFlightOrder.setPayStatus(createJPOrderGJDTO.getOrder().getDealerPayInfo().getStatus());
			gjFlightOrder.setCurrentValue(createJPOrderGJDTO.getOrder().getStatus().getCurrentValue());
			gjFlightOrder.setExceptionOrder(createJPOrderGJDTO.getOrder().getExceptionInfo().getExceptionOrder());
			gjFlightOrder.setAdjustAmount(createJPOrderGJDTO.getOrder().getExceptionInfo().getAdjustAmount());
			
			gjFlightOrderDAO.update(gjFlightOrder);
			//支付
			PayToJPOrderGJCommand payToJPOrderGJCommand = new PayToJPOrderGJCommand();
			payToJPOrderGJCommand.setPlatformOrderId(gjFlightOrder.getOrderNO());
			payToJPOrderGJCommand.setTotalPrice(createJPOrderGJDTO.getOrder().getDealerPayInfo().getTotalPrice());
			HgLogger.getInstance().info("gk", "【ApprovalGJFlightService】【execute】:"+JSON.toJSONString(payToJPOrderGJCommand));
			PayToJPOrderGJDTO payToJPOrderGJDTO = new PayToJPOrderGJDTO();
			try {
				payToJPOrderGJDTO = gjFlightService.payToOrderGJ(payToJPOrderGJCommand);
			} catch (GJFlightException e) {
				HgLogger.getInstance().info("gk", "【ApprovalGJFlightService】【execute】自动扣款失败:"+JSON.toJSONString(payToJPOrderGJDTO));
				//支付状态
				gjFlightOrder.setPayStatus(FlightPayStatus.PAY_FAILED);
				//订单状态还未写
				gjFlightOrderDAO.update(gjFlightOrder);
				return  e.getCode().toString()+";"+e.getMessage();
			}
			HgLogger.getInstance().info("gk", "【ApprovalGJFlightService】【execute】自动扣款成功:"+JSON.toJSONString(payToJPOrderGJDTO));
			gjFlightOrder.setStatus(FlightPayStatus.PAY_SUCCESS);
			//订单状态还未写
			gjFlightOrderDAO.update(gjFlightOrder);
			
			/**
			 * 创建结算中心数据
			 *//*
			GJFlightCabinQO gjFlightCabinQO = new GJFlightCabinQO();
			gjFlightCabinQO.setOrderID(gjFlightOrder.getId());
			List<GJFlightCabin> gjFlightCabins = gjFlightCabinDAO.queryList(gjFlightCabinQO);
			User user = userDAO.get(gjFlightOrder.getUserID());
			COSAOF cosaof = new COSAOF();
			cosaof.setId(UUIDGenerator.getUUID());
			cosaof.setPassengerName(gjFlightOrder.getUserName());
			cosaof.setVoyage(gjFlightCabins.get(0).getOrgCity()+"--"+gjFlightCabins.get(0).getDstCity());
			cosaof.setOrderType(CostCenterOrder.GJ_FLIGHT_ORDER);
			cosaof.setOrderID(gjFlightOrder.getId());
			cosaof.setCompanyID(user.getCompanyID());
			cosaof.setCompanyName(user.getCompanyName());
			cosaof.setDepartmentID(user.getDepartmentID());
			cosaof.setDepartmentName(user.getDepartmentName());
			cosaof.setTotalPrice(gjFlightOrder.getTotalPrice());
			cosaof.setCreateTime(gjFlightOrder.getCreateTime());
			cosaof.setOrderStatus(gjFlightOrder.getStatus());
			cosaof.setCosaofStatus(1);
			cosaofdao.save(cosaof);
			*/
			/**
			 * 创建成本中心及订单中间表
			 */
			/*
			CostCenterOrder costCenterOrder = new CostCenterOrder();
			costCenterOrder.setId(UUIDGenerator.getUUID());
			costCenterOrder.setOrderType(CostCenterOrder.GJ_FLIGHT_ORDER);
			costCenterOrder.setOrderID(gjFlightOrder.getId());
			costCenterOrder.setUserID(user.getId());
			costCenterOrder.setName(user.getName());
			costCenterOrder.setIdCardNO(user.getIdCardNO());
			costCenterOrder.setCompanyID(user.getCompanyID());
			costCenterOrder.setCompanyName(user.getCompanyName());
			costCenterOrder.setDepartmentID(user.getDepartmentID());
			costCenterOrder.setDepartmentName(user.getDepartmentName());
			costCenterOrder.setTotalPrice(gjFlightOrder.getTotalPrice());
			costCenterOrder.setCostfPrice(gjFlightOrder.getTotalPrice());
			costCenterOrderDAO.save(costCenterOrder);*/
			
		}
		return CommonService.SUCCESS;
	}

}
