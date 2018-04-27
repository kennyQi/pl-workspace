package slfx.mp.spi.inter;

import slfx.mp.command.admin.CreatePlatformPolicyCommand;
import slfx.mp.command.admin.ModifyPlatformPolicyCommand;
import slfx.mp.command.admin.StartPlatformPolicyCommand;
import slfx.mp.command.admin.StopPlatformPolicyCommand;
import slfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
import slfx.mp.qo.PlatformPolicyQO;

public interface AdminSalePolicyService extends BaseMpSpiService<SalePolicySnapshotDTO, PlatformPolicyQO> {
	
	/**
	 * 增加平台政策
	 * 
	 * @param command
	 */
	public void createPlatformPolicy(CreatePlatformPolicyCommand command);
	
	/**
	 * 修改平台政策
	 * 
	 * @param command
	 */
	public void modifyPlatformPolicy(ModifyPlatformPolicyCommand command);

	/**
	 * 启用平台政策
	 * 
	 * @param command
	 */
	public void startPlatformPolicy(StartPlatformPolicyCommand command);
	
	/**
	 * 停用平台政策
	 * 
	 * @param command
	 */
	public void stopPlatformPolicy(StopPlatformPolicyCommand command);
	
}
