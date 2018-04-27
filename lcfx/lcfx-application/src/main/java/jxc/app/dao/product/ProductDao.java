package jxc.app.dao.product;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.pojo.command.CreateAttentionCommand;
import hg.pojo.qo.ProductQO;
import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.product.Product;
import jxc.domain.model.product.ProductSupplierCorrelation;
import jxc.domain.util.Tools;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao extends BaseDao<Product, ProductQO> {
	@Override
	protected Criteria buildCriteria(Criteria criteria, ProductQO qo) {
		criteria.createCriteria("unit", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("productCategory", JoinType.LEFT_OUTER_JOIN);
		criteria.createCriteria("productBrand", JoinType.LEFT_OUTER_JOIN);
		if (qo != null) {

			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));

			if (StringUtils.isNotEmpty(qo.getUnitId())) {
				criteria.add(Restrictions.eq("unit.id", qo.getUnitId()));
			}
			
			
			if (qo.getIdList() !=null && qo.getIdList().size()>0) {
				criteria.add(Restrictions.in("id", qo.getIdList()));
			}
			
			if (StringUtils.isNotBlank(qo.getBrandId())) {
				criteria.add(Restrictions.eq("productBrand.id", qo.getBrandId()));
			}
			if (StringUtils.isNotBlank(qo.getName())) {
				if (qo.getNameLike() != null && qo.getNameLike()) {
					criteria.add(Restrictions.like("productName", qo.getName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("productName", qo.getName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getProductCategoryId())) {
				criteria.add(Restrictions.in("productCategory.id", qo.getProductCategoryId().split(",")));
			}
			if (StringUtils.isNotBlank(qo.getProductCode())) {
				if (qo.getProductCodeLike() != null && qo.getProductCodeLike()) {
					criteria.add(Restrictions.like("productCode", qo.getProductCode(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("productCode", qo.getProductCode()));
				}
			}
			if (qo.getStatus() != null) {
				criteria.add(Restrictions.eq("status.using", qo.getStatus()));
			}
			if (qo.getSettingOutStockParam() != null) {
				criteria.add(Restrictions.eq("status.settingOutStockParam", qo.getSettingOutStockParam()));
			}
			if (StringUtils.isNotBlank(qo.getAttribute())) {
				criteria.add(Restrictions.eq("attribute", Integer.parseInt(qo.getAttribute())));
			}

			Tools.DateQueryProcess("createDate", criteria, qo.getCreateDateStart(), qo.getCreateDateEnd(), null);
		}
		criteria.addOrder(Order.desc("createDate"));
		return criteria;
	}
	
	@Override
	protected Class<Product> getEntityClass() {
		return Product.class;
	}

}
