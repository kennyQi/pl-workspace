package zzpl.app.service.local.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.User;
import zzpl.pojo.dto.jp.plfx.gn.FlightOrderDTO;
import zzpl.pojo.qo.jp.PassengerQO;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

public class FlightOrderConver extends EntityConvertUtils {

	/**
	 * @Title: listConvert 
	 * @author guok
	 * @Description: Order转换
	 * @Time 2015年7月10日 10:37:49
	 * @param workflowInstanceOrders
	 * @return List<WorkflowInstanceOrderDTO> 设定文件
	 * 
	 * 2015年7月17日 16:49:13
	 * 添加字段转换
	 * @throws
	 */
	public static List<FlightOrderDTO> listConvert(List<FlightOrder> flightOrders,PassengerDAO passengerDAO,CompanyDAO companyDAO,UserDAO userDAO) {
		List<FlightOrderDTO> flightOrderDTOs = new ArrayList<FlightOrderDTO>();
		if (flightOrders.size()>0) {
			for (FlightOrder flightOrder : flightOrders) {
//				HgLogger.getInstance().info("gk", "【FlightOrderConver】【listConvert】【flightOrder】:"+JSON.toJSONString(flightOrder));
				FlightOrderDTO dto = new FlightOrderDTO();
				try {
						dto.setId(flightOrder.getId());
						dto.setLinkName(flightOrder.getLinkName());
						dto.setLinkTelephone(flightOrder.getLinkTelephone());
						dto.setOrderNO(flightOrder.getOrderNO());
						dto.setFlightNO(flightOrder.getFlightNO());
						dto.setPlaneType(flightOrder.getPlaneType());
						if (flightOrder.getPayStatus() != null) {
							dto.setPayStatus(flightOrder.getPayStatus());
						}
						if (StringUtils.isNotBlank(flightOrder.getDepartAirport())) {
							dto.setDepartAirport(flightOrder.getDepartAirport());
						}
						
						if (StringUtils.isNotBlank(flightOrder.getArrivalAirport())) {
							dto.setArrivalAirport(flightOrder.getArrivalAirport());
						}
						
						if (StringUtils.isNotBlank(flightOrder.getCabinName())) {
							dto.setCabinName(flightOrder.getCabinName());
						}
						
						if (StringUtils.isNotBlank(flightOrder.getOrgCityName())) {
							dto.setOrgCity(flightOrder.getOrgCityName());
						}
						
						if (StringUtils.isNotBlank(flightOrder.getDstCityName())) {
							dto.setDstCity(flightOrder.getDstCityName());
						}
						
						if (StringUtils.isNotBlank(flightOrder.getAirComp())) {
							dto.setAirComp(flightOrder.getAirComp());
						}
						
						dto.setStartTime(flightOrder.getStartTime());
						dto.setEndTime(flightOrder.getEndTime());
						dto.setCreateTime(flightOrder.getCreateTime());
						dto.setOilFee(flightOrder.getOilFee());
						dto.setIbePrice(flightOrder.getIbePrice());
						dto.setBuildFee(flightOrder.getBuildFee());
						dto.setCommitPrice(flightOrder.getCommitPrice());
						if (flightOrder.getTotalPrice() != null) {
							
							if (flightOrder.getTotalPrice() < 0) {
								dto.setFailed("申请分销价格失败");
							}else {
								dto.setTotalPrice(flightOrder.getTotalPrice());
							}
						}
						if (StringUtils.isNotBlank(flightOrder.getRefuseMemo())) {
							dto.setRefuseMemo(flightOrder.getRefuseMemo());
						}
						if (StringUtils.isNotBlank(flightOrder.getMemo())) {
							dto.setMemo(flightOrder.getMemo());
						}
						if (StringUtils.isNotBlank(flightOrder.getNote())) {
							dto.setNote(flightOrder.getNote());
						}
						dto.setStatus(flightOrder.getStatus());
						dto.setPlatTotalPrice(flightOrder.getPlatTotalPrice());
						dto.setRefundPrice(flightOrder.getRefundPrice());
						if (StringUtils.isNotBlank(flightOrder.getCompanyID())) {
							Company company = companyDAO.get(flightOrder.getCompanyID().replaceAll(",", ""));
							dto.setCompanyName(company.getCompanyName());
						}
						
						if (flightOrder.getPrintTicketType()!=null&&flightOrder.getPrintTicketType()!=0) {
							if (flightOrder.getPrintTicketType()==FlightOrder.MANAUL_VOTE_PRIMT_TICKET) {
								dto.setPrintTicketType(FlightOrder.MANAUL_VOTE_PRIMT_TICKET);
							}else {
								dto.setPrintTicketType(FlightOrder.AUTO_VOTE_PRIMT_TICKET);
							}
						}
						
						//订票人
						if (StringUtils.isNotBlank(flightOrder.getReplacePerson())) {
							User user = userDAO.get(flightOrder.getReplacePerson());
							dto.setUserName(user.getName());
						}else {
							User user = userDAO.get(flightOrder.getUserID());
							dto.setUserName(user.getName());
						}
						
						//乘机人信息
						PassengerQO passengerQO = new PassengerQO();
						passengerQO.setFlightOrderID(flightOrder.getId());
						List<Passenger> passengers = passengerDAO.queryList(passengerQO);
						String passengerName = "";
						String airID = "";
						if (passengers.size()>0&&passengers!=null) {
							for (int i = 0; i < passengers.size(); i++) {
								if ((i+1)==passengers.size()) {
									passengerName += passengers.get(i).getPassengerName()+"";
									if (StringUtils.isNotBlank(passengers.get(i).getAirID())) {
										airID += passengers.get(i).getAirID()+"";
									}
								}else {
									passengerName += passengers.get(i).getPassengerName()+",";
									if (StringUtils.isNotBlank(passengers.get(i).getAirID())) {
										airID += passengers.get(i).getAirID()+",";
									}
								}
								
							}
							dto.setPassengerName(passengerName);
							dto.setAirID(airID);
						}
						flightOrderDTOs.add(dto);
				} catch (Exception e) {
					HgLogger.getInstance().error("cs",	"【FlightOrderConver】【listConvert】："+e.getMessage());
				}
			}
		}
		return flightOrderDTOs;
	}
}
