package hg.demo.member.service.dao.hibernate;

import hg.demo.member.common.domain.model.AuthUser;
import hg.demo.member.service.qo.hibernate.AuthUserQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by admin on 2016/5/20.
 */
@Repository("authUserDAO")
public class AuthUserDAO extends BaseHibernateDAO<AuthUser, AuthUserQO> {
    @Override
    protected Class<AuthUser> getEntityClass() {
        return AuthUser.class;
    }

    @Override
    protected void queryEntityComplete(AuthUserQO qo, List<AuthUser> list) {
        if (list!=null&&list.size()>0){
            for (AuthUser authUser:list){
                Hibernate.initialize(authUser.getAuthRoleSet());
            }
        }
    }

    @Override
    protected Criteria buildCriteria(Criteria criteria, AuthUserQO qo) {
        return criteria;
    }
}
