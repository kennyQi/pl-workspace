package hsl.app.dao;

import hg.common.component.hibernate.HibernateSimpleDao;

import org.springframework.stereotype.Repository;

/**
 * @类功能说明：安全扩展DAO
 * @类修改者：
 * @修改日期：2014-12-2下午5:25:50
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-12-2下午5:25:50
 */
@Repository
public class SecurityExtDao extends HibernateSimpleDao {
	
	/**
	 * @方法功能说明：查询这个角色有几个用户在用
	 * @修改者名字：zhurz
	 * @修改时间：2014-12-2下午5:29:26
	 * @修改内容：
	 * @参数：@param roleId
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer roleUseCount(String roleId) {
		String hql = "select count(distinct au.id) from AuthUser au join au.authRoleSet ar where ar.id=?";
		Long count = (Long) getSession().createQuery(hql).setParameter(0, roleId).uniqueResult();
		return count.intValue();
	}
	
}
