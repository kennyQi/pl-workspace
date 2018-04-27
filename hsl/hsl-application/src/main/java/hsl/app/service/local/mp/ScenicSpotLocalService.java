package hsl.app.service.local.mp;

import hg.common.component.BaseServiceImpl;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.clickrecord.ClickRateDao;
import hg.log.clickrecord.ClickRateQo;
import hg.log.po.clickrecord.ClickRate;
import hg.log.util.HgLogger;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.spi.inter.AdService;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.dao.HotScenicSpotDao;
import hsl.app.dao.MPOrderDao;
import hsl.app.dao.MPOrderUserDao;
import hsl.app.dao.MPPolicyDao;
import hsl.app.dao.PCHotScenicDao;
import hsl.app.dao.ScenicSpotDao;
import hsl.app.dao.SpecOfferDao;
import hsl.domain.model.mp.ad.HotScenicSpot;
import hsl.domain.model.mp.ad.SpecialOfferMp;
import hsl.domain.model.mp.order.MPOrder;
import hsl.domain.model.mp.order.MPOrderUser;
import hsl.domain.model.mp.scenic.MPHotScenicSpot;
import hsl.domain.model.mp.scenic.MPPolicy;
import hsl.domain.model.mp.scenic.MPScenicSpot;
import hsl.domain.model.mp.scenic.MPScenicSpotsBaseInfo;
import hsl.domain.model.mp.scenic.MPScenicSpotsGeographyInfo;
import hsl.pojo.command.CreateHotScenicSpotCommand;
import hsl.pojo.command.MPOrderCreateCommand;
import hsl.pojo.command.ModifyHotScenicSpotCommand;
import hsl.pojo.command.ad.CreatePCHotSecnicSpotCommand;
import hsl.pojo.command.ad.CreateSpecCommand;
import hsl.pojo.command.ad.DeletePCHotCommand;
import hsl.pojo.command.ad.DeleteSpecCommand;
import hsl.pojo.command.ad.UpdateSpecCommand;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPOrderDTO;
import hsl.pojo.dto.mp.MPOrderStatusDTO;
import hsl.pojo.dto.mp.NoticeTypeDTO;
import hsl.pojo.dto.mp.PCHotScenicSpotDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.mp.SpecialOfferMpDTO;
import hsl.pojo.dto.user.UserBaseInfoDTO;
import hsl.pojo.dto.user.UserContactInfoDTO;
import hsl.pojo.dto.user.UserDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslHotScenicSpotQO;
import hsl.pojo.qo.mp.HslMPOrderQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslPCHotScenicSpotQO;
import hsl.pojo.qo.mp.HslRankListQO;
import hsl.pojo.qo.mp.HslSpecOfferMpQO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.response.mp.MPOrderCreateResponse;
import slfx.api.v1.response.mp.MPQueryPolicyResponse;
import slfx.api.v1.response.mp.MPQueryScenicSpotsResponse;
import slfx.mp.pojo.dto.supplierpolicy.TCSupplierPolicySnapshotDTO;

import com.alibaba.fastjson.JSON;



@Service
@Transactional
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ScenicSpotLocalService extends BaseServiceImpl<MPScenicSpot, HslMPScenicSpotQO, ScenicSpotDao>{

	@Autowired
	private MPPolicyDao mpPolicyDao;
	@Autowired
	private ScenicSpotDao scenicSpotDao;
	@Autowired
	private MPOrderDao mpOrderDao;
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private HotScenicSpotDao hotScenicSpotDao;
	@Autowired
	private MPOrderUserDao orderUserDao;
	@Autowired
	private HgLogger hgLogger; 
	@Autowired
	private ClickRateDao clickRateDao;
	@Autowired
	private SpecOfferDao specOfferDao;
	@Autowired
	private PCHotScenicDao pcHotScenicDao;
	@Autowired
	private AdService adService;
	
	public HotScenicSpotDTO getScenicSpotDTO(HslHotScenicSpotQO qo){
		MPHotScenicSpot spot=hotScenicSpotDao.get(qo.getId());
		HotScenicSpotDTO dto=BeanMapperUtils.map(spot, HotScenicSpotDTO.class);
		dto.setScenicSpotId(spot.getScenicSpot().getId());
		return dto;
	}
	
	/**
	 * 查询MPOrder列表并转化为dto列表返回
	 * @param mpOrderQO
	 * @return
	 * @throws MPException
	 */
	public List<MPOrderDTO> getMPOrderList(HslMPOrderQO mpOrderQO) throws MPException {
		Map map=queryMPOrderList(mpOrderQO);
		List<MPOrder> mpOrderList=(List<MPOrder>)map.get("list");
		List<MPOrderDTO> dtos=new ArrayList<MPOrderDTO>();
		//MPOrder转换成MPOrderDTO
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		for (MPOrder mpOrder : mpOrderList) {
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
				try {
					policyDTO.setNoticeTypes(JSON.parseArray(mpOrder.getMpPolicy().getBuyNotie(), NoticeTypeDTO.class));
				} catch (Exception e) {
					HgLogger.getInstance().error("wuyg", "ScenicSpotLocalService->getMPOrderList查询MPOrder列表并转化为dto列表返回->fastjson转化购票需知出错:"+mpOrder.getMpPolicy().getBuyNotie());
					HgLogger.getStackTrace(e);
				}
				dto.setMpPolicy(policyDTO);
			}
//			if(mpOrder.getScenicSpot()!=null && Hibernate.isInitialized(mpOrder.getScenicSpot())){
				ScenicSpotDTO scenicSpotDTO=BeanMapperUtils.map(mpOrder.getScenicSpot(), ScenicSpotDTO.class);
				dto.setScenicSpot(scenicSpotDTO);
//			}
				
			dtos.add(dto);
		}
		
		return dtos;
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
		userDTO.setCompanyId(mpOrderUser.getCompanyId());
		userDTO.setCompanyName(mpOrderUser.getCompanyName());
		userDTO.setDepartmentId(mpOrderUser.getDepartmentId());
		userDTO.setDepartmentName(mpOrderUser.getDepartmentName());
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
	 * 门票预订
	 * @param mpOrderCreateCommand
	 * @return
	 */
	public void createMPOrder(MPOrderCreateCommand mpOrderCreateCommand,MPOrderCreateResponse response,String dealerOrderId){
		
		//判断门票是否预订成功
		if("1".equals(response.getResult())){
			//查询景点政策信息
			slfx.api.v1.request.qo.mp.MPPolicyQO mpPolicyQO=new slfx.api.v1.request.qo.mp.MPPolicyQO();
			mpPolicyQO.setPolicyId(mpOrderCreateCommand.getPolicyId());
			//发送请求
			hgLogger.info("liujz", "对slfx-api发送景点政策查询请求"+JSON.toJSONString(mpPolicyQO));
			ApiRequest request=new ApiRequest("MPQueryPolicy", ClientKeyUtil.FROM_CLIENT_KEY,"192.168.1.1", UUIDGenerator.getUUID(), mpPolicyQO);
			MPQueryPolicyResponse policyResponse=slfxApiClient.send(request, MPQueryPolicyResponse.class);
			hgLogger.info("liujz", "slfx-api返回景点政策查询结果"+JSON.toJSONString(policyResponse));
			//查询景点信息
			slfx.api.v1.request.qo.mp.MPScenicSpotsQO mpScenicSpotsQO=new slfx.api.v1.request.qo.mp.MPScenicSpotsQO();
			mpScenicSpotsQO.setScenicSpotId(policyResponse.getPolicies().get(0).getScenicSpotSnapshot().getScenicSpotsId());
			//发送请求
			hgLogger.info("liujz", "对slfx-api发送景点查询请求"+JSON.toJSONString(mpScenicSpotsQO));
			ApiRequest scenicSpotRequest=new ApiRequest("MPQueryScenicSpots", ClientKeyUtil.FROM_CLIENT_KEY,"192.168.1.1", UUIDGenerator.getUUID(), mpScenicSpotsQO);
			MPQueryScenicSpotsResponse scenicSpotResponse=slfxApiClient.send(scenicSpotRequest, MPQueryScenicSpotsResponse.class);
			hgLogger.info("liujz", "slfx-api返回景点查询结果"+JSON.toJSONString(scenicSpotResponse));
			//ScenicSpotSnapshotDTO转换成MPScenicSpot
			MPScenicSpot mpScenicSpot=BeanMapperUtils.map(scenicSpotResponse.getScenicSpots().get(0),MPScenicSpot.class);
			//TCSupplierPolicySnapshotDTO转换成MPPolicy
			MPPolicy policy=transFromTCSupplierPolicySnapshotDTO(policyResponse.getPolicies().get(0));

			MPOrder mpOrder=new MPOrder();
			mpOrder.createOrder(mpOrderCreateCommand, response.getOrderId(),mpScenicSpot,policy,dealerOrderId);
			
			if(scenicSpotDao.get(mpScenicSpot.getId())==null){
				scenicSpotDao.save(mpScenicSpot);
			}
			
			if(mpPolicyDao.get(policy.getId())==null){
				mpPolicyDao.save(policy);
			}
			orderUserDao.save(mpOrder.getOrderUser());
			orderUserDao.save(mpOrder.getTakeTicketUser());
			if(mpOrder.getTravelers()!=null){
				orderUserDao.saveList(mpOrder.getTravelers());
			}
			mpOrderDao.save(mpOrder);

		}
	}
	
	
	/**
	 * 查询订单列表
	 * @param mpOrderQO
	 * @return
	 * @throws MPException 
	 */
	public Map queryMPOrderList(HslMPOrderQO hslMPOrderQO) throws MPException{
		
		Pagination pagination=new Pagination();
		pagination.setCondition(hslMPOrderQO);
		if(hslMPOrderQO != null && hslMPOrderQO.getPageNo() != null ){
			pagination.setPageNo(hslMPOrderQO.getPageNo());			
		}else{
			pagination.setPageNo(1);
		}
		
		if(hslMPOrderQO != null && hslMPOrderQO.getPageSize() != null ){
			pagination.setPageSize(hslMPOrderQO.getPageSize());			
		}else{
			pagination.setPageSize(20);
		}
		
		pagination=mpOrderDao.queryPagination(pagination);
		if(pagination.getList()==null || pagination.getList().size()==0){
			throw new MPException(MPException.RESULT_ORDER_NOTFOUND, "订单查询无结果");
		}
	
		
		Map map=new HashMap();
		map.put("list", pagination.getList());
		map.put("count", pagination.getTotalCount());
		
		return map;
	}
	
	/**
	 * 查询热门景点
	 * @param mpScenicSpotsQO
	 * @return
	 */
	public Map queryHotScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO){
		
		HslHotScenicSpotQO hslHotScenicSpotQO=new HslHotScenicSpotQO();
		
		Map map=new HashMap();
		//如果有热门景点id直接获取
		if (StringUtils.isNotBlank(mpScenicSpotsQO.getHotScenicSpotId())) {
			MPHotScenicSpot mpHotScenicSpot = hotScenicSpotDao
					.get(mpScenicSpotsQO.getHotScenicSpotId());
			List<MPHotScenicSpot> list = new ArrayList<MPHotScenicSpot>();
			list.add(mpHotScenicSpot);
			map.put("hotScenicSpot", list);
			map.put("count", 1);
		} 
		//如果没有热门景点id则正常查询
		else {
			if (StringUtils.isNotBlank(mpScenicSpotsQO.getScenicSpotId())) {
				hslHotScenicSpotQO.setScenicSpotId(mpScenicSpotsQO
						.getScenicSpotId());
			}
			
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getContent())){
				hslHotScenicSpotQO.setScenicSpotName(mpScenicSpotsQO.getContent());
				hslHotScenicSpotQO.setScenicSpotNameIsLike(mpScenicSpotsQO.getByName());
			}
			
			hslHotScenicSpotQO.setIsOpen(mpScenicSpotsQO.getIsOpen());
			Pagination pagination = new Pagination();
			pagination.setCondition(hslHotScenicSpotQO);
			pagination.setPageNo(mpScenicSpotsQO.getPageNo());
			pagination.setPageSize(mpScenicSpotsQO.getPageSize());
			pagination = hotScenicSpotDao.queryPagination(pagination);

			map.put("hotScenicSpot", pagination.getList());
			map.put("count", pagination.getTotalCount());
		}
		return map; 
	}
	
	/**
	 * 取消最旧当前热门景点信息
	 * @param mpHotScenicSpot
	 */
	public void removeCurrentHotScenicSpot(MPHotScenicSpot mpHotScenicSpot){
		mpHotScenicSpot.removeCurrentHotScenicSpot();
		hotScenicSpotDao.update(mpHotScenicSpot);
	}
	
	/**
	 * 新建热门景点
	 * @param command
	 */
	public void createHotScenicSpot(CreateHotScenicSpotCommand command){
		//判断热门景点里是否已存在
		HslHotScenicSpotQO qo=new HslHotScenicSpotQO();
		qo.setScenicSpotId(command.getScenicSpotId());
		MPHotScenicSpot hotspot= hotScenicSpotDao.queryUnique(qo);
		if(hotspot==null){
			//新增
			MPHotScenicSpot mpHotScenicSpot=new MPHotScenicSpot();
			mpHotScenicSpot.createHotScenicSpot(command);
			MPScenicSpot scenicSpot = scenicSpotDao.get(command.getScenicSpotId());
			if (scenicSpot == null) {
				scenicSpotDao.save(mpHotScenicSpot.getScenicSpot());
			}
			hotScenicSpotDao.save(mpHotScenicSpot);
			if (scenicSpot != null) {
				scenicSpotDao.evict(scenicSpot);
			}
			scenicSpotDao.update(mpHotScenicSpot.getScenicSpot());
		}else{
			//更新
			hotspot.setCreateDate(command.getCreateDate());
			hotspot.setOpen(command.getOpen());
			hotspot.setOpenDate(command.getOpenDate());
			MPScenicSpot scenicSpot=hotspot.getScenicSpot();
			MPScenicSpotsBaseInfo baseInfo=scenicSpot.getScenicSpotBaseInfo();
			MPScenicSpotsGeographyInfo geographyInfo=scenicSpot.getScenicSpotGeographyInfo();
			baseInfo.setAlias(command.getAlias());
			baseInfo.setGrade(command.getGrade());
			baseInfo.setImage(command.getImage());
			baseInfo.setIntro(command.getIntro());
			baseInfo.setName(command.getName());
			scenicSpot.setScenicSpotBaseInfo(baseInfo);
			geographyInfo.setAddress(command.getAddress());
			geographyInfo.setCityCode(command.getCity());
			geographyInfo.setProvinceCode(command.getProvince());
			geographyInfo.setTraffic(command.getTraffic());
			scenicSpot.setScenicSpotGeographyInfo(geographyInfo);
			getDao().update(scenicSpot);
			getDao().update(hotspot);
		}
		
			
	}
	
	/**
	 * 设置热门景点为当前热门景点
	 * @param command
	 */
	public void modifyCurrentHotScenicSpot(ModifyHotScenicSpotCommand command){
		MPHotScenicSpot mpHotScenicSpot=hotScenicSpotDao.get(command.getId());
		mpHotScenicSpot.modifyCurrentHotScenicSpot(command);
		hotScenicSpotDao.update(mpHotScenicSpot);
	}
	
	@Override
	protected ScenicSpotDao getDao() {
		return scenicSpotDao;
	}

	

	
	/**
	 * ScenicSpotSnapshotDTO转换成MPScenicSpot
	 * @param dto
	 * @return
	 */
//	private MPScenicSpot transFormScenicSpotSnapshotDTO(slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO dto){
//		
//		MPScenicSpotsBaseInfo baseInfo=new MPScenicSpotsBaseInfo();
//		baseInfo.setName(dto.getTcScenicSpotsName());
//		
//		MPScenicSpotsGeographyInfo geographyInfo=new MPScenicSpotsGeographyInfo();
//		geographyInfo.setProvinceCode(dto.getProvinceCode());
//		geographyInfo.setCityCode(dto.getCityCode());
//		
//		MPScenicSpot mpScenicSpot=new MPScenicSpot();
//		mpScenicSpot.setScenicSpotBaseInfo(baseInfo);
//		mpScenicSpot.setScenicSpotGeographyInfo(geographyInfo);
//		mpScenicSpot.setId(dto.getScenicSpotsId());
//		
//		return mpScenicSpot;
//	}
	
	/**
	 * TCSupplierPolicySnapshotDTO转换成MPPolicy
	 * @param tcSupplierPolicySnapshotDTO
	 * @return
	 */
	private MPPolicy transFromTCSupplierPolicySnapshotDTO(TCSupplierPolicySnapshotDTO tcSupplierPolicySnapshotDTO){
		MPPolicy policy=BeanMapperUtils.map(tcSupplierPolicySnapshotDTO, MPPolicy.class);
		if(tcSupplierPolicySnapshotDTO.getNotice().getNoticeTypes()!=null || tcSupplierPolicySnapshotDTO.getNotice().getNoticeTypes().size()!=0){
			policy.setBuyNotie(JSON.toJSONString(tcSupplierPolicySnapshotDTO.getNotice().getNoticeTypes()));
		}
		return policy;
	}
	

	

	
	
	public List<ClickRate> queryScenicSpotClickRate(HslRankListQO rankListQO){
		Pagination pagination=new Pagination();
		ClickRateQo clickRateQo=new ClickRateQo();
		pagination.setPageNo(1);
		pagination.setPageSize(rankListQO.getPageSize());
		pagination.setCondition(clickRateQo);
		pagination=clickRateDao.queryPagination(pagination);
		
		List<ClickRate>list = (List<ClickRate>)pagination.getList();
		
		return list;
	}
	
	/**********************1.3添加功能*************************/
	
	/**
	 * 创建特价门票
	 * @param command
	 */
	public void CreateSpecScenic(CreateSpecCommand command){
		SpecialOfferMp entity = new SpecialOfferMp();
		entity.create(command);
		specOfferDao.save(entity);
	}

	/**
	 * 获取特价门票列表
	 * @param pagination
	 * @return
	 */
	public Pagination getSpecialList(Pagination pagination) {
		List<AdDTO> adlist = (List<AdDTO>)pagination.getList();
		List<SpecialOfferMpDTO> dtos = new ArrayList<SpecialOfferMpDTO>();
		if(adlist!=null&&adlist.size()>0){
			HslSpecOfferMpQO hslSpecOfferMpQO = (HslSpecOfferMpQO)pagination.getCondition();
			for(AdDTO adDTO : adlist){
				hslSpecOfferMpQO.setAdId(adDTO.getId());
				List<SpecialOfferMp> list = specOfferDao.queryList(hslSpecOfferMpQO);
				if(list!=null&&list.size()>0){
					SpecialOfferMpDTO dto = BeanMapperUtils.map(list.get(0), SpecialOfferMpDTO.class);
					HslAdDTO addto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
					dto.setAdBaseInfo(addto.getAdBaseInfo());
					dto.setAdStatus(addto.getAdStatus());
					dto.setPosition(addto.getPosition());
					dtos.add(dto);
				}else{
					try {
						/*DeleteAdCommand adCommand = new DeleteAdCommand();
						adCommand.setAdId(adDTO.getId());
						adService.deletAd(adCommand);*/
					} catch (Exception e) {
						e.printStackTrace();
						HgLogger.getInstance().error("zhuxy", "特价门票，回滚删除没有了明细的广告出错"+HgLogger.getStackTrace(e));
					}
				}
			}
			
		}
		pagination.setList(dtos);
		return pagination;
	}

	/**
	 * 根据id获取特价门票
	 * @param id
	 * @return
	 */
	public SpecialOfferMpDTO getSpec(String id) {
		SpecialOfferMp specialOfferMp = specOfferDao.get(id);
		return BeanMapperUtils.map(specialOfferMp, SpecialOfferMpDTO.class);
	}
	
	/**
	 * 更新特价门票
	 * @param command
	 */
	public void updateSpec(UpdateSpecCommand command){
		SpecialOfferMp entity = specOfferDao.get(command.getId());
		entity.update(command);
		specOfferDao.update(entity);
	}
	
	/**
	 * 删除特价门票
	 * @param command
	 */
	public void deleteSpec(DeleteSpecCommand command){
		specOfferDao.deleteById(command.getId());
	}

	
	/**
	 * 创建pc热门景点
	 * @param command
	 */
	public void CreatePCHot(CreatePCHotSecnicSpotCommand command) {
		HotScenicSpot entity = new HotScenicSpot();
		entity.create(command);
		MPScenicSpot scenicSpot = scenicSpotDao.get(command.getScenicSpotId());
		if (scenicSpot == null) {
			scenicSpotDao.save(entity.getScenicSpot());
		}
		if (scenicSpot != null) {
			scenicSpotDao.evict(scenicSpot);
		}
		scenicSpotDao.update(entity.getScenicSpot());
		pcHotScenicDao.save(entity);
	}

	/**
	 * 查询pc热门景点
	 * @param pagination
	 */
	public Pagination getPCHotScenicSpots(Pagination pagination) {
		List<AdDTO> adlist = (List<AdDTO>)pagination.getList();
		List<PCHotScenicSpotDTO> dtos = new ArrayList<PCHotScenicSpotDTO>();
		if(adlist!=null&&adlist.size()>0){
			HslPCHotScenicSpotQO hslPCHotScenicSpotQO = (HslPCHotScenicSpotQO)pagination.getCondition();
			for(AdDTO adDTO : adlist){
				hslPCHotScenicSpotQO.setAdId(adDTO.getId());
				List<HotScenicSpot> list = pcHotScenicDao.queryList(hslPCHotScenicSpotQO);
				if(list!=null&&list.size()>0){
					PCHotScenicSpotDTO dto = BeanMapperUtils.map(list.get(0), PCHotScenicSpotDTO.class);
					HslAdDTO addto = BeanMapperUtils.map(adDTO, HslAdDTO.class);
					dto.setAdBaseInfo(addto.getAdBaseInfo());
					dto.setAdStatus(addto.getAdStatus());
					dto.setPosition(addto.getPosition());
					dtos.add(dto);
				}
			}
			
		}
		pagination.setList(dtos);
		return pagination;
	}

/**
	 * 删除pc端热门景点
	 * @param command
	 */
	public void deleteHotSpot(DeletePCHotCommand command) {
	//		DeletePCHotCommand
			if(StringUtils.isNotBlank(command.getId())){
				//根据id删除热门景点
				pcHotScenicDao.deleteById(command.getId());
			}else if(StringUtils.isNotBlank(command.getAdId())){
				//根据广告id删除热门景点
				HslPCHotScenicSpotQO hotScenicSpotQO = new HslPCHotScenicSpotQO();
				hotScenicSpotQO.setAdId(command.getAdId());
				List<HotScenicSpot> list = pcHotScenicDao.queryList(hotScenicSpotQO);
				HotScenicSpot entity = null;
				if(list!=null&&list.size()>0){
					entity = list.get(0);
					pcHotScenicDao.delete(entity);
				}
			}
		}
}
