package hsl.app.dao.line;
import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.domain.model.xl.DateSalePrice;
import hsl.domain.model.xl.DayRoute;
import hsl.domain.model.xl.Line;
import hsl.domain.model.xl.LineImage;
import hsl.domain.model.xl.LinePictureFolder;
import hsl.domain.model.xl.LineRouteInfo;
import hsl.pojo.qo.line.HslLineQO;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSON;
@Repository
public class LineDAO extends BaseDao<Line,HslLineQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslLineQO qo) {
		
		if(qo!=null){
			//主键查找
			if(!StringUtils.isBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			//名字查询
			if(!StringUtils.isBlank(qo.getSearchName())){
				criteria.add(Restrictions.like("baseInfo.name", "%"+qo.getSearchName()+"%"));
			}
			//出发城市查询
			if(!StringUtils.isBlank(qo.getStartCity())){
				criteria.add(Restrictions.eq("baseInfo.starting", qo.getStartCity()));
			}
			
			//目的地城市查询
			if(StringUtils.isNotBlank(qo.getEndCity())){
				
				criteria.add(Restrictions.like("baseInfo.destinationCity","%"+ qo.getEndCity()+"%"));
			}
			
			if(StringUtils.isNotBlank(qo.getEndCityName())){
				
				criteria.add(Restrictions.like("baseInfo.destinationCity","%"+ qo.getEndCity()+"%"));
			}
			
			
			//价格范围查询
					
			if(qo.getMaxPrice()!=null&&qo.getMinPrice()!=null){
				criteria.add(Restrictions.between("minPrice", new Double(qo.getMinPrice()),new Double(qo.getMaxPrice())));
				
			}else if(qo.getMaxPrice()!=null){
				criteria.add(Restrictions.le("minPrice",new Double(qo.getMaxPrice())));
			}else if(qo.getMinPrice()!=null){
				criteria.add(Restrictions.ge("minPrice", new Double(qo.getMinPrice())));
			}
			//出发时间 查询
			if(qo.getBeginDateTime() != null&&qo.getEndDateTime()!=null){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections.property("d.line.id"));
				detachedCriteria.add(Restrictions.between("d.saleDate", qo.getBeginDateTime(),qo.getEndDateTime()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}else if(qo.getBeginDateTime()!=null){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections.property("d.line.id"));
				detachedCriteria.add(Restrictions.gt("d.saleDate", qo.getBeginDateTime()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}else if(qo.getEndDateTime()!=null){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections.property("d.line.id"));
				detachedCriteria.add(Restrictions.lt("d.saleDate", qo.getEndDateTime()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			
			//酒店等级
			if(!StringUtils.isBlank(qo.getLevel())){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(DayRoute.class, "d");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections.property("d.line.id"));
				detachedCriteria.add(Restrictions.like("d.stayLevel",qo.getLevel(),MatchMode.ANYWHERE));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			
			//行程天数
			if(qo.getDayCount()!=null){
				switch(qo.getScope()){
					case 0:{
						criteria.add(Restrictions.eq("routeInfo.routeDays", qo.getDayCount()));
						break;
					}
					case 1:{
						criteria.add(Restrictions.ge("routeInfo.routeDays", qo.getDayCount()));	
						break;
					}
					case -1:{
						criteria.add(Restrictions.le("routeInfo.routeDays", qo.getDayCount()));
						break;
					}
				}
				
			}
			
			if(qo.getForSale()!=0){
				criteria.add(Restrictions.eq("forSale", qo.getForSale()));
			}
			//按销量排序
			if(qo.getOrderKind()==HslLineQO.ORDERKIND_RECOMMENDATIONLEVEL){
				if(qo.getOrderType()==HslLineQO.ORDERTYPE_DESC){
					criteria.addOrder(Order.desc("baseInfo.recommendationLevel"));
				}else{
					 criteria.addOrder(Order.asc("baseInfo.recommendationLevel"));
				}
			}else if(qo.getOrderKind()==HslLineQO.ORDERKIND_SALES){
				if(qo.getOrderType()==HslLineQO.ORDERTYPE_DESC){
					criteria.addOrder(Order.desc("baseInfo.sales"));
				}else{
					criteria.addOrder(Order.asc("baseInfo.sales"));
				}
			}else if(qo.getOrderKind()==HslLineQO.ORDERKIND_PRICE){
				if(qo.getOrderType()==HslLineQO.ORDERTYPE_DESC){
					criteria.addOrder(Order.desc("minPrice"));
				}else{
					criteria.addOrder(Order.asc("minPrice"));
				}
			}else{
				//按上架时间倒序排序
				criteria.addOrder(Order.desc("baseInfo.createDate"));
			}
			
			
			
		}
		
		
		return criteria;
	}

	/**
	 * 出发时间范围查询
	 * @param qo
	 * @param detachedCriteria
	 */
	private boolean setDateQuery(final HslLineQO qo, final DetachedCriteria detachedCriteria) {
		if(qo.getBeginDateTime() != null&&qo.getEndDateTime()!=null){
			detachedCriteria.add(Restrictions.between("d.saleDate", qo.getBeginDateTime(),qo.getEndDateTime()));
		}else if(qo.getBeginDateTime()!=null){
			detachedCriteria.add(Restrictions.gt("d.saleDate", qo.getBeginDateTime()));
		}else if(qo.getEndDateTime()!=null){
			detachedCriteria.add(Restrictions.lt("d.saleDate", qo.getEndDateTime()));
		}else{
			return false;
		}
		return true;
	}

	@SuppressWarnings("unused")
	private Criteria checkDateSale(Criteria criteria, Criteria dateSaleCriteria) {
		if(dateSaleCriteria==null){
			dateSaleCriteria = criteria.createCriteria("dateSalePriceList",JoinType.LEFT_OUTER_JOIN);
		}
		return dateSaleCriteria;
	}
	
	@SuppressWarnings("unused")
	private Criteria checkDayRoute(Criteria criteria, Criteria dayRouteCriteria){
		if(dayRouteCriteria==null){
			dayRouteCriteria = criteria.createCriteria("routeInfo.dayRouteList",JoinType.LEFT_OUTER_JOIN);
			dayRouteCriteria.addOrder(Order.asc("days"));
		}
		return dayRouteCriteria;
	}

	@Override
	protected Class<Line> getEntityClass() {
		return Line.class;
	}

	@Override
	public List<Line> queryList(HslLineQO qo, Integer offset, Integer maxSize) {
		List<Line> list=super.queryList(qo, offset, maxSize);
		for(Line l:list){
			init(l,qo);
		}
		return super.queryList(qo, offset, maxSize);
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		pagination = super.queryPagination(pagination);
		@SuppressWarnings("unchecked")
		List<Line> list= (List<Line>)pagination.getList();
		HslLineQO qo = (HslLineQO)pagination.getCondition();
		for(Line l:list){
			init(l,qo);
		}
		return pagination;
	}

	@Override
	public Line queryUnique(HslLineQO qo) {
		Line line =  super.queryUnique(qo);
		if(line!=null){
			init(line,qo);
		}
		return line;
	}
	
	public Line querySimpleUnique(HslLineQO qo){
		return super.queryUnique(qo);
	}
	
	private void init(Line line,HslLineQO qo){
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
	
	

}
