package lxs.api.controller.IntroductionH5;

import lxs.api.controller.BaseController;
import lxs.app.service.app.FunctionIntroduntionService;
import lxs.app.service.app.IntroductionService;
import lxs.domain.model.app.FunctionIntroduction;
import lxs.domain.model.app.Introduction;
import lxs.pojo.qo.app.FunctionIntroductionQO;
import lxs.pojo.qo.app.IntroductionQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * 
 * @author jinyy 简介控制器
 */
@Controller
@RequestMapping("introduction")
public class IntroductionControllerH5 extends BaseController {

	/**
	 * http://127.0.0.1:8080/lxs-api/introduction/introductionDetailView?type=1
	 * 根据type=?，查询出一条的详情。景区简介1,旅游协议2, 经营许可3
	 */
	@Autowired
	private IntroductionService introductionservice;

	@RequestMapping("introductionDetailView")
	public ModelAndView introductionDetailView(
			@RequestParam(value = "type", required = true) String type) {

		IntroductionQO introductionQO = new IntroductionQO();
		try {
			introductionQO.setIntroductionType(Integer.parseInt(type));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		ModelAndView mav = new ModelAndView("introduction/detail");
		Introduction introduction = introductionservice
				.queryUnique(introductionQO);
		if (introduction == null)
			introduction = new Introduction();
		introduction.setIntroductionType(Integer.parseInt(type));
		mav.addObject("introduction", introduction);

		return mav;
	}

	/**
	 * 功能介绍  
	 * 访问地址：        http://127.0.0.1:8080/lxs-api/introduction/functionIntroductionDetailView
	 */
	@Autowired
	private FunctionIntroduntionService functionIntroduntionService;

	@RequestMapping("functionIntroductionDetailView")
	public ModelAndView orderNoticeDetailView() {
		FunctionIntroductionQO qo = new FunctionIntroductionQO();
		ModelAndView mav = new ModelAndView("introduction/orderDetail");
		FunctionIntroduction bean = functionIntroduntionService.queryUnique(qo);
		if (bean == null)
			bean = new FunctionIntroduction();
		mav.addObject("bean", bean);
		return mav;
	}

}
