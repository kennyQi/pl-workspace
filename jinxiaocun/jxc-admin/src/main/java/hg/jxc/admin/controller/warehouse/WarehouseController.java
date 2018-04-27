package hg.jxc.admin.controller.warehouse;

import hg.common.model.qo.DwzPaginQo;
import hg.common.page.Pagination;
import hg.common.util.DwzJsonResultUtil;
import hg.jxc.admin.common.CommandUtil;
import hg.jxc.admin.controller.BaseController;
import hg.pojo.command.CreateWarehouseCommand;
import hg.pojo.command.DeleteWarehouseCommand;
import hg.pojo.command.ModifyWarehouseCommand;
import hg.pojo.exception.WarehouseException;
import hg.pojo.qo.WarehouseQO;
import hg.pojo.qo.WarehouseTypeQO;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.qo.AreaQo;
import hg.system.qo.CityQo;
import hg.system.qo.ProvinceQo;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.List;

import jxc.app.service.warehouse.WarehouseService;
import jxc.app.service.warehouse.WarehouseTypeService;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouse.WarehouseType;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/warehouse")
public class WarehouseController extends BaseController {
	@Autowired
	WarehouseService warehouseService;
	
	@Autowired
	WarehouseTypeService typeService;
	
	@Autowired
	ProvinceService provinceService;
	
	@Autowired
	CityService cityService;

	@Autowired
	AreaService areaService;
	
	
	/**
	 * 查询仓库列表
	 * @param model
	 * @param dwzPaginQo
	 * @return
	 */
	@RequestMapping("/list")
	public String queryWarehouseList(Model model, @ModelAttribute DwzPaginQo dwzPaginQo) {
		Pagination pagination = createPagination(dwzPaginQo, new WarehouseQO());
		pagination = warehouseService.queryPagination(pagination);
		model.addAttribute("pagination", pagination);
		return "warehouse/warehouse_list.html";
	}

	/**
	 * 添加仓库
	 * @param command
	 * @return
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createWarehouse(CreateWarehouseCommand command) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		
		if(StringUtils.isBlank(command.getTypeId())){
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, "仓库类型为必填项");
		}
	 	try {
	 		
			warehouseService.createWarehouse(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "仓库添加成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "warehouse_list");
			
		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}
	}

	/**
	 * 跳转增加仓库页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/to_add")
	public String toAddWarehouse(Model model) {
		WarehouseTypeQO qo = new WarehouseTypeQO();
		List<WarehouseType> warehouseTypeList = typeService.queryList(qo);
		model.addAttribute("warehouseTypeList", warehouseTypeList);
		
		List<Province> provinceList = provinceService.queryList(new ProvinceQo());
		model.addAttribute("provinceList",provinceList);
		
		return "warehouse/warehouse_add.html";
	}

	/**
	 * 删除仓库
	 * @param command
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public String deleteWarehouse(DeleteWarehouseCommand command) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		try {
			
			warehouseService.deleteWarehouse(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "仓库删除成功", null, "warehouse_list");
			
		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}
		
	}

	/**
	 * 跳转仓库编辑页面
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping("/edit")
	public String editWarehouse(Model model, @RequestParam(value = "id", required = true) String id) {
		Warehouse warehouse = warehouseService.get(id);
		model.addAttribute("warehouse", warehouse);
		
		List<Province> provinceList = provinceService.queryList(new ProvinceQo());
		model.addAttribute("provinceList",provinceList);
		
		CityQo cityQo = new CityQo();
		cityQo.setProvinceCode(warehouse.getProvince().getCode());
		List<City> cityList = cityService.queryList(cityQo);

		AreaQo areaQo = new AreaQo();
		areaQo.setCityCode(warehouse.getCity().getCode());
		List<Area> areaList = areaService.queryList(areaQo);
		
		List<WarehouseType> warehouseTypeList = typeService.queryList(new WarehouseTypeQO());
		model.addAttribute("warehouseTypeList", warehouseTypeList);
		model.addAttribute("cityList", cityList);
		model.addAttribute("areaList", areaList);

		return "warehouse/warehouse_edit.html";
	}

	/**
	 * 更新仓库
	 * @param command
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public String updateWarehouse(ModifyWarehouseCommand command) {
		
		CommandUtil.SetOperator(getAuthUser(), command);
		
		try {
			
			warehouseService.updateWarehouse(command);
			return DwzJsonResultUtil.createJsonString(DwzJsonResultUtil.STATUS_CODE_200, "仓库修改成功", DwzJsonResultUtil.CALL_BACK_TYPE_CLOSE_CURRENT, "warehouse_list");
			
		} catch (WarehouseException e) {

			e.printStackTrace();
			return DwzJsonResultUtil.createSimpleJsonString(DwzJsonResultUtil.STATUS_CODE_300, e.getMessage());
		}
	}


}