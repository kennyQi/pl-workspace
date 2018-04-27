package hg.log.clickrecord;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import hg.log.base.BaseLogDao;
import hg.log.po.clickrecord.ClickRate;

@Repository
public class ClickRateDao extends BaseLogDao<ClickRate,ClickRateQo>{

	@Override
	protected Query buildQuery(Query query, ClickRateQo qo) {
		
		if(StringUtils.isNotBlank(qo.getSceincSpotId())){
			query.addCriteria(Criteria.where("scenicSpotId").is(qo.getSceincSpotId()));
		}
		
		Sort.Order order = new Sort.Order(Direction.DESC , "clickAmount");
		query.with(new Sort(order));
		
		return query;
	}

	@Override
	protected Class<ClickRate> getDocumentClass() {
		// TODO Auto-generated method stub
		return ClickRate.class;
	}

}
