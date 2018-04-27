package zzpl.app.service.local.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.GJFlightCabinDAO;
import zzpl.app.dao.jp.GJFlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.GJFlightCabin;
import zzpl.domain.model.order.GJFlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.CostCenter;
import zzpl.domain.model.user.User;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.dto.jp.status.FlightPayStatus;
import zzpl.pojo.dto.workflow.PassengerDTO;
import zzpl.pojo.dto.workflow.WorkflowInstanceOrderDTO;
import zzpl.pojo.qo.jp.GJFlightCabinQO;
import zzpl.pojo.qo.jp.PassengerQO;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

public class WorkflowInstanceOrderConver extends EntityConvertUtils {

	/**
	 * @Title: listConvert 
	 * @author guok
	 * @Description: WorkflowInstanceOrder转换
	 * @Time 2015年7月10日 10:37:49
	 * @param workflowInstanceOrders
	 * @return List<WorkflowInstanceOrderDTO> 设定文件
	 * 
	 * 2015年7月17日 16:49:13
	 * 添加字段转换
	 * @throws
	 */
	public static List<WorkflowInstanceOrderDTO> listConvert(List<WorkflowInstanceOrder> workflowInstanceOrders,FlightOrderDAO flightOrderDAO,GJFlightOrderDAO gjFlightOrderDAO,UserDAO userDAO,GJFlightCabinDAO gjFlightCabinDAO,PassengerDAO passengerDAO,CostCenterDAO costCenterDAO) {
		List<WorkflowInstanceOrderDTO> workflowInstanceOrderDTOs = new ArrayList<WorkflowInstanceOrderDTO>();
		if (workflowInstanceOrders.size()>0) {
//			HgLogger.getInstance().info("gk", "【WorkflowInstanceOrderConver】【listConvert】【workflowInstanceOrders】:"+JSON.toJSONString(workflowInstanceOrders));
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				WorkflowInstanceOrderDTO dto = new WorkflowInstanceOrderDTO();
				try {
					dto.setOrderID(workflowInstanceOrder.getOrderID());
					if (StringUtils.equals(workflowInstanceOrder.getOrderType(), WorkflowInstanceOrder.GN_FLIGHT_ORDER)) {
						//查询订单
						FlightOrder flightOrder = flightOrderDAO.get(workflowInstanceOrder.getOrderID());
						HgLogger.getInstance().info("gk", "【WorkflowInstanceOrderConver】【listConvert】【flightOrder】:"+JSON.toJSONString(flightOrder));
						
						dto.setLinkName(flightOrder.getLinkName());
						dto.setLinkTelephone(flightOrder.getLinkTelephone());
						dto.setOrderNO(flightOrder.getOrderNO());
						dto.setFlightNO(flightOrder.getFlightNO());
						dto.setPlaneType(flightOrder.getPlaneType());
						dto.setDepartAirport(flightOrder.getDepartAirport());
						dto.setArrivalAirport(flightOrder.getArrivalAirport());
						dto.setCabinName(flightOrder.getCabinName());
						dto.setCabinCode(flightOrder.getCabinCode());
						dto.setOrgCity(flightOrder.getOrgCityName());
						dto.setDstCity(flightOrder.getDstCityName());
						dto.setAirComp(flightOrder.getAirComp());
						dto.setAirCompanyName(flightOrder.getAirCompanyName());
						dto.setStartTime(flightOrder.getStartTime());
						dto.setEndTime(flightOrder.getEndTime());
						dto.setCreateTime(flightOrder.getCreateTime());
						dto.setOilFee(flightOrder.getOilFee());
						dto.setIbePrice(flightOrder.getIbePrice());
						dto.setBuildFee(flightOrder.getBuildFee());
						dto.setCommitPrice(flightOrder.getCommitPrice());
						dto.setTotalPrice(flightOrder.getTotalPrice());
						dto.setRefuseMemo(flightOrder.getRefuseMemo());
						dto.setMemo(flightOrder.getMemo());
						dto.setNote(flightOrder.getNote());
						dto.setTicketChannel(flightOrder.getTicketChannel());
						dto.setTicketChannelName(flightOrder.getTicketChannelName());
						dto.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
						dto.setPayStatus(flightOrder.getPayStatus());
						dto.setPayType(flightOrder.getPayType());
						//支付类型
						if (StringUtils.equals(flightOrder.getPayType(), "1")) {
							dto.setPayTypeMSG("垫付");
						}else{
							dto.setPayTypeMSG("申请人支付");
						}
						
						//支付状态
						if (flightOrder.getPayStatus() == FlightPayStatus.PAY_SUCCESS) {
							dto.setPayStatusMSG("支付成功");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.PAY_FAILED) {
							dto.setPayStatusMSG("支付失败");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.REFUND_SUCCESS) {
							dto.setPayStatusMSG("退款成功");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.REFUND_FAILED) {
							dto.setPayStatusMSG("退款失败");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.REFUND) {
							dto.setPayStatusMSG("等待退款");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.REFUND_USER_SUCCESS) {
							dto.setPayStatusMSG("退款到申请人成功");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.USER_PAY_SUCCESS) {
							dto.setPayStatusMSG("用户支付成功");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.USER_REFUND) {
							dto.setPayStatusMSG("等待退款");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.WAIT_USER_TO_PAY) {
							dto.setPayStatusMSG("等待申请人支付");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.WAIT_TO_PAY) {
							dto.setPayStatusMSG("等待支付");
						}else if (flightOrder.getPayStatus() == FlightPayStatus.REFUND_USER_FAILED) {
							dto.setPayStatusMSG("退款到申请人失败");
						}
						//出票方式
						if (flightOrder.getPrintTicketType()!=null&&flightOrder.getPrintTicketType()!=0) {
							if (flightOrder.getPrintTicketType()==FlightOrder.MANAUL_VOTE_PRIMT_TICKET) {
								dto.setPrintTicketType(FlightOrder.MANAUL_VOTE_PRIMT_TICKET);
							}else {
								dto.setPrintTicketType(FlightOrder.AUTO_VOTE_PRIMT_TICKET);
							}
						}
						
						List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
						String airID = "";
						String passengerName = "";
						//乘机人信息
						PassengerQO passengerQO = new PassengerQO();
						passengerQO.setFlightOrderID(flightOrder.getId());
						List<Passenger> passengers = passengerDAO.queryList(passengerQO);
						if (passengers.size()>0&&passengers!=null) {
							for (int i=0; i< passengers.size() ; i++) {
								PassengerDTO passengerDTO = new PassengerDTO();
								passengerDTO.setPassengerName(passengers.get(i).getPassengerName());
								passengerDTO.setIdNO(passengers.get(i).getIdNO());
								passengerDTO.setTelephone(passengers.get(i).getTelephone());
								passengerDTO.setAirID(passengers.get(i).getAirID());
								passengerDTO.setRefundMemo(passengers.get(i).getRefundMemo());
								passengerDTO.setRefundType(passengers.get(i).getRefundType());
								passengerDTOs.add(passengerDTO);
								if (passengers.size()==1||i==passengers.size()) {
									passengerName += passengers.get(i).getPassengerName();
									if (passengers.get(i).getAirID() != null && StringUtils.isNotBlank(passengers.get(i).getAirID())) {
										airID += passengers.get(i).getAirID();
									}
								}else {
									passengerName += passengers.get(i).getPassengerName()+",";
									if (passengers.get(i).getAirID() != null && StringUtils.isNotBlank(passengers.get(i).getAirID())) {
										airID += passengers.get(i).getAirID() + ",";
									}
								}
								
								
							}
							dto.setPassengers(passengerDTOs);
						}
						
						dto.setAirID(airID);
						dto.setPassengerName(passengerName);
						
						//申请人员信息
						//查询用户
						User user = userDAO.get(flightOrder.getUserID());
						if (user!=null) {
							dto.setUserID(user.getId());
							if (StringUtils.isNotBlank(user.getName())) {
								dto.setUserName(user.getName());
							}
							if (StringUtils.isNotBlank(user.getLinkMobile())) {
								dto.setUserMobile(user.getLinkMobile());
							}
							if (StringUtils.isNotBlank(user.getUserNO())) {
								dto.setUserNO(user.getUserNO());
							}
							if (StringUtils.isNotBlank(user.getDepartmentName())) {
								dto.setDepartmentName(user.getDepartmentName());
							}
						}
						
						//查询成本中心
						if(StringUtils.isNotBlank(flightOrder.getCostCenterID())){
							CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
							if (costCenter != null) {
								dto.setCostCenterID(costCenter.getId());
								dto.setCostCenterName(costCenter.getCostCenterName());
							}
						}
						
					}else {
						GJFlightOrder gjFlightOrder = gjFlightOrderDAO.get(workflowInstanceOrder.getOrderID());
						//查询用户
						User user = userDAO.get(gjFlightOrder.getUserID());
						dto.setLinkName(gjFlightOrder.getLinkName());
						dto.setLinkTelephone(gjFlightOrder.getLinkTelephone());
						dto.setOrderNO(gjFlightOrder.getOrderNO());
						GJFlightCabinQO gjFlightCabinQO = new GJFlightCabinQO();
						gjFlightCabinQO.setOrderID(gjFlightOrder.getId());
						List<GJFlightCabin> gjFlightCabins = gjFlightCabinDAO.queryList(gjFlightCabinQO);
						dto.setFlightNO(gjFlightCabins.get(0).getCarriageFlightno());
						dto.setOrgCity(gjFlightCabins.get(0).getOrgCity());
						dto.setDstCity(gjFlightCabins.get(0).getDstCity());
						dto.setAirComp(gjFlightCabins.get(0).getCarriageAirline());
						dto.setStartTime(gjFlightCabins.get(0).getStartTime());
						dto.setEndTime(gjFlightCabins.get(0).getEndTime());
						dto.setCreateTime(gjFlightOrder.getCreateTime());
						if (StringUtils.isNotBlank(gjFlightOrder.getIbePrice())) {
							dto.setIbePrice(gjFlightOrder.getIbePrice());
						}
						dto.setCommitPrice(gjFlightOrder.getCommitPrice());
						if (gjFlightOrder.getTotalPrice()!=0) {
							dto.setTotalPrice(gjFlightOrder.getTotalPrice());
						}
						if (StringUtils.isNotBlank(gjFlightOrder.getRefuseMemo())) {
							dto.setRefuseMemo(gjFlightOrder.getRefuseMemo());
						}
						if (StringUtils.isNotBlank(gjFlightOrder.getMemo())) {
							dto.setMemo(gjFlightOrder.getMemo());
						}
						
						List<PassengerDTO> passengerDTOs = new ArrayList<PassengerDTO>();
						/**
						 * 乘机人信息
						 */
						PassengerQO passengerQO = new PassengerQO();
						passengerQO.setGjFlightOrderID(gjFlightOrder.getId());
						List<Passenger> passengers = passengerDAO.queryList(passengerQO);
						
						if (passengers.size()>0&&passengers!=null) {
							for (Passenger passenger : passengers) {
								PassengerDTO passengerDTO = new PassengerDTO();
								passengerDTO.setPassengerName(passenger.getPassengerName());
								passengerDTO.setIdNO(passenger.getIdNO());
								passengerDTO.setTelephone(passenger.getTelephone());
								passengerDTO.setAirID(passenger.getAirID());
								passengerDTOs.add(passengerDTO);
							}
							dto.setPassengers(passengerDTOs);
						}
						
						//用户信息
						if (user!=null) {
							dto.setUserID(user.getId());
							if (StringUtils.isNotBlank(user.getName())) {
								dto.setUserName(user.getName());
							}
							if (StringUtils.isNotBlank(user.getLinkMobile())) {
								dto.setUserMobile(user.getLinkMobile());
							}
							if (StringUtils.isNotBlank(user.getUserNO())) {
								dto.setUserNO(user.getUserNO());
							}
							if (StringUtils.isNotBlank(user.getDepartmentName())) {
								dto.setDepartmentName(user.getDepartmentName());
							}
						}
					}
					workflowInstanceOrderDTOs.add(dto);
				} catch (Exception e) {
					HgLogger.getInstance().error("cs",	"【WorkflowInstanceOrderConver】【listConvert】："+e.getMessage());
				}
			}
		}
		return workflowInstanceOrderDTOs;
	}
}
