package plfx.jp.app.dao.pay;

import hg.common.component.BaseDao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.pay.PayRecord;
import plfx.jp.qo.pay.PayRecordQO;

/**
 * 
 * @类功能说明：支付记录DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年11月16日下午3:13:27
 * @版本：V1.0
 *
 */
@Repository
public class PayRecordDAO extends BaseDao<PayRecord, PayRecordQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PayRecordQO qo) {
		if(qo != null){
			if(qo.getDealerOrderId() != null){
				criteria.add(Restrictions.eq("dealerOrderId", qo.getDealerOrderId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<PayRecord> getEntityClass() {
		return PayRecord.class;
	}

}
