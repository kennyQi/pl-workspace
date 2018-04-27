package plfx.xl.app.dao;

import hg.common.component.BaseDao;

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

import plfx.xl.domain.model.line.LineSnapshot;
import plfx.xl.domain.model.order.LineOrder;
import plfx.xl.domain.model.order.LineOrderTraveler;
import plfx.xl.pojo.qo.LineOrderQO;

/**
 * 
 * @类功能说明：线路订单DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月16日下午3:26:57
 * @版本：V1.0
 *
 */
@Repository
public class LineOrderDAO extends BaseDao<LineOrder, LineOrderQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineOrderQO qo) {
		//查询排序
		if(qo.getCreateDateAsc() != null){
			criteria.addOrder(qo.getCreateDateAsc() ? Order.asc("baseInfo.createDate"):Order.desc("baseInfo.createDate"));
		}
		//经销商订单号
		if(StringUtils.isNotBlank(qo.getDealerOrderNo())){
			criteria.add(Restrictions.eq("baseInfo.dealerOrderNo", qo.getDealerOrderNo()));
		}
		//平台订单号
		if(StringUtils.isNotBlank(qo.getOrderNo())){
			criteria.add(Restrictions.eq("baseInfo.orderNo", qo.getOrderNo()));
		}
		
		//发团日期区间
		if(qo.getCreateDateFrom() != null){
			criteria.add(Restrictions.ge("baseInfo.travelDate", qo.getCreateDateFrom()));
		}
		if(qo.getCreateDateTo() != null){
			criteria.add(Restrictions.le("baseInfo.travelDate", qo.getCreateDateTo()));
		}
		
		//订单总金额区间
		if(qo.getBeginPrice() != null){
			criteria.add(Restrictions.ge("baseInfo.salePrice", qo.getBeginPrice()));
		}
		if(qo.getEndPrice() != null){
			criteria.add(Restrictions.le("baseInfo.salePrice", qo.getEndPrice()));
		}
		
		//线路id
		if(StringUtils.isNotBlank(qo.getLineId())) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("line.id", qo.getLineId()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		
		//线路名称模糊查询
		if(StringUtils.isNotBlank(qo.getLineName())) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.like("lineName", qo.getLineName(), MatchMode.ANYWHERE));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		//线路类型城市查询
		if(StringUtils.isNotBlank(qo.getCityOfType())){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("cityOfType", qo.getLineName()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
				
		//线路类别查询
		if(qo.getType() != null) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("type", qo.getType()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		//出发地(1国内线2国外线)
		/*if(StringUtils.isNotBlank(qo.getStartingDepart())){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("startGrade", qo.getStartingDepart()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		//线路类别中的1国内线,2国外线
		if(StringUtils.isNotBlank(qo.getDomesticLine())){
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("typeGrade", qo.getDomesticLine()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}*/
		//线路出发地
		if(StringUtils.isNotBlank(qo.getStartingCityID())) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineSnapshot.class, "p");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".lineSnapshot.id").eqProperty("p.id"));
			detachedCriteria.setProjection(Projections.property("id"));
			detachedCriteria.add(Restrictions.eq("starting", qo.getStartingCityID()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		if(qo.getPayStatus() != null) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineOrderTraveler.class, "l");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("l.lineOrder.id"));
			detachedCriteria.setProjection(Projections.property("l.lineOrder.id"));
			detachedCriteria.add(Restrictions.eq("l.xlOrderStatus.payStatus", qo.getPayStatus()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		if(qo.getStatus() != null) {
			DetachedCriteria detachedCriteria = DetachedCriteria.forClass(LineOrderTraveler.class, "l");
			detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("l.lineOrder.id"));
			detachedCriteria.setProjection(Projections.property("l.lineOrder.id"));
			detachedCriteria.add(Restrictions.eq("l.xlOrderStatus.status", qo.getStatus()));
			criteria.add(Subqueries.exists(detachedCriteria));
		}
		return criteria;
	}

	@Override
	protected Class<LineOrder> getEntityClass() {
		return LineOrder.class;
	}
	
	@Override
	public LineOrder queryUnique(LineOrderQO qo) {
		LineOrder lineOrder = super.queryUnique(qo);
		//加载订单游客
		Hibernate.initialize(lineOrder.getTravelers());
		return lineOrder;
	}

}
