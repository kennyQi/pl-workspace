package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.auth.AuthPerm;
import hg.system.qo.AuthPermQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * 
 * @类功能说明：资源_dao
 * @类修改者：zzb
 * @修改日期：2014年10月31日上午11:30:29
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月31日上午11:30:29
 *
 */
@Repository
public class AuthPermDao extends BaseDao<AuthPerm, AuthPermQo> {

	
	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthPermQo qo) {
		if (qo != null) {
			// 资源类型
			if (StringUtils.isNotBlank(qo.getPermType())) {
				criteria.add(Restrictions.eq("permType", qo.getPermType()));
			}
			// 资源链接
			if (StringUtils.isNotBlank(qo.getUrlLike())) {
				criteria.add(Restrictions.like("url", qo.getUrlLike(), MatchMode.ANYWHERE));
			}
			// 上级资源
			if (StringUtils.isNotBlank(qo.getParentId())) {
				criteria.add(Restrictions.eq("parentId", qo.getParentId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<AuthPerm> getEntityClass() {
		return AuthPerm.class;
	}
	
	@Override
	public AuthPerm queryUnique(AuthPermQo qo) {
		List<AuthPerm> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
