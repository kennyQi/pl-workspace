package slfx.xl.spi.inter;

import slfx.xl.pojo.command.route.CreateDayRouteCommand;
import slfx.xl.pojo.command.route.DeleteDayRouteCommand;
import slfx.xl.pojo.command.route.ModifyDayRouteCommand;
import slfx.xl.pojo.dto.route.DayRouteDTO;
import slfx.xl.pojo.exception.SlfxXlException;
import slfx.xl.pojo.qo.DayRouteQO;

/**
 * 
 * @类功能说明：线路每日行程Service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日下午3:32:38
 * @版本：V1.0
 *
 */
public interface DayRouteService extends BaseXlSpiService<DayRouteDTO, DayRouteQO>{

	/**
	 * 
	 * @方法功能说明：添加一日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月15日下午3:14:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:String 一日行程ID
	 * @throws
	 */
	public DayRouteDTO createDayRoute(CreateDayRouteCommand command) throws SlfxXlException;
	
	/**
	 * 
	 * @方法功能说明：修改一日行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:13:49
	 * @修改内容：
	 * @参数：@param command
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyDayRoute(ModifyDayRouteCommand command);
	
	/**
	 * 
	 * @方法功能说明：删除一日行程
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午3:16:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean deleteDayRoute(DeleteDayRouteCommand command);
	

}
