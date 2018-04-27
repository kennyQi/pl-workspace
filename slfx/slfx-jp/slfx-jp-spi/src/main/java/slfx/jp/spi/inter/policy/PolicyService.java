package slfx.jp.spi.inter.policy;

import hg.common.page.Pagination;
import slfx.jp.command.admin.policy.PolicyCommand;
import slfx.jp.pojo.dto.policy.PolicyDTO;
import slfx.jp.qo.admin.policy.PolicyQO;
import slfx.jp.spi.inter.BaseJpSpiService;

/**
 * 
 * @类功能说明： 价格政策的service接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月4日下午3:18:48
 * @版本：V1.0
 *
 */
public interface PolicyService extends BaseJpSpiService<PolicyDTO, PolicyQO>{
	/**
	 * 
	 * @方法功能说明：查询价格政策列表
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月4日下午3:18:46
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
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月4日下午3:20:43
	 * @修改内容：
	 * @参数：@param policyQO
	 * @参数：@return
	 * @return:PlatformOrderDetailDTO
	 * @throws
	 */
	public PolicyDTO queryPolicyDetail(PolicyQO policyQO);
	
	/**
	 * 
	 * @方法功能说明：保存价格政策
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月7日上午9:21:23
	 * @修改内容：
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean savePolicy(PolicyCommand command);
	
	/**
	 * 
	 * @方法功能说明： 发布价格政策
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月7日上午9:58:04
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
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月7日上午10:31:11
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean cancelPolicy(PolicyCommand command);
	
	/**
	 * 
	 * @方法功能说明： 获取生效的政策：一般需要三个参数：生效时间、供应商、经销商
	 * @修改者名字：qiuxianxiang
	 * @修改时间：2014年8月13日上午10:54:13
	 * @修改内容：
	 * @参数：@param policyQO
	 * @参数：@return
	 * @return:PolicyDTO
	 * @throws
	 */
	public PolicyDTO getEffectPolicy(PolicyQO policyQO);
}
