package pl.admin.controller.comment;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.log.util.HgLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.admin.controller.BaseController;
import pl.app.service.CommentServiceImpl;
import pl.cms.domain.entity.comment.Comment;
import pl.cms.pojo.command.comment.ApproveCommentCommand;
import pl.cms.pojo.qo.CommentQO;
import javax.servlet.http.HttpServletRequest;
/**
 * @类功能说明：评论管理
 * @类修改者：
 * @修改日期：2015年3月17日上午8:44:32
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：chenxy
 * @创建时间：2015年3月17日上午8:44:32
 */
@Controller
@RequestMapping("/comment")
public class CommentController extends BaseController {

    @Autowired
    private CommentServiceImpl commentService;

    /**
     * 展示内容管理系统列表
     * @param request
     * @param model
     * @param qo
     * @return
     */
    @RequestMapping(value = "/list")
    public String newsList(HttpServletRequest request, Model model,
                       @ModelAttribute CommentQO qo) {
        try{
        	model.addAttribute("param", qo);       	
        	qo.setFetchArticle(true);
            Pagination pagination = new Pagination();
            pagination.setPageNo(qo.getPageNo());
            pagination.setPageSize(qo.getPageSize());
            pagination.setCondition(qo);
            pagination = commentService.queryPagination(pagination);
            model.addAttribute("pagination", pagination);
            model.addAttribute("type", "news");
        }
        catch (Exception e){
        	 e.printStackTrace();
        	 HgLogger.getInstance().info("chenxy", "查询Comment列表异常"+HgLogger.getStackTrace(e));
        }
        return "/comment/list.html";
    }

    @ResponseBody
    @RequestMapping(value="/delete/{id}")
    public String del(HttpServletRequest request, Model model,
                      @PathVariable String id){
        try {
        	commentService.deleteById(id);
        }catch (Exception e){
            HgLogger.getInstance().error("chenxy","删除Comment信息异常");
            return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_500, "删除失败");
        }
        return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_200, "删除成功");
    }
    @ResponseBody
    @RequestMapping(value="/approve/{id}")
    public String approveComment(HttpServletRequest request, Model model,
                       @PathVariable String id){
        ApproveCommentCommand approveCommentCommand = new ApproveCommentCommand();
        approveCommentCommand.setCommentId(id);
        commentService.approveCommentDao(approveCommentCommand);
        return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_200, "审核成功");
    }
    @RequestMapping(value="/edit/{id}")
    public String edit(HttpServletRequest request, Model model,
                       @PathVariable String id){
        CommentQO qo = new CommentQO();
        qo.setId(id);
        qo.setFetchArticle(false);
        Comment comment = commentService.queryUnique(qo);
        model.addAttribute("comment",comment);
        return "/comment/edit.html";
    }

}
