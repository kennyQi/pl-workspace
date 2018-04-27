package zzpl.app.service.local.jp.gn;

import hg.log.util.HgLogger;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import zzpl.app.dao.jp.FlightOrderDAO;
import zzpl.app.dao.workflow.WorkflowInstanceOrderDAO;
import zzpl.domain.model.order.FlightOrder;
import zzpl.domain.model.workflow.WorkflowInstanceOrder;
import zzpl.pojo.dto.jp.plfx.gn.JPQueryHighPolicyGNDTO;
import zzpl.pojo.dto.jp.status.FlightOrderStatus;
import zzpl.pojo.exception.jp.GNFlightException;
import zzpl.pojo.qo.jp.FlightOrderQO;
import zzpl.pojo.qo.jp.plfx.gn.JPPolicyGNQO;
import zzpl.pojo.qo.workflow.WorkflowInstanceOrderQO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：差旅管理员代办列表第一次打开去分销请求出票获取机票成本价和应收金额
 * @类修改者：
 * @修改日期：2015年12月24日下午3:26:06
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：cangs
 * @创建时间：2015年12月24日下午3:26:06
 */
@Service
@Transactional
public class QueryPolicyService {
	@Autowired
	private WorkflowInstanceOrderDAO workflowInstanceOrderDAO;
	@Autowired
	private FlightOrderDAO flightOrderDAO;
	@Autowired
	private GNFlightService gnFlightService;

	public void printTicket(String workflowInstanceID){
		try{
			//查询订单
			WorkflowInstanceOrderQO workflowInstanceOrderQO = new WorkflowInstanceOrderQO();
			workflowInstanceOrderQO.setWorkflowInstanceID(workflowInstanceID);
			List<WorkflowInstanceOrder> workflowInstanceOrders = workflowInstanceOrderDAO.queryList(workflowInstanceOrderQO);
			for (WorkflowInstanceOrder workflowInstanceOrder : workflowInstanceOrders) {
				FlightOrderQO flightOrderQO = new FlightOrderQO();
				flightOrderQO.setId(workflowInstanceOrder.getOrderID());
				FlightOrder flightOrder = flightOrderDAO.queryUnique(flightOrderQO);
				//三种不继续走下去的情况 分别是
				//1：已经下出来单子的
				//2：申请人支付已经去分销下过单
				//3：下完单子但是被分销定是取消的
				//-----------------以上备注全部作废------------------
				//当获取价格政策成功则不去查询，否则查询
				if(flightOrder.getStatus()==FlightOrderStatus.GET_POLICY_SUCCESS||flightOrder.getStatus()==FlightOrderStatus.PRINT_TICKET_FAILED)
					break;
				
				//根据订单信息组装查询订单所有的政策信息，进行验证
				JPPolicyGNQO jpPolicyGNQO = new JPPolicyGNQO();
				jpPolicyGNQO.setEncryptString(flightOrder.getEncryptString());
				jpPolicyGNQO.setTickType(flightOrder.getTickType());
				if(queryPolicy(flightOrder.getId(),jpPolicyGNQO)==null){
					break;
				}
			}
		}catch(Exception e){
			HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【printTicket】差旅管理员点击查看详情"+HgLogger.getStackTrace(e));
		}
	}
	
	/**
	 * 
	 * @方法功能说明：查看政策并修改订单信息
	 * @修改者名字：cangs
	 * @修改时间：2015年12月25日上午9:32:41
	 * @修改内容：
	 * @参数：@param orderID
	 * @参数：@param jpPolicyGNQO
	 * @参数：@return
	 * @return:FlightOrder
	 * @throws
	 */
	public FlightOrder queryPolicy(String orderID,JPPolicyGNQO jpPolicyGNQO){
		HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【queryPolicy】查看政策并修改订单信息"+"orderID"+orderID);
		HgLogger.getInstance().info("cs", "【FlightOrderService】"+"【queryPolicy】查看政策并修改订单信息"+"jpPolicyGNQO"+JSON.toJSONString(jpPolicyGNQO));
		FlightOrder flightOrder = flightOrderDAO.get(orderID);
		if(flightOrder==null){
			return null;
		}
		JPQueryHighPolicyGNDTO jqHighPolicyGNDTO = new JPQueryHighPolicyGNDTO();
		try {
			jqHighPolicyGNDTO = gnFlightService.queryHighPolicyGN(jpPolicyGNQO);
			BigDecimal sum = new BigDecimal(0);
			BigDecimal ibePrice = new BigDecimal(jqHighPolicyGNDTO.getPricesGNDTO().getIbePrice());
			sum=sum.add(ibePrice);
			BigDecimal oilFee = new BigDecimal(jqHighPolicyGNDTO.getOilFee());
			sum=sum.add(oilFee);
			BigDecimal buildFee = new BigDecimal(jqHighPolicyGNDTO.getBuildFee());
			sum=sum.add(buildFee);
			flightOrder.setIbePrice(ibePrice.toString());
			flightOrder.setOilFee(jqHighPolicyGNDTO.getOilFee());
			flightOrder.setBuildFee(jqHighPolicyGNDTO.getBuildFee());
			flightOrder.setCommitPrice(sum.doubleValue());
			flightOrder.setStatus(FlightOrderStatus.GET_POLICY_SUCCESS);
			flightOrder.setPassengers(null);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【queryPolicy】【execute】flightOrder:"+JSON.toJSONString(flightOrder));
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【FlightOrderService】【queryPolicy】【execute】政策查询失败:" + HgLogger.getStackTrace(e));
			flightOrder.setTotalPrice(-1.0);
			flightOrder.setStatus(FlightOrderStatus.GET_POLICY_FAIL);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【queryPolicy】去分销差政策失败"+"flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			return null;
		}
		flightOrder.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
		//组装下单Command
		/*JPBookTicketGNCommand jpBookTicketGNCommand = new JPBookTicketGNCommand(); 
		jpBookTicketGNCommand.setEncryptString(jqHighPolicyGNDTO.getPricesGNDTO().getEncryptString());
		jpBookTicketGNCommand.setDealerOrderId(flightOrder.getOrderNO());
		jpBookTicketGNCommand.setFlightNo(flightOrder.getFlightNO());
		jpBookTicketGNCommand.setStartDate(flightOrder.getStartTime());
		if (StringUtils.isNotBlank(flightOrder.getCabinCode())) {
			jpBookTicketGNCommand.setCabinCode(flightOrder.getCabinCode());
		}
		if (StringUtils.isNotBlank(flightOrder.getCabinName())) {
			jpBookTicketGNCommand.setCabinName(flightOrder.getCabinName());
		}
		//联系人及乘机人信息
		PassengerInfoGNDTO passengerInfoGNDTO = new PassengerInfoGNDTO();
		passengerInfoGNDTO.setName(flightOrder.getLinkName());
		passengerInfoGNDTO.setTelephone(flightOrder.getLinkTelephone());
		List<PassengerGNDTO> passengerGNDTOs = new ArrayList<PassengerGNDTO>();
		for (Passenger passenger : passengers) {
			PassengerGNDTO passengerGNDTO = new PassengerGNDTO();
			passengerGNDTO.setPassengerName(passenger.getPassengerName());
			passengerGNDTO.setIdType(passenger.getIdType());
			passengerGNDTO.setIdNo(passenger.getIdNO());
			passengerGNDTO.setPassengerType(passenger.getPassengerType());
			passengerGNDTOs.add(passengerGNDTO);
		}
		passengerInfoGNDTO.setPassengerList(passengerGNDTOs);
		jpBookTicketGNCommand.setPassengerInfoGNDTO(passengerInfoGNDTO);
		//去分销下订单
		JPBookOrderGNDTO jpBookOrderGNDTO = new JPBookOrderGNDTO();
		try {
			jpBookOrderGNDTO = gnFlightService.bookOrderGN(jpBookTicketGNCommand);
		} catch (GNFlightException e) {
			HgLogger.getInstance().error("gk", "【FlightOrderService】【printTicket】下单失败:" + HgLogger.getStackTrace(e));
			flightOrder.setTotalPrice(-1.0);
			HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】去分销下单失败"+"flightOrder:"+JSON.toJSONString(flightOrder));
			flightOrderDAO.update(flightOrder);
			break;
		}
		HgLogger.getInstance().info("gk", "【FlightOrderService】【printTicket】下单成功:"+JSON.toJSONString(jpBookOrderGNDTO));*/
//		flightOrder.setStatus(FlightOrderStatus.APPROVAL_SUCCESS);
		/*
		 * 2015年9月9日16:34:47 
		 * 因为要把 按照票面价 卖票 所以要把订单中的 经销商价格 改为票面价 这样 结算中心取数据时 会去取这个票面价格 结算价格同样就会更新到这个价格 
		 * 因为 票面价与成本价 会有差额 这部分差额就是中智票量所盈利的部分
		 */
		/*
		 * *
		flightOrder.setPlatTotalPrice(jpBookOrderGNDTO.getPriceGNDTO().getPlatTotalPirce());
		 */
		flightOrder.setPlatTotalPrice(flightOrder.getCommitPrice());
		flightOrder.setTotalPrice(jqHighPolicyGNDTO.getPricesGNDTO().getSingleTotalPrice());
		flightOrder.setMemo(jqHighPolicyGNDTO.getPricesGNDTO().getMemo());
		flightOrder.setPlcid(String.valueOf(jqHighPolicyGNDTO.getPricesGNDTO().getPlcid()));
//		flightOrder.setPayPlatform(jqHighPolicyGNDTO.getPricesGNDTO().getPayType());
		HgLogger.getInstance().info("gk", "【FlightOrderService】【queryPolicy】"+"flightOrder:"+JSON.toJSONString(flightOrder));
		flightOrderDAO.update(flightOrder);
		return flightOrder;
	}
}
