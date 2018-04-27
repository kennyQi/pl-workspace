package slfx.jp.spi.inter.dealer;

import hg.common.page.Pagination;
import slfx.jp.command.admin.dealer.DealerCommand;
import slfx.jp.pojo.dto.dealer.DealerDTO;
import slfx.jp.qo.admin.dealer.DealerQO;
import slfx.jp.spi.inter.BaseJpSpiService;

public interface DealerService extends BaseJpSpiService<DealerDTO, DealerQO> {
	
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月16日上午8:42:56
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
	 * @修改时间：2014年10月13日下午1:55:49
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean saveDealer(DealerCommand command);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月13日下午4:24:55
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateDealer(DealerCommand command);
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月13日下午4:41:25
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deleteDealer(DealerCommand command );
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月15日上午10:17:13
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean useDealer(DealerCommand command );
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月17日上午10:16:56
	 * @修改内容：
	 * @参数：@param ids
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean multiUse(DealerCommand command);
	
}
