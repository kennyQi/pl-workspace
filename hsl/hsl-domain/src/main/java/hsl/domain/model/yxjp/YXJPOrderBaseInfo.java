package hsl.domain.model.yxjp;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Date;

/**
 * 易行机票订单基本信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings({"serial"})
public class YXJPOrderBaseInfo implements Serializable, HSLConstants.FromType {

	/**
	 * 订单编号
	 */
	@Column(name = "ORDER_NO", length = 64)
	private String orderNo;

	/**
	 * 分销订单号
	 */
	@Column(name = "OUT_ORDER_NO", length = 64)
	private String outOrderNo;

	/**
	 * 向分销下单时间（这个字段为空时说明未向分销下单）
	 */
	@Column(name = "OUT_ORDER_DATE", columnDefinition = M.DATE_COLUM)
	private Date outOrderDate;

	/**
	 * 来源标识：0 mobile , 1  pc
	 *
	 * @see HSLConstants.FromType
	 */
	@Column(name = "FROM_CLIENT_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer fromType;

	/**
	 * 订单创建时间
	 */
	@Column(name = "CREATE_DATE", columnDefinition = M.DATE_COLUM)
	private Date createDate;

	/**
	 * 订单的pnr
	 */
	@Column(name = "PNR", length = 16)
	private String pnr;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getOutOrderNo() {
		return outOrderNo;
	}

	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}

	public Date getOutOrderDate() {
		return outOrderDate;
	}

	public void setOutOrderDate(Date outOrderDate) {
		this.outOrderDate = outOrderDate;
	}

	public Integer getFromType() {
		return fromType;
	}

	public void setFromType(Integer fromType) {
		this.fromType = fromType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getPnr() {
		return pnr;
	}

	public void setPnr(String pnr) {
		this.pnr = pnr;
	}
}
