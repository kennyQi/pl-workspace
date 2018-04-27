package plfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.line.DayRoute;
import plfx.xl.pojo.qo.DayRouteQO;

/**
 * 
 * @类功能说明：行程DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日上午11:09:46
 * @版本：V1.0
 *
 */
@Repository
public class DayRouteDAO extends BaseDao<DayRoute, DayRouteQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, DayRouteQO qo) {
		
		if(qo != null){
			
			if(qo.getIsLineLazy() != null){
				criteria.setFetchMode("line", qo.getIsLineLazy() ? FetchMode.SELECT: FetchMode.JOIN);
			}
			
			if(StringUtils.isNotBlank(qo.getLineID())){
				
				criteria.add(Restrictions.eq("line.id", qo.getLineID()));
			}
			
			if(qo.getDays() != null){
				criteria.add(Restrictions.eq("days", qo.getDays()));
			}
			
			if(qo.getDaysAsc()){
				criteria.addOrder(qo.getDaysAsc()?Order.asc("days"):Order.desc("days"));
			}
			
			
			
			
		}
		return criteria;
	}

	@Override
	protected Class<DayRoute> getEntityClass() {
		return DayRoute.class;
	}

}
