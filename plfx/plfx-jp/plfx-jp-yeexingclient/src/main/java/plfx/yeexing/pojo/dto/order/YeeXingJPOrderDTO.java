package plfx.yeexing.pojo.dto.order;

import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPassengerDTO;
import plfx.yeexing.pojo.dto.flight.YeeXingPriceDTO;


/**
 * 
 * @类功能说明：机票平台订单DTO
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年8月26日下午4:51:57
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingJPOrderDTO extends BaseJpDTO {
	/***
	 * 订单号
	 * 易行天下订单号（易行天下系统中唯一）
	 */
	private  String  orderid;
	/***
	 * 外部订单号(分销平台订单号)    
	 * 合作伙伴订单号（合作伙伴系统中唯一）
	 */
	private  String  out_orderid;
	/***
	 * 订单生成时间   
	 * Yyyy-MM-dd HH:mm:ss
	 */
	private  String  createTime;
	/***
	 * 游客信息
	 */
	private List<YeeXingPassengerDTO> passengerList;
	/***
	 * 价格信息
	 */
	private YeeXingPriceDTO priceDTO;

	/**
	 * 表示该次操作是否成功 
	 * T:成功F：失败
	 */
	private String is_success;
	
	/***
	 * 经销商订单号   
	 * 
	 */
	private  String  dealerOrderId;
	
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
	public String getOrderid() {
		return orderid;
	}
	public void setOrderid(String orderid) {
		this.orderid = orderid;
	}
	public String getOut_orderid() {
		return out_orderid;
	}
	public void setOut_orderid(String out_orderid) {
		this.out_orderid = out_orderid;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public List<YeeXingPassengerDTO> getPassengerList() {
		return passengerList;
	}
	public void setPassengerList(List<YeeXingPassengerDTO> passengerList) {
		this.passengerList = passengerList;
	}
	public YeeXingPriceDTO getPriceDTO() {
		return priceDTO;
	}
	public void setPriceDTO(YeeXingPriceDTO priceDTO) {
		this.priceDTO = priceDTO;
	}
	public String getDealerOrderId() {
		return dealerOrderId;
	}
	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}
}