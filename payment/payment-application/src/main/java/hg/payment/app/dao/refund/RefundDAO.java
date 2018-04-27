package hg.payment.app.dao.refund;

import hg.common.component.BaseDao;
import hg.payment.app.pojo.qo.refund.RefundLocalQO;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.refund.Refund;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

@Repository
public class RefundDAO extends BaseDao<Refund,RefundLocalQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, RefundLocalQO qo) {
		
		if(qo != null){
			
			criteria.setFetchMode("payOrder", qo.isPayOrderLazy()? FetchMode.SELECT: FetchMode.JOIN);
			
			DetachedCriteria payOrderCriteria = DetachedCriteria.forClass(PayOrder.class);
			
			if(StringUtils.isNotBlank(qo.getClientTradeNo())){
				payOrderCriteria.add(Restrictions.eq("clientTradeNo", qo.getClientTradeNo()));
			}
			
			if(StringUtils.isNotBlank(qo.getPaymentClientID())){
				payOrderCriteria.add(Restrictions.eq("paymentClient.id", qo.getPaymentClientID()));
			}
			
			if(StringUtils.isNotBlank(qo.getThirdPartyTradeNo())){
				payOrderCriteria.add(Restrictions.eq("thirdPartyTradeNo", qo.getThirdPartyTradeNo()));
			}
			
			criteria.add(Subqueries.propertyIn("payOrder",payOrderCriteria.setProjection(Projections.property("id"))));
			
			
			if(StringUtils.isNotBlank(qo.getTradeNo())){
				criteria.add(Restrictions.eq("payOrder.id", qo.getTradeNo()));
			}
		}
		
		
		
		
		return criteria;
	}

	@Override
	protected Class<Refund> getEntityClass() {
		// TODO Auto-generated method stub
		return Refund.class;
	}

}
