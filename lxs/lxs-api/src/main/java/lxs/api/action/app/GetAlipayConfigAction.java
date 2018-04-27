package lxs.api.action.app;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;

import hg.common.util.SysProperties;
import hg.log.util.HgLogger;
import lxs.api.action.LxsAction;
import lxs.api.base.ApiRequest;
import lxs.api.v1.response.app.AlipayConfigResponse;

@Component("GetAlipayConfigAction")
public class GetAlipayConfigAction implements LxsAction{

	@Override
	public String execute(ApiRequest apiRequest) {
		AlipayConfigResponse alipayConfigResponse = new AlipayConfigResponse();
		alipayConfigResponse.setNotify_url(SysProperties.getInstance().get("appnotify_url"));
		alipayConfigResponse.setPartner(SysProperties.getInstance().get("appPARTNER"));
		alipayConfigResponse.setRsa_private(SysProperties.getInstance().get("appRSA_PRIVATE"));
		alipayConfigResponse.setRsa_public(SysProperties.getInstance().get("appRSA_PUBLIC"));
		alipayConfigResponse.setSeller(SysProperties.getInstance().get("appSELLER"));
		HgLogger.getInstance().info("lxs_dev", "【GetAlipayConfigAction】【alipayConfigResponse】"+JSON.toJSONString(alipayConfigResponse));
		return JSON.toJSONString(alipayConfigResponse);
	}

}
