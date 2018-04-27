package pl.h5.control.comment;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.app.service.CommentServiceImpl;
import pl.cms.pojo.command.comment.CreateCommentCommand;
import pl.h5.control.BaseController;
@RequestMapping("/comment")
@Controller
public class CommentController extends BaseController{
	@Autowired
	private CommentServiceImpl commentServiceImpl;
	
	@RequestMapping(value = "/create")
	@ResponseBody
	private String createComment(HttpServletRequest request, Model model,
			@ModelAttribute CreateCommentCommand command){
		commentServiceImpl.createComment(command);
		return "success";
	}
}
 