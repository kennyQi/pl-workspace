package hsl.app.service.local.jp;

import hg.common.util.BeanMapperUtils;
import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.dao.CityAirCodeDAO;
import hsl.app.dao.JPOrderDao;
import hsl.app.dao.JPPassangerDao;
import hsl.domain.model.jp.JPOrder;
import hsl.domain.model.sys.CityAirCode;
import hsl.pojo.dto.jp.CityAirCodeDTO;
import hsl.pojo.dto.jp.FlightDTO;
import hsl.pojo.dto.jp.JPOrderStatusConstant;
import hsl.pojo.dto.jp.RefundActionTypeDTO;
import hsl.pojo.qo.jp.HslJPOrderQO;
import hsl.spi.qo.sys.CityAirCodeQO;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.request.command.jp.JPAskOrderTicketCommand;
import slfx.api.v1.request.qo.jp.JPAirCodeQO;
import slfx.api.v1.request.qo.jp.JPCancleOrderTicketReasonQO;
import slfx.api.v1.request.qo.jp.JPFlightQO;
import slfx.api.v1.response.jp.JPAskOrderTicketResponse;
import slfx.api.v1.response.jp.JPCancelOrderTicketReasonResponse;
import slfx.api.v1.response.jp.JPCityAirCodeResponse;
import slfx.api.v1.response.jp.JPQueryFlightResponse;
import com.alibaba.fastjson.JSON;
@Service("jpFlightLocalService")
@Transactional
public class JPFlightLocalService {

	@Resource
	private SlfxApiClient client;
	
	@Resource
	private JPOrderDao orderDao;
	
	@Resource
	private JPPassangerDao passangerDao;
	
	@Resource
	private CityAirCodeDAO cityAirCodeDAO;
	
	@Resource
	private SMSUtils smsUtils;
	
	
	@SuppressWarnings("unused")
	private DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	@SuppressWarnings("unused")
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
	
	// 查询航班
	public List<FlightDTO> queryFlight(JPFlightQO qo) {
		// 创建要发送的请求对象
		ApiRequest request = new ApiRequest("JPQueryFlight",ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), qo);

		// 发送请求
		JPQueryFlightResponse response = null;
		List<FlightDTO> flightDTOs = null;
		try {
			HgLogger.getInstance().info("zhangka", "HSL-APP(查询航班)->JPFlightLocalService->queryFlight->request[查询航班]:" + JSON.toJSONString(request));
			
			response = client.send(request, JPQueryFlightResponse.class);
			
			HgLogger.getInstance().info("zhangka", "HSL-APP(查询航班)->JPFlightLocalService->queryFlight->response[查询航班]:" +JSON.toJSONString(response.getResult()));
			
			//不同DTO转化
			if (response != null && !response.getResult().equals("-1") && response.getFlightList() != null)
				flightDTOs = BeanMapperUtils.getMapper().mapAsList(response.getFlightList(), FlightDTO.class);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "HSL-APP(查询航班)->JPFlightLocalService->queryFlight->exception[查询航班]:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return flightDTOs;
	}
	
	//出票
	public void askOrderTicket(JPAskOrderTicketCommand command) {
		// 创建要发送的请求对象
		ApiRequest request = new ApiRequest("JPAskOrderTicket",ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), command);
	
		JPAskOrderTicketResponse response = null;
		JPOrder jpOrder = null;
		try {
			
			HgLogger.getInstance().info("zhangka", "HSL-APP(请求出票)->JPFlightLocalService->askOrderTicket->request[出票]:" + JSON.toJSONString(request));
			// 发送请求
			response = client.send(request, JPAskOrderTicketResponse.class);
			HgLogger.getInstance().info("zhangka", "HSL-APP(请求出票)->JPFlightLocalService->askOrderTicket->response[出票]:" +JSON.toJSONString(response.getResult()));
			
			
			HslJPOrderQO qo = new HslJPOrderQO();
			qo.setDealerOrderCode(command.getOrderId());
			jpOrder = orderDao.queryUnique(qo);
			
			//查不到订单，直接返回
			if (jpOrder == null || response == null) {
				return;	
			}
			jpOrder.setBuyerEmail(command.getBuyerEmail());
			jpOrder.setPayTradeNo(command.getPayOrderId());
			//处理请求出票结果
			if (response.getResult().equals("-1")){//出票失败
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_FAIL));
			} else if (response.getResult().equals("1")) {//出票处理中
				jpOrder.setPayTradeNo(command.getPayOrderId());
				jpOrder.setStatus(Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_DEALING));
				//机票订单支付状态为   支付成功， 故不需要变化
			}
			orderDao.update(jpOrder);
			
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "HSL-APP(请求出票)->JPFlightLocalService->askOrderTicket->exception[出票]:" + HgLogger.getStackTrace(e));
		}
	}
	
	//城市机场三字码查询（返回所有数据）
	public List<CityAirCodeDTO> queryCityAirCode(JPAirCodeQO jpAirCodeQO) {

		// 创建要发送的请求对象
		ApiRequest request = new ApiRequest("JPQueryCityAirCode", ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), jpAirCodeQO);
		
		JPCityAirCodeResponse response = null;
		List<CityAirCodeDTO> cityAirCodeDTOs = null;
		try {
			
			HgLogger.getInstance().info("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->request[城市机场三字码查询]:" + JSON.toJSONString(request));
			
			// 发送请求
			response = client.send(request, JPCityAirCodeResponse.class);
			
			HgLogger.getInstance().info("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->response[城市机场三字码查询]:" +JSON.toJSONString(response.getResult()));
			
			if (response != null && !response.getResult().equals("-1") && response.getCityAirCodeList() != null)
				cityAirCodeDTOs = BeanMapperUtils.getMapper().mapAsList(response.getCityAirCodeList(), CityAirCodeDTO.class);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "HSL-APP(三字码)->JPFlightLocalService->queryCityAirCode->exception[城市机场三字码查询]:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return cityAirCodeDTOs;
	}
	
	/**
	 * 查询机场编码信息
	 * @param cityAirCodeQO
	 * @return
	 */
	public CityAirCode queryLocalCityAirCode(CityAirCodeQO cityAirCodeQO) {
		return cityAirCodeDAO.queryUnique(cityAirCodeQO);
	}
	
	public List<RefundActionTypeDTO> queryCancelOrderTicketReason(String platCode) {
		
		JPCancleOrderTicketReasonQO qo = new JPCancleOrderTicketReasonQO();
		qo.setPlatCode(platCode);
		// 创建要发送的请求对象
		ApiRequest request = new ApiRequest("JPCancelTicketReason", ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1", UUID.randomUUID().toString(), qo);
		
		JPCancelOrderTicketReasonResponse response = null;
		List<RefundActionTypeDTO> actionTypeList = null;
		try {
			
			HgLogger.getInstance().info("zhangka", "HSL-APP(退废票原因)->JPFlightLocalService->queryCancelOrderTicketReason->request[退废票原因查询]:" + JSON.toJSONString(request));
			
			// 发送请求
			response = client.send(request, JPCancelOrderTicketReasonResponse.class);
			
			if (response != null && response.getActionTypeList() != null)
				actionTypeList = BeanMapperUtils.getMapper().mapAsList(response.getActionTypeList(), RefundActionTypeDTO.class);
		} catch (Exception e) {
			HgLogger.getInstance().error("zhangka", "HSL-APP(退废票原因)->JPFlightLocalService->queryCancelOrderTicketReason->exception[退废票原因查询]:" + HgLogger.getStackTrace(e));
			return null;
		}
		
		return actionTypeList;
	}
}
