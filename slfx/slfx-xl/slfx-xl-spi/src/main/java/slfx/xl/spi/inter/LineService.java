package slfx.xl.spi.inter;

import java.util.List;

import slfx.xl.pojo.command.line.AuditLineCommand;
import slfx.xl.pojo.command.line.CopyLineCommand;
import slfx.xl.pojo.command.line.CreateLineCommand;
import slfx.xl.pojo.command.line.GroundingLineCommand;
import slfx.xl.pojo.command.line.ModifyLineCommand;
import slfx.xl.pojo.command.line.ModifyLineMinPriceCommand;
import slfx.xl.pojo.command.line.UnderCarriageLineCommand;
import slfx.xl.pojo.command.line.XLUpdateLineMessageApiCommand;
import slfx.xl.pojo.command.route.ModifyLineRouteInfoCommand;
import slfx.xl.pojo.dto.line.LineDTO;
import slfx.xl.pojo.qo.LineQO;

/**
 * 
 * @类功能说明：线路service
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月2日下午5:42:58
 * @版本：V1.0
 *
 */
public interface LineService extends BaseXlSpiService<LineDTO, LineQO>{
	
	/**
	 * 新增线路基本信息
	 * @param command
	 * @return
	 */
	public Boolean createLine(CreateLineCommand command);
	
	/**
	 * 
	 * @方法功能说明：修改线路行程信息
	 * @修改者名字：luoyun
	 * @修改时间：2014年12月16日下午2:28:29
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:Boolean
	 * @throws
	 */
	public Boolean modifyLineRouteInfo(ModifyLineRouteInfoCommand command);

	/**
	 * 修改线路基本信息
	 * @param command
	 * @return
	 */
	public Boolean modifyLine(ModifyLineCommand command);
	
	/**
	 * 审核线路基本信息
	 * @param command
	 * @return
	 */
	public boolean auditLine(AuditLineCommand command);
	
	/**
	 * 上架线路基本信息
	 * @param command
	 * @return
	 */
	public boolean groundingLine(GroundingLineCommand command);
	
	/**
	 * 下架线路基本信息
	 * @param command
	 * @return
	 */
	public boolean underCarriageLine(UnderCarriageLineCommand command);
	
	/**
	 * 复制线路基本信息
	 * @param command
	 * @return
	 */
	public boolean copyLine(CopyLineCommand command);
	
/*---------------------admin使用上面，shop使用下面----------------------*/
	
	/**
	 * 
	 * @方法功能说明：商城查询线路list
	 * @修改者名字：tandeng
	 * @修改时间：2014年12月23日上午11:04:44
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:List<LineDTO>
	 * @throws
	 */
	public List<LineDTO> shopQueryLineList(LineQO qo);
	/**
	 * 
	 * @方法功能说明：商城查询线路总页数
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年1月30日下午3:25:20
	 * @修改内容：
	 * @参数：@param qo
	 * @参数：@return
	 * @return:Integer
	 * @throws
	 */
	public Integer shopQueryLineTotalCount(LineQO qo);
	/**
	 * 
	 * @方法功能说明：线路修改通知
	 * @修改者名字：caizhenghao
	 * @修改时间：2015年2月2日下午3:51:40
	 * @修改内容：
	 * @参数：@param apiCommand
	 * @return:void
	 * @throws
	 */
	public void sendLineUpdateMessage(XLUpdateLineMessageApiCommand apiCommand);

	/**
	 * 
	 * @方法功能说明：修改团期最低价格
	 * @修改者名字：yuqz
	 * @修改时间：2015年4月8日下午4:21:53
	 * @修改内容：
	 * @参数：@param modifyLineMinPriceCommand
	 * @return:void
	 * @throws
	 */
	public void modifyLineMinPrice(ModifyLineMinPriceCommand modifyLineMinPriceCommand);

	/**
	 * 
	 * @方法功能说明：团期最低价格变化通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年5月11日下午3:21:23
	 * @修改内容：
	 * @参数：@param modifyLineMinPriceCommand
	 * @return:void
	 * @throws
	 */
	public void sendModifyLineMinPriceMessage(ModifyLineMinPriceCommand modifyLineMinPriceCommand);
}
