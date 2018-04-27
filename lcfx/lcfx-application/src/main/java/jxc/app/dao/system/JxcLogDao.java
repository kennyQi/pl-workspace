package jxc.app.dao.system;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import hg.log.base.BaseLogDao;
import hg.pojo.dto.log.JxcLog;
import hg.pojo.qo.JxcLogQo;
@Repository
public class JxcLogDao extends BaseLogDao<JxcLog,JxcLogQo>{
	
	@Override
	protected Query buildQuery(Query query, JxcLogQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getName())) {
				query.addCriteria(new Criteria().orOperator(Criteria.where("operatorAccount").regex(qo.getName()),Criteria.where("operatorName").regex(qo.getName())));
			}
			if (StringUtils.isNotBlank(qo.getUserType())) {
				query.addCriteria(Criteria.where("operatorType").regex(qo.getUserType()));
			}
			if(StringUtils.isNotBlank(qo.getContent())){
				query.addCriteria(Criteria.where("content").regex(qo.getContent()));
			}
		}
		return query;
	}

	@Override
	protected Class<JxcLog> getDocumentClass() {
		return JxcLog.class;
	}

}
