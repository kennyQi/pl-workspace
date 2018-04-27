package hg.dzpw.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.MyBeanUtils;
import hg.dzpw.app.pojo.qo.DealerQo;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.domain.model.dealer.Dealer;
import hg.dzpw.domain.model.dealer.DealerScenicspotSetting;
import hg.system.dao.AreaDao;
import hg.system.dao.CityDao;
import hg.system.dao.ProvinceDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DealerDao extends BaseDao<Dealer, DealerQo> {

	@Autowired
	private DealerScenicspotSettingDao dealerScenicspotSettingDao;
	@Autowired
	private ProvinceDao provinceDao;
	@Autowired
	private CityDao cityDao;
	@Autowired
	private AreaDao areaDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, DealerQo qo) {

		if (qo != null) {
			//经销商id
			if(qo.getId()!=null)
			{
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			// 经销商名称
			if (qo.getName() != null) {
				if (qo.getNameLike()) {
					criteria.add(Restrictions.like("baseInfo.name", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("baseInfo.name", qo.getName()));
				}
			}
			
			// 创建时间
			if (qo.getCreateDateBegin() != null) {
				criteria.add(Restrictions.ge("baseInfo.createDate", qo.getCreateDateBegin()));
			}
			if (qo.getCreateDateEnd() != null) {
				criteria.add(Restrictions.le("baseInfo.createDate", qo.getCreateDateEnd()));
			}

			// 状态
			if (qo.getStatus() != null) {
				criteria.add(Restrictions.eq("baseInfo.status", qo.getStatus()));
			}

			// 是否被逻辑删除
			if (qo.getRemoved() != null) {
				criteria.add(Restrictions.eq("baseInfo.removed", qo.getRemoved()));
			}
			//登录帐号
			if ( StringUtils.isNotBlank(qo.getAdminLoginName())) {
				if (qo.getAdminLoginNameLike()!=null && qo.getAdminLoginNameLike()) {
					criteria.add(Restrictions.like("clientInfo.adminLoginName", qo.getAdminLoginName(),MatchMode.ANYWHERE));
				}else {
					criteria.add(Restrictions.eq("clientInfo.adminLoginName", qo.getAdminLoginName()));
				}
				
			}
			// 联系人
			if (qo.getLinkManLike()) {
				criteria.add(Restrictions.like("baseInfo.linkMan", qo.getLinkMan(), MatchMode.ANYWHERE));
			}

			// 联系电话
			if (qo.getTelephone() != null) {
				criteria.add(Restrictions.eq("baseInfo.telephone", qo.getTelephone()));
			}

			// 联系 邮箱
			if (qo.getEmail() != null) {
				criteria.add(Restrictions.eq("baseInfo.email", qo.getEmail()));
			}

			// 地址
			
			if (qo.getProvinceQo() != null) {
				provinceDao.buildCriteriaOut(criteria.createCriteria(
						"baseInfo.province", qo.getProvinceQo().getAlias(),
						JoinType.LEFT_OUTER_JOIN), qo.getProvinceQo());
			}
			if (qo.getCityQo() != null) {
				cityDao.buildCriteriaOut(criteria.createCriteria("baseInfo.city", qo
						.getCityQo().getAlias(), JoinType.LEFT_OUTER_JOIN), qo
						.getCityQo());
			}
			if (qo.getAreaQo() != null) {
				areaDao.buildCriteriaOut(criteria.createCriteria("baseInfo.area", qo
						.getAreaQo().getAlias(), JoinType.LEFT_OUTER_JOIN), qo
						.getAreaQo());
			}

			if (qo.getKey() != null) {
				criteria.add(Restrictions.eq("clientInfo.key", qo.getKey()));
			}
			
			if(qo.isOrderDesc())
			{
			criteria.addOrder(Order.desc("baseInfo.createDate"));
			}

			// 经销商未被删除
			criteria.add(Restrictions.ne("baseInfo.status", Dealer.DEALER_STATUS_REMOVE));

			if (qo.getScenicspotSettingQo() != null
					&& StringUtils.isNotBlank(qo.getScenicspotSettingQo().getScenicSpotId())) {

				DetachedCriteria settingDetachedCriteria = DetachedCriteria.forClass(DealerScenicspotSetting.class, "setting");
				Criteria settingCriteria = (Criteria) MyBeanUtils.getFieldValue(settingDetachedCriteria, "criteria");
				dealerScenicspotSettingDao.buildCriteriaOut(settingCriteria, qo.getScenicspotSettingQo());
				settingDetachedCriteria.add(Property.forName("setting.dealer.id").eqProperty(criteria.getAlias() + ".id"));
				settingDetachedCriteria.setProjection(Projections.property("setting.id"));
				Criterion criterion1 = Subqueries.exists(settingDetachedCriteria);

				if (qo.getScenicspotSettingQo().onlyUseableTrue()) {
					DetachedCriteria settingDetachedCriteria2 = DetachedCriteria.forClass(DealerScenicspotSetting.class, "setting2");
					settingDetachedCriteria2.add(Property.forName("setting2.dealer.id").eqProperty(criteria.getAlias() + ".id"));
					settingDetachedCriteria2.add(Restrictions.eq("scenicSpot.id", qo.getScenicspotSettingQo().getScenicSpotId()));
					settingDetachedCriteria2.setProjection(Projections.property("setting2.id"));
					Criterion criterion2 = Subqueries.notExists(settingDetachedCriteria2);
					criteria.add(Restrictions.or(criterion1, criterion2));
				} else if (qo.getScenicspotSettingQo().needQuery()) {
					criteria.add(criterion1);
				}

			}
		}

		return criteria;
	}

	@Override
	protected Class<Dealer> getEntityClass() {
		return Dealer.class;
	}

	private void querySettings(DealerQo qo, List<Dealer> list) {
		if (qo.getScenicspotSettingQo() != null
				&& StringUtils.isNotBlank(qo.getScenicspotSettingQo().getScenicSpotId())) {

			List<String> dealerIds = new ArrayList<String>();

			for (Dealer dealer : list)
				dealerIds.add(dealer.getId());

			if (dealerIds.size() > 0) {
				DealerScenicspotSettingQo settingQo = qo.getScenicspotSettingQo();
				DealerQo dealerQo = new DealerQo();
				dealerQo.setIds(dealerIds);
				settingQo.setDealerQo(dealerQo);
				List<DealerScenicspotSetting> settings = dealerScenicspotSettingDao.queryList(settingQo);
				Map<String, DealerScenicspotSetting> settingMap = new HashMap<String, DealerScenicspotSetting>();

				for (DealerScenicspotSetting setting : settings)
					settingMap.put(setting.getDealer().getId(), setting);

				for (Dealer dealer : list)
					dealer.setSetting(settingMap.get(dealer.getId()));

			}

		}
	}

	@Override
	public List<Dealer> queryList(DealerQo qo, Integer offset, Integer maxSize) {
		List<Dealer> list = super.queryList(qo, offset, maxSize);
		querySettings(qo, list);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		if (resultPagination.getCondition() instanceof DealerQo)
			querySettings((DealerQo) resultPagination.getCondition(), (List<Dealer>) resultPagination.getList());
		return resultPagination;
	}

}
