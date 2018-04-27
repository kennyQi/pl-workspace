package hsl.pojo.dto.jp.plfx;

import hsl.pojo.dto.BaseDTO;
import hsl.pojo.dto.jp.PriceInfoGNDTO;

/***
 * 
 * @类功能说明：下单前查政策RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年6月23日上午10:35:24
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPQueryHighPolicyGNDTO extends BaseDTO {

	/***
	 * 表示该次操作是否成功 
	 * T:成功F：失败
	 */
	private String is_success;
	
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
	private PriceInfoGNDTO pricesGNDTO;
	
	public PriceInfoGNDTO getPricesGNDTO() {
		return pricesGNDTO;
	}

	public void setPricesGNDTO(PriceInfoGNDTO pricesGNDTO) {
		this.pricesGNDTO = pricesGNDTO;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

//	public String getIsOneCabinManyPrice() {
//		return isOneCabinManyPrice;
//	}
//
//	public void setIsOneCabinManyPrice(String isOneCabinManyPrice) {
//		this.isOneCabinManyPrice = isOneCabinManyPrice;
//	}
//
//	public String getIsHighestPrice() {
//		return isHighestPrice;
//	}
//
//	public void setIsHighestPrice(String isHighestPrice) {
//		this.isHighestPrice = isHighestPrice;
//	}

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
