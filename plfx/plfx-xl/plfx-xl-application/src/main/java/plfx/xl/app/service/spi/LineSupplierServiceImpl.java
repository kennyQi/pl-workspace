package plfx.xl.app.service.spi;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import plfx.xl.app.component.base.BaseXlSpiServiceImpl;
import plfx.xl.app.service.local.LineSupplierLocalService;
import plfx.xl.pojo.command.supplier.AuditLineSupplierCommand;
import plfx.xl.pojo.command.supplier.CreateLineSupplierCommand;
import plfx.xl.pojo.command.supplier.ModifyLineSupplierCommand;
import plfx.xl.pojo.dto.LineSupplierDTO;
import plfx.xl.pojo.qo.LineSupplierQO;
import plfx.xl.spi.inter.LineSupplierService;

/**
 * 
 * 
 *@类功能说明：线路供应商SERVICE实现
 *@类修改者：
 *@修改日期：
 *@修改说明：
 *@公司名称：浙江汇购科技有限公司
 *@部门：技术部
 *@作者：luoyun
 *@创建时间：2014年12月4日上午10:17:42
 *
 */
@Service("lineSupplierService")
public class LineSupplierServiceImpl  extends BaseXlSpiServiceImpl<LineSupplierDTO, LineSupplierQO, LineSupplierLocalService> implements LineSupplierService{

	@Resource
	private LineSupplierLocalService lineSupplierLocalService;
	
	@Override
	protected LineSupplierLocalService getService() {
		return lineSupplierLocalService;
	}

	@Override
	protected Class<LineSupplierDTO> getDTOClass() {
		return LineSupplierDTO.class;
	}

	@Override
	public boolean createLineSupplier(CreateLineSupplierCommand command) {
		return lineSupplierLocalService.createLineSupplier(command);
	}

	@Override
	public boolean modifyLineSupplier(ModifyLineSupplierCommand command) {
		return lineSupplierLocalService.updateLineSupplier(command);
	}

	@Override
	public boolean auditLineSupplier(AuditLineSupplierCommand command) {
		return lineSupplierLocalService.auditLineSupplier(command);
	}

	

}
