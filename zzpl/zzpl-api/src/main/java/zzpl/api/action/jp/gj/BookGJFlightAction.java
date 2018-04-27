package zzpl.api.action.jp.gj;

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
import zzpl.api.client.dto.jp.GJFlightDTO;
import zzpl.api.client.request.jp.GJBookCommand;
import zzpl.api.client.response.jp.GJBookResponse;
import zzpl.app.service.local.user.UserRoleService;
import zzpl.app.service.local.user.UserService;
import zzpl.app.service.local.util.OrderUtil;
import zzpl.app.service.local.workflow.WorkflowStepService;
import zzpl.domain.model.user.UserRole;
import zzpl.domain.model.workflow.WorkflowStep;
import zzpl.pojo.command.jp.BookGJFlightCommand;
import zzpl.pojo.command.workflow.SendCommand;
import zzpl.pojo.dto.jp.plfx.gj.GJFlightCabinDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.exception.workflow.WorkflowException;
import zzpl.pojo.qo.user.UserRoleQO;
import zzpl.pojo.qo.workflow.WorkflowStepQO;

import com.alibaba.fastjson.JSON;

@Component("BookGJFlightAction")
public class BookGJFlightAction implements CommonAction{

	@Autowired
	private WorkflowStepService workflowStepService;
	@Autowired
	private UserRoleService userRoleService;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private UserService userService;
	/**
	 * 起草环节为1
	 */
	public final static Integer STEP_1 = 1;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		GJBookResponse gjBookResponse = new GJBookResponse();
		try {
			GJBookCommand gjBookCommand = JSON.parseObject(apiRequest.getBody().getPayload(), GJBookCommand.class);
			HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"gjBookCommand:"+JSON.toJSONString(gjBookCommand));
			//1：把流程的command转出来
			SendCommand sendCommand = new SendCommand();
			sendCommand.setWorkflowID(gjBookCommand.getWorkflowID());
			sendCommand.setCurrentStepNO(STEP_1);
			sendCommand.setCurrentUserID(gjBookCommand.getUserID());
			// 下一步人员信息，1.0前台没有选人不能动态加载只能在代码中判断下一环节
			WorkflowStepQO workflowStepQO = new WorkflowStepQO();
			workflowStepQO.setWorkflowID(gjBookCommand.getWorkflowID());
			workflowStepQO.setStepNO(STEP_1);
			WorkflowStep workflowStep = workflowStepService.queryUnique(workflowStepQO);
			sendCommand.setNextStepNO(STEP_1 + 1);
			List<String> nextUserIDs = new ArrayList<String>();
			switch (workflowStep.getChooseExecutorType()) {
			case 0:
				//结束
				break;
			case 1:
				//固定人员
				String[] nextUserID=workflowStep.getExecutor().split(";");
				for(int i=0;i<nextUserID.length;i++){
					nextUserIDs.add(nextUserID[i]);
				}
				sendCommand.setNextUserIDs(nextUserIDs);
				break;
			case 2:
				//按照角色
				UserRoleQO userRoleQO = new UserRoleQO();
				userRoleQO.setRoleID(workflowStep.getExecutor());
				List<UserRole> userRoles = userRoleService.queryList(userRoleQO);
				for(UserRole userRole:userRoles){
					nextUserIDs.add(userRole.getUser().getId());
				}
				sendCommand.setNextUserIDs(nextUserIDs);
				break;
			case 3:
				//按照部门选择，1.0不知道怎么做，直接办结
				break;
			case 4:
				//自定义，还没定义，直接办结
				break;
			}
			//2：要执行的service方法
			String[] services={"BookGJFlightService"};
			//3：把service的command转出来
			BookGJFlightCommand bookGJFlightCommand = new BookGJFlightCommand();
			bookGJFlightCommand.setCreateTime(new Date());
			bookGJFlightCommand.setEncryptString(gjBookCommand.getEncryptString());
			List<GJFlightCabinDTO> gjFlightCabinDTOs = new ArrayList<GJFlightCabinDTO>();
			for(GJFlightDTO gjFlightDTO:gjBookCommand.getGjFlightDTOs()){
				GJFlightCabinDTO gjFlightCabinDTO = new GJFlightCabinDTO();
				gjFlightCabinDTO.setCarriageAirline(gjFlightDTO.getAirComp());
				gjFlightCabinDTO.setCarriageFlightno(gjFlightDTO.getFlightNO());
				gjFlightCabinDTO.setCabinCode(gjFlightDTO.getCabinCode());
				gjFlightCabinDTO.setArrivalAirport(gjFlightDTO.getArrivalAirport());
				gjFlightCabinDTO.setDstTerm(gjFlightDTO.getArrivalTerm());
				gjFlightCabinDTO.setDstCity(gjFlightDTO.getDstCity());
				gjFlightCabinDTO.setDepartAirport(gjFlightDTO.getDepartAirport());
				gjFlightCabinDTO.setOrgTerm(gjFlightDTO.getDepartTerm());
				gjFlightCabinDTO.setOrgCity(gjFlightDTO.getOrgCity());
				gjFlightCabinDTO.setDuration(gjFlightDTO.getDuration());
				gjFlightCabinDTO.setEndTime(gjFlightDTO.getEndTime());
				gjFlightCabinDTO.setFlightNo(gjFlightDTO.getFlightNO());
				gjFlightCabinDTO.setMealCode(gjFlightDTO.getMealCode());
				gjFlightCabinDTO.setPlaneType(gjFlightDTO.getPlaneType());
				gjFlightCabinDTO.setStartTime(gjFlightDTO.getStartTime());
				gjFlightCabinDTOs.add(gjFlightCabinDTO);
			}
			bookGJFlightCommand.setGjFlightCabins(gjFlightCabinDTOs);
			bookGJFlightCommand.setIbePrice(gjBookCommand.getIbePrice());
			bookGJFlightCommand.setLinkAddress(gjBookCommand.getLinkAddress());
			bookGJFlightCommand.setLinkEmail(gjBookCommand.getLinkEmail());
			bookGJFlightCommand.setLinkName(gjBookCommand.getLinkName());
			bookGJFlightCommand.setLinkTelephone(gjBookCommand.getLinkTelephone());
			bookGJFlightCommand.setNote(gjBookCommand.getNote());
			bookGJFlightCommand.setOrderNO(OrderUtil.createOrderNo(new Date(), getOrderSequence(), 1, 0));
			bookGJFlightCommand.setPassengerListJSON(JSON.toJSONString(gjBookCommand.getPassengerList()));
			bookGJFlightCommand.setReimbursementStatus(gjBookCommand.getReimbursementStatus());
			bookGJFlightCommand.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS);
			bookGJFlightCommand.setTotalTax(gjBookCommand.getTotalTax());
			BigDecimal ibePrice = new BigDecimal(gjBookCommand.getIbePrice());
			BigDecimal sum = new BigDecimal(0);
			if(StringUtils.isNotBlank(gjBookCommand.getTotalTax().toString())){
				BigDecimal totalTax = new BigDecimal(gjBookCommand.getTotalTax());
				sum=sum.add(totalTax);
			}
			bookGJFlightCommand.setTotalPrice(ibePrice.add(sum).doubleValue());
			bookGJFlightCommand.setCommitPrice(ibePrice.add(sum).doubleValue());
			bookGJFlightCommand.setUserID(gjBookCommand.getUserID());
			bookGJFlightCommand.setUserName(userService.get(gjBookCommand.getUserID()).getName());
			HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"sendCommand:"+JSON.toJSONString(sendCommand));
			HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"services:"+JSON.toJSONString(services));
			HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"bookGJFlightCommand:"+JSON.toJSONString(bookGJFlightCommand));
			workflowStepService.send(sendCommand, services, bookGJFlightCommand);
			gjBookResponse.setResult(ApiResponse.RESULT_CODE_OK);
			gjBookResponse.setMessage("订单提交成功");
			//创建下一个序列号
			this.setNextOrderSequence();
		}catch(WorkflowException e){
			HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"国际机票下单失败，"+HgLogger.getStackTrace(e));
			gjBookResponse.setResult(e.getCode());
			gjBookResponse.setMessage(e.getMessage());
		}
		HgLogger.getInstance().info("cs", "【BookGJFlightAction】"+"gnBookResponse:"+JSON.toJSONString(gjBookResponse));
		return JSON.toJSONString(gjBookResponse);
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
			String value = jedis.get("line_sequence");
			String date = jedis.get("line_sequence_date");
			if (StringUtils.isBlank(value)
					|| StringUtils.isBlank(date)
					|| !date.equals(String.valueOf(Calendar.getInstance()
							.getTime().getTime()))) {
				value = "0";
			}

			return Integer.parseInt(value);
		} finally {
			jedisPool.returnResource(jedis);
		}
	}
	
	// 设置下一条订单流水号
	public String setNextOrderSequence() {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			int value = this.getOrderSequence();

			if (value >= 9999) {
				value = 0;
			} else {
				value++;
			}

			jedis.set("line_sequence", String.valueOf(value));
			jedis.set("line_sequence_date",
					String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}
}
