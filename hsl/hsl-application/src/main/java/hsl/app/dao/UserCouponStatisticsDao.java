package hsl.app.dao;
import hg.common.component.BaseDao;
import hsl.domain.model.coupon.UserCouponStatistics;
import hsl.pojo.qo.coupon.HslUserCouponStatisticsQO;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * @类功能说明：卡券的dao
 * @类修改者：
 * @修改日期：2014年10月15日下午3:31:53
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：chenxy
 * @创建时间：2014年10月15日下午3:31:53
 *
 */
@Repository
public class UserCouponStatisticsDao extends BaseDao<UserCouponStatistics,HslUserCouponStatisticsQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslUserCouponStatisticsQO qo) {
		//卡券编号
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserId())){
				criteria.add(Restrictions.eq("userId", qo.getUserId()));
			}
			if(StringUtils.isNotBlank(qo.getCouponActivityId())){
				criteria.add(Restrictions.eq("couponActivityId", qo.getCouponActivityId()));
			}
			if(StringUtils.isNotBlank(qo.getGetCouponTime())){
				criteria.add(Restrictions.eq("getCouponTime", qo.getCouponActivityId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<UserCouponStatistics> getEntityClass() {
		return UserCouponStatistics.class;
	}
}
