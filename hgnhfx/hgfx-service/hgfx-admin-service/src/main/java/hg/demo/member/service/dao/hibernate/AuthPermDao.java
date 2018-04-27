/**
 * @ISecurityDao.java Create on 2016-5-23下午2:24:31
 * Copyright (c) 2012 by www.hg365.com。
 */
package hg.demo.member.service.dao.hibernate;


import hg.demo.member.common.domain.model.AuthPerm;
import hg.demo.member.service.qo.hibernate.AuthPermQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2016-5-23下午2:24:31
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：<a href=gaoce@hgtech365.com>gaoce</a>
 * @创建时间：2016-5-23下午2:24:31
 * @version：
 */
@Repository("authPermDao")
public class AuthPermDao extends BaseHibernateDAO<AuthPerm, AuthPermQO>{

	@Override
	protected Class<AuthPerm> getEntityClass() {
		return AuthPerm.class;
	}

	@Override
	protected void queryEntityComplete(AuthPermQO qo, List<AuthPerm> list) {
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, AuthPermQO qo) {
		return criteria;
	}
		
}
