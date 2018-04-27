package zzpl.app.service.local.push;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;

import java.util.Map;

import org.springframework.stereotype.Service;

import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

import com.alibaba.fastjson.JSON;

@Service
public class PushService {
	
	public final static String MASTER_SECRET = SysProperties.getInstance().get("pushMasterSecret");
	
	public final static String APP_KEY = SysProperties.getInstance().get("pushAPPKey");
	
	public final static String TITLE = SysProperties.getInstance().get("pushTitle");
	
	/**
	 * 
	 * @方法功能说明：根据设备ID点对点推送消息
	 * @修改者名字：cangs
	 * @修改时间：2015年8月19日下午1:10:48
	 * @修改内容：
	 * @参数：@param deviceID
	 * @参数：@param title
	 * @参数：@param content
	 * @参数：@param extra
	 * @参数：@return
	 * @return:boolean
	 * @throws
	 */
	public boolean pushAndroidByDeviceID(String deviceID,String content,Map<String, String> extra){
		PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】MASTER_SECRET:"+MASTER_SECRET);
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】APP_KEY:"+APP_KEY);
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】"+"pushClient:"+JSON.toJSONString(pushClient));
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】"+"deviceID:"+JSON.toJSONString(deviceID));
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】"+"title:"+TITLE);
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】"+"content:"+JSON.toJSONString(content));
		HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】"+"extra:"+JSON.toJSONString(extra));
        try {
        	pushClient.sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.alias(deviceID))
//                .setAudience(Audience.all())
//                .setNotification(Notification.ios(content, extra))
                .setNotification(Notification.newBuilder()
                		.setAlert(content)
                		.addPlatformNotification(AndroidNotification.newBuilder()
                				.setTitle(TITLE).addExtras(extra).build())
                		.addPlatformNotification(IosNotification.newBuilder()
                				.incrBadge(1).addExtras(extra).build())
                		.build())
                .build());
        } catch (APIConnectionException e) {
            HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】【APIConnectionException】"+"推送失败"+HgLogger.getStackTrace(e));
            HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】【APIConnectionException】"+"推送失败"+JSON.toJSONString(e.getMessage()));
            return false;
        } catch (APIRequestException e) {
            HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】【APIRequestException】"+"推送失败"+HgLogger.getStackTrace(e));
            HgLogger.getInstance().info("cs", "【pushAndroidByDeviceID】【APIRequestException】"+"推送失败"+JSON.toJSONString(e.getErrorCode()+"，message："+e.getErrorMessage()));
            return false;
        }
		return true;
	}
	
	public boolean sendMessageAndroidByDeviceID(String deviceID,String message,Map<String, String> extra){
		PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】MASTER_SECRET:"+MASTER_SECRET);
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】APP_KEY:"+APP_KEY);
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】"+"pushClient:"+JSON.toJSONString(pushClient));
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】"+"deviceID:"+JSON.toJSONString(deviceID));
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】"+"title:"+TITLE);
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】"+"message:"+JSON.toJSONString(message));
		HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】"+"extra:"+JSON.toJSONString(extra));
        try {
        	pushClient.sendPush(PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias(deviceID))
                .setMessage(Message.newBuilder().setMsgContent(message).addExtras(extra).build())
                .build());
        } catch (APIConnectionException e) {
            HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】【APIConnectionException】"+"推送失败"+HgLogger.getStackTrace(e));
            HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】【APIConnectionException】"+"推送失败"+JSON.toJSONString(e.getMessage()));
            return false;
        } catch (APIRequestException e) {
            HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】【APIRequestException】"+"推送失败"+HgLogger.getStackTrace(e));
            HgLogger.getInstance().info("cs", "【sendMessageAndroidByDeviceID】【APIRequestException】"+"推送失败"+JSON.toJSONString(e.getErrorCode()+"，message："+e.getErrorMessage()));
            return false;
        }
		return true;
	}
}
