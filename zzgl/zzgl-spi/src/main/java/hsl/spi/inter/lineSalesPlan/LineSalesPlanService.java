package hsl.spi.inter.lineSalesPlan;
import hsl.pojo.command.lineSalesPlan.CreateLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.ModifyLineSalesPlanCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLSPSalesNumCommand;
import hsl.pojo.command.lineSalesPlan.UpdateLineSalesPlanStatusCommand;
import hsl.pojo.dto.lineSalesPlan.LineSalesPlanDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.LineSalesPlanQO;
import hsl.spi.inter.BaseSpiService;
/**
 * @类功能说明：
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称： 浙江票量云数据科技有限公司
 * @部门： 技术部
 * @作者： chenxy
 * @创建时间： 2015/12/1 10:15
 */
public interface LineSalesPlanService extends BaseSpiService<LineSalesPlanDTO,LineSalesPlanQO> {
	/**
	 * 添加线路的销售方案
	 * @param createLineSalesPlanCommand
	 * @return
	 */
	public LineSalesPlanDTO addLineSalesPlan(CreateLineSalesPlanCommand createLineSalesPlanCommand) throws LSPException;

	/**
	 * 修改线路方案状态
	 * @param updateLineSalesPlanStatusCommand
	 */
	public void updateLineSalesPlanStatus(UpdateLineSalesPlanStatusCommand updateLineSalesPlanStatusCommand)throws LSPException;

	/**
	 * 修改线路的销售方案信息
	 * @param modifyLineSalesPlanCommand
	 * @return
	 */
	public LineSalesPlanDTO modifyLineSalesPlan(ModifyLineSalesPlanCommand modifyLineSalesPlanCommand)throws LSPException;

	/**
	 * 删除线路销售方案
	 * @param id
	 */
	public void deleteLineSalesPlan(String id) throws LSPException;

	/**
	 * 修改销售方案的已售数量
	 * @param updateLSPSalesNumCommand
	 * @throws LSPException
	 */
	public void updateLSPSalesNum(UpdateLSPSalesNumCommand updateLSPSalesNumCommand) throws LSPException;
}
