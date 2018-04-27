package plfx.xl.app.dao;


import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.xl.domain.model.salepolicy.SalePolicySnapshot;
import plfx.xl.pojo.qo.SalePolicySnapshotQO;

/**
 * @类功能说明：价格政策快照DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月24日上午10:17:49
 * @版本：V1.0
 *
 */
@Repository("salePolicySnapshotDAO_xl")
public class SalePolicySnapshotDAO extends BaseDao<SalePolicySnapshot, SalePolicySnapshotQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, SalePolicySnapshotQO qo) {
		
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id",qo.getId()));
			}
			
			if(StringUtils.isNotBlank(qo.getSalePolicyID())){
				criteria.add(Restrictions.eq("salePolicy.id",qo.getSalePolicyID()));
			}
			
			if(qo.getIsNew()!= null && qo.getIsNew()){
				criteria.addOrder(Order.desc("createDate"));
				criteria.setMaxResults(1);
			}
			
			
		}
		return criteria;
	}

	@Override
	protected Class<SalePolicySnapshot> getEntityClass() {
		return SalePolicySnapshot.class;
	}
}
