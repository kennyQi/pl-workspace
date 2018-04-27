package hsl.pojo.qo.coupon;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

import java.util.Date;

/**
 * 卡券转赠查询
 *
 * @author zhurz
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "couponTransferRecordDAO")
public class CouponTransferRecordQO extends BaseQo {

	/**
	 * 卡券ID
	 */
	@QOAttr(name = "couponId")
	private String couponId;

	/**
	 * 转赠时间
	 */
	@QOAttr(name = "transferDate", type = QOAttrType.GE)
	private Date transferDateBegin;

	@QOAttr(name = "transferDate", type = QOAttrType.LE)
	private Date transferDateEnd;

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public Date getTransferDateBegin() {
		return transferDateBegin;
	}

	public void setTransferDateBegin(Date transferDateBegin) {
		this.transferDateBegin = transferDateBegin;
	}

	public Date getTransferDateEnd() {
		return transferDateEnd;
	}

	public void setTransferDateEnd(Date transferDateEnd) {
		this.transferDateEnd = transferDateEnd;
	}
}
