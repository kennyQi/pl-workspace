package hg.jxc.admin.controller.warehouse;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.common.JsonResultUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateWarehouseTypeCommand;
import hg.pojo.command.DeleteWarehouseTypeCommand;
import hg.pojo.command.ModifyWarehouseTypeCommand;
import hg.pojo.exception.WarehouseException;
import hg.pojo.qo.WarehouseTypeQO;
import jxc.app.service.warehouse.WarehouseTypeService;
import jxc.domain.model.warehouse.WarehouseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/warehouse/type")
public class WarehouseTypeController extends BaseController {

	@Autowired
	WarehouseTypeService warehouseTypeService;

	/**
	 * 查询仓库类型列表
	 * 
	 * @param model
	 * @param dwzPaginQo
	 * @return
	 */
	@RequestMapping("/list")
	public String queryWarehouseTypeList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo) {
		Pagination pagination = createPagination(dwzPaginQo, new WarehouseTypeQO());
		pagination = warehouseTypeService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "warehouse/type/warehouse_type_list.html";
	}

	/**
	 * 添加仓库类型
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createWarehouseType(CreateWarehouseTypeCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			warehouseTypeService.createWarehouseType(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "仓库类型添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT,
					"warehouse_type_list");

		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());

		}
	}

	/**
	 * 跳转添加仓库类型页面
	 * 
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddWarehouseType() {
		return "warehouse/type/warehouse_type_add.html";
	}

	/**
	 * 删除仓库类型
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteWarehouseType(DeleteWarehouseTypeCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			warehouseTypeService.deleteWarehouseType(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "仓库类型删除成功", null, "warehouse_type_list");

		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}

	}

	/**
	 * 跳转仓库类型编辑页面
	 * 
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editWarehouseType(Model model, @RequestParam(value = "id", required = true) String id) {
		WarehouseType warehouseType = warehouseTypeService.get(id);
		model.addAttribute("warehouseType", warehouseType);
		return "warehouse/type/warehouse_type_edit.html";
	}

	/**
	 * 更新仓库类型
	 * 
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateWarehouseType(ModifyWarehouseTypeCommand command) {

		CommandUtil.SetOperator(getAuthUser(), command);

		try {

			warehouseTypeService.updateWarehouseType(command);
			return JsonResultUtil.dwzSuccessMsg("仓库类型修改成功", "warehouse_type_list");

		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}
	}

}