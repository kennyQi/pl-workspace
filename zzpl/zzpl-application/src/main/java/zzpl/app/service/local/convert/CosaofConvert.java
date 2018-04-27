package zzpl.app.service.local.convert;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.jp.PassengerDAO;
import zzpl.app.dao.user.CompanyDAO;
import zzpl.app.dao.user.CostCenterDAO;
import zzpl.app.dao.user.UserDAO;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.order.Passenger;
import zzpl.domain.model.user.COSAOF;
import zzpl.domain.model.user.Company;
import zzpl.domain.model.user.CostCenter;
import zzpl.domain.model.user.CostCenterOrder;
import zzpl.domain.model.user.User;
import zzpl.pojo.dto.jp.status.COSAOFStatus;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.dto.user.CosaofDTO;
import zzpl.pojo.qo.jp.PassengerQO;
import zzpl.pojo.qo.user.CostCenterOrderQO;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;

public class CosaofConvert extends EntityConvertUtils {

	/**
	 * @Title: getList 
	 * @author guok
	 * @Description: 结算列表转换方法
	 * @Time 2015年8月13日下午5:45:57
	 * @param cosaofs
	 * @return List<CosaofDTO> 设定文件
	 * @throws
	 */
	public static List<CosaofDTO> getList(List<COSAOF> cosaofs,FlightOrderDAO flightOrderDAO,PassengerDAO passengerDAO,CostCenterDAO costCenterDAO,CompanyDAO companyDAO,UserDAO userDAO) {
		
		List<CosaofDTO> cosaofDTOs = new ArrayList<CosaofDTO>();
		if (cosaofs.size()>0) {
			for (COSAOF cosaof : cosaofs) {
				try {
					if (StringUtils.equals(cosaof.getOrderType(), CostCenterOrder.GN_FLIGHT_ORDER)) {
						CosaofDTO dto = new CosaofDTO();
						FlightOrder flightOrder = flightOrderDAO.get(cosaof.getOrderID());
						
						
						dto.setId(cosaof.getId());
						dto.setOrderID(cosaof.getOrderID());
						dto.setOrderNO(flightOrder.getOrderNO());
						dto.setPassengerName(cosaof.getPassengerName());
						dto.setCosaofStatus(cosaof.getCosaofStatus());
						dto.setOrderType(cosaof.getOrderType());
						if (dto.getCosaofStatus()==COSAOFStatus.NOT_SETTLED) {
							dto.setStatus("未结算");
						}else {
							dto.setStatus("已结算");
						}
						dto.setOrderStatus(cosaof.getOrderStatus());
						if (cosaof.getOrderStatus()==FlightOrderStatus.APPROVAL_SUCCESS) {
							dto.setoStatus(FlightOrderStatus.APPROVAL_SUCCESS_MSG);
						}else if (cosaof.getOrderStatus()==FlightOrderStatus.PRINT_TICKET_SUCCESS) {
							dto.setoStatus(FlightOrderStatus.PRINT_TICKET_SUCCESS_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.PRINT_TICKET_FAILED){
							dto.setoStatus(FlightOrderStatus.PRINT_TICKET_FAILED_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.CANCEL_APPROVAL_SUCCESS){
							dto.setoStatus(FlightOrderStatus.CANCEL_APPROVAL_SUCCESS_MSG);
						}else if (cosaof.getOrderStatus()==FlightOrderStatus.CANCEL_TICKET_SUCCESS) {
							dto.setoStatus(FlightOrderStatus.CANCEL_TICKET_SUCCESS_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.CANCEL_TICKET_FAILED){
							dto.setoStatus(FlightOrderStatus.CANCEL_TICKET_FAILED_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS){
							dto.setoStatus(FlightOrderStatus.CONFIRM_CANCEL_ORDER_SUCCESS_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.CONFIRM_ORDER_SUCCESS){
							dto.setoStatus(FlightOrderStatus.CONFIRM_ORDER_SUCCESS_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.CONFIRM_ORDER_FAILED){
							dto.setoStatus(FlightOrderStatus.CONFIRM_ORDER_FAILED_MSG);
						}else if(cosaof.getOrderStatus()==FlightOrderStatus.ORDER_PROCESS_VOID){
							dto.setoStatus(FlightOrderStatus.ORDER_PROCESS_VOID_MSG);
						}else {
							dto.setoStatus("订单异常");
						}
						
						//机票成本价
						if (cosaof.getTotalPrice() != null) {
							if (cosaof.getTotalPrice() < 0) {
								dto.setFailed("申请分销价格失败");
							}else {
								dto.setTotalPrice(cosaof.getTotalPrice());
							}
						}
						//退款价格
						if (cosaof.getRefundPrice() != null) {
							if (cosaof.getRefundPrice() < 0) {
								dto.setRefundPrice(0.0);
							}else {
								dto.setRefundPrice(cosaof.getRefundPrice());
							}
						}
						//结算价格
						if (cosaof.getCasaofPrice() != null) {
							dto.setCasaofPrice(cosaof.getCasaofPrice());
						}
						//分销价加
						if (cosaof.getPlatTotalPrice() != null) {
							dto.setPlatTotalPrice(cosaof.getPlatTotalPrice());
						}
						if (StringUtils.isNotBlank(cosaof.getPnr())) {
							dto.setPnr(cosaof.getPnr());
						}
						if (StringUtils.isNotBlank(cosaof.getVoyage())) {
							dto.setVoyage(cosaof.getVoyage());
						}
						
						if (cosaof.getUserPayPrice() != null) {
							dto.setUserPayPrice(cosaof.getUserPayPrice());
						}
						if (cosaof.getUserRefundPrice() != null) {
							dto.setUserRefundPrice(cosaof.getUserRefundPrice());
						}
						if (StringUtils.isNotBlank(cosaof.getCompanyID())) {
							Company company = companyDAO.get(flightOrder.getCompanyID());
							dto.setCompanyName(company.getCompanyName());
						}
						
						//订票人
						if (StringUtils.isNotBlank(flightOrder.getReplacePerson())) {
							User user = userDAO.get(flightOrder.getReplacePerson());
							dto.setUserName(user.getName());
						}else {
							User user = userDAO.get(flightOrder.getUserID());
							dto.setUserName(user.getName());
						}
						
						
						//乘机人信息，姓名，票号
						String airID = "";
						String passengerName = "";
						PassengerQO passengerQO = new PassengerQO();
						passengerQO.setFlightOrderID(flightOrder.getId());
						List<Passenger> passengers = passengerDAO.queryList(passengerQO);
						for (int i = 0; i < passengers.size(); i++) {
							if ((i+1)==passengers.size()) {
								if (StringUtils.isNotBlank(passengers.get(i).getAirID())) {
									airID += passengers.get(i).getAirID();
								}
								passengerName += passengers.get(i).getPassengerName();
							}else {
								if (StringUtils.isNotBlank(passengers.get(i).getAirID())) {
									airID += passengers.get(i).getAirID()+",";
								}
								passengerName += passengers.get(i).getPassengerName()+",";
							}
						}
						dto.setAirID(airID);
						dto.setPassengerName(passengerName);
						
						dto.setCreateTime(cosaof.getCreateTime().toString().substring(0, cosaof.getCreateTime().toString().length()-5));
						//成本中心
						CostCenterOrderQO costCenterOrderQO = new CostCenterOrderQO();
						costCenterOrderQO.setOrderID(flightOrder.getId());
						CostCenter costCenter = costCenterDAO.get(flightOrder.getCostCenterID());
						if (costCenter!=null) {
							dto.setCostCenterName(costCenter.getCostCenterName());
						}
						cosaofDTOs.add(dto);
					}
				} catch (Exception e) {
					HgLogger.getInstance().error("gk",	"【CosaofConvert】【getList】："+e.getMessage());
				}
				
			}
		}
		return cosaofDTOs;
	}
}
