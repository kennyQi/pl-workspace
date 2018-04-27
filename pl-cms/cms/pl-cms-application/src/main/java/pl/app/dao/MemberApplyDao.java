package pl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import pl.cms.domain.entity.apply.MemberApply;
import pl.cms.pojo.qo.MemberApplyQO;


@Repository
public class MemberApplyDao extends BaseDao<MemberApply, MemberApplyQO> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, MemberApplyQO qo) {
		
		if (qo != null) {
			criteria.addOrder(Order.desc("createDate"));
			if (StringUtils.isNotBlank(qo.getScenicName())) {
				if (qo.isScenicNameLike()) {
					criteria.add(Restrictions.like("scenicName", qo.getScenicName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("scenicName", qo.getScenicName()));
				}
			}
			
			if (StringUtils.isNotBlank(qo.getLinkMan())) {
				if (qo.isLinkManLike()) {
					criteria.add(Restrictions.like("linkMan", qo.getLinkMan(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("linkMan", qo.getLinkMan()));
				}
			}
			
			if (StringUtils.isNotBlank(qo.getMobile())) {
				if (qo.isMobileLike()) {
					criteria.add(Restrictions.like("mobile", qo.getMobile(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("mobile", qo.getMobile()));
				}
			}
		}
		return criteria;
	}

	@Override
	protected Class<MemberApply> getEntityClass() {
		return MemberApply.class;
	}

}
