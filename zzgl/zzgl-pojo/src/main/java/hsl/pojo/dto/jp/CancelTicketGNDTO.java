package hsl.pojo.dto.jp;

import hsl.pojo.dto.BaseDTO;

/**
 * @类功能说明：航班申请取消Response
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yaosanfeng
 * @创建时间：2015年7月3日下午3:04:58
 * @版本：V1.0
 * 
 */
@SuppressWarnings("serial")
public class CancelTicketGNDTO extends BaseDTO {

	/**
	 * 是否成功 表示该次操作是否成功 T:成功F：失败
	 */
	private String is_success;

	/**
	 * 经销商订单号
	 */
	private String dealerOrderId;

	/**
	 * 错误信息
	 */
	private String error;

	public String getDealerOrderId() {
		return dealerOrderId;
	}

	public void setDealerOrderId(String dealerOrderId) {
		this.dealerOrderId = dealerOrderId;
	}

	public String getIs_success() {
		return is_success;
	}

	public void setIs_success(String is_success) {
		this.is_success = is_success;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
