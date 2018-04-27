package plfx.yeexing.pojo.dto.flight;



import java.util.List;

import plfx.jp.pojo.dto.BaseJpDTO;


/*****
 * 
 * @类功能说明：易行天下生成订单DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:08:10
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class YeeXingFlightOrderDTO extends BaseJpDTO{
	/***
	 * 订单号   易行天下订单号（易行天下系统中唯一）
	 */
	private  String  orderid;
	/***
	 * 外部订单号    合作伙伴订单号（合作伙伴系统中唯一）
	 */
	private  String  out_orderid;
	/***
	 * 订单生成时间   Yyyy-MM-dd HH:mm:ss
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
	 * 表示该次操作是否成功 T:成功F：失败
	 */
	private String is_success;
	
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
	
	
	


}
