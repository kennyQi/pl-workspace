package pl.app.dao;
import hg.common.component.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import pl.cms.domain.entity.contribution.ContributionRecord;
import pl.cms.pojo.qo.ContributionQO;
@Repository
public class ContributionRecordDao extends BaseDao<ContributionRecord, ContributionQO> {
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ContributionQO qo) {
		
		if (qo != null) {
			if(StringUtils.isNotBlank(qo.getContributionId())){
				criteria.add(Restrictions.eq("contribution.id",qo.getContributionId()));
			}
		}
		return criteria;
	}

	@Override
	protected Class<ContributionRecord> getEntityClass() {
		return ContributionRecord.class;
	}
	
}
