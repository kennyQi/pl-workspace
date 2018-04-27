package lxs.admin.controller.introduction;

import hg.common.util.DwzJsonResultUtil;
import lxs.app.service.app.FunctionIntroduntionService;
import lxs.app.service.app.IntroductionService;
import lxs.app.service.app.OrderNoticeService;
import lxs.domain.model.app.FunctionIntroduction;
import lxs.domain.model.app.Introduction;
import lxs.domain.model.app.OrderNotice;
import lxs.pojo.command.app.AddFunctionIntroductionCommand;
import lxs.pojo.command.app.AddIntroductionCommand;
import lxs.pojo.command.app.AddOrderNoticeCommand;
import lxs.pojo.qo.app.FunctionIntroductionQO;
import lxs.pojo.qo.app.IntroductionQO;
import lxs.pojo.qo.app.OrderNoticeQO;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @类功能说明：随心游简介控制器
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江票量科技有限公司
 * @作者：jinyy
 * @创建时间：2015年10月10日下午2:12:11
 */
@Controller
@RequestMapping(value = "/introduction")
public class IntroductionController {

	@Autowired
	private IntroductionService introductionservice;

	@Autowired
	private OrderNoticeService orderNoticeService;
	
	@Autowired
	private FunctionIntroduntionService functionIntroduntionService;

	// 1旅游简介
	@RequestMapping(value = "/jqjjAddView")
	public String addJqjjView(Model model) {
		IntroductionQO introductionQO = new IntroductionQO();
		introductionQO.setIntroductionType(Introduction.JQJJ);
		Introduction introduction = introductionservice
				.queryUnique(introductionQO);
		if (introduction == null)
			introduction = new Introduction();
		model.addAttribute("introduction", introduction);
		return "/introduction/jqjjAdd.html";
	}

	// 1旅游简介
	@ResponseBody
	@RequestMapping(value = "/jqjjAdd")
	public String addJqjj(Model model, AddIntroductionCommand command) {
		if (StringUtils.isNotBlank(command.getId())) {
			introductionservice.updateLyjj(command);

		} else {
			introductionservice.addLyjj(command);
		}

		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "操作成功!", null, "jqjjAdd");

	}

	// 2旅游协议
	@RequestMapping(value = "/lyxyAddView")
	public String addLyxyView(Model model) {

		IntroductionQO introductionQO = new IntroductionQO();
		introductionQO.setIntroductionType(Introduction.LYXY);
		Introduction introduction = introductionservice
				.queryUnique(introductionQO);
		if (introduction == null)
			introduction = new Introduction();
		model.addAttribute("introduction", introduction);
		return "/introduction/lyxyAdd.html";
	}

	// 2旅游协议
	@ResponseBody
	@RequestMapping(value = "/lyxyAdd")
	public String addLyxyAdd(Model model, AddIntroductionCommand command) {
		if (StringUtils.isNotBlank(command.getId())) {
			introductionservice.updateLyxy(command);

		} else {
			introductionservice.addLyxy(command);
		}

		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "操作成功!", null, "lyxyAdd");

	}

	// 3经营许可
	@RequestMapping(value = "/jyxkAddView")
	public String addJyxkView(Model model) {

		IntroductionQO introductionQO = new IntroductionQO();
		introductionQO.setIntroductionType(Introduction.JYXK);

		Introduction introduction = introductionservice
				.queryUnique(introductionQO);
		if (introduction == null)
			introduction = new Introduction();
		model.addAttribute("introduction", introduction);
		return "/introduction/jyxkAdd.html";
	}

	// 3经营许可
	@ResponseBody
	@RequestMapping(value = "/jyxkAdd")
	public String addJyxkAdd(Model model, AddIntroductionCommand command) {
		if (StringUtils.isNotBlank(command.getId())) {
			introductionservice.updateJyxk(command);

		} else {
			introductionservice.addJyxk(command);
		}

		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "操作成功!", null, "jyxkAdd");

	}

	// 订单通知设置
	@RequestMapping(value = "/orderNoticeAddView")
	public String addOrderNoticeView(Model model) {
		OrderNoticeQO orderNoticeQO = new OrderNoticeQO();

		OrderNotice orderNotice = orderNoticeService.queryUnique(orderNoticeQO);
		if (orderNotice == null)
			orderNotice = new OrderNotice();
		model.addAttribute("orderNotice", orderNotice);
		return "/introduction/orderNoticeAdd.html";
	}

	//  订单通知添加/修改
	@ResponseBody
	@RequestMapping(value = "/orderNoticeAdd")
	public String addorderNotice(Model model, AddOrderNoticeCommand command) {
		if (StringUtils.isNotBlank(command.getId())) {
			orderNoticeService.updateBean(command);

		} else {
			orderNoticeService.addBean(command);
		}

		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "操作成功!", null,
				"orderNoticeAdd");

	}

	// 功能介绍
	@RequestMapping(value = "/gnjsAddView")
	public String addGnjsView(Model model) {
		FunctionIntroductionQO functionIntroductionQO = new FunctionIntroductionQO();

		FunctionIntroduction functionIntroduction = functionIntroduntionService.queryUnique(functionIntroductionQO);
		if (functionIntroduction == null)
			functionIntroduction = new FunctionIntroduction();
		model.addAttribute("functionIntroduction", functionIntroduction);
		return "/introduction/functionIntroAdd.html";
	}

	// 功能介绍，添加/修改
	@ResponseBody
	@RequestMapping(value = "/gnjsAdd")
	public String addGnjs(Model model, AddFunctionIntroductionCommand command) {
		if (StringUtils.isNotBlank(command.getId())) {
			functionIntroduntionService.updateBean(command);

		} else {
			functionIntroduntionService.addBean(command);
		}

		return DwzJsonResultUtil.createJsonString(
				DwzJsonResultUtil.STATUS_CODE_200, "操作成功!", null,
				"gnjsAdd");

	}
	
	
	

}
