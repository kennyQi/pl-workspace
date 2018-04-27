package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.auth.AuthUser;
import hg.system.qo.AuthUserQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：用户_dao
 * @类修改者：zzb
 * @修改日期：2014年11月5日下午2:52:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年11月5日下午2:52:11
 */
@Repository
public class AuthUserDao extends BaseDao<AuthUser, AuthUserQo> {

	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthUserQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getLoginName())) {
				if (qo.getLoginNameLike()) {
					criteria.add(Restrictions.like("loginName", qo.getLoginName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("loginName", qo.getLoginName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getDisplayName())) {
				if (qo.getDisplayNameLike()) {
					criteria.add(Restrictions.like("displayName", qo.getDisplayName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("displayName", qo.getDisplayName()));
				}
			}
		}
		return criteria;
	}

	@Override
	protected Class<AuthUser> getEntityClass() {
		return AuthUser.class;
	}
	
	@Override
	public AuthUser queryUnique(AuthUserQo qo) {
		List<AuthUser> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
