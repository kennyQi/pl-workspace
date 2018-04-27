package hg.payment.app.dao.client;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.payment.app.pojo.qo.client.PaymentClientLocalQO;
import hg.payment.domain.model.client.PaymentClient;

@Repository
public class PaymentClientDAO extends BaseDao<PaymentClient,PaymentClientLocalQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, PaymentClientLocalQO qo) {
		if(qo != null){
			//名称
			if(StringUtils.isNotBlank(qo.getName())){
				if(qo.getIsNameLike()){
					criteria.add(Restrictions.like("name", qo.getName(),MatchMode.ANYWHERE));
				}else{
					criteria.add(Restrictions.eq("name", qo.getName()));
				}
			}
			
			//是否启用
			if(qo.getValid() != null){
				criteria.add(Restrictions.eq("valid", qo.getValid()));
			}
			
			//id
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			
			//可用渠道
			if(StringUtils.isNotBlank(qo.getValidPayChannel())){
				criteria.add(Restrictions.or(Restrictions.like("validPayChannels", ","+qo.getValidPayChannel()+",",MatchMode.ANYWHERE),
						Restrictions.like("validPayChannels", ","+qo.getValidPayChannel(),MatchMode.ANYWHERE),
						Restrictions.like("validPayChannels", qo.getValidPayChannel()+",",MatchMode.ANYWHERE),
						Restrictions.like("validPayChannels", qo.getValidPayChannel(),MatchMode.ANYWHERE)));
			}
		}
		return criteria;
	}

	@Override
	protected Class<PaymentClient> getEntityClass() {
		return PaymentClient.class;
	}
	
	

}
