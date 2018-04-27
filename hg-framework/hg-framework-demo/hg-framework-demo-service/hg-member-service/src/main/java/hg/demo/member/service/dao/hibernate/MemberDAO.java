package hg.demo.member.service.dao.hibernate;

import hg.demo.member.service.qo.hibernate.MemberQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.demo.member.common.domain.model.Member;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhurz
 */
@Repository("memberDAO")
public class MemberDAO extends BaseHibernateDAO<Member, MemberQO> {

	@Override
	protected Class<Member> getEntityClass() {
		return Member.class;
	}

	@Override
	protected void queryEntityComplete(MemberQO qo, List<Member> list) {
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, MemberQO qo) {
		return criteria;
	}
}
