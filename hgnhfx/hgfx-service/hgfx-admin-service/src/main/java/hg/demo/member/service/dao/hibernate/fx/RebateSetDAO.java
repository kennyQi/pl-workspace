package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import hg.demo.member.service.qo.hibernate.fx.RebateSetQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.rebate.RebateSet;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository("rebateSetDAO")
public class RebateSetDAO extends BaseHibernateDAO<RebateSet, RebateSetQO> {

	@Override
	protected Class<RebateSet> getEntityClass() {
		// TODO Auto-generated method stub
		return RebateSet.class;
	}

	@Override
	protected void queryEntityComplete(RebateSetQO qo, List<RebateSet> list) {
		// TODO Auto-generated method stub

	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, RebateSetQO qo) {
		// TODO Auto-generated method stub
		if (qo.getQueryWay() != null) {
			// 当前月查询
			if (qo.getQueryWay().intValue() == RebateSetQO.QUERY_CURR_MONTH) {
				criteria.add(Restrictions.or(
						Restrictions.eq("invalidDate", qo.getInvalidDateEQ1()),
						Restrictions.eqOrIsNull("invalidDate", null)));
				// 次月查询
			} else if (qo.getQueryWay().intValue() == RebateSetQO.QUERY_NEXT_MONTH) {
				/*
				 * 查询条件
				 *A.生效时间=(次月 00:00:00)
				 *B.生效状态为Y
				 *isCheck=false
					或
				 *C.生效时间<=(本月 00:00:00 )
				 *D.生效状态为Y或null
				 *E.失效时间null
				 *isCheck=false
				 *或
				 *待审核
				 */
				criteria.add(Restrictions.or(Restrictions.and(
						Restrictions.eq("implementDate",
								qo.getImplementDateEQ1()),
						Restrictions.eq("isImplement", true)), Restrictions
						.and(Restrictions.le("implementDate",
								qo.getImplementDateEnd1()), Restrictions
								.eqOrIsNull("invalidDate", null), Restrictions
								.or(Restrictions.eqOrIsNull("isImplement", null),
										Restrictions.eq("isImplement", true))),
										Restrictions.eq("checkStatus", 0)));
				criteria.add(Restrictions.eqOrIsNull("isCheck", false));
			}
		}
		return criteria;
	}

}
