package plfx.api.controller.base;

import plfx.api.client.base.slfx.ApiRequest;


/**
 * 
 * @类功能说明：分销apicontroller基础接口
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月23日下午2:46:56
 * @版本：V1.0
 *
 */
public interface SLFXAction {
	
	public String execute(ApiRequest apiRequest);
	
}
