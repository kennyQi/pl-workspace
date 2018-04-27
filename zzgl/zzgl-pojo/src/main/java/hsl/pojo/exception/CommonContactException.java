package hsl.pojo.exception;
public class CommonContactException extends BaseException{
	private static final long serialVersionUID = 1L;
	public CommonContactException(Integer code, String message){
		super(code,message);
	}
	/**
	 * 用户不存在
	 */
	public static final Integer USER_NO_EXIST= 1; 
	
	/**
	 * 证件重复
	 */
	public static final Integer CARDNO_REPEAT= 2; 
}
