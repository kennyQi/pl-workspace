package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import jxc.domain.model.system.CheckRecord;
import hg.common.component.BaseDao;
import hg.pojo.qo.CheckRecordQo;

@Repository
public class CheckRecordDao extends BaseDao<CheckRecord, CheckRecordQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, CheckRecordQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getBelongToId())) {
				criteria.add(Restrictions.eq("belongTo", qo.getBelongToId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<CheckRecord> getEntityClass() {
		return CheckRecord.class;
	}

}
