package hsl.app.dao;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.coupon.CouponActivity;
import hsl.pojo.qo.coupon.HslCouponActivityQO;
/**
 * @类功能说明：卡券活动DAO
 * @类修改者：
 * @修改日期：2014年10月15日下午3:46:17
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：wuyg
 * @创建时间：2014年10月15日下午3:46:17
 *
 */
@Repository
public class CouponActivityDao extends BaseDao<CouponActivity, HslCouponActivityQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslCouponActivityQO qo) {
		//活动名称
		if(StringUtils.isNotBlank(qo.getName())){
			criteria.add(Restrictions.ilike("baseInfo.name", qo.getName(),MatchMode.ANYWHERE));
		}
		//卡券类型
		if(qo.getCouponType()>0)
			criteria.add(Restrictions.eq("baseInfo.couponType", qo.getCouponType()));
		//发放方式 1、注册发放 2、订单满送
		if(qo.getIssueWay()>0){
			criteria.add(Restrictions.eq("issueConditionInfo.issueWay", qo.getIssueWay()));
			//判断并查询订单满
			//发放卡券的时候查询>=,查询活动时查询=
			if(qo.getIssueWay()==CouponActivity.ISSUE_WAY_ORDER_OVER_LINE&&qo.getIssueNumLine()>0){
				if(qo.isGreater()){
					criteria.add(Restrictions.le("issueConditionInfo.issueNumLine", qo.getIssueNumLine()));
				}else{
					criteria.add(Restrictions.eq("issueConditionInfo.issueNumLine", qo.getIssueNumLine()));
				}
			}
			criteria.add(Restrictions.ge("issueConditionInfo.issueEndDate", new Date()));
		}
		
		//当前活动状态
		if(qo.getCurrentValue()>0){
			//查询结束的活动时，同时查询取消的活动
			if(CouponActivity.COUPONACTIVITY_STATUS_ACTIVITY_OVER==qo.getCurrentValue()){
				criteria.add(Restrictions.or(Restrictions.eq("status.currentValue", CouponActivity.COUPONACTIVITY_STATUS_ACTIVITY_CANCEL), Restrictions.eq("status.currentValue", CouponActivity.COUPONACTIVITY_STATUS_ACTIVITY_OVER)));
			}else{
				criteria.add(Restrictions.eq("status.currentValue", qo.getCurrentValue()));
			}
		}
		//查询多状态
		if(qo.getStatusTypes()!=null&&qo.getStatusTypes().length>0){
			criteria.add(Restrictions.or(eachEqProperty(qo.getStatusTypes(), "status.currentValue")));
		}
		
		//排序
		if(qo.isOrderbyPriority()){
//			DetachedCriteria subquery=DetachedCriteria.forClass(getEntityClass()).setProjection(Property.forName("issueConditionInfo.priority").max());
//			criteria.add(Property.forName("issueConditionInfo.priority").eq(subquery));
			criteria.addOrder(Order.desc("issueConditionInfo.priority"));
		}else{//发放的开始时间倒序
			criteria.addOrder(Order.desc("issueConditionInfo.issueBeginDate"));
		}
		return criteria;
	}
	@Override
	protected Class<CouponActivity> getEntityClass() {
		return CouponActivity.class;
	}

}
