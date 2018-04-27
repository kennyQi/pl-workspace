package plfx.mp.spi.inter.api;

import plfx.api.client.api.v1.mp.request.qo.MPScenicSpotsQO;
import plfx.api.client.api.v1.mp.response.MPQueryScenicSpotsResponse;


/**
 * 
 * @类功能说明：景区API
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年8月6日下午8:01:13
 *
 */
public interface ApiMPScenicSpotsService {
	
	/**
	 * 景区查询
	 * 
	 * @param qo
	 * @return
	 */
	public MPQueryScenicSpotsResponse queryScenicSpots(MPScenicSpotsQO qo);
	
}
