package hg.dzpw.app.service.api.sms;

import hg.common.util.SMSUtils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * @类功能说明：电子票务短信发送服务
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：guotx
 * @创建时间：2015-12-2下午3:34:20
 * @版本：
 */
@Service
public class SmsService {

	@Autowired
	private SMSUtils smsUtils;
	/**
	 * 
     * @描述： 短信发送接口
     * @author: guotx 
     * @version: 2015-12-2 下午3:23:25
	 */
	public List<String> sendSms(String mobile, String msgcont) throws Exception {
		List<String> result = new ArrayList<String>();
		// 判断字节的长度
		int length = msgcont.length();

		// 字节的长度是140的整数倍
		if (length % 70 == 0) {
			for (int i = 0; i < length / 70; i++) {
				String msgcont1 = msgcont.substring(i * 70, i * 70 + 70);
					result.add(smsUtils.sendSms(mobile, msgcont1));
			}
		}
		// 字节的不是长度是140的整数倍
		else {
			for (int i = 0; i < length / 70 + 1; i++) {
				if (i != length / 70) {
					String msgcont1 = msgcont.substring(i * 70, i * 70 + 70);
						result.add(smsUtils.sendSms(mobile, msgcont1));
				} else {
					int j = length % 70;
					String msgcont1 = msgcont.substring(i * 70, i * 70 + j);
						result.add(smsUtils.sendSms(mobile, msgcont1));
				}
			}
		}
		return result;
	}
}
