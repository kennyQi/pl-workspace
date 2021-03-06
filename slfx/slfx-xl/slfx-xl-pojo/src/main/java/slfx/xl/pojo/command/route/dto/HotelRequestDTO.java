package slfx.xl.pojo.command.route.dto;

import java.io.Serializable;

/**
 * 
 * @类功能说明：线路行程酒店请求DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：luoyun
 * @创建时间：2014年12月15日上午10:54:54
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class HotelRequestDTO implements Serializable{

	/**
	 * 住宿星级
	 */
	private Integer stayLevel;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;

	public Integer getStayLevel() {
		return stayLevel;
	}

	public void setStayLevel(Integer stayLevel) {
		this.stayLevel = stayLevel;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	
	
}
