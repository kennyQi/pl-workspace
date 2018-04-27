package pl.app.dao;

import hg.common.component.BaseDao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import pl.cms.domain.entity.article.ArticleChannel;
import pl.cms.pojo.qo.ArticleChannelQO;


@Repository
public class ArticleChannelDao extends BaseDao<ArticleChannel, ArticleChannelQO> {

	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ArticleChannelQO qo) {
		
		if (qo != null) {
			if (StringUtils.isNotBlank(qo.getArticleId())) {
			}
		}
		return criteria;
	}

	@Override
	protected Class<ArticleChannel> getEntityClass() {
		return ArticleChannel.class;
	}

}
