package lxs.domain.model.mp;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @类功能说明:
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：chenys
 * @创建时间：2014-11-10 下午4:16:54
 * @版本：V1.0
 */
@Embeddable
public class TicketPolicyUseCondition {

	/**
	 * 有效天数(独立单票可入园天数 or 联票自出票后的有效天数)
	 */
	@Column(name = "VALID_DAYS")
	private Integer validDays;

	public Integer getValidDays() {
		return validDays;
	}

	public void setValidDays(Integer validDays) {
		this.validDays = validDays;
	}

}