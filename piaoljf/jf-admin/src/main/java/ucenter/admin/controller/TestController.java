package ucenter.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping(value="/test")
public class TestController extends BaseController {
	
	@RequestMapping(value = "/index")
	public Object test() {
//		return new ;
//		return new RedirectView("http://www.baidu.com");
		return "redirect:http://www.baidu.com";
	}
}
