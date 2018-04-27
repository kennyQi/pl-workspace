package plfx.jp.spi.inter.supplier;

import hg.common.page.Pagination;

import java.util.List;

import plfx.jp.command.admin.supplier.SupplierCommand;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.BaseJpSpiService;
/**
 * 
 * @类功能说明：供应商service
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:04:47
 * @版本：V1.0
 *
 */
public interface SupplierService extends BaseJpSpiService<SupplierDTO, SupplierQO> {
	/**
	 * 
	 * @方法功能说明：供应商列表
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:04:41
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
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:05:05
	 * @修改内容：
	 * @参数：@param pagination
	 * @参数：@return
	 * @return:Pagination
	 * @throws
	 */
	public Pagination querySupplierList(Pagination pagination);
	
	/**
	 * 
	 * @方法功能说明：新增供应商
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:05:13
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean saveSupplier(SupplierCommand command);
	
	/**
	 * 
	 * @方法功能说明：修改供应商信息
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:05:28
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updateSupplier(SupplierCommand command);
	
	/**
	 * 
	 * @方法功能说明：删除供应商
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:05:45
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deleteSupplier(SupplierCommand command );
	
	/**
	 * 
	 * @方法功能说明：修改供应商状态
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:06:15
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean useSupplier(SupplierCommand command );
	
	/**
	 * 
	 * @方法功能说明：批量修改供应商状态
	 * @修改者名字：yuqz
	 * @修改时间：2015年7月2日下午3:06:34
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean multiUse(SupplierCommand command );
	
	
}
