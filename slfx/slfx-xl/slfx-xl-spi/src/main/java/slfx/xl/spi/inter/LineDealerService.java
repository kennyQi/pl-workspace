package slfx.xl.spi.inter;

import hg.common.page.Pagination;
import slfx.xl.pojo.command.dealer.AuditLineDealerCommand;
import slfx.xl.pojo.command.dealer.CreateLineDealerCommand;
import slfx.xl.pojo.command.dealer.ModifyLineDealerCommand;
import slfx.xl.pojo.dto.LineDealerDTO;
import slfx.xl.pojo.qo.LineDealerQO;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tuhualiang
 * @创建时间：2014年12月5日上午9:30:47
 * @版本：V1.0
 */
public interface LineDealerService extends BaseXlSpiService<LineDealerDTO, LineDealerQO>{
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:11
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination queryDealerList(Pagination pagination);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:16
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean saveDealer(CreateLineDealerCommand command);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:23
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateDealer(ModifyLineDealerCommand command);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:27
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	/*public boolean deleteDealer(DealerCommand command );*/
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:32
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean useDealer(AuditLineDealerCommand command );
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年12月5日上午9:31:36
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean multiUse(AuditLineDealerCommand command);
}
