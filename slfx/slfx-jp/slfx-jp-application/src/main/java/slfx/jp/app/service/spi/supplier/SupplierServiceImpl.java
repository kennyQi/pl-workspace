package slfx.jp.app.service.spi.supplier;

import hg.common.page.Pagination;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import slfx.jp.app.component.base.BaseJpSpiServiceImpl;
import slfx.jp.app.service.local.supplier.SupplierLocalService;
import slfx.jp.command.admin.supplier.SupplierCommand;
import slfx.jp.pojo.dto.supplier.SupplierDTO;
import slfx.jp.qo.admin.supplier.SupplierQO;
import slfx.jp.spi.inter.supplier.SupplierService;

/**
 * 
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：qiuxianxiang
 * @创建时间：2014年8月4日下午3:39:50
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
