package hsl.spi.inter.lineSalesPlan.order;
import hsl.pojo.command.lineSalesPlan.order.CreateLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.ModifyLSPOrderPayInfoCommand;
import hsl.pojo.command.lineSalesPlan.order.RefundLSPOrderCommand;
import hsl.pojo.command.lineSalesPlan.order.UpdateLSPOrderStatusCommand;
import hsl.pojo.dto.lineSalesPlan.order.LSPOrderDTO;
import hsl.pojo.exception.LSPException;
import hsl.pojo.qo.lineSalesPlan.order.LSPOrderQO;
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
public interface LSPOrderService extends BaseSpiService<LSPOrderDTO,LSPOrderQO> {
	/**
	 * 创建线路销售方案订单
	 * @param createLSPOrderCommand
	 * @return
	 */
	public LSPOrderDTO createLSPOrder(CreateLSPOrderCommand createLSPOrderCommand)throws LSPException;

	/**
	 * 更新订单状态
	 * @param updateLSPOrderStatusCommand
	 * @throws LSPException
	 */
	public void updateLSPOrderStatus(UpdateLSPOrderStatusCommand updateLSPOrderStatusCommand)throws LSPException;

	/**
	 * 修改LSP订单的支付信息
	 * @param modifyLSPOrderPayInfoCommand
	 * @throws LSPException
	 */
	public void modifyLSPOrderPayInfo(ModifyLSPOrderPayInfoCommand modifyLSPOrderPayInfoCommand) throws Exception;

	/**
	 * 支付完成后检查是否组团成功。
	 * 仅仅是团购订单检查
	 * @param dealerOrderNo
	 * @throws LSPException
	 */
	public void checkLspISGroupSuc(String dealerOrderNo) throws LSPException;

	/**
	 * 退款成功
	 * @param result_details
	 * @throws LSPException
	 */
	public void refundLSPOrder(String result_details)throws LSPException;
}
