package hg.dzpw.pojo.qo;

import hg.dzpw.pojo.common.BasePojoQo;

import java.util.Date;

/**
 * @类功能说明：财务支付对账管理查询对象
 * @类修改者：
 * @修改日期：2014-11-14下午3:34:09
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-14下午3:34:09
 */
public class ReconciliationOrderQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/**
	 * 景区ID（为null或空字符串视为全部）
	 */
	private String scenicSpotId;

	/**
	 * 是否为景区端查询 为true时，scenicSpotId不能为空
	 */
	private Boolean scenicSpotSelect = false;

	/**
	 * 页面查询开始条件
	 */
	private Date orderDateBegin;

	/**
	 * 首次查询结束条件
	 */
	private Date orderDateEnd;

	private Integer orderDateSort;

	public String getScenicSpotId() {
		return scenicSpotId;
	}

	public void setScenicSpotId(String scenicSpotId) {
		this.scenicSpotId = scenicSpotId;
	}

	public Boolean getScenicSpotSelect() {
		return scenicSpotSelect;
	}

	public void setScenicSpotSelect(Boolean scenicSpotSelect) {
		this.scenicSpotSelect = scenicSpotSelect;
	}

	public Date getOrderDateBegin() {
		return orderDateBegin;
	}

	public void setOrderDateBegin(Date orderDateBegin) {
		this.orderDateBegin = orderDateBegin;
	}

	public Date getOrderDateEnd() {
		return orderDateEnd;
	}

	public void setOrderDateEnd(Date orderDateEnd) {
		this.orderDateEnd = orderDateEnd;
	}

	public Integer getOrderDateSort() {
		return orderDateSort;
	}

	public void setOrderDateSort(Integer orderDateSort) {
		this.orderDateSort = orderDateSort;
	}

}
