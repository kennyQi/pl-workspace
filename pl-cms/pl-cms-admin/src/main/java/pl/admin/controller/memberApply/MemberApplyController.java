package pl.admin.controller.memberApply;
import hg.common.page.Pagination;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.admin.controller.BaseController;
import pl.app.service.MemberApplyServiceImpl;
import pl.cms.pojo.qo.MemberApplyQO;
/**
 * @类功能说明：入会申请控制
 * @类修改者：
 * @修改日期：2015年3月19日下午4:11:28
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月19日下午4:11:28
 */
@Controller
@RequestMapping("/memberApply")
public class MemberApplyController extends BaseController {

	@Autowired
	private MemberApplyServiceImpl memberApplyServiceImpl;
	/**
	 * @方法功能说明：入会申请列表
	 * @修改者名字：chenxy
	 * @修改时间：2015年3月19日下午4:08:26
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
			@ModelAttribute MemberApplyQO qo) {
		model.addAttribute("param", qo);
		qo.setScenicNameLike(true);
		Pagination pagination = new Pagination();
		pagination.setPageNo(qo.getPageNo());
		pagination.setPageSize(qo.getPageSize());
		pagination.setCondition(qo);
		pagination = memberApplyServiceImpl.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return "/memberApply/list.html";
	}

}
