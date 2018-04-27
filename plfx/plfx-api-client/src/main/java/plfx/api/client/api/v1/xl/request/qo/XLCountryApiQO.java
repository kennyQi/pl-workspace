package plfx.api.client.api.v1.xl.request.qo;

import plfx.api.client.base.slfx.ApiPayload;

/*****
 * 
 * @类功能说明：通过国家代码或城市代码查询国家,城市,该国家所有旅游城市
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年12月17日上午9:17:40
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLCountryApiQO extends ApiPayload {
	
	
	/**
	 * 国家名称
	 */
	private String name;
	
	/**
	 * 国家代码
	 */
	private String code;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
