package plfx.jd.app.dao;

import hg.common.component.BaseDao;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import plfx.jd.domain.model.order.Customer;
import plfx.jd.domain.model.order.HotelOrder;
import plfx.jd.pojo.qo.HotelOrderQO;

/**
 * 
 * @类功能说明：酒店订单DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年02月27日下午4:29:38
 * @版本：V1.0
 *
 */
@Repository
public class HotelOrderDAO extends BaseDao<HotelOrder, HotelOrderQO> {
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	@Override
	protected Criteria buildCriteria(Criteria criteria, HotelOrderQO qo) {
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getDealerProjectId())){
				criteria.add(Restrictions.eq("dealer.id", qo.getDealerProjectId()));
			}
			
			if(StringUtils.isNotBlank(qo.getOrderNo())){
				criteria.add(Restrictions.eq("orderNo", qo.getOrderNo()));
			}
			
			if(StringUtils.isNotBlank(qo.getSupplierOrderNo())){
				if(StringUtils.isNotBlank(qo.getSupplierProjectId())){
					criteria.add(Restrictions.eq("supplier.id", qo.getSupplierProjectId()));
				}
				criteria.add(Restrictions.like("supplierOrderNo", qo.getSupplierOrderNo(),MatchMode.ANYWHERE));
			}
			
			if(StringUtils.isNotBlank(qo.getHotelNo())){
				criteria.add(Restrictions.like("hotelNo",qo.getHotelNo(),MatchMode.ANYWHERE));
			}
			try{
				if(StringUtils.isNotBlank(qo.getCreateDateBegin()) && StringUtils.isNotBlank(qo.getCreateDateEnd())){
					criteria.add(Restrictions.between("creationDate",sdf.parseObject(qo.getCreateDateBegin()),sdf.parseObject(qo.getCreateDateEnd())));
				}else if(StringUtils.isNotBlank(qo.getCreateDateBegin())){
					criteria.add(Restrictions.gt("creationDate",sdf.parseObject(qo.getCreateDateBegin())));
				}else if(StringUtils.isNotBlank(qo.getCreateDateEnd())){
					criteria.add(Restrictions.lt("creationDate",sdf.parseObject(qo.getCreateDateEnd())));
				}
			
				if(StringUtils.isNotBlank(qo.getArrivalDateBegin()) && StringUtils.isNotBlank(qo.getArrivalDateEnd())){
					criteria.add(Restrictions.between("arrivalDate",sdf.parseObject(qo.getArrivalDateBegin()),sdf.parseObject(qo.getArrivalDateEnd())));
				}else if(StringUtils.isNotBlank(qo.getArrivalDateBegin())){
					criteria.add(Restrictions.gt("arrivalDate",sdf.parseObject(qo.getArrivalDateBegin())));
				}else if(StringUtils.isNotBlank(qo.getArrivalDateEnd())){
					criteria.add(Restrictions.lt("arrivalDate",sdf.parseObject(qo.getArrivalDateEnd())));
				}
				
				if(StringUtils.isNotBlank(qo.getDepartureDateBegin()) && StringUtils.isNotBlank(qo.getDepartureDateEnd())){
					criteria.add(Restrictions.between("departureDate",sdf.parseObject(qo.getDepartureDateBegin()),sdf.parseObject(qo.getDepartureDateEnd())));
				}else if(StringUtils.isNotBlank(qo.getDepartureDateBegin())){
					criteria.add(Restrictions.gt("departureDate",sdf.parseObject(qo.getDepartureDateBegin())));
				}else if(StringUtils.isNotBlank(qo.getDepartureDateEnd())){
					criteria.add(Restrictions.lt("departureDate",sdf.parseObject(qo.getDepartureDateEnd())));
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			if(StringUtils.isNotBlank(qo.getContacts())){
				DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Customer.class, "c");
				detachedCriteria.add(Property.forName(Criteria.ROOT_ALIAS + ".id").eqProperty("c.hotelOrder.id"));
				detachedCriteria.setProjection(Projections.property("id"));
				detachedCriteria.add(Restrictions.eq("c.type","0"));
				detachedCriteria.add(Restrictions.eq("c.name", qo.getContacts()));
				criteria.add(Subqueries.exists(detachedCriteria));
			}
			if(StringUtils.isNotBlank(qo.getStatus())){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<HotelOrder> getEntityClass() {
		return HotelOrder.class;
	}

}
