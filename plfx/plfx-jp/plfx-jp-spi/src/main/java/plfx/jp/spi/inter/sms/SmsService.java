package plfx.jp.spi.inter.sms;


public interface SmsService {

	/**
	 * 
	 * @方法功能说明：发送短信
	 * @修改者名字：yuqz
	 * @修改时间：2015年12月25日下午2:35:54
	 * @修改内容：
	 * @参数：@param mobile
	 * @参数：@param msg
	 * @参数：@return
	 * @return:String
	 * @throws
	 */
	String sendSms(String mobile, String msg);

}
