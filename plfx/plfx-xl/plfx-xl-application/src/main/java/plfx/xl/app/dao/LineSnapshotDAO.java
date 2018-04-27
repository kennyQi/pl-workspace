package plfx.xl.app.dao;


import hg.common.component.BaseDao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.DayRoute;
import plfx.xl.domain.model.line.LineSnapshot;
import plfx.xl.pojo.qo.LineSnapshotQO;

/**
 * 
 * @类功能说明：线路快照DAO
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
public class LineSnapshotDAO extends BaseDao<LineSnapshot, LineSnapshotQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineSnapshotQO qo) {
		
		if(qo != null){
			
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id",qo.getId()));
			}
			
			if(StringUtils.isNotBlank(qo.getLineID())){
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			
			if(qo.getIsNew()!= null && qo.getIsNew()){
				criteria.addOrder(Order.desc("createDate"));
				criteria.setMaxResults(1);
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<LineSnapshot> getEntityClass() {
		return LineSnapshot.class;
	}
	
	@Override
	public LineSnapshot queryUnique(LineSnapshotQO qo) {
		LineSnapshot lineSnapshot = super.queryUnique(qo);
		if (lineSnapshot.getLine() != null && lineSnapshot.getLine().getLineImageList() != null) {
			Hibernate.initialize(lineSnapshot.getLine().getLineImageList());
		}
		if(null != lineSnapshot.getLine().getDateSalePriceList()){
			Hibernate.initialize(lineSnapshot.getLine().getDateSalePriceList());
		}
		if(null != lineSnapshot.getLine() && null != lineSnapshot.getLine().getRouteInfo() && null != lineSnapshot.getLine().getRouteInfo().getDayRouteList()){
			List<DayRoute> dayRouteList = lineSnapshot.getLine().getRouteInfo().getDayRouteList();
			for(DayRoute dayRoute : dayRouteList){
				if(null != dayRoute.getLineImageList() && !dayRoute.getLineImageList().isEmpty()){
					Hibernate.initialize(dayRoute.getLineImageList());
				}
			}
		}
				
		return lineSnapshot;
	}

}
