package plfx.jd.pojo.qo;

import hg.common.component.BaseQo;
/****
 * 
 * @类功能说明：酒店详情QO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月20日下午4:36:57
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YLHotelQO extends BaseQo{

	/***
	 * 酒店id
	 */
	private String hotelId;
	
	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}	
}
