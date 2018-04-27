package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.model.staff.Staff;
import hg.system.qo.AuthStaffQo;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
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
public class AuthStaffDao extends BaseDao<Staff, AuthStaffQo> {

	
	/**
	 * 查询接口实现
	 */
	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthStaffQo qo) {
		Criteria auserCri = criteria.createCriteria("authUser", JoinType.INNER_JOIN);
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getLoginName())) {
				if (qo.getIsLoginNameLike()) {
					auserCri.add(Restrictions.like("loginName", qo.getLoginName(), MatchMode.ANYWHERE));
				} else {
					auserCri.add(Restrictions.eq("loginName", qo.getLoginName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getRealName())) {
				if (qo.getIsRealNameLike()) {
					criteria.add(Restrictions.like("info.realName", qo.getRealName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("info.realName", qo.getRealName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getMobile())) {
				criteria.add(Restrictions.eq("info.mobile", qo.getMobile()));
			}
			if (qo.getCreateDateBegin() != null) {
				criteria.add(Restrictions.ge("createDate", qo.getCreateDateBegin()));
			}
			if (qo.getCreateDateEnd() != null) {
				criteria.add(Restrictions.le("createDate", qo.getCreateDateEnd()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Staff> getEntityClass() {
		return Staff.class;
	}
	
	@Override
	public Staff queryUnique(AuthStaffQo qo) {
		List<Staff> list = super.queryList(qo);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}


}
