package hsl.app.service.spi.mp;
import hg.common.component.BaseQo;
import hg.common.page.Pagination;
import hg.common.util.BeanMapperUtils;
import hg.common.util.UUIDGenerator;
import hg.log.po.clickrecord.ClickRate;
import hg.log.util.HgLogger;
import hg.service.ad.command.ad.CreateAdCommand;
import hg.service.ad.command.ad.DeleteAdCommand;
import hg.service.ad.command.ad.UpdateAdCommand;
import hg.service.ad.pojo.dto.ad.AdDTO;
import hg.service.ad.pojo.qo.ad.AdQO;
import hg.service.ad.spi.inter.AdService;
import hsl.app.common.util.ClientKeyUtil;
import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.mp.ScenicSpotLocalService;
import hsl.domain.model.mp.scenic.MPHotScenicSpot;
import hsl.pojo.command.CreateHotScenicSpotCommand;
import hsl.pojo.command.ModifyHotScenicSpotCommand;
import hsl.pojo.command.ad.CreatePCHotSecnicSpotCommand;
import hsl.pojo.command.ad.CreateSpecCommand;
import hsl.pojo.command.ad.DeleteSpecCommand;
import hsl.pojo.command.ad.UpdatePCHotCommand;
import hsl.pojo.command.ad.UpdateSpecCommand;
import hsl.pojo.dto.ad.HslAdConstant;
import hsl.pojo.dto.ad.HslAdDTO;
import hsl.pojo.dto.mp.BookInfoDTO;
import hsl.pojo.dto.mp.DateSalePriceDTO;
import hsl.pojo.dto.mp.HotScenicSpotDTO;
import hsl.pojo.dto.mp.MPSimpleDTO;
import hsl.pojo.dto.mp.NoticeTypeDTO;
import hsl.pojo.dto.mp.PCScenicSpotDTO;
import hsl.pojo.dto.mp.PolicyDTO;
import hsl.pojo.dto.mp.ScenicSpotDTO;
import hsl.pojo.dto.mp.SpecialOfferMpDTO;
import hsl.pojo.exception.MPException;
import hsl.pojo.qo.mp.HslHotScenicSpotQO;
import hsl.pojo.qo.mp.HslMPDatePriceQO;
import hsl.pojo.qo.mp.HslMPPolicyQO;
import hsl.pojo.qo.mp.HslMPScenicSpotQO;
import hsl.pojo.qo.mp.HslPCHotScenicSpotQO;
import hsl.pojo.qo.mp.HslRankListQO;
import hsl.pojo.qo.mp.HslSpecOfferMpQO;
import hsl.spi.inter.mp.MPScenicSpotService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import slfx.api.base.ApiRequest;
import slfx.api.base.SlfxApiClient;
import slfx.api.v1.response.mp.MPQueryDatePriceResponse;
import slfx.api.v1.response.mp.MPQueryPolicyResponse;
import slfx.api.v1.response.mp.MPQueryScenicSpotsResponse;
import com.alibaba.fastjson.JSON;
/**
 * 实现后台景区接口功能
 * @author liujz
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class MPScenicSpotServiceImpl extends BaseSpiServiceImpl <ScenicSpotDTO,BaseQo,ScenicSpotLocalService> implements MPScenicSpotService {

	@Autowired
	private ScenicSpotLocalService scenicSpotLocalService;
	@Autowired
	private SlfxApiClient slfxApiClient;
	@Autowired
	private HgLogger hgLogger;
	@Autowired
	private AdService adService;
	
	@Override
	protected ScenicSpotLocalService getService() {
		return scenicSpotLocalService;
	}

	@Override
	protected Class<ScenicSpotDTO> getDTOClass() {
		return ScenicSpotDTO.class;
	}
	
	
	@Override
	public Map  queryScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO) throws MPException {

		Map map=new HashMap();
		//查询热门景点
		if (mpScenicSpotsQO.getHot()) {
			List<HotScenicSpotDTO> dtos=new ArrayList<HotScenicSpotDTO>();
			Map hotMap=scenicSpotLocalService.queryHotScenicSpot(mpScenicSpotsQO);
			List<MPHotScenicSpot> hotScenicSpotList=(List<MPHotScenicSpot>)hotMap.get("hotScenicSpot");
			for (MPHotScenicSpot mpHotScenicSpot : hotScenicSpotList) {
				HotScenicSpotDTO dto=new HotScenicSpotDTO();
				dto=BeanMapperUtils.map(mpHotScenicSpot.getScenicSpot(), HotScenicSpotDTO.class);
				dto.setId(mpHotScenicSpot.getId());
				dto.setCreateDate(mpHotScenicSpot.getCreateDate());
				dto.setOpenDate(mpHotScenicSpot.getOpenDate());
				dto.setScenicSpotId(mpHotScenicSpot.getScenicSpot().getId());
				dtos.add(dto);
			}
			map.put("dto", dtos);
			map.put("count", hotMap.get("count").toString());
		}else{
			
			List<PCScenicSpotDTO> dtos=new ArrayList<PCScenicSpotDTO>();
			
			//hsl.pojo.qo.mp.HslMPScenicSpotQO转换成slfx-api-client MPScenicSpotsQO
			slfx.api.v1.request.qo.mp.MPScenicSpotsQO qo=new slfx.api.v1.request.qo.mp.MPScenicSpotsQO();
			//判断是按照区域查询还是按照名字查询
			//如果按景区和按地区同时查询，按景区先查
			if(mpScenicSpotsQO.getByArea() && mpScenicSpotsQO.getByName()){
				qo.setName(mpScenicSpotsQO.getContent());
			}
			else if(mpScenicSpotsQO.getByName() && !mpScenicSpotsQO.getByArea()){
				qo.setName(mpScenicSpotsQO.getContent());
			}
			else if (mpScenicSpotsQO.getByArea() && !mpScenicSpotsQO.getByName()){
				qo.setArea(mpScenicSpotsQO.getContent());
			}
			//是否加载图片信息
			qo.setImagesFetchAble(mpScenicSpotsQO.isImagesFetchAble());
			//是否加载政策须知
			qo.setTcPolicyNoticeFetchAble(mpScenicSpotsQO.isTcPolicyNoticeFetchAble());
			
			//如果景点id不为空，添加景点id查询条件
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getScenicSpotId())){
				qo.setScenicSpotId(mpScenicSpotsQO.getScenicSpotId());
			}
			
			//如果价格区间不为空
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getMinPrice())){
				qo.setAmountAdviceMin(Double.parseDouble(mpScenicSpotsQO.getMinPrice()));
			}
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getMaxPrice())){
				qo.setAmountAdviceMax(Double.parseDouble(mpScenicSpotsQO.getMaxPrice()));
			}
			
			//如果景点级别不为空
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getGrade())&&!mpScenicSpotsQO.getGrade().equals("0")){
				qo.setGrade(mpScenicSpotsQO.getGrade());
			}
			//如果省、市不为空
			if(StringUtils.isNotBlank(mpScenicSpotsQO.getCity())&&!mpScenicSpotsQO.getCity().equals("0")){
				qo.setArea(mpScenicSpotsQO.getCity());
			}else if(StringUtils.isNotBlank(mpScenicSpotsQO.getProvince()) &&!mpScenicSpotsQO.getProvince().equals("0")&& StringUtils.isBlank(mpScenicSpotsQO.getCity())){
				qo.setArea(mpScenicSpotsQO.getProvince());
			}
			
			//按价格排序
			if(mpScenicSpotsQO.getOrderBy()==1){
				qo.setAmountAdviceSort(-1);
			}else if (mpScenicSpotsQO.getOrderBy()==2){
				qo.setAmountAdviceSort(1);
			}
			
			qo.setPageNo(mpScenicSpotsQO.getPageNo());
			qo.setPageSize(mpScenicSpotsQO.getPageSize());

			//发送请求
			hgLogger.info("liujz", "对slfx-api发送景区查询请求"+JSON.toJSONString(qo));
			ApiRequest apiRequest=new ApiRequest("MPQueryScenicSpots",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
			MPQueryScenicSpotsResponse response=slfxApiClient.send(apiRequest, MPQueryScenicSpotsResponse.class);
			hgLogger.info("liujz", "slfx-api返回景区查询结果"+JSON.toJSONString(response));
			
			//如果按景区和按地区同时查询，景区查询无结果，则按照地区查
			if (response.getScenicSpots()==null || response.getScenicSpots().size()==0) {
				if(mpScenicSpotsQO.getByArea() && mpScenicSpotsQO.getByName()){
					qo.setArea(mpScenicSpotsQO.getContent());
					qo.setName(null);
				}
				//否则报异常
				else{
					throw new MPException(MPException.RESULT_SCENICSPOT_NOTFOUND, "无查询结果");
				}
				
				//发送请求
				hgLogger.info("liujz", "对slfx-api发送景区查询请求"+JSON.toJSONString(qo));
				apiRequest=new ApiRequest("MPQueryScenicSpots",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
				MPQueryScenicSpotsResponse scenicSpotsResponse=slfxApiClient.send(apiRequest, MPQueryScenicSpotsResponse.class);
				hgLogger.info("liujz", "slfx-api返回景区查询结果"+JSON.toJSONString(scenicSpotsResponse));
				
				if(scenicSpotsResponse.getScenicSpots()==null || scenicSpotsResponse.getScenicSpots().size()==0){
					throw new MPException(MPException.RESULT_SCENICSPOT_NOTFOUND, "无查询结果");
				}
				
				//slfx-api-client ScenicSpotDTO转换成hsl-api-client ScenicSpotDTO
				dtos=BeanMapperUtils.getMapper().mapAsList(scenicSpotsResponse.getScenicSpots(), PCScenicSpotDTO.class);
				
				//遍历同程景区信息，把预订信息设置到新的dto中
				int i=0;
				for (slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO scenicSpotDTO : scenicSpotsResponse.getScenicSpots()) {
					BookInfoDTO bookInfoDTO=new BookInfoDTO();
					bookInfoDTO.setAmountAdvice(scenicSpotDTO.getTcScenicSpotInfo().getAmountAdvice());
					bookInfoDTO.setBookFlag(scenicSpotDTO.getTcScenicSpotInfo().getBookFlag());
					bookInfoDTO.setBuyNotie(scenicSpotDTO.getTcScenicSpotInfo().getTcPolicyNotice().getBuyNotie());
					bookInfoDTO.setPayMode(scenicSpotDTO.getTcScenicSpotInfo().getPayMode());
					dtos.get(i).setBookInfoDTO(bookInfoDTO);
					i++;
				}
				map.put("count", scenicSpotsResponse.getTotalCount());
			}else{
				//slfx-api-client ScenicSpotDTO转换成hsl-api-client ScenicSpotDTO
				dtos=BeanMapperUtils.getMapper().mapAsList(response.getScenicSpots(), PCScenicSpotDTO.class);
				
				//遍历同程景区信息，把预订信息设置到新的dto中
				int i=0;
				for (slfx.mp.pojo.dto.scenicspot.ScenicSpotDTO scenicSpotDTO : response.getScenicSpots()) {
					BookInfoDTO bookInfoDTO=new BookInfoDTO();
					bookInfoDTO.setAmountAdvice(scenicSpotDTO.getTcScenicSpotInfo().getAmountAdvice());
					bookInfoDTO.setBookFlag(scenicSpotDTO.getTcScenicSpotInfo().getBookFlag());
					bookInfoDTO.setBuyNotie(scenicSpotDTO.getTcScenicSpotInfo().getTcPolicyNotice().getBuyNotie());
					bookInfoDTO.setPayMode(scenicSpotDTO.getTcScenicSpotInfo().getPayMode());
					dtos.get(i).setBookInfoDTO(bookInfoDTO);
					i++;
				}
				
				map.put("count", response.getTotalCount());
			}

			map.put("dto", dtos);
		}
	

	
		return map;
	}
	

	public void removeCurrentHotScenicSpot(HslMPScenicSpotQO mpScenicSpotsQO){
		Map hotMap=scenicSpotLocalService.queryHotScenicSpot(mpScenicSpotsQO);
		List<MPHotScenicSpot> hotScenicSpotList=(List<MPHotScenicSpot>)hotMap.get("hotScenicSpot");
		scenicSpotLocalService.removeCurrentHotScenicSpot(hotScenicSpotList.get(0));
	}
	
	public void createHotScenicSpot(CreateHotScenicSpotCommand command){
		scenicSpotLocalService.createHotScenicSpot(command);
	}
	
	public void modifyCurrentHotScenicSpot(ModifyHotScenicSpotCommand command){
		scenicSpotLocalService.modifyCurrentHotScenicSpot(command);
	}
	
	
	public Map queryScenicPolicy(HslMPPolicyQO mpPolicyQO){
		
		Map map=new HashMap();
		//hsl-api-client MPPolicyQO转换成slfx-api-client MPPolicyQO
		slfx.api.v1.request.qo.mp.MPPolicyQO qo=BeanMapperUtils.map(mpPolicyQO,slfx.api.v1.request.qo.mp.MPPolicyQO.class);
		//发送查询景点政策请求
		hgLogger.info("liujz", "对slfx-api发送景点政策查询请求"+JSON.toJSONString(qo));
		ApiRequest apiRequest=new ApiRequest("MPQueryPolicy",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
		MPQueryPolicyResponse response=slfxApiClient.send(apiRequest, MPQueryPolicyResponse.class);
		hgLogger.info("liujz", "slfx-api返回景点政策查询结果"+JSON.toJSONString(response.getResult()));
		//slfx-api-client TCSupplierPolicySnapshotDTO转换成hsl-api-client PolicyDTO
		
		
		List<PolicyDTO> dtos =BeanMapperUtils.getMapper().mapAsList(response.getPolicies(), PolicyDTO.class);
		int i=0;
		for (PolicyDTO policyDTO : dtos) {
			policyDTO.setNoticeTypes(BeanMapperUtils.getMapper().mapAsList(response.getPolicies().get(i).getNotice().getNoticeTypes(), NoticeTypeDTO.class));
			i++;
		}
		
		map.put("dto", dtos);
		map.put("count", response.getTotalCount());
		
		return map;
	}
	
	
	public List<MPSimpleDTO> queryScenicSpotClickRate(HslRankListQO rankListQO){
		List<ClickRate> clickRateList=scenicSpotLocalService.queryScenicSpotClickRate(rankListQO);
		List<MPSimpleDTO> list =new ArrayList<MPSimpleDTO>();
		//转换成slfx-api-client MPScenicSpotsQO
		slfx.api.v1.request.qo.mp.MPScenicSpotsQO qo=new slfx.api.v1.request.qo.mp.MPScenicSpotsQO();
		
		for (ClickRate clickRate : clickRateList) {
			qo.setScenicSpotId(clickRate.getScenicSpotId());
			
			//发送请求
			hgLogger.info("liujz", "对slfx-api发送景区查询请求"+JSON.toJSONString(qo));
			ApiRequest apiRequest=new ApiRequest("MPQueryScenicSpots",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
			MPQueryScenicSpotsResponse response=slfxApiClient.send(apiRequest, MPQueryScenicSpotsResponse.class);
			hgLogger.info("liujz", "slfx-api返回景区查询结果"+JSON.toJSONString(response.getResult()));
			
			MPSimpleDTO dto=new MPSimpleDTO();
			if(response.getScenicSpots()!=null&&response.getScenicSpots().size()>0){
				dto.setPrice(response.getScenicSpots().get(0).getTcScenicSpotInfo().getAmountAdvice());
				dto.setScenicSpotId(response.getScenicSpots().get(0).getId());
				dto.setScenicSpotName(response.getScenicSpots().get(0).getScenicSpotBaseInfo().getName());
				list.add(dto);
			}
		}
		
		return list;
	}
	
	
	public Map queryDatePrice(HslMPDatePriceQO mpDatePriceQO) throws MPException{

		//hsl-api-client MPDatePriceQO转换成slfx-api-client MPDatePriceQO
		slfx.api.v1.request.qo.mp.MPDatePriceQO qo=new slfx.api.v1.request.qo.mp.MPDatePriceQO();
		qo.setPolicyId(mpDatePriceQO.getPolicyId());
		
		//查询价格日历请求
		hgLogger.info("liujz", "对slfx-api发送价格日历查询请求"+JSON.toJSONString(qo));
		ApiRequest apiRequest=new ApiRequest("MPQueryDatePrice",ClientKeyUtil.FROM_CLIENT_KEY, "127.0.0.1", UUIDGenerator.getUUID(), qo);
		MPQueryDatePriceResponse response=slfxApiClient.send(apiRequest, MPQueryDatePriceResponse.class);
		hgLogger.info("liujz", "slfx-api返回价格日历查询结果"+JSON.toJSONString(response.getResult()));
		if(response.getDateSalePrices()==null || response.getDateSalePrices().size()==0){
			throw new MPException(MPException.RESULT_DATEPRICE_NOTFOUND, "价格日历查询无结果");
		}
		//slfx-api-client DateSalePriceDTO转换成hsl-api-client DateSalePriceDTO
		List<DateSalePriceDTO> dtos=new ArrayList<DateSalePriceDTO>();
		SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
		for (slfx.mp.pojo.dto.platformpolicy.DateSalePriceDTO dateSalePriceDTO : response.getDateSalePrices()) {
			DateSalePriceDTO dto=new DateSalePriceDTO();
			dto.setSaleDate(format.format(dateSalePriceDTO.getSaleDate()));
			dto.setSalePrice(dateSalePriceDTO.getSalePrice());
			dtos.add(dto);
		}
		
		Map map=new HashMap();
		map.put("dto", dtos);
		map.put("count", response.getTotalCount());
		
		return map;
	}

	/**
	 * 创建特价门票
	 * @throws Exception 
	 */
	@Override
	public void createSpecScenic(CreateSpecCommand command) throws Exception {
		CreateAdCommand createAdCommand = BeanMapperUtils.map(command, CreateAdCommand.class);
		AdDTO adDTO = null;
		try {
			adDTO = adService.createAd(createAdCommand);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		if(adDTO!=null){
			//远程保存广告成功
			try{
				command.setAdId(adDTO.getId());
				scenicSpotLocalService.CreateSpecScenic(command);
			}catch(Exception e){
				e.printStackTrace();
				HgLogger.getInstance().error("zhuxy", "本地保存特价门票明细失败"+HgLogger.getStackTrace(e));
				//删除已经保存的广告
				try{
					DeleteAdCommand adCommand = new DeleteAdCommand();
					adCommand.setAdId(adDTO.getId());
					adService.deletAd(adCommand);
				}catch(Exception ex){
					ex.printStackTrace();
					HgLogger.getInstance().error("zhuxy", "保存特价门票的时候回滚添加广告操作失败"+HgLogger.getStackTrace(ex));
				}
			}
		}
	}
	
	/**
	 * 获取特价门票列表
	 */
	@Override
	public Pagination getSpecialList(Pagination pagination) {
		HslSpecOfferMpQO qo = (HslSpecOfferMpQO)pagination.getCondition();
		AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
		pagination.setCondition(adQO);
		pagination = adService.queryPagination(pagination);
		pagination.setCondition(qo);
		pagination = scenicSpotLocalService.getSpecialList(pagination);
		return pagination;
	}
	
	/**
	 * 根据特价门票id获取特价门票
	 */
	public SpecialOfferMpDTO getSpecialOfferMp(String id){
		SpecialOfferMpDTO dto = scenicSpotLocalService.getSpec(id);
		if(dto==null){
			return null;
		}
		AdQO adQO = new AdQO();
		adQO.setId(dto.getAdId());
		AdDTO adDTO = adService.queryUnique(adQO);
		if(adDTO==null){
			return null;
		}
		HslAdDTO dto2 = BeanMapperUtils.map(adDTO, HslAdDTO.class);
		dto.setAdBaseInfo(dto2.getAdBaseInfo());
		dto.setAdStatus(dto2.getAdStatus());
		dto.setPosition(dto2.getPosition());
		
		return dto;
	}

	/**
	 * 创建pc热门景点
	 * @throws MPException 
	 */
	@Override
	public void createPCHot(CreatePCHotSecnicSpotCommand command) throws MPException {
		CreateAdCommand createAdCommand = BeanMapperUtils.map(command, CreateAdCommand.class);
		//先判断是否存在
		AdQO adQO = new AdQO();
		adQO.setTitle(command.getTitle());
		List<AdDTO> ads = adService.queryList(adQO);
		if(ads!=null&&ads.size()>0){
			throw new MPException(MPException.RESULT_HOT_EXIST,"景点已经存在");
		}
		AdDTO adDTO = null;
		try {
			adDTO = adService.createAd(createAdCommand);
		} catch (Exception e) {
			e.printStackTrace();
			HgLogger.getInstance().error("zhuxy", "添加热门景点保存广告失败"+HgLogger.getStackTrace(e));
		}
		if(adDTO!=null){
			try {
				//远程保存广告成功
				command.setAdId(adDTO.getId());
				scenicSpotLocalService.CreatePCHot(command);
			} catch (Exception e) {
				e.printStackTrace();
				HgLogger.getInstance().error("zhuxy", "添加热门景点保存明细失败"+HgLogger.getStackTrace(e));
				//本地保存热门景点的明细失败，广告回滚
				try {
					DeleteAdCommand adCommand = new DeleteAdCommand();
					adCommand.setAdId(adDTO.getId());
					adService.deletAd(adCommand);
				} catch (Exception e2) {
					e2.printStackTrace();
					HgLogger.getInstance().error("zhuxy", "添加热门景点回滚广告失败"+HgLogger.getStackTrace(e2));
				}
				
			}
		}
	}

	/**
	 * 更新特价门票
	 */
	@Override
	public void updateSpec(UpdateSpecCommand command) throws Exception {
		UpdateAdCommand adCommand = BeanMapperUtils.map(command, UpdateAdCommand.class);
		if(StringUtils.isBlank(command.getId())){
			throw new Exception("特价门票主键不存在");
		}
		if(StringUtils.isBlank(command.getAdId())&&StringUtils.isNotBlank(command.getId())){
			//广告id不存在而本地id存在，则查询本地数据库找出广告id
			SpecialOfferMpDTO mpDTO = scenicSpotLocalService.getSpec(command.getId());
			if(mpDTO!=null){
				command.setAdId(mpDTO.getAdId());
			}else{
				throw new Exception("特价门票不存在");
			}
		}
		adCommand.setId(command.getAdId());
		AdDTO adDTO = null;
		try {
			adDTO = adService.UpdateAd(adCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(adDTO!=null){
			//远程保存广告成功
			scenicSpotLocalService.updateSpec(command);
		}
	}

	/**
	 * 删除特价门票
	 */
	@Override
	public void deleteSpec(DeleteSpecCommand command) throws Exception {
		if(StringUtils.isBlank(command.getId())||StringUtils.isBlank(command.getAdId())){
			throw new Exception("数据不完整");
		}
		DeleteAdCommand adCommand = BeanMapperUtils.map(command, DeleteAdCommand.class);
		try{
			adService.deletAd(adCommand);
			scenicSpotLocalService.deleteSpec(command);
		}catch(Exception e){
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public HotScenicSpotDTO getScenicSpot(HslHotScenicSpotQO qo) {
		return scenicSpotLocalService.getScenicSpotDTO(qo);
	}

	/**
	 * 查询pc热门景点列表
	 */
	@Override
	public Pagination getHotScenicSpots(Pagination pagination) {
		HslPCHotScenicSpotQO qo = (HslPCHotScenicSpotQO)pagination.getCondition();
		qo.setPositionId(HslAdConstant.HOT);
		AdQO adQO = BeanMapperUtils.getMapper().map(qo, AdQO.class);
		pagination.setCondition(adQO);
		pagination = adService.queryPagination(pagination);
		pagination.setCondition(qo);
		pagination = scenicSpotLocalService.getPCHotScenicSpots(pagination);
		return pagination;
	}

	@Override
	public void updatePCHotScenic(UpdatePCHotCommand command) {
		// TODO Auto-generated method stub
		
	}
	
}
