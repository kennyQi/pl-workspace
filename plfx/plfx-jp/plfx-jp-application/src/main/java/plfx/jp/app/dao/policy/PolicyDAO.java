package plfx.jp.app.dao.policy;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import plfx.jp.domain.model.policy.JPPlatformPolicy;
import plfx.jp.qo.admin.policy.PolicyQO;
/**
 * 
 * @类功能说明：政策DAO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:24:01
 * @版本：V1.0
 *
 */
@Repository
public class PolicyDAO extends BaseDao<JPPlatformPolicy, PolicyQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, PolicyQO qo) {
		
		if (qo != null) {
			
			// 过滤取消的订单
//			criteria.add(Restrictions.ne("status", PolicyConstants.CANCEL));
			// 供应商
			if (StringUtils.isNotBlank(qo.getSuppId())) {
				criteria.add(Restrictions.eq("supplier.id", qo.getSuppId()));
			}
			if (StringUtils.isNotBlank(qo.getSuppCode())) {
				criteria.add(Restrictions.eq("supplier.code", qo.getSuppCode()));
			}
			// 适用经销商
			if (StringUtils.isNotBlank(qo.getDealerId())) {
				criteria.add(Restrictions.eq("dealer.id", qo.getDealerId()));
			}
			if (StringUtils.isNotBlank(qo.getDealerCode())) {
				criteria.add(Restrictions.eq("dealer.code", qo.getDealerCode()));
			}
			// 政策名称
			if (StringUtils.isNotBlank(qo.getName())) {
				criteria.add(Restrictions.like("name", qo.getName(), MatchMode.ANYWHERE));
			}
			// 政策状态
			if (StringUtils.isNotBlank(qo.getStatus())) {
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			// 创建人
			if (StringUtils.isNotBlank(qo.getCreatePersion())) {
				criteria.add(Restrictions.like("createPersion", qo.getCreatePersion(), MatchMode.ANYWHERE));
			}
			// 生效时间
			if(qo.getBeginTime()!=null){
				criteria.add(Restrictions.ge("beginTime", qo.getBeginTime()));
			}
			if(qo.getEndTime()!=null){
				criteria.add(Restrictions.le("endTime", qo.getEndTime()));
			}	
			
			if (null != qo.getCurrentTime()) {
				criteria.add(Restrictions.le("beginTime", qo.getCurrentTime()));
				criteria.add(Restrictions.ge("endTime", qo.getCurrentTime()));
			}
			
			/*if (null != qo.getBeginTime() && null != qo.getEndTime()) {
				Restrictions.not(
						Restrictions.or(
							Restrictions.ge("beginTime", qo.getBeginTime()),
							Restrictions.le("endTime", qo.getBeginTime())
						)
					);
			} else if (null != qo.getBeginTime()) {
				criteria.add(Restrictions.ge("endTime", qo.getBeginTime()));
			} else if (null != qo.getEndTime()) {
				criteria.add(Restrictions.le("beginTime", qo.getEndTime()));
			}*/
			
			if (null != qo.getSortCreateTime()) {
				if (qo.getSortCreateTime()) {
					criteria.addOrder(Order.desc("createTime"));
				} else {
					criteria.addOrder(Order.asc("createTime"));
				}
				
			}
			
		}
		
		return criteria;
	}

	@Override
	protected Class<JPPlatformPolicy> getEntityClass() {
		return JPPlatformPolicy.class;
	}

}