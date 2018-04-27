package slfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.log.JPOrderLog;
import slfx.jp.qo.admin.JPOrderLogQO;

/**
 * 
 * @类功能说明：平台订单日志DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：caizhenghao
 * @创建时间：2015年1月21日下午10:41:04
 * @版本：V1.0
 *
 */
@Repository
public class JPOrderLogDAO extends BaseDao<JPOrderLog, JPOrderLogQO> {

	@Override
	protected Class<JPOrderLog> getEntityClass() {
		return JPOrderLog.class;
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
