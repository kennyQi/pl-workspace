package slfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.xl.domain.model.crm.LineDealer;
import slfx.xl.pojo.qo.LineDealerQO;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月5日上午9:32:19
 * @版本：V1.0
 *
 */
@Repository
public class LineDealerDAO extends BaseDao<LineDealer, LineDealerQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, LineDealerQO qo) {
		if (qo != null) {
			if(qo.getCreateDateAsc()!=null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			if(qo.getStatus()!=null&&qo.getStatus().length()!=0){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if(qo.getBeginTime()!=null){
				criteria.add(Restrictions.ge("createDate", qo.getBeginTime()));
			}
			if(qo.getEndTime()!=null){
				criteria.add(Restrictions.le("createDate", qo.getEndTime()));
			}			
			if(qo.getName()!=null&&qo.getName().length()!=0){
				criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
			}
			if(qo.getCode()!=null&&qo.getCode().length()!=0){
				criteria.add(Restrictions.like("code", qo.getCode(), MatchMode.ANYWHERE));
			}
		}				
		return criteria;
	}

	@Override
	protected Class<LineDealer> getEntityClass() {
		return LineDealer.class;
	}

}
