package zzpl.pojo.dto.jp.plfx.gn;

import java.util.List;

import zzpl.pojo.dto.BaseDTO;


/***
 * 
 * @类功能说明：查询航班RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:36:35
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPQueryFlightListGNDTO extends BaseDTO {

	/**
	 * 航班列表
	 */
	private List<FlightGNDTO> flightList;
	
	/**
	 * 航班总数
	 */
	private Integer totalCount;
	
	/**
	 *参考机场建设费
	 */
	private Double buildFee;
	/**
	 * 参考燃油费
	 */
	private Double oilFee;

	/**
	 * 返回状态
	 */
	private String is_success;
	
	public Double getBuildFee() {
		return buildFee;
	}
	
	public void setBuildFee(Double buildFee) {
		this.buildFee = buildFee;
	}

	public Double getOilFee() {
		return oilFee;
	}

	public void setOilFee(Double oilFee) {
		this.oilFee = oilFee;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public List<FlightGNDTO> getFlightList() {
		return flightList;
	}
	
	public void setFlightList(List<FlightGNDTO> flightList) {
		this.flightList = flightList;
	}


}
