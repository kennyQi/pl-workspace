package hsl.app.dao.log;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import hg.log.base.BaseLogDao;
import hsl.pojo.log.PLZXClickRecord;
import hsl.pojo.qo.log.PLZXClickRecordQo;

@Repository
public class PLZXClickRecordDao extends BaseLogDao<PLZXClickRecord,PLZXClickRecordQo>{

	@Override
	protected Query buildQuery(Query query, PLZXClickRecordQo qo) {
		if(qo!=null){
			if(StringUtils.isNotBlank(qo.getUserId())){
				query.addCriteria(Criteria.where("userId").is(qo.getUserId()));
			}
			if(StringUtils.isNotBlank(qo.getObjectId())){
				query.addCriteria(Criteria.where("objectId").is(qo.getObjectId()));
			}
			if(qo.getObjectType()!=null&&qo.getObjectType()>0){
				query.addCriteria(Criteria.where("objectType").is(qo.getObjectType()));
			}
		}
		return query;
	}

	@Override
	protected Class<PLZXClickRecord> getDocumentClass() {
		return PLZXClickRecord.class;
	}

}
