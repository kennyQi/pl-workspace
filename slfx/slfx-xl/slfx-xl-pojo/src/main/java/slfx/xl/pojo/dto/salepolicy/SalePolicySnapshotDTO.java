package slfx.xl.pojo.dto.salepolicy;

import slfx.xl.pojo.dto.BaseXlDTO;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @类功能说明：价格政策快照DTO
 * @类修改者：
 * @修改日期：
 * @修改说明：
 * @公司名称：浙江汇购科技有限公司
 * @部门：技术部
 * @作者：tandeng
 * @创建时间：2014年12月17日下午2:32:14
 * 
 */
@SuppressWarnings("serial")
public class SalePolicySnapshotDTO extends BaseXlDTO {

	/**
	 * 快照来源实体
	 */
	private SalePolicyLineDTO salePolicy;

	/**
	 * 价格政策名称
	 */
	private String salePolicyName;

	/**
	 * 价格政策类型
	 */
	private Integer priceType;

	/**
	 * 所有快照信息，SalePolicy对象的JSON
	 */
	private String allInfoSalePolicyJSON;

	public SalePolicySnapshotDTO(SalePolicyLineDTO salePolicy) {
		this.salePolicy = salePolicy;
	}

	public SalePolicySnapshotDTO() {
		if (this.salePolicy != null) {
			create(salePolicy);
		}
	}

	public void create(SalePolicyLineDTO salePolicy) {
		this.salePolicy = salePolicy;

		// 订单搜索可能会用到的几个价格政策属性
		this.setSalePolicyName(salePolicy.getSalePolicyLineName());
		this.setPriceType(salePolicy.getPriceType());

		this.setAllInfoSalePolicyJSON(JSON.toJSONString(salePolicy));
	}

	public SalePolicyLineDTO getSalePolicy() {
		return salePolicy;
	}

	public void setSalePolicy(SalePolicyLineDTO salePolicy) {
		this.salePolicy = salePolicy;
	}

	public String getSalePolicyName() {
		return salePolicyName;
	}

	public void setSalePolicyName(String salePolicyName) {
		this.salePolicyName = salePolicyName;
	}

	public Integer getPriceType() {
		return priceType;
	}

	public void setPriceType(Integer priceType) {
		this.priceType = priceType;
	}

	public String getAllInfoSalePolicyJSON() {
		return allInfoSalePolicyJSON;
	}

	public void setAllInfoSalePolicyJSON(String allInfoSalePolicyJSON) {
		this.allInfoSalePolicyJSON = allInfoSalePolicyJSON;
	}

}