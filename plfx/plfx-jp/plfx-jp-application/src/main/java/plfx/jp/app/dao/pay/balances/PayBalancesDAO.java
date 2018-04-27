package plfx.jp.app.dao.pay.balances;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.pay.balances.PayBalances;
import plfx.jp.qo.pay.balances.PayBalancesQO;

/**
 * 
 * @类功能说明：支付宝余额DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年12月25日下午1:36:52
 * @版本：V1.0
 *
 */
@Repository
public class PayBalancesDAO extends BaseDao<PayBalances, PayBalancesQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PayBalancesQO qo) {
		if(qo != null){
			if(qo.getType() != null){
				criteria.add(Restrictions.eq("type", qo.getType()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<PayBalances> getEntityClass() {
		return PayBalances.class;
	}

}
