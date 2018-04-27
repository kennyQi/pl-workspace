package plfx.xl.pojo.command.line;

import hg.common.component.BaseCommand;

/**
 * 
 * @类功能说明：修改线路最低价格command
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：yuqz
 * @创建时间：2015年4月8日下午5:55:13
 * @版本：V1.0
 *
 */
@SuppressWarnings("serial")
public class ModifyLineMinPriceCommand extends BaseCommand{
	/**
	 * 线路id
	 */
	private String lineId;
	
	/**
	 * 団期最低价格
	 */
	private Double minPrice;

	public String getLineId() {
		return lineId;
	}

	public void setLineId(String lineId) {
		this.lineId = lineId;
	}

	public Double getMinPrice() {
		return minPrice;
	}

	public void setMinPrice(Double minPrice) {
		this.minPrice = minPrice;
	}
	
	
}
