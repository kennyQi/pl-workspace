package pl.app.dao;
import hg.common.component.BaseDao;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import pl.cms.domain.entity.article.Article;
import pl.cms.pojo.qo.ArticleQO;
@Repository
public class ArticleDao extends BaseDao<Article, ArticleQO> {

	@Autowired
	private ArticleChannelDao channelDao;
	
	@Override
	protected Criteria buildCriteria(Criteria criteria, ArticleQO qo) {
		
		if (qo != null) {
			if (qo.getFetchChannel() || StringUtils.isNotBlank(qo.getShowChannelId())) {
				Criteria channelsCriteria = criteria.createCriteria("showChannels", "channels",JoinType.LEFT_OUTER_JOIN);
				if (StringUtils.isNotBlank(qo.getShowChannelId())) {
					channelsCriteria.add(Restrictions.eq("id", qo.getShowChannelId()));
				}
			}
		}
		return criteria;
	}

	@Override
	protected Class<Article> getEntityClass() {
		return Article.class;
	}

}
