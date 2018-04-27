package hg.demo.member.service.dao.hibernate.fx;

import hg.demo.member.service.qo.hibernate.fx.ProductQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.Product;

import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

/**
 * @author cangs
 */
@Repository("productDAO")
public class ProductDAO extends BaseHibernateDAO<Product, ProductQO>{

	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

	@Override
	protected void queryEntityComplete(ProductQO qo, List<Product> list) {
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProductQO qo) {
		return criteria;
	}

}
