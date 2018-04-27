package pl.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.ArticleChannelDao;
import pl.app.dao.ArticleDao;
import pl.cms.domain.entity.article.ArticleChannel;
import pl.cms.pojo.qo.ArticleChannelQO;
import hg.common.component.BaseServiceImpl;

@Service
@Transactional
public class ArticleChannelServiceImpl extends
		BaseServiceImpl<ArticleChannel, ArticleChannelQO, ArticleChannelDao> {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleChannelDao articleChannelDao;

	@Override
	protected ArticleChannelDao getDao() {
		return articleChannelDao;
	}

}
