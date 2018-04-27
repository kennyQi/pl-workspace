package hg.system.service;

import hg.common.component.BaseService;
import hg.system.command.perm.CreateAuthPermCommand;
import hg.system.command.perm.ModifyAuthPermCommand;
import hg.system.command.perm.RemoveAuthPermCommand;
import hg.system.exception.HGException;
import hg.system.model.auth.AuthPerm;
import hg.system.qo.AuthPermQo;

/**
 * 
 * @类功能说明：权限_资源service接口
 * @类修改者：zzb
 * @修改日期：2014年10月31日上午11:14:11
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zzb
 * @创建时间：2014年10月31日上午11:14:11
 *
 */
public interface AuthPermService extends BaseService<AuthPerm, AuthPermQo> {

	/**
	 * @方法功能说明：删除资源
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午9:20:41
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void removeAuthPerm(RemoveAuthPermCommand command);

	/**
	 * @方法功能说明：创建资源
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午9:20:57
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@throws HGException
	 * @return:void
	 * @throws
	 */
	void createAuthPerm(CreateAuthPermCommand command) throws HGException;

	/**
	 * @方法功能说明：编辑资源
	 * @修改者名字：zzb
	 * @修改时间：2014年11月3日上午10:29:31
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	void modifyAuthPerm(ModifyAuthPermCommand command) throws HGException;
	
	
}
