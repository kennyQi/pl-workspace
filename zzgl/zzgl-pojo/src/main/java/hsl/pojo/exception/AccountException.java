package hsl.pojo.exception;

@SuppressWarnings("serial")
public class AccountException extends BaseException{
	public AccountException(Integer code, String message){
		super(code,message);
	}
	/**
	 *充值码不可用
	 */
	public static final Integer NOT_AVAILABLE = 1; 
	/**
	 * 账户不存在
	 */
	public static final Integer NOT_ACCOUNT = 2;
	/**
	 * 充值码不存在
	 */
	public static final Integer NOT_PAYCODE = 3;
	
	/**
	 * 已使用
	 */
	public static final Integer ALREADY_PAYCODE = 4;
	/**
	 * 账户余额不足
	 */
	public static final Integer ALREADY_INSUFFICIENT = 5;
	/**
	 * 消费快照不存在
	 */
	public static  final  Integer CONSUMPTION_NOEXIST =6;
}
