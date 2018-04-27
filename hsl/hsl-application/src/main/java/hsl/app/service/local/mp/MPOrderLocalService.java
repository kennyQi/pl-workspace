package hsl.app.service.local.mp;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hsl.app.dao.MPOrderDao;
import hsl.app.dao.MPOrderUserDao;
import hsl.domain.model.mp.order.MPOrder;
import hsl.domain.model.mp.order.MPOrderUser;
import hsl.pojo.command.MPOrderCancelCommand;
import hsl.pojo.command.UpdateSyncOrderStatusCommand;
import hsl.pojo.dto.company.TravelDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.mp.MPOrderStatusDTO;
import hsl.pojo.dto.mp.NoticeTypeDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.user.UserBaseInfoDTO;
import hsl.pojo.dto.user.UserContactInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.spi.common.MpEnumConstants;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.alibaba.fastjson.JSON;
@Service
@Transactional
public class MPOrderLocalService extends BaseServiceImpl<MPOrder,HslMPOrderQO,MPOrderDao>{

	@Autowired
	private MPOrderDao mpOrderDao;
	@Autowired
	private MPOrderUserDao mpOrderUserDao;
	
	@Override
	protected MPOrderDao getDao() {
		return mpOrderDao;
	}

	public MPOrderUser queryTakeTicketUser(String id){
		return mpOrderUserDao.get(id);
	}
	
	/**
	 * 返回差旅dto
	 * @param pagination
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Pagination queryTravelDtoPagination(Pagination pagination){
		Pagination p1=queryPagination(pagination);
		p1.setList(changeMPToTravelDto((List<MPOrder>) p1.getList()));
		return p1;
	}
	/**
	 * 门票列表转化为差旅dto列表,职位，成员名称未设置
	 * @param mplist
	 * @return
	 */
	private List<TravelDTO> changeMPToTravelDto(List<MPOrder> mplist){
		List<TravelDTO> list=new ArrayList<TravelDTO>();
		for(MPOrder mpOrder:mplist){
			MPOrderUser mpOrderUser=mpOrder.getTakeTicketUser();
			TravelDTO travelDTO=new TravelDTO();
			travelDTO.setOrderNum(mpOrder.getDealerOrderCode());//经销商id
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
			try {
				travelDTO.setTarvelDate(sdf.parse(mpOrder.getTravelDate()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			travelDTO.setId(mpOrderUser.getUserId());//设置成员id
			travelDTO.setProjectType(2);
			travelDTO.setPrice(mpOrder.getPrice());
			travelDTO.setCompanyName(mpOrderUser.getCompanyName());
			travelDTO.setDeptName(mpOrderUser.getDepartmentName());
			travelDTO.setDestination(mpOrder.getScenicSpot().getScenicSpotGeographyInfo().getAddress());
			list.add(travelDTO);
		}
		return list;
	}
	
	/**
	 * 查询MPOrder并转化为dto返回
	 * @param mpqo
	 * @return
	 */
	public MPOrderDTO getMPOrderDTO(HslMPOrderQO mpqo){
		MPOrder mpOrder=queryUnique(mpqo);
		//转化
		MPOrderDTO dto=new MPOrderDTO();
		dto.setDealerOrderCode(mpOrder.getDealerOrderCode());
		dto.setCreateDate(mpOrder.getCreateDate());
		dto.setTravelDate((mpOrder.getTravelDate()));
		dto.setNumber(mpOrder.getNumber());
		dto.setPrice(mpOrder.getPrice());
		dto.setStatus(BeanMapperUtils.map(mpOrder.getStatus(), MPOrderStatusDTO.class));
		dto.setOrderUser(transFormMPOrderUser(mpOrder.getOrderUser()));
		dto.setTakeTicketUser(transFormMPOrderUser(mpOrder.getTakeTicketUser()));
		dto.setTravelers(transFormMPOrderUsers(mpOrder.getTravelers()));
		if(mpOrder.getMpPolicy()!=null && Hibernate.isInitialized(mpOrder.getMpPolicy())){
			PolicyDTO policyDTO =BeanMapperUtils.map(mpOrder.getMpPolicy(), PolicyDTO.class);
			policyDTO.setNoticeTypes(JSON.parseArray(mpOrder.getMpPolicy().getBuyNotie(), NoticeTypeDTO.class));
			dto.setMpPolicy(policyDTO);
		}
		if(mpOrder.getScenicSpot()!=null && Hibernate.isInitialized(mpOrder.getScenicSpot())){
			ScenicSpotDTO scenicSpotDTO=BeanMapperUtils.map(mpOrder.getScenicSpot(), ScenicSpotDTO.class);
			dto.setScenicSpot(scenicSpotDTO);
		}
		return dto;
	}
	
	/**
	 * MPOrderUser转换成UserDTO
	 * @param mpOrderUser
	 * @return
	 */
	public UserDTO transFormMPOrderUser(MPOrderUser mpOrderUser){
		UserDTO userDTO=new UserDTO();
		UserBaseInfoDTO userBaseInfoDTO=new UserBaseInfoDTO();
		userBaseInfoDTO.setIdCardNo(mpOrderUser.getIdCardNo());
		userBaseInfoDTO.setName(mpOrderUser.getName());
		UserContactInfoDTO userContactInfoDTO=new UserContactInfoDTO();
		userContactInfoDTO.setMobile(mpOrderUser.getMobile());
		if(StringUtils.isNotBlank(mpOrderUser.getUserId())){
			userDTO.setId(mpOrderUser.getUserId());
		}
		userDTO.setBaseInfo(userBaseInfoDTO);
		userDTO.setContactInfo(userContactInfoDTO);
		return userDTO;
	}
	
	/**
	 * MPOrderUser列表转换成UserDTO列表
	 * @param mpOrderUserList
	 * @return
	 */
	public List<UserDTO> transFormMPOrderUsers(List<MPOrderUser> mpOrderUserList){
		List<UserDTO> list=new ArrayList<UserDTO>();
		for (MPOrderUser mpOrderUser : mpOrderUserList) {
			UserDTO userDTO=new UserDTO();
			UserBaseInfoDTO userBaseInfoDTO=new UserBaseInfoDTO();
			userBaseInfoDTO.setIdCardNo(mpOrderUser.getIdCardNo());
			userBaseInfoDTO.setName(mpOrderUser.getName());
			UserContactInfoDTO userContactInfoDTO=new UserContactInfoDTO();
			userContactInfoDTO.setMobile(mpOrderUser.getMobile());
			userDTO.setBaseInfo(userBaseInfoDTO);
			userDTO.setContactInfo(userContactInfoDTO);
			list.add(userDTO);
		}

		
		return list;
	}
	
	/**
	 * 取消订单
	 * @param MPOrderCancelCommand
	 */
	public void cancelMPOrder(MPOrderCancelCommand command){
		MPOrder mpOrder = mpOrderDao.get(command.getOrderId());
		//command中没有取消原因，只有代码
		String remark=MpEnumConstants.OrderCancelReason.cancelReasonMap.get(command.getReason()+"");
		mpOrder.cancelOrder(remark);
		mpOrderDao.update(mpOrder);
	}

	public void updateSyncOrderStatus(String cancelremark,
			UpdateSyncOrderStatusCommand command) {
		HslMPOrderQO hslMPOrderQO=new HslMPOrderQO();
		hslMPOrderQO.setPlatformOrderCode(command.getOrderId());
//		hslMPOrderQO.setDealerOrderCode(command.getOrderId());
//		MPOrder mpOrder = mpOrderDao.queryUnique(hslMPOrderQO);
		MPOrder mpOrder=queryUnique(hslMPOrderQO);
		mpOrder.syncStatusFromSlfx(command.getStatus());
		mpOrder.setCancelRemark(cancelremark);
		mpOrderDao.save(mpOrder);
	}
}
