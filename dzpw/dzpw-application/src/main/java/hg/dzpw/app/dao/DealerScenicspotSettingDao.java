package hg.dzpw.app.dao;

import java.util.List;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.dzpw.app.pojo.qo.DealerScenicspotSettingQo;
import hg.dzpw.domain.model.dealer.DealerScenicspotSetting;
import hg.dzpw.domain.model.policy.TicketPolicy;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class DealerScenicspotSettingDao extends BaseDao<DealerScenicspotSetting, DealerScenicspotSettingQo> {

	@Autowired
	private DealerDao dealerDao;

	@Override
	protected Criteria buildCriteria(Criteria criteria, DealerScenicspotSettingQo qo) {
		if (qo == null)
			return criteria;
		// 所属景区
		if (StringUtils.isNotBlank(qo.getScenicSpotId()))
			criteria.add(Restrictions.eq("scenicSpot.id", qo.getScenicSpotId()));
		// 所属经销商
		if (qo.getDealerQo() != null) {
			Criteria dealerCriteria = criteria.createCriteria("dealer", qo
					.getDealerQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			dealerDao.buildCriteriaOut(dealerCriteria, qo.getDealerQo());
		}
		// 押金数额
		if (qo.getPledgeAmountMin() != null)
			criteria.add(Restrictions.ge("pledgeAmount", qo.getPledgeAmountMin()));
		
		if (qo.getPledgeAmountMax() != null)
			criteria.add(Restrictions.le("pledgeAmount", qo.getPledgeAmountMax()));

		// 是否可用
		if (qo.getUseable() != null)
			criteria.add(Restrictions.eq("useable", qo.getUseable()));

		return criteria;
	}

	@Override
	protected Class<DealerScenicspotSetting> getEntityClass() {
		return DealerScenicspotSetting.class;
	}
	
	
	@Override
	public List<DealerScenicspotSetting> queryList(DealerScenicspotSettingQo qo, Integer offset, Integer maxSize) {
		
		List<DealerScenicspotSetting> list = super.queryList(qo, offset, maxSize);
		if (qo!=null){
			for (DealerScenicspotSetting o : list) {
				if (qo.isScenicSpotAble())
					Hibernate.initialize(o.getScenicSpot());
			}
		}
		return list;
	}
	
	
	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination resultPagination = super.queryPagination(pagination);
		DealerScenicspotSettingQo qo = (DealerScenicspotSettingQo)pagination.getCondition();
		
		if (qo!=null){
			List<DealerScenicspotSetting> list = (List<DealerScenicspotSetting>)resultPagination.getList();
			for (DealerScenicspotSetting o : list){
				if (qo.isScenicSpotAble())
					Hibernate.initialize(o.getScenicSpot());
			}
		}
		
		return pagination;
	}
	
	@Override
	public DealerScenicspotSetting queryUnique(DealerScenicspotSettingQo qo) {
		DealerScenicspotSetting set =  super.queryUnique(qo);
		
		if (qo!=null){
			if (qo.isScenicSpotAble())
				Hibernate.initialize(set.getScenicSpot());
		}
		return set;
	}

}
