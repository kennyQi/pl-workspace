package plfx.jp.app.service.spi.sms;

import hg.common.util.SMSUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import plfx.jp.spi.inter.sms.SmsService;

@Service("smsService")
public class SmsServiceImpl implements SmsService{
	@Autowired
	private SMSUtils smsUtils;
	
	@Override
	public String sendSms(String mobile, String msg) {
		String result = "";
		try {
			result = smsUtils.sendSms(mobile, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
