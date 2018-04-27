/**
 * @文件名称：JfSystemService.java
 * @类路径：hgtech.jfadmin.service
 * @描述：TODO
 * @作者：pengel
 * @时间：2014年10月28日上午9:52:45
 */
package hgtech.jfadmin.service;

import java.util.List;

import hgtech.jf.tree.WithChildren;
import hgtech.jfaccount.Domain;
import hgtech.jfaccount.JfAccountType;
import hgtech.jfaccount.TradeType;

/**
 * @类功能说明：系统服务
 * @类修改者：
 * @修改日期：2014年10月28日上午9:52:45
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：pengel
 * @创建时间：2014年10月28日上午9:52:45
 *
 */
public interface JfSystemService {
	/**
	 * @方法功能说明：机构查看
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:39:07
	 * @修改内容：
	 * @参数：@return
	 * @return:List<Domain>
	 * @throws
	 */
	public List<Domain> getSystemList();
    /**
     * @方法功能说明：交易类型查看
     * @修改者名字：pengel
     * @修改时间：2014年11月7日下午2:39:59
     * @修改内容：
     * @参数：@return
     * @return:List<TradeType>
     * @throws
     */
	public List<TradeType> getTradeList();
	/**
	 * @方法功能说明：账户类型查看
	 * @修改者名字：pengel
	 * @修改时间：2014年11月7日下午2:40:20
	 * @修改内容：
	 * @参数：@return
	 * @return:List<JfAccountType>
	 * @throws
	 */
	public List<JfAccountType> getAccountList();

}
