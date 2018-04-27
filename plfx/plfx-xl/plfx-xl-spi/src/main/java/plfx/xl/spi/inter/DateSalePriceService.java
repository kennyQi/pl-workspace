package plfx.xl.spi.inter;

import plfx.xl.pojo.command.price.BatchModifyDateSalePriceCommand;
import plfx.xl.pojo.command.price.CreateDateSalePriceCommand;
import plfx.xl.pojo.command.price.ModifyDateSalePriceCommand;
import plfx.xl.pojo.dto.price.DateSalePriceDTO;
import plfx.xl.pojo.qo.DateSalePriceQO;


/**
 * 
 * @类功能说明：价格日历Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月17日下午4:43:12
 * @版本：V1.0
 *
 */
public interface DateSalePriceService extends BaseXlSpiService<DateSalePriceDTO, DateSalePriceQO>{

	/**
	 * 
	 * @方法功能说明：修改单天团期信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午1:59:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean modifyDateSalePrice(ModifyDateSalePriceCommand command);
	
	/**
	 * 
	 * @方法功能说明：批量修改多天团期信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月18日下午5:17:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean batchModifyDateSalePrice(BatchModifyDateSalePriceCommand command);
	
	/**
	 * 
	 * @方法功能说明：新增单天团期
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月19日下午4:53:06
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public Boolean createDateSalePrice(CreateDateSalePriceCommand command);
	
	/**
	 * 
	 * @方法功能说明：计算线路某天的平均价格
	 * @修改者名字：luoyun
	 * @修改时间：2015年1月5日下午5:15:45
	 * @修改内容：
	 * @参数：@param dateSalePriceQO
	 * @参数：@return
	 * @return:Double
	 * @throws
	 */
	public Double countDailyAverPrice(DateSalePriceQO dateSalePriceQO);

	/**
	 * 
	 * @方法功能说明：删除单天团期
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月15日上午11:02:34
	 * @修改内容：
	 * @参数：@param id
	 * @参数：@return
	 * @return:void
	 * @throws
	 */
	public void deleteDateSalePrice(String id);
	
}
