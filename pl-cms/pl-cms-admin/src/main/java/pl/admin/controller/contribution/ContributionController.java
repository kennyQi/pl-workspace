package pl.admin.controller.contribution;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.common.util.UUIDGenerator;
import hg.system.common.system.SecurityConstants;
import hg.system.model.auth.AuthUser;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.admin.controller.BaseController;
import pl.app.service.ContributionServiceImpl;
import pl.cms.domain.entity.contribution.Contribution;
import pl.cms.domain.entity.contribution.ContributionRecord;
import pl.cms.pojo.command.article.CreateArticleCommand;
import pl.cms.pojo.command.contribution.CheckContributionCommand;
import pl.cms.pojo.qo.ContributionQO;
/**
 * @类功能说明：投稿管理
 * @类修改者：
 * @修改日期：2015年3月24日下午2:53:57
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月24日下午2:53:57
 */
@RequestMapping(value="/contribution")
@Controller
public class ContributionController extends BaseController {

	@Autowired
	private ContributionServiceImpl contributionServiceImpl;
	/**
	 * @方法功能说明：稿件列表
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月25日下午4:37:00
	 * @修改内容：
	 * @参数：@param request
	 * @参数：@param model
	 * @参数：@param qo
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	@RequestMapping(value = "/list")
	public String list(HttpServletRequest request, Model model,
			@ModelAttribute ContributionQO qo) {
		model.addAttribute("param", qo);
		qo.setTitleLike(true);
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);
		pagination = contributionServiceImpl.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "/contribution/list.html";
	}

	@RequestMapping(value = "/add")
	public String add(HttpServletRequest request, Model model)
			throws IOException {
		model.addAttribute("articleid", UUIDGenerator.getUUID());
		return "/contribution/add.html";
	}

	@RequestMapping(value = "/look/{contributionId}")
	public String look(HttpServletRequest request, Model model,
			@PathVariable String contributionId) {
		
		ContributionQO qo = new ContributionQO();
		qo.setId(contributionId);
		Contribution contribution = contributionServiceImpl.queryUnique(qo);
		ContributionQO qo2 = new ContributionQO();
		qo2.setContributionId(contributionId);
		List<ContributionRecord> contributionRecords=contributionServiceImpl.queryContributionRecordById(qo2);
		model.addAttribute("contribution", contribution);
		model.addAttribute("contributionRecords", contributionRecords);
		return "/contribution/look.html";
	}
	
	@RequestMapping(value = "/check")
	@ResponseBody
	public String update(HttpServletRequest request, Model model,
			@ModelAttribute CheckContributionCommand command) {
		String statusCode = DwzJsonResultUtil.STATUS_CODE_200;
		String message = "审核成功";
		String callbackType = DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT;
		String navTabId = "contributionList";

		if (command != null) {
			try {
				Subject currentUser = SecurityUtils.getSubject();
				AuthUser user=(AuthUser) currentUser.getSession().getAttribute(SecurityConstants.SESSION_USER_KEY);
				if(user==null){
					return DwzJsonResultUtil.createJsonString( DwzJsonResultUtil.STATUS_CODE_500, "session失效，请重新登录",
							callbackType, navTabId);
				}
				command.setAdminId(user.getId());
				command.setAdminName(user.getLoginName());
				contributionServiceImpl.checkContribution(command);
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
//		articleServiceImpl.deleteById(articleId);
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
		String navTabId = "contributionList";

		if (command != null) {
			try {
//				articleServiceImpl.createArticle(command);
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
