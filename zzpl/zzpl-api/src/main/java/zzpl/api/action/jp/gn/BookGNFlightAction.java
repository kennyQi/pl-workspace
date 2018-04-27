package zzpl.api.action.jp.gn;

import hg.common.util.BeanMapperUtils;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import zzpl.api.action.CommonAction;
import zzpl.api.client.base.ApiRequest;
import zzpl.api.client.base.ApiResponse;
import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.api.client.request.jp.GNTeamBookCommand;
import zzpl.api.client.request.jp.GNTeamBookCommand.GNTeamPassengerDTO;
import zzpl.api.client.response.jp.GNTeamBookResponse;
import zzpl.app.service.local.jp.gn.FlightOrderService;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.util.OrderUtil;
import zzpl.app.service.local.workflow.TasklistWaitService;
import zzpl.app.service.local.workflow.WorkflowInstanceOrderService;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.user.User;
import zzpl.pojo.command.jp.BookGNFlightCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.exception.workflow.WorkflowException;

import com.alibaba.fastjson.JSON;
@Component("BookGNFlightAction")
public class BookGNFlightAction implements CommonAction{

	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private UserService userService;
	@Autowired
	private TasklistWaitService tasklistWaitService;
	@Autowired
	private WorkflowInstanceOrderService workflowInstanceOrderService;
	@Autowired
	private FlightOrderService flightOrderService;
	/**
	 * 起草环节为1
	 */
	public final static Integer STEP_1 = 1;
	
	/**
	 * 办结流程为99
	 */
	public final static Integer STEP_99 = 99;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GNTeamBookResponse gnTeamBookResponse = new GNTeamBookResponse();
		try {
			GNTeamBookCommand gnTeamBookCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GNTeamBookCommand.class);
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"gnTeamBookCommand:"+JSON.toJSONString(gnTeamBookCommand));
			//1：把流程的command转出来
			SendCommand sendCommand = new SendCommand();
			sendCommand.setWorkflowID(gnTeamBookCommand.getWorkflowID());
			sendCommand.setCurrentUserID(gnTeamBookCommand.getUserID());
			sendCommand.setCurrentStepNO(gnTeamBookCommand.getCurrentStepNO());
			sendCommand.setNextUserIDs(gnTeamBookCommand.getNextUserIDs());
			sendCommand.setWorkflowID(gnTeamBookCommand.getWorkflowID());
			sendCommand.setNextStepNO(gnTeamBookCommand.getNextStepNO());
			
			for (String userID : gnTeamBookCommand.getNextUserIDs()) {
				workflowStepService.smsUser(userID);
			}
			//2：要执行的service方法
			String[] services={"BookGNFlightService"};
			//3：把service的command转出来
			String unionOrderNO ="";
			String returnOrderNO ="";
			String orderNO = "";
			//为了判断是否要生成订单号
			boolean flag = false;
			if(gnTeamBookCommand.getTeamPassengerDTOs().size()==1){
				orderNO = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0);
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"orderNO【1】:"+JSON.toJSONString(orderNO));
				returnOrderNO = orderNO;
				//创建下一个序列号
				this.setNextOrderSequence();
			}else if(gnTeamBookCommand.getTeamPassengerDTOs().size()>1){
				flag = true;
				unionOrderNO = "UN_"+OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0);
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"unionOrderNO【2】:"+JSON.toJSONString(unionOrderNO));
				returnOrderNO = unionOrderNO;
				//创建下一个序列号
				this.setNextOrderSequence();
			}
			for(GNTeamPassengerDTO gnTeamPassengerDTO:gnTeamBookCommand.getTeamPassengerDTOs()){
				BookGNFlightCommand bookGNFlightCommand = new BookGNFlightCommand();
				bookGNFlightCommand.setAirComp(gnTeamBookCommand.getFlightGNListDTO().getAirComp());
				bookGNFlightCommand.setAirCompanyName(gnTeamBookCommand.getFlightGNListDTO().getAirCompanyName());
				bookGNFlightCommand.setArrivalAirport(gnTeamBookCommand.getFlightGNListDTO().getArrivalAirport());
				bookGNFlightCommand.setArrivalTerm(gnTeamBookCommand.getFlightGNListDTO().getArrivalTerm());
				bookGNFlightCommand.setBuildFee(gnTeamBookCommand.getFlightGNListDTO().getBuildFee());
				bookGNFlightCommand.setCabinDiscount(gnTeamBookCommand.getFlightGNListDTO().getCabinDiscount());
				bookGNFlightCommand.setCabinName(gnTeamBookCommand.getFlightGNListDTO().getCabinName());
				bookGNFlightCommand.setCabinCode(gnTeamBookCommand.getFlightGNListDTO().getCabinCode());
				BigDecimal ibePrice = new BigDecimal(gnTeamBookCommand.getFlightGNListDTO().getIbePrice());
				BigDecimal sum = new BigDecimal(0);
				if(gnTeamBookCommand.getFlightGNListDTO().getOilFee()!=null&&StringUtils.isNotBlank(gnTeamBookCommand.getFlightGNListDTO().getOilFee().toString())){
					BigDecimal oilFee = new BigDecimal(gnTeamBookCommand.getFlightGNListDTO().getOilFee());
					sum=sum.add(oilFee);
				}
				if(gnTeamBookCommand.getFlightGNListDTO().getBuildFee()!=null&&StringUtils.isNotBlank(gnTeamBookCommand.getFlightGNListDTO().getBuildFee().toString())){
					BigDecimal buildFee = new BigDecimal(gnTeamBookCommand.getFlightGNListDTO().getBuildFee());
					sum=sum.add(buildFee);
				}
				bookGNFlightCommand.setCommitPrice(ibePrice.add(sum).doubleValue());
				bookGNFlightCommand.setTotalPrice(ibePrice.add(sum).doubleValue());
				bookGNFlightCommand.setCreateTime(new Date());
				bookGNFlightCommand.setDepartAirport(gnTeamBookCommand.getFlightGNListDTO().getDepartAirport());
				bookGNFlightCommand.setDepartTerm(gnTeamBookCommand.getFlightGNListDTO().getDepartTerm());
				bookGNFlightCommand.setDstCity(gnTeamBookCommand.getFlightGNListDTO().getDstCity());
				bookGNFlightCommand.setDstCityName(gnTeamBookCommand.getFlightGNListDTO().getDstCityName());
				bookGNFlightCommand.setEndTime(gnTeamBookCommand.getFlightGNListDTO().getEndTime());
				bookGNFlightCommand.setEncryptString(gnTeamBookCommand.getFlightGNListDTO().getEncryptString());
				bookGNFlightCommand.setFlightNO(gnTeamBookCommand.getFlightGNListDTO().getFlightNO());
				bookGNFlightCommand.setIbePrice(gnTeamBookCommand.getFlightGNListDTO().getIbePrice());
				bookGNFlightCommand.setLinkEmail(gnTeamBookCommand.getLinkEmail());
				bookGNFlightCommand.setLinkName(gnTeamBookCommand.getLinkName());
				bookGNFlightCommand.setLinkTelephone(gnTeamBookCommand.getLinkTelephone());
				bookGNFlightCommand.setNote(gnTeamBookCommand.getNote());
				bookGNFlightCommand.setOilFee(gnTeamBookCommand.getFlightGNListDTO().getOilFee());
				if(flag){
					orderNO = OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0);
					HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"orderNO【3】:"+JSON.toJSONString(orderNO));
					//创建下一个序列号
					this.setNextOrderSequence();
				}
				bookGNFlightCommand.setOrderNO(orderNO);
				bookGNFlightCommand.setOrgCity(gnTeamBookCommand.getFlightGNListDTO().getOrgCity());
				bookGNFlightCommand.setOrgCityName(gnTeamBookCommand.getFlightGNListDTO().getOrgCityName());
				GNPassengerDTO gnPassengerDTO = BeanMapperUtils.getMapper().map(gnTeamPassengerDTO, GNPassengerDTO.class);
				List<GNPassengerDTO> gnPassengerDTOs = new ArrayList<GNPassengerDTO>();
				gnPassengerDTOs.add(gnPassengerDTO);
				bookGNFlightCommand.setPassengerListJSON(JSON.toJSONString(gnPassengerDTOs));
				bookGNFlightCommand.setCostCenterID(gnTeamPassengerDTO.getCostCenterID());
				bookGNFlightCommand.setPlaneType(gnTeamBookCommand.getFlightGNListDTO().getPlaneType());
				bookGNFlightCommand.setReimbursementStatus(gnTeamBookCommand.getReimbursementStatus());
				bookGNFlightCommand.setTickType(Integer.parseInt(SysProperties.getInstance().get("ticketType")));
				bookGNFlightCommand.setStartTime(gnTeamBookCommand.getFlightGNListDTO().getStartTime());
				bookGNFlightCommand.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS);
				bookGNFlightCommand.setPayStatus(FlightPayStatus.WAIT_TO_PAY);
				bookGNFlightCommand.setPayType(gnTeamBookCommand.getPayType());
				bookGNFlightCommand.setUserID(gnTeamBookCommand.getUserID());
				User user = userService.get(gnTeamBookCommand.getUserID());
				bookGNFlightCommand.setUserName(user.getName());
				bookGNFlightCommand.setCompanyID(user.getCompanyID());
				bookGNFlightCommand.setDepartmentID(user.getDepartmentID());
				bookGNFlightCommand.setPlatTotalPrice(ibePrice.add(sum).doubleValue());
				bookGNFlightCommand.setUnionOrderNO(unionOrderNO);
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"sendCommand:"+JSON.toJSONString(sendCommand));
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"services:"+JSON.toJSONString(services));
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"bookGNFlightCommand:"+JSON.toJSONString(bookGNFlightCommand));
				workflowStepService.send(sendCommand, services, bookGNFlightCommand);
				gnTeamBookResponse.setResult(ApiResponse.RESULT_CODE_OK);
				gnTeamBookResponse.setMessage("订单提交成功");
				
				
				gnTeamBookResponse.setOrderNO(returnOrderNO);
				orderNO="";
			}
		} catch (WorkflowException e) {
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"国内(多人)机票下单失败，"+HgLogger.getStackTrace(e));
			gnTeamBookResponse.setResult(e.getCode());
			gnTeamBookResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】"+"gnTeamBookResponse:"+JSON.toJSONString(gnTeamBookResponse));
		return JSON.toJSONString(gnTeamBookResponse);
	}

	/**
	 * @功能说明：从redis拿取序列号，也许该加个同步锁
	 * @修改者：
	 * @修改日期：
	 * @修改说明：
	 * @创建者 zhuxy
	 * @创建时间：2015年2月28日
	 */
	private int getOrderSequence() {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			String value = jedis.get("zx_flightorder_sequence");
			String date = jedis.get("zx_flightorder_sequence_date");
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"value:"+JSON.toJSONString(value));
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"时间比较"+JSON.toJSONString(date)+String.valueOf(Calendar.getInstance().getTime().getTime()));
			if (StringUtils.isBlank(value)|| StringUtils.isBlank(date)||date.equals(String.valueOf(Calendar.getInstance().getTime().getTime()))) {
				HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【getOrderSequence】"+"重置value");
				value = "0";
			}

			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
		HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"设置下一个流水号");
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();

			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}

			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"value:"+JSON.toJSONString(value));
			HgLogger.getInstance().info("cs", "【TeamBookGNFlightAction】【setNextOrderSequence】"+"Calendar.getInstance().getTime().getTime():"+String.valueOf(Calendar.getInstance().getTime().getTime()));
			jedis.set("zx_flightorder_sequence", String.valueOf(value));
			jedis.set("zx_flightorder_sequence_date",String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
}

