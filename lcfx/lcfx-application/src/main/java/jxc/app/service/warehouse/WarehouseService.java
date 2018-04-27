package jxc.app.service.warehouse;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateWarehouseCommand;
import hg.pojo.command.DeleteWarehouseCommand;
import hg.pojo.command.ModifyWarehouseCommand;
import hg.pojo.exception.WarehouseException;
import hg.pojo.qo.WarehouseQO;
import hg.system.model.meta.Area;
import hg.system.model.meta.City;
import hg.system.model.meta.Province;
import hg.system.service.AreaService;
import hg.system.service.CityService;
import hg.system.service.ProvinceService;

import java.util.List;

import jxc.app.dao.warehouse.WarehouseDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouse.WarehouseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class WarehouseService extends BaseServiceImpl<Warehouse, WarehouseQO, WarehouseDao> {
	@Autowired
	private WarehouseDao warehouseDao;

	@Autowired
	private AreaService areaService;

	@Autowired
	private CityService cityService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private WarehouseTypeService warehouseTypeService;

	@Autowired
	private JxcLogger logger;

	/**
	 * 添加仓库
	 * 
	 * @param command
	 * @return
	 * @throws WarehouseException
	 */
	public void createWarehouse(CreateWarehouseCommand command) throws WarehouseException {

		// 判断仓库名称是否存在
		if (warehouseNameIsExisted(command.getName(), null, true)) {

			// 仓库名称存在不允许创建
			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSE_NAME_REPEAT, "该仓库名称已存在");
		}

		// 查询出省市区
		Province province = provinceService.get(command.getProvinceId());
		City city = cityService.get(command.getCityId());
		Area area = areaService.get(command.getAreaId());

		// 查询出仓库类型
		WarehouseType type = warehouseTypeService.get(command.getTypeId());

		// 仓库名称不存在允许创建
		Warehouse warehouse = new Warehouse();
		warehouse.createWarehouse(command, province, city, area, type);
		this.save(warehouse);
		logger.debug(this.getClass(), "czh", command.getOperatorName() + "新增仓库 " + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	/**
	 * 修改仓库
	 * 
	 * @param command
	 * @return
	 * @throws WarehouseException
	 */
	public void updateWarehouse(ModifyWarehouseCommand command) throws WarehouseException {

		// 判断仓库名称是否重复
		if (warehouseNameIsExisted(command.getName(), command.getWarehouseId(), false)) {

			// 仓库名称重复不允许修改
			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSE_NAME_REPEAT, "该仓库名称已存在");
		}

		// 查询出省市区
		Province province = provinceService.get(command.getProvinceId());
		City city = cityService.get(command.getCityId());
		Area area = areaService.get(command.getAreaId());

		// 仓库名称不重复、没有使用，可以修改
		Warehouse warehouse = this.get(command.getWarehouseId());
		warehouse.modifyWarehouse(command, province, city, area);
		this.update(warehouse);
		logger.debug(this.getClass(), "czh", command.getOperatorName() + "修改仓库 " + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	/**
	 * 删除仓库
	 * 
	 * @param command
	 * @throws WarehouseException
	 */
	public void deleteWarehouse(DeleteWarehouseCommand command) throws WarehouseException {

		// 判断仓库是否被引用（二期）
		if (checkWarehouseIsUse()) {
			// 仓库已使用，不允许删除
			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSE_USE, "仓库已使用,不能删除");
		}

		Warehouse warehouse = this.get(command.getWarehouseId());
		warehouse.setStatusRemoved(true);
		logger.debug(this.getClass(), "czh", command.getOperatorName() + "删除仓库 " + warehouse.getName(), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	/**
	 * 判断仓库名是否重复
	 * 
	 * @param name
	 *            ,isCreate
	 * @return
	 */
	private boolean warehouseNameIsExisted(String name, String id, boolean isCreate) {
		WarehouseQO qo = new WarehouseQO();
		qo.setName(name);
		Warehouse w = queryUnique(qo);

		if (isCreate) {
			if (w != null) {
				return true;
			}
		} else {
			if (w != null && !id.equals(w.getId())) {
				return true;
			}
		}
		return false;

	}

	public boolean checkWarehouseIsUse() {
		return false;
	}

	@Override
	protected WarehouseDao getDao() {
		return warehouseDao;
	}

}
