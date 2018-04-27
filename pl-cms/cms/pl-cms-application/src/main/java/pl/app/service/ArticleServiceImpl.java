package pl.app.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.app.dao.ArticleChannelDao;
import pl.app.dao.ArticleDao;
import pl.app.dao.CommentDao;
import pl.cms.domain.entity.article.Article;
import pl.cms.domain.entity.article.ArticleChannel;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.domain.entity.image.Image;
import pl.cms.pojo.command.article.CreateArticleCommand;
import pl.cms.pojo.command.article.ModifyArticleCommand;
import pl.cms.pojo.command.article.SupportArticleCommand;
import pl.cms.pojo.qo.ArticleQO;
import pl.cms.pojo.qo.CommentQO;
import hg.common.component.BaseServiceImpl;
import hg.common.component.RemoteConfigurer;
import hg.common.util.SysProperties;
import hg.common.util.file.FdfsFileInfo;
import hg.service.image.command.image.DeleteImageCommand;
import hg.service.image.pojo.exception.ImageException;
import hg.service.image.spi.inter.ImageService_1;

/**
 * @类功能说明：资讯
 * @类修改者：
 * @修改日期：2015年4月3日上午10:58:12
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年4月3日上午10:58:12
 */
@Service
@Transactional
public class ArticleServiceImpl extends
		BaseServiceImpl<Article, ArticleQO, ArticleDao> {

	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ArticleChannelDao articleChannelDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private ImageService imageLocalService;
	@Autowired
	private RemoteConfigurer remoteConfigurer;
	@Autowired
	private ImageService_1 imageSpiService;
	
	/**
	 * @方法功能说明：新增资讯
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月3日上午10:59:31
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws IOException
	 * @参数：@throws SolrServerException
	 * @return:void
	 * @throws
	 */
	public void createArticle(CreateArticleCommand command) throws IOException,
			SolrServerException {

		// 保存显示频道
		Set<ArticleChannel> showChannels = new HashSet<ArticleChannel>();
		for (String channelId : command.getShowChannelIds()) {
			ArticleChannel channel = articleChannelDao.load(channelId);
			showChannels.add(channel);
		}

		// 向图片服务创建文章标题图片
		Image titleImage = imageLocalService.createTitleImage(command.getTitleImageFileInfo(),
				command.getTitle(), command.getTitle(), "PL_CMS_ARTICLE_TITLE_IMAGE", "PL_CMS_ARTICLE_TITLE_IMAGE");

		// 保存文章
		Article article = new Article(command, showChannels, titleImage);
		articleDao.save(article);
	}

	/**
	 * @throws ImageException 
	 * @方法功能说明：修改资讯
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月3日上午10:59:44
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws IOException
	 * @参数：@throws SolrServerException
	 * @return:void
	 * @throws
	 */
	public void modifyArticle(ModifyArticleCommand command) throws IOException,
			SolrServerException {
		// 保存显示频道
		Set<ArticleChannel> showChannels = new HashSet<ArticleChannel>();
		for (String channelId : command.getShowChannelIds()) {
			ArticleChannel channel = articleChannelDao.load(channelId);
			showChannels.add(channel);
		}
		Article article = articleDao.get(command.getArticleId());

		Image image = article.getBaseInfo().getTitleImage();

		// 判断图片是否发生了变化
		if (checkArticleTitleImage(article, command.getTitleImageFileInfo())) {
			DeleteImageCommand deleteImageCommand = new DeleteImageCommand(
					SysProperties.getInstance().get("imageServiceProjectId"),
					SysProperties.getInstance().get(
							"imageServiceProjectEnvName"));
			//	删除旧图片
			List<String> ids = new ArrayList<String>();
			ids.add(image.getImageId());
			deleteImageCommand.setImageIds(ids);
			
			imageSpiService.deleteImage_1(deleteImageCommand);
			
			// 向图片服务保存最新图片
			image = imageLocalService.createTitleImage(command.getTitleImageFileInfo(),
					command.getTitle(), command.getTitle(), "PL_CMS_ARTICLE_TITLE_IMAGE", "PL_CMS_ARTICLE_TITLE_IMAGE");
		}

		article.modify(command, showChannels, image);
		articleDao.update(article);
	}

	
	

	/**
	 * 
	 * @方法功能说明：判断文章标题图片是否有变化
	 * @修改者名字：yuxx
	 * @修改时间：2015年4月8日上午10:25:51
	 * @修改内容：
	 * @参数：@param article
	 * @参数：@param command
	 * @参数：@return true代表有变，false代表不变
	 * @return:boolean
	 * @throws
	 */
	private boolean checkArticleTitleImage(Article article,
			FdfsFileInfo tempFileInfo) {
		return !StringUtils.equals(article.getBaseInfo().getTitleImage()
				.getImageId(), tempFileInfo.getImageId());
	}

	/**
	 * @方法功能说明：删除资讯
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月3日上午10:59:56
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@throws Exception
	 * @return:void
	 * @throws
	 */
	public void delete(Serializable id) throws Exception {
		Article article = articleDao.get(id);
		articleDao.deleteById(id);
		CommentQO qo = new CommentQO();
		qo.setArticleId(id.toString());
		qo.setFetchArticle(false);
		List<Comment> comments = commentDao.queryList(qo);
		for (Comment comment : comments) {
			commentDao.delete(comment);
		}
		List<String> imageIds=new ArrayList<>();
		DeleteImageCommand command=new DeleteImageCommand(
				SysProperties.getInstance().get("imageServiceProjectId"),
				SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageIds.add(article.getBaseInfo().getTitleImage().getImageId());
		command.setImageIds(imageIds);
		imageSpiService.deleteImage_1(command);
	}

	/**
	 * @方法功能说明：文章点赞
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月3日上午11:00:09
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void support(SupportArticleCommand command) {
		// ArticleQO qo=new ArticleQO();
		// qo.setId(id);
		// Article article=articleDao.queryUnique(qo);
		// ArticleStatus articleStatus=article.getStatus();
		// articleStatus.setSupportCount(articleStatus.getSupportCount()+1);
		// article.setStatus(articleStatus);
		// articleDao.update(article);

		Article article = articleDao.get(command.getArticleId());
		article.support();
		articleDao.update(article);
	}

	/**
	 * @方法功能说明：文章取消点赞
	 * @修改者名字：chenxy
	 * @修改时间：2015年4月3日上午11:00:09
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void cancelSupport(String id) {
		Article article = articleDao.get(id);
		article.cancelSupport();
		articleDao.update(article);
	}

	@Override
	protected ArticleDao getDao() {
		return articleDao;
	}
	// @Override
	// public Pagination queryPagination(Pagination pagination) {
	// Pagination pagination2=getDao().queryPagination(pagination);
	// @SuppressWarnings("unchecked")
	// List<Article> articles=(List<Article>)pagination2.getList();
	// for (Article article : articles) {
	// Hibernate.initialize(article.getShowChannels());
	// }
	// return pagination2;
	// }
}
