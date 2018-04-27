package lxs.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.Date;

import lxs.domain.model.line.DateSalePrice;
import lxs.pojo.qo.line.DateSalePriceQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

@Repository
public class LxsDateSalePriceDAO extends BaseDao<DateSalePrice, DateSalePriceQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, DateSalePriceQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
				if(qo.getStartDate()!=null&&qo.getEndDate()!=null){
					criteria.add(Restrictions.between("saleDate", qo.getStartDate(), qo.getEndDate()));
				}
				if(StringUtils.isNotBlank(qo.getSaleDate())){
					Date date = DateUtil.dateStr2BeginDate(qo.getSaleDate());
					criteria.add(Restrictions.eq("saleDate", date));
				}
			}
			criteria.addOrder(Order.asc("saleDate"));
		}
		return criteria;
	}

	@Override
	protected Class<DateSalePrice> getEntityClass() {
		return DateSalePrice.class;
	}

	public double maxProperty(String propertyName, DateSalePriceQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.doubleValue();
	}
	public double minProperty(String propertyName, DateSalePriceQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.min(propertyName)).uniqueResult());
		return number == null ? 0: number.doubleValue();
	}
}
