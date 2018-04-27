package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.JxcBaseModel;
import jxc.domain.model.distributor.MileOrder;
import jxc.domain.util.Tools;
import hg.common.component.BaseDao;
import hg.pojo.qo.MileOrderQo;
import hg.pojo.qo.MileOrderQo;

@Repository
public class MileOrderDao extends BaseDao<MileOrder, MileOrderQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, MileOrderQo qo) {
		if (qo != null) {
			criteria.add(Restrictions.eq(JxcBaseModel.PROPERTY_NAME_STATUS_REMOVED, qo.getStatusRemoved()));
			
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

}
