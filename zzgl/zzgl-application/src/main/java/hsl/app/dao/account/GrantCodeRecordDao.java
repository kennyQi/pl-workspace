package hsl.app.dao.account;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hg.common.util.DateUtil;
import hsl.domain.model.user.account.GrantCodeRecord;
import hsl.domain.model.user.account.PayCode;
import hsl.pojo.qo.account.GrantCodeRecordQO;

/**
 * 
 * @类功能说明：充值码发放
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-8-31上午10:39:54
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
@Repository
public class GrantCodeRecordDao extends BaseDao<GrantCodeRecord,GrantCodeRecordQO>{
	@Override
	protected Criteria buildCriteria(Criteria criteria, GrantCodeRecordQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getId())){
				criteria.add(Restrictions.eq("id", qo.getId()));
			}
			if(qo.getStatus()!=null&&qo.getStatus()>0){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			//发码时间
			if(StringUtils.isNotBlank(qo.getBeginTime())&&StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.between("createTime",DateUtil.dateStr2BeginDate(qo.getBeginTime()), DateUtil.dateStr2EndDate(qo.getEndTime())));
			}else if(StringUtils.isNotBlank(qo.getBeginTime())){
				criteria.add(Restrictions.ge("createTime", DateUtil.dateStr2BeginDate(qo.getBeginTime())));
			}else if(StringUtils.isNotBlank(qo.getEndTime())){
				criteria.add(Restrictions.lt("createTime", DateUtil.dateStr2EndDate(qo.getEndTime())));
			}
			
			if(StringUtils.isNotBlank(qo.getCompanyLinkName())){
				criteria.createAlias("businessPartners", "businessPartners");
				criteria.add(Restrictions.eq("businessPartners.companyLinkName", qo.getCompanyLinkName()));
			}
			if(StringUtils.isNotBlank(qo.getCompanyLinkTel())){
				criteria.createAlias("businessPartners", "businessPartners");
				criteria.add(Restrictions.eq("businessPartners.companyLinkTel", qo.getCompanyLinkTel()));
			}
			if(StringUtils.isNotBlank(qo.getCompanyName())){
				criteria.createAlias("businessPartners", "businessPartners");
				criteria.add(Restrictions.eq("businessPartners.companyName", qo.getCompanyName()));
			}
			
			if(StringUtils.isNotBlank(qo.getCompanyId())){
				//criteria.createAlias("businessPartners", "businessPartners");
				criteria.add(Restrictions.eq("businessPartners.id", qo.getCompanyId()));;
			}
			
			
			criteria.addOrder(Order.desc("createTime"));
		}
		return criteria;
	}

	@Override
	protected Class<GrantCodeRecord> getEntityClass() {
		return GrantCodeRecord.class;
	}

	@Override
	public GrantCodeRecord queryUnique(GrantCodeRecordQO qo) {
		GrantCodeRecord record=super.queryUnique(qo);
		if(record!=null){
			for(PayCode payCode:record.getPayCodes()){
				Hibernate.initialize(payCode);
			}
		}
		return record;
	}
}
