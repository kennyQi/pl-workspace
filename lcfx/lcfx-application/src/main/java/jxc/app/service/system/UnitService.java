package jxc.app.service.system;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.CreateUnitCommand;
import hg.pojo.command.ModifyUnitCommand;
import hg.pojo.command.RemoveUnitCommand;
import hg.pojo.exception.ProductException;
import hg.pojo.qo.ProductQO;
import hg.pojo.qo.UnitQO;
import jxc.app.dao.system.UnitDao;
import jxc.app.service.product.ProductService;
import jxc.app.util.JxcLogger;
import jxc.domain.model.product.Product;
import jxc.domain.model.system.Unit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

@Service
public class UnitService extends BaseServiceImpl<Unit, UnitQO, UnitDao> {
	@Autowired
	private UnitDao unitDao;

	@Override
	protected UnitDao getDao() {
		return unitDao;
	}

	@Autowired
	private JxcLogger logger;

	@Autowired
	private ProductService productService;

	public Unit createUnit(CreateUnitCommand command) throws ProductException {
		if (checkNameIsExisted(command.getUnitName(),null)) {
			throw new ProductException(ProductException.UNIT_NAME_EXIST, "单位名称已存在");
		}
		Unit unit = new Unit();
		unit.createUnit(command);
		save(unit);
		logger.debug(this.getClass(), "wkq", command.getOperatorName() + "添加计量单位" + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
		return unit;

	}

	public void modifyUnit(ModifyUnitCommand command) throws ProductException {
		if (checkNameIsExisted(command.getUnitName(),command.getId())) {
			throw new ProductException(ProductException.UNIT_NAME_EXIST, "单位名称已存在");
		}
		Unit unit = get(command.getId());
		unit.modifyUnit(command);
		update(unit);
		logger.debug(this.getClass(), "wkq", command.getOperatorName() + "修改计量单位" + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	public void removeUnit(RemoveUnitCommand command) throws ProductException {
		if (checkUnitIsUsing(command.getId())) {
			throw new ProductException(1, "删除失败，单位被使用");
		}
		Unit unit = new Unit();
		unit.remove(command);
		update(unit);

		logger.debug(this.getClass(), "wkq", command.getOperatorName() + "删除计量单位" + JSON.toJSONString(command), command.getOperatorName(), command.getOperatorType(), command.getOperatorAccount(), "");
	}

	public boolean checkNameIsExisted(String n, String id) {
		UnitQO qo = new UnitQO();
		qo.setName(n);
		Unit unit = queryUnique(qo);
		if (id == null) {
			if (unit != null) {
				return true;
			}
		} else {
			if (unit != null && !id.equals(unit.getId())) {
				return true;
			}
		}
		return false;
	}

	private boolean checkUnitIsUsing(String id) {
		ProductQO qo = new ProductQO();
		qo.setUnitId(id);
		Product product = productService.queryUnique(qo);
		if (product != null) {
			return true;
		}
		return false;
	}

}
