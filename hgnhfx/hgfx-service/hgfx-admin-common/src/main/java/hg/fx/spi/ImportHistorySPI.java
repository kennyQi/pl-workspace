package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.importHistory.CreateImportHistoryCommand;
import hg.fx.domain.ImportHistory;
import hg.fx.spi.qo.ImportHistorySQO;

import java.util.List;
import java.util.Map;

/**
 * @类功能说明：商户订单导入历史SPI
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江汇购科技有限公司
 * @部门： 技术部
 * @作者： zhouwei
 * @创建时间： 2016年6月1日 上午9:58:19
 * @版本： V1.0
 */
public interface ImportHistorySPI extends BaseServiceProviderInterface {
	
	/**
	 * 
	 * @方法功能说明：添加
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 下午3:17:50
	 * @修改内容：
	 * @param command
	 * @return
	 */
	ImportHistory create(CreateImportHistoryCommand command);

	/**
	 * 
	 * @方法功能说明：条件分页查询商户导入记录
	 * @修改者名字：zhouwei
	 * @修改时间：2016年6月1日 上午10:02:34
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	Pagination<ImportHistory> queryPagination(ImportHistorySQO sqo);
	
	/**
	 * 
	 * @方法功能说明：根据条件查询操作人
	 * @修改者名字：cangs
	 * @修改时间：2016年6月3日下午5:27:43
	 * @修改内容：
	 * @参数：@param sqo
	 * @参数：@return
	 * @return:Map<String,String>
	 * @throws
	 */
	Map<String, String> queryDstributorUser(ImportHistorySQO sqo);
	
	/**
	 * 
	 * @方法功能说明：条件分页查询商户导入记录
	 * @修改者名字：cangsong
	 * @修改时间：2016年6月14日 上午16:02:34
	 * @修改内容：
	 * @param sqo
	 * @return
	 */
	List<ImportHistory> queryList(ImportHistorySQO sqo);
	
}
