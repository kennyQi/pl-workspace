package hsl.app.dao.account;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.dto.jp.status.JPOrderStatusConstant;
import hsl.pojo.dto.line.order.XLOrderStatusConstant;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
@Repository
public class AccountConsumeSnapshotDao extends BaseDao<AccountConsumeSnapshot,AccountConsumeSnapshotQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria,AccountConsumeSnapshotQO qo) {
		if(qo!=null){
			if(qo.getStatus()!=null&&qo.getStatus()>0){
				//机票已退款
				if(qo.getStatus()==Integer.parseInt(JPOrderStatusConstant.SHOP_TICKET_REBACK_SUCC)||qo.getStatus()==Integer.parseInt(XLOrderStatusConstant.SLFX_REFUND_SUCCESS)){
					criteria.add(Restrictions.eq("status", AccountConsumeSnapshot.STATUS_TK));
				}else{
					if(qo.getAccountstatus()!=null&&qo.getAccountstatus()>0){
						criteria.add(Restrictions.eq("status", qo.getAccountstatus()));
					}else{
						criteria.add(Restrictions.eq("status", AccountConsumeSnapshot.STATUS_SY));
					}
				}
			}else{
				if(qo.getAccountstatus()!=null&&qo.getAccountstatus()>0){
					criteria.add(Restrictions.eq("status", qo.getAccountstatus()));
				}
			}
			if(StringUtils.isNotBlank(qo.getOrderId())){
				criteria.add(Restrictions.eq("orderId", qo.getOrderId()));
			}
			if(StringUtils.isNotBlank(qo.getAccountId())){
				criteria.createAlias("account", "account");
				criteria.add(Restrictions.eq("account.id", qo.getAccountId()));
			}
			if(StringUtils.isNotBlank(qo.getRefundOrderId())){
				criteria.add(Restrictions.eq("refundOrderId", qo.getRefundOrderId()));
			}
			if(qo.getOrderType()!=null&&qo.getOrderType()>0){
				criteria.add(Restrictions.eq("orderType", qo.getOrderType()));
			}
		}
		return criteria;
	}
	@Override
	protected Class<AccountConsumeSnapshot> getEntityClass() {
		return AccountConsumeSnapshot.class;
	}

}
