package plfx.jp.app.service.spi.supplier;

import hg.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.app.service.local.supplier.SupplierLocalService;
import plfx.jp.app.service.spi.base.BaseJpSpiServiceImpl;
import plfx.jp.command.admin.supplier.SupplierCommand;
import plfx.jp.pojo.dto.supplier.SupplierDTO;
import plfx.jp.qo.admin.supplier.SupplierQO;
import plfx.jp.spi.inter.supplier.SupplierService;

/**
 * 
 * @类功能说明：供应商实现类
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年7月2日下午3:07:30
 * @版本：V1.0
 *
 */
@Service("supplierService")
public class SupplierServiceImpl extends BaseJpSpiServiceImpl<SupplierDTO, SupplierQO, SupplierLocalService>  implements SupplierService{

	@Autowired
	SupplierLocalService supplierLocalService;
		
	@Override
	protected SupplierLocalService getService() {
		return supplierLocalService;
	}

	@Override
	protected Class<SupplierDTO> getDTOClass() {
		return SupplierDTO.class;
	}
	
	@Override
	public List<SupplierDTO> getSupplierList(SupplierQO o){
		return this.queryList(o);
	}
	
	@Override
	public Pagination querySupplierList(Pagination pagination) {
		return queryPagination(pagination);
	}
	
	@Override
	public boolean saveSupplier(SupplierCommand command) {
		return supplierLocalService.saveSupplier(command);
	}
		
	
	public boolean updateSupplier(SupplierCommand command){
		return supplierLocalService.updateSupplier(command);
		
	}
	
	public boolean useSupplier(SupplierCommand command){
		return supplierLocalService.useSupplier(command);
	}
	
	
	public boolean deleteSupplier(SupplierCommand command){
		return supplierLocalService.deleteSupplier(command);
	}
	
	public boolean multiUse(SupplierCommand command){
		return supplierLocalService.multiUse(command);
	}
}
