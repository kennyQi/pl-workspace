package plfx.api.client.api.v1.gn.response;

import java.util.Date;
import java.util.List;

import plfx.api.client.api.v1.gn.dto.PassengerGNDTO;
import plfx.api.client.api.v1.gn.dto.PriceGNDTO;
import plfx.api.client.common.ApiResponse;


/****
 * 
 * @类功能说明：机票下单结果RESPONSE
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月2日下午1:47:26
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class JPBookOrderGNResponse extends ApiResponse{
	/***
	 * 经销商订单号   
	 * 
	 */
	private  String  dealerOrderId;
	
	/***
	 * 订单生成时间   
	 * Yyyy-MM-dd HH:mm:ss
	 */
//	private  String  createTime;
	private  Date  createTime;
	/***
	 * 游客信息
	 */
	private List<PassengerGNDTO> passengerList;
	/***
	 * 价格信息
	 */
	private PriceGNDTO priceGNDTO;

	/**
	 * 表示该次操作是否成功 
	 * T:成功F：失败
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
	public String getIs_success() {
		return is_success;
	}
	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}
	
//	public String getCreateTime() {
//		return createTime;
//	}
//	public void setCreateTime(String createTime) {
//		this.createTime = createTime;
//	}
	
	public List<PassengerGNDTO> getPassengerList() {
		return passengerList;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setPassengerList(List<PassengerGNDTO> passengerList) {
		this.passengerList = passengerList;
	}
	public PriceGNDTO getPriceGNDTO() {
		return priceGNDTO;
	}
	public void setPriceGNDTO(PriceGNDTO priceGNDTO) {
		this.priceGNDTO = priceGNDTO;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	
}
