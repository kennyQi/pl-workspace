package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.pojo.qo.SpecValueQO;
import hg.pojo.qo.SpecificationQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.SpecValue;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
@Repository
public class SpecValueDao extends BaseDao<SpecValue, SpecValueQO> {

	
	@Override
	protected Criteria buildCriteria(Criteria criteria, SpecValueQO qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

			if(StringUtils.isNotBlank(qo.getSpecificationId())){
				criteria.add(Restrictions.eq("specification.id",qo.getSpecificationId()));
			}
			if(StringUtils.isNotBlank(qo.getName())){
				criteria.add(Restrictions.eq("specValue",qo.getName()));
			}
		}
		criteria.createCriteria("specification");
		return criteria;
	}

	@Override
	protected Class<SpecValue> getEntityClass() {
		return SpecValue.class;
	}

}
