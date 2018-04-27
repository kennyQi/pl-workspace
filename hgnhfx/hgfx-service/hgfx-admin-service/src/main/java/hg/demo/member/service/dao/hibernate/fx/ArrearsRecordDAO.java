package hg.demo.member.service.dao.hibernate.fx;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import hg.demo.member.service.qo.hibernate.fx.ArrearsRecordQO;
import hg.framework.service.component.base.hibernate.BaseHibernateDAO;
import hg.fx.domain.ArrearsRecord;

/**
 * @author cangs
 */
@Repository("arrearsRecordDAO")
public class ArrearsRecordDAO extends BaseHibernateDAO<ArrearsRecord, ArrearsRecordQO>{

	@Override
	protected Class<ArrearsRecord> getEntityClass() {
		return ArrearsRecord.class;
	}

	@Override
	protected void queryEntityComplete(ArrearsRecordQO qo,
			List<ArrearsRecord> list) {
		
	}

	@Override
	protected Criteria buildCriteria(Criteria criteria, ArrearsRecordQO qo) {
		if(StringUtils.isNotBlank(qo.getOrderWay())){
			if(ArrearsRecordQO.ORDERWAY_1.equals(qo.getOrderWay())){
			//先按先按照审核状态降序排序,再按照申请时间排序
			criteria.addOrder(Order.desc("checkStatus"));
			criteria.addOrder(Order.desc("applyDate"));
			}
		}
		return criteria;
	}
}
