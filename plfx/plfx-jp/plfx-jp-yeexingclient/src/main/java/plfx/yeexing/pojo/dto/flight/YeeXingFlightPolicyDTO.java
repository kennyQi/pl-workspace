package plfx.yeexing.pojo.dto.flight;



import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;


/****
 * 
 * @类功能说明：易行天下航班政策DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月16日下午4:00:42
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingFlightPolicyDTO extends BaseJpDTO{
	

	/***
	 * 表示该次操作是否成功 
	 * T:成功F：失败 
	 */
	private String is_success;
	
	/***
	 * 是否一舱多价 
	 * 1-是；0-否
	 */
	private String isOneCabinManyPrice;
	
	/***
	 * 是否一舱多价中的最高价 
	 * 1-是；0-否
	 */
	private String isHighestPrice;
	
	/***
	 * 机场建设费
	 */
	private Double buildFee;

	/***
	 * 燃油税 
	 */
	private Double oilFee;

	/***
	 * 价格政策信息  
	 */
	private List<YeeXingPriceInfoDTO> pricesList;

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

	public List<YeeXingPriceInfoDTO> getPricesList() {
		return pricesList;
	}

	public void setPricesList(List<YeeXingPriceInfoDTO> pricesList) {
		this.pricesList = pricesList;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getIsOneCabinManyPrice() {
		return isOneCabinManyPrice;
	}

	public void setIsOneCabinManyPrice(String isOneCabinManyPrice) {
		this.isOneCabinManyPrice = isOneCabinManyPrice;
	}

	public String getIsHighestPrice() {
		return isHighestPrice;
	}

	public void setIsHighestPrice(String isHighestPrice) {
		this.isHighestPrice = isHighestPrice;
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
	
	
	
	
   
}