package hg.log.clickrecord;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import hg.log.base.BaseLogDao;
import hg.log.po.clickrecord.ClickRecord;

@Repository
public class ClickRecordDao extends BaseLogDao<ClickRecord,ClickRecordQo>{

	@Override
	protected Query buildQuery(Query query, ClickRecordQo qo) {

		if(qo!=null){
			
			if(StringUtils.isNotBlank(qo.getUserId())){
				query.addCriteria(Criteria.where("userId").is(qo.getUserId()));
			}
			
			if(StringUtils.isNotBlank(qo.getScenicSpotId())){
				query.addCriteria(Criteria.where("scenicSpotId").is(qo.getScenicSpotId()));
			}
			
		}


		
		return query;
	}

	@Override
	protected Class<ClickRecord> getDocumentClass() {
		// TODO Auto-generated method stub
		return ClickRecord.class;
	}

}
