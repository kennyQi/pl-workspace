package hsl.domain.model.coupon;

import hg.common.component.BaseModel;
import hg.common.util.UUIDGenerator;
import hsl.domain.model.M;

import javax.persistence.*;
import java.util.Date;

/**
 * 卡券转赠记录
 *
 * @author zhurz
 */
@Entity
@SuppressWarnings("serial")
@Table(name = M.TABLE_PREFIX_HSL_COUPON + "TRANSFER_RECORD")
public class CouponTransferRecord extends BaseModel {

	/**
	 * 卡券ID
	 */
	@Column(name = "COUPON_ID", length = 64)
	private String couponId;

	/**
	 * 来自用户
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FROM_USER_ID")
	private UserSnapshot fromUser;

	/**
	 * 转赠用户
	 */
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "TO_USER_ID")
	private UserSnapshot toUser;

	/**
	 * 转赠时间
	 */
	@Column(name = "TRANSFER_DATE", columnDefinition = M.DATE_COLUM)
	private Date transferDate;

	/**
	 * 创建一条转赠记录
	 *
	 * @param couponId
	 * @param fromUser
	 * @param toUser
	 */
	public void create(String couponId, UserSnapshot fromUser, UserSnapshot toUser) {
		setId(UUIDGenerator.getUUID());
		setCouponId(couponId);
		setFromUser(fromUser);
		setToUser(toUser);
		setTransferDate(new Date());
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public UserSnapshot getFromUser() {
		return fromUser;
	}

	public void setFromUser(UserSnapshot fromUser) {
		this.fromUser = fromUser;
	}

	public UserSnapshot getToUser() {
		return toUser;
	}

	public void setToUser(UserSnapshot toUser) {
		this.toUser = toUser;
	}

	public Date getTransferDate() {
		return transferDate;
	}

	public void setTransferDate(Date transferDate) {
		this.transferDate = transferDate;
	}
}
