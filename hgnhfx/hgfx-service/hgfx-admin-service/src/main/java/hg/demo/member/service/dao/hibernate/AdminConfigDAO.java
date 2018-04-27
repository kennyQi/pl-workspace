package hg.demo.member.service.dao.hibernate;

import hg.demo.member.common.domain.model.adminconfig.AdminConfig;
import hg.demo.member.service.qo.hibernate.AdminConfigQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

@Repository("adminConfigDAO")
public class AdminConfigDAO extends BaseHibernateDAO<AdminConfig, AdminConfigQO> {

@Override
protected Class<AdminConfig> getEntityClass() {
return AdminConfig.class;
}

@Override
protected void queryEntityComplete(AdminConfigQO qo, List<AdminConfig> list) {

}

@Override
protected Criteria buildCriteria(Criteria criteria, AdminConfigQO qo) {
return criteria;
}
}