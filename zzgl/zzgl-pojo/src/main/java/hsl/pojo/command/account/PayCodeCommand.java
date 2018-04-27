package hsl.pojo.command.account;
import hg.common.component.BaseCommand;
/**
 * 
 * @类功能说明：充值码
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhaows
 * @创建时间：2015-9-1上午9:29:25
 * @类修改者：
 * @修改日期：
 * @修改说明：
 */

@SuppressWarnings("serial")
public class PayCodeCommand extends BaseCommand{
	/**
	 * 充值码
	 */
	private String code;
	/**
	 * 持用用户快照
	 */
	private HoldUserSnapshotCommand holdUserSnapshotCommand;
	/**
	 * 用户账户信息
	 */
	private AccountCommand accountCommand;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public HoldUserSnapshotCommand getHoldUserSnapshotCommand() {
		return holdUserSnapshotCommand;
	}
	public void setHoldUserSnapshotCommand(
			HoldUserSnapshotCommand holdUserSnapshotCommand) {
		this.holdUserSnapshotCommand = holdUserSnapshotCommand;
	}
	public AccountCommand getAccountCommand() {
		return accountCommand;
	}
	public void setAccountCommand(AccountCommand accountCommand) {
		this.accountCommand = accountCommand;
	}


}
