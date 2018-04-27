package lxs.api.action;

import hg.common.util.SMSUtils;
import hg.common.util.SysProperties;
import lxs.api.base.ApiRequest;
import lxs.api.v1.request.command.user.SMSCommand;
import lxs.api.v1.response.user.SMSResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

@Component("SendSMSAction")
public class SendSMSAction implements LxsAction{

	@Autowired
	private SMSUtils smsUtils;
	
	@Override
	public String execute(ApiRequest apiRequest) {
		SMSResponse smsResponse = new SMSResponse();
		SMSCommand smsCommand = JSON.parseObject(apiRequest.getBody().getPayload(),SMSCommand.class);
		try {
			String result = smsUtils.sendSms(smsCommand.getMobile(),"【"+SysProperties.getInstance().get("smsSign")+"】"+smsCommand.getContent());
			smsResponse.setMessage(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			smsResponse.setMessage("not_ok");
		}
		return JSON.toJSONString(smsResponse);
	}

}
