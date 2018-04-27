package slfx.mp.app.dao;

import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotEnum.FILTER_TYPE_AREA;
import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotEnum.FILTER_TYPE_PRICE;
import static slfx.mp.spi.common.MpEnumConstants.SalePolicySnapshotEnum.FILTER_TYPE_SCENIC_SPOT;
import hg.common.component.BaseDao;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import slfx.mp.app.pojo.qo.SalePolicySnapshotLocalQO;
import slfx.mp.app.pojo.qo.SalePolicySnapshotQO;
import slfx.mp.domain.model.platformpolicy.SalePolicySnapshot;

@Repository("salePolicySnapshotDAO_mp")
public class SalePolicySnapshotDAO extends BaseDao<SalePolicySnapshot, SalePolicySnapshotQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SalePolicySnapshotQO qo) {
		if (qo != null) {
			// 是否按快照日期从大到小
			if (qo.getSnapshotDateDesc() != null) {
				criteria.addOrder(qo.getSnapshotDateDesc() ? Order
						.desc("snapshotDate") : Order.asc("snapshotDate"));
			}
			// 政策编号
			if (StringUtils.isNotBlank(qo.getPolicyId())) {
				criteria.add(Restrictions.eq("policyId", qo.getPolicyId()));
			}
			// 政策生效时间
			if (qo.getBeginDate() != null) {
				criteria.add(Restrictions.or(Restrictions.ge("beginDate",
						qo.getBeginDate()), Restrictions.and(
						Restrictions.le("beginDate", qo.getBeginDate()),
						Restrictions.gt("endDate", qo.getBeginDate()))));
			}
			// 政策结束时间
			if (qo.getEndDate() != null) {
				criteria.add(Restrictions.or(Restrictions.le("endDate",
						qo.getEndDate()), Restrictions.and(
						Restrictions.ge("endDate", qo.getEndDate()),
						Restrictions.le("beginDate", qo.getEndDate()))));
			}
			// 状态
			if (qo.getState() != null) {
				criteria.add(Restrictions.eq("status.status", qo.getState()));
			}
			// 创建人
			if (StringUtils.isNotBlank(qo.getCreateUserName())) {
				if (qo.getCreateUserNameLike()) {
					criteria.add(Restrictions.like("operatorName", qo.getCreateUserName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("operatorName", qo.getCreateUserName()));
				}
			}
			// 适用范围 经销商ID
			if (StringUtils.isNotBlank(qo.getDealerId())) {
				criteria.add(Restrictions.eq("dealerId", qo.getDealerId()));
			}

			// 景区名称
			if (StringUtils.isNotBlank(qo.getScenicSpotName())) {
				if (qo.getScenicSpotNameLike()) {
					criteria.add(Restrictions.like("scenicSpotNames", qo.getScenicSpotName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.like("scenicSpotNames",
							"," + qo.getScenicSpotName() + ",", MatchMode.ANYWHERE));
				}
			}
			// 景区所在省
			if (StringUtils.isNotBlank(qo.getScenicSpotProviceCode())) {
				criteria.add(Restrictions.eq("filterProvCode", qo.getScenicSpotProviceCode()));
			}
			// 景区所在市
			if (StringUtils.isNotBlank(qo.getScenicSpotCityCode())) {
				criteria.add(Restrictions.eq("filterCityCode", qo.getScenicSpotCityCode()));
			}
			// 景区所在区
			if (StringUtils.isNotBlank(qo.getScenicSpotAreaCode())) {
				criteria.add(Restrictions.eq("filterAreaCode", qo.getScenicSpotAreaCode()));
			}
			// 是否最新快照
			if (qo.getLastSnapshot() != null) {
				criteria.add(Restrictions.eq("lastSnapshot", qo.getLastSnapshot()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<SalePolicySnapshot> getEntityClass() {
		return SalePolicySnapshot.class;
	}
	
	/**
	 * 根据qo查询覆盖到供应商政策的平台政策列表
	 * 
	 * @param qo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SalePolicySnapshot> querySalePolicySnapshots(SalePolicySnapshotLocalQO qo) {
		Criteria criteria = getSession().createCriteria(SalePolicySnapshot.class);
		if (qo != null) {
			// 按优先级 DESC 快照日期 DESC
			criteria.addOrder(Order.desc("level"));
			criteria.addOrder(Order.desc("snapshotDate"));
			// --------------- 供应商经销商 ---------------
			// 经销商政策id
			if (StringUtils.isNotBlank(qo.getPolicyId())) {
				criteria.add(Restrictions.eqOrIsNull("policyId", qo.getPolicyId()));
			}
			// 供应商id
			if (StringUtils.isNotBlank(qo.getSupplierId())) {
				criteria.add(Restrictions.eqOrIsNull("supplierId", qo.getSupplierId()));
			}
			// --------------- 按省市区 ---------------
			List<Criterion> areaFilters = new ArrayList<Criterion>();
			areaFilters.add(Restrictions.eq("filterType", FILTER_TYPE_AREA));
			if (StringUtils.isNotBlank(qo.getFilterProvCode())) {
				areaFilters.add(Restrictions.eq("filterProvCode", qo.getFilterProvCode()));
			}
			if (StringUtils.isNotBlank(qo.getFilterCityCode())) {
				areaFilters.add(Restrictions.eqOrIsNull("filterCityCode", qo.getFilterCityCode()));
			}
			if (StringUtils.isNotBlank(qo.getFilterAreaCode())) {
				areaFilters.add(Restrictions.eqOrIsNull("filterAreaCode", qo.getFilterAreaCode()));
			}
			// --------------- 按价格 ---------------
			List<Criterion> priceFilters = new ArrayList<Criterion>();
			priceFilters.add(Restrictions.eq("filterType", FILTER_TYPE_PRICE));
			if (qo.getFilterPriceMin() != null) {
				Criterion c1 = Restrictions.ge("filterPriceMin", qo.getFilterPriceMin());
				Criterion c21 = Restrictions.lt("filterPriceMin", qo.getFilterPriceMin());
				Criterion c22 = Restrictions.ge("filterPriceMax", qo.getFilterPriceMin());
				priceFilters.add(Restrictions.or(c1, Restrictions.and(c21, c22)));
			}
			if (qo.getFilterPriceMax() != null) {
				Criterion c1 = Restrictions.le("filterPriceMax", qo.getFilterPriceMax());
				Criterion c21 = Restrictions.gt("filterPriceMax", qo.getFilterPriceMax());
				Criterion c22 = Restrictions.le("filterPriceMin", qo.getFilterPriceMax());
				priceFilters.add(Restrictions.or(c1, Restrictions.and(c21, c22)));
			}
			// --------------- 按景点 ---------------
			List<Criterion> scenicFilters = new ArrayList<Criterion>();
			areaFilters.add(Restrictions.eq("filterType", FILTER_TYPE_SCENIC_SPOT));
			if (StringUtils.isNotBlank(qo.getScenicSpotId())) {
				scenicFilters.add(Restrictions.like("scenicSpotIds", "," + qo.getScenicSpotId() + ",", MatchMode.ANYWHERE));
			}
			// --------------- 筛选时间 ---------------
			if (qo.getBeginDate() != null) {
				Criterion c1 = Restrictions.ge("beginDate", qo.getBeginDate());
				Criterion c21 = Restrictions.lt("beginDate", qo.getBeginDate());
				Criterion c22 = Restrictions.ge("endDate", qo.getBeginDate());
				criteria.add(Restrictions.or(c1, Restrictions.and(c21, c22)));
			}
			if (qo.getEndDate() != null) {
				Criterion c1 = Restrictions.le("endDate", qo.getEndDate());
				Criterion c21 = Restrictions.gt("endDate", qo.getEndDate());
				Criterion c22 = Restrictions.le("beginDate", qo.getEndDate());
				criteria.add(Restrictions.or(c1, Restrictions.and(c21, c22)));
			}
			List<Criterion> filterList = new ArrayList<Criterion>();
			if (areaFilters.size() >= 2) {
				Criterion[] areaFiltersArray = new Criterion[areaFilters.size()];
				areaFilters.toArray(areaFiltersArray);
				filterList.add(Restrictions.and(areaFiltersArray));
			}
			if (priceFilters.size() >= 2) {
				Criterion[] priceFiltersArray = new Criterion[priceFilters.size()];
				priceFilters.toArray(priceFiltersArray);
				filterList.add(Restrictions.and(priceFiltersArray));
			}
			if (scenicFilters.size() >= 2) {
				Criterion[] scenicFiltersArray = new Criterion[scenicFilters.size()];
				scenicFilters.toArray(scenicFiltersArray);
				filterList.add(Restrictions.and(scenicFiltersArray));
			}
			// 3个过滤条件OR查询
			if (filterList.size() > 0) {
				Criterion[] filtersArray = new Criterion[filterList.size()];
				filterList.toArray(filtersArray);
				criteria.add(Restrictions.or(filtersArray));
			}
		}
		return criteria.list();
	}

}
