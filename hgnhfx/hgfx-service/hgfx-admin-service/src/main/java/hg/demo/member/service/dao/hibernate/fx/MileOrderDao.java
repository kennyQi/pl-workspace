package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import hg.demo.member.service.qo.hibernate.fx.MileOrderQo;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.MileOrder;
import hg.fx.util.Tools;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class MileOrderDao extends BaseHibernateDAO<MileOrder, MileOrderQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, MileOrderQo qo) {
		if (qo != null) {
			
			if (StringUtils.isNotBlank(qo.getDistributorId())) {
				criteria.add(Restrictions.eq("distributor.id", qo.getDistributorId()));
			}
			if (StringUtils.isNotBlank(qo.getCsairCard())) {
				criteria.add(Restrictions.like("csairCard", qo.getCsairCard(),MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(qo.getCsairName())) {
				criteria.add(Restrictions.like("csairName", qo.getCsairName(),MatchMode.ANYWHERE));
			}
			if (StringUtils.isNotBlank(qo.getOrderCode())) {				
				if (qo.getQueryOrderCodeLike()) {
					criteria.add(Restrictions.like("orderCode", qo.getOrderCode(),MatchMode.ANYWHERE));
				}else {
					criteria.add(Restrictions.like("orderCode", qo.getOrderCode()));
				}
			}
			if (qo.getStatus() != null) {
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if (qo.getSendStatus() != null) {
				criteria.add(Restrictions.eq("sendStatus", qo.getSendStatus()));
			}
			
			Tools.DateQueryProcess("payDate", criteria, qo.getPayDateStart(), qo.getPayDateEnd(), null);
			Tools.DateQueryProcess("importDate", criteria, qo.getImportDateStart(), qo.getImportDateEnd(), null);

		}
		criteria.addOrder(Order.desc("importDate"));

		return criteria;
	}

	@Override
	protected Class<MileOrder> getEntityClass() {
		return MileOrder.class;
	}

	@Override
	protected void queryEntityComplete(MileOrderQo arg0, List<MileOrder> arg1) {
		
	}

}
