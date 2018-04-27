package hg.jxc.admin.controller.setting;

import javax.servlet.http.HttpServletRequest;

import jxc.app.service.system.UnitService;
import jxc.domain.model.system.Unit;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.common.Tools;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemoveUnitCommand;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.UnitQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/unit")
public class UnitController extends BaseController {
	@Autowired
	UnitService unitService;

	final static String navTabRel = "unit_list";

	@RequestMapping("/list")
	public String queryUnitList(Model model, HttpServletRequest request, @ModelAttribute DwzPaginQo dwzPaginQo, UnitQO qo) {
		qo.setNameLike(true);
		Pagination pagination = createPagination(dwzPaginQo, qo);
		pagination = unitService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);

		JsonResultUtil.AddFormData(model, qo);
		return "setting/unit/unitList.html";
	}

	@RequestMapping("/to_add")
	public String toAddUnit(Model model, HttpServletRequest request) {
		return "setting/unit/unitAdd.html";
	}

	@RequestMapping("/create")
	@ResponseBody
	public String createUnit(Model model, HttpServletRequest request, CreateUnitCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		Unit unit;
		try {
			unit = unitService.createUnit(command);
		} catch (ProductException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		// 返回json
		return JsonResultUtil.dwzSuccessMsg("保存成功", navTabRel, "<option value=\\\"" + unit.getId() + "\\\">" + unit.getUnitName() + "</option>");
	}

	@RequestMapping("/edit")
	public String editUnit(Model model, HttpServletRequest request, UnitQO qo) {
		Tools.modelAddFlag("edit", model);
		Unit unit = unitService.queryUnique(qo);
		JsonResultUtil.AddFormData(model, unit);

		return "setting/unit/unitAdd.html";
	}

	@RequestMapping("/update")
	@ResponseBody
	public String updateUnit(Model model, HttpServletRequest request, ModifyUnitCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);
		try {
			unitService.modifyUnit(command);
		} catch (ProductException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}

		return JsonResultUtil.dwzSuccessMsg("修改成功", navTabRel);
	}

	@RequestMapping("/remove")
	@ResponseBody
	public String removeUnit(RemoveUnitCommand command) {
		CommandUtil.SetOperator(getAuthUser(), command);

		try {
			unitService.removeUnit(command);
		} catch (ProductException e) {
			return JsonResultUtil.dwzExceptionMsg(e);
		}
		return JsonResultUtil.dwzSuccessMsgNoClose("删除成功", navTabRel);

	}

}
