package plfx.xl.spi.inter;

import plfx.xl.pojo.command.supplier.AuditLineSupplierCommand;
import plfx.xl.pojo.command.supplier.CreateLineSupplierCommand;
import plfx.xl.pojo.command.supplier.ModifyLineSupplierCommand;
import plfx.xl.pojo.dto.LineSupplierDTO;
import plfx.xl.pojo.qo.LineSupplierQO;

/**
 * 
 * 
 *@类功能说明：线路供应商Service接口
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日下午2:06:10
 *
 */
public interface LineSupplierService extends BaseXlSpiService<LineSupplierDTO, LineSupplierQO>{


	/**
	 * 新增线路供应商
	 * @param command
	 * @return
	 */
	public boolean createLineSupplier(CreateLineSupplierCommand command);
	
	/**
	 * 修改线路供应商
	 * @param command
	 * @return
	 */
	public boolean modifyLineSupplier(ModifyLineSupplierCommand command);
	
	/**
	 * 审核线路供应商
	 * @param command
	 * @return
	 */
	public boolean auditLineSupplier(AuditLineSupplierCommand command);
}
