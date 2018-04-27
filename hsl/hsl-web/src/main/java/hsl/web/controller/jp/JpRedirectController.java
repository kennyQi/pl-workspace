package hsl.web.controller.jp;

import hsl.web.controller.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * @param <payOrderService>
 * @类功能： 机票控制器
 * @作者： liusong
 * @创建时间：2014年9月15日上午
 */
@Controller
public class JpRedirectController extends BaseController {

	@RequestMapping(value = "/jp/main")
	public ModelAndView main(@RequestParam(value = "from", required = false) String from,
							 @RequestParam(value = "to", required = false) String to,
							 @RequestParam(value = "time", required = false) String time) {

		ModelAndView mav = new ModelAndView();
		mav.getModel().put("orgCity", from);
		mav.getModel().put("dstCity", to);
		mav.getModel().put("startDate", time);
		mav.setView(new RedirectView("/yxjp/main", true));

		return mav;
	}
}
