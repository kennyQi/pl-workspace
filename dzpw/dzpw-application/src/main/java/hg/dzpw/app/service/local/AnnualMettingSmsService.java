package hg.dzpw.app.service.local;

import hg.common.util.SMSUtils;
import hg.log.util.HgLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
@Service
public class AnnualMettingSmsService {
	/**下单成功后提示信息*/
	private final String NOTICE_SMS="【票量旅游】创新创业、共赴前程！2016年汇购科技新年年会兹定于1月23日13：40在歌山品悦大酒店5楼歌山厅隆重举行，真诚期待您的光临！请各位嘉宾持本人二代身份证或参会二维码【LINK】于13：10—13：40至会场签到处签到并获取抽奖资格！";
	/**签到成功后提示信息*/
	private final String AFTER_USE_SMS="【票量旅游】您好【NAME】，您已签到成功，抽奖号码为【LOTTORY】，祝您好运！";
	
	@Autowired
	private SMSUtils smsUtils;
	/**
	 * 
	 * @描述：核销（验票）成功后发送短信
	 * @author: guotx
	 * @version: 2016-1-18 下午2:38:51
	 * @return: 发送成功返回true，失败返回false
	 * @param phoneNum 发送短信手机号码
	 * @param tourisName 游玩人姓名
	 * @param lottory 抽奖号码
	 */
	public boolean sendSmsAfterUse(String phoneNum,String tourisName,String lottory) {
		
		String message=AFTER_USE_SMS.replaceAll("NAME", tourisName).replaceAll("LOTTORY", lottory);
		try {
			String result=smsUtils.sendSms(phoneNum,message);
			HgLogger.getInstance().debug("guotx", String.format("年会签到短信发送结果：%s", result));
		} catch (Exception e) {
			HgLogger.getInstance().error("guotx", "年会签到发送短信失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	/**
	 * 
	 * @描述： 下单后短信通知
	 * @author: guotx 
	 * @version: 2016-1-18 下午2:51:27
	 * @return: 发送成功返回true，失败返回false
	 * @param phoneNum 发送短信手机号
	 * @param link 短信中的二维码链接
	 */
	public boolean sendSmsNotice(String phoneNum,String link){
		String message=NOTICE_SMS.replaceAll("LINK", link);
		try {
			String result=smsUtils.sendSms(phoneNum,message);
			HgLogger.getInstance().debug("guotx", String.format("年会消息通知短信发送结果：%s", result));
		} catch (Exception e) {
			HgLogger.getInstance().error("guotx", "年会消息通知发送短信失败");
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
