package hg.fx.spi;

import hg.framework.common.base.BaseServiceProviderInterface;
import hg.framework.common.model.Pagination;
import hg.fx.command.mileOrder.CheckMileOrderCommand;
import hg.fx.command.mileOrder.ConfirmMileOrderCommand;
import hg.fx.command.mileOrder.ImportMileOrderCommand;
import hg.fx.domain.MileOrder;
import hg.fx.spi.qo.MileOrderSQO;

import java.util.List;

/**
 * 订单SPI
 * @author zqq
 * @date   2016年6月1日
 */
public interface MileOrderSPI extends BaseServiceProviderInterface{

	/**
	 * 查询唯一订单记录
	 * @author zqq
	 * @date   2016年6月1日
	 */
	public MileOrder queryUnique(MileOrderSQO qo);
	
	/**
	 * 条件查询订单记录
	 * @author zqq
	 * @date   2016年6月1日
	 */
	public List<MileOrder> queryList(MileOrderSQO qo);
	
	/**
	 * 条件分页查询订单记录
	 * @author zqq
	 * @date   2016年6月1日
	 */
	public Pagination<MileOrder> queryPagination(MileOrderSQO qo);

	/**
	 * 导入批量订单，检查卡号.
	 * @param importMileOrderCommand
	 * @return 错误在前的批量列表
	 */
	public ImportMileOrderCommand importBatch(
			ImportMileOrderCommand importMileOrderCommand) ;

	/**
	 * 提交批量订单。提交导入的订单。逐笔处理：预付金够，冻结预付金，生成订单和预付金变化明细，否则生成状态为取消的订单。
	 * @param importMileOrderCommand
	 * @return 
	 */
	public ImportMileOrderCommand submitBatch(ImportMileOrderCommand importMileOrderCommand);

	/**
	 * 订单批量通过、拒绝， 输入ids记录id数组，flag=true通过 false拒绝
	 * @param ids
	 * @param flag
	 * @throws Exception
	 */
	public void  batchCheck(CheckMileOrderCommand cmd, Boolean flag)throws Exception;

	/**
	 * 标记 已提交订单给外部处理
	 * @param id
	 */
	public abstract void sentOrder(String id);

	/**
	 * 设置风险控制开关
	 * @param checkAbnormal
	 */
	public abstract void setCheckAbnormal(boolean checkAbnormal);

	/**
	 * 订单批量人工确认、拒绝， 输入ids记录id数组，flag=true通过 false拒绝
	 * @param cmd
	 * @param flag
	 */
	public abstract void batchConfirm(ConfirmMileOrderCommand cmd, Boolean flag);
	
}
