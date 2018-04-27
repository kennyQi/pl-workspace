package slfx.jp.spi.inter.supplier;

import hg.common.page.Pagination;

import java.util.List;

import slfx.jp.command.admin.supplier.SupplierCommand;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.inter.BaseJpSpiService;

public interface SupplierService extends BaseJpSpiService<SupplierDTO, SupplierQO> {
	/**
	 * 
	 * @方法功能说明：供应商列表
	 * @修改者名字：renfeng
	 * @修改时间：2014年8月8日下午3:18:22
	 * @修改内容：
	 * @参数：@param o
	 * @参数：@return
	 * @return:List<SupplierDTO>
	 * @throws
	 */
	public List<SupplierDTO> getSupplierList(SupplierQO o);
	
	/**
	 * 
	 * @方法功能说明：供应商列表（分页）
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月13日下午1:14:27
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination querySupplierList(Pagination pagination);
	
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
	public boolean saveSupplier(SupplierCommand command);
	
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
	public boolean updateSupplier(SupplierCommand command);
	
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
	public boolean deleteSupplier(SupplierCommand command );
	
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
	public boolean useSupplier(SupplierCommand command );
	
	/**
	 * 
	 * @方法功能说明：
	 * @修改者名字：tuhualiang
	 * @修改时间：2014年10月17日上午11:20:00
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean multiUse(SupplierCommand command );
	
	
}
