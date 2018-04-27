package hsl.spi.inter.preferential;
import hg.common.page.Pagination;
import hsl.pojo.command.CreatePreferentialCommand;
import hsl.pojo.command.UpdatePreferentialCommand;
import hsl.pojo.dto.preferential.PreferentialDTO;
import hsl.pojo.qo.preferential.HslPreferentialQO;
import hsl.spi.inter.BaseSpiService;
/**
 * 
 * @类功能说明：首页特惠专区广告位
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015年4月27日上午10:54:15
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */
public interface HslPreferentialService extends BaseSpiService<PreferentialDTO,HslPreferentialQO>{
	/**
	 * 
	 * @方法功能说明：创建首页特惠专区广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日上午10:58:45
	 * @参数：@param command
	 * @参数：@throws LineIndexAdException
	 * @return:void
	 */
	public void createPreferential(CreatePreferentialCommand command);
	/**
	 * @throws Exception 
	 * 
	 * @方法功能说明：修改特惠专区广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日上午11:00:21
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	
	public void modifyPreferential(UpdatePreferentialCommand command) throws Exception;
	/**
	 * 
	 * @方法功能说明：删除特惠专区广告
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月27日上午11:05:29
	 * @参数：@param id
	 * @return:void
	 * @throws
	 */
	public void deletePreferential(UpdatePreferentialCommand command);
	/**
	 * 
	 * @方法功能说明：查询特惠专区列表
	 * @创建者名字：zhaows
	 * @创建时间：2015年4月28日上午8:30:27
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	/*public Pagination getPreferentialList(Pagination pagination);*/
}
