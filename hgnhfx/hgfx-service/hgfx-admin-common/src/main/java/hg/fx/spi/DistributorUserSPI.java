package hg.fx.spi;


import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.distributoruser.CreateDistributorUserCommand;
import hg.fx.command.distributoruser.ModifyDistributorUserCommand;
import hg.fx.command.distributoruser.RemoveDistributorUserCommand;
import hg.fx.domain.DistributorUser;
import hg.fx.spi.qo.DistributorUserSQO;

/**
 * 商户SPI
 * @author Caihuan
 * @date   2016年6月1日
 */
public interface DistributorUserSPI extends BaseServiceProviderInterface{

	/**
	 * 查询单个 sqo.setId
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	public DistributorUser queryUnique(DistributorUserSQO sqo);
	
	/**
	 * 条件分页查询商户记录
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public Pagination<DistributorUser> queryPagination(DistributorUserSQO sqo);
	
	/**
	 * 创建商户帐号
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public DistributorUser create(CreateDistributorUserCommand command);
	
	/**
	 * 修改商户帐号信息
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public DistributorUser modify(ModifyDistributorUserCommand command);
	
	/**
	 * 逻辑删除帐号
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	public void delete(RemoveDistributorUserCommand command);
	
	/**
	 * 获取本月里程消费
	 * @author Caihuan
	 * @date   2016年6月3日
	 */
	public Long getMonthReserveInfo(String distributor);
}
