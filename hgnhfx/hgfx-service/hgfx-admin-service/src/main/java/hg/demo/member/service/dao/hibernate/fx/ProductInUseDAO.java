package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ProductInUseQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.ProductInUse;

/**
 * 商户使用商品dao
 * @author Caihuan
 * @date   2016年6月1日
 */
@Repository("productInUseDAO")
public class ProductInUseDAO extends BaseHibernateDAO<ProductInUse, ProductInUseQO> {

	@Autowired
	private DistributorDAO distributorDAO;
	@Override
	protected Class<ProductInUse> getEntityClass() {
		return ProductInUse.class;
	}

	@Override
	protected void queryEntityComplete(ProductInUseQO qo,
			List<ProductInUse> list) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ProductInUseQO qo) {
		
		if(qo.getDistributorQo()!=null)
		{
			Criteria dc = criteria.createCriteria("distributor", qo.getDistributorQo().getAlias(), JoinType.LEFT_OUTER_JOIN);
			distributorDAO.buildCriteriaOut(dc, qo.getDistributorQo());
		}
		return criteria;
	}

}
