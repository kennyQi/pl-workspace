package plfx.mp.app.service.local;

import hg.common.component.BaseServiceImpl;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import plfx.mp.app.dao.SalePolicySnapshotDAO;
import plfx.mp.app.pojo.qo.SalePolicySnapshotQO;
import plfx.mp.command.admin.CreatePlatformPolicyCommand;
import plfx.mp.command.admin.ModifyPlatformPolicyCommand;
import plfx.mp.command.admin.StartPlatformPolicyCommand;
import plfx.mp.command.admin.StopPlatformPolicyCommand;
import plfx.mp.domain.model.platformpolicy.SalePolicySnapshot;

@Service
@Transactional
public class SalePolicySnapshotLocalService extends BaseServiceImpl<SalePolicySnapshot, SalePolicySnapshotQO, SalePolicySnapshotDAO> {
	
	@Autowired
	private SalePolicySnapshotDAO dao;
	
	@Override
	protected SalePolicySnapshotDAO getDao() {
		return dao;
	}
	
	/**
	 * 创建平台政策
	 * 
	 * @param command
	 */
	public void createPlatformPolicy(CreatePlatformPolicyCommand command) {
		HgLogger.getInstance().info("wuyg", "创建平台政策");
		SalePolicySnapshot salePolicySnapshot = new SalePolicySnapshot();
		salePolicySnapshot.createPlatformPolicy(command);
		getDao().save(salePolicySnapshot);
	}

	/**
	 * 平台政策修改
	 * 
	 * @param command
	 */
	public void modifyPlatformPolicy(ModifyPlatformPolicyCommand command) {
		HgLogger.getInstance().info("wuyg", "平台政策修改");
		SalePolicySnapshotQO qo=new SalePolicySnapshotQO();
		qo.setLastSnapshot(true);
		qo.setPolicyId(command.getPolicyId());
		SalePolicySnapshot salePolicySnapshot = getDao().queryUnique(qo);
		SalePolicySnapshot salePolicySnapshot2 = salePolicySnapshot.modifyPlatformPolicy(command);
		if (salePolicySnapshot2 != null) {
			getDao().update(salePolicySnapshot);
			getDao().save(salePolicySnapshot);
		} else {
			getDao().clear();
		}
	}

	/**
	 * 平台政策禁用
	 * 
	 * @param command
	 */
	public void stopPlatformPolicy(StopPlatformPolicyCommand command) {
		HgLogger.getInstance().info("wuyg", "平台政策禁用");
		SalePolicySnapshotQO qo = new SalePolicySnapshotQO();
		qo.setLastSnapshot(true);
		qo.setPolicyId(command.getPolicyId());
		SalePolicySnapshot salePolicySnapshot = getDao().queryUnique(qo);
		salePolicySnapshot.stopPlatformPolicy(command);
		getDao().update(salePolicySnapshot);
	}
	
	/**
	 * 平台政策启用
	 * 
	 * @param command
	 */
	public void startPlatformPolicy(StartPlatformPolicyCommand command) {
		HgLogger.getInstance().info("wuyg", "平台政策启用");
		SalePolicySnapshot salePolicySnapshot = getDao().get(command.getSalePolicyId());
		salePolicySnapshot.startPlatformPolicy(command);
		getDao().update(salePolicySnapshot);
	}
	
	/**
	 * 根据平台政策ID查询最新平台政策快照
	 * 
	 * @param policyId
	 * @return
	 */
	public SalePolicySnapshot getLast(String policyId) {
		HgLogger.getInstance().info("wuyg", "根据平台政策ID查询最新平台政策快照");
		if (policyId == null) return null;
		SalePolicySnapshotQO qo = new SalePolicySnapshotQO();
		qo.setPolicyId(policyId);
		qo.setLastSnapshot(true);
		return getDao().queryUnique(qo);
	}

}
