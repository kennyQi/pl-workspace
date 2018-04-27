package hg.dzpw.merchant.controller.clear;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import hg.dzpw.merchant.controller.BaseController;

/**
 * @Time 2015-05-04
 * @description 处理结算管理中的结算信息
 * @author Guotx
 *
 */
@Controller
@RequestMapping(value = "/clear")
public class ClearController extends BaseController {
	@RequestMapping(value = "/clearInfo")
	public String clearInfo(Model model) {
		return "/clear/clearInfo.html";
	}
}
