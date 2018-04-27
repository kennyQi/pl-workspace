/**
 * @RuleDao.java Create on 2016-5-23下午2:29:03
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.common.domain.model.AuthRole;
import hg.demo.member.service.qo.hibernate.AuthRoleQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:29:03
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:29:03
 * @version：
 */
@Repository("authRoleDao")
public class AuthRoleDao extends BaseHibernateDAO<AuthRole, AuthRoleQO>{

	@Override
	protected Class<AuthRole> getEntityClass() {
		return AuthRole.class;
	}

	@Override
	protected void queryEntityComplete(AuthRoleQO qo, List<AuthRole> list) {
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthRoleQO qo) {
		return criteria;
	}

}
