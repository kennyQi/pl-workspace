package hsl.app.dao.account;
import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.user.account.PayCode;
import hsl.pojo.qo.account.PayCodeQO;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
/**
 * @方法功能说明：充值码发放
 * @创建者名字：zhaows
 * @创建时间：2015-8-31上午10:22:31
 * @参数：@param args
 * @return:void
 * @throws
 */
@Repository
public class PayCodeDao extends BaseDao<PayCode, PayCodeQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, PayCodeQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getCode())){
				criteria.add(Restrictions.eq("code", qo.getCode()));
			}
			if(qo.getType()==PayCodeQO.PAYCODE_CZ){//
				criteria.add(Restrictions.isNotNull("holdUserSnapshot"));
			}
			if(StringUtils.isNotBlank(qo.getBeginTime())&&StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("rechargeDate",DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}else if(StringUtils.isNotBlank(qo.getBeginTime())){
				criteria.add(Restrictions.ge("rechargeDate", DateUtil.dateStr2BeginDate(qo.getBeginTime())));
			}else if(StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.lt("rechargeDate", DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			if(StringUtils.isNotBlank(qo.getUserName())){
				criteria.createAlias("holdUserSnapshot", "holdUserSnapshot");
				criteria.add(Restrictions.like("holdUserSnapshot.loginName", qo.getUserName(),MatchMode.ANYWHERE));
			}
			if(StringUtils.isNotBlank(qo.getGrantCodeRecordID())){
				criteria.createAlias("grantCodeRecord", "grantCodeRecord");
				criteria.add(Restrictions.eq("grantCodeRecord.id", qo.getGrantCodeRecordID()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<PayCode> getEntityClass() {
		return PayCode.class;
	}







}
