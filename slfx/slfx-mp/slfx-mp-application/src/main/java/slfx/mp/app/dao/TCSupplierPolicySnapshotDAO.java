package slfx.mp.app.dao;

import hg.common.component.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import slfx.mp.app.pojo.qo.TCSupplierPolicySnapshotQO;
import slfx.mp.domain.model.supplierpolicy.TCSupplierPolicySnapshot;

@Repository
public class TCSupplierPolicySnapshotDAO extends BaseDao<TCSupplierPolicySnapshot, TCSupplierPolicySnapshotQO> {

	@Autowired
	private ScenicSpotDAO scenicSpotDAO;

	@Override
	protected Criteria buildCriteria(Criteria criteria, TCSupplierPolicySnapshotQO qo) {
		// 购票须知、政策须知
		criteria.setFetchMode("notice", FetchMode.JOIN);
		if (qo != null) {
			// 平台景区id
			if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
				criteria.add(Restrictions.eq("scenicSpotSnapshot.scenicSpotsId", qo.getScenicSpotId()));
			}
			// 政策id
			if (StringUtils.isNotBlank(qo.getPolicyId())) {
				criteria.add(Restrictions.eq("policyId", qo.getPolicyId()));
			}
			// 是否最新快照
			if (qo.getLastSnapshot() != null) {
				criteria.add(Restrictions.eq("lastSnapshot", qo.getLastSnapshot()));
			}
			// 景点ID
			if(StringUtils.isNotBlank(qo.getTcScenicSpotsId())){
				criteria.add(Restrictions.eq("scenicSpotSnapshot.tcScenicSpotsId", qo.getTcScenicSpotsId()));
			}
			// 景点名称
			if (StringUtils.isNotBlank(qo.getTcScenicSpotsName())) {
				if (qo.isTcScenicSpotsNameLike()) {
					criteria.add(Restrictions.like("scenicSpotSnapshot.tcScenicSpotsName", qo.getTcScenicSpotsName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("scenicSpotSnapshot.tcScenicSpotsName", qo.getTcScenicSpotsName()));
				}
			}
			// 所在省代码
			if (StringUtils.isNotBlank(qo.getProvinceCode())) {
				criteria.add(Restrictions.eq("scenicSpotSnapshot.provinceCode", qo.getProvinceCode()));
			}
			// 所在城市代码
			if (StringUtils.isNotBlank(qo.getCityCode())) {
				criteria.add(Restrictions.eq("scenicSpotSnapshot.cityCode", qo.getCityCode()));
			}
			// 所在区域代码
			if (StringUtils.isNotBlank(qo.getAreaCode())) {
				criteria.add(Restrictions.eq("scenicSpotSnapshot.areaCode", qo.getAreaCode()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<TCSupplierPolicySnapshot> getEntityClass() {
		return TCSupplierPolicySnapshot.class;
	}

}
