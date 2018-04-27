package jxc.app.service.warehouse;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateWarehouseTypeCommand;
import hg.pojo.command.DeleteWarehouseTypeCommand;
import hg.pojo.command.ModifyWarehouseTypeCommand;
import hg.pojo.exception.WarehouseException;
import hg.pojo.qo.WarehouseQO;
import hg.pojo.qo.WarehouseTypeQO;

import java.util.List;

import jxc.app.dao.warehouse.WarehouseTypeDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.warehouse.Warehouse;
import jxc.domain.model.warehouse.WarehouseType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;

@Service
@Transactional
public class WarehouseTypeService extends BaseServiceImpl<WarehouseType, WarehouseTypeQO, WarehouseTypeDao>{
	@Autowired
	private WarehouseTypeDao warehouseTypeDao;
	
	@Autowired
	private WarehouseService warehouseService;
	
	@Autowired
	private JxcLogger logger;
	
	@Override
	protected WarehouseTypeDao getDao() {
		return warehouseTypeDao;
	}

	/**
	 * 新建仓库类型
	 * @param command
	 * @return
	 * @throws WarehouseException 
	 */
	public void createWarehouseType(CreateWarehouseTypeCommand command) throws WarehouseException{
		
		//判断仓库类型名称是否存在	
		if (typeNameIsExisted(command.getName(),null, true)) {
			
			// 仓库类型名称存在不允许创建
			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSETYPE_NAME_REPEAT, "该仓库类型名称已存在");
		}
		
		// 仓库类型名称不存在允许创建
		WarehouseType warehouseType = new WarehouseType();
		warehouseType.createWarehouseType(command);
		this.save(warehouseType);
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"新增仓库类型 " + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	/**
	 * 更新仓库类型
	 * @param command
	 * @return
	 * @throws WarehouseException 
	 */
	public void updateWarehouseType(ModifyWarehouseTypeCommand command) throws WarehouseException{
		
		//判断仓库类型名称是否重复	
//		if (typeNameIsExisted(command.getName(),command.getWarehouseTypeId(),false)) {
//			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSETYPE_NAME_REPEAT, "该仓库类型名称已存在");
//		}
			
		//仓库类型存在，不重名，可以更新
		WarehouseType warehouseType =this.get(command.getWarehouseTypeId());
		warehouseType.modifyWarehouseType(command);
		this.update(warehouseType);
		logger.debug(this.getClass(), "czh", command.getOperatorName()+"修改仓库类型 " + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}
	
	/**
	 * 删除仓库类型
	 * @param command
	 * @throws WarehouseException 
	 */
	public void deleteWarehouseType(DeleteWarehouseTypeCommand command) throws WarehouseException {
		
		//判断仓库类型是否被引用
		if (checkWarehouseTypeIsUse(command.getWarehouseTypeId())) {

			// 仓库被引用则不允许删除
			throw new WarehouseException(WarehouseException.RESULT_WAREHOUSETYPE_USE, "该仓库类型被使用，不能删除");
		}
		
		// 仓库类型未被引用，则可以删除
		WarehouseType type=this.get(command.getWarehouseTypeId());
		type.setStatusRemoved(true);
		update(type);
		logger.debug(this.getClass(), "czh", command.getOperatorName()	+ "删除仓库类型 " + type.getName(),command.getOperatorName(), command.getOperatorType(),	command.getOperatorAccount(), "");

	}
	
	
	/**
	 * 判断仓库类型是否使用
	 * @param warehouseTypeId
	 * @return
	 */
	public boolean checkWarehouseTypeIsUse(String  warehouseTypeId) {
		WarehouseQO qo = new WarehouseQO();
		qo.setWarehouseTypeId(warehouseTypeId);
		List<Warehouse>  list = warehouseService.queryList(qo);
		if(list.size() > 0){
			//true表示已在使用
			return true;
		}
		
		return false;
	}
	
	/**
	 * 判断仓库类型名称是否存在
	 * @param name,isCreate
	 * @return
	 */
	private boolean typeNameIsExisted(String name, String id, boolean isCreate) {
		WarehouseTypeQO qo = new WarehouseTypeQO();
		qo.setName(name);
		WarehouseType type= queryUnique(qo);

		if (isCreate) {
			if (type != null) {
				return true;
			}
		} else {
			if (type != null && !id.equals(type.getId())) {
				return true;
			}
		}
		return false;

	}}
