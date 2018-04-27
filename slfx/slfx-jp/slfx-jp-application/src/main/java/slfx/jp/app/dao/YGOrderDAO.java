package slfx.jp.app.dao;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import slfx.jp.domain.model.order.YGOrder;
import slfx.jp.qo.admin.YGOrderQO;

/**
 * 
 * @类功能说明：中航易购订单DAO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午4:41:47
 * @版本：V1.0
 *
 */
@Repository
public class YGOrderDAO extends BaseDao<YGOrder, YGOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, YGOrderQO qo) {

		if (qo != null) {
			if (qo.getOrderNo() != null) {
				criteria.add(Restrictions.eq("ygOrderNo", qo.getOrderNo()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<YGOrder> getEntityClass() {
		return YGOrder.class;
	}

	
}
