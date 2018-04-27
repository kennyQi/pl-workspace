package hg.jxc.admin.controller.setting;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.system.PaymentMethodService;
import jxc.domain.model.system.PaymentMethod;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.Tools;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreatePaymentMethodCommand;
import hg.pojo.command.ModifyPaymentMethodCommand;
import hg.pojo.command.RemovePaymentMethodCommand;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.PaymentMethodQo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/payment_method")
public class PaymentMethodController extends BaseController {
	@Autowired
	PaymentMethodService paymentMethodService;

	final static String navTabRel = "payment_method_list";

	@RequestMapping("/list")
	public String queryPaymentMethodList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, PaymentMethodQo qo) {
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = paymentMethodService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		return "setting/paymentMethod/paymentMethodList.html";
	}

	@RequestMapping("/to_add")
	public String toAddPaymentMethod(Model model, HttpServletRequest request) {
		return "setting/paymentMethod/paymentMethodAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createPaymentMethod(Model model, HttpServletRequest request, CreatePaymentMethodCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			paymentMethodService.createPaymentMethod(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel);
	}

	@RequestMapping("/edit")
	public String editPaymentMethod(Model model, HttpServletRequest request, PaymentMethodQo qo) {
		PaymentMethod paymentMethod = paymentMethodService.queryUnique(qo);
		JsonResultUtil.AddFormData(model, paymentMethod);
		Tools.modelAddFlag("edit", model);
		return "setting/paymentMethod/paymentMethodAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updatePaymentMethod(Model model, HttpServletRequest request, ModifyPaymentMethodCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			paymentMethodService.modifyPaymentMethod(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("编辑成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removePaymentMethod(RemovePaymentMethodCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			paymentMethodService.removePaymentMethod(command);
		} catch (SettingException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);

	}

}
