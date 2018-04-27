package plfx.jp.spi.inter.pay.balances;

import plfx.jp.command.pay.balances.CreatePayBalancesCommand;
import plfx.jp.command.pay.balances.DeletePayBalancesCommand;
import plfx.jp.command.pay.balances.UpdatePayBalancesCommand;
import plfx.jp.pojo.dto.pay.balances.PayBalancesDTO;
import plfx.jp.qo.pay.balances.PayBalancesQO;
import plfx.jp.spi.inter.BaseJpSpiService;

public interface PayBalancesService extends BaseJpSpiService<PayBalancesDTO, PayBalancesQO>{
	
	/**
	 * 
	 * @方法功能说明：api更新支付宝余额记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月25日下午2:09:15
	 * @修改内容：
	 * @参数：@param payBalancesDTO
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean updatePayBalances(PayBalancesDTO payBalancesDTO);

	/**
	 * 
	 * @方法功能说明：api更新支付宝余额记录，并判断是否需要发短信通知
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月28日上午9:04:58
	 * @修改内容：
	 * @参数：@param payBalancesDTO
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean warnPayBalances(PayBalancesDTO payBalancesDTO);

	/**
	 * 
	 * @方法功能说明：admin新增支付宝余额记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:07:47
	 * @修改内容：
	 * @参数：@param command
	 * @return:void
	 * @throws
	 */
	public void createPayBalances(CreatePayBalancesCommand command);

	/**
	 * 
	 * @方法功能说明：admin修改支付宝余额记录
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:14:24
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean adminUpdatePayBalances(UpdatePayBalancesCommand command);

	/**
	 * 
	 * @方法功能说明：admin删除支付宝余额
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月30日上午10:24:41
	 * @修改内容：
	 * @参数：@param command
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean deletePayBalances(DeletePayBalancesCommand command);

}
