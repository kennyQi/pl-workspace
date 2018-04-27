package plfx.gnjp.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.gnjp.domain.model.order.GNJPOrderLog;
import plfx.yeexing.qo.admin.JPOrderLogQO;


/****
 * 
 * @类功能说明：平台机票订单日志DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月9日下午3:02:22
 * @版本：V1.0
 *
 */
@Repository
public class JPOrderLogDAO extends BaseDao<GNJPOrderLog, JPOrderLogQO> {

	@Override
	protected Class<GNJPOrderLog> getEntityClass() {
		return GNJPOrderLog.class;
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, JPOrderLogQO qo) {
		if(qo != null){
			if(StringUtils.isNotBlank(qo.getOrderId())){
				criteria.add(Restrictions.eq("jpOrder.id",qo.getOrderId()));
			}
			criteria.addOrder(Order.desc("logDate"));
		}
		return criteria;
	}
}
