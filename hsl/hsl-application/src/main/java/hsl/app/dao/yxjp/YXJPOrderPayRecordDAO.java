package hsl.app.dao.yxjp;

import hg.common.component.BaseDao;
import hg.common.util.MyBeanUtils;
import hsl.domain.model.yxjp.YXJPOrderPassenger;
import hsl.domain.model.yxjp.YXJPOrderPayRecord;
import hsl.pojo.qo.yxjp.YXJPOrderPayRecordQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Subqueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * 易行机票订单支付记录DAO
 *
 * @author zhurz
 * @since 1.7
 */
@Repository("yxjpOrderPayRecordDAO")
public class YXJPOrderPayRecordDAO extends BaseDao<YXJPOrderPayRecord, YXJPOrderPayRecordQO> {

	@Autowired
	private YXJPOrderPassengerDAO passengerDAO;

	@Override
	protected Criteria buildCriteria(Criteria criteria, YXJPOrderPayRecordQO qo) {

		// 乘客信息（子查询）
		if (qo.getPassengerQO() != null) {
			DetachedCriteria dc = DetachedCriteria.forClass(YXJPOrderPassenger.class, "p");
			dc.setProjection(Projections.property("p.id"));
			dc.add(Property.forName("p.fromOrder.id").eqProperty((StringUtils.isBlank(qo.getAlias()) ? Criteria.ROOT_ALIAS : qo.getAlias()) + ".id"));
			Criteria passengerCriteria = (Criteria) MyBeanUtils.getFieldValue(dc, "criteria");
			passengerDAO.buildCriteriaOut(passengerCriteria, qo.getPassengerQO());
			criteria.add(Subqueries.exists(dc));
		}

		return criteria;
	}

	@Override
	protected Class<YXJPOrderPayRecord> getEntityClass() {
		return YXJPOrderPayRecord.class;
	}
}
