package hg.pojo.exception;

@SuppressWarnings("serial")
public class SettingException extends BaseException {

	public SettingException (Integer code, String message){
		super(code,message);
	}
	
	/**
	 * 结款名称已存在
	 */
	public final static Integer PAYMENT_METHOD_NAME_EXIST=1;
	/**
	 * 注意事项已存在
	 */
	public final static Integer ATTENTION_NAME_EXIST=2;
}
