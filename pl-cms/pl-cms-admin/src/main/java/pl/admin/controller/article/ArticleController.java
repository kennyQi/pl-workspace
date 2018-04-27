package pl.admin.controller.article;
import hg.common.component.CommonDao;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.SysProperties;
import hg.common.util.UUIDGenerator;
import hg.service.image.pojo.dto.ImageDTO;
import hg.service.image.pojo.qo.AlbumQo;
import hg.service.image.pojo.qo.ImageQo;
import hg.service.image.pojo.qo.ImageUseTypeQo;
import hg.service.image.spi.inter.ImageService_1;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.admin.controller.BaseController;
import pl.app.service.ArticleChannelServiceImpl;
import pl.app.service.ArticleServiceImpl;
import pl.cms.domain.entity.article.Article;
import pl.cms.domain.entity.article.ArticleChannel;
import pl.cms.pojo.command.article.CreateArticleCommand;
import pl.cms.pojo.command.article.ModifyArticleCommand;
import pl.cms.pojo.qo.ArticleChannelQO;
import pl.cms.pojo.qo.ArticleQO;
/**
 * @类功能说明：资讯管理
 * @类修改者：
 * @修改日期：2015年3月24日下午2:54:29
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月24日下午2:54:29
 */
@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

	@Autowired
	private ArticleServiceImpl articleServiceImpl;
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private ArticleChannelServiceImpl articleChannelServiceImpl;
	@Autowired
	private ImageService_1 imageService_1;
	/**
	 * 新闻列表
	 * 
	 * @param request
	 * @param model
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute ArticleQO qo) {
		model.addAttribute("param", qo);
		
		List<ArticleChannel> articleChannels = articleChannelServiceImpl.queryList(new ArticleChannelQO());
		model.addAttribute("articleChannels", articleChannels);

		qo.setTitleLike(true);
		qo.setFetchChannel(true);
		qo.setOrderByCreateDate(-1);
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);
		
//		pagination = commonDao.forEntity(Article.class).autoQueryPagination(pagination);
		pagination = articleServiceImpl.queryPagination(pagination);
		
		model.addAttribute("pagination", pagination);

		return "/article/list.html";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model)
			throws IOException {
		List<ArticleChannel> articleChannels = articleChannelServiceImpl.queryList(new ArticleChannelQO());
		model.addAttribute("articleChannels", articleChannels);
		model.addAttribute("articleid", UUIDGenerator.getUUID());
		return "/article/add.html";
	}

	@RequestMapping(value = "/edit/{articleId}")
	public String edit(HttpServletRequest request, Model model,
			@PathVariable String articleId) {
		
		List<ArticleChannel> articleChannels = articleChannelServiceImpl.queryList(new ArticleChannelQO());
		ArticleQO qo = new ArticleQO();
		qo.setId(articleId);
		qo.setFetchChannel(true);
		qo.setFetchImage(true);
		Article article = articleServiceImpl.queryUnique(qo);
		
		ImageQo imageQo = new ImageQo();
		imageQo.setProjectId(SysProperties.getInstance().get("imageServiceProjectId"));
		imageQo.setEnvName(SysProperties.getInstance().get("imageServiceProjectEnvName"));
		imageQo.setId(article.getBaseInfo().getTitleImage().getImageId());
		ImageUseTypeQo useType=new ImageUseTypeQo();
		useType.setId("PL_CMS_ARTICLE_TITLE_IMAGE");
		imageQo.setUseType(useType);
		AlbumQo albumQo=new AlbumQo();
		albumQo.setId("PL_CMS_ARTICLE_TITLE_IMAGE");
		imageQo.setAlbumQo(albumQo);
		ImageDTO imageDTO = imageService_1.queryUniqueImage_1(imageQo);
		
		model.addAttribute("articleChannels", articleChannels);
		model.addAttribute("article", article);
		model.addAttribute("titleImageFileInfoJSON", imageDTO.getSpecImageMap().get("default").getFileInfo());
		model.addAttribute("fileUploadPath", SysProperties.getInstance().get("fileUploadPath"));
		return "/article/edit.html";
	}

	@RequestMapping(value = "/update")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute ModifyArticleCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "修改成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "articleList";

		if (command != null) {
			try {
				articleServiceImpl.modifyArticle(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "修改失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);
	}

	@ResponseBody
	@RequestMapping(value = "/delete/{articleId}")
	public String delete(HttpServletRequest request, Model model,
			@PathVariable String articleId) {
		try {
			articleServiceImpl.delete(articleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return DwzJsonResultUtil.createSimpleJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
	}

	@ResponseBody
	@RequestMapping(value = "/create")
	public String create(HttpServletRequest request, Model model,
			@ModelAttribute CreateArticleCommand command) {

		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "保存成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "articleList";

		if (command != null) {
			try {
				articleServiceImpl.createArticle(command);
			} catch (Exception e) {
				e.printStackTrace();
				statusCode = DwzJsonResultUtil.STATUS_CODE_500;
				message = "保存失败:" + e.getMessage();
			}
		}

		return DwzJsonResultUtil.createJsonString(statusCode, message,
				callbackType, navTabId);

	}

}
