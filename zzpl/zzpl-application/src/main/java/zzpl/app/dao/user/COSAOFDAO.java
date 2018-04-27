package zzpl.app.dao.user;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import zzpl.domain.model.user.COSAOF;
import zzpl.pojo.qo.user.COSAOFQO;
import hg.common.component.BaseDao;
import hg.common.util.SysProperties;

@Repository
public class COSAOFDAO extends BaseDao<COSAOF, COSAOFQO> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, COSAOFQO qo) {
		if (qo!=null) {
			
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
				return criteria;
			}
			
			if(qo.getPassengerName()!=null&&StringUtils.isNotBlank(qo.getPassengerName())){
				criteria.add(Restrictions.like("passengerName", qo.getPassengerName(),MatchMode.ANYWHERE));
			}
			
			if(qo.getCompanyID()!=null&&StringUtils.isNotBlank(qo.getCompanyID())){
				if (qo.getRoleID().contains(SysProperties.getInstance().get("travleAdminID"))) {
					String[] companys = qo.getCompanyID().split(",");
					criteria.add(Restrictions.in("companyID", companys));
				}else {
					criteria.add(Restrictions.eq("companyID", qo.getCompanyID()));
				}
			}
			
			if (qo.getStatus()!=null&&qo.getStatus()!=0) {
				criteria.add(Restrictions.eq("cosaofStatus", qo.getStatus()));
			}
			
			if (qo.getDepartmentID()!=null&&StringUtils.isNotBlank(qo.getDepartmentID())) {
				criteria.add(Restrictions.eq("departmentID", qo.getDepartmentID()));
			}
			
			if (qo.getOrderID()!=null&&StringUtils.isNotBlank(qo.getOrderID())) {
				criteria.add(Restrictions.eq("orderID", qo.getOrderID()));
			}
			
			if (qo.getStartCreateTime() != null ) {
				criteria.add(Restrictions.ge("createTime", qo.getStartCreateTime()));
			}
			if (qo.getEndCreateTime() != null) {
				criteria.add(Restrictions.le("createTime", qo.getEndCreateTime()));
			}
			
			if(qo.getOrderByCreatTime()!=null&&StringUtils.isNotBlank(qo.getOrderByCreatTime())){
				criteria.addOrder(Order.desc("createTime"));
			}
			
		}
		return criteria;
	}

	@Override
	protected Class<COSAOF> getEntityClass() {
		return COSAOF.class;
	}

}
