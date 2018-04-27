package zzpl.pojo.dto.user.status;

public class ResetPasswordSagaStatus {
	
	public final static int CODE_TOO_MANY_TIMES = 1;
	
	public final static String CODE_TOO_MANY_TIMES_MESSAGE = "验证次数过多";
	
	public final static int  CODE_PAST_DUE = 2;

	public final static String CODE_PAST_DUE_MESSAGE = "验证码超时";
	
	public final static int CODE_CORRECT = 3;
	
	public final static String CODE_CORRECT_MESSAGE = "验证成功";
	
	public final static int CODE_WRONG = 4;

	public final static String CODE_WRONG_MESSAGE = "验证失败";
	
}
