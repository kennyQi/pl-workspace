package hg.payment.app.dao.payorder;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hg.payment.app.pojo.qo.payorder.PayOrderLocalQO;
import hg.payment.domain.model.payorder.PayOrder;
import hg.payment.domain.model.payorder.PayOrderProcessor;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class PayOrderDAO extends BaseDao<PayOrder, PayOrderLocalQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PayOrderLocalQO qo){
		if(qo != null){
			
			//客户端信息延迟加载
//			criteria.setFetchMode("paymentClient", qo.getClientLazy()? FetchMode.SELECT: FetchMode.JOIN);
			
			//支付平台订单ID
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			//客户端ID
			if(StringUtils.isNotBlank(qo.getPaymentClientID())){
				criteria.add(Restrictions.eq("paymentClient.id", qo.getPaymentClientID()));
			}
			
			//支付渠道
			if(qo.getPayChannelType() != null){
				criteria.add(Restrictions.eq("payChannelType", qo.getPayChannelType()));
			}
			
			//商城订单号
			if(StringUtils.isNotBlank(qo.getClientTradeNo())){
				criteria.add(Restrictions.eq("clientTradeNo", qo.getClientTradeNo().trim()));
			}
			
			//开始时间
			if(StringUtils.isNotBlank(qo.getBeginDate())){
				Date begin = DateUtil.dateStr2BeginDate(qo.getBeginDate());
				criteria.add(Restrictions.ge("createDate",begin));
			}
			
			//结束时间
			if(StringUtils.isNotBlank(qo.getEndDate())){
				Date end = DateUtil.dateStr2EndDate(qo.getEndDate());
				criteria.add(Restrictions.le("createDate", end));
			}
			
			//按创建时间倒序排列
			if(qo.getCreateDateAsc() != null){
				criteria.addOrder(qo.getCreateDateAsc()?Order.asc("createDate"):Order.desc("createDate"));
			}
			
			//是否支付成功
			if(qo.getPaySuccess() != null && qo.getPaySuccess()){
				criteria.add(Restrictions.eq("payOrderProcessor.payStatus",PayOrderProcessor.PAY_SUCCESS));
			}
		}
		
		
		return criteria;
	}

	@Override
	protected Class<PayOrder> getEntityClass() {
		// TODO Auto-generated method stub
		return PayOrder.class;
	}

}
