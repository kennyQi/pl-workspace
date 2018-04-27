package zzpl.app.dao.jp;

import java.util.List;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.jp.JPOrder;
import zzpl.pojo.qo.jp.JPOrderQO;

@Repository
public class JPOrderDao extends BaseDao<JPOrder, JPOrderQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria,  JPOrderQO qo) {
		
		if (StringUtils.isNotBlank(qo.getDealerOrderCode())) {
			criteria.add(Restrictions.eq("dealerOrderCode", qo.getDealerOrderCode()));
		}
		//如果有两个值则查询范围，否则查询当天订单
		if (StringUtils.isNotBlank(qo.getBeginDateTime()) && StringUtils.isNotBlank(qo.getEndDateTime())) {
			criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginDateTime()), DateUtil.dateStr2EndDate(qo.getEndDateTime())));
		} else if (StringUtils.isNotBlank(qo.getBeginDateTime())) {
			criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getBeginDateTime()),DateUtil.dateStr2EndDate(qo.getBeginDateTime())));
		} else if (StringUtils.isNotBlank(qo.getEndDateTime())) {
			criteria.add(Restrictions.between("createDate", DateUtil.dateStr2BeginDate(qo.getEndDateTime()),DateUtil.dateStr2EndDate(qo.getEndDateTime())));
		}
		
//		//根据航班起飞时间查询订单          
//		if (StringUtils.isNotBlank(qo.getFlightStartTime()) && StringUtils.isNotBlank(qo.getFlightEndTime())) {
//			criteria.add(Restrictions.between("flightStartTime", DateUtil.dateStr2BeginDate(qo.getFlightStartTime()), DateUtil.dateStr2EndDate(qo.getFlightEndTime())));
//		} else if (StringUtils.isNotBlank(qo.getFlightStartTime())) {
//			criteria.add(Restrictions.between("flightStartTime", DateUtil.dateStr2BeginDate(qo.getFlightStartTime()), DateUtil.dateStr2EndDate(qo.getFlightStartTime())));
//		} else if (StringUtils.isNotBlank(qo.getFlightEndTime())) {
//			criteria.add(Restrictions.between("flightStartTime", DateUtil.dateStr2BeginDate(qo.getFlightEndTime()), DateUtil.dateStr2EndDate(qo.getFlightEndTime())));
//		}
		
		if (StringUtils.isNotBlank(qo.getFlightStartTime()) && StringUtils.isNotBlank(qo.getFlightEndTime())) {
			criteria.add(Restrictions.between("flightStartTime", qo.getFlightStartTime()+" 00:00:00", qo.getFlightEndTime()+" 23:59:59"));
		} else if (StringUtils.isNotBlank(qo.getFlightStartTime())) {
			criteria.add(Restrictions.between("flightStartTime", qo.getFlightStartTime()+" 00:00:00", qo.getFlightStartTime()+" 23:59:59"));
		} else if (StringUtils.isNotBlank(qo.getFlightEndTime())) {
			criteria.add(Restrictions.between("flightStartTime", qo.getFlightEndTime()+" 00:00:00", qo.getFlightEndTime()+" 23:59:59"));
		}
		
		
		if (qo.getStatus() != null) {
			criteria.add(Restrictions.eq("status", qo.getStatus()));
		}
		if (qo.getPayStatus() != null) {
			criteria.add(Restrictions.eq("payStatus", qo.getPayStatus()));
		}
		
		if (qo.getSts() != null && qo.getSts().length > 0) {
			criteria.add(Restrictions.in("status", qo.getSts()));
		}
		
		if (qo.getPaySts() != null && qo.getPaySts().length > 0) {
			criteria.add(Restrictions.in("payStatus", qo.getPaySts()));
		}
		
		if (StringUtils.isNotBlank(qo.getPnr())) {
			criteria.add(Restrictions.eq("pnr", qo.getPnr()));
		}
		
		if (StringUtils.isNotBlank(qo.getActName())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.ilike("passanger.name", qo.getActName(),MatchMode.ANYWHERE));
		}
		
		if (StringUtils.isNotBlank(qo.getUserId())) {
			criteria.add(Restrictions.eq("orderUser.userId", qo.getUserId()));
		}
		
		if (StringUtils.isNotBlank(qo.getUserId())) {
			criteria.add(Restrictions.eq("orderUser.userId", qo.getUserId()));
			if(qo.isShowMember()){
				//查询成员
				criteria.createAlias("passangers", "passanger");
				criteria.add(Restrictions.isNotNull("passanger.memeberId"));
			}
		}
		
		if (StringUtils.isNotBlank(qo.getLoginName())) {
			criteria.add(Restrictions.eq("orderUser.loginName", qo.getLoginName()));
		}
		
		if (qo.getPageNo() != null && qo.getPageSize() != null) {
			criteria.setFirstResult((qo.getPageNo() - 1) * qo.getPageSize());
			criteria.setMaxResults(qo.getPageSize());
		}else{
			criteria.setFirstResult(0);
			criteria.setMaxResults(15);
		}
		
		if (StringUtils.isNotBlank(qo.getCompanyId())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.like("passanger.companyId", qo.getCompanyId()));
		}
		
		if (StringUtils.isNotBlank(qo.getCompanyName())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.like("passanger.companyName", qo.getCompanyName()));
		}
		
		if (StringUtils.isNotBlank(qo.getDepartmentId())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.like("passanger.departmentId", qo.getDepartmentId()));
		}
		
		if (StringUtils.isNotBlank(qo.getDepartmentName())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.like("passanger.departmentName", qo.getDepartmentName()));
		}
		
		if (StringUtils.isNotBlank(qo.getMemeberId())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.like("passanger.memeberId", qo.getMemeberId()));
		}
		
		if (StringUtils.isNotBlank(qo.getCardNo())) {
			criteria.createAlias("passangers", "passanger");
			criteria.add(Restrictions.eq("passanger.cardNo", qo.getCardNo()));
		}
		
		//根据第三方支付单号查询
		if(StringUtils.isNotBlank(qo.getPayTradeNo())){
			criteria.add(Restrictions.eq("payTradeNo", qo.getPayTradeNo()));
		}
		
		//根据商城订单号排序
		criteria.addOrder(Order.desc("dealerOrderCode"));
		
		//航班号
		if(StringUtils.isNotBlank(qo.getFlightNo())){
			criteria.add(Restrictions.like("jpOrderSnapshot", qo.getFlightNo(),MatchMode.ANYWHERE));
		}
		return criteria;
	}

	@Override
	protected Class<JPOrder> getEntityClass() {

		return JPOrder.class;
	}

	@Override
	public List<JPOrder> queryList(JPOrderQO qo) {
		if(qo != null && qo.getPageNo() != null && qo.getPageSize() != null){
			return queryList(qo, (qo.getPageNo() - 1)*qo.getPageSize(),qo.getPageSize());			
		}else{
			return queryList(qo, 0, null);			
		}
	}
}
