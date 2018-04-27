package hsl.pojo.qo.yxjp;

import hg.common.annotation.QOAttr;
import hg.common.annotation.QOConfig;
import hg.common.component.BaseQo;

/**
 * 批量退款查询对象
 *
 * @author zhurz
 * @since 1.7
 */
@SuppressWarnings("serial")
@QOConfig(daoBeanId = "yxjpOrderPayBatchRefundRecordDAO")
public class YXJPOrderPayBatchRefundRecordQO extends BaseQo {

	/**
	 * 退款批次号
	 */
	@QOAttr(name = "batchNo")
	private String batchNo;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
}
