package plfx.mp.spi.inter;

import plfx.mp.command.admin.CreatePlatformPolicyCommand;
import plfx.mp.command.admin.ModifyPlatformPolicyCommand;
import plfx.mp.command.admin.StartPlatformPolicyCommand;
import plfx.mp.command.admin.StopPlatformPolicyCommand;
import plfx.mp.pojo.dto.platformpolicy.SalePolicySnapshotDTO;
import plfx.mp.qo.PlatformPolicyQO;

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
