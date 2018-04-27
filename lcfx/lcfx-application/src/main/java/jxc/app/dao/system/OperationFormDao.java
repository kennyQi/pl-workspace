package jxc.app.dao.system;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import jxc.domain.model.system.OperationForm;
@Repository
public class OperationFormDao extends BaseDao<OperationForm,BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<OperationForm> getEntityClass() {
		return OperationForm.class;
	}

}
