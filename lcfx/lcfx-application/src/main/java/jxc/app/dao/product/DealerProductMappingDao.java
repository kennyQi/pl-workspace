package jxc.app.dao.product;

import hg.common.component.BaseDao;
import hg.pojo.qo.DealerProductMappingQO;
import jxc.domain.model.product.DealerProductMapping;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
@Repository
public class DealerProductMappingDao extends BaseDao<DealerProductMapping,DealerProductMappingQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DealerProductMappingQO qo) {

		if(qo != null){
			if(StringUtils.isNotBlank(qo.getProjectId())){
				criteria.add(Restrictions.eq("project.id",qo.getProjectId()));
			}
			if(StringUtils.isNotBlank(qo.getSkuProductId())){
				criteria.add(Restrictions.eq("skuProduct.id",qo.getSkuProductId()));
			}
			if(qo.getSequence() != null){
				criteria.add(Restrictions.eq("sequence",qo.getSequence()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<DealerProductMapping> getEntityClass() {
		return DealerProductMapping.class;
	}

}
