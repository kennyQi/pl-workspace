package lxs.app.dao.app;

import hg.common.component.BaseDao;
import lxs.domain.model.app.FunctionIntroduction;
import lxs.pojo.qo.app.FunctionIntroductionQO;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;
@Repository
public class FunctionIntroductionDao extends BaseDao< FunctionIntroduction,  FunctionIntroductionQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria,
			FunctionIntroductionQO qo) {
		// TODO Auto-generated method stub
		return criteria;
	}

	@Override
	protected Class<FunctionIntroduction> getEntityClass() {
		// TODO Auto-generated method stub
		return FunctionIntroduction.class;
	}

}
