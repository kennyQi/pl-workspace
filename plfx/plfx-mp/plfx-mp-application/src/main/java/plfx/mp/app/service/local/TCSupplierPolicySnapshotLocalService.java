package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.common.util.DateUtil;
import hg.common.util.MD5HashUtil;
import hg.common.util.UUIDGenerator;
import hg.log.util.HgLogger;
import hg.system.cache.AddrProjectionCacheManager;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.component.manager.TCSupplierPolicySnapshotManager;
import plfx.mp.app.dao.ScenicSpotDAO;
import plfx.mp.app.dao.TCPolicyNoticeDAO;
import plfx.mp.app.dao.TCSupplierPolicySnapshotDAO;
import plfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO;
import plfx.mp.domain.model.scenicspot.ScenicSpot;
import plfx.mp.domain.model.supplierpolicy.NoticeItem;
import plfx.mp.domain.model.supplierpolicy.NoticeType;
import plfx.mp.domain.model.supplierpolicy.ScenicSpotSnapshot;
import plfx.mp.domain.model.supplierpolicy.TCPolicyNotice;
import plfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;
import plfx.mp.tcclient.tc.domain.Info;
import plfx.mp.tcclient.tc.domain.Notice;
import plfx.mp.tcclient.tc.domain.Policy;
import plfx.mp.tcclient.tc.domain.PriceQueryScenery;
import plfx.mp.tcclient.tc.dto.jd.SceneryPriceDto;
import plfx.mp.tcclient.tc.pojo.Response;
import plfx.mp.tcclient.tc.pojo.ResultSceneryPrice;
import plfx.mp.tcclient.tc.service.TcClientService;
import plfx.mp.tcclient.tc.service.impl.TcClientServiceImpl;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class TCSupplierPolicySnapshotLocalService extends BaseServiceImpl<TCSupplierPolicySnapshot, TCSupplierPolicySnapshotQO, TCSupplierPolicySnapshotDAO> {

	@Autowired
	private TCSupplierPolicySnapshotDAO dao;
	
	@Autowired
	private ScenicSpotDAO scenicSpotDAO;
	@Autowired
	private TCPolicyNoticeDAO tcPolicyNoticeDAO;
	
	@Autowired
	private TcClientService tcClientService;
	
	@Autowired
	private TCSupplierPolicySnapshotManager tcSupplierPolicySnapshotManager;
	@Autowired
	private AddrProjectionCacheManager addrProjectionCacheManager;
	@Override
	protected TCSupplierPolicySnapshotDAO getDao() {
		return dao;
	}

	/**
	 * 根据供应商政策ID获取最新供应商政策快照
	 * 
	 * @param policyId
	 * @return
	 */
	public TCSupplierPolicySnapshot getLast(String policyId) {
		HgLogger.getInstance().info("zhurz", "根据供应商政策ID获取最新供应商政策快照：" + policyId);
		if (policyId == null)
			return null;
		TCSupplierPolicySnapshot snapshot = tcSupplierPolicySnapshotManager
				.getTcSupplierPolicySnapshot(policyId);
		if (snapshot == null) {
			TCSupplierPolicySnapshotQO qo = new TCSupplierPolicySnapshotQO();
			qo.setLastSnapshot(true);
			qo.setPolicyId(policyId);
			snapshot = getDao().queryUnique(qo);
			tcSupplierPolicySnapshotManager.putTcSupplierPolicySnapshot(snapshot);
		}
		return snapshot;
	}
	
	/**
	 * 根据景区ID获取供应商政策列表
	 * 
	 * @param scenicSpotId
	 * @return
	 */
	public List<TCSupplierPolicySnapshot> getListByScenicSpotId(String scenicSpotId) {
		HgLogger.getInstance().info("zhurz", "根据景区ID获取供应商政策列表："+scenicSpotId);
		ScenicSpot scenicSpot = scenicSpotDAO.get(scenicSpotId);
		
		String tcScenicId = scenicSpot.getTcScenicSpotInfo().getId();
		
		List<TCSupplierPolicySnapshot> tcSupplierPolicySnapshots = 
				tcSupplierPolicySnapshotManager.getTcSupplierPolicySnapshots(tcScenicId);
		
		// 缓存里有则返回
		if (tcSupplierPolicySnapshots.size() > 0) {
			HgLogger.getInstance().info("zhurz", "根据景区ID获取供应商政策列表：" + scenicSpotId + "返回缓存数据");
			return tcSupplierPolicySnapshots;
		}
		
		// 判断景区是否拥有价格政策
		if (tcSupplierPolicySnapshotManager.isTcScenicNoPolice(tcScenicId)) {
			HgLogger.getInstance().info("zhurz", "根据景区ID获取供应商政策列表：" + scenicSpotId + "该景区没有价格政策");
			return new ArrayList<TCSupplierPolicySnapshot>();
		}
		
		// 缓存里没有则查库
		TCSupplierPolicySnapshotQO qo=new TCSupplierPolicySnapshotQO();
		qo.setLastSnapshot(true);
		qo.setTcScenicSpotsId(scenicSpot.getTcScenicSpotInfo().getId());
		tcSupplierPolicySnapshots = getDao().queryList(qo);

		if (tcSupplierPolicySnapshots != null && tcSupplierPolicySnapshots.size() > 0) {
			TCSupplierPolicySnapshot tcSupplierPolicySnapshot = tcSupplierPolicySnapshots.get(0);
			String nowDateStr = DateUtil.formatDateTime(new Date(), "yyyy-MM-dd");
			String snapshotDateStr = DateUtil.formatDateTime(tcSupplierPolicySnapshot.getSnapshotDate(), "yyyy-MM-dd");
			// 如果快照日期不是同一天，则需要重新拉取接口
			if (StringUtils.equals(nowDateStr, snapshotDateStr)) {
				HgLogger.getInstance().info("zhurz", "根据景区ID获取供应商政策列表：快照日期不是同一天，重新拉取接口");
				if (tcSupplierPolicySnapshots == null || tcSupplierPolicySnapshots.size() == 0)
					tcSupplierPolicySnapshotManager.setNoPoliceTcScenicId(tcScenicId);
				tcSupplierPolicySnapshotManager.refresh(tcSupplierPolicySnapshots);
				return tcSupplierPolicySnapshots;
			}
		}
		
		// 库里不是当天则查询入库并放入缓存
		SceneryPriceDto dto = new SceneryPriceDto();
		dto.setSceneryIds(scenicSpot.getTcScenicSpotInfo().getId());
		Response<ResultSceneryPrice> response = tcClientService.getSceneryPrice(dto);
		ResultSceneryPrice result = response.getResult();
		
		// 检查快照MD5是否与本次相同，相同则直接返回
		String resultMd5 = MD5HashUtil.toMD5(JSON.toJSONString(result));
		if(tcSupplierPolicySnapshots!=null && tcSupplierPolicySnapshots.size()>0){
			TCSupplierPolicySnapshot tcSupplierPolicySnapshot = tcSupplierPolicySnapshots.get(0);
			if(StringUtils.equals(resultMd5, tcSupplierPolicySnapshot.getResultMd5())){
				HgLogger.getInstance().info("zhurz", "根据景区ID获取供应商政策列表：数据库查询结果与接口调用结果MD5相同。");
				if (tcSupplierPolicySnapshots == null || tcSupplierPolicySnapshots.size() == 0)
					tcSupplierPolicySnapshotManager.setNoPoliceTcScenicId(tcScenicId);
				tcSupplierPolicySnapshotManager.refresh(tcSupplierPolicySnapshots);
				return tcSupplierPolicySnapshots;
			}
		}
		
		List<PriceQueryScenery> priceQuerySceneries = result.getScenery();
		if (priceQuerySceneries != null && priceQuerySceneries.size()>0) {
			// 政策须知
			TCPolicyNotice tcPolicyNotice = tcPolicyNoticeDAO.get(tcScenicId);
			if (tcPolicyNotice == null) {
				tcPolicyNotice = new TCPolicyNotice();
				tcPolicyNotice.setId(tcScenicId);
				tcPolicyNoticeDAO.save(tcPolicyNotice);
			}
			PriceQueryScenery priceQueryScenery = priceQuerySceneries.get(0);
			List<Notice> notices = priceQueryScenery.getNotice();
			List<NoticeType> noticeTypes = new ArrayList<NoticeType>();
			tcPolicyNotice.setNoticeTypes(noticeTypes);
			for(Notice notice:notices){
				List<NoticeItem> noticeItems = new ArrayList<NoticeItem>();
				NoticeType noticeType = new NoticeType();
				noticeType.setTypeName(notice.getNTypeName());
				noticeType.setSort(notice.getNType());
				noticeType.setNoticeItems(noticeItems);
				for(Info info:notice.getNInfo()){
					NoticeItem noticeItem = new NoticeItem();
					noticeItem.setSort(info.getNId());
					noticeItem.setTypeId(String.valueOf(info.getNId()));
					noticeItem.setName(info.getNName());
					noticeItem.setContent(info.getNContent());
					noticeItems.add(noticeItem);
				}
				noticeTypes.add(noticeType);
			}
			tcPolicyNoticeDAO.update(tcPolicyNotice);
			
			// 政策列表
			List<TCSupplierPolicySnapshot> tcSupplierPolicySnapshots2 = new ArrayList<TCSupplierPolicySnapshot>();
			List<Policy> policies = priceQueryScenery.getPolicy();
			Date now = new Date();
			for (Policy policy : policies) {
				TCSupplierPolicySnapshot tcSupplierPolicySnapshot = new TCSupplierPolicySnapshot();
				ScenicSpotSnapshot scenicSpotSnapshot = tcSupplierPolicySnapshot.getScenicSpotSnapshot();
				tcSupplierPolicySnapshot.setId(UUIDGenerator.getUUID());
				tcSupplierPolicySnapshot.setPolicyId(policy.getPolicyId().toString());
				tcSupplierPolicySnapshot.setLastSnapshot(true);
				tcSupplierPolicySnapshot.setName(policy.getPolicyName());
				tcSupplierPolicySnapshot.setTicketId(String.valueOf(policy.getTicketId()));
				tcSupplierPolicySnapshot.setTicketName(policy.getTicketName());
				tcSupplierPolicySnapshot.setRemark(policy.getRemark());
				tcSupplierPolicySnapshot.setRetailPrice(policy.getPrice());
				tcSupplierPolicySnapshot.setTcPrice(policy.getTcPrice());
				tcSupplierPolicySnapshot.setpMode(policy.getPMode());
				tcSupplierPolicySnapshot.setTicketDelivery(policy.getGMode());
				tcSupplierPolicySnapshot.setMinTicket(policy.getMinT());
				tcSupplierPolicySnapshot.setMaxTicket(policy.getMaxT());
				tcSupplierPolicySnapshot.setDpPrize(Integer.valueOf(policy.getDpPrize()));
				tcSupplierPolicySnapshot.setRealNameNeed(policy.getRealName().intValue()==1?true:false);
				tcSupplierPolicySnapshot.setRseIdCardNeed(policy.getUseCard().intValue()==1?true:false);
				tcSupplierPolicySnapshot.setValidityBeginDate(DateUtil.dateStr2BeginDate(policy.getBDate()));
				tcSupplierPolicySnapshot.setValidityEndDate(DateUtil.dateStr2EndDate(policy.getEDate()));
				tcSupplierPolicySnapshot.setValidityType(policy.getOpenDateType());
				tcSupplierPolicySnapshot.setInvalidDate(policy.getCloseDate());
				tcSupplierPolicySnapshot.setNotice(tcPolicyNotice);
				tcSupplierPolicySnapshot.setSnapshotDate(now);
				tcSupplierPolicySnapshot.setResultMd5(resultMd5);
				scenicSpotSnapshot.setScenicSpotsId(scenicSpotId);
				scenicSpotSnapshot.setTcScenicSpotsId(scenicSpot.getTcScenicSpotInfo().getId());
				scenicSpotSnapshot.setTcScenicSpotsName(scenicSpot.getScenicSpotBaseInfo().getName());
				scenicSpotSnapshot.setProvinceCode(scenicSpot.getScenicSpotGeographyInfo().getProvinceCode());
				scenicSpotSnapshot.setCityCode(scenicSpot.getScenicSpotGeographyInfo().getCityCode());
				scenicSpotSnapshot.setAreaCode(scenicSpot.getScenicSpotGeographyInfo().getAreaCode());
				Province p = addrProjectionCacheManager.getProvince(scenicSpotSnapshot.getProvinceCode());
				City c = addrProjectionCacheManager.getCity(scenicSpotSnapshot.getCityCode());
				Area a = addrProjectionCacheManager.getArea(scenicSpotSnapshot.getAreaCode());
				scenicSpotSnapshot.setProvinceName(p == null ? "" : p.getName());
				scenicSpotSnapshot.setCityName(c == null ? "" : c.getName());
				scenicSpotSnapshot.setAreaName(a == null ? "" : a.getName());
				tcSupplierPolicySnapshots2.add(tcSupplierPolicySnapshot);
			}
			
			// 保存入库
			if (tcSupplierPolicySnapshots == null)
				tcSupplierPolicySnapshots = new ArrayList<TCSupplierPolicySnapshot>();
			for (TCSupplierPolicySnapshot tcSupplierPolicySnapshot : tcSupplierPolicySnapshots) {
				tcSupplierPolicySnapshot.setLastSnapshot(false);
				getDao().update(tcSupplierPolicySnapshot);
			}
			for (TCSupplierPolicySnapshot tcSupplierPolicySnapshot : tcSupplierPolicySnapshots2) {
				tcSupplierPolicySnapshot.setLastSnapshot(true);
				getDao().save(tcSupplierPolicySnapshot);
			}
			if (tcSupplierPolicySnapshots == null || tcSupplierPolicySnapshots.size() == 0)
				tcSupplierPolicySnapshotManager.setNoPoliceTcScenicId(tcScenicId);
			tcSupplierPolicySnapshotManager.refresh(tcSupplierPolicySnapshots2);
			return tcSupplierPolicySnapshots2;
		}
		return new ArrayList<TCSupplierPolicySnapshot>();
	}
	
	public static void main(String[] args) {
		TcClientService tcClientService = new  TcClientServiceImpl();
		SceneryPriceDto dto = new SceneryPriceDto();
		dto.setSceneryIds("47311");
		Response<ResultSceneryPrice> response = tcClientService.getSceneryPrice(dto);
		//System.out.println(JSON.toJSONString(response , true));
	}
}
