package slfx.jp.spi.inter;

import slfx.jp.command.admin.CreatePlatformPolicyCommand;
import slfx.jp.command.admin.ModifyPlatformPolicyCommand;
import slfx.jp.command.admin.StopPlatformPolicyCommand;

/**
 * 
 * @类功能说明：admin平台政策管理SERVICE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年7月31日下午5:08:51
 * @版本：V1.0
 *
 */
public interface AdminPlatformPolicyService {
	
	/**
	 * 增加平台政策
	 * @param qo
	 */
	public void createPlatformPolicy(CreatePlatformPolicyCommand command);
	
	public void modifyPlatformPolicy(ModifyPlatformPolicyCommand command);
	
	public void stopPlatformPolicy(StopPlatformPolicyCommand command);
}
