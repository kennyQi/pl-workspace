package hsl.app.dao;

import hg.common.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import hg.common.component.BaseDao;
import hsl.domain.model.event.SMSValidateRecord;
import hsl.pojo.qo.user.HslSMSCodeQO;

@Repository
public class SMSCodeDao extends BaseDao<SMSValidateRecord, HslSMSCodeQO>{

	@Override
	protected Criteria buildCriteria(Criteria criteria, HslSMSCodeQO qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getSendDate())){
				criteria.add(Restrictions.between("sendDate", DateUtil.dateStr2BeginDate(qo.getSendDate()), DateUtil.dateStr2EndDate(qo.getSendDate())));
			}
			if(qo.getStatus()!=null){
				criteria.add(Restrictions.eq("status", qo.getStatus()));
			}
			if(StringUtils.isNotBlank(qo.getMobile())){
				criteria.add(Restrictions.eq("mobile", qo.getMobile()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<SMSValidateRecord> getEntityClass() {
		return SMSValidateRecord.class;
	}

}
