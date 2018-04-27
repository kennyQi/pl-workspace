package hg.log.domainevent;

import hg.log.base.BaseLogDao;
import hg.log.po.domainevent.DomainEvent;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class DomainEventDao extends BaseLogDao<DomainEvent, DomainEventQo> {

	@Override
	protected Query buildQuery(Query query, DomainEventQo qo) {
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getModelName())) {
				query.addCriteria(Criteria.where("modelName").is(qo.getModelName()));
			}
			if (StringUtils.isNotBlank(qo.getMethodName())) {
				query.addCriteria(Criteria.where("methodName").is(qo.getMethodName()));
			}
			if (StringUtils.isNotBlank(qo.getParam())) {
				query.addCriteria(Criteria.where("params").regex(escapeLikeParam(qo.getParam())));
			}
			if (qo.getModelVersion() != null && qo.getModelVersion().length > 0) {
				query.addCriteria(Criteria.where("modelVersion").in(
						(Object[]) qo.getModelVersion()));
			}
		}
		return query;
	}

	@Override
	protected Class<DomainEvent> getDocumentClass() {
		return DomainEvent.class;
	}

}
