package hg.fx.spi;

import java.util.List;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.distributor.CreateDistributorCommand;
import hg.fx.command.distributor.ModifyDistributorCommand;
import hg.fx.domain.Distributor;
import hg.fx.spi.qo.DistributorSQO;

/**
 * 商户SPI
 * @author Caihuan
 * @date   2016年6月1日
 */
public interface DistributorSPI extends BaseServiceProviderInterface{

	/**
	 * 查询唯一商户记录
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public Distributor queryUnique(DistributorSQO qo);
	
	/**
	 * 条件查询商户记录
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public List<Distributor> queryList(DistributorSQO qo);
	
	/**
	 * 条件分页查询商户记录
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public Pagination<Distributor> queryPagination(DistributorSQO sqo);
	
	/**
	 * 商户批量启用禁用，输入ids记录id数组，flag=true启用 false禁用
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public List<Distributor> batchEnableDisable(String[] ids, Boolean flag) throws Exception;
	
	/**
	 * 创建商户信息
	 * @author Caihuan
	 * @date   2016年6月2日
	 */
	public Distributor createDistributor(CreateDistributorCommand command);
	/**
	 * 修改商户信息：手机号，签名,状态
	 * @author Caihuan
	 * @date   2016年6月1日
	 */
	public Distributor modifyDistributor(ModifyDistributorCommand command);
	
	/**
	 * 检查手机号是否存在
	 * @author Caihuan
	 * @date   2016年6月8日
	 */
	public boolean checkExistPhone(String phone);
}
