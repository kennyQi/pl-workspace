package hsl.domain.model.yxjp;

import hsl.domain.model.M;
import hsl.pojo.util.HSLConstants;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * 乘客基本信息
 *
 * @author zhurz
 * @since 1.7
 */
@Embeddable
@SuppressWarnings("serial")
public class YXJPOrderPassengerBaseInfo implements Serializable, HSLConstants.Traveler {

	/**
	 * 在订单中的序号，从1开始
	 */
	@Column(name = "ORDER_SEQ", columnDefinition = M.NUM_COLUM)
	private Integer seq;

	/**
	 * 姓名
	 */
	@Column(name = "NAME", length = 64)
	private String name;

	/**
	 * 手机号
	 */
	@Column(name = "MOBILE", length = 64)
	private String mobile;

	/**
	 * 证件号
	 */
	@Column(name = "ID_NO", length = 64)
	private String idNo;

	/**
	 * 证件类型
	 *
	 * @see HSLConstants.Traveler
	 */
	@Column(name = "ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer idType;

	/**
	 * 乘客类型（目前只支持成人）
	 *
	 * @see HSLConstants.Traveler
	 */
	@Column(name = "TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private Integer type;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public Integer getIdType() {
		return idType;
	}

	public void setIdType(Integer idType) {
		this.idType = idType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
