package pl.h5.control.contribution;
import hg.common.page.Pagination;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import pl.app.service.CommentServiceImpl;
import pl.app.service.ContributionServiceImpl;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.domain.entity.contribution.Contribution;
import pl.cms.pojo.command.contribution.CreateContributionCommand;
import pl.cms.pojo.qo.CommentQO;
import pl.cms.pojo.qo.ContributionQO;
import pl.h5.control.BaseController;
import pl.h5.control.constant.PullUpListResult;
@RequestMapping("/contribution")
@Controller
public class ContributionController extends BaseController{

	@Autowired
	private ContributionServiceImpl contributionServiceImpl;
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	@RequestMapping(value = "/list")
	public String queryContributionList(HttpServletRequest request, Model model,
			@ModelAttribute ContributionQO qo){
		qo.setTitleLike(true);
		qo.setCheckStatus(Contribution.CONTRIBUTION_APPROVE);
		Pagination pagination = new Pagination();
		pagination.setPageNo(1);
		pagination.setPageSize(20);
		pagination.setCondition(qo);
		pagination.setCheckPassLastPageNo(false);
		pagination = contributionServiceImpl.queryPagination(pagination);
		if(pagination.getList().size() < 5&&pagination.getList().size()!=0){
			model.addAttribute("haveMore", "false");
		}else{
			model.addAttribute("haveMore", "true");
		}
		model.addAttribute("pagination", pagination);
		model.addAttribute("qo", qo);
//		List<Contribution> contributions = contributionServiceImpl.queryList(qo);
//		model.addAttribute("contributions", contributions);
		return "/contribution/contribution_list.html";
	}
	@ResponseBody
	@RequestMapping("pullUpList")
	public String pullUpList(@RequestParam(value="pageNo",required=false) Integer pageNo, 
			@RequestParam(value="pageSize",required=false) Integer pageSize, ContributionQO qo ,PrintWriter out){
		qo.setTitleLike(true);
		qo.setCheckStatus(Contribution.CONTRIBUTION_APPROVE);
		PullUpListResult pullUpListResult = new PullUpListResult();
		pullUpListResult.setHaveMore(true);//是否还有数据
		Pagination pagination = new Pagination();
		pagination.setPageNo(pageNo);
		pagination.setPageSize(pageSize);
		pagination.setCondition(qo);
		pagination.setCheckPassLastPageNo(false);
		pagination = contributionServiceImpl.queryPagination(pagination);
		if(qo.getPageSize() > pagination.getList().size()){
			pullUpListResult.setHaveMore(false);
		}
		pullUpListResult.setPagination(pagination);
		pullUpListResult.setPageNo(pageNo);
		pullUpListResult.setPageSize(pageSize);
		pullUpListResult.setQo(qo);
		
		SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
		filter.getExcludes().add("showChannels");
		String s = JSON.toJSONString(pullUpListResult, filter);
		return s;
	}
	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model,
			@ModelAttribute ContributionQO qo){
		String message=request.getParameter("success");
		if(!StringUtils.isNotBlank(message)){
			message="false";
		}
		model.addAttribute("success", message);
		return "/contribution/contribution_add.html";
	}
	@RequestMapping(value = "/create")
	public RedirectView create(HttpServletRequest request, Model model,
			@ModelAttribute CreateContributionCommand command){
		RedirectView view=new RedirectView( "/contribution/list",true);
		contributionServiceImpl.createContribution(command);
		return view;
	}
	@RequestMapping(value = "/details/{contributionId}")
	public String details(HttpServletRequest request, Model model,@PathVariable String contributionId){
		ContributionQO qo=new ContributionQO();
		qo.setId(contributionId);
		qo.setCheckStatus(Contribution.CONTRIBUTION_APPROVE);
		Contribution contribution=contributionServiceImpl.queryUnique(qo);
		if(contribution==null){
			model.addAttribute("redirectURL", "/contribution/list");
			return "/fail404.html";
		}
		CommentQO commentQO=new CommentQO();
		commentQO.setArticleId(contributionId);
		commentQO.setIsChecked(true);
		commentQO.setFetchArticle(false);
		List<Comment> comments=commentServiceImpl.queryList(commentQO);
		model.addAttribute("contribution", contribution);
		model.addAttribute("comments", comments);
		return "/contribution/contribution_details.html";
	}
	@RequestMapping(value="/support/{contributionId}")
	@ResponseBody
	public String support(HttpServletRequest request, Model model,@PathVariable String contributionId){
		contributionServiceImpl.support(contributionId);
		return "success";
	}
	@RequestMapping(value="/cancelSupport/{contributionId}")
	@ResponseBody
	public String cancelSupport(HttpServletRequest request, Model model,@PathVariable String contributionId){
		contributionServiceImpl.cancelSupport(contributionId);
		return "success";
	}
}
 