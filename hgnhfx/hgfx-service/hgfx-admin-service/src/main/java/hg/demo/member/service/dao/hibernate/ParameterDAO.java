package hg.demo.member.service.dao.hibernate;

import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.demo.member.common.domain.model.mall.Parameter;
import hg.demo.member.service.qo.hibernate.ParameterQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhurz
 */
@Repository("parameterDAO")
public class ParameterDAO extends BaseHibernateDAO<Parameter, ParameterQO> {

	@Override
	protected Class<Parameter> getEntityClass() {
		return Parameter.class;
	}

	@Override
	protected void queryEntityComplete(ParameterQO qo, List<Parameter> list) {

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ParameterQO qo) {
		return criteria;
	}
}
