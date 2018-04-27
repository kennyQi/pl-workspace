package hg.demo.member.service.spi.impl.fx;

import hg.demo.member.service.dao.hibernate.fx.OldDistributorDao;
import hg.demo.member.service.qo.hibernate.fx.OldDistributorQo;
import hg.framework.service.component.base.BaseService;
import hg.fx.command.distributor.ChangeStatusDistributorCommand;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.domain.Distributor;
import hg.fx.util.CodeUtil;
import hg.fx.util.LcfxException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OldDistributorService extends BaseService/*<Distributor, DistributorQo, DistributorDao>*/ {
	@Autowired
	private OldDistributorDao distributorDao;
	@Autowired
	private MileOrderService mileOrderService;

	protected OldDistributorDao getDao() {
		return distributorDao;
	}

	 

	@Transactional(rollbackFor = Exception.class)
	public void create(CreateDistributorCommand command) throws LcfxException {
		if (nameIsExisted(command.getCompanyName(), null)) {
			throw new LcfxException(null, "分销商名称已存在");
		}
		Distributor d = new Distributor();
		command.setCode(CodeUtil.getDistributorCode());
		d.create(command);

		getDao().save(d);
	}

 

	public void modify(ModifyDistributorCommand command) throws LcfxException {
		Distributor d = getDao(). get(command.getId());
		if (nameIsExisted(command.getName(), command.getId())) {
			throw new LcfxException(null, "分销商名称已存在");
		}

		d.modify(command);

		getDao().update(d);

	}

	@Transactional(rollbackFor = Exception.class)
	public void changeStatus(ChangeStatusDistributorCommand command) throws LcfxException {
		List<String> ids = command.getIds();
		if (command.getStatus() == Distributor.STATUS_OF_DISABLE) {
			/*for (String id : ids) {
				MileOrderQo qo = new MileOrderQo();
				qo.setDistributorId(id);
				Integer count = mileOrderService.queryCount(qo);
				if (count > 0) {
					throw new LcfxException(null, "分销商被使用，不能禁用");
				}
			}*/
		}
		for (String id : ids) {
			Distributor distributor =getDao(). get(id);
			distributor.setStatus(command.getStatus());
			getDao().update(distributor);
		}

	}

	public boolean nameIsExisted(String name, String modifyId) {
		// 名称唯一
		OldDistributorQo qo = new OldDistributorQo();
		qo.setName(name);
		qo.setNameLike(false);
		Distributor d = getDao(). queryUnique(qo);
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



	public Distributor queryUnique(OldDistributorQo qo) {
		return getDao().queryUnique(qo);
	}



	public Distributor get(String distributorId) {
		return getDao().get(distributorId);
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
