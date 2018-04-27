package pl.h5.control.article;
import hg.common.page.Pagination;
import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.io.PrintWriter;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import pl.app.service.ArticleServiceImpl;
import pl.app.service.CommentServiceImpl;
import pl.cms.domain.entity.article.Article;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.pojo.command.article.SupportArticleCommand;
import pl.cms.pojo.qo.ArticleQO;
import pl.cms.pojo.qo.CommentQO;
import pl.h5.control.BaseController;
import pl.h5.control.constant.Constants;
import pl.h5.control.constant.PullUpListResult;
/**
 * @类功能说明：资讯管理控制层
 * @类修改者：
 * @修改日期：2015年3月27日上午9:45:02
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月27日上午9:45:02
 */
@RequestMapping("/article")
@Controller
public class ArticleController extends BaseController{
	@Resource
	private ArticleServiceImpl articleServiceImpl;
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	@RequestMapping(value = "/list")
	public String queryArticleList(HttpServletRequest request, Model model,
			@ModelAttribute ArticleQO qo){
		qo.setOrderByCreateDate(-1);
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(10);
		pagination.setCondition(qo);
		pagination.setCheckPassLastPageNo(false);
		pagination = articleServiceImpl.queryPagination(pagination);
		if(pagination.getList().size() < 5&&pagination.getList().size()!=0){
			model.addAttribute("haveMore", "false");
		}else{
			model.addAttribute("haveMore", "true");
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);
		
//		List<Article> articles=articleServiceImpl.queryList(qo,10);
//		model.addAttribute("articles", articles);
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		model.addAttribute("title", Constants.articleChannelMap.get(Integer.parseInt(qo.getShowChannelId())));
		return "/article/article_list.html";
	}
	
	@ResponseBody
	@RequestMapping("pullUpList")
	public String pullUpList(@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value="pageSize",required=false) Integer pageSize, ArticleQO qo ,PrintWriter out){
		String s="";
		try{
			PullUpListResult pullUpListResult = new PullUpListResult();
			pullUpListResult.setHaveMore(true);//是否还有数据
			Pagination pagination = new Pagination();
			pagination.setPageNo(pageNo);
			pagination.setPageSize(pageSize);
			pagination.setCondition(qo);
			pagination.setCheckPassLastPageNo(false);
			pagination = articleServiceImpl.queryPagination(pagination);
			if(qo.getPageSize() > pagination.getList().size()){
				pullUpListResult.setHaveMore(false);
			}
			pullUpListResult.setPagination(pagination);
			pullUpListResult.setPageNo(pageNo);
			pullUpListResult.setPageSize(pageSize);
			pullUpListResult.setQo(qo);
			s= JSON.toJSONString(pullUpListResult);
			HgLogger.getInstance().info("chenxy", "下拉查询数据JSON"+s);
		}catch(Exception e){
			e.printStackTrace();
			HgLogger.getInstance().error("chenxy", "下拉出错："+HgLogger.getStackTrace(e));
		}
		return s;
	}
	
	@RequestMapping(value = "/details/{articleId}")
	private String queryUniqueArticle(HttpServletRequest request, Model model,@PathVariable String articleId){
		ArticleQO articleQO=new ArticleQO();
		articleQO.setId(articleId);
		Article article=articleServiceImpl.queryUnique(articleQO);
		String channleId=request.getParameter("showChannelId");
		if(article==null){
			model.addAttribute("redirectURL", "/article/list?showChannelId="+channleId);
			return "/fail404.html";
		}
		String fileUploadPath = SysProperties.getInstance().get("fileUploadPath");
		model.addAttribute("fileUploadPath", fileUploadPath);
		model.addAttribute("article", article);
		model.addAttribute("title", Constants.articleChannelMap.get(Integer.parseInt(channleId)));
		CommentQO commentQO=new CommentQO();
		commentQO.setArticleId(articleId);
		commentQO.setIsChecked(true);
		commentQO.setFetchArticle(false);
		List<Comment> comments=commentServiceImpl.queryList(commentQO);
		model.addAttribute("comments", comments);
		return "/article/article_details.html";
	}
	
	@RequestMapping(value="/support/{articleId}")
	@ResponseBody
	public String support(HttpServletRequest request, Model model,@PathVariable String articleId){
		SupportArticleCommand command = new SupportArticleCommand();
		command.setArticleId(articleId);
		articleServiceImpl.support(command);
		return "success";
	}
	
	@RequestMapping(value="/cancelSupport/{contributionId}")
	@ResponseBody
	public String cancelSupport(HttpServletRequest request, Model model,@PathVariable String contributionId){
		articleServiceImpl.cancelSupport(contributionId);
		return "success";
	}
}
 