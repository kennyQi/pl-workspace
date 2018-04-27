package hsl.spi.inter.account;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.qo.account.AccountQO;
import hsl.spi.inter.BaseSpiService;
public interface AccountService extends BaseSpiService<AccountDTO,AccountQO>{
	/**
	 * 消费余额
	 */
	public AccountDTO  consumptionAccount(AccountCommand command)throws AccountException ;
	/**
	 * 余额退款
	 */
	public String  refundAccount(RefundCommand command)throws AccountException ;
	/**
	 * 定时器退款
	 */
	public String TimerRefundAccount(RefundCommand command);
}
