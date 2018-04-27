package hsl.app.dao;

import hg.common.component.BaseDao;
import hsl.domain.model.coupon.CouponActivityEvent;
import hsl.pojo.qo.coupon.HslCouponActivityEventQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：2014年10月15日下午5:54:15
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午5:54:15
 *
 */
@Repository
public class CouponActivityEventDao extends BaseDao<CouponActivityEvent, HslCouponActivityEventQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslCouponActivityEventQO qo) {
		if(StringUtils.isNotBlank(qo.getCouponActivityId()))
			criteria.add(Restrictions.eq("couponActivityId", qo.getCouponActivityId()));
		if(qo.getEventType()==0||qo.getEventType()==CouponActivityEvent.EVENT_TYPE_CANCELED){
			criteria.add(Restrictions.eq("eventType", CouponActivityEvent.EVENT_TYPE_CANCELED));
		}else{
			criteria.add(Restrictions.eq("eventType", qo.getEventType()));
		}
		return criteria;
	}

	@Override
	protected Class<CouponActivityEvent> getEntityClass() {
		return CouponActivityEvent.class;
	}

}
