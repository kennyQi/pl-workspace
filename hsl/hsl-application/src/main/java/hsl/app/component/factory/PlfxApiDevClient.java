package hsl.app.component.factory;

import com.alibaba.fastjson.JSON;
import hsl.app.component.config.SysProperties;
import org.apache.commons.io.IOUtils;
import plfx.api.client.api.v1.gn.request.JPFlightGNQO;
import plfx.api.client.api.v1.gn.request.JPPolicyGNQO;
import plfx.api.client.common.ApiRequestBody;
import plfx.api.client.common.ApiResponse;
import plfx.api.client.common.util.PlfxApiClient;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhurz
 */
public class PlfxApiDevClient extends PlfxApiClient {

	String json1;
	String json2;

	public PlfxApiDevClient(String url, String dealerKey, String secretKey) {
		super(url, dealerKey, secretKey);
		InputStream ras1 = this.getClass().getResourceAsStream("/testres1.json");
		InputStream ras2 = this.getClass().getResourceAsStream("/testres2.json");
		try {
			json1 = IOUtils.toString(ras1, "utf-8");
			json2 = IOUtils.toString(ras2, "utf-8");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ras1.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				ras2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public <T extends ApiResponse> T send(ApiRequestBody requestBody, Class<T> responseClass) {
		String envId = SysProperties.getInstance().get("envId", "");
		// wtdo 开发环境如果要使用测试环境要注释掉
		if (envId.contains("DEV")) {
			// 查询政策用假数据
			if (requestBody instanceof JPFlightGNQO) {
				return JSON.parseObject(json1, responseClass);
			} else if (requestBody instanceof JPPolicyGNQO) {
				return JSON.parseObject(json2, responseClass);
			}
		}
		return super.send(requestBody, responseClass);
	}
}
