package slfx.xl.app.dao;

import hg.common.component.BaseDao;

import java.util.Date;
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
import org.springframework.stereotype.Repository;

import slfx.xl.domain.model.line.DateSalePrice;
import slfx.xl.domain.model.line.DayRoute;
import slfx.xl.domain.model.line.Line;
import slfx.xl.domain.model.line.LineImage;
import slfx.xl.domain.model.line.LineSnapshot;
import slfx.xl.pojo.qo.LineQO;

/**
 * 
 * @类功能说明：线路DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月02日下午4:41:04
 * @版本：V1.0
 * 
 */
@Repository
public class LineDAO extends BaseDao<Line, LineQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineQO qo) {

		if (qo != null) {

			if (StringUtils.isNotBlank(qo.getId())) {
				criteria.add(Restrictions.eq("id", qo.getId()));
			}

			if (qo.getIds() != null) {
				criteria.add(Restrictions.in("id", qo.getIds()));
			}

			if (StringUtils.isNotBlank(qo.getLineName())) {
				if (qo.isNameLike()) {
					criteria.add(Restrictions.like("baseInfo.name",
							qo.getLineName(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("baseInfo.name",
							qo.getLineName()));
				}
			}
			if (StringUtils.isNotBlank(qo.getNumber())) {
				criteria.add(Restrictions.eq("baseInfo.number",
						qo.getNumber()));
			}
			if (qo.getType() != null) {
				criteria.add(Restrictions.eq("baseInfo.type", qo.getType()));
			}

			if (qo.getStatus() != null) {
				criteria.add(Restrictions.eq("statusInfo.status",
						qo.getStatus()));
			}

			if (StringUtils.isNotBlank(qo.getStartingCityID())) {
				criteria.add(Restrictions.eq("baseInfo.starting",
						qo.getStartingCityID()));
			}

			if (qo.getCreateDateFrom() != null) {
				criteria.add(Restrictions.ge("baseInfo.createDate",
						qo.getCreateDateFrom()));
			}

			if (qo.getCreateDateTo() != null) {
				criteria.add(Restrictions.le("baseInfo.createDate",
						qo.getCreateDateTo()));
			}

			if (qo.getCreateDateAsc() != null) {
				criteria.addOrder(qo.getCreateDateAsc() ? Order
						.asc("baseInfo.createDate") : Order
						.desc("baseInfo.createDate"));
			}

			// 线路类型中的城市信息
			if (StringUtils.isNotBlank(qo.getCityOfType())) {
				criteria.add(Restrictions.eq("baseInfo.cityOfType",
						qo.getCityOfType()));
			}

			// 成人价格
			if (qo.getAdultPriceMax() != null && qo.getAdultPriceMin() != null) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.between("d.adultPrice",
						qo.getAdultPriceMin(), qo.getAdultPriceMax()));
				if (qo.getBeginDate() != null) {
					if (qo.getBeginDate().getTime() < new Date().getTime()) {
						qo.setBeginDate(new Date());
					}
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							qo.getBeginDate()));
				} else {
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							new Date()));
				}
				criteria.add(Subqueries.exists(detachedCriteria));
			} else if (qo.getAdultPriceMax() != null) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.lt("d.adultPrice",
						qo.getAdultPriceMax()));
				if (qo.getBeginDate() != null) {
					if (qo.getBeginDate().getTime() < new Date().getTime()) {
						qo.setBeginDate(new Date());
					}
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							qo.getBeginDate()));
				} else {
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							new Date()));
				}
				criteria.add(Subqueries.exists(detachedCriteria));
			} else if (qo.getAdultPriceMin() != null) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.gt("d.adultPrice",
						qo.getAdultPriceMin()));
				if (qo.getBeginDate() != null) {
					if (qo.getBeginDate().getTime() < new Date().getTime()) {
						qo.setBeginDate(new Date());
					}
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							qo.getBeginDate()));
				} else {
					detachedCriteria.add(Restrictions.gt("d.saleDate",
							new Date()));
				}
				criteria.add(Subqueries.exists(detachedCriteria));
			}

			if (qo.getRouteDays() != null) {
				criteria.add(Restrictions.eq("routeInfo.routeDays",
						qo.getRouteDays()));
			}
			if (StringUtils.isNotBlank(qo.getStayLevel())) {
				criteria.createAlias("routeInfo.dayRouteList", "dayRoute");
				criteria.add(Restrictions.like("dayRoute.stayLevel",
						qo.getStayLevel(), MatchMode.ANYWHERE));
			}

			if (qo.getBeginDate() != null && qo.getEndDate() != null) {
				if (qo.getBeginDate().getTime() < new Date().getTime()) {
					qo.setBeginDate(new Date());
				}
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.between("d.saleDate",
						qo.getBeginDate(), qo.getEndDate()));
				criteria.add(Subqueries.exists(detachedCriteria));
			} else if (qo.getBeginDate() != null) {
				if (qo.getBeginDate().getTime() < new Date().getTime()) {
					qo.setBeginDate(new Date());
				}
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.gt("d.saleDate",
						qo.getBeginDate()));
				criteria.add(Subqueries.exists(detachedCriteria));
			} else if (qo.getEndDate() != null) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						DateSalePrice.class, "d");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("d.line.id"));
				detachedCriteria.setProjection(Projections
						.property("d.line.id"));
				detachedCriteria.add(Restrictions.between("d.saleDate",
						new Date(), qo.getEndDate()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			if (qo.isRecommendationLevelAsc()) {
				criteria.addOrder(Order.asc("baseInfo.recommendationLevel"));
			}
			if (qo.isSalesAsc()) {
				criteria.addOrder(Order.asc("baseInfo.sales"));
			}
			if (qo.isPriceAsc()) {
				// TODO
			}
			// 接口分页使用
			if (qo.getPageNo() != null && qo.getPageNo() > 0
					&& qo.getPageSize() != null && qo.getPageSize() > 0) {
				Integer offset = (qo.getPageNo() - 1) * qo.getPageSize();
				Integer maxSize = qo.getPageNo() * qo.getPageSize();
				if (offset != null && offset >= 0) {
					criteria.setFirstResult(offset);
				}
				if (maxSize != null && maxSize >= 1) {
					criteria.setMaxResults(maxSize);
				}
			}
			if (qo.getLineSnapshotBeginDate() != null
					&& qo.getLineSnapshotEndDate() != null) {
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(
						LineSnapshot.class, "l");
				detachedCriteria.add(Property.forName(
						Criteria.ROOT_ALIAS + ".id").eqProperty("l.line.id"));
				detachedCriteria.setProjection(Projections
						.property("l.line.id"));
				detachedCriteria.add(Restrictions.between("l.createDate",
						qo.getLineSnapshotBeginDate(),
						qo.getLineSnapshotEndDate()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}

		}

		return criteria;
	}

	/**
	 * 
	 * @方法功能说明：查询价格政策的适用的线路列表
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月5日上午10:03:40
	 * @修改内容：
	 * @参数：@param salePolicyID
	 * @参数：@return
	 * @return:List<Line>
	 * @throws
	 */
	@SuppressWarnings("unchecked")
	public List<Line> findLineBySalePolicyID(String salePolicyID) {

		String hql = "select distinct line from Line line ,SalePolicy salePolicy where line in elements(salePolicy.lines) and salePolicy.id=?";

		return this.find(hql, salePolicyID);
	}

	@Override
	protected Class<Line> getEntityClass() {
		return Line.class;
	}
	
	@Override
	public Line queryUnique(LineQO qo) {
		Line line = super.queryUnique(qo);
		if (line != null && line.getLineImageList() != null) {
			Hibernate.initialize(line.getLineImageList());
		}
		if(null != line.getDateSalePriceList()){
			Hibernate.initialize(line.getDateSalePriceList());
		}
		if(null != line && null != line.getRouteInfo() && null != line.getRouteInfo().getDayRouteList()){
			List<DayRoute> dayRouteList = line.getRouteInfo().getDayRouteList();
			for(DayRoute dayRoute : dayRouteList){
				if(null != dayRoute.getLineImageList() && !dayRoute.getLineImageList().isEmpty()){
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
		}
				
		return line;
	}
	
	@Override
	public List<Line> queryList(LineQO qo) {
		List<Line> lineList = super.queryList(qo);
		if(null != lineList){
			for(Line line : lineList){
				if(null != line.getDateSalePriceList()){
					Hibernate.initialize(line.getDateSalePriceList());
				}
				if (line != null && line.getLineImageList() != null) {
					Hibernate.initialize(line.getLineImageList());
					for(LineImage lineImage : line.getLineImageList()){
						Hibernate.initialize(lineImage.getLine());
					}
				}
				if(null != line && null != line.getRouteInfo() && null != line.getRouteInfo().getDayRouteList()){
					List<DayRoute> dayRouteList = line.getRouteInfo().getDayRouteList();
					for(DayRoute dayRoute : dayRouteList){
						if(null != dayRoute.getLineImageList() && !dayRoute.getLineImageList().isEmpty()){
							Hibernate.initialize(dayRoute.getLineImageList());
							for(LineImage lineImage : dayRoute.getLineImageList()){
								Hibernate.initialize(lineImage.getDayRoute());
							}
						}
					}
				}
			}
		}
		return lineList;
	}

}
