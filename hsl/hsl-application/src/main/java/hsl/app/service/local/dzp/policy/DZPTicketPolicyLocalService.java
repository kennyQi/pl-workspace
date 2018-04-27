package hsl.app.service.local.dzp.policy;

import hg.common.component.BaseServiceImpl;
import hg.common.util.UUIDGenerator;
import hg.dzpw.dealer.client.api.v1.request.TicketPolicyQO;
import hg.dzpw.dealer.client.api.v1.response.TicketPolicyResponse;
import hg.dzpw.dealer.client.common.util.DealerApiClient;
import hg.dzpw.dealer.client.dto.policy.*;
import hg.log.util.HgLogger;
import hsl.app.dao.dzp.scenicspot.DZPScenicSpotDAO;
import hsl.app.dao.dzp.policy.DZPTicketPolicyDatePriceDAO;
import hsl.app.dao.dzp.policy.DZPTicketPolicyDAO;
import hsl.app.dao.dzp.policy.DZPTicketPolicySnapshotDAO;
import hsl.domain.model.dzp.policy.*;
import hsl.domain.model.dzp.scenicspot.DZPScenicSpot;
import hsl.pojo.exception.DZPTicketPolicyException;
import hsl.pojo.exception.ShowMessageException;
import hsl.pojo.qo.dzp.scenicspot.DZPScenicSpotQO;
import hsl.pojo.qo.dzp.policy.DZPTicketPolicyQO;
import hsl.pojo.util.HSLConstants;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 电子票务-门票政策Service
 * Created by huanggg on 2016/3/3.
 */
@Service
@Transactional
public class DZPTicketPolicyLocalService extends BaseServiceImpl<DZPTicketPolicy,DZPTicketPolicyQO,DZPTicketPolicyDAO> {

	private String    devName            = "hgg";
	@Autowired
	private DZPTicketPolicyDAO          dzpTicketPolicyDao;
	@Autowired
	private DZPTicketPolicyDatePriceDAO             datePriceDao;
	@Autowired
	private DZPScenicSpotDAO            dzpwScenicspotDao;
	@Autowired
	private DealerApiClient              dzpwDealerApiClient;
	@Autowired
	private DZPTicketPolicySnapshotDAO dZPTicketPolicySnapshotDao;

	@Override
	protected DZPTicketPolicyDAO getDao() {

		return dzpTicketPolicyDao;
	}
	/**
	 * 电子票务-门票列表
	 * @param qo
	 * @return
	 */
	public List<DZPTicketPolicy> list(DZPTicketPolicyQO qo) throws ShowMessageException{

		if(StringUtils.isBlank(qo.getScenicSpotId())){
			throw new ShowMessageException("传入参数错误");
		}

		//门票列表
		List<DZPTicketPolicy>  ticketPolicies = dzpTicketPolicyDao.queryList(qo);

		if(CollectionUtils.isEmpty(ticketPolicies)){
			return null;
		}

		return ticketPolicies;
	}

	/**
	 * 从电子票务 同步门票政策
	 */
	public Integer syncTicketPolicy(TicketPolicyQO qo) throws DZPTicketPolicyException {

		HgLogger.getInstance().info(devName,"同步电子票务开始");
		qo.setCalendarFetch(true);
		qo.setSingleTicketPoliciesFetch(true);
		Date now=new Date();
		qo.setModifyDateEnd(now);

		//【1】调用电子票务借口抓取门票政策数据
		TicketPolicyResponse response = dzpwDealerApiClient.send(qo, TicketPolicyResponse.class);
		Integer totalCount = response.getTotalCount();
		List<TicketPolicyDTO> ticketPolicyDTOs = response.getTicketPolicies();
		if(CollectionUtils.isEmpty(ticketPolicyDTOs)){
			return -1;
		}

		//【2】门票政策,价格日历数据处理
		updateTicketPolicy(ticketPolicyDTOs);

		HgLogger.getInstance().info(devName,"同步电子票务结束");

		return ticketPolicyDTOs.size();
	}

	/**
	 * 门票政策,价格日历数据处理
	 * @param ticketPolicyDTOs
	 * @throws hsl.pojo.exception.DZPTicketPolicyException
	 */
	private void updateTicketPolicy(List<TicketPolicyDTO> ticketPolicyDTOs) throws DZPTicketPolicyException {

		for (TicketPolicyDTO ticketPolicyDTO:ticketPolicyDTOs){

			Integer type = ticketPolicyDTO.getType();

			//【1】先判断是否存在门票政策
			DZPTicketPolicyQO qo = new DZPTicketPolicyQO();
			qo.setId(ticketPolicyDTO.getId());
			qo.setPriceFetch(true);

			DZPTicketPolicy ticketPolicy = dzpTicketPolicyDao.queryUnique(qo);
			//【2】不存在
			if(ticketPolicy == null){

				HgLogger.getInstance().info(devName, "DZPWTicketPolicyLocalService->updateDZPWTicketPolicy->本地没有对应电子票务,新建");
				//创建门票政策
				ticketPolicy = createDZPWTicketPolicy(ticketPolicyDTO, type);
				dzpTicketPolicyDao.save(ticketPolicy);
				HgLogger.getInstance().info(devName, "DZPWTicketPolicyLocalService->updateDZPWTicketPolicy->新建"+(type==TicketPolicyDTO.TICKET_POLICY_TYPE_GROUP?"联票":"单票")+"结束");
			//【3】存在
			}else{
				//如果版本不一样，不更新门票政策
				Integer visionA  = ticketPolicyDTO.getVersion();//电子票务版本
				Integer visionB = ticketPolicy.getVersion();//本地票务版本
				if(!visionA.equals(visionB)){
					//更新门票政策
					updateTicketPolicy(ticketPolicyDTO,ticketPolicy,type);
					dzpTicketPolicyDao.update(ticketPolicy);
				}else{
					HgLogger.getInstance().info(devName, "DZPWTicketPolicyLocalService->updateDZPWTicketPolicy->版本一致不需要更新!!门票ID:"+ticketPolicyDTO.getId());
				}
			}

		}
	}

	/**
	 * 创建门票政策
	 * @param dto
	 * @param type
	 * @return
	 */
	private DZPTicketPolicy createDZPWTicketPolicy(TicketPolicyDTO dto,int type){

		//【1】创建门票政策
		DZPTicketPolicy dzpwpolicy = new DZPTicketPolicy();
		dzpwpolicy.setBaseInfo(new DZPTicketPolicyBaseInfo());
		dzpwpolicy.setUseInfo(new DZPTicketPolicyUseCondition());
		dzpwpolicy.setSellInfo(new DZPTicketPolicySellInfo());
		dzpwpolicy.setStatus(new DZPTicketPolicyStatus());
		dzpwpolicy.setId(dto.getId());
		//创建门票政策快照
		DZPTicketPolicySnapshot dzpwpolicySnapshot = createDZPWTicketPolicySnapshot(dto, type);
		dzpwpolicy.setLastSnapshot(dzpwpolicySnapshot);
		dzpTicketPolicyDao.save(dzpwpolicy);
		//【2】更新门票政策
		dzpwpolicy = updateTicketPolicy(dto, dzpwpolicy, type);

		return dzpwpolicy;
	}

	/**
	 * 创建门票政策快照
	 * @param dto
	 * @param type
	 * @return
	 */
	private DZPTicketPolicySnapshot createDZPWTicketPolicySnapshot(TicketPolicyDTO dto,int type){

		//【1】创建门票政策快照
		DZPTicketPolicySnapshot dzpwpolicySnapshot = new DZPTicketPolicySnapshot();
		dzpwpolicySnapshot.setBaseInfo(new DZPTicketPolicyBaseInfo());
		dzpwpolicySnapshot.setUseInfo(new DZPTicketPolicyUseCondition());
		dzpwpolicySnapshot.setSellInfo(new DZPTicketPolicySellInfo());
		dzpwpolicySnapshot.setId(UUIDGenerator.getUUID());

		dZPTicketPolicySnapshotDao.save(dzpwpolicySnapshot);
		//【2】更新门票政策快照
		dzpwpolicySnapshot = updateTicketPolicySnapshot(dto, dzpwpolicySnapshot);

		return dzpwpolicySnapshot;
	}

	/**
	 * 更新门票
	 * @param dto
	 * @param dzpwpolicy
	 * @param type
	 * @return
	 */
	private DZPTicketPolicy updateTicketPolicy(TicketPolicyDTO dto,DZPTicketPolicy dzpwpolicy,int type){
		//设置版本
		dzpwpolicy.setVersion(dto.getVersion());
		dzpwpolicy.setType(dto.getType());//门票类型
		//设置所属门票政策
		String parentId = dto.getParentId();
		DZPTicketPolicyQO qo = new DZPTicketPolicyQO();
		//门票
		if(StringUtils.isBlank(parentId)){
			List<TicketPolicyDTO> singleTicketPolicies = dto.getSingleTicketPolicies();
			if(CollectionUtils.isNotEmpty(singleTicketPolicies)){
				//设置门票政策详情
				for(TicketPolicyDTO ticketPolicy : singleTicketPolicies){
					qo.setId(ticketPolicy.getId());
					DZPTicketPolicy dzpTicketPolicyChild = dzpTicketPolicyDao.queryUnique(qo);
					if(dzpTicketPolicyChild != null){
						dzpwpolicy.getSingleTicketPolicies().add(dzpTicketPolicyChild);
					}
				}
			}
		}else {//门票详情
			qo.setId(parentId);
			DZPTicketPolicy dzpTicketPolicy = dzpTicketPolicyDao.queryUnique(qo);
			if(dzpTicketPolicy != null){
				dzpwpolicy.setParent(dzpTicketPolicy);
			}
		}
		//【1】基本信息
		TicketPolicyBaseInfoDTO dtobaseinfo = dto.getBaseInfo();
		DZPTicketPolicyBaseInfo baseinfo = dzpwpolicy.getBaseInfo();
		baseinfo.setCreateDate(dtobaseinfo.getCreateDate());//创建时间
		baseinfo.setModifyDate(dtobaseinfo.getModifyDate());//修改时间
		baseinfo.setIntro(dtobaseinfo.getIntro());//联票OR单票介绍
		baseinfo.setName(dtobaseinfo.getName());//名称
		baseinfo.setShortName(dtobaseinfo.getShortName());//简称
		baseinfo.setKey(dtobaseinfo.getKey());//代码
		baseinfo.setNotice(dtobaseinfo.getNotice());//预定须知
		baseinfo.setSaleAgreement(dtobaseinfo.getSaleAgreement());//售卖协议
		baseinfo.setTraffic(dtobaseinfo.getTraffic());//交通信息
		baseinfo.setScenicSpotNameStr(dtobaseinfo.getScenicSpotNameStr());//包含景点
		//单票、联票门市价/市场票面价
		baseinfo.setRackRate(dtobaseinfo.getRackRate());
		//联票(与经销商)游玩价
		baseinfo.setPlayPrice(dtobaseinfo.getPlayPrice());

		//【2】使用条件
		DZPTicketPolicyUseCondition useCondition = dzpwpolicy.getUseInfo();
		TicketPolicyUseConditionDTO dtousecondition = dto.getUseInfo();
		//可游玩景点
		useCondition.setValidDays(dtousecondition.getValidDays());//门票使用条件：固定有效期
		useCondition.setValidUseDateType(dtousecondition.getValidUseDateType());
		useCondition.setValidTimesPerDay(dtousecondition.getValidTimesPerDay());//单票单天可入园次数
		useCondition.setValidTimesTotal(dtousecondition.getValidTimesTotal());//单票可入园总次数
		useCondition.setGroupPolicyFirst(dtousecondition.getGroupPolicyFirst());//是否套票使用条件优先

		//【3】门票销售信息
		TicketPolicySellInfoDTO sellInfoDto = dto.getSellInfo();
		DZPTicketPolicySellInfo sellinfo =dzpwpolicy.getSellInfo();
		sellinfo.setAutoMaticRefund(sellInfoDto.getAutoMaticRefund());//是否过期自动退款
		sellinfo.setReturnCost(sellInfoDto.getReturnCost());//退票手续费
		sellinfo.setReturnRule(sellInfoDto.getReturnRule());//退票规则

		//【4】门票政策状态
		TicketPolicyStatusDTO dzpwStatus = dto.getStatus();
		DZPTicketPolicyStatus hslStatus = dzpwpolicy.getStatus();
		hslStatus.setFinished(TicketPolicyStatusDTO.TICKET_POLICY_STATUS_FINISH.equals(dzpwStatus.getCurrent()));

		//【5】设置景区
		String scenicSpotId = dto.getScenicSpotId();
		if(StringUtils.isNotBlank(scenicSpotId)){
			DZPScenicSpotQO scenicSpotQo = new DZPScenicSpotQO();
			scenicSpotQo.setId(scenicSpotId);
			DZPScenicSpot scenicSpot = dzpwScenicspotDao.queryUnique(scenicSpotQo);
			if(scenicSpot != null){
				dzpwpolicy.setScenicSpot(scenicSpot);
			}
		}

		//【6】设置快照
		DZPTicketPolicySnapshot lastSnapshot = dzpwpolicy.getLastSnapshot();
		if(lastSnapshot != null){//已经存在快照
			lastSnapshot = updateTicketPolicySnapshot(dto, lastSnapshot);
		}else{//不存在快照
			lastSnapshot = createDZPWTicketPolicySnapshot(dto, type);
		}
		dzpwpolicy.setLastSnapshot(lastSnapshot);

		//【7】价格日历
		updateDatePrice(dto, dzpwpolicy);

		return dzpwpolicy;
	}

	/**
	 * 更新门票政策快照
	 * @param dto
	 * @param dzpwpolicySnapshot
	 * @return
	 */
	private DZPTicketPolicySnapshot updateTicketPolicySnapshot(TicketPolicyDTO dto,DZPTicketPolicySnapshot dzpwpolicySnapshot){
		//设置版本
		dzpwpolicySnapshot.setVersion(dto.getVersion());//版本
		dzpwpolicySnapshot.setTicketPolicyId(dto.getId());//所属票务ID
		dzpwpolicySnapshot.setType(dto.getType());//门票快照类型
		//【1】基本信息
		TicketPolicyBaseInfoDTO dtobaseinfo = dto.getBaseInfo();
		DZPTicketPolicyBaseInfo baseinfo = dzpwpolicySnapshot.getBaseInfo();
		baseinfo.setCreateDate(dtobaseinfo.getCreateDate());//创建时间
		baseinfo.setModifyDate(dtobaseinfo.getModifyDate());//修改时间
		baseinfo.setIntro(dtobaseinfo.getIntro());//联票OR单票介绍
		baseinfo.setName(dtobaseinfo.getName());//名称
		baseinfo.setShortName(dtobaseinfo.getShortName());//简称
		baseinfo.setKey(dtobaseinfo.getKey());//代码
		baseinfo.setNotice(dtobaseinfo.getNotice());//预定须知
		baseinfo.setSaleAgreement(dtobaseinfo.getSaleAgreement());//售卖协议
		baseinfo.setTraffic(dtobaseinfo.getTraffic());//交通信息
		baseinfo.setScenicSpotNameStr(dtobaseinfo.getScenicSpotNameStr());//包含景点
		//单票、联票门市价/市场票面价
		baseinfo.setRackRate(dtobaseinfo.getRackRate());
		//联票(与经销商)游玩价
		baseinfo.setPlayPrice(dtobaseinfo.getPlayPrice());

		//【2】使用条件
		DZPTicketPolicyUseCondition useCondition = dzpwpolicySnapshot.getUseInfo();
		TicketPolicyUseConditionDTO dtousecondition = dto.getUseInfo();
		//可游玩景点
		useCondition.setValidDays(dtousecondition.getValidDays());//门票使用条件：固定有效期
		useCondition.setValidUseDateType(dtousecondition.getValidUseDateType());
		useCondition.setValidTimesPerDay(dtousecondition.getValidTimesPerDay());//单票单天可入园次数
		useCondition.setValidTimesTotal(dtousecondition.getValidTimesTotal());//单票可入园总次数
		useCondition.setGroupPolicyFirst(dtousecondition.getGroupPolicyFirst());//是否套票使用条件优先

		//【3】门票销售信息
		TicketPolicySellInfoDTO sellInfoDto = dto.getSellInfo();
		DZPTicketPolicySellInfo sellinfo =dzpwpolicySnapshot.getSellInfo();
		sellinfo.setAutoMaticRefund(sellInfoDto.getAutoMaticRefund());//是否过期自动退款
		sellinfo.setReturnCost(sellInfoDto.getReturnCost());//退票手续费
		sellinfo.setReturnRule(sellInfoDto.getReturnRule());//退票规则

		//【5】设置景区
		String scenicSpotId = dto.getScenicSpotId();
		if(StringUtils.isNotBlank(scenicSpotId)){
			DZPScenicSpotQO scenicSpotQo = new DZPScenicSpotQO();
			scenicSpotQo.setId(scenicSpotId);
			DZPScenicSpot scenicSpot = dzpwScenicspotDao.queryUnique(scenicSpotQo);
			if(scenicSpot != null){
				dzpwpolicySnapshot.setScenicSpotId(scenicSpotId);
				dzpwpolicySnapshot.setScenicSpotName(scenicSpot.getBaseInfo().getName());
			}
		}

		//【6】价格日历快照
		updateDatePriceSnapshot(dto, dzpwpolicySnapshot);

		return dzpwpolicySnapshot;
	}

	/**
	 * 更新门票政策 --价格日历
	 * @param dto
	 * @param dzpwpolicy
	 */
	private void updateDatePrice(TicketPolicyDTO dto,DZPTicketPolicy dzpwpolicy){

		List<TicketPolicyDatePriceDTO> dzpwPricelist = dto.getCalendar().getPrices();
		Map<String, DZPTicketPolicyDatePrice> hslPriceMap = dzpwpolicy.getPrice();
		if(CollectionUtils.isNotEmpty(dzpwPricelist)){
			//用提供的价格日历
			dzpwpolicy.setPrice(modifyDateSalePrice(dzpwPricelist, dzpwpolicy));
			//更新门票政策
			dzpTicketPolicyDao.update(dzpwpolicy);
		}else{
			//电子票务没传价格日历的，暂不处理
		}

	}

	/**
	 * 更新门票政策快照 --价格日历
	 * @param dto
	 * @param dzpwpolicySnapshot
	 */
	private void updateDatePriceSnapshot(TicketPolicyDTO dto,DZPTicketPolicySnapshot dzpwpolicySnapshot){

		List<TicketPolicyDatePriceDTO> dzpwPricelist = dto.getCalendar().getPrices();
		Map<String, DZPTicketPolicySnapshotDatePrice> hslPriceMap = dzpwpolicySnapshot.getPrice();
		if(CollectionUtils.isNotEmpty(dzpwPricelist)){
			//用提供的价格日历
			dzpwpolicySnapshot.setPrice(modifyDateSalePriceSnapshot(dzpwPricelist, dzpwpolicySnapshot));
			//更新门票政策
			dZPTicketPolicySnapshotDao.update(dzpwpolicySnapshot);
		}else{
			//电子票务没传价格日历的，暂不处理
		}

	}

	/**
	 * 根据电子票务返回的结果设置价格日历快照
	 * @param dzpwpolicySnapshot
	 * @param dzpwPricelist
	 * @return
	 */
	private Map<String, DZPTicketPolicySnapshotDatePrice> modifyDateSalePriceSnapshot(List<TicketPolicyDatePriceDTO> dzpwPricelist , DZPTicketPolicySnapshot dzpwpolicySnapshot){

		Map<String, DZPTicketPolicySnapshotDatePrice> hslPriceMap = new HashMap<String, DZPTicketPolicySnapshotDatePrice>();
		//先清空原旧门票的价格日历
		Map<String, DZPTicketPolicySnapshotDatePrice> currentDatePriceSnapshot = dzpwpolicySnapshot.getPrice();
		Set<String> keys = currentDatePriceSnapshot.keySet();
		for(String key : keys){
			DZPTicketPolicySnapshotDatePrice tpdps = currentDatePriceSnapshot.get(key);
			datePriceDao.delete(tpdps);
		}
		//循环遍历价格日历
		for(TicketPolicyDatePriceDTO datePriceDTO : dzpwPricelist){
			//创建价格日历
			DZPTicketPolicySnapshotDatePrice datePrice = new DZPTicketPolicySnapshotDatePrice();
			datePrice.setTicketPolicySnapshot(dzpwpolicySnapshot);
			datePrice.setId(UUIDGenerator.getUUID());
			datePrice.setDate(datePriceDTO.getDate());
			datePrice.setPrice(datePriceDTO.getPrice());
			datePriceDao.save(datePrice);
			hslPriceMap.put(datePrice.getDate(),datePrice);
		}

		return hslPriceMap;
	}

	/**
	 * 根据电子票务返回的结果设置价格日历
	 * @param dzpwpolicy
	 * @param dzpwPricelist
	 * @return
	 */
	private Map<String, DZPTicketPolicyDatePrice> modifyDateSalePrice(List<TicketPolicyDatePriceDTO> dzpwPricelist , DZPTicketPolicy dzpwpolicy){

		Map<String, DZPTicketPolicyDatePrice> hslPriceMap = new HashMap<String, DZPTicketPolicyDatePrice>();
		Map<String, DZPTicketPolicyDatePrice> currentDatePrice = dzpwpolicy.getPrice();
		Set<String> keys = currentDatePrice.keySet();
		for(String key : keys){
			DZPTicketPolicyDatePrice tpdp = currentDatePrice.get(key);
			datePriceDao.delete(tpdp);
		}
		dzpwpolicy.setPrice(hslPriceMap);//先清空原旧门票的价格日历
		Double lowerPrice = 0D;//最低价格
		Double singlePrice = 0D;//景区单票最低价格
		Double groupPrice = 0D;//景区联票最低价格
		//循环遍历价格日历
		for(TicketPolicyDatePriceDTO datePriceDTO : dzpwPricelist){

			//获取最低价格
			Double currentPrice = datePriceDTO.getPrice();
			if(currentPrice > lowerPrice){
				lowerPrice = currentPrice;
			}
			//创建价格日历
			DZPTicketPolicyDatePrice datePrice = new DZPTicketPolicyDatePrice();
			datePrice.setTicketPolicy(dzpwpolicy);
			datePrice.setId(UUIDGenerator.getUUID());
			datePrice.setDate(datePriceDTO.getDate());
			datePrice.setPrice(currentPrice);
			datePriceDao.save(datePrice);
			hslPriceMap.put(datePrice.getDate(),datePrice);
		}

		dzpwpolicy.getSellInfo().setPriceMin(lowerPrice);
		dzpwpolicy.getLastSnapshot().getSellInfo().setPriceMin(lowerPrice);

		//获取到门票所属景区
		DZPScenicSpot scenicSpot = dzpwpolicy.getScenicSpot();
		//获取到景区包含门票列表
		List<DZPTicketPolicy> ticketPolicies = scenicSpot.getTicketPolicies();
		if(CollectionUtils.isNotEmpty(ticketPolicies)){
			for (DZPTicketPolicy ticketPolicy : ticketPolicies){
				//【1】景区单票最低价格
				if(HSLConstants.DZPGroupTicketType.TICKET_TYPE_SINGLE.equals(ticketPolicy.getType())){
					Double currentSinglePrice = ticketPolicy.getSellInfo().getPriceMin();
					if(currentSinglePrice == null){
						continue;
					}
					singlePrice = Math.min(singlePrice, currentSinglePrice);
				}
				//【2】景区联票最低价格
				if(HSLConstants.DZPGroupTicketType.TICKET_TYPE_GROUP.equals(ticketPolicy.getType())){
					Double currentGroupPrice = ticketPolicy.getSellInfo().getPriceMin();
					if(currentGroupPrice == null){
						continue;
					}
					groupPrice = Math.min(groupPrice, currentGroupPrice);
				}
				//【3】景区所有门票中最低价格
				lowerPrice = Math.min(singlePrice, groupPrice);
			}
		}

		dzpwpolicy.getScenicSpot().setPriceMin(lowerPrice);//景区所有门票中最低价格
		dzpwpolicy.getScenicSpot().setSinglePriceMin(singlePrice);//景区单票最低价格
		dzpwpolicy.getScenicSpot().setGroupPriceMin(groupPrice);//景区联票最低价格
		dzpTicketPolicyDao.update(dzpwpolicy);

		return hslPriceMap;
	}

}
