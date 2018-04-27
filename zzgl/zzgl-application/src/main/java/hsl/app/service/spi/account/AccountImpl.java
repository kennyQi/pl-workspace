package hsl.app.service.spi.account;

import hsl.app.component.base.BaseSpiServiceImpl;
import hsl.app.service.local.account.AccountLocalService;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.qo.account.AccountQO;
import hsl.spi.inter.account.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AccountImpl extends BaseSpiServiceImpl<AccountDTO,AccountQO,AccountLocalService> implements AccountService{
	@Autowired
	private AccountLocalService accountLocalService;

	@Override
	protected AccountLocalService getService() {
		return accountLocalService;
	}

	@Override
	protected Class<AccountDTO> getDTOClass() {
		return AccountDTO.class;
	}

	@Override
	public AccountDTO consumptionAccount(AccountCommand command) throws AccountException {
		return accountLocalService.consumptionAccount(command);
	}
	public AccountDTO queryUnique(AccountQO qo){
		return accountLocalService.queryUniques(qo);
	}

	@Override
	public String refundAccount(RefundCommand command)
			throws AccountException {
		return accountLocalService.refundAccount(command);
	}

	@Override
	public String TimerRefundAccount(RefundCommand command) {
		return accountLocalService.TimerRefundAccount(command);
	}
}
