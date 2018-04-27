package hg.demo.member.service.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.common.domain.model.Department;
import hg.demo.member.common.domain.model.UserBaseInfo;
import hg.demo.member.service.qo.hibernate.UserBaseInfoQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;

/**
 * 
* <p>Title: UserBaseInfoDAO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:14:21
 */
@Repository("userBaseInfoDAO")
public class UserBaseInfoDAO extends BaseHibernateDAO<UserBaseInfo, UserBaseInfoQO> {

	@Override
	protected Class<UserBaseInfo> getEntityClass() {
		return UserBaseInfo.class;
	}

	@Override
	protected void queryEntityComplete(UserBaseInfoQO qo, List<UserBaseInfo> list) {

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, UserBaseInfoQO qo) {
		return criteria;
	}
}
