package hg.dzpw.pojo.qo;

import java.util.Date;

import hg.dzpw.pojo.common.BasePojoQo;

/**
 * @类功能说明：经销商销售统计查询对象
 * @类修改者：
 * @修改日期：2014-11-21上午11:25:59
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @作者：zhurz
 * @创建时间：2014-11-21上午11:25:59
 */
public class DealerSaleStatisticsQo extends BasePojoQo {
	private static final long serialVersionUID = 1L;

	/** 查询类型：按销量 */
	public final static int QUERY_TYPE_ORDER_TIKECT_COUNT = 1;
	/** 查询类型：按销售额 */
	public final static int QUERY_TYPE_SALE_TIKECT_AMOUNT = 2;

	/**
	 * 查询类型
	 */
	private int queryType = QUERY_TYPE_ORDER_TIKECT_COUNT;

	/**
	 * 经销商ID
	 */
	private String dealerId;

	/**
	 * 销售日期（开始）
	 */
	private Date orderDateBegin;

	/**
	 * 销售日期（截止）
	 */
	private Date orderDateEnd;

	public int getQueryType() {
		return queryType;
	}

	public void setQueryType(int queryType) {
		this.queryType = queryType;
	}

	public String getDealerId() {
		return dealerId;
	}

	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
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

}
