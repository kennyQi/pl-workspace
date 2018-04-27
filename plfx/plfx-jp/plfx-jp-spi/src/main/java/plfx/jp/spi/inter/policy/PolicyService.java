package plfx.jp.spi.inter.policy;

import hg.common.page.Pagination;
import plfx.jp.command.admin.policy.PolicyCommand;
import plfx.jp.pojo.dto.policy.PolicyDTO;
import plfx.jp.qo.admin.policy.PolicyQO;
import plfx.jp.spi.inter.BaseJpSpiService;

/**
 * 
 * @类功能说明：价格政策的service接口
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午2:59:32
 * @版本：V1.0
 *
 */
public interface PolicyService extends BaseJpSpiService<PolicyDTO, PolicyQO>{
	/**
	 * 
	 * @方法功能说明：查询价格政策列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:59:40
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryPolicyList(Pagination pagination);
	
	/**
	 * 
	 * @方法功能说明：价格政策详情查询
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:59:46
	 * @修改内容：
	 * @参数：@param policyQO
	 * @参数：@return
	 * @return:PolicyDTO
	 * @throws
	 */
	public PolicyDTO queryPolicyDetail(PolicyQO policyQO);
	
	/**
	 * 
	 * @方法功能说明：保存价格政策
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午2:59:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean savePolicy(PolicyCommand command);
	
	/**
	 * 
	 * @方法功能说明：发布价格政策
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:00:00
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean publicPolicy(PolicyCommand command);
	
	/**
	 * 
	 * @方法功能说明：非物理删除政策
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:00:07
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean cancelPolicy(PolicyCommand command);
	
	/**
	 * 
	 * @方法功能说明：获取生效的政策：一般需要三个参数：生效时间、供应商、经销商
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:00:16
	 * @修改内容：
	 * @参数：@param policyQO
	 * @参数：@return
	 * @return:PolicyDTO
	 * @throws
	 */
	public PolicyDTO getEffectPolicy(PolicyQO policyQO);

	/**
	 * 
	 * @方法功能说明：删除价格政策
	 * @修改者名字：yuqz
	 * @修改时间：2015年9月9日下午1:54:54
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deletePolicy(PolicyCommand command);
}
