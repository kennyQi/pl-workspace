package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.auth.AuthRole;
import hg.system.qo.AuthRoleQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：角色_dao
 * @类修改者：zzb
 * @修改日期：2014年10月31日上午11:30:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月31日上午11:30:29
 */
@Repository
public class AuthRoleDao extends BaseDao<AuthRole, AuthRoleQo> {

	
	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthRoleQo qo) {
		if (qo != null) {
			// 角色名称
			if (StringUtils.isNotBlank(qo.getRoleName())) {
				if (qo.isRoleNameLike())
					criteria.add(Restrictions.like("roleName", qo.getRoleName(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("roleName", qo.getRoleName()));
			}
			// 显示名
			if (StringUtils.isNotBlank(qo.getDisplayName())) {
				if (qo.isDisplayNameLike())
					criteria.add(Restrictions.like("displayName", qo.getDisplayName(), MatchMode.ANYWHERE));
				else
					criteria.add(Restrictions.eq("displayName", qo.getDisplayName()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<AuthRole> getEntityClass() {
		return AuthRole.class;
	}
	
	@Override
	public AuthRole queryUnique(AuthRoleQo qo) {
		List<AuthRole> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
