package lxs.app.dao.app;

import hg.common.component.BaseDao;
import lxs.domain.model.app.Introduction;
import lxs.pojo.qo.app.IntroductionQO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class IntroductionDAO extends BaseDao<Introduction, IntroductionQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, IntroductionQO qo) {

		if (qo != null&&qo.getIntroductionType()!=null) {
				criteria.add(Restrictions.eq("introductionType",qo.getIntroductionType()));
		}

		return criteria;
	}

	@Override
	protected Class<Introduction> getEntityClass() {
		// TODO Auto-generated method stub
		return Introduction.class;
	}

	

}
