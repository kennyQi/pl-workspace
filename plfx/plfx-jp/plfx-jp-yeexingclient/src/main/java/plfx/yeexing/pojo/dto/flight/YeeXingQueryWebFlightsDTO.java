package plfx.yeexing.pojo.dto.flight;



import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;


/***
 * 
 * @类功能说明：易行天下航班列表DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:33:38
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingQueryWebFlightsDTO extends BaseJpDTO{
	/**
	 * 航班list
	 */
	private List<YeeXingFlightDTO> flightList;

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
	
	/***
	 * 错误信息  
	 * 格式：错误代码^错误信息
	 */
	private String error;
	
	public String getError() {
		return error;
	}


	public void setError(String error) {
		this.error = error;
	}


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


	public List<YeeXingFlightDTO> getFlightList() {
		return flightList;
	}


	public void setFlightList(List<YeeXingFlightDTO> flightList) {
		this.flightList = flightList;
	}


	

	
	
}
