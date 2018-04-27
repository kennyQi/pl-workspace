package hg.system.dao;

import hg.common.component.BaseDao;
import hg.system.cache.MetaTagCacheManager;
import hg.system.model.seo.MetaTag;
import hg.system.qo.MetaTagQo;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MetaTagDao extends BaseDao<MetaTag, MetaTagQo> {

	@Override
	protected Criteria buildCriteria(Criteria criteria, MetaTagQo qo) {
		if (qo == null)
			return criteria;
		// 请求地址(模糊匹配)
		if (StringUtils.isNotBlank(qo.getUri()))
			criteria.add(Restrictions.like("uri", qo.getUri(), MatchMode.ANYWHERE));
		// 标题(模糊匹配)
		if (StringUtils.isNotBlank(qo.getTitle()))
			criteria.add(Restrictions.like("title", qo.getTitle(), MatchMode.ANYWHERE));
		// 关键词(模糊匹配)
		if (StringUtils.isNotBlank(qo.getKeywords()))
			criteria.add(Restrictions.like("keywords", qo.getKeywords(), MatchMode.ANYWHERE));
		// 描述(模糊匹配)
		if (StringUtils.isNotBlank(qo.getDescription()))
			criteria.add(Restrictions.like("description", qo.getDescription(), MatchMode.ANYWHERE));
		return criteria;
	}

	@Override
	protected Class<MetaTag> getEntityClass() {
		return MetaTag.class;
	}
	
	@Autowired
	private MetaTagCacheManager cacheManager;

	@Override
	public void save(MetaTag entity) {
		cacheManager.refreshMetaTag(entity);
		super.save(entity);
	}

	@Override
	public void update(MetaTag entity) {
		cacheManager.refreshMetaTag(entity);
		super.update(entity);
	}

	
}
