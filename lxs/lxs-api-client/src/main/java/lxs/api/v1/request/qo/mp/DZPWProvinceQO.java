package lxs.api.v1.request.qo.mp;

import com.alibaba.fastjson.JSON;

import lxs.api.base.ApiPayload;

@SuppressWarnings("serial")
public class DZPWProvinceQO extends ApiPayload {

	public static void main(String[] args) {
		DZPWProvinceQO dzpwProvinceQO = new DZPWProvinceQO();
		System.out.println(JSON.toJSONString(dzpwProvinceQO));
	}
}
