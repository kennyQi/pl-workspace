package jxc.app.service.distributor;

import java.util.List;

import hg.common.component.BaseServiceImpl;
import hg.pojo.command.distributor.ChangeStatusDistributorCommand;
import hg.pojo.command.distributor.CreateDistributorCommand;
import hg.pojo.command.distributor.ModifyDistributorCommand;
import hg.pojo.command.distributor.RemoveDistributorCommand;
import hg.pojo.exception.LcfxException;
import hg.pojo.exception.SettingException;
import hg.pojo.qo.BrandQO;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.DistributorQo;
import hg.pojo.qo.MileOrderQo;
import jxc.app.dao.system.DistributorDao;
import jxc.app.dao.system.DistributorDao;
import jxc.app.util.JxcLogger;
import jxc.domain.model.distributor.Distributor;
import jxc.domain.model.product.Brand;
import jxc.domain.util.CodeUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistributorService extends BaseServiceImpl<Distributor, DistributorQo, DistributorDao> {
	@Autowired
	private DistributorDao distributorDao;
	@Autowired
	private MileOrderService mileOrderService;

	@Override
	protected DistributorDao getDao() {
		return distributorDao;
	}

	@Autowired
	private JxcLogger logger;

	@Transactional(rollbackFor = Exception.class)
	public void create(CreateDistributorCommand command) throws LcfxException {
		if (nameIsExisted(command.getName(), null)) {
			throw new LcfxException(null, "分销商名称已存在");
		}
		Distributor d = new Distributor();
		command.setCode(CodeUtil.getDistributorCode());
		d.create(command);

		save(d);
	}

	public void remove(RemoveDistributorCommand command) throws LcfxException {
		List<String> ids = command.getIds();
		for (String id : ids) {
			MileOrderQo qo = new MileOrderQo();
			qo.setDistributorId(id);
			Integer count = mileOrderService.queryCount(qo);
			if (count > 0) {
				throw new LcfxException(null, "分销商被使用，不能删除");
			}
		}

		for (String id : ids) {
			deleteById(id);

		}

	}

	public void modify(ModifyDistributorCommand command) throws LcfxException {
		Distributor d = get(command.getId());
		if (nameIsExisted(command.getName(), command.getId())) {
			throw new LcfxException(null, "分销商名称已存在");
		}

		d.modify(command);

		update(d);

	}

	@Transactional(rollbackFor = Exception.class)
	public void changeStatus(ChangeStatusDistributorCommand command) throws LcfxException {
		List<String> ids = command.getIds();
		if (command.getStatus() == Distributor.STATUS_DISABLE) {
			for (String id : ids) {
				MileOrderQo qo = new MileOrderQo();
				qo.setDistributorId(id);
				Integer count = mileOrderService.queryCount(qo);
				if (count > 0) {
					throw new LcfxException(null, "分销商被使用，不能禁用");
				}
			}
		}
		for (String id : ids) {
			Distributor distributor = get(id);
			distributor.setStatus(command.getStatus());
			update(distributor);
		}

	}

	public boolean nameIsExisted(String name, String modifyId) {
		// 名称唯一
		DistributorQo qo = new DistributorQo();
		qo.setName(name);
		qo.setNameLike(false);
		Distributor d = queryUnique(qo);
		// 添加时
		if (modifyId == null) {
			if (d != null) {
				return true;
			}
			return false;
		} else {// 修改时
			if (d != null && !modifyId.equals(d.getId())) {
				return true;
			}
			return false;
		}

	}

	// public void createDistributor(CreateDistributorCommand command) throws
	// SettingException {
	// if (distributorNameIsExisted(command.getDistributorName(), null)) {
	// throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST,
	// "结款方式已存在");
	// }
	//
	// Distributor distributor = new Distributor();
	// distributor.createDistributor(command);
	// save(distributor);
	//
	// logger.debug(this.getClass(), "wkq", "添加结款方式:" +
	// command.getDistributorName(), command.getOperatorName(),
	// command.getOperatorType(),
	// command.getOperatorAccount(), "");
	//
	// }
	//
	// public void modifyDistributor(ModifyDistributorCommand command) throws
	// SettingException {
	// if (distributorNameIsExisted(command.getDistributorName(),
	// command.getId())) {
	// throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST,
	// "结款方式已存在");
	// }
	//
	// Distributor distributor = get(command.getId());
	// distributor.modifyDistributor(command);
	// update(distributor);
	//
	// logger.debug(this.getClass(), "wkq", "修改结款方式:" +
	// command.getDistributorName(), command.getOperatorName(),
	// command.getOperatorType(),
	// command.getOperatorAccount(), "");
	//
	// }
	//
	// public void removeDistributor(RemoveDistributorCommand command) throws
	// SettingException {
	// if (distributorIsUsing(command.getId())) {
	// throw new SettingException(SettingException.PAYMENT_METHOD_NAME_EXIST,
	// "结款方式已使用");
	// }
	//
	// DistributorQo qo = new DistributorQo();
	// qo.setId(command.getId());
	// Distributor distributor = queryUnique(qo);
	// distributor.removeDistributor(command);
	// update(distributor);
	//
	// logger.debug(this.getClass(), "wkq", "删除结款方式:" + command.getId(),
	// command.getOperatorName(), command.getOperatorType(),
	// command.getOperatorAccount(),
	// "");
	//
	// }
	//
	// private boolean distributorIsUsing(String id) {
	// return false;
	// }
	//
	// private boolean distributorNameIsExisted(String distributorName, String
	// id) {
	// DistributorQo qo = new DistributorQo();
	// qo.setDistributorName(distributorName);
	// Distributor distributor = queryUnique(qo);
	// if (id == null) {
	// if (distributor != null) {
	// return true;
	// }
	// } else {
	// if (distributor != null && !id.equals(distributor.getId())) {
	// return true;
	// }
	// }
	// return false;
	// }
}
