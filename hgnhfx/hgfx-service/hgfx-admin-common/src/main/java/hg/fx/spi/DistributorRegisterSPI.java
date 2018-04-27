package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.DistributorRegister.CreateDistributorRegisterCommand;
import hg.fx.command.DistributorRegister.AduitDistributorRegisterCommand;
import hg.fx.domain.DistributorRegister;
import hg.fx.spi.qo.DistributorRegisterSQO;

import java.util.List;
import java.util.Map;

public interface DistributorRegisterSPI extends BaseServiceProviderInterface{

	/**
	 * 查询唯一商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public DistributorRegister queryUnique(DistributorRegisterSQO qo);
	
	/**
	 * 条件查询商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public List<DistributorRegister> queryList(DistributorRegisterSQO qo);
	
	/**
	 * 条件分页查询商户注册申请记录
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public Pagination<DistributorRegister> queryPagination(DistributorRegisterSQO sqo);
	
	
	/**
	 * 创建商户注册申请信息
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public DistributorRegister createDistributorRegister(CreateDistributorRegisterCommand command);
	/**
	 * 审核商户注册审核请求
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public List<String> aduitDistributorRegister(AduitDistributorRegisterCommand command);
	
	/**
	 * 检查手机号是否存在
	 * (包括正式商户和待审商户记录不包括已拒绝的商户记录)
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public boolean checkExistPhone(String phone);
	
	/**
	 * 检查帐号是否存在
	 * (包括正式商户和待审商户记录不包括已拒绝的商户记录)
	 * @author zqq
	 * @date   2016年7月14日
	 */
	public boolean checkExistAccount(String account);
}
