package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOAttrType;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 易行机票订单操作日志查询
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderOperateLogDAO")
public class YXJPOrderOperateLogQO extends BaseQo {

	/**
	 * 所属订单
	 */
	@QOAttr(name = "fromOrder.id")
	private String fromOrderId;

	/**
	 * 操作时间排序
	 * 大于0是asc，小于0是desc。
	 */
	@QOAttr(name = "oprateDate", type = QOAttrType.ORDER)
	private Integer oprateDateOrder = 0;

	public String getFromOrderId() {
		return fromOrderId;
	}

	public void setFromOrderId(String fromOrderId) {
		this.fromOrderId = fromOrderId;
	}

	public Integer getOprateDateOrder() {
		return oprateDateOrder;
	}

	public void setOprateDateOrder(Integer oprateDateOrder) {
		this.oprateDateOrder = oprateDateOrder;
	}
}
