package plfx.mp.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.mp.app.pojo.qo.OrderUserInfoQO;
import plfx.mp.domain.model.order.OrderUserInfo;

@Repository
public class OrderUserInfoDAO extends BaseDao<OrderUserInfo, OrderUserInfoQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, OrderUserInfoQO qo) {
		if (qo != null) {
			// 渠道用户id
			if (StringUtils.isNotBlank(qo.getChannelUserId())) {
				criteria.add(Restrictions.eq("channelUserId", qo.getChannelUserId()));
			}
			// 姓名
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.isNameLike()) {
					criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("name", qo.getName()));
				}
			}
			// 手机
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
	protected Class<OrderUserInfo> getEntityClass() {
		return OrderUserInfo.class;
	}

}
