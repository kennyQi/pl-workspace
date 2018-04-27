package hsl.app.dao;

import hg.common.component.BaseDao;
import hg.common.page.Pagination;
import hsl.domain.model.coupon.Coupon;
import hsl.domain.model.coupon.CouponStatus;
import hsl.domain.model.coupon.CouponTransferRecord;
import hsl.pojo.qo.coupon.HslCouponQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @类功能说明：卡券的dao
 * @类修改者：
 * @修改日期：2014年10月15日下午3:31:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午3:31:53
 */
@Repository
public class CouponDao extends BaseDao<Coupon, HslCouponQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslCouponQO qo) {
		//卡券编号
		if (qo != null) {
			
			Criteria activityC = criteria.createCriteria("baseInfo.couponActivity", JoinType.LEFT_OUTER_JOIN);

//			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			if (StringUtils.isNotBlank(((HslCouponQO) qo).getId())) {
				//判断卡券编号是否是模糊查询
				if (qo.getIdLike()) {
					criteria.add(Restrictions.like("id", qo.getId(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("id", qo.getId()));
				}
			}
			//卡券编号模糊查询
			if (StringUtils.isNotBlank(((HslCouponQO) qo).getCouponId())) {
				//判断卡券编号是否是模糊查询
				if (qo.getIdLike()) {
					criteria.add(Restrictions.like("id", qo.getCouponId(), MatchMode.ANYWHERE));
				} else {
					criteria.add(Restrictions.eq("id", qo.getCouponId()));
				}
			}
			//发放渠道：1、注册发放   2、订单满送
			if (qo.getIssueWay() > 0) {
				if (activityC == null) {
					activityC = criteria.createCriteria("baseInfo.couponActivity", JoinType.LEFT_OUTER_JOIN);
				}
				activityC.add(Restrictions.eq("issueConditionInfo.issueWay", qo.getIssueWay()));
			}
			//卡号状态:1、未使用；2、已使用；3、已过期；4、已废弃
			if (qo.getCurrentValues() != null && qo.getCurrentValues().length > 0) {

				List<Integer> status = new ArrayList<Integer>();
				for (int s : qo.getCurrentValues())
					status.add(s);

				if (qo.getCurrentValues().length == 1) {
					criteria.add(Restrictions.eq("status.currentValue", qo.getCurrentValues()[0]));
				} else {
					criteria.add(Restrictions.in("status.currentValue", status));
				}

				//卡券未使用时订单快照为空(且该卡券 不是 处于 被占用 状态，占用状态是 临界于点击支付后但是没有支付成功或者取消支付时的一种状态)
				if (status.contains(1) && !qo.isOccupy()) {
					criteria.add(Restrictions.isNull("consumeOrder"));
				}
			}
			if (qo.getStatusTypes().length > 0) {
				//根据PC端需求需要把已过期和已作废放在一起
				if (qo.getStatusTypes().length == 1 && (Integer) qo.getStatusTypes()[0] == CouponStatus.TYPE_NOUSED) {
					criteria.add(Restrictions.eq("status.currentValue", CouponStatus.TYPE_NOUSED));
					criteria.add(Restrictions.isNull("consumeOrder"));
				} else {
					Criterion criterion1 = Restrictions.or(eachEqProperty(qo.getStatusTypes(), "status.currentValue"));
					if (Arrays.asList(qo.getStatusTypes()).indexOf(CouponStatus.TYPE_ISUSED) >= 0) {
						Criterion criterion2 = Restrictions.isNotNull("consumeOrder");
						criteria.add(Restrictions.or(criterion1, criterion2));
					} else {
						criteria.add(criterion1);
					}
				}
			}
			//账户名查询
			if (StringUtils.isNotBlank(qo.getLoginName())) {
				//判断用户名是否模糊查询
				if (qo.getLoginNameLike()) {
					Criteria criteria2 = criteria.createCriteria("holdingUser", JoinType.LEFT_OUTER_JOIN);
					criteria2.add(Restrictions.like("loginName", qo.getLoginName(), MatchMode.ANYWHERE));
				} else {
					Criteria criteria2 = criteria.createCriteria("holdingUser", JoinType.LEFT_OUTER_JOIN);
					criteria2.add(Restrictions.eq("loginName", qo.getLoginName()));
				}
			}
			//根据用户ID 查询
			if (StringUtils.isNotBlank(qo.getUserId())) {
				Criteria criteria2 = criteria.createCriteria("holdingUser", JoinType.LEFT_OUTER_JOIN);
				criteria2.add(Restrictions.like("userId", qo.getUserId()));
			}
			if (qo.getEventType() > 0) {
				
			}
			//查询订单快照的订单id
			if (StringUtils.isNotBlank(qo.getOrderId())) {
				if (qo.isUseEvent()) {
					//查询顺序：订单id->订单快照(按照创建时间倒序)->卡券事件->卡券，
					criteria.createCriteria("eventList", JoinType.LEFT_OUTER_JOIN).addOrder(Order.desc("occurrenceTime"))
							.createCriteria("consumeOrder", JoinType.LEFT_OUTER_JOIN)
							.add(Restrictions.eq("orderId", qo.getOrderId()));
				} else {
					Criteria criteria2 = criteria.createCriteria("consumeOrder", JoinType.LEFT_OUTER_JOIN);
					criteria2.add(Restrictions.eq("orderId", qo.getOrderId()));
				}
				
			}
			//根据订单价格查询可用卡券
			if (qo.getOrderPrice() > 0) {
				if (activityC == null) {
					activityC = criteria.createCriteria("baseInfo.couponActivity", JoinType.LEFT_OUTER_JOIN);
				}
				activityC.add(Restrictions.le("useConditionInfo.minOrderPrice", qo.getOrderPrice()));
//				//使用条件 0、不限	1、订单满多少可用
//				if(qo.getCondition()==1){
//					activityC.add(Restrictions.eq("useConditionInfo.condition", qo.getCondition()));
//					
//				}
			}
			//不查询已过期的卡券
			if (qo.isOverdue() == true) {
				if (activityC == null) {
					activityC = criteria.createCriteria("baseInfo.couponActivity", JoinType.LEFT_OUTER_JOIN);
				}
				activityC.add(Restrictions.gt("useConditionInfo.endDate", new Date()));
			}
			if (qo.isFromH5()) {
				if (activityC == null) {
					activityC = criteria.createCriteria("baseInfo.couponActivity", JoinType.LEFT_OUTER_JOIN);
				}
				activityC.addOrder(Order.desc("baseInfo.faceValue"));
				activityC.addOrder(Order.asc("useConditionInfo.endDate"));
			} else {
				criteria.addOrder(Order.desc("baseInfo.createDate"));
			}

			// 有转赠记录
			if (qo.getHasTransferRecord() != null && qo.getHasTransferRecord()) {
				criteria.add(Restrictions.ge("status.alreadySendTimes", 1));
			}

			// 转赠来自的账户
			if (StringUtils.isNotBlank(qo.getFromLoginName())) {
				DetachedCriteria recordCriteria = DetachedCriteria.forClass(CouponTransferRecord.class, "r");
				String alias = StringUtils.isNotBlank(qo.getAlias()) ? qo.getAlias() : Criteria.ROOT_ALIAS;
				recordCriteria.add(Restrictions.eqProperty("r.couponId", alias + ".id"));
				recordCriteria.createCriteria("fromUser").add(Restrictions.like("loginName", qo.getFromLoginName(), MatchMode.ANYWHERE));
				recordCriteria.setProjection(Projections.property("r.id"));
				criteria.add(Subqueries.exists(recordCriteria));
			}

			// 使用种类
			if (StringUtils.isNotBlank(qo.getUsedKind())) {
				activityC.add(Restrictions.like("useConditionInfo.usedKind", qo.getUsedKind(), MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	protected Class<Coupon> getEntityClass() {
		return Coupon.class;
	}

	@Override
	public List<Coupon> queryList(HslCouponQO qo, Integer offset,
								  Integer maxSize) {
		List<Coupon> list = super.queryList(qo, offset, maxSize);
		for (Coupon coupon : list) {
			Hibernate.initialize(coupon.getEventList());
		}
		return list;
	}

	@Override
	public Pagination queryPagination(Pagination pagination) {
		Pagination pagination2 = super.queryPagination(pagination);
		@SuppressWarnings("unchecked")
		List<Coupon> list = (List<Coupon>) pagination2.getList();
		for (Coupon coupon : list) {
			Hibernate.initialize(coupon.getEventList());
		}
		return pagination2;
	}

	public Coupon getCouponByMaxTime(HslCouponQO qo) {
		Integer eventType = qo.getEventType();
		Coupon c = null;
		if (eventType > 0) {
			Object co = this.findUnique(" from Coupon co  join co.eventList as ev  where  ev.occurrenceTime  in ( select max(e.occurrenceTime) from Coupon c join c.eventList as e where e.eventType=?)", eventType);
			//查询出来是Coupon和eventlist的数组
			Object[] c1 = (Object[]) co;
			//		Object co= this.findUnique("select max(e.occurrenceTime) from Coupon c join c.eventList as e");
			//		Object co= this.findUnique("select max(e.occurrenceTime), from CouponEvent e join ");
			//		Object co= this.findUnique("select max(occurrenceTime) from CouponEvent");]
			c = (Coupon) c1[0];
		}
		return c;
	}

}
