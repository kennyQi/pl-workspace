package zzpl.app.service.local.jp.gj;

import hg.common.component.BaseCommand;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import zzpl.api.client.dto.jp.GJPassengerDTO;
import zzpl.app.dao.jp.GJFlightCabinDAO;
import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.workflow.WorkflowInstanceDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.app.service.local.util.CommonService;
import zzpl.domain.model.order.GJFlightCabin;
import zzpl.domain.model.order.GJFlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.command.jp.BookGJFlightCommand;

import com.alibaba.fastjson.JSON;

@Component("BookGJFlightService")
public class BookGJFlightService implements CommonService {

	@Autowired
	private GJFlightOrderDAO gjFlightOrderDAO;
	@Autowired
	private GJFlightCabinDAO gjFlightCabinDAO;
	@Autowired
	private PassengerDAO passengerDAO;
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private WorkflowInstanceDAO workflowInstanceDAO;
	
	@Override
	public String execute(BaseCommand command, String workflowInstanceID) {
		BookGJFlightCommand bookGJFlightCommand = JSON.parseObject(JSON.toJSONString(command), BookGJFlightCommand.class);
		HgLogger.getInstance().info("cs", "【BookGJFlightService】"+"bookGJFlightCommand:"+JSON.toJSONString(bookGJFlightCommand));
		//1:保存订单信息
		//1.1航班信息
		List<GJFlightCabin> gjFlightCabins = JSON.parseArray(JSON.toJSONString(bookGJFlightCommand.getGjFlightCabins()), GJFlightCabin.class);
		bookGJFlightCommand.setGjFlightCabins(null);
		//1.2订单信息
		GJFlightOrder gjFlightOrder =  JSON.parseObject(JSON.toJSONString(bookGJFlightCommand), GJFlightOrder.class);
		String orderID = UUIDGenerator.getUUID();
		gjFlightOrder.setId(orderID);
		gjFlightOrderDAO.save(gjFlightOrder);
		for(GJFlightCabin gjFlightCabin:gjFlightCabins){
			gjFlightCabin.setId(UUIDGenerator.getUUID());
			gjFlightCabin.setGjFlightOrder(gjFlightOrder);
			gjFlightCabinDAO.save(gjFlightCabin);
		}
		//1.3保存乘客信息
		List<GJPassengerDTO> passengerList = JSON.parseArray(bookGJFlightCommand.getPassengerListJSON(), GJPassengerDTO.class);
		for (GJPassengerDTO gjPassengerDTO : passengerList) {
			Passenger passenger = new Passenger();
			passenger.setId(UUIDGenerator.getUUID());
			passenger.setPassengerName(gjPassengerDTO.getBaseInfo().getPassengerName());
			passenger.setIdNO(gjPassengerDTO.getBaseInfo().getIdNo());
			passenger.setBirthday(gjPassengerDTO.getBaseInfo().getBirthday());
			passenger.setExpiryDate(gjPassengerDTO.getBaseInfo().getExpiryDate());
			passenger.setIdType(gjPassengerDTO.getBaseInfo().getIdType().toString());
			passenger.setTelephone(gjPassengerDTO.getBaseInfo().getMobile());
			passenger.setNationality(gjPassengerDTO.getBaseInfo().getNationality());
			passenger.setPassengerType(gjPassengerDTO.getBaseInfo().getPassengerType().toString());
			passenger.setSex(gjPassengerDTO.getBaseInfo().getSex());
			passenger.setOrderType(Passenger.GJ_ORDER);
			passenger.setGjFlightOrder(gjFlightOrder);
			passengerDAO.save(passenger);
		}
		//2:保存流程实例与订单关系
		WorkflowInstanceOrder workflowInstanceOrder = new WorkflowInstanceOrder();
		workflowInstanceOrder.setId(UUIDGenerator.getUUID());
		workflowInstanceOrder.setOrderID(orderID);
		workflowInstanceOrder.setOrderType(WorkflowInstanceOrder.GJ_FLIGHT_ORDER);
		workflowInstanceOrder.setWorkflowInstance(workflowInstanceDAO.get(workflowInstanceID));
		workflowInstanceOrderDAO.save(workflowInstanceOrder);
		return CommonService.SUCCESS;
	}

}
