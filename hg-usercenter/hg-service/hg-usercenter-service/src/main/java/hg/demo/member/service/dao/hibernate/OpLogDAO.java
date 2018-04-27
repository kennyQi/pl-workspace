package hg.demo.member.service.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.demo.member.common.domain.model.OpLog;
import hg.demo.member.service.qo.hibernate.OpLogQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;

/**
 * 
* <p>Title: UserBaseInfoDAO</p>
* <p>Description: </p>
* <p>Company: </p> 
* @author xuwangwei
* @date 2016年6月27日 下午2:14:21
 */
@Repository("opLogDAO")
public class OpLogDAO extends BaseHibernateDAO<OpLog, OpLogQO> {

	@Override
	protected Class<OpLog> getEntityClass() {
		return OpLog.class;
	}

	@Override
	protected void queryEntityComplete(OpLogQO qo, List<OpLog> list) {

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, OpLogQO qo) {
		return criteria;
	}
}
