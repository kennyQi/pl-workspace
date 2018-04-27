package jxc.app.dao.product;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.component.BaseQo;
import jxc.domain.model.product.ProductSnapshot;
@Repository
public class ProductSnapshotDao extends BaseDao<ProductSnapshot,BaseQo>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, BaseQo qo) {
		return criteria;
	}

	@Override
	protected Class<ProductSnapshot> getEntityClass() {
		return ProductSnapshot.class;
	}
	
}
