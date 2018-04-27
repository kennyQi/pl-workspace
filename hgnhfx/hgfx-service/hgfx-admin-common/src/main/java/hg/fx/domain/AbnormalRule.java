package hg.fx.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import hg.demo.member.common.domain.model.def.O;
import hg.framework.common.base.BaseStringIdModel;

/**
 * @类功能说明：异常订单规则
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yangkang
 * @创建时间：2016-5-31下午4:29:31
 * @版本：V1.0
 *
 */
@Entity
@Table(name = O.FX_TABLE_PREFIX + "ABNORMAL_RULE")
public class AbnormalRule extends BaseStringIdModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 单笔订单里程上限
	 */
	@Column(name = "ORDER_UNIT_MAX", columnDefinition = O.LONG_NUM_COLUM)
	private Long orderUnitMax;
	
	/**
	 * 同账号每日订单上限次数
	 */
	@Column(name = "DAY_MAX", columnDefinition = O.NUM_COLUM)
	private Integer dayMax;
	
	/**
	 * 同账号每月订单上限次数
	 * */
	@Column(name = "MOUTH_MAX", columnDefinition = O.NUM_COLUM)
	private Integer mouthMax;

	public Long getOrderUnitMax() {
		return orderUnitMax;
	}

	public void setOrderUnitMax(Long orderUnitMax) {
		this.orderUnitMax = orderUnitMax;
	}

	public Integer getDayMax() {
		return dayMax;
	}

	public void setDayMax(Integer dayMax) {
		this.dayMax = dayMax;
	}

	public Integer getMouthMax() {
		return mouthMax;
	}

	public void setMouthMax(Integer mouthMax) {
		this.mouthMax = mouthMax;
	}
	
}
