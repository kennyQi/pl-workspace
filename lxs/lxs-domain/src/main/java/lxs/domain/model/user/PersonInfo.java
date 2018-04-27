package lxs.domain.model.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lxs.domain.model.M;

/**
 * 
 * @类功能说明：商城用户常用的他人用户信息，如常旅客，代购收货人等
 * @类修改者：yuxx
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuxx
 * @创建时间：2014年7月30日下午1:46:29
 * @版本：V1.0
 * 
 */
@Embeddable
public class PersonInfo {

	/**
	 * 姓名
	 */
	@Column(name = "P_NAME", length = 32)
	private String name;

	/**
	 * 证件号
	 */
	@Column(name = "P_ID_CARD_NO", length = 64)
	private String idCardNo;

	/**
	 * 1身份证
	 */
	@Column(name = "P_ID_TYPE", columnDefinition = M.TYPE_NUM_COLUM)
	private String idType;

	/**
	 * 手机号
	 */
	@Column(name = "P_MOBILE", length = 32)
	private String mobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

}
