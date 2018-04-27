package lxs.app.dao.line;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hg.common.util.MyBeanUtils;

import java.util.ArrayList;
import java.util.List;

import lxs.domain.model.line.DayRoute;
import lxs.domain.model.line.Line;
import lxs.domain.model.line.LineRouteInfo;
import lxs.pojo.qo.line.LineQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.springframework.stereotype.Repository;

@Repository
public class LxsLineDAO extends BaseDao<Line, LineQO> {


	@Override
	protected Class<Line> getEntityClass() {
		return Line.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineQO qo) {
		/**
		 *
		 * 
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("a"));
		projectionList.add(Projections.rowCount());
		criteria.setProjection(projectionList);
		实现了
		select a,count(*) from TABLE group by a;
		 */
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getSearchName())) {
				criteria.add(Restrictions.like("baseInfo.name", qo.getSearchName(), MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(qo.getStartCity())) {
				criteria.add(Restrictions.eq("baseInfo.starting",qo.getStartCity()));
			}
			if (qo.getStartingProvince()!=null&&qo.getStartingProvince().size()!=0) {
				criteria.add(Restrictions.in("baseInfo.starting",qo.getStartingProvince()));
			}
			if (qo.getLineIDs()!=null) {
				criteria.add(Restrictions.in("id",qo.getLineIDs()));
			}
			if (StringUtils.isNotBlank(qo.getType())) {
				criteria.add(Restrictions.eq("baseInfo.type",Integer.parseInt(qo.getType())));
			}
			if(StringUtils.isNotBlank(qo.getIsSale())){
				criteria.add(Restrictions.eq("forSale",Integer.parseInt(qo.getIsSale())));
			}
			if(StringUtils.isNotBlank(String.valueOf(qo.getForSale()))&&qo.getForSale()!=0){
				if(!StringUtils.equals("yes", qo.getForSaletype()))
					criteria.add(Restrictions.eq("forSale",qo.getForSale()));
			}
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id",qo.getId()));
			}
			if(StringUtils.isNotBlank(qo.getDestinationCity())){
				criteria.add(Restrictions.like("baseInfo.destinationCity",qo.getDestinationCity(),MatchMode.ANYWHERE));
			}
			if(qo.getLocalStatus()==LineQO.NOT_DEL){
				criteria.add(Restrictions.not(Restrictions.eq("localStatus", 2)));
			}
			if(qo.getLocalStatus()==LineQO.SHOW){
				criteria.add(Restrictions.eq("localStatus", 0));
			}
			if(qo.getLocalStatus()==LineQO.HIDE){
				criteria.add(Restrictions.eq("localStatus", 1));
			}
			if(qo.getLowPrice()!=0&&qo.getHighPrice()!=0){
				criteria.add(Restrictions.between("minPrice", qo.getLowPrice(), qo.getHighPrice()));
			}
			if(qo.getRouteDays()!=null){
				criteria.add(Restrictions.eq("routeInfo.routeDays", qo.getRouteDays()));
			}
			if(StringUtils.isNotBlank(qo.getGetHotDestinationCity())&&StringUtils.equals(qo.getGetHotDestinationCity(), "get")){
				ProjectionList projectionList = Projections.projectionList();
				projectionList.add(Projections.groupProperty("baseInfo.destinationCity"));
				projectionList.add(Projections.rowCount());
				criteria.add(Restrictions.eq("baseInfo.type",Integer.parseInt(qo.getType())));
				criteria.setProjection(projectionList);
			}
			if(StringUtils.isNotBlank(qo.getRouteDays5More())&&StringUtils.equals(qo.getRouteDays5More(), "yes")){
				criteria.add(Restrictions.ge("routeInfo.routeDays", 5));
			}
			if(qo.getSaleDate()!=null&&StringUtils.isNotBlank(qo.getSaleDate())){
				criteria.add(Restrictions.like("saleDates",qo.getSaleDate(),MatchMode.ANYWHERE));
			}
		}
		// 排序
		if (qo.getOrder() != 0 && qo.getOrder() == 1) {
			if (qo.getOrderType().equals("asc")) {
				criteria.addOrder(Order.asc("baseInfo.sales"));
			} else {
				criteria.addOrder(Order.desc("baseInfo.sales"));
			}
		} else if (qo.getOrder() != 0	&& qo.getOrder() == 2) {
			if (qo.getOrderType().equals("asc")) {
				criteria.addOrder(Order.asc("minPrice"));
			} else {
				criteria.addOrder(Order.desc("minPrice"));
			}
		} else {
			if(qo.getSort()==LineQO.QUERY_WITH_TOP){
				criteria.addOrder(Order.desc("sort"));
			}
			criteria.addOrder(Order.desc("baseInfo.createDate"));
		}
		return criteria;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		List<Line> list = (List<Line>) pagination.getList();
		LineQO qo = (LineQO) pagination.getCondition();
		for (Line l : list) {
			init(l, qo);
		}
		return pagination;
	}

	@Override
	public Line queryUnique(LineQO qo) {
		Line line = super.queryUnique(qo);
		if (line != null) {
			init(line, qo);
		}
		return line;
	}

	public Line querySimpleUnique(LineQO qo) {
		return super.queryUnique(qo);
	}

	private void init(Line line,LineQO qo){
		if(qo.getGetDateSalePrice()){
			Hibernate.initialize(line.getDateSalePriceList());
		}
		Hibernate.initialize(line.getLineImageList());
		LineRouteInfo info = line.getRouteInfo();
		if(info!=null){
			Hibernate.initialize(info.getDayRouteList());
			for(DayRoute dayRoute:info.getDayRouteList()){
				Hibernate.initialize(dayRoute.getLineImageList());
			}
		}
	}

	public int maxProperty(String propertyName, LineQO qo) {
		Criteria criteria = createCriteria(qo);
		CriteriaImpl impl = (CriteriaImpl) criteria;
		MyBeanUtils.setFieldValue(impl, ORDER_ENTRIES, new ArrayList<Object>());
		Number number = ((Number) criteria.setProjection(Projections.max(propertyName)).uniqueResult());
		return number == null ? 0: number.intValue();
	}
}
