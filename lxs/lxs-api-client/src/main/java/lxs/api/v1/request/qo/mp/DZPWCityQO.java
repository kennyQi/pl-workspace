package lxs.api.v1.request.qo.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class DZPWCityQO extends ApiPayload{

	/**
	 * 编码
	 */
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public static void main(String[] args) {
		DZPWCityQO dzpwCityQO = new DZPWCityQO();
		dzpwCityQO.setCode("138");
		System.out.println(JSON.toJSONString(dzpwCityQO));
	}
}
