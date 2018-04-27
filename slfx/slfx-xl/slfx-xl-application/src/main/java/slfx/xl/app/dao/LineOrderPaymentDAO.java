package slfx.xl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.xl.domain.model.order.LineOrderPayment;
import slfx.xl.pojo.qo.LineOrderPaymentQO;

/**
 * 
 * @类功能说明：订单支付信息DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月23日下午4:45:36
 * @版本：V1.0
 *
 */
@Repository
public class LineOrderPaymentDAO extends BaseDao<LineOrderPayment,LineOrderPaymentQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, LineOrderPaymentQO qo) {
		
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getLineOrderID())){
				criteria.add(Restrictions.eq("lineOrder.id", qo.getLineOrderID()));
			}
			
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			
			
		}
		return criteria;
	}

	@Override
	protected Class<LineOrderPayment> getEntityClass() {
		return LineOrderPayment.class;
	}

}
