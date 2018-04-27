package hsl.app.service.spi.lineSalesPlan;

import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.lineSalesPlan.LineSalesPlanLocalService;
import hsl.domain.model.lineSalesPlan.LineSalesPlan;
import hsl.pojo.command.lineSalesPlan.CreateLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.ModifyLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLSPSalesNumCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanConstant;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.spi.inter.lineSalesPlan.LineSalesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 10:39
 */
@Service
public class LineSalePlanServiceImpl extends BaseSpiServiceImpl<LineSalesPlanDTO,LineSalesPlanQO,LineSalesPlanLocalService> implements LineSalesPlanService{
	@Autowired
	private LineSalesPlanLocalService lineSalesPlanLocalService;
	@Override
	protected LineSalesPlanLocalService getService() {
		return lineSalesPlanLocalService;
	}
	@Override
	protected Class<LineSalesPlanDTO> getDTOClass() {
		return LineSalesPlanDTO.class;
	}

	@Override
	public LineSalesPlanDTO addLineSalesPlan(CreateLineSalesPlanCommand createLineSalesPlanCommand) throws LSPException {
		LineSalesPlanDTO lineSalesPlanDTO=lineSalesPlanLocalService.addLineSalesPlan(createLineSalesPlanCommand);
		return lineSalesPlanDTO;
	}

	@Override
	public void updateLineSalesPlanStatus(UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand)throws LSPException {
		lineSalesPlanLocalService.updateLineSalesPlanStatus(updateLineSalesPlanStatusCommand);
	}

	@Override
	public LineSalesPlanDTO modifyLineSalesPlan(ModifyLineSalesPlanCommand modifyLineSalesPlanCommand) throws LSPException{
		LineSalesPlanDTO lineSalesPlanDTO=lineSalesPlanLocalService.modifyLineSalesPlan(modifyLineSalesPlanCommand);
		return lineSalesPlanDTO;
	}

	@Override
	public void deleteLineSalesPlan(String id) throws LSPException {
		LineSalesPlanQO lineSalesPlanQO=new LineSalesPlanQO();
		lineSalesPlanQO.setId(id);
		LineSalesPlan lineSalesPlan=lineSalesPlanLocalService.queryUnique(lineSalesPlanQO);
		if(lineSalesPlan==null){
			throw new LSPException("删除活动不存在");
		}
		if(lineSalesPlan.getLineSalesPlanStatus().getStatus()!= LineSalesPlanConstant.LSP_STATUS_NOCHECK){
			throw new LSPException("非未审核状态不能删除");
		}
		lineSalesPlanLocalService.deleteById(id);
	}

	@Override
	public void updateLSPSalesNum(UpdateLSPSalesNumCommand updateLSPSalesNumCommand) throws LSPException {
		lineSalesPlanLocalService.updateLSPSalesNum(updateLSPSalesNumCommand);
	}
}
