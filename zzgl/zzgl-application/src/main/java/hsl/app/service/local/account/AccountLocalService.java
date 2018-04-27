package hsl.app.service.local.account;

import com.alibaba.fastjson.JSON;
import hg.common.component.BaseServiceImpl;
import hg.common.util.EntityConvertUtils;
import hg.log.util.HgLogger;
import hsl.app.dao.account.AccountConsumeSnapshotDao;
import hsl.app.dao.account.AccountDao;
import hsl.domain.model.user.account.Account;
import hsl.domain.model.user.account.AccountConsumeSnapshot;
import hsl.pojo.command.account.AccountCommand;
import hsl.pojo.command.account.RefundCommand;
import hsl.pojo.dto.account.AccountConsumeSnapshotDTO;
import hsl.pojo.dto.account.AccountDTO;
import hsl.pojo.exception.AccountException;
import hsl.pojo.qo.account.AccountConsumeSnapshotQO;
import hsl.pojo.qo.account.AccountQO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
@Transactional
public class AccountLocalService extends BaseServiceImpl<Account, AccountQO,AccountDao>{
	@Autowired
	private AccountDao accountDao;
	@Autowired
	private AccountConsumeSnapshotDao accountConsumeSnapshotDao;
	private Integer constant=100;

	public AccountDTO consumptionAccount(AccountCommand command) throws AccountException {
		HgLogger.getInstance().info("zhaows", "consumptionAccount---消费账户余额参数" + JSON.toJSONString(command));
		AccountQO accountQO=new AccountQO();
		accountQO.setIsLock(true);
		accountQO.setUserID(command.getCommandId());
		Account account=accountDao.queryUnique(accountQO);
		HgLogger.getInstance().info("zhaows","consumptionAccount---加锁->查询余额账户");
		double consumptionBalance=0.00;
		HgLogger.getInstance().info("zhaows","consumptionAccount---查询余额账户" + JSON.toJSONString(account));
		AccountDTO dto;
		AccountConsumeSnapshot consumeSnapshot;
		if(account==null){
			throw new AccountException(AccountException.NOT_ACCOUNT,"余额账户不存在");
		}else{
			AccountConsumeSnapshotQO qo=new AccountConsumeSnapshotQO();
			qo.setOrderId(command.getAccountConsumeSnapshotCommand().getOrderId());
			qo.setAccountId(account.getId());
			qo.setAccountstatus(AccountConsumeSnapshot.STATUS_ZY);
			consumeSnapshot=accountConsumeSnapshotDao.queryUnique(qo);//查询当前订单对应的快照是否存在
			HgLogger.getInstance().info("zhaows","consumptionAccount---查询消费快照" + JSON.toJSONString(consumeSnapshot));
			System.out.println("查询状态账户余额为>>>>>>>>>>>>>>>>>>>>"+account.getConsumeOrderSnapshots().size());
			AccountConsumeSnapshot accountConsumeSnapshot=new AccountConsumeSnapshot();
			if(command.getConsumption()==false&&consumeSnapshot==null) {//占用帐号余额
				for (AccountConsumeSnapshot consumeOrderSnapshots : account.getConsumeOrderSnapshots()) {
					if (consumeOrderSnapshots.getStatus() == 1) {
						consumptionBalance += consumeOrderSnapshots.getPayPrice();
					}
				}
				if (account.getBalance() - consumptionBalance < command.getCurrentMoney()) {
					throw new AccountException(AccountException.ALREADY_INSUFFICIENT, "余额账户不足");
				}
				accountConsumeSnapshot.create(command.getAccountConsumeSnapshotCommand());
				accountConsumeSnapshot.setAccount(account);
				//保存消费快照
				HgLogger.getInstance().info("zhaows", "consumptionAccount---保存消费快照" + JSON.toJSONString(accountConsumeSnapshot));
				accountConsumeSnapshotDao.save(accountConsumeSnapshot);
			}else if(command.getConsumption()){//消费账户余额
				if(consumeSnapshot==null){//如果command.getConsumption()==true &&consumeSnapshot==null说明用户现在处于支付宝页面长时间未支付
					qo.setAccountstatus(AccountConsumeSnapshot.STATUS_ZF);
					consumeSnapshot=accountConsumeSnapshotDao.queryUnique(qo);//查询当前订单对应的快照是否存在
				}
				//修改账户余额
				double balance=(account.getBalance()*constant-command.getCurrentMoney()*constant)/constant;//修改账户余额
				account.setBalance(balance);
				account.setLastConsumeDate(new Date());//修改最后消费时间
				List<AccountConsumeSnapshot> listSnapshot=new ArrayList<AccountConsumeSnapshot>();
				listSnapshot.add(consumeSnapshot);
				account.setConsumeOrderSnapshots(listSnapshot);
				HgLogger.getInstance().info("zhaows","consumptionAccount---修改账户余额" + JSON.toJSONString(account));
				accountDao.update(account);
				consumeSnapshot.setStatus(command.getAccountConsumeSnapshotCommand().getStatus());
				accountConsumeSnapshotDao.update(consumeSnapshot);
			}
			dto=EntityConvertUtils.convertDtoToEntity(account, AccountDTO.class);
		}
		return dto;
	}
	public String refundAccount(RefundCommand command)
			throws AccountException {
		HgLogger.getInstance().info("zhaows","refundAccount退款" + JSON.toJSONString(command));
		AccountQO qo=new AccountQO();
		qo.setId(command.getAccountId());
		Account account=accountDao.queryUnique(qo);
		if(account==null){
			throw new AccountException(AccountException.NOT_ACCOUNT,"余额账户不存在");
		}else{
			double balance=(account.getBalance()*constant+command.getRefundMoney()*constant)/constant;
			account.setBalance(balance);
			accountDao.update(account);
			/*修改原来消费快照的消费金额*/
			AccountConsumeSnapshotQO accountConsumeSnapshotQO=new AccountConsumeSnapshotQO();
			accountConsumeSnapshotQO.setId(command.getAccountConsumeSnapshotId());
			AccountConsumeSnapshot accountConsumeSnapshot=accountConsumeSnapshotDao.queryUnique(accountConsumeSnapshotQO);
			accountConsumeSnapshot.setPayPrice((accountConsumeSnapshot.getPayPrice() * constant - command.getRefundMoney() * constant) / constant);
			accountConsumeSnapshotDao.update(accountConsumeSnapshot);
			/*添加新的退款消费快照*/
			getDao().flush();
			AccountConsumeSnapshot refundSnapshot=new AccountConsumeSnapshot();
			refundSnapshot.setAccount(account);
			refundSnapshot.refund(command);
			HgLogger.getInstance().info("zhaows","refundAccount---新增消费快照" + JSON.toJSONString(refundSnapshot));
			accountConsumeSnapshotDao.save(refundSnapshot);
		}
		return "success";
	}
	public String TimerRefundAccount(RefundCommand command){
		AccountQO qo=new AccountQO();
		qo.setId(command.getAccountId());
		Account account=accountDao.queryUnique(qo);
		double balance=(account.getBalance()*constant+command.getRefundMoney()*constant)/constant;
		account.setBalance(balance);
		accountDao.update(account);
		return "success";
	}
	@Override
	protected AccountDao getDao() {
		return accountDao;
	}
	public AccountDTO queryUniques(AccountQO qo){
		Account account=accountDao.queryUnique(qo);
		AccountDTO dto=EntityConvertUtils.convertDtoToEntity(account, AccountDTO.class);
		if(account!=null){
			if(qo.isConsumeOrderSnapshots()){
				List<AccountConsumeSnapshotDTO> dtos=EntityConvertUtils.convertEntityToDtoList(account.getConsumeOrderSnapshots(), AccountConsumeSnapshotDTO.class);
				dto.setConsumeOrderSnapshots(dtos);
			}
		}
		return dto;
	}
}
