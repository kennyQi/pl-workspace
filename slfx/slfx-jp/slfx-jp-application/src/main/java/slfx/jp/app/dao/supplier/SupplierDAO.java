package slfx.jp.app.dao.supplier;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.supplier.Supplier;
import slfx.jp.qo.admin.supplier.SupplierQO;
/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月5日下午3:46:45
 * @版本：V1.0
 *
 */
@Repository
public class SupplierDAO extends BaseDao<Supplier, SupplierQO>  {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, SupplierQO qo) {
		
		if (qo != null) {
			
//			criteria.setFetchMode("name", qo.getName()?FetchMode.SELECT: FetchMode.JOIN);

			// ------------------排序条件------------------
			if(qo.getCreateDateAsc()!=null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
//					
//			if(qo.getCreateDateTo()!=null){
//				criteria.add(Restrictions.le("createDate", qo.getCreateDateTo()));
//			}
//			
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
			if(qo.getNumber()!=null&&qo.getNumber().length()!=0){
				criteria.add(Restrictions.like("number", qo.getNumber(), MatchMode.ANYWHERE));
			}
		}				
		return criteria;
	}

	@Override
	protected Class<Supplier> getEntityClass() {
		return Supplier.class;
	}

}
