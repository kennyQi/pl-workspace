package slfx.api.v1.request.qo.xl;

import slfx.api.base.ApiPayload;

/**
 * 
 * @类功能说明：api接口查询线路订单QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月22日下午4:46:08
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class XLLineOrderApiQO extends ApiPayload {
	
	/**
	 * 主键
	 */
	private String id;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
