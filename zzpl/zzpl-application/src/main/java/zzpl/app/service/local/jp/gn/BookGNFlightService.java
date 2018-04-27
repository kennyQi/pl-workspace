package zzpl.app.service.local.jp.gn;

import java.util.List;

import hg.common.component.BaseCommand;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.client.dto.jp.GNPassengerDTO;
import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.BookGNFlightCommand;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：生成本地订单
 * @类修改者：
 * @修改日期：2015年12月24日下午3:01:01
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午3:01:01
 */
@Component("BookGNFlightService")
public class BookGNFlightService implements CommonService {

	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		BookGNFlightCommand bookGNFlightCommand = JSON.parseObject(JSON.toJSONString(command), BookGNFlightCommand.class);
		HgLogger.getInstance().info("cs", "【BookGNFlightService】"+"bookGNFlightCommand:"+JSON.toJSONString(bookGNFlightCommand));
		//1:保存订单信息
		FlightOrder flightOrder = new FlightOrder();
		flightOrder=JSON.parseObject(JSON.toJSONString(bookGNFlightCommand), FlightOrder.class);
		String orderID = UUIDGenerator.getUUID();
		flightOrder.setId(orderID);
		flightOrderDAO.save(flightOrder);
		//保存游客信息
		List<GNPassengerDTO> passengerList = JSON.parseArray(bookGNFlightCommand.getPassengerListJSON(), GNPassengerDTO.class);
		for (GNPassengerDTO gnPassengerDTO : passengerList) {
			Passenger passenger = new Passenger();
			passenger.setId(UUIDGenerator.getUUID());
			passenger.setIdNO(gnPassengerDTO.getIdNO());
			passenger.setIdType(gnPassengerDTO.getIdType());
			passenger.setOrderType(Passenger.GN_ORDER);
			passenger.setPassengerName(gnPassengerDTO.getPassengerName());
			passenger.setPassengerType(gnPassengerDTO.getPassengerType());
			passenger.setTelephone(gnPassengerDTO.getTelephone());
			passenger.setStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS.toString());
			passenger.setFlightOrder(flightOrder);
			HgLogger.getInstance().info("cs", "【BookGNFlightService】"+"passenger:"+JSON.toJSONString(passenger));
			passengerDAO.save(passenger);
		}
		//2:保存流程实例与订单关系
		WorkflowInstanceOrder workflowInstanceOrder = new WorkflowInstanceOrder();
		workflowInstanceOrder.setId(UUIDGenerator.getUUID());
		workflowInstanceOrder.setOrderID(orderID);
		workflowInstanceOrder.setOrderType(WorkflowInstanceOrder.GN_FLIGHT_ORDER);
		workflowInstanceOrder.setWorkflowInstance(workflowInstanceDAO.get(workflowInstanceID));
		HgLogger.getInstance().info("cs", "【BookGNFlightService】"+"workflowInstanceOrder:"+JSON.toJSONString(workflowInstanceOrder));
		workflowInstanceOrderDAO.save(workflowInstanceOrder);
		HgLogger.getInstance().info("cs", "【BookGNFlightService】"+"国内机票申请提交成功");
		return CommonService.SUCCESS;
	}

}
