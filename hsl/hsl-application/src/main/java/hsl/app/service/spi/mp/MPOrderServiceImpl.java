package hsl.app.service.spi.mp;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.response.mp.MPOrderCancelResponse;
import slfx.api.v1.response.mp.MPOrderCreateResponse;
import slfx.api.v1.response.mp.MPSyncOrderResponse;
import slfx.mp.pojo.dto.order.MPOrderUserInfoDTO;
import com.alibaba.fastjson.JSON;
import hg.common.component.BaseQo;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.common.util.OrderUtil;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.mp.MPOrderLocalService;
import hsl.app.service.local.mp.ScenicSpotLocalService;
import hsl.domain.model.mp.order.MPOrder;
import hsl.domain.model.mp.order.MPOrderUser;
import hsl.pojo.command.MPOrderCancelCommand;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.command.MPSyncOrderCommand;
import hsl.pojo.command.UpdateSyncOrderStatusCommand;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.user.UserBaseInfoDTO;
import hsl.pojo.dto.user.UserContactInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.inter.mp.MPOrderService;
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MPOrderServiceImpl extends
		BaseSpiServiceImpl<MPOrderDTO, BaseQo, MPOrderLocalService> implements
		MPOrderService {

	@Autowired
	private MPOrderLocalService mpOrderLocalService;
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private JedisPool jedisPool;
	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;

	@Override
	protected MPOrderLocalService getService() {
		return mpOrderLocalService;
	}

	@Override
	protected Class<MPOrderDTO> getDTOClass() {
		return MPOrderDTO.class;
	}

	
	@Override
	public UserDTO queryTakeTicketUser(String id) {
		MPOrderUser mpOrderUser = mpOrderLocalService.queryTakeTicketUser(id);
		UserDTO userDTO = new UserDTO();
		UserBaseInfoDTO baseInfo = new UserBaseInfoDTO();
		baseInfo.setName(mpOrderUser.getName());
		userDTO.setBaseInfo(baseInfo);
		UserContactInfoDTO contactInfo = new UserContactInfoDTO();
		contactInfo.setMobile(mpOrderUser.getMobile());
		userDTO.setContactInfo(contactInfo);
		return userDTO;
	}

	@Override
	public Map cancelMPOrder(MPOrderCancelCommand command) {
		//查询平台订单号
		HslMPOrderQO qo = new HslMPOrderQO();
		qo.setDealerOrderCode(command.getOrderId());
		MPOrder order = mpOrderLocalService.queryUnique(qo);

		// 向商旅分销平台取消订单
		slfx.api.v1.request.command.mp.MPOrderCancelCommand slfxCommand = new slfx.api.v1.request.command.mp.MPOrderCancelCommand();
		slfxCommand.setOrderId(order.getPlatformOrderCode());
		slfxCommand.setCancelReason(command.getReason());

		hgLogger.info("liujz", "对slfx-api发送取消订单请求" + JSON.toJSONString(command));
		ApiRequest apiRequest = new ApiRequest("MPOrderCancel",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1",UUIDGenerator.getUUID(), slfxCommand);
		MPOrderCancelResponse response = slfxApiClient.send(apiRequest,MPOrderCancelResponse.class);
		hgLogger.info("liujz","slfx-api返回取消订单结果" + JSON.toJSONString(response));
		if (response.getCancelSuccess()) {
			// 本地数据库取消订单
			mpOrderLocalService.cancelMPOrder(command);
		} else {
			//	取消失败，同步订单
			MPSyncOrderCommand syncOrderCommand = new MPSyncOrderCommand();
			syncOrderCommand.setOrderId(order.getId());
			syncOrderCommand.setPlatformOrderCode(order.getPlatformOrderCode());
			
			syncOrder(syncOrderCommand);
		}
		
		//增加取消门票订单返回数据
		Map map=new HashMap();
		HslMPOrderQO mpqo=new HslMPOrderQO();
		mpqo.setDealerOrderCode(command.getOrderId());
		//转化
		MPOrderDTO dto=mpOrderLocalService.getMPOrderDTO(mpqo);
		
		map.put("dto", dto);
		return map;
	}

	@Override
	public Map createMPOrder(MPOrderCreateCommand mpOrderCreateCommand) {
		// hsl.pojo.command.MPOrderCreateCommand转换成slfx-api-client
		slfx.api.v1.request.command.mp.MPOrderCreateCommand command = new slfx.api.v1.request.command.mp.MPOrderCreateCommand();

		command.setChannelUserId(mpOrderCreateCommand.getOrderUserInfo()
				.getId());
		// 生成经销商订单号
		String dealerOrderId ="";
		if("pc".equals(mpOrderCreateCommand.getSource())){
			dealerOrderId= OrderUtil.createOrderNo(new Date(),
				this.getOrderSequence(), 1,1);
		}else{
			dealerOrderId= OrderUtil.createOrderNo(new Date(),
					this.getOrderSequence(), 1,0);
		}
		command.setDealerOrderId(dealerOrderId);
		command.setNumber(mpOrderCreateCommand.getNumber());
		command.setPolicyId(mpOrderCreateCommand.getPolicyId());
		command.setPrice(mpOrderCreateCommand.getPrice());
		command.setTraveler(transFormUserInfoDtos(mpOrderCreateCommand
				.getTraveler()));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		try {
			command.setTravelDate(format.parse(mpOrderCreateCommand
					.getTravelDate()));
		} catch (ParseException e) {
			HgLogger.getInstance().error("zhangka", "MPOrderServiceImpl->createMPOrder->exception:" + HgLogger.getStackTrace(e));
		}
		command.setOrderUserInfo(transFormUserInfoDto(mpOrderCreateCommand
				.getOrderUserInfo()));
		if (StringUtils.isNotBlank(mpOrderCreateCommand.getBookManIP())) {
			command.setBookManIP(mpOrderCreateCommand.getBookManIP());
		}
		command.setTakeTicketUserInfo(transFormUserInfoDto(mpOrderCreateCommand
				.getTakeTicketUserInfo()));

		// 发送门票预定请求
		hgLogger.info("liujz", "对slfx-api发送门票预订请求" + JSON.toJSONString(command));
		ApiRequest apiRequest = new ApiRequest("MPCreateOrder",ClientKeyUtil.FROM_CLIENT_KEY, "192.168.1.1",UUIDGenerator.getUUID(), command);
		MPOrderCreateResponse response = slfxApiClient.send(apiRequest,MPOrderCreateResponse.class);
		Map map = new HashMap();
		if(response!=null){
			hgLogger.info("liujz","slfx-api返回门票预订结果" + JSON.toJSONString(response.getResult()));
			// 订单保存到本地数据库
			scenicSpotLocalService.createMPOrder(mpOrderCreateCommand, response,dealerOrderId);

			// 保存成功后，设置下一条订单流水号
			this.setNextOrderSequence();

			MPOrderDTO dto = new MPOrderDTO();
			dto.setDealerOrderCode(dealerOrderId);

			
			map.put("dto", dto);
			map.put("code", response.getResult());
			map.put("msg", response.getMessage());
		}
		

		return map;
	}

	@Override
	public void syncOrder(MPSyncOrderCommand command) {
		
		// 向商旅分销平台同步订单状态请求
		slfx.api.v1.request.command.mp.MPSyncOrderCommand slfxSyncOrderCommand = new slfx.api.v1.request.command.mp.MPSyncOrderCommand();
		slfxSyncOrderCommand.setOrderId(command.getPlatformOrderCode());
		
		hgLogger.info("liujz",
				"对slfx-api发送同步订单状态请求" + JSON.toJSONString(command));
		ApiRequest apiRequest = new ApiRequest("MPSyncOrder",
				ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1",
				UUIDGenerator.getUUID(), slfxSyncOrderCommand);
		MPSyncOrderResponse response = slfxApiClient.send(apiRequest,
				MPSyncOrderResponse.class);
		hgLogger.info("liujz",
				"slfx-api返回同步订单状态结果" + JSON.toJSONString(response.getResult()));
		
		//	修改本地数据库状态
		UpdateSyncOrderStatusCommand updateSyncOrderStatusCommand = new UpdateSyncOrderStatusCommand();
		updateSyncOrderStatusCommand.setOrderId(command.getPlatformOrderCode());
		hsl.pojo.dto.mp.MPOrderStatusDTO mpOrderStatusDTO=BeanMapperUtils.map(response.getStatus(), hsl.pojo.dto.mp.MPOrderStatusDTO.class);
		updateSyncOrderStatusCommand.setStatus(mpOrderStatusDTO);
		
		mpOrderLocalService.updateSyncOrderStatus(response.cancelRemark,updateSyncOrderStatusCommand);
		
	}

	// 获取订单流水号
	public int getOrderSequence() {
		Jedis jedis = null;
		try {

			jedis = jedisPool.getResource();
			String value = jedis.get("mp_sequence");
			String date = jedis.get("mp_sequence_date");
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

			jedis.set("mp_sequence", String.valueOf(value));
			jedis.set("mp_sequence_date",
					String.valueOf(Calendar.getInstance().getTime().getTime()));
		} finally {
			jedisPool.returnResource(jedis);
		}
		return null;
	}

	/**
	 * userDTO列表转化成OrderUserInfoDTO列表
	 * 
	 * @param list
	 * @return
	 */
	private List<MPOrderUserInfoDTO> transFormUserInfoDtos(List<UserDTO> list) {
		List<MPOrderUserInfoDTO> dtos = new ArrayList<MPOrderUserInfoDTO>();
		if (list != null) {
			// 遍历userDTO列表转化成OrderUserInfoDTO
			for (UserDTO userDTO : list) {
				MPOrderUserInfoDTO dto = new MPOrderUserInfoDTO();
				// userDTO必有属性
				if (StringUtils.isNotBlank(userDTO.getId())) {
					dto.setChannelUserId(userDTO.getId());
				}
				dto.setName(userDTO.getBaseInfo().getName());
				dto.setMobile(userDTO.getContactInfo().getMobile());
				// userDTO非必有属性
				if (StringUtils.isBlank(userDTO.getBaseInfo().getIdCardNo())) {
					dto.setIdCardNo(userDTO.getBaseInfo().getIdCardNo());
				}

				if (StringUtils.isBlank(userDTO.getContactInfo().getEmail())) {
					dto.setEmail(userDTO.getContactInfo().getEmail());
				}
				dtos.add(dto);
			}

			return dtos;
		}
		return null;
	}

	/**
	 * userDTO转化成OrderUserInfoDTO
	 * 
	 * @param list
	 * @return
	 */
	private MPOrderUserInfoDTO transFormUserInfoDto(UserDTO userDTO) {

		MPOrderUserInfoDTO dto = new MPOrderUserInfoDTO();
		// userDTO必有属性
		if (StringUtils.isNotBlank(userDTO.getId())) {
			dto.setChannelUserId(userDTO.getId());
		}
		if (userDTO.getBaseInfo() != null) {
			dto.setName(userDTO.getBaseInfo().getName());
		}
		dto.setMobile(userDTO.getContactInfo().getMobile());
		// userDTO非必有属性
		if (StringUtils.isBlank(userDTO.getBaseInfo().getIdCardNo())) {
			dto.setIdCardNo(userDTO.getBaseInfo().getIdCardNo());
		}

		if (StringUtils.isBlank(userDTO.getContactInfo().getEmail())) {
			dto.setEmail(userDTO.getContactInfo().getEmail());
		}

		return dto;
	}


	public Map queryMPOrderList(HslMPOrderQO mpOrderQO) throws MPException {
		List<MPOrderDTO> list=scenicSpotLocalService.getMPOrderList(mpOrderQO);
		HashMap<String, Object> map=new HashMap<String, Object>();
		map.put("dto", list);
		return map;
	}
	

}
